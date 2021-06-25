package me.joesvart.commandnode.bukkit;

import me.joesvart.commandnode.bukkit.annotation.Command;
import me.joesvart.commandnode.bukkit.data.impl.BaseCommandData;
import lombok.Getter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public abstract class CommandHandler {

    @Getter
    private static CommandHandler commandHandler;

    private final List<BaseCommandData> commands = new ArrayList<>();

    public CommandHandler() {
        commandHandler = this;
    }

    /**
     * Register a command to the me.joesvart.commandnode.discord.handler
     *
     * @param object the object to get the command data from
     */
    public void registerCommand(Object object) {
        final List<Method> commands = this.getMethods(Command.class, object);

        for (Method command : commands) {
            this.register(new BaseCommandData(object, command));
        }
    }

    /**
     * Register the command to the {@link CommandHandler#commands} list.
     *
     * @param data the command to register
     */
    public void register(BaseCommandData data) {
        this.commands.add(data);
    }

    /**
     * Get all methods annotated with a {@link Annotation} in an object
     *
     * @param annotation the annotation which the method must be annotated with
     * @param object     the object with the methods
     * @param <T>        the type of the annontation
     * @return the list of methods
     */
    private <T extends Annotation> List<Method> getMethods(Class<T> annotation, Object object) {
        return Arrays.stream(object.getClass().getMethods())
                .filter(method -> method.getAnnotation(annotation) != null)
                .collect(Collectors.toList());
    }
}