package tools;



import java.security.NoSuchAlgorithmException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.util.encoders.Base64;



/**
 * Utility methods for the symmetric encryption.
 * 
 * @author Athanasia Katsouraki 
 * 
 */
public class SymKeyTools 
{
  
    /**
     * Generates and returns a symmetric encryption key
     * @return the secret key
     * @throws Exception 
     */
    public static SecretKey GenSymKey() throws Exception{
       SecretKey skey = null ;
       try {
            skey = KeyGenerator.getInstance("AES").generateKey();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
       
       return skey;
    }
 
    /**
     * Convertes a secret key into a String
     * @param secretKey the secret key
     * @return the secret key as a String
     */
    public static String GenSymKeyToString (SecretKey secretKey)
    {
       String stringKey=null;
        if (secretKey != null) {
            stringKey = new String(Base64.encode(secretKey.getEncoded()));
        }   
        return stringKey;
    }
    
     /**
      * Converts a String into a SecretKey object
     * @param stringKey the String
     * @return the SecretKey object
     */
    public static SecretKey StringToGenSymKey (String stringKey) 
    {
       
         byte[]encodedKey;
         encodedKey = Base64.decode(stringKey.getBytes());
     
         SecretKey originalKey = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        
        return originalKey;
     }
}
