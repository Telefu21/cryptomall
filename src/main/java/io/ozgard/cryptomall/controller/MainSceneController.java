package io.ozgard.cryptomall.controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;

@Component
@FxmlView("../view/mainscene.fxml")
public class MainSceneController implements Initializable
{
	private static Stage stage = null;
	
	@FXML
	@Autowired
	TextField textFieldWorkingDirectory;
	
	@FXML
	@Autowired
	ComboBox<String> comboKeyGenAlgSelect;
	
	@FXML
	@Autowired
	ComboBox<String> comboRSAKeyLength;
	
	@FXML
	@Autowired
	ComboBox<String> comboKeyFileFormat;
	
	static public void setStage(Stage stageT)
	{
		stage = stageT;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{			
		comboKeyFileFormat.setItems(FXCollections.observableArrayList("PEM", "DER"));
		comboKeyGenAlgSelect.setItems(FXCollections.observableArrayList("RSA", "ECC"));
		comboRSAKeyLength.setItems(FXCollections.observableArrayList("1024-bit", "2048-bit", "4096-bit"));
	}
	
	@FXML
	void browseWorkingDirectory()
	{
		DirectoryChooser directoryChooser = new DirectoryChooser();
		
		directoryChooser.setTitle("Select Working Directory");
		
		final File selectedDirectory = directoryChooser.showDialog(stage);
        
		if (selectedDirectory != null) 
        {
			textFieldWorkingDirectory.setText(selectedDirectory.getAbsolutePath());
        }
	}
}
