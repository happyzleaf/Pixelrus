package com.happyzleaf.pixelrus;

import com.happyzleaf.pixelrus.data.PixelrusKeys;
import com.happyzleaf.pixelrus.pokerus.PokerusHelper;
import com.happyzleaf.pixelrus.pokerus.PokerusListener;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import com.pixelmonmod.pixelmon.storage.PlayerStorage;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GamePostInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.Random;

@Plugin(id = Pixelrus.PLUGIN_ID, name = Pixelrus.PLUGIN_NAME, version = Pixelrus.VERSION, description = "Pixelrus adds the features of Pokerus to Pixelmon!",
		url = "http://happyzleaf.com/", authors = {"happyzleaf"}, dependencies = {@Dependency(id = "pixelmon")})
public class Pixelrus {
	public static final String PLUGIN_ID = "pixelrus";
	public static final String PLUGIN_NAME = "Pixelrus";
	public static final String VERSION = "0.0.1-BETA";
	
	public static final Logger LOGGER = LoggerFactory.getLogger(PLUGIN_NAME);
	
	public static final Random rnd = new Random();
	
	public static PluginContainer container;
	public static Pixelrus instance;
	
	@Listener
	public void preInit(GamePreInitializationEvent event) {
		container = Sponge.getPluginManager().getPlugin(Pixelrus.PLUGIN_ID).get();
		instance = this;
		
		PixelrusKeys.register();
		
		PokerusListener.init();
		
		LOGGER.info("Pixelrus v" + VERSION + " loaded!");
	}
	
	@Listener
	public void postInit(GamePostInitializationEvent event) {
		CommandSpec give = CommandSpec.builder()
				.arguments(GenericArguments.integer(Text.of("slot")))
				.executor((src, args) -> {
					if (!(src instanceof Player)) {
						throw new CommandException(Text.of(TextColors.RED, "You must be a player to run this command."));
					}
					
					int slot = (int) args.getOne("slot").get() - 1;
					if (slot < 0 || slot >= 6){
						throw new CommandException(Text.of(TextColors.RED, "Slot must be within 1 and 6."));
					}
					
					PlayerStorage s = Utility.getStorage((Player) src);
					EntityPixelmon pixelmon = s.getPokemon(s.getIDFromPosition(slot), (World) ((Player) src).getWorld());
					PokerusHelper.infect((Entity) pixelmon);
					src.sendMessage(Text.of(TextColors.GREEN, "Successfully gave pixelrus to " + pixelmon.getName() + "."));
					
					return CommandResult.success();
				})
				.build();
		CommandSpec pixelrus = CommandSpec.builder()
				.child(give, "give")
				/*.executor((src, args) -> {
					src.sendMessage(Text.of("HAS_POKERUS = " + (e.get(PixelrusKeys.HAS_POKERUS).isPresent() ? e.get(PixelrusKeys.HAS_POKERUS).get() : "null")));
					src.sendMessage(Text.of("INFECTED = " + (e.get(PixelrusKeys.INFECTED).isPresent() ? e.get(PixelrusKeys.INFECTED).get() : "null")));
					return CommandResult.success();
				})*/
				.build();
		Sponge.getCommandManager().register(this, pixelrus, "pixelrus", "pokerus");
	}
}
