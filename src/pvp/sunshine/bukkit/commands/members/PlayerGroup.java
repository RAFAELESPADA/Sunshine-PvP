package pvp.sunshine.bukkit.commands.members;



import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public enum PlayerGroup {


    DONO("Dono", 1, "Dono", "tag.dono", ChatColor.DARK_RED, 1),
    SUBDONO("SubDono", 2, "SubDono", "tag.subdono", ChatColor.DARK_RED, 1),
    ADMIN("Admin", 3, "Admin", "tag.admin", ChatColor.RED, 1),
    COORD("Coord", 4, "Coord", "tag.coord", ChatColor.GOLD, 3),
    MODPLUS("Mod+", 5, "Mod+", "tag.mod+", ChatColor.DARK_PURPLE, 3),
    MOD("Mod", 6, "Mod", "tag.mod", ChatColor.DARK_PURPLE, 4),
    TRIAL("Trial", 7, "Trial", "tag.trial", ChatColor.LIGHT_PURPLE, 4),
    HELPER("Helper", 8, "Helper", "tag.ajudante", ChatColor.DARK_AQUA, 5),
    BUILDER("Builder", 9, "Builder", "tag.builder", ChatColor.BLUE , 5),
    YOUTUBERPLUS("Youtuber+", 10, "Youtuber+", "tag.youtuber+", ChatColor.BLUE, 9),
    INVESTIDOR("Investidor", 11, "Investidor", "tag.investidor", ChatColor.DARK_GREEN , 10),
    STREAMER("Streamer", 12, "Streamer", "tag.streamer", ChatColor.DARK_PURPLE, 9),
    YOUTUBER("Youtuber", 13, "Youtuber", "tag.youtuber", ChatColor.AQUA, 9),
    TIKTOKER("TikToker", 14, "TikToker", "tag.tiktoker", ChatColor.WHITE, 9),
    MINIYT("MiniYT", 15, "MiniYT", "tag.miniyt", ChatColor.RED, 9),
    BETA("Beta", 16, "Beta", "tag.beta", ChatColor.DARK_BLUE , 10),
    SLOWER("Slower", 20, "Slower", "tag.slower", ChatColor.AQUA, 9),
    PALADINO("Paladino", 21, "Paladino", "tag.paladino", ChatColor.DARK_GREEN , 11),
    IMPERADOR("Imperador", 22, "Imperador", "tag.imperador", ChatColor.GOLD , 12),
    LORD("Lord", 23, "Lord", "tag.lord", ChatColor.AQUA , 12),
    CAVALEIRO("Cavaleiro", 24, "Cavaleiro", "tag.cavaleiro", ChatColor.GREEN , 12),
    NITRO("Nitro", 25, "Nitro", "tag.nitro", ChatColor.LIGHT_PURPLE , 13),
    DEFAULT("Default", 26, "Default", "tag.default", ChatColor.GRAY, 14);

    private final String name;
    private final String permission;
    private final ChatColor color;
    private final int priority;
    private static final List<PlayerGroup> ROLES = new ArrayList<>();
    PlayerGroup(final String s, final int n, final String name, final String permission, final ChatColor color, final int priority) {
        this.name = name;
        this.permission = permission;
        this.color = color;
        this.priority = priority;
    }

    public String getName() {
        return this.name;
    }
    

    public String getPermission() {
        return this.permission;
    }

    public ChatColor getColor() {
        return this.color;
    }

    public String getColoredName() {
        return this.getColor() + this.getName();
    }

    public int getPriority() {
        return this.priority;
    }
    public static List<PlayerGroup> getRoles() {
        return ROLES;
    }

    public String getBoldColoredName() {
        return this.getColor() + "§l" + this.getName();
    }

    public static PlayerGroup getByName(final String name) {
        for (PlayerGroup group : PlayerGroup.values()) {
            if (group.name().equalsIgnoreCase(name)) {
                return group;
            }
        }
        return null;
    }

    public static PlayerGroup getGroup(final Player player) {
        for (PlayerGroup group : PlayerGroup.values()) {
            if (player.hasPermission(group.getPermission())) {
                return group;
            }
        }
        return PlayerGroup.DEFAULT;
    }

    public static String getPlayerNameWithGroup(Player player) {
        PlayerGroup group = getGroup(player);
        String prefix = group.getBoldColoredName().toUpperCase();
        return prefix + group.getColor() + " " + player.getName();
    }

}
