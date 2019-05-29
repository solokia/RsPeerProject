package AutoMine.Models;

import AutoMine.Constants.Locations;

import AutoMine.Constants.Rocks;
import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.ui.Log;

import java.util.ArrayList;

public class Location {

    private String name;
    private String type;
//    private ArrayList <Monster> Monsters;
    private int level;
    private ArrayList<Rock> rocksList=new ArrayList<>();
    private Position[] posList;
    private Area area;


    public Location(){}

    public Location(Locations loc){
        this.name = loc.getName();
        this.type = loc.getType();
        this.level = loc.getLevel();
//        this.rocksList = loc.getRockList();
        Log.info("adding rocks soon");
        for(Rocks next : loc.getRockList()){
            Log.info("add rock loop");
            Log.info(next.values());
           rocksList.add(new Rock(next));
            Log.info("adding Rocks");
        }

        this.area = loc.getArea();

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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public ArrayList<Rock> getRocksList() {
        return rocksList;
    }

    public void setRocksList(ArrayList<Rock> rocksList) {
        this.rocksList = rocksList;
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
