/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package structures;

/**
 *
 * @author Obaro
 */
public class ItemBasket {
   
    private  String __itemId;
    private int __noOfItems;
    private  double __currentPrices;
    private double __afterPromoPrice;
    private double __weight;

    public double getWeight() {
        return __weight;
    }

    public void setWeight(double __weight) {
        this.__weight = __weight;
    }

    public double getAfterPromoPrice() {
        return __afterPromoPrice;
    }

    public void setAfterPromoPrice(double afterPromoPrice) {
        this.__afterPromoPrice = afterPromoPrice;
    }
    private int payableItemsNo;
   public  ItemBasket(){
        this.__itemId="";
        this.__currentPrices=0.0;
        this.__afterPromoPrice=this.__currentPrices;
        this.__noOfItems=1;
        payableItemsNo= this.__noOfItems;
    }

    public String getItemId() {
        return __itemId;
    }

    public void setItemId(String __itemId) {
        this.__itemId = __itemId;
      
    }

    public int getNoOfItems() {
        return __noOfItems;
    }

    public void setNoOfItems(int __noOfItems) {
        this.__noOfItems = __noOfItems;
        this.payableItemsNo= this.__noOfItems;
    }

    public double getCurrentPrices() {
        return __currentPrices;
    }
   
    public void setCurrentPrices(double __currentPrices) {
        this.__currentPrices = __currentPrices;
        this.__afterPromoPrice= this.__currentPrices ;
    }

    public int getPayableItemsNo() {
        return payableItemsNo;
    }

    public void setPayableItemsNo(int payableItemsNo) {
        this.payableItemsNo = payableItemsNo;
    }
    
    
    
    
    
}
