/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import com.googlecode.lanterna.gui.Component;
import com.googlecode.lanterna.gui.Window;
import com.googlecode.lanterna.gui.component.Button;
import com.googlecode.lanterna.gui.component.Label;
import com.googlecode.lanterna.gui.component.Panel;
import com.googlecode.lanterna.gui.component.Table;
import java.util.ArrayList;
import structures.ItemBasket;
import structures.ItemGroup;
import structures.SaleItem;
import structures.StockItem;

/**
 *
 * @author Obaro
 */
class BasketView extends Window {
private ShoppingCenterView __parent;
private Button __btnBack;
private  Panel __pnlMain;
private Table __tblBaskets;



    public BasketView(String title,ShoppingCenterView parent) {
        super(title);
        this.__parent=parent;
        this.__initGui();
        
    }

    private void __initGui() {
       __pnlMain= new Panel(Panel.Orientation.VERTICAL) ;
     Panel pnlButtons= new Panel(Panel.Orientation.HORISONTAL);
     __tblBaskets= new Table(6);
     Component[] titles= new Component[6];
     titles[0]=new Label("S/N");
     titles[1]=new Label("Item Name");    
     titles[2]=new Label("Item Price");
     titles[3]=new Label("No Of Items");    
     titles[4]=new Label("Total Amount");
     titles[5]=new Label("Promo Status");   
     this.__tblBaskets.setColumnPaddingSize(5);
     this.__tblBaskets.addRow(titles);
     this.loadItemView();
     
     Panel pnlTable= new Panel();
     pnlTable.addComponent(this.__tblBaskets);
     this.__pnlMain.addComponent(pnlTable);
      
    this.__btnBack= new Button("Back",()->{
        this.onBackClick();
    });
    pnlButtons.addComponent(this.__btnBack);
    this.__pnlMain.addComponent(pnlButtons);
    this.addComponent(this.__pnlMain);
    }

    private void onBackClick() {
        this.close();
        this.__parent.getOwner().showWindow(this.__parent);
     }

    private void loadItemView() {
        ArrayList<ItemBasket> basket= this.__parent.getItemBasket();
        int counter =1;
        if(basket !=null)
        {
            for (ItemBasket itemB:basket ){
             if(this.__parent.isItemOnPromotion(itemB.getItemId()))
                {
                    this.filterItemsIntoGroup(itemB);
                  
                }
             else{
                 //item is not on promotion, display its normally on the table with its actual amounts
                 this.CreateNewTableRowView(counter,itemB);
             }
             counter++;
            }
        }
    }

    private void CreateNewTableRowView(int c, ItemBasket itemB) {
        
        if(itemB !=null){
            
            SaleItem saleItem = this.__parent.getSaleItemById(itemB.getItemId());
            if(saleItem !=null){
                
            StockItem stockItem = this.__parent.getStockItemById(saleItem.getStockID());
            if(stockItem !=null){
                Component [] columns= new Component[6];
                columns[0]=new Label(""+c);
                columns[1]=new Label(stockItem.getName());    
                columns[2]=new Label("£"+saleItem.getSalePrice());
                columns[3]=new Label(""+ (int)itemB.getNoOfItems());    
                columns[4]=new Label("£"+(saleItem.getSalePrice() * itemB.getNoOfItems() ));
                String promoStatus = (this.__parent.isItemOnPromotion(itemB.getItemId()))? "On Promo":" ---- ";
                columns[5]=new Label(promoStatus);   
                this.__tblBaskets.addRow(columns);
            }
            }
            
        }
        
     }

    private void filterItemsIntoGroup(ItemBasket itemB) {
       
       String groupID = this.__parent.getGroupIdBySaleItemId( itemB.getItemId());
       if(groupID !=null){
           //check if the promo group as be added to the basket
        //   if(this.__parent.isGroupAlreadyCreated(groupID)){
          //     this.__parent.addNewGroupItem(groupID,itemB.getItemId())
          }
           
       }
     
    

    
    
    
}
