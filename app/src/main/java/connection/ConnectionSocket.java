package connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Zsolt on 2015.05.03..
 */
public interface ConnectionSocket {

    public InputStream getInputStream() throws IOException;

    public OutputStream getOutputStream() throws IOException;

    public void close() throws IOException;
}
