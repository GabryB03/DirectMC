package it.gabrielebologna.directmc.commands;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import it.gabrielebologna.directmc.command.Cmd;
import it.gabrielebologna.directmc.main.DirectMC;
import it.gabrielebologna.directmc.utils.PermissionsUtils;
public class EnderchestCmd extends Cmd{
	public EnderchestCmd(){
		super("enderchest");
		addString("no-player", "&7Only players can execute this command.");
		addString("no-permission", "&7You do not have permission to run this command.");
		addString("no-online", "&7This player is not online.");
		addString("enderchest", "&7You have been opened your enderchest.");
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (sender instanceof Player){
			Player p = (Player) sender;
			if (PermissionsUtils.hasPermission(p, "enderchest") || PermissionsUtils.hasPermission(p, "ec")){
				DirectMC.sendMessage(p, getString("enderchest"));
				p.openInventory(p.getEnderChest());
				return true;
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