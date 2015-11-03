package connection.wifi;

import java.net.InetAddress;

import connection.Packet;

/**
 * Created by zscse on 2015. 11. 02..
 */
public class WifiConnectionPacket extends Packet {
    private InetAddress address;

    public WifiConnectionPacket(InetAddress address) {
        this.address = address;
    }

    public InetAddress getAddress() {
        return address;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }
}
