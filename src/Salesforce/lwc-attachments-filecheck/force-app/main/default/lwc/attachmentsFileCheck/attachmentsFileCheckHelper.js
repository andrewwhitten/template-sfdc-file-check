
function setIconAndLabel(item) {

    // Create icon for file type
    if (item.FileType === 'WORD_X') {

        item.fileTypeIcon = 'doctype:word';
        item.fileTypeIconLabel = 'Word (docx)';

        } else if (item.FileType === 'WORD' ) {

            item.fileTypeIcon = 'doctype:word';
            item.fileTypeIconLabel = 'Word (doc)';

        } else if (item.FileType === 'EXCEL_X') {

            item.fileTypeIcon = 'doctype:excel';
            item.fileTypeIconLabel = 'Excel (xlsx)';

        }  else if (item.FileType === 'EXCEL' ) {

            item.fileTypeIcon = 'doctype:excel';
            item.fileTypeIconLabel = 'Excel (xls)';

        } else if (item.FileType === 'POWERPOINT_X') {

            item.fileTypeIcon = 'doctype:ppt';
            item.fileTypeIconLabel = 'Powerpoint (pptx)';

        }  else if (item.FileType === 'POWERPOINT' ) {

            item.fileTypeIcon = 'doctype:ppt';
            item.fileTypeIconLabel = 'Powerpoint (ppt)';

        } else if (item.FileType === 'PDF' ) {

            item.fileTypeIcon = 'doctype:pdf';
            item.fileTypeIconLabel = 'PDF';
        
        } else if (item.FileType === 'RTF' ) {

            item.fileTypeIcon = 'doctype:rtf';
            item.fileTypeIconLabel = 'RTF';

        } else if (item.FileType === 'ZIP' ) {
            
            item.fileTypeIcon = 'doctype:zip';
            item.fileTypeIconLabel = 'ZIP';

        } else if (item.FileType === 'HTML' ) {

            item.fileTypeIcon = 'doctype:html';
            item.fileTypeIconLabel = 'HTML';

        } else {
            
            item.fileTypeIcon = 'doctype:unknown';
            item.fileTypeIconLabel = 'Unknown';
        }

                    // Create icon and text for DQ result
                    if (item.Data_Quality_Check__c === 'FILE_CHECK_SUCCEED' ) {
                        item.dqCheckIcon = 'action:approval';  
                        item.dqCheckIconLabel = 'Valid';
                    } else if (item.Data_Quality_Check__c === 'FILE_CHECK_FAILED_PASSWORD_PROTECTED' ) {
                        item.dqCheckIcon = 'action:new_custom77';  
                        item.dqCheckIconLabel = 'Password Protected';
                    } else if (item.Data_Quality_Check__c === 'FILE_CHECK_FAILED_UNSUPPORTED_TYPE' ) {
                        item.dqCheckIcon = 'action:question_post_action';
                        item.dqCheckIconLabel = 'Unsupported data quality type';
                    } else {
                        item.dqCheckIcon = 'action:close';
                        item.dqCheckIconLabel = 'Failed';
                    }

    return item;
}

export { setIconAndLabel }