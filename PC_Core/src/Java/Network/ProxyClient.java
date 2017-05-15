package Java.Network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ProxyClient implements Runnable {

	public static ServerSocket server;
	
	@Override
	public void run() {
		try {
			server = new ServerSocket(4565);
			System.out.println("[Proxy] Esperando cliente conectar-se!");
			System.out.println("[Proxy] IP: " + server.getInetAddress().getHostAddress() + ":" + server.getLocalPort());
			while(!server.isClosed()){
				Socket socket = server.accept();
				System.out.println("[Proxy] Cliente conectou-se ao servidor!");
				System.out.println("[Proxy] IP: " + server.getInetAddress().getHostAddress() + ":" + server.getLocalPort());
				new Thread(new Receiver(socket)).start();;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
