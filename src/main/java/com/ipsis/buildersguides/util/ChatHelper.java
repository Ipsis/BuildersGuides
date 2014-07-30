package com.ipsis.buildersguides.util;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.util.ForgeDirection;

public class ChatHelper {

    public static void displayTargetMessage(EntityPlayer player, BlockPosition p, ForgeDirection d, boolean target) {

        if (player != null) {
            String s;
            if (target) s = "Target "; else s = "Block ";
            player.addChatComponentMessage(new ChatComponentText(s + d + " : " + "(" + p.x + ", " + p.y + ", " + p.z + ")"));
        }
    }

    public static void displaySimpleMessage(EntityPlayer player, String s) {

        if (player != null)
            player.addChatComponentMessage(new ChatComponentText(s));
    }
}
