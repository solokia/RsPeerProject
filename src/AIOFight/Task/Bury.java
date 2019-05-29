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

public class Bury extends Task {

    private static Player me;
    private static int boneCount = 0;
    private Item[] invBones=null;
    private Item boneToBury;
    private static int maxBonesToBury =5, minBoneToBury =3;
    private Predicate<Item> bonePred = item ->{
        if (item.getName().contains("bones"))
            return item.getName().contains("bones");
        else if (item.getName().contains("Bones"))
            return item.getName().contains("Bones");
        return false;
    };


    @Override
    public boolean validate() {
        Log.info("Inside bury validate");
        me = Players.getLocal();
        if(!Config.isConfigured()){
            return false;
        }
        try{
            boneCount = Inventory.getCount(bonePred);
            Log.info("get Bone Count "+boneCount);
        }catch(Exception e){
            Log.info("get Bone Count error : "+e);
            boneCount=0;
            return false;
        }
        return (boneCount>Random.nextInt(minBoneToBury,maxBonesToBury)||(boneCount>0&&Inventory.isFull()))
                && !me.isMoving()
                && me.getTarget()==null
                && !me.isAnimating();
    }

    @Override
    public int execute() {
        Log.info("Inside bury execute");
        int playerhp = me.getHealthPercent();
        do {
            if(me.getHealthPercent()<playerhp)
                return 200;
            try{

                boneCount = Inventory.getCount(bonePred);
                Log.info("get Bone Count "+boneCount);
                invBones = Inventory.getItems(bonePred);
                boneToBury =invBones[Random.nextInt(invBones.length)];
                boneToBury.interact("Bury");
                Time.sleep(500,1000);
                boneCount--;
            }catch(Exception e){
                Log.info("get Bone Count error "+ e);
                boneCount=0;
            }

        }while(boneCount>0);

        try{
            Item invArrows = Inventory.getFirst("Steel knife");
            if(invArrows!=null){
                invArrows.interact("Wield");
            }
        }catch (Exception e){
            Log.info("no arrow");
        }

        return Random.nextInt(300,800);
    }
}
