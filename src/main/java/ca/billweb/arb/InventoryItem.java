package ca.billweb.arb;

import org.bson.types.ObjectId;

public class InventoryItem {
    private ObjectId id;

    private String itemName = "";
    private int count = 0;

    public InventoryItem() {}

    public InventoryItem(String itemName) {
        this.itemName = itemName;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
