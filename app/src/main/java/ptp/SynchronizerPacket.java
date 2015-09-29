package ptp;

import connection.Packet;

/**
 * Created by zscse on 2015. 09. 29..
 */
public class SynchronizerPacket extends Packet {
    private long time;

    public SynchronizerPacket(long time) {
        this.time = time;
    }

    public long getTime() {
        return time;
    }
}
