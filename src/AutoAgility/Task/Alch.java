package AutoAgility.Task;

import AutoAgility.model.*;
import org.rspeer.runetek.adapter.component.InterfaceComponent;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Interfaces;
import org.rspeer.runetek.api.component.tab.*;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

import java.util.function.Predicate;

public class Alch extends Task {
    private Player me;
    private int counter=0;
    private Predicate<Item> ipred= item->{
        if(item.isNoted())
            return item.isNoted();
        else if(item.getName().equals(Config.getAlchName()))
            return true;
        else
            return false;
    };
    @Override
    public boolean validate() {
        me = Players.getLocal();
//        Log.info("In Alch"+(me.isMoving()));
        return Config.isConfigured()
                &&!me.isAnimating()
                &&Config.isHighAlch()
                &&Inventory.contains("Nature rune")
                &&Inventory.contains(ipred);
    }

    @Override
    public int execute() {
        Log.info("alch execute");
        Item[] noteList ;
        noteList = Inventory.getItems(ipred); // check was done before entering function.
        Item item = Inventory.getFirst(noteList[0].getName());
        Time.sleep(350,500);

//        if(counter>Random.nextInt(4,7)){
//            Tabs.open(Tab.MAGIC);
//            Time.sleep(300,600);
//            counter= 0;
//        }
//        if (!Tabs.isOpen(Tab.MAGIC)){
//            counter++;
//        }else
//            Time.sleep(350,500);
        if(Magic.cast(Spell.Modern.HIGH_LEVEL_ALCHEMY,item)){
            MagicPaint.incAlch();
            MagicPaint.setMagicCurrentLevel(Skills.getLevel(Skill.MAGIC));
            MagicPaint.update();
            Time.sleep(200,300);
            return 300;
        }
//        Log.info("alch execute fail");
//        InterfaceComponent highAlchSpell = Interfaces.getComponent(Config.getMagicParentIndex(),Config.getMagicChildIndex());
//        if(!highAlchSpell.isVisible()){
//            counter++;
//        }
//        if(counter> Random.nextInt(4,7)){
//            if (!Tabs.isOpen(Tab.MAGIC))
//                Tabs.open(Tab.MAGIC);
//            Time.sleep(300,600);
//            counter= 0;
//        }
//        if (highAlchSpell.isVisible()) {
//            counter=0;
////                Log.info("enter interface");
//            Time.sleep(200, 300);
//            highAlchSpell.interact("");
//
//            Time.sleep(200, 300);
//
//
//
//            item.interact("Cast");
//            Time.sleep(200, 300);
//            MagicPaint.incAlch();
//            MagicPaint.setMagicCurrentLevel(Skills.getLevel(Skill.MAGIC));
////            MagicPaint.setMagicCurrentExp(Skills.getExperience(Skill.MAGIC));//might miss 1 cast
//            MagicPaint.update();
//        }
        return 300;
    }
}
