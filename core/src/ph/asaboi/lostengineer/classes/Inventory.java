package ph.asaboi.lostengineer.classes;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.util.ArrayList;
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
        Item item = new Item();
        item.Code = "Wood";
        item.Quantity = 0;
        item.Name = "Raw Wood";
        Items.add(item);

        item = new Item();
        item.Code = "IronOre";
        item.Quantity = 0;
        item.Name = "Iron Ore";
        Items.add(item);

        item = new Item();
        item.Code = "CopperOre";
        item.Quantity = 0;
        item.Name = "Copper Ore";
        Items.add(item);

        item = new Item();
        item.Code = "Coal";
        item.Quantity = 0;
        item.Name = "Coal";
        Items.add(item);

        item = new Item();
        item.Code = "Stone";
        item.Quantity = 0;
        item.Name = "Raw Stone";
        Items.add(item);

        item = new Item();
        item.Code = "stone-furnace";
        item.Quantity = 0;
        item.Name = "Stone Furnace";
        Items.add(item);



    }

    public void Add(String Code, int qty){
        Item item = from(Items).where("code", eq(Code)).first();
        if(item != null)
        {
            item.Quantity += qty;
        }
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
}
