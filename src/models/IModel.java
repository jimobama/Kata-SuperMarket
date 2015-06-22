/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import structures.IError;
import controllers.InterfaceController;
import structures.InterfaceObserver;

/**
 *
 * @author Obaro
 */
public abstract class IModel extends IError implements InterfaceObserver {

    @Override
    public void attach(InterfaceController controller) {

    }

}
