/*

 The class is the main controller of the Kata Super market and its may Job is to help coordinate events or activities of users interaction and
 carryout the business logic sending and data to model to process and retrieved then, send then back to the view for to display to users.

 */
package controllers;

import java.util.ArrayList;
import models.KataSuperMarketModel;
import views.KataSuperMarketView;
import models.IModel;
import structures.ItemGroup;
import structures.SaleItem;
import structures.StockItem;
import views.IView;

/**
 *
 * @author Obaro
 */
public class KataSuperMarketController implements InterfaceController {

    //create the model inner view and model, the suffix __ means its a private attributes identicator (just a style of coding)
    private KataSuperMarketModel __model;
    private KataSuperMarketView __view;

    public KataSuperMarketController(KataSuperMarketModel model, KataSuperMarketView view) {

        //initial the privite fields here
        this.__model = model;
        this.__view = view;
        this.__model.attach(this);
        this.__view.attach(this);

    }

    //add the items into stocks(stock_items)
    @Override
    public void run() {
        //check if the model and view are not null values
        if (this.__model != null && this.__view != null) {
            //language the view for the user

            this.__view.setModel(__model);
            this.__view.show();
        }

    }

    @Override
    public IModel getModel() {
        return this.__model;
    }

    @Override
    public IView getView() {
        return this.__view;
    }

    //This method is called within the IView class
    public void addStockItemEvent(StockItem item) {

        if (item != null) {

            if (item.validate()) {
                if (!this.__model.addStockItem(item)) {
                    IView.report(this.__view.getOwner(), IView.WARNING, item.getError());
                    return;
                }

                IView.report(this.__view.getOwner(), IView.WARNING, "Item successfully added to stock list");
            } else {
                //report error back to the users
                IView.report(this.__view.getOwner(), IView.WARNING, item.getError());
            }

        }

    }

    public ArrayList<StockItem> getStockList() {
        return this.__model.getStockArrayList();
    }

    public void removeStockItemEvent(StockItem item) {
        if (item != null) {
            if (!this.__model.removeStockItem(item)) {
                IView.report(this.__view.getOwner(), IView.ERROR, this.__model.getError());
                return;
            }
            IView.report(this.__view.getOwner(), IView.INFORMATION, "Item with reference number [" + item.getStockId() + "] has be remove from stock list");
        }
    }

    //Private in case multiply thread try to access it
    public synchronized StockItem getStockById(String stockID) {

        //Console.Write("Stock Id "+stockID);
        if (this.__model.isStockIdExist(stockID)) {
            StockItem item = this.__model.getStockItem(stockID);
            return item;
        }

        return null;

    }

    //This method is callled with inside views or model , which means concurrents can happen here
    public synchronized boolean addSaleItemEvent(SaleItem item) {
      
        boolean isokay = false;
        if (item.verify()) {
            if(this.__model.isSaleItemAlreadyExist(item)) {
                IView.report(this.__view.getOwner(), 2, "The item with the stock number [ " + item.getStockID() + " ] already exist");
             }else{
                 isokay =  this.addSaleItemNow(item);
              }
                  
            }else{
              IView.report(this.__view.getOwner(), 1, item.getError());
        }

        return isokay;
}
    
    

    private synchronized boolean  addSaleItemNow(SaleItem item) {
            boolean  isokay = this.__model.addSaleItem(item);
                if (isokay) {
                    isokay=true;
                    IView.report(this.__view.getOwner(), 1, "Item as be successfully added to sale list");
                } else {
                    // this should not happen
                    IView.report(this.__view.getOwner(), 2,"Sales Error "+ this.__model.getError());
                } 
                
                return isokay;

    }

    public ArrayList<SaleItem> getSaleItemList() {
        return this.__model.getSaleListArray();
    }

    public ItemGroup getItemGroupById(String groupid) {
        
        ItemGroup itemgroup=null;
           if(this.__model.isItemGroupIDExists(groupid))
           {
               itemgroup= this.__model.getItemGroupById(groupid);
           }
         return itemgroup;
      }

    public void onCreateItemGroup(ItemGroup item) {
        
        if(this.__model.isItemGroupIDExists(item.getSaleProGroupID()))
        {//should not happen
            IView.report(this.__view.getOwner(), 1,"The group already exist");
        }else{
            boolean okay=this.__model.createItemGroup(item);
            if(okay)
            {
                 IView.report(this.__view.getOwner(), 0,"Item Group successfully created");
            }else{
                IView.report(this.__view.getOwner(), 2,this.__model.getError()); 
            }
            
        }
    }

    public ArrayList<ItemGroup> getItemGroupList() {
    return this.__model.getItemGroupList();
    }

    public boolean addSaleItemOnGroupEvent(String itemRef, String groupRef)
    {
        boolean okay= false;
       
        if(this.__model.addItemIntoGroup(itemRef,groupRef))
        {
            IView.report(this.__view.getOwner(), 0,"Item has be successfully added into the give group"); 
            okay=true;
        }else{
            IView.report(this.__view.getOwner(), 2, this.__model.getError());
        }
        return okay;
    }

    public SaleItem getSaleItemById(String id) {
        
        return this.__model.getSaleItemById(id);
      }

    public boolean isUpdateItemGroup(ItemGroup __itemGroup) {
       return this.__model.isUpdateItemGroup(__itemGroup);
    }

}
