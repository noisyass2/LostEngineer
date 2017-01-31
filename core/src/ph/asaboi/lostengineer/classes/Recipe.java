package ph.asaboi.lostengineer.classes;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by P004785 on 1/31/2017.
 */

public class Recipe {
    public String Name;
    public ArrayList<Item> Inputs;
    public ArrayList<Item> Outputs;

    public String Code;

    public int Index;
    public String code()
    {
        return Code;
    }
}
