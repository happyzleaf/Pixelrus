package com.happyzleaf.pixelrus.pokerus;

import com.happyzleaf.pixelrus.Pixelrus;
import com.happyzleaf.pixelrus.Utility;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.blocks.tileEntities.TileEntityHealer;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import com.pixelmonmod.pixelmon.storage.PlayerStorage;
import net.minecraft.world.World;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.tileentity.TargetTileEntityEvent;
import org.spongepowered.api.scheduler.Task;

import java.util.concurrent.TimeUnit;

public class PokerusListener {
	public static void init() {
		Pixelmon.EVENT_BUS.register(new PokerusListener());
		Task.builder().interval(1, TimeUnit.MINUTES).execute(() -> {
			for (Player player : Sponge.getServer().getOnlinePlayers()) {
				PlayerStorage s = Utility.getStorage(player);
				World w = (World) player.getWorld();
				for (int i = 0; i < 6; i++) {
					EntityPixelmon p = s.getPokemon(s.getIDFromPosition(i), w);
					if (p != null) {
						if (PokerusHelper.isInfected((Entity) p)) {
							PokerusHelper.increaseVirus((Entity) p);
						}
					}
				}
			}
		}).submit(Pixelrus.instance);
	}
	
	@Listener
	public void onTargetTileEntity(TargetTileEntityEvent event) { //Waiting for HealerEvent, may be added in Pixelmon 6.3
		//I have literally no clue if this event works, i'm just guessing it may be the right event but it needs testing
		if (event.getSource() instanceof Player && event.getTargetTile() instanceof TileEntityHealer) {
			Player player = (Player) event.getSource();
			PlayerStorage s = Utility.getStorage(player);
			World w = (World) player.getWorld();
			for (int i = 0; i < 6; i++) {
				EntityPixelmon p = s.getPokemon(s.getIDFromPosition(i), w);
				if (p != null) {
					if (PokerusHelper.hasPokerus((Entity) p)) {
						PokerusHelper.announce(player, (Entity) p);
						return; //Let's announce one pokémon per time.
					}
				}
			}
		}
	}
}
