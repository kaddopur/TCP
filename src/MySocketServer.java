import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import android.content.Context;
import android.util.Log;

class MySocketServer implements Runnable {
	private static final String TAG = "MySocketServer";

	private static final String serverIpString = "192.168.43.1";
	private static final int serverListenPort = 8888;
	private static final int connectionMaxLength = 10;

	private static Context mContext = null;

	public static void startListen(Context ctx) {
		Log.e(TAG, "+startListen()");

		mContext = ctx;
		Thread serverSocketListen = new Thread(new MySocketServer());
		serverSocketListen.start();

		Log.e(TAG, "-startListen()");
	}

	public static void stopListen() {

	}

	@Override
	public void run() {
		try {
			// establish server socket
			int connIndex = 0;
			ServerSocket serverSocket = new ServerSocket(serverListenPort,
					connectionMaxLength, InetAddress.getByName(serverIpString));
			Log.e(TAG, "port:" + serverSocket.getLocalPort());

			while (true) {
				Socket incoming = serverSocket.accept();
				Log.e(TAG,
						"Connected a client!connIndex:"
								+ connIndex
								+ " RemoteSocketAddress:"
								+ String.valueOf(incoming
										.getRemoteSocketAddress()));
				Thread connHandle = new Thread(new ConnectionHandle(mContext,
						incoming, connIndex));
				connHandle.start();
				connIndex++;
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
