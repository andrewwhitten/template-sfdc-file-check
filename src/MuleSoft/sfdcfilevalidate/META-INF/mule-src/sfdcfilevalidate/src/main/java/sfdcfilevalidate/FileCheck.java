package sfdcfilevalidate;


public class FileCheck {
	
	public static FileResult CheckFile(String contentVersionId, String contentDocumentId,  String fileName, String fileType, String fileContentBase64 ) {
		
		// Construct the file result
		FileResult fileResult = new FileResult();
		fileResult.fileName = fileName;
		fileResult.contentDocumentId = contentDocumentId;
		fileResult.contentVersionId = contentVersionId;
		fileResult.status = FileResultStatus.NONE;
		
		if(fileType.compareTo("PDF") == 0)
		{
			fileResult = FileCheckPdf.CheckFile(contentVersionId, contentDocumentId, fileName, fileContentBase64);
		}
		else if(fileType.compareTo("ZIP") == 0)
		{
			fileResult = FileCheckZipArchive.CheckFile(contentVersionId, contentDocumentId, fileName, fileContentBase64);
		}
		else if(fileType.compareTo("WORD_X") == 0)
		{
			fileResult = FileCheckWordx.CheckFile(contentVersionId, contentDocumentId, fileName, fileContentBase64);
			
		}
		else if(fileType.compareTo("WORD") == 0)
		{
			fileResult = FileCheckWord.CheckFile(contentVersionId, contentDocumentId, fileName, fileContentBase64);
			
		}
		else if(fileType.compareTo("EXCEL_X") == 0)
		{
			fileResult.status = FileResultStatus.FILE_CHECK_FAILED_UNSUPPORTED_TYPE;
		}
		else
		{
			fileResult.status = FileResultStatus.FILE_CHECK_FAILED_UNSUPPORTED_TYPE;
		}
		
		fileResult.fileType = fileType;
		
		return fileResult;
	}

}
