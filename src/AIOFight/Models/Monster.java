package AIOFight.Models;

import AIOFight.Constants.Mobs;

public class Monster {

    private String name;
    private String[] ids;
    private String[] drops;

    public Monster(){}

    public Monster(Mobs m) {
        this.name = m.getName();
        this.ids = m.getIds();
        this.drops = m.getDrops();
    }

    public Monster(String name, String[] ids, String[] drops) {
        this.name = name;
        this.ids = ids;
        this.drops = drops;
    }

    public String getName() {
        return name;
    }

    public String[] getIds() {
        return ids;
    }

    public String[] getDrops() {
        return drops;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIds(String[] ids) {
        this.ids = ids;
    }

    public void setDrops(String[] drops) {
        this.drops = drops;
    }
}
