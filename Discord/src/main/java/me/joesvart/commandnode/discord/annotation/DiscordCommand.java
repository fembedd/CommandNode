package me.joesvart.commandnode.discord.annotation;

import me.joesvart.commandnode.discord.objects.DiscordCommandEvent;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.Collections;
import java.util.List;

public interface DiscordCommand {

    String getName();

    String getDescription();

    void execute(DiscordCommandEvent command, TextChannel textChannel, Member member, String[] args);

    default List<String> aliases() {
        return Collections.emptyList();
    }

    default String category() {
        return "General";
    }
}
