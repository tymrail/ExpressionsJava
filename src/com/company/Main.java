package com.company;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String input = "";
        String exp = "";
        Scanner scan = new Scanner(System.in);
        for (;;) {
            input = scan.nextLine();
            if (input.equals("exit"))
                break;
            String output = Main.dealInput(input,exp);
            if (output.contains("!")) {
                exp = output.replace("!","");
                output = exp;
            }
            System.out.println(output);
        }
        scan.close();
    }

    public static String dealInput(String input,String exp) {
        plusMinusExpressions<String,Integer,String,Integer> tempPme = new plusMinusExpressions<>();
        if (input.substring(0,1).equals("!")) {
            if (input.substring(0,4).equals("!d/d")) {
                System.out.println("d/d");
                tempPme.expressionInit(exp);
                tempPme.plusMinusCollect(exp);
                input = input.replaceAll(" ","");
                String output = tempPme.derivative(input.replaceFirst("!d/d",""));
                return output;
            }
            else if (input.substring(0,9).equals("!simplify")) {
                tempPme.expressionInit(exp);
                tempPme.plusMinusCollect(exp);
                String[] chars = new String[1000];
                Integer[] pows = new Integer[1000];
                input = input.replaceAll("!simplify ","").replaceAll("!simplify","");
                String[] tempC = input.split("=|,| ");
                for (int i = 0,j = 0; i < tempC.length; i += 2, j++) {
                    chars[j] = tempC[i];
                    pows[j] = Integer.parseInt(tempC[i+1]);
                }
                String output = tempPme.simplify(chars,pows);
                return output;
            }
            else {
                System.out.println("Wrong operate.");
                return "!wrong";
            }
        }
        else {
            exp = input.replaceAll(" ","");
            tempPme.expressionInit(exp);
            tempPme.plusMinusCollect(exp);
            String output = "!" + tempPme.expression(tempPme.getExp(),tempPme.getConnector());
            return output;
        }
    }
}
