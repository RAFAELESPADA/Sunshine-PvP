package net.kombopvp.pvp.command;


/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;

import net.kombopvp.pvp.KomboPvP2;
/*    */ 
/*    */ public class KITPVP implements CommandExecutor
/*    */ {
/*    */   private KomboPvP2 main;
/*    */   static KomboPvP2 plugin;
/*    */   
/*    */   public KITPVP(KomboPvP2 main)
/*    */   {
/* 17 */     this.main = main;
/* 18 */     plugin = main;
/*    */   }
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
/*    */   {
/* 23 */     
/* 24 */     if (cmd.getName().equalsIgnoreCase("kitpvp"))
/*    */     {
/*    */ 
/* 27 */       sender.sendMessage("§6§lARENA v2.4 §9 criado por Rafael Melo");
/* 37 */       
/*    */       
/* 39 */       return false;
/*    */     }
/* 41 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\Desktop\video\Minhas Coisas do Desktop\KP-PVPvB12 (1).jar!\me\RafaelAulerDeMeloAraujo\main\KitCredits.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */

