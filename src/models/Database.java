/*
The file contain a Database class that can be used to stream Java object into normal file and retrieved the objects from the file
 */
package models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Obaro
 */
class Database {

    private String filename;
    private boolean _save;

    //The parameterised controller for the Database that takes a filenanme and write to it
    Database(String FileName) {
        filename = FileName;
    }

 

    //The method check if a database saveObject operation successfully executes
    boolean IsSave() {

        return _save;
    }

    void SaveObject(Object journalists) {
        _save = false;
        File file = new File(this.filename);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        try {
            FileOutputStream fop = new FileOutputStream(file);
            try {
                ObjectOutputStream out = new ObjectOutputStream(fop);
                out.writeObject(journalists);
                out.close();
                fop.close();
                _save = true;
            } catch (IOException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //The method fetch all the object from the file
    Object GetObject() {
        File file = new File(this.filename);
        Object objectStream = null;
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        try {
            FileInputStream fop = new FileInputStream(file);
            try {
                ObjectInputStream in = new ObjectInputStream(fop);
                objectStream = (Object) in.readObject();
                in.close();
                fop.close();

            } catch (IOException ex) {
                //Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

        return objectStream;
    }

}
