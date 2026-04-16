package pvp.sunshine.bukkit.manager.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.bukkit.Bukkit;
import pvp.sunshine.bukkit.BukkitMain;

public class Storage {
	
	public static Connection connection;

	public static void start() {
		if (checkConnection()) {
			return;
		}

		String user = BukkitMain.getInstance().getConfig().getString("MySQL.user");
		String host = BukkitMain.getInstance().getConfig().getString("MySQL.host");
		String database = BukkitMain.getInstance().getConfig().getString("MySQL.database");
		String password = BukkitMain.getInstance().getConfig().getString("MySQL.password");
		Integer port = Integer.valueOf(BukkitMain.getInstance().getConfig().getInt("MySQL.port"));
		String URL = "jdbc:mysql://" + host + ":" + port + "/" + database;

		try {
			long startTime = System.currentTimeMillis();
			Bukkit.getConsoleSender().sendMessage("§e§lMYSQL §fEstabelecendo conexão...");
			connection = DriverManager.getConnection(URL, user, password);
			long elapsedTime = System.currentTimeMillis() - startTime; // Calcula o tempo decorrido
			Bukkit.getConsoleSender().sendMessage("§a§lMYSQL §fO banco de dados §aStorage§f foi aberto!");
			Bukkit.getConsoleSender().sendMessage("§a§lMYSQL §fTempo de abertura: §f(§a" + elapsedTime + " ms§f)");
		} catch (SQLException e) {
			handleSQLException(e);
		}
	}

	public static Connection getConnection() {
		if (checkConnection()) {
			return connection;
		}
		return null;
	}

	public static PreparedStatement getStatement(String mysql) {
		if (checkConnection()) {
			try {
				return connection.prepareStatement(mysql);
			} catch (SQLException e) {
				handleSQLException(e);
			}
		}
		return null;
	}

	public static ResultSet getResult(String mysql) {
		if (checkConnection()) {
			try {
				PreparedStatement statement = getStatement(mysql);
				return statement.executeQuery();
			} catch (SQLException e) {
				handleSQLException(e);
			}
		}
		return null;
	}

	public static void disconnect() {
		if (checkConnection()) {
			try {
				connection.close();
				Bukkit.getConsoleSender().sendMessage("§a§lMYSQL §fA conexão com §eStorage§f foi encerrada.");
			} catch (SQLException e) {
				handleSQLException(e);
			}
		}
	}

	public static void handleSQLException(SQLException e) {
		Bukkit.getConsoleSender().sendMessage("§c§lMYSQL §fErro SQL: " + e.getMessage());
		e.printStackTrace();
	}

	public static boolean checkConnection() {
		try {
			return (connection != null && !connection.isClosed());
		} catch (SQLException e) {
			handleSQLException(e);
			return false;
		}
	}
}
