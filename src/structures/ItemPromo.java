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
    private int __forEachMulitpleNo;
    private int __type;    
    //This fields depends on the type of promo
    private double __priceBasePromo;    
    private double __percentage;
    private double __itemBase;
    private String __desc;
    
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

    public double getItemBase() {
        return __itemBase;
    }

    public void setItemBase(double __itemBase) {
        this.__itemBase = __itemBase;
    }

}
