package no.runsafe.survivalevictor;

import no.runsafe.framework.api.IWorld;
import no.runsafe.framework.api.event.player.IPlayerChangedWorldEvent;
import no.runsafe.framework.api.log.IConsole;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.event.player.RunsafePlayerChangedWorldEvent;

public class PlayerMonitor implements IPlayerChangedWorldEvent
{
	public PlayerMonitor(IConsole console)
	{
		this.console = console;
	}

	@Override
	public void OnPlayerChangedWorld(RunsafePlayerChangedWorldEvent event)
	{
		IPlayer player = event.getPlayer();
		IWorld world = player.getWorld();

		if (world == null)
		{
			console.logInformation("Player %s is going to a NULL world!", player.getName());
			return;
		}

		console.logInformation("Player %s is going to world: %s", player.getName(), world.getName());

		IWorld sourceWorld = event.getSourceWorld();
		if (sourceWorld == null)
		{
			console.logInformation("Player %s is coming from a NULL world", player.getName());
			return;
		}

		console.logInformation("Player %s is coming from world: %s", player.getName(), world.getName());
	}

	private final IConsole console;
}
