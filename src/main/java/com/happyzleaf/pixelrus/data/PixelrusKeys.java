package com.happyzleaf.pixelrus.data;

import com.happyzleaf.pixelrus.Pixelrus;
import com.happyzleaf.pixelrus.data.impl.HasPixelmonDataImpl;
import com.happyzleaf.pixelrus.data.impl.InfectedDataImpl;
import com.pixelmonmod.pixelmon.api.dialogue.Dialogue;
import net.minecraft.entity.player.EntityPlayerMP;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.DataRegistration;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.util.TypeTokens;
import org.spongepowered.api.util.generator.dummy.DummyObjectProvider;

import java.util.ArrayList;

public class PixelrusKeys {
	private static ArrayList<Dialogue> dialogue = new ArrayList<>();
	
	static {
		dialogue.add(new Dialogue.DialogueBuilder().setName("Joyce").setText("Oh, looks like your pixelmon got AIDS!").build());
	}
	
	/**
	 * True if the pokerus has been announced when the player used a healer
	 */
	public static Key<Value<Boolean>> HAS_POKERUS = DummyObjectProvider.createExtendedFor(Key.class, "HAS_POKERUS");
	
	/**
	 * Returns how many minutes have passed since the pixelmon was infected.
	 */
	public static Key<Value<Integer>> INFECTED = DummyObjectProvider.createExtendedFor(Key.class, "INFECTED");
	
	public static void register() {
		HAS_POKERUS = Key.builder()
				.type(TypeTokens.BOOLEAN_VALUE_TOKEN)
				.id(Pixelrus.PLUGIN_ID + ":has_pokerus")
				.name("Has Pokerus")
				.query(DataQuery.of("has_pokerus"))
				.build();
		INFECTED = Key.builder()
				.type(TypeTokens.INTEGER_VALUE_TOKEN)
				.id(Pixelrus.PLUGIN_ID + ":infected")
				.name("Infected")
				.query(DataQuery.of("infected"))
				.build();
		
		DataRegistration.builder()
				.dataName("Has Pixelmon")
				.manipulatorId("has_pixelmon")
				.dataClass(HasPixelmonData.class)
				.dataImplementation(HasPixelmonDataImpl.class)
				.immutableClass(HasPixelmonData.Immutable.class)
				.immutableImplementation(HasPixelmonDataImpl.Immutable.class)
				.builder(new HasPixelmonDataImpl.Builder())
				.buildAndRegister(Pixelrus.container);
		
		DataRegistration.builder()
				.dataName("Infected")
				.manipulatorId("infected")
				.dataClass(InfectedData.class)
				.dataImplementation(InfectedDataImpl.class)
				.immutableClass(InfectedData.Immutable.class)
				.immutableImplementation(InfectedDataImpl.Immutable.class)
				.builder(new InfectedDataImpl.Builder())
				.buildAndRegister(Pixelrus.container);
	}
	
	//JUST SOME TEST UTILITIES
	public static void infect(Entity entity) {
		entity.offer(entity.getOrCreate(HasPixelmonData.class).get());
		entity.offer(entity.getOrCreate(InfectedData.class).get());
	}
	
	public static void announce(Player player, Entity entity) {
		entity.offer(HAS_POKERUS, true);
		Dialogue.setPlayerDialogueData((EntityPlayerMP) player, dialogue, true);
	}
	
	public static void timePassed(Entity entity) {
		entity.offer(INFECTED, entity.get(INFECTED).get() + 1);
	}
	
	public static void cure(Entity entity) {
		entity.remove(INFECTED);
	}
	
	public static void forceRemove(Entity entity) {
		entity.remove(INFECTED);
		cure(entity);
	}
}
