package com.happyzleaf.pixelrus.data;

import com.happyzleaf.pixelrus.Pixelrus;
import com.happyzleaf.pixelrus.data.impl.HasPokerusDataImpl;
import com.happyzleaf.pixelrus.data.impl.InfectedDataImpl;
import com.happyzleaf.pixelrus.data.impl.VirusTypeDataImpl;
import com.happyzleaf.pixelrus.pokerus.VirusTypes;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.DataRegistration;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.api.util.TypeTokens;
import org.spongepowered.api.util.generator.dummy.DummyObjectProvider;

public class PixelrusKeys {
	/**
	 * True if the pokerus has been announced when the player used a healer
	 */
	public static Key<Value<Boolean>> HAS_POKERUS = DummyObjectProvider.createExtendedFor(Key.class, "HAS_POKERUS");
	
	/**
	 * Holds the virus type ordinal representing a {@link VirusTypes}
	 */
	public static Key<Value<Integer>> VIRUS_TYPE = DummyObjectProvider.createExtendedFor(Key.class, "VIRUS_TYPE");
	
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
		VIRUS_TYPE = Key.builder()
				.type(TypeTokens.INTEGER_VALUE_TOKEN)
				.id(Pixelrus.PLUGIN_ID + ":virus_type")
				.name("Virus Type")
				.query(DataQuery.of("virus_type"))
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
				.dataClass(HasPokerusData.class)
				.dataImplementation(HasPokerusDataImpl.class)
				.immutableClass(HasPokerusData.Immutable.class)
				.immutableImplementation(HasPokerusDataImpl.Immutable.class)
				.builder(new HasPokerusDataImpl.Builder())
				.buildAndRegister(Pixelrus.container);
		DataRegistration.builder()
				.dataName("Virus Type")
				.manipulatorId("virus_type")
				.dataClass(VirusTypeData.class)
				.dataImplementation(VirusTypeDataImpl.class)
				.immutableClass(VirusTypeData.Immutable.class)
				.immutableImplementation(VirusTypeDataImpl.Immutable.class)
				.builder(new VirusTypeDataImpl.Builder())
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
}
