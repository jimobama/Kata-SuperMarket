/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import com.googlecode.lanterna.gui.Action;
import com.googlecode.lanterna.gui.Component;
import com.googlecode.lanterna.gui.GUIScreen.Position;
import com.googlecode.lanterna.gui.Window;
import com.googlecode.lanterna.gui.component.Button;
import com.googlecode.lanterna.gui.component.Label;
import com.googlecode.lanterna.gui.component.Panel;
import com.googlecode.lanterna.gui.component.Table;
import com.googlecode.lanterna.gui.component.TextBox;
import controllers.KataSuperMarketController;
import java.util.ArrayList;
import structures.SaleItem;
import structures.StockItem;

/**
 *
 * @author Obaro
 */
public class StockItemListView extends Window {

    private Table __tblViews;
    private StockManagerView __parent;
    private TextBox __txtStockID;

    public StockItemListView(String title, StockManagerView parent) {
        super(title);
        this.__parent = parent;
        this.__tblViews = new Table(7);
        this.__tblViews.setColumnPaddingSize(5);

        Component[] components = new Component[7];

        components[0] = new Label("S/N");
        components[1] = new Label("Stock Number");
        components[2] = new Label("UIN");
        components[3] = new Label("Stock Level");
        components[4] = new Label("Cost Of Item");
        components[5] = new Label("Description");
        components[6] = new Label("Date Added");

        __tblViews.addRow(components);
        this.addComponent(__tblViews);
        this.loadView();
        this.addComponent(this.__nav());

    }

    private Panel __nav() {
        Panel panel = new Panel(Panel.Orientation.HORISONTAL);
        Button btnBack = new Button("Back", new Action() {

            @Override
            public void doAction() {

                onBackButtonEvent();
            }

        });
        
        
        Button btnAddToSale = new Button("Add To Sale",new Action(){

            @Override
            public void doAction() {
                onAddToSaleClicked();
            }
            
        });

        panel.addComponent(btnBack);
        panel.addComponent(new Label(" StockID:"));
        this.__txtStockID= new TextBox("",40);
        panel.addComponent(this.__txtStockID);
        panel.addComponent(btnAddToSale);
        return panel;
    }

    private void onBackButtonEvent() {
        this.__parent.getOwner().getActiveWindow().close();
        this.__parent.getOwner().showWindow(this.__parent, Position.CENTER);
    }

    private void loadView() {
        KataSuperMarketController controller = (KataSuperMarketController) this.__parent.getController();

        ArrayList<StockItem> list = (ArrayList<StockItem>) controller.getStockList();
        if (list != null) {

            for (int i = 0; i < list.size(); i++) {
                Component[] components = new Component[7];
                StockItem item = list.get(i);
                components[0] = new Label(String.valueOf(i + 1));
                components[1] = new Label(item.getStockId());
                components[2] = new Label(item.getId());
                components[3] = new Label(String.valueOf(item.getNoOfStocks()));
                components[4] = new Label(String.valueOf("£" + item.getCostForEach()));
                components[5] = new Label(item.getDesc());
                components[6] = new Label("-- -- --");
                this.__tblViews.addRow(components);

            }
        } else {

        }
    }

    void show() {

        if (this.__parent != null) {
            this.__parent.getOwner().showWindow(this);
        }

    }

    
    
 private  void   onAddToSaleClicked()
{
   String stockID= this.__txtStockID.getText();
   
   //cross check the number if it exist in the model
   KataSuperMarketController controller=( KataSuperMarketController) this.__parent.getController();
   StockItem item= controller.getStockById(stockID);
   
    if(item == null )
    {
      this.getOwner().getActiveWindow().close();
      IView.report(this.getOwner(), 1, "The Stock number ["+stockID+"] does not exist");
      this.getOwner().showWindow(this);
      return ;
    }
    
    //else if the stock item is found  then
    
    this.openAddSaleWindow(item);

}
/* The method will open a new window to the Stock Item */
    private void openAddSaleWindow( StockItem item) {
        
        AddSaleView addSaleWindow= new  AddSaleView(item , this);
        addSaleWindow.show();
     }
    
    public boolean addSaleItem(SaleItem item){
       
        boolean bResult;
        KataSuperMarketController controller=( KataSuperMarketController) this.__parent.getController();
        return  bResult = controller.addSaleItemEvent(item);
        
    }
    
}




