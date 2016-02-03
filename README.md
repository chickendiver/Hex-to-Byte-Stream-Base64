# Hex-to-Byte-Stream-Base64
Converts a hex value of 16 bytes to a byte array, and then outputs that array as Base64

Takes a 20 character namespace id and a 12 character instance id as arguments. 

To create a new namespace id, turn your beacon into configuration mode and configure it as an Eddystone beacon. Then, generate a new UID from URI using "tablespace.com/beacon[beacon id], where [beacon id] is the id printed on the back of the beacon.

To register the beacon, use https://developers.google.com/oauthplayground and input your Google Developer Console (https://console.developers.google.com) oauth credientials in the settings menu (select "client-side" OAuth flow). Then, enter "https://www.googleapis.com/auth/userlocation.beacon.registry" in the box on the left and hit "Authorize APIs".

This will authenticate you to use the appropriate oauth details. Then, configure a POST request to "https://proximitybeacon.googleapis.com/v1beta1/beacons:register", with the body containing a JSON string looking like this:

{
  "advertisedId": {
    "type": "EDDYSTONE",
    "id": "[BASE 64 ENCODED BEACON ID]"
  },
  "status": "ACTIVE",
  "description": "DESCRIPTION TO BE ATTACHED TO THE BEACON",
}
