package pvp.sunshine.bukkit.manager.event;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class HelixActionBar {
      private static Plugin plugin;

          private static String nmsver;

              private static boolean useOldMethods = false;

                  public static void send(Player player, String message) {
                            if (!player.isOnline())
                                        return;
                                                       
                                                                                            nmsver = Bukkit.getServer().getClass().getPackage().getName();
                                                                                                    nmsver = nmsver.substring(nmsver.lastIndexOf(".") + 1);
                                                                                                            try {
                                                                                                                          Object packet;
                                                                                                                                      Class<?> craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + nmsver + ".entity.CraftPlayer");
                                                                                                                                                  Object craftPlayer = craftPlayerClass.cast(player);
                                                                                                                                                              Class<?> packetPlayOutChatClass = Class.forName("net.minecraft.server." + nmsver + ".PacketPlayOutChat");
                                                                                                                                                                          Class<?> packetClass = Class.forName("net.minecraft.server." + nmsver + ".Packet");
                                                                                                                                                                                      if (useOldMethods) {
                                                                                                                                                                                                        Class<?> chatSerializerClass = Class.forName("net.minecraft.server." + nmsver + ".ChatSerializer");
                                                                                                                                                                                                                        Class<?> iChatBaseComponentClass = Class.forName("net.minecraft.server." + nmsver + ".IChatBaseComponent");
                                                                                                                                                                                                                                        Method m3 = chatSerializerClass.getDeclaredMethod("a", new Class[] { String.class });
                                                                                                                                                                                                                                                        Object cbc = iChatBaseComponentClass.cast(m3.invoke(chatSerializerClass, new Object[] { "{\"text\": \"" + message + "\"}" }));
                                                                                                                                                                                                                                                                        packet = packetPlayOutChatClass.getConstructor(new Class[] { iChatBaseComponentClass, byte.class }).newInstance(new Object[] { cbc, Byte.valueOf((byte)2) });
                                                                                                                                                                                      } else {
                                                                                                                                                                                                        Class<?> chatComponentTextClass = Class.forName("net.minecraft.server." + nmsver + ".ChatComponentText");
                                                                                                                                                                                                                        Class<?> iChatBaseComponentClass = Class.forName("net.minecraft.server." + nmsver + ".IChatBaseComponent");
                                                                                                                                                                                                                                        try {
                                                                                                                                                                                                                                                              Class<?> chatMessageTypeClass = Class.forName("net.minecraft.server." + nmsver + ".ChatMessageType");
                                                                                                                                                                                                                                                                                  Object[] chatMessageTypes = chatMessageTypeClass.getEnumConstants();
                                                                                                                                                                                                                                                                                                      Object chatMessageType = null;
                                                                                                                                                                                                                                                                                                                          for (Object obj : chatMessageTypes) {
                                                                                                                                                                                                                                                                                                                                                    if (obj.toString().equals("GAME_INFO"))
                                                                                                                                                                                                                                                                                                                                                                                chatMessageType = obj;
                                                                                                                                                                                                                                                                                                                          }
                                                                                                                                                                                                                                                                                                                                              Object chatCompontentText = chatComponentTextClass.getConstructor(new Class[] { String.class }).newInstance(new Object[] { message });
                                                                                                                                                                                                                                                                                                                                                                  packet = packetPlayOutChatClass.getConstructor(new Class[] { iChatBaseComponentClass, chatMessageTypeClass }).newInstance(new Object[] { chatCompontentText, chatMessageType });
                                                                                                                                                                                                                                        } catch (ClassNotFoundException cnfe) {
                                                                                                                                                                                                                                                              Object chatCompontentText = chatComponentTextClass.getConstructor(new Class[] { String.class }).newInstance(new Object[] { message });
                                                                                                                                                                                                                                                                                  packet = packetPlayOutChatClass.getConstructor(new Class[] { iChatBaseComponentClass, byte.class }).newInstance(new Object[] { chatCompontentText, Byte.valueOf((byte)2) });
                                                                                                                                                                                                                                        }
                                                                                                                                                                                      }
                                                                                                                                                                                                  Method craftPlayerHandleMethod = craftPlayerClass.getDeclaredMethod("getHandle", new Class[0]);
                                                                                                                                                                                                              Object craftPlayerHandle = craftPlayerHandleMethod.invoke(craftPlayer, new Object[0]);
                                                                                                                                                                                                                          Field playerConnectionField = craftPlayerHandle.getClass().getDeclaredField("playerConnection");
                                                                                                                                                                                                                                      Object playerConnection = playerConnectionField.get(craftPlayerHandle);
                                                                                                                                                                                                                                                  Method sendPacketMethod = playerConnection.getClass().getDeclaredMethod("sendPacket", new Class[] { packetClass });
                                                                                                                                                                                                                                                              sendPacketMethod.invoke(playerConnection, new Object[] { packet });
                                                                                                            } catch (Exception e) {
                                                                                                                          e.printStackTrace();
                                                                                                            }
                  }

                   
                              
                      
                                                                                                            
                                                                                                                                                                                                                                        
                                                                                                                                                                                                                                                                                                                          
                                                                                                                                                                                                                                        
                                                                                                                                                                                      }
                                                                                                                                                                                      
                                                                                                            
                                                                
                  

