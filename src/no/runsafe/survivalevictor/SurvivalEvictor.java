package no.runsafe.survivalevictor;

import no.runsafe.framework.RunsafeConfigurablePlugin;
import no.runsafe.framework.features.Database;
import no.runsafe.framework.features.Events;

public class SurvivalEvictor extends RunsafeConfigurablePlugin
{
	@Override
	protected void pluginSetup()
	{
		// Framework features
		//addComponent(Commands.class);
		addComponent(Database.class);
		addComponent(Events.class);
		//addComponent(FrameworkHooks.class);
		//addComponent(LUAScripts.class);
		//addComponent(UniverseRegistration.class);

		// Plugin components
		addComponent(EvictionRepository.class);
		addComponent(PlayerMonitor.class);
	}
}
