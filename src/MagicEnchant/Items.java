package MagicEnchant;

public enum Items {
    RINGOFRECOIL("Ring of recoil","Sapphire ring"),
    GAMESNECKLACE("Games necklace(8)","Sapphire Necklace"),
    RINGOFDUELING("Ring of dueling(8)","Emerald ring"),
    BINDINGNECKLACE("Binding necklace","Emerald necklace"),
    CASTLEWARSBRACELET("Castle wars bracelet(3)","Emerald bracelet"),
    FLAMTAERBRACELET("Flamtaer bracelet","Jade bracelet"),
    AMULETOFCHEMISTRY("Amulet of chemistry","Jade amulet"),
    ABYSSALBRACELET("Abyssal bracelet(5)","Diamond bracelet"),
    RINGOFWEALTH("Ring of wealth (5)","Dragonstone ring"),
    SKILLSNECKLACE("Skills necklace(4)","Dragon necklace"),
    COMBATBRACELET("Combat bracelet(4)","Dragonstone bracelet"),
    AMULETOFGLORY("Amulet of glory(4)","Dragonstone amulet");
    private final String name;
    private final String material;

    Items(String name, String material) {
        this.name = name;
        this.material = material;
    }

    public String toString(){
        return name;
    }

    public String getName() {
        return name;
    }

    public String getMaterial() {
        return material;
    }
}
