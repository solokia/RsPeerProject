package MasterStealer;

import org.rspeer.runetek.api.movement.position.Position;

import java.util.HashSet;

public class Config  {

    private static HashSet<String> itemToLoot = new HashSet<>();
    private static int health=7;
    private static Position bankPosition;
    private static String food="Jug of wine";
    private static boolean configured = true;


    public  Config() {

    }



    public static HashSet<String> getItemToLoot() {
        return itemToLoot;
    }

    public static void setItemToLoot(HashSet<String> itemToLoot) {
        Config.itemToLoot = itemToLoot;
    }

    public static int getHealth() {
        return health;
    }

    public static void setHealth(int health) {
        Config.health = health;
    }

    public static Position getBankPosition() {
        return bankPosition;
    }

    public static void setBankPosition(Position bankPosition) {
        Config.bankPosition = bankPosition;
    }

    public static String getFood() {
        return food;
    }

    public static void setFood(String food) {
        Config.food = food;
    }

    public static boolean isConfigured() {
        return configured;
    }

    public static void setConfigured(boolean configured) {
        Config.configured = configured;
    }
}
