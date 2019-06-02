package AutoAgility;

import AutoAgility.Constants.Courses;
import AutoAgility.Constants.Food;
import AutoAgility.Task.*;
import AutoAgility.model.AgiPaint;
import AutoAgility.model.Config;
import AutoAgility.model.MagicPaint;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.component.tab.Skill;
import org.rspeer.runetek.api.component.tab.Skills;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.event.listeners.RenderListener;
import org.rspeer.runetek.event.types.RenderEvent;
import org.rspeer.script.ScriptMeta;
import org.rspeer.script.task.Task;
import org.rspeer.script.task.TaskScript;
import org.rspeer.ui.Log;

import java.awt.*;


@ScriptMeta(developer = "Solly", desc = "Auto Agi and enchant", name = "AutoAgi")
public class AutoAgility extends TaskScript implements RenderListener {
    private static Task[] TASKS = { new Banking(),new Heal(),new Drink(),new Alch(),new Climb(),new Walk()};
//    private long startTime;
//    private long currentTime;
//    private long durationInMillis =1;

    @Override
    public void onStart() {
        Player me = Players.getLocal();
        Config.setFood(Food.Lobster);
        Config.setCourse(Courses.Canifis);
        Config.setConfigured(true);
        if(Config.isHighAlch()){
            MagicPaint.setMagicStartExp(Skills.getExperience(Skill.MAGIC));
            MagicPaint.setMagicCurrentExp(Skills.getExperience(Skill.MAGIC));
            MagicPaint.setMagicStartLevel(Skills.getLevel(Skill.MAGIC));
            MagicPaint.setMagicCurrentLevel(Skills.getLevel(Skill.MAGIC));
            MagicPaint.setStartTime(System.currentTimeMillis());
        }
        AgiPaint.setAgiStartExp(Skills.getExperience(Skill.AGILITY));
        AgiPaint.setAgiCurrentExp(Skills.getExperience(Skill.AGILITY));
        AgiPaint.setAgiStartLevel(Skills.getLevel(Skill.AGILITY));
        AgiPaint.setAgiCurrentLevel(Skills.getLevel(Skill.AGILITY));

        AgiPaint.setStartTime(System.currentTimeMillis());

        AgiPaint.update();


        submit(TASKS);

    }
    @Override
    public void onStop() {
        Log.fine("Thank you for using AutoAGI-Alch");
        Log.fine("Rounds Completed : " + AgiPaint.getRounds());
        Log.fine("Marks Looted : "+ AgiPaint.getMarks());
        Log.fine("Total Agi Exp earned : "+AgiPaint.getAgiExpEarned());
        Log.fine("Agi Level Gained : "+(AgiPaint.getAgiCurrentLevel()-AgiPaint.getAgiStartLevel()));
        Log.fine("Total Magic Exp earned : "+MagicPaint.getMagicExpEarned());
        Log.fine("Magic Level Gained : "+(MagicPaint.getMagicCurrentLevel()-MagicPaint.getMagicStartLevel()));

        Log.fine("Time Elapse : "+AgiPaint.getTime());
    }

    @Override
    public void notify(RenderEvent renderEvent) {//y230-330
        Graphics g = renderEvent.getSource();
        AgiPaint.update();

        g.drawString("Rounds Completed : " + AgiPaint.getRounds(), 20, 230); //x and y are destinations the size of a pixel on the canvas, canvas is 503x765
        g.drawString("Marks Looted : "+ AgiPaint.getMarks(),20,250);
        g.drawString("Agi EXP earned : "+AgiPaint.getAgiExpEarned()+" ("+AgiPaint.getAgiExpPerHour()+")",20,270);
        g.drawString("Agi Level : "+AgiPaint.getAgiCurrentLevel()+"  ("+(AgiPaint.getAgiCurrentLevel()-AgiPaint.getAgiStartLevel())+")",20,290);
        g.drawString("TTL : "+ AgiPaint.getAgiTTL(),20,310);
        g.drawString("Time Elapse : "+ AgiPaint.getTime(),20,330);

        if(Config.isHighAlch()){
            MagicPaint.update();
            g.drawString("Alch Cast : "+ MagicPaint.getAlch(),200,250);
            g.drawString("Magic EXP earned : "+MagicPaint.getMagicExpEarned()+" ("+MagicPaint.getMagicExpPerHour()+")",200,270);
            g.drawString("Magic Level : "+MagicPaint.getMagicCurrentLevel()+" ("+(MagicPaint.getMagicCurrentLevel()-MagicPaint.getMagicStartLevel())+")",200,290);
            g.drawString("Magic TTL : "+ MagicPaint.getMagicTTL(),200,310);
        }
    }
}
