package mchi.csie.ntu;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.EditText;

public class SocketListener implements Runnable {
	
	public static final String TAG = "WIFIDev";
	public static final int SOCKET_LISTENER_MSG = 123456789;
	private int serverPort;
	
	
	public SocketListener(int port) {
		serverPort = port;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Log.e(TAG, "+SocketListener()");
		try {
			// establish server socket
			int connIndex = 0;
			ServerSocket serverSocket = new ServerSocket(serverPort);
			Log.e(TAG, "port:" + serverSocket.getLocalPort());

			while (true) {
				Socket incoming = serverSocket.accept();
				Log.e(TAG, "Connected a client!connIndex:"
								+ connIndex
								+ " RemoteSocketAddress:"
								+ String.valueOf(incoming.getRemoteSocketAddress()));
				Thread connHandle = new Thread(new LinkHandler(incoming));
				connHandle.start();
				connIndex++;
				
				if(connIndex > 3) {
					serverSocket.close();
					break;
				}
			}
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Log.e(TAG, "-SocketListener()");
		
	}
}



