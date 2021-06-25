package me.joesvart.commandnode.bukkit.data.impl;

import me.joesvart.commandnode.bukkit.annotation.Command;
import me.joesvart.commandnode.bukkit.data.CommandData;

import java.lang.reflect.Method;

public class BaseCommandData extends CommandData<Command> {

    /**
     * Constructor to make a new {@link CommandData} object
     *
     * @param object the object to create it for
     * @param method the method to register to the command data
     */
    public BaseCommandData(Object object, Method method) {
        super(object, method);
    }

    /**
     * Get the type of the annotation
     *
     * @return the type
     */
    @Override
    public Class<Command> getAnnotationType() {
        return Command.class;
    }

    /**
     * Check if the command is user-only
     *
     * @return whether it's user-only or not
     */
    @Override
    public boolean isUserOnly() {
        return this.getCommand().userOnly();
    }
}
