package com.zclau.classloader;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

public class HelloXClassLoader extends ClassLoader{

    public static void main(String[] args) throws Exception {
        Class<?> clazz = new HelloXClassLoader().loadClass("Hello");
        Object instance = clazz.newInstance();
        // 调用实例方法
        Method method = clazz.getMethod("hello");
        method.invoke(instance);
    }


    @Override
    protected Class<?> findClass(final String name) throws ClassNotFoundException {
        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(name + ".xlass")){
            int length = is.available();
            byte[] bytes = new byte[length];
            is.read(bytes);
            byte[] decodeBytes = decode(bytes);
            return defineClass(name, decodeBytes, 0, bytes.length);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            throw new ClassNotFoundException(name, ioe);
        }
    }

    public byte[] decode(byte[] bytes) {
        byte[] decodeBytes = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            decodeBytes[i] = (byte) (255 - bytes[i]);
        }
        return decodeBytes;
    }
}
