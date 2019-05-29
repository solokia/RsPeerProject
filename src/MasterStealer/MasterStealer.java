package MasterStealer;

import MasterStealer.Task.*;
import org.rspeer.script.ScriptMeta;
import org.rspeer.script.task.Task;
import org.rspeer.script.task.TaskScript;

@ScriptMeta(developer = "Solly", desc = "steal farmer", name = "AA MasterStealer")
public class MasterStealer extends TaskScript {
    private static Task[] TASKS = { new Drop(),new Banking(),new Heal(),  new Steal(), new Walk() };

    @Override
    public void onStart() {
        submit(TASKS);
    }
}
