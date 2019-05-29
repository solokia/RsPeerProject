package SevenQP;


import org.rspeer.runetek.adapter.Varpbit;
import org.rspeer.runetek.adapter.scene.Npc;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.Varps;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.Dialog;
import org.rspeer.runetek.api.component.InterfaceOptions;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.Script;
import org.rspeer.script.ScriptMeta;
import org.rspeer.ui.Log;
@ScriptMeta(developer = "Solly", desc = "7QP", name = "Auto7QP")
public class SevenQP extends Script {
    private static Position npcPosition1= new Position(2951,3450,0);
    private static Area quest1Area;
    private String quest1Name = "Doric's Quest";
    private String name1 = "Doric";
    private Player me;
    private Npc Doric;

    @Override
    public void onStart(){
//        Varpbit i = Varps.getAll();
//        int x = Varps.getBitValue(303);
//        Log.info("Varps x : " + x);
//        Log.info("Varps key: " + i.getKey());
//        Log.info("Varps index : " + i.getVarpIndex());
//        Log.info("Varps i.getvalue 31: " + i.getValue(31));
//        Log.info("Varps i.tostring : " + i.toString());

    }
    @Override
    public int loop() {
        Log.info(InterfaceOptions.QuestTabMode.QUEST_LIST.values());
        return 10000;
    }
    public int quest1(){
        InterfaceOptions.QuestTabMode.valueOf(quest1Name);
        Log.info("looping");
        me = Players.getLocal();
        quest1Area= Area.surrounding(npcPosition1,2,npcPosition1.getFloorLevel());
        if(!quest1Area.contains(me)){
            Movement.walkToRandomized(quest1Area.getCenter());
            Time.sleep(300,2000);
            return 300;
        }
        try {
            Doric = Npcs.getNearest(name1);
        }catch(Exception e){}
        if(!Dialog.isOpen()) {
            Doric.interact("Talk-to");
            Time.sleep(300, 800);
        }else{
            if(Dialog.canContinue())
                Dialog.processContinue();
            if(Dialog.getChatOptions().length==5)
                Dialog.process(0);
            if(Dialog.isProcessing()) {
                Log.info("waiting");
                Time.sleepUntil(()->!Dialog.isProcessing(),200,1000);
                Time.sleep(200, 300);
            }
            if(Dialog.getChatOptions().length==2)
                Dialog.process(0);
            Time.sleep(300);
        }
        return 300;
    }
}
