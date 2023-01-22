package it.gabrielebologna.directmc.commands;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import it.gabrielebologna.directmc.command.Cmd;
import it.gabrielebologna.directmc.main.DirectMC;
import it.gabrielebologna.directmc.utils.PermissionsUtils;
import it.gabrielebologna.directmc.utils.ProjectileUtils;
public class EggCmd extends Cmd{
	public EggCmd(){
		super("egg");
		addString("no-player", "&7Only players can execute this command.");
		addString("no-permission", "&7You do not have permission to run this command.");
		addString("egg", "&7You have been launched an egg.");
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (sender instanceof Player){
			Player p = (Player) sender;
			if (PermissionsUtils.hasPermission(p, "egg")){
				ProjectileUtils.LaunchEgg(p);
				DirectMC.sendMessage(p, getString("egg"));
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