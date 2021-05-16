package ua.goptsii;

import lombok.Data;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Data
public class Message {
    private Integer cType;
    private Integer bUserId;

    /* easy to save message in byte array for encoding and etc. */
    private byte [] message;

    public static final int BYTES_WITHOUT_MESSAGE = Integer.BYTES * 2;

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

    public byte[] getMessageForPacket(){

        //ByteBuffer - easily to work with memory
        return ByteBuffer.allocate(getMessageBytesLength())
                .putInt(cType)
                .putInt(bUserId)
                .put(message).array();
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

    public int getTextMessageBytesLength(){
        return message.length;
    }

    public int getMessageBytesLength() {
        return  BYTES_WITHOUT_MESSAGE + getTextMessageBytesLength();
    }
}
