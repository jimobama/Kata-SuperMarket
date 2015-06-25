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
import com.googlecode.lanterna.terminal.TerminalSize;
import java.util.ArrayList;
import structures.ItemGroup;
import structures.ItemPromo;
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
    private Panel __promotion;

    private Button __btnBack;
    private Button __btnRemove;
    private Button __btnCreatePromo;
    private Button __btnApplyPromo;
    //text
    private Label lblGroupId;
    private Label   lblGroupName;
    private Label __lblpromotionID;
  
    private Label __lbltypeName;
    private Label __lblMulitply;
    private Label __lblpromo;
    
    private ItemGroup __itemGroup;

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
        
        Panel row1= new Panel(Panel.Orientation.HORISONTAL);
        row1.addComponent(new Label("Group Id:"));
        this.lblGroupId= new Label("");
        row1.addComponent(this.lblGroupId);        
        row1.addComponent(new Label("Group Name:"));
        this.lblGroupName= new Label("");
        row1.addComponent(this.lblGroupName);
        this.__pnlMain.addComponent(row1);   
        
       __promotion= new Panel(Panel.Orientation.VERTICAL);
        
         TerminalSize lblsize= new TerminalSize(25,2);
         Panel row2= new Panel(Panel.Orientation.HORISONTAL);
         Label promoid= new Label("Promotion Number");
         promoid.setAlignment(Component.Alignment.TOP_LEFT);
         promoid.setPreferredSize(lblsize);
         row2.addComponent(promoid);
          //initialsed promo fields     
         this.__lblpromotionID= new Label("......");
         row2.addComponent(this.__lblpromotionID);
         __promotion.addComponent(row2);
         //row3
         Panel row3= new Panel(Panel.Orientation.HORISONTAL);       
         Label mulplicity= new Label("Promotion Criteria");
         mulplicity.setAlignment(Component.Alignment.TOP_LEFT);
         mulplicity.setPreferredSize(lblsize);
         row3.addComponent(mulplicity);         
         this.__lblMulitply= new Label("......");
         row3.addComponent(this.__lblMulitply);
          __promotion.addComponent(row3);
       
         //row five
         Panel row5= new Panel(Panel.Orientation.HORISONTAL); 
         Label name= new Label("Promotion Type");
         name.setAlignment(Component.Alignment.TOP_LEFT);
         name.setPreferredSize(lblsize);
         row5.addComponent(name); 
         this.__lbltypeName= new Label(".....");
         row5.addComponent(this.__lbltypeName);
          __promotion.addComponent(row5);
          
         //row6
          Panel row6= new Panel(Panel.Orientation.HORISONTAL); 
         Label promo= new Label("Discount");
         promo.setAlignment(Component.Alignment.TOP_LEFT);
         promo.setPreferredSize(lblsize);
         row6.addComponent(promo); 
         this.__lblpromo=new Label("....");    
         row6.addComponent(this.__lblpromo);
          __promotion.addComponent(row6);
         
         
          
         
         
         
         this.__pnlMain.addComponent(this.__promotion);
        
         
         this.__pnlMain.addComponent(new EmptySpace());
         this.__pnlMain.addComponent(__table);
         this.__pnlMain.addComponent(new EmptySpace());
         
         
        
        //add the buttons
        Panel pnlButtons = new Panel(new Border.Bevel(true),Panel.Orientation.HORISONTAL);
        
        this.__btnBack= new Button("Back",()->{
            onBackClicked();
        });
        pnlButtons.addComponent(this.__btnBack);
        
   
        
        this.__btnCreatePromo= new Button("Create/Edit  Promotion",()->{
            onCreateNewPromoClicked();
        });
        pnlButtons.addComponent(this.__btnCreatePromo);
        
        
        this.__btnApplyPromo= new Button("Update/Save",()->{
            onApplyNewPromoClicked();
        });
        pnlButtons.addComponent(this.__btnApplyPromo);
        
       
        this.__pnlMain.addComponent(pnlButtons);
        this.addComponent(this.__pnlMain);
        
    }

    void onGroupEventLoad(ItemGroup _itemGroup) {
        if(_itemGroup !=null){
         this.__itemGroup=_itemGroup;
         this.updateCreatePromotion(this.__itemGroup.getPromotion());
         this.lblGroupId.setText(_itemGroup.getSaleProGroupID());
         this.lblGroupName.setText(_itemGroup.getGroupname());
         
         ArrayList<String> saleIDs=  _itemGroup.getListString();
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
           promoView.updatePromotionView(this.__itemGroup.getPromotion());
           this.__parent.getOwner().showWindow( promoView, GUIScreen.Position.CENTER);
       }
    }

    private void onApplyNewPromoClicked() {
        
        //UPDATE THE PROMO Group Item
        this.close();
        if(this.__itemGroup !=null){
            if(this.__itemGroup.getPromotion() !=null){
                //update the item
                if(this.__parent.isUpdateItemGroup(this.__itemGroup))
                {
                      IView.report(this.getOwner(), 0, "Item Group promotion as be successfully updated and active");
                }else{
                    IView.report(this.getOwner(), 1, "Item could not updated");
                    this.__parent.getOwner().showWindow(this);
                }
            }
        }
        this.__parent.getOwner().showWindow(this);
    }

    void updateCreatePromotion(ItemPromo promo) {
        
        this.__itemGroup.setPromotion(promo);        
        //reset promotion fields again
        this.__setPromotionView(this.__itemGroup.getPromotion());
        
    }

    private void __setPromotionView(ItemPromo promotion) {
        
        if(promotion !=null){
            //set the controls fields
            this.__lblMulitply.setText(String.valueOf(promotion.getForEachMulitpleNo()));
            
            switch(promotion.getType()){
                case ItemPromo.ITEM_BASE:
                     this.__lblpromo.setText(""+promotion.getItemBase());
                      this.__lbltypeName.setText("Item Base");
                    break;
                case ItemPromo.PERCENTAGE_BASE:
                    this.__lblpromo.setText(promotion.getPercentage()+"%");
                      this.__lbltypeName.setText("Percentage Base");
                    break;
                case ItemPromo.PRICE_BASE:
                     this.__lblpromo.setText("£"+promotion.getPriceBase());
                      this.__lbltypeName.setText("Price Base");
                    break;
            }
         
            this.__lblpromotionID.setText(promotion.getPromoID());
           
           
            
        }
    }

}
