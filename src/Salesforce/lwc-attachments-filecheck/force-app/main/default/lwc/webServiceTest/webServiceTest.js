import { LightningElement } from 'lwc';

export default class WebServiceTest extends LightningElement {

    handleClick(event) {

        this.handleFetch();
    }

    test(event) {
        
        let endPoint ='https://sfdcfilevalidate2.us-e2.cloudhub.io/FileCheck?"069B0000009YNaHIAW"'; 
        
        debugger;

        fetch(endPoint, {
            method: "GET"
        })
        .then((response) => response.json()) 
            /* response.json() gives us back a promise
            we need to process the promise in .then()*/
            .then((repos) => {
                this.repos = repos;
            });

        const calloutURI = 'https://sfdcfilevalidate2.us-e2.cloudhub.io/FileCheck?"069B0000009YNaHIAW"'; // 'https://localhost:8082/FileCheck?"069B0000009YNaHIAW"';
        // requires whitelisting of calloutURI in CSP Trusted Sites - "Accept": "application/json"
        fetch(calloutURI, {
            method: "GET",
            mode: "no-cors",
            headers: {
                // content type
                //"Content-Type": "application/json",
                "Content-Type": "*/*",
                // adding your access token
                //"Authorization": "Bearer ",
                "Access-Control-Allow-Origin": "*",
                "accept": "*/*"
              }
        }).then(
            (response) => {
                debugger;
                if (response.ok) {
                    debugger;
                    return response.json();
                } 
            }
        ).then(responseJSON => {
            debugger;
            //var receivedMessage = responseJSON.items[0].details;
            var receivedMessage = responseJSON;
            console.log('%%%%1'+JSON.stringify(receivedMessage));
            console.log('%%%%1'+receivedMessage);
        });
    }

    async handleFetch() {
        let endPoint ='https://sfdcfilevalidate2.us-e2.cloudhub.io/FileCheck?"069B0000009YNaHIAW"'; 

        const response = await fetch(endPoint);
        const repos = await response.json();

        console.log(repos);
        console.log(repos.items[0]);
        console.log(repos.items[0].details);
      }
}