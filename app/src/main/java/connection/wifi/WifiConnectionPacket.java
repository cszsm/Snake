package connection.wifi;

import java.net.InetAddress;

import connection.Packet;

/**
 * Created by zscse on 2015. 11. 02..
 */
public class WifiConnectionPacket extends Packet {
    private InetAddress source;
    private InetAddress destination;

    public WifiConnectionPacket(InetAddress source) {
        this.source = source;
    }

    public WifiConnectionPacket(InetAddress source, InetAddress destination) {
        this.source = source;
        this.destination = destination;
    }

    public InetAddress getSource() {
        return source;
    }

    public InetAddress getDestination() {
        return destination;
    }

    public void setDestination(InetAddress address) {
        this.destination = address;
    }
}
