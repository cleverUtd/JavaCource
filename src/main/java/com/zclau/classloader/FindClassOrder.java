package com.zclau.classloader;

/**
 * Created by liuzicong on 14/5/2017.
 */
public class FindClassOrder {

    public static void main(String[] args) throws ClassNotFoundException {
        HelloLoader helloLoader = new HelloLoader();
        helloLoader.print();

        
    }
}
