/******************************************************************************
 *  Compilation:  javac HelloWorld.java
 *  Execution:    java HelloWorld
 *
 ******************************************************************************/

import static java.lang.System.out;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;

import static java.lang.System.exit;

public class Base64Encode {
	
	protected static byte[] eddystoneBeaconId;
    
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
    
    public static void registerBeacon(String encodedEddystoneId){
    	// The fluent API relieves the user from having to deal with manual deallocation of system
    	// resources at the cost of having to buffer response content in memory in some cases.

    	try {
			System.out.println("Request response" + Request.Post("")
			.bodyString("Important stuff", ContentType.DEFAULT_TEXT)
			.execute().returnContent());
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	/*
    	Request.Post("http://targethost/login")
    	    .bodyForm(Form.form().add("username",  "vip").add("password",  "secret").build())
    	    .execute().returnContent();*/
    }

}
