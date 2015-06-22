/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import structures.SaleItem;
import structures.StockItem;
import controllers.InterfaceController;
import controllers.KataSuperMarketController;
import java.util.ArrayList;
import structures.ItemGroup;
import views.Console;

/**
 *
 * @author Obaro
 */
public class KataSuperMarketModel extends IModel {

    private KataSuperMarketController __controller;
    private final StockManager __stockManager;
    private final SaleManager __saleManager;
    private final ItemGroupManager __itemGroupManager;

    public KataSuperMarketModel() {
        this.__stockManager = new StockManager(null);
        this.__saleManager = new SaleManager(null);
        __itemGroupManager= new  ItemGroupManager();
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
        
        return this.__itemGroupManager.getItemGroupList();
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

}
