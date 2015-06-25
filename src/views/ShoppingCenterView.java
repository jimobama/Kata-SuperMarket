/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import com.googlecode.lanterna.gui.Border;
import com.googlecode.lanterna.gui.Component;
import com.googlecode.lanterna.gui.GUIScreen.Position;
import com.googlecode.lanterna.gui.Window;
import com.googlecode.lanterna.gui.component.Button;
import com.googlecode.lanterna.gui.component.EmptySpace;
import com.googlecode.lanterna.gui.component.Label;
import com.googlecode.lanterna.gui.component.Panel;
import com.googlecode.lanterna.gui.component.Table;
import com.googlecode.lanterna.gui.component.TextBox;
import com.googlecode.lanterna.terminal.TerminalSize;
import controllers.KataSuperMarketController;
import java.util.ArrayList;
import structures.ItemBasket;
import structures.ItemPromo;
import structures.SaleItem;
import structures.SaleItemGroup;
import structures.StockItem;

/**
 *
 * @author Obaro
 */
class ShoppingCenterView extends Window {

    private KataSuperMarketView __parent;

    private TextBox __txtItemID;
    private Button __btnBack;
    private Button __btnAddItem;
    private Button __btnGotoBasket;
    private Panel __pnlMain;
 
    private Table __tblSaleItems;

    public ShoppingCenterView(String title, KataSuperMarketView parent) {
        super(title);
        this.__parent = parent;
        this.__initGui();
    }

    private void __initGui() {
        __pnlMain = new Panel(Panel.Orientation.VERTICAL);
        __tblSaleItems = new Table(3);

        this.__loadSaleItems();
        Panel pnlTable = new Panel(Panel.Orientation.VERTICAL);
        
        pnlTable.addComponent(this.__tblSaleItems);
        this.__pnlMain.addComponent(pnlTable);

        Panel pnlButtons = new Panel(new Border.Standard(),Panel.Orientation.HORISONTAL);
        __btnBack = new Button("Back", () -> {
            this.onBackClicked();
        });
        pnlButtons.addComponent(__btnBack);
        
        Label lblItemID= new Label("Item Number: ");
        pnlButtons.addComponent(lblItemID);
        this.__txtItemID= new TextBox("",20);
         pnlButtons.addComponent(this.__txtItemID);
         
        this.__btnAddItem = new Button("Add To Basket", () -> {
            this.onAddToBasketClicked();
        });
        pnlButtons.addComponent(this.__btnAddItem);
        
        this.__btnGotoBasket = new Button("View Basket [0]", () -> {
            this.onViewBasketClicked();
        });
        pnlButtons.addComponent(this.__btnGotoBasket);
        
        __pnlMain.addComponent(pnlButtons);

        this.addComponent(this.__pnlMain);
       
       

    }

    private void onBackClicked() {

        this.close();
        this.__parent.getOwner().showWindow(this.__parent, Position.CENTER);
    }
  
    //Method loads the Sale items into blocks of columns and rows
    private void __loadSaleItems() {

        KataSuperMarketController controller = (KataSuperMarketController) this.__parent.getController();

        ArrayList<SaleItem> list = controller.getSaleItemList();
        list = this.__filterItemsForSales(list);
        Panel[] row = new Panel[list.size()];
        int counter = 0;

       
        int endPosition = -1;
      
        Panel[] columns = new Panel[3];
      if(list.size()>3) {  
        for (int i = 0; i < list.size(); i++) {
          
           SaleItem item = list.get(i);
           columns[counter] = this.createSalePanelView(item);
           
           if (counter == 2) {
               counter = 0;
               this.__tblSaleItems.addRow(columns);
               columns = new Panel[3]; 
                endPosition= i + 1; 
               continue;
              
            }         
           counter++;
        
       
        }//end for statments
      }else{
        endPosition =0;  
      }

        //Load the rest of the items into the next row
        if (endPosition >= 0 ) {
             Console.WriteLn(endPosition);
            //load the rest column items into the view
            Panel cols[] = new Panel[3];
            cols[0] = this.createSalePanelView(null);
            cols[1] = this.createSalePanelView(null);
            cols[2] = this.createSalePanelView(null);
            int _counter = 0;
            
            for (int i = endPosition; i < list.size(); i++) {
                // Console.WriteLn(i);
                SaleItem _item = list.get(i);
                cols[_counter] = this.createSalePanelView(_item);
                _counter++;
              
            }
            this.__tblSaleItems.addRow(cols);

        }

    }

    //The method filter out Items that are not in stock 
    private ArrayList<SaleItem> __filterItemsForSales(ArrayList<SaleItem> list) {
        ArrayList<SaleItem> result = new ArrayList<>();

        if (list != null) {
            KataSuperMarketController controller = (KataSuperMarketController) this.__parent.getController();
            //function loop
            list.stream().forEach((item) -> {
                String stockID = item.getStockID();
                StockItem stockItem = controller.getStockById(stockID);
                if (stockItem != null) {
                    if (stockItem.getNoOfStocks() > 0) {
                        result.add(item);
                    }
                }
            });

        }
        return result;
    }

    //The methods created a panel of Items blocks
    private Panel createSalePanelView(SaleItem item) {
        if (item == null) {
            return new Panel();
        }

        KataSuperMarketController controller = (KataSuperMarketController) this.__parent.getController();
        StockItem stockItem = controller.getStockById(item.getStockID());
        TerminalSize lblSize = new TerminalSize(15, 1);
        Panel itemView = new Panel(new Border.Bevel(true), Panel.Orientation.VERTICAL);
        //Column 0
        if (stockItem != null) {

            //row 1
            Panel row1 = new Panel(Panel.Orientation.HORISONTAL);

            Label lblNumberCol11 = new Label("Item Number:");
            lblNumberCol11.setAlignment(Component.Alignment.TOP_LEFT);
            lblNumberCol11.setPreferredSize(lblSize);
            row1.addComponent(lblNumberCol11);

            row1.addComponent(new Label(item.getSaleID()));
            itemView.addComponent(row1);
            itemView.addComponent(new EmptySpace());

            //row 2
            Panel row2 = new Panel(Panel.Orientation.HORISONTAL);
            Label lblNumberCol12 = new Label("Item Name:");
            lblNumberCol12.setAlignment(Component.Alignment.TOP_LEFT);
            lblNumberCol12.setPreferredSize(lblSize);

            row2.addComponent(lblNumberCol12);
            row2.addComponent(new Label(stockItem.getName()));
            itemView.addComponent(row2);
            itemView.addComponent(new EmptySpace());
            //row three
            Panel row3 = new Panel(Panel.Orientation.HORISONTAL);
            Label lblPrice1 = new Label("Item Price:");
            lblPrice1.setAlignment(Component.Alignment.TOP_LEFT);
            lblPrice1.setPreferredSize(lblSize);
            row3.addComponent(lblPrice1);
            row3.addComponent(new Label("£" + item.getSalePrice()));
            itemView.addComponent(row3);
            itemView.addComponent(new EmptySpace());
            //row 4
            Panel row4 = new Panel(Panel.Orientation.HORISONTAL);
            Label lblStockLevel = new Label("In Stock:");
            lblStockLevel.setAlignment(Component.Alignment.TOP_LEFT);
            lblStockLevel.setPreferredSize(lblSize);
            row4.addComponent(lblStockLevel);
            row4.addComponent(new Label("" + stockItem.getNoOfStocks()));
            itemView.addComponent(row4);
        }

        return itemView;
    }

    private void onAddToBasketClicked() {
        
        String __saleID = this.__txtItemID.getText();
         KataSuperMarketController controller = (KataSuperMarketController) this.__parent.getController();
        SaleItem item = controller.getSaleItemById(__saleID);
        this.close();
        if(item ==null){            
            IView.report(this.__parent.getOwner(),1, "The item number did not exists");           
        }else{
            //add the item to the Basket List 
            
           if(item.getWeight() > 0.0){
            
              String strcurrent_weight;
               strcurrent_weight = "0";
              try{
                 strcurrent_weight=  InputDialog.showMessageBox(this.__parent.getOwner(),"Input","Item Weight");
                 
                 double current_weight= Double.parseDouble(strcurrent_weight);
                 
                 double currentP= (item.getSalePrice() *current_weight )/ item.getWeight();
                //add to basket
                 item.setSalePrice(currentP);
                 item.setWeight(current_weight);
                 this.createBasketItemFrom(item);
                
           
              }catch(NumberFormatException err){
                  IView.report(this.__parent.getOwner(),1, "Invalid item purchase weight entered ");
                  
              }
              
           }else{
           this.createBasketItemFrom(item);
           }
        }
       this.__parent.getOwner().showWindow(this);
        
        
      }

    private void onViewBasketClicked() {
        //This load the basket view with complement calculations of items promos and percentages
        this.close();
        BasketView _basketView= new BasketView("Basket Items ",this);
        this.__parent.getOwner().showWindow(_basketView);
        
     }

    ArrayList<ItemBasket> getItemBasket() {
         KataSuperMarketController controller = (KataSuperMarketController) this.__parent.getController();
        return controller.getItemBasket();
     }

    SaleItem getSaleItemById(String itemId) {
        KataSuperMarketController controller = (KataSuperMarketController) this.__parent.getController();
       return controller .getSaleItemById(itemId);
    }

    StockItem getStockItemById(String stockID) {
        
         KataSuperMarketController controller = (KataSuperMarketController) this.__parent.getController();
         return controller.getStockById(stockID);
     }

    boolean isItemOnPromotion(String itemB) {
         KataSuperMarketController controller = (KataSuperMarketController) this.__parent.getController();
         
         return controller.isItemOnPromotion( itemB);
     }

    String getGroupIdBySaleItemId(String saleItemId) {
          KataSuperMarketController controller = (KataSuperMarketController) this.__parent.getController();
           return controller.getGroupIdBySaleItemId(saleItemId);
       }

    void addBasketItemToGroup(String groupID, ItemBasket itemB) {
         KataSuperMarketController controller = (KataSuperMarketController) this.__parent.getController();         
         controller.addBasketItemToGroup(groupID, itemB);   
          
    }

    ArrayList<SaleItemGroup> getSaleItemGroups() {
       KataSuperMarketController controller = (KataSuperMarketController) this.__parent.getController();
       return controller.getItemSaleGroups();
      }

    void clearSaleItemGroups() {
       KataSuperMarketController controller = (KataSuperMarketController) this.__parent.getController();
       controller.clearSaleItemGroups();
    }

    ItemPromo getPromotionByItemId(String itemId) {
        KataSuperMarketController controller = (KataSuperMarketController) this.__parent.getController();
        return controller.getPromotionByItemId(itemId);
    }

    private void createBasketItemFrom(SaleItem item) {
               KataSuperMarketController controller = (KataSuperMarketController) this.__parent.getController();
                ItemBasket basketItem= new ItemBasket();
                basketItem.setItemId(item.getSaleID());
                basketItem.setCurrentPrices(item.getSalePrice());
                basketItem.setWeight(item.getWeight());
                

                controller.addBasketItem(basketItem);
                int basketCount= controller.getBasketCount();

                this.__btnGotoBasket.setText("View Basket ["+ basketCount+"]"); }

}
