package mongo.serialize;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.MongoClient;

public final class MongoDBFactory {

    private static final int    PORT    = 27017;
    private static final String IP      = "localhost";
    private static final String DB_NAME = "smart_agent";
    private static MongoClient  client  = null;

    public static synchronized DB getMongoDB() throws UnknownHostException {
        if (client == null) {
            client = new MongoClient(IP, PORT);
        }
        return client.getDB(DB_NAME);
    }
}