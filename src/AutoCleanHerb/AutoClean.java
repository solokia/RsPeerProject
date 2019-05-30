package AutoCleanHerb;

import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.script.Script;
import org.rspeer.script.ScriptMeta;
import org.rspeer.ui.Log;

import java.util.function.Predicate;

@ScriptMeta(developer = "Solly", desc = "AutoCleanHerb", name = "AutoClean")
public class AutoClean extends Script {
    private String gHerbname="Grimy harralander";
    private String cHerbname="Harralander";
    private Predicate<Item> herbPred = item ->{
        if (item.getName().contains("Grimy"))
            return item.getName().contains("Grimy");
        else
        return false;
    };
    @Override
    public int loop() {
        if(!Inventory.contains(herbPred)){
            return bank();
        }else
            if (Inventory.contains(herbPred))
        return cleanAll();

        return 200;
}
    public int cleanAll(){
        Item[] items = Inventory.getItems();
        int[] cleanPattern1 = {0,1,2,3,7,6,5,4,8,9,10,11,15,14,13,12,16,17,18,19,23,22,21,20,24,25,26,27};
//        for(Item i:items){
//            i.interact("Clean");
//            Time.sleep(250,320);
//        }
//        Time.sleep(200);

        for(int i : cleanPattern1){
            if (i<items.length){
                if(Random.nextInt(1,500)>2)// 1 in 500 chance to miss
                items[i].interact("Clean");
                Time.sleep(210,270);
            }
        }
        Time.sleep(300,500);
        return bank();
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
            if(!Bank.contains(herbPred)){

                return -1;
            }
            Bank.withdrawAll(herbPred);
            Time.sleep(400,700);
            Bank.close();
            Time.sleep(200,500);
        }

        return 300;
    }
}
