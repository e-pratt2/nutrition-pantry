package Database;

import java.io.Serializable;
import java.util.ArrayList;

public class SerializableDatabase implements Serializable {
    ArrayList<Store> stores;
    ArrayList<Grocery> groceries;

    public void addGrocery(Grocery g) {
        this.groceries.add(g);
    }
    public void addStore(Store s) {
        this.stores.add(s);
    }
    public void addReceipt(Receipt r, Store s)  {
        s.addReceipt(r);
    }


}
