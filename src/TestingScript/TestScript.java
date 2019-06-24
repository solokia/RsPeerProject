package TestingScript;

import org.rspeer.runetek.adapter.component.InterfaceComponent;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.Login;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.Dialog;
import org.rspeer.runetek.api.component.Interfaces;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.event.EventListener;
import org.rspeer.runetek.event.listeners.LoginResponseListener;
import org.rspeer.runetek.event.listeners.RenderListener;
import org.rspeer.runetek.event.types.LoginResponseEvent;
import org.rspeer.script.Script;
import org.rspeer.script.ScriptBlockingEvent;
import org.rspeer.script.ScriptMeta;
import org.rspeer.script.events.LoginScreen;
import org.rspeer.script.events.WelcomeScreen;
import org.rspeer.ui.Log;

@ScriptMeta(developer = "Solly", desc = "TestScript functions", name = "TestScript")
public class TestScript extends Script implements LoginResponseListener{
    private final int PARENT_INDEX = 160;
    private final int CHILD_INDEX = 5;

    private String arrowShaft = "arrow Shaft";
    @Override
    public void onStart(){
        removeBlockingEvent(LoginScreen.class);
//        LoginScreen login = new LoginScreen(this);
//        login.setDelayOnLoginLimit(true);
//        login.setStopScriptOn(LoginResponseEvent.Response.ENTER_AUTH);
//        login.setStopScriptOn(LoginResponseEvent.Response.SERVER_BEING_UPDATED);
//        login.setStopScriptOn(LoginResponseEvent.Response.RUNESCAPE_UPDATE);
//        WelcomeScreen wx = new WelcomeScreen(this);

    }
    @Override
    public int loop() {
        Player me = Players.getLocal();
//        Item x = Inventory.getFirst(arrowShaft);
//        Item y = Inventory.getFirst("Feather");
//        x.interact("Use");
//        Time.sleep(200,300);
//        y.interact("Use");
//        Time.sleep(1000,1300);
//        try {
//            InterfaceComponent wornEquipment = Interfaces.getComponent(PARENT_INDEX, CHILD_INDEX);
//            Log.info("wornEquipment get actions "+wornEquipment.interact("Make sets:"));
//        }catch (Exception e){
//            Log.severe(e);
//        }
//
//        Time.sleep(8000,10000);
//        return 500;
//        InterfaceComponent wornEquipment = Interfaces.getComponent(PARENT_INDEX, CHILD_INDEX);
////        int hp = Integer.parseInt(wornEquipment.getText());

//        Log.info(hp);

//        Log.info("State "+Login.getState());
//        Log.info("properties "+ Login.isDisplayingAccountStatus());
//        Log.info("properties size "+Login.getProperties().size());
//        LoginScreen x = new LoginScreen(this);

//        Log.info("State1 "+Login.getState());
//        if(Login.getState()==0){
//            LoginScreen ls = new LoginScreen(this);
//            Log.info("Login validate 1 "+ls.validate());
//            ls.process();
//            Time.sleep(10000);
//            Log.info("Login validate 2 "+ls.validate());
//
//        }
        Log.info(Dialog.isOpen());
        Log.info(Dialog.canContinue());
        if(Dialog.isOpen()){
            for (InterfaceComponent i : Dialog.getChatOptions()){
                Log.info(i.getText());
            }
        }
        return 500;


    }

    @Override
    public void notify(LoginResponseEvent loginResponseEvent) {
//        Log.info("State "+Login.getState());
//        Log.info("code "+loginResponseEvent.getResponse().getCode());
//
//        setStopping(true);
    }
}
