package net.kombopvp.pvp.command;


/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Listener;

import net.kombopvp.pvp.KomboPvP2;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StaffChat
/*    */   implements Listener, CommandExecutor
/*    */ {
/*    */   private KomboPvP2 main;
/* 17 */   public int vida = 7;
/* 18 */   public int fome = 7;
/*    */   
/*    */   public StaffChat(KomboPvP2 main) {
/* 21 */     this.main = main;
/*    */   }
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
/* 25 */     if (!(sender instanceof Player))
/*    */     {
/* 27 */       sender.sendMessage("§cThis command is only for players!");
/* 28 */       return true;
/*    */     }
/* 30 */     if (cmd.getName().equalsIgnoreCase("sc"))
/*    */     {
/*    */ 
/* 33 */       if (args.length == 0) {
/* 34 */         sender.sendMessage("§7> §cUse: §7/sc <message>");
/*    */       }
/* 36 */       if (args.length > 0)
/*    */       {
/* 38 */         StringBuilder string = new StringBuilder();
/* 39 */         for (int i = 0; i < args.length; i++) {
/* 40 */           string.append(args[i] + " ");
/*    */         }
/* 42 */         String mensagem = string.toString();
/*    */         
/*    */ 
/* 45 */         for (Player arrayOfPlayer : Bukkit.getOnlinePlayers())
/*    */         {
/*    */ 
/* 48 */           Player staff = arrayOfPlayer;
/* 49 */           if (!sender.hasPermission("kombo.cmd.report")) {
/* 50 */             sender.sendMessage("§cVocê não tem permissão");
/*    */             
/* 52 */             return true;
/*    */           }
/* 54 */           if (staff.hasPermission("kombo.cmd.report")) {
/* 55 */             staff.sendMessage("§5§LSC §b§L\u279C §6" + sender.getName() + ": §f" + mensagem);
/*    */           }
/*    */         }
/*    */       }
/*    */       
/* 60 */       return false;
/*    */     }
/* 62 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\Desktop\video\Minhas Coisas do Desktop\KP-PVPvB12 (1).jar!\me\RafaelAulerDeMeloAraujo\main\StaffChat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */