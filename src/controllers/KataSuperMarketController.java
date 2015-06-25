/*

 The class is the main controller of the Kata Super market and its may Job is to help coordinate events or activities of users interaction and
 carryout the business logic sending and data to model to process and retrieved then, send then back to the view for to display to users.

 */
package controllers;

import models.KataSuperMarketModel;
import views.KataSuperMarketView;

/**
 *
 * @author Obaro
 */
public class KataSuperMarketController extends AbstractExternalOperation 
implements InterfaceController<KataSuperMarketModel,KataSuperMarketView > {

    //create the model inner view and model, the suffix __ means its a private attributes identicator (just a style of coding)
    private final KataSuperMarketModel __model;
    private final KataSuperMarketView __view;

    public KataSuperMarketController(KataSuperMarketModel model, KataSuperMarketView view) {
          super();
        //initial the privite fields here
        this.attach(this);
        this.__model = model;
        this.__view = view;
       
        this.__model.attach(this);
        this.__view.attach(this);
        

    }

    //add the items into stocks(stock_items)
    @Override
    public void run() {
        //check if the model and view are not null values
        if (this.__model != null && this.__view != null) {
                     
            this.__view.show();
        }

    }

    @Override
    public KataSuperMarketModel getModel() {
        return this.__model;
    }

    @Override
    public KataSuperMarketView  getView() {
        return this.__view;
    }

}
