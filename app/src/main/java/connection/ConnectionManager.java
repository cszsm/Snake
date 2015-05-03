package connection;

import connection.enumeration.DeviceType;

/**
 * Created by Zsolt on 2015.05.03..
 */
public class ConnectionManager {
    private static ConnectionManager instance = null;

    private ConnectionSocket socket;
    private DeviceType deviceType;

    protected ConnectionManager() {
    }

    public static ConnectionManager getInstance() {
        if (instance == null)
            instance = new ConnectionManager();
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
}
