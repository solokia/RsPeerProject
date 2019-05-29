package AIOFight.Models;


import org.rspeer.runetek.api.movement.position.Position;

public class ComplexMovement {
    private String type;
    private String action;
    private Position position;

    public ComplexMovement() {
    }

    public ComplexMovement(String type, String action, Position position) {
        this.type = type;
        this.action = action;
        this.position = position;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public  Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
