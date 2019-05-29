package AutoCleanHerb;

import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.script.Script;
import org.rspeer.script.ScriptMeta;
import org.rspeer.ui.Log;

@ScriptMeta(developer = "Solly", desc = "AutoCleanHerb", name = "AutoClean")
public class AutoClean extends Script {
    private String gHerbname="Grimy harralander";
    private String cHerbname="Harralander";
    @Override
    public int loop() {
        if(!Inventory.contains(gHerbname)){
            return bank();
        }else
            if (Inventory.contains(gHerbname))
            cleanAll();

        return 300;
    }
    public void cleanAll(){
        Item[] items = Inventory.getItems();
        for(Item i:items){
            i.interact("Clean");
            Time.sleep(270,320);
        }
        Time.sleep(200);

    }

    public int bank(){
//        clicked=false;
        if (!Bank.isOpen()){
            Bank.open();
            Time.sleepUntil(() -> Bank.isOpen(), 1000);
        }

        if (Bank.isOpen()) {
            Time.sleep(200);
            Log.info("Depositing");
            if (!Inventory.isEmpty())
                Bank.depositInventory();
            Time.sleep(700,1200);
            if(!Bank.contains(gHerbname)){

                return -1;
            }
            Bank.withdrawAll(gHerbname);
            Time.sleep(500,800);
            Bank.close();
            Time.sleep(200,500);
        }

        return 300;
    }
}
