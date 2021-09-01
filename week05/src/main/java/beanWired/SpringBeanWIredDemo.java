package beanWired;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringBeanWIredDemo {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

        /**
         * 方式一：通过配置@ComponentScan(basePackages = "beanWired")来启动扫描
         */
        Student student101 = (Student) applicationContext.getBean("student101");
        student101.speak();

        /**
         * 方式二：基于Java的显式配置，通过@Bean手动创建Bean
         */
        Student student102 = (Student) applicationContext.getBean("student102");
        student102.speak();

        /**
         * 方式三：利用BeanDefinitionRegistryPostProcessor，定义beanDefinition
         */
        Student student103 = (Student) applicationContext.getBean("student103");
        student103.speak();

        Student student104 = (Student) applicationContext.getBean("student104");
        student104.speak();
    }
}
