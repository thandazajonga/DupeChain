import java.security.*;
//import org.bouncycastle.*;
import java.util.Base64;
public class StringUtil 
{
    public static String useSha256 (String data)
    {
        StringBuffer sBuffer= new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash=md.digest(data.getBytes("UTF-8"));
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length()==1) {
                    sBuffer.append("0");
                } else {
                    sBuffer.append(hex);
                }
            }
            return sBuffer.toString();
        } catch (Exception e) {
            System.out.println("Couldn't digest data.");
            throw new RuntimeException(e);
        }
    }
    //takes in sender private key and signs it and return an array of bytes
    public static byte[] applyECDSASig(PrivateKey privateKey,String input)
    {
        Signature dsa;
		byte[] output = new byte[0];
		try {
			dsa = Signature.getInstance("ECDSA", "BC");
			dsa.initSign(privateKey);
			byte[] strByte = input.getBytes();
			dsa.update(strByte);
			byte[] realSig = dsa.sign();
			output = realSig;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
        return output;
    }
    //Verifies a String signature 
	public static boolean verifyECDSASig(PublicKey publicKey, String data, byte[] signature) {
		try {
			Signature ecdsaVerify = Signature.getInstance("ECDSA", "BC");
			ecdsaVerify.initVerify(publicKey);
			ecdsaVerify.update(data.getBytes());
			return ecdsaVerify.verify(signature);
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
    
    public static String getStringFromKey(Key key) {
		return Base64.getEncoder().encodeToString(key.getEncoded());
	}
}
