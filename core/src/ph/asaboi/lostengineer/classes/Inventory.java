package ph.asaboi.lostengineer.classes;

import java.util.ArrayList;
import static com.wagnerandade.coollection.Coollection.*;

/**
 * Created by P004785 on 1/26/2017.
 */

public class Inventory {
    public ArrayList<Item> Items;

    public Inventory() {
        Items = new ArrayList<Item>();
        Item item = new Item();
        item.Code = "Wood";
        item.Quantity = 0;
        item.Name = "Raw Wood";
        Items.add(item);
    }

    public void Add(String Code, int qty){
        Item item = from(Items).where("code", eq(Code)).first();
        if(item != null)
        {
            item.Quantity += qty;
        }
    }

}
