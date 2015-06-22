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
public class ItemPromo extends IError {

    private String __promoID;
    private String __itemGroupId;
    private int __forEachMulitpleNo;
    private int __type;
    
    //This fields depends on the type of promo
    private int forPriceOf;    
    private double __percentage;

    void processByPrice(int noItems, double price) {

        this.__forEachMulitpleNo = noItems;

    }

    void processByPercentage(double percentage) {
        this.__percentage = percentage;
    }

    void apply() {

    }

}
