package mongo.serialize;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

public class MongoJsonSerializer {

    private static final String DEFAULT_COLLECTION_NAME = "data";

    private final DBCollection collection;

    public void addUniqueConstraint(String... constarints) {
        BasicDBObject index = new BasicDBObject();
        for (String constarint : constarints) {
            index.append(constarint, 1);
        }
        DBObject indexOptions = new BasicDBObject();
        indexOptions.put("unique", true);
        collection.createIndex(index, indexOptions);
    }

    public MongoJsonSerializer() throws UnknownHostException {

        collection = MongoDBFactory.getMongoDB().getCollection(DEFAULT_COLLECTION_NAME);

    }

    public MongoJsonSerializer(String collectionName) throws UnknownHostException {

        collection = MongoDBFactory.getMongoDB().getCollection(collectionName);

    }

    public void addJson(String json) {

        DBObject dbObject = (DBObject) JSON.parse(json);

        collection.insert(dbObject);

    }

    public List<String> getJson(String json) {
        DBCursor cursor;
        if (json != null) {
            DBObject dbObject = (DBObject) JSON.parse(json);
            cursor = collection.find(dbObject);
        }
        else {
            cursor = collection.find();
        }
        List<String> jsons = new ArrayList<String>();
        while (cursor.hasNext()) {
            jsons.add(JSON.serialize(cursor.next()));
        }
        return jsons;
    }

}
