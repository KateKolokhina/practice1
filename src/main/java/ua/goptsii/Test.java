package ua.goptsii;

import com.github.snksoft.crc.CRC;
import com.google.common.primitives.UnsignedLong;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Test {

    public static void main(String[] args) {

        UnsignedLong k  = UnsignedLong.valueOf(478697);
        Message bMsq = new Message(10, 1, "test");
        Packet p = new Packet((byte) 123, k, bMsq.getMessageBytesLength(), bMsq);
        System.out.println(Arrays.toString(p.toPacket()));
        byte [] packet= {19,
                123,
                0, 0, 0, 0, 0, 7, 77, -23, 0, 0, 0, 12, -89, -109, 0, 0, 0, 10, 0, 0, 0, 1, 94, -68, -6, -12, 95, 99, -46, -42, -96, 55, -36, 28, 117, -40, 107, 9, 47, -122};

        byte[] packetHex = {
                0x13,
                0x7B,
                0x00,
                0x00,
                0x00,
                0x00,
                0x00,
                0x07,
                0x4D,
                -0x17,
                0x00,
                0x00,
                0x00,
                0x0C,
                -0x59,
                -0x6D,
                0x00,
                0x00,
                0x00,
                0x0A,
                0x00,
                0x00,
                0x00,
                0x01,
                0x5E,
                -0x44,
                -0x06,
                -0x0C,
                0x5F,
                0x63,
                -0x2E,
                -0x2A,
                -0x60,
                0x37,
                -0x24,
                0x1C,
                0x75,
                -0x28,
                0x6B,
                0x09,
                0x2F,
                -0x7A
        };
        System.out.println(Arrays.toString(packetHex));
        try {
            Packet p1 = new Packet(packetHex);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
