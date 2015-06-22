/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import com.googlecode.lanterna.gui.Action;
import com.googlecode.lanterna.gui.Window;
import com.googlecode.lanterna.gui.component.Button;
import com.googlecode.lanterna.gui.component.Panel;

/**
 *
 * @author Obaro
 */
abstract class SaleManagerCommandView extends Window {

    private Button btnProcessItemForSale;
    private Button btnback;
    private Button btnDiscount;
    private Button btnCreatePromoGroup;
  
    
    
    public SaleManagerCommandView(String title) {

        super(title);
         btnProcessItemForSale= new Button("Process Item for sale", new Action(){

            @Override
            public void doAction() {
                onProcessClicked();
            }
             
         });
         
         btnback = new Button("Back",new Action(){
             @Override
            public void doAction() {
                onBackClicked();
            }
         });
         
         btnCreatePromoGroup= new Button("Create Item Group",new Action(){

            @Override
            public void doAction() {
                onCreatePromoClicked();
            }
             
         });
         btnDiscount = new Button("Apply Item Discount", new Action(){

            @Override
            public void doAction() {
                onDiscountClicked();
            }
             
         });
         
        
         panel.addComponent(this.btnCreatePromoGroup);
         panel.addComponent(this.btnDiscount);
         panel.addComponent(this.btnProcessItemForSale);
         panel.addComponent(this.btnback);
         
         this.addComponent(panel);
         
    }
  
  Panel panel = new Panel(Panel.Orientation.VERTICAL);
    
 
  abstract  void   onProcessClicked();
  abstract  void   onBackClicked();
  abstract  void   onDiscountClicked();
  abstract void    onCreatePromoClicked();
  
  

}
