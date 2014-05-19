package net.sparkzz.servercontrol.command;

import net.sparkzz.util.Colorizer;
import net.sparkzz.util.MsgHandler;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class AdminsOnlyCommand implements CommandExecutor {

	private boolean ao = false;

	public boolean isAO() {
		return ao;
	}
	
	public void setAo() {
		if (ao) ao = false;
		else ao = true;
	}
	
	MsgHandler msg = MsgHandler.getInstance();
	Colorizer color = Colorizer.getInstance();
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!sender.hasPermission("server.adminsonly")) {
			msg.deny(sender);
			return true;
		}
		
		if (args.length > 0) {
			msg.args(sender, 2);
			return true;
		}
		
		setAo();
		
		if (ao) msg.send(sender, msg.warn("Admins Only at this time! Players without op status/permission will not be able to join"));
		else msg.send(sender, color.GREEN + "Anyone can join now!");
		
		return false;
	}
}