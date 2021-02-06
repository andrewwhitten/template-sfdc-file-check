package sfdcfilevalidate;

import java.io.ByteArrayInputStream;
import java.util.Base64;

import org.apache.poi.extractor.ExtractorFactory;
import org.apache.poi.extractor.POITextExtractor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.EncryptedDocumentException;

public class FileCheckWord {

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
				
				POIFSFileSystem fileSystem = new POIFSFileSystem(fileIn);
				
				POITextExtractor oleTextExtractor = ExtractorFactory.createExtractor(fileSystem);
		        
				if(oleTextExtractor != null)  {
		        	fileResult.status = FileResultStatus.FILE_CHECK_SUCCEED;
		        }
				
			} 
			catch(EncryptedDocumentException ede) {
				
				fileResult.status = FileResultStatus.FILE_CHECK_FAILED_PASSWORD_PROTECTED;
			}
			catch (Exception e) {
				
				fileResult.status = FileResultStatus.FILE_CHECK_UNKNOWN;
			}	
		}
		
		return fileResult;
	}
}
