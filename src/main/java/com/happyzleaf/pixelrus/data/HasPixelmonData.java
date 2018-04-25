package com.happyzleaf.pixelrus.data;

import org.spongepowered.api.data.manipulator.DataManipulator;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.data.manipulator.ImmutableDataManipulator;
import org.spongepowered.api.data.value.immutable.ImmutableValue;
import org.spongepowered.api.data.value.mutable.Value;

public interface HasPixelmonData extends DataManipulator<HasPixelmonData, HasPixelmonData.Immutable> {
	Value<Boolean> announced();
	
	interface Immutable extends ImmutableDataManipulator<Immutable, HasPixelmonData> {
		ImmutableValue<Boolean> announced();
	}
	
	interface Builder extends DataManipulatorBuilder<HasPixelmonData, Immutable> {}
}
