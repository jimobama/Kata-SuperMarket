/*
This is a central model where all controller request most pass before going to the children models insided the main model
 */
package models;

import structures.SaleItem;
import structures.StockItem;
import controllers.InterfaceController;
import controllers.KataSuperMarketController;
import java.util.ArrayList;
import structures.ItemBasket;
import structures.ItemGroup;
import structures.ItemPromo;
import structures.SaleItemGroup;
import structures.SaleItemPromotion;


/**
 *
 * @author Obaro
 */
public class KataSuperMarketModel extends IModel {

    private KataSuperMarketController __controller;
    private final StockManager __stockManager;
    private final SaleManager __saleManager;
    private final ItemGroupManager __itemGroupManager;
    private final BasketManager __backetManager;
    private final SaleItemGroupManager __saleItemGroupManager;

    public KataSuperMarketModel() {
        this.__stockManager = new StockManager(null);
        this.__saleManager = new SaleManager(null);
        __itemGroupManager= new  ItemGroupManager();
        __backetManager= new BasketManager();
        __saleItemGroupManager= new SaleItemGroupManager();
    }

    @Override
    public void attach(InterfaceController controller) {
        __controller = (KataSuperMarketController) controller;
    }

    public boolean addStockItem(StockItem item) {
        boolean okay = true;

        if (item != null && item.validate()) {
            this.__stockManager.add(item);
            if (!this.__stockManager.isDone()) {
                okay = false;
                this.setError(this.__stockManager.getError());
            }
        }

        return okay;
    }

    public boolean removeStockItem(StockItem item) {
        boolean okay = true;
        if (item != null) {

            this.__stockManager.remove(item);
            if (!this.__stockManager.isDone()) {
                okay = false;
                this.setError(this.__stockManager.getError());
            }

        }
        return okay;
    }

    public ArrayList<StockItem> getStockArrayList() {
        return this.__stockManager.getList();
    }

    @Override
    public InterfaceController getController() {
        return this.__controller;
    }

    public boolean isStockIdExist(String stockID) {
        
       return this.__stockManager.isExist(new StockItem(stockID));
    }

    public StockItem getStockItem(String stockID) {
       StockItem item= this.__stockManager.getItemById(stockID);
       if(item.validate())
       {
           return item;
           
       }
       this.setError(this.__stockManager.getError());
       return null;
    }

    public boolean isSaleItemAlreadyExist(SaleItem item) {
       
        boolean okay=false;
         if(this.__saleManager.isStockItemExist(item)){
           okay= true;
         }
         return okay;
    }

    public boolean addSaleItem(SaleItem item) {
        boolean okay=false;
        this.__saleManager.add(item);
        if(this.__saleManager.isDone()){
            
            okay=true;
        } else{
            this.setError(this.__saleManager.getError());
        }        
        
        return okay;
     }

    public ArrayList<SaleItem> getSaleListArray() {
       return  this.__saleManager.getList();
    }

    public boolean isItemGroupIDExists(String groupid) 
    {
       return this.__itemGroupManager.isExist(new ItemGroup(groupid));
    }

    public ItemGroup getItemGroupById(String groupid) {
       
        return this.__itemGroupManager.getItemById(groupid);
    }

    public boolean createItemGroup(ItemGroup item) {
        boolean okay=false;
        
        this.__itemGroupManager.add(item);
        if(this.__itemGroupManager.isDone()){
            okay=true;
        }
        
        return okay;
        
     }

    public ArrayList<ItemGroup> getItemGroupList() {
        
        return this.__itemGroupManager.getList();
     }

    public boolean addItemIntoGroup(String itemRef, String groupRef) 
         {
             boolean  okay=false;
             SaleItem saleItem= new SaleItem(itemRef);
             if(this.__saleManager.isExist(saleItem))
             {
                 saleItem= this.__saleManager.getItemById(itemRef);
                 
                 if(this.__itemGroupManager.addSaleItem(groupRef,saleItem))
                 {
                     okay=true;
                 }else{
                     this.setError(this.__itemGroupManager.getError());
                 }
                 
                 
             }else{
                 this.setError("The sale item reference ["+itemRef+"] did not exist ");
             }
             
             return okay;
        
       }

    public SaleItem getSaleItemById(String id) {
        
        return this.__saleManager.getItemById(id);
     }

    public boolean isUpdateItemGroup(ItemGroup __itemGroup) {
         if(__itemGroup !=null){
             
             this.__itemGroupManager.update(__itemGroup);
             if(this.__itemGroupManager.isDone()){
                 return true;
             }
         }
         return false;
    }

    public boolean isBasketItemUpdate(ItemBasket item) {
       
        boolean okay=false;
        if(this.__backetManager.isExist(item))        {
           this.__backetManager.update(item,1); 
           if(this.__backetManager.isDone()){
               okay=true;
           }else{
               this.setError("Could not update item into basket");
           }
        }else{
             this.__backetManager.add(item);
             if(this.__backetManager.isDone()){
               okay=true;
           }else{
                 this.setError("Could not add item to basket");
             }
        }
        return okay;
     };

    public int getBasketCount() {
    return this.__backetManager.size();
    }

    public ArrayList<ItemBasket> getItemBasket() {
        return this.__backetManager.getList();
     }

    public boolean isItemOnPromotion(String saleID) {        
        return this.__itemGroupManager.isItemInGroupOnPromotion(saleID);
      }

    public String getGroupIdBySaleItemID(String saleItemId) {
        return this.__itemGroupManager.getGroupIdBySaleId(saleItemId);
     }

    public boolean isStockItemDeletedByID(String stockID) {
        this.__stockManager.remove(new StockItem(stockID));
        return this.__stockManager.isDone();
    }

    public boolean isSaleItemDeletedById(String itemRef) {
        this.__saleManager.remove(new SaleItem(itemRef));
        return this.__saleManager.isDone();
    }

    public void addBasketItemIntoGroup(String groupID, ItemBasket itemB) {
        SaleItemPromotion sp= new SaleItemPromotion(groupID,itemB);
        sp.setNoOfItems(itemB.getNoOfItems());
        
      //create a new cell item group
        SaleItemGroup group = new SaleItemGroup(groupID);
     
        if(!this.__saleItemGroupManager.isExist(group)){      
             ItemGroup itemGroup= this.__itemGroupManager.getItemById(group.getGroupId());
             if(itemGroup.hasPromotion()){
                 // Console.WriteLn("Group Item Promo Amount £" +(itemGroup.getPromotion().getPriceBase()));//debug
                  group.setPromo(itemGroup.getPromotion());
             }
             group.add(sp);
             
            if(group.isDone()){ 
              this.__saleItemGroupManager.add(group);
                 if(this.__saleItemGroupManager.isDone()){       
                     this.setError("Added Successfully");
               
                    }
              }
          
        }else{            
            //else if the group exists 
            group =  this.__saleItemGroupManager.getItemById(groupID);
            if(group !=null){
                if(!group.isExist(sp)){
                    group.add(sp);
                }else{
                    group.update(sp);
                }
               
                this.__saleItemGroupManager.update(group);
            }
            
        }
        
     }

    public ArrayList<SaleItemGroup> getItemSaleGroups() {
        return this.__saleItemGroupManager.getList();
    }

    public void clearItemSaleGroup() {
        this.__saleItemGroupManager.clear();
      }

    public ItemPromo getPromotionByItemId(String itemId) {
        
       return this.__itemGroupManager.getPromotionBySaleId(itemId);
    }
    

}
