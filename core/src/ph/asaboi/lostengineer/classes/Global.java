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

    public Table GetRecipesTable(Skin skin)
    {
        RecipeButtons = new ArrayList<TextButton>();
        Table recipeTable = new Table();
        RecipeIndex = 0;
        for (final Recipe recipe :
                Recipes) {

            TextButton btnCraft = new TextButton(recipe.Name,skin);
            btnCraft.addListener(new ChangeListener() {
                public void changed (ChangeEvent event, Actor actor) {
                    CraftItem(recipe.Code);
                }
            });

            recipe.Index = RecipeIndex;
            RecipeButtons.add(btnCraft);
            RecipeIndex++;

            recipeTable.add(btnCraft).pad(5);
            recipeTable.row();
        }
        return  recipeTable;
    }

    private void CraftItem(String code) {
        Recipe recipe = from(Recipes).where("code", eq(code)).first();
        if(Inventory.Has(recipe.Inputs))
        {
            Inventory.Remove(recipe.Inputs);
            Inventory.Add(recipe.Outputs);
        }
    }
}
