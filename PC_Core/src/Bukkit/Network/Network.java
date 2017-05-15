package Bukkit.Network;

import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;

public class Network {
	
	private Socket socket;
	private PrintWriter pw;
	
	public Network(){
		try{
			do{
				System.out.println("[Servidor] Tentando conectar com servidor bungeecord!");
				try{
					socket = new Socket("0.0.0.0", 4565);
				}catch(ConnectException e){
					System.out.println("[Servidor] Erro ao conectar-se com servidor bungeecord!");
					System.out.println("[Servidor] Reconexão em 30 segundos!");
					Thread.sleep(1000*30);
					continue;
				}
				System.out.println("[Servidor] Conexão com servidor feita com sucesso!");
				new Thread(new Receiver(socket)).start();
				break;
			}while(true);
			pw = new PrintWriter(socket.getOutputStream());

				
			
		}catch(Exception e){
			
		}
	}
	
	public PrintWriter getPW(){
		return this.pw;
	}
	
	public void send(String msg){
		pw.println(msg);
		System.out.println("[Servidor] Enviando mensagem para o proxy!");
		System.out.println("[Servidor] Mensagem:");
		System.out.println("[Servidor]    "   + msg);
		pw.flush();
		System.out.println("[Servidor] Flush");
	}

}
