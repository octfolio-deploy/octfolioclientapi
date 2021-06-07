# Octfolio Client API
## Introduction
The Octfolio API is an API built in PHP that follows RESTFUL principles 

## Supported HTTP Verbs
The Octfolio API is constantly under development so this is subject to change
| HTTP Verb | Support |
|:------:|:--------:|
| GET | &#9745; |
| PUT | &#9744; |
| POST | &#9744; |
| DELETE | &#9744; |

## HTTP VERB - GET
Sending a GET request with the correct information will allow you to retrieve information from your client portals data source. Please note when preparing your headings that the HTTP GET character limit is 2048 characters. Below is a list of manditory and optional headings you can add to your GET request.
### Headings
| Heading | Type | Description | Manditory |
|:------:|:--------:|:--------:|:--------:|
| key | String | API key provided to you by OCTFOLIO | &#9745; |
| client | String | Domain name for your client portal | &#9745; |
| assettype | String | The asset type you wish the retrieve data from. A list may be seen below. In the list below you'll need to pass in the asset code | &#9745; |
| version | String | API version number. Refer to the versioning heading for more information | &#9745; |
| columns | Array | An array indexed from zero containing the columns you wish returned, returns all columns if null | &#9744; |
| limit | Integer | The number of records you wish to be returned, returns everything if null | &#9744; |
| condition | Array[Array] | Nested array containing filtering information. Refer to the conditional heading below for more information | &#9744; |
| order | Array[Array] | Nested array containing ordering information. Refer to the ordering heading for more information | &#9744; |

### Conditions
HTTP GET requests allow you to tag on conditional headings to filter the returned data. Conditional headings contain the following three things:
1. column - this is the column you wish to filter by.
2. operator - the operation used to filter. Supported operators are "<", ">", ">=", "<=", "=", "like", "LIKE". Please note "LIKE" and "like" are the same.
3. comparator - this is what we'll be comparing our column to.

For example, if I only wanted buildings whose building code was "123ABC" I would send the following header:
```JSON
"condition": [
            {
                "column": "buildingcode",
                "operator": "=",
                "comparator": "123ABC"
            }
        ]
```
Or if I wanted to only see records assigned to audit 1, last edited by "Bill" before or on August 9th 2020 I would send the following header:
```JSON
"condition": [
    {
        "column": "auditid",
        "operator": "=",
        "comparator": "1"
    },
    {
        "column": "lasteditedname",
        "operator": "=",
        "comparator": "Bill"
    },
    {
        "column": "lastupdate",
        "operator": "<=",
        "comparator": "2020-08-09"
    }
]
```
There's three things worth noting here:
1. Comparators are not case sensative. Searching for "Bill" is the same as searching for "bill".
2. Dates and date times use universal format, which is year, month, day, hour, minute, second. e.g. "2020-08-09 21:13:57".
3. We don't validate if your request makes sense. Feel free to check if the last edit date is less than "Barry". The server will simply return no matching records.

### Ordering return
Similar to the conditional headings above you can pass a nested array to order the returning data. The array should be structured as Array(Array("column"=>"columnname","asc"=>true),Array("column"=>"nextcolumnname","asc"=>false)). Each column inside the array must contain the headings "column" and "asc". Column is the name of the column and asc determines if the column will be in ascending order (true) or decending order (false). For example, if I wanted to sort records by auditid in ascending order and then by date last edited in decending order for the records with the same auditid:
```JSON
"order":[
    {
        "column":"auditid",
        "asc":true
    },
    {
        "column":"lastupdate",
        "asc":false
    }
]
```

### Version
Currently the only release is version 1.0. To pass this into the API use the following setting:
```JSON
"verison":"1.0"
```
As versions are released they will be updated here. If there is an update that changes how parameters are passed in or how returns are made they will be released in a new version as to not disrupt existing users.

### Asset Types
Please note, below is a list of all asset types supported by OCTFOLIO, your client portal may not be configured to use certain asset types. For information specific to your portal please reach out to your OCTFOLIO representative or send us an email at the support address listed below.
| Asset Type | Asset Code | Headings |
|:------:|:--------:|:--------|
| Organisation | org | - "organisationid"<br>- "assetid"<br>- "organisationcode"<br>- "organisationname"<br>- "statecode"<br>- "statename"<br>- "regionname"<br>- "insertbyname"<br>- "lasteditedname"<br>- "insertdate"<br>- "lastupdate" |
| Site | site | - "siteid"<br>- "organisationid"<br>- "assetid"<br>- "sitecode"<br>- "sitename"<br>- "state"<br>- "regionname"<br>- "statecode"<br>- "statename"<br>- "insertbyname"<br>- "lasteditedname"<br>- "insertdate"<br>- "lastupdate" |
| Building | building | - "buildingid"<br>- "siteid"<br>- "assetid"<br>- "buildingcode"<br>- "buildingname"<br>- "address"<br>- "suburb"<br>- "state"<br>- "postcode"<br>- "lng"<br>- "lat"<br>- "constructionyear"<br>- "sitesize"<br>- "regionname"<br>- "statecode"<br>- "statename"<br>- "insertbyname"<br>- "lasteditedname"<br>- "insertdate"<br>- "lastupdate" |
| Level | level | - "levelid"<br>- "buildingid"<br>- "assetid"<br>- "leveloptionid"<br>- "insertbyname"<br>- "lasteditedname"<br>- "insertdate"<br>- "lastupdate"<br>- "longname"<br>- "shortname"<br>- "leveloptioncode" |
| Room | room | - "roomid"<br>- "levelid"<br>- "assetid"<br>- "roomcode"<br>- "roomname"<br>- "insertbyname"<br>- "lasteditedname"<br>- "insertdate"<br>- "lastupdate" |
| Object | object | - "assetobjectid"<br>- "roomid"<br>- "assetid"<br>- "assetobjectcode"<br>- "assetobjectname"<br>- "insertbyname"<br>- "lasteditedname"<br>- "insertdate"<br>- "lastupdate" |
| Audit | audit | - "auditid"<br>- "workordernum"<br>- "scheduleddate"<br>- "auditstatusid"<br>- "adhoc"<br>- "contractorid"<br>- "intrusivenesslevel"<br>- "insertbyname"<br>- "lasteditedname"<br>- "insertdate"<br>- "lastupdate"<br>- "assigneduser"<br>- "auditstatus" |
| Record | record | - "inspectionid"<br>- "assetid"<br>- "auditid"<br>- "reportdate"<br>- "datecompleted"<br>- "approveddate"<br>- "approvedbyname"<br>- "insertdate"<br>- "insertbyname"<br>- "lastupdate"<br>- "lasteditedname"<br>- "inspector"<br>- "contractorname"<br>- "approvedbyname" |
| Sample | sample | - "asbestossampleid"<br>- "inspectionid"<br>- "assetid"<br>- "sampledate"<br>- "sampleid"<br>- "notes"<br>- "acmfullremoval"<br>- "insertdate"<br>- "insertbyname"<br>- "lastupdate"<br>- "lasteditedname"<br>- "resultid"<br>- "asbestosstatus" |



# Return
The APIs response depends on the type of request sent but three things will never change. The response will always be a JSON array, this array will always contain a boolean return ['error'] as to whether an error was tripped in addition to an array containing the headers sent ['headers']. An example of a successful GET request is below:
```JSON
{
    "error": false,
    "data": [
        {
            "inspectionid": "1",
            "assetid": "1-1-1-0-0-0",
            "auditid": "1",
            "reportdate": "2015-01-05 00:00:00",
            "datecompleted": "2020-08-10 21:50:04",
            "approveddate": "2020-08-10 21:50:04",
            "approvedbyname": "Octfolio Administrator",
            "insertdate": "2020-08-10 21:50:04",
            "insertbyname": "Octfolio Administrator",
            "lastupdate": null,
            "lasteditedname": null,
            "inspector": null,
            "contractorname": null
        }
    ],
    "headers": {
        "key": "testkey",
        "client": "testclient",
        "assettype": "record",
        "limit": "1",
        "version":"1.0"
    }
}
```
If the request is successful your data will be returned under the header "data", as seen above.  
if you experience an error we will attempt to guide you in the right direction with the return message. For example, querying an asset type that doesn't exist will return a list of asset types you can chose from:
```JSON
{
    "error": true,
    "message": "The input assettype is not a valid assettype",
    "validassettypes": [
        "org",
        "site",
        "building",
        "level",
        "room",
        "object",
        "audit",
        "record",
        "sample"
    ],
    "headers": {
        "key": "testkey",
        "client": "testclient",
        "assettype": "lawnchair",
        "limit": "1",
        "version":"1.0"
    }
}
```
# Errors & Troubleshooting
If the API encounters an error with your request it will add ['error']=true to your response. Below is an example of an error caused by requesting a column that doesn't exist:
```JSON
{
    "error": true,
    "message": "Invalid column name given in header",
    "validcolumnnames": [
        "buildingid",
        "siteid",
        "assetid",
        "buildingcode",
        "buildingname",
        "address",
        "suburb",
        "state",
        "postcode",
        "lng",
        "lat",
        "constructionyear",
        "sitesize",
        "regionname",
        "statecode",
        "statename",
        "insertbyname",
        "lasteditedname",
        "insertdate",
        "lastupdate"
    ],
    "headers": {
        "key": "testkey",
        "client": "testclient",
        "assettype": "building",
        "columns": [
            "buildingid",
            "assetid",
            "buildingmiddlename"
        ],
        "limit": "1",
        "version":"1.0"
    }
}
```
The response contains the error flag as well as an attempt to guide the user by suggesting valid column headings.

You may also see an incorrect API key error:
```JSON
{
    "error": true,
    "message": "This is not a valid API key for this client",
    "headers": {
        "key": "testkey1",
        "client": "testclient",
        "assettype": "building",
        "columns": [
            "buildingid",
            "assetid"
        ],
        "limit": "1",
        "version":"1.0"
    }
}
```
API keys are client specific and case sensative.

If you get a response and the message states it's a system error then please contact OCTFOLIO support. This error means the API is having trouble connecting to your datasource.

# Examples

buildingid and assetid for the first 10 buildings inserted by anyone with "Leon" in their name:
```JSON
"key": "mykey",
"client": "myclient",
"assettype": "building",
"columns": [
    "buildingid",
    "assetid"
],
"limit": "10",
"condition": [
    {
        "column": "insertbyname",
        "operator": "like",
        "comparator": "leon"
    }
],
"version":"1.0"
```
All samples taken before March 1st 2020 that have been removed:
```JSON
"key": "mykey",
"client": "myclient",
"assettype": "sample",
"condition": [
    {
        "column": "sampledate",
        "operator": "<",
        "comparator": "2020-09-01"
    },
    {
        "column": "acmfullremoval",
        "operator": "=",
        "comparator": "1"
    }
],
"version":"1.0"
```

# Support
Email: support@octfolio.com
Website: [octfolio.com](https://octfolio.com.au/)

As the API grows we at OCTFOLIO will attempt to upload demos of the new features to this repository. Octfolio runs on PHP so the examples will focus around PHP but anything that can send a HTTP request can use the API.