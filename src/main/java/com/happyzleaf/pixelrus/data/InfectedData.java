package com.happyzleaf.pixelrus.data;

import org.spongepowered.api.data.manipulator.DataManipulator;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.data.manipulator.ImmutableDataManipulator;
import org.spongepowered.api.data.value.immutable.ImmutableValue;
import org.spongepowered.api.data.value.mutable.Value;

public interface InfectedData extends DataManipulator<InfectedData, InfectedData.Immutable> {
	Value<Integer> timePassed();
	
	interface Immutable extends ImmutableDataManipulator<Immutable, InfectedData> {
		ImmutableValue<Integer> timePassed();
	}
	
	interface Builder extends DataManipulatorBuilder<InfectedData, Immutable> {}
}
