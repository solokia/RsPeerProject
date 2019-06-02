package AutoAgility.model;

import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.movement.position.Position;

public class SubCourse {

    private Area area;
    private Position obstaclePos;
    private String obstacleName;

    public SubCourse(){}

    public SubCourse(Area area, Position obstaclePos,String obstacleName) {
        this.area = area;
        this.obstaclePos = obstaclePos;
        this.obstacleName = obstacleName;
    }

    public SubCourse(Position[] positions, Position obstaclePos,String obstacleName) {
        if(positions.length>2){
            this.area = Area.polygonal(positions);
        }
        else
            this.area = Area.rectangular(positions[0],positions[1]);
        this.obstaclePos = obstaclePos;
        this.obstacleName = obstacleName;

    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Position getObstaclePos() {
        return obstaclePos;
    }

    public void setObstaclePos(Position obstaclePos) {
        this.obstaclePos = obstaclePos;
    }

    public String getObstacleName() {
        return obstacleName;
    }

    public void setObstacleName(String obstacleName) {
        this.obstacleName = obstacleName;
    }
}
