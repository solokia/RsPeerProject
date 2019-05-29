package AIOFight.Task;

import AIOFight.Models.Config;

import org.rspeer.runetek.adapter.scene.Npc;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

import java.util.ArrayList;
import java.util.function.Predicate;

public class Fight extends Task {
    private static Player me;
    private static Area fightArea;
    private static ArrayList<String> mobsToFight=new ArrayList<>();
    private int health=20;
    @Override
    public boolean validate() {
        Log.info("Inside Fight validate");

        me = Players.getLocal();
        if(!Config.isConfigured()){
            return false;
        }
        try{
            fightArea.contains(me);
        }catch(Exception e){
            Log.info("Inside Fight validate exception");
            return false;
        }
        return !Inventory.isFull()
                && fightArea.contains(me)
                && !mobsToFight.isEmpty()
                && me.getHealthPercent()>health
                && !me.isMoving()
                && me.getTarget()==null
                && !me.isAnimating()
                && Inventory.contains(Config.getFood().getName());
    }

    @Override
    public int execute() {
        Log.info("Inside Fight execute");
        Predicate<Npc> npcPred = n -> mobsToFight.contains(n.getName())
                && ((n.getTarget()!=null&&n.getTarget().equals(me)));

        Npc selectedTarget = Npcs.getNearest(npcPred);
        if(selectedTarget ==null){// check for any npc attacking me before selecting nearest
            npcPred = n -> mobsToFight.contains(n.getName())
                    && (n.getTarget() == null||(n.getTarget()!=null&&n.getTarget().equals(me)));
            selectedTarget = Npcs.getNearest(npcPred);
        }

        selectedTarget.interact("Attack");
        return Random.nextInt(300,800);
    }

    public static Area getFightArea() {
        return fightArea;
    }

    public static void setFightArea(Area fightArea) {
        Fight.fightArea = fightArea;
    }

    public static ArrayList<String> getMobsToFight() {
        return mobsToFight;
    }

    public static void setMobsToFight(ArrayList<String> mobsToFight) {
        Log.info("setting mobs to fight");
        Fight.mobsToFight = mobsToFight;
        for (String mob :mobsToFight){
            Log.info(mob);
        }
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
