package AutoMine.Constants;

import org.rspeer.runetek.api.movement.position.Position;

import java.util.Arrays;
import java.util.HashSet;

public enum Rocks {
    Copper("Copper",1,new Position[]{new Position(3228,3144),new Position(3229,3145),
            new Position(3230,3145),new Position(3230,3147),new Position(3229,3148)}),
    Tin("Tin",1,new Position[]{new Position(3225,3148),new Position(3223,3148),
            new Position(3222,3147),new Position(3223,3146),new Position(3224,3146)}),
    Iron("Iron",15,new Position[]{new Position(3285,3368),new Position(3285,3369),
            new Position(3286,3369),new Position(3288,3370)});
    private final String name;
    private final int level;
    private final HashSet<Position> orePositions;

    Rocks(String name, int level, Position[] orePositions) {
        this.name = name;
        this.level = level;
        this.orePositions = new HashSet<>(Arrays.asList(orePositions));
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public HashSet<Position> getOrePositions() {
        return orePositions;
    }
}
