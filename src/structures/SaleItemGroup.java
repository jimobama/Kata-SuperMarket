/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package structures;

import java.util.ArrayList;
import java.util.Iterator;
import models.InterfaceModel;



/**
 *
 * @author Obaro
 */
public class SaleItemGroup extends IError implements InterfaceModel<SaleItemPromotion> {
   private ArrayList<SaleItemPromotion> __basketItems;
   private ItemPromo __promo;
   private boolean __isDone=false;
    private String groupId;
    
    
      public SaleItemGroup(String groupID){
          groupId=groupID;         
          __promo=null;          
       __basketItems= new ArrayList<>();
      }
      
       public SaleItemGroup(String groupID,ItemPromo promo ){
           groupId="";
          this.setGroupId(groupId);
          __promo=promo;
          
       __basketItems= new ArrayList<>();
      }
      
      

   @Override
    public ArrayList<SaleItemPromotion> getList() {
        return __basketItems;
    }

    public boolean isDone() {
        return __isDone;
    }
  

   
 
    @Override
    public void add(SaleItemPromotion t) {
        
        this.__isDone=false;
         if( t != null && !this.isExist(t)){
             this.__basketItems.add(t);      
            
             this.__isDone=true;
         }else{
             this.setError("Already exists");
         }
     }

    @Override
    public void remove(SaleItemPromotion t) {
        this.__isDone=false;
        if(this.isExist(t))
        {
            for(Iterator<SaleItemPromotion> it = this.__basketItems.iterator(); it.hasNext();)
            {
                SaleItemPromotion temp = it.next();
                if(t.getGroupID().equalsIgnoreCase(this.getGroupId()) && temp.getItemId().equalsIgnoreCase(t.getItemId()))
                {
                    it.remove();
                    this.__isDone=true;
                    break;
                }
                    
            }
        }
     }

    @Override
    public boolean isExist(SaleItemPromotion t) 
     { boolean okay=false;
         if(t !=null){
             
            for(Iterator<SaleItemPromotion> it = this.__basketItems.iterator(); it.hasNext();)
            {
                SaleItemPromotion temp = it.next();
                if(this.getGroupId().equalsIgnoreCase(t.getGroupID()) && temp.getItemId().equalsIgnoreCase(t.getItemId()))
                {
                    okay = true;
                    break;
                }
                    
            }
         }
         return okay;
     }

    @Override
    public SaleItemPromotion getItemById(String id) {
        SaleItemPromotion result=null;
        SaleItemPromotion t= new SaleItemPromotion(id);
       for (SaleItemPromotion temp : this.__basketItems) {
           if(this.getGroupId().equalsIgnoreCase(t.getGroupID()) && temp.getItemId().equalsIgnoreCase(t.getItemId()))
           {
               result =temp;
               break;
           }
       }
        
        return result;
    }
   
   
    //this will be able to process the promotion on the items , this items can have variable prices 
    
    public ArrayList<SaleItemPromotion> applyPromotion(int processPayamentOn){        
       this.__basketItems= this.__promo.process(this.__basketItems, processPayamentOn);
       return this.__basketItems;
    }

    public String getGroupId() {
        return groupId;
    }

    public final void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public void setPromo(ItemPromo __promo) {
        this.__promo = __promo;
    }
     public ItemPromo getPromo() {
       return this.__promo;
    }


   @Override
    public void update(SaleItemPromotion sp) {
        if(this.isExist(sp)){
            
            for(int i=0; i < this.__basketItems.size(); i++)
            {
               SaleItemPromotion itemTem= this.__basketItems.get(i);
               if(itemTem.getItemId().equalsIgnoreCase(sp.getItemId())){
                    this.__basketItems.set(i, sp);
               }
            }
        }
        
    }

  
   

  
 
}
