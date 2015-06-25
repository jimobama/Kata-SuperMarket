/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.util.ArrayList;
import structures.ItemBasket;
import structures.ItemGroup;
import structures.ItemPromo;
import structures.SaleItem;
import structures.SaleItemGroup;
import structures.StockItem;
import views.IView;

/**
 *
 * @author Obaro
 */
abstract class AbstractExternalOperation   {
    
    KataSuperMarketController  __controller;
  
    protected void attach(KataSuperMarketController controller){
      __controller= controller;   
    }
    public void addStockItemEvent(StockItem item) {

        if (item != null) {

            if (item.validate()) {
                if (!this.__controller.getModel().addStockItem(item)) {
                    IView.report(this.__controller.getView().getOwner(), IView.WARNING, item.getError());
                    return;
                }

                IView.report(this.__controller.getView().getOwner(), IView.WARNING, "Item successfully added to stock list");
            } else {
                //report error back to the users
                IView.report(this.__controller.getView().getOwner(), IView.WARNING, item.getError());
            }

        }

    }

    public ArrayList<StockItem> getStockList() {
        return this.__controller.getModel().getStockArrayList();
    }

    public void removeStockItemEvent(StockItem item) {
        if (item != null) {
            if (!this.__controller.getModel().removeStockItem(item)) {
                IView.report(this.__controller.getView().getOwner(), IView.ERROR, this.__controller.getModel().getError());
                return;
            }
            IView.report(this.__controller.getView().getOwner(), IView.INFORMATION, "Item with reference number [" + item.getStockId() + "] has be remove from stock list");
        }
    }

    //Private in case multiply thread try to access it
    public synchronized StockItem getStockById(String stockID) {

        //Console.Write("Stock Id "+stockID);
        if (this.__controller.getModel().isStockIdExist(stockID)) {
            StockItem item = this.__controller.getModel().getStockItem(stockID);
            return item;
        }

        return null;

    }

    //This method is callled with inside views or model , which means concurrents can happen here
    public synchronized boolean addSaleItemEvent(SaleItem item) {
      
        boolean isokay = false;
        if (item.verify()) {
            if(this.__controller.getModel().isSaleItemAlreadyExist(item)) {
                IView.report(this.__controller.getView().getOwner(), 2, "The item with the stock number [ " + item.getStockID() + " ] already exist");
             }else{
                 isokay =  this.addSaleItemNow(item);
              }
                  
            }else{
              IView.report(this.__controller.getView().getOwner(), 1, item.getError());
        }

        return isokay;
}
    
    

    private synchronized boolean  addSaleItemNow(SaleItem item) {
            boolean  isokay = this.__controller.getModel().addSaleItem(item);
                if (isokay) {
                    isokay=true;
                    IView.report(this.__controller.getView().getOwner(), 1, "Item as be successfully added to sale list");
                } else {
                    // this should not happen
                    IView.report(this.__controller.getView().getOwner(), 2,"Sales Error "+ this.__controller.getModel().getError());
                } 
                
                return isokay;

    }

    public ArrayList<SaleItem> getSaleItemList(){
       if( this.__controller!=null){
        return this.__controller.getModel().getSaleListArray();
       }
       return null;
       
    }

    public ItemGroup getItemGroupById(String groupid) {
        
        ItemGroup itemgroup=null;
           if(this.__controller.getModel().isItemGroupIDExists(groupid))
           {
               itemgroup= this.__controller.getModel().getItemGroupById(groupid);
           }
         return itemgroup;
      }

    public void onCreateItemGroup(ItemGroup item) {
        
        if(this.__controller.getModel().isItemGroupIDExists(item.getSaleProGroupID()))
        {//should not happen
            IView.report(this.__controller.getView().getOwner(), 1,"The group already exist");
        }else{
            boolean okay=this.__controller.getModel().createItemGroup(item);
            if(okay)
            {
                 IView.report(this.__controller.getView().getOwner(), 0,"Item Group successfully created");
            }else{
                IView.report(this.__controller.getView().getOwner(), 2,this.__controller.getModel().getError()); 
            }
            
        }
    }

    public ArrayList<ItemGroup> getItemGroupList() {
    return this.__controller.getModel().getItemGroupList();
    }

    public boolean addSaleItemOnGroupEvent(String itemRef, String groupRef)
    {
        boolean okay= false;
       
        if(this.__controller.getModel().addItemIntoGroup(itemRef,groupRef))
        {
            IView.report(this.__controller.getView().getOwner(), 0,"Item has be successfully added into the give group"); 
            okay=true;
        }else{
            IView.report(this.__controller.getView().getOwner(), 2, this.__controller.getModel().getError());
        }
        return okay;
    }

    public SaleItem getSaleItemById(String id) {
        
        return this.__controller.getModel().getSaleItemById(id);
      }

    public boolean isUpdateItemGroup(ItemGroup __itemGroup) {
       return this.__controller.getModel().isUpdateItemGroup(__itemGroup);
    }

    public void addBasketItem(ItemBasket item) {
        if(this.__controller.getModel().isBasketItemUpdate(item)){
            IView.report(this.__controller.getView().getOwner(),0,"The Basket Item as be successfully added and updated");
        }else{
             IView.report(this.__controller.getView().getOwner(),2,this.__controller.getModel().getError());
        }
     }

    public int getBasketCount() {
    return this.__controller.getModel().getBasketCount();
    }

    public ArrayList<ItemBasket> getItemBasket() {
        
        return this.__controller.getModel().getItemBasket();
     }

    public boolean isItemOnPromotion(String saleID) {
        
        return this.__controller.getModel().isItemOnPromotion(saleID);
      }

    public String getGroupIdBySaleItemId(String saleItemId) {
        
        return this.__controller.getModel().getGroupIdBySaleItemID(saleItemId);
      }

    public boolean isDeleteStockById(String stockID) {
        return this.__controller.getModel().isStockItemDeletedByID(stockID);
    }

    public boolean isSaleItemDeletedById(String itemRef) {
        
        return this.__controller.getModel().isSaleItemDeletedById(itemRef);
       }

    public void addBasketItemToGroup(String groupID, ItemBasket itemB) {
        
        this.__controller.getModel().addBasketItemIntoGroup(groupID,itemB);
       
     }

    public ArrayList<SaleItemGroup> getItemSaleGroups() {
        
        return this.__controller.getModel().getItemSaleGroups();
       }

    public void clearSaleItemGroups() {
        this.__controller.getModel().clearItemSaleGroup();
     }

    public ItemPromo getPromotionByItemId(String itemId) {
      return this.__controller.getModel().getPromotionByItemId(itemId);
    }
    
    
}
