package AIOFight.Constants;

import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.movement.position.Position;

public enum Locations {
//    private static final Area FIGHT_AREA = Area.rectangular();//scene base 3184 3240
    Chicken_Area("Chicken Area", "AIOFight",new Mobs[] {Mobs.Chicken},new Position[]{new Position(3225,3300),new Position (3236,3288)},false,new ComplexMove[]{}),
    Test_Area("Chicken Area2", "AIOFight",new Mobs[] {Mobs.Chicken},new Position[]{new Position(3225,3300),new Position (3236,3288)},false,new ComplexMove[]{}),
    Cow_Area1("Cow Area1", "AIOFight",new Mobs[] {Mobs.Cow,Mobs.CowCalf},new Position[]{new Position(3245,3294),
            new Position(3245,3281),new Position(3254,3280),new Position(3254,3258),new Position(3265,3258),new Position(3265,3294)},false,new ComplexMove[]{}),
    Cow_Area2("Cow Area2","AIOFight",new Mobs[]{Mobs.Cow,Mobs.CowCalf},new Position[]{
            new Position(3199, 3282, 0),
            new Position(3200, 3282, 0),
            new Position(3201, 3283, 0),
            new Position(3206, 3283, 0),
            new Position(3207, 3284, 0),
            new Position(3212, 3284, 0),
            new Position(3213, 3285, 0),
            new Position(3213, 3289, 0),
            new Position(3214, 3290, 0),
            new Position(3214, 3293, 0),
            new Position(3211, 3295, 0),
            new Position(3211, 3302, 0),
            new Position(3210, 3303, 0),
            new Position(3205, 3303, 0),
            new Position(3204, 3302, 0),
            new Position(3200, 3302, 0),
            new Position(3194, 3302, 0),
            new Position(3193, 3300, 0),
            new Position(3193, 3286, 0),
            new Position(3195, 3283, 0),
            new Position(3196, 3282, 0)
    },false,new ComplexMove[]{}),
    Rat_Area("Rat Area","AIOFight",new Mobs[]{Mobs.GiantRat},new Position[]{new Position(3233,3156),new Position(3210,3194)},false,new ComplexMove[]{}),
    Hill_Giant_Cave("Hill Giant Cave","AIOFight",new Mobs[]{Mobs.HillGiant},new Position[] {
            new Position(3115, 9853, 0),
            new Position(3113, 9850, 0),
            new Position(3109, 9850, 0),
            new Position(3109, 9847, 0),
            new Position(3108, 9846, 0),
            new Position(3108, 9843, 0),
            new Position(3106, 9841, 0),
            new Position(3106, 9840, 0),
            new Position(3105, 9839, 0),
            new Position(3100, 9839, 0),
            new Position(3100, 9838, 0),
            new Position(3097, 9838, 0),
            new Position(3095, 9835, 0),
            new Position(3095, 9829, 0),
            new Position(3098, 9827, 0),
            new Position(3099, 9827, 0),
            new Position(3102, 9824, 0),
            new Position(3105, 9824, 0),
            new Position(3106, 9823, 0),
            new Position(3110, 9823, 0),
            new Position(3112, 9825, 0),
            new Position(3112, 9826, 0),
            new Position(3114, 9828, 0),
            new Position(3118, 9828, 0),
            new Position(3122, 9832, 0),
            new Position(3122, 9833, 0),
            new Position(3125, 9836, 0),
            new Position(3125, 9839, 0),
            new Position(3124, 9840, 0),
            new Position(3124, 9842, 0),
            new Position(3126, 9844, 0),
            new Position(3126, 9847, 0),
            new Position(3125, 9848, 0),
            new Position(3125, 9850, 0),
            new Position(3124, 9851, 0),
            new Position(3122, 9851, 0),
            new Position(3120, 9854, 0)
    },true,new ComplexMove[]{ComplexMove.moveFromGiant,ComplexMove.moveToGiant});
    private final String name;
    private final String type;
    private final Mobs[]  Monster;
    private final Position[] posList;
    private final Area area;
    private final boolean isComplex;
    private final ComplexMove[] complexMoves;


    Locations(final String name, final String type, final Mobs[] monster, final Position[] posList, final boolean isComplex,final ComplexMove[] complexMoves) {
        this.name = name;
        this.type = type;
        this.Monster = monster;
        this.posList = posList;
        if(posList.length>2){
            this.area = Area.polygonal(posList);
        }
        else
            this.area = Area.rectangular(posList[0],posList[1]);
        this.isComplex = isComplex;
        this.complexMoves = complexMoves;

    }

//    @Override
//    public String toString() {
//
//        return this.name;
//    }


    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Mobs[] getMonster() {
        return Monster;
    }

    public Position[] getPosList() {
        return posList;
    }

    public Area getArea() {
        return area;
    }

    public boolean isComplex() {
        return isComplex;
    }

    public ComplexMove[] getComplexMoves() {
        return complexMoves;
    }
}
