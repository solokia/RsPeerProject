package AIOFight.Constants;

public enum Mobs {

    Chicken("Chicken",new String[]{"2692","2963"},new String[]{"Feather","Bones"}),
    Cow("Cow",new String[]{"2805","2806","2808"},new String[]{"Steel knife","Bones"}),
    CowCalf("Cow calf",new String[]{"2807","2809"},new String[]{"Bones"}),
    GiantRat("Giant rat",new String[]{"2864","2836"},new String[]{"Bones"}),
    HillGiant("Hill Giant",new String[]{"2098","2099","2010","2011"},
    new String[]{"Coins","Big bones","Law rune","Nature rune","Death rune","Cosmic rune","Chaos rune","Giant key"});

    private final String name;
    private final String[] ids;
    private final String[] drops;

    Mobs(final String name, final String[] ids, final String[] drops){
        this.name = name;
        this.ids = ids;
        this.drops = drops;
    }

    public String getName(){ return name; }
    public String[] getIds(){ return ids; }
    public String[] getDrops(){ return drops; }

}
