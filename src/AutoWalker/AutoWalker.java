package AutoWalker;

import AutoWalker.View.AutoWalkerGUI;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.commons.BankLocation;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.Script;
import org.rspeer.script.ScriptMeta;

@ScriptMeta(developer = "Solly", desc = "Auto Walk to Banks", name = "AutoWalker")
public class AutoWalker extends Script {
    private static BankLocation bankToWalkTo;
    private static boolean configured=false;
    private static boolean internalConf = false;
    private static Area bankArea;
    private static AutoWalkerGUI gui;

    @Override
    public void onStart(){
        gui = new AutoWalkerGUI();
        gui.setVisible(true);
    }

    @Override
    public int loop() {
        if(configured){
            Player me = Players.getLocal();
            if(!internalConf)
                bankArea = Area.surrounding(bankToWalkTo.getPosition(),3);
            if(!bankArea.contains(me)){
                Movement.walkToRandomized(bankArea.getCenter());
                Time.sleep(1000, 2000);
            }
            if(bankArea.contains(me)){
                return -1;
            }
        }
        return 300;
    }

    public static void setBankToWalkTo(BankLocation bankToWalkTo) {
        AutoWalker.bankToWalkTo = bankToWalkTo;
    }

    public static void setConfigured(boolean configured) {
        AutoWalker.configured = configured;
    }
}
