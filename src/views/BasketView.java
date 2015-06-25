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
import java.text.DecimalFormat;
import java.util.ArrayList;
import structures.ItemBasket;
import structures.ItemPromo;
import structures.SaleItem;
import structures.SaleItemGroup;
import structures.SaleItemPromotion;
import structures.StockItem;

/**
 *
 * @author Obaro
 */
class BasketView extends Window {
private final ShoppingCenterView __parent;
private Button __btnBack;
private  Panel __pnlMain;
private Table __tblBaskets;
private int index;


    public BasketView(String title,ShoppingCenterView parent) {
        super(title);
        this.__parent=parent;
        this.__initGui();
        
    }

    private void __initGui() {
        index=1;
       __pnlMain= new Panel(Panel.Orientation.VERTICAL) ;
     Panel pnlButtons= new Panel(Panel.Orientation.HORISONTAL);
     __tblBaskets= new Table(8);
     Component[] titles= new Component[8];
     titles[0]=new Label("S/N");
     titles[1]=new Label("Item Name");    
     titles[2]=new Label("Actual Price");
     titles[3]=new Label("No Of Items");  
     titles[4]=new Label("E/P Items");  
     titles[5]=new Label("Total Payament");
     titles[6]=new Label("Discount Price");  
     titles[7]=new Label("Discount Total Payment");   
     
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
       
        
        if(basket !=null)
        { 
           
            for (ItemBasket itemB:basket ){
             if(this.__parent.isItemOnPromotion(itemB.getItemId()))
                {
                    this.filterItemsIntoGroup(itemB);
                    
                }
             else{
                 //item is not on promotion, display its normally on the table with its actual amounts
                 this.CreateNewTableRowView(index,itemB);
             }
             index++;
            }
            
            //process the group and display them
            this.displayPromotionItems();
        }
    }

    private void CreateNewTableRowView(int c, ItemBasket itemB) {
        DecimalFormat f = new DecimalFormat("##.00");
        if(itemB !=null){
            
            SaleItem saleItem = this.__parent.getSaleItemById(itemB.getItemId());
            if(saleItem !=null){
                
            StockItem stockItem = this.__parent.getStockItemById(saleItem.getStockID());
            if(stockItem !=null){
                Component [] columns= new Component[8];
                columns[0]=new Label(""+c);
                columns[1]=new Label(stockItem.getName());  
                String lvlCurrentPrice="£"+ f.format(itemB.getCurrentPrices());
                if(itemB.getWeight()>0){
                    lvlCurrentPrice+=" Per "+itemB.getWeight()+"g";
                }
                columns[2]=new Label(lvlCurrentPrice);
                
                
                columns[3]=new Label(""+ (int)itemB.getNoOfItems());   
                columns[4]=new Label(""+ (int)itemB.getPayableItemsNo());    
                columns[5]=new Label("£"+ f.format(itemB.getCurrentPrices() * itemB.getNoOfItems() ));  
                
                columns[6]=new Label("£"+f.format(itemB.getAfterPromoPrice() ));  
                double discountAmount= (itemB.getAfterPromoPrice() * itemB.getPayableItemsNo());
                Label promoLabel= new Label("£"+f.format(discountAmount));
                
                if(this.__parent.isItemOnPromotion(itemB.getItemId()))
                {
                    //get the promo of the item
                    ItemPromo  promo= this.__parent.getPromotionByItemId(itemB.getItemId());
                    if(promo !=null){
                    switch( promo.getType()){
                        case ItemPromo.ITEM_BASE:
                             promoLabel.setText("£"+(itemB.getAfterPromoPrice() * itemB.getPayableItemsNo()));
                            break;
                        case ItemPromo.PRICE_BASE:
                            //calculate the discount total
                            double pricesOfEffectedItems = itemB.getAfterPromoPrice() * itemB.getPayableItemsNo(); 
                            // Console.WriteLn("Affected Numbers  Price =  "+  pricesOfEffectedItems);
                             int  noAffectedNo=itemB.getNoOfItems()- itemB.getPayableItemsNo();
                             
                             double pricesForNotEffectedItems= (noAffectedNo * itemB.getCurrentPrices());
                             // Console.WriteLn("Not Affected Numbers  Price =  "+ pricesForNotEffectedItems);
                             double totalPrices= pricesForNotEffectedItems + pricesOfEffectedItems;
                             promoLabel.setText("£"+f.format(totalPrices));
                            break;
                        case ItemPromo.PERCENTAGE_BASE:
                        default:
                            break;
                            
                    }
                    }
                    
                }
                
                columns[7]=promoLabel; 
                this.__tblBaskets.addRow(columns);
            }
            }
            
        }
        
     }

    private void filterItemsIntoGroup(ItemBasket itemB) {
      
       String groupID = this.__parent.getGroupIdBySaleItemId( itemB.getItemId());
       if(groupID !=null){
             this.__parent.addBasketItemToGroup(groupID, itemB);
          }
           
       }

    private void displayPromotionItems() {
        
        ArrayList<SaleItemGroup> groups = this.__parent.getSaleItemGroups();
        
          for(SaleItemGroup group: groups){
           // Console.WriteLn("The total sale is "+ group.getPromo().getPercentage());
            ArrayList<SaleItemPromotion> sales= group.applyPromotion(ItemPromo.REMOVE_MAXIMUM);
            sales.stream().forEach((sp) -> {                
               this.CreateNewTableRowView(this.index, sp);
            });
         }
          
      this.__parent.clearSaleItemGroups();
   
    }
     
    

    
    
    
}
