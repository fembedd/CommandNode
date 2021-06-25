package me.joesvart.commandnode.bukkit;

import me.joesvart.commandnode.bukkit.data.CommandExecutingData;
import me.joesvart.commandnode.bukkit.data.ParameterData;
import me.joesvart.commandnode.bukkit.data.impl.BaseCommandData;
import me.joesvart.commandnode.bukkit.executor.CommandExecutor;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;

public interface CommandInvoker {

    BaseCommandData getData();

    /**
     * Handle the command execution for a command executor
     *
     * @param executor the executor of the command
     * @param label    the label used to perform the command
     * @param strings  the provided arguments
     */
    default void execute(CommandExecutor executor, String label, String[] strings) {
        final CommandExecutingData executingData = this.getData(strings);
        final String[] args = executingData.getArgs();

        if(!executor.hasPermission(executingData.getPermission())) {
            executor.sendMessage("&cNo permission.");
            return;
        }

        if(!executor.isUser() && executingData.getCommandData().isUserOnly()) {
            executor.sendMessage("&cOnly users can perform this command.");
            return;
        }

        final ParameterData[] parameterDatum = executingData.getCommandData().getParameterData();
        final Object[] data;

        if (parameterDatum != null) {
            data = new Object[parameterDatum.length];

            for (int i = 0; i < parameterDatum.length; i++) {
                final ParameterData parameterData = parameterDatum[i];

                if (i >= args.length && parameterData.getDefaultValue() == null) {
                    executor.sendMessage(executingData.getCommandData().getUsageMessage(label));
                    return;
                }

                try {
                    if (parameterData.isLastIndex() && parameterData.isString()) {
                        final String[] array = Arrays.copyOfRange(args, i, args.length);
                        final StringBuilder builder = new StringBuilder();

                        for (String string : array) {
                            if (!builder.toString().isEmpty()) {
                                builder.append(" ");
                            }

                            builder.append(string);
                        }

                        data[i] = builder.toString();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            data = new Object[0];
        }

        try {
            executingData.getCommandData().invoke(executor, data);
        } catch (InvocationTargetException | IllegalAccessException exception) {
            exception.printStackTrace();
        }
    }

    default CommandExecutingData getData(String[] strings) {
        if (strings.length >= 1) {
            final BaseCommandData data = this.getByLabel((Collection<BaseCommandData>) this.getData().getCommand(), strings[0]);

            if (data != null) {
                return new CommandExecutingData(
                    Arrays.copyOfRange(strings, 1, strings.length),
                    data,
                    data.getCommand().permission()
                );
            }
        }

        return new CommandExecutingData(
            strings,
            this.getData(),
            this.getData().getCommand().permission()
        );
    }

    /**
     * Get a {@link BaseCommandData} object by the
     * sub command's label from a list of datum.
     *
     * @param datum the datum to find the sub command from
     * @param label the found label
     * @return the found data, or null
     */
    default BaseCommandData getByLabel(Collection<BaseCommandData> datum, String label) {
        for (BaseCommandData data : datum) {
            if (data.getCommand().label().equalsIgnoreCase(label)) {
                return data;
            }
        }

        return null;
    }

}
