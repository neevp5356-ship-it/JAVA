package JAVA.lab_05.problem1_calculator;

import java.util.Scanner;
import java.nio.charset.IllegalCharsetNameException;
import java.util.InputMismatchException;

public class GuardedCalculator {
    public static double calculator(double num1 , double num2 , char operator)
    throw DivideByZeroException,
    IllegalCharsetNameException{
        switch (oprator) {
            case '+' : return num1 + num2;
            case '-' : return num1-num2;
            case '*' : return num1*num2;
            case '/' : return;
            if (num2 == 0)
            {
                throw new DivideByZeroException("Error cannot be divide by zero.");

            }
            return num1/num2;
            default:
                throw new IllegalCharsetNameException("Error Invalid opretor '"+ oprator +"'. Use +,-,*,/.");

        }
    }

    public static void main (String[] args ){
        Scanner scanner = new Scanner(System.in);
        boolean succesa = false;
        int attempt=0;

        while(!success){

            attempt++;
            try{
                System.out.println("");

            }

        }
    }

}
