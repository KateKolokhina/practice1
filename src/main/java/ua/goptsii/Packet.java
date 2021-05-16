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

    public Packet(byte bSrc, UnsignedLong bPktId, Integer wLen, short wCrc16_1, Message bMsq, short wCrc16_2){
        this.bSrc = bSrc;
        this.bPktId = bPktId;
        this.wLen = wLen;
        this.wCrc16_1 = wCrc16_1;
        this.bMsq = bMsq;
        this.wCrc16_2 = wCrc16_2;
    }

    public Packet(byte[] encodedPacket) throws Exception{
    ByteBuffer buffer = ByteBuffer.wrap (encodedPacket);
    Byte bMagicCheck = buffer.get();
    if (!bMagicCheck.equals(this.bMagic)){
        throw new Exception("Wrong bMagic!");
    }
    bSrc = buffer.get();
    long pktId = buffer.getLong();
    bPktId = UnsignedLong.fromLongBits(pktId);
    wLen = buffer.getInt();
    wCrc16_1= buffer.getShort();
        if (packetFirstLength()!=(wCrc16_1)){
            throw new Exception("Wrong wCrc16_1!");
        }
        ////////////////////////////
    int cType=buffer.getInt();
    int bUserId=buffer.getInt();
    byte[] messageText= new byte[wLen];
    buffer.get(messageText);
    bMsq = new Message(cType,bUserId, new String (messageText));
    bMsq.decode();
    wCrc16_2= buffer.getShort();
        if (packetSecondLength()!=(wCrc16_2)){
            throw new Exception("Wrong wCrc16_2!");
        }
    }


    public int packetFirstLength(){
        return Byte.BYTES+Byte.BYTES+Long.BYTES+Integer.BYTES;
    }

    public int packetSecondLength(){
        return bMsq.getMessageBytesLength();
    }
    public byte[] toPacket() throws Exception{
    Message message = bMsq;
        message.encode();
    byte[] packetPartFirst = ByteBuffer.allocate(packetFirstLength())
            .put(bMagic)
            .put(bSrc)
            .putLong(bPktId.longValue())
            .putInt(wLen)
            .array();

        byte[] packetPartSecond = ByteBuffer.allocate(packetSecondLength())
                .put(bMsq.getMessageForPacket())
                .array();

        wCrc16_1= (short)CRC.calculateCRC(CRC.Parameters.CRC16, packetPartFirst);
        wCrc16_2=(short)CRC.calculateCRC(CRC.Parameters.CRC16, packetPartSecond);

        int packetLength = packetPartFirst.length+packetPartSecond.length+wCrc16_1+wCrc16_2;
        return ByteBuffer.allocate(packetLength).put(packetPartFirst)
                .putShort(wCrc16_1)
                .put(packetPartSecond)
                .putShort(wCrc16_2)
                .array();
    }
}
