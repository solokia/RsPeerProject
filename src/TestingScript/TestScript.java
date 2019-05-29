package TestingScript;

import org.rspeer.runetek.adapter.component.InterfaceComponent;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.Interfaces;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.Script;
import org.rspeer.script.ScriptMeta;
import org.rspeer.ui.Log;

@ScriptMeta(developer = "Solly", desc = "TestScript functions", name = "TestScript")
public class TestScript extends Script {
    private final int PARENT_INDEX = 160;
    private final int CHILD_INDEX = 5;

    private String arrowShaft = "arrow Shaft";
    @Override
    public int loop() {
        Player me = Players.getLocal();
//        Item x = Inventory.getFirst(arrowShaft);
//        Item y = Inventory.getFirst("Feather");
//        x.interact("Use");
//        Time.sleep(200,300);
//        y.interact("Use");
//        Time.sleep(1000,1300);
//        try {
//            InterfaceComponent wornEquipment = Interfaces.getComponent(PARENT_INDEX, CHILD_INDEX);
//            Log.info("wornEquipment get actions "+wornEquipment.interact("Make sets:"));
//        }catch (Exception e){
//            Log.severe(e);
//        }
//
//        Time.sleep(8000,10000);
//        return 500;
        InterfaceComponent wornEquipment = Interfaces.getComponent(PARENT_INDEX, CHILD_INDEX);
        int hp = Integer.parseInt(wornEquipment.getText());
        Log.info(hp);
        return 500;
    }
}
