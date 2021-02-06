public with sharing class ContentDocumentHelper {

    // Cache has to be true on initialization of data table, otherwise wire method won't retrieve any data
    @AuraEnabled(cacheable=true)
    public static List<ContentVersion> getRelatedDocuments(Id recordId) {

        return getRelatedDocumentsForRecord(recordId);
    }

    // Cache has to be false on refreshing table, otherwise recent update from external system (MuleSoft AnyPoint) probably won't appear on records.
    @AuraEnabled(cacheable=false)
    public static List<ContentVersion> getRelatedDocumentsWithoutCache(Id recordId) {

        return getRelatedDocumentsForRecord(recordId);
    }
    
    // Logic for both public methods
    private static List<ContentVersion> getRelatedDocumentsForRecord(Id recordId) {

        List<ContentVersion> contentVersions = new List<ContentVersion>();

        try {

            if(ContentDocumentLink.SObjectType.getDescribe().isAccessible() && ContentVersion.SObjectType.getDescribe().isAccessible())
            {
                // Find all the Content Document Links for records
                List<ContentDocumentLink> contentDocumentLinks = [SELECT ID, ContentDocumentId, LinkedEntityId FROM ContentDocumentLink WHERE LinkedEntityId = :recordId];

                // Build a new list of ID's in order to avoid putting SOQL in a loop
                List<Id> documentIds = new List<Id>();

                for(ContentDocumentLink cdl : contentDocumentLinks)
                {
                    Id documentId = cdl.ContentDocumentId;
                    documentIds.add(documentId);
                }
                
                // SOQL query to retrieve the current state of files attached to records
                List<ContentVersion> cdv = [SELECT Id, ContentDocumentId, Title, FileExtension, FileType, ContentModifiedDate, ContentSize, Description, PathOnClient, TagCsv, TextPreview, VersionNumber, CreatedDate, Data_Quality_Check_Date_Time__c, Data_Quality_Check__c FROM ContentVersion WHERE IsLatest = TRUE AND ContentDocumentId IN :documentIds];

                contentVersions.addAll(cdv);
            }

        } catch (Exception e) {
            // Send error back to UI
            throw new AuraHandledException(e.getMessage());
        }

        return contentVersions;
    }
}
