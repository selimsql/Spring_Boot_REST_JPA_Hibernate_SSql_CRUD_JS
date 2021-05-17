package com.selimsql.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"com.selimsql.springboot"})
public class AppStarter {

   private static final Class<?> APP_CLASS = AppStarter.class;
   private static final String   APP_NAME  = APP_CLASS.getSimpleName();

   public static void main(String[] args) {
     final String msg = "-------------------------\n" + APP_NAME + ".main";
     System.out.println(msg);

     SpringApplication springApp = new SpringApplication(APP_CLASS);
     springApp.run(args);

     System.out.println("SpringApplication.run:" + APP_NAME + " STARTED.");
  }
}
