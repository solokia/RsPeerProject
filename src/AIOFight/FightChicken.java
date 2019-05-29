package AIOFight;

import AIOFight.Constants.ComplexMove;
import AIOFight.Constants.Food;
import AIOFight.Constants.Locations;
import AIOFight.Constants.Mobs;
import AIOFight.Models.ComplexMovement;
import AIOFight.Models.Config;
import AIOFight.Models.Location;
import AIOFight.Models.Monster;
import AIOFight.Task.*;
import AIOFight.View.AIOfightGUI;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.commons.BankLocation;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.ScriptMeta;
import org.rspeer.script.task.Task;
import org.rspeer.script.task.TaskScript;
import org.rspeer.ui.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

@ScriptMeta(developer = "Solly", desc = "AIOFight Shit2", name = "AIOFight script2")
public class FightChicken extends TaskScript {

    private static Locations locations;
    private static Mobs mobs;
    private static Location location;
    public static Monster monster;
    private AIOfightGUI gui;
    private Area FIGHT_AREA;//testing
    private Position FIGHT_POSITION;
    private static String mobName;
    private static boolean configured=false;
    private static boolean changed=false;
    private static Task[] TASKS = { new Banking(),new Heal(), new Loot(), new Bury(), new Fight(), new Walk() };
    private static ArrayList<String> mobsToFight;
    private static HashSet<String> itemsToLoot;
//    private static Config config = new Config();

    public static int i = 0;

    public static void tempController(){ // will probably set a configure model
        Log.info("setting up");
        location = new Location(locations);
        Config.setFightArea(location.getArea());
        Fight.setFightArea(location.getArea());
        Loot.setFightArea(location.getArea());
        Walk.setFightArea(location.getArea());
        mobsToFight=new ArrayList<>();// resetting it to release any previous choice
        itemsToLoot=new HashSet<>();
        for (Monster next: location.getMonsters()) {
            Log.info(next.getName());
            mobsToFight.add(next.getName());//since its reference only after button is press should be fine
            itemsToLoot.addAll(Arrays.asList(next.getDrops())); // not sure if Collection.addAll(set,Array.toString()) will be faster
            Config.getItemToLoot().addAll(Arrays.asList(next.getDrops()));
//            for(String i : next.getDrops())
//                itemsToLoot.add(i);
        }

        Fight.setMobsToFight(mobsToFight);
        Loot.setItemToLoot(itemsToLoot);
        Walk.setBankPosition(BankLocation.getNearest().getPosition());
        Config.setBankArea(Area.surrounding(BankLocation.getNearest().getPosition(),5,BankLocation.getNearest().getPosition().getFloorLevel()));
        Walk.setBankArea(Area.surrounding(BankLocation.getNearest().getPosition(),5,BankLocation.getNearest().getPosition().getFloorLevel()));
        Banking.setBankArea(Area.surrounding(BankLocation.getNearest().getPosition(),5,BankLocation.getNearest().getPosition().getFloorLevel()));
        if(BankLocation.getNearest()==BankLocation.LUMBRIDGE_CASTLE){
            Walk.setLumbridge(true);
            Log.info("set lumbridge true");
        }else
            Log.info(BankLocation.getNearest().getName());

        Config.setComplexMovement(locations.isComplex());
        for(ComplexMove cm: locations.getComplexMoves()) {
            Log.info("main "+cm.getDirection());
            for (ComplexMovement cmovement: cm.getCm()) {
                Log.info("main "+cmovement.getAction());
            }
        }
        Config.setComplexMoves(new ArrayList<>(Arrays.asList(locations.getComplexMoves())));
        Config.setFood(Food.Lobster);
        Config.setConfigured(true);


    }

    @Override
    public void onStart() {
        Log.info("GUI Configuration");
        gui = new AIOfightGUI();
        gui.setVisible(true);
        Player me = Players.getLocal();
        submit(TASKS);
//        while(!configured){
//            Time.sleep(300);
//        }

    }

//    @Override
//    public int loop() {
//        if(configured) {
//            if(!changed) {
//                Location location = new Location(locations);
//                FIGHT_AREA = location.getArea();
//                mobName = location.getMonsters().get(0).getName();
//                changed=true;
//            }
//
//            Player me = Players.getLocal();
//            if(FIGHT_POSITION==null)
//                FIGHT_POSITION = FIGHT_AREA.getTiles().get(Random.nextInt(FIGHT_AREA.getTiles().size()));
//            try{
//                if(!FIGHT_AREA.contains(me) && me.getTarget()==null) {
//                    Log.info("walking");
//                    Movement.walkTo(FIGHT_POSITION);
//                    Time.sleep(1000, 3000);
//                }
//            }catch(Exception e){ }
//            if(FIGHT_AREA.contains(me)) {
//                Log.info("Loop number " + i++);
//                attack();
//                //Movement.walkTo(Positionable position)
////        Log.info("Sleeping");
//                Time.sleep(500, 1500);
////        Log.info("sleep over");
//                int test = Random.nextInt(1, 500);
//                Log.info("test value for sleeping" + test);
//                if (test > 495) {
//                    Log.info("test long sleep" + test);
//                    Time.sleep(12000, 24000);
//                }
//            }
//        }
//        return 300;
//    }
//    private void takeItem(Pickable i){
//        Player me = Players.getLocal();
//        if(i!=null && me.getTargetIndex() == -1){
//            i.interact("Take");
//            Log.info("looted and target index = "+me.getTargetIndex());
//        }
//        Log.info("sleeping after take item function");
//        Time.sleep(1000,2000);
//        Log.info("Sleep over");
//    }
//    private void attack() {
//        Log.info("into attack");
//        Player me = Players.getLocal();
//        Npc mobToKill = Npcs.getNearest(n -> n.getName().equals(mobName) && !n.isHealthBarVisible()&&(n.getTargetIndex() == -1 || n.getTarget().equals(me)));
//        Pickable groundBones = Pickables.getNearest(x -> x.getName().equals("Bones") && x.distance(me) < 3);
//        Pickable feathers = Pickables.getNearest(x -> x.getName().equals("Feather") && x.distance(me) < 4);
//
//        Log.info("me.gettargetindex "+me.getHealthPercent());
//        Log.info("me.gettarget "+me.getTarget());
//        if (!Movement.isRunEnabled() && Movement.getRunEnergy() > Random.nextInt(10, 25))
//            Movement.toggleRun(true);
//        if (Dialog.canContinue())
//            Dialog.processContinue();
////        if (invBones != null && me.getTargetIndex() == -1)
////            invBones.interact("Bury");
//
////        if(feathers != null && me.getTargetIndex() == -1)
////            feathers.interact("Take");
//        if(feathers != null && me.getTargetIndex() == -1)
//            takeItem(feathers);
//        burryBone();
//
//        if (groundBones != null && me.getTargetIndex() == -1) {
//            takeItem(groundBones);
//        }else {
//            if (me.getTargetIndex() == -1) {
//                if (mobToKill != null) {
//                    if (!me.isAnimating()) {
//                        Log.info("Attacking");
//                        mobToKill.interact("Attack");
//                        Log.info("Sleeping");
//                        Log.info("me.gettarget 1 "+me.getTarget());
//                        Time.sleep(2000);
//                        Log.info("me.gettarget after sleep "+me.getTarget());
////                        Player ume = Players.getLocal();
//
//                        Time.sleepUntil(() -> (me.getTarget() == null),()->(me.isAnimating()),200, Random.nextInt(3000, 4000));
//                        Log.info("finish attack "+me.getTarget());
//                        Log.info(!me.isMoving());
//                        Log.info("Finish attacking ");
//                    }
//
//                }
//            }
//        }
//
//
//    }

    public void burryBone(){
        Player me = Players.getLocal();
        int BoneCount = 0;
        // had error initializing bonecount with each call so seperating them
        try{
            BoneCount =Inventory.getCount("Bones");
            Log.info("get Bone Count "+BoneCount);
        }catch(Exception e){
            Log.info("get Bone Count error ");
        }
        if(BoneCount>Random.nextInt(4,10)&& me.getTargetIndex() == -1){
            while(BoneCount!=0){
                Item invBones = Inventory.getFirst("Bones");
                invBones.interact("Bury");
                Time.sleep(500,1000);
                BoneCount =Inventory.getCount("Bones");
                Log.info(BoneCount);
            }
        }
    }

    @Override
    public void onStop(){

    }

    public static void setLocations(Locations locations) {
        FightChicken.locations = locations;
    }

    public static void setMobs(Mobs mobs) {
        FightChicken.mobs = mobs;
    }

    public static void setConfigured(boolean configured) {
        FightChicken.configured = configured;
    }


}

