package mchi.csie.ntu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import org.apache.http.conn.util.InetAddressUtils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TCPTest extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		final Button bt_client = (Button) findViewById(R.id.button1);
		final Button bt_server = (Button) findViewById(R.id.button2);
		final EditText et_output = (EditText) findViewById(R.id.EditText1);

		bt_client.setText("Client");
		bt_server.setText("Server");
		et_output.setText("");

		bt_client.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				//connectToServer();
				
				//----------------------
				WifiManager mWifiManager = (WifiManager)getSystemService(WIFI_SERVICE);
				WifiInfo wifiinfo = mWifiManager.getConnectionInfo();
				DhcpInfo dhcpinfo = mWifiManager.getDhcpInfo();
				et_output.setText(dhcpinfo.toString() + "\n");
				//----------------------
				
				
				
				
				
				
				
				
				writeToServer();

			}

			private void writeToServer() {
				String serverIP = "192.168.43.1";
				int serverPort = 12345;
				
				try {
					Socket s = new Socket(serverIP, serverPort);
					
					et_output.append("+writeToServer()\n");
					// outgoing stream redirect to socket
					OutputStream out = s.getOutputStream();
					PrintWriter output = new PrintWriter(out);
					output.println("Hello Android!");
					output.flush(); 
					et_output.append("-writeToServer()\n");
					
					
					/*
					BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));

					// read line(s)
					String st = input.readLine();
					et_output.append(st + "\n");
					*/

					// Close connection
					//s.close();

				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					et_output.append("UnknownHostException\n");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					et_output.append("IOException\n");
				}
				
			}

			private void connectToServer() {
				// TODO Auto-generated method stub
				WifiManager mWifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);

				if (!mWifiManager.isWifiEnabled()) {
					mWifiManager.setWifiEnabled(true);

				}

				WifiConfiguration netConfig = new WifiConfiguration();
				netConfig.SSID = "\"Bomb\"";
				netConfig.preSharedKey = "\"bombplus\"";
				netConfig.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
			    netConfig.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
			    netConfig.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
			    netConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
			    netConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
			    netConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
			    netConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
			    netConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP); 

				if (mWifiManager.enableNetwork(
						mWifiManager.addNetwork(netConfig), true)) {
					et_output.setText("[Hotspot] get\n");
				} else {
					et_output.setText("no [Hotspot]\n");
				}
			}
		});

		
		
		
		
		
		
		
		
		
		
		bt_server.setOnClickListener(new View.OnClickListener() {
			
			public final String TAG = "WIFIDev";

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				//openHotspot();
				
				/*----------------------
				WifiManager mWifiManager = (WifiManager)getSystemService(WIFI_SERVICE);
				WifiInfo wifiinfo = mWifiManager.getConnectionInfo();
				DhcpInfo dhcpinfo = mWifiManager.getDhcpInfo();
				et_output.append(dhcpinfo.toString() + "\n");
				*/
				
				
				createServer();

			}

			private void createServer() {
				// TODO Auto-generated method stub
				int serverPort = 12345;
				
				Log.e(TAG, "+createServer()");
				Thread th_socketListener = new Thread(new SocketListener(serverPort));
				th_socketListener.start();
				Log.e(TAG, "-createServer()");
				
				
				/*
				try {
					Boolean end = false;
					ServerSocket ss = new ServerSocket(serverPort);
					
					
					while (!end) {
						// Server is waiting for client here, if needed
						et_output.append("wait for client\n");
						Socket s = ss.accept();
						et_output.append("client accepted\n");
						BufferedReader input = new BufferedReader(
								new InputStreamReader(s.getInputStream()));
						PrintWriter output = new PrintWriter(s
								.getOutputStream(), true); // Autoflush
						String st = input.readLine();
						et_output.append("From client: " + st);
						output.println("Good bye and thanks for all the fish :)");
						s.close();

						end = true;
					}
					ss.close();

				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				*/
			}

			private void openHotspot() {
				// TODO Auto-generated method stub
				WifiManager mWifiManager = (WifiManager) getSystemService(WIFI_SERVICE);

				if (mWifiManager.isWifiEnabled()) {
					mWifiManager.setWifiEnabled(false);
				}
				Method[] wmMethods = mWifiManager.getClass()
						.getDeclaredMethods();
				for (Method method : wmMethods) {
					if (method.getName().equals("setWifiApEnabled")) {
						WifiConfiguration netConfig = new WifiConfiguration();
						netConfig.SSID = "\"Bomb\"";
						netConfig.preSharedKey = "\"bombplus\"";
						netConfig.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
					    netConfig.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
					    netConfig.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
					    netConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
					    netConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
					    netConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
					    netConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
					    netConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP); 
						try {
							method.invoke(mWifiManager, netConfig, true);
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
					}
				}
			}
		});

	}
}