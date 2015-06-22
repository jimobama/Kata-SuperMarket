/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import com.googlecode.lanterna.gui.GUIScreen.Position;
import controllers.InterfaceController;
import controllers.KataSuperMarketController;

/**
 *
 * @author Obaro
 */
class SaleManagerView extends SaleManagerCommandView implements IView {

   
    private KataSuperMarketView __parent;
    private KataSuperMarketController __controller;

    public SaleManagerView(String title, IView parent)
    {
       super(title);
        __parent= (KataSuperMarketView)parent;
        
    }



    @Override
    public void setModel(Object model) {
     
    }

    @Override
    public void show() {
      if(this.__parent !=null){
          this.__parent.getOwner().getActiveWindow().close();
          this.__parent.getOwner().showWindow(this,Position.CENTER);          
      }
    }

    @Override
    public void attach(InterfaceController controller) {
     this.__controller = (KataSuperMarketController) controller;
    }

    @Override
    public InterfaceController getController() {
        return this.getController();
    }

    @Override
    void onProcessClicked() {
      this.__parent.getOwner().getActiveWindow().close();
      
    }

    @Override
    void onBackClicked() {
        this.getOwner().getActiveWindow().close();
        this.__parent.getOwner().showWindow(this.__parent);
    }

    @Override
    void onDiscountClicked() {
     }

    @Override
    void onCreatePromoClicked() {
        
        
     
    }

}
