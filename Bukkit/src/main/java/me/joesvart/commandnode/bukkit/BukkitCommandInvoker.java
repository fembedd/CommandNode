package me.joesvart.commandnode.bukkit;

import me.joesvart.commandnode.bukkit.executor.BukkitCommandExecutor;
import me.joesvart.commandnode.bukkit.data.impl.BaseCommandData;
import lombok.Getter;
import me.joesvart.commandnode.bukkit.executor.CommandExecutor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

@Getter
public class BukkitCommandInvoker extends Command implements CommandInvoker {

    private final BaseCommandData data;

    /**
     * Constructor to make a new {@link CommandInvoker}
     * object with a provided {@link BaseCommandData} object.
     *
     * @param data the data to use for the command
     */
    public BukkitCommandInvoker(BaseCommandData data) {
        super(data.getCommand().label());

        this.data = data;

        if (data.getCommand().aliases().length > 0) {
            this.setAliases(Arrays.asList(data.getCommand().aliases()));
        }
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] strings) {
        this.execute(new BukkitCommandExecutor(sender), label, strings);
        return false;
    }

    @Override
    public void execute(CommandExecutor executor, String label, String[] strings) {
        CommandInvoker.super.execute(executor, label, strings);
    }
}