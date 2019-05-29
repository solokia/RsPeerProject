package AIOFight.Task;

import AIOFight.Models.Config;
import org.rspeer.runetek.adapter.scene.Pickable;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.scene.Pickables;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

import java.util.HashSet;

public class Loot extends Task {
    private static Player me;
    private static Area fightArea;
    private static HashSet<String> itemToLoot = new HashSet<>();
    private int health=20;
    private int itemDist=4;
    private Pickable p;
    private Config config;
    @Override
    public boolean validate() {
        if(!Config.isConfigured()){
            return false;
        }
        try {

            Log.info("Inside loot validate");
            String[] items = itemToLoot.toArray(new String[itemToLoot.size()]);
            p = Pickables.getNearest(x -> itemToLoot.contains(x.getName()) && x.distance(me) < itemDist);
            Log.info(fightArea.contains(p));
            fightArea.contains(me);
            fightArea.contains(p);
            Log.info("finish check loot validate");
        }catch(Exception e){
            Log.info("Nothing to loot?");
            return false;
        }
        me = Players.getLocal();
        return !Inventory.isFull()
                && fightArea.contains(me)
                && !itemToLoot.isEmpty()
                && me.getHealthPercent()>health //might not need health check
                && !me.isMoving()
                && me.getTarget()==null
                && !me.isAnimating()
                && p!=null;
    }

    @Override
    public int execute() {
        Log.info("Inside loot execute");
        do {
            try {

                Log.info("Looping loot execute");
                p = Pickables.getNearest(x -> itemToLoot.contains(x.getName()) && x.distance(me) < itemDist);
                if(Inventory.isFull())
                    return Random.nextInt(300,500);
                if(me.isAnimating())
                    return Random.nextInt(300,800); //probably got attacked since pick has no animation might change to hp later
                if(p==null)
                    return Random.nextInt(300,800);
                if(!me.isMoving()) {
                    Log.info("Picking up"+p.getName());
                    p.interact("Take");
                    Time.sleep(Random.nextInt(300,500));//delay for taking
                    Time.sleepUntil(()->!me.isMoving(),300,Random.nextInt(3000,4000));//delay for moving to take
                    Log.info("Sleep over for"+p.getName());
                }

            }catch (Exception e){
                Log.info("Looting error "+e);
            }
        }while(p!=null);
        return Random.nextInt(300,800);
    }

    public static Area getFightArea() {
        return fightArea;
    }

    public static void setFightArea(Area fightArea) {
        Loot.fightArea = fightArea;
    }

    public static HashSet<String> getItemToLoot() {
        return itemToLoot;
    }

    public static void setItemToLoot(HashSet<String> itemToLoot) {
        Loot.itemToLoot = itemToLoot;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getItemDist() {
        return itemDist;
    }

    public void setItemDist(int itemDist) {
        this.itemDist = itemDist;
    }
}
