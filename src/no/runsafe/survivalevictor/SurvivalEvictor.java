package no.runsafe.survivalevictor;

import no.runsafe.framework.RunsafePlugin;
import no.runsafe.framework.features.Database;
import no.runsafe.framework.features.Events;

public class SurvivalEvictor extends RunsafePlugin
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
		addComponent(PlayerMonitor.class);
	}
}
