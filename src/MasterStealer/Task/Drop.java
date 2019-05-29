package MasterStealer.Task;


import MasterStealer.Config;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

public class Drop extends Task {
    private String[] drops = {"Potato seed","Onion seed","Cabbage seed","Tomato seed",
            "Sweetcorn seed","Strawberry seed","Watermelon seed","Barley seed",
            "Hammerstone seed","Asgarnian seed","Jute seed","Yanillian seed",
            "Krandorian seed","Wildblood seed","Marigold seed","Nasturtium seed",
            "Rosemary seed","Woad seed","Redberry seed","Cadavaberry seed",
            "Dwellberry seed","Jangerberry seed","Whiteberry seed","Poison ivy seed",
            "Harralander seed","Irit seed","Cadantine seed","Guam seed",
            "Marrentill seed","Tarromin seed","Jug"};



    @Override
    public boolean validate() {

        return Inventory.isFull()&&Inventory.contains(Config.getFood());
    }

    @Override
    public int execute() {
        Log.info("Inside Drop");
        if(Inventory.contains(drops)){

            Item todrop;

            while(Inventory.contains(drops)) {
                todrop = Inventory.getFirst(drops);
                todrop.interact("Drop");

                Time.sleep(450,700);
            }
        }
        if(Inventory.isFull()&&Inventory.contains(Config.getFood())){
            Item todrop = Inventory.getFirst(Config.getFood());
            Time.sleep(400,800);
        }
        return 0;
    }
}
