package Database;

import java.io.Serializable;
import java.util.ArrayList;

public class SerializableDatabase implements Serializable {
    static SerializableDatabase instance;

    private SerializableDatabase() {}

    public static SerializableDatabase getInstance() {
        if(instance == null)
            instance = new SerializableDatabase();
        return instance;
    }

    ArrayList<Store> stores;
    ArrayList<Grocery> groceries;

    public void addGrocery(Grocery g) {

    }
    public void addStore(Store s) {

    }
    public void addReceipt(Receipt r, Store s)  {
        s.addReceipt(r);
    }
}
