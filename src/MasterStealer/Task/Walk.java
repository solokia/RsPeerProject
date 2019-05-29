package MasterStealer.Task;

import MasterStealer.Config;

import org.rspeer.runetek.adapter.scene.Npc;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

public class Walk extends Task {
    private Area bankArea = Area.rectangular(3092, 3245, 3093, 3240);
    private Player me ;
    private String name = "Master Farmer";
    private Area thieveArea = Area.polygonal(
            new Position[] {
                    new Position(3087, 3256, 0),
                    new Position(3087, 3244, 0),
                    new Position(3073, 3244, 0),
                    new Position(3073, 3257, 0)
            }
    );
    @Override
    public boolean validate() {
//        Log.info("Inside walk validate");
        me = Players.getLocal();


        return (traverseToBank()|| traverseToFight());
    }

    @Override
    public int execute() {
        Log.info("Walking");
        if(traverseToBank()){
            Log.info("Walking to bank");
            walkToBank();
        }
        if(traverseToFight()){
            Log.info("Walking to steal");
            walkToSteal();
        }
        return Random.nextInt(500,700);
    }
    public void walkToBank(){
        Position bankPosition = bankArea.getTiles().get(Random.nextInt(bankArea.getTiles().size()));
        Movement.walkTo(bankPosition);
        Time.sleep(300,2000);
    }
    public void walkToSteal(){
        Npc farmer=null;
        try {
            farmer = Npcs.getNearest(name);
        }catch(Exception e){
            Log.info("Walk to steal error "+e.toString());
        }
        if(farmer==null){
            Log.info("Can't find farmer walking to center");
            Movement.walkTo(thieveArea.getCenter().getPosition());
        }else
            Movement.walkTo(farmer.getPosition());

        Time.sleep(300,1500);
    }
    private boolean traverseToBank(){
        return (Inventory.isFull()||!Inventory.contains(Config.getFood()))&&!bankArea.contains(me);
    }

    private boolean traverseToFight(){
        return !thieveArea.contains(me)&&(!Inventory.isFull()&&Inventory.contains(Config.getFood()));
    }
}
