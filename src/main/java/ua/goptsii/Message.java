package ua.goptsii;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Message {
    private Integer cType;
    private Integer bUserId;
    private byte [] message;
    private Cipher cipher;
    private SecretKey secretKey;

    public Message(int cType, int bUserId, String message){
        this.cType = cType;
        this.bUserId = bUserId;
        this.message = message.getBytes();
        byte[] encryptionKeyBytes = "thisisa128bitkey".getBytes();
        secretKey = new SecretKeySpec(encryptionKeyBytes, "AES");
        try {
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }

    }

    public String getMessage() {
            return new String(message);

    }

    public void encode() throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        message  = cipher.doFinal(message);
    }

    public void decode() throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        message  = cipher.doFinal(message);
    }

    public int lengthBytesText(){
        return message.length;
    }
    public int lengthBytesFull(){
        return Integer.BYTES+Integer.BYTES+lengthBytesText();
    }
}
