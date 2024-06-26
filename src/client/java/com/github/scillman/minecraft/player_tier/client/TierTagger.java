package com.github.scillman.minecraft.player_tier.client;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.text.Text;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.util.Formatting;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;

import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class TierTagger
{
    /**
     * All the different available tiers.
     */
    public enum Tier
    {
        HT0 ("HT0"),
        HT1 ("HT1"),
        HT3 ("HT3"),
        HT4 ("HT4"),
        HT5 ("HT5"),
        LT1 ("LT1"),
        LT2 ("LT2"),
        LT3 ("LT3"),
        LT4 ("LT4"),
        LT5 ("LT5"),
        RHT1 ("RHT1"),
        RHT2 ("RHT2"),
        RLT1 ("RLT1"),
        RLT2 ("RLT2"),

        /**
         * Default tier when none of the above is applied.
         */
        DEFAULT ("DEFAULT");

        private final String tier;

        private Tier(String tier)
        {
            this.tier = tier;
        }

        public boolean equalsName(String other)
        {
            return this.tier.equals(other);
        }

        public String toString()
        {
            return this.tier;
        }
    }

    /**
     * A map with all the players and their respective tiers.
     */
    private static final HashMap<String, Tier> tierMembers = new HashMap<String, Tier>();

    /**
     * A list of members that have a star.
     * @remark Are these moderators?!
     */
    private static final ArrayList<String> starMembers = new ArrayList<String>();

    public TierTagger()
    {
    }

    /**
     * Initialize the tier data structures.
     */
    public static void initialize()
    {
        tierMembers.clear();
        tierMembers.put("snarling", Tier.LT3);
        tierMembers.put("LeKiko", Tier.RLT1);

        starMembers.clear();
        starMembers.add("Ezied");
        starMembers.add("Witherred");
        starMembers.add("TheNightAgent");
        starMembers.add("Intallact");
        starMembers.add("qRapid");
        starMembers.add("Artzuma");
        starMembers.add("7959");
    }

    /**
     * Appends the player's tier before the text.
     * @param player The player whoms tier to add.
     * @param text The text before which the tier has to be added.
     * @return The text with the player's tier added in front of it.
     */
    public static Text appendTier(PlayerEntity player, Text text)
    {
        String playerName = player.getName().getString();

        @Nullable MutableText tier = getPlayerTier(playerName);
        if (tier == null)
        {
            return text;
        }

        if (starMembers.contains(playerName))
        {
            tier = Text.literal("★ ").append(tier);
        }

        return tier.append(Text.literal(" | ").setStyle(Style.EMPTY.withColor(Formatting.GRAY))).append(text);
    }

    /**
     * 
     * @param playerName
     * @return
     */
    private static @Nullable MutableText getPlayerTier(String playerName)
    {
        if (tierMembers.containsKey(playerName))
        {
            Tier tier = tierMembers.get(playerName);
            String strTier = tier.toString();
            
            int color = getTierColor(tier);

            MutableText suffix = Text.literal(strTier);
            suffix.setStyle(Style.EMPTY.withColor(color));
            return suffix;
        }

        return null;
    }

    /**
     * Get RGB color code.
     * @param r Red
     * @param g Green
     * @param b Blue
     * @return RGB as an Integer.
     */
    private static int rgb(int r, int g, int b)
    {
        return (r << 16) | (g << 8) | b;
    }

    /**
     * Get the color coding for the given tier.
     * @param tier The tier whoms color coding to get.
     * @return The color coding for the given tier.
     */
    private static int getTierColor(Tier tier)
    {
        switch (tier)
        {
            case Tier.RHT2: return rgb(255, 165, 0); // 16753920
            case Tier.HT3:  return rgb(218, 165, 32); // 14329120
            case Tier.LT3:
            case Tier.RLT1: return rgb(238, 232, 170); // 15657130
            case Tier.LT1:  return rgb(255, 182, 193); // 16758465
            case Tier.HT1:
            case Tier.RHT1: return rgb(255, 0, 0); // 16711680
            case Tier.LT2:
            case Tier.RLT2: return rgb(255, 228, 181); // 16770229
            case Tier.HT0:  return rgb(173, 216, 230); // 11393254
            case Tier.HT4:  return rgb(  0, 100,   0); // 25600
            case Tier.LT4:  return rgb(144, 238, 144); // 9498256
            case Tier.LT5:  return rgb(211, 211, 211); // 13882323
            case Tier.HT5:  return rgb(128, 128, 128); // 8421504

            default:
                assert(tier == Tier.DEFAULT);
                return rgb(255, 255, 255);
        }
    }

    // /**
    //  * Get the text formatting to use for the given tier.
    //  * @param tier The tier to get the formatting for.
    //  * @return The formatting of the given tier.
    //  */
    // private static Formatting getFormattingForTier(Tier tier)
    // {
    //     switch (tier)
    //     {
    //         case Tier.HT3:
    //         case Tier.HT4:
    //         case Tier.HT5:
    //         case Tier.LT2:
    //         case Tier.LT3:
    //         case Tier.LT4:
    //         case Tier.LT5:
    //         case Tier.RHT1:
    //         case Tier.RHT2:
    //         case Tier.RLT1:
    //         case Tier.RLT2:
    //             return Formatting.GRAY;

    //         case Tier.HT0:
    //             return Formatting.YELLOW;

    //         case Tier.HT1:
    //         case Tier.LT1:
    //             return Formatting.RED;

    //         default:
    //             assert(tier == Tier.DEFAULT);
    //             return Formatting.WHITE;
    //     }
    // }

    // /**
    //  * Convert the given string tier name to a Tier enumeration value.
    //  * @param str The string value.
    //  * @return The enumaration value of the given string.
    //  */
    // private static Tier stringToTier(String str)
    // {
    //     switch (str)
    //     {
    //         case "RHT2":
    //         case "RLT2":
    //         case "HT3":
    //         case "RLT1":
    //         case "RHT1":
    //         case "LT2":
    //         case "LT3":
    //         case "HT4":
    //         case "LT4":
    //         case "LT5":
    //         case "HT5":
    //         case "HT0":
    //         case "HT1":
    //         case "LT1":
    //             return Tier.valueOf(str);

    //         default:
    //             return Tier.DEFAULT;
    //     }
    // }
}
