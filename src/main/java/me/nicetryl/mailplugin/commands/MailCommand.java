package me.nicetryl.mailplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MailCommand implements TabExecutor {
    private final Map<String, List<Mail>> mailStorage = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage(ChatColor.RED + "Only players can execute this command.");
            return true;
        }
        if(args.length == 0){
            p.sendMessage(ChatColor.WHITE + "/mail send <name> <mail>");
            p.sendMessage(ChatColor.WHITE + "/mail read");
            p.sendMessage(ChatColor.WHITE + "/mail clear");
        }
        if(args.length >= 1 ) {
            String firstArgument = args[0];

            if (firstArgument.equalsIgnoreCase("send")) {
                if(args.length == 1 || args.length == 2){
                    p.sendMessage(ChatColor.RED + "Please provide a message to send");
                    return true;
                }
                String playerName = args[1];
                Player target = Bukkit.getServer().getPlayerExact(playerName);

                StringBuilder builder = new StringBuilder();
                String mailSender = p.getName();
                for (int i = 2; i < args.length; i++) {
                    builder.append(args[i]);
                    builder.append(" ");
                }
                String mailContents = builder.toString().stripTrailing();

                if(target == null) {
                    p.sendMessage(ChatColor.RED + "This user is offline or does not exist");
                    return true;
                }
                Mail mail = new Mail(playerName, mailSender, mailContents);
                mailStorage.computeIfAbsent(playerName, k -> new ArrayList<>()).add(mail);
                sender.sendMessage(ChatColor.YELLOW + "You have send mail to " + ChatColor.WHITE + playerName);
                return true;
            }
            if (firstArgument.equalsIgnoreCase("read")) {
                List<Mail> playerMail = mailStorage.getOrDefault(p.getName(), new ArrayList<>());
                if(playerMail.isEmpty()) {
                    p.sendMessage(ChatColor.YELLOW + "Mailbox Empty");
                }else{
                    p.sendMessage(ChatColor.GOLD + "Your Mail: ");
                    for(Mail mail : playerMail) {
                        p.sendMessage(ChatColor.YELLOW + "From: " + ChatColor.WHITE + mail.getSenderName() + " - " + mail.getMailContents());
                    }
                    return true;
                }
            }
            if(firstArgument.equalsIgnoreCase("clear")){
                mailStorage.clear();
                p.sendMessage(ChatColor.GOLD + "Your mail has been cleared");
                return true;
            }

        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        if(args.length == 1){
            List<String> argOne = new ArrayList<>();
            argOne.add("send");
            argOne.add("read");
            argOne.add("clear");
            return argOne;
        }
        return null;
    }
}
