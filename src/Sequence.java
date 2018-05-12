/*
Filename: Sequence.java
Author: Shimer, Jacob D.

This is the utility class for Project 3.  It contains the classes computeRecursive, recursive, computeIterative, and
getEfficiency.
*/

import java.util.ArrayList;
import java.util.List;

class Sequence {
    private static int efficiency;

    //Initializes efficiency with the value of 0 then gets n and passes it to recursive
    static int computeRecursive(int n) {
        efficiency = 0;
        return(recursive(n));
    }

    //recursive receives the index of what the user wants and first checks if it is 0 or 1.  If it is 0 or 1, then it
    // just returns these values.  If it isn't, then it calls itself twice.  First it takes n-1 and multiplies that by 2
    // and then it calls itself again, this time adding n-2 to the first call.
    private static int recursive(int n) {
        if (n == 0) {
            efficiency++;
            return(0);
        } else if (n == 1) {
            efficiency++;
            return(1);
        } else {
            efficiency++;
            return(recursive(n-1)*2 + recursive(n-2));
        }
    }

    //computeIterative checks if n is 0 or 1. If it is, then it just adds those values to the list "values" and then
    // returns the digit at the nth index.  If it isn't 0 or 1, then it runs through a for loop that takes assigns the
    // value of x-1 to previous and x-2 to secondPrev.  Then it multiplies previous by 2 and adds secondPrev to it.
    static String computeIterative(int n) {
        List<Integer> values = new ArrayList<>();
        efficiency = 0;
        if (n==0) {
            values.add(0);
            efficiency++;
            return(String.valueOf(values.get(n)));
        } else if(n==1) {
            values.add(0);
            values.add(1);
            efficiency++;
            return(String.valueOf(values.get(n)));
        } else {
            values.add(0);
            values.add(1);
            for(int x = 2; x<= n; x++) {
                int previous = values.get((x-1));
                int secondPrev = values.get((x-2));
                values.add(((previous * 2) + secondPrev));
                efficiency = x;
            }

            return(String.valueOf(values.get(n)));
        }
    }
    //getEfficiency only returns the value of efficiency.
    static int getEfficiency() {
        return efficiency;
    }
}
