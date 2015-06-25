/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

/**
 *
 * @author Obaro
 * @param <TModel>
 */
public interface InterfaceController<TModel, TView> {

    void run();
    public abstract TModel getModel();
    public abstract TView getView();
    

}
