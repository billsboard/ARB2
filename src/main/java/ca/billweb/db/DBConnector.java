package ca.billweb.db;

import ca.billweb.arb.Player;
import ca.billweb.arb.ServerObject;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.InsertOneOptions;
import com.mongodb.client.model.ReplaceOptions;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.Arrays;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class DBConnector {
    public static MongoClient mc;
    public static MongoDatabase db;

    public static MongoCollection<ServerObject> serverCollection;

    public static void initConnection() {
        String dbName = System.getenv("ARB_DB");


        ServerAddress s1 = new ServerAddress("localhost", 27017);
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyToClusterSettings(builder ->
                        builder.hosts(Arrays.asList(s1)))
                .build();
        mc = MongoClients.create(settings);

        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));


        db = mc.getDatabase(dbName).withCodecRegistry(pojoCodecRegistry);
        serverCollection = db.getCollection("servers", ServerObject.class);

        System.out.println("Connected to Database");
    }

    public static boolean guildExists(String guildID) {
        FindIterable<ServerObject> it = serverCollection.find(Filters.eq("guildID", guildID));
        ServerObject s = it.first();
        return s != null;
    }

    public static ServerObject loadServer(String guildID) {
        return loadServer(guildID, true);
    }

    public static ServerObject loadServer(String guildID, boolean createIfMissing) {
        boolean exists = guildExists(guildID);
        if(!exists && !createIfMissing) return null;
        else if(!exists) {
            ServerObject server = new ServerObject(guildID);
            serverCollection.insertOne(server);
            return server;
        }
        else {
            return serverCollection.find(Filters.eq("guildID", guildID)).first();
        }
    }


    public static MongoCollection<Player> getPlayerCollection(String guildID) {
        ServerObject so = loadServer(guildID);
        return db.getCollection(so.getGuildID() + "-data", Player.class);
    }

    public static boolean playerExists(String guildID, String userID) {
        loadServer(guildID);
        MongoCollection<Player> collection = getPlayerCollection(guildID);
        return collection.find(Filters.eq("userID", userID)).first() != null;
    }
    public static Player getPlayer(String guildID, String userID) {
        return getPlayer(guildID, userID, true);
    }

    public static Player getPlayer(String guildID, String userID, boolean createIfMissing) {
        if(playerExists(guildID, userID)) {
            MongoCollection<Player> collection = getPlayerCollection(guildID);
            return collection.find(Filters.eq("userID", userID)).first();
        }
        else if(createIfMissing) {
            Player p = Player.generatePlayer(guildID, userID);
            MongoCollection<Player> collection = getPlayerCollection(guildID);
            collection.insertOne(p);
            return p;
        }
        return null;
    }

    public static void savePlayer(Player p) {
        MongoCollection<Player> collection = getPlayerCollection(p.getGuildID());
        collection.replaceOne(Filters.eq("_id", p.getId()), p, new ReplaceOptions().upsert(true));
    }
}
