package com.happyzleaf.pixelrus.pokerus;

import com.happyzleaf.pixelrus.Pixelrus;
import com.happyzleaf.pixelrus.Utility;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import com.pixelmonmod.pixelmon.storage.PlayerStorage;
import net.minecraft.world.World;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.InteractBlockEvent;
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
							PokerusHelper.progress((Entity) p);
						}
					}
				}
			}
		}).submit(Pixelrus.instance);
	}
	
	private static final BlockType BLOCK_HEALER = Sponge.getGame().getRegistry().getType(BlockType.class, "pixelmon:healer").orElseThrow(() -> new RuntimeException("BlockHealer cannot be found in the registry. Wait what..."));
	
	@Listener
	public void onInteractBlockSecondary(InteractBlockEvent.Secondary event) { //Waiting for HealerEvent, may be added in Pixelmon 6.3
		//I have literally no clue if this event works, i'm just guessing it may be the right event but it needs testing
		if (event.getSource() instanceof Player && event.getTargetBlock().getState().getType().equals(BLOCK_HEALER) && event.getUseBlockResult().asBoolean()) {
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
