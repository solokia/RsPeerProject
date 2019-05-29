package AIOFight.Models;

import AIOFight.Constants.Locations;
import AIOFight.Constants.Mobs;
import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.ui.Log;

import java.util.ArrayList;

public class Location {
    private String name;
    private String type;
    private ArrayList <Monster> Monsters;
    private Position[] posList;
    private Area area;
    private Locations location;

    public Location(){}

    public Location(Locations loc){
        this.name = loc.getName();
        this.type = loc.getType();
        this.Monsters = new ArrayList<>();
        for(Mobs next : loc.getMonster()){
            Monsters.add(new Monster(next));
            Log.info("adding Monster");
        }
        this.posList = loc.getPosList();
        this.area = loc.getArea();

    }

    public Location(String name, String type, ArrayList<Monster> monster, Position[] posList, Area area) {
        this.name = name;
        this.type = type;
        this.Monsters = new ArrayList<>();
        for(Monster next : monster){
            Monsters.add(next);
        }
        this.posList = posList;
        this.area = area;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList <Monster> getMonsters() {
        return Monsters;
    }

    public void setMonster(ArrayList<Monster> monster) {
        for(Monster next : monster){
            Monsters.add(next);
        }
    }

    public Position[] getPosList() {
        return posList;
    }

    public void setPosList(Position[] posList) {
        this.posList = posList;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }
}
