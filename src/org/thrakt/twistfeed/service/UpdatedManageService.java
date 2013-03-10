package org.thrakt.twistfeed.service;

import org.slim3.datastore.Datastore;
import org.slim3.memcache.Memcache;
import org.thrakt.twistfeed.model.IdUpdatedModel;

public class UpdatedManageService {
    public static IdUpdatedModel getLastUpdated(String id) {
        IdUpdatedModel updated = Memcache.get(id);
        if (updated == null) {
            updated =
                Datastore
                    .getOrNull(
                        IdUpdatedModel.class,
                        Datastore.createKey(
                            IdUpdatedModel.class,
                            Long.valueOf(id)));
            if (updated != null) {
                Memcache.put(id, updated);
            }
        }
        return updated;
    }

    public static void putLastUpdated(IdUpdatedModel lastUpdate) {
        Memcache.put(String.valueOf(lastUpdate.getKey().getId()), lastUpdate);
        Datastore.put(lastUpdate);
    }

}
