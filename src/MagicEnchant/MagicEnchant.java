package MagicEnchant;


import org.rspeer.runetek.adapter.component.InterfaceComponent;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.commons.BankLocation;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.Dialog;
import org.rspeer.runetek.api.component.Interfaces;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.component.tab.Tab;
import org.rspeer.runetek.api.component.tab.Tabs;
import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.Script;
import org.rspeer.script.ScriptMeta;
import org.rspeer.ui.Log;

import java.awt.*;
import java.awt.event.*;
import java.awt.TrayIcon.MessageType;
import java.net.MalformedURLException;

@ScriptMeta(developer = "Solly", desc = "AutoEnchant", name = "AutoEnchant")
public class MagicEnchant extends Script {
    private Player me;
    private Area bankArea =Area.surrounding(BankLocation.getNearest().getPosition(),3);
    private Area tanArea =Area.surrounding(new Position(3276,3191),1) ;
    private static String itemName="Ring of Recoil",materialName="Sapphire ring";
    private boolean clicked = false;
    private static boolean speed = false;
    private static int PARENT_INDEX = 218;
    private static int CHILD_INDEX = 9;
    private final String ACTION = "Cast";
    private static boolean configured =false;
    private EnchantGUI gui;
//    private String npcName = "Ellis";


    @Override
    public void onStart(){
        me = Players.getLocal();
       gui = new EnchantGUI();
       gui.setVisible(true);
    }
    @Override
    public void onStop(){
        if (gui != null) {
            gui.setVisible(false); //Makes sure the gui isn't visible
            gui.dispose(); //Tells the garbage collector that the gui's memory can be freed for use elsewhere
        }
    }
    @Override
    public int loop() {
        if(!configured)
            return 500;
        try{
            if(!Inventory.contains("Cosmic rune")) {
                displayNotification();
                return -1;
            }

            if(!Inventory.contains(materialName)){
                if(speed)
                    Time.sleep(10000,30000);
                return bank();
            }else
            if (Inventory.contains(materialName)&&!(clicked&&speed))
                enchantAll();
            if(Dialog.isOpen()){
                if(Dialog.canContinue())
                    clicked=false;
                if(speed)
                    Time.sleep(2000,20000);
            }

        }catch (Exception e){
            Log.severe(e);
        }


        return 500;
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
            trayIcon.displayMessage("Magic Enchant", "Script ended", MessageType.INFO);
        }catch(Exception e){
            Log.severe(e);
        }
    }

    public static void tempController(Items i,Spells spell,String sp){
        itemName = i.getName();
        materialName = i.getMaterial();
        PARENT_INDEX = spell.getParent();
        CHILD_INDEX = spell.getChild();
        speed=!sp.equals("Fast");
        Log.info("Speed = "+sp);
        configured=true;
    }
    public boolean enchantAll(){
        clicked=true;
        try {
//            Log.info(PARENT_INDEX + " " + CHILD_INDEX);
            InterfaceComponent wornEquipment = Interfaces.getComponent(PARENT_INDEX, CHILD_INDEX);
            if (wornEquipment.isVisible()) {
                Time.sleep(300, 400);
                wornEquipment.interact("");

                Time.sleep(400, 600);
                Item item = Inventory.getFirst(materialName);
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
            Log.severe(e);
        }
        return true;
    }
    public int bank(){
        clicked=false;
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
                displayNotification();
                return -1;
            }
            Bank.withdrawAll(materialName);
            Time.sleep(500,1500);
            Bank.close();
            Time.sleep(300,700);
            if (!Tabs.isOpen(Tab.MAGIC))
                Tabs.open(Tab.MAGIC);
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

    public static void setConfigured(boolean c) {
        configured = c;
    }
}
