/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import com.googlecode.lanterna.gui.Border;
import com.googlecode.lanterna.gui.Component;
import com.googlecode.lanterna.gui.GUIScreen;
import com.googlecode.lanterna.gui.Window;
import com.googlecode.lanterna.gui.component.Button;
import com.googlecode.lanterna.gui.component.EmptySpace;
import com.googlecode.lanterna.gui.component.Label;
import com.googlecode.lanterna.gui.component.Panel;
import com.googlecode.lanterna.gui.component.Table;
import java.util.ArrayList;
import structures.ItemGroup;
import structures.SaleItem;
import structures.StockItem;

/**
 *
 * @author Obaro
 */
public class SaleGroupView extends Window {

    private ItemGroupListView __parent;
    private Table __table;
    private static final int SIZE = 5;
    private Panel __pnlMain;

    private Button __btnBack;
    private Button __btnRemove;
    private Button __btnCreatePromo;
    private Button __btnApplyPromo;
    //text
    private Label lblGroupId;
    private Label lblGroupName;

    public SaleGroupView(String title, ItemGroupListView view) {
        super(title);
        __parent = (ItemGroupListView) view;

        this.__initGui();
    }

    private void __initGui() {

        this.__pnlMain= new Panel(Panel.Orientation.VERTICAL);
        this.__table = new Table(SIZE);
        this.__table.setColumnPaddingSize(5);
        Component[] columns = new Component[SIZE];
        columns[0] = new Label("S/N");
        columns[1] = new Label("Sale Item ID");
        columns[2] = new Label("Item Name");
        columns[3] = new Label("Price");
        columns[4] = new Label("Description");
        this.__table.addRow(columns);
        this.__loadItemsView();
        
        Panel pnlGroupInfo= new Panel(Panel.Orientation.HORISONTAL);
        pnlGroupInfo.addComponent(new Label("Group Id:"));
        this.lblGroupId= new Label("");
        pnlGroupInfo.addComponent(this.lblGroupId);
        
         pnlGroupInfo.addComponent(new Label("Group Name:"));
        this.lblGroupName= new Label("");
        pnlGroupInfo.addComponent(this.lblGroupName);
       this.__pnlMain.addComponent(pnlGroupInfo);
        
         this.__pnlMain.addComponent(new EmptySpace());
         this.__pnlMain.addComponent(__table);
         this.__pnlMain.addComponent(new EmptySpace());
        
        //add the buttons
        Panel pnlButtons = new Panel(new Border.Bevel(true),Panel.Orientation.HORISONTAL);
        
        this.__btnBack= new Button("Back",()->{
            onBackClicked();
        });
        pnlButtons.addComponent(this.__btnBack);
        
   
        
        this.__btnCreatePromo= new Button("Create New Promo",()->{
            onCreateNewPromoClicked();
        });
        pnlButtons.addComponent(this.__btnCreatePromo);
        
        
        this.__btnApplyPromo= new Button("Add promo",()->{
            onApplyNewPromoClicked();
        });
        pnlButtons.addComponent(this.__btnApplyPromo);
        
       
        this.__pnlMain.addComponent(pnlButtons);
        this.addComponent(this.__pnlMain);
        
    }

    void onGroupEventLoad(ItemGroup _itemGroup) {
        if(_itemGroup !=null){
         this.lblGroupId.setText(_itemGroup.getSaleProGroupID());
         this.lblGroupName.setText(_itemGroup.getGroupname());
         
         ArrayList<String> saleIDs=  _itemGroup.getList();
         if(saleIDs !=null)
         {
            for(int i=0; i < saleIDs.size(); i++) 
            {
                String id= saleIDs.get(i);
                SaleItem item = this.__parent.getSaleItemById(id);
                  StockItem stockItem = this.__parent.getStockItemById(item.getStockID());
                  
                if(item !=null && stockItem !=null){
                    Component[] columns = new Component[SIZE];
                    columns[0] = new Label(String.valueOf(i +1 ));
                    columns[1] = new Label(item.getSaleID());                  
                   
                    columns[2] = new Label(stockItem.getName());                  
                    columns[4] = new Label(stockItem.getDesc());                    
                    columns[3] = new Label("£"+item.getSalePrice());
                    this.__table.addRow(columns);
        
                }
            }
         }
        }
     
    }

    private void __loadItemsView() {
        
        
     }

    private void onBackClicked() {
      this.close();
      if(this.__parent !=null){
         this.__parent.getOwner().showWindow(this.__parent);
      }
    }

    private void onCreateNewPromoClicked() {
        
        this.close();
       NewPromoView promoView = new NewPromoView("Create New Promo",this); 
       if(this.__parent !=null){
           this.__parent.getOwner().showWindow( promoView, GUIScreen.Position.CENTER);
       }
    }

    private void onApplyNewPromoClicked() {
    }

}
