package AutoMine;

import AutoMine.Constants.Locations;
import AutoMine.Constants.Rocks;
import AutoMine.Models.Location;
import AutoMine.Models.Rock;
import AutoMine.Task.*;
import AutoMine.view.AutoMineGUI;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.ScriptMeta;
import org.rspeer.script.task.Task;
import org.rspeer.script.task.TaskScript;
import org.rspeer.ui.Log;

import java.util.ArrayList;
import java.util.HashSet;

@ScriptMeta(developer = "Solly", desc = "Auto Mining", name = "AutoMine")
public class AutoMine extends TaskScript {
    private static Locations locations;
    private static Rocks rocks;
    private static Location location;
    public static Rock rock;
    private AutoMineGUI gui;
    private Area FIGHT_AREA;//testing
    private Position FIGHT_POSITION;
    private static String mobName;
    private static boolean configured=false;
    private static boolean changed=false;
    private static Task[] TASKS = { new Banking(),new ChangeWorld(), new Mining(), new Walk() };
    private static ArrayList<Rock> rocksToMine;
    private static HashSet<Position> rocksPositions;
    @Override
    public void onStart() {
        Log.info("GUI Configuration");
        gui = new AutoMineGUI();
        gui.setVisible(true);
        Player me = Players.getLocal();
        submit(TASKS);
    }
    public static void tempController(){
        Log.info("setting up");
        location = new Location(locations);
        Mining.setMiningArea(location.getArea());
        ChangeWorld.setMiningArea(location.getArea());
        Walk.setMiningArea(location.getArea());
        rocksToMine=new ArrayList<>(location.getRocksList());// resetting it to release any previous choice
        rocksPositions=new HashSet<>();
        for (Rock next: rocksToMine) {
            Log.info(next.getOrePositions());
            rocksPositions.addAll(next.getOrePositions());//since its reference only after button is press should be fine
        }
        Mining.setRockList(rocksPositions);
        Walk.setBankPosition(locations.getBankLocation().getPosition());
        Walk.setBankArea(Area.surrounding(locations.getBankLocation().getPosition(),5,locations.getBankLocation().getPosition().getFloorLevel()));
        Banking.setBankArea(Area.surrounding(locations.getBankLocation().getPosition(),5,locations.getBankLocation().getPosition().getFloorLevel()));

    }

    public static Locations getLocations() {
        return locations;
    }

    public static void setLocations(Locations locations) {
        AutoMine.locations = locations;
    }

    public static Rocks getRocks() {
        return rocks;
    }

    public static void setRocks(Rocks rocks) {
        AutoMine.rocks = rocks;
    }

    public static Location getLocation() {
        return location;
    }

    public static void setLocation(Location location) {
        AutoMine.location = location;
    }

    public static Rock getRock() {
        return rock;
    }

    public static void setRock(Rock rock) {
        AutoMine.rock = rock;
    }

    public static boolean isConfigured() {
        return configured;
    }

    public static void setConfigured(boolean configured) {
        AutoMine.configured = configured;
    }

    public static boolean isChanged() {
        return changed;
    }

    public static void setChanged(boolean changed) {
        AutoMine.changed = changed;
    }
}
