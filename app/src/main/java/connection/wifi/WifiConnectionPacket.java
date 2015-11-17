package connection.wifi;

import java.net.InetAddress;

import connection.Packet;

/**
 * Created by zscse on 2015. 11. 02..
 */
public class WifiConnectionPacket extends Packet {
    private InetAddress address;
    private boolean starter;

    public WifiConnectionPacket(InetAddress address, boolean starter) {
        this.address = address;
        this.starter = starter;
    }

    public InetAddress getAddress() {
        return address;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }

    public boolean isStarter() {
        return starter;
    }

    public void setStarter(boolean starter) {
        this.starter = starter;
    }
}
