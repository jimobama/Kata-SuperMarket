package structures;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import controllers.InterfaceController;

/**
 *
 * @author Obaro
 */
public interface InterfaceObserver {

    void attach(InterfaceController controller);

    InterfaceController getController();

}
