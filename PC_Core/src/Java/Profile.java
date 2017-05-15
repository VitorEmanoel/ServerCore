package Java;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import Bungee.Main;



public class Profile {
	
	
	public static HashMap<UUID, Profile> profiles = new HashMap<>();
	private static Connection con;
	private Map<String, Group> servergroup = new HashMap<String, Group>();
	private UUID uuid;
	private String password;
	private String realname;
	private String fakename;
	private boolean logged;
	private String lastloggin;
	private String email;
	private boolean legacy;
	
	public Profile(UUID uuid, String realName, String password){
		this.uuid = uuid;
		this.realname = realName;
		this.fakename = realName;
		this.password = password;
	}
	
	public String getLastLoggin(){
		return this.lastloggin;
	}
	
	
	public void setLastLoggin(String loc){
		this.lastloggin = loc;
	}
	
	public void setLogged(boolean loged){
		this.logged = loged;
	}
	
	public boolean hasLogged(){
		return logged;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public String getEmail(){
		return this.email;
	}

	public UUID getUniqueID(){
		return this.uuid;
	}
	
	public void Legacy(boolean legacy){
		this.legacy = legacy;
	}
	
	public Boolean getLegacy(){
		return this.legacy;
	}
	
	public void addGroup(String server, Group group){
		this.servergroup.put(server, group);
	}
	
	public void removeGroup(String server){
		this.servergroup.remove(server);
	}
	
	public List<String> getServerGroupList(){
		List<String> list = new ArrayList<>();
		servergroup.keySet().forEach(key->{
			list.add(key + ":" + servergroup.get(key).toString());
		});
		return list;
		
	}
	
	public Group getGroup(String server){
		return this.servergroup.get(server);
	}
	
	public void setFakeName(String fakeName){
		this.fakename = fakeName;
	}
	
	public String getFakeName(){
		return this.fakename;
	}
	
	public String getName(){
		return this.realname;
	}
	
	public String getPassword(){
		return this.password;
	}
	
	public void changePassword(String newpassword){
		this.password = newpassword;
	}
	
	public boolean hasPermission(Group group, String server){
		String[] sdata = server.split("-");
		String svtype = sdata[0];
		if(servergroup.containsKey(server))
			if(servergroup.get(server).getValue() <= group.getValue())
				return true;
		if(servergroup.containsKey("*"))
			if(servergroup.get("*").getValue() <= group.getValue())
				return true;
		if(servergroup.containsKey(svtype + "-*"))
			if(servergroup.get(svtype + "-*").getValue() <= group.getValue())
				return true;
		return false;
	}
	
	//Statics methods
	public static void load(){
		Main.mysql.open();
		System.out.println("--------------------[Profiles]--------------------");
		System.out.println("Carregando os perfis!");
		con = Main.mysql.getConnection();
		try{
			Statement s = con.createStatement();
			s.execute("CREATE DATABASE IF NOT EXISTS PROJECTCRAFT_CORE;");
			s.execute("USE PROJECTCRAFT_CORE;");
			s.execute("CREATE TABLE IF NOT EXISTS PROFILES (uuid varchar(50), "
															  + "name varchar(16), "
															  + "fakename varchar(16),"
															  + "password longtext,"
															  + "logged tinyint,"
															  + "groups longtext,"
															  + "lastlogin text,"
															  + "email text);");
			
			ResultSet res = con.createStatement().executeQuery("SELECT * FROM PROFILES;");
			while (res.next()){
				UUID uuid = UUID.fromString(res.getString("uuid"));
				String name = res.getString("name");
				String fakename = res.getString("fakename");
				String password = res.getString("password");
				String email = res.getString("email");
				boolean logged = (res.getString("logged") == "1") ? true : false;
				
				String text = res.getString("groups");
				String[] v = text.split(",");
		        String[] groups = new String[text.split(":").length -1];
		        String[] servers = new String[text.split(":").length - 1];
		        for(int i = 0; i < v.length; i++){
		            String[] pv = v[i].split(":");
		            groups[i] = pv[1];
		            servers[i] = pv[0];
		        }
				Profile profile = new Profile(uuid, name, password);
				profile.setLogged(logged);
				profile.setFakeName(fakename);
				profile.setEmail(email);
				for(int i=0;i<servers.length;i++){
					profile.addGroup(servers[i], Group.valueOf(groups[i]));
				}
				Profile.register(profile);
			}
			System.out.println("Perfis carregados com sucesso!");
		}catch(SQLException e){
			e.printStackTrace();
			System.out.println("Ocorreu um erro ao carregar os perfis!");
		}finally{
			System.out.println("--------------------[Profiles]--------------------");
			Main.mysql.close();
		}
		
	}
	
	public static void save(){
		Main.mysql.open();
		con = Main.mysql.getConnection();
		try{
			System.out.println("--------------------[Profiles]--------------------");
			con.createStatement().execute("use projectcraft_core");
			System.out.println("Salvando perfis!");
			for(Profile profile : profiles.values()){
				String groups = "";
				if(profile.getServerGroupList().size() > 1){
					for(String key : profile.getServerGroupList()){
						groups += key + ",";
					}
				}else{
					groups = profile.getServerGroupList().get(0);
				}
				PreparedStatement register = con.prepareStatement("INSERT INTO `profiles`(`uuid`, `name`, `fakename`, `password`, `logged`, `groups`, `lastlogin`, `email`) VALUES ('" + profile.getUniqueID() + "', '" + profile.getName() + "','" + profile.getFakeName() + "','" + profile.getPassword() + "', '" + booleanToInt(profile.hasLogged()) + "', '" + groups + "','" + profile.getLastLoggin() + "', '" + profile.getEmail() + "');");
				PreparedStatement update = con.prepareStatement("UPDATE `profiles` SET `name`='" + profile.getName() + "',`fakename`='" + profile.getFakeName() + "',`password`='" + profile.getPassword() + "',`logged`='" + booleanToInt(profile.hasLogged()) + "',`groups`='" + groups + "',`lastlogin`='" + profile.getLastLoggin() + "', `email`='" + profile.getEmail() + "' WHERE UUID='" + profile.getUniqueID() + "';");
				ResultSet verify = con.createStatement().executeQuery("SELECT * FROM `profiles` WHERE UUID='" + profile.getUniqueID() + "';");
				if(verify.next())
					update.executeUpdate();
				else
					register.executeUpdate();
				
			}
			System.out.println("Perfis salvos com sucesso!");
		}catch(SQLException e){
			e.printStackTrace();
			System.out.println("Ocorreu um erro ao carregar os perfis!");
		}finally{
			System.out.println("--------------------[Profiles]--------------------");
			Main.mysql.close();
		}
	}
	
	public static void update(String name){
		
	}
	
	public static Profile get(String name){
		Profile profile = null;
		for(Profile p : profiles.values()){
			if(p.getName().equalsIgnoreCase(name)){
				profile = p;
			}
		}
		return profile;
	}
	
	public static Profile get(UUID uuid){
		return profiles.get(uuid);
	}
	
	public static void register(Profile prof){
		if(profiles.containsKey(prof))return;
		profiles.put(prof.getUniqueID(), prof);
	}
	
	public static void unregister(Profile prof){
		if(!profiles.containsValue(prof))return;
		profiles.remove(prof.getUniqueID());
	}
	
	//Private methods
	private static int booleanToInt(boolean b){
		return (b) ? 1 : 0;
	}
}
