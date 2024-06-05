package ca.billweb.commands;

import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

public class CommandList {
    public static final CommandData[] slashCommands = {
            Commands.slash("ping", "Check if bot is online"),
            Commands.slash("arbping", "Check if bot is online")
    };
}
