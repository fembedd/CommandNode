package me.joesvart.commandnode.discord;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import me.joesvart.commandnode.discord.annotation.DiscordCommand;
import net.dv8tion.jda.api.JDA;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Setter @Getter @NonNull
public class DiscordCommandManager {

    @Getter
    private static DiscordCommandManager instance;

    private final Logger logger = Logger.getLogger("CommandNode - Discord");
    private final List<DiscordCommand> discordCommands = new ArrayList<>();

    private final JDA jda;

    private Color color = Color.ORANGE;

    private String prefix;
    private String noFoundMessage = "Sorry! I can't find the '$1' command.";
    private String errorMessage = "An error occurred while executing this command.";

    private boolean sendMessageIfCommandNoFound = true;

    public DiscordCommandManager(JDA jda, String prefix) {
        instance = this;

        this.jda = jda;
        this.prefix = prefix;
    }

    /**
     * @param discordCommands provided a {@link DiscordCommand} to can be register on {@link
     *                     DiscordCommandManager#discordCommands}
     */
    public void registerCommands(DiscordCommand... discordCommands) {
        for (DiscordCommand discordCommand : discordCommands) {
            registerCommand(discordCommand);
        }
    }

    /**
     * @param discordCommand return a {@link DiscordCommandManager#discordCommands}
     */
    public void registerCommand(DiscordCommand discordCommand) {
        discordCommands.add(discordCommand);
    }

    /**
     * @param name assign on {@link DiscordCommand}
     * @return find all on {@link DiscordCommandManager#discordCommands}
     */
    public DiscordCommand getCommandByNameOrAlias(String name) {
        for (DiscordCommand discordCommand : discordCommands) {
            if (discordCommand.getName().equalsIgnoreCase(name)) return discordCommand;
            if (!discordCommand.aliases().isEmpty()) {
                for (String alt : discordCommand.aliases()) {
                    if (alt.equalsIgnoreCase(name)) return discordCommand;
                }
            }
        }
        return null;
    }
}
