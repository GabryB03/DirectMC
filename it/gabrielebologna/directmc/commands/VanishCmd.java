package it.gabrielebologna.directmc.commands;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import it.gabrielebologna.directmc.command.Cmd;
import it.gabrielebologna.directmc.main.DirectMC;
import it.gabrielebologna.directmc.utils.PermissionsUtils;
public class VanishCmd extends Cmd{
	public VanishCmd(){
		super("vanish");
		addString("no-player", "&7Only players can execute this command.");
		addString("no-permission", "&7You do not have permission to run this command.");
		addString("no-online", "&7This player is not online.");
		addString("enabled", "&7Your &avanish &7has been enabled.");
		addString("disabled", "&7Your &avanish &7has been disabled.");
		addString("enabled-to", "&7The &avanish &7of the player &c{PLAYER} &7has been enabled.");
		addString("disabled-to", "&7The &avanish &7of the player &c{PLAYER} &7has been disabled.");
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (sender instanceof Player){
			Player p = (Player) sender;
			if (PermissionsUtils.hasPermission(p, "vanish") || PermissionsUtils.hasPermission(p, "v")){
				try{
					Player player = Bukkit.getPlayerExact(args[0]);
					if (player == null){
						DirectMC.sendMessage(p, getString("no-online"));
						return true;
					}else{
						if (!DirectMC.hasVanish(player)){
							DirectMC.getVanishes().add(player);
							for (Player tPlayer: Bukkit.getOnlinePlayers()){
								player.hidePlayer(tPlayer);
							}
							DirectMC.sendMessage(player, getString("enabled"));
							DirectMC.sendMessage(p, getString("enabled-to").replace("{PLAYER}", player.getName()));
							return true;
						}else{
							DirectMC.getVanishes().remove(player);
							for (Player tPlayer: Bukkit.getOnlinePlayers()){
								player.showPlayer(tPlayer);
							}
							DirectMC.sendMessage(player, getString("disabled"));
							DirectMC.sendMessage(p, getString("disabled-to").replace("{PLAYER}", player.getName()));
							return true;
						}
					}
				}catch(Exception e){
					if (!DirectMC.hasVanish(p)){
						DirectMC.getVanishes().add(p);
						for (Player tPlayer: Bukkit.getOnlinePlayers()){
							p.hidePlayer(tPlayer);
						}
						DirectMC.sendMessage(p, getString("enabled"));
						return true;
					}else{
						DirectMC.getVanishes().remove(p);
						for (Player tPlayer: Bukkit.getOnlinePlayers()){
							p.showPlayer(tPlayer);
						}
						DirectMC.sendMessage(p, getString("disabled"));
						return true;
					}
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