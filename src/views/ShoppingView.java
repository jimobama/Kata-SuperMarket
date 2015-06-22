/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import controllers.InterfaceController;
import controllers.KataSuperMarketController;

/**
 *
 * @author Obaro
 */
class ShoppingView implements IView {

    private KataSuperMarketController __controller;

    @Override
    public void setModel(Object model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void show() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void attach(InterfaceController controller) {

        this.__controller = (KataSuperMarketController) controller;
    }

    @Override
    public InterfaceController getController() {
        return this.__controller;
    }

}
