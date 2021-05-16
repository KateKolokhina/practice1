package ua.goptsii;

import com.github.snksoft.crc.CRC;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Test {

    public static void main(String[] args) {

        Message message = new Message(12, 12, "test");

        byte[] packetPartSecond = ByteBuffer.allocate(message.getMessageBytesLength() )
                .put(message.getMessageForPacket())
                .array();

//        //CRC of message
//        Short wCrc16_2=(short) CRC.calculateCRC(CRC.Parameters.CRC16, packetPartSecond);
//
//        System.out.println(wCrc16_2+" "+wCrc16_2.BYTES+" "+ Short.BYTES);

        System.out.println(message.getMessage());
        System.out.println(message.getMessage());
        message.encode();
        System.out.println(message.getMessage());
        message.decode();
        System.out.println(message.getMessage());

    }
}
