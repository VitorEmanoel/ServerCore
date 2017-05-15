package Java.Network;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Receiver implements Runnable {

	private Socket socket;
	public Receiver(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		try {
			Scanner scanner = new Scanner(socket.getInputStream());
			while(scanner.hasNextLine()){
				System.out.println("[Proxy] Recebendo mensagem do cliente!");
				String text = scanner.nextLine();
				System.out.println("[Proxy] Mensagem: " );
				System.out.println("[Proxy]    " + text);
				if(text.contains("ligado") || text.contains("desligado")){
					ProxyManager.loadServer(text, socket);
				}
				if(text.equalsIgnoreCase("desconectar")){
					System.out.println("[Proxy] Cliente desconectado!");
					System.out.println("[Proxy] IP: "+ socket.getInetAddress().getHostAddress() + ":" + socket.getPort());
					socket.close();
					continue;
				}
			}
			scanner.close();
		} catch (IOException | IllegalArgumentException e) {
			e.printStackTrace();
		}
		
	}

}
