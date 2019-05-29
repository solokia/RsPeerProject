package AIOFight.Constants;

import AIOFight.Models.ComplexMovement;
import org.rspeer.runetek.api.movement.position.Position;

public enum ComplexMove {
    moveToGiant("Fight",new ComplexMovement[]{new ComplexMovement("Take","Brass Key",new Position(3185,3436,0)),
            new ComplexMovement("Movement","Walk",new Position(3115,3449,0)),
            new ComplexMovement("Interact","Open",new Position(3115,3450,0)),
            new ComplexMovement("Interact","Climb-down",new Position(3116,3452,0))}),
    moveFromGiant("Bank",new ComplexMovement[]{new ComplexMovement("Interact","Climb-up",new Position(3116,9852,0)),
            new ComplexMovement("Interact","Open",new Position(3115,3450,0))});

    private final String direction;
//    private final static String type;
//    private final static String action;
//    private final static Position position;
    private final ComplexMovement[] cm;

    ComplexMove(String direction, ComplexMovement[] cm) {
        this.direction = direction;
        this.cm = cm;
    }


    public String getDirection() {
        return direction;
    }

    public ComplexMovement[] getCm() {
        return cm;
    }

}
