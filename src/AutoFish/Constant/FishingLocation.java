package AutoFish.Constant;

import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.movement.position.Position;

public enum FishingLocation{

    Draynor("Draynor Fishing","Fishing",new String[]{"Small Net","Bait"},1,new Position[]{new Position(3086,3232),new Position(3089,3227)});

    private final String name;
    private final String type;
    private final String[]  actions;
    private final int minLevel;
    private final Position[] posList;
    private final Area area;

    FishingLocation(String name, String type, String[] actions, int minLevel, Position[] posList) {
        this.name = name;
        this.type = type;
        this.actions = actions;
        this.minLevel = minLevel;
        this.posList = posList;
        if(posList.length>2){
            this.area = Area.polygonal(posList);
        }
        else
            this.area = Area.rectangular(posList[0],posList[1]);

    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String[] getActions() {
        return actions;
    }

    public int getMinLevel() {
        return minLevel;
    }

    public Position[] getPosList() {
        return posList;
    }

    public Area getArea() {
        return area;
    }
}