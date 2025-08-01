package io.ozgard.cryptomall;

import io.ozgard.cryptomall.event.StageReadyEvent;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class JavaFxApp extends Application
{
    private ConfigurableApplicationContext context;

    @Override
    public void init() 
    {
        ApplicationContextInitializer<GenericApplicationContext> initializer =
                context -> 
        		{
                    context.registerBean(Application.class, () -> JavaFxApp.this);
                    context.registerBean(Parameters.class, this::getParameters);
                    context.registerBean(HostServices.class, this::getHostServices);
                    context.registerBean(TextField.class);
                    context.registerBean(TextArea.class);
                    context.registerBean(ComboBox.class);
                    context.registerBean(Button.class);
                    context.registerBean(TitledPane.class);
                    context.registerBean(CheckBox.class);
                    context.registerBean(Tab.class);
                    context.registerBean(RadioButton.class);
                    context.registerBean(ToggleGroup.class);
                    context.registerBean(TableView.class);
                    context.registerBean(TableColumn.class);
                    context.registerBean(Label.class);
                };
                
        this.context = new SpringApplicationBuilder()
        					.sources(SpringBootApp.class)
        					.initializers(initializer)
        					.run(getParameters().getRaw().toArray(new String[0]));
    }

    @Override
    public void start(Stage primaryStage) throws Exception 
    {
        this.context.publishEvent(new StageReadyEvent(primaryStage));
    }

    @Override
    public void stop() 
    {
        this.context.close();
        Platform.exit();
    }

}
