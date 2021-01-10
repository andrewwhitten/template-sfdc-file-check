package sfdcfilevalidate;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime; 
import java.util.Base64;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;


/**
 * @author Andrew Whitten
 *
 */
public class FileCheckPdf {


	public static FileResult CheckFile(String contentVersionId, String contentDocumentId,  String fileName, String fileContentBase64 ) {
		
		// Contruct the file result
		FileResult fileResult = new FileResult();
		fileResult.fileName = fileName;
		fileResult.contentDocumentId = contentDocumentId;
		fileResult.contentVersionId = contentVersionId;
		fileResult.status = FileResultStatus.NONE;
		
		byte[] decodedString = null;
		
		try {

			// Decode the String from Base64
			decodedString = Base64.getDecoder().decode(fileContentBase64.getBytes());
	             
	    } catch (Exception ex) {
	    	 
	    	ex.printStackTrace();
	    } 
		
		
		if(decodedString != null) {
			 
			// Create an in-memory PDF document
			PDDocument newPDF = null;
			
			 try {
				 			 
				 newPDF = PDDocument.load(decodedString);
				 fileResult.status = FileResultStatus.FILE_CHECK_SUCCEED;				 
			 
			 } catch (InvalidPasswordException ipe) {
				 
				 // This will be invoked when we load a file that needs a password to open
				 fileResult.status = FileResultStatus.FILE_CHECK_FAILED_PASSWORD_PROTECTED;
				 
			 }
			 catch (UnsupportedEncodingException e) {
				 
				 fileResult.status = FileResultStatus.FILE_CHECK_FAILED_VALIDATION;
				 e.printStackTrace();
				 
		     } catch (Exception ex) {
		    	 
		    	 fileResult.status = FileResultStatus.FILE_CHECK_FAILED_VALIDATION;
		    	 ex.printStackTrace();
		     }
			 finally { 
				 
				 // Close the PDDocument 
				 if(newPDF != null) {
					 		 
					 try {
						newPDF.close();
						
					} catch (IOException e) {
						// Just in case the close method fails
						e.printStackTrace();
					}
				 }
			 } 	 
		}		

		// Create some details for the check
		fileResult.details = FileCheckPdf.CreateDetails(fileName);

		return fileResult;
	}
	
	static String CreateDetails(String fileName) {
		
		String details = fileName + " was checked on " + LocalDateTime.now().toString();
		
		return details;
	}
}