package io.ozgard.cryptomall.event;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import io.ozgard.cryptomall.controller.MainSceneController;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;

@Component
public class StageReadyEventListener implements ApplicationListener<StageReadyEvent> 
{

    private final ApplicationContext 	applicationContext;
    private final String 				applicationTitle;

    public StageReadyEventListener(ApplicationContext applicationContext, @Value("${main_ui_title}") String applicationTitle) 
    {
        this.applicationTitle = applicationTitle;
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) 
    {	
    	String javaFxEnabled = "true";
		
		try 
		{
			Properties appProps = new Properties();
			
			FileInputStream fs = new FileInputStream("src/main/resources/application.properties");
			
			appProps.load(fs);
			
			javaFxEnabled = appProps.getProperty("javafxenabled");
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}

		if (javaFxEnabled.compareTo("false") == 0)
		{
			return;
		}
		
		Stage stage = event.getStage();
		FxWeaver fxWeaver = applicationContext.getBean(FxWeaver.class);
		Parent root = fxWeaver.loadView(MainSceneController.class);
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("../view/mainscene.css").toExternalForm());
		stage.setScene(scene);
		stage.setTitle(this.applicationTitle);
		stage.show();
    }
}
