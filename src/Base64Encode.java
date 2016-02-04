/******************************************************************************
 * 
 *
 ******************************************************************************/

import static java.lang.System.out;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStoreFactory;
import com.google.api.client.util.store.FileDataStoreFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;

import static java.lang.System.exit;

public class Base64Encode {
	
	protected static byte[] eddystoneBeaconId;
	private static final String APPLICATION_NAME = "Tablespace-BeaconTool/0.1.0";
	private static final java.io.File DATA_STORE_DIR =
		      new java.io.File(System.getProperty("user.home"), ".store/beacon_tool");
	/**
	   * Global instance of the {@link DataStoreFactory}. The best practice is to make it a single
	   * globally shared instance across your application.
	   */
	  private static FileDataStoreFactory dataStoreFactory;

	  /** Global instance of the HTTP transport. */
	  private static HttpTransport httpTransport;

	  /** Global instance of the JSON factory. */
	  private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	
    public static void main(String[] args) {
        
        String namespaceHexVal = null;
        String instanceIdHexVal = null;
        
        System.out.println("args.length: " + args.length);
        
        if(args.length == 0){
            System.out.println("No arguments given. Provide a 20-byte hex string for conversion to Base64.");
            System.exit(-1);
        }
        else if(args.length > 2){
            System.out.println("Too many arguments given. Provide a 20-byte hex string for conversion to Base64.");
            System.exit(-1); 
        }
        else if(args.length == 1){
        	System.out.println("Please provide both the namespace id and the instance id as separate arguments.");
            System.exit(-1); 
        }
        else if (args.length == 2){
            System.out.println("Converting namespace " + args[0] + " and instance " + args[1] + " to byte string.");
            namespaceHexVal = args[0];
            instanceIdHexVal = args[1];
            if (instanceIdHexVal.length() == 1){
            	instanceIdHexVal = "00000000000" + instanceIdHexVal;
            }
            
            System.out.println("Length of namespace argument: " + namespaceHexVal.length());
            if (namespaceHexVal.length() == 20){
            	System.out.println("Length of instance id argument: " + instanceIdHexVal.length());
            	if (instanceIdHexVal.length() == 12){
            		eddystoneBeaconId = hexStringToByteArray(namespaceHexVal + instanceIdHexVal);
            	}
            	else{
            		System.out.println("Please supply a 10-byte instance id hex string for conversion.");
                    System.exit(-1); 
            	}
            	
            }         
            else{
            	System.out.println("Please supply a 20-byte namespace hex string for conversion.");
                System.exit(-1); 
            }
        }
        
        if (eddystoneBeaconId.length < 16){
            System.out.println("Incorrect hex value given - hex was too short.");
            System.out.println("Byte array length: " + eddystoneBeaconId.length);
            System.exit(-1);
        }
        else if (eddystoneBeaconId.length > 16){
            System.out.println("Incorrect hex value given - hex was too long.");
            System.out.println("Byte array length: " + eddystoneBeaconId.length);
            System.exit(-1);
        }
        else{
            System.out.println("Hex length OK. Converting to Base64.");
            String base64EncodedEddystoneBeaconId = new String(org.apache.commons.codec.binary.Base64.encodeBase64(eddystoneBeaconId));
            System.out.println("Base64 encoded string: " + base64EncodedEddystoneBeaconId);
            //registerBeacon(base64EncodedEddystoneBeaconId);
            
        }
        
        /*    
		byte[] eddystoneBeaconId = new byte[] {
		(byte) 0x48, (byte) 0x2f,(byte) 0x9b, (byte) 0xe9, (byte) 0xde, (byte) 0x6a, (byte) 0x27, (byte) 0x1e, (byte) 0xab, (byte) 0x84, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01 };
    	String base64EncodedEddystoneBeaconId = new String(org.apache.commons.codec.binary.Base64.encodeBase64(eddystoneBeaconId));
		System.out.println(base64EncodedEddystoneBeaconId);*/
        
    }
    
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }
    
    /*public static void registerBeacon(String encodedEddystoneId){
    	try {
    	    httpTransport = GoogleNetHttpTransport.newTrustedTransport();
    	    dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);
    	    // authorization
    	    //Credential credential = authorize();
    	    // set up global Plus instance
    	    plus = new Plus.Builder(httpTransport, JSON_FACTORY, credential).setApplicationName(
    	        APPLICATION_NAME).build();
    	    
    	   // ...
    }
    	
	private static Credential authorize() throws Exception {
		  // load client secrets
		  GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
		      new InputStreamReader(PlusSample.class.getResourceAsStream("/client_secrets.json")));
		  // set up authorization code flow
		  GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
		      httpTransport, JSON_FACTORY, clientSecrets,
		      Collections.singleton(PlusScopes.PLUS_ME)).setDataStoreFactory(
		      dataStoreFactory).build();
		  // authorize
		  return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
	}*/

}
