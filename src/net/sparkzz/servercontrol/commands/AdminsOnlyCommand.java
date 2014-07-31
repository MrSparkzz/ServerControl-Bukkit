package net.sparkzz.servercontrol.commands;

import net.sparkzz.servercontrol.command.CommandManager;
import net.sparkzz.servercontrol.util.Options;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by Brendon on 7/16/2014.
 */
public class AdminsOnlyCommand extends CommandManager {

	public AdminsOnlyCommand() {
		super("server.adminsonly", "/adminsonly");
	}

	private boolean ao = options.getOption(Options.ADMINS_ONLY);

	public void setAo() {
		if (ao) setAo(false);
		else setAo(true);
	}

	private void setAo(boolean value) {
		ao = value;
		options.setOption(Options.ADMINS_ONLY, value);
	}

	@Override
	public boolean command(CommandSender sender, Command command, String[] args) {
		if (args.length > 0) {
			msg.args(sender, 1);
			return false;
		}

		setAo();

		if (ao) msg.send(sender, msg.warn("Admins only at this time! Players without permission will not be able to join!"));
		else msg.send(sender, color.GREEN + "Anyone can join now!");

		return true;
	}
}