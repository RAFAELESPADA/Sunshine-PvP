package me.rafaelauler.events;


import org.bukkit.entity.Player;


public class Arena extends WarpHandle {
	
	@Override
	public void execute(Player player) {
		super.execute(player);
		player.getInventory().clear();
		EventoComando e =  new EventoComando();
		e.Gladiator(player);

        player.sendMessage("�aVoc� agora est� no evento ArenaPvP!");
        player.sendMessage("�aSiga as instru��es que ser�o dadas no chat!");
	}
}