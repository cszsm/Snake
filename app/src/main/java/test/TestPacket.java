package test;

import android.graphics.Point;

import java.io.Serializable;
import java.util.List;

import connection.ConnectionManager;
import connection.Packet;
import connection.enumeration.DeviceType;

/**
 * Created by zscse on 2015. 09. 28..
 */
public class TestPacket extends Packet {
    private static int lastId = 1;
    private int id;
    private DeviceType sender;
    private long timestamp;
    private List<Integer> list;

    public TestPacket(long timestamp, List<Integer> list) {
        id = lastId;
        lastId++;
        sender = ConnectionManager.getInstance().getDeviceType();
        this.list = list;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public DeviceType getSender() {
        return sender;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getLength() {
        return list.size();
    }
}
