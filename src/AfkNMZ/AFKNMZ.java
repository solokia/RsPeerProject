package AfkNMZ;

import org.rspeer.runetek.adapter.component.InterfaceComponent;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Interfaces;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.component.tab.Skill;
import org.rspeer.runetek.api.component.tab.Skills;
import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.Script;
import org.rspeer.script.ScriptMeta;
import org.rspeer.ui.Log;


import java.util.function.Predicate;

@ScriptMeta(developer = "Solly", desc = "AFK", name = "AfkNMZ")

public class AFKNMZ extends Script {
    private final int PARENT_INDEX = 202;
    private final int CHILD_INDEX = 3;
    private final int SUB_INDEX = 5;
    private InterfaceComponent absPotInterface =  Interfaces.getComponent(PARENT_INDEX, CHILD_INDEX,SUB_INDEX);
    private int absCount =0;
    private Item rockCake;
    private Player me;
    private static Predicate<Item> itemPredicate = item -> {
        if(item.getName().contains("Absorption"))
            return item.getName().contains("Absorption");
        else
            return false;
    };
    private static Predicate<Item> itemPredicate2 = item -> {
        if(item.getName().contains("Super ranging"))
            return item.getName().contains("Super ranging");
        else
            return false;
    };
    private Area startArea = Area.rectangular(2598, 3121, 2610, 3109);
    private boolean loopStop =false;
    @Override
    public void onStart(){
        rockCake = Inventory.getFirst("Dwarven rock cake");

    }
    @Override
    public int loop() {
        absPotInterface =  Interfaces.getComponent(PARENT_INDEX, CHILD_INDEX,SUB_INDEX);
        int currentHp = Skills.getCurrentLevel(Skill.HITPOINTS);
        int currentRange = Skills.getCurrentLevel(Skill.RANGED);
        me = Players.getLocal();
        if(startArea.contains(me)){
            Log.info("Back to starting area stopping ");
            return -1;
        }
        if(absPotInterface.isVisible())
            absCount =Integer.parseInt(absPotInterface.getText());
        else
            absCount = 0;
        if(!Inventory.contains(itemPredicate)&&!Inventory.contains(itemPredicate2)&&absCount<100){
            Log.info("empty pot ending");
            return -1;
        }

        if(Inventory.contains(itemPredicate)) {
            if (absCount <= 300 + Random.nextInt(10, 50)){
                Item absPot = Inventory.getFirst(itemPredicate);
//                int absCount = Inventory.getCount(itemPredicate);
                while(Inventory.contains(itemPredicate)&&absCount<500+Random.nextInt(10,100)){
                    try {
                        absPot = Inventory.getFirst(itemPredicate);
                        absPot.interact("Drink");
                        if(absCount==0)
                            Time.sleep(1000);
                        else
                            Time.sleep(400,800);
                        absPotInterface = Interfaces.getComponent(PARENT_INDEX, CHILD_INDEX, SUB_INDEX);
                        absCount = Integer.parseInt(absPotInterface.getText());
                        Log.info("absorption count "+absCount);
                        if(loopStop)
                            break;
                    } catch(Exception e){
                        Log.info("Exception in absorption "+e.toString());
                        break;
                    }
                }
            }
        }

        if(currentHp>1){
            if(currentHp>3){
                loopCake(currentHp);
            }
            if(Random.nextInt(1,99)>50){
                loopCake(currentHp);
            }
        }

        if (currentRange<=Skills.getLevel(Skill.RANGED)){
            if (Inventory.contains(itemPredicate2)){
                Item rangePot = Inventory.getFirst(itemPredicate2);
                rangePot.interact("Drink");
                Time.sleep(1000);
            }
        }
        return 60000;
    }
    @Override
    public void onStop(){
        loopStop=true;
    }
    private void loopCake(int times){
        Log.info("Eating hp : "+ Skills.getCurrentLevel(Skill.HITPOINTS));
        if(times>20)
            times=20;
        for(int i =times;i>1;i--){
            rockCake.interact("Guzzle");
            Time.sleep(1000);
            if(loopStop||Skills.getCurrentLevel(Skill.HITPOINTS)==1)
                break;
        }
    }
    @Override
    public void onPause(){
        Log.info("testing");
        setStopping(true);
    }
}
