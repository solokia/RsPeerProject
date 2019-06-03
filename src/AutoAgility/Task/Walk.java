package AutoAgility.Task;

import AutoAgility.model.*;

import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.api.scene.SceneObjects;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;


public class Walk extends Task {
    private Player me;
//    private Task alch = new Alch() ;
    @Override
    public boolean validate() {

        me = Players.getLocal();

        return Config.isConfigured()
                &&me.getFloorLevel()==0;
    }

    @Override
    public int execute() {
        if (Movement.getRunEnergy() > Random.nextInt(30, 40) && !Movement.isRunEnabled())
            Movement.toggleRun(true);
        if ((!Inventory.contains(Config.getFood().getName()) || !Inventory.contains(Config.getItemPredicate())) && !Config.getBankArea().contains(me)){
            Log.info("Walking to Bank");
            walkToBank();
        }
        else if(Inventory.contains(Config.getFood().getName()) || Inventory.contains(Config.getItemPredicate()))
            walkToStart();
        return 300;
    }
    public void walkToBank(){
        Movement.walkToRandomized(Config.getBankPos());
        Time.sleep(500,700);
    }
    public void walkToStart(){
        Position runTo = Config.getCourse().get(0).getObstaclePos();
//        if(alch.validate()){
//            Log.info("HA");
//            alch.execute();
//        }
        if(!Area.surrounding(runTo,5).contains(me))
            Movement.walkTo(runTo);
        else{
            SceneObject courseStart = SceneObjects.getNearest(Config.getCourse().get(0).getObstacleName());
//            Log.info(courseStart.getActions().toString());
            courseStart.interact(courseStart.getActions()[0]);
        }
        Time.sleep(400,700);
    }
}
