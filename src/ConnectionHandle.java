import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

import android.content.Context;
import android.util.Log;

class ConnectionHandle implements Runnable {
	public static final String TAG = "ConnectionHandle:";
	public static final String DELIMITER = "##";
	public static final String CMD_GETDIR = "cmd::getdir";

	private Context mContext;
	private Socket connectedSocket;
	private int connIndex;

	public ConnectionHandle(Context ctx, Socket incoming, int connIdx) {
		mContext = ctx;
		connectedSocket = incoming;
		connIndex = connIdx;
	}

	@Override
	public void run() {
		Log.e(TAG, "+run()");

		try {
			try {
				InputStream inStream = connectedSocket.getInputStream();
				OutputStream outStream = connectedSocket.getOutputStream();

				Scanner in = new Scanner(inStream, "UTF8");
				PrintStream out = new PrintStream(outStream, true, "UTF8");
				// PrintWriter out = new PrintWriter(outStream, true);

				// InputStreamReader reader = new InputStreamReader(inStream,
				// "UTF8");
				// OutputStreamWriter writer = new OutputStreamWriter(outStream,
				// "UTF8");

				// String test = TAG + "abc123";
				// writer.write(test);
				// writer.flush();

				boolean done = false;

				in.useDelimiter(DELIMITER); // Delimiter token.

				while (!done && in.hasNext()) {

					String token = in.next();
					Log.e(TAG, token);

					if (token.equals(CMD_GETDIR)) {
						String parentDir = in.next();
						//String sendDirInfo = FileExplorer.getSubDir(parentDir);
						//out.print(sendDirInfo);
						out.flush();
					}

					if (token.equals("Bye")) {
						done = true;
					}
				}

				connectedSocket.close();

				Thread.sleep(10);
			} finally {
				// incoming.close();
				// outStream.close();
			}
		} catch (IOException e) {
			Log.e(TAG, "IOException:" + e.getMessage());
			e.printStackTrace();
		} catch (InterruptedException e) {
			Log.e(TAG, "InterruptedException:" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			Log.e(TAG,
					"Exception:" + e.getClass().getName() + " msg:"
							+ e.getMessage());
		}

		Log.e(TAG, "-run()");
	}
}