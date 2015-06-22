/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;
import com.googlecode.lanterna.gui.Border;
import com.googlecode.lanterna.gui.Component;
import com.googlecode.lanterna.gui.GUIScreen;
import com.googlecode.lanterna.gui.GUIScreen.Position;
import com.googlecode.lanterna.gui.Window;
import com.googlecode.lanterna.gui.component.Button;
import com.googlecode.lanterna.gui.component.Label;
import com.googlecode.lanterna.gui.component.Panel;
import com.googlecode.lanterna.gui.component.Table;
import com.googlecode.lanterna.gui.component.TextBox;
import com.googlecode.lanterna.terminal.TerminalSize;
import controllers.KataSuperMarketController;
import java.util.ArrayList;
import structures.ItemGroup;
import structures.SaleItem;
import structures.StockItem;


/**
 *
 * @author Obaro
 */
public class SalesItemListView extends Window {
    
    private  Table __table;
    private TextBox __txtGroupID;
    private TextBox __txtRef;
    private Panel __pnlTableView;
    private Panel __main;
    static final int TABLE_SIZE = 7;
    
    KataSuperMarketView __parent;
    public SalesItemListView(String title,KataSuperMarketView parent) {
        super("");
        //set the parents;
        this.__parent=( KataSuperMarketView)parent;
        this.__initGui();
    }

    private void __initGui() {
        this.__table= new Table(TABLE_SIZE);
        this.__pnlTableView= new Panel();
        this.__main= new Panel(Panel.Orientation.VERTICAL);
        Component[] comps = new Component[TABLE_SIZE];
        
        comps[0] = new Label("S/N");
        comps[1] = new Label("Reference Number");
        comps[2] = new Label("Item Name");
        comps[3] = new Label("Stock Level");
        comps[4] = new Label("Weight");
        comps[5] = new Label("Price");
        comps[6] = new Label("Description");
        this.__table.setColumnPaddingSize(5);
        
        this.__table.addRow(comps);        
        __loadSaleView();
        __pnlTableView.addComponent(__table);
        
        
        this.__main.addComponent(__pnlTableView);
        this.__main.addComponent(this.__ShoppingGui());
        this.addComponent(this.__main);
        this.__parent.getOwner().invalidate();
    }
    
    
     private void __loadSaleView() {
        
         KataSuperMarketController controller=( KataSuperMarketController)this.__parent.getController();
        ArrayList<SaleItem> items= controller.getSaleItemList();
      
        if(items ==null || items.size() <=0) {
            return;             
        }
        
        for (int i=0; i < items.size(); i++){
            
        SaleItem item = items.get(i);
     
        StockItem stockItem = controller.getStockById(item.getStockID());
        if(stockItem !=null){           
        
        Component[] comps= new Component[TABLE_SIZE];
        
        comps[0] = new Label(String.valueOf(i+1));
        comps[1] = new Label(item.getSaleID());
        //get the item name
      
        comps[2] = new Label(stockItem.getName());
        comps[3] = new Label(""+stockItem.getNoOfStocks());
        
        
        comps[4] = new Label(item.getWeight()+"kg");
        comps[5] = new Label("£"+item.getSalePrice());
        //promo 
        comps[6] = new Label("30 percentage promo P45");
        __table.addRow(comps);
        }
        }
        
    }

     
     
      private Panel __ShoppingGui() {
        Panel panel;
        TerminalSize lblSize= new TerminalSize(20,2);
        panel = new Panel(new Border.Standard.Bevel(true), Panel.Orientation.VERTICAL);
        panel.setTitle("Item Promo/Bonus Section");
        
        //row 1
        Panel row1= new Panel(Panel.Orientation.HORISONTAL);
        Label lblRef = new Label("Reference Number: ");
        lblRef.setAlignment(Component.Alignment.TOP_LEFT);
        lblRef.setPreferredSize(lblSize);
        
        __txtRef = new TextBox("", 30);      
        row1.addComponent(lblRef);
        row1.addComponent(__txtRef);        
        panel.addComponent(row1);
        //row 2
         
        Panel row2= new Panel(Panel.Orientation.HORISONTAL);
        Label lblNumberToPurchase= new Label("Group Id");
        lblNumberToPurchase.setAlignment(Component.Alignment.TOP_LEFT);
        lblNumberToPurchase.setPreferredSize(lblSize);
        
        Button btnInsertGroupID= new Button(" Insert Group number", () -> {
            onInsertGroupID();
        });
        row2.addComponent(lblNumberToPurchase);
        __txtGroupID= new TextBox("",20);
        row2.addComponent(__txtGroupID);
        row2.addComponent(btnInsertGroupID);
        panel.addComponent(row2);
        
        //button panel
        
        
        Panel row3= new Panel(new Border.Bevel(true),Panel.Orientation.HORISONTAL);
        Button btnBack = new Button ("Back", () -> {
            onBackClicked();
        });
        Button btnCreateNewPromo = new Button("New group", () -> {
            onCreateNewPromoClicked();
        });
        
        Button btnAddItemToGroup= new Button("Add Item", () -> {
            onAddItemToGroupClicked();
        });
        row3.addComponent(btnBack);
        row3.addComponent(btnCreateNewPromo);
        row3.addComponent(btnAddItemToGroup);
        panel.addComponent(row3);
        return panel;
    }
      
      
      private void onAddItemToGroupClicked(){
          //The method add the item with the reference number to the group Id, which means 
          //if the group is in a particullar promo it will apply to the item
          String itemRef= this.__txtRef.getText();
          String groupRef= this.__txtGroupID.getText();
          KataSuperMarketController controller =( KataSuperMarketController) this.__parent.getController();
          this.close();
          //call the controller to add the item to the group with the groupref id
          if(controller.addSaleItemOnGroupEvent(itemRef,groupRef))
           {
            
                this.__txtRef.setText("");
                this.__txtGroupID.setText("");
          
          }
         this.__parent.getOwner().showWindow(this);
          
          
      }
      private void onCreateNewPromoClicked(){
          //open the new Group form for user
         
          if(this.__parent !=null){
             this.close();
             NewGroupView newGroupForm = new NewGroupView("New Item Group",this);
             this.__parent.getOwner().showWindow(newGroupForm,Position.CENTER);
          }
          
      }
      private void  onBackClicked()
      {
           this.close();
          if(this.__parent !=null)
          {
           this.__parent.getOwner().showWindow(this.__parent, GUIScreen.Position.CENTER);
          }
      }
      
      // The method will open a new window containing a list of existing promotion on the screen
      private void  onInsertGroupID()
      {
         this.close();
         ItemGroupListView groupViews= new  ItemGroupListView("Item Group List",this);
         if(this.__parent!=null){
            this.__parent.getOwner().showWindow(groupViews);
         }
        
      }

    ItemGroup getItemGroupByID(String groupid) {
        
      KataSuperMarketController controller =( KataSuperMarketController) this.__parent.getController();
      return controller.getItemGroupById(groupid);        
    }

    void setSelectedItemGroup(ItemGroup _itemGroup) {
        
        //set the group details
        if(_itemGroup !=null){
            this.__txtGroupID.setText(_itemGroup.getSaleProGroupID());
        }
     }

    void onCreateGroupEvent(ItemGroup item) {
        
         if(item !=null && item.validated()){
             
              KataSuperMarketController controller =( KataSuperMarketController) this.__parent.getController();
              controller.onCreateItemGroup(item);
         }
     }

    ArrayList<ItemGroup> getItemGroupList() {
         KataSuperMarketController controller =( KataSuperMarketController) this.__parent.getController();
              return controller.getItemGroupList();
      
     }

    SaleItem getSaleItemById(String id) {
           KataSuperMarketController controller =( KataSuperMarketController) this.__parent.getController();
         return  controller.getSaleItemById(id);
     }

    StockItem getStockItemByID(String stockID) {
      KataSuperMarketController controller =( KataSuperMarketController) this.__parent.getController();
      return controller.getStockById(stockID);
     }

  
}
