package connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Zsolt on 2015.05.03..
 *
 * Interface for sockets
 */
public interface ConnectionSocket {

    InputStream getInputStream() throws IOException;

    OutputStream getOutputStream() throws IOException;

    void close() throws IOException;
}
