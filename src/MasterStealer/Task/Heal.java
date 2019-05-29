package MasterStealer.Task;

import MasterStealer.Config;
import org.rspeer.runetek.adapter.component.InterfaceComponent;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Interfaces;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

import java.util.function.Predicate;

public class Heal extends Task {
    private static Player me;
    private static int currentHp;
    private final static int PARENT_INDEX = 160;
    private final static int CHILD_INDEX = 5;
    private InterfaceComponent hpComp;
    @Override
    public boolean validate() {
        if(!Config.isConfigured()){
            return false;
        }
        me= Players.getLocal();
//        Log.info("Heal Inventory contains "+Inventory.contains(Config.getFood()));
        hpComp = Interfaces.getComponent(PARENT_INDEX, CHILD_INDEX);
        currentHp = Integer.parseInt(hpComp.getText());
        return Inventory.contains(Config.getFood())
                &&currentHp<=Config.getHealth();
    }

    @Override
    public int execute() {
        Log.info("Executing Heal");
        currentHp = Integer.parseInt(hpComp.getText());
//        Time.sleepUntil(()->me.getGraphic()!=-1,300,4000);
        if(currentHp>Config.getHealth()) {// in case hp not updated
            Log.info("current hp : "+currentHp+" config hp "+Config.getHealth());
            return 300;
        }
        try {
            Predicate<Item> foodPred = item -> item.getName().contains(Config.getFood());
            int foodCount = Inventory.getCount(foodPred);
            Log.info("get Bone Count "+foodCount);
            Item[] invFood = Inventory.getItems(foodPred);
            Item foodToEat =invFood[Random.nextInt(invFood.length)];
            foodToEat.interact("Drink");
            Log.info("sleeping for eat"+me.getHealthPercent());
            Time.sleepUntil(()->(currentHp<Integer.parseInt(hpComp.getText())),300,Random.nextInt(2000,3000));
            Log.info("sleeping after eat"+me.getHealthPercent());
        }catch(Exception e){
            Log.severe(e);
        }

        return Random.nextInt(500,700);
    }
}
