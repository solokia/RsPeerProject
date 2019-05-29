package AIOFight.Task;

import AIOFight.Models.Config;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

public class Banking extends Task {
    private Player me;
    private static Area bankArea;

    @Override
    public boolean validate() {
        me = Players.getLocal();
        Log.info("Inside banking validate");
        if(!Config.isConfigured()){
            return false;
        }
        try{
            Log.info("Banking Try"+bankArea.contains(me));
//            Log.info(bankArea.getTiles());
        }catch(Exception e){
            Log.info("Banking Exception "+e);
            return false;
        }

        return bankArea.contains(me)
                && (Inventory.isFull()||(!Inventory.contains(Config.getFood().getName())));//set selection of not having food next time
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
                int numberOfFood = 2;//26-Config.getItemToLoot().size();//giving extra 2 slots
                Bank.withdraw(Config.getFood().getName(),numberOfFood);
                Time.sleep(300,800);
            }catch(Exception e){
                Log.severe(e);
            }
            Bank.close();
            Time.sleep(300,800);

        }
        Walk.setCheckpoint(false);
        Walk.setSetCheck(false);
        return 300;
    }

    public static Area getBankArea() {
        return bankArea;
    }

    public static void setBankArea(Area bankArea) {
        Banking.bankArea = bankArea;
    }
    public static boolean take(String item){
        if (!Bank.isOpen()) { // not going if else incase of lag or whatever and just make sure bank opens
            Bank.open();
            Time.sleepUntil(() -> Bank.isOpen(),200 ,2000);

        }
        if (Bank.isOpen()) {
            Log.info("Taking");
            Time.sleep(300,800);
            try {
                Bank.withdraw(item, 1);
            }catch(Exception e){
                return false;
            }
            Time.sleep(1000,3500);
            Bank.close();
            Time.sleep(300,800);
            return true;
        }
        return false;
    }
}
