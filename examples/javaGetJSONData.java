package examples;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class javaGetJSONData{
    public static void main(String[] args) throws IOException, InterruptedException{
        // HttpClient was added in Java 11 so check your version with java -version
        // This request will return the first 10 inspectionids and specificids from records where the contractors name has 'company'
        // in it and the record is assigned to audit 23

        // Defining the parameters I would like the send for this request
        String key = "myKey";
        String client = "myClient";
        String myUrl = "https://"+client+".octfolio.op/getData.php";
        String headers = "";
        String assetType = "record";
        Integer limit = 10;
        String[] columns = {"inspectionid","specificid"};
        List<Map<String,String>> conditions = new ArrayList<>();
        conditions.add(Map.of("column","contractorname","operator","like","comparator","company"));
        conditions.add(Map.of("column","auditid","operator","=","comparator","23"));

        // GET request parameters are contained in the URL seperated by an '&'
        // Java needs to encode each parameter value using percent encoding
        headers+="key="+URLEncoder.encode(key,"UTF-8");
        headers+="&client="+URLEncoder.encode(client,"UTF-8");
        headers+="&assettype="+URLEncoder.encode(assetType,"UTF-8");
        // I defined limit as an integer so I'll need to call its tostring method
        headers+="&limit="+URLEncoder.encode(limit.toString(),"UTF-8");
        for(int i=0;i<columns.length;i++){
            headers+="&columns["+i+"]="+URLEncoder.encode(columns[i],"UTF-8");
        }
        for(int i=0;i<conditions.size();i++){
            headers+="&condition["+i+"][column]="+URLEncoder.encode(conditions.get(i).get("column"),"UTF-8");
            headers+="&condition["+i+"][operator]="+URLEncoder.encode(conditions.get(i).get("operator"),"UTF-8");
            headers+="&condition["+i+"][comparator]="+URLEncoder.encode(conditions.get(i).get("comparator"),"UTF-8");
        }
        // add the parameters built above to the URL
        myUrl += "?"+headers;
        // default HTTP Verb is GET
        HttpClient requestClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(myUrl)).build();
        HttpResponse<String> response = requestClient.send(request,HttpResponse.BodyHandlers.ofString());
        // response is held in response.body() but is formatted as a JSON encoded string, you'll need to convert to 
        // whatever format you need
        System.out.println(response.body());
    }
}