package AIOFight.Task;

import AIOFight.Models.Config;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

import java.util.function.Predicate;

public class Heal extends Task {
    private Player me;
    @Override
    public boolean validate() {
        if(!Config.isConfigured()){
            return false;
        }
        me= Players.getLocal();


        return Inventory.contains(Config.getFood().getName())
                &&me.getHealthPercent()<=Config.getHealth();
    }

    @Override
    public int execute() {
        Log.info("Executing Heal");
        int currentHp = me.getHealthPercent();
        if(currentHp>50)// in case hp not updated
            return 300;
        try {
            Predicate<Item> foodPred = item -> item.getName().contains(Config.getFood().getName());
            int foodCount = Inventory.getCount(foodPred);
            Log.info("get Bone Count "+foodCount);
            Item[] invFood = Inventory.getItems(foodPred);
            Item foodToEat =invFood[Random.nextInt(invFood.length)];
            foodToEat.interact("Eat");
            Log.info("sleeping for eat"+me.getHealthPercent());
            Time.sleepUntil(()->(currentHp<me.getHealthPercent()),300,Random.nextInt(1500,3000));
            Log.info("sleeping after eat"+me.getHealthPercent());
        }catch(Exception e){
            Log.severe(e);
        }

        return 300;
    }
}
