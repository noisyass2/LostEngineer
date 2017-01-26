package ph.asaboi.lostengineer.classes;

/**
 * Created by P004785 on 1/26/2017.
 */

public class Global {
    public Inventory Inventory;

    public Global() {
        Inventory = new Inventory();
    }

    public void GainWood(int i) {
        Inventory.Add("Wood",i);
        System.out.println("Added wood");

    }

    public void GainOre(String ore, int i) {
        System.out.println("Added " + i + " " + ore);
    }
}
