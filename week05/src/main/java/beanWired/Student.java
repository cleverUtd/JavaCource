package beanWired;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component("student101")
public class Student {

  private String name = "student101";

  public Student() {}

  public Student(String name) {
    this.name = name;
  }

  public void speak() {
    System.out.println("I am " + this.getName());
  }

}
