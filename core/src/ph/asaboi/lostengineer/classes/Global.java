package ph.asaboi.lostengineer.classes;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import static com.wagnerandade.coollection.Coollection.*;

import java.util.ArrayList;

/**
 * Created by P004785 on 1/26/2017.
 */

public class Global {
    public Inventory Inventory;
    public ArrayList<Recipe> Recipes;
    private int RecipeIndex;
    private ArrayList<TextButton> RecipeButtons;

    public Global() {
        Inventory = new Inventory();
        Recipes = new ArrayList<Recipe>();

        //Recipes
        Recipe recipe = new Recipe();
        recipe.Name = "Stone Furnace";
        recipe.Code = "stone-furnace";

        recipe.Inputs = new ArrayList<Item>();
        Item stone = new Item();
        stone.Code = "Stone";
        stone.Quantity = 5;
        stone.Name = "Raw Stone";

        recipe.Inputs.add(stone);

        recipe.Outputs = new ArrayList<Item>();
        Item furnace = new Item();
        furnace.Code = "stone-furnace";
        furnace.Quantity = 1;
        furnace.Name = "Stone Furnace";

        recipe.Outputs.add(furnace);
        recipe.Speed = 200;
        Recipes.add(recipe);


        recipe = new Recipe();
        recipe.Name = "Burner Drill";
        recipe.Code = "burner-mining-drill";

        recipe.Inputs = new ArrayList<Item>();
        Item stone = new Item();
        stone.Code = "iron-gear-wheel";
        stone.Quantity = 5;
        stone.Name = "Raw Stone";

        recipe.Inputs.add(stone);

        recipe.Outputs = new ArrayList<Item>();
        Item furnace = new Item();
        furnace.Code = "stone-furnace";
        furnace.Quantity = 1;
        furnace.Name = "Stone Furnace";

        recipe.Outputs.add(furnace);
        recipe.Speed = 200;
        Recipes.add(recipe);

    }

    public void GainWood(int i) {
        Inventory.Add("Wood",i);
        System.out.println("Added wood");

    }

    public void GainOre(String ore, int i) {
        Inventory.Add(ore,i);
        System.out.println("Added " + i + " " + ore);
    }

    public void AddMiner(String miner)
    {
        if(Inventory.Has("burner-mining-drill",1)) {
            Inventory.Add(miner + "-drill", 1);
            Inventory.Remove("burner-mining-drill",1);
        }
        System.out.println("Added " + miner + " drill");
    }

    public void DelMiner(String miner)
    {
        if(Inventory.Has(miner + "-drill",1)) {
            Inventory.Add("burner-mining-drill", 1);
            Inventory.Remove(miner + "-drill",1);
        }
        System.out.println("Removed " + miner + " drill");
    }

    public void AddFurnace(String furnace) {
        if(Inventory.Has("stone-furnace",1)) {
            Inventory.Add(furnace + "-furnace", 1);
            Inventory.Remove("stone-furnace",1);
        }
        System.out.println("Added " + furnace + " furnace");
    }


    public void DelFurnace(String furnace)
    {
        if(Inventory.Has(furnace + "-furnace",1)) {
            Inventory.Add("stone-furnace", 1);
            Inventory.Remove(furnace + "-furnace",1);
        }
        System.out.println("Removed " + furnace + " furnace");
    }
}
