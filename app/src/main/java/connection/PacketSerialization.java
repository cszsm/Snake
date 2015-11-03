package connection;

import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Zsolt on 2015.03.24..
 * <p/>
 * Serialize and deserialize a packet
 */
public class PacketSerialization {

    public static byte[] serialize(Packet packet) throws IOException {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        ObjectOutputStream o = new ObjectOutputStream(b);
        o.writeObject(packet);
        Log.v("udp", String.valueOf(b.size()));
        return b.toByteArray();
    }

    public static Packet deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream b = new ByteArrayInputStream(bytes);
        ObjectInputStream o = new ObjectInputStream(b);
        return (Packet) o.readObject();
    }
}
