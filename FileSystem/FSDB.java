package FileSystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class FSDB implements Serializable{
    private static FSDB FSDBinstance = new FSDB();
    private static final String basepath = new File("").getAbsolutePath();
    private static final String FILENAME = basepath.concat("/FileSystem/DB/FSDB.ser");
    Map<String, FileSystem> users = new HashMap<>();

    static {
        ressuruct();
    }

    public static FileSystem getTempInstance() {
        return new FileSystem("default");
    }

    public FileSystem getUserInstance(String username) {
        if (users.containsKey(username)) {
            return users.get(username);
        } else {
            FileSystem temp = new FileSystem(username);
            users.put(username, temp);
            return users.get(username);
        }
    }

    private static void ressuruct() {
        FSDB temp = new FSDB();
        try {
            FileInputStream file = new FileInputStream(FILENAME);
            ObjectInputStream in = new ObjectInputStream(file);
            temp = (FSDB) in.readObject();
            file.close();
            in.close();
        }catch (Exception e) {
            System.err.println("FileSystem::FSDB::ressuruct " + e);
        }
        FSDBinstance = temp;
    }

    public void saveFSDB() {
        try {
            FileOutputStream file = new FileOutputStream(FILENAME);
            ObjectOutputStream out = new ObjectOutputStream(file);

            out.writeObject(FSDB.getFSDBInstance());
            out.flush();
            out.close();
            file.close();
            // System.err.println(this.getClass().getPackage() + "::" + this.getClass() + "::" + this.getClass().getEnclosingMethod());

        } catch (Exception e) {
            System.err.println(this.getClass().getPackage() + "::" 
            + this.getClass() + "::" + this.getClass().getEnclosingMethod() + e);
        }
    }

    public static FSDB getFSDBInstance() {
        return FSDBinstance;
    }
}
