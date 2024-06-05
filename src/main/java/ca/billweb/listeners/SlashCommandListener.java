package ca.billweb.listeners;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SlashCommandListener extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        switch (event.getName()) {
            case "ping": case "arbping": {
                EmbedBuilder b = new EmbedBuilder();
                b.setDescription("Ki, Ohpe, WAKAY!");
                event.replyEmbeds(b.build()).setEphemeral(false).queue();
                break;
            }

        }
    }
}
