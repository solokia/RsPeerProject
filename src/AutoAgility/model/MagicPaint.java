package AutoAgility.model;

import org.rspeer.runetek.api.component.tab.Skill;
import org.rspeer.runetek.api.component.tab.Skills;
import org.rspeer.ui.Log;

public class MagicPaint {

    private static int alch = 0;
    private static int magicStartExp = 1;
    private static int magicCurrentExp = 1;
    private static int magicStartLevel = 1;
    private static int magicCurrentLevel = 1;
    private static int magicExpEarned =0;
    private static long startTime;
    private static long currentTime;
    private static String magicTTL = "00:00:00";
    private static String magicExpPerHour ="0";
//    private static

    public MagicPaint() {
    }
    public static void update(){

        currentTime = System.currentTimeMillis();
        long durationInMillis=currentTime-startTime;
        long second,minute,hour;

//        int expNeeded = Skills.getExperienceToNextLevel(Skill.AGILITY);
        if(durationInMillis>1000){
            double magicExpPerSec = ((double)magicExpEarned)/(durationInMillis/(1000));
            if (magicExpPerSec>0) {
                double secNeeded =(Skills.getExperienceToNextLevel(Skill.MAGIC) / magicExpPerSec);
                second = (long)(secNeeded) % 60;
                minute = (long)(secNeeded / 60) % 60;
                hour = (long)(secNeeded / (60 * 60)) % 24;

                magicExpPerHour =String.format("%d", Math.round((magicExpPerSec*60*60)));
                magicTTL = String.format("%02d:%02d:%02d", hour, minute, second);
            }
        }
    }

    public static int getAlch() {
        return alch;
    }

    public static void setAlch(int alch) {
        MagicPaint.alch = alch;
    }
    public static void incAlch(){
        MagicPaint.alch++;
        MagicPaint.magicExpEarned+=65;
    }

    public static int getMagicStartExp() {
        return magicStartExp;
    }

    public static void setMagicStartExp(int magicStartExp) {
        MagicPaint.magicStartExp = magicStartExp;
    }

    public static int getMagicCurrentExp() {
        return magicCurrentExp;
    }

    public static void setMagicCurrentExp(int magicCurrentExp) {
        MagicPaint.magicCurrentExp = magicCurrentExp;
        magicExpEarned = magicCurrentExp - magicStartExp;
    }

    public static int getMagicStartLevel() {
        return magicStartLevel;
    }

    public static void setMagicStartLevel(int magicStartLevel) {
        MagicPaint.magicStartLevel = magicStartLevel;
    }

    public static int getMagicCurrentLevel() {
        return magicCurrentLevel;
    }

    public static void setMagicCurrentLevel(int magicCurrentLevel) {
        MagicPaint.magicCurrentLevel = magicCurrentLevel;
    }

    public static int getMagicExpEarned() {
        return magicExpEarned;
    }

    public static void setMagicExpEarned(int magicExpEarned) {
        MagicPaint.magicExpEarned = magicExpEarned;
    }

    public static long getStartTime() {
        return startTime;
    }

    public static void setStartTime(long startTime) {
        MagicPaint.startTime = startTime;
    }

    public static long getCurrentTime() {
        return currentTime;
    }

    public static void setCurrentTime(long currentTime) {
        MagicPaint.currentTime = currentTime;
    }

    public static String getMagicTTL() {
        return magicTTL;
    }

    public static void setMagicTTL(String magicTTL) {
        MagicPaint.magicTTL = magicTTL;
    }

    public static String getMagicExpPerHour() {
        return magicExpPerHour;
    }

    public static void setMagicExpPerHour(String magicExpPerHour) {
        MagicPaint.magicExpPerHour = magicExpPerHour;
    }
}
