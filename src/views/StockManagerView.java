/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import com.googlecode.lanterna.gui.GUIScreen;
import com.googlecode.lanterna.gui.GUIScreen.Position;
import com.googlecode.lanterna.gui.dialog.DialogButtons;
import com.googlecode.lanterna.gui.dialog.DialogResult;
import com.googlecode.lanterna.gui.dialog.MessageBox;
import controllers.InterfaceController;
import controllers.KataSuperMarketController;
import structures.StockItem;

/**
 *
 * @author Obaro
 */
class StockManagerView extends StockItemCommandView implements IView {

    private KataSuperMarketController __controller;
    private KataSuperMarketView __parentView;
    private String __title;
    private static boolean LOOP = true;
    private int __next;

    StockManagerView(String title) {
        super(title);
        __title = title;
        this.__next = 0;
    }

    StockManagerView(String title, IView parent) {
        super(title);
        __parentView = (KataSuperMarketView) parent;
        __title = title;
    }

    @Override
    public void setModel(Object model) {

    }

    @Override
    public void show() {
        this.__parentView.getOwner().getActiveWindow().close();
        
        this.__parentView.getOwner().showWindow(this, GUIScreen.Position.CENTER);
        
    }

    @Override
    public void attach(InterfaceController controller) {
        this.__controller = (KataSuperMarketController) controller;
    }

    @Override
    protected void onAddClicked() {
        AddStockItemView form = new AddStockItemView("Add Item", this);
        this.close();
        form.show();

    }

    @Override
    protected void onBackClick() {
        this.close();
        this.__parentView.getOwner().setTitle("Main Window");
        this.__parentView.getOwner().showWindow(this.__parentView,Position.CENTER);
    
    }


    @Override
    protected void onViewClick() {

        if( this.__parentView !=null){
         this.close();
        }
        StockItemListView _stockView = new StockItemListView("Stock Database ", this);       
       _stockView.show();
        

    }

    public void addItemEvent(StockItem item) {

        if (item.validate()) {
            this.__controller.addStockItemEvent(item);
            this.getOwner().getActiveWindow().close();

            DialogResult rtv = MessageBox.showMessageBox(this.getOwner(), "Information", "Do you want to view list of stock items", DialogButtons.YES_NO);
            if (rtv == DialogResult.YES) {
                this.onViewClick();
            } else {
                //ask if he want to cancel or continue with the page again

                rtv = MessageBox.showMessageBox(this.getOwner(), "Alert", "I guess you want to add another Item again? ", DialogButtons.OK_CANCEL);
                if (rtv == DialogResult.OK) {
                    ;
                    this.onAddClicked();
                } else {

                    this.onBackClick();
                }
            }

        } else {
            IView.report(this.getOwner(), 2, item.getError());
        }
    }

    @Override
    public InterfaceController getController() {
        return this.__controller;
    }

}
