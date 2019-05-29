package AutoMine.Task;

import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

import java.util.function.Predicate;

import static org.rspeer.runetek.api.component.WorldHopper.randomHopInF2p;

public class ChangeWorld extends Task {
    private Player me;
    private static Area miningArea;
    private Player[] count;
    private Predicate<Player> inArea = inArea->{
        return miningArea.contains(inArea);
    };

    @Override
    public boolean validate() {
//        Log.info("in change world");
        int missCount=0;
        try {
            me = Players.getLocal();
            count = Players.getLoaded(inArea);
            missCount = Mining.getMissCount();
//            Log.info("Number of players in area : "+count.length);
        }catch(Exception e){
            return false;
        }
        return count.length>3
                && miningArea.contains(me)
                && missCount>5
                && !Inventory.isFull();
    }

    @Override
    public int execute() {
        Log.info("Changing world");
        randomHopInF2p();
        Mining.setMissCount(0);
        return 3000;
    }

    public static Area getMiningArea() {
        return miningArea;
    }

    public static void setMiningArea(Area miningArea) {
        ChangeWorld.miningArea = miningArea;
    }
}
