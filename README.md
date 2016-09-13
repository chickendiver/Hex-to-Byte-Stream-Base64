# Hex-to-Byte-Stream-Base64
Converts a hex value of 16 bytes to a byte array, and then outputs that array as Base64

Takes a 20 character namespace id and a 12 character instance id as arguments. 

To create a new namespace id, turn your beacon into configuration mode and configure it as an Eddystone beacon. Then, generate a new UID from URI using "tablespace.com/beacon[beacon id], where [beacon id] is the id printed on the back of the beacon.

Compile the program, by running

    javac -cp "./lib/*" ./src/Base64Encode.java
    
Then add your namespace and instance ids as the arguments to 

    java -cp "./lib/commons-codec-1.10.jar;./src/;" Base64Encode [namespaceid] [instanceid]


To register the beacon, use https://developers.google.com/oauthplayground and input your Google Developer Console (https://console.developers.google.com) oauth credientials in the settings menu (select "client-side" OAuth flow). Then, enter "https://www.googleapis.com/auth/userlocation.beacon.registry" in the box on the left and hit "Authorize APIs".

This will authenticate you to use the appropriate oauth details. Then, configure a POST request to "https://proximitybeacon.googleapis.com/v1beta1/beacons:register", with the body containing a JSON string looking like this:
<PRE> {
  "advertisedId": {
    "type": "EDDYSTONE",
    "id": "[BASE 64 ENCODED BEACON ID]"
  },
  "status": "ACTIVE",
  "description": "DESCRIPTION TO BE ATTACHED TO THE BEACON",
}
</PRE> 

This will return a new beacon instance, including a beacon name. Use that beacon name to attach data, by POSTing this request:

     {
      "namespacedType":"bradleys-oauth-playground/string",
      "data":"[data string]"
    }

to 

    https://proximitybeacon.googleapis.com/v1beta1/beacons/[beaconNam]e/attachments

For more information, use:

https://developers.google.com/beacons/proximity/how-tos/authorizing

https://developers.google.com/beacons/proximity/register?hl=en


