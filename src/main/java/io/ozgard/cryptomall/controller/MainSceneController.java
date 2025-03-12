package io.ozgard.cryptomall.controller;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.ozgard.cryptomall.params.EncryptDecryptParams;
import io.ozgard.cryptomall.params.KeyGenerateParams;
import io.ozgard.cryptomall.service.CalculatorService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
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
	@Autowired
	EncryptDecryptParams encryptDecryptParams;
	
	@FXML
	@Autowired
	RadioButton radioButtonVerifySignature;
	@FXML
	@Autowired
	RadioButton radioButtonGenSignature;
	@Autowired
	ToggleGroup radioToppleGroup;
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
	@Autowired
	TitledPane titledPanePrimeGeneration;
	@FXML
	PasswordField passFieldKeyGenFilePasswd;
	@FXML
	@Autowired
	CheckBox checkBoxKeyGenEncryptKeyFile;
	@FXML
	@Autowired
	CheckBox checkBoxEncryptDecyptEnableRSAOaep;
	@FXML
	@Autowired
	TextArea texAreaLogOutput;
	@FXML
	@Autowired
	TextField textFieldKeyFileConvertFilePath;
	@FXML
	@Autowired
	TextField textFieldEncryptDecryptBrowseKeyFile;
	@FXML
	@Autowired
	TextField textFieldSignVerifyInputFilePath;
	@FXML
	@Autowired
	TextField textFieldSignVerifyKeyFilePath;
	@FXML
	@Autowired
	TextField textFieldSignVerifySignedFilePath;
	@FXML
	@Autowired
	TextField textFieldEncryptDecryptBrowseFile;
	@FXML
	PasswordField textFieldEncryptDecryptPassPhrase;
	@FXML
	@Autowired
	TextField passFieldKeyFileConvertPasswd;
	@FXML
	@Autowired
	ComboBox<String> comboKeyFileConvertConversionOptions;
	@FXML
	@Autowired
	Tab tabKeyGenerate;
	@FXML
	@Autowired
	Tab tabEncryptDecrypt;
	@FXML
	@Autowired
	Tab tabSignVerify;
	@FXML
	@Autowired
	ComboBox<String> comboEncryptDecryptCipher;
	@FXML
	@Autowired
	ComboBox<String> comboEncryptDecyptHashFunction;
	@FXML
	@Autowired
	ComboBox<String> comboEncryptDecryptType;
	@FXML
	@Autowired
	ComboBox<String> comboSignVerifyHashFunction;
	@FXML
	@Autowired
	CheckBox checkBoxEncryptDecryptAddSalt;
	@FXML
	@Autowired
	TitledPane titledPaneEncryptDecryptFile;
	@FXML
	@Autowired
	TitledPane titledPaneEncryptDecryptText;
	@FXML
	@Autowired
	Button buttonEncryptDecyptBrowseKeyFile;
	@FXML
	@Autowired
	TextArea textAreaEncryptDecryptText;
	@FXML
	@Autowired
	Button buttonEncryptDecryptTextTrigger;
	@FXML
	@Autowired
	Button buttonEncryptDecryptFileTrigger;
	@FXML
	@Autowired
	Button buttonSignVerifySignedFileBrowse;
	@FXML
	@Autowired
	CheckBox checkBoxEncryptDecryptBinaryOutput;
	
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
		comboEncryptDecryptType.setItems(FXCollections.observableArrayList(EncryptDecryptParams.ENCRYPT_DECRYPT_TYPE_SYM_ENCRYPTION, EncryptDecryptParams.ENCRYPT_DECRYPT_TYPE_SYM_DECRYPTION, EncryptDecryptParams.ENCRYPT_DECRYPT_TYPE_ASYM_ENCRYPTION, 
				EncryptDecryptParams.ENCRYPT_DECRYPT_TYPE_ASYM_DECRYPTION, EncryptDecryptParams.ENCRYPT_DECRYPT_TYPE_GENERATE_HASH, EncryptDecryptParams.ENCRYPT_DECRYPT_TYPE_GENERATE_CMAC, EncryptDecryptParams.ENCRYPT_DECRYPT_TYPE_GENERATE_HMAC));
		comboEncryptDecryptType.setValue(EncryptDecryptParams.ENCRYPT_DECRYPT_TYPE_SYM_ENCRYPTION);
		
		titledPaneKeygenSettings.setCollapsible(false);
		titledPaneKeygenProcessing.setCollapsible(false);
		titledPaneEncryptDecryptFile.setCollapsible(false);
		titledPaneEncryptDecryptText.setCollapsible(false);
		titledPanePrimeGeneration.setCollapsible(false);
		
		checkBoxEncryptDecyptEnableRSAOaep.setDisable(true);
		comboEncryptDecyptHashFunction.setDisable(true);
		textFieldEncryptDecryptBrowseKeyFile.setDisable(true);
		buttonEncryptDecyptBrowseKeyFile.setDisable(true);
		
		passFieldKeyGenFilePasswd.setDisable(true);
		comboKeyGenFileEncyptCipher.setDisable(true);
		comboKeygenElipticCurveName.setDisable(true);
		checkBoxKeyGenEncryptKeyFile.setSelected(false);
		checkBoxEncryptDecryptBinaryOutput.setVisible(false);
		
		String [] ecList = calculatorService.getListElipticCurveName();
		comboKeygenElipticCurveName.setItems(FXCollections.observableArrayList(ecList));
		comboKeygenElipticCurveName.setValue(ecList[0]);
		
		String [] cipherList = calculatorService.getListCiphers();
		comboKeyGenFileEncyptCipher.setItems(FXCollections.observableArrayList(cipherList));
		comboKeyGenFileEncyptCipher.setValue(cipherList[0]);
		comboEncryptDecryptCipher.setItems(FXCollections.observableArrayList(cipherList));
		comboEncryptDecryptCipher.setValue(cipherList[0]);
		
		String [] hashList = calculatorService.getListHashFuncs();
		comboEncryptDecyptHashFunction.setItems(FXCollections.observableArrayList(hashList));
		comboEncryptDecyptHashFunction.setValue(hashList[0]);
		comboSignVerifyHashFunction.setItems(FXCollections.observableArrayList(hashList));
		comboSignVerifyHashFunction.setValue(hashList[0]);
		
		tabKeyGenerate.setDisable(true);
		tabEncryptDecrypt.setDisable(true);
		tabSignVerify.setDisable(true);
		
		textFieldSignVerifyInputFilePath.setText("Select Input File");
		textFieldSignVerifyKeyFilePath.setText("Select Private Key File");
		textFieldSignVerifySignedFilePath.setText("Not Used in This Mode");
		textFieldSignVerifySignedFilePath.setDisable(true);
		buttonSignVerifySignedFileBrowse.setDisable(true);
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
			tabKeyGenerate.setDisable(false);
			tabEncryptDecrypt.setDisable(false);
			tabSignVerify.setDisable(false);
        }
	}
	
	@FXML
	void browseEncryptDecryptFileOnClick()
	{
		FileChooser fileChooser = new FileChooser();
		
		fileChooser.setTitle("Select File");
		
		if(textFieldWorkingDirectory.getText().contains("\\") == true)
		{
			fileChooser.setInitialDirectory(new File(textFieldWorkingDirectory.getText()));
		}
		
		final File selectedFile = fileChooser.showOpenDialog(stage);
        
		if (selectedFile != null) 
        {
			buttonEncryptDecryptFileTrigger.setDisable(false);
			textFieldEncryptDecryptBrowseFile.setText(selectedFile.getAbsolutePath());
        }
	}
	
	@FXML
	void browseEncryptDecryptKeyFileOnClick()
	{
		FileChooser fileChooser = new FileChooser();
		
		fileChooser.setTitle("Select Private Key File");
		
		if(textFieldWorkingDirectory.getText().contains("\\") == true)
		{
			fileChooser.setInitialDirectory(new File(textFieldWorkingDirectory.getText()));
		}
		
		final File selectedFile = fileChooser.showOpenDialog(stage);
        
		if (selectedFile != null) 
        {
			textFieldEncryptDecryptBrowseKeyFile.setText(selectedFile.getAbsolutePath());
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
		 }
		
		if (comboKeyGenAlgSelect.getValue() == KeyGenerateParams.KEYGEN_ALGO_SELECT_DSA
		 || comboKeyGenAlgSelect.getValue() == KeyGenerateParams.KEYGEN_ALGO_SELECT_DH)
		 {
			 comboKeyGenKeyLength.setDisable(false);
			 comboKeygenElipticCurveName.setDisable(true);
		 }
		
		if (comboKeyGenAlgSelect.getValue() == KeyGenerateParams.KEYGEN_ALGO_SELECT_RSA)
		 {
			 comboKeyGenKeyLength.setDisable(false);
			 comboKeygenElipticCurveName.setDisable(true);
		 }
		 
		 if (comboKeyGenAlgSelect.getValue() == KeyGenerateParams.KEYGEN_ALGO_SELECT_ECC)
		 {
			 comboKeyGenKeyLength.setDisable(true);
			 comboKeygenElipticCurveName.setDisable(false);
		 }
	}
	
	@FXML
	void encryptDecryptTypeChanged()
	{
		buttonEncryptDecryptFileTrigger.setDisable(true);
		
		if(comboEncryptDecryptType.getValue() == EncryptDecryptParams.ENCRYPT_DECRYPT_TYPE_SYM_ENCRYPTION || comboEncryptDecryptType.getValue() == EncryptDecryptParams.ENCRYPT_DECRYPT_TYPE_SYM_DECRYPTION)
		{
			checkBoxEncryptDecyptEnableRSAOaep.setSelected(false);
			checkBoxEncryptDecyptEnableRSAOaep.setDisable(true);
			comboEncryptDecyptHashFunction.setDisable(true);
			textFieldEncryptDecryptBrowseKeyFile.setDisable(true);
			buttonEncryptDecyptBrowseKeyFile.setDisable(true);
			comboEncryptDecryptCipher.setDisable(false);
			textFieldEncryptDecryptPassPhrase.setDisable(false);
			checkBoxEncryptDecryptAddSalt.setDisable(false);
			titledPaneEncryptDecryptText.setDisable(false);
			titledPaneEncryptDecryptFile.setText("File Encryption");
			buttonEncryptDecryptFileTrigger.setText("Encrypt File");
			textFieldEncryptDecryptBrowseFile.setText("Select File to Encrypt");
			titledPaneEncryptDecryptText.setText("Text Encryption");
			buttonEncryptDecryptTextTrigger.setText("Encrypt");
			checkBoxEncryptDecryptBinaryOutput.setVisible(false);
			
			if(comboEncryptDecryptType.getValue() == EncryptDecryptParams.ENCRYPT_DECRYPT_TYPE_SYM_DECRYPTION)
			{
				titledPaneEncryptDecryptText.setDisable(true);
				titledPaneEncryptDecryptFile.setText("File Decryption");
				buttonEncryptDecryptFileTrigger.setText("Decrypt File");
				textFieldEncryptDecryptBrowseFile.setText("Select File to Decrypt");
			}
		}
		
		if(comboEncryptDecryptType.getValue() == EncryptDecryptParams.ENCRYPT_DECRYPT_TYPE_ASYM_ENCRYPTION || comboEncryptDecryptType.getValue() == EncryptDecryptParams.ENCRYPT_DECRYPT_TYPE_ASYM_DECRYPTION)
		{
			checkBoxEncryptDecyptEnableRSAOaep.setSelected(false);
			checkBoxEncryptDecyptEnableRSAOaep.setDisable(false);
			comboEncryptDecyptHashFunction.setDisable(true);
			textFieldEncryptDecryptBrowseKeyFile.setDisable(false);
			buttonEncryptDecyptBrowseKeyFile.setDisable(false);
			comboEncryptDecryptCipher.setDisable(true);
			textFieldEncryptDecryptPassPhrase.setDisable(true);
			checkBoxEncryptDecryptAddSalt.setDisable(true);
			titledPaneEncryptDecryptText.setDisable(false);
			titledPaneEncryptDecryptFile.setText("File Encryption");
			buttonEncryptDecryptFileTrigger.setText("Encrypt File");
			textFieldEncryptDecryptBrowseKeyFile.setText("Select Public Key File");
			textFieldEncryptDecryptBrowseFile.setText("Select File to Encrypt - Size should be less than key size");
			titledPaneEncryptDecryptText.setText("Text Encryption");
			buttonEncryptDecryptTextTrigger.setText("Encrypt");
			checkBoxEncryptDecryptBinaryOutput.setVisible(false);
			
			if(comboEncryptDecryptType.getValue() == EncryptDecryptParams.ENCRYPT_DECRYPT_TYPE_ASYM_DECRYPTION)
			{
				titledPaneEncryptDecryptText.setDisable(true);
				titledPaneEncryptDecryptFile.setText("File Decryption");
				buttonEncryptDecryptFileTrigger.setText("Decrypt File");
				textFieldEncryptDecryptBrowseKeyFile.setText("Select Private Key File");
				textFieldEncryptDecryptBrowseFile.setText("Select File to Decrypt");
			}
		}
		
		if(comboEncryptDecryptType.getValue() == EncryptDecryptParams.ENCRYPT_DECRYPT_TYPE_GENERATE_HASH)
		{
			checkBoxEncryptDecyptEnableRSAOaep.setSelected(false);
			checkBoxEncryptDecyptEnableRSAOaep.setDisable(true);
			comboEncryptDecyptHashFunction.setDisable(false);
			textFieldEncryptDecryptBrowseKeyFile.setDisable(true);
			buttonEncryptDecyptBrowseKeyFile.setDisable(true);
			comboEncryptDecryptCipher.setDisable(true);
			textFieldEncryptDecryptPassPhrase.setDisable(true);
			checkBoxEncryptDecryptAddSalt.setDisable(true);
			titledPaneEncryptDecryptText.setDisable(false);
			titledPaneEncryptDecryptFile.setText("File Hashing");
			buttonEncryptDecryptFileTrigger.setText("Generate Hash");
			textFieldEncryptDecryptBrowseKeyFile.setText("Select Key File");
			textFieldEncryptDecryptBrowseFile.setText("Select File to Hash Generate");
			titledPaneEncryptDecryptText.setText("Text Hash Generate");
			buttonEncryptDecryptTextTrigger.setText("Generate");
			checkBoxEncryptDecryptBinaryOutput.setVisible(true);
		}
		
		if(comboEncryptDecryptType.getValue() == EncryptDecryptParams.ENCRYPT_DECRYPT_TYPE_GENERATE_CMAC || comboEncryptDecryptType.getValue() == EncryptDecryptParams.ENCRYPT_DECRYPT_TYPE_GENERATE_HMAC)
		{
			checkBoxEncryptDecyptEnableRSAOaep.setSelected(false);
			checkBoxEncryptDecyptEnableRSAOaep.setDisable(true);
			comboEncryptDecyptHashFunction.setDisable(false);
			textFieldEncryptDecryptBrowseKeyFile.setDisable(true);
			buttonEncryptDecyptBrowseKeyFile.setDisable(true);
			comboEncryptDecryptCipher.setDisable(false);
			textFieldEncryptDecryptPassPhrase.setDisable(false);
			checkBoxEncryptDecryptAddSalt.setDisable(true);
			titledPaneEncryptDecryptText.setDisable(false);
			titledPaneEncryptDecryptFile.setText("File CMAC Generation");
			buttonEncryptDecryptFileTrigger.setText("Generate CMAC");
			textFieldEncryptDecryptBrowseKeyFile.setText("Select Key File");
			textFieldEncryptDecryptBrowseFile.setText("Select File to CMAC Generate");
			titledPaneEncryptDecryptText.setText("Text CMAC Generation");
			buttonEncryptDecryptTextTrigger.setText("Generate");
			checkBoxEncryptDecryptBinaryOutput.setVisible(true);
			
			if(comboEncryptDecryptType.getValue() == EncryptDecryptParams.ENCRYPT_DECRYPT_TYPE_GENERATE_HMAC)
			{
				comboEncryptDecryptCipher.setDisable(true);
				titledPaneEncryptDecryptFile.setText("File HMAC Generation");
				buttonEncryptDecryptFileTrigger.setText("Generate HMAC");
				textFieldEncryptDecryptBrowseFile.setText("Select File to HMAC Generate");
				titledPaneEncryptDecryptText.setText("Text HMAC Generation");
			}
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
		keygenParams.setOutputFilePath(keygenParams.getInputFilePath());
		
		setLogOutput(calculatorService.keyGenerate(keygenParams));
	}
	
	@FXML
	void radioButtonVerifySignatureOnMouseClicked()
	{
		
	}
	
	@FXML
	void radioButtonGenerateSignatureOnMouseClicked()
	{
		radioButtonGenSignature.setSelected(true);
		radioButtonVerifySignature.setSelected(false);
		textFieldSignVerifyInputFilePath.setText("Select Input File");
		textFieldSignVerifyKeyFilePath.setText("Select Private Key File");
		textFieldSignVerifySignedFilePath.setText("Not Used in This Mode");
		textFieldSignVerifySignedFilePath.setDisable(true);
		buttonSignVerifySignedFileBrowse.setDisable(true);	
	}
	
	@FXML
	void buttonGenerateVerifySignatureOnMouseClicked()
	{
		radioButtonGenSignature.setSelected(false);
		radioButtonVerifySignature.setSelected(true);
		textFieldSignVerifyInputFilePath.setText("Select Original File");
		textFieldSignVerifyKeyFilePath.setText("Select Public Key File");
		textFieldSignVerifySignedFilePath.setText("Select Signature File");
		textFieldSignVerifySignedFilePath.setDisable(false);
		buttonSignVerifySignedFileBrowse.setDisable(false);	
	}
	
	@FXML
	void buttonPrimeGenerateOnMouseClicked()
	{
		
	}
	
	@FXML
	void buttonSignVerifyInputFileBrowseOnMouseClicked()
	{
		browseFile("Select Input File", textFieldSignVerifyInputFilePath);
	}
	
	@FXML
	void buttonSignVerifyKeyFileBrowseOnMouseClicked()
	{
		browseFile("Select Key File", textFieldSignVerifyKeyFilePath);
	}
	
	@FXML
	void buttonSignVerifySignedFileBrowseOnMouseClicked()
	{
		browseFile("Select Signed File", textFieldSignVerifySignedFilePath);
	}
	
	@FXML
	void buttonKeyFileConvertConvertOnMouseClicked()
	{
		String outputFileName = textFieldWorkingDirectory.getText() + "\\" + textFieldKeyFileConvertFilePath.getText().split("\\\\")[textFieldKeyFileConvertFilePath.getText().split("\\\\").length - 1].split("\\.")[0];
		
		switch(comboKeyFileConvertConversionOptions.getValue())
		{
			case KeyGenerateParams.KEYGEN_CONVERT_DER_TO_PEM:
				keygenParams.setInputFilePath("\"" + textFieldKeyFileConvertFilePath.getText() + "\"");
				keygenParams.setOutputFilePath("\"" + outputFileName + ".pem" + "\"");
				keygenParams.setInKeyFileFormat(KeyGenerateParams.KEYGEN_FILE_FORMAT_SELECT_DER);
				keygenParams.setOutKeyFileFormat(KeyGenerateParams.KEYGEN_FILE_FORMAT_SELECT_PEM);
				
				setLogOutput(calculatorService.convertFilePemDer(keygenParams));
				break;
				
			case KeyGenerateParams.KEYGEN_CONVERT_PEM_TO_DER:
				keygenParams.setInputFilePath("\"" + textFieldKeyFileConvertFilePath.getText() + "\"");
				keygenParams.setOutputFilePath("\"" + outputFileName + ".der" + "\"");
				keygenParams.setInKeyFileFormat(KeyGenerateParams.KEYGEN_FILE_FORMAT_SELECT_PEM);
				keygenParams.setOutKeyFileFormat(KeyGenerateParams.KEYGEN_FILE_FORMAT_SELECT_DER);
				
				setLogOutput(calculatorService.convertFilePemDer(keygenParams));
				break;
				
			case KeyGenerateParams.KEYGEN_CONVERT_PRIVKEY_TO_VIEW:
				keygenParams.setInputFilePath("\"" + textFieldKeyFileConvertFilePath.getText() + "\"");
				keygenParams.setInKeyFileFormat(KeyGenerateParams.KEYGEN_FILE_FORMAT_SELECT_PEM);
				keygenParams.setOutKeyFileFormat(KeyGenerateParams.KEYGEN_FILE_FORMAT_SELECT_PEM);
				
				if(passFieldKeyFileConvertPasswd.getText().length() >= 4)
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
				keygenParams.setInKeyFileFormat(KeyGenerateParams.KEYGEN_FILE_FORMAT_SELECT_PEM);
				keygenParams.setOutKeyFileFormat(KeyGenerateParams.KEYGEN_FILE_FORMAT_SELECT_PEM);
				setLogOutput(calculatorService.pubKeyView(keygenParams));
				break;
				
			case KeyGenerateParams.KEYGEN_CONVERT_PUB_FROM_PRIV:
				keygenParams.setInputFilePath("\"" + textFieldKeyFileConvertFilePath.getText() + "\"");
				keygenParams.setOutputFilePath("\"" + outputFileName  + "_pub.pem" + "\"");
				keygenParams.setInKeyFileFormat(KeyGenerateParams.KEYGEN_FILE_FORMAT_SELECT_PEM);
				keygenParams.setOutKeyFileFormat(KeyGenerateParams.KEYGEN_FILE_FORMAT_SELECT_PEM);
				
				if(passFieldKeyFileConvertPasswd.getText().length() >= 4)
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
				keygenParams.setInputFilePath("\"" + textFieldKeyFileConvertFilePath.getText() + "\"");
				keygenParams.setOutputFilePath("\"" + outputFileName + ".file" + "\"");
				
				setLogOutput(calculatorService.convertFileBase64ToAny(keygenParams));
				break;
				
			case KeyGenerateParams.KEYGEN_CONVERT_TO_BASE64:
				keygenParams.setInputFilePath("\"" + textFieldKeyFileConvertFilePath.getText() + "\"");
				keygenParams.setOutputFilePath("\"" + outputFileName + ".b64" + "\"");
				
				setLogOutput(calculatorService.convertFileBase64ToAny(keygenParams));
				break;
		}
	}
	
	@FXML
	void buttonEncryptDecryptFileOnMouseClick()
	{
		String outputFileName = textFieldWorkingDirectory.getText() + "\\" + textFieldEncryptDecryptBrowseFile.getText().split("\\\\")[textFieldEncryptDecryptBrowseFile.getText().split("\\\\").length - 1].split("\\.")[0];
		
		encryptDecryptParams.setAddSaltEnabled(checkBoxEncryptDecryptAddSalt.isSelected());
		encryptDecryptParams.setCipher(comboEncryptDecryptCipher.getValue());
		encryptDecryptParams.setEnableRSAOaep(checkBoxEncryptDecyptEnableRSAOaep.isSelected());
		encryptDecryptParams.setInputFilePath("\"" + textFieldEncryptDecryptBrowseFile.getText() + "\"");
		encryptDecryptParams.setTextInput(textAreaEncryptDecryptText.getText());
		encryptDecryptParams.setHashFunction(comboEncryptDecyptHashFunction.getValue());
		encryptDecryptParams.setKeyFilePath("\"" + textFieldEncryptDecryptBrowseKeyFile.getText() + "\"");
		encryptDecryptParams.setPassPhrase(textFieldEncryptDecryptPassPhrase.getText());
		
		switch(comboEncryptDecryptType.getValue())
		{
			case EncryptDecryptParams.ENCRYPT_DECRYPT_TYPE_SYM_ENCRYPTION:
				encryptDecryptParams.setOutputFilePath("\"" + outputFileName + "_" + encryptDecryptParams.getCipher().replaceAll(" ", "") + ".enc" + "\"");
				setLogOutput(calculatorService.symmetricEncrypt(encryptDecryptParams));
				break;
				
			case EncryptDecryptParams.ENCRYPT_DECRYPT_TYPE_ASYM_ENCRYPTION:
				encryptDecryptParams.setOutputFilePath("\"" + outputFileName + ".enc" + "\"");
				setLogOutput(calculatorService.asymmetricEncrypt(encryptDecryptParams));
				break;
				
			case EncryptDecryptParams.ENCRYPT_DECRYPT_TYPE_SYM_DECRYPTION:
				encryptDecryptParams.setOutputFilePath("\"" + outputFileName + ".dec" + "\"");
				setLogOutput(calculatorService.symmetricDecrypt(encryptDecryptParams));
				break;
				
			case EncryptDecryptParams.ENCRYPT_DECRYPT_TYPE_ASYM_DECRYPTION:
				encryptDecryptParams.setOutputFilePath("\"" + outputFileName + ".dec" + "\"");
				setLogOutput(calculatorService.asymmetricDecrypt(encryptDecryptParams));
				break;
				
			case EncryptDecryptParams.ENCRYPT_DECRYPT_TYPE_GENERATE_HASH:
				encryptDecryptParams.setOutputFilePath("\"" + outputFileName + encryptDecryptParams.getHashFunction().replaceAll(" ", "") + ".hash" + "\"");
				encryptDecryptParams.setBinaryOutputFileEnabled(checkBoxEncryptDecryptBinaryOutput.isSelected());
				setLogOutput(calculatorService.generateHash(encryptDecryptParams));
				break;
				
			case EncryptDecryptParams.ENCRYPT_DECRYPT_TYPE_GENERATE_CMAC:
				encryptDecryptParams.setOutputFilePath("\"" + outputFileName + encryptDecryptParams.getHashFunction().replaceAll(" ", "") + encryptDecryptParams.getCipher().replaceAll(" ", "") + ".cmac" + "\"");
				encryptDecryptParams.setCipher(comboEncryptDecryptCipher.getValue().replaceFirst("-", ""));
				encryptDecryptParams.setBinaryOutputFileEnabled(checkBoxEncryptDecryptBinaryOutput.isSelected());
				setLogOutput(calculatorService.generateCMac(encryptDecryptParams));
				break;
				
			case EncryptDecryptParams.ENCRYPT_DECRYPT_TYPE_GENERATE_HMAC:
				encryptDecryptParams.setOutputFilePath("\"" + outputFileName + encryptDecryptParams.getHashFunction().replaceAll(" ", "") + ".hmac" + "\"");
				encryptDecryptParams.setBinaryOutputFileEnabled(checkBoxEncryptDecryptBinaryOutput.isSelected());
				setLogOutput(calculatorService.generateHMac(encryptDecryptParams));
				break;
		}
	}
	
	@FXML
	void buttonEncryptDecryptTextOnMouseClick()
	{
		
	}
	
	@FXML
	void browseKeyGenConvertFileSelect()
	{
		FileChooser fileChooser = new FileChooser();
		
		fileChooser.setTitle("Select Input File");
		
		if(textFieldWorkingDirectory.getText().contains("\\") == true)
		{
			fileChooser.setInitialDirectory(new File(textFieldWorkingDirectory.getText()));
		}
		
		final File selectedFile = fileChooser.showOpenDialog(stage);
        
		if (selectedFile != null) 
        {
			textFieldKeyFileConvertFilePath.setText(selectedFile.getAbsolutePath());
        }
	}
	
	@FXML
	void checkBoxEncryptDecyptEnableRSAOaepOnAction()
	{
		if(checkBoxEncryptDecyptEnableRSAOaep.isSelected())
		{
			comboEncryptDecyptHashFunction.setDisable(false);
		}
		else
		{
			comboEncryptDecyptHashFunction.setDisable(true);
		}
	}
	
	private void browseFile(String title, TextField textField)
	{
		FileChooser fileChooser = new FileChooser();
		
		fileChooser.setTitle(title);
		
		if(textFieldWorkingDirectory.getText().contains("\\") == true)
		{
			fileChooser.setInitialDirectory(new File(textFieldWorkingDirectory.getText()));
		}
		
		final File selectedFile = fileChooser.showOpenDialog(stage);
        
		if (selectedFile != null) 
        {
			textField.setText(selectedFile.getAbsolutePath());
        }
	}
	
	void setLogOutput(String text)
	{
		texAreaLogOutput.setText(/*texAreaLogOutput.getText() +*/  "------------- [" + LocalDate.now() + " Time: " + LocalTime.now() + " ] ------------- \n\n" + text + "\n\n" );
		//texAreaLogOutput.setScrollTop(Double.MAX_VALUE);
	}
}
