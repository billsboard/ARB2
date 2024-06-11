package ca.billweb.arb;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class Shop {
    public static final Weapon WEAPONS[] = {
            new Weapon("Stick", "Quite sticky", 100, 25, 1.0, 0.95),
            new Weapon("Slipper", "9/10 Asian parents recommend", 150, 32, 1.0, 1),
            new Weapon("Bofadesium Dagger", "Also known as \"Short Sword\". High critical ratio.", 225, 55, 1.1, 0.9, "crit_up_1"),
            new Weapon("Sword", "Very good for long range engagements", 225, 70, 1, 1),
            new Weapon("Perfume", "Hm.", 450, 90, 1.3, 0.8, "air")
    };



    public static MessageEmbed[] generateWeaponShop() {

        MessageEmbed[] out = new MessageEmbed[(WEAPONS.length + 9) / 10];

        int i = 0, p = 0;
        EmbedBuilder b = new EmbedBuilder();
        b.setDescription("Weapons (Page 1/" + out.length +"). Use `/buy [item]` to buy the item");
        b.setFooter("Use /item [item] to see more information on the item.");
        for(; i < WEAPONS.length; i++){
            Weapon w = WEAPONS[i];
            b.addField(w.getName() + " – $" + w.getCost(), w.getDescription(), false);

            if(i % 10 == 9) {
                out[p] = b.build();
                p++;
                b = new EmbedBuilder();
                b.setDescription("Weapons (Page " + (p + 1) + "/" + out.length +") Use `/buy [item]` to buy the item");
                b.setFooter("Use /item [item] to see more information on the item.");
            }
        }
        out[p] = b.build();
        return out;
    }
}
