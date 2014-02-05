package no.runsafe.survivalevictor;

import no.runsafe.framework.api.database.IDatabase;
import no.runsafe.framework.api.database.ISchemaUpdate;
import no.runsafe.framework.api.database.Repository;
import no.runsafe.framework.api.database.SchemaUpdate;
import no.runsafe.framework.api.player.IPlayer;

public class EvictionRepository extends Repository
{
	public EvictionRepository(IDatabase database)
	{
		this.database = database;
	}

	@Override
	public String getTableName()
	{
		return "survival_evict";
	}

	@Override
	public ISchemaUpdate getSchemaUpdateQueries()
	{
		ISchemaUpdate update = new SchemaUpdate();

		update.addQueries(
			"CREATE TABLE `survival_evict` (" +
				"`player` VARCHAR(20) NOT NULL," +
				"`start_date` DATE NOT NULL," +
				"PRIMARY KEY (`player`)" +
			")"
		);

		return update;
	}

	public int getDaysRemaining(IPlayer player)
	{
		String playerName = player.getName(); // Get the name of the player.
		database.execute("INSERT IGNORE INTO `survival_evict` (player, start_date) VALUES(?, NOW())", playerName);
		int daysLeft = 7 - database.queryInteger("SELECT DATEDIFF(NOW(), start_date) FROM survival_evict WHERE player = ?", playerName);

		return daysLeft < 0 ? 0 : daysLeft;
	}
}
