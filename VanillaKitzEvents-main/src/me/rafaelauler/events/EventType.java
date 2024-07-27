package me.rafaelauler.events;





import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;

import lombok.Getter;

@Getter
public enum EventType {


    ARENAPVP("ArenaPvP", Arrays.asList("Ol� a todos! Bem vindo ao &c&lArena PvP&l&f!", "No evento, voc�s receberam um kit com items", "Com o tempo com jogadores diminuindo voc�s ser�o teleportados a um novo local.", "Todos tem 20 segundos para se preparar.", "Agora vamos as &c&lREGRAS DO EVENTO&l&f:", "&fN�o tente escapar da arena pvp.", "&fN�o fa�a mais times que o promotor deixar." , "Come�ando evento! Boa sorte"), WaveWarp.ARENAPVP.getLocation()),
    LAVA("LavaChallenge", Arrays.asList("Ol� a todos! Bem vindo ao evento &4&lLava Challenge", "&fAo inicio do evento todos receberam um kit com sopas.", "Todos precisar�o tankar a lava que voc�s ser�o colocados!", "Suas sopas N�O ser�o reabastecidas durante o evento, ent�o use elas com cautela.", "Voc�s ter�o 10 segundos para se prepararem!", "Come�ando evento! Boa sorte!"), WaveWarp.LAVACHALLENGE.getLocation()),
    SUMO("Sumo", Arrays.asList("Bem vindo ao evento Sum�!", "No in�cio do evento voc�s ser�o puxados de 2 em 2 para uma luta individual", "Quem ganhar voltar� ao evento!", "E quem perder voltar� ao spawn!", "Come�ando evento. Divirta-se"), WaveWarp.Sumo.getLocation()),
    ARENANINJA("ArenaNinja", Arrays.asList("Ol� a todos! Bem vindo ao &c&lArena NINJA&l&f!", "No evento, voc�s receberam um kit com items", "Com o tempo com jogadores diminuindo voc�s ser�o teleportados a um novo local.", "Todos tem 20 segundos para se preparar.", "Todos tem a habilidade do kit NINJA durante o evento", "Agora vamos as &c&lREGRAS DO EVENTO&l&f:", "&fN�o tente escapar da arena pvp.", "&fN�o fa�a mais times que o promotor deixar." , "Come�ando evento! Boa sorte"), WaveWarp.ARENANINJA.getLocation()),
    _1v1("1v1", Arrays.asList("Bem vindo ao evento 1vs1!", "No in�cio do evento voc�s ser�o puxados de 2 em 2 para uma luta individual", "Quem ganhar voltar� ao evento!", "E quem perder voltar� ao spawn!", "Come�ando evento. Divirta-se"), WaveWarp.ONE_VS_ONE.getLocation()),
    ;
    private final String name;
    private final List<String> explicacao;
    private final Location location;
    EventType(String name, List<String> explicacao, Location location) {
        this.name = name;
        this.explicacao = explicacao;
        this.location = location;
    }

    public static EventType getEventoByName(String name) {
        for (EventType evento : EventType.values()) {
        	 if (evento.getName().equalsIgnoreCase(name))
                 return evento;
         }
        return null;
    }
    public String getName() {
		return name;
	}
    public List<String> getExplicacao() {
		return explicacao;
	}
    public Location getLocation() {
		return location;
	}

    public static void explicarEvento(EventType evento) {
        List<String> explic = evento.getExplicacao();
        int actualsec = 1;
        for (String message : explic) {
            Bukkit.getScheduler().runTaskLater(Main.instance, () -> {
            	EventoUtils.getEventoPlayers().forEach(p -> {
               p.sendMessage("�6�lKOMBO" + " �f" + ChatColor.translateAlternateColorCodes('&', message));
               p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
        });
            	
    }, actualsec * 20);
            actualsec += 5;
        }
}
}


// mdr 801.5 100 519.5
// lava 641.5 118 518.5
// pvp 732.5 80 521.5
// 1v1 868.5 95 457.5
