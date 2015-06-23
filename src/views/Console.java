/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.util.Scanner;

public class Console {

    public static Scanner console = new Scanner(System.in);

    public static String askString(String aPrompt) // method for string input
    {
        System.out.print(aPrompt); // aPrompt is a request for string input
        String reply = console.nextLine(); // input held in a variable called reply
        return reply;
    }

    public static int askInt(String aPrompt) // method for integer input
    {
        return (int) askDouble(aPrompt);

    }

    public static double askDouble(String aPrompt) // method for double input
    {
        String reply = askString(aPrompt);
        try {
            double d = Double.parseDouble(reply); // parseDouble converts string to double
            return d;
        } catch (NumberFormatException err) {
            return 0.0;
        }

    }
// method for one character input for menu options

    public static char askOption(String aMenu) {
        WriteLn(aMenu);
        String reply = askString("Enter option: ");
        if (reply.trim().length() == 0) // checks for null input
        {
            return '\0';
        } else // Take first character of string and convert to Uppercase
        {
            return Character.toUpperCase(reply.trim().charAt(0));
        }
    }
// method for one character input

    public static char askChar(String aChar) {
        String reply = askString(aChar);
        if (reply.trim().isEmpty()) {
            return (char) 0;
        }
// Take first character of string and convert to Uppercase
        return reply.trim().charAt(0);
    }

    public static void WriteLn(String msg) {
        Write(msg + "\n");
    }

    public static void Write(String msg) {
        System.out.print(msg);
    }

    public static void clear() {

        System.out.print("\f");
        //if this did not work then 
        try {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                Runtime.getRuntime().exec("cls");
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (final Exception e) {
            //  Handle any exceptions.
        }

    }

    public static void exit(int code) {
        System.exit(code);
    }

    static void WriteLn(int counter) {
        
        WriteLn(""+counter);
         }
}
