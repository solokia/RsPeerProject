package AIOFight.Constants;

public enum Food {
    Shrimp("Shrimp",3),
    Trout("Trout",7),
    Salmon("Salmon",9),
    Tuna("Tuna",10),
    Lobster("Lobster",12),
    Swordfish("Swordfish",14);
    private final String name;
    private final int heal;

    Food(String name, int heal) {
        this.name = name;
        this.heal = heal;
    }

    public String getName() {
        return name;
    }

    public int getHeal() {
        return heal;
    }
}
