package io.ozgard.cryptomall.params;

import org.springframework.stereotype.Component;

@Component
public class CertificateParams 
{
	private String subjAttribsCertStr [] = {"CN", "OU", "O", "L", "ST", "C", "T", "SERIALNUMBER", "GN", "SN", "initials", "pseudonym", "DC", "STREET", "UID", "dnQualifier", "generationQualifier"};
	private CertificateParams[] certificateParamsRows;
	private String 	elementName;
    private String 	rootCertificate;
    private String 	intermediateCertificate;
    private String 	endEntitiyCertificate;
    private String 	rootKeyVerifyFilePath;
    private String 	intermediateKeyVerifyFilePath;
    private String 	endEntityKeyVerifyFilePath;
    private String	rootHashFunction;
    private String	intermediateHashFunction;
    private String	endEntityHashFunction;
    private String	workingDirectory;
    private Boolean isGenerateCertificateSelected;
    private Boolean isVerifyCertificateSelected;
    private Boolean isTwoChainVerifySelected;
    
    byte [] rootCertBytes;
	byte [] rootCsrBytes;
	byte [] intermediateCertBytes;
	byte [] intermediateCsrBytes;
	byte [] endEntityCertBytes;
	byte [] endEntityCsrBytes;
	
    private CertificateParams(String elementName, String rootCertificate, String intermediateCertificate, String endEntitiyCertificate) 
    {
        this.elementName = elementName;
        this.rootCertificate = rootCertificate;
        this.intermediateCertificate = intermediateCertificate;
        this.endEntitiyCertificate = endEntitiyCertificate;
    }
    
    public CertificateParams() 
    {
    	CertificateParams[] rows = {	new CertificateParams("Days to Expire", "30", "30", "30"),
											new CertificateParams("Common Name (CN)", "RootCert", "IntermediateCert", "EndEntityCert"),
											new CertificateParams("Organisational Unit (OU)", "", "", ""),
											new CertificateParams("Organisation (O)", "", "", ""),
											new CertificateParams("Locality Name (L)", "UK", "UK", "UK"),
											new CertificateParams("State or Province Name (ST)", "", "", ""),
											new CertificateParams("Country Name (C)", "", "", ""),
											new CertificateParams("Title (T)", "", "", ""),
											new CertificateParams("SERIALNUMBER", "", "", ""),
											new CertificateParams("Given Name (GN)", "", "", ""),
											new CertificateParams("Surname (SN)", "", "", ""),
											new CertificateParams("initials ", "", "", ""),
											new CertificateParams("pseudonym", "", "", ""),
											new CertificateParams("Domain Component (DC)", "", "", ""),
											new CertificateParams("STREET", "", "", ""),
											new CertificateParams("User ID (UID)", "", "", ""),
											new CertificateParams("dnQualifier", "", "", ""),
											new CertificateParams("generationQualifier", "", "", "")};
    	
    	this.setCertificateParamsRows(rows);
    }

	public String[] getSubjAttribsCertStr() 
	{
		return subjAttribsCertStr;
	}

	public void setSubjAttribsCertStr(String[] subjAttribsCertStr) 
	{
		this.subjAttribsCertStr = subjAttribsCertStr;
	}

	public CertificateParams[] getCertificateParamsRows() 
	{
		return certificateParamsRows;
	}

	public void setCertificateParamsRows(CertificateParams[] certificateParamsRows) 
	{
		this.certificateParamsRows = certificateParamsRows;
	}

	public String getElementName() 
	{
		return elementName;
	}

	public String getRootCertificate() 
	{
		return rootCertificate;
	}

	public String getIntermediateCertificate() 
	{
		return intermediateCertificate;
	}

	public String getEndEntitiyCertificate() 
	{
		return endEntitiyCertificate;
	}

	public void setElementName(String elementName) 
	{
		this.elementName = elementName;
	}

	public void setRootCertificate(String rootCertificate) 
	{
		this.rootCertificate = rootCertificate;
	}

	public void setIntermediateCertificate(String intermediateCertificate) 
	{
		this.intermediateCertificate = intermediateCertificate;
	}

	public void setEndEntitiyCertificate(String endEntitiyCertificate) 
	{
		this.endEntitiyCertificate = endEntitiyCertificate;
	}

	public String getRootKeyVerifyFilePath() {
		return rootKeyVerifyFilePath;
	}

	public void setRootKeyVerifyFilePath(String rootKeyVerifyFilePath) 
	{
		this.rootKeyVerifyFilePath = rootKeyVerifyFilePath;
	}

	public String getIntermediateKeyVerifyFilePath() 
	{
		return intermediateKeyVerifyFilePath;
	}

	public void setIntermediateKeyVerifyFilePath(String intermediateKeyVerifyFilePath) 
	{
		this.intermediateKeyVerifyFilePath = intermediateKeyVerifyFilePath;
	}

	public String getEndEntityKeyVerifyFilePath() 
	{
		return endEntityKeyVerifyFilePath;
	}

	public void setEndEntityKeyVerifyFilePath(String endEntityKeyVerifyFilePath) 
	{
		this.endEntityKeyVerifyFilePath = endEntityKeyVerifyFilePath;
	}

	public Boolean getIsGenerateCertificateSelected() 
	{
		return isGenerateCertificateSelected;
	}

	public void setIsGenerateCertificateSelected(Boolean isGenerateCertificateSelected) 
	{
		this.isGenerateCertificateSelected = isGenerateCertificateSelected;
	}

	public Boolean getIsTwoChainVerifySelected() 
	{
		return isTwoChainVerifySelected;
	}

	public void setIsTwoChainVerifySelected(Boolean isTwoChainVerifySelected) 
	{
		this.isTwoChainVerifySelected = isTwoChainVerifySelected;
	}

	public Boolean getIsVerifyCertificateSelected() 
	{
		return isVerifyCertificateSelected;
	}

	public void setIsVerifyCertificateSelected(Boolean isVerifyCertificateSelected) 
	{
		this.isVerifyCertificateSelected = isVerifyCertificateSelected;
	}

	public String getRootHashFunction() {
		return rootHashFunction;
	}

	public void setRootHashFunction(String rootHashFunction) 
	{
		this.rootHashFunction = rootHashFunction;
	}

	public String getIntermediateHashFunction() 
	{
		return intermediateHashFunction;
	}

	public void setIntermediateHashFunction(String intermediateHashFunction) 
	{
		this.intermediateHashFunction = intermediateHashFunction;
	}

	public String getEndEntityHashFunction() 
	{
		return endEntityHashFunction;
	}

	public void setEndEntityHashFunction(String endEntityHashFunction) 
	{
		this.endEntityHashFunction = endEntityHashFunction;
	}

	public String getWorkingDirectory() 
	{
		return workingDirectory;
	}

	public void setWorkingDirectory(String workingDirectory) 
	{
		this.workingDirectory = workingDirectory;
	}

	public byte[] getRootCertBytes() 
	{
		return rootCertBytes;
	}

	public void setRootCertBytes(byte[] rootCertBytes) 
	{
		this.rootCertBytes = rootCertBytes;
	}

	public byte[] getRootCsrBytes() 
	{
		return rootCsrBytes;
	}

	public void setRootCsrBytes(byte[] rootCsrBytes) 
	{
		this.rootCsrBytes = rootCsrBytes;
	}

	public byte[] getIntermediateCertBytes() 
	{
		return intermediateCertBytes;
	}

	public void setIntermediateCertBytes(byte[] intermediateCertBytes) 
	{
		this.intermediateCertBytes = intermediateCertBytes;
	}

	public byte[] getIntermediateCsrBytes() 
	{
		return intermediateCsrBytes;
	}

	public void setIntermediateCsrBytes(byte[] intermediateCsrBytes) 
	{
		this.intermediateCsrBytes = intermediateCsrBytes;
	}

	public byte[] getEndEntityCertBytes() 
	{
		return endEntityCertBytes;
	}

	public void setEndEntityCertBytes(byte[] endEntityCertBytes) 
	{
		this.endEntityCertBytes = endEntityCertBytes;
	}

	public byte[] getEndEntityCsrBytes() 
	{
		return endEntityCsrBytes;
	}

	public void setEndEntityCsrBytes(byte[] endEntityCsrBytes) 
	{
		this.endEntityCsrBytes = endEntityCsrBytes;
	}	
}
