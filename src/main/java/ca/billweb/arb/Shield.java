package ca.billweb.arb;

import org.bson.types.ObjectId;

public class Shield {
    ObjectId id;


    public Shield() {}

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }
}
