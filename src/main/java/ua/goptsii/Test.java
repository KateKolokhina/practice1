package ua.goptsii;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Test {

    public static void main(String[] args) {

        Message message = new Message(12, 12, "fuck");

        String str = "fuck";
        System.out.println(str.length() + " "+str.getBytes().length);

//        try {
//            message.encode();
//            System.out.println((message.getMessage()));
//            message.decode();
//            System.out.println((message.getMessage()));
//
//        } catch (InvalidKeyException e) {
//            e.printStackTrace();
//        } catch (BadPaddingException e) {
//            e.printStackTrace();
//        } catch (IllegalBlockSizeException e) {
//            e.printStackTrace();
//        }
    }
}
