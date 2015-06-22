/*
 The class keep track of the sales items , that the customer will be able to see
 Promo can only be apply to sales item only not stock items to be able to make it
 flexible to determine which items have specific promos

 */
package structures;

import services.Helper;
import models.StockManager;
import structures.StockItem;

/**
 *
 * @author Obaro
 */
public class SaleItem extends IError   {

    private String __saleID;
    private String __stockID;
    private double __salePrice;
    private double __weight;

   public  SaleItem() {
        this.__saleID = Helper.generateId("SL", 10);
    }

   public  SaleItem(String saleID) {
        this.__saleID = saleID;
    }

 public    boolean verify() {
        boolean okay = true;
         StockItem item = StockManager.GetItemById(this.getStockID());
        
        if (item == null) {
            this.setError("No sale item specified");
            okay = false;
        } else if (this.getSalePrice() < 0) {
            this.setError("Sale price can not be less than 0");
            okay = false;
        } else if (item.getNoOfStocks() == 0) {
            this.setError("This item is out of stock");
            okay = false;
        } else if (this.getWeight() < 0) {
            this.setError("Item weight can not be less than 0");
            okay = false;
        } else if (this.getSaleID().trim().equals("")) {
            //this should not happen
            this.setError("Sales id not specified");
            okay = false;
        }

        return okay;
    }

    public String getStockID() {
        return __stockID;
    }

    public void setStockID(String __stockID) {
        this.__stockID = __stockID;
    }

    public double getSalePrice() {
        return __salePrice;
    }

    public void setSalePrice(double __salePrice) {
        this.__salePrice = __salePrice;
    }

    public double getWeight() {
        return __weight;
    }

    public void setWeight(double __weight) {
        this.__weight = __weight;
    }

    public String getSaleID() {
        return __saleID;
    }

    public boolean isEqual(SaleItem item) {

        Boolean okay = false;

        if (this.getSaleID().trim().equalsIgnoreCase(item.getSaleID().trim())) {
            okay = true;
        }
        return okay;
    }
}
