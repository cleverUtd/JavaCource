package com.zclau.spring.boot;

import com.zclau.spring.boot.bean.Klass;
import com.zclau.spring.boot.bean.School;
import com.zclau.spring.boot.bean.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(SchoolProperties.class)
@ConditionalOnClass({Klass.class, Student.class, School.class})
@ConditionalOnProperty(prefix = "spring.school", value = "enabled", matchIfMissing = true)
public class SchoolAutoConfiguration {

    @Autowired
    private SchoolProperties schoolProperties;

    @Bean
    @ConditionalOnMissingBean(Klass.class)
    public Klass klass() {
        Klass klass = new Klass();
        klass.setStudents(schoolProperties.getStudents());
        return klass;
    }

    @Bean
    @ConditionalOnMissingBean(School.class)
    public School school(Klass klass) {
        School school = new School();
        school.setClass1(klass);
        school.setName(schoolProperties.getName());
        return school;
    }

}
