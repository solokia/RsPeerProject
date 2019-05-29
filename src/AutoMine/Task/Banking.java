package AutoMine.Task;

import AIOFight.Task.Walk;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

import java.util.function.Predicate;

public class Banking extends Task {
    private Player me;
    private static Area bankArea;
    private static Predicate<Item> item = item->{
       return item.getName().contains("pickaxe");

    };
    @Override
    public boolean validate() {
        me = Players.getLocal();
        Log.info("Inside banking validate");
        try{
            Log.info("Banking Try"+bankArea.contains(me));
//            Log.info(bankArea.getTiles());
        }catch(Exception e){
            Log.info("Banking Exception "+e);
            return false;
        }

        return bankArea.contains(me)
                && Inventory.isFull();
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
            try {
                Bank.depositAllExcept(item);
            }catch(Exception e) {
                Bank.depositInventory();
            }
            Time.sleep(1000,3500);
            if(Random.nextInt(1,30)>28) {
                Bank.close();
                Time.sleep(300);
            }

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
}
