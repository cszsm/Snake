package ptp;

import java.io.Serializable;

/**
 * Created by zscse on 2015. 09. 29..
 *
 * A packet for sending timestamp
 */
public class SynchronizerPacket implements Serializable {
    private long time;

    public SynchronizerPacket(long time) {
        this.time = time;
    }

    public long getTime() {
        return time;
    }
}
