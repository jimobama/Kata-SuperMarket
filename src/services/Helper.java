/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Obaro
 */
public class Helper {

    public static String generateId(int i) {

        String id = "";
        for (int c = 0; c < i; c++) {

            int choose = Validator.uniqueNumber(0, 1);

            id += String.valueOf(Validator.uniqueNumber(0, 9));

        }
        return id;

    }

    public static boolean isNamingFormat(String name) {
        boolean okay = false;
        String pattern = "^[a-zA-Z_]+[a-zA-Z0-9 ]*$";
        if (Validator.isMatch(pattern, name)) {
            okay = true;
        }
        return okay;
    }

    public static String generateId(String sl, int i) {
        String id = sl.trim();
        int len = sl.trim().length();
        int size = i - len;
        if (size <= 0) {
            return id;
        }

        id += generateId(size);
        return id;
    }

    public static class Validator {

        static boolean isMatch(String pattern, String value) {
            boolean isokay = false;
            // matched the give
            // value with
            Pattern p = Pattern.compile(pattern); // This method compile the pattern
            // to the understand of Java
            Matcher m = p.matcher(value.trim()); // this object create a matcher
            // that will match the value
            // give
            if (m.matches()) {
                isokay = true;
            }

            return isokay;
        }

        static int uniqueNumber(int lower, int upper) {

            Random rand = new Random();
            int n2 = (lower + rand.nextInt(upper));

            return n2;
        }
    }

}
