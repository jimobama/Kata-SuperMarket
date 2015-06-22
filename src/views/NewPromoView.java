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
import com.googlecode.lanterna.gui.component.EmptySpace;
import com.googlecode.lanterna.gui.component.Label;
import com.googlecode.lanterna.gui.component.Panel;
import com.googlecode.lanterna.gui.component.TextBox;
import com.googlecode.lanterna.terminal.TerminalSize;
import services.Helper;
import structures.ItemPromo;

/**
 *
 * @author Obaro
 */
class NewPromoView extends Window {
   private final  SaleGroupView __parent;
   private Panel __pnlMain;
   private  Button __btnBack;
   private Button __btnCreate;
   private TextBox __txtApplyNumberOfItems;

   private Label lblPromoId;
   private Label __lblPromoType;
   
   //public static consts
   private Panel __pnlPromo;
   private TextBox __txtPriceBase;
    private TextBox __txtItemBase;
   private TextBox __txtPercentage; 
   
   static private int PromoType=0;
   
 
   
    public NewPromoView(String title,SaleGroupView parent) {
        super(title);
        this.__parent=parent;
        
        this.__initGui();
    }

    private void __initGui() {
        
        this.__pnlMain= new Panel(Panel.Orientation.VERTICAL);
        __pnlPromo= new Panel(Panel.Orientation.VERTICAL);
        
        TerminalSize lblSize= new TerminalSize(20,2);
        //Row 1
        Panel row1= new Panel(Panel.Orientation.HORISONTAL);
        
        Label lblPromoID= new Label("Promo ID:");
        lblPromoID.setAlignment(Component.Alignment.TOP_LEFT);
        lblPromoID.setPreferredSize(lblSize);
        row1.addComponent(lblPromoID);
        this.lblPromoId= new Label(Helper.generateId(5));
        row1.addComponent( this.lblPromoId);
        this.__pnlMain.addComponent(row1);
        
         /*Row 2
        Panel row2= new Panel(Panel.Orientation.HORISONTAL);
        
        Label lblPromoName= new Label("Promo  Name:");
        lblPromoName.setAlignment(Component.Alignment.TOP_LEFT);
        lblPromoName.setPreferredSize(lblSize);
        row2.addComponent(lblPromoName);
        this.__promoName= new TextBox("",30);
        row2.addComponent(this.__promoName);
        this.__pnlMain.addComponent(row2);
        
        */
        //row3
        Panel row3= new Panel(Panel.Orientation.HORISONTAL);
        
        Label lblItems= new Label("Apply to muliply of:");
        lblItems.setAlignment(Component.Alignment.TOP_LEFT);
        lblItems.setPreferredSize(lblSize);
        row3.addComponent(lblItems);
        this.__txtApplyNumberOfItems= new TextBox("",30);
        row3.addComponent(this.__txtApplyNumberOfItems);
        this.__pnlMain.addComponent(row3);
        
        
        //row4
         this.__pnlMain.addComponent(new EmptySpace());
        Panel row4= new Panel(Panel.Orientation.HORISONTAL);        
        Label lblPromotType= new Label("Promo Type:")   ;     
        lblPromotType.setAlignment(Component.Alignment.TOP_LEFT);
        lblPromotType.setPreferredSize(lblSize);
        row4.addComponent(lblPromotType);
        
        this.__lblPromoType= new Label("-----");
        row4.addComponent(__lblPromoType);
        Button btnTypeOfPromo= new Button("Choose promo type...",()->{
            this.onChoosePromotionClicked();
        });
        row4.addComponent(btnTypeOfPromo);
        this.__pnlMain.addComponent(row4);
        
         this.__pnlMain.addComponent(this.__pnlPromo);
        
        
        
        Panel pnlButtons = new Panel(new Border.Bevel(true), Panel.Orientation.HORISONTAL);
        this.__btnBack= new Button("Back", ()->{
            this.onBackClicked();
        });
        pnlButtons.addComponent(this.__btnBack);
        
         this.__btnCreate= new Button("Add Group Promotion", ()->{
            this.onAddPromoClicked();
        });
        pnlButtons.addComponent(this.__btnCreate);
        
        
        
        this.__pnlMain.addComponent(pnlButtons);        
        this.addComponent(this.__pnlMain);
        
     }

    private void onBackClicked() {
        this.close();
        if(this.__parent !=null){
            this.__parent.getOwner().showWindow(this.__parent);
        }
      }

    private void onChoosePromotionClicked() {
        
        this.close();
        ChoosePromotionView promo= new ChoosePromotionView("Select a promotion type",this);
        this.__parent.getOwner().showWindow(promo, GUIScreen.Position.CENTER);
      }

    void setPromotionType( int  type) {
        this.__pnlPromo.removeAllComponents();
        PromoType=type;
        switch(type){
            case ItemPromo.ITEM_BASE:
                this.__lblPromoType.setText("Item Base");
                this.showItemBasePromo();
                break;
            case ItemPromo.PERCENTAGE_BASE:
                this.__lblPromoType.setText("Percentage Base");
                this.showPercentageBasePromo();
                break;
            case ItemPromo.PRICE_BASE:
                this.__lblPromoType.setText("Price Base");
                this.showPriceBasePromotion();
                break;                
            default:
                this.__lblPromoType.setText("----");
        }
        
      }

    private void showItemBasePromo() {
       TerminalSize lblsize = new TerminalSize(20,2);
       Panel row1= new Panel(Panel.Orientation.HORISONTAL);
       Label lblItemBase = new Label("Number of Item:");
       lblItemBase.setAlignment(Component.Alignment.TOP_LEFT);
       lblItemBase.setPreferredSize(lblsize);
       row1.addComponent(lblItemBase);
       this.__txtItemBase= new TextBox("",30);
       row1.addComponent(this.__txtItemBase);
       this.__pnlPromo.addComponent(row1);
    }

    private void showPercentageBasePromo() {
       TerminalSize lblsize = new TerminalSize(20,2);
       Panel row1= new Panel(Panel.Orientation.HORISONTAL);
       Label lblItemBase = new Label("Percentage :");
       lblItemBase.setAlignment(Component.Alignment.TOP_LEFT);
       lblItemBase.setPreferredSize(lblsize);
       row1.addComponent(lblItemBase);
       this.__txtPercentage= new TextBox("",30);
       row1.addComponent( this.__txtPercentage);
       this.__pnlPromo.addComponent(row1);
       
     }

    private void showPriceBasePromotion() {
       TerminalSize lblsize = new TerminalSize(20,2);
       Panel row1= new Panel(Panel.Orientation.HORISONTAL);
       Label lblItemBase = new Label("For the price of(£) :");
       lblItemBase.setAlignment(Component.Alignment.TOP_LEFT);
       lblItemBase.setPreferredSize(lblsize);
       row1.addComponent(lblItemBase);
       this.__txtPriceBase= new TextBox("",30);
       row1.addComponent( this.__txtPriceBase);
       this.__pnlPromo.addComponent(row1);
        
     }

    private void onAddPromoClicked() {
         this.close();
        ItemPromo promo = new ItemPromo(this.lblPromoId.getText());
         String message="Invalid entry in Number of items for promotion field";
         promo.setType(PromoType);
        
        try{
            promo.setForEachMulitpleNo(Integer.parseInt(this.__txtApplyNumberOfItems.getText()));
            switch(promo.getType()){
               case ItemPromo.ITEM_BASE:
               {
                 message="Enter a valid item base input number";
                 promo.setItemBase(Double.parseDouble(this.__txtItemBase.getText()));
                 break;  
               }
               case ItemPromo.PERCENTAGE_BASE:
                    message="Enter a promotion percentage for the items provided";
                    promo.setPercentage(Double.parseDouble(this.__txtPercentage.getText()));
                   break;
               case ItemPromo.PRICE_BASE:
                    message="Enter a valid price for the number of items when promotion criteria is met";
                    promo.setPriceBase(Double.parseDouble(this.__txtPriceBase.getText()));
                   break;
           default:
                message="";
           break;
           }
         
        }catch(NumberFormatException err){
            IView.report(this.getOwner(),1,  message);
            return ;
        }
       
        //Check if the promo items valid
        
        if(promo.validated()){
            //if the itempromo is valid           
           this.__parent.updateCreatePromotion(promo);
           this.__parent.getOwner().showWindow(this.__parent);
           return ;
        }else{
            IView.report(this.getOwner(),1,promo.getError());  
        }
        
        this.__parent.getOwner().showWindow(this,Position.CENTER); 
     }
    
    
}
