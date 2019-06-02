package AutoAgility.model;

import AutoAgility.Constants.Courses;
import AutoAgility.Constants.Food;
import AutoAgility.Constants.SubCourses;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.api.commons.BankLocation;
import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.movement.position.Position;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.function.Predicate;

public class Config {
//    private static Courses course;
    private static LinkedList<SubCourse> course= new LinkedList<>();
    private static Food food;
    private static boolean configured = false;
    private static int health=15;
    private static int numberOfFood=8;
    private static int upperEnergy = 65;
    private static int lowerEnergy = 50;
    private static boolean highAlch = true;
    private static int MAGIC_PARENT_INDEX = 218;
    private static int MAGIC_CHILD_INDEX = 38;
    private static Position bankPos = BankLocation.getNearest().getPosition();
    private static Area bankArea = Area.surrounding(bankPos,4);
    private static Predicate<Item> itemPredicate = item -> {
        if(item.getName().contains("Stamina potion"))
            return item.getName().contains("Stamina potion");
        else
            return false;
    };
    private static int numberOfPot = 4;
    private static String alchName = "Onyx bolts (e)";

    public Config() {
    }

    public static LinkedList<SubCourse> getCourse() {
        return course;
    }

    public static void setCourse(Courses c) {

        for (SubCourses s : c.getSubCourses()){
            course.add(s.getSubCourse());
        }
    }

    public static Food getFood() {
        return food;
    }

    public static void setFood(Food food) {
        Config.food = food;
    }

    public static boolean isConfigured() {
        return configured;
    }

    public static void setConfigured(boolean configured) {
        Config.configured = configured;
    }

    public static int getHealth() {
        return health;
    }

    public static void setHealth(int health) {
        Config.health = health;
    }

    public static int getUpperEnergy() {
        return upperEnergy;
    }

    public static void setUpperEnergy(int upperEnergy) {
        Config.upperEnergy = upperEnergy;
    }

    public static int getLowerEnergy() {
        return lowerEnergy;
    }

    public static void setLowerEnergy(int lowerEnergy) {
        Config.lowerEnergy = lowerEnergy;
    }

    public static boolean isHighAlch() {
        return highAlch;
    }

    public static void setHighAlch(boolean highAlch) {
        Config.highAlch = highAlch;
    }

    public static int getMagicParentIndex() {
        return MAGIC_PARENT_INDEX;
    }

    public static void setMagicParentIndex(int magicParentIndex) {
        MAGIC_PARENT_INDEX = magicParentIndex;
    }

    public static int getMagicChildIndex() {
        return MAGIC_CHILD_INDEX;
    }

    public static void setMagicChildIndex(int magicChildIndex) {
        MAGIC_CHILD_INDEX = magicChildIndex;
    }

    public static Predicate<Item> getItemPredicate() {
        return itemPredicate;
    }

    public static Position getBankPos() {
        return bankPos;
    }

    public static void setBankPos(Position bankPos) {
        Config.bankPos = bankPos;
    }

    public static Area getBankArea() {
        return bankArea;
    }

    public static void setBankArea(Area bankArea) {
        Config.bankArea = bankArea;
    }

    public static int getNumberOfFood() {
        return numberOfFood;
    }

    public static void setNumberOfFood(int numberOfFood) {
        Config.numberOfFood = numberOfFood;
    }

    public static int getNumberOfPot() {
        return numberOfPot;
    }

    public static void setNumberOfPot(int numberOfPot) {
        Config.numberOfPot = numberOfPot;
    }

    public static String getAlchName() {
        return alchName;
    }

    public static void setAlchName(String alchName) {
        Config.alchName = alchName;
    }
}
