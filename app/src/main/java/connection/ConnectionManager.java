package connection;

import connection.enumeration.DeviceType;

/**
 * Created by Zsolt on 2015.05.03..
 * <p/>
 * Manages the connection, stores the socket and the device's type
 */
public class ConnectionManager {

    private static ConnectionManager instance = null;

    private DeviceType deviceType;
    private ConnectionSocket socket;
    private TransferThread transferThread;
    private int offset;

    protected ConnectionManager() {
        offset = 0;
    }

    public static ConnectionManager getInstance() {
        if (instance == null) {
            instance = new ConnectionManager();
        }
        return instance;
    }

    public ConnectionSocket getSocket() {
        return socket;
    }

    public void setSocket(ConnectionSocket socket) {
        this.socket = socket;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public TransferThread getTransferThread() {
        return transferThread;
    }

    public void setTransferThread(TransferThread transferThread) {
        this.transferThread = transferThread;
    }

    public void reset() {
        instance = null;
    }
}
