package com.happyzleaf.pixelrus.pokerus;

import com.happyzleaf.pixelrus.data.HasPokerusData;
import com.happyzleaf.pixelrus.data.InfectedData;
import com.happyzleaf.pixelrus.data.PixelrusKeys;
import com.happyzleaf.pixelrus.data.VirusTypeData;
import com.pixelmonmod.pixelmon.api.dialogue.Dialogue;
import net.minecraft.entity.player.EntityPlayerMP;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;

import java.util.ArrayList;

public class PokerusHelper {
	private static ArrayList<Dialogue> elm, nurse, healer;
	
	static  {
		elm = new ArrayList<>();
		elm.add(new Dialogue.DialogueBuilder().setName("Elm").setText("Hello, <player>?").build());
		elm.add(new Dialogue.DialogueBuilder().setName("Elm").setText("I discovered an odd thing.").build());
		elm.add(new Dialogue.DialogueBuilder().setName("Elm").setText("Apparently there's something called Pokérus that infects Pokémon.").build());
		elm.add(new Dialogue.DialogueBuilder().setName("Elm").setText("Yes, it's like a virus, so it's called Pokérus.").build());
		elm.add(new Dialogue.DialogueBuilder().setName("Elm").setText("It multiplies fast and infects other Pokémon too. But that's all.").build());
		elm.add(new Dialogue.DialogueBuilder().setName("Elm").setText("It doesn't seem to do anything, and it goes away over time.").build());
		elm.add(new Dialogue.DialogueBuilder().setName("Elm").setText("I guess it's nothing to worry about. Bye!").build());
		
		nurse = new ArrayList<>();
		nurse.add(new Dialogue.DialogueBuilder().setName("Nurse").setText("Your Pokémon may be infected with Pokérus.").build());
		nurse.add(new Dialogue.DialogueBuilder().setName("Nurse").setText("Little is known about the Pokérus except that they are microscopic life-forms that attach to Pokémon.").build());
		nurse.add(new Dialogue.DialogueBuilder().setName("Nurse").setText("While infected, Pokémon are said to grow exceptionally well.").build());
		
		healer = new ArrayList<>();
		healer.add(new Dialogue.DialogueBuilder().setName("Healer").setText("Blip blop. Your Pokémon may be infected with Pokérus.").build());
		healer.add(new Dialogue.DialogueBuilder().setName("Healer").setText("Little is known about the Pokérus except that they are microscopic life-forms that attach to Pokémon.").build());
		healer.add(new Dialogue.DialogueBuilder().setName("Healer").setText("While infected, Pokémon are said to grow exceptionally well. Blooop.").build());
	}
	
	public static boolean hasPokerus(Entity entity) {
		return entity.get(PixelrusKeys.HAS_POKERUS).isPresent();
	}
	
	public static boolean isInfected(Entity entity) {
		return entity.get(PixelrusKeys.INFECTED).isPresent();
	}
	
	public static void infect(Entity entity) {
		entity.offer(entity.getOrCreate(HasPokerusData.class).get());
		entity.offer(entity.getOrCreate(VirusTypeData.class).get());
		entity.offer(entity.getOrCreate(InfectedData.class).get());
	}
	
	public static void announce(Player player, Entity entity) {
		Dialogue.setPlayerDialogueData((EntityPlayerMP) player, healer, true);
		entity.offer(PixelrusKeys.HAS_POKERUS, true);
	}
	
	public static void increaseVirus(Entity entity) {
		int updated = entity.get(PixelrusKeys.INFECTED).get() + 1;
		if (updated >= VirusTypes.values()[entity.get(PixelrusKeys.VIRUS_TYPE).get()].minutes) {
			cure(entity);
		} else {
			entity.offer(PixelrusKeys.INFECTED, updated);
		}
	}
	
	public static void cure(Entity entity) {
		entity.remove(PixelrusKeys.VIRUS_TYPE); //Is this supposed to be removed?
		entity.remove(PixelrusKeys.INFECTED);
	}
}
