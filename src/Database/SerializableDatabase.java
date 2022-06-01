package Database;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * a database class that allows to save and load information to and from a file
 */
public class SerializableDatabase implements Serializable {

    private static SerializableDatabase instance;

    private ArrayList<Store> stores;
    private ArrayList<Grocery> groceries;

    /**
     * Constructor creates a new SerializableDatabase object
     */
    private SerializableDatabase() {
        this.stores = new ArrayList<>();
        this.groceries = new ArrayList<>();

    }

    /**
     * this method crates SerializableDatabase object only once if its null
     * otherwise return the SerializableDatabase object
     * @return returns the SerializableDatabase object
     */
    public static SerializableDatabase getInstance() {
        if(instance == null)
            instance = new SerializableDatabase();
        return instance;
    }

    /**
     *
     * @return returns true if the SerializableDatabase object is not null
     * @return returns false if the SerializableDatabase object in null
     */
    public static boolean hasInstance() {
        return instance != null;
    }

    /**
     * opens a file and object output stream and writes/saes the instance to a file
     * @param filepath String that holds the filepath
     * @throws IOException
     */
    public static void saveInstance(String filepath) throws IOException {
        try(FileOutputStream file = new FileOutputStream(filepath)) {
            ObjectOutputStream objStream = new ObjectOutputStream(file);

            objStream.writeObject(getInstance());
        }
    }

    /**
     * opens a file and object input stream and retrieves the information saved to a file
     * @param filepath string that holds the filepath
     * @throws IOException
     */
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

    /**
     *
     * @return returns this SerializableDatabase objects list of groceries
     */
    public List<Grocery> getGroceries() {
        return groceries;
    }

    /**
     *
     * @return returns this SerializableDatabase objects list of stores
     */
    public List<Store> getStores() {
        return stores;
    }

    /**
     * adds the object passed in into the grocery list
     * @param g grocery object to be added
     */
    public void addGrocery(Grocery g) {
        this.groceries.add(g);
    }

    /**
     * adds the object passed in to the list of stores
     * @param s store object to add
     */
    public void addStore(Store s) {
        this.stores.add(s);
    }

    /**
     * adds the receipt object passed in into the store's receipt list
     * @param r receipt object to be added
     * @param s store object associated with the receipt
     */
    public void addReceipt(Receipt r, Store s)  {
        s.addReceipt(r);
    }
}
