package AfkNMZ;

import org.rspeer.runetek.adapter.component.InterfaceComponent;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.Login;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Interfaces;
import org.rspeer.runetek.api.component.tab.Combat;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.component.tab.Skill;
import org.rspeer.runetek.api.component.tab.Skills;
import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.event.listeners.RenderListener;
import org.rspeer.runetek.event.types.RenderEvent;
import org.rspeer.script.Script;
import org.rspeer.script.ScriptMeta;
import org.rspeer.script.events.LoginScreen;
import org.rspeer.ui.Log;


import java.awt.*;
import java.util.function.Predicate;

@ScriptMeta(developer = "Solly", desc = "AFK", name = "AfkNMZ")

public class AFKNMZ extends Script implements RenderListener {
    private final int PARENT_INDEX = 202;
    private final int CHILD_INDEX = 3;
    private final int SUB_INDEX = 5;
    private InterfaceComponent absPotInterface =  Interfaces.getComponent(PARENT_INDEX, CHILD_INDEX,SUB_INDEX);
    private int absCount =0;
    private Item rockCake;
    private Player me;
    private static String potName = "Overload";
    private static Predicate<Item> itemPredicate = item -> {
        if(item.getName().contains("Absorption"))
            return item.getName().contains("Absorption");
        else
            return false;
    };
    private static Predicate<Item> itemPredicate2 = item -> {
        if(item.getName().contains(potName))
            return item.getName().contains(potName);
        else
            return false;
    };
    private Area startArea = Area.rectangular(2598, 3121, 2610, 3109);
    private boolean loopStop =false;
    private Skill selectedSkill = Skill.ATTACK;
    private int startSkillExp;
    private int currentSkillExp;
    private long startTime;
    private long currentTime;
    private double expDiff=0;
    private String time = "";
    private String expPerHour = "";
    @Override
    public void onStart(){
        rockCake = Inventory.getFirst("Dwarven rock cake");
        startSkillExp = Skills.getExperience(selectedSkill);
        startTime = System.currentTimeMillis();
        currentTime = System.currentTimeMillis();
        removeBlockingEvent(LoginScreen.class);
    }
    @Override
    public int loop() {
        if(Login.getState()==0) {
            Log.fine("Login Screen Stopping");
//            Login.enterCredentials();
            return -1;
        }

        absPotInterface =  Interfaces.getComponent(PARENT_INDEX, CHILD_INDEX,SUB_INDEX);
        int currentHp = Skills.getCurrentLevel(Skill.HITPOINTS);
        int currentRange = Skills.getCurrentLevel(Skill.ATTACK);
        currentSkillExp = Skills.getExperience(selectedSkill);
        me = Players.getLocal();
        if(me==null ){
            return -1;
        }
        if(startArea.contains(me)){
            Log.info("Back to starting area stopping ");
            displayNotification();
            return -1;
        }
        if(absPotInterface.isVisible())
            absCount =Integer.parseInt(absPotInterface.getText());
        else
            absCount = 0;
        if(!Inventory.contains(itemPredicate)&&!Inventory.contains(itemPredicate2)&&absCount<100){
            Log.info("empty pot ending");
            displayNotification();
            return -1;
        }
        if (currentRange<=Skills.getLevel(Skill.ATTACK)){

            if (Inventory.contains(itemPredicate2)&&currentHp>50){
                Log.info("Buffing up");
                Item rangePot = Inventory.getFirst(itemPredicate2);
                rangePot.interact("Drink");
                Time.sleepUntil(()->Skills.getCurrentLevel(Skill.HITPOINTS)==(currentHp-50),300,10000);
                Time.sleep(300,500);
                if(Skills.getLevel(Skill.HITPOINTS)==currentHp-50&&Skills.getLevel(Skill.HITPOINTS)>1)
                    loopCake(currentHp);
            }
        }
        if(Inventory.contains(itemPredicate)) {
            if (absCount <= 300 + Random.nextInt(10, 400)){
                Item absPot = Inventory.getFirst(itemPredicate);
//                int absCount = Inventory.getCount(itemPredicate);
                while(Inventory.contains(itemPredicate)&&absCount<800+Random.nextInt(10,100)){
                    try {
                        absPot = Inventory.getFirst(itemPredicate);
                        absPot.interact("Drink");
                        if(absCount==0)
                            Time.sleep(1200,1500);
                        else
                            Time.sleep(300,800);
                        absPotInterface = Interfaces.getComponent(PARENT_INDEX, CHILD_INDEX, SUB_INDEX);
                        absCount = Integer.parseInt(absPotInterface.getText());
                        Log.info("absorption count "+absCount);
                        if(loopStop)
                            break;
                    } catch(Exception e){
                        Log.info("Exception in absorption "+e.toString());
                        break;
                    }
                }
                Time.sleep(300,500);
                loopCake(currentHp);
            }
        }


        if(currentHp>1&&currentHp<51||(currentHp>1&&!Inventory.contains(itemPredicate2))){
            if(currentHp>Random.nextInt(1,4)){
                loopCake(currentHp);
            }else
            if(Random.nextInt(1,99)>95){
                loopCake(currentHp);
            }
        }


        return Random.nextInt(31230,61230);
    }
    @Override
    public void onStop(){
        loopStop=true;
        Log.fine("Time Elapsed : "+time);
        Log.fine("Exp Earned : "+ expDiff);
        Log.fine("EXP per hour : "+expPerHour);
    }
    private void loopCake(int times){
//        if(Combat.getSpecialEnergy()==100){
//            Log.info("using special");
//            Combat.toggleSpecial(true);
//            Time.sleep(600);
//        }

        Log.info("Eating hp : "+ Skills.getCurrentLevel(Skill.HITPOINTS));
//        if(times>9) {
//            times = 20;
//            int amount = (times/10)+1;
//
//
//        }
        for(int i =times;i>1;i--){
            if(loopStop||Skills.getCurrentLevel(Skill.HITPOINTS)==1||Skills.getCurrentLevel(Skill.HITPOINTS)>=45&&Inventory.contains(itemPredicate2) )
                break;
            rockCake.interact("Guzzle");
            Time.sleep(400,800);

        }
    }
    public void displayNotification(){
        try {
            SystemTray tray = SystemTray.getSystemTray();

            // If you want to create an icon in the system tray to preview
//            Image image = Toolkit.getDefaultToolkit().createImage("some-icon.png");
            //Alternative (if the icon is on the classpath):
            Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("icon.png"));

            TrayIcon trayIcon = new TrayIcon(image, "Java AWT Tray Demo");
            //Let the system resize the image if needed
            trayIcon.setImageAutoSize(true);
            //Set tooltip text for the tray icon
            trayIcon.setToolTip("System tray icon demo");
            tray.add(trayIcon);

            // Display info notification:
            trayIcon.displayMessage("Magic Enchant", "Script ended", TrayIcon.MessageType.INFO);
        }catch(Exception e){
            Log.severe(e);
        }
    }
    @Override
    public void notify(RenderEvent renderEvent) {
        Graphics g = renderEvent.getSource();
        currentTime = System.currentTimeMillis();
        long durationInMillis=currentTime-startTime;
        long second = (durationInMillis / 1000) % 60;
        long minute = (durationInMillis / (1000 * 60)) % 60;
        long hour = (durationInMillis / (1000 * 60 * 60)) % 24;
        time = String.format("%02d:%02d:%02d", hour, minute, second);
        expDiff = Skills.getExperience(selectedSkill)-startSkillExp;
        double expPerSec= expDiff/(durationInMillis/(1000));
        expPerHour =String.format("%d", Math.round(expPerSec*60*60));
        if(durationInMillis>1000&&expPerSec>0) {

            g.drawString("EXP earned : " + expDiff +" ("+expPerHour+")", 20, 230); //x and y are destinations the size of a pixel on the canvas, canvas is 503x765
            g.drawString("Time elapsed : " + time, 20, 250);
        }

    }
}
