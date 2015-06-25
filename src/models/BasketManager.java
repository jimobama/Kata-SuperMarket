/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.ArrayList;
import java.util.Iterator;
import structures.IError;
import structures.ItemBasket;
import views.Console;

/**
 *
 * @author Obaro
 */
public class BasketManager extends IError implements InterfaceModel<ItemBasket>{

    private boolean __isDone=false;
   private   ArrayList<ItemBasket> __list;
    public  BasketManager(){
         __isDone=false;
         __list= new  ArrayList<>();
    }
   

    void update(ItemBasket item, int number) {
        this.__isDone=false;
      
        if(this.isExist(item)){
     
            ItemBasket tempItem = this.getItem(item);
               
            if(tempItem !=null){
               Console.WriteLn("Item Number = "+  tempItem.getCurrentPrices()); 
               tempItem.setNoOfItems(tempItem.getNoOfItems()+ number);               
                for(int i=0; i < this.__list.size(); i++)
                    {
                        ItemBasket item2temp = this.__list.get(i);
                        if(tempItem.getItemId().equalsIgnoreCase(item2temp.getItemId())){
                           this.__list.set(i, tempItem);
                           this.__isDone=true;
                           break;
                        }
                    }
               
            }
        }
        
       }

    boolean isDone() {
        return this.__isDone;
    }

    @Override
    public void add(ItemBasket t) {
         this.__isDone=false;
        if(!this.isExist(t)){
            this.__list.add(t);
            this.__isDone=true;
        } else{
            this.setError("Could not added item because its already exists");
        }
      }

    @Override
    public void remove(ItemBasket t) {
          this.__isDone=false;
        if(this.isExist(t))
        {
            for(Iterator<ItemBasket> it= this.__list.iterator(); it.hasNext();)
            {
                ItemBasket item = it.next();
                if(item.getItemId().equalsIgnoreCase(t.getItemId())){
                    it.remove();
                    this.__isDone=true;
                }
            }
        }else{
            this.setError("Could not remove item because it does not exists");  
        }
    }

    //The item exists if they are both same prices
    @Override
    public boolean isExist(ItemBasket t) {
         
        if(this.__list !=null){
            
            
            for (ItemBasket item : this.__list) {
                if(item.getItemId().equalsIgnoreCase(t.getItemId())
                        && item.getCurrentPrices()== t.getCurrentPrices()){
                   
                    return true;
                }
            }
        }
        
        return false;
     }

    @Override
    public ItemBasket getItemById(String id) {
        ItemBasket item=  new ItemBasket();
        item.setItemId(id);
        
        if(this.isExist(item))
        {
            for (ItemBasket tempItem : this.__list) {
                if(item.getItemId().equalsIgnoreCase(tempItem.getItemId())){
                    return  tempItem ;
                }
            }
            
        }
        return null;
     }
    
     public ItemBasket getItem(ItemBasket item) {
      
        
        if(this.isExist(item))
        {
            for (ItemBasket tempItem : this.__list) {
                if(item.getItemId().equalsIgnoreCase(tempItem.getItemId()) && item.getCurrentPrices() == tempItem.getCurrentPrices()){
                    return  tempItem ;
                }
            }
            
        }
        return null;
     }

    int size() {
        
        if(this.__list !=null){
            return this.__list.size();
        }else{
            return 0;
        }
     }

    @Override
    public ArrayList<ItemBasket> getList() {
        if( this.__list ==null){
             this.__list = new ArrayList<>();   
        }
        return this.__list ;
       }

    @Override
    public void update(ItemBasket t) {
        
    }
    
}
