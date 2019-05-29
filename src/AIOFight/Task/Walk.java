package AIOFight.Task;

import AIOFight.Constants.ComplexMove;
import AIOFight.Models.ComplexMovement;
import AIOFight.Models.Config;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.api.scene.SceneObjects;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

import java.util.ArrayList;
import java.util.Arrays;

public class Walk extends Task {
    private Player me;
    private static Area bankArea;
    private static Area fightArea;
    private static Position bankPosition;
    private static Area check;
//    private static int checkCount=0;
    private static boolean lumbridge=false;
    private Area hcLumb = Area.surrounding(new Position(3196,3218),3);
    private static boolean checkpoint=false;
    private static boolean setCheck = false;

    private ArrayList<ComplexMovement> cmFight=new ArrayList<>(),cmBank=new ArrayList<>();
    private static boolean complexGate=false;
    private static int complexStep=0;
    @Override
    public boolean validate(){
        Log.info("inside walk");
        me = Players.getLocal();
        if(!Config.isConfigured()){
            Log.info("walk returning false");
            return false;
        }else if(Config.isConfigured()) {
            Log.info("Walk setting config");
            complexConf();
        }
        try {
            Log.info("inside try walk");
            boolean test = bankArea.contains(me) || fightArea.contains(me);
        }catch (Exception e){
            Log.info("inside walk exception"+e);
            return false;
        }
        if(!(traverseToBank()|| traverseToFight())){
//            checkCount=0;
            checkpoint=false;
            setCheck=false;
        }
        if(!setCheck){
            checkpoint = lumbridge;
            setCheck = true;
        }
        return (traverseToBank()|| traverseToFight());
    }

    public int execute(){
        Log.info("inside walk execute");

//        if (check==null)
//            check = Area.surrounding(me.getPosition(),6);
//        else if(check.contains(me))
//            checkCount++;
//        else {
//            checkCount=0;
//            check = Area.surrounding(me.getPosition(), 6);
//        }
        if(traverseToFight()) {
            if(Config.isComplexMovement()&&!complexGate&&!fightArea.contains(me))
                complexToFight();
            Position fightPosition = fightArea.getTiles().get(Random.nextInt(fightArea.getTiles().size()));
//            if (lumbridge == true && checkpoint==true&&!hcLumb.contains(me)&&me.getY()<3243) {
//                Log.info("walking to hardcoded lumbridge area");
//                Movement.walkToRandomized(hcLumb.getCenter());
//                Time.sleep(1000, 2000);
//                return 300;
//            }
//            if(hcLumb.contains(me))
//                checkpoint=false;
            if (!fightArea.contains(me) && me.getTarget() == null&&(complexGate||!Config.isComplexMovement())) {
                Log.info("walking");
                Movement.walkTo(fightPosition);
                Time.sleep(1000, 2000);
            }
//            if(checkCount>4) {
//                hardCodedWalk(fightPosition);
//                Movement.getDebug().clear();
//            }
        }
        if(traverseToBank()){
            if(fightArea.contains(me))
                complexGate=true;
            if(Config.isComplexMovement()&&complexGate&&!bankArea.contains(me))
                complexToBank();
            else {
                Log.info("walking to bank");
                Movement.walkTo(bankPosition);
                Time.sleep(1000, 2000);
            }
//            if (lumbridge == true && checkpoint==true&&!hcLumb.contains(me)&&me.getY()>3235) {
//                Log.info("walking to hardcoded lumbridge area");
//                Movement.walkToRandomized(hcLumb.getCenter());
//                Time.sleep(1000, 2000);
//                return 300;
//            }
//            if(hcLumb.contains(me))
//                checkpoint=false;


//            if(checkCount>4) {
//                hardCodedWalk(bankPosition);
//                Movement.getDebug().clear();
//            }
        }
        return 300;
    }
    private boolean complexConf(){
        ArrayList<ComplexMove> cmList=Config.getComplexMoves();
        try {
            for (ComplexMove cm : cmList) {
                Log.info("Forloop Log.info(cm.getDirection()); " + cm.getDirection());
                if (cm.getDirection().equals("Fight")) {
                    for(ComplexMovement cmovement : cm.getCm()) {
                        Log.info("complex conf Fight loop "+cm.getDirection()+"cm size"+cm.getCm().length);
                        Log.info("Loop action "+cmovement.getAction());
                        cmFight.add(cmovement);//new ArrayList<ComplexMovement>(Arrays.asList(cm.getCm()));
                    }
                } else {
                    Log.info("Enter cmbank insertion");
                    cmBank = new ArrayList<ComplexMovement>(Arrays.asList(cm.getCm()));
                }
            }
        }catch(Exception e){
            Log.severe(e);
            return false;
        }

        return true;

    }
    private boolean complexToFight(){
        if(complexStep>=cmFight.size()){
            complexGate=true;
            complexStep=0;
            return true;
        }

        if(switchComplex(cmFight.get(complexStep))){
            complexStep++;
        }

        return true;
    }
    private boolean complexToBank(){
        if(complexStep>=cmBank.size()){
            complexGate=false;
            complexStep=0;
        }

        if(switchComplex(cmBank.get(complexStep))){
            complexStep++;
        }

        return true;
    }
    private boolean switchComplex(ComplexMovement cm ){
        String str = cm.getType();
        Log.info("CM in switch "+ cm.getPosition()+ " and complex step is "+complexStep);
        switch(str){
            case "Take"://assume user has needed items
                if(!Inventory.contains(cm.getAction())) {
                    if (walkToPosition(cm.getPosition()))
                        return Banking.take(cm.getAction());
                }else if(Inventory.contains(cm.getAction()))
                    return true;
                break;
            case "Movement":
                return walkToPosition(cm.getPosition());

            case "Interact":
                Position current = me.getPosition();
                SceneObject target = SceneObjects.getFirstAt(cm.getPosition());
                target.interact(cm.getAction());
                Time.sleepUntil(()->!me.getPosition().equals(current),300,3000);
                return(!me.getPosition().equals(current));

            default:
                return false;
        }
        return false;
    }
    private boolean walkToPosition(Position x){
        Area b = Area.surrounding(x,1,x.getFloorLevel());
        if(!b.contains(me)) {
            Movement.walkTo(x);
            Time.sleep(1000, 2000);
            return false;
        }
        return true;
    }
//    private void hardCodedWalk(Position dest){
//        Time.sleepUntil(() -> (!me.isMoving()), 300, Random.nextInt(5000,7000));
//        int destY = dest.getY();
//        int destX = dest.getX();
//
//        destY = destY-me.getY();
//        destX = destX-me.getX();
//        if(Math.abs(destY)>10)
//            if (destY>10) {
//                destY = 10;
//            }else{
//                destY=-10;
//            }
//        if (Math.abs(destX)>10)
//            if (destX>10) {
//            destX = 10;
//            }else{
//            destX=-10;
//            }
//        while(checkCount>0) {
//            Log.info("Walking hardcoded with "+destX+","+destY+" Offset");
//            Position flag = new Position(me.getX() + destX, me.getY() + destY);
//            Movement.setWalkFlag(flag);
//            Time.sleepUntil(() -> (!me.isMoving()), 300, Random.nextInt(4000,6000));
//            Movement.buildPath(me.getPosition(),dest).walk();
//
//            checkCount--;
//        }
//    }

    private boolean traverseToBank(){
        return (Inventory.isFull()||!Inventory.contains(Config.getFood().getName())&&!bankArea.contains(me));
    }

    private boolean traverseToFight(){
        return !fightArea.contains(me)&&(!Inventory.isFull()&&Inventory.contains(Config.getFood().getName()));
    }

    public static Area getBankArea() {
        return bankArea;
    }

    public static void setBankArea(Area bankArea) {
        Walk.bankArea = bankArea;
    }

    public static Area getFightArea() {
        return fightArea;
    }

    public static void setFightArea(Area fightArea) {
        Walk.fightArea = fightArea;
    }

    public static Position getBankPosition() {
        return bankPosition;
    }

    public static void setBankPosition(Position bankPosition) {
        Walk.bankPosition = bankPosition;
    }

    public static boolean isLumbridge() {
        return lumbridge;
    }

    public static void setLumbridge(boolean lumbridge) {
        Walk.lumbridge = lumbridge;
    }

    public static boolean isCheckpoint() {
        return checkpoint;
    }

    public static void setCheckpoint(boolean checkpoint) {
        Walk.checkpoint = checkpoint;
    }

    public static boolean isSetCheck() {
        return setCheck;
    }

    public static void setSetCheck(boolean setCheck) {
        Walk.setCheck = setCheck;
    }
}
