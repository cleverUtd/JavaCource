package com.zclau.classloader;

/**
 * Created by liuzicong on 14/5/2017.
 */
public class HelloLoader {

    static {
        System.out.println("static init........");
    }

    public void print() {
        System.out.println("I am  in app classloader");
    }
}
