package ca.billweb.arb;

import org.bson.types.ObjectId;

public class ServerObject {

    private ObjectId id;
    private String guildID;

    // Game settings
    private boolean captureDuplicateSlave = true;

    public ServerObject() {}

    public ServerObject(String guildID) {
        this.guildID = guildID;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getGuildID() {
        return guildID;
    }

    public void setGuildID(String guildID) {
        this.guildID = guildID;
    }

    public boolean isCaptureDuplicateSlave() {
        return captureDuplicateSlave;
    }

    public void setCaptureDuplicateSlave(boolean captureDuplicateSlave) {
        this.captureDuplicateSlave = captureDuplicateSlave;
    }
}
