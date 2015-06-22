/*
 The class is group of items that a particular promo discount can be able to

 */
package structures;

import java.util.ArrayList;
import java.util.Iterator;
import services.Helper;
import models.InterfaceModel;
import models.SaleManager;

/**
 *
 * @author Obaro
 */
public class ItemGroup extends IError implements InterfaceModel<SaleItem>,java.io.Serializable {

    private ArrayList<String> __saleItemIDs;
    private String __saleProGroupID;
    private boolean __promoStatus;
    private String __descPromo;
    private String __groupname;

    
   public  ItemGroup() {
        init();

    }
   public ItemGroup(String groupid, ArrayList<String> ids) {
        init(ids, groupid);

    }

   public  ItemGroup(String groupid) {
        init(groupid);

    }

    ItemGroup(ArrayList<String> ids) {
        init(ids);

    }

   

    private void init(String id) {
        __descPromo = "";
        __groupname ="";
        __saleProGroupID =id;
        this.__promoStatus=false;
        this.__saleItemIDs = new ArrayList<>();
       
    }
      private void init() {
        __descPromo = "";
        __groupname ="";
        __saleProGroupID =Helper.generateId(5);
        this.__promoStatus=false;
        this.__saleItemIDs = new ArrayList<>();
       
    }

    private void init(ArrayList<String> ids) {
        init("");
        if (ids == null) {

            ids = new ArrayList<>();
        }
        this.__saleItemIDs = ids;
    }

    private void init(ArrayList<String> ids, String groupname) {
        init(groupname);
        if (ids == null) {

            ids = new ArrayList<>();
        }
        this.__saleItemIDs = ids;
    }

    @Override
    public void add(SaleItem item) {
        if (!this.isExist(item)) {
            //add the item
            this.__saleItemIDs.add(item.getSaleID());

        }

    }

    public void remove(SaleItem item) {
        if (this.isExist(item)) {
            for (Iterator<String> it = this.__saleItemIDs.iterator(); it.hasNext();) {
                String id = it.next();
                if (id.equalsIgnoreCase(item.getSaleID())) {
                    it.remove();
                }
            }
        }
    }

    public boolean isExist(SaleItem item) {
        boolean okay = false;
        if (item != null) {
            for (Iterator<String> it = this.__saleItemIDs.iterator(); it.hasNext();) {
                if (it.next().equalsIgnoreCase(item.getSaleID())) {
                    okay = true;
                    break;
                }
            }
        }

        return okay;
    }
   
    public ArrayList<String> getSaleItemIDs() {
        return __saleItemIDs;
    }

    public void setSaleItemIDs(ArrayList<String> __saleItemIDs) {
        this.__saleItemIDs = __saleItemIDs;
    }

    public String getDescPromo() {
        return __descPromo;
    }

    public void setDescPromo(String __descPromo) {
        this.__descPromo = __descPromo;
    }

    public String getGroupname() {
        return __groupname;
    }

    public void setGroupname(String __groupname) {
        this.__groupname = __groupname;
    }

    public String getSaleProGroupID() {
        return __saleProGroupID;
    }

    @Override
    public SaleItem getItemById(String id) {
        SaleItem item_t = new SaleItem(id);

        item_t = SaleManager.GetItemByID(id);
        return item_t;
    }

    public boolean isPromoStatus() {
        return __promoStatus;
    }

    public void setPromoStatus(boolean __promoStatus) {
        this.__promoStatus = __promoStatus;
    }

    public boolean validated() {
        boolean okay=false;
        
        if(this.__saleProGroupID.trim().equalsIgnoreCase("")){
            this.setError("Enter a valid group id");
        }else if(!Helper.isNamingFormat(__groupname)){
            this.setError("Enter a valid name for the group");
        }else{
            okay=true;
        }
       
        
        return okay;
        
      }
    
   public  int getSize(){
        if(__saleItemIDs !=null){
        return __saleItemIDs.size();
        }
        return 0;
    }

    public ArrayList<String> getList() {
        return  __saleItemIDs;
      }

}
