package Java;

public enum Group {
	
	OWNER(0, "Dono"), 
	ADMINISTRATOR(1, "Administrador"),
	MODERATOR(2, "Moderador"), 
	HELPER(3, "Ajudante"), 
	VIP_PLUS(4, "VIP+"), 
	VIP(5, "VIP"),
	MEMBER(6, "Membro");

	private final int rank;
	private final String name;
	
	Group (int valor, String namen){
		rank = valor;
		name = namen;
	}
	
	public String getName(){
		return name;
	}
	
	public int getValue(){
		return rank;
	}

}
