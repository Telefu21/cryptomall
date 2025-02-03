package io.ozgard.cryptomall.controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.ozgard.cryptomall.params.KeyGenerateParams;
import io.ozgard.cryptomall.service.CalculatorService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;

@Component
@FxmlView("../view/mainscene.fxml")
public class MainSceneController implements Initializable
{
	private static Stage stage = null;
	
	@Autowired
	private CalculatorService calculatorService;
	@Autowired
	KeyGenerateParams keygenParams;
	
	@FXML
	@Autowired
	TextField textFieldWorkingDirectory;
	@FXML
	@Autowired
	ComboBox<String> comboKeyGenAlgSelect;
	@FXML
	@Autowired
	ComboBox<String> comboKeyGenKeyFileFormat;
	@FXML
	@Autowired
	ComboBox<String> comboKeyGenFileEncyptCipher;
	@FXML
	@Autowired
	ComboBox<String> comboKeyGenRSAKeyLength;
	@FXML
	@Autowired
	ComboBox<String> comboKeygenElipticCurveName;
	@FXML
	@Autowired
	TitledPane titledPaneKeygenRSAParams;
	@FXML
	PasswordField passFieldKeyGenFilePasswd;
	@FXML
	@Autowired
	CheckBox checkBoxKeyGenEncryptKeyFile;
	@FXML
	@Autowired
	Button buttonKeyGenGenerate;
	
	static public void setStage(Stage stageT)
	{
		stage = stageT;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{			
		comboKeyGenKeyFileFormat.setItems(FXCollections.observableArrayList(KeyGenerateParams.KEYGEN_FILE_FORMAT_SELECT_PEM, KeyGenerateParams.KEYGEN_FILE_FORMAT_SELECT_DER));
		comboKeyGenKeyFileFormat.setValue(KeyGenerateParams.KEYGEN_FILE_FORMAT_SELECT_PEM);
		comboKeyGenAlgSelect.setItems(FXCollections.observableArrayList(KeyGenerateParams.KEYGEN_ALGO_SELECT_RSA, KeyGenerateParams.KEYGEN_ALGO_SELECT_ECC));
		comboKeyGenAlgSelect.setValue(KeyGenerateParams.KEYGEN_ALGO_SELECT_RSA);
		comboKeyGenRSAKeyLength.setItems(FXCollections.observableArrayList(KeyGenerateParams.KEYGEN_RSA_KEY_LENGHT_1024, KeyGenerateParams.KEYGEN_RSA_KEY_LENGHT_2048, KeyGenerateParams.KEYGEN_RSA_KEY_LENGHT_4096));
		comboKeyGenRSAKeyLength.setValue(KeyGenerateParams.KEYGEN_RSA_KEY_LENGHT_1024);
		titledPaneKeygenRSAParams.setCollapsible(false);
		passFieldKeyGenFilePasswd.setDisable(true);
		comboKeyGenFileEncyptCipher.setDisable(true);
		comboKeygenElipticCurveName.setDisable(true);
		checkBoxKeyGenEncryptKeyFile.setSelected(false);
		
		String [] ecList = calculatorService.getListElipticCurveName();
		comboKeygenElipticCurveName.setItems(FXCollections.observableArrayList(ecList));
		comboKeygenElipticCurveName.setValue(ecList[0]);
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
	
	@FXML
	void keyGenEncryptKeyFileCheckBox()
	{
		 if (checkBoxKeyGenEncryptKeyFile.isSelected())
		 {
			 passFieldKeyGenFilePasswd.setDisable(false);
			 comboKeyGenFileEncyptCipher.setDisable(false);
		 }
		 
		 if (!checkBoxKeyGenEncryptKeyFile.isSelected())
		 {
			 passFieldKeyGenFilePasswd.setDisable(true);
			 comboKeyGenFileEncyptCipher.setDisable(true);
		 }
	}
	
	@FXML
	void keyGenAlgoChanged()
	{
		if ((String)comboKeyGenAlgSelect.getValue() == KeyGenerateParams.KEYGEN_ALGO_SELECT_RSA)
		{
			comboKeyGenRSAKeyLength.setDisable(false);
			comboKeygenElipticCurveName.setDisable(true);
		}
		 
		 if ((String)comboKeyGenAlgSelect.getValue() == KeyGenerateParams.KEYGEN_ALGO_SELECT_ECC)
		 {
			 comboKeyGenRSAKeyLength.setDisable(true);
			 comboKeygenElipticCurveName.setDisable(false);
		 }
	}
	
	@FXML
	void keyGenerateKeyFile()
	{
		keygenParams.setWorkingDirectory(textFieldWorkingDirectory.getText());
		keygenParams.setElepticCurveName(comboKeygenElipticCurveName.getValue());
		keygenParams.setEncryptKeyFile(checkBoxKeyGenEncryptKeyFile.isSelected());
		keygenParams.setFileEncryptionCipher(comboKeyGenFileEncyptCipher.getValue());
		keygenParams.setFileEncryptionPassword(passFieldKeyGenFilePasswd.getText());
		keygenParams.setKeyFileFormat(comboKeyGenKeyFileFormat.getValue());
		keygenParams.setKeyGenAlgo(comboKeyGenAlgSelect.getValue());
		keygenParams.setRsaKeyLength(comboKeyGenRSAKeyLength.getValue());
		
		calculatorService.keyGenerate(keygenParams);
	}
}
