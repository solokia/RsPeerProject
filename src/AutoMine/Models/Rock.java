package AutoMine.Models;


import AutoMine.Constants.Rocks;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.ui.Log;

import java.util.HashSet;

public class Rock {
    private String name;
    private int level;
    private static HashSet<Position> orePositions=new HashSet<>();

    public Rock(){}

    public Rock(Rocks r) {
        this.name = r.getName();
        this.level = r.getLevel();
        Log.info("adding a rock positions "+ r.values());
        try {
            this.orePositions.addAll(r.getOrePositions());
        }catch (Exception e){
            Log.info("Exception in building rock");
        }
        Log.info("finish rock init");

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public static HashSet<Position> getOrePositions() {
        return orePositions;
    }

    public static void setOrePositions(HashSet<Position> orePositions) {
        Rock.orePositions = orePositions;
    }
}
