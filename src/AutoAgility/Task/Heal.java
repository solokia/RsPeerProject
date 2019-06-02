package AutoAgility.Task;

import AutoAgility.model.*;
import org.rspeer.runetek.adapter.component.InterfaceComponent;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Interfaces;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.component.tab.Skill;
import org.rspeer.runetek.api.component.tab.Skills;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

import java.util.function.Predicate;

public class Heal extends Task {
    private static Player me;
    private static int currentHp;
    private static int maxHP;
    private final static int PARENT_INDEX = 160;
    private final static int CHILD_INDEX = 5;
    private InterfaceComponent hpComp;
    @Override
    public boolean validate() {

        if(!Config.isConfigured()){
            return false;
        }
        me= Players.getLocal();
        currentHp = Skills.getCurrentLevel(Skill.HITPOINTS);
        maxHP = Skills.getLevel(Skill.HITPOINTS);
//        hpComp = Interfaces.getComponent(PARENT_INDEX, CHILD_INDEX);
//        currentHp = Integer.parseInt(hpComp.getText());
        return Config.isConfigured()
                &&Inventory.contains(Config.getFood().getName())
                &&currentHp<=Config.getHealth();
    }

    @Override
    public int execute() {
        Log.info("Executing Heal");
        currentHp = Skills.getCurrentLevel(Skill.HITPOINTS);
//        Time.sleepUntil(()->me.getGraphic()!=-1,300,4000);
        if(currentHp>Config.getHealth()) {// in case hp not updated
            Log.info("current hp : "+currentHp+" config hp "+Config.getHealth());
            return 300;
        }
        try {
            Predicate<Item> foodPred = item -> item.getName().contains(Config.getFood().getName());
            int foodCount = Inventory.getCount(foodPred);
            Log.info("get food Count "+foodCount);
            Item[] invFood = Inventory.getItems(foodPred);
            Item foodToEat =invFood[Random.nextInt(invFood.length)];
            foodToEat.interact(Config.getFood().getAction());
            Log.info("sleeping for eat"+Skills.getCurrentLevel(Skill.HITPOINTS));
            Time.sleepUntil(()->(currentHp<Skills.getCurrentLevel(Skill.HITPOINTS)),300,Random.nextInt(2000,3000));
            Log.info("sleeping after eat"+Skills.getCurrentLevel(Skill.HITPOINTS));
        }catch(Exception e){
            Log.severe(e);
        }

        return Random.nextInt(500,700);
    }
}
