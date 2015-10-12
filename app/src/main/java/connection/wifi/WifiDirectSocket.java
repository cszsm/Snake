package connection.wifi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import connection.ConnectionSocket;

/**
 * Created by Zsolt on 2015.05.03..
 * <p/>
 * Delegator class for Socket
 * Implements ConnectionSocket
 */
public class WifiDirectSocket implements ConnectionSocket {

    private Socket socket;

    public WifiDirectSocket(Socket socket) {
        this.socket = socket;
    }

//    @Override
//    public InputStream getInputStream() throws IOException {
//        return socket.getInputStream();
//    }
//
//    @Override
//    public OutputStream getOutputStream() throws IOException {
//        return socket.getOutputStream();
//    }

    @Override
    public void send(byte[] packet) throws IOException {
        socket.getOutputStream().write(packet);
    }

    @Override
    public int receive(byte[] packet) throws IOException {
        return socket.getInputStream().read(packet);
    }

    @Override
    public void close() throws IOException {
        socket.close();
    }
}
