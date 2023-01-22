package it.gabrielebologna.directmc.commands;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import it.gabrielebologna.directmc.command.Cmd;
import it.gabrielebologna.directmc.main.DirectMC;
import it.gabrielebologna.directmc.managers.tpa.TPARequest;
import it.gabrielebologna.directmc.managers.tpa.TPAType;
import it.gabrielebologna.directmc.utils.PermissionsUtils;
public class TPAcceptCmd extends Cmd{
	public TPAcceptCmd(){
		super("tpaccept");
		addString("no-player", "&7Only players can execute this command.");
		addString("no-permission", "&7You do not have permission to run this command.");
		addString("no-request", "&7You do not have a teleport request to accept.");
		addString("tpaccept", "&7The teleport request has been accepted.");
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (sender instanceof Player){
			Player p = (Player) sender;
			if (PermissionsUtils.hasPermission(p, "tpaccept")){
				if (DirectMC.getTPAManager().isReceiverPresent(p)){
					TPARequest request = DirectMC.getTPAManager().getTPARequestOfReceiver(p);
					DirectMC.sendMessage(p, getString("tpaccept"));
					DirectMC.sendMessage(request.getSender(), getString("tpaccept"));
					if (request.getType() == TPAType.TO){
						request.getSender().teleport(p.getLocation());
					}else if (request.getType() == TPAType.HERE){
						p.teleport(request.getSender().getLocation());
					}
					DirectMC.getTPAManager().deleteTPARequest(p);
					return true;
				}else{
					DirectMC.sendMessage(p, getString("no-request"));
					return true;
				}
			}else{
				DirectMC.sendMessage(p, getString("no-permission"));
				return true;
			}
		}else{
			sender.sendMessage(getString("no-player"));
			return true;
		}
	}
}