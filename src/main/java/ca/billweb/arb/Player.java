package ca.billweb.arb;

import ca.billweb.bot.Utils;
import ca.billweb.constants.GameParameters;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player {
    ObjectId id;

    private String userID;
    private String guildID;

    int lvl = 1, exp = 0;

    private Shield activeShield = null;

    private int curHP = 100, maxHP = 100;
    private int atk = 100, def = 100, dex = 100, agi = 100, intel = 100;
    private int bal = 0;
    private int bankBal = 0, bankLvl = 1;

    private Map<String, Long> lastActionTime = new HashMap<>();
    private Map<String, Long> actionCooldown = new HashMap<>();

    List<Slave> slaves = new ArrayList<>();
    List<String> traits = new ArrayList<>();
    HashMap<String, Integer> inventory = new HashMap<>();
    Slave activeSlave = null;

    public Player() {}

    public Player(String guildID, String userID) {
        this.guildID = guildID;
        this.userID = userID;
    }


    public void addMoney(int amount) {
        bal += amount;
    }

    public void addInventory(String item, int amount) {
        if(!inventory.containsKey(item)) inventory.put(item, amount);
        else inventory.put(item, inventory.get(item) + amount);
    }

    public void addBankBal(int amount) {
        bankBal += amount;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getGuildID() {
        return guildID;
    }

    public void setGuildID(String guildID) {
        this.guildID = guildID;
    }

    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getCurHP() {
        return curHP;
    }

    public void setCurHP(int curHP) {
        this.curHP = curHP;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public int getDex() {
        return dex;
    }

    public void setDex(int dex) {
        this.dex = dex;
    }

    public int getAgi() {
        return agi;
    }

    public void setAgi(int agi) {
        this.agi = agi;
    }

    public int getIntel() {
        return intel;
    }

    public void setIntel(int intel) {
        this.intel = intel;
    }

    public List<Slave> getSlaves() {
        return slaves;
    }

    public void setSlaves(List<Slave> slaves) {
        this.slaves = slaves;
    }

    public int getBal() {
        return bal;
    }

    public void setBal(int bal) {
        this.bal = bal;
    }

    public Slave getActiveSlave() {
        return activeSlave;
    }

    public void setActiveSlave(Slave activeSlave) {
        this.activeSlave = activeSlave;
    }

    public List<String> getTraits() {
        return traits;
    }

    public void setTraits(List<String> traits) {
        this.traits = traits;
    }

    public HashMap<String, Integer> getInventory() {
        return inventory;
    }

    public void setInventory(HashMap<String, Integer> inventory) {
        this.inventory = inventory;
    }

    public int getBankBal() {
        return bankBal;
    }

    public void setBankBal(int bankBal) {
        this.bankBal = bankBal;
    }

    public int getBankLvl() {
        return bankLvl;
    }

    public void setBankLvl(int bankLvl) {
        this.bankLvl = bankLvl;
    }

    public Map<String, Long> getLastActionTime() {
        return lastActionTime;
    }

    public void setLastActionTime(Map<String, Long> lastActionTime) {
        this.lastActionTime = lastActionTime;
    }

    public Map<String, Long> getActionCooldown() {
        return actionCooldown;
    }

    public void setActionCooldown(Map<String, Long> actionCooldown) {
        this.actionCooldown = actionCooldown;
    }

    public Shield getActiveShield() {
        return activeShield;
    }

    public void setActiveShield(Shield activeShield) {
        this.activeShield = activeShield;
    }

    public static Player generatePlayer(String guildID, String userID) {
        Player p = new Player(guildID, userID);
        p.setAtk(Utils.statRoll(GameParameters.BASE_PLAYER_ATK, GameParameters.MIN_STAT_ROLL, GameParameters.MAX_STAT_ROLL));
        p.setDef(Utils.statRoll(GameParameters.BASE_PLAYER_DEF, GameParameters.MIN_STAT_ROLL, GameParameters.MAX_STAT_ROLL));
        p.setDex(Utils.statRoll(GameParameters.BASE_PLAYER_DEX, GameParameters.MIN_STAT_ROLL, GameParameters.MAX_STAT_ROLL));
        p.setAgi(Utils.statRoll(GameParameters.BASE_PLAYER_AGI, GameParameters.MIN_STAT_ROLL, GameParameters.MAX_STAT_ROLL));
        p.setIntel(Utils.statRoll(GameParameters.BASE_PLAYER_INTEL, GameParameters.MIN_STAT_ROLL, GameParameters.MAX_STAT_ROLL));

        p.setBal(0);
        p.setBankBal(0);
        p.setBankLvl(1);
        p.setActiveSlave(null);

        p.setLvl(1);
        p.setExp(0);

        p.slaves = new ArrayList<>();
        p.setTraits(new ArrayList<>());
        p.setInventory(new HashMap<>());
        p.setLastActionTime(new HashMap<>());
        p.setActionCooldown(new HashMap<>());

        p.setMaxHP(GameParameters.BASE_PLAYER_HP);
        p.setCurHP(p.getMaxHP());
        return p;
    }
}


