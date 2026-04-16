/*
 * Copyright 2015 Marvin Schäfer (inventivetalent). All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ''AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and contributors and should not be interpreted as representing official policies,
 * either expressed or implied, of anybody else.
 */

package pvp.sunshine.bukkit.manager.bossbar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nonnull;

import org.bukkit.entity.Player;

public abstract class BossBarAPI {

	private static final Map<UUID, BossBar> barMap = new ConcurrentHashMap<>();

	public static void setMessage(Player player, String message) {
		setMessage(player, message, 100);
	}

	public static void setMessage(Player player, String message, float percentage) {
		setMessage(player, message, percentage, 0);
	}

	public static void setMessage(Player player, String message, float percentage, int timeout) {
		setMessage(player, message, percentage, timeout, 100);
	}

	public static void setMessage(Player player, String message, float percentage, int timeout, float minHealth) {
		if (!barMap.containsKey(player.getUniqueId())) {
			barMap.put(player.getUniqueId(), new BossBar(player, message, percentage, timeout, minHealth));
		}
		BossBar bar = barMap.get(player.getUniqueId());
		if (!bar.message.equals(message)) {
			bar.setMessage(message);
		}
		float newHealth = percentage / 100F * bar.getMaxHealth();
		if (bar.health != newHealth) {
			bar.setHealth(newHealth);
		}
		if (!bar.isVisible()) {
			bar.setVisible(true);
		}
	}

	public static String getMessage(Player player) {
		BossBar bar = getBossBar(player);
		if (bar == null) {
			return null;
		}
		return bar.getMessage();
	}

	public static boolean hasBar(@Nonnull Player player) {
		return barMap.containsKey(player.getUniqueId());
	}

	public static void removeBar(@Nonnull Player player) {
		BossBar bar = getBossBar(player);
		if (bar == null) {
			return;
		}
		bar.setVisible(false);
		barMap.remove(player.getUniqueId());
	}

	public static void setHealth(Player player, float percentage) {
		BossBar bar = getBossBar(player);
		if (bar == null) {
			return;
		}
		bar.setHealth(percentage);
	}

	public static float getHealth(@Nonnull Player player) {
		BossBar bar = getBossBar(player);
		if (bar == null) {
			return -1;
		}
		return bar.getHealth();
	}

	public static BossBar getBossBar(@Nonnull Player player) {
		if (player == null) {
			return null;
		}
		return barMap.get(player.getUniqueId());
	}

	public static Collection<BossBar> getBossBars() {
		List<BossBar> list = new ArrayList<>(barMap.values());
		return list;
	}

	protected static void sendPacket(Player p, Object packet) {
		if (p == null || packet == null) {
			throw new IllegalArgumentException("player and packet cannot be null");
		}
		try {
			Object handle = Reflection.getHandle(p);
			Object connection = Reflection.getField(handle.getClass(), "playerConnection").get(handle);
			Reflection.getMethod(connection.getClass(), "sendPacket", Reflection.getNMSClass("Packet"))
					.invoke(connection, new Object[] { packet });
		} catch (Exception e) {
		}
	}

}
