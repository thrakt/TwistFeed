package org.thrakt.twistfeed.meta;

//@javax.annotation.Generated(value = { "slim3-gen", "@VERSION@" }, date = "2013-03-04 23:38:22")
/** */
public final class RequestTokenModelMeta extends org.slim3.datastore.ModelMeta<org.thrakt.twistfeed.model.RequestTokenModel> {

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<org.thrakt.twistfeed.model.RequestTokenModel, com.google.appengine.api.datastore.Key> key = new org.slim3.datastore.CoreAttributeMeta<org.thrakt.twistfeed.model.RequestTokenModel, com.google.appengine.api.datastore.Key>(this, "__key__", "key", com.google.appengine.api.datastore.Key.class);

    /** */
    public final org.slim3.datastore.UnindexedAttributeMeta<org.thrakt.twistfeed.model.RequestTokenModel, twitter4j.auth.RequestToken> token = new org.slim3.datastore.UnindexedAttributeMeta<org.thrakt.twistfeed.model.RequestTokenModel, twitter4j.auth.RequestToken>(this, "token", "token", twitter4j.auth.RequestToken.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<org.thrakt.twistfeed.model.RequestTokenModel, java.lang.Long> version = new org.slim3.datastore.CoreAttributeMeta<org.thrakt.twistfeed.model.RequestTokenModel, java.lang.Long>(this, "version", "version", java.lang.Long.class);

    private static final RequestTokenModelMeta slim3_singleton = new RequestTokenModelMeta();

    /**
     * @return the singleton
     */
    public static RequestTokenModelMeta get() {
       return slim3_singleton;
    }

    /** */
    public RequestTokenModelMeta() {
        super("RequestTokenModel", org.thrakt.twistfeed.model.RequestTokenModel.class);
    }

    @Override
    public org.thrakt.twistfeed.model.RequestTokenModel entityToModel(com.google.appengine.api.datastore.Entity entity) {
        org.thrakt.twistfeed.model.RequestTokenModel model = new org.thrakt.twistfeed.model.RequestTokenModel();
        model.setKey(entity.getKey());
        twitter4j.auth.RequestToken _token = blobToSerializable((com.google.appengine.api.datastore.Blob) entity.getProperty("token"));
        model.setToken(_token);
        model.setVersion((java.lang.Long) entity.getProperty("version"));
        return model;
    }

    @Override
    public com.google.appengine.api.datastore.Entity modelToEntity(java.lang.Object model) {
        org.thrakt.twistfeed.model.RequestTokenModel m = (org.thrakt.twistfeed.model.RequestTokenModel) model;
        com.google.appengine.api.datastore.Entity entity = null;
        if (m.getKey() != null) {
            entity = new com.google.appengine.api.datastore.Entity(m.getKey());
        } else {
            entity = new com.google.appengine.api.datastore.Entity(kind);
        }
        entity.setUnindexedProperty("token", serializableToBlob(m.getToken()));
        entity.setProperty("version", m.getVersion());
        entity.setProperty("slim3.schemaVersion", 1);
        return entity;
    }

    @Override
    protected com.google.appengine.api.datastore.Key getKey(Object model) {
        org.thrakt.twistfeed.model.RequestTokenModel m = (org.thrakt.twistfeed.model.RequestTokenModel) model;
        return m.getKey();
    }

    @Override
    protected void setKey(Object model, com.google.appengine.api.datastore.Key key) {
        validateKey(key);
        org.thrakt.twistfeed.model.RequestTokenModel m = (org.thrakt.twistfeed.model.RequestTokenModel) model;
        m.setKey(key);
    }

    @Override
    protected long getVersion(Object model) {
        org.thrakt.twistfeed.model.RequestTokenModel m = (org.thrakt.twistfeed.model.RequestTokenModel) model;
        return m.getVersion() != null ? m.getVersion().longValue() : 0L;
    }

    @Override
    protected void assignKeyToModelRefIfNecessary(com.google.appengine.api.datastore.AsyncDatastoreService ds, java.lang.Object model) {
    }

    @Override
    protected void incrementVersion(Object model) {
        org.thrakt.twistfeed.model.RequestTokenModel m = (org.thrakt.twistfeed.model.RequestTokenModel) model;
        long version = m.getVersion() != null ? m.getVersion().longValue() : 0L;
        m.setVersion(Long.valueOf(version + 1L));
    }

    @Override
    protected void prePut(Object model) {
    }

    @Override
    protected void postGet(Object model) {
    }

    @Override
    public String getSchemaVersionName() {
        return "slim3.schemaVersion";
    }

    @Override
    public String getClassHierarchyListName() {
        return "slim3.classHierarchyList";
    }

    @Override
    protected boolean isCipherProperty(String propertyName) {
        return false;
    }

    @Override
    protected void modelToJson(org.slim3.datastore.json.JsonWriter writer, java.lang.Object model, int maxDepth, int currentDepth) {
        org.thrakt.twistfeed.model.RequestTokenModel m = (org.thrakt.twistfeed.model.RequestTokenModel) model;
        writer.beginObject();
        org.slim3.datastore.json.Default encoder0 = new org.slim3.datastore.json.Default();
        if(m.getKey() != null){
            writer.setNextPropertyName("key");
            encoder0.encode(writer, m.getKey());
        }
        if(m.getToken() != null){
            writer.setNextPropertyName("token");
            encoder0.encode(writer, m.getToken());
        }
        if(m.getVersion() != null){
            writer.setNextPropertyName("version");
            encoder0.encode(writer, m.getVersion());
        }
        writer.endObject();
    }

    @Override
    protected org.thrakt.twistfeed.model.RequestTokenModel jsonToModel(org.slim3.datastore.json.JsonRootReader rootReader, int maxDepth, int currentDepth) {
        org.thrakt.twistfeed.model.RequestTokenModel m = new org.thrakt.twistfeed.model.RequestTokenModel();
        org.slim3.datastore.json.JsonReader reader = null;
        org.slim3.datastore.json.Default decoder0 = new org.slim3.datastore.json.Default();
        reader = rootReader.newObjectReader("key");
        m.setKey(decoder0.decode(reader, m.getKey()));
        reader = rootReader.newObjectReader("token");
        m.setToken(decoder0.decode(reader, m.getToken(), twitter4j.auth.RequestToken.class));
        reader = rootReader.newObjectReader("version");
        m.setVersion(decoder0.decode(reader, m.getVersion()));
        return m;
    }
}