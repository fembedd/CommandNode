package me.joesvart.commandnode.bukkit.example;

import me.joesvart.commandnode.bukkit.annotation.Command;
import me.joesvart.commandnode.bukkit.annotation.Sender;
import org.bukkit.entity.Player;

public class ExampleCommand {

    @Command(label = "example", permission = "commandnode.command.example", aliases = {"test", "exam"}, userOnly = false)
    public void example(@Sender Player player) {
        player.sendMessage("test");
    }
}