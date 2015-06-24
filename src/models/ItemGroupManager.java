/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import structures.IError;
import structures.ItemGroup;
import java.util.ArrayList;
import java.util.Iterator;
import structures.SaleItem;

/**
 *
 * @author Obaro
 */
public class ItemGroupManager extends IError implements InterfaceModel<ItemGroup> {

    private static ArrayList<ItemGroup> __list;
    private final Database __db;
    private boolean __isDone=false;

    public  ItemGroupManager()
     {
       ItemGroupManager.__list= new ArrayList<>();
      this.__db= new Database("itemgroup.txt");
      this.load();
     }
    
    public boolean isDone(){
        return this.__isDone;
    }
    
    @Override
    public void add(ItemGroup t) {
         __isDone=false;

        if (!this.isExist(t)) {
            this.__isDone= ItemGroupManager.__list.add(t);
           
            this.__db.SaveObject(__list);
            
           
        } else {
            this.setError("the group name [" + t.getGroupname() + "] already exists");
        }
    }

    @Override
    public void remove(ItemGroup t) {
         this.__isDone=false;
        if (this.isExist(t)) {
            for (Iterator<ItemGroup> it = __list.iterator(); it.hasNext();) {
                if (it.next().getSaleProGroupID().equalsIgnoreCase(t.getSaleProGroupID())) {
                    it.remove();
                    this.__isDone=true;
                }
            }
        }
    }

    @Override
    public boolean isExist(ItemGroup t) {
        boolean isOkay =false;

        if (t != null) {
            for (Iterator<ItemGroup> it = __list.iterator(); it.hasNext();) {
                if (it.next().getSaleProGroupID().equalsIgnoreCase(t.getSaleProGroupID())) {
                    isOkay = true;
                }
            }
        }
        return isOkay;

    }

    @Override
    public ItemGroup getItemById(String id) {

        ItemGroup itemG = new ItemGroup(id);
       
        if (this.isExist(itemG)) {
            for (ItemGroup itemTemp : __list) {
                if (itemTemp.getSaleProGroupID().trim().equalsIgnoreCase(id.trim()))
                {
                    itemG=itemTemp;
                    break;
                }
            }
        }else{
            itemG=null;
        }
        return itemG;
    }

    private void load() {
       //load database into cache memory
        __list=(ArrayList<ItemGroup>) this.__db.GetObject();
        if(__list==null){
          __list= new  ArrayList<>();
        }
    }

    ArrayList<ItemGroup> getItemGroupList() {
        
        return __list;
     }

    boolean addSaleItem(String groupRef, SaleItem saleItem) {
        boolean okay=false;
         ItemGroup itemGroup= new  ItemGroup(groupRef);
        if(this.isExist(itemGroup)){
                 itemGroup= this.getItemById(groupRef);
                  if( itemGroup !=null){
                    if(!itemGroup.isExist(saleItem)){
                        itemGroup.add(saleItem);
                        this.__db.SaveObject(__list);                        
                        okay=true;
                      }else{
                         this.setError("The sale item already exists on the given group");
                     }
                 }
                     
        }else{
            this.setError("Group id did not exists");
        }
        
        
        return okay;
    }

    
     public void update(ItemGroup group){
         this.__isDone=false;
        if(group !=null){
          
            if(this.isExist(group)){
                
                 for (int i=0 ;i < __list.size(); i++)
                    {
                        ItemGroup itemTemp= __list.get(i);

                        if (itemTemp.getSaleProGroupID().trim().equalsIgnoreCase( group.getSaleProGroupID().trim()))
                        {
                          __list.set(i, group);
                           this.__db.SaveObject(__list);
                           this.__isDone=true;
                           break;
                        }
                   }
                 
            }
        }
     }

    boolean isItemInGroupOnPromotion(String saleID) {
        
        ItemGroup group = this.getItemGroupBySaleId(saleID);
        if(group !=null)
             return group.hasPromotion();
        return false;
     }

    private ItemGroup getItemGroupBySaleId(String saleID) {
        ItemGroup result=null;
        if(__list !=null){
            
            for(Iterator<ItemGroup> it= __list.iterator(); it.hasNext();){
                
                ItemGroup group = it.next();
                 if(group.isExist(new SaleItem(saleID)))
                 {
                    result=  group;
                    break;
                 }
            }
                
        }
        return result;
      }

    String getGroupIdBySaleId(String saleItemId) {
        ItemGroup group = getItemGroupBySaleId(saleItemId);
        if(group !=null){
            return group.getSaleProGroupID();
        }
        return null;        
    }
        
}
