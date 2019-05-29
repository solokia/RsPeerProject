package AutoMine.Task;

import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

public class Walk extends Task {
    private Player me;
    private static Area bankArea;
    private static Area miningArea;
    private static Position bankPosition;
    private static Area check;
    private static int checkCount=0;
    private static boolean lumbridge=false;
    private Area hcLumb = Area.surrounding(new Position(3196,3218),3);
    private static boolean checkpoint=false;
    private static boolean setCheck = false;
    @Override
    public boolean validate(){
        Log.info("inside walk");
        me = Players.getLocal();
        try {
            Log.info("inside try walk");
            boolean test = bankArea.contains(me) || miningArea.contains(me);
        }catch (Exception e){
            Log.info("inside walk exception"+e);
            return false;
        }
        if(!(traverseToBank()|| traverseToFight())){
            checkCount=0;
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
            Position fightPosition = miningArea.getTiles().get(Random.nextInt(miningArea.getTiles().size()));
            if (lumbridge == true && checkpoint==true&&!hcLumb.contains(me)&&me.getY()<3243) {
                Log.info("walking to hardcoded lumbridge area");
                Movement.walkToRandomized(hcLumb.getCenter());
                Time.sleep(1000, 2000);
                return 300;
            }
            if(hcLumb.contains(me))
                checkpoint=false;
            if (!miningArea.contains(me) && me.getTarget() == null) {
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
            if (lumbridge == true && checkpoint==true&&!hcLumb.contains(me)&&me.getY()>3235) {
                Log.info("walking to hardcoded lumbridge area");
                Movement.walkToRandomized(hcLumb.getCenter());
                Time.sleep(1000, 2000);
                return 300;
            }
            if(hcLumb.contains(me))
                checkpoint=false;

            Log.info("walking to bank");
            Movement.walkTo(bankPosition);
            Time.sleep(1000, 2000);
//            if(checkCount>4) {
//                hardCodedWalk(bankPosition);
//                Movement.getDebug().clear();
//            }
        }
        return 300;
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
        return Inventory.isFull()&&!bankArea.contains(me);
    }

    private boolean traverseToFight(){
        return !Inventory.isFull()&&!miningArea.contains(me);
    }

    public static Area getBankArea() {
        return bankArea;
    }

    public static void setBankArea(Area bankArea) {
        Walk.bankArea = bankArea;
    }

    public static Area getMiningArea() {
        return miningArea;
    }

    public static void setMiningArea(Area miningArea) {
        Walk.miningArea = miningArea;
    }

    public static Position getBankPosition() {
        return bankPosition;
    }

    public static void setBankPosition(Position bankPosition) {
        Walk.bankPosition = bankPosition;
    }

    public static Area getCheck() {
        return check;
    }

    public static void setCheck(Area check) {
        Walk.check = check;
    }

    public static int getCheckCount() {
        return checkCount;
    }

    public static void setCheckCount(int checkCount) {
        Walk.checkCount = checkCount;
    }

    public static boolean isLumbridge() {
        return lumbridge;
    }

    public static void setLumbridge(boolean lumbridge) {
        Walk.lumbridge = lumbridge;
    }

    public Area getHcLumb() {
        return hcLumb;
    }

    public void setHcLumb(Area hcLumb) {
        this.hcLumb = hcLumb;
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
