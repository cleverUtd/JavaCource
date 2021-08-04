package com.zclau.classloader;

/**
 * Created by liuzicong on 13/5/2017.
 */
public class PrintClassLoaderTree {

    public static void main(String[] args) {
        ClassLoader cl = PrintClassLoaderTree.class.getClassLoader();
        while (cl != null) {
            System.out.println(cl);
            cl = cl.getParent();
        }
    }
}
