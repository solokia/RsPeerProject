package AutoTan;


import org.rspeer.runetek.adapter.component.InterfaceComponent;
import org.rspeer.runetek.adapter.scene.Npc;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.commons.BankLocation;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.Dialog;
import org.rspeer.runetek.api.component.Interfaces;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.Script;
import org.rspeer.script.ScriptMeta;
import org.rspeer.ui.Log;

@ScriptMeta(developer = "Solly", desc = "AutoTanning", name = "AutoTan")
public class AutoTan extends Script {
    private Player me;
    private Area bankArea =Area.surrounding(BankLocation.getNearest().getPosition(),3);
    private Area tanArea =Area.surrounding(new Position(3276,3191),1) ;
    private String itemName="Hard Leather",materialName="Cowhide";
    private boolean clicked = false;
    private final int PARENT_INDEX = 324;
    private final int CHILD_INDEX = 125;
    private final String ACTION = "Tan";
    private String npcName = "Ellis";


    @Override
    public void onStart(){
        me = Players.getLocal();
    }
    @Override
    public int loop() {
        try{
            if(Dialog.isOpen()){
                clicked=false;
            }
            if(tanArea.contains(me)&&Inventory.contains(materialName))
            {
                tan();
            }
            if (bankArea.contains(me)&&!Inventory.contains(materialName)){
                return bank();
            }


            if(!Movement.isRunEnabled() &&Movement.getRunEnergy()> Random.nextInt(30,50)){
                Movement.toggleRun(true);
            }
            if(Inventory.contains(materialName)&&!tanArea.contains(me)){
                walkToSmelt();
            }
            if(!Inventory.contains(materialName)&&!bankArea.contains(me)){
                walkToBank();
            }


        }catch (Exception e){
            Log.severe(e);
        }


        return 500;
    }

    public boolean tan(){
        Npc tanner = Npcs.getNearest(npcName);
        clicked=true;
        if(tanner!=null)
            try{
                tanner.interact("Trade");
                Time.sleep(1000,2000);
                selectTan();
            }catch (Exception e){
                Log.severe(e);
            }
        Time.sleep(1000,3000);
        return false;
    }
    public int bank(){
        clicked=false;
        if (Bank.isOpen()) {
            Time.sleep(300);
            Log.info("Depositing");
            Bank.depositAll(itemName);
            Time.sleep(1000,3500);
            if(!Bank.contains(materialName)){

                return -1;
            }
            Bank.withdrawAll(materialName);
            Time.sleep(500,1500);
            Bank.close();
            Time.sleep(300,700);
        } else {
            Bank.open();
            Time.sleepUntil(() -> Bank.isOpen(), 1000);
        }
        return 300;
    }
    public void walkToSmelt(){
        Position tanPosition = tanArea.getTiles().get(Random.nextInt(tanArea.getTiles().size()));
        Movement.walkTo(tanPosition);
        Time.sleep(300,1500);
    }
    public void walkToBank(){
        Position bankPosition = bankArea.getTiles().get(Random.nextInt(bankArea.getTiles().size()));
        Movement.walkTo(bankPosition);
        Time.sleep(300,2000);
    }
    public boolean selectTan(){


// The action we copied down earlier
        InterfaceComponent wornEquipment = Interfaces.getComponent(PARENT_INDEX, CHILD_INDEX);

// ALWAYS null check interface components
        if(wornEquipment != null ){// we use that component straight away
            //sleep until the Worn Equipment interface is selected, remember the material id tidbit I mentioned before? Time out after 2 seconds
//            Time.sleepUntil( () -> wornEquipment.getMaterialId() != -1, 2000)
            wornEquipment.interact("");
            Time.sleepUntil(()->!Inventory.contains("Cowhide"),300,3000);
        }
        Time.sleep(300,800);
        return false;
    }
}
