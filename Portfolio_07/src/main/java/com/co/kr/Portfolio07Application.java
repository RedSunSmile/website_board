package com.co.kr;



import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;

@SpringBootApplication
public class Portfolio07Application {

	public static void main(String[] args) {
		SpringApplication.run(Portfolio07Application.class, args);
	}
	  

	public static class CustomGenerator extends AnnotationBeanNameGenerator {
		    @Override
		    public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
		      return definition.getBeanClassName();
		    }
	  }
}
