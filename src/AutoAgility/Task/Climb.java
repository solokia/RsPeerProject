package AutoAgility.Task;

import AutoAgility.model.*;
import org.rspeer.runetek.adapter.component.InterfaceComponent;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.adapter.scene.Pickable;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.Interfaces;
import org.rspeer.runetek.api.component.tab.*;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Pickables;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.api.scene.SceneObjects;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

import java.util.function.Predicate;

public class Climb extends Task {
    private Player me;
    private int counter=0;
    private int markCount =0;
    private Predicate<Item> ipred= item->{
        if(item.isNoted())
            return item.isNoted();
        else if(item.getName().equals(Config.getAlchName()))
            return true;
        else
            return false;
    };
//    private Task alch = new Alch() ;
    @Override
    public boolean validate() {

        me = Players.getLocal();
//        Log.info("In Climb"+(me.getFloorLevel()>0));

        return Config.isConfigured()
                &&me.getFloorLevel()>0
//                && !me.isMoving()
                && (!me.isAnimating()||me.getAnimation()==713);
    }

    @Override
    public int execute() {
        Log.info("Climb execute");
//        Courses courseSelected = Config.getCourse();
        for (SubCourse sub: Config.getCourse()){
            if(sub.getArea().contains(me)){
//                Log.info("subArea Contains");
                Position pos = sub.getObstaclePos();
                Pickable mark = Pickables.getNearest("Mark of grace");
//                Log.info("Test for high alch"+alch.validate());
//                if(alch.validate()){
//                    Log.info("HA");
//                    alch.execute();
//                }
                if(sub.getArea().contains(mark)){
                    Log.info("Take mark");
                    try {
                        Item invMark = Inventory.getFirst("Mark of grace"); // not sure null or error
                        if(invMark==null){
                            markCount=0;
                        }else
                            markCount=invMark.getStackSize();
                    }catch(Exception e){
                        Log.info("0 Mark of grace in Inventory");
                        markCount=0;
                    }

                    mark.interact("Take");
                    Time.sleepUntil(()->!me.isMoving(),300,3000);
                    Time.sleep(300,400);
                    try {
                        Item invMark = Inventory.getFirst("Mark of grace"); // not sure null or error
                        if (markCount < invMark.getStackSize())
                            AgiPaint.incMarks();
                    }catch(Exception e){
                        Log.info("Fail to pick mark");
                    }
                }else if(Area.surrounding(pos,3).contains(me)){
//                    Log.info("obstacle contain");
                    Time.sleep(200,300);//Time to react to landing
                    SceneObject climb = SceneObjects.getNearest(sub.getObstacleName());
                    climb.interact(climb.getActions()[0]);
                    Time.sleepUntil(()->!sub.getArea().contains(me)&&!me.isMoving(),300,3000);
                    if(Config.getCourse().getLast().equals(sub)){
                        AgiPaint.incRounds();
                    }
                }else{
//                    Log.info("Walkto obstacle");
                    if(!Movement.walkTo(pos))
                        Movement.setWalkFlag(pos);//if get stuck in midair
                    Time.sleep(400,600);
                }
                AgiPaint.setAgiCurrentLevel(Skills.getLevel(Skill.AGILITY));
                AgiPaint.setAgiCurrentExp(Skills.getExperience(Skill.AGILITY));
                AgiPaint.update();
                return 300;
            }
        }
        return 300;
    }


    public int getMarkCount() {
        return markCount;
    }

    public void setMarkCount(int markCount) {
        this.markCount = markCount;
    }
}
