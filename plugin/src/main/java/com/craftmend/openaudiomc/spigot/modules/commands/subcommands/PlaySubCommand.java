package com.craftmend.openaudiomc.spigot.modules.commands.subcommands;

import com.craftmend.openaudiomc.spigot.OpenAudioMcSpigot;
import com.craftmend.openaudiomc.spigot.modules.commands.interfaces.SubCommand;
import com.craftmend.openaudiomc.spigot.modules.commands.objects.Argument;
import com.craftmend.openaudiomc.generic.media.objects.Media;
import com.craftmend.openaudiomc.generic.media.objects.MediaOptions;
import com.craftmend.openaudiomc.spigot.modules.players.objects.SpigotConnection;
import com.craftmend.openaudiomc.spigot.modules.players.objects.PlayerSelector;
import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlaySubCommand extends SubCommand {

    private OpenAudioMcSpigot openAudioMcSpigot;

    public PlaySubCommand(OpenAudioMcSpigot openAudioMcSpigot) {
        super("play");
        registerArguments(
                new Argument("<selector> <source>",
                        "Plays a sound for all the players in a selection"),
                new Argument("<selector> <source> <options>",
                        "Plays a sound with configuration (like fade time, sync etc) for all players in a selection")
        );
        this.openAudioMcSpigot = openAudioMcSpigot;
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            Bukkit.getServer().dispatchCommand(sender, "oa help " + getCommand());
            return;
        }

        if (args.length == 2) {
            Media media = new Media(args[1]);
            for (Player player : new PlayerSelector(args[0]).getPlayers(sender)) {
                SpigotConnection spigotConnection = openAudioMcSpigot.getPlayerModule().getClient(player);
                spigotConnection.sendMedia(media);
            }
            message(sender, "Media created.");
            return;
        }

        if (args.length == 3) {
            try {
                MediaOptions mediaOptions = new Gson().fromJson(args[2], MediaOptions.class);
                Media media = new Media(args[1]).applySettings(mediaOptions);
                for (Player player : new PlayerSelector(args[0]).getPlayers(sender)) {
                    SpigotConnection spigotConnection = openAudioMcSpigot.getPlayerModule().getClient(player);
                    spigotConnection.sendMedia(media);
                }
                message(sender, "Media and options created.");
            } catch (Exception e) {
                message(sender, "Error. Invalid options. Please refer to the command guide.");
            }
            return;
        }
        Bukkit.getServer().dispatchCommand(sender, "oa help " + getCommand());
    }
}
