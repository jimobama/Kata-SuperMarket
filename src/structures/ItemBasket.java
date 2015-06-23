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
    
    ItemBasket(){
        this.__itemId="";
        this.__currentPrices=0.0;
        this.__noOfItems=0;
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
    }

    public double getCurrentPrices() {
        return __currentPrices;
    }

    public void setCurrentPrices(double __currentPrices) {
        this.__currentPrices = __currentPrices;
    }
    
    
    
    
    
}
