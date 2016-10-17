package fi.jyu.imdb.exception;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ErrorMessage {
   private String errorMessage;
   private int errorCode;
   private String documentation;
   
   public ErrorMessage(){}
   
   public ErrorMessage(String errorMessage, int errorCode, String documentation) {
	   super();
	   this.setErrorMessage(errorMessage);
	   this.setErrorCode(errorCode);
	   this.setDocumentation(documentation);
   }
   
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public int getErrorCode() {
		return errorCode;
	}
	
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
	public String getDocumentation() {
		return documentation;
	}
	
	public void setDocumentation(String documentation) {
		this.documentation = documentation;
	}
}