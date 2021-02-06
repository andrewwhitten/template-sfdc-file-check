package sfdcfilevalidate;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class FileCheckWordx {

	public static FileResult CheckFile(String contentVersionId, String contentDocumentId,  String fileName, String fileContentBase64 ) {
		
		// Construct the file result
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
			 
			
			try {
			
				ByteArrayInputStream fileIn = new ByteArrayInputStream(decodedString);	
				
				XWPFDocument docx = new XWPFDocument(fileIn);	
				XWPFWordExtractor ex = new XWPFWordExtractor(docx);
				
				String text = ex.getText();
				
				if(text != null)  {
		        	fileResult.status = FileResultStatus.FILE_CHECK_SUCCEED;
		        }
		        else {
		        	fileResult.status = FileResultStatus.FILE_CHECK_FAILED_PASSWORD_PROTECTED;
		        }
				
				docx.close();

				
			} catch (Exception e) {
			//} catch (IOException e) {
				
				fileResult.status = FileResultStatus.FILE_CHECK_FAILED_PASSWORD_PROTECTED;
			}
				
		}

		
		return fileResult;
	}
	
}



