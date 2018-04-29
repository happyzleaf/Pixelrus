package com.happyzleaf.pixelrus.data;

import org.spongepowered.api.data.manipulator.DataManipulator;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.data.manipulator.ImmutableDataManipulator;
import org.spongepowered.api.data.value.immutable.ImmutableValue;
import org.spongepowered.api.data.value.mutable.Value;

public interface VirusTypeData extends DataManipulator<VirusTypeData, VirusTypeData.Immutable> {
	Value<Integer> type();
	
	interface Immutable extends ImmutableDataManipulator<Immutable, VirusTypeData> {
		ImmutableValue<Integer> type();
	}
	
	interface Builder extends DataManipulatorBuilder<VirusTypeData, Immutable> {}
}
