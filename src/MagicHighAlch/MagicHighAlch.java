package MagicHighAlch;


import org.rspeer.runetek.adapter.component.InterfaceComponent;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.adapter.scene.Npc;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.Game;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.GrandExchange;
import org.rspeer.runetek.api.component.GrandExchangeSetup;
import org.rspeer.runetek.api.component.Interfaces;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.component.tab.Tab;
import org.rspeer.runetek.api.component.tab.Tabs;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.providers.RSGrandExchangeOffer;
import org.rspeer.script.Script;
import org.rspeer.script.ScriptMeta;
import org.rspeer.script.events.breaking.BreakEvent;
import org.rspeer.ui.Log;

import java.util.Arrays;
import java.util.HashSet;

import java.util.Set;
import java.util.function.Predicate;

@ScriptMeta(developer = "Solly", desc = "AutoAlch", name = "HighAutoAlch")
public class MagicHighAlch extends Script {
    private Player me;
    private static String itemName="Ring of Recoil",materialName="Sapphire ring";
    private static boolean speed = false;
    private static int PARENT_INDEX = 218;
    private static int CHILD_INDEX = 38;
    private static final int GEPARENT = 465;
    private final String ACTION = "Cast";
    private String runeName = "Nature rune";
    private int runePrice = 212;
    private int presetPrice = 9005;//9007,8695
    private int noToBuy =100;
    private String items[] = {"Onyx bolts (e)","Air battlestaff"};//"Onyx bolts (e)",
    private int highAlchValue = 9300;// 9300,9000
    private int ongoingColor = 14188576;
    private int doneColor = 24320;
    private int doneWidth = 105;
    private int i=0;
    private RSGrandExchangeOffer emptySlot;
    private boolean setup = false;
    private boolean buying = false;
    private String buyingItem = "";
    private int counter = 0;


    private Set<String> itemSet = new HashSet<>();
    private Predicate<Item> ipred= item->{
        if(item.isNoted())
            return item.isNoted();
        else if(itemSet.contains(item.getName()))
            return itemSet.contains(item.getName());
        else
            return false;
    };
//    private String npcName = "Ellis";


    @Override
    public void onStart(){
        Log.info("Start");
        itemSet.addAll(Arrays.asList(items));
//        itemSet.add(items);
        Log.info("stuck here?");

        me = Players.getLocal();
        Log.info("Startend");
//        gui = new EnchantGUI();
//        gui.setVisible(true);
    }
    @Override
    public int loop() {
        me = Players.getLocal();
        if(me==null)
            return 500;

        if(!setup)
            return setGE();
        if(!Inventory.contains(ipred)&&!buying){
            Log.info(Inventory.contains(ipred));
            return buyStaff(items[1]);
        }
        if(!Inventory.contains(runeName)&&!buying){
            return buyStaff(runeName);
        }
//        Log.info("Loop");
        if( buying)
            return buyingState();
        else {

            if (!Inventory.contains("Nature rune") && !buying)
                return -1;
            if (!Inventory.contains(ipred) && !buying)
                return -1;
            else if (Inventory.contains("Nature rune"))
                return enchantAll();

        }

        return 500;
    }
    public int setGE(){
        Log.info("Setting up ");
        Npc geClerk = Npcs.getNearest("Grand Exchange Clerk");
        geClerk.interact("Exchange");
        Time.sleep(500,1000);
        if(!GrandExchange.isOpen()) {
            Log.info("Opening GE");
            GrandExchange.open(); // doesn't work :/
            Time.sleepUntil(()->GrandExchange.isOpen(),300,3000);
            Time.sleep(400,600);
        }
        if (GrandExchange.isOpen()) {
            Time.sleep(300,700);
            setup = true;
            emptySlot = GrandExchange.getFirstEmpty();
            if (emptySlot == null)
                return -1;
            emptySlot.create(RSGrandExchangeOffer.Type.BUY);
            Time.sleep(1300,2000);
            GrandExchangeSetup.setItem(runeName);
            Time.sleep(1000,2000);
            runePrice = GrandExchangeSetup.getPricePerItem();
            Time.sleep(500,1000);
            InterfaceComponent closeComponent = Interfaces.getComponent(GEPARENT,2,11);
            closeComponent.click();
            Time.sleep(300,700);
            if (!Tabs.isOpen(Tab.MAGIC))
                Tabs.open(Tab.MAGIC);
            Time.sleep(300,700);
                return 300;
        }
        return 300;
    }
    public int buyStaff( String name){
//        BreakEvent.setCondition();
        Npc geClerk = Npcs.getNearest("Grand Exchange Clerk");
        geClerk.interact("Exchange");
        Time.sleep(500,1000);
        int priceToBuy = presetPrice;
        if(!GrandExchange.isOpen()) {
            GrandExchange.open();
            Time.sleepUntil(()->GrandExchange.isOpen(),300,3000);
            Time.sleep(400,600);
        }
        if (GrandExchange.isOpen()){


            emptySlot.create(RSGrandExchangeOffer.Type.BUY);
            Time.sleep(1300,2000);
            GrandExchangeSetup.setItem(name);
            Time.sleep(1300,2000);
            int ppi = GrandExchangeSetup.getPricePerItem();
            if(name.equals(runeName)) {
                runePrice = ppi;
                if (noToBuy!=0){

                    GrandExchangeSetup.setQuantity((int)noToBuy);
                    Time.sleep(400,600);
                    GrandExchangeSetup.confirm();
                    Time.sleep(400,600);
                    buyingItem=name;
                    buying=true;
                }
            }
            else{
                int profit = highAlchValue-presetPrice-5-runePrice;
                if (profit<0) {
                    Log.info("Negative Profit stopping");
                    return -1;
                }
                else {
                    priceToBuy = presetPrice + Random.nextInt(2, 5);
                    GrandExchangeSetup.setPrice(priceToBuy);
                }

                Item coin = Inventory.getFirst("Coins");
                int invGold = coin.getStackSize();

                noToBuy =(invGold/(priceToBuy+runePrice));
//                Log.info("number to buy "+Math.floor((double)invGold/(double)(priceToBuy+runePrice)));
//                Log.info("number to buy "+Math.floor(invGold/(double)(priceToBuy+runePrice)));

                GrandExchangeSetup.setQuantity(noToBuy);
                GrandExchangeSetup.confirm();
                buying=true;
                buyingItem=name;
            }
            if(noToBuy!=0) {
/*
Get progress doesn't work from here on.
 */
                buyingState();

            }
            if(GrandExchange.isOpen()) {
                InterfaceComponent closeComponent = Interfaces.getComponent(GEPARENT, 2, 11);
                closeComponent.click();
                Time.sleep(300, 700);
                if (!Tabs.isOpen(Tab.MAGIC))
                    Tabs.open(Tab.MAGIC);
            }
            Time.sleep(300,700);
        }
        return 0;
    }
    public int buyingState(){
        Npc geClerk = Npcs.getNearest("Grand Exchange Clerk");
        geClerk.interact("Exchange");
        Time.sleep(500,1000);
//        int priceToBuy = presetPrice;
        if(!GrandExchange.isOpen()) {
            GrandExchange.open();
            Time.sleepUntil(()->GrandExchange.isOpen(),300,3000);
            Time.sleep(400,600);
        }
        if (GrandExchange.isOpen()){
            Time.sleep(300);
            InterfaceComponent progressCheck=Interfaces.getComponent(GEPARENT,7,19);
            for(i=7;i<16;i++){
                Time.sleep(300);
                progressCheck = Interfaces.getComponent(GEPARENT,i,19);
                if(progressCheck.getText().equals(buyingItem)){
                    Log.info("retrieved state");
                    progressCheck = Interfaces.getComponent(GEPARENT,i,22);
                    break;
                }
                try {
                    Log.info(progressCheck.getText());
                }catch(Exception e){
                    Log.info(e.toString());
                }
            }
            if(i>15) {
                Log.info("Stopping i>15");
                return -1;
            }
            for(int t = 0;t<300;t++){
                if(progressCheck.getTextColor() == doneColor) {
                    Log.info("progress = done");
                    break;
                }
                Time.sleep(300,400);
                if(!progressCheck.isVisible()){
                    Log.info("can't see progress of buying");
                    return -1;
                }
            }
            if(progressCheck.getTextColor() == doneColor){
                Log.info("Collecting");
                Interfaces.getComponent(GEPARENT,i,2).interact("View offer");
                Log.info("viewing");
                Time.sleep(300, 500);
                Interfaces.getComponent(GEPARENT,23,2).click();
                Log.info("collect1");
                Time.sleep(300, 500);
                Interfaces.getComponent(GEPARENT,23,3).click();
                Log.info("collect2");
                Time.sleep(300, 500);
                buying=false;
                Log.info("buying set to "+ buying);
            }
    //                Time.sleepUntil(() -> (progressCheck.getTextColor() == doneColor), 300, 30000);
            Time.sleep(300, 500);
//
//                Log.info("get prog "+emptySlot.getState());
//                Log.info("get prog "+RSGrandExchangeOffer.Progress.FINISHED);
//
//                if (emptySlot.getProgress() == RSGrandExchangeOffer.Progress.FINISHED) {
//                    emptySlot.collect(RSGrandExchangeOffer.CollectionAction.NOTE);
////                    emptySlot.collect(RSGrandExchangeOffer.CollectionAction.ITEM);
//                }
//                emptySlot.collect(RSGrandExchangeOffer.CollectionAction.NOTE);

        }
        if(GrandExchange.isOpen()) {
            InterfaceComponent closeComponent = Interfaces.getComponent(GEPARENT, 2, 11);
            closeComponent.click();
            Time.sleep(300, 700);
            if (!Tabs.isOpen(Tab.MAGIC))
                Tabs.open(Tab.MAGIC);
        }
        Time.sleep(300,700);
        return 300;
    }
    public int buyStaff2(){
        Npc geClerk = Npcs.getNearest("Grand Exchange Clerk");
        geClerk.interact("Exchange");
        Time.sleep(500,1000);
        InterfaceComponent geInterface = Interfaces.getComponent(GEPARENT,7,16);
        InterfaceComponent subComponent;

        if(geInterface.isVisible()&&i==0){
            for(i=7;i<16;i++){
                subComponent = Interfaces.getComponent(GEPARENT,i,16);
                try {
                    if (subComponent.getText().equals("Empty"))
                        break;
                }catch (Exception e){
                    Log.fine(e.toString());
                }
            }
        }
        if(i>15)
            return 300;

        subComponent = Interfaces.getComponent(GEPARENT,i,3);
        subComponent.interact("Create");
        InterfaceComponent typeComponent = Interfaces.getComponent(162,45);
//        if (typeComponent.isVisible()){
//            Game.getClient().fireScriptEvent(96,"hello",0);
//        }

        return 300;
    }

    public int enchantAll(){
//        Log.info("Enter enchantAll");

        try {
            if(GrandExchange.isOpen()){
                InterfaceComponent closeComponent = Interfaces.getComponent(GEPARENT,2,11);
                closeComponent.click();
                Time.sleep(300,700);
                if (!Tabs.isOpen(Tab.MAGIC))
                    Tabs.open(Tab.MAGIC);
                Time.sleep(300,700);
            }

//            Log.info(PARENT_INDEX + " " + CHILD_INDEX);
            Item[] noteList = null;
            noteList = Inventory.getItems(ipred);
            if(noteList.length==0)
                return -1;
            InterfaceComponent wornEquipment = Interfaces.getComponent(PARENT_INDEX, CHILD_INDEX);
            if(!wornEquipment.isVisible()){
                counter++;
            }
            if(counter>15){
                if (!Tabs.isOpen(Tab.MAGIC))
                    Tabs.open(Tab.MAGIC);
                Time.sleep(300,700);
                counter= 0;
                return 300;
            }
            if (wornEquipment.isVisible()) {
//                Log.info("enter interface");
                Time.sleep(300, 400);
                wornEquipment.interact("");

                Time.sleep(400, 600);

                Item item = Inventory.getFirst(noteList[0].getName());

                item.interact("Cast");
            }
//        Item[] items = Inventory.getItems();
//        for(Item i:items){
//            i.interact("Cast");
//            Time.sleep(270,320);
//        }
//        Time.sleep(200);
//        Time.sleep(1000,1500);
        }catch (Exception e){
            Log.severe("Enchant error "+e);
            return -1;
        }
        return 500;
    }
    public int bank(){

        if(!Bank.isOpen()){
            Bank.open();
            Time.sleepUntil(() -> Bank.isOpen(), 1000);
        }
        if (Bank.isOpen()) {
            Time.sleep(300);
            Log.info("Depositing");
            Bank.depositAll(itemName);
            Time.sleep(1000,3500);
            if(!Bank.contains(materialName)){

                return -1;
            }
            Bank.withdrawAll(materialName);
            Time.sleep(500,1500);
            Bank.close();
            Time.sleep(300,700);
        }
        return 300;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public static void setPARENT_INDEX(int p) {
        PARENT_INDEX = p;
    }

    public static void setCHILD_INDEX(int c) {
        CHILD_INDEX = c;
    }


}
