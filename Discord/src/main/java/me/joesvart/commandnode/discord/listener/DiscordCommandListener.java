package me.joesvart.commandnode.discord.listener;

import lombok.AllArgsConstructor;
import me.joesvart.commandnode.discord.DiscordCommandManager;
import me.joesvart.commandnode.discord.objects.DiscordCommandEvent;
import me.joesvart.commandnode.discord.objects.DiscordCommandPreConstructor;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
public class DiscordCommandListener extends ListenerAdapter {

    private final DiscordCommandManager discordCommandManager;

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        if (!event.getMessage().getContentRaw().startsWith(discordCommandManager.getPrefix())) return;
        if (event.getMessage().getContentRaw().equalsIgnoreCase("!")) return;

        DiscordCommandPreConstructor discordCommandPreConstructor =
            new DiscordCommandPreConstructor(event.getMessage().getContentRaw(), discordCommandManager.getPrefix(), discordCommandManager);

        if (discordCommandPreConstructor.getDiscordCommand() != null) {
            try {
                discordCommandPreConstructor
                        .getDiscordCommand()
                        .execute(
                                new DiscordCommandEvent(
                                        event.getJDA(),
                                        (int) event.getResponseNumber(),
                                        event.getMessage(),
                                    discordCommandPreConstructor),
                                event.getChannel(),
                                event.getMember(),
                                discordCommandPreConstructor.getArgs());
            } catch (Exception exception) {
                discordCommandManager
                        .getLogger()
                        .warning("An error occurred while executing " + discordCommandPreConstructor.getLabel());
                event.getMessage().reply(discordCommandManager.getErrorMessage()).queue();
                exception.printStackTrace();
            }
        } else {
            if (discordCommandManager.isSendMessageIfCommandNoFound())
                event.getMessage().reply(discordCommandManager.getNoFoundMessage().replace("$1", discordCommandPreConstructor.getLabel())).queue();
        }
    }
}
