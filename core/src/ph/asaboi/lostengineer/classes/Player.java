package ph.asaboi.lostengineer.classes;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.util.ArrayList;

import static com.wagnerandade.coollection.Coollection.*;

/**
 * Created by P004785 on 1/26/2017.
 */

public class Player {
    public float ChopSpeed;
    public boolean IsChopping;
    private int ChopCounter;
    public boolean IsChopEnabled;
    public float ChopPBARValue;
    public boolean IsMining;
    public int MineCounter;
    public int MineSpeed;
    public int MinePBARValue;
    public Global Global;
    public boolean IsMineEnabled;
    public String CurrentOre;

    public boolean IsBusy;
    public float ActionBARValue;
    public float ActionCounter;
    public float ActionSpeed;
    private Recipe CurrentRecipe;
    private boolean IsCrafting;
    private ArrayList<TextButton> RecipeButtons;
    private int RecipeIndex;
    private float ActionMax;

    public Player(Global global) {
        IsChopping = false;
        ChopSpeed = 1;
        MineSpeed = 1;
        ChopCounter = 0;
        IsChopEnabled = true;
        Global = global;
        IsMining = false;
        MineCounter = 0;
        IsMineEnabled = true;
        IsCrafting = false;

        ActionCounter = 0;
        ActionBARValue = 0;
        ActionSpeed = 1;
        ActionMax = 100;
    }



    public void Update()
    {
        if(IsBusy)
        {
            ActionCounter -= ActionSpeed;
            ActionBARValue = (((ActionCounter/ActionMax  ) * 100) < 0) ? 0 : (ActionCounter/ActionMax ) * 100;
//            System.out.println("ActionBARValue = " + ActionBARValue);
            if(ActionBARValue <= 0)
            {
                if(IsChopping)
                {
                    IsChopping = false;
                    Global.GainWood(10);
                }

                if(IsMining)
                {
                    IsMining = false;
                    Global.GainOre(CurrentOre,1);
                }

                if(IsCrafting)
                {
                    IsCrafting = false;
                    Global.Inventory.Add(CurrentRecipe.Outputs);
                }

                IsBusy = false;
            }
        }
    }

    public void Chop(){
        IsBusy = true;
        ActionCounter += 100;
        ActionMax = 100;
        IsChopping = true;
    }

    public void Mine(String ore) {
        CurrentOre = ore;
        IsBusy = true;
        ActionCounter += 100;
        ActionMax = 100;
        IsMining = true;
    }

    public Table GetRecipesTable(Skin skin)
    {
        RecipeButtons = new ArrayList<TextButton>();
        Table recipeTable = new Table();
        RecipeIndex = 0;
        for (final Recipe recipe :
                Global.Recipes) {

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

    public void CraftItem(String recipeCode) {

        Recipe recipe = from(Global.Recipes).where("code", eq(recipeCode)).first();
        if(Global.Inventory.Has(recipe.Inputs))
        {
            //Start Crafting
            IsBusy = true;

            // Remove before crafting
            Global.Inventory.Remove(recipe.Inputs);

            CurrentRecipe = recipe;
            ActionCounter += recipe.Speed;
            ActionMax = recipe.Speed;
            IsCrafting = true;
        }


    }
}
