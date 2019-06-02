package AutoAgility.Task;

import AutoAgility.model.*;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

import java.util.function.Predicate;

public class Drink extends Task {
    private Player me;

    @Override
    public boolean validate() {

        me = Players.getLocal();

        return Config.isConfigured()
                &&Movement.getRunEnergy()< Random.nextInt(Config.getLowerEnergy(),Config.getUpperEnergy())
                && !me.isAnimating()
                && !me.isMoving()
                && Inventory.contains(Config.getItemPredicate());
    }

    @Override
    public int execute() {
        Log.info("In Drink");
        Item stam = Inventory.getFirst(Config.getItemPredicate());
        stam.interact("Drink");
        return 300;
    }
}
