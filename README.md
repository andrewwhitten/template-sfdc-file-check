# template-sfdc-file-check

I have built a Salesforce LWC Lightning control and MuleSoft Anypoint 4.3 runtime service that can be added to any record page (Case, Account, whatever) and find the data quality / encryption / password protect status of all the attachments. You have to establish the Mule service, extend the ContentVersion standard object, and drag and drop the control onto the page designer.

<IMG src='https://github.com/andrewwhitten/template-sfdc-file-check/blob/main/media/ShortDemo.gif'/>

The interactions of my solution are detailed in the Sequence Diagram below. The data quality process is initiated by the Salesforce LWC control calling AnyPoint with a list of DocumentId's to check:

<IMG src='https://github.com/andrewwhitten/template-sfdc-file-check/blob/main/media/dqcheck-sequence.png'/>

The MuleSoft Anypoint 4.3 Flow will take a list of Document ID's and start processing them. Currently it uses a For loop to check each file:

<IMG src='https://github.com/andrewwhitten/template-sfdc-file-check/blob/main/media/sfdcfilevalidate.png'/>

You don't have to call this process from Salesforce - just call the service from a REST client or your browser:

<IMG src='https://github.com/andrewwhitten/template-sfdc-file-check/blob/main/media/Browser.png'/>

<p>What does this version detect?</p>

<ul>
  <li>PDF - Password protection</li>
  <li>Microsoft Word (doc and docx) - Password protection</li>
  <li>Zip file password encryption</li>
</ul>

<p>What may future versions include?</p>

<ul>
  <li>Microsoft Excel password protection</li>
  <li>Microsoft PowerPoint password protection</li>
  <li>Image validation</li>
  <li>Corrupt files</li>
</ul>

<p>This proof of concept introduces a framework for working with Salesforce files in MuleSoft. You can analyze your files with any Java code you like. Do you need to scan all word documents for 'Copyright of Acme'? Just write another Java class.</p>

<p><strong>Disclaimer</strong></p>

<ul>
  <li><strong>Question</strong>: Is this Production ready?</li>
  <li><strong>Answer</strong>: No. This is currently at a 'working proof of concept' stage, but needs a lot more in terms of performance management, error handling and testing. You should only use this in your Salesforce developer sandboxes.</li>
</ul>

<p><strong>Problem Background</strong></p>

<p>The Salesforce CRM platform does not yet have a coding capability to read and analyze large files. For example, if a user was to password protect a large PDF file would require a lot of inventive Apex coding from scratch to determine that it was password protected and unusable.</p>

<p>Java on the other hand can do this kind of file analysis, and take advantage of strong and capable open source libraries that are available to work for a variety of file formats. MuleSoft Anypoint is a popular integration product owned by Salesforce and used by many Salesforce orgs, that can further be extended with Java code.</p>

<p>There are other options, such as licensing <a href="https://www.heroku.com/salesforce">Salesforce Heroku</a> or maybe <a href="https://developer.salesforce.com/blogs/2019/11/introducing-salesforce-evergreen.html">Serverless Functions</a> when formally available. You can also create web services hosted on Microsoft Azure, AWS, or anything else. Many Salesforce customers have however invested in MuleSoft that does have strong Salesforce support out of the box. This design is not a compelling reason by itself to procure MuleSoft if you don't have it already, however it is interesting if you already have MuleSoft and some spare capacity on it.</p>

#Notes:

<ol>
  <li>All this can be run on free trial services. <a rel="noreferrer noopener" href="https://developer.salesforce.com/signup" target="_blank">Salesforce</a> and <a href="https://anypoint.mulesoft.com/login/signup">MuleSoft</a> have signup pages.</li>
  <li>You can run the MuleSoft AnyPoint service locally on your machine, however Salesforce won't be able to connect to it (or at least you will have a hard job making the connection). You will need to deploy to AnyPoint Cloud with an SSL certificate for a full end-to-end. </li>
  <li>I'm running this on a medium AnyPoint vCore in the cloud (the highest available on the trial service). Performance seems fine, but there has been no real performance testing yet. There probably is a limit around how much you can throw at this service before it starts failing.</li>
  <li>You will need to add your MuleSoft service's URL to <strong>CSP Trusted Sites</strong> in Salesforce Admin</li>
  <li>The web service is called from a LWC component directly. This can be secured to the calling host, but in the next version I would probably put into Apex so that it is called from the Salesforce org rather than directly from the browser.</li>
  <li>The ability to detect whether a document is password protected was actually not as easy as I had imagined. Open source libraries are great, but they really lack a simple isDocumentEncrypted() function. 
    <ul>
      <li>PDF password detection comes courtesy of&nbsp;<a href="https://pdfbox.apache.org/">Apache PDF Box</a>&nbsp;</li><li>ZIP password detection is with the standard Java libraries. </li>
      <li>Microsoft document password detection uses <a href="https://poi.apache.org/">Apache POI</a></li>
      <li>Other file types and other types of file quality detection can be added to the MuleSoft solution just by adding a new Java class and libraries </li>
    </ul>
  </li>
  <li>The next step is to extend this to determine issues when users uploads a file through the UI. For example, rejecting an upload of an encrypted PDF</li><li>I'm not so experienced with AnyPoint, so my Flow is rather long. I'll also look to break that up in anticipation of other services that will come and reuse common parts</li>
</ol>
