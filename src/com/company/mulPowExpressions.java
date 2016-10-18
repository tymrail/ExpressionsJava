package com.company;

import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class mulPowExpressions<F,K,P> {
    public Vector<F> factor;
    public MyDictionary<K,P> alphabet = new MyDictionary<>();

    public mulPowExpressions() {
        this.factor = new Vector<>();
        this.alphabet = new MyDictionary<>();
    }

    public P getValue(K key) {
        return alphabet.get(key);
    }

    public boolean isExist(K key) {
        return alphabet.isExist(key);
    }

    public P remove(K key) {
        return alphabet.remove(key);
    }

    public static String[] strMulSplit(String str) {
        String[] strs = str.split("\\*");
        return strs;
    }

    public Vector<F> getFactor() {
        return factor;
    }

    public MyDictionary<K,P> getDic() {
        return alphabet;
    }

    public void minusPower(K charactor) {

        this.alphabet.put(charactor, -1);
        if ((Integer)this.alphabet.get(charactor) == 0) {
            this.factor.addElement((F)(Integer) 1);
            this.alphabet.remove(charactor);
        }
    }

    public static String[] strPowerSplit(String str) {
        String[] strs = str.split("\\^");
        return strs;
    }

    public static Integer Pow(Integer x, Integer y) {
        Integer result = 1;
        for (; y > 0; y--) {
            result *= x;
        }
        return result;
    }

    public Integer powerPower(String[] strs, int start){
        if (start >= strs.length) {
            return 1;
        }
        Integer result = StringToInt(strs[start]);
        for (int j = start+1; j < strs.length; j++) {
            result = Pow(result,StringToInt(strs[j]));
        }
        return result;
    }

    public boolean init(String str) {
        String[] strs = strMulSplit(str);
        List<dicSet> list = new ArrayList<>();

        for (int i = 0; i < strs.length; i++) {
            if (strs[i].equals("")) {
                System.out.println("There's a * without any character");
                return false;
            }
            if (isPower(strs[i])) {
                if (isDpow(strs[i])) {
                    String[] nums = strPowerSplit(strs[i]);
                    Integer factorNum = Pow(StringToInt(nums[0]),powerPower(nums, 1));
                    this.factor.addElement((F) factorNum);
                }
                else if (isWpow(strs[i])) {
                    String[] exps = strPowerSplit(strs[i]);
                    Integer factorNum = Pow(StringToInt(exps[1]),powerPower(exps, 2));
                    /*initLetter(exps[0],factorNum);*/
                    dicSet set = new dicSet();
                    set.setAll(exps[0],factorNum);
                    list.add(set);
                }
                else {
                    System.out.println("Error in " + strs[i]);
                    return false;
                }
            }
            else if (isAlpha(strs[i])) {
                dicSet set = new dicSet();
                set.setAll(strs[i],1);
                list.add(set);
            }
            else if (isNumeric(strs[i])) {
                initFactor(strs[i]);
            }
            else {
                System.out.println("Error in " + strs[i]);
                return false;
            }
        }
        setSort(list);
        for (dicSet s : list) {
            initLetter(s.getCharactor(),s.getPower());
        }

        return true;
    }

    public static Integer StringToInt(String str) {
        return Integer.parseInt(str);
    }

    public void initFactor(String ch) {
        Integer tmp = StringToInt(ch);
        this.factor.addElement((F) tmp);
    }

    public void setSort(List<dicSet> list) {
        Collections.sort(list, (arg0, arg1) -> arg0.getCharactor().toString().compareTo(arg1.getCharactor().toString()));
    }

    public void initLetter(String ch, Integer power) {
        alphabet.put(ch, power);
    }

    public static boolean isPower(String str) {
        Pattern pattern = Pattern.compile("\\^");
        Matcher isPow = pattern.matcher(str);
        return isPow.find();
    }

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

    public static boolean isAlpha(String str) {
        Pattern pattern = Pattern.compile("[a-zA-Z]*");
        Matcher isAB = pattern.matcher(str);
        return isAB.matches();
    }

    public static boolean isWpow(String str) {
        Pattern ptn = Pattern.compile("\\w+\\^\\d+(\\^\\d+)*");
        Matcher iswPow = ptn.matcher(str);
        return iswPow.matches();
    }

    public static boolean isDpow(String str) {
        Pattern ptn = Pattern.compile("\\d+\\^\\d+(\\^\\d+)*");
        Matcher isdPow = ptn.matcher(str);
        return isdPow.matches();
    }

    public dicSet dealExpression() {
        Integer finalFac = 1;
        Vector<F> facs = this.factor;
        MyDictionary<K,P> dic = this.alphabet;
        dicSet set = new dicSet();
        for (F num : facs) {
            finalFac *= (Integer) num;
        }
        String result = new String();
        if (!dic.isEmpty()) {
            result = dic.getKey(0).toString();
            if ((Integer)dic.getValue(0)!=1)
                result += "^" + dic.getValue(0).toString();
            for (int i = 1; i < dic.size(); i++) {
                result += "*" + dic.getKey(i).toString();
                if ((Integer)dic.getValue(i)!=1)
                    result += "^" + dic.getValue(i).toString();
            }
        }

        set.setAll(result,finalFac);
        return set;
    }


    public static void main(String[] args) {
        mulPowExpressions<Integer,String,Integer> exp = new mulPowExpressions<>();
        exp.init("z*y^2^2*a^2*xyz^2^2");
        /*for (int i = 0; i < exp.factor.size(); i++) {
            System.out.println(exp.factor.get(i));
        }
        System.out.println();
        Enumeration<String> keys = exp.alphabet.keys();
        while(keys.hasMoreElements()){
            System.out.print(keys.nextElement() + " ");
        }
        System.out.println();
        Enumeration<Integer> elements = exp.alphabet.elements();
        while(elements.hasMoreElements()){
            System.out.print(elements.nextElement() + " ");
        }
        System.out.println();
        System.out.println(exp.getValue("y"));*/
    }
}
