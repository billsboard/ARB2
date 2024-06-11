package ca.billweb.commands;

import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

public class CommandList {
    public static final CommandData[] slashCommands = {
            Commands.slash("ping", "Check if bot is online"),
            Commands.slash("arbping", "Check if bot is online"),

            Commands.slash("server", "Get information about the server"),
            Commands.slash("arbserver", "Get information about the server"),

            Commands.slash("profile", "Get information about a player")
                    .addOption(OptionType.USER, "user", "Player to query", false),
            Commands.slash("arbprofile", "Get information about a player")
                    .addOption(OptionType.USER, "user", "Player to query", false),

            Commands.slash("daily", "Claim your daily reward"),
            Commands.slash("arbdaily", "Claim your daily reward"),

            Commands.slash("weekly", "Claim your daily reward"),
            Commands.slash("arbweekly", "Claim your daily reward"),

            Commands.slash("shop", "View the shop")
                    .addOption(OptionType.STRING, "category", "Shop category to view", false)
                    .addOption(OptionType.INTEGER, "page", "Shop page number", false),
            Commands.slash("arbshop", "View the shop")
                    .addOption(OptionType.STRING, "category", "Shop category to view", false)
                    .addOption(OptionType.INTEGER, "page", "Shop page number", false),

            Commands.slash("balance", "View player balances")
                    .addOption(OptionType.USER, "user", "Player to query", false),
            Commands.slash("money", "View player balances")
                    .addOption(OptionType.USER, "user", "Player to query", false),
            Commands.slash("arbbalance", "View player balances")
                    .addOption(OptionType.USER, "user", "Player to query", false),
            Commands.slash("arbmoney", "View player balances")
                    .addOption(OptionType.USER, "user", "Player to query", false),

            Commands.slash("deposit", "Deposit money into bank")
                    .addOption(OptionType.INTEGER, "amount", "Amount of money (> 0) to deposit", true),
            Commands.slash("arbdeposit", "Deposit money into bank")
                    .addOption(OptionType.INTEGER, "amount", "Amount of money (> 0) to deposit", true),

            Commands.slash("withdraw", "Withdraw money from bank")
                    .addOption(OptionType.INTEGER, "amount", "Amount of money (> 0) to deposit", true),
            Commands.slash("arbwithdraw", "Deposit money into bank")
                    .addOption(OptionType.INTEGER, "amount", "Amount of money (> 0) to deposit", true),

            Commands.slash("buy", "Buy an item")
                    .addOption(OptionType.STRING, "item", "The item to buy", true)
                    .addOption(OptionType.INTEGER, "amount", "How many to buy", false),
            Commands.slash("arbbuy", "Buy an item")
                    .addOption(OptionType.STRING, "item", "The item to buy", true)
                    .addOption(OptionType.INTEGER, "amount", "How many to buy", false),
    };
}
