package pvp.sunshine.bukkit.commands.members;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import pvp.sunshine.bukkit.BukkitMain;
import pvp.sunshine.bukkit.manager.mysql.connections.SQLClan;
import pvp.sunshine.bukkit.manager.mysql.connections.SQLRank;

public class ClanCMD implements CommandExecutor {

    public static List<String> cooldown = new ArrayList<String>();
    public static Map<Player, String> convite = new HashMap<Player, String>();

    public static String getInvalidChars(String string) {
        return Pattern.compile("[A-Za-z]").matcher(string).replaceAll("");
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        if (args.length == 0) {
            sender.sendMessage(
                    "§6§lSISTEMA DE CLANS\n§e* /clan criar (nome) (tag) §f- §bCria um clan.\n§e* /clan deletar §f- §bDeleta seu clan.\n§e* /clan info (seu clan) §f- §bObter informações do seu clan. \n§e* /clan convidar (jogador) §f-§b Convida um jogador para seu clan.\n§e* /clan expulsar (jogador) §f-§b Expulsa um membro do seu clan.\n§e* /clan aceitar (clan) §f-§b Aceita o convite de um clan.\n§e* /clan sair §f-§b Sai do clan atual.\n§e* /clan promover (jogador) §f-§b Promove um membro de seu clan.\n§e* /clan rebaixar §f- §bRebaixa o cargo de um superior de seu clan.\n§e* /c (mensagem) §f-§b Envia um mensagem no chat do clan.");
            return true;
        }
        if (args[0].equalsIgnoreCase("criar")) {
            if (args.length < 2 || args.length == 1) {
                p.sendMessage("§e§lCLAN §fUtilize §e/clan criar (nome) (tag)!");
                return true;
            }
            if (!p.hasPermission("tag.vip+") && !p.hasPermission("tag.vip") && !p.hasPermission("tag.creator") && SQLRank.getXp(p) < 1000) {
                sender.sendMessage("§c§lCLAN §fVocê precisa ter 1.000 de xp ou adqurir seu vip ou vip+ para criar um clan.");
                return true;
            }
            if (!SQLClan.clan.get(p.getUniqueId()).equalsIgnoreCase("Nenhum")) {
                p.sendMessage("§c§lCLAN §fVocê já está em um clan.");
                return true;
            }
            String tag = args[2].replace("&", "§").replace("§k", "").replace("§l", "").replace("§m", "").replace("§o",
                    "");
            String tag2 = ChatColor.stripColor(tag);
            if (tag2.length() > 3 || tag2.length() < 3) {
                p.sendMessage("§c§lCLAN §fA tag do clan deve ter conter apenas 3 caracteres.");
                return true;
            }
            if (!SQLClan.checkName(args[1])) {
                p.sendMessage(
                        "§c§lCLAN §fNão é possível utilizar esse tipo de caractere para criar um clan e o limite do nome de clan são de 12 letras.");
                return true;
            }
            if (SQLClan.checkClan(args[1])) {
                sender.sendMessage("§c§lCLAN §fJá existe um clan com esse nome, tente outro.");
                return true;
            }
            if (SQLClan.checkTag(args[2])) {
                sender.sendMessage("§c§lCLAN §fJá existe um clan com essa tag, tente outra.");
                return true;
            }
            if (!getInvalidChars(args[2]).isEmpty()) {
                sender.sendMessage(
                        "§c§lCLAN §fA tag na qual você tentou utilizar em seu clan possui caracteres inválidas, tente outra.");
                return true;
            }
            SQLClan.clan.replace(p.getUniqueId(), args[1]);
            SQLClan.cargo.replace(p.getUniqueId(), "Dono");
            if (!SQLClan.tag.containsKey(p.getUniqueId())) {
                SQLClan.tag.put(p.getUniqueId(), args[2]);
            }
            new BukkitRunnable() {
                public void run() {
                    SQLClan.registerClan(args[1], args[2], SQLRank.getXp(p));
                    SQLClan.updatePlayer(p);
                }
            }.runTaskAsynchronously(BukkitMain.getInstance());
            sender.sendMessage("§a§lCLAN §fSeu clan foi criado com sucesso!");
            Bukkit.broadcastMessage("§e§lCLAN §fO jogador(a) §e" + p.getName() + "§f criou o clan §e" + args[1] + "§f!");
            return true;
        }
        if (args[0].equalsIgnoreCase("sair")) {
            if (args.length < 1) {
                p.sendMessage("§e§lCLAN §fUtilize §e/clan sair");
                return true;
            }
            if (SQLClan.cargo.get(p.getUniqueId()).equalsIgnoreCase("Dono")) {
                p.sendMessage("§c§lCLAN §fNão é possível sair do seu clan sendo um dono, apenas pode deletá-lo!");
                return true;
            }
            if (SQLClan.clan.get(p.getUniqueId()).equalsIgnoreCase("Nenhum")) {
                p.sendMessage("§c§lCLAN §fVocê não está em nenhum clan.");
                return true;
            }

            sender.sendMessage("§a§lCLAN §fVocê saiu do clan com sucesso!");
            for (Player jogadores : Bukkit.getOnlinePlayers()) {
            	Player p1 = Bukkit.getPlayer(sender.getName());
                
                if (SQLClan.clan.get(jogadores.getUniqueId()).equalsIgnoreCase(SQLClan.clan.get(p1.getUniqueId()))) {
                    jogadores.sendMessage("§c§lCLAN §fO jogador §c" + sender.getName() + " §fsaiu do clan!");
                }
            }
            SQLClan.clan.put(p.getUniqueId(), "Nenhum");
            SQLClan.cargo.put(p.getUniqueId(), "Nenhum");
            try {
                SQLClan.removeXp(SQLRank.getXp((Player) sender), SQLClan.clan.get(p.getUniqueId()));
                SQLClan.connection.createStatement().executeUpdate("UPDATE `Clan` SET `NICK`='" + p.getName() + "', `ClanName`='Nenhum', `CargoName`='Nenhum' WHERE `NICK`='" + p.getName() + "';");
            } catch (SQLException localSQLException) {
                localSQLException.printStackTrace();
            }
            return true;
        }
        if (args[0].equalsIgnoreCase("deletar")) {
            if (args.length > 1) {
                p.sendMessage("§c§lERRO §fComando incorreto, utilize /clan deletar");
                return true;
            }
            if (!SQLClan.cargo.get(p.getUniqueId()).equalsIgnoreCase("Dono")) {
                p.sendMessage("§c§lCLAN §fApenas o dono pode deletar o clan!");
                return true;
            }
            sender.sendMessage("§a§lCLAN §fSeu clan foi deletado com sucesso. Por favor relogue do servidor para que as alterações sejam feitas sem nenhum problema!");
            
            String clanname = SQLClan.clan.get(p.getUniqueId());
            new BukkitRunnable() {
                public void run() {
                    try {
                        SQLClan.clan.replace(p.getUniqueId(), "Nenhum");
                        SQLClan.cargo.replace(p.getUniqueId(), "Membro");
                        if (SQLClan.tag.containsKey(p.getUniqueId())) {
                            SQLClan.tag.remove(p.getUniqueId());
                        }
                        SQLClan.deleteClan(clanname);
                        PreparedStatement ps = SQLClan.connection.prepareStatement(
                                "SELECT `UUID`, `ClanName`, `CargoName` FROM `Clan` WHERE `ClanName`='" + clanname
                                        + "'");
                        ResultSet rs = ps.executeQuery();

                        while (rs.next()) {
                            SQLClan.removeAllClans(rs.getString("UUID"));
                        }
                    } catch (SQLException localSQLException) {
                        localSQLException.printStackTrace();
                    }
                    for (Player todos : Bukkit.getOnlinePlayers()) {
                        if (SQLClan.clan.containsKey(todos.getUniqueId())) {
                            if (SQLClan.clan.get(todos.getUniqueId()).equalsIgnoreCase(clanname)) {
                                todos.sendMessage("§c§lCLAN §fO dono §c" + p.getName() + " §fdeletou o clan!");
                               
                                SQLClan.clan.replace(todos.getUniqueId(), "Nenhum");
                                SQLClan.cargo.replace(todos.getUniqueId(), "Membro");
                                if (SQLClan.tag.containsKey(todos.getUniqueId())) {
                                    SQLClan.tag.remove(todos.getUniqueId());
                                }
                            }
                        }
                    }
                    SQLClan.updatePlayer(p);
                }
            }.runTaskAsynchronously(BukkitMain.getInstance());
            return true;

        }
        if (args[0].equalsIgnoreCase("convidar")) {
            if (args.length < 2 || args.length < 1) {
                p.sendMessage("§e§lCLAN §fUtilize §e/clan convidar (nick)");
                return true;
            }
            if (SQLClan.cargo.get(p.getUniqueId()).equalsIgnoreCase("Dono")
                    || SQLClan.cargo.get(p.getUniqueId()).equalsIgnoreCase("Admin")
                    | (SQLClan.cargo.get(p.getUniqueId()) == ("Dono"))
                    || (SQLClan.cargo.get(p.getUniqueId()) == ("Admin"))) {

                Player target2 = Bukkit.getPlayer(args[1]);
                if (target2 == null) {
                    sender.sendMessage("§c§lCLAN §fNão é possivel convidar um jogador offline.");
                    return true;
                }
                if (SQLRank.getXp(target2) < 0) {
                    sender.sendMessage("§c§lCLAN §fNão é possivel adicionar um membro ao seu clan com XP negativo.");
                    return true;
                }
                if (!SQLClan.clan.get(target2.getUniqueId()).equalsIgnoreCase("Nenhum")) {
                    sender.sendMessage("§c§lCLAN §fEsse jogador já pertence a outro clan.");
                    return true;
                }
                if (SQLClan.clan.get(p.getUniqueId()).equalsIgnoreCase("Nenhum")) {
                    p.sendMessage("§c§lCLAN §fVocê não está em nenhum clan.");
                    return true;
                }
                if (convite.containsKey(target2)) {
                    sender.sendMessage("§c§lCLAN §fVocê já convidou esse jogador, aguarde para convidá-lo novamente.");
                    return true;
                }
                convite.put(target2, SQLClan.clan.get(p.getUniqueId()));
                target2.sendMessage("§a§lCLAN §fVocê recebeu um convite do clan §a" + convite.get(target2)
                        + " §fuse §a/clan aceitar " + SQLClan.clan.get(p.getUniqueId()) + " §fpara entrar.\n§a§lCLAN §fVocê tem apenas 1 minuto para aceitar, caso contrário, o convite será expirado.");
                sender.sendMessage("§a§lCLAN §fVocê convidou o jogador §a" + target2.getName() + " §fpara seu clan!");
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (convite.containsKey(target2)) {
                            convite.remove(target2);
                            target2.sendMessage("§c§lCLAN §fO convite para o clan expirou pois não houve nenhuma reação.");
                            sender.sendMessage("§c§lCLAN §fSeu convite para o jogador §c" + target2.getName() + "§f expirou pois não houve reação.");
                            target2.playSound(target2.getLocation(), Sound.EXPLODE, 1.0f, 1.0f);
                            p.playSound(p.getLocation(), Sound.EXPLODE, 1.0f, 1.0f);
                        }
                    }
                }.runTaskLater(BukkitMain.getInstance(), 20 * 60);
            } else {
                sender.sendMessage("§c§lCLAN §fVocê não possui o cargo para recrutar membro para este clan.");
                return true;
            }

        }
        if (args[0].equalsIgnoreCase("aceitar")) {
            if (args.length < 2 || args.length < 1) {
                p.sendMessage("§e§lCLAN §fUtilize §e/clan aceitar (clan)");
                return true;
            }
            if (!convite.containsKey(sender)) {
                sender.sendMessage("§c§lCLAN §fVocê não possui nenhum convite.");
                return true;
            }
            if (!args[1].equalsIgnoreCase(convite.get(sender))) {
                sender.sendMessage("§c§lCLAN §fVocê não possui nenhum convite para esse clan.");
                return true;
            }
            if (SQLClan.tag.containsKey(p.getUniqueId())) {
                SQLClan.tag.remove(p.getUniqueId());
            }
            if (!SQLClan.tag.containsKey(p.getUniqueId())) {
                SQLClan.tag.put(p.getUniqueId(), SQLClan.getTagConnection(convite.get(sender)));
            }
            SQLClan.clan.replace(p.getUniqueId(), convite.get(sender));
            SQLClan.cargo.replace(p.getUniqueId(), "Membro");
            for (Player jogadores : Bukkit.getOnlinePlayers()) {
                if (SQLClan.clan.get(jogadores.getUniqueId()).equalsIgnoreCase(convite.get(sender))) {
                    jogadores.sendMessage("§a§lCLAN §fO jogador §a" + sender.getName() + " §fagora faz parte do clan!");
              
                }
            }
            convite.remove(sender);
            Player p1 = Bukkit.getPlayer(sender.getName());
            
            sender.sendMessage(
                    "§a§lCLAN §fVocê entrou no clan §a" + SQLClan.clan.get(p1.getUniqueId()) + " §fcom sucesso!");
            new BukkitRunnable() {
                public void run() {
                    SQLClan.updatePlayer(p);
                }
            }.runTaskAsynchronously(BukkitMain.getInstance());
            return true;
        }
        if (args[0].equalsIgnoreCase("promover")) {
            if (args.length < 1) {
                p.sendMessage("§e§lCLAN §fUtilize §e/clan promover (nick)");
                return true;
            }
            if (SQLClan.clan.get(p.getUniqueId()).equalsIgnoreCase("Nenhum")) {
                p.sendMessage("§c§lCLAN §fVocê não está em nenhum clan.");
                return true;
            }
            if (!SQLClan.cargo.get(p.getUniqueId()).equalsIgnoreCase("Dono")) {
                sender.sendMessage("§c§lCLAN §fVocê não é um dono para promover um membro do clan.");
                return true;
            }
            Player target3 = Bukkit.getPlayer(args[1]);
            if (target3 == null) {
                sender.sendMessage("§c§lCLAN §fNão é possível alterar o cargo de um jogador offline em seu clan.");
                return true;
            }
            if (!SQLClan.clan.get(target3.getUniqueId()).equalsIgnoreCase(SQLClan.clan.get(p.getUniqueId()))) {
                sender.sendMessage("§c§lCLAN §fEsse jogador não pertence ao seu clan.");
                return true;
            }
            if (target3 == p) {
                p.sendMessage("§c§lCLAN §fVocê não pode promover a si mesmo.");
                return true;
            }
            sender.sendMessage("§a§lCLAN §fVocê promoveu o membro §a" + target3.getName()
                    + " §fpara o cargo administrador em seu clan!");
            target3.sendMessage("§a§lCLAN §fVocê foi promovido para o cargo administrador no clan!\n");
            SQLClan.cargo.replace(target3.getUniqueId(), "Admin");
            new BukkitRunnable() {
                public void run() {
                    SQLClan.updatePlayer(target3);
                }
            }.runTaskAsynchronously(BukkitMain.getInstance());
            return true;
        }
        if (args[0].equalsIgnoreCase("rebaixar")) {
            if (args.length < 1 || args.length > 2) {
                p.sendMessage("§e§lCLAN §fUtilize §e/clan rebaixar (nick)");
                return true;
            }
            if (SQLClan.clan.get(p.getUniqueId()).equalsIgnoreCase("Nenhum")) {
                p.sendMessage("§c§lCLAN §fVocê não está em nenhum clan.");
                return true;
            }
            if (!SQLClan.cargo.get(p.getUniqueId()).equalsIgnoreCase("Dono")) {
                sender.sendMessage("§c§lCLAN §fVocê não é um dono para rebaixar um membro do clan.");
                return true;
            }
            Player target3 = Bukkit.getPlayer(args[1]);
            if (target3 == null) {
                sender.sendMessage("§c§lCLAN §fNão é possível alterar o cargo de um jogador offline em seu clan.");
                return true;
            }
            if (target3.getName().equalsIgnoreCase(sender.getName())) {
                sender.sendMessage("§c§lCLAN §fVocê não pode rebaixar a si mesmo.");
                return true;
            }
            if (!SQLClan.clan.get(target3.getUniqueId()).equalsIgnoreCase(SQLClan.clan.get(p.getUniqueId()))) {
                sender.sendMessage("§c§lCLAN §fEsse jogador não pertence ao seu clan.");
                return true;
            }
            if (!SQLClan.cargo.get(target3.getUniqueId()).equalsIgnoreCase("Admin")) {
                sender.sendMessage("§c§lCLAN §fVocê não pode rebaixar um jogador que já possui o cargo minimo.");
                return true;
            }
            sender.sendMessage("§a§lCLAN §fVocê rebaixou o administrador §a" + target3.getName()
                    + " §fpara o cargo administrador em seu clan!");
            target3.sendMessage("§c§lCLAN §fO dono do clan o rebaixou para o cargo membro!");
            SQLClan.cargo.replace(target3.getUniqueId(), "Membro");
            SQLClan.updatePlayer(target3);
            return true;
        }
        if (args[0].equalsIgnoreCase("info")) {
            if (args.length == 1) {
                p.sendMessage("§e§lCLAN §fUtilize §e/clan info (clan)");
                return true;
            }
            if (cooldown.contains(p.getName())) {
                p.sendMessage("§c§lERRO §fAguarde para utilizar este comando novamente.");
                return true;
            }
            cooldown.add(p.getName());
            new BukkitRunnable() {
                public void run() {
                    cooldown.remove(p.getName());
                }
            }.runTaskLater(BukkitMain.getInstance(), 20 * 10);
            new BukkitRunnable() {
                public void run() {
                    if (!SQLClan.getTagConnection(args[1]).equalsIgnoreCase("Nenhuma")) {
                        p.sendMessage("\n§7Informações do clan: §e" + args[1] + " (" + SQLClan.getTagConnection(args[1])
                                + ") " + "\n§7Membros:");
                        p.sendMessage("");
                        int totalx = 0;
                        int membro = 0;
                        int admin = 0;
                        try {
                            PreparedStatement ps = SQLClan.connection.prepareStatement(
                                    "SELECT `NICK`, `ClanName`, `CargoName` FROM `Clan` WHERE `ClanName`='" + args[1]
                                            + "'");
                            ResultSet rs = ps.executeQuery();

                            while (rs.next()) {
                                String tempcachename = rs.getString("NICK");
                                String tempcargo = rs.getString("CargoName");
                                int xp = Integer.valueOf(SQLRank.getXpConnection(tempcachename));
                                if (tempcargo.equalsIgnoreCase("Dono")) {
                                    p.sendMessage("§7" + tempcachename + ": §fXP: §a" + xp + "§7: §fCargo: §4Dono");
                                } else {
                                    if (tempcargo.equalsIgnoreCase("Admin")) {
                                        p.sendMessage("§7" + tempcachename + ": §fXP: §a" + xp
                                                + "§7: §fCargo: §cAdministrador");
                                        admin = admin + 1;
                                    } else {
                                        p.sendMessage(
                                                "§7" + tempcachename + ": §fXP: §a" + xp + "§7: §fCargo: §7Membro");
                                        membro = membro + 1;
                                    }
                                }
                                totalx = totalx + xp;
                            }
                        } catch (SQLException localSQLException) {
                            localSQLException.printStackTrace();

                        }
                        p.sendMessage("");
                        p.sendMessage("§fAdministradores (§7" + admin + "§f)");
                        p.sendMessage("§fMembros (§7" + membro + "§f)");
                        p.sendMessage("");
                        p.sendMessage("§fXP total: §e" + totalx);
                    } else if (!SQLClan.getClanTagConnection(args[1]).equalsIgnoreCase("Nenhuma")) {
                        p.sendMessage("\n§7Informações do clan: §e" + SQLClan.getClanTagConnection(args[1]) + " ("
                                + args[1] + ") " + "\n§7Membros:");
                        p.sendMessage("");
                        int totalx = 0;
                        int membro = 0;
                        int admin = 0;
                        try {
                            PreparedStatement ps = SQLClan.connection.prepareStatement(
                                    "SELECT `NICK`, `ClanName`, `CargoName` FROM `Clan` WHERE `ClanName`='"
                                            + SQLClan.getClanTagConnection(args[1]) + "'");
                            ResultSet rs = ps.executeQuery();

                            while (rs.next()) {
                                String tempcachename = rs.getString("NICK");
                                String tempcargo = rs.getString("CargoName");
                                int xp = Integer.valueOf(SQLRank.getXpConnection(tempcachename));
                                if (tempcargo.equalsIgnoreCase("Dono")) {
                                    p.sendMessage("§7" + tempcachename + ": §fXP: §a" + xp + "§7: §fCargo: §4Dono");
                                } else {
                                    if (tempcargo.equalsIgnoreCase("Admin")) {
                                        p.sendMessage("§7" + tempcachename + ": §fXP: §a" + xp
                                                + "§7: §fCargo: §cAdministrador");
                                        admin = admin + 1;
                                    } else {
                                        p.sendMessage(
                                                "§7" + tempcachename + ": §fXP: §a" + xp + "§7: §fCargo: §7Membro");
                                        membro = membro + 1;
                                    }
                                }
                                totalx = totalx + xp;
                            }
                        } catch (SQLException localSQLException) {
                            localSQLException.printStackTrace();

                        }
                        p.sendMessage("");
                        p.sendMessage("§fAdministradores (§7" + admin + "§f)");
                        p.sendMessage("§fMembros (§7" + membro + "§f)");
                        p.sendMessage("");
                        p.sendMessage("§fXP total: §e" + totalx);
                    } else {
                        sender.sendMessage("§c§lERRO §fEsse clan não existe.");
                    }

                }
            }.runTaskAsynchronously(BukkitMain.getInstance());

            return true;

        }

        if (args[0].equalsIgnoreCase("expulsar")) {
            if (args.length < 1) {
                p.sendMessage("§e§lCLAN §fUtilize §e/clan expulsar (nick)");
                return true;
            }
            if (SQLClan.clan.get(p.getUniqueId()).equalsIgnoreCase("Nenhum")) {
                p.sendMessage("§c§lCLAN §fVocê não está em nenhum clan.");
                return true;
            }
            if (!SQLClan.cargo.get(p.getUniqueId()).equalsIgnoreCase("Dono")) {
                sender.sendMessage("§c§lCLAN §fVocê não é um dono para expulsar um membro do clan.");
                return true;
            }
            Player target3 = Bukkit.getPlayer(args[1]);
            if (target3 == null) {
                sender.sendMessage("§c§lCLAN §fNão é possível expulsar um jogador offline em seu clan.");
                return true;
            }
            if (target3 == sender) {
                sender.sendMessage("§c§lCLAN §fVocê não pode expulsar a si mesmo.");
                return true;
            }
            if (!SQLClan.clan.get(target3.getUniqueId()).equalsIgnoreCase(SQLClan.clan.get(p.getUniqueId()))) {
                sender.sendMessage("§c§lCLAN §fEsse jogador não pertence ao seu clan.");
                return true;
            }
            sender.sendMessage("§a§lCLAN §fVocê expulsou o jogador §a" + target3.getName() + " §f§f de seu clan.");
            target3.sendMessage("§c§lCLAN §fVocê foi expulso do clan.");
            SQLClan.clan.put(target3.getUniqueId(), "Nenhum");
            SQLClan.cargo.put(target3.getUniqueId(), "Nenhum");

            for (Player jogadores : Bukkit.getOnlinePlayers()) {
            	Player p1 = Bukkit.getPlayer(sender.getName());
                
                if (SQLClan.clan.get(jogadores.getUniqueId()).equalsIgnoreCase(SQLClan.clan.get(p1.getUniqueId()))) {
                    jogadores.sendMessage("§c§lCLAN §fO jogador §c" + target3.getName() + " §ffoi expulso do clan.");
                }
            }
            try {
                SQLClan.removeXp(SQLRank.getXp(target3), SQLClan.clan.get(target3.getUniqueId()));
                SQLClan.connection.createStatement().executeUpdate("UPDATE `Clan` SET `NICK`='" + target3 + "', `ClanName`='Nenhum', `CargoName`='Nenhum' WHERE `NICK`='" + target3 + "';");
            } catch (SQLException localSQLException) {
                localSQLException.printStackTrace();
            }
        }
        return false;
    }
}
