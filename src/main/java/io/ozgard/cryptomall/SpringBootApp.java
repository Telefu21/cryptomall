package io.ozgard.cryptomall;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import javafx.application.Application;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.spring.SpringFxWeaver;

@SpringBootApplication
public class SpringBootApp 
{
	public static void main(@Value("${version}") String[] args) 
	{
		Application.launch(JavaFxApp.class, args);
	}

    @Bean
    FxWeaver fxWeaver(ConfigurableApplicationContext applicationContext) 
	{
        return new SpringFxWeaver(applicationContext); 
    }
}
