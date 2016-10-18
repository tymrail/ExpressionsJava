package com.company;

import java.util.Vector;

public class plusMinusExpressions<S,F,K,P> {
    private Vector<S> connector;
    private Vector<mulPowExpressions<F,K,P>> exps;

    public plusMinusExpressions() {
        this.connector = new Vector<>();
        this.exps = new Vector<>();
    }

    public Vector getConnector() {
        return this.connector;
    }

    public Vector getExp() {
        return this.exps;
    }

    public boolean plusMinusCollect(String str) {
        for(int i = 0; i < str.length(); i++){
            String subStr = str.substring(i, i+1);
            if (subStr.equals("+")|| subStr.equals("-")) {
                if (i != 0 && connector.size() == 0)
                    this.connector.addElement((S) "+");
                if (i == str.length() - 1) {
                    System.out.println("The last character is " + subStr);
                    return false;
                }
                this.connector.addElement((S) subStr);
            }
        }
        return true;
    }

    public boolean expressionInit(String str) {
        String[] strs = str.split("\\+|-");
        for (int i = 0; i < strs.length; i++) {
            if (strs[i].equals(""))
                if (i == 0)
                    continue;
                else {
                    System.out.println("There's an empty expression between two +s");
                    return false;
                }
            mulPowExpressions<F, K, P> expsTemp = new mulPowExpressions<>();
            if (!expsTemp.init(strs[i])) {
                System.out.println("Wrong in " + strs[i]);
                return false;
            }
            this.exps.addElement(expsTemp);
        }
        return true;
    }

    public String simplify(K[] charactors, P[] value) {
        String result = "";
        Vector<mulPowExpressions<F,K,P>> expsTemp = exps;
        Vector<S> cons = connector;
        for (mulPowExpressions expTemp : expsTemp) {
            for (int i = 0; i < charactors.length; i++) {
                if (expTemp.isExist(charactors[i])) {
                    expTemp.initFactor(expTemp.Pow((Integer) value[i], (Integer) expTemp.remove(charactors[i])).toString());
                }
            }
        }
        result = expression(expsTemp,cons);
        return result;
    }

    public String derivative(K character) {
        String result = "";
        Vector<mulPowExpressions<F,K,P>> expsTemp = exps;
        Vector<S> cons = connector;

        for (int i = 0; i < expsTemp.size(); i++) {
            mulPowExpressions<F,K,P> expTemp = expsTemp.get(i);

            if (expTemp.isExist(character)) {
                expTemp.initFactor(expTemp.getValue(character).toString());
                expTemp.minusPower(character);
            }
            else {
                expTemp.initFactor("0");
            }
        }
        result = expression(expsTemp,cons);
        return result;
    }

    public String expression(Vector<mulPowExpressions<F,K,P>> tempExps,Vector<S> cons) {
        MyDictionary<K,P> finalDic = new MyDictionary<>();
        String result = new String();
        //dicSet set = new dicSet();
        if (cons.isEmpty()) {
            dicSet set = tempExps.get(0).dealExpression();
            finalDic.put(set.getCharactor(),set.getPower());
        }
        else {
            for (int i = 0; i < tempExps.size(); i++) {
                dicSet set = tempExps.get(i).dealExpression();
                if (cons.get(i).equals("-"))
                    finalDic.put(set.getCharactor(),-set.getPower());
                else
                    finalDic.put(set.getCharactor(),set.getPower());
            }
        }

        for (int j = 0; j < finalDic.size(); j++) {
            if (finalDic.getKey(j).toString().equals("") && (Integer) finalDic.getValue(j) != 0)
                result += "+" + finalDic.getValue(j);
            else if ((Integer) finalDic.getValue(j) < 0)
                result += finalDic.getValue(j).toString() + "*" + finalDic.getKey(j).toString();
            else if ((Integer) finalDic.getValue(j) == 0)
                continue;
            else if ((Integer) finalDic.getValue(j) == 1)
                result += "+" + finalDic.getKey(j).toString();
            else
                result += "+" + finalDic.getValue(j).toString() + "*" + finalDic.getKey(j).toString();
        }
        result = dealString(result);

        return result;
    }

    public static String dealString(String str) {
        if (str.length() > 1 && str.substring(0,1).equals("+")){
            str = str.replaceFirst("\\+","");
        }
        return str;
    }

    public static void main(String[] args) {
        String s = "3*x-4*y^2";
        plusMinusExpressions<String,Integer,String,Integer> pme = new plusMinusExpressions<>();
        mulPowExpressions<Integer,String,Integer> pmeTemp = new mulPowExpressions<>();

        if (!pme.plusMinusCollect(s)) {
            System.out.println("Wrong");
            return;
        }

        else if (!pme.expressionInit(s)) {
            System.out.println("Please Enter Expressions Again.");
            return;
        }

        /*Vector<String> V = pme.connector;

        for (String v : V) {
            System.out.print(v);
        }
        System.out.println();

        pmeTemp = pme.exps.get(1);
        for (int j = 0; j < pmeTemp.factor.size(); j++) {
            System.out.println(pmeTemp.factor.get(j));
        }
        System.out.println();
        Enumeration<String> keys = pmeTemp.alphabet.keys();
        while(keys.hasMoreElements()){
            System.out.print(keys.nextElement() + " ");
        }
        System.out.println();
        Enumeration<Integer> elements = pmeTemp.alphabet.elements();
        while(elements.hasMoreElements()){
            System.out.print(elements.nextElement() + " ");
        }
        System.out.println();*/
        /*String[] sss = {"y"};
        Integer[] ppp = {1};
        String res = pme.simplify(sss,ppp);
        System.out.println(res);*/
        String ress = pme.derivative("y");
        System.out.println(dealString(ress));
    }
}
