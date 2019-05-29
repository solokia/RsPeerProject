package MasterStealer.Task;


import MasterStealer.Config;
import org.rspeer.runetek.adapter.component.InterfaceComponent;
import org.rspeer.runetek.adapter.scene.Npc;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Interfaces;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

public class Steal extends Task {
    private Area thieveArea = Area.polygonal(
            new Position[] {
                    new Position(3087, 3256, 0),
                    new Position(3087, 3244, 0),
                    new Position(3073, 3244, 0),
                    new Position(3073, 3257, 0)
    }
);
    private static Player me ;
    private String name = "Master Farmer";
    private static int currentHp;
    private final static int PARENT_INDEX = 160;
    private final static int CHILD_INDEX = 5;
    private InterfaceComponent hpComp;
    @Override
    public boolean validate() {
        me = Players.getLocal();
        hpComp = Interfaces.getComponent(PARENT_INDEX, CHILD_INDEX);
        currentHp = Integer.parseInt(hpComp.getText());
//        Log.info("Validate Steal "+ me.getHealthPercent());
        return thieveArea.contains(me)
                &&( currentHp>Config.getHealth())
                && !Inventory.isFull()
                && Inventory.contains(Config.getFood());
    }

    @Override
    public int execute() {
        if(me.getGraphic()!=-1){
            if(Random.nextInt(1,50)>18){
                Log.info("Stunned random return");
                return Random.nextInt(300,1700);
            }

        }

        Npc target = Npcs.getNearest(name);
        currentHp = Integer.parseInt(hpComp.getText());
        if (currentHp<5){
            return 300;
        }
        target.interact("Pickpocket");
        Time.sleep(500,700);
        return Random.nextInt(300,700);
    }
}
