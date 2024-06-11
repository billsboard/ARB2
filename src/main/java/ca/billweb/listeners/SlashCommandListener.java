package ca.billweb.listeners;

import ca.billweb.arb.Player;
import ca.billweb.arb.ServerObject;
import ca.billweb.arb.Shop;
import ca.billweb.bot.Utils;
import ca.billweb.constants.GameParameters;
import ca.billweb.constants.TimerConstants;
import ca.billweb.db.DBConnector;
import com.mongodb.client.MongoCollection;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class SlashCommandListener extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        Guild guild = event.getGuild();
        User sender = event.getUser();

        switch (event.getName()) {
            case "ping": case "arbping": {
                EmbedBuilder b = new EmbedBuilder();
                b.setDescription("Ki, Ohpe, WAKAY!");
                event.replyEmbeds(b.build()).setEphemeral(false).queue();
                break;
            }
            case "server": case "arbserver": {
                ServerObject so = DBConnector.loadServer(guild.getId());

                EmbedBuilder b = new EmbedBuilder();
                b.setDescription(guild.getName());
                b.setThumbnail(guild.getIconUrl());

                StringBuilder sb = new StringBuilder();
                sb.append(so.isCaptureDuplicateSlave() ? "Duplicate slaves permitted" : "Unique slaves only");

                b.addField("Server settings", sb.toString(), false);

                event.replyEmbeds(b.build()).setEphemeral(false).queue();
                break;
            }
            case "profile": case "arbprofile": {
                User target = sender;
                if(event.getOption("user") != null) target = event.getOption("user").getAsUser();

                Player p = DBConnector.getPlayer(guild.getId(), target.getId());
                EmbedBuilder b = new EmbedBuilder();
                b.setThumbnail(target.getAvatarUrl())
                        .setDescription(target.getAsMention() + "'s profile")
                        .addField("Health", String.format(":heart: %d / %d", p.getCurHP(), p.getMaxHP()), true)
                        .addField("Balance", ":heavy_dollar_sign:" + p.getBal(), true);

                StringBuilder sb = new StringBuilder();
                for (String t : p.getTraits()) {
                    sb.append(t).append(",");
                }
                if(sb.length() <= 0)
                    sb.append("None");

                b.addField("Traits", sb.toString(), false);

                sb = new StringBuilder();
                sb.append("**ATK**: ").append(p.getAtk()).append("\n")
                        .append("**DEF**: ").append(p.getDef()).append("\n")
                        .append("**DEX**: ").append(p.getDex()).append("\n")
                        .append("**AGI**: ").append(p.getAgi()).append("\n")
                        .append("**INT**: ").append(p.getIntel());
                b.addField("Stats", sb.toString(), false);


                event.replyEmbeds(b.build()).setEphemeral(false).queue();
                break;
            }
            case "daily": case "arbdaily": {
                Player p = DBConnector.getPlayer(guild.getId(), sender.getId());
                if(Utils.checkTimerRatelimit(p, TimerConstants.DAILY)) {
                    Utils.executeAction(p, TimerConstants.DAILY, TimerConstants.DAY);
                    p.addMoney(GameParameters.DAILY_MONEY);

                    DBConnector.savePlayer(p);

                    EmbedBuilder b = new EmbedBuilder();
                    b.setDescription(String.format("You have claimed your daily reward of $%d. You now have $%d.", GameParameters.DAILY_MONEY, p.getBal()));
                    b.setColor(Color.green);
                    event.replyEmbeds(b.build()).setEphemeral(false).queue();
                }
                else {
                    event.replyEmbeds(generateRateLimitMsg(p, TimerConstants.DAILY)).setEphemeral(false).queue();
                }
                break;
            }
            case "weekly": case "arbweekly": {
                Player p = DBConnector.getPlayer(guild.getId(), sender.getId());
                if(Utils.checkTimerRatelimit(p, TimerConstants.WEEKLY)) {
                    Utils.executeAction(p, TimerConstants.WEEKLY, TimerConstants.WEEK);
                    p.addMoney(GameParameters.WEEKLY_MONEY);

                    DBConnector.savePlayer(p);

                    EmbedBuilder b = new EmbedBuilder();
                    b.setDescription(String.format("You have claimed your weekly reward of $%d. You now have $%d.", GameParameters.WEEKLY_MONEY, p.getBal()));
                    b.setColor(Color.green);
                    event.replyEmbeds(b.build()).setEphemeral(false).queue();
                }
                else {
                    event.replyEmbeds(generateRateLimitMsg(p, TimerConstants.WEEKLY)).setEphemeral(false).queue();
                }
                break;
            }
            case "attack": case "arbattack": {
                break;
            }
            case "shop": case "arbshop": {
                if(event.getOption("category") == null) {
                    EmbedBuilder b = new EmbedBuilder();
                    b.setDescription("Please specify a category.\nAvailable shops are: Weapon, Shield, and Item");
                    b.setColor(Color.RED);
                    event.replyEmbeds(b.build()).setEphemeral(false).queue();
                    break;
                }
                else {
                    String category = event.getOption("category").getAsString().toLowerCase();
                    int page = 1;
                    if(event.getOption("page") != null) page = event.getOption("page").getAsInt();

                    switch (category) {
                        case "weapon": {
                            MessageEmbed[] pages = Shop.generateWeaponShop();
                            if(page <= 0 || page > pages.length) {
                                event.replyEmbeds(generateErrorMsg("Page not found. Page number must be between 1 and " + pages.length)).setEphemeral(false).queue();
                                break;
                            }
                            else {
                                event.replyEmbeds(pages[page - 1]).setEphemeral(false).queue();
                            }
                            break;
                        }
                        default: {
                            EmbedBuilder b = new EmbedBuilder();
                            b.setDescription("Category not found.\nAvailable shops are: Weapon, Shield, and Item");
                            b.setColor(Color.RED);
                            event.replyEmbeds(b.build()).setEphemeral(false).queue();
                            break;
                        }
                    }
                }

                break;
            }
            case "money": case "balance": case "arbmoney": case "arbbalance": {
                User target = sender;
                if(event.getOption("user") != null) target = event.getOption("user").getAsUser();

                Player p = DBConnector.getPlayer(guild.getId(), target.getId());
                EmbedBuilder b = new EmbedBuilder();
                b.setDescription(target.getAsMention() + "'s finances");
                b.addField("Wallet", ":heavy_dollar_sign: " + p.getBal(), false);
                b.addField("Bank", ":heavy_dollar_sign: " + p.getBankBal() + "/" + GameParameters.BANK_LIMITS[p.getBankLvl()] +
                        "\nBank level: " + p.getBankLvl(), false);

                event.replyEmbeds(b.build()).setEphemeral(false).queue();
                break;
            }
            case "deposit": case "arbdeposit": {
                if(event.getOption("amount") == null) {
                    event.replyEmbeds(generateErrorMsg("Please specify an amount to deposit.")).setEphemeral(false).queue();
                    break;
                }
                int amount = event.getOption("amount").getAsInt();
                Player p = DBConnector.getPlayer(guild.getId(), sender.getId());

                if(amount <= 0) {
                    event.replyEmbeds(generateErrorMsg("Please specify a positive integer to deposit.")).setEphemeral(false).queue();
                    break;
                }
                else if(amount > p.getBal()) {
                    event.replyEmbeds(generateErrorMsg("You don't have that much money!")).setEphemeral(false).queue();
                    break;
                }
                else if(p.getBankBal() == GameParameters.BANK_LIMITS[p.getBankLvl()]) {
                    event.replyEmbeds(generateErrorMsg("Your bank is full! Use `/bank upgrade` to upgrade.")).setEphemeral(false).queue();
                    break;
                }

                int bankLimit = GameParameters.BANK_LIMITS[p.getBankLvl()];
                if(p.getBankBal() + amount > bankLimit) {
                    int difference = p.getBankBal() + amount - bankLimit;
                    p.addBankBal(difference);
                    p.addMoney(-difference);
                    DBConnector.savePlayer(p);
                    EmbedBuilder b = new EmbedBuilder();
                    b.setDescription("Deposited $" + amount + " to your bank. You bank is now full.");
                    b.setColor(Color.GREEN);
                    event.replyEmbeds(b.build()).setEphemeral(false).queue();
                }
                else {
                    p.addBankBal(amount);
                    p.addMoney(-amount);
                    DBConnector.savePlayer(p);
                    EmbedBuilder b = new EmbedBuilder();
                    b.setColor(Color.GREEN);
                    b.setDescription("Successfully deposited $" + amount + " into your bank. Your bank balance is now $" + p.getBankBal());
                    event.replyEmbeds(b.build()).setEphemeral(false).queue();
                }


                break;
            }
            case "withdraw": case "arbwithdraw": {
                if(event.getOption("amount") == null) {
                    event.replyEmbeds(generateErrorMsg("Please specify an amount to withdraw.")).setEphemeral(false).queue();
                    break;
                }
                int amount = event.getOption("amount").getAsInt();
                Player p = DBConnector.getPlayer(guild.getId(), sender.getId());

                if(amount <= 0) {
                    event.replyEmbeds(generateErrorMsg("Please specify a positive integer to withdraw.")).setEphemeral(false).queue();
                    break;
                }
                else if(amount > p.getBankBal()) {
                    event.replyEmbeds(generateErrorMsg("You don't have that much money in your bank!")).setEphemeral(false).queue();
                    break;
                }

                p.addBankBal(-amount);
                p.addMoney(amount);
                DBConnector.savePlayer(p);
                EmbedBuilder b = new EmbedBuilder();
                b.setColor(Color.GREEN);
                b.setDescription("Successfully withdrew $" + amount + " from your bank. Your wallet balance is now $" + p.getBal());
                event.replyEmbeds(b.build()).setEphemeral(false).queue();

                break;
            }
            case "buy": case "arbbuy": {

            }
        }
    }

    public MessageEmbed generateErrorMsg(String msg) {
        EmbedBuilder b = new EmbedBuilder();
        b.setDescription(msg);
        b.setColor(Color.RED);

        return b.build();
    }

    public MessageEmbed generateRateLimitMsg(Player p, String action){
        EmbedBuilder b = new EmbedBuilder();
        b.setDescription(":clock2: Action is on cooldown. Remaining time: " +
                millisToDateTime((p.getLastActionTime().get(action) + p.getActionCooldown().get(action)) - System.currentTimeMillis()) + ".");
        b.setColor(Color.MAGENTA);

        return b.build();
    }

    public String millisToDateTime(long millis) {
        String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1));
        return hms;
    }
}
