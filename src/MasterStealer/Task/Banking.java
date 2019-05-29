package MasterStealer.Task;


import MasterStealer.Config;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

public class Banking extends Task {
    private Player me ;
    private Area bankArea = Area.rectangular(3092, 3245, 3093, 3240);
    public boolean validate() {
        me = Players.getLocal();
//        Log.info("Inside banking validate");
        if(!Config.isConfigured()){
            return false;
        }
        try{
//            Log.info("Banking Try"+bankArea.contains(me));
//            Log.info(bankArea.getTiles());
        }catch(Exception e){
            Log.info("Banking Exception "+e);
            return false;
        }

        return bankArea.contains(me)
                && (Inventory.isFull()||(!Inventory.contains(Config.getFood())));//set selection of not having food next time
    }

    @Override
    public int execute() {
        Log.info("Executing Banking Task");
        if (!Bank.isOpen()) { // not going if else incase of lag or whatever and just make sure bank opens
            Bank.open();
            Time.sleepUntil(() -> Bank.isOpen(), 1000);

        }
        if (Bank.isOpen()) {
            Log.info("Depositing");
            Time.sleep(300,800);
            Bank.depositInventory();
            Time.sleep(1000,3500);
            try{
                int numberOfFood = 10;//26-Config.getItemToLoot().size();//giving extra 2 slots
                Bank.withdraw(Config.getFood(),numberOfFood);
                Time.sleep(300,800);
            }catch(Exception e){
                Log.severe(e);
            }
            Bank.close();
            Time.sleep(300,800);

        }
        if(Random.nextInt(1,500)>480){
            Log.info("Long Sleep");
            Time.sleep(100000,200000);
        }
        return Random.nextInt(400,700);

    }
}
