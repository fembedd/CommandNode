package me.joesvart.commandnode.bukkit;

import me.joesvart.commandnode.bukkit.data.impl.BaseCommandData;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;

import java.lang.reflect.Field;

public class BukkitCommandHandler extends CommandHandler {

    private final String fallbackPrefix;
    private final CommandMap commandMap;

    public BukkitCommandHandler(String fallbackPrefix) {
        super();

        if ((this.commandMap = this.getCommandMap()) == null) {
            throw new IllegalArgumentException("Unable to find CommandMap inside of Bukkit#getPluginManager");
        }

        this.fallbackPrefix = fallbackPrefix;
    }

    /**
     * Register the command to the {@link BukkitCommandHandler} list.
     *
     * @param data the command to register
     */
    @Override
    public void register(BaseCommandData data) {
        super.register(data);
        this.commandMap.register(fallbackPrefix, new BukkitCommandInvoker(data));
    }

    /**
     * Get the {@link CommandMap} registered
     * inside of the {@link org.bukkit.plugin.SimplePluginManager} class
     *
     * @return the found command map, or null
     */
    private CommandMap getCommandMap() {
        try {
            final Field field = Bukkit.getPluginManager().getClass().getDeclaredField("commandMap");
            field.setAccessible(true);

            return (CommandMap) field.get(Bukkit.getPluginManager());
        } catch (NoSuchFieldException | IllegalAccessException exception) {
            exception.printStackTrace();
        }

        return null;
    }
}
