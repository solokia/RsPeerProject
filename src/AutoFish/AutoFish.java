package AutoFish;

import AutoFish.Constant.FishingLocation;
import org.rspeer.runetek.adapter.scene.Npc;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.commons.BankLocation;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.Script;
import org.rspeer.script.ScriptMeta;
import org.rspeer.ui.Log;

@ScriptMeta(developer = "Solly", desc = "AutoFish", name = "AutoFisher")
public class AutoFish extends Script {
//    private static FishingLocation;
    private static Area bankArea;
    private static int randomT;
    private static int randomTile;
    private static Position randomPosition;
    @Override
    public int loop() {

//        if (Dialog.canContinue())
//            Dialog.processContinue();
        bankArea = Area.surrounding(BankLocation.DRAYNOR.getPosition(),3);
        Player me = Players.getLocal();

        if(!FishingLocation.Draynor.getArea().contains(me)&&!Inventory.isFull()) {
            Log.info("walking to fish");
            Log.info(FishingLocation.Draynor.getArea().contains(me));

            randomTile = Random.nextInt(FishingLocation.Draynor.getArea().getTiles().size()-1);
            randomPosition = FishingLocation.Draynor.getArea().getTiles().get(randomTile);
            Movement.walkTo(randomPosition );
            Time.sleep(3000, 5000);
        }
        else if(!me.isAnimating()&&!Inventory.isFull()){
            Log.info("Fishing");
            Npc fishSpot = Npcs.getNearest("Fishing Spot");
            fishSpot.interact("Small Net");
            Time.sleepUntil(()->!me.isAnimating(),30000);
            randomT = Random.nextInt(1, 500);
            if(randomT>490){
                Log.info("faking long sleep");
                Time.sleep(30000,60000);
            }
            else if(randomT<300) {
                Log.info("Short sleep");
                Time.sleep(2000, 4000);
            }
            Time.sleep(600,1200);

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
                Bank.depositInventory();
                Time.sleep(1000,3500);
                Bank.withdraw("Small Fishing Net",1);
                Time.sleep(500,1500);
            } else {
                Bank.open();
                Time.sleepUntil(() -> Bank.isOpen(), 1000);
            }
        }

        return 300;
    }
}
