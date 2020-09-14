<?php
//we will fill this later, leave blank
$headings = "";
$key = "mykey";
$client = "myclient";
$myUrl = "https://$client.octfolio.op/getData.php";
$assettype = "sample";
$limit = 10;
$conditions = Array(
    Array("column"=>"sampledate","operator"=>"<","comparator"=>"2020-09-01"),
    Array("column"=>"acmfullremoval","operator"=>"=","comparator"=>"1")
);
//GET requests typically carry their parameters in the URL so I'm going to append the parameters to my URL
$headings .= "?key=$key";
$headings .= "&client=$client";
$headings .= "&assettype=$assettype";
if(isset($limit)&&$limit!=""){
    $headings.="&limit=$limit";
}
if(isset($conditions)&&is_array($conditions)){
    for($i=0;$i<count($conditions);$i++){
        if(!isset($conditions[$i]['column'])||!isset($conditions[$i]['operator'])||!isset($conditions[$i]['comparator'])){
            echo "Invalid conditional headings skipped!";
            continue;
        }
        $headings.='&conditon[$i][column]='.$conditions[$i]['column'];
        $headings.='&conditon[$i][operator]='.$conditions[$i]['operator'];
        $headings.='&conditon[$i][comparator]='.$conditions[$i]['comparator'];
    }
}
$myUrl .= $headings;
// echo $myUrl."\n";

$myConnetion = curl_init();
//pointing curl at the API with the parameters in the URL
curl_setopt($myConnetion, CURLOPT_URL, $myUrl);

//curls default request type is a GET request
// curl_setopt($myConnetion, CURLOPT_CUSTOMREQUEST, "GET");

//I want something I can decode to JSON
curl_setopt($myConnetion, CURLOPT_RETURNTRANSFER, true);
$result = curl_exec($myConnetion);
if(curl_errno($myConnetion)){
    echo 'Curl error: ' . curl_error($myConnetion);
}
curl_close($myConnetion);
print_r(json_decode($result,true));
?>