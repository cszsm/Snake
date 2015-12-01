package connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Zsolt on 2015.05.03..
 * <p/>
 * Interface for sockets
 */
public interface ConnectionSocket {

    /**
     * Sends a packet to the remote device
     */
    void send(byte[] packet) throws IOException;

    /**
     * Receive a packet from the remote device
     */
    int receive(byte[] packet) throws IOException;

    /**
     * Closes the socket
     */
    void close() throws IOException;
}
