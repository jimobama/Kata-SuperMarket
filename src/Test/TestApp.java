/*
 The application is using the simple MVC pattern to model the data abstraction 
 Where the model handles everything that will deal with data processing and the view will be reponsible of displaying 
 information to the users and the controller will handle the business logics and coordinations
 */
package Test;

import controllers.KataSuperMarketController;
import models.KataSuperMarketModel;
import views.KataSuperMarketView;

/**
 *
 * @author Obaro This is the main class that will run while the application is
 * executed
 */
public class TestApp {

    static boolean okay = true;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        run();

    }

    private static void run() {
        KataSuperMarketController controller;
        controller = new KataSuperMarketController(new KataSuperMarketModel(), new KataSuperMarketView("Kata Super Market 0.1 "));
        controller.run();
        //System.out.println( Helper.generateId(10));

    }

}
