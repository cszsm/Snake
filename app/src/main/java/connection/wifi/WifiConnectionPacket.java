package connection.wifi;

import java.io.Serializable;
import java.net.InetAddress;

/**
 * Created by zscse on 2015. 11. 02..
 */
public class WifiConnectionPacket implements Serializable {
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
}
