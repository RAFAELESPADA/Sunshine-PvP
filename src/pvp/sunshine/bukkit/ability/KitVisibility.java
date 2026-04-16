package pvp.sunshine.bukkit.ability;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class KitVisibility {

    public static final Map<String, Integer> abilityCounts = new HashMap<>();

    static {
        initializeAbilityCounts();
    }

    private static void initializeAbilityCounts() {
        for (Ability ability : Ability.values()) {
            abilityCounts.put(ability.getName(), 0);
        }
    }

    public static int getAbilityCount(Ability ability) {
        int count = 0;
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (RegisterAbility.getAbility(player).equals(ability.getName())) {
                count++;
            }
        }
        return count;
    }

    private enum Ability {
        PVP("PvP"),
        KANGAROO("Kangaroo"),
        GRANDPA("Grandpa"),
        ANCHOR("Anchor"),
        ANTI_STOMPER("AntiStomper"),
        BARBARIAN("Barbarian"),
        BOXER("Boxer"),
        CONFUSER("Confuser"),
        FIREMAN("Fireman"),
        FISHERMAN("Fisherman"),
        GLADIATOR("Gladiator"),
        HOT_POTATO("HotPotato"),
        MAGMA("Magma"),
        MONK("Monk"),
        NINJA("Ninja"),
        PHANTOM("Phantom"),
        SNAIL("Snail"),
        STOMPER("Stomper"),
        SWITCHER("Switcher"),
        THOR("Thor"),
        TIME_LORD("TimeLord"),
        URGAL("Urgal"),
        VIKING("Viking"),
        VIPER("Viper");

        private final String name;

        Ability(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
