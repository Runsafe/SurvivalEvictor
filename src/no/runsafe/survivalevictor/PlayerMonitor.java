package no.runsafe.survivalevictor;

import no.runsafe.framework.api.IConfiguration;
import no.runsafe.framework.api.ILocation;
import no.runsafe.framework.api.IWorld;
import no.runsafe.framework.api.event.player.IPlayerTeleportEvent;
import no.runsafe.framework.api.event.plugin.IConfigurationChanged;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.event.player.RunsafePlayerTeleportEvent;

public class PlayerMonitor implements IPlayerTeleportEvent, IConfigurationChanged
{
	public PlayerMonitor(EvictionRepository repository)
	{
		this.repository = repository;
	}

	@Override
	public void OnPlayerTeleport(RunsafePlayerTeleportEvent event)
	{
		// Check if we have a world name defined, otherwise just stop processing.
		if (worldName == null)
			return;

		IWorld world = event.getTo().getWorld(); // Grab the target world.

		// Make sure the world we are going to is not NULL before starting.
		if (world != null)
		{
			// Check the player is teleporting to the eviction world.
			if (world.getName().equals(worldName))
			{
				IPlayer player = event.getPlayer(); // Grab the player.
				int daysLeft = repository.getDaysRemaining(player); // How many days the player has left.

				if (daysLeft == 0)
				{
					event.cancel();

					IWorld sourceWorld = event.getFrom().getWorld();
					if (sourceWorld != null && world.isWorld(sourceWorld) && spawnLocation != null)
					{
						player.teleport(spawnLocation);
						player.sendColouredMessage("&cThe logged in while inside a closed world, you've been teleported away!");
					}
					else
					{
						player.sendColouredMessage("&cThe world you are trying to get to is now closed!");
					}
				}
				else
				{
					player.sendColouredMessage("&4ATTENTION! &cThis world is closed, you will only be able to access it for %s more days.", daysLeft);
				}
			}
		}
	}

	@Override
	public void OnConfigurationChanged(IConfiguration configuration)
	{
		worldName = configuration.getConfigValueAsString("world");
		spawnLocation = configuration.getConfigValueAsLocation("spawnLocation");
	}

	private String worldName;
	private ILocation spawnLocation;
	private final EvictionRepository repository;
}
