package me.joesvart.commandnode.discord.objects;

import lombok.Getter;
import me.joesvart.commandnode.discord.DiscordCommandManager;
import me.joesvart.commandnode.discord.annotation.DiscordCommand;

import java.util.Arrays;

public class DiscordCommandPreConstructor {

    @Getter
    private final DiscordCommand discordCommand;

    @Getter
    private final String label;

    @Getter
    private final String[] args;

    public DiscordCommandPreConstructor(String rawMessage, String prefix, DiscordCommandManager discordCommandManager) {
        String[] argsWithOutPrefix = rawMessage.replaceFirst(prefix, "").split("\\s+");
        this.label = argsWithOutPrefix[0].toLowerCase();

        this.discordCommand = discordCommandManager.getCommandByNameOrAlias(label);
        this.args = Arrays.copyOfRange(argsWithOutPrefix, 1, argsWithOutPrefix.length);
    }
}
