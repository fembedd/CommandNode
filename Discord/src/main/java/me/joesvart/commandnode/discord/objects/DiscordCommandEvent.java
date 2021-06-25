package me.joesvart.commandnode.discord.objects;

import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

@Getter
public class DiscordCommandEvent extends GuildMessageReceivedEvent {

    private final String[] args;

    private final String label;

    public DiscordCommandEvent(
            JDA jda, int responseNumber, Message message, DiscordCommandPreConstructor discordCommandPreConstructor) {
        super(jda, responseNumber, message);
        this.args = discordCommandPreConstructor.getArgs();
        this.label = discordCommandPreConstructor.getLabel();
    }

    public void reply(Message message) {
        this.getMessage().reply(message).queue();
    }

    public void reply(String message) {
        this.getMessage().reply(message).queue();
    }
}
