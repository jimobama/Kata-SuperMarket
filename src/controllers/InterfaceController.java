/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import models.IModel;
import views.IView;

/**
 *
 * @author Obaro
 */
public interface InterfaceController {

    void run();

    public abstract IModel getModel();

    public abstract IView getView();

}
