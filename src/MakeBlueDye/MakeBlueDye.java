package MakeBlueDye;

import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.adapter.scene.Npc;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.commons.BankLocation;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.Dialog;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.Script;
import org.rspeer.script.ScriptMeta;
import org.rspeer.ui.Log;


@ScriptMeta(developer = "Solly", desc = "Make blue dyes", name = "MakeBlueDye")
public class MakeBlueDye extends Script {
    private static Position npcPosition = new Position(3086,3258,0);
    private static Area buyArea = Area.surrounding(npcPosition,4,npcPosition.getFloorLevel());
    private static Area bankArea = Area.surrounding(BankLocation.DRAYNOR.getPosition(),3);
    private static Npc aggie = Npcs.getNearest("Aggie");
    private static Item woad;
    private Player me;
    @Override
    public int loop() {
        Log.info("looping");
        if(!Inventory.contains("Woad leaf"))
            return -1;
        me = Players.getLocal();
        if(!buyArea.contains(me)&&!Inventory.isFull()&&Inventory.contains("Woad leaf")&&Inventory.contains("Coins")){
            Movement.walkToRandomized(buyArea.getCenter());
            Time.sleep(300,2000);
            return 300;
        }
        if(Inventory.isFull()&& !bankArea.contains(me)){
            Log.info("walking to bank");
            Movement.walkToRandomized(bankArea.getCenter());
            Time.sleep(3000, 5000);
        }
        if(Inventory.isFull()&& bankArea.contains(me)){
            Log.info("Banking");
//            Movement.walkTo(BankLocation.getNearest().getPosition());
//            Time.sleep(1000,3000);
            if (Bank.isOpen()) {
                Log.info("Depositing");
                Bank.depositAll("Blue dye");
                Time.sleep(1000,3500);
                Bank.close();
                Time.sleep(500,1500);
            } else {
                Bank.open();
                Time.sleepUntil(() -> Bank.isOpen(), 1000);
            }
        }
        try {
            aggie = Npcs.getNearest("Aggie");
            if(!Dialog.isOpen()&&!Inventory.isFull()&&buyArea.contains(me)) {
                woad = Inventory.getFirst("Woad leaf");
                woad.interact("Use");
                Time.sleep(300,600);
                aggie.interact("Use");
                Time.sleepUntil(()->Dialog.isOpen(),4000);
            }else{
                if(Dialog.canContinue())
                    Dialog.processContinue();

                if(Dialog.isProcessing()) {
                    Log.info("waiting");
                    Time.sleepUntil(()->!Dialog.isProcessing(),200,1000);
                    Time.sleep(100, 200);
                }

                Time.sleep(300);
            }
        }catch(Exception e){}


        return Random.nextInt(300,500);

    }
}
