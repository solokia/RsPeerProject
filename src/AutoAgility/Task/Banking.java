package AutoAgility.Task;

import AutoAgility.model.AgiPaint;
import AutoAgility.model.Config;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

public class Banking extends Task {
    private Player me;
    @Override
    public boolean validate() {

        me= Players.getLocal();
        return Config.isConfigured()&&(!Inventory.contains(Config.getFood().getName())||!Inventory.contains(Config.getItemPredicate()))&&Config.getBankArea().contains(me);
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
//            Bank.depositInventory();
            Time.sleep(1000,3500);
            if(Bank.getCount(Config.getFood().getName())==0||Bank.getCount(Config.getItemPredicate())==0)
                return -1;
            try{
                if(!Inventory.contains(Config.getFood().getName())) {
                    Bank.withdraw(Config.getFood().getName(), Config.getNumberOfFood());
                    Time.sleep(300, 800);
                }
                if(!Inventory.contains(Config.getItemPredicate())) {
                    Bank.withdraw(Config.getItemPredicate(), Config.getNumberOfPot());
                    Time.sleep(300, 800);
                }
            }catch(Exception e){
                Log.severe(e);
                return -1;
            }
            Bank.close();
            Time.sleep(300,800);

        }

        return 300;
    }
}
