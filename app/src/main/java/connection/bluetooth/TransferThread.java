package connection.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothSocket;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import connection.Packet;
import connection.PacketSerialization;
import zsolt.cseh.snake.BluetoothActivity;

/**
 * Created by Zsolt on 2015.03.21..
 */
public class TransferThread extends Thread {
    private final BluetoothSocket bluetoothSocket;
    private final InputStream inputStream;
    private final OutputStream outputStream;

    private Packet packet;
    private int arrivedPackets;

    public TransferThread(BluetoothSocket bluetoothSocket) {
        this.bluetoothSocket = bluetoothSocket;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;

        arrivedPackets = 0;

        try {
            tmpIn = bluetoothSocket.getInputStream();
            tmpOut = bluetoothSocket.getOutputStream();
        } catch (IOException e) {

        }

        inputStream = tmpIn;
        outputStream = tmpOut;
    }

    public void run() {
        byte[] buffer = new byte[1024];
        int bytes = 0;

        while (true) {
            try {
                Log.v("transfer", String.valueOf(bytes) + "bytes");

                if(bytes != 0) {
//                    Log.v("tag", "bytes != 0");
                    try {
                        packet = PacketSerialization.deserialize(buffer);
                        arrivedPackets++;
//                        Log.v("tag", "FOOD X - " + packet.getFoodX());
//                        Log.v("tag", "FOOD Y - " + packet.getFoodY());
                        Log.v("transfer", "Packet arrived");
                    } catch (ClassNotFoundException e) {
                        Log.v("transfer", "elkurodott");
                        e.printStackTrace();
                    }
                }

                bytes = inputStream.read(buffer);
            } catch (IOException e) {
                Log.v("transfer", "bajvan");
                break;
            } catch (Exception e) {
                Log.v("transfer", "miafaszvan");
                Log.v("transfer", e.getMessage());
                break;
            }
        }
    }

    public void write(byte[] bytes) {
        try {
            Log.v("transfer", "Packet sent");
            outputStream.write(bytes);
        } catch (IOException e) {

        }
    }

    public void cancel() {
        try {
            bluetoothSocket.close();
        } catch (IOException e) { }
    }

    public Packet getPacket() {
        if(0 < arrivedPackets) {
            arrivedPackets--;
            return packet;
        }
        else
            return null;
    }
}
