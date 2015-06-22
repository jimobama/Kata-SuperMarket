/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import com.googlecode.lanterna.gui.Border;
import com.googlecode.lanterna.gui.Component;
import com.googlecode.lanterna.gui.Window;
import com.googlecode.lanterna.gui.component.Button;
import com.googlecode.lanterna.gui.component.Label;
import com.googlecode.lanterna.gui.component.Panel;
import com.googlecode.lanterna.gui.component.Table;
import com.googlecode.lanterna.gui.component.TextBox;
import java.util.ArrayList;
import structures.ItemGroup;
import structures.SaleItem;
import structures.StockItem;

/**
 *
 * @author Obaro
 */
public class ItemGroupListView extends Window {
  final private  SalesItemListView __parent;
  private Table __tableGroup  ;
  private Panel __pnlTable;
  private Panel __pnlMain;
  private Panel __pnlCommands;
  //TextBox for the Group Reference Number
  private TextBox __txtGroupReference ;
  //buttom commands
   private Button __btnSelect;
   private Button __btnViewGroupItems;
 
   private Button __btnBack;
   
  
  private static final int TABLE_SIZE=6;
  
 
  
    public ItemGroupListView(String title,SalesItemListView view) {
        super(title);
        this.__parent=(SalesItemListView)view;
        
        
        
        this.__initGui();
        
    }

    private void __initGui() {
        //initial views elements
      
        this.__tableGroup= new Table(TABLE_SIZE);
        
        //initialised panels
        this.__pnlMain= new Panel(Panel.Orientation.VERTICAL);
        this.__pnlTable = new Panel("Item Group Table List");
        this.__pnlCommands= new Panel(new Border.Bevel(true),Panel.Orientation.HORISONTAL);
        
        this.__tableGroup.setColumnPaddingSize(5);
        //Create the column names
        Component[] columns= new Component[TABLE_SIZE];
        columns[0]= new Label("S/N");
        columns[1]= new Label("Group Id");
        columns[2]= new Label("Group Name");
        columns[3]= new Label("Total Items [In]");
        columns[4]= new Label("Promo Status");
        columns[5]= new Label("Description");
        this.__tableGroup.addRow(columns);
        //call a method to load the table from that models
        this.__loadTableView();
        this.__initButtons();
        //add the command panel
        this.__pnlTable.addComponent(this.__tableGroup);
        this.__pnlMain.addComponent(this.__pnlTable);
        this.__pnlMain.addComponent(this.__pnlCommands);
        this.addComponent(this.__pnlMain);     
      
     }

    private void __loadTableView() {
        //Load all the group into the table
        
        ArrayList<ItemGroup> groups= this.__parent.getItemGroupList();
       if(groups !=null){
        for(int i=0; i < groups.size(); i++){
            
         ItemGroup item = groups.get(i);
         String status=(item.isPromoStatus())?"On":"--";
         
        Component[] columns= new Component[TABLE_SIZE];
        columns[0]= new Label(String.valueOf(i+1));
        columns[1]= new Label(item.getSaleProGroupID());
        columns[2]= new Label(item.getGroupname());
        columns[3]= new Label(""+item.getSize());
        columns[4]= new Label(status);
        columns[5]= new Label(item.getDescPromo());
        this.__tableGroup.addRow(columns);
       }
        
     }
       
    }

    private void __initButtons() {
        
        
       this.__btnBack= new Button("Back",()->{
           onBackClicked();
       });
       this.__pnlCommands.addComponent(this.__btnBack);
       
       //add TextBox
       Label lblReference= new Label(" Group ID: ");
       this.__pnlCommands.addComponent(lblReference);
        
       this.__txtGroupReference= new TextBox("",30);
       this.__pnlCommands.addComponent( this.__txtGroupReference);
       
       this.__btnSelect= new Button("Select",()->{
          onSelectClicked();
       });
       this.__pnlCommands.addComponent(this.__btnSelect);
      
       this.__btnViewGroupItems= new Button("View group items",()->{
           onViewGroupItemClicked();
       });
       this.__pnlCommands.addComponent(__btnViewGroupItems);
       
          
      
    }
    

    private void onBackClicked() {
        
        //if back button is click close the window and load the parent window
        this.close();
        if(this.__parent !=null){
            this.__parent.getOwner().showWindow(this.__parent);
        }
    }

    private void onSelectClicked() {
        
        String groupid= this.__txtGroupReference.getText();
        ItemGroup _itemGroup = this.__parent.getItemGroupByID(groupid);
           this.close();
        if(_itemGroup !=null)
        {
            //Do something good here
            this.__parent.setSelectedItemGroup(_itemGroup);
        }else{
            IView.report(this.__parent.getOwner(),1, "Enter a valid group refence number from the table above");
           
        }
       this.__parent.getOwner().showWindow(this);
     }

    private void onViewGroupItemClicked() {
        
        String groupId= this.__txtGroupReference.getText();
         ItemGroup _itemGroup = this.__parent.getItemGroupByID(groupId);
          this.close();
         if(_itemGroup!=null){
                SaleGroupView saleGroupView= new  SaleGroupView("Sale Item Group List",this);
                saleGroupView.onGroupEventLoad(_itemGroup);
                this.__parent.getOwner().showWindow(saleGroupView);
                
         }else{
             IView.report(this.__parent.getOwner(),1, "Enter a valid group refence number from the table above"); 
              this.__parent.getOwner().showWindow(this);
         }
        
     }

 

    SaleItem getSaleItemById(String id) {
        return this.__parent.getSaleItemById(id);
     }

    StockItem getStockItemById(String stockID) {
        
        return this.__parent.getStockItemByID(stockID);
      }
    
}
