package ph.asaboi.lostengineer.classes;

/**
 * Created by P004785 on 1/31/2017.
 */

public class Machine extends  Item {
    public float ActionCounter;
    public float ActionSpeed;
    public float ActionBar;
    public Inventory Inventory;
    private boolean IsBusy;
    public Recipe Recipe;

    public Machine(Inventory inventory) {
        IsBusy = false;
        ActionSpeed = 1;
        ActionCounter = 0;
        ActionBar = 0;
        Inventory = inventory;

        Category = "Machine";
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
                Inventory.Add(Recipe.Outputs);
                IsBusy = false;
            }
        }else{
            // Check if inventory has enough.
            if(Inventory.Has(Recipe.Inputs))
            {
                // Start the machine
                IsBusy = true;
                ActionCounter += Recipe.Speed;
                // Take out the inputs.
                Inventory.Remove(Recipe.Inputs);
            }
        }
    }
}
