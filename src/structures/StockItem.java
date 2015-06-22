/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package structures;

import services.Helper;
import structures.Item;

/**
 *
 * @author Obaro
 */
public class StockItem extends Item implements java.io.Serializable {

    private String __stockId;
    private double __costForEach;
    private int __noOfStocks;

    public StockItem(String id) {
        super("", "");//call the supper default constructor
        this.__stockId = id;
        this.setName("");
    }

    public StockItem(double __cost, int noOfStocks, String name, String Desc) {
        super(name, Desc);//call the supper default constructor
        this.__noOfStocks = noOfStocks;
        this.__costForEach = __cost;
        __stockId=Helper.generateId(10);

    }

    @Override
    public boolean validate() {
        boolean okay = true;

        if (!super.validate()) {
            okay = false;
            return okay;
        }

        if (this.getCostForEach() <= 0) {
            this.setError("Number of item to stock have to be more than 0");
            okay = false;
        } else if (this.getNoOfStocks() <= 0) {
            okay = false;
            this.setError("Cost of each item cannot be a signed value or less than 0");
        }

        return okay;
    }

    //getters and setters
    public double getCostForEach() {
        return __costForEach;
    }

    public void setCostForEach(double __costForEach) {
        this.__costForEach = __costForEach;
    }

    public int getNoOfStocks() {
        return __noOfStocks;
    }

    public void setNoOfStocks(int __noOfStocks) {
        this.__noOfStocks = __noOfStocks;
    }

    public String getStockId() {
        return __stockId;
    }

    public boolean isEqual(StockItem o) {
        boolean okay = false;
        if(o==null) return false;
        if (this.getStockId().trim().equalsIgnoreCase(o.getStockId().trim())) {
            okay = true;
        } 

        return okay;
    }

}
