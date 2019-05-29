package AIOFight.Models;

import AIOFight.Constants.ComplexMove;
import AIOFight.Constants.Food;
import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.movement.position.Position;

import java.util.ArrayList;
import java.util.HashSet;

public class Config {
    private static Area fightArea;
    private static Area bankArea;
    private static boolean complexMovement;
    private static ArrayList<ComplexMove> complexMoves;
    private static HashSet<String> itemToLoot = new HashSet<>();
    private static int maxBonesToBury =15,minBoneToBury=4;
    private static int health=50;
    private static Position bankPosition;
    private static Food food;
    private static boolean configured = false;

    public Config(){

    }
//    public Config(Area fightArea, Area bankArea, boolean complexMovement, ArrayList<ComplexMove> complexMoves, Position bankPosition, Food food) {
//        this.fightArea = fightArea;
//        this.bankArea = bankArea;
//        this.complexMovement = complexMovement;
//        this.complexMoves = complexMoves;
//        this.bankPosition = bankPosition;
//        this.food = food;
//    }

    public static Area getFightArea() {
        return fightArea;
    }

    public static void setFightArea(Area fightArea) {
        Config.fightArea = fightArea;
    }

    public static Area getBankArea() {
        return bankArea;
    }

    public static void setBankArea(Area bankArea) {
        Config.bankArea = bankArea;
    }

    public static boolean isComplexMovement() {
        return complexMovement;
    }

    public static void setComplexMovement(boolean complexMovement) {
        Config.complexMovement = complexMovement;
    }

    public static ArrayList<ComplexMove> getComplexMoves() {
        return complexMoves;
    }

    public static void setComplexMoves(ArrayList<ComplexMove> complexMoves) {
        Config.complexMoves = complexMoves;
    }

    public static int getMaxBonesToBury() {
        return maxBonesToBury;
    }

    public static void setMaxBonesToBury(int maxBonesToBury) {
        Config.maxBonesToBury = maxBonesToBury;
    }

    public static int getMinBoneToBury() {
        return minBoneToBury;
    }

    public static void setMinBoneToBury(int minBoneToBury) {
        Config.minBoneToBury = minBoneToBury;
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

    public static HashSet<String> getItemToLoot() {
        return itemToLoot;
    }

    public static void setItemToLoot(HashSet<String> itemToLoot) {
        Config.itemToLoot = itemToLoot;
    }
}
