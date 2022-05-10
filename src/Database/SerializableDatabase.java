package Database;

import java.io.*;
import java.util.ArrayList;

public class SerializableDatabase implements Serializable {
    static SerializableDatabase instance;

    private SerializableDatabase() {}

    public static SerializableDatabase getInstance() {
        if(instance == null)
            instance = new SerializableDatabase();
        return instance;
    }
    public static void saveInstance(String filepath) throws IOException {
        try(FileOutputStream file = new FileOutputStream(filepath)) {
            ObjectOutputStream objStream = new ObjectOutputStream(file);

            objStream.writeObject(getInstance());
        }
    }
    public static void loadInstance(String filepath) throws IOException {
        try(FileInputStream file = new FileInputStream(filepath)) {
            ObjectInputStream objStream = new ObjectInputStream(file);

            try{
                Object obj = objStream.readObject();
                instance = (SerializableDatabase) obj;
            } catch(ClassNotFoundException | ClassCastException e) {
                System.out.println("failed to load database :(");
            }
        }
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
