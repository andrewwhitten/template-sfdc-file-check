package sfdcfilevalidate;

import java.time.LocalDateTime; 

/**
 * @author Andrew Whitten
 *
 */
public class FileResult {

	public FileResultStatus status = FileResultStatus.NONE;
	
	public String fileName = "";
	
	public String details = "";
	
	public String contentVersionId = "";
	
	public String contentDocumentId = "";
	
	public LocalDateTime dateTimeOfCheck = LocalDateTime.now();
	
	public String fileType = "";
}
