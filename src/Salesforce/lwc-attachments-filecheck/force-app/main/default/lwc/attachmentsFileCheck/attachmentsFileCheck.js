import { LightningElement, api, wire, track } from 'lwc';
import getRelatedDocuments from '@salesforce/apex/ContentDocumentHelper.getRelatedDocuments';
import getRelatedDocumentsWithoutCache from '@salesforce/apex/ContentDocumentHelper.getRelatedDocumentsWithoutCache';
import { setIconAndLabel } from './attachmentsFileCheckHelper.js';

// Columns used in data table showing file details and data quality status
const columns = [
    { label: '', initialWidth: 150, cellAttributes: { iconName: { fieldName: 'fileTypeIcon' }, iconLabel: { fieldName :'fileTypeIconLabel'} }},
    { label: 'Title', fieldName: 'Title', initialWidth: 200},
    // { label: 'Content Document Id', fieldName: 'ContentDocumentId' }, // For debugging purpose
    { label: 'DQ Result', initialWidth: 250, cellAttributes: { iconName: { fieldName: 'dqCheckIcon' }, iconLabel: { fieldName :'dqCheckIconLabel'} }},
    { label: 'DQ Run', fieldName: 'Data_Quality_Check_Date_Time__c', type: 'date', typeAttributes:{
        weekday: "short",
        year: "numeric",
        month: "short",
        day: "2-digit",
        hour: "2-digit",
        minute: "2-digit"
    }},
];

// Columns used in table showing web service results - Info only, not required for file quality check
const postCheckColumns = [
    { label: 'File Name', fieldName: 'fileName'},
    { label: 'Content Document Id', fieldName: 'contentDocumentId' },
    { label: 'Content Version Id', fieldName: 'contentVersionId'},
    { label: 'DateTime of Check', fieldName: 'dateTimeOfCheck', type: 'text'},
    { label: 'Status', fieldName: 'status', type: 'text'},
    { label: 'Details', fieldName: 'details', type: 'text'},
];

export default class AttachmentsFileCheck extends LightningElement {
    @api recordId = '500B00000062F2bIAE'; // Default to test record (Case, Account, etc) in my org for testing on local web server

    records;                // Files associated with record
    error;                  // Error mesage back from getRelatedDocuments Apex call
    postCheckRecords;       // Records from the file analysis web service call
    isLoading = false;      // Controls the spinner control
    duration = 0;                       // Length of asynchronous web service execution
    showWebServiceResponse = false;     // Hide the web service response by default
    showWebServiceResponseButtonLabel = 'Show Web Service Response';    // Set the label of the web response show button
    loadingMessageLabel = 'Loading...';

    // Retrieve the Content Version records
    @wire(getRelatedDocuments, {recordId: '$recordId'}) documents ({ error, data }) {

        if (data) {

            this.loadingMessageLabel = 'No file records found.';

            this.columns = columns;
            this.records = this.augmentReturnData(data); 
            this.error = undefined;

        } else if (error) {

            this.error = error;
            this.records = undefined;
        }
    };

    // Add additional elements to display icons and other formatting in datatable
    augmentReturnData(dataList) {

        var records = [];                   // The new Array to build

        // Loop through rows of ContentVersion list
        for( let row of dataList) {

            // Salesforce data list arrays cannot be modified, so create copy and use that instead
            var item = Object.assign({}, row);

            item = setIconAndLabel(item);

            records.push(item);
        }

        return records;
    };

    // On Button Click
    handleClick(event) {
        
        var params = this.buildIdString();

        this.handleFetch(params).then(result => { 

            // NOTE: It would in theory be better to use the refreshApex call, however that will 
            // only retrieve the cached data
            getRelatedDocumentsWithoutCache({ recordId: this.recordId }).then((data) => {

                if(data) {
                    this.records = this.augmentReturnData(data);
                } 
            })
        });
    }

    // Show or hide web service response on the UI
    handleClickWebServiceResponse(event) {

        this.showWebServiceResponse = !this.showWebServiceResponse;

        if(this.showWebServiceResponse) {
            this.showWebServiceResponseButtonLabel = 'Hide Web Service Response';
        } else {
            this.showWebServiceResponseButtonLabel = 'Show Web Service Response';
        }
    }

    // Build a parameter string of all DocumentIds to send to service
    buildIdString() {

        var returnString = '';

        if(this.records) {

            this.records.forEach(function (item, index) {
                console.log(item, index);
                returnString += '"' + item.ContentDocumentId + '",';
            });

            // Remove last comma
            returnString = returnString.substring(0, returnString.length - 1);
        }

        return returnString;
    }

    // Quick time function
    getTime() {
        var d = new Date();
        return d.getTime();
    }

    // Check the data quality 
    async handleFetch(params) {

        this.isLoading = true;  // Start Spinner
        var startCheckTime = this.getTime();

        let endPoint ='https://sfdcfilevalidate2.us-e2.cloudhub.io/FileCheck?' + params;

        const response = await fetch(endPoint);

        const repos = await response.json();

        this.postCheckRecords = repos.items;

        // Length of operation in milliseconds
        this.duration = this.getTime() - startCheckTime;

        this.isLoading = false; // Stop Spinner
      }

    columns = columns;
    postCheckColumns = postCheckColumns;
}