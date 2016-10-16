package com.company;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Vector;

public class MyDictionary<K,V> extends Dictionary<K,V> {

    private Vector<K> keys;
    private Vector<V> elements;

    public MyDictionary() {
        this.keys = new Vector<K>();
        this.elements = new Vector<V>();
    }

    public Enumeration<K> keys() {
        return this.keys.elements();
    }

    public Enumeration<V> elements() {
        return this.elements.elements();
    }

    public V get(Object key) {
        int i = keys.indexOf(key);
        return this.elements.get(i);
    }

    public K getKey(Integer i) {
        return this.keys.get(i);
    }

    public V getValue(Integer i) {
        return this.elements.get(i);
    }

    public boolean isEmpty() {
        return this.keys.size()== 0;
    }

    public V put(Object key, Object value) {
        int i = keys.indexOf(key);
        if (i != -1) {
            V oldElement = this.elements.elementAt(i);
            Object newValue = (Integer) value + (Integer)oldElement;
            this.elements.setElementAt((V) newValue, i);
            return oldElement;
        } else {
            this.keys.addElement((K) key);
            this.elements.addElement((V) value);
            return null;
        }
    }

    public V remove(Object key) {
        int i = this.keys.indexOf(key);
        if (i != -1) {
            V oldElement = this.elements.elementAt(i);
            this.keys.removeElementAt(i);
            this.elements.removeElementAt(i);
            return oldElement;
        } else{
            return null;
        }

    }

    public int size() {
        return this.keys.size();
    }

    public boolean isExist(Object key) {
        int i = keys.indexOf(key);
        if (i == -1)
            return false;
        else
            return true;
    }

    public static void main(String[] args) {
        MyDictionary<String,Integer> dic = new MyDictionary<>();
        dic.put("x", 7);
        dic.put("y", 2);
        dic.put("z", 3);
        dic.put("w", 4);
        dic.put("x", -8);
        System.out.println(dic.get("x") + dic.get("w"));
        System.out.println(dic.isExist("x"));
        Enumeration<String> keys = dic.keys();
        while(keys.hasMoreElements()){
            System.out.print(keys.nextElement() + " ");
        }
        System.out.println();
        Enumeration<Integer> elements = dic.elements();
        while(elements.hasMoreElements()){
            System.out.print(elements.nextElement() + " ");
        }
        System.out.println();
    }
}
