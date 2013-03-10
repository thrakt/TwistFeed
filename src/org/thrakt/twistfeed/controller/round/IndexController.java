package org.thrakt.twistfeed.controller.round;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;
import org.thrakt.twistfeed.model.IdUpdatedModel;

import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;

public class IndexController extends Controller {

    @Override
    public Navigation run() throws Exception {
        List<IdUpdatedModel> idList =
            Datastore.query(IdUpdatedModel.class).asList();
        StringBuffer url = request.getRequestURL();
        String host = url.substring(0, url.indexOf("/", 10));

        List<Future<HTTPResponse>> responseList =
            new ArrayList<Future<HTTPResponse>>(idList.size());

        for (IdUpdatedModel model : idList) {
            long id = model.getKey().getId();
            responseList.add(URLFetchServiceFactory
                .getURLFetchService()
                .fetchAsync(new URL(host + "/feed/id/" + id)));
        }

        for (Future<HTTPResponse> response : responseList) {
            response.get();
        }

        return null;
    }
}
