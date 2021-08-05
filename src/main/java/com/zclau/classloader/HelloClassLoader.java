package com.zclau.classloader;

import java.util.Base64;

/**
 * @author liuzicong
 */
public class HelloClassLoader extends ClassLoader {

    public static void main(String[] args) throws Exception {
        new HelloClassLoader().findClass("com.zclau.classloader.Hello").newInstance();
    }


    @Override
    protected Class<?> findClass(final String name) throws ClassNotFoundException {
        String helloBase64 = "yv66vgAAADQAHAoABgAOCQAPABAIABEKABIAEwcAFAcAFQEABjxpbml0PgEAAygpVgEABENvZGUB" +
                "AA9MaW5lTnVtYmVyVGFibGUBAAg8Y2xpbml0PgEAClNvdXJjZUZpbGUBAApIZWxsby5qYXZhDAAH" +
                "AAgHABYMABcAGAEAGEhlbGxvIENsYXNzIEluaXRpYWxpemVkLgcAGQwAGgAbAQAbY29tL3pjbGF1" +
                "L2NsYXNzbG9hZGVyL0hlbGxvAQAQamF2YS9sYW5nL09iamVjdAEAEGphdmEvbGFuZy9TeXN0ZW0B" +
                "AANvdXQBABVMamF2YS9pby9QcmludFN0cmVhbTsBABNqYXZhL2lvL1ByaW50U3RyZWFtAQAHcHJp" +
                "bnRsbgEAFShMamF2YS9sYW5nL1N0cmluZzspVgAhAAUABgAAAAAAAgABAAcACAABAAkAAAAdAAEA" +
                "AQAAAAUqtwABsQAAAAEACgAAAAYAAQAAAAYACAALAAgAAQAJAAAAJQACAAAAAAAJsgACEgO2AASx" +
                "AAAAAQAKAAAACgACAAAACAAIAAkAAQAMAAAAAgAN";
        byte[] bytes = decode(helloBase64);
        return defineClass(name, bytes, 0, bytes.length);
    }

    public byte[] decode(String base64) {
        return Base64.getDecoder().decode(base64);
    }

}
