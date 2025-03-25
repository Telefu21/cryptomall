package io.ozgard.cryptomall.params;

import org.springframework.stereotype.Component;

@Component
public class CertificateTableParams 
{
	public static CertificateTableParams[] certificateTableParamsRows = {	new CertificateTableParams("Days to Expire", "30", "30", "30"),
																			new CertificateTableParams("Common Name (CN)", "RootCert", "IntermediateCert", "EndEntityCert"),
																			new CertificateTableParams("Organisational Unit (OU)", "", "", ""),
																			new CertificateTableParams("Organisation (O)", "", "", ""),
																			new CertificateTableParams("Locality Name (L)", "UK", "UK", "UK"),
																			new CertificateTableParams("State or Province Name (ST)", "", "", ""),
																			new CertificateTableParams("Country Name (C)", "", "", ""),
																			new CertificateTableParams("Title (T)", "", "", ""),
																			new CertificateTableParams("SERIALNUMBER", "", "", ""),
																			new CertificateTableParams("Given Name (GN)", "", "", ""),
																			new CertificateTableParams("Surname (SN)", "", "", ""),
																			new CertificateTableParams("initials ", "", "", ""),
																			new CertificateTableParams("pseudonym", "", "", ""),
																			new CertificateTableParams("Domain Component (DC)", "", "", ""),
																			new CertificateTableParams("STREET", "", "", ""),
																			new CertificateTableParams("User ID (UID)", "", "", ""),
																			new CertificateTableParams("dnQualifier", "", "", ""),
																			new CertificateTableParams("generationQualifier", "", "", "")};
	
	private String elementName;
    private String rootCertificate;
    private String intermediateCertificate;
    private String endEntitiyCertificate;
    
    private CertificateTableParams(String elementName, String rootCertificate, String intermediateCertificate, String endEntitiyCertificate) 
    {
        this.elementName = elementName;
        this.rootCertificate = rootCertificate;
        this.intermediateCertificate = intermediateCertificate;
        this.endEntitiyCertificate = endEntitiyCertificate;
    }
    
    private CertificateTableParams() 
    {
    	
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
}
