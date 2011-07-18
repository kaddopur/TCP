package mchi.csie.ntu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

class LinkHandler implements Runnable {
	
	public static final String TAG = "WIFIDev";
	Socket clientSocket;
	Handler mainHandler;

	public LinkHandler(Socket incoming) {
		// TODO Auto-generated constructor stub
		clientSocket  = incoming;
	}

	public LinkHandler(Handler mHandler, Socket incomingSocket) {
		// TODO Auto-generated constructor stub
		clientSocket  = incomingSocket;
		mainHandler = mHandler;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Log.i(TAG, "+LinkHandler.run()");
		
		Log.i(TAG, clientSocket.toString());
		
		final int ET_OUTPUT = 99999;
		
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			//PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true); // Autoflush
			String st = input.readLine();
			Log.e(TAG, "From client: " + st);
			
			String obj = "From client: " + st + "\n";
			mainHandler.removeMessages(0);
			Message m = mainHandler.obtainMessage(ET_OUTPUT, obj);
			mainHandler.sendMessage(m);
			
			//output.println("Good bye and thanks for all the fish :)");
			
			
			clientSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.i(TAG, "IOException");
		}
		
		Log.i(TAG, "-LinkHandler.run()");
	}
}
