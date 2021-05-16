package ua.goptsii;

import com.google.common.primitives.UnsignedLong;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class PacketTest {

    public static final String TEST_MESSAGE = "test";
    @Test
    public void selfTest() throws Exception {

        Packet pack = new Packet((byte) 6, UnsignedLong.valueOf(1L),new Message(5, 4,TEST_MESSAGE ));
        Packet pack2 = new Packet(pack.toPacket());

        pack.getBMsq().decode();
        System.out.println(pack.getBMsq().getMessage());
        System.out.println(pack2.getBMsq().getMessage());

        Assert.assertEquals(pack.getBSrc(), pack2.getBSrc());
        Assert.assertEquals(pack.getBPktId(), pack2.getBPktId());
        Assert.assertEquals(pack.getWLen(), pack2.getWLen());

        Assert.assertEquals(pack.getWCrc16_1(), pack2.getWCrc16_1());
        Assert.assertEquals(pack.getWCrc16_2(), pack2.getWCrc16_2());

        Assert.assertEquals(pack.getBMsq().getCType(), pack2.getBMsq().getCType());
        Assert.assertEquals(pack.getBMsq().getBUserId(), pack2.getBMsq().getBUserId());
        Assert.assertArrayEquals(pack.getBMsq().getMessageForPacket(), pack2.getBMsq().getMessageForPacket());
        Assert.assertEquals(TEST_MESSAGE, pack2.getBMsq().getMessage());
    }

}
