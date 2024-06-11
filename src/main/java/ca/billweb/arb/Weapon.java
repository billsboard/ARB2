package ca.billweb.arb;

import org.bson.types.ObjectId;

public class Weapon {
    private ObjectId id;
    private String name = "Test Weapon", description = "A test weapon for development";
    private int baseDmg = 1, cost = 1;
    private double rollVarianceMultiplier = 1, accuracyModifier;

    private String[] traits = {};


    public Weapon() {}

    public Weapon(String name, String description, int cost, int baseDmg, double rollVarianceMultiplier, double accuracyModifier) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.baseDmg = baseDmg;
        this.rollVarianceMultiplier = rollVarianceMultiplier;
        this.accuracyModifier = accuracyModifier;
    }

    public Weapon(String name, String description, int cost, int baseDmg, double rollVarianceMultiplier, double accuracyModifier, String... traits) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.baseDmg = baseDmg;
        this.rollVarianceMultiplier = rollVarianceMultiplier;
        this.accuracyModifier = accuracyModifier;
        this.traits = traits;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getBaseDmg() {
        return baseDmg;
    }

    public void setBaseDmg(int baseDmg) {
        this.baseDmg = baseDmg;
    }

    public double getRollVarianceMultiplier() {
        return rollVarianceMultiplier;
    }

    public void setRollVarianceMultiplier(double rollVarianceMultiplier) {
        this.rollVarianceMultiplier = rollVarianceMultiplier;
    }

    public double getAccuracyModifier() {
        return accuracyModifier;
    }

    public void setAccuracyModifier(double accuracyModifier) {
        this.accuracyModifier = accuracyModifier;
    }

    public String[] getTraits() {
        return traits;
    }

    public void setTraits(String[] traits) {
        this.traits = traits;
    }
}
