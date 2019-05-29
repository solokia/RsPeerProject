package AutoMine.Constants;

import org.rspeer.runetek.api.commons.BankLocation;
import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.movement.position.Position;

public enum Locations {
    Lumbridge("Lumbridge", "AutoMine",1,new Rocks[] {Rocks.Copper,Rocks.Tin},
            new Position[]{new Position(3222,3149),new Position(3231,3144)},BankLocation.LUMBRIDGE_CASTLE),
    AlKharid("AlKharid","AutoMine",20,new Rocks[]{},new Position[] {new Position(3306, 3280, 0),
                    new Position(3306, 3281, 0),
                    new Position(3302, 3286, 0),
                    new Position(3301, 3287, 0),
                    new Position(3303, 3298, 0),
                    new Position(3306, 3302, 0),
                    new Position(3306, 3304, 0),
                    new Position(3304, 3306, 0),
                    new Position(3303, 3307, 0),
                    new Position(3304, 3309, 0),
                    new Position(3304, 3313, 0),
                    new Position(3303, 3316, 0),
                    new Position(3302, 3318, 0),
                    new Position(3299, 3318, 0),
                    new Position(3296, 3314, 0),
                    new Position(3295, 3311, 0),
                    new Position(3294, 3309, 0),
                    new Position(3296, 3308, 0),
                    new Position(3295, 3303, 0),
                    new Position(3292, 3300, 0),
                    new Position(3296, 3292, 0),
                    new Position(3296, 3289, 0),
                    new Position(3294, 3288, 0),
                    new Position(3293, 3286, 0)
            },BankLocation.AL_KHARID),
    VarrockEast("Varrock East", "AutoMine",15,new Rocks[]{Rocks.Iron}, new Position[] {
                    new Position(3284, 3371, 0),
                    new Position(3288, 3367, 0)
            },BankLocation.VARROCK_EAST);

    private final String name;
    private final String type;
    private final int level;
    private final Rocks[] rockList;
    private final Position[] posList;
    private final Area area;
    private final BankLocation bankLocation;
    Locations(String name, String type,int level, Rocks[] rockList, Position[] posList,BankLocation bankLocation) {
        this.name = name;
        this.type = type;
        this.level = level;
        this.rockList = rockList;
        this.posList = posList;

        if(posList.length>2){
            this.area = Area.polygonal(posList);
        }
        else
            this.area = Area.rectangular(posList[0],posList[1]);
        this.bankLocation = bankLocation;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getLevel() {
        return level;
    }

    public Rocks[] getRockList() {
        return rockList;
    }

    public Position[] getPosList() {
        return posList;
    }

    public Area getArea() {
        return area;
    }

    public BankLocation getBankLocation() {
        return bankLocation;
    }
}
