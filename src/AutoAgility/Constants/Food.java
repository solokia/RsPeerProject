package AutoAgility.Constants;

public enum Food {
    Shrimp("Shrimp",3,"Eat"),
    Trout("Trout",7,"Eat"),
    Salmon("Salmon",9,"Eat"),
    Tuna("Tuna",10,"Eat"),
    Lobster("Lobster",12,"Eat"),
    Swordfish("Swordfish",14,"Eat");
    private final String name;
    private final int heal;
    private final String action;

    Food(String name, int heal, String action) {
        this.name = name;
        this.heal = heal;
        this.action = action;
    }

    public String getName() {
        return name;
    }

    public int getHeal() {
        return heal;
    }

    public String getAction() {
        return action;
    }
}
