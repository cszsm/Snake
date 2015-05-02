package connection.wifi;

import java.net.Socket;
import java.util.logging.SocketHandler;

import connection.enumaration.DeviceType;

/**
 * Created by Zsolt on 2015.05.02..
 */
public class WifiDirectManager {
    private static WifiDirectManager instance = null;

    private Socket socket;
    private DeviceType deviceType;

    protected WifiDirectManager() {}

    public static WifiDirectManager getInstance(){
        if(instance == null)
            instance = new WifiDirectManager();
        return instance;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }
}
