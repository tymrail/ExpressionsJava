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

}
//1234567