/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import com.googlecode.lanterna.gui.GUIScreen;
import com.googlecode.lanterna.gui.GUIScreen.Position;
import com.googlecode.lanterna.gui.Window;
import com.googlecode.lanterna.gui.component.Button;
import com.googlecode.lanterna.gui.component.EmptySpace;
import com.googlecode.lanterna.gui.component.Panel;
import structures.ItemPromo;

/**
 *
 * @author Obaro
 */
class ChoosePromotionView extends Window{
private final NewPromoView __parent;
private Button __btnBack;
private Button __btnItemPromotion;
private Button __btnPercentatgePromotion;
private Button __btnPriceBasePromotion;

    public ChoosePromotionView(String title,NewPromoView parent) {
        super(title);
        this.__parent=parent;
        this.__initGui();
    }

    private void __initGui() {
        
        Panel btnPanel= new Panel(Panel.Orientation.VERTICAL);
          btnPanel.addComponent(new EmptySpace());
        this.__btnBack= new Button("Disselect/[back]",()->{
            this.onBackClicked();
        });
         btnPanel.addComponent(this.__btnBack);
        btnPanel.addComponent(new EmptySpace());
        this.__btnItemPromotion= new Button("Item Base Promotion",()->{
            this.onItemBasePromoClicked();
        });
        btnPanel.addComponent(this.__btnItemPromotion);
          btnPanel.addComponent(new EmptySpace());
        
        this.__btnPercentatgePromotion= new Button("Percentage Base Promotion",()->{
            this.onPercentageBasePromoClicked();
        });
        btnPanel.addComponent(this.__btnPercentatgePromotion);
          btnPanel.addComponent(new EmptySpace());
        
         this.__btnPercentatgePromotion= new Button("Price Base Promotion",()->{
            this.onPriceBasePromoClicked();
        });
        btnPanel.addComponent(this.__btnPercentatgePromotion);
        btnPanel.addComponent(new EmptySpace());
        
        
       
        this.addComponent(btnPanel);
        
     }

    private void onBackClicked() {
        this.close();
         this.__parent.setPromotionType(100);
        this.__parent.getOwner().showWindow(this.__parent,Position.CENTER);
      }

    private void onItemBasePromoClicked() {
        this.__parent.setPromotionType(ItemPromo.ITEM_BASE);
        this.close();
        this.__parent.getOwner().showWindow(this.__parent, Position.CENTER);
    }

    private void onPercentageBasePromoClicked() {
        this.__parent.setPromotionType(ItemPromo.PERCENTAGE_BASE);
        this.close();
        this.__parent.getOwner().showWindow(this.__parent, Position.CENTER);
    }

    private void onPriceBasePromoClicked() {
         this.__parent.setPromotionType(ItemPromo.PRICE_BASE);
        this.close();
        this.__parent.getOwner().showWindow(this.__parent, Position.CENTER);
     }
    
}
