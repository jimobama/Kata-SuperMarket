/*
 This class will manager item to that can sell , help to set the sales prices  and maintain information about the items and
 sales groups
 */
package models;

import structures.IError;
import structures.SaleItem;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Obaro
 */
public class SaleManager extends IError implements InterfaceModel<SaleItem> {

    private static ArrayList<SaleItem> __itemlist = new ArrayList<>();
   private final Database file;
    private boolean __isDone=false;
  
    SaleManager(ArrayList<SaleItem> list) {
        if (list != null) {
            SaleManager.__itemlist = list;
        }
        file= new Database("saleList.txt");
        this.load();
    }
   private void load(){
         if (__itemlist == null) {
            __itemlist= new ArrayList<>();
        }
        __itemlist = (ArrayList<SaleItem>) file.GetObject();
        if (__itemlist == null) {
           __itemlist = new ArrayList<>();
        }
    }
   boolean isDone(){
       return __isDone;
   }
    @Override
    public void add(SaleItem t) {
        __isDone=false;
        if (!this.isExist(t)) {
            SaleManager.__itemlist.add(t);
            file.SaveObject(__itemlist);
           
            if(this.file.IsSave()){
            __isDone=true;
            }else{
               this.setError("Couldnt save sale item into DB , please report this error");
            }
        }
    }

    @Override
    public void remove(SaleItem item) {
        this.__isDone=false;
        if (this.isExist(item)) {
            for (Iterator<SaleItem> it = SaleManager.__itemlist.iterator(); it.hasNext();) {
                SaleItem item_t = it.next();
                if (item_t.isEqual(item)) {
                    it.remove();
                    file.SaveObject(__itemlist);
                    if(this.file.IsSave()){
                    __isDone=true;
                    }else{
                        this.setError("Couldnt delete sale item from  DB , please report this error");
                    }
                    
                    break;
                }
            }
        }
    }

    @Override
    public boolean isExist(SaleItem t) {

        boolean okay =false;

        if (t != null) {

            for (Iterator<SaleItem> it = SaleManager.__itemlist.iterator(); it.hasNext();) {
                SaleItem item_t = it.next();
                if (item_t.isEqual(t)) {
                    okay = true;
                    break;
                }
            }

        }else{
            okay=true;
        }

        return okay;
    }

    @Override
    public SaleItem getItemById(String id) {

        SaleItem item = new SaleItem(id);
        if (this.isExist(item)) {
            for (SaleItem item_t : SaleManager.__itemlist) {
                if (item_t.isEqual(item)) {
                    item = item_t;
                    break;
                }
            }
        } else {
            item = null;
        }

        return item;

    }

    public static SaleItem GetItemByID(String id) {
        SaleManager m = new SaleManager(null);
        return m.getItemById(id);
    }

  ArrayList<SaleItem>  getList() {
        return __itemlist;
      
   }

    boolean isStockItemExist(SaleItem stockItem) {
       
        boolean okay =false;

        if (stockItem != null) {
           
            for (SaleItem item_t : SaleManager.__itemlist) {
                if (item_t.getStockID().trim().equalsIgnoreCase(stockItem.getStockID().trim())) 
                {
                    okay = true;
                    break;
                }
            }

        }

        return okay; 
        
    }
    
   

}
