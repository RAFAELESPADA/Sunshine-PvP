package pvp.sunshine.bukkit.manager.npc;

import net.jitse.npclib.NPCLib;
import net.jitse.npclib.api.NPC;
import net.jitse.npclib.api.events.NPCInteractEvent;
import net.jitse.npclib.api.skin.Skin;
import net.jitse.npclib.api.state.NPCSlot;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import pvp.sunshine.bukkit.manager.inventory.BoxShopType;
import pvp.sunshine.bukkit.manager.inventory.ShopAbilityType;
import pvp.sunshine.bukkit.manager.inventory.WarpType;

import java.util.Arrays;

public class NPCRegister implements Listener {

    private JavaPlugin plugin;
    private NPCLib npcLib;
    private Location location;

    public NPC getShopNPC() {
        return shopNPC;
    }

    private NPC shopNPC;

    public NPC getAssistenteNPC() {
        return assistenteNPC;
    }

    private NPC assistenteNPC;

    public NPC getComboNPC() {
        return comboNPC;
    }

    private NPC comboNPC;

    public NPC getGappleNPC() {
        return GappleNPC;
    }

    private NPC GappleNPC;

    public NPC getSumoNPC() {
        return sumoNPC;
    }

    private NPC sumoNPC;
    private NPC potionNPC;

    private NPC caixasNPC;


    public NPCRegister(JavaPlugin plugin) {
        this.plugin = plugin;
        npcLib = new NPCLib(plugin);
        Bukkit.getPluginManager().registerEvents(this, plugin);
        World lobbyWorld = Bukkit.getWorld("lobbypvp2");
        lobbyWorld.loadChunk(-1, 1);
        lobbyWorld.loadChunk(0, 1);
        lobbyWorld.loadChunk(1, 0);
        lobbyWorld.loadChunk(1, -1);

        location = new Location(Bukkit.getWorld("lobbypvp2"), -4.335, 65.00000000, -10.458, -86.0F, 0.9F);
        shopNPC = npcLib.createNPC(Arrays.asList("§d§lLOJA DE KITS",
                "§eKits e Combate", "§7Clique para visualizar!"));
        shopNPC.setSkin(new Skin(
                "ewogICJ0aW1lc3RhbXAiIDogMTY0NTI3NDU2NTU2NCwKICAicHJvZmlsZUlkIiA6ICI0NWY3YTJlNjE3ODE0YjJjODAwODM5MmRmN2IzNWY0ZiIsCiAgInByb2ZpbGVOYW1lIiA6ICJfSnVzdERvSXQiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjc5MTA0OTdlZTc3NTNkYWY0MGE3NWExM2YzYTc3NWRmMmUxOWM4ZDRhYzM0YjZkMmQ2ZmFmNTdiYzIwY2IzZSIKICAgIH0KICB9Cn0=",
                "KRmpZsyzvEIHgTgMkRyljoEH6IhUQ1n/qkJlS71sTzIDu1bI7G8ZnSkjYrnRh3CIz5Q4zdn6gK5xS4q5fdsOyM9wkvgbsHqs/dQ00YnfXy20GXkXxHe/d+u4GYgjVy8oKlIEFXUcUTZbTiS+0yokh4d9xAP+kg9C8eXvoqnMD5YdCPFcu/fgC/rT0IzTBGgMHKMSGrLoZv2+xFl62J5zZdXKb6w79/Gw00AmW7G3gJwMRybfkzanbk4+eq4BwZIr+/NP1YUJVqJMk7GC1jMSiFQjUUNpfAISZm2JBpVluSfPVo9UCeGxtGVCMEEKsGydMgFutJFSkvGTVB+DAErhLYYvMIK1i0iCtoRtxOYqR7b99HiFl1kS0ws+kC5vB9hhX9fX/A4fNSjYb+Yb02kWPf7rEibIhzi5JNPYYn98AkhF1fgcPaDnaOBDgjTul87FKdyMrwnVtePFfUM3aXcbBRKiTt20bLbYjFituRvuWJ1wL0f0ZcMqFY+XVY8m59JhPwZ4dlUpSrPBgPzxeVzIDKuuvh3XLxjyFsE4mleQ7pmVVP3JB9YXV77PXZCagQdz8l1xPF7ncHjSSNSV7SMZWx8ymJ2+CRjUzeprG7mjn6ogrSgTURDkknO1CpsdTYJYZD47PVaKNwk9vj+mZT/NzBOfccDG5pKKqjAu/6CGp3U="));
        shopNPC.setItem(NPCSlot.MAINHAND, new ItemStack(Material.GOLD_SWORD));
        shopNPC.setLocation(location);
        shopNPC.create();

        location = new Location(Bukkit.getWorld("lobbypvp2"), 18.773, 68.0000000, 4.700, -163.0F, 3.4F);
        assistenteNPC = npcLib.createNPC(Arrays.asList("§d§lASSISTENTE", "§eLuna"));
        assistenteNPC.setSkin(new Skin(
                "ewogICJ0aW1lc3RhbXAiIDogMTcwMTUzMDIyNTEwMiwKICAicHJvZmlsZUlkIiA6ICJlN2E3MzZhMjFlM2I0YzA2YmVhOGVmMjVmODg0MmJhZiIsCiAgInByb2ZpbGVOYW1lIiA6ICJKZWVwMDIwNiIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS81ZTJkMzVjZTM5YTNhNWNhM2U4ZTQ3MWU5YjliNWY2YTRmYmU3YjEwYzBlZjA2YWJlN2I0YTRmMTc2NDUzYzUyIiwKICAgICAgIm1ldGFkYXRhIiA6IHsKICAgICAgICAibW9kZWwiIDogInNsaW0iCiAgICAgIH0KICAgIH0KICB9Cn0=",
                "gsR2pUbQObDJi3+pT2Rsn8Eu75eQc/VjXbdWOfF/kD6jmG3ZfM0umWbQOjglP7agA1BvGCVlH/qrrFSIVPethr0FY3xSuCqk3V6txKcf12aUx6jNYNqkLaKVFKg0UYOtuienqe7BDlpk5kCYIZTZR8c5NOvVIeiLz6y21WYML6vzxRYQh94vMSeceNZHlIRxureNCI7p+7R07Af4dakmycWC+nwHatRpYGc3NwoR39wgkslSZ5gwaFmoiiYopQEmZvAqRbZHP/KZZg+LQAQl5N0JD1pt2ukncXKn/8WdOhWaEgYJy4asFCMBzx8UdXeaTBICrDT/+ncUXuccF2pj7AL5nmh4Q9CdmHAF/HUrkNyqqaGk2Qu+3RdY77y5h2szD5rv3fZzcMLw4dDHj0eqD7Dpp7zd4cfC+bOTHDeqjqmpBjzMRDNt8+n+NP2P2jxr3RPBlTV9YBkc7XC+dlzyt47GkjYoEXeLiafJg/8Qc6j4y7qeNuQSZGkFYOJqn40lx5WwmSlJ7FMrFoK8pksoq5jg9qz4LKlugIKnclnaALbI96HkmBUKM6857jnTrZeR1UZOZk3VZK010M/dydly5zJPROXD2x5CL9tLCNR3HkBOvuM7DKYFHYZn3eZhCwXsbuEqa4pUXXFGQg+Fguqof9qOiuyC5xZVUzI8bWh1t3o="));
        assistenteNPC.setItem(NPCSlot.MAINHAND, new ItemStack(Material.DIAMOND));
        assistenteNPC.setLocation(location);
        assistenteNPC.create();

        location = new Location(Bukkit.getWorld("lobbypvp2"), 20.887, 68.00000000000, -4.700, -5.1F, 5.5F);
        caixasNPC = npcLib.createNPC(Arrays.asList("§6§lMESTRE DAS CAIXAS", "§eManoel Gomes"));
        caixasNPC.setSkin(new Skin(
                "ewogICJ0aW1lc3RhbXAiIDogMTY4MTg1OTk1Mzk0NywKICAicHJvZmlsZUlkIiA6ICJmZDIwMGYwMDE4OTI0NzgxODI5OWIzZjE5Yzc4Y2E3MSIsCiAgInByb2ZpbGVOYW1lIiA6ICJ0dXNnIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2FjM2MzYmRlNTE3YjNmZjJjMDFjOGQ3MGFlNTk2YWFmZGQwMDgwZjhjNjE4ZjJkZDBkZTZjM2RlZTcwMTcxNmIiCiAgICB9CiAgfQp9",
                "saFIixLjlS5Uso5LQqELOxXN20eIlC8WC4T/0yj4uDogiSuNucpTwHs1u3jY9YZhlBTAIpLqbKMaQhein3/8aZFB5Uy4o+GSijndZ8xXYIW/OoH2QBx7JeXRLHKzRQhkxCWxvfpYtUl93ZbenmO45RQGQAQQxI0G+Y9aQfVft57i5cn4YDuH1y0UyfgyMG/bMe0NzVzQRRjT39YBC5OaQvqPZldINja6W3TPsTpSYUMHzNmFxVdXnvPoN/7BgCfvQXxnTaq3aFCEqJI75AXB3CqMJ8J+Ndiv36jSiG6QBv9C78AbuYqa012Nykuqd7A/mlJw0+Pe7PPUma/IeVLfcuJ1OkWeOdgVPIu3N4hxOXe73l6zxGdHtLPyDbGkj39DWu4KbjCIpvz1b+6BUtwf/xQKOgZSzBFqhQ23AR0b5yYuFntohgX/mEY4QLFe4ci1paVYgJY0W4phZ+E6n7Ka8crJRvNXoh8PJDAovQrK3xlM0Ae9XBkCtLLtt8AlLWLexb6eKvTa0otLRQ3V3QVCvIWoBRttTNb4nqBD2m86Fppb4BFMrG3SeOQPpXYsvSTnhDr6qpHRhmoGSOTTdXVaIDwzNVGuZzykgTLVbe0a6CjU5lennEuhFhn9klaoE4di1N7kfAASH+SoiPWQ6LKrKXCxNww1W2SmLwj8PKWRIG4="));
        caixasNPC.setItem(NPCSlot.MAINHAND, new ItemStack(Material.ENDER_CHEST));
        caixasNPC.setLocation(location);
        caixasNPC.create();

        location = new Location(Bukkit.getWorld("lobbypvp2"), -9.567, 65.000000000, 8.407, -106.9F, -0.0F);
        comboNPC = npcLib.createNPC(Arrays.asList("§6§lNOVO!", "§bWarp ComboFly", "§e" + WarpType.ComboFly + " jogadores"));
        comboNPC.setSkin(new Skin(
                "ewogICJ0aW1lc3RhbXAiIDogMTU5NDk1ODk4NzA3NSwKICAicHJvZmlsZUlkIiA6ICI3MzgyZGRmYmU0ODU0NTVjODI1ZjkwMGY4OGZkMzJmOCIsCiAgInByb2ZpbGVOYW1lIiA6ICJ4cWwiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmJkZTVmY2Q0Y2FkMzEzZjI1MjZmYzY5ZDVhMDI2NzJmMTJmNzk3NGY4NmYzMTZhYjg4NDQxOTNhMmU5OTFiNiIKICAgIH0KICB9Cn0=",
                "Qb43Zd5/xr/BBfEPt1gb5PTd1oZEHm73AlGxcA06YZ485OvhPd65jORK3UAfrEHIgZzYXtLWHHM/lzQg0/6guyF9wdX/LB5XvOV2Dkxv9fA07ngvEQct1TUQEjmO8UoE8r2vuXXWeJSipbmfDw8p5GDZwbgvpYDvdK2jf4DOk01GiiOILv8fmTOP60RqyPnZp0oN9TjFfuoRErHpLmntPo/3mClcnh0wW/+r8wYsLAPr4EbsbreV5cHUJLBQyUKrG7gCyM7TqDgt3LS7hMLJLFr0SF1XpkPudwrV2qQfmmAloZt8juu34L4SlMh2AOcwHSDhjEfcOiM4DmMEiO7PmVywn7Axm+GumSYSzKl2fpQTU8n8IiUc5SYICiXrLmUnNT6hDEF1coEEfJcnrb26HoU1d7akws4zEMzN0gfQQJXxGqJ/RwzpWp1AgeOzLBOQ4LpJ4szKyOUkQDn8nRp/OgJo998fVN/CsAfxd9V+Mt8CeOWxNXng2sanw67hotp91YqjjCilFBknEBoCPCS/xW4i1ZNz6teF5P9QrgLUiY45mB1+V+kAvt2pQV+ASXylFIY5EDkdFsCNntgDJdbjh3tjka4taUgdB05yruhhyDz/OYL9OiWdDORGagPipbRJo1gz7HwiYeoMAw0f67ygj6jLPCXg/P3mLR86/8Inv+E="));
        comboNPC.setItem(NPCSlot.MAINHAND, new ItemStack(Material.GOLD_SWORD));
        comboNPC.setLocation(location);
        comboNPC.create();

        location = new Location(Bukkit.getWorld("lobbypvp2"), -11.419, 65.0000000000, 4.473, -106.9F, -0.0F);
        GappleNPC = npcLib.createNPC(Arrays.asList("§6§lNOVO!", "§bWarp Gapple", "§e" + WarpType.Gapple + " jogadores"));
        GappleNPC.setSkin(new Skin(
                "ewogICJ0aW1lc3RhbXAiIDogMTY4MzMwMjg2NjUxMCwKICAicHJvZmlsZUlkIiA6ICJjMTNkYzkxZjg1YjA0ZWM4OGU2NDk5YzdjZDc4Zjk3MSIsCiAgInByb2ZpbGVOYW1lIiA6ICJjYXNzdGhlY3J5cHRpZCIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9mNjhlZjliYmJiZTM0NDIzMDcxZDk5N2QyZDhhNmQwZWE2MDM3YTA1Y2UwYjliZDkyZDZlMDJmNzE0OTg0NzkyIgogICAgfQogIH0KfQ==",
                "r8wISNFJ0QAIhewdcH16N4ygYBXWw6heHZGg5gugjMQkjzVNPWHTfUVEro0ChGigTykVX4t88VVKq/yJvtadIZIO4/IbRPfag5RrVPy7KaHEkErClGwJnbJf7drTSrrJR6Gg9oLvB6O7pwQ00J5txlmDXm8+v5zY0OPaJzYjG1wWqwdXbT1cGq4AivtkrLj6q4Mu6e0xYpzl+miZFcLzcSl5YBHzPhQd+k6DLswn5EMf9NjfbsLCAkh4LR1fktLIt4a33UFDYyRCRU09LYg2xqDMpSzFo/Pkc1fg2qeG4yCkbVu05i6hFwbX6vvT/9bV9URiPy+zNxQ8qsS36dWjwPDnVLp5vHf6f4j5WDFVD6Bcw5rrcsGCQXOGfyDsI1Gfj8QRjftqWbX9BW9KnjlexGCEcuBJX5B8finBBBRdJmbqUjHaqUvF4t44xOg84Bf76d8kHL2FsU4Gj1Y4y+IcOu7boprd9xWLRAQhVIOYCDnwZrFjeN36z682U6LPkCOoHqfNYEoe2bvRwyxuKuCXCL+ByCmHnBaFz7OWkvxWWNb3NF9Vkhd7DQCBd9akkceXxJ1vO2jFDObo4QssC1uRzq5cZ3YVX0Y/rKqW5haUM0w4SeiC48mTkCbzfO45l2bE9GWhEIpON1jMZbQ1tCttW8/3DEMwz4jcOfetzHCJXzE="));
        GappleNPC.setItem(NPCSlot.MAINHAND, new ItemStack(Material.GOLDEN_APPLE));
        GappleNPC.setLocation(location);
        GappleNPC.create();

        location = new Location(Bukkit.getWorld("lobbypvp2"), -12.329, 65.0000000000, 0.486, -96.1F, -3.6F);
        sumoNPC = npcLib.createNPC(Arrays.asList("§6§lNOVO!", "§bWarp Knockback", "§e" + WarpType.Knockback + " jogadores"));
        sumoNPC.setSkin(new Skin(
                "ewogICJ0aW1lc3RhbXAiIDogMTY5MzcxNzc1MjA2NCwKICAicHJvZmlsZUlkIiA6ICJkYmE4OTUzOThiYTc0MzZlOTQ2YzVkZTk4N2UzZGVkNSIsCiAgInByb2ZpbGVOYW1lIiA6ICJTb21lQ29tbW9uIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzY0NTgzODYxZGZjZjQwNmY2NWJhODlhNTUzYzllMWY1MTc3ZDlmMTE1OTQ4YTZiYjc5M2I1NmQwMGY2YjljYzMiCiAgICB9CiAgfQp9",
                "DUT1X6us4ldQpMV1bbeCMzJyNnyqC4G/GxCTeMSftc1uFLYuK0usHET1et9IzuJU7NzI+7zXfRj357UucREX6Hpo0EVSjkKaCGxH4NjW9xLcoRGDkufqI3MjYRZpWB3ef0dRq6TvqlzfXztyh31QdhFQqN7bqFol6Huh3WIcWkWUz0U6rqBjOutwcvutIHleyzsocQ/hEIEi90WJ8k5Yrk5WHENiIiH81dT1srYApIeMMAo9ZXIMDsSFKyugxZJ/cFFT05yPIbZIAzG5WKrt/LAFU55dS3ny9K1nne0E09kWveAdBKSBb+8qSfGielxZsRiXWqwCsBTXEtgwAlRo1+uKn7Z04Px2K+llED2BOjkqME8/u7R9srOK3bfBoEBfEYfhKR/yEW32m0NK3hFbjbBOvYYrBLGBobV2vBTbYRi1YV7j5OS64ax1NwOEuzPw8iWfqC2AkHfRY6bCuZBCtWdH3i56bta2fa3TOFdAiczLpk0k5/JEGbsLGyTQWOl5qw+LZ2xo9i/O5t/KfzP0/t+qoLNYfQYc/JUV3MS3I4/B24KfhTztNKsif62HxdLi7UB4gStUfcIUZXsITLBAnXqQCR84oJDa+cNf1TlUYOeaWmIQs0SWwni4xZaGJbRPwKQiAwBXlzfh5Oo0Lt2ovH3l1js6nUfvBresWHP1mWs="));
        sumoNPC.setItem(NPCSlot.MAINHAND, new ItemStack(Material.STICK));
        sumoNPC.setLocation(location);
        sumoNPC.create();

        location = new Location(Bukkit.getWorld("lobbypvp2"), -11.273, 65.0000000000, -3.361, -88.8F, -7.4F);
        potionNPC = npcLib.createNPC(Arrays.asList("§6§lNOVO!", "§bWarp 1v1", "§e" + WarpType.x1 + " jogadores"));
        potionNPC.setSkin(new Skin(
                "ewogICJ0aW1lc3RhbXAiIDogMTY1MzE3NzM5Njg3MSwKICAicHJvZmlsZUlkIiA6ICJmMTA0NzMxZjljYTU0NmI0OTkzNjM4NTlkZWY5N2NjNiIsCiAgInByb2ZpbGVOYW1lIiA6ICJ6aWFkODciLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmNmYjU2MDcyODMyMjFiNzIwMTJlOWJkOTA0MjRiMDM5ZDI0MWFmM2VhNTczNzFmYWFlNTI3NDE4M2ZhYjhkYiIsCiAgICAgICJtZXRhZGF0YSIgOiB7CiAgICAgICAgIm1vZGVsIiA6ICJzbGltIgogICAgICB9CiAgICB9CiAgfQp9",
                "oV4zwKaA9m/9Gvap0Cm4XJbs8ttL1XAbsajkwScSQgFtvb/RlyCWNehzdHCHrlzcgNvh+7efgRHtjQWv1wsMTzSvhZG9jkPA+SDW6XhIfW89IEkPxuLUXlivQe2HwNwWR0iEqlRM4MKqK1JgbOSL/i1xnY9/VowdKmTxtq4bd6y8RXaOx7P3BdnoUg5hrrItOJxlCZ+jDL5br3YmrTf7Da7mJK7BhpzMfUlLlm25Yu/e9fn2/SPYm+ysawhyl1w4CgQp2GZoB6nfNmQFLsFrKU+Ii8uXRj6V8h5c+euvVT+sLZKU1tzlEufBrtMzlLhP/waEzWlXZzyk+5vJwjPWefWqv8f9Uqo8mDRCzGiuzHQFMAalizA2TH365GHzlOJQasAP3AWwRKyFAkH9lBs/5MuyleiU0Yxc4JRhkW6bJbJph592WrthHVOxaloWaXBOr+e1gFMIxH/lr1KGFv6TUuoXQgvMSRva7/WPKooPSfdj5o+u2ABua6ULdo1RADdAvrNME3oZSo+BNJcL5E1bSuUyZ5fpd1o8duNpiumbYmaORpsPUQE5eFeFGh5TfUeeBV5dOVnV6sHnuNLqEDQlt10RexLzZ+gLXiVu/yowT+1lwLoboVozsHWVtyJ7iZjLKPpBZPA5YH7WHGHYBFM3b0pnvo9y0tkXxrVGSWSnUyc="));
        potionNPC.setItem(NPCSlot.MAINHAND, new ItemStack(Material.BLAZE_ROD));
        potionNPC.setLocation(location);
        potionNPC.create();


        new BukkitRunnable() {
            @Override
            public void run() {
                update();
            }
        }.runTaskTimer(plugin, 20L, 20L);
    }

    public void update() {
        shopNPC.setText(Arrays.asList("§d§lLOJA DE KITS", "§eKits e Combate", "§7Clique para visualizar!"));
        assistenteNPC.setText(Arrays.asList("§d§lASSISTENTE", "§eLuna"));
        caixasNPC.setText(Arrays.asList("§6§lMESTRE DAS CAIXAS", "§eManoel Gomes"));
        comboNPC.setText(Arrays.asList("§6§lNOVO!", "§bWarp ComboFly", "§e" + WarpType.ComboFly + " jogadores"));
        GappleNPC.setText(Arrays.asList("§6§lNOVO!", "§bWarp Gapple", "§e" + WarpType.Gapple + " jogadores"));
        sumoNPC.setText(Arrays.asList("§6§lNOVO!", "§bWarp Knockback", "§e" + WarpType.Knockback + " jogadores"));
        potionNPC.setText(Arrays.asList("§6§lNOVO!", "§bWarp 1v1", "§e" + WarpType.x1 + " jogadores"));


    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        new BukkitRunnable() {
            @Override
            public void run() {
                getShopNPC().show(event.getPlayer());
                getAssistenteNPC().show(event.getPlayer());
                getComboNPC().show(event.getPlayer());
                getGappleNPC().show(event.getPlayer());
                getSumoNPC().show(event.getPlayer());
                potionNPC.show(event.getPlayer());
                caixasNPC.show(event.getPlayer());
                }
        }.runTaskLater(plugin, 10L);
    }



    @EventHandler
    public void onClick(NPCInteractEvent event) {
        NPC npc = event.getNPC();
        Player player = event.getWhoClicked();
        if (npc.equals(shopNPC)) {
            if (player.hasPermission("kit.*")) {
                player.sendMessage("§c§lSHOP §fVocê já possui todos os kits da loja.");
            } else {
                ShopAbilityType.getShopKit(player);
            }
        } else if (npc.equals(getAssistenteNPC())) {
            player.sendMessage("§e§lASSISTENTE §fOlá! Eu sou a §eLuna§f e é um prazer conhecê-lo(a). Em breve, estarei atuando na network para oferecer suporte aos jogadores, tanto externa quanto internamente. Fique à vontade para me procurar se precisar de ajuda. Mal posso esperar para trabalhar junto com você!");

        } else if (npc.equals(getComboNPC())) {
            player.chat("/warp combofly");
        } else if (npc.equals(getSumoNPC())) {
            player.chat("/warp knockback");
        } else if (npc.equals(getGappleNPC())) {
            player.chat("/warp gapple");
        } else if (npc.equals(potionNPC)) {
            player.chat("/1v1");
        } else if (npc.equals(caixasNPC)) {
            BoxShopType.getBoxShop(player);
        }

    }
}
