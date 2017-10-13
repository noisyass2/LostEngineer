package ph.asaboi.lostengineer.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

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
        FileHandle file = Gdx.files.internal("data/recipes.json");

        JsonReader jsonReader = new JsonReader();
        JsonValue root = jsonReader.parse(file);

        for(JsonValue recipe : root.get("Recipes"))
        {
            Recipe newRecipe = new Recipe();
            newRecipe.Name = recipe.getString("Name");
            newRecipe.Code = recipe.getString("Code");
            newRecipe.Speed = recipe.getInt("Speed");
            newRecipe.Inputs = new ArrayList<Item>();
            newRecipe.Outputs = new ArrayList<Item>();

            for(JsonValue input : recipe.get("Inputs"))
            {
                Item item = DB.GetItemByCode(input.getString("Code"));
                item.Quantity = input.getInt("Quantity");

                newRecipe.Inputs.add(item);
            }

            for(JsonValue output : recipe.get("Outputs"))
            {
                Item item = DB.GetItemByCode(output.getString("Code"));
                item.Quantity = output.getInt("Quantity");

                newRecipe.Outputs.add(item);
            }

            Recipes.add(newRecipe);
        }
//
//
//        Recipe recipe = new Recipe();
//        recipe.Name = "Stone Furnace";
//        recipe.Code = "stone-furnace";
//
//        recipe.Inputs = new ArrayList<Item>();
//        Item stone = DB.GetItemByCode("stone");
//        stone.Quantity = 5;
//        stone.Name = "Raw Stone";
//
//        recipe.Inputs.add(stone);
//
//        recipe.Outputs = new ArrayList<Item>();
//        Item furnace = new Item();
//        furnace.Code = "stone-furnace";
//        furnace.Quantity = 1;
//        furnace.Name = "Stone Furnace";
//
//        recipe.Outputs.add(furnace);
//        recipe.Speed = 200;
//        Recipes.add(recipe);
//
//
//        recipe = new Recipe();
//        recipe.Name = "Burner Drill";
//        recipe.Code = "burner-mining-drill";
//
//        recipe.Inputs = new ArrayList<Item>();
//        Item ironPlate = new Item();
//       ironPlate.Code = "iron-plate";
//       ironPlate.Quantity = 5;
//       ironPlate.Name = "Raw Stone";
//
//        recipe.Inputs.add(ironPlate);
//
//        recipe.Outputs = new ArrayList<Item>();
//        furnace = new Item();
//        furnace.Code = "burner-mining-drill";
//        furnace.Quantity = 1;
//        furnace.Name = "Burner Drill";
//
//        recipe.Outputs.add(furnace);
//        recipe.Speed = 200;
//        Recipes.add(recipe);

    }

    private static Global instance = null;
    public static Global getInstance(){
        if(instance == null)  instance = new Global();
        return instance;
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
