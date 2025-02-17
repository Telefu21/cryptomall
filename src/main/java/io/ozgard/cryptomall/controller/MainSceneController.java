package io.ozgard.cryptomall.controller;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
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
	ComboBox<String> comboKeyGenKeyLength;
	@FXML
	@Autowired
	ComboBox<String> comboKeygenElipticCurveName;
	@FXML
	@Autowired
	TitledPane titledPaneKeygenProcessing;
	@FXML
	@Autowired
	TitledPane titledPaneKeygenSettings;
	@FXML
	PasswordField passFieldKeyGenFilePasswd;
	@FXML
	@Autowired
	CheckBox checkBoxKeyGenEncryptKeyFile;
	@FXML
	@Autowired
	Button buttonKeyGenGenerate;
	@FXML
	@Autowired
	Button buttonKeyFileConvertConvert;
	@FXML
	@Autowired
	TextArea texAreaLogOutput;
	@FXML
	@Autowired
	TextField textFieldKeyFileConvertFilePath;
	@FXML
	@Autowired
	TextField passFieldKeyFileConvertPasswd;
	@FXML
	@Autowired
	ComboBox<String> comboKeyFileConvertConversionOptions;
	
	static public void setStage(Stage stageT)
	{
		stage = stageT;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{			
		comboKeyGenKeyFileFormat.setItems(FXCollections.observableArrayList(KeyGenerateParams.KEYGEN_FILE_FORMAT_SELECT_PEM, KeyGenerateParams.KEYGEN_FILE_FORMAT_SELECT_DER));
		comboKeyGenKeyFileFormat.setValue(KeyGenerateParams.KEYGEN_FILE_FORMAT_SELECT_PEM);
		comboKeyGenAlgSelect.setItems(FXCollections.observableArrayList(KeyGenerateParams.KEYGEN_ALGO_SELECT_RSA, KeyGenerateParams.KEYGEN_ALGO_SELECT_ECC, 
				KeyGenerateParams.KEYGEN_ALGO_SELECT_DSA, KeyGenerateParams.KEYGEN_ALGO_SELECT_DH, KeyGenerateParams.KEYGEN_ALGO_SELECT_ED25519, 
				KeyGenerateParams.KEYGEN_ALGO_SELECT_X448, KeyGenerateParams.KEYGEN_ALGO_SELECT_ED448, KeyGenerateParams.KEYGEN_ALGO_SELECT_X25519));
		comboKeyGenAlgSelect.setValue(KeyGenerateParams.KEYGEN_ALGO_SELECT_RSA);
		comboKeyGenKeyLength.setItems(FXCollections.observableArrayList(KeyGenerateParams.KEYGEN_KEY_LENGHT_512,KeyGenerateParams.KEYGEN_KEY_LENGHT_1024, KeyGenerateParams.KEYGEN_KEY_LENGHT_2048, KeyGenerateParams.KEYGEN_KEY_LENGHT_4096));
		comboKeyGenKeyLength.setValue(KeyGenerateParams.KEYGEN_KEY_LENGHT_1024);
		comboKeyFileConvertConversionOptions.setItems(FXCollections.observableArrayList(KeyGenerateParams.KEYGEN_CONVERT_PUB_FROM_PRIV, KeyGenerateParams.KEYGEN_CONVERT_PRIVKEY_TO_VIEW, KeyGenerateParams.KEYGEN_CONVERT_PUBKEY_TO_VIEW,
				KeyGenerateParams.KEYGEN_CONVERT_PEM_TO_DER, KeyGenerateParams.KEYGEN_CONVERT_DER_TO_PEM, KeyGenerateParams.KEYGEN_CONVERT_TO_BASE64, KeyGenerateParams.KEYGEN_CONVERT_FROM_BASE64));
		comboKeyFileConvertConversionOptions.setValue(KeyGenerateParams.KEYGEN_CONVERT_PUB_FROM_PRIV);
		titledPaneKeygenSettings.setCollapsible(false);
		titledPaneKeygenProcessing.setCollapsible(false);
		passFieldKeyGenFilePasswd.setDisable(true);
		comboKeyGenFileEncyptCipher.setDisable(true);
		comboKeygenElipticCurveName.setDisable(true);
		checkBoxKeyGenEncryptKeyFile.setSelected(false);
		
		String [] ecList = calculatorService.getListElipticCurveName();
		comboKeygenElipticCurveName.setItems(FXCollections.observableArrayList(ecList));
		comboKeygenElipticCurveName.setValue(ecList[0]);
		
		String [] cipherList = calculatorService.getListCiphers();
		comboKeyGenFileEncyptCipher.setItems(FXCollections.observableArrayList(cipherList));
		comboKeyGenFileEncyptCipher.setValue(cipherList[0]);
		
		buttonKeyGenGenerate.setDisable(true);
		buttonKeyFileConvertConvert.setDisable(true);
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
			buttonKeyGenGenerate.setDisable(false);
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
		if (comboKeyGenAlgSelect.getValue() == KeyGenerateParams.KEYGEN_ALGO_SELECT_ED25519
		 || comboKeyGenAlgSelect.getValue() == KeyGenerateParams.KEYGEN_ALGO_SELECT_ED448
		 || comboKeyGenAlgSelect.getValue() == KeyGenerateParams.KEYGEN_ALGO_SELECT_X25519
		 || comboKeyGenAlgSelect.getValue() == KeyGenerateParams.KEYGEN_ALGO_SELECT_X448)
		 {
			 comboKeyGenKeyLength.setDisable(true);
			 comboKeygenElipticCurveName.setDisable(true);
			 comboKeyGenKeyFileFormat.setDisable(false);
		 }
		
		if (comboKeyGenAlgSelect.getValue() == KeyGenerateParams.KEYGEN_ALGO_SELECT_DSA
		 || comboKeyGenAlgSelect.getValue() == KeyGenerateParams.KEYGEN_ALGO_SELECT_DH)
		 {
			 comboKeyGenKeyLength.setDisable(false);
			 comboKeygenElipticCurveName.setDisable(true);
			 comboKeyGenKeyFileFormat.setValue(KeyGenerateParams.KEYGEN_FILE_FORMAT_SELECT_PEM);
			 comboKeyGenKeyFileFormat.setDisable(true);
		 }
		
		if (comboKeyGenAlgSelect.getValue() == KeyGenerateParams.KEYGEN_ALGO_SELECT_RSA)
		 {
			 comboKeyGenKeyLength.setDisable(false);
			 comboKeygenElipticCurveName.setDisable(true);
			 comboKeyGenKeyFileFormat.setDisable(false);
		 }
		 
		 if (comboKeyGenAlgSelect.getValue() == KeyGenerateParams.KEYGEN_ALGO_SELECT_ECC)
		 {
			 comboKeyGenKeyLength.setDisable(true);
			 comboKeygenElipticCurveName.setDisable(false);
			 comboKeyGenKeyFileFormat.setDisable(false);
		 }
	}
	
	@FXML
	void buttonKeyGenGenerateOnMouseClicked()
	{
		keygenParams.setElepticCurveName(comboKeygenElipticCurveName.getValue());
		keygenParams.setEncryptKeyFile(checkBoxKeyGenEncryptKeyFile.isSelected());
		keygenParams.setFileEncryptionCipher(comboKeyGenFileEncyptCipher.getValue());
		keygenParams.setFileEncryptionPassword(passFieldKeyGenFilePasswd.getText());
		keygenParams.setInKeyFileFormat(comboKeyGenKeyFileFormat.getValue());
		keygenParams.setOutKeyFileFormat(comboKeyGenKeyFileFormat.getValue());
		keygenParams.setKeyGenAlgo(comboKeyGenAlgSelect.getValue());
		keygenParams.setKeyLength(comboKeyGenKeyLength.getValue());
		
		keygenParams.setInputFilePath("\"" + textFieldWorkingDirectory.getText() + "\\" + comboKeyGenAlgSelect.getValue().toLowerCase() + "_privkey" + "." + comboKeyGenKeyFileFormat.getValue().toLowerCase() + "\"");
		keygenParams.setOutputFilePath("\"" + textFieldWorkingDirectory.getText() + "\\" + comboKeyGenAlgSelect.getValue().toLowerCase() + "_privkey" + "." + comboKeyGenKeyFileFormat.getValue().toLowerCase() + "\"");
		
		setLogOutput(calculatorService.keyGenerate(keygenParams));
	}
	
	@FXML
	void buttonKeyFileConvertConvertOnMouseClicked()
	{
		switch(comboKeyFileConvertConversionOptions.getValue())
		{
			case KeyGenerateParams.KEYGEN_CONVERT_DER_TO_PEM:
				setLogOutput(calculatorService.convertFilePemDer(keygenParams));
				break;
				
			case KeyGenerateParams.KEYGEN_CONVERT_PEM_TO_DER:
				setLogOutput(calculatorService.convertFilePemDer(keygenParams));
				break;
				
			case KeyGenerateParams.KEYGEN_CONVERT_PRIVKEY_TO_VIEW:
				keygenParams.setInputFilePath("\"" + textFieldKeyFileConvertFilePath.getText() + "\"");
				keygenParams.setInKeyFileFormat("PEM");
				keygenParams.setOutKeyFileFormat("PEM");
				
				if(passFieldKeyFileConvertPasswd.getText().compareTo(" ") != 0)
				{
					keygenParams.setFileEncryptionPassword(passFieldKeyFileConvertPasswd.getText());
					keygenParams.setEncryptKeyFile(true);
				}
				else
				{
					keygenParams.setEncryptKeyFile(false);
				}
				
				setLogOutput(calculatorService.privKeyView(keygenParams));
				break;
				
			case KeyGenerateParams.KEYGEN_CONVERT_PUBKEY_TO_VIEW:
				keygenParams.setInputFilePath("\"" + textFieldKeyFileConvertFilePath.getText() + "\"");
				keygenParams.setInKeyFileFormat("PEM");
				keygenParams.setOutKeyFileFormat("PEM");
				setLogOutput(calculatorService.pubKeyView(keygenParams));
				break;
				
			case KeyGenerateParams.KEYGEN_CONVERT_PUB_FROM_PRIV:
				keygenParams.setInputFilePath("\"" + textFieldKeyFileConvertFilePath.getText() + "\"");
				keygenParams.setOutputFilePath("\"" + textFieldWorkingDirectory.getText() + "\\" + "converted_pubkey" + ".pem" + "\"");
				keygenParams.setInKeyFileFormat("PEM");
				keygenParams.setOutKeyFileFormat("PEM");
				
				if(passFieldKeyFileConvertPasswd.getText().compareTo("") != 0)
				{
					keygenParams.setFileEncryptionPassword(passFieldKeyFileConvertPasswd.getText());
					keygenParams.setEncryptKeyFile(true);
				}
				else
				{
					keygenParams.setEncryptKeyFile(false);
				}
				
				setLogOutput(calculatorService.pubKeyGenerate(keygenParams));
				break;
				
			case KeyGenerateParams.KEYGEN_CONVERT_FROM_BASE64:
				
				break;
				
			case KeyGenerateParams.KEYGEN_CONVERT_TO_BASE64:
				
				break;
		}
	}
	
	@FXML
	void browseKeyGenConvertFileSelect()
	{
		FileChooser fileChooser = new FileChooser();
		
		fileChooser.setTitle("Select Input File");
		
		final File selectedFile = fileChooser.showOpenDialog(stage);
        
		if (selectedFile != null) 
        {
			textFieldKeyFileConvertFilePath.setText(selectedFile.getAbsolutePath());
			buttonKeyFileConvertConvert.setDisable(false);
        }
	}
	
	void setLogOutput(String text)
	{
		texAreaLogOutput.setText(texAreaLogOutput.getText() +  "------------- [" + LocalDate.now() + " Time: " + LocalTime.now() + " ] ------------- \n\n" + text + "\n\n" );
		texAreaLogOutput.setScrollTop(Double.MAX_VALUE);
	}
}
