package ca.billweb.bot;

import ca.billweb.arb.Player;
import ca.billweb.db.DBConnector;

import java.util.HashMap;

public class Utils {

    public static final double EPSILON = 1e-6;

    public static String formatMention(String userID) {
        return "<@" + userID + ">";
    }
    public static int statRoll(int base, double rollMin, double rollMax) {
        return (int) (base * randDouble(rollMin, rollMax));
    }

    // Inclusive
    public static int randInt(int low, int high) {
        if(low == high) return low;
        else if(low > high) {
            int tmp = low;
            low = high;
            high = tmp;
        }
        return (int) ((high - low + 1) * Math.random() + low);
    }

    public static double randDouble(double low, double high) {
        if(Math.abs(high - low) < EPSILON) return low;
        else if(low > high) {
            double tmp = high;
            high = low;
            low = tmp;
        }
        return (Math.random() * (high - low)) + low;
    }

    public static boolean checkTimerRatelimit(Player p, String timer) {
        if(p.getLastActionTime() == null) p.setLastActionTime(new HashMap<>());
        if(p.getActionCooldown() == null) p.setActionCooldown(new HashMap<>());

        if(!p.getLastActionTime().containsKey(timer)) {
            p.getLastActionTime().put(timer, 0L);
            return true;
        }
        else if(!p.getActionCooldown().containsKey(timer)) {
            p.getActionCooldown().put(timer, 0L);
            return true;
        }

        return System.currentTimeMillis() > p.getLastActionTime().get(timer) + p.getActionCooldown().get(timer);
    }

    public static void executeAction(Player p, String timer, long cooldown) {
        p.getLastActionTime().put(timer, System.currentTimeMillis());
        p.getActionCooldown().put(timer, cooldown);

        DBConnector.savePlayer(p);
    }
}
