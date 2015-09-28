package test;

import android.graphics.Point;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zscse on 2015. 09. 28..
 */
public class TestPacket implements Serializable{
    private long timestamp;
    private List<Integer> list;

    public TestPacket(long timestamp, List<Integer> list) {
        this.list = list;
        this.timestamp = timestamp;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public int getLength() {
        return list.size();
    }
}
