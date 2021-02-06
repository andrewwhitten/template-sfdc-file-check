package sfdcfilevalidate;

import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipInputStream;
import java.time.LocalDateTime;
import java.util.Base64;


public class FileCheckZipArchive {

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
			 
		// Create an in-memory ZIP document
			
			try {
			
				ByteArrayInputStream fileIn = new ByteArrayInputStream(decodedString);
				
				ZipInputStream zipIn = new ZipInputStream(fileIn);
				
				ZipEntry entry = zipIn.getNextEntry();
				
				if(entry == null) {
					
					
					fileResult.status = FileResultStatus.FILE_CHECK_FAILED;
					
				} else if(entry.getSize() == 0) {
					
					fileResult.status = FileResultStatus.FILE_CHECK_FAILED_PASSWORD_PROTECTED;
					
				} else {
					
					fileResult.status = FileResultStatus.FILE_CHECK_SUCCEED;
				}
				
				
				zipIn.closeEntry();
				
				zipIn.close();
				
				//fileResult.status = FileResultStatus.FILE_CHECK_SUCCEED;
				
			
			} catch (ZipException ze) {
				
				 fileResult.status = FileResultStatus.FILE_CHECK_FAILED_VALIDATION;
				 ze.printStackTrace();
				 
			} catch (IOException e) {
				 
				 fileResult.status = FileResultStatus.FILE_CHECK_FAILED_VALIDATION;
				 e.printStackTrace();
				 
		     } catch (Exception ex) {
		    	 
		    	 fileResult.status = FileResultStatus.FILE_CHECK_FAILED_VALIDATION;
		    	 ex.printStackTrace();
		     }
		}
		
		// Create some details for the check
		fileResult.details = FileCheckZipArchive.CreateDetails(fileName);

		return fileResult;
	}
	
	static String CreateDetails(String fileName) {
		
		String details = fileName + " was checked on " + LocalDateTime.now().toString();
		
		return details;
	}
}
