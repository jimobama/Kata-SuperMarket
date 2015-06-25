/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import com.googlecode.lanterna.gui.Component;
import com.googlecode.lanterna.gui.GUIScreen.Position;
import com.googlecode.lanterna.gui.Window;
import com.googlecode.lanterna.gui.component.Button;
import com.googlecode.lanterna.gui.component.EmptySpace;
import com.googlecode.lanterna.gui.component.Label;
import com.googlecode.lanterna.gui.component.Panel;
import com.googlecode.lanterna.gui.component.TextBox;
import com.googlecode.lanterna.gui.dialog.DialogButtons;
import com.googlecode.lanterna.gui.dialog.DialogResult;
import com.googlecode.lanterna.gui.dialog.MessageBox;
import com.googlecode.lanterna.terminal.TerminalSize;
import structures.SaleItem;
import structures.StockItem;

/**
 *
 * @author Obaro
 */
public class AddSaleView extends Window {

    //Fields 
    private Panel __pnlLeft;
    private Panel __pnlRight;
    private Panel __pnlMain;
    private final StockItem __item;
    private final StockItemListView __parent;

    private TextBox __txtSalePrice;
    private TextBox __txtWeight;

    private Label __lblCostPrice;
    private Label __lblItemName;
    private Label __lblStockLevel;

    //need buttons for additing and going back to the parent windows
    private Button __btnBack;
    private Button __btnAdd;
   // private Button __btnDelete;

    //
    public AddSaleView(StockItem item, Window parent) {
        super("Add New [ Stock Number: " + item.getStockId()+" ]");

        //this should not happen
        if (item == null) {
            item = new StockItem("");
        }
        this.__item = item;
        this.__parent = (StockItemListView) parent;

        this.__initGui();
        this.addComponent(__pnlLeft);

    }

    //method will show the window and close the parent active window on the console screen
    public void show() {
        if (this.__parent != null) {
            this.__parent.getOwner().getActiveWindow().close();
            this.__parent.getOwner().showWindow(this, Position.CENTER);
        }
    }

    //The method initialised the Gui view and position all the elements
    private void __initGui() {

        //Initialised the left panel
        __pnlLeft = new Panel(Panel.Orientation.VERTICAL);
        //create a row of horizontal panel to add each fields with size of label for alignment

        TerminalSize size = new TerminalSize(15, 1);

        //Row 1
        __pnlLeft.addComponent(new EmptySpace());
        Panel row1 = new Panel(Panel.Orientation.HORISONTAL);
        Label lblItemname = new Label("Item Name : ");
        lblItemname.setPreferredSize(size);
        lblItemname.setAlignment(Label.Alignment.TOP_LEFT);
        row1.addComponent(lblItemname);
        this.__lblItemName = new Label(this.__item.getName());
        row1.addComponent(this.__lblItemName);
        __pnlLeft.addComponent(row1);

        //Row 2
        Panel row2 = new Panel(Panel.Orientation.HORISONTAL);
        Label lblCostPrice = new Label("Item Cost Price :");
        lblCostPrice.setPreferredSize(size);
        lblCostPrice.setAlignment(Label.Alignment.TOP_LEFT);

        row2.addComponent(lblCostPrice);
        this.__lblCostPrice = new Label(String.valueOf("£" + this.__item.getCostForEach()));
        row2.addComponent(this.__lblCostPrice);
        __pnlLeft.addComponent(row2);

        //Row 3
        Panel row3 = new Panel(Panel.Orientation.HORISONTAL);
       
        Label lblStockLevel = new Label("Stock Level");
        lblStockLevel.setAlignment(Label.Alignment.TOP_LEFT);
        lblStockLevel.setPreferredSize(size);
        row3.addComponent(lblStockLevel);
        this.__lblStockLevel = new Label("" + this.__item.getNoOfStocks());
        row3.addComponent(this.__lblStockLevel);
        this.__pnlLeft.addComponent(row3);

        //Row 4         
        Panel pnlSaleFields = new Panel(Panel.Orientation.VERTICAL);
        pnlSaleFields.addComponent(new EmptySpace());
        Panel row4 = new Panel(Panel.Orientation.HORISONTAL);
        Label lblSalePrice = new Label("Sale Price");
        lblSalePrice.setPreferredSize(size);
        lblSalePrice.setAlignment(Label.Alignment.TOP_LEFT);
        row4.addComponent(lblSalePrice);
        this.__txtSalePrice = new TextBox("", 20);
        row4.addComponent(this.__txtSalePrice);
        pnlSaleFields.addComponent(row4);

        //Row 5
        pnlSaleFields.addComponent(new EmptySpace());
        Panel row5 = new Panel(Panel.Orientation.HORISONTAL);
        Label lblItemWeight = new Label("Item Weight[optional]");
        lblItemWeight.setPreferredSize(size);
        lblItemWeight.setAlignment(Component.Alignment.TOP_LEFT);
        row5.addComponent(lblItemWeight);
        this.__txtWeight = new TextBox("", 20);
        row5.addComponent(this.__txtWeight);
        pnlSaleFields.addComponent(row5);
        pnlSaleFields.addComponent(new EmptySpace());

        //Create a button panel which is row 6 in an horizontal arrangement
        Panel row6 = new Panel(Panel.Orientation.HORISONTAL);
        this.__btnAdd = new Button("Save", () -> {
            onAddClicked();
        });

        this.__btnBack = new Button("Back", () -> {
            onBackClicked();
        });
    
         

        //add to panel
        Label btnEmptySpace= new Label("");
        btnEmptySpace.setAlignment(Component.Alignment.TOP_LEFT);
        btnEmptySpace.setPreferredSize(size);
        row6.addComponent(btnEmptySpace);
        row6.addComponent(this.__btnBack);
        row6.addComponent(this.__btnAdd);
      
        pnlSaleFields.addComponent(row6);
        this.__pnlLeft.addComponent(pnlSaleFields);

    }

    //Method add the item to the Sale list by calling the parent controller 
    private void onAddClicked() {

        if (this.__item.validate()) {
            SaleItem item = new SaleItem();
            item.setStockID(this.__item.getStockId());
            try {
                double price = Double.parseDouble(this.__txtSalePrice.getText());
                item.setSalePrice(price);
            } catch (NumberFormatException err) {
                IView.report(this.getOwner(), 1, "Invalid entry on sale price field");
                return;
            }

            try {
                double weight = Double.parseDouble(this.__txtWeight.getText());
                item.setWeight(weight);
            } catch (NumberFormatException err) {
                IView.report(this.getOwner(), 1, "Invalid entry on sale item weight field, Leave it as zero[0.0]");
                this.__txtWeight.setText("0.0");
                return;
            }
            if (item.verify()) {
                   addSaleItem(item);
            } else {
                IView.report(this.getOwner(), 1, "Sale Error: " + item.getError());
            }

        }

    }

    private void addSaleItem(SaleItem item) {
        //else continue will add the item to the sale lists
         
       
        if (!this.profitCheck()) {
            this.getOwner().showWindow(this, Position.CENTER);
        } else {
            //proceed with additing the item
             boolean bRes = this.__parent.addSaleItem(item);
            if (bRes) {
                if (this.__parent != null) {
                    this.__parent.getOwner().invalidate();
                    this.__parent.getOwner().showWindow(this.__parent);
                }
            } else {
                if (this.__parent != null) {
                    this.__parent.getOwner().invalidate();
                    this.__parent.getOwner().showWindow(this, Position.CENTER);
                } else {
                         //close the application , this should not happen 

                }

            }
        }
    }

    private void onBackClicked() {
        this.getOwner().getActiveWindow().close();
        this.__parent.getOwner().showWindow(__parent);

    }

    private double getViewProfit() {
        double profit = 0.0;
        try {
            double price = Double.parseDouble(this.__txtSalePrice.getText());
            double cost = this.__item.getCostForEach();
            profit = price - cost;

        } catch (NumberFormatException err) {
            //do nothing
        }
        profit = Math.round(profit * 100.0) / 100.0;
        return profit;
    }

    private boolean  profitCheck() {
        double profit = this.getViewProfit();
        String messsage = "";
        this.getOwner().getActiveWindow().close();
        if (profit < 0) {
            messsage = "Are you willing to lost this amount £[" + profit + "] for each items sold?";
        } else {
            messsage = "The profit you will make is [£" + profit + "] for each item sold are you satisfy with that?";
        }
        DialogResult result = MessageBox.showMessageBox(this.getOwner(), "Warning", messsage, DialogButtons.YES_NO);
        return result != DialogResult.NO;
        
    }

    private void onDeleteClicked() {
      }

}
