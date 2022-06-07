package Database;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * a database class that allows to save and load information to and from a file
 */
public class SerializableDatabase implements Serializable {
    public static final long serialVersionUID = 1L;
    
    private static SerializableDatabase instance;

    private final ArrayList<Store> stores;
    private final ArrayList<Grocery> groceries;

    /**
     * Constructor creates a new SerializableDatabase object
     */
    private SerializableDatabase() {
        this.stores = new ArrayList<>();
        this.groceries = new ArrayList<>();

    }

    //All of the methods that use the instance variable use the singleton pattern
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

    /**Check whether an instance is currently open.
     * @return returns true if the SerializableDatabase has a currently open instance, retrieved by getInstance()
     */
    public static boolean hasInstance() {
        return instance != null;
    }

    /**
     * opens a file and object output stream and writes/save the instance to a file
     * @param filepath String that holds the filepath
     * @throws IOException if the file writing failed.
     */
    public static void saveInstance(String filepath) throws IOException {
        //tries to open a file output strean
        //if fails closes the file
        try(FileOutputStream file = new FileOutputStream(filepath)) {
            //does serialization
            ObjectOutputStream objStream = new ObjectOutputStream(file);
            //reads the objects into the object stream
            objStream.writeObject(getInstance());
        }
    }

    /**
     * opens a file and object input stream and retrieves the information saved to a file
     * @param filepath string that holds the filepath
     * @throws IOException if loading failed due to an IO error.
     */
    public static void loadInstance(String filepath) throws IOException {
        //trys to open a file input stream
        //if an exception is thrown it will close the file
        try(FileInputStream file = new FileInputStream(filepath)) {
            //does the deserialization
            ObjectInputStream objStream = new ObjectInputStream(file);

            try{
                //reads the objects from the object stream
                Object obj = objStream.readObject();
                instance = (SerializableDatabase) obj;
            //throws exception if reading from the object stream fails
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
