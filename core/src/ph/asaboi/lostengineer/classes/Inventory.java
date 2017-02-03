package ph.asaboi.lostengineer.classes;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.util.ArrayList;
import java.util.List;

import static com.wagnerandade.coollection.Coollection.*;

/**
 * Created by P004785 on 1/26/2017.
 */

public class Inventory {
    public ArrayList<Item> Items;

    public ArrayList<Label> Labels;

    private Table inventoryTable;
    private Table recipeTable;

    public Inventory() {
        InitializeItems();
    }

    private void InitializeItems() {
        Items = new ArrayList<Item>();
        DB.Init();
        Item item = DB.GetItemByCode("wood");
        Items.add(item);

        item = DB.GetItemByCode("iron-ore");
        Items.add(item);

        item = DB.GetItemByCode("copper-ore");
        Items.add(item);

        item = DB.GetItemByCode("coal");
        Items.add(item);

        item = DB.GetItemByCode("stone");
        Items.add(item);

        item = DB.GetItemByCode("iron-plate");
        Items.add(item);

        item = DB.GetItemByCode("stone-furnace");
        item.Quantity = 1;
        Items.add(item);

        item = new Item();
        item.Code = "burner-mining-drill";
        item.Quantity = 1;
        item.Name = "Burner Drill";
        Items.add(item);

        // Drills
        Machine ironDrill = new Machine(this);
        ironDrill.Code = "iron-drill";
        ironDrill.Quantity = 0;
        ironDrill.Name = "Iron Drill";

        Recipe recipe = new Recipe();
        recipe.Name = "Iron Ore";
        recipe.Code = "iron-ore-recipe";

        recipe.Inputs = new ArrayList<Item>();
        item = DB.GetItemByCode("coal");
        item.Quantity = 1;
        recipe.Inputs.add(item);

        recipe.Outputs = new ArrayList<Item>();
        Item ironOre = DB.GetItemByCode("iron-ore");
        ironOre.Quantity = 3;
        recipe.Outputs.add(ironOre);

        recipe.Speed = 300;
        ironDrill.Recipe = recipe;



        Machine coalDrill = new Machine(this);
        coalDrill.Code = "coal-drill";
        coalDrill.Quantity = 0;
        coalDrill.Name = "Coal Drill";

        recipe = new Recipe();
        recipe.Name = "Coal";
        recipe.Code = "coal-recipe";

        recipe.Inputs = new ArrayList<Item>();
        item = DB.GetItemByCode("coal");
        item.Quantity = 1;
        recipe.Inputs.add(item);

        recipe.Outputs = new ArrayList<Item>();
        item = DB.GetItemByCode("coal");
        item.Quantity = 5;
        recipe.Outputs.add(item);

        recipe.Speed = 300;
        coalDrill.Recipe = recipe;

        Machine stoneDrill = new Machine(this);
        stoneDrill.Code = "stone-drill";
        stoneDrill.Quantity = 0;
        stoneDrill.Name = "Stone Drill";

        recipe = new Recipe();
        recipe.Name = "Stone";
        recipe.Code = "stone-recipe";

        recipe.Inputs = new ArrayList<Item>();
        item = DB.GetItemByCode("coal");
        item.Quantity = 1;
        recipe.Inputs.add(item);

        recipe.Outputs = new ArrayList<Item>();
        Item stone = DB.GetItemByCode("stone");
        stone.Quantity = 5;
        recipe.Outputs.add(stone);

        recipe.Speed = 300;
        stoneDrill.Recipe = recipe;

        Items.add(ironDrill);
        Items.add(coalDrill);
        Items.add(stoneDrill);

        // Furnaces
        Machine ironFurnace = new Machine(this);
        ironFurnace.Code = "iron-furnace";
        ironFurnace.Quantity = 0;
        ironFurnace.Name = "Iron Furnace";

        recipe = new Recipe();
        recipe.Name = "Iron Plate";
        recipe.Code = "iron-plate-recipe";

        recipe.Inputs = new ArrayList<Item>();
        item = DB.GetItemByCode("coal");
        item.Quantity = 1;
        recipe.Inputs.add(item);

        ironOre = DB.GetItemByCode("iron-ore");
        ironOre.Quantity = 1;
        recipe.Inputs.add(ironOre);

        recipe.Outputs = new ArrayList<Item>();
        Item ironPlate = DB.GetItemByCode("iron-plate");
        ironPlate.Quantity = 1;
        recipe.Outputs.add(ironPlate);

        recipe.Speed = 300;
        ironFurnace.Recipe = recipe;


        Items.add(ironFurnace);
    }


    public Table GetTable(Skin skin) {
        inventoryTable = new Table();
        Labels = new ArrayList<Label>();
        int ItemIndex = 0;
        for (Item item:
                Items) {
            Label lbl = new Label(item.Name + " : " +item.Quantity,skin);
            item.Index = ItemIndex;
            inventoryTable.add(lbl).left().pad(5);
            inventoryTable.row();

            Labels.add(ItemIndex,lbl);

            ItemIndex++;
        }

        return inventoryTable;
    }

    public void Update()
    {
        for (Item item:
                Items) {
            Labels.get(item.Index).setText(item.Name + " : " +item.Quantity);
        }

        List<Item> machines = from(Items).where("category",eq("Machine")).all();
        for (Item item :
                machines) {
            Machine machine = (Machine)item;
            for (int i = 0; i < machine.Quantity; i++) {

                machine.Update();
            }
        }
    }

    public void Add(ArrayList<Item> addItems) {
        for (Item inputItem :
                addItems) {
            Item exists = from(Items).where("code", eq(inputItem.Code)).first();
            if(exists != null)
            {
                exists.Quantity += inputItem.Quantity;
            }
        }
    }

    public void Remove(ArrayList<Item> delItems) {
        for (Item inputItem :
                delItems) {
            Item exists = from(Items).where("code", eq(inputItem.Code)).first();
            if(exists != null)
            {
                exists.Quantity -= inputItem.Quantity;
            }
        }
    }

    public boolean Has(ArrayList<Item> inputs) {
        boolean hasItems = true;

        for (Item inputItem :
                inputs) {
            Item exists = from(Items).where("code", eq(inputItem.Code)).first();
            if(exists != null)
            {
                if(exists.Quantity < inputItem.Quantity)
                {
                    hasItems = false;

                }
            }else {
                hasItems = false;

                break;
            }
        }

        return hasItems;
    }

    public int GetQuantity(String itemCode)
    {
        Item exists = from(Items).where("code",eq(itemCode)).first();
        if(exists != null)
        {
            return exists.Quantity;
        }
        return  0;
    }

    public boolean Has(String itemCode, int qty) {
        boolean hasItems = true;

        Item exists = from(Items).where("code", eq(itemCode)).first();
        if(exists != null)
        {
            if(exists.Quantity < qty)
            {
                hasItems = false;
            }
        }else {
            hasItems = false;

        }

        return  hasItems;
    }

    public void Add(String Code, int qty){
        Item item = from(Items).where("code", eq(Code)).first();
        if(item != null)
        {
            item.Quantity += qty;
        }
    }

    public void Remove(String itemCode, int qty) {
        Item item = from(Items).where("code", eq(itemCode)).first();
        if(item != null)
        {
            item.Quantity -= qty;
        }
    }
}
