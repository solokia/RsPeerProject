package AutoSmelt;


import org.rspeer.runetek.adapter.component.InterfaceComponent;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.adapter.scene.SceneObject;
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
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.api.scene.SceneObjects;
import org.rspeer.script.Script;
import org.rspeer.script.ScriptMeta;
import org.rspeer.ui.Log;

@ScriptMeta(developer = "Solly", desc = "AutoSmelt", name = "AutoSmelt")
public class AutoSmelt extends Script {
    private Player me;
    private Area bankArea =Area.surrounding(new Position(3096,3494),3);//BankLocation.getNearest().getPosition()
    private Area smeltArea =Area.surrounding(new Position(3109,3499),2) ;//new Position(3274,3186)
    private String itemName="Gold bracelet",materialName="Gold bar";
    private boolean clicked = false;
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
            if(smeltArea.contains(me)&&Inventory.contains(materialName)&&!me.isAnimating()&&!clicked)
            {
                smelt();
            }
            if (bankArea.contains(me)&&!Inventory.contains(materialName)){
                return bank();
            }


            if(!Movement.isRunEnabled() &&Movement.getRunEnergy()> Random.nextInt(30,50)){
                Movement.toggleRun(true);
            }
            if(Inventory.contains(materialName)&&!smeltArea.contains(me)){
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

    public boolean smelt(){
        SceneObject furnace = SceneObjects.getNearest("Furnace");
        clicked=true;
        if(furnace!=null)
            try{
                furnace.interact("Smelt");
                Time.sleep(1000,2000);
                selectSmelt();
            }catch (Exception e){
                Log.severe(e);
            }
        Time.sleep(1000,3000);
        return false;
    }
    public int bank(){
        clicked=false;
        if (Bank.isOpen()) {
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
        Movement.walkTo(smeltArea.getCenter());
        Time.sleep(300,2000);
    }
    public void walkToBank(){
        Movement.walkTo(new Position(3096,3494));//BankLocation.getNearest().getPosition()
        Time.sleep(300,2000);
    }
    public boolean selectSmelt(){
        final int PARENT_INDEX = 446;
        final int CHILD_INDEX = 46;

// The action we copied down earlier
        final String ACTION = "Make";

        InterfaceComponent wornEquipment = Interfaces.getComponent(PARENT_INDEX, CHILD_INDEX);

// ALWAYS null check interface components
        if(wornEquipment != null && wornEquipment.interact(ACTION)){
            //sleep until the Worn Equipment interface is selected, remember the material id tidbit I mentioned before? Time out after 2 seconds
//            Time.sleepUntil( () -> wornEquipment.getMaterialId() != -1, 2000)
        }
        Time.sleep(300,800);
        return false;
    }
}
