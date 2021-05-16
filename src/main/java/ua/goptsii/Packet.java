package ua.goptsii;

import com.github.snksoft.crc.CRC;
import com.google.common.primitives.UnsignedLong;

import java.nio.ByteBuffer;

public class Packet {
    private Byte bMagic = 0x13;
    private Byte bSrc;
    private UnsignedLong bPktId;
    private Integer wLen;
    private Short wCrc16_1;
    private Message bMsq;
    private Short wCrc16_2;

    public static final int LENGTH_FIRST_PART_PACKAGE = Byte.BYTES + Byte.BYTES + Long.BYTES + Integer.BYTES;

    public Packet(byte bSrc, UnsignedLong bPktId, Integer wLen, short wCrc16_1, Message bMsq, short wCrc16_2) {
        this.bSrc = bSrc;
        this.bPktId = bPktId;
        this.wLen = wLen;
        this.wCrc16_1 = wCrc16_1;
        this.bMsq = bMsq;
        this.wCrc16_2 = wCrc16_2;
    }

    public Packet(byte[] encodedPacket) throws Exception {
        ByteBuffer buffer = ByteBuffer.wrap(encodedPacket);
        Byte bMagicCheck = buffer.get();

        if (!bMagicCheck.equals(this.bMagic)) {
            throw new Exception("Wrong bMagic!");
        }

        bSrc = buffer.get();
        long pktId = buffer.getLong();
        bPktId = UnsignedLong.fromLongBits(pktId);
        wLen = buffer.getInt();

        //TODO CALCULATE wCrc16_1Calculated AS WE DO IT BEFORE
        Short wCrc16_1Calculated = 0;

        wCrc16_1 = buffer.getShort();

        if (!wCrc16_1Calculated.equals(wCrc16_1)) {
            throw new Exception("Wrong wCrc16_1!");
        }

        ////////////////////////////

        int cType = buffer.getInt();
        int bUserId = buffer.getInt();
        byte[] messageText = new byte[wLen];
        buffer.get(messageText);
        bMsq = new Message(cType, bUserId, new String(messageText));

        bMsq.decode();

        //TODO CALCULATE wCrc16_2Calculated AS WE DO IT BEFORE
        Short wCrc16_2Calculated = 0;

        wCrc16_2 = buffer.getShort();

        if (!wCrc16_2Calculated.equals(wCrc16_2)) {
            throw new Exception("Wrong wCrc16_2!");
        }
    }

    public int packetSecondLength() {
        return bMsq.getMessageBytesLength();
    }

    public byte[] toPacket() {
        Message message = bMsq;
        message.encode();

        byte[] packetPartFirst = ByteBuffer.allocate(LENGTH_FIRST_PART_PACKAGE)
                .put(bMagic)
                .put(bSrc)
                .putLong(bPktId.longValue())
                .putInt(wLen)
                .array();

        //CRC of first part of message
        wCrc16_1 = (short) CRC.calculateCRC(CRC.Parameters.CRC16, packetPartFirst);

        byte[] packetPartSecond = ByteBuffer.allocate(packetSecondLength())
                .put(bMsq.getMessageForPacket())
                .array();

        //CRC of message
        wCrc16_2 = (short) CRC.calculateCRC(CRC.Parameters.CRC16, packetPartSecond);


        int packetLength = LENGTH_FIRST_PART_PACKAGE + Short.BYTES + packetSecondLength() + Short.BYTES;

        return ByteBuffer.allocate(packetLength)
                .put(packetPartFirst)
                .putShort(wCrc16_1)
                .put(packetPartSecond)
                .putShort(wCrc16_2)
                .array();
    }
}
