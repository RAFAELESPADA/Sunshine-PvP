// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.utils.npc.nms;

import java.util.Iterator;
import net.minecraft.server.v1_8_R3.CancelledPacketHandleException;
import br.com.stenox.training.utils.hologram.individual.HologramLine;
import br.com.stenox.training.utils.hologram.individual.IndividualHolograms;
import org.bukkit.event.Event;
import br.com.stenox.training.utils.npc.NPCInteractEvent;
import br.com.stenox.training.utils.npc.enums.EventAction;
import org.bukkit.Bukkit;
import br.com.stenox.training.utils.npc.NPC;
import br.com.stenox.training.Main;
import io.netty.channel.ChannelPromise;
import io.netty.channel.ChannelHandlerContext;
import org.bukkit.entity.Player;
import io.netty.channel.ChannelDuplexHandler;

public class PacketReader extends ChannelDuplexHandler
{
    private Player p;
    private final double d;
    
    public PacketReader(final Player p) {
        this.d = Math.pow(2.5, 2.5);
        this.p = p;
    }
    
    public void write(final ChannelHandlerContext ctx, final Object msg, final ChannelPromise promise) throws Exception {
        super.write(ctx, msg, promise);
    }
    
    public void channelRead(final ChannelHandlerContext context, final Object packet) throws Exception {
        try {
            if (packet.getClass().getSimpleName().equalsIgnoreCase("PacketPlayInUseEntity")) {
                final Integer id = Integer.parseInt(ReflectionUtil.getFieldValue(packet, "a").toString());
                final String action = ReflectionUtil.getFieldValue(packet, "action").toString();
                for (final NPC npc : Main.getInstance().getNpcManager().getNpcs()) {
                    if (npc.getID() == id) {
                        if (action.equalsIgnoreCase("ATTACK")) {
                            Bukkit.getServer().getPluginManager().callEvent((Event)new NPCInteractEvent(this.p, npc, EventAction.LEFT_CLICK));
                            break;
                        }
                        if (action.equalsIgnoreCase("INTERACT")) {
                            Bukkit.getServer().getPluginManager().callEvent((Event)new NPCInteractEvent(this.p, npc, EventAction.RIGHT_CLICK));
                            break;
                        }
                        break;
                    }
                }
                boolean found = false;
                for (final IndividualHolograms h : Main.getInstance().getHologramManager().getHologramsList()) {
                    if (found) {
                        break;
                    }
                    if (h.getTarget() != this.p) {
                        continue;
                    }
                    if (h.isHided()) {
                        continue;
                    }
                    if (h.getClickHandler() == null) {
                        continue;
                    }
                    for (int i = 0; i < h.getHologramLines().size(); ++i) {
                        final HologramLine e = h.getHologramLines().get(i);
                        if (e.getSlime().getId() == id) {
                            found = true;
                            h.getClickHandler().onClick(this.p, h, i, action.equalsIgnoreCase("INTERACT_AT") ? EventAction.RIGHT_CLICK : EventAction.LEFT_CLICK);
                            break;
                        }
                    }
                }
            }
            super.channelRead(context, packet);
        }
        catch (CancelledPacketHandleException e2) {
            e2.printStackTrace();
        }
    }
}