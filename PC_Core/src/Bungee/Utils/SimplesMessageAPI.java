package Bungee.Utils;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class SimplesMessageAPI {
	
	
	private CommandSender p;
	public SimplesMessageAPI(CommandSender p){
		this.p = p;
	}
	
	public BaseComponent[] msg(String msg){
		return new ComponentBuilder(msg).create();
	}
	
	public void send(String msg){
		p.sendMessage(new ComponentBuilder(msg).create());
	}
	
	public void send(String msg, HoverEvent.Action action ,String tooltip){
		if(!(p instanceof ProxiedPlayer))return;
		TextComponent text = new TextComponent(TextComponent.fromLegacyText(msg));
		text.setHoverEvent(new HoverEvent(action, msg(tooltip)));
		p.sendMessage(text);
	}
	
	public void send(String msg, ClickEvent.Action action, String cmd){
		if(!(p instanceof ProxiedPlayer))return;
		TextComponent text = new TextComponent(TextComponent.fromLegacyText(msg));
		text.setClickEvent(new ClickEvent(action, cmd));
		p.sendMessage(text);
	}
	
	public void send(String msg, HoverEvent.Action action1,String tooltip, ClickEvent.Action action, String cmd){
		if(!(p instanceof ProxiedPlayer))return;
		TextComponent text = new TextComponent(TextComponent.fromLegacyText(msg));
		text.setHoverEvent(new HoverEvent(action1, msg(tooltip)));
		text.setClickEvent(new ClickEvent(action, cmd));
		p.sendMessage(text);
	}

}
