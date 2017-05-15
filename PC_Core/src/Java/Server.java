package Java;

import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Server {
	
	private String name;
	private InetSocketAddress ip;
	private int onlines = 0;
	private int max;
	private Status action;
	private Socket socket;
	private PrintWriter print;
	
	public Server(String name, InetSocketAddress ip){
		this.name = name;
		this.ip = ip;
	}
	
	public void setPrintWriter(PrintWriter print){
		this.print = print;
	}
	
	public PrintWriter getPrintWriter(){
		return this.print;
	}
	
	
	public void setSocket(Socket socket){
		this.socket = socket;
	}
	
	public Socket getSocket(){
		return this.socket;
	}
	
	public Server(String name, String ip, int port){
		this.name = name;
		this.ip = new InetSocketAddress(ip, port);
	}
	
	public String getName(){
		return this.name;
	}
	
	public InetSocketAddress getAddres(){
		return this.ip;
	}
	
	public void setStatus(Status ac){
		this.action = ac;;
	}
	
	public boolean hasOnline(){
		return (this.action == Status.LIGADO);
	}
	
	public Status getStatus(){
		return action;
	}
	
	public void setOnlines(int onlines){
		this.onlines = onlines;
	}
	
	public int getOnlines(){
		return this.onlines;
	}
	
	public void setMaxPlayers(int max){
		this.max = max;
	}
	
	public int getMaxPlayers(){
		return this.max;
	}
}
