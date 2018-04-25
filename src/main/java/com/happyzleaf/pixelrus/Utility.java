package com.happyzleaf.pixelrus;

import com.pixelmonmod.pixelmon.storage.PixelmonStorage;
import com.pixelmonmod.pixelmon.storage.PlayerStorage;
import net.minecraft.entity.player.EntityPlayerMP;
import org.spongepowered.api.entity.living.player.Player;

public class Utility {
	public static PlayerStorage getStorage(Player player) {
		return PixelmonStorage.pokeBallManager.getPlayerStorage((EntityPlayerMP) player).orElseThrow(() -> new RuntimeException("Cannot find " + player.getName() + " storage."));
	}
}
