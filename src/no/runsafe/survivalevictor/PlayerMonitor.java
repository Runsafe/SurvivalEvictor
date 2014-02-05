package no.runsafe.survivalevictor;

import no.runsafe.framework.api.ILocation;
import no.runsafe.framework.api.IWorld;
import no.runsafe.framework.api.event.player.IPlayerTeleport;
import no.runsafe.framework.api.log.IConsole;
import no.runsafe.framework.api.player.IPlayer;

public class PlayerMonitor implements IPlayerTeleport
{
	public PlayerMonitor(IConsole console)
	{
		this.console = console;
	}

	@Override
	public boolean OnPlayerTeleport(IPlayer player, ILocation from, ILocation to)
	{
		IWorld world = to.getWorld();

		if (world == null)
		{
			console.logInformation("Player %s is going to a NULL world!", player.getName());
			return true;
		}

		console.logInformation("Player %s is going to world: %s", player.getName(), world.getName());

		IWorld sourceWorld = from.getWorld();
		if (sourceWorld == null)
		{
			console.logInformation("Player %s is coming from a NULL world", player.getName());
			return true;
		}

		console.logInformation("Player %s is coming from world: %s", player.getName(), world.getName());

		return true;
	}

	private final IConsole console;
}
