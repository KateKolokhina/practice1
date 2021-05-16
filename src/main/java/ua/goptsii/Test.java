package ua.goptsii;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Test {

    public static void main(String[] args) {

        Message message = new Message(12, 12, "test");

        System.out.println(message.getMessage());
        System.out.println(message.getMessage());
        message.encode();
        System.out.println(message.getMessage());
        message.decode();
        System.out.println(message.getMessage());

    }
}
