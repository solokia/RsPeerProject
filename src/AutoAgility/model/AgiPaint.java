package AutoAgility.model;

import org.rspeer.runetek.api.component.tab.Skill;
import org.rspeer.runetek.api.component.tab.Skills;
import org.rspeer.ui.Log;

public class AgiPaint {
    private static int rounds = 0;
    private static int marks = 0;
//    private static int alch = 0;
    private static int agiStartExp = 1;
    private static int agiCurrentExp = 1;
    private static int agiStartLevel = 1;
    private static int agiCurrentLevel = 1;
    private static int agiExpEarned =0;
    private static long startTime;
    private static long currentTime;
    private static String time;
    private static String agiTTL;
    private static String agiExpPerHour ="0";
//    private static

    public AgiPaint() {
    }
    public static void update(){

        currentTime = System.currentTimeMillis();
        long durationInMillis=currentTime-startTime;
        long second = (durationInMillis / 1000) % 60;
        long minute = (durationInMillis / (1000 * 60)) % 60;
        long hour = (durationInMillis / (1000 * 60 * 60)) % 24;
        time = String.format("%02d:%02d:%02d", hour, minute, second);
//        int expNeeded = Skills.getExperienceToNextLevel(Skill.AGILITY);
       if(durationInMillis>1000){
            double agiExpPerSec = ((double)agiExpEarned)/(durationInMillis/(1000));
            if (agiExpPerSec>0) {
                double secNeeded =(Skills.getExperienceToNextLevel(Skill.AGILITY) / agiExpPerSec);
                second = (long)(secNeeded) % 60;
                minute = (long)(secNeeded / 60) % 60;
                hour = (long)(secNeeded / (60 * 60)) % 24;

                agiExpPerHour =String.format("%d", Math.round(agiExpPerSec*60*60));
            }
           agiTTL = String.format("%02d:%02d:%02d", hour, minute, second);
        }else {
           //preset time to level to current time elapse
           agiTTL = time;
       }

    }
    public static int getRounds() {
        return rounds;
    }

    public static void setRounds(int rounds) {
        AgiPaint.rounds = rounds;
    }
    public static void incRounds(){
        AgiPaint.rounds++;
    }
    public static int getMarks() {
        return marks;
    }

    public static void setMarks(int marks) {
        AgiPaint.marks = marks;
    }
    public static void incMarks(){
        AgiPaint.marks++;
    }
//    public static int getAlch() {
//        return alch;
//    }
//
//    public static void setAlch(int alch) {
//        AgiPaint.alch = alch;
//    }

    public static int getAgiStartExp() {
        return agiStartExp;
    }

    public static void setAgiStartExp(int agiStartExp) {
        AgiPaint.agiStartExp = agiStartExp;
    }

    public static int getAgiCurrentExp() {
        return agiCurrentExp;
    }

    public static void setAgiCurrentExp(int agiCurrentExp) {
        AgiPaint.agiCurrentExp = agiCurrentExp;
        agiExpEarned = agiCurrentExp - agiStartExp;
    }

    public static int getAgiStartLevel() {
        return agiStartLevel;
    }

    public static void setAgiStartLevel(int agiStartLevel) {
        AgiPaint.agiStartLevel = agiStartLevel;
    }

    public static int getAgiCurrentLevel() {
        return agiCurrentLevel;
    }

    public static void setAgiCurrentLevel(int agiCurrentLevel) {
        AgiPaint.agiCurrentLevel = agiCurrentLevel;
    }

    public static int getAgiExpEarned() {
        return agiExpEarned;
    }

    public static void setAgiExpEarned(int agiExpEarned) {
        AgiPaint.agiExpEarned = agiExpEarned;
    }

    public static long getStartTime() {
        return startTime;
    }

    public static void setStartTime(long startTime) {
        AgiPaint.startTime = startTime;
    }

    public static long getCurrentTime() {
        return currentTime;
    }

    public static void setCurrentTime(long currentTime) {
        AgiPaint.currentTime = currentTime;
    }

    public static String getTime() {
        return time;
    }

    public static void setTime(String time) {
        AgiPaint.time = time;
    }

    public static String getAgiTTL() {
        return agiTTL;
    }

    public static void setAgiTTL(String agiTTL) {
        AgiPaint.agiTTL = agiTTL;
    }

    public static String getAgiExpPerHour() {
        return agiExpPerHour;
    }

    public static void setAgiExpPerHour(String agiExpPerHour) {
        AgiPaint.agiExpPerHour = agiExpPerHour;
    }
}
