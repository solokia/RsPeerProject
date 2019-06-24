package AutoGildedPrayer;


import org.rspeer.runetek.adapter.component.InterfaceComponent;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.adapter.scene.Npc;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Dialog;
import org.rspeer.runetek.api.component.Interfaces;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.input.Keyboard;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.api.scene.SceneObjects;
import org.rspeer.runetek.event.listeners.RenderListener;
import org.rspeer.runetek.event.types.RenderEvent;
import org.rspeer.script.Script;
import org.rspeer.script.ScriptMeta;

import java.util.function.Predicate;
@ScriptMeta(developer = "Solly", desc = "AutoPray", name = "AutoPray")
public class AutoPrayer extends Script implements RenderListener {
    private Area housingArea;
    private Player me;
    private Predicate<Item> bonePred = item ->{
        if (item.getName().contains("bones")) {
            if(!item.isNoted())
                return item.getName().contains("bones");
        }
        else if (item.getName().contains("Bones"))
            if(!item.isNoted())
                return item.getName().contains("Bones");
        return false;
    };
    private Predicate<Item> notedBonePred = item ->{
        if (item.getName().contains("bones")) {
            if(item.isNoted())
                return item.getName().contains("bones");
        }
        else if (item.getName().contains("Bones"))
            if(item.isNoted())
                return item.getName().contains("Bones");
        return false;
    };
    private Area rimArea = Area.rectangular(2954, 3224, 2945, 3210);
    public void onStart(){
        me = Players.getLocal();
        housingArea = Area.surrounding(me.getPosition(),50);
    }

    @Override
    public int loop() {
        me = Players.getLocal();
        if(!Inventory.contains(notedBonePred)&&!Inventory.contains(bonePred)){
            return -1;
        }
        if(housingArea.contains(me)&& Inventory.contains(bonePred)&&!me.isAnimating()){
            checkAnimation();
        }
        if(housingArea.contains(me)&&!Inventory.contains(bonePred)&&!me.isAnimating()){
            exitHouse();
        }
        if(rimArea.contains(me)&&Inventory.contains(bonePred)&&!me.isMoving()){
            return enterHouse();
        }
        if(rimArea.contains(me)&&!Inventory.contains(bonePred)&&Inventory.contains(notedBonePred)&&!me.isMoving()){
            refill();
        }
        return Random.nextInt(300,500);
    }
    public int checkAnimation(){

            for (int i = 0; i < 20; i++) {
                if (me.isAnimating()) {
                    return 300;
                }

                Time.sleep(100, 250);
            }
            if(Inventory.contains(bonePred))
                pray();

        return 300;
    }
    public void pray(){
        Item bone = Inventory.getFirst(bonePred);
        bone.interact("Use");
        SceneObject altar = SceneObjects.getNearest("Altar");
        altar.interact("Use");
        Time.sleep(300,400);
    }
    public void exitHouse(){
        SceneObject portal = SceneObjects.getNearest("Portal");
        portal.interact("Enter");

        Time.sleep(1000,2000);
    }
    public int enterHouse() {
        SceneObject portal = SceneObjects.getNearest("Portal");
        portal.interact("Friend's house");
        InterfaceComponent textBox = Interfaces.getComponent(162, 45);
        Time.sleepUntil(() -> textBox.isVisible(), 300, 10000);
        Time.sleep(300,400);
        if (textBox.isVisible()){
            Keyboard.pressEnter();
            Time.sleepUntil(()->!rimArea.contains(me),300,10000);
        }
        if(!rimArea.contains(me)){
            return 300;
        }else
            return -1;
    }
    public void refill(){
        Item notedBone = Inventory.getFirst(notedBonePred);
        notedBone.interact("Use");
       // Position door = new Position(2959,3214);
      //  Movement.walkToRandomized(door);
       // Time.sleep(1000,3000);
        Npc phail = Npcs.getNearest("Phials");
        phail.interact("Use");
        Time.sleepUntil(()-> Dialog.isOpen(),300,10000);
        if(Dialog.isOpen()){
            Time.sleep(400,500);
            Dialog.process(2);
            Time.sleep(500,800);
        }
    }
    @Override
    public void notify(RenderEvent renderEvent) {

    }

}
