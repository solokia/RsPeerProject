package MagicEnchant;

public enum Spells {

    LVL1ENCHANT("Lvl-1 Enchant",9),
    LVL2ENCHANT("Lvl-2 Enchant",20),
    LVL3ENCHANT("Lvl-3 Enchant",32),
    LVL4ENCHANT("Lvl-4 Enchant",40),
    LVL5ENCHANT("Lvl-5 Enchant",55),
    LVL6ENCHANT("Lvl-6 Enchant",69),
    LVL7ENCHANT("Lvl-7 Enchant",72),
    SUPERHEAT("Superheat Item",29),
    HIGHALCH("High Level Alchemy",38),
    LOWALCH("Low Level Alchemy",17);
    private final String name;
    private final int parent=218;
    private final int child;

    Spells(String name,int child) {
        this.name = name;
        this.child = child;
    }
    public String toString(){
        return name;
    }

    public String getName() {
        return name;
    }

    public int getParent() {
        return parent;
    }

    public int getChild() {
        return child;
    }

}
