package jol;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author liuzicong
 */
public class JolTest {

    public static void main(String[] args) {
        Person p = new Person("hello", 1);
        System.out.println(ClassLayout.parseInstance(p).toPrintable());
    }
}
