package me.joesvart.commandnode.discord.objects;

import lombok.Getter;
import me.joesvart.commandnode.discord.DiscordCommandManager;
import me.joesvart.commandnode.discord.annotation.DiscordCommand;

import java.util.HashSet;
import java.util.Set;

@Getter
public class DiscordCategory {

    private static final Set<DiscordCategory> categories = new HashSet<>();

    private final String name;

    public DiscordCategory(String name) {
        this.name = name;
        categories.add(this);
    }

    public Set<DiscordCommand> getCommands() {
        Set<DiscordCommand> discordCommands = new HashSet<>();
        for (DiscordCommand discordCommand : DiscordCommandManager.getInstance().getDiscordCommands()) {
            if (!discordCommand.category().equalsIgnoreCase(name)) continue;
            discordCommands.add(discordCommand);
        }
        return discordCommands;
    }

    public static DiscordCategory getCategory(String name) {
        for (DiscordCategory discordCategory : categories) {
            if (discordCategory.getName().equalsIgnoreCase(name)) return discordCategory;
        }
        return null;
    }

    public static Set<DiscordCategory> getCategories() {
        for (DiscordCommand discordCommand : DiscordCommandManager.getInstance().getDiscordCommands()) {
            if (getCategory(discordCommand.category()) != null) continue;
            new DiscordCategory(discordCommand.category());
        }
        return categories;
    }
}
