package BuyWoadLeaves;


import org.rspeer.runetek.adapter.scene.Npc;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Dialog;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.Script;
import org.rspeer.script.ScriptMeta;
import org.rspeer.ui.Log;

@ScriptMeta(developer = "Solly", desc = "Buy Woad Leaves", name = "BuyWoadLeaves")
public class BuyWoadLeaves extends Script {
    private Player me;
    private static Position npcPosition= new Position(3025,3379,0);
    private static Area buyArea = Area.surrounding(npcPosition,5,npcPosition.getFloorLevel());
    private static Npc wyson = Npcs.getNearest("Wyson the gardener");
    @Override
    public int loop() {
        Log.info("looping");
        me = Players.getLocal();
        if(!buyArea.contains(me)){
            Movement.walkToRandomized(buyArea.getCenter());
            Time.sleep(300,2000);
            return 300;
        }
        try {
            wyson = Npcs.getNearest("Wyson the gardener");
        }catch(Exception e){}
        if(!Dialog.isOpen()) {
            wyson.interact("Talk-to");
            Time.sleep(300, 800);
        }else{
            if(Dialog.canContinue())
                Dialog.processContinue();
            if(Dialog.getChatOptions().length==2)
                Dialog.process("Yes please, I need woad leaves");
            if(Dialog.isProcessing()) {
                Log.info("waiting");
                Time.sleepUntil(()->!Dialog.isProcessing(),200,1000);
                Time.sleep(100, 300);
            }
            if(Dialog.getChatOptions().length==4)
                Dialog.process(3);
            Time.sleep(300);
        }

        return Random.nextInt(300,500);
    }
}
