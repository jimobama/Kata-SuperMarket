/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import com.googlecode.lanterna.gui.Border;
import com.googlecode.lanterna.gui.Window;
import com.googlecode.lanterna.gui.component.Button;
import com.googlecode.lanterna.gui.component.Panel;

/**
 *
 * @author Obaro
 */
class NewPromoView extends Window {
   private  SaleGroupView __parent;
   private Panel __pnlMain;
   private  Button __btnBack;
   private Button __btnCreate;
   
   
    public NewPromoView(String title,SaleGroupView parent) {
        super(title);
        this.__parent=parent;
        
        this.__initGui();
    }

    private void __initGui() {
        
        this.__pnlMain= new Panel(Panel.Orientation.VERTICAL);
        
        
        Panel pnlButtons = new Panel(new Border.Bevel(true), Panel.Orientation.HORISONTAL);
        this.__btnBack= new Button("Back", ()->{
            this.onBackClicked();
        });
        pnlButtons.addComponent(this.__btnBack);
        
        this.__pnlMain.addComponent(pnlButtons);        
        this.addComponent(this.__pnlMain);
        
     }

    private void onBackClicked() {
        this.close();
        if(this.__parent !=null){
            this.__parent.getOwner().showWindow(this.__parent);
        }
      }
    
    
}
