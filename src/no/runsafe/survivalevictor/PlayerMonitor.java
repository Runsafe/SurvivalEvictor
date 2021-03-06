package no.runsafe.survivalevictor;

import no.runsafe.framework.api.IConfiguration;
import no.runsafe.framework.api.ILocation;
import no.runsafe.framework.api.IWorld;
import no.runsafe.framework.api.event.player.IPlayerMove;
import no.runsafe.framework.api.event.player.IPlayerTeleportEvent;
import no.runsafe.framework.api.event.plugin.IConfigurationChanged;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.event.player.RunsafePlayerTeleportEvent;

import java.util.ArrayList;
import java.util.List;

public class PlayerMonitor implements IPlayerTeleportEvent, IConfigurationChanged, IPlayerMove
{
	public PlayerMonitor(EvictionRepository repository)
	{
		this.repository = repository;
	}

	@Override
	public void OnPlayerTeleport(RunsafePlayerTeleportEvent event)
	{
		// Check if we have a world name defined, otherwise just stop processing.
		if (worlds == null || worlds.isEmpty())
			return;

		IWorld world = event.getTo().getWorld(); // Grab the target world.

		// Make sure the world we are going to is not NULL before starting.
		if (world != null)
		{
			// Check the player is teleporting to the eviction world.
			if (worlds.contains(world.getName()))
			{
				IPlayer player = event.getPlayer(); // Grab the player.
				if (player.hasPermission("runsafe.evict.immune"))
					return;

				int daysLeft = repository.getDaysRemaining(player); // How many days the player has left.

				if (daysLeft == 0)
					illegalPlayers.add(player.getName());
				else
					player.sendColouredMessage("&4ATTENTION! &cThis world is closed, you will only be able to access it for %s more days.", daysLeft);
			}
		}
	}

	@Override
	public boolean OnPlayerMove(IPlayer player, ILocation from, ILocation to)
	{
		if (spawnLocation == null)
			return true;

		String playerName = player.getName();
		if (illegalPlayers.contains(playerName))
		{
			player.teleport(spawnLocation);
			player.sendColouredMessage("&4The world you were in is closed, you have been teleported out!");
			illegalPlayers.remove(playerName);
		}
		return true;
	}

	@Override
	public void OnConfigurationChanged(IConfiguration configuration)
	{
		worlds = configuration.getConfigValueAsList("worlds");
		spawnLocation = configuration.getConfigValueAsLocation("spawnLocation");
	}

	private List<String> worlds;
	private ILocation spawnLocation;
	private final EvictionRepository repository;
	private final List<String> illegalPlayers = new ArrayList<String>(0);
}
