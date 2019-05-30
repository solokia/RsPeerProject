package AutoHerbMix;

import org.rspeer.runetek.adapter.component.InterfaceComponent;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.Interfaces;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.script.Script;
import org.rspeer.script.ScriptMeta;
import org.rspeer.ui.Log;

import java.util.Arrays;
import java.util.HashSet;
import java.util.function.Predicate;

@ScriptMeta(developer = "Solly", desc = "Auto mix unfinished potion", name = "AutoMix")
public class AutoHerbMix extends Script {
    private int makeParent = 270;
    private int makeChild = 14;
    private String vialName="Vial of water";
    private String[] herbName={"Guam leaf","Marrentill","Tarromin","Harralander","Ranarr",
            "Toadflax","Irit","Avantoe","Kwuarm","Snapdragon","Cadantine","Lantadyme","Dwarf weed","Torstol"};
    private HashSet<String> herbSet = new HashSet<>(Arrays.asList(herbName));
    private Predicate<Item> herbPred = item ->{
        if (herbSet.contains(item.getName())&&item.getStackSize()>0)
            return herbSet.contains(item.getName());
        else
        return false;
    };
    @Override
    public int loop() {
        if(!Inventory.contains(vialName)||!Inventory.contains(herbPred)){
            return bank();
        }else
            if (Inventory.contains(vialName)&&Inventory.contains(herbPred))
        return mixAll();

        return 300;
}
    public int mixAll(){
        Log.info("entering Mixall");
        Item herb = Inventory.getFirst(herbPred);
        Item vial = Inventory.getFirst(vialName);
        Time.sleep(300,500);
        switch (Random.nextInt(1,2)){
            case 1:
                herb.interact("Use");
                Time.sleep(300,500);
                vial.interact("Use");
                break;
            case 2:
                vial.interact("Use");
                Time.sleep(300,500);
                herb.interact("Use");
                break;

        }
        Log.info("getting component");
        Time.sleep(300,500);
        InterfaceComponent make = Interfaces.getComponent(makeParent, makeChild);
        for(int i =0; i<5;i++) {
            make = Interfaces.getComponent(makeParent, makeChild);
            if(make!=null)
                break;
            else
                Time.sleep(300);
        }
        Time.sleep(300,500);
        if(make!=null&&make.isVisible()) {
            Log.info("Component found");
            make.interact("Make");
            Time.sleepUntil(()->(!Inventory.contains(vialName)||!Inventory.contains(herbPred)),300,10000);

        }

        Log.info("Exiting mixAll");
        return 300;
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
            if(!Bank.contains(herbPred)||!Bank.contains(vialName)){

                return -1;
            }


            Item herb = Bank.getFirst(herbPred);
            Log.info("Taking Herb "+ herb.getName());
            Bank.withdraw(herb.getName(),14);
            Time.sleep(700,900);
            Log.info("Depositing taking vial");
            Bank.withdraw(vialName,14);
            Time.sleepUntil(()->Inventory.contains(vialName),300,3000);
            Bank.close();
            Time.sleep(300,500);
        }

        return 300;
    }
}
