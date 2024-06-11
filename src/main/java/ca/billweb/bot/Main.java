package ca.billweb.bot;

import ca.billweb.commands.CommandList;
import ca.billweb.db.DBConnector;
import ca.billweb.listeners.OtherEventListener;
import ca.billweb.listeners.SlashCommandListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

public class Main {
    public static void main(String[] args) {
        String botToken = System.getenv("ARB_TOKEN");

        JDABuilder builder = JDABuilder.createDefault(botToken);

        // Disable parts of the cache
        builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE)
                .addEventListeners(new SlashCommandListener(), new OtherEventListener())
                .setBulkDeleteSplittingEnabled(false)
                .setActivity(Activity.playing("Ki"));

        /*for (int i = 0; i < 10; i++) {
            JDA jda = builder.useSharding(i, 10).build();
            jda.updateCommands().addCommands(CommandList.commands).addCommands(CommandList.adminCommands).queue();
        }*/
        // ^^^ Sharding if bot gets big

        JDA jda = builder.build();
        jda.updateCommands().addCommands(CommandList.slashCommands).queue();

        System.out.println("Connected to Discord");

        DBConnector.initConnection();
    }
}
