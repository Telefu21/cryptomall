package io.ozgard.cryptomall.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.ozgard.cryptomall.params.CertificateParams;
import io.ozgard.cryptomall.params.CrcParams;
import io.ozgard.cryptomall.params.EncryptDecryptParams;
import io.ozgard.cryptomall.params.KeyGenerateParams;
import io.ozgard.cryptomall.params.PostQuantumCryptoParams;
import io.ozgard.cryptomall.params.SignVerifyPrimeParams;
import io.ozgard.cryptomall.service.CRCService;
import io.ozgard.cryptomall.service.OpenSslService;
import io.ozgard.cryptomall.service.PostQuantumCryptoService;
import io.ozgard.cryptomall.service.UtilityService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
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
	private OpenSslService openSslService;
	@Autowired
	private CRCService crcService;
	@Autowired
	private PostQuantumCryptoService postQuantumCryptoService;
	@Autowired
	private UtilityService utilityService;
	
	@Autowired
	KeyGenerateParams keygenParams;
	@Autowired
	EncryptDecryptParams encryptDecryptParams;
	@Autowired
	SignVerifyPrimeParams signVerifyPrimeParams;
	@Autowired
	CrcParams crcParams;
	@Autowired
	CertificateParams certificateParams;
	@Autowired
	PostQuantumCryptoParams postQuantumCryptoParams;
	
	@FXML
	@Autowired
	RadioButton radioButtonVerifySignature;
	@FXML
	@Autowired
	RadioButton radioButtonGenerateSignature;
	@FXML
	@Autowired
	RadioButton radioButtonCRC8;
	@FXML
	@Autowired
	RadioButton radioButtonCRC16;
	@FXML
	@Autowired
	RadioButton radioButtonCRC32;
	@FXML
	@Autowired
	RadioButton radioButtonCRC64;
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
	ComboBox<String> comboCertRootHashFunction;
	@FXML
	@Autowired
	ComboBox<String> comboCertIntermediateHashFunction;
	@FXML
	@Autowired
	ComboBox<String> comboCertEndEntityHashFunction;
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
	CheckBox checkBoxPrimeHexOutput;
	@FXML
	@Autowired
	CheckBox checkBoxEncDecHashMacHexIn;
	@FXML
	@Autowired
	CheckBox checkBoxReflectInput;
	@FXML
	@Autowired
	CheckBox checkBoxReflectOutput;
	@FXML
	@Autowired
	CheckBox checkBoxEncryptDecyptEnableRSAOaep;
	@FXML
	@Autowired
	CheckBox checkBoxGenerateSafePrime;
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
	TextField textFieldCRCPolynominal;
	@FXML
	@Autowired
	TextField textFieldCRCInitValue;
	@FXML
	@Autowired
	TextField textFieldCRCXorValue;
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
	TextField textFieldSignVerifySaltLength;
	@FXML
	@Autowired
	TextField textFieldEncryptDecryptBrowseFile;
	@FXML
	PasswordField textFieldEncryptDecryptPassPhrase;
	@FXML
	@Autowired
	TextField textFieldPrimeLength;
	@FXML
	@Autowired
	TextField textFieldHexViewFilePath;
	@FXML
	@Autowired
	TextField passFieldKeyFileConvertPasswd;
	@FXML
	@Autowired
	TextArea textAreaCRCInput;
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
	Tab tabCertificateProcessing;
	@FXML
	@Autowired
	Tab tabHexViewPQC;
	@FXML
	@Autowired
	ComboBox<String> comboEncryptDecryptCipher;
	@FXML
	@Autowired
	ComboBox<String> comboBoxPredifinedCRC;
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
	CheckBox checkBoxCRCInputHex;
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
	TextArea textAreaHexView;
	@FXML
	@Autowired
	Button buttonEncryptDecryptTextTrigger;
	@FXML
	@Autowired
	Button buttonEncryptDecryptFileTrigger;
	@FXML
	@Autowired
	Button buttonGenerateVerifySignature;
	@FXML
	@Autowired
	Button buttonSignVerifySignedFileBrowse;
	@FXML
	@Autowired
	CheckBox checkBoxEncryptDecryptBinaryOutput;
	@FXML
	@Autowired
	CheckBox checkBoxSignVerifyEnableRSAPSS;
	@FXML
	@Autowired
	TableView<CertificateParams> tableViewCertificateParams;
	@FXML
	@Autowired
	TextField textFieldCertRootBrowse;
	@FXML
	@Autowired
	TextField textFieldCertIntermediateBrowse;
	@FXML
	@Autowired
	TextField textFieldCertEndEntityBrowse;
	@FXML
	@Autowired
	Button buttonCertRootBrowse;
	@FXML
	@Autowired
	Button buttonCertIntermediateBrowse;
	@FXML
	@Autowired
	Button buttonCertEndEntityBrowse;
	@FXML
	@Autowired
	RadioButton radioButtonGenerateCertificate;
	@FXML
	@Autowired
	RadioButton radioButtonVerifyCertificate;
	@FXML
	@Autowired
	CheckBox checkBoxCertTwoTierVerify;
	@FXML
	@Autowired
	Button buttonCertGenerateVerify;
	@FXML
	@Autowired
	TextField textFieldPQCDataFilePath;
	@FXML
	@Autowired
	TextField textFieldPQCPublicKeyFilePath;
	@FXML
	@Autowired
	TextField textFieldPQCSignatureFilePath;
	@FXML
	@Autowired
	Button buttonPQCDataFileBrowse;
	@FXML
	@Autowired
	Button buttonPQCPublicKeyFileBrowse;
	@FXML
	@Autowired
	Button buttonPQCGenerateVerifyExchange;
	@FXML
	@Autowired
	Button buttonPQCSignatureFileBrowse;
	@FXML
	@Autowired
	RadioButton radioButtonPQCDilithium;
	@FXML
	@Autowired
	RadioButton radioButtonPQCFalcon;
	@FXML
	@Autowired
	RadioButton radioButtonPQCSphincs;
	@FXML
	@Autowired
	RadioButton radioButtonPQCKyber;
	@FXML
	@Autowired
	RadioButton radioButtonPQCHQC;
	@FXML
	@Autowired
	RadioButton radioButtonPQCBike;
	@FXML
	@Autowired
	RadioButton radioButtonPQCClassicMceliece;
	@FXML
	@Autowired
	ComboBox<String> comboBoxPQCDilithiumParams;
	@FXML
	@Autowired
	ComboBox<String> comboBoxPQCFalconParams;
	@FXML
	@Autowired
	ComboBox<String> comboBoxPQCSphincsParams;
	@FXML
	@Autowired
	ComboBox<String> comboBoxPQCKyberParams;
	@FXML
	@Autowired
	ComboBox<String> comboBoxPQCHQCParams;
	@FXML
	@Autowired
	ComboBox<String> comboBoxPQCBikeParams;
	@FXML
	@Autowired
	ComboBox<String> comboBoxPQCClassicMcElieceParams;
	@FXML
	@Autowired
	TextArea textAreaPQCInput;
	@FXML
	@Autowired
	CheckBox checkBoxPQCInputHex;
	@FXML
	@Autowired
	CheckBox checkBoxPQCSignatureVerify;
	@FXML
	@Autowired
	CheckBox checkBoxPQCSignatureGenerate;
	
	TableColumn<CertificateParams, String> tableColumnCertificateElementsName;
	TableColumn<CertificateParams, String> tableColumnRootCertificate;
	TableColumn<CertificateParams, String> tableColumnIntermediateCertificate;
	TableColumn<CertificateParams, String> tableColumnEndEntityCertificate;
	
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
				KeyGenerateParams.KEYGEN_CONVERT_PEM_TO_DER, KeyGenerateParams.KEYGEN_CONVERT_DER_TO_PEM, KeyGenerateParams.KEYGEN_CONVERT_TO_BASE64, KeyGenerateParams.KEYGEN_CONVERT_FROM_BASE64, KeyGenerateParams.KEYGEN_CONVERT_VIEW_CERTIFICATE,
				KeyGenerateParams.KEYGEN_CONVERT_VIEW_CSR, KeyGenerateParams.KEYGEN_CONVERT_VIEW_CRL,KeyGenerateParams.KEYGEN_CONVERT_PEM_TO_ASN1));
		comboKeyFileConvertConversionOptions.setValue(KeyGenerateParams.KEYGEN_CONVERT_PUB_FROM_PRIV);
		comboEncryptDecryptType.setItems(FXCollections.observableArrayList(EncryptDecryptParams.ENCRYPT_DECRYPT_TYPE_SYM_ENCRYPTION, EncryptDecryptParams.ENCRYPT_DECRYPT_TYPE_SYM_DECRYPTION, EncryptDecryptParams.ENCRYPT_DECRYPT_TYPE_ASYM_ENCRYPTION, 
				EncryptDecryptParams.ENCRYPT_DECRYPT_TYPE_ASYM_DECRYPTION, EncryptDecryptParams.ENCRYPT_DECRYPT_TYPE_GENERATE_HASH, EncryptDecryptParams.ENCRYPT_DECRYPT_TYPE_GENERATE_CMAC, EncryptDecryptParams.ENCRYPT_DECRYPT_TYPE_GENERATE_HMAC));
		comboEncryptDecryptType.setValue(EncryptDecryptParams.ENCRYPT_DECRYPT_TYPE_SYM_ENCRYPTION);
		
		pqcComboItemSet(postQuantumCryptoParams.getDilithiumStrToParams().keySet(), comboBoxPQCDilithiumParams);
		pqcComboItemSet(postQuantumCryptoParams.getKyberStrToParams().keySet(), comboBoxPQCKyberParams);
		pqcComboItemSet(postQuantumCryptoParams.getFalconStrToParams().keySet(), comboBoxPQCFalconParams);
		pqcComboItemSet(postQuantumCryptoParams.getBikeStrToParams().keySet(), comboBoxPQCBikeParams);
		pqcComboItemSet(postQuantumCryptoParams.getHqcStrToParams().keySet(), comboBoxPQCHQCParams);
		pqcComboItemSet(postQuantumCryptoParams.getMecelieceStrToParams().keySet(), comboBoxPQCClassicMcElieceParams);
		pqcComboItemSet(postQuantumCryptoParams.getSphincsStrToParams().keySet(), comboBoxPQCSphincsParams);
				
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
		
		String [] ecList = openSslService.getListElipticCurveName();
		comboKeygenElipticCurveName.setItems(FXCollections.observableArrayList(ecList));
		comboKeygenElipticCurveName.setValue(ecList[0]);
		
		String [] cipherList = openSslService.getListCiphers();
		comboKeyGenFileEncyptCipher.setItems(FXCollections.observableArrayList(cipherList));
		comboKeyGenFileEncyptCipher.setValue(cipherList[0]);
		comboEncryptDecryptCipher.setItems(FXCollections.observableArrayList(cipherList));
		comboEncryptDecryptCipher.setValue(cipherList[0]);
		
		String [] hashList = openSslService.getListHashFuncs();
		comboEncryptDecyptHashFunction.setItems(FXCollections.observableArrayList(hashList));
		comboEncryptDecyptHashFunction.setValue(hashList[0]);
		comboSignVerifyHashFunction.setItems(FXCollections.observableArrayList(hashList));
		comboSignVerifyHashFunction.setValue(hashList[0]);
		comboCertEndEntityHashFunction.setItems(FXCollections.observableArrayList(hashList));
		comboCertEndEntityHashFunction.setValue(hashList[11]);
		comboCertIntermediateHashFunction.setItems(FXCollections.observableArrayList(hashList));
		comboCertIntermediateHashFunction.setValue(hashList[11]);
		comboCertRootHashFunction.setItems(FXCollections.observableArrayList(hashList));
		comboCertRootHashFunction.setValue(hashList[11]);
		
		tabKeyGenerate.setDisable(true);
		tabEncryptDecrypt.setDisable(true);
		tabSignVerify.setDisable(true);
		tabCertificateProcessing.setDisable(true);
		tabHexViewPQC.setDisable(true);
		
		textFieldSignVerifyInputFilePath.setText("Select Input File");
		textFieldSignVerifyKeyFilePath.setText("Select Private Key File");
		textFieldSignVerifySignedFilePath.setText("Not Used in This Mode");
		textFieldSignVerifySignedFilePath.setDisable(true);
		buttonSignVerifySignedFileBrowse.setDisable(true);
		textFieldSignVerifySaltLength.setDisable(true);
		
		radioButtonCRC8OnAction();
		
		tableViewCertificateParams.setEditable(true);
		
		tableColumnCertificateElementsName = new TableColumn<CertificateParams, String>("");
		tableColumnCertificateElementsName.setCellValueFactory(new PropertyValueFactory<CertificateParams, String>("elementName"));
		tableColumnCertificateElementsName.setSortable(false);
		
		tableColumnRootCertificate = new TableColumn<CertificateParams, String>("Root Certificate (CA)");
		tableColumnRootCertificate.setCellValueFactory(new PropertyValueFactory<CertificateParams, String>("rootCertificate"));
		tableColumnRootCertificate.setCellFactory(TextFieldTableCell.<CertificateParams>forTableColumn());
		tableColumnRootCertificate.setOnEditCommit((CellEditEvent<CertificateParams, String> t) -> {((CertificateParams)t.getTableView().getItems().get(t.getTablePosition().getRow())).setRootCertificate(t.getNewValue());});
		tableColumnRootCertificate.setSortable(false);
		
		tableColumnIntermediateCertificate = new TableColumn<CertificateParams, String>("Intermediate Certificate (CA)");
		tableColumnIntermediateCertificate.setCellValueFactory(new PropertyValueFactory<CertificateParams, String>("intermediateCertificate"));
		tableColumnIntermediateCertificate.setCellFactory(TextFieldTableCell.<CertificateParams>forTableColumn());
		tableColumnIntermediateCertificate.setOnEditCommit((CellEditEvent<CertificateParams, String> t) -> {((CertificateParams)t.getTableView().getItems().get(t.getTablePosition().getRow())).setIntermediateCertificate(t.getNewValue());});
		tableColumnIntermediateCertificate.setSortable(false);
		
		tableColumnEndEntityCertificate = new TableColumn<CertificateParams, String>("End Entity Certificate (CA)");
		tableColumnEndEntityCertificate.setCellValueFactory(new PropertyValueFactory<CertificateParams, String>("endEntitiyCertificate"));
		tableColumnEndEntityCertificate.setCellFactory(TextFieldTableCell.<CertificateParams>forTableColumn());
		tableColumnEndEntityCertificate.setOnEditCommit((CellEditEvent<CertificateParams, String> t) -> {((CertificateParams)t.getTableView().getItems().get(t.getTablePosition().getRow())).setEndEntitiyCertificate(t.getNewValue());});
		tableColumnEndEntityCertificate.setSortable(false);
		
		tableViewCertificateParams.getColumns().add(tableColumnCertificateElementsName);
		tableViewCertificateParams.getColumns().add(tableColumnRootCertificate);
		tableViewCertificateParams.getColumns().add(tableColumnIntermediateCertificate);
		tableViewCertificateParams.getColumns().add(tableColumnEndEntityCertificate);
	
		ObservableList<CertificateParams> itemList = FXCollections.observableArrayList();
		
		for(int i = 0; i < certificateParams.getCertificateParamsRows().length; i++)
		{
			itemList.add(certificateParams.getCertificateParamsRows()[i]);
		}
		
		tableViewCertificateParams.setItems(itemList);
		
		radioButtonGenerateCertificateOnAction();
		
		radioButtonPQCDilithiumOnAction();
	}
	
	private void setCertificatesParams()
	{
		certificateParams.setRootKeyVerifyFilePath(textFieldCertRootBrowse.getText());
		certificateParams.setIntermediateKeyVerifyFilePath(textFieldCertIntermediateBrowse.getText());
		certificateParams.setEndEntityKeyVerifyFilePath(textFieldCertEndEntityBrowse.getText());
		certificateParams.setIsGenerateCertificateSelected(radioButtonGenerateCertificate.isSelected());
		certificateParams.setIsVerifyCertificateSelected(radioButtonVerifyCertificate.isSelected());
		certificateParams.setIsTwoChainVerifySelected(checkBoxCertTwoTierVerify.isSelected());
		certificateParams.setRootHashFunction(comboCertRootHashFunction.getValue());
		certificateParams.setIntermediateHashFunction(comboCertIntermediateHashFunction.getValue());
		certificateParams.setEndEntityHashFunction(comboCertEndEntityHashFunction.getValue());
		certificateParams.setWorkingDirectory(textFieldWorkingDirectory.getText());
		
		for(int i = 0; i < certificateParams.getCertificateParamsRows().length; i++) 
		{
			certificateParams.getCertificateParamsRows()[i].setRootCertificate(tableColumnRootCertificate.getCellData(i));
			certificateParams.getCertificateParamsRows()[i].setIntermediateCertificate(tableColumnIntermediateCertificate.getCellData(i));
			certificateParams.getCertificateParamsRows()[i].setEndEntitiyCertificate(tableColumnEndEntityCertificate.getCellData(i));
		}
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
			tabCertificateProcessing.setDisable(false);
			tabHexViewPQC.setDisable(false);
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
		browseFile("Select Private Key File", textFieldEncryptDecryptBrowseKeyFile);
	}
	
	@FXML
	void buttonHexViewFileBrowseOnMouseClicked()
	{
		Boolean isFileSelected = browseFile("Select File to View in Hex Format", textFieldHexViewFilePath);
		
		if(isFileSelected)
		{
			try 
			{
				textAreaHexView.setText(utilityService.convertFileToHex(textFieldHexViewFilePath.getText()));
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
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
	void radioButtonCRC8OnAction()
	{
		crcParams.setWidth(8);
		
		radioButtonCRC8.setSelected(true);
		radioButtonCRC16.setSelected(false);
		radioButtonCRC32.setSelected(false);
		radioButtonCRC64.setSelected(false);
		
		setCrcPredefinedValues(CrcParams.Crc8PredefinedParams);
	}
	
	@FXML
	void radioButtonCRC16OnAction()
	{
		crcParams.setWidth(16);
		
		radioButtonCRC8.setSelected(false);
		radioButtonCRC16.setSelected(true);
		radioButtonCRC32.setSelected(false);
		radioButtonCRC64.setSelected(false);
		
		setCrcPredefinedValues(CrcParams.Crc16PredefinedParams);
	}
	
	@FXML
	void radioButtonCRC32OnAction()
	{
		crcParams.setWidth(32);
		
		radioButtonCRC8.setSelected(false);
		radioButtonCRC16.setSelected(false);
		radioButtonCRC32.setSelected(true);
		radioButtonCRC64.setSelected(false);
		
		setCrcPredefinedValues(CrcParams.Crc32PredefinedParams);
	}
	
	@FXML
	void radioButtonCRC64OnAction()
	{
		crcParams.setWidth(64);
		
		radioButtonCRC8.setSelected(false);
		radioButtonCRC16.setSelected(false);
		radioButtonCRC32.setSelected(false);
		radioButtonCRC64.setSelected(true);
		
		setCrcPredefinedValues(CrcParams.Crc64PredefinedParams);
	}
	
	private void setCrcPredefinedValues(CrcParams [] crcParams)
	{
		ObservableList<String> itemList = FXCollections.observableArrayList();
		
		for(int i = 0; i < crcParams.length; i++)
		{
			itemList.add(crcParams[i].getPredefinedParamsStr());
		}
		
		comboBoxPredifinedCRC.setItems(itemList);
		comboBoxPredifinedCRC.setValue(crcParams[0].getPredefinedParamsStr());
		
		setCrcWidgetValues(crcParams[0]);
	}
	
	private void setCrcWidgetValues(CrcParams crcParams)
	{
		String polynomial = "0x" + Long.toHexString(crcParams.getPolynomial()).toUpperCase();
		String init = "0x" + Long.toHexString(crcParams.getInit()).toUpperCase();
		String finalXorValue = "0x" + Long.toHexString(crcParams.getFinalXor()).toUpperCase();
		
		if(radioButtonCRC32.isSelected())
		{
			polynomial = polynomial.replaceAll("0xFFFFFFFF", "0x");
			init = init.replaceAll("0xFFFFFFFF", "0x");
			finalXorValue = finalXorValue.replaceAll("0xFFFFFFFF", "0x");
		}
		
		textFieldCRCPolynominal.setText(polynomial);
		textFieldCRCInitValue.setText(init);
		textFieldCRCXorValue.setText(finalXorValue);
	
		checkBoxReflectInput.setSelected(crcParams.isReflectIn());
		checkBoxReflectOutput.setSelected(crcParams.isReflectOut());	
	}
	
	@FXML
	void comboBoxPredifinedCRCOnAction()
	{
		int index = 0;
		
		if(radioButtonCRC8.isSelected())
		{
			for(index = 0; index < CrcParams.Crc8PredefinedParams.length; index++)
			{
				if(CrcParams.Crc8PredefinedParams[index].getPredefinedParamsStr() == comboBoxPredifinedCRC.getValue())
				{
					setCrcWidgetValues(CrcParams.Crc8PredefinedParams[index]);
					return;
				}
			}
		}
		
		if(radioButtonCRC16.isSelected())
		{
			for(index = 0; index < CrcParams.Crc16PredefinedParams.length; index++)
			{
				if(CrcParams.Crc16PredefinedParams[index].getPredefinedParamsStr() == comboBoxPredifinedCRC.getValue())
				{
					setCrcWidgetValues(CrcParams.Crc16PredefinedParams[index]);
					return;
				}
			}
		}
		
		if(radioButtonCRC32.isSelected())
		{
			for(index = 0; index < CrcParams.Crc32PredefinedParams.length; index++)
			{
				if(CrcParams.Crc32PredefinedParams[index].getPredefinedParamsStr() == comboBoxPredifinedCRC.getValue())
				{
					setCrcWidgetValues(CrcParams.Crc32PredefinedParams[index]);
					return;
				}
			}
		}
		
		if(radioButtonCRC64.isSelected())
		{
			for(index = 0; index < CrcParams.Crc64PredefinedParams.length; index++)
			{
				if(CrcParams.Crc64PredefinedParams[index].getPredefinedParamsStr() == comboBoxPredifinedCRC.getValue())
				{
					setCrcWidgetValues(CrcParams.Crc64PredefinedParams[index]);
					return;
				}
			}
		}
	}
	
	@FXML
	void buttonCRCGenerateOnMouseClicked()
	{	
		if(textAreaHexView.getText().length() == 0)
		{
			setLogOutput("!!! No File data Available, CRC not calculated !!!!");
		}
		
		if(textAreaHexView.getText().length() != 0)
		{
			String fileString = utilityService.readFileContentToString(textFieldHexViewFilePath.getText());

			setLogOutput(generateCRC(fileString, "File \"" + textFieldHexViewFilePath.getText().split("\\\\")[textFieldHexViewFilePath.getText().split("\\\\").length - 1]));
		}
		
		if(textAreaCRCInput.getText().length() == 0)
		{
			setLogOutput("!!! No Text Area data Available, CRC not calculated !!!!");
		}
		
		if(textAreaCRCInput.getText().length() != 0)
		{
			String textInputStr = "";
			
			if(checkBoxCRCInputHex.isSelected())
			{
				textInputStr = utilityService.hexToAscii(textAreaCRCInput.getText().replaceAll(" 0x", "").replaceAll("0x", ""));
			}
			
			if(!checkBoxCRCInputHex.isSelected())
			{
				textInputStr = textAreaCRCInput.getText();
			}
			
			setLogOutput(generateCRC(textInputStr, "Text Area"));
		}	
	}
	
	private String generateCRC(String str, String inputSource)
	{
		String	outStr ="";
		
		crcParams.setPolynomial((new BigInteger(textFieldCRCPolynominal.getText().replaceAll("0x", ""), 16)).longValue());
		crcParams.setFinalXor((new BigInteger(textFieldCRCXorValue.getText().replaceAll("0x", ""), 16)).longValue());
		crcParams.setInit((new BigInteger(textFieldCRCInitValue.getText().replaceAll("0x", ""), 16)).longValue()); 
		
		crcParams.setReflectIn(checkBoxReflectInput.isSelected());
		crcParams.setReflectOut(checkBoxReflectOutput.isSelected());
		
		crcService.init(crcParams);
		
		long [] crcTable = crcService.getCrcTable();
		
		if (str.length() != 0)
		{
			outStr = "CRC Result for " + inputSource + " : 0x" + Long.toHexString( crcService.calculateCRC(crcParams, str.getBytes())).toUpperCase() + "\n\n";
		}
		
		outStr += "CRC Table:\n";
		
		for(int i = 0; i < crcTable.length; i++)
		{
			outStr += "0x" + Long.toHexString(crcTable[i]).toUpperCase() + " ";
			
			if((i + 1) % 16 == 0)
			{
				outStr += "\n";
			}
		}
		
		return outStr;
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
		
		setLogOutput(openSslService.keyGenerate(keygenParams));
	}
	
	@FXML
	void radioButtonVerifySignatureOnAction()
	{
		radioButtonGenerateSignature.setSelected(false);
		radioButtonVerifySignature.setSelected(true);
		textFieldSignVerifyInputFilePath.setText("Select Original File");
		textFieldSignVerifyKeyFilePath.setText("Select Public Key File");
		textFieldSignVerifySignedFilePath.setText("Select Signature File");
		textFieldSignVerifySignedFilePath.setDisable(false);
		buttonSignVerifySignedFileBrowse.setDisable(false);	
		buttonGenerateVerifySignature.setText("Verify Signature");
	}
	
	@FXML
	void radioButtonGenerateSignatureOnAction()
	{
		radioButtonGenerateSignature.setSelected(true);
		radioButtonVerifySignature.setSelected(false);
		textFieldSignVerifyInputFilePath.setText("Select Input File");
		textFieldSignVerifyKeyFilePath.setText("Select Private Key File");
		textFieldSignVerifySignedFilePath.setText("Not Used in This Mode");
		textFieldSignVerifySignedFilePath.setDisable(true);
		buttonSignVerifySignedFileBrowse.setDisable(true);	
		buttonGenerateVerifySignature.setText("Generate Signature");
	}
	
	@FXML
	void buttonGenerateVerifySignatureOnMouseClicked()
	{
		String outputFileName = textFieldWorkingDirectory.getText() + "\\" + textFieldSignVerifyInputFilePath.getText().split("\\\\")[textFieldSignVerifyInputFilePath.getText().split("\\\\").length - 1].split("\\.")[0];
		
		signVerifyPrimeParams.setInputFilePath("\"" + textFieldSignVerifyInputFilePath.getText() + "\"");
		signVerifyPrimeParams.setHashFunction(comboSignVerifyHashFunction.getValue().replaceAll(" ", ""));
		signVerifyPrimeParams.setSaltLen(textFieldSignVerifySaltLength.getText());
		signVerifyPrimeParams.setKeyFilePath("\"" + textFieldSignVerifyKeyFilePath.getText() + "\"");
		signVerifyPrimeParams.setRsaPssEnabled(checkBoxSignVerifyEnableRSAPSS.isSelected());
		signVerifyPrimeParams.setSignatureFilePath("\"" + textFieldSignVerifySignedFilePath.getText() + "\"");
		
		if(radioButtonGenerateSignature.isSelected() == true)
		{
			signVerifyPrimeParams.setOutputFilePath("\"" + outputFileName + signVerifyPrimeParams.getHashFunction() +  ".signature" + "\"");
			setLogOutput(openSslService.generateSignature(signVerifyPrimeParams));
		}
		
		if(radioButtonVerifySignature.isSelected() == true)
		{
			setLogOutput(openSslService.verifySignature(signVerifyPrimeParams));
		}
	}
	
	@FXML
	void buttonPrimeGenerateOnMouseClicked()
	{
		signVerifyPrimeParams.setPrimeLength(textFieldPrimeLength.getText());
		signVerifyPrimeParams.setHexOutPrime(checkBoxPrimeHexOutput.isSelected());
		signVerifyPrimeParams.setSafePrime(checkBoxGenerateSafePrime.isSelected());
		
		setLogOutput(openSslService.generatePrime(signVerifyPrimeParams));
	}
	
	@FXML
	void buttonCertRootBrowseOnMouseClicked()
	{
		browseFile("Select Input File", textFieldCertRootBrowse);
	}
	
	@FXML
	void buttonCertIntermediateBrowseOnMouseClicked()
	{
		browseFile("Select Input File", textFieldCertIntermediateBrowse);
	}
	
	@FXML
	void buttonCertEndEntityBrowseOnMouseClicked()
	{
		browseFile("Select Input File", textFieldCertEndEntityBrowse);
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
				
				setLogOutput(openSslService.convertFilePemDer(keygenParams));
				break;
				
			case KeyGenerateParams.KEYGEN_CONVERT_PEM_TO_DER:
				keygenParams.setInputFilePath("\"" + textFieldKeyFileConvertFilePath.getText() + "\"");
				keygenParams.setOutputFilePath("\"" + outputFileName + ".der" + "\"");
				keygenParams.setInKeyFileFormat(KeyGenerateParams.KEYGEN_FILE_FORMAT_SELECT_PEM);
				keygenParams.setOutKeyFileFormat(KeyGenerateParams.KEYGEN_FILE_FORMAT_SELECT_DER);
				
				setLogOutput(openSslService.convertFilePemDer(keygenParams));
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
				
				setLogOutput(openSslService.privKeyView(keygenParams));
				break;
				
			case KeyGenerateParams.KEYGEN_CONVERT_PUBKEY_TO_VIEW:
				keygenParams.setInputFilePath("\"" + textFieldKeyFileConvertFilePath.getText() + "\"");
				keygenParams.setInKeyFileFormat(KeyGenerateParams.KEYGEN_FILE_FORMAT_SELECT_PEM);
				keygenParams.setOutKeyFileFormat(KeyGenerateParams.KEYGEN_FILE_FORMAT_SELECT_PEM);
				setLogOutput(openSslService.pubKeyView(keygenParams));
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
				
				setLogOutput(openSslService.pubKeyGenerate(keygenParams));
				break;
				
			case KeyGenerateParams.KEYGEN_CONVERT_FROM_BASE64:
				keygenParams.setInputFilePath("\"" + textFieldKeyFileConvertFilePath.getText() + "\"");
				keygenParams.setOutputFilePath("\"" + outputFileName + ".file" + "\"");
				
				setLogOutput(openSslService.convertFileBase64ToAny(keygenParams));
				break;
				
			case KeyGenerateParams.KEYGEN_CONVERT_TO_BASE64:
				keygenParams.setInputFilePath("\"" + textFieldKeyFileConvertFilePath.getText() + "\"");
				keygenParams.setOutputFilePath("\"" + outputFileName + ".b64" + "\"");
				
				setLogOutput(openSslService.convertFileBase64ToAny(keygenParams));
				break;
				
			case KeyGenerateParams.KEYGEN_CONVERT_VIEW_CERTIFICATE:
				keygenParams.setInputFilePath("\"" + textFieldKeyFileConvertFilePath.getText() + "\"");
				
				setLogOutput(openSslService.convertFileViewCertificate(keygenParams));
				break;
				
			case KeyGenerateParams.KEYGEN_CONVERT_VIEW_CRL:
				keygenParams.setInputFilePath("\"" + textFieldKeyFileConvertFilePath.getText() + "\"");
				
				setLogOutput(openSslService.convertFileViewCrlCertificate(keygenParams));
				break;
				
			case KeyGenerateParams.KEYGEN_CONVERT_VIEW_CSR:
				keygenParams.setInputFilePath("\"" + textFieldKeyFileConvertFilePath.getText() + "\"");
				
				setLogOutput(openSslService.convertFileViewCsrCertificate(keygenParams));
				break;
				
			case KeyGenerateParams.KEYGEN_CONVERT_PEM_TO_ASN1:
				keygenParams.setInputFilePath("\"" + textFieldKeyFileConvertFilePath.getText() + "\"");
				
				setLogOutput(openSslService.convertFilePemToAnsi(keygenParams));
				break;
		}
	}
	
	@FXML
	void buttonEncryptDecryptFileOnMouseClick()
	{
		String outputFileName = textFieldWorkingDirectory.getText() + "\\" + textFieldEncryptDecryptBrowseFile.getText().split("\\\\")[textFieldEncryptDecryptBrowseFile.getText().split("\\\\").length - 1].split("\\.")[0];
		String inputFileName = "\"" + textFieldEncryptDecryptBrowseFile.getText() + "\"";
		
		encryptDecryptProcessorWithRetStr(outputFileName, inputFileName);
	}

	private String encryptDecryptProcessor(String outputFileName, String inputFileName)
	{
		encryptDecryptParams.setAddSaltEnabled(checkBoxEncryptDecryptAddSalt.isSelected());
		encryptDecryptParams.setCipher(comboEncryptDecryptCipher.getValue());
		encryptDecryptParams.setEnableRSAOaep(checkBoxEncryptDecyptEnableRSAOaep.isSelected());
		encryptDecryptParams.setInputFilePath(inputFileName);
		encryptDecryptParams.setTextInput(textAreaEncryptDecryptText.getText());
		encryptDecryptParams.setHashFunction(comboEncryptDecyptHashFunction.getValue());
		encryptDecryptParams.setKeyFilePath("\"" + textFieldEncryptDecryptBrowseKeyFile.getText() + "\"");
		encryptDecryptParams.setPassPhrase(textFieldEncryptDecryptPassPhrase.getText());
		
		switch(comboEncryptDecryptType.getValue())
		{
			case EncryptDecryptParams.ENCRYPT_DECRYPT_TYPE_SYM_ENCRYPTION:
				encryptDecryptParams.setOutputFilePath("\"" + outputFileName + "_" + encryptDecryptParams.getCipher().replaceAll(" ", "") + ".enc" + "\"");
				return(openSslService.symmetricEncrypt(encryptDecryptParams));
				
			case EncryptDecryptParams.ENCRYPT_DECRYPT_TYPE_ASYM_ENCRYPTION:
				encryptDecryptParams.setOutputFilePath("\"" + outputFileName + ".enc" + "\"");
				return(openSslService.asymmetricEncrypt(encryptDecryptParams));
				
			case EncryptDecryptParams.ENCRYPT_DECRYPT_TYPE_SYM_DECRYPTION:
				encryptDecryptParams.setOutputFilePath("\"" + outputFileName + ".dec" + "\"");
				return(openSslService.symmetricDecrypt(encryptDecryptParams));
				
			case EncryptDecryptParams.ENCRYPT_DECRYPT_TYPE_ASYM_DECRYPTION:
				encryptDecryptParams.setOutputFilePath("\"" + outputFileName + ".dec" + "\"");
				return(openSslService.asymmetricDecrypt(encryptDecryptParams));
				
			case EncryptDecryptParams.ENCRYPT_DECRYPT_TYPE_GENERATE_HASH:
				encryptDecryptParams.setBinaryOutputFileEnabled(checkBoxEncryptDecryptBinaryOutput.isSelected());
				return(openSslService.generateHash(encryptDecryptParams));
				
			case EncryptDecryptParams.ENCRYPT_DECRYPT_TYPE_GENERATE_CMAC:
				encryptDecryptParams.setCipher(comboEncryptDecryptCipher.getValue().replaceFirst("-", ""));
				encryptDecryptParams.setBinaryOutputFileEnabled(checkBoxEncryptDecryptBinaryOutput.isSelected());
				return(openSslService.generateCMac(encryptDecryptParams));
				
			case EncryptDecryptParams.ENCRYPT_DECRYPT_TYPE_GENERATE_HMAC:
				encryptDecryptParams.setBinaryOutputFileEnabled(checkBoxEncryptDecryptBinaryOutput.isSelected());
				return(openSslService.generateHMac(encryptDecryptParams));
		}
		
		return "";
	}
	
	@FXML
	void buttonEncryptDecryptTextOnMouseClick()
	{
		String outputFileName = textFieldWorkingDirectory.getText() + "\\" + "Text_Area";
		String inputFileName = outputFileName;
		
		String textInputStr = textAreaEncryptDecryptText.getText();
		
		if(checkBoxEncDecHashMacHexIn.isSelected())
		{
			utilityService.stringHexToFile(inputFileName, textInputStr);
		}
		
		if(!checkBoxEncDecHashMacHexIn.isSelected())
		{	
			try
			{
				FileWriter writer = new FileWriter(inputFileName);
				
	            writer.write(textInputStr);
	            
	            writer.close();
	        } 
			catch (IOException e) 
			{
	            System.out.println("An error occurred while writing to the file: " + e.getMessage());
	        }
		}

		encryptDecryptProcessorWithRetStr(outputFileName, inputFileName);
	}
	
	private void encryptDecryptProcessorWithRetStr(String outputFileName, String inputFileName)
	{
		String outputStr = encryptDecryptProcessor(outputFileName, inputFileName);
		
		switch(comboEncryptDecryptType.getValue())
		{
			case EncryptDecryptParams.ENCRYPT_DECRYPT_TYPE_SYM_ENCRYPTION:
				outputStr = "Encrypted Data has been written to file: " + encryptDecryptParams.getOutputFilePath();
				break;
				
			case EncryptDecryptParams.ENCRYPT_DECRYPT_TYPE_ASYM_ENCRYPTION:
				outputStr = "Encrypted Data has been written to file: " + encryptDecryptParams.getOutputFilePath();
				break;
				
			case EncryptDecryptParams.ENCRYPT_DECRYPT_TYPE_SYM_DECRYPTION:
				outputStr = "Decrypted Data has been written to file: " + encryptDecryptParams.getOutputFilePath();
				break;
				
			case EncryptDecryptParams.ENCRYPT_DECRYPT_TYPE_ASYM_DECRYPTION:
				outputStr = "Decrypted Data has been written to file: " + encryptDecryptParams.getOutputFilePath();
				break;
				
			case EncryptDecryptParams.ENCRYPT_DECRYPT_TYPE_GENERATE_HASH:
				outputStr = "Hash of Iput Data: (" + encryptDecryptParams.getHashFunction().replaceAll(" ", "") + ") " + outputStr.split("=")[1];
				break;
				
			case EncryptDecryptParams.ENCRYPT_DECRYPT_TYPE_GENERATE_CMAC:
				if(outputStr.contains("="))
				{
					outputStr = "CMAC of Iput Data: (" + encryptDecryptParams.getHashFunction().replaceAll(" ", "") + "&" + encryptDecryptParams.getCipher().replaceAll(" ", "") + ") " + outputStr.split("=")[1];
				}
				break;
				
			case EncryptDecryptParams.ENCRYPT_DECRYPT_TYPE_GENERATE_HMAC:
				outputStr = "HMAC of Iput Data: (" + encryptDecryptParams.getHashFunction().replaceAll(" ", "") + ") " + outputStr.split("=")[1];
				break;
		}
		
		setLogOutput(outputStr);
	}
	
	@FXML
	void browseKeyGenConvertFileSelect()
	{
		browseFile("Select Input File", textFieldKeyFileConvertFilePath);
	}
	
	@FXML
	void radioButtonGenerateCertificateOnAction()
	{
		radioButtonVerifyCertificate.setSelected(false);
		radioButtonGenerateCertificate.setSelected(true);
		textFieldCertRootBrowse.setText("Select Key File For Root Certificate");
		textFieldCertIntermediateBrowse.setText("Select Key File For Intermediate Certificate");
		textFieldCertEndEntityBrowse.setText("Select Key File For En Entity Certificate");
		checkBoxCertTwoTierVerify.setDisable(true);
		checkBoxCertTwoTierVerify.setSelected(false);
		tableViewCertificateParams.setDisable(false);
		comboCertRootHashFunction.setDisable(false);
		comboCertIntermediateHashFunction.setDisable(false);
		comboCertEndEntityHashFunction.setDisable(false);
		buttonCertGenerateVerify.setText("Generate");
		textFieldCertRootBrowse.setDisable(false);
		buttonCertRootBrowse.setDisable(false);
	}
	
	@FXML
	void radioButtonVerifyCertificateOnAction()
	{
		radioButtonVerifyCertificate.setSelected(true);
		radioButtonGenerateCertificate.setSelected(false);
		textFieldCertRootBrowse.setText("Select Root Certificate for Verification");
		textFieldCertIntermediateBrowse.setText("Select Intermediate Certificate for Verification");
		textFieldCertEndEntityBrowse.setText("Select End Entity Certificate for Verification");
		checkBoxCertTwoTierVerify.setDisable(false);
		checkBoxCertTwoTierVerify.setSelected(false);
		tableViewCertificateParams.setDisable(true);
		comboCertRootHashFunction.setDisable(true);
		comboCertIntermediateHashFunction.setDisable(true);
		comboCertEndEntityHashFunction.setDisable(true);
		buttonCertGenerateVerify.setText("Verify");
		textFieldCertRootBrowse.setDisable(false);
		buttonCertRootBrowse.setDisable(false);
	}
	
	@FXML
	void buttonCertGenerateVerifyOnMouseClicked()
	{
		setCertificatesParams();
		
		if(certificateParams.getIsGenerateCertificateSelected())
		{
			setLogOutput(openSslService.generateCertificates(certificateParams));
		}
		
		if(certificateParams.getIsVerifyCertificateSelected())
		{
			setLogOutput(openSslService.verifyChainOfCertificates(certificateParams));
		}
	}
	
	@FXML
	void checkBoxCertTwoTierVerifyOnAction()
	{
		if(checkBoxCertTwoTierVerify.isSelected())
		{
			textFieldCertRootBrowse.setText("");
			textFieldCertIntermediateBrowse.setText("Select CA Certificate for Verification");
			textFieldCertEndEntityBrowse.setText("Select Certificate to Verify");
			textFieldCertRootBrowse.setDisable(true);
			buttonCertRootBrowse.setDisable(true);
		}
		
		if(!checkBoxCertTwoTierVerify.isSelected())
		{
			textFieldCertRootBrowse.setText("Select Root Certificate for Verification");
			textFieldCertIntermediateBrowse.setText("Select Intermediate Certificate for Verification");
			textFieldCertEndEntityBrowse.setText("Select End Entity Certificate for Verification");
			textFieldCertRootBrowse.setDisable(false);
			buttonCertRootBrowse.setDisable(false);
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
	
	@FXML
	void buttonPQCDataFileBrowseOnMouseClicked()
	{
		browseFile("Select Data Input File", textFieldPQCDataFilePath);
	}
	
	@FXML
	void buttonPQCPublicKeyFileBrowseOnMouseClicked()
	{
		browseFile("Select Public Key File", textFieldPQCPublicKeyFilePath);
	}
	
	@FXML
	void buttonPQCSignatureFileBrowseOnMouseClicked()
	{
		browseFile("Select Signature File", textFieldPQCSignatureFilePath);
	}
	
	@FXML
	void checkBoxPQCSignatureGenerateOnAction()
	{
		buttonPQCGenerateVerifyExchange.setText("Generate Signature");
		textAreaPQCInput.setDisable(false);
		checkBoxPQCInputHex.setDisable(false);
		checkBoxPQCInputHex.setSelected(false);
		checkBoxPQCSignatureGenerate.setSelected(true);
		checkBoxPQCSignatureGenerate.setDisable(false);
		checkBoxPQCSignatureVerify.setSelected(false);
		checkBoxPQCSignatureVerify.setDisable(false);
		textFieldPQCDataFilePath.setDisable(false);
		textFieldPQCDataFilePath.setText("Select Input File for Signature Generation");
		buttonPQCDataFileBrowse.setDisable(false);
		textFieldPQCPublicKeyFilePath.setDisable(true);
		textFieldPQCPublicKeyFilePath.setText("Not Used for Signature Generation");
		buttonPQCPublicKeyFileBrowse.setDisable(true);
		textFieldPQCSignatureFilePath.setDisable(true);
		textFieldPQCSignatureFilePath.setText("Not Used for Signature Generation");
		buttonPQCSignatureFileBrowse.setDisable(true);
	}
	
	@FXML
	void checkBoxPQCSignatureVerifyOnAction()
	{
		buttonPQCGenerateVerifyExchange.setText("Verify Signature");
		textAreaPQCInput.setDisable(true);
		textAreaPQCInput.clear();
		checkBoxPQCInputHex.setDisable(true);
		checkBoxPQCInputHex.setSelected(false);
		checkBoxPQCSignatureGenerate.setSelected(false);
		checkBoxPQCSignatureGenerate.setDisable(false);
		checkBoxPQCSignatureVerify.setSelected(true);
		checkBoxPQCSignatureVerify.setDisable(false);
		textFieldPQCDataFilePath.setDisable(false);
		textFieldPQCDataFilePath.setText("Select Input File for Signature Verification");
		buttonPQCDataFileBrowse.setDisable(false);
		textFieldPQCPublicKeyFilePath.setDisable(false);
		textFieldPQCPublicKeyFilePath.setText("Select Public Key File for Signature Verification");
		buttonPQCPublicKeyFileBrowse.setDisable(false);
		textFieldPQCSignatureFilePath.setDisable(false);
		textFieldPQCSignatureFilePath.setText("Select Signature File for Signature Verification");
		buttonPQCSignatureFileBrowse.setDisable(false);
	}
	
	@FXML
	void buttonPQCGenerateVerifyExchangeOnMouseClicked()
	{
		String retStr = "";
		
		postQuantumCryptoParams.setWorkingDirectoryPath(textFieldWorkingDirectory.getText());
		
		if(radioButtonPQCDilithium.isSelected() && checkBoxPQCSignatureGenerate.isSelected())
		{
			setPqcSignatureGenerateParams();
			postQuantumCryptoParams.setParameterSet(comboBoxPQCDilithiumParams.getValue());
			retStr = postQuantumCryptoService.signatureGenerateDilithium(postQuantumCryptoParams);
		}
		
		if(radioButtonPQCFalcon.isSelected() && checkBoxPQCSignatureGenerate.isSelected())
		{
			setPqcSignatureGenerateParams();
			postQuantumCryptoParams.setParameterSet(comboBoxPQCFalconParams.getValue());
			retStr = postQuantumCryptoService.signatureGenerateFalcon(postQuantumCryptoParams);
		}
		
		if(radioButtonPQCSphincs.isSelected() && checkBoxPQCSignatureGenerate.isSelected())
		{
			setPqcSignatureGenerateParams();
			postQuantumCryptoParams.setParameterSet(comboBoxPQCSphincsParams.getValue());
			retStr = postQuantumCryptoService.signatureGenerateSphincs(postQuantumCryptoParams);
		}
		
		if(radioButtonPQCDilithium.isSelected() && checkBoxPQCSignatureVerify.isSelected())
		{
			setPqcSignatureVerifyParams();
			postQuantumCryptoParams.setParameterSet(comboBoxPQCDilithiumParams.getValue());
			retStr = postQuantumCryptoService.signatureVerifyDilithium(postQuantumCryptoParams);
		}
		
		if(radioButtonPQCFalcon.isSelected() && checkBoxPQCSignatureVerify.isSelected())
		{
			setPqcSignatureVerifyParams();
			postQuantumCryptoParams.setParameterSet(comboBoxPQCFalconParams.getValue());
			retStr = postQuantumCryptoService.signatureVerifyFalcon(postQuantumCryptoParams);
		}
		
		if(radioButtonPQCSphincs.isSelected() && checkBoxPQCSignatureVerify.isSelected())
		{
			setPqcSignatureVerifyParams();
			postQuantumCryptoParams.setParameterSet(comboBoxPQCSphincsParams.getValue());
			retStr = postQuantumCryptoService.signatureVerifySphincs(postQuantumCryptoParams);
		}
		
		if(radioButtonPQCKyber.isSelected())
		{
			postQuantumCryptoParams.setParameterSet(comboBoxPQCKyberParams.getValue());
			retStr = postQuantumCryptoService.keyEncapsulateKyber(postQuantumCryptoParams);
		}
		
		if(radioButtonPQCHQC.isSelected())
		{
			postQuantumCryptoParams.setParameterSet(comboBoxPQCHQCParams.getValue());
			retStr = postQuantumCryptoService.keyEncapsulateHQC(postQuantumCryptoParams);
		}
		
		if(radioButtonPQCBike.isSelected())
		{
			postQuantumCryptoParams.setParameterSet(comboBoxPQCBikeParams.getValue());
			retStr = postQuantumCryptoService.keyEncapsulateBike(postQuantumCryptoParams);
		}
		
		if(radioButtonPQCClassicMceliece.isSelected())
		{
			postQuantumCryptoParams.setParameterSet(comboBoxPQCClassicMcElieceParams.getValue());
			retStr = postQuantumCryptoService.keyEncapsulateClassicMcEliece(postQuantumCryptoParams);
		}
		
		setLogOutput(retStr);
	}
	
	private void setPqcSignatureGenerateParams() 
	{
		postQuantumCryptoParams.setInputFileBytes(null);
		
		if(textFieldPQCDataFilePath.getText().contains("\\") == true)
		{
			postQuantumCryptoParams.setInputFileBytes(utilityService.readFileContentToString(textFieldPQCDataFilePath.getText()).getBytes());
		}
		
		postQuantumCryptoParams.setTextAreaBytes(null);
		
		if(textAreaPQCInput.getText().length() != 0)
		{
			String textInputStr = "";
			
			if(checkBoxPQCInputHex.isSelected())
			{
				textInputStr = utilityService.hexToAscii(textAreaPQCInput.getText().replaceAll(" 0x", "").replaceAll("0x", ""));
			}
			
			if(!checkBoxPQCInputHex.isSelected())
			{
				textInputStr = textAreaPQCInput.getText();
			}
			
			postQuantumCryptoParams.setTextAreaBytes(textInputStr.getBytes());
		}	
	}
	
	private void setPqcSignatureVerifyParams() 
	{
		postQuantumCryptoParams.setInputFileBytes(utilityService.readFileContentToString(textFieldPQCDataFilePath.getText()).getBytes());
		postQuantumCryptoParams.setPublicKeyFileBytes(utilityService.readFileContentToString(textFieldPQCPublicKeyFilePath.getText()).getBytes());
		postQuantumCryptoParams.setSignatureFileBytes(utilityService.readFileContentToString(textFieldPQCSignatureFilePath.getText()).getBytes());
	}

	@FXML
	void radioButtonPQCDilithiumOnAction()
	{
		radioButtonPQCToggle(radioButtonPQCDilithium);
		
		checkBoxPQCSignatureGenerateOnAction();
	}
	
	@FXML
	void radioButtonPQCSphincsOnAction()
	{
		radioButtonPQCToggle(radioButtonPQCSphincs);
		
		checkBoxPQCSignatureGenerateOnAction();
	}
	
	@FXML
	void radioButtonPQCFalconOnAction()
	{
		radioButtonPQCToggle(radioButtonPQCFalcon);
		
		checkBoxPQCSignatureGenerateOnAction();
	}
	
	@FXML
	void radioButtonPQCKyberOnAction()
	{
		disableAllWidgetsPQCKeyEn();
		
		buttonPQCGenerateVerifyExchange.setText("Start Key Encapsulation Mechanism");

		radioButtonPQCToggle(radioButtonPQCKyber);
	}
	
	@FXML
	void radioButtonPQCHQCOnAction()
	{
		disableAllWidgetsPQCKeyEn();
		
		buttonPQCGenerateVerifyExchange.setText("Start Key Encapsulation Mechanism");

		radioButtonPQCToggle(radioButtonPQCHQC);
	}
	
	@FXML
	void radioButtonPQCBikeOnAction()
	{
		disableAllWidgetsPQCKeyEn();
		
		buttonPQCGenerateVerifyExchange.setText("Start Key Encapsulation Mechanism");
		
		radioButtonPQCToggle(radioButtonPQCBike);
	}
	
	@FXML
	void radioButtonPQCClassicMcelieceOnAction()
	{
		disableAllWidgetsPQCKeyEn();
		
		buttonPQCGenerateVerifyExchange.setText("Start Key Encapsulation Mechanism");
		
		radioButtonPQCToggle(radioButtonPQCClassicMceliece);
	}
	
	void radioButtonPQCToggle(RadioButton radioButtonToSelect)
	{
		radioButtonPQCFalcon.setSelected(false);
		radioButtonPQCSphincs.setSelected(false);
		radioButtonPQCBike.setSelected(false);
		radioButtonPQCClassicMceliece.setSelected(false);
		radioButtonPQCHQC.setSelected(false);
		radioButtonPQCKyber.setSelected(false);
		radioButtonPQCDilithium.setSelected(false);
		
		radioButtonToSelect.setSelected(true);
	}
	
	void disableAllWidgetsPQCKeyEn()
	{
		textFieldPQCDataFilePath.setDisable(true);
		textFieldPQCDataFilePath.clear();
		textFieldPQCPublicKeyFilePath.setDisable(true);
		textFieldPQCPublicKeyFilePath.clear();
		textFieldPQCSignatureFilePath.setDisable(true);
		textFieldPQCSignatureFilePath.clear();
		buttonPQCDataFileBrowse.setDisable(true);
		buttonPQCPublicKeyFileBrowse.setDisable(true);
		buttonPQCSignatureFileBrowse.setDisable(true);
		checkBoxPQCSignatureGenerate.setDisable(true);
		checkBoxPQCSignatureGenerate.setSelected(false);
		checkBoxPQCSignatureVerify.setDisable(true);
		checkBoxPQCSignatureVerify.setSelected(false);
		textAreaPQCInput.setDisable(true);
		textAreaPQCInput.setText("All Key Encapsulation Files will be stored to Working Directory");
		checkBoxPQCInputHex.setDisable(true);
		checkBoxPQCInputHex.setSelected(false);
	}
	
	@FXML
	void checkBoxSignVerifyEnableRSAPSSOnAction()
	{
		if(checkBoxSignVerifyEnableRSAPSS.isSelected())
		{
			textFieldSignVerifySaltLength.setDisable(false);
		}
		else
		{
			textFieldSignVerifySaltLength.setDisable(true);
		}
	}
	
	private Boolean browseFile(String title, TextField textField)
	{
		Boolean isFileSelected = false;
		
		FileChooser fileChooser = new FileChooser();
		
		fileChooser.setTitle(title);
		
		if(textFieldWorkingDirectory.getText().contains("\\") == true)
		{
			fileChooser.setInitialDirectory(new File(textFieldWorkingDirectory.getText()));
		}
		
		final File selectedFile = fileChooser.showOpenDialog(stage);
        
		if (selectedFile != null) 
        {
			isFileSelected = true;
			textField.setText(selectedFile.getAbsolutePath());
        }
		
		return isFileSelected;
	}
	
	private void pqcComboItemSet(Set<String> keyset, ComboBox<String> comboBox)
	{
		ObservableList<String> itemListPqc = FXCollections.observableArrayList();
		
		for (String key : keyset) 
		{
			itemListPqc.add(key);
		}
		
		
		comboBox.setItems(itemListPqc.sorted());	
		comboBox.setValue(itemListPqc.sorted().get(0));
	}
	
	void setLogOutput(String text)
	{
		texAreaLogOutput.setText(texAreaLogOutput.getText() +  "------------- [" + LocalDate.now() + " Time: " + LocalTime.now() + " ] ------------- \n\n" + text + "\n\n" );
		texAreaLogOutput.setScrollTop(Double.MAX_VALUE);
	}
}
