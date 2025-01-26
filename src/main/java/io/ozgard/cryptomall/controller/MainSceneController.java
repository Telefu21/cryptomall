package io.ozgard.cryptomall.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import net.rgielen.fxweaver.core.FxmlView;

@Component
@FxmlView("../view/mainscene.fxml")
public class MainSceneController implements Initializable
{
	@FXML
	TextField textFieldWorkingDirectory = new TextField();

	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{

	}
	
	@FXML
	void browseWorkingDirectory()
	{
		textFieldWorkingDirectory.setText("Deneme");
	}
}
