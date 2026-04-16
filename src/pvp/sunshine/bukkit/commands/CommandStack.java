package pvp.sunshine.bukkit.commands;

public final class CommandStack {
	public static final String PERMISSION_DENIED = "§c§lERRO §fVocê não tem permissão para executar este comando.";
	public static final String PLAYER_IS_OFFLINE = "§c§lERRO §fJogador não encontrado.";

	public static String command(String sintaxe) {
		return "§c§lERRO §fComando inválido, utilize: /" + sintaxe;
	}
}
