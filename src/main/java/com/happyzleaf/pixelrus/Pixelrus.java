package com.happyzleaf.pixelrus;

import com.happyzleaf.pixelrus.data.HasPixelmonData;
import com.happyzleaf.pixelrus.data.PixelrusKeys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.data.DataTransactionResult;
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

@Plugin(id = Pixelrus.PLUGIN_ID, name = Pixelrus.PLUGIN_NAME, description = "Pixelrus adds the features of Pokerus to Pixelmon!",
		url = "http://happyzleaf.com/", authors = {"happyzleaf"}, dependencies = {@Dependency(id = "pixelmon")})
public class Pixelrus {
	public static final String PLUGIN_ID = "pixelrus";
	public static final String PLUGIN_NAME = "Pixelrus";
	
	public static final Logger LOGGER = LoggerFactory.getLogger(PLUGIN_NAME);
	
	public static PluginContainer container;
	
	@Listener
	public void preInit(GamePreInitializationEvent event) {
		container = Sponge.getPluginManager().getPlugin(Pixelrus.PLUGIN_ID).get();
		
		LOGGER.info("Registering keys.");
		PixelrusKeys.register();
	}
	
	@Listener
	public void postInit(GamePostInitializationEvent event) {
		/*CommandSpec write = CommandSpec.builder()
				//.arguments(GenericArguments.integer(Text.of("slot")))
				.executor((src, args) -> {
					if (!(src instanceof Player)) {
						throw new CommandException(Text.of(TextColors.RED, "You must be a player to run this command."));
					}
					
					/*int slot = (int) args.getOne("slot").get() - 1;
					if (slot < 0 || slot >= 6){
						throw new CommandException(Text.of(TextColors.RED, "Slot must be within 1 and 6."));
					}
					
					PlayerStorage s = Utility.getStorage((Player) src);
					Entity e = (Entity) s.getPokemon(s.getIDFromPosition(slot), (World) ((Player) src).getWorld());*/
					/*Entity e = (Entity) src;
					DataTransactionResult.Type result = e.offer(PixelrusKeys.HAS_POKERUS, false).getType();
					if (result == DataTransactionResult.Type.SUCCESS) {
						src.sendMessage(Text.of(TextColors.GREEN, "Successfully wrote keys to entity."));
					} else {
						src.sendMessage(Text.of(TextColors.RED, result.name()));
					}
					//e.offer(PixelrusKeys.INFECTED, 0);
					
					return CommandResult.success();
				})
				.build();
		
		CommandSpec read = CommandSpec.builder()
				//.arguments(GenericArguments.integer(Text.of("slot")))
				.executor((src, args) -> {
					if (!(src instanceof Player)) {
						throw new CommandException(Text.of(TextColors.RED, "You must be a player to run this command."));
					}
					
					/*int slot = (int) args.getOne("slot").get() - 1;
					if (slot < 0 || slot >= 6){
						throw new CommandException(Text.of(TextColors.RED, "Slot must be within 1 and 6."));
					}
					
					PlayerStorage s = Utility.getStorage((Player) src);
					Entity e = (Entity) s.getPokemon(s.getIDFromPosition(slot), (World) ((Player) src).getWorld());*/
					/*Entity e = (Entity) src;
					boolean announced = e.get(PixelrusKeys.HAS_POKERUS).get();
					//int timePassed = e.get(PixelrusKeys.INFECTED).get();
					src.sendMessage(Text.of(TextColors.DARK_GREEN, "HAS_POKERUS (announced): " + announced));
					//src.sendMessage(Text.of(TextColors.DARK_GREEN, "INFECTED (timePassed): " + timePassed));
					
					return CommandResult.success();
				})
				.build();
		
		CommandSpec remove = CommandSpec.builder()
				//.arguments(GenericArguments.integer(Text.of("slot")))
				.executor((src, args) -> {
					if (!(src instanceof Player)) {
						throw new CommandException(Text.of(TextColors.RED, "You must be a player to run this command."));
					}
					
					/*int slot = (int) args.getOne("slot").get() - 1;
					if (slot < 0 || slot >= 6){
						throw new CommandException(Text.of(TextColors.RED, "Slot must be within 1 and 6."));
					}
					
					PlayerStorage s = Utility.getStorage((Player) src);
					Entity e = (Entity) s.getPokemon(s.getIDFromPosition(slot), (World) ((Player) src).getWorld());*/
					/*Entity e = (Entity) src;
					e.remove(PixelrusKeys.HAS_POKERUS);
					//e.remove(PixelrusKeys.INFECTED);
					src.sendMessage(Text.of(TextColors.GREEN, "Successfully removed keys to entity."));
					
					return CommandResult.success();
				})
				.build();*/
		
		CommandSpec main = CommandSpec.builder()
				//.child(write, "write")
				//.child(read, "read")
				//.child(remove, "remove")
				.arguments(GenericArguments.string(Text.of("choice")))
				.executor((src, args) -> {
					Entity e = (Entity) src;
					
					switch ((String) args.getOne("choice").get()) {
						case "infect":
							PixelrusKeys.infect(e);
							break;
						case "announce":
							PixelrusKeys.announce((Player) src, e);
							break;
						case "timePassed":
							PixelrusKeys.timePassed(e);
							break;
						case "cure":
							PixelrusKeys.cure(e);
							break;
						case "forceRemove":
							PixelrusKeys.forceRemove(e);
							break;
						case "read":
							src.sendMessage(Text.of("HAS_POKERUS = " + (e.get(PixelrusKeys.HAS_POKERUS).isPresent() ? e.get(PixelrusKeys.HAS_POKERUS).get() : "null")));
							src.sendMessage(Text.of("INFECTED = " + (e.get(PixelrusKeys.INFECTED).isPresent() ? e.get(PixelrusKeys.INFECTED).get() : "null")));
							break;
						default:
							src.sendMessage(Text.of("nothing"));
					}
					
					return CommandResult.success();
				})
				.build();
		Sponge.getCommandManager().register(this, main, "pixelrus", "pokerus");
	}
}
