package no.runsafe.survivalevictor;

import no.runsafe.framework.api.IWorld;
import no.runsafe.framework.api.event.player.IPlayerTeleportEvent;
import no.runsafe.framework.api.log.IConsole;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.event.player.RunsafePlayerTeleportEvent;

public class PlayerMonitor implements IPlayerTeleportEvent
{
	public PlayerMonitor(IConsole console)
	{
		this.console = console;
	}

	@Override
	public void OnPlayerTeleport(RunsafePlayerTeleportEvent event)
	{
		IPlayer player = event.getPlayer();
		IWorld world = event.getTo().getWorld();

		if (world == null)
		{
			console.logInformation("Player %s is going to a NULL world!", player.getName());
			return;
		}

		console.logInformation("Player %s is going to world: %s", player.getName(), world.getName());

		IWorld sourceWorld = event.getFrom().getWorld();
		if (sourceWorld == null)
		{
			console.logInformation("Player %s is coming from a NULL world", player.getName());
			return;
		}

		console.logInformation("Player %s is coming from world: %s", player.getName(), world.getName());
	}

	private final IConsole console;
}
