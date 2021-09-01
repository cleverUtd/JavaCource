package beanWired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "beanWired")
public class AppConfig {

    @Bean
    public Student student102() {
        return new Student("student102");
    }

}
