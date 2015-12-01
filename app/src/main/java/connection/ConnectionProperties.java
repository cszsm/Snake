package connection;

import connection.enumeration.DeviceType;

/**
 * Created by Zsolt on 2015.05.03..
 * <p/>
 * Manages the connection, stores the socket and the device's type
 */
public class ConnectionProperties {

    private static ConnectionProperties instance = null;

    private DeviceType deviceType;
    private ConnectionSocket socket;
    private TransferThread transferThread;
    private int offset;

    protected ConnectionProperties() {
        offset = 0;
    }

    public static ConnectionProperties getInstance() {
        if (instance == null) {
            instance = new ConnectionProperties();
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
