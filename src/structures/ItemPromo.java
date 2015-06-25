/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package structures;

import java.util.ArrayList;

/**
 *
 * @author Obaro
 */
public class ItemPromo extends IError {

    private String __promoID;
    private int __forEachMulitpleNo;
    private int __type;    
    //This fields depends on the type of promo
    private double __priceBasePromo;    
    private double __percentage;
    private int __itemBase;
    private String __desc;
    public final static int REMOVE_MAXIMUM=0;
    public final static int REMOVE_MINIMUM=1;
    
    
   public ItemPromo(String text) {
        this.__promoID=text;
        this.__forEachMulitpleNo=1;
        __type =PERCENTAGE_BASE;
        this.__priceBasePromo=0;
        __percentage= 0.0;      
     
    }
    

    public double getPriceBasePromo() {
        return __priceBasePromo;
    }

    public void setPriceBasePromo(double __priceBasePromo) {
        this.__priceBasePromo = __priceBasePromo;
    }

    public String getDesc() {
        return __desc;
    }

    public void setDesc(String __desc) {
        this.__desc = __desc;
    }
    
    public static final int   PERCENTAGE_BASE=1, ITEM_BASE=2,PRICE_BASE=3;

 
     
    //This validate the ItemPromo
public boolean validated()
{
    boolean okay=false;
      if(this.__promoID.trim().isEmpty()){
          this.setError("Invalid promo identify");
      }else if(this.__forEachMulitpleNo <=0 ){
            this.setError("How many mulitple of items to apply promo?");
      }else if(this.__type !=PERCENTAGE_BASE && this.__type !=ITEM_BASE && this.__type !=PRICE_BASE ){
          this.setError("Select a valid promotion type !");
      }else{
          switch(__type){
              case PERCENTAGE_BASE:
                  if(this.__percentage <=0.0){
                      this.setError("Enter a percenter value for the givens items ");
                  }else{
                      okay=true;
                  }
                  break;
              case ITEM_BASE:
                  if(this.__itemBase <=0){
                      this.setError("Enter the number of item to pay for if promo condition is met");
                  }else{
                      okay=true;
                  }
                  break;
              case PRICE_BASE:
                  if(this.__priceBasePromo <=0){
                      this.setError("Enter a price of the items given please!");
                  }
                  else{
                      okay=true;
                  }
                  break;           
               
          }
      }
     
    return okay;
}
    public String getPromoID() {
        return __promoID;
    }

    public void setPromoID(String __promoID) {
        this.__promoID = __promoID;
    }

    public int getForEachMulitpleNo() {
        return __forEachMulitpleNo;
    }

    public void setForEachMulitpleNo(int __forEachMulitpleNo) {
        this.__forEachMulitpleNo = __forEachMulitpleNo;
    }

    public int getType() {
        return __type;
    }

    public void setType(int __type) {
        this.__type = __type;
    }

    public double getPriceBase() {
        return __priceBasePromo;
    }

    public void setPriceBase(double forPriceOf) {
        this.__priceBasePromo = forPriceOf;
    }

    public double getPercentage() {
        return __percentage;
    }

    public void setPercentage(double __percentage) {
        this.__percentage = __percentage;
    }

    public int getItemBase() {
        return __itemBase;
    }

    public void setItemBase(int  __itemBase) {
        this.__itemBase = __itemBase;
    }

    public  ArrayList<SaleItemPromotion> process(ArrayList<SaleItemPromotion> __basketItems, int processPayamentOn) {
         ArrayList<SaleItemPromotion> results=null;
      __basketItems=   this.sortAsccendingOfPrices(__basketItems);
         
             switch(this.__type){
                 
                 case ItemPromo.ITEM_BASE:           
                     
                     results = this.__processItemBasePromo( __basketItems,processPayamentOn);
                     break;
                 case ItemPromo.PERCENTAGE_BASE:
                    // results = this.__processPercentageBasePromo(__basketItems,processPayamentOn);
                     break;
                 case ItemPromo.PRICE_BASE:
                    results = this.__processPriceBasePromo( __basketItems,processPayamentOn);
                     break;  
                 default:
                     results= new ArrayList<>();
             }
        
             return results;
      }

    //this method will calculate the item base promotion and result the modified version of the list on item to and not to be pay for
    // with information of the percentages apply to the items
    private ArrayList<SaleItemPromotion> __processItemBasePromo(ArrayList<SaleItemPromotion> __basketItems, int processPayamentOn)
    {
      int totalItemOrdered = this.getTotalItemsOrder( __basketItems);     
      //check if promotiion critiria is melt
      if(this.getForEachMulitpleNo() > totalItemOrdered){
        //not met return 
          return __basketItems;
      }
     
      //get the number of item to pay for
      int itemNoToPayFor = totalItemOrdered % this.getForEachMulitpleNo(); 
     
      //get the number of items to process  and should be payable
      int multpi = (int) totalItemOrdered / getForEachMulitpleNo();
     
      //Get the number of items not to pay for
      itemNoToPayFor =(int) ( totalItemOrdered - (itemNoToPayFor +  multpi * (int)this.getItemBase()));
     //Console.WriteLn("Items not to pay for "+ itemNoToPayFor);
      
       
     __basketItems=   this.reducePayableItemNumbersForEachItemsPos(itemNoToPayFor, __basketItems, processPayamentOn);
     
    this.processCalculation( __basketItems);
    return  __basketItems;
    }

    private ArrayList<SaleItemPromotion> reducePayableItemNumbersForEachItemsPos(int itemNoToPayFor, ArrayList<SaleItemPromotion> __basketItems, int processPayamentOn)
    {
       if(itemNoToPayFor < 1) return __basketItems;
       //process the items to remove base of the processPayamentState
        ArrayList<SaleItemPromotion>  results;
       switch (processPayamentOn){
           case  ItemPromo.REMOVE_MAXIMUM:
           default:
               //reve itembase of maximum prices
               results= this.removeBaseMaximumPrice(itemNoToPayFor,__basketItems);
               break;
           
       }
       
       return results;
       
    }

    private ArrayList<SaleItemPromotion> removeBaseMaximumPrice(int itemNoToPayFor, ArrayList<SaleItemPromotion> __basketItems) {
          
            
            for(int i=__basketItems.size()-1;  i >=0 ; i--){
                
                //loop the number of items 
                SaleItemPromotion sp= __basketItems.get(i);
                
                
                for(int j=0; ((j < sp.getNoOfItems()) && itemNoToPayFor> 0); j++)
                {
                 
                     sp.setPayableItemsNo(sp.getPayableItemsNo()-1);
                    itemNoToPayFor--;
                    __basketItems.set(i, sp);
                    
                    if((sp.getPayableItemsNo() == sp.getNoOfItems()) || itemNoToPayFor==0 ){                       
                        break;
                    }
                   
                }
               if( itemNoToPayFor==0){
                   break;
               }
            }
      
        
        
        return __basketItems;        
    }

    private ArrayList<SaleItemPromotion> sortAsccendingOfPrices(ArrayList<SaleItemPromotion> __basketItems) {
        
        if(__basketItems !=null){
             for(int i=0; i < __basketItems.size(); i++){
                 
                 int min= i;
                 
                 for( int j= i+1 ; j < __basketItems.size() ; j++ ){
                     
                     //test which is min and insert it
                     SaleItemPromotion item1 =  __basketItems.get(min);
                     SaleItemPromotion item2 =  __basketItems.get(j);
                     
                     if(item1.getCurrentPrices() >  item2.getCurrentPrices())
                     {
                         //find the new min mum
                         min= j;
                     }
                    
                 }
                 
                 //swap them 
                if(min != i) {
                        SaleItemPromotion temItem =   __basketItems.get(min);
                        __basketItems.set(min, __basketItems.get(i));
                        __basketItems.set(i, temItem);
                }
             }
        }
        return __basketItems;
     }



    private ArrayList<SaleItemPromotion> markNotPayable(ArrayList<SaleItemPromotion> __basketItems) {
        
        ArrayList<SaleItemPromotion> results= new ArrayList<>();
        if(__basketItems !=null){
            
            for(SaleItemPromotion sp: __basketItems){
                sp.setIsPayable(false);
                results.add(sp);
            }
                
            }
       
         return results;
      }

    private void processCalculation(ArrayList<SaleItemPromotion> results) {
        
       double totalAmountIn100Percentage=0.0;
       double totalamountToPay=0.0;
             
        switch (this.getType()){
            
            case ItemPromo.ITEM_BASE:
                
        if(results !=null){
             
             
             for (SaleItemPromotion sp: results){
                 
                 if(sp.isIsPayable()){
                      totalamountToPay += sp.getAfterPromoPrice() * sp.getPayableItemsNo();
                      totalAmountIn100Percentage +=sp.getAfterPromoPrice() * sp.getPayableItemsNo();
                 }else{
                      totalAmountIn100Percentage +=sp.getAfterPromoPrice() * sp.getPayableItemsNo();
                 }
             }
        }
            break;
            case ItemPromo.PRICE_BASE:
                
                break;
                
        }
        
        
        //calculate the percentage taking off and 
          this.setPriceBase(totalamountToPay);
          double percentagePaid=    (totalamountToPay * 100 )/ totalAmountIn100Percentage;
          this.setPercentage( 100 - percentagePaid );
             
        
     }

    private int getTotalItemsOrder(ArrayList<SaleItemPromotion> groups) {
     
        int results=0;
        for(SaleItemPromotion p: groups){
            results += p.getNoOfItems();
        }
        return results;
    }

    
    //prices base algorithms
    private ArrayList<SaleItemPromotion> __processPriceBasePromo(ArrayList<SaleItemPromotion> __basketItems, int processPayamentOn) {
         int noOfItems= this.getTotalItemsOrder(__basketItems);
         
         //Does it met promo criteria
         if(noOfItems >=  this.getForEachMulitpleNo()){
             
         int itemsToPayInFull= (noOfItems % this.getForEachMulitpleNo());  
         int promoNoOfItems=  noOfItems - itemsToPayInFull; 
         
         int divNumber = (promoNoOfItems / this.getForEachMulitpleNo()); 
         double totalPricesAfterPromo =divNumber * this.getPriceBase();
         
       
             //share the totalProprices Among the first promoNoOfItems items and return the first
             
       __basketItems =  this.shareItemBasePrices(totalPricesAfterPromo, promoNoOfItems,__basketItems );
             
         
         this.processCalculation(__basketItems);
         }
         
         
        return  __basketItems; 
        
        
       }

    private ArrayList<SaleItemPromotion> shareItemBasePrices(double totalPricesAfterPromo, int promoNoOfItems, ArrayList<SaleItemPromotion> __basketItems) {
 
   
     int applyItems =  promoNoOfItems;    
     double shareAmountEach =(double) (totalPricesAfterPromo / applyItems);
    
  
    
    for(int i=0; i < __basketItems.size(); i++ ){
         SaleItemPromotion sp=__basketItems.get(i);
        
          
         for(int j=0; j < sp.getNoOfItems();j++)
         {    
             sp.setPayableItemsNo(j+1);
             sp.setAfterPromoPrice(shareAmountEach);                
            
             __basketItems.set(i, sp);
             applyItems --;
            if(applyItems ==0){
                break;
            }
             
         }
        if(applyItems==0){
            break;
        }
         
    }
    return __basketItems;    
    
    }

 

}
