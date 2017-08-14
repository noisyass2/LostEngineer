package ph.asaboi.lostengineer.classes;

import java.util.ArrayList;
import static com.wagnerandade.coollection.Coollection.*;

/**
 * Created by P004785 on 2/2/2017.
 */

public class DB {
    public static ArrayList<Item> Items;
    public static ArrayList<Machine> Machines;
    public static ArrayList<Recipe> Recipes;



    public static void Init()
    {
        Items = new ArrayList<Item>();
        String[] names = new String[] { "Raw Wood","Iron Ore" ,"Copper Ore","Coal","Stone","Iron Plate","Copper Plate","Stone Furnace","Burner Drill" };
        String[] codes = new String[] { "wood","iron-ore","copper-ore","coal","stone", "iron-plate","copper-plate","stone-furnace","burner-mining-drill" };

        for (int i = 0; i < names.length; i++) {
            Item item = new Item();
            item.Code = codes[i];
            item.Name = names[i];
            item.Index = i;
            item.Quantity = 1;
            Items.add(item);
        }

    }

    public static Item GetItemByCode(String code)
    {
        Item item = new Item();

        Item dbItem = from(Items).where("code", eq(code)).first();
        if(dbItem != null)
        {
            item.Name = dbItem.Name;
            item.Code = dbItem.Code;
            item.Index = dbItem.Index;
            item.Category = dbItem.Category;
            item.Quantity = 0;
        }

        return item;
    }
}
