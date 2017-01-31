package ph.asaboi.lostengineer.classes;

/**
 * Created by P004785 on 1/31/2017.
 */

public class Machine extends  Item {
    public float ActionCounter;
    public float ActionSpeed;
    public float ActionBar;
    public Global Global;
    private boolean IsBusy;
    public Recipe Recipe;

    public Machine(Global global) {
        IsBusy = false;
        ActionSpeed = 1;
        ActionCounter = 0;
        ActionBar = 0;
        Global = global;
    }

    public void Update()
    {
        if(IsBusy){
            // Machine is started, process recipe
            ActionCounter -= ActionSpeed;
            ActionBar = (ActionCounter< 0) ? 0 : ActionCounter;
            if(ActionBar <= 0)
            {
                // Process done, add the output.
                Global.Inventory.Add(Recipe.Outputs);
            }
        }else{
            // Check if inventory has enough.
            if(Global.Inventory.Has(Recipe.Inputs))
            {
                // Start the machine
                IsBusy = true;
                ActionSpeed += Recipe.Speed;
                // Take out the inputs.
                Global.Inventory.Remove(Recipe.Inputs);
            }
        }
    }
}
