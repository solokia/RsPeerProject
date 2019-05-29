package AutoMine.Task;

import AutoMine.Constants.Rocks;

import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.api.scene.SceneObjects;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

import java.util.HashSet;

import java.util.function.Predicate;

public class Mining extends Task {
    private Player me;
    private static Rocks rocks;
    private static Area miningArea;
    private static HashSet<Position> rockList;
    private static SceneObject rockToMine;
    private static SceneObject rockCache;
    private static int missCount=0;
    private static Predicate<SceneObject> rock = rock -> {
        if(rock == null)
            return false;
        short[] colors = rock.getDefinition().getNewColors();
        if (colors == null || colors.length == 0)
            return false;
        if(!rock.getName().equals("Rocks"))
            return false;
        return rockList.contains(rock.getPosition());
    };


    @Override
    public boolean validate() {
        Log.info("Inside Mining");
        me = Players.getLocal();
        try{
            if(!miningArea.contains(me))
                return false;
        }catch(Exception e){
            return false;
        }

        return !Inventory.isFull()
//                && !me.isMoving()
                && miningArea.contains(me);
    }

    @Override
    public int execute() {
        Log.info("Mining execute");
        Time.sleep(100,300);
        if(me.isAnimating()){
            Log.info("Mining animating");
            try {
                rockCache = SceneObjects.getFirstAt(rockToMine.getPosition());
            }catch(Exception e){
                try {
                    rockToMine = getNextRock();
                    rockToMine.interact("Mine");
                    missCount++;
//                    rockCache = rockToMine.getId();
                } catch (Exception f) {
                    Log.severe(f);
                }
            }
            if (rockToMine.getId()==rockCache.getId()) {
                return Random.nextInt(300, 500);
            }

        }
        if(getNextRock()!=null) {
            rockToMine = getNextRock();
            rockToMine.interact("Mine");
            missCount = 0;
            Time.sleep(400,800);
        }
        return 330;
    }

    private static SceneObject getNextRock() {
        try {
            return SceneObjects.getNearest(rock);
        } catch (Exception e) {
            return null;
        }
    }

    public static Rocks getRocks() {
        return rocks;
    }

    public static void setRocks(Rocks rocks) {
        Mining.rocks = rocks;
    }

    public static Area getMiningArea() {
        return miningArea;
    }

    public static void setMiningArea(Area miningArea) {
        Mining.miningArea = miningArea;
    }

    public static HashSet<Position> getRockList() {
        return rockList;
    }

    public static void setRockList(HashSet<Position> rockList) {
        Mining.rockList = rockList;
    }

    public static int getMissCount() {
        return missCount;
    }

    public static void setMissCount(int missCount) {
        Mining.missCount = missCount;
    }
}
