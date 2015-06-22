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
import com.googlecode.lanterna.gui.component.Label;
import com.googlecode.lanterna.gui.component.Panel;
import com.googlecode.lanterna.gui.component.TextBox;

import services.Helper;
import structures.StockItem;

/**
 *
 * @author Obaro
 */
public class AddStockItemView extends Window {

    private final StockManagerView __parent;
    private final TextBox __txtItemName;
    private final TextBox __txtItemID;
    private final TextBox __txtaDesc;
    private final TextBox __txtStockLevelNumber;
    private final TextBox __txtCost;

    private final Panel form;

    public AddStockItemView(String title, Window window) {
        super(title);
        __parent = (StockManagerView) window;

        //initial gui
        form = new Panel(Panel.Orientation.VERTICAL);
        __txtItemID = new TextBox("", 40);
        Label lblNumber = new Label("Item Number");
        lblNumber.setAlignment(Component.Alignment.CENTER);
        form.addComponent(lblNumber);
        form.addComponent(__txtItemID);

        __txtItemName = new TextBox("", 40);
        form.addComponent(new Label("Item Name"));
        form.addComponent(__txtItemName);

        form.addComponent(new Label("Description"));
        __txtaDesc = new TextBox("Nil", 40);
        form.addComponent(this.__txtaDesc);

        form.addComponent(new Label("Number of item to stock"));
        __txtStockLevelNumber = new TextBox("", 40);
        form.addComponent(__txtStockLevelNumber);
        form.addComponent(new Label("Cost of Item"));
        __txtCost = new TextBox("", 20);
        form.addComponent(this.__txtCost);

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

        Panel pnlButtons = new Panel(Panel.Orientation.HORISONTAL);
        pnlButtons.addComponent(btnClose);
        pnlButtons.addComponent(btnAdd);

        form.addComponent(pnlButtons);

        this.__txtItemID.setText(Helper.generateId(10));
        this.__txtItemID.setEditPosition(0);

        this.addComponent(form);

    }

    protected void addEventTrigger() {
        StockItem item = new StockItem(this.__txtItemID.getText());
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
