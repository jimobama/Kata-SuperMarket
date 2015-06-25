/*
The stock manager that take care of number of items in database and  cost informations of the item 
 */
package models;

import structures.IError;
import structures.StockItem;
import java.util.ArrayList;
import java.util.Iterator;


/**
 *
 * @author Obaro
 */
public class StockManager extends IError implements InterfaceModel<StockItem> {

    //list of items stocked
    private static ArrayList<StockItem> __stocks_list = new ArrayList<>();
    private boolean __isDone;
    private final Database file;

   public  StockManager(ArrayList<StockItem> _stocks) {
        file = new Database("stocks.txt");
        if (_stocks != null) {
            StockManager.__stocks_list = _stocks;

        }

        this.__isDone = false;
         this.load();
    }

    private void load() {
        if (__stocks_list == null) {
            __stocks_list = new ArrayList<>();
        }
        __stocks_list = (ArrayList<StockItem>) file.GetObject();
        if (__stocks_list == null) {
            __stocks_list = new ArrayList<>();
        }
    }

    @Override
    public void add(StockItem item) {
        this.__isDone = false;
        if (!this.isExist(item)) {
            StockManager.__stocks_list.add(item);
            file.SaveObject(__stocks_list);
            if (file.IsSave()) {
                this.__isDone = true;
            } else {
                this.setError("Could not save file");
            }
        }
    }

    // this method will remove the item from the list
    @Override
    public void remove(StockItem item) {
        this.__isDone = false;
        if (this.isExist(item)) {
            for (Iterator<StockItem> it = StockManager.__stocks_list.iterator(); it.hasNext();) {
                StockItem item_t = it.next();
                if (item_t.isEqual(item)) {
                    it.remove();
                    file.SaveObject(__stocks_list);
                    if (file.IsSave()) {
                        this.__isDone = true;
                        break;
                    } else {
                        this.setError("Could not remove from database");
                    }
                }
            }
        }
    }

    @Override
    public StockItem getItemById(String id) {
        StockItem item = new StockItem(id);
        if (this.isExist(item)) {
            for (StockItem item_t : StockManager.__stocks_list) {
                if (item_t.isEqual(item)) {
                    item = item_t;
                    break;
                }
            }
        } else {
            item = null;
            this.setError("Item will the given id [" + id + "] donot exists");
        }
        return item;
    }
//Check if the Item exist in the stock list

    @Override
    public boolean isExist(StockItem item) {
        boolean okay = false;
       
   
        for (StockItem item_t : StockManager.__stocks_list) {
            if (item_t.isEqual(item)) {
                okay = true;
                break;
            }
        }
        return okay;
    }

    //static class object starting with capital letter
   public  static StockItem GetItemById(String id) {
        StockManager m = new StockManager(null);
        return m.getItemById(id);
    }
    boolean isDone() {

        return __isDone;
    }

    ArrayList<StockItem> getList() {
       
        return StockManager.__stocks_list;
    }

  

}
