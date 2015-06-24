/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import com.googlecode.lanterna.gui.Action;
import com.googlecode.lanterna.gui.Component;
import com.googlecode.lanterna.gui.GUIScreen;
import com.googlecode.lanterna.gui.GUIScreen.Position;
import com.googlecode.lanterna.gui.Window;
import com.googlecode.lanterna.gui.component.Button;
import com.googlecode.lanterna.gui.component.EmptySpace;
import com.googlecode.lanterna.gui.component.Label;
import com.googlecode.lanterna.gui.component.Panel;
import com.googlecode.lanterna.gui.component.TextBox;
import com.googlecode.lanterna.terminal.TerminalSize;

import services.Helper;
import structures.StockItem;

/**
 *
 * @author Obaro
 */
public class AddStockItemView extends Window {

    private final StockManagerView __parent;
    private final TextBox __txtItemName;
    private final Label __lblStockItemRef;
    private final TextBox __txtaDesc;
    private final TextBox __txtStockLevelNumber;
    private final TextBox __txtCost;

    private final Panel form;

    public AddStockItemView(String title, Window window) {
        super(title);
        __parent = (StockManagerView) window;

        //initial gui
        TerminalSize lblSize = new TerminalSize(20,1);        
        form = new Panel(Panel.Orientation.VERTICAL);
        form.addComponent(new EmptySpace());
        
        //row01
        Panel row01= new Panel(Panel.Orientation.HORISONTAL);
        __lblStockItemRef = new Label("");
        Label lblNumber = new Label("Item Number");
        lblNumber.setAlignment(Component.Alignment.TOP_LEFT);
        lblNumber.setPreferredSize(lblSize);
        lblNumber.setAlignment(Component.Alignment.CENTER);
        row01.addComponent(lblNumber);
        row01.addComponent(__lblStockItemRef);
        form.addComponent(row01);
        

        //row 2
         Panel row02= new Panel(Panel.Orientation.HORISONTAL);
         form.addComponent(new EmptySpace());
        __txtItemName = new TextBox("", 40);
        Label lblItemName=new Label("Item Name");
        lblItemName.setAlignment(Component.Alignment.TOP_LEFT);
        lblItemName.setPreferredSize(lblSize);        
        row02.addComponent(lblItemName);
        
        row02.addComponent(__txtItemName);
        form.addComponent(row02);
        

        //row03
         Panel row03= new Panel(Panel.Orientation.HORISONTAL);
         form.addComponent(new EmptySpace());
          Label lblDesc=new Label("Description");
         lblDesc.setAlignment(Component.Alignment.TOP_LEFT);
         lblDesc.setPreferredSize(lblSize); 
        
         row03.addComponent(lblDesc);
        __txtaDesc = new TextBox("Nil", 40);
         row03.addComponent(this.__txtaDesc);
         form.addComponent(row03);

         //row04
          Panel row04= new Panel(Panel.Orientation.HORISONTAL);
          form.addComponent(new EmptySpace());
           Label lblNoStock=new Label("Stock Number ");
         lblNoStock.setAlignment(Component.Alignment.TOP_LEFT);
         lblNoStock.setPreferredSize(lblSize); 
         
        row04.addComponent(lblNoStock);
        __txtStockLevelNumber = new TextBox("", 40);        
        row04.addComponent(__txtStockLevelNumber);
        form.addComponent(row04);
         
        //row05
        Panel row05= new Panel(Panel.Orientation.HORISONTAL);
        form.addComponent(new EmptySpace());
          Label lblCost=new Label("Cost of Item");
         lblCost.setAlignment(Component.Alignment.TOP_LEFT);
         lblCost.setPreferredSize(lblSize); 
         row05.addComponent(lblCost);
        __txtCost = new TextBox("", 20);
        row05.addComponent(this.__txtCost);
        form.addComponent(row05);

        Button btnAdd = new Button("Add", new Action() {
            @Override
            public void doAction() {
                addEventTrigger();

            }

        });

        Button btnClose = new Button("Close", new Action() {

            @Override
            public void doAction() {

                formEventClose();
            }

        });

        Panel row06 = new Panel(Panel.Orientation.HORISONTAL);
        form.addComponent(new EmptySpace());
        Label lblEmpty= new Label("");
        lblEmpty.setAlignment(Component.Alignment.TOP_LEFT);
        lblEmpty.setPreferredSize(lblSize);
         row06.addComponent(lblEmpty);
        row06.addComponent(btnClose);
        row06.addComponent(btnAdd);
       
        form.addComponent(row06);
        form.addComponent(new EmptySpace());
        this.__lblStockItemRef.setText(Helper.generateId(5));
       

        this.addComponent(form);

    }

    protected void addEventTrigger() {
        StockItem item = new StockItem(this.__lblStockItemRef.getText());
        item.setName(this.__txtItemName.getText());
        item.setDesc(this.__txtaDesc.getText());

        try {
            int level = Integer.parseInt(this.__txtStockLevelNumber.getText());
            item.setNoOfStocks(level);
        } catch (NumberFormatException err) {
            IView.report(this.getOwner(), 0, "Invalid entry in Number of Item to stock field");
        }
        try {
            double cost = Double.parseDouble(this.__txtCost.getText());
            item.setCostForEach(cost);

        } catch (NumberFormatException err) {
            IView.report(this.getOwner(), 0, "Invalid entry in Item cost");
        }

        if (!item.validate()) {
            IView.report(this.getOwner(), 0, item.getError());
        } else {
            //add to the Stock list
            this.__parent.addItemEvent(item);
        }

    }

    protected void formEventClose() {
        close();
        this.__parent.getOwner().showWindow(__parent, GUIScreen.Position.CENTER);
    }

    public void show() {
        this.__parent.getOwner().showWindow(this,Position.CENTER);
    }

}
