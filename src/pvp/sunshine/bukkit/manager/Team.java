package pvp.sunshine.bukkit.manager;

import java.util.HashSet;
import java.util.Set;

public class Team {
	private String name;
	private Set<String> players;
	private String prefix;
	private String suffix;

	public Team(String name, String prefix, String suffix) {
		this.name = name;
		this.players = new HashSet<>();
		this.prefix = prefix;
		this.suffix = suffix;
	}

	public Team(String name) {
		this(name, "", "");
	}

	public String getName() {
		return this.name;
	}

	public Set<String> getPlayers() {
		return this.players;
	}

	public String getPrefix() {
		return this.prefix;
	}

	public String getSuffix() {
		return this.suffix;
	}

	public void setPrefix(String prefix) {
		if (prefix != null) {
			this.prefix = prefix;
		} else {
			throw new IllegalArgumentException("Prefix cannot be null");
		}
	}

	public void setSuffix(String suffix) {
		if (suffix != null) {
			this.suffix = suffix;
		} else {
			throw new IllegalArgumentException("Suffix cannot be null");
		}
	}

	public void addPlayer(String playerName) {
		this.players.add(playerName);
	}
}
