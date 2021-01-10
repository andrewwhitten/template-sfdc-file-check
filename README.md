# template-sfdc-file-check

The Salesforce platform allows users to upload files, however there isn't much that you can do to ensure that those files are correctly formatted. For example, a user might decide to password protect a PDF before uploading, which can cause obvious issues if you want to work with those files afterwards.

The Salesforce programming language, Apex, isn't able to work with the contents of such files effectivally, and can't use common open source libraries to validate them.

Salesforce currently offers Heroku that can use many different languages and frameworks to work on your Salesforce data. Unfortunately most organizations don't have Heroku licensing.

The MuleSoft Anypoint integration platform is more common with Salesforce customers, and logic and frameworks are easially implemented with Java.

Flow 1: PDF Password Check - this service will examine a PDF file uploaded into Salesforce and indicate if it is password protected

Flow 2:
