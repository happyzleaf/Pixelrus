package com.happyzleaf.pixelrus.data.impl;

import com.happyzleaf.pixelrus.data.HasPokerusData;
import com.happyzleaf.pixelrus.data.PixelrusKeys;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableBooleanData;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractBooleanData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;
import org.spongepowered.api.data.value.immutable.ImmutableValue;
import org.spongepowered.api.data.value.mutable.Value;

import java.util.Optional;

public class HasPokerusDataImpl extends AbstractBooleanData<HasPokerusData, HasPokerusData.Immutable> implements HasPokerusData {
	private static final int CONTENT_VERSION = 1;
	
	HasPokerusDataImpl(boolean announced) {
		super(announced, PixelrusKeys.HAS_POKERUS, false);
	}
	
	@Override
	public Value<Boolean> announced() {
		return getValueGetter();
	}
	
	@Override
	public Optional<HasPokerusData> fill(DataHolder dataHolder, MergeFunction overlap) {
		Optional<HasPokerusDataImpl> data_ = dataHolder.get(HasPokerusDataImpl.class);
		if (data_.isPresent()) {
			HasPokerusDataImpl data = data_.get();
			HasPokerusDataImpl finalData = overlap.merge(this, data);
			setValue(finalData.getValue());
		}
		return Optional.of(this);
	}
	
	@Override
	public Optional<HasPokerusData> from(DataContainer container) {
		return from((DataView) container);
	}
	
	public Optional<HasPokerusData> from(DataView view) {
		if (view.contains(PixelrusKeys.HAS_POKERUS.getQuery())) {
			setValue(view.getBoolean(PixelrusKeys.HAS_POKERUS.getQuery()).get());
			return Optional.of(this);
		} else {
			return Optional.empty();
		}
	}
	
	@Override
	public HasPokerusDataImpl copy() {
		return new HasPokerusDataImpl(getValue());
	}
	
	@Override
	public Immutable asImmutable() {
		return new Immutable(getValue());
	}
	
	@Override
	public int getContentVersion() {
		return CONTENT_VERSION;
	}
	
	@Override
	public DataContainer toContainer() {
		return super.toContainer().set(PixelrusKeys.HAS_POKERUS.getQuery(), getValue());
	}
	
	public static class Immutable extends AbstractImmutableBooleanData<HasPokerusData.Immutable, HasPokerusData> implements HasPokerusData.Immutable {
		Immutable(boolean enabled) {
			super(enabled, PixelrusKeys.HAS_POKERUS, false);
		}
		
		@Override
		public HasPokerusDataImpl asMutable() {
			return new HasPokerusDataImpl(getValue());
		}
		
		@Override
		public int getContentVersion() {
			return CONTENT_VERSION;
		}
		
		@Override
		public DataContainer toContainer() {
			return super.toContainer().set(PixelrusKeys.HAS_POKERUS.getQuery(), getValue());
		}
		
		@Override
		public ImmutableValue<Boolean> announced() {
			return getValueGetter();
		}
	}
	
	public static class Builder extends AbstractDataBuilder<HasPokerusData> implements HasPokerusData.Builder {
		public Builder() {
			super(HasPokerusData.class, CONTENT_VERSION);
		}
		
		@Override
		public HasPokerusDataImpl create() {
			return new HasPokerusDataImpl(false);
		}
		
		@Override
		public Optional<HasPokerusData> createFrom(DataHolder dataHolder) {
			return create().fill(dataHolder);
		}
		
		@Override
		protected Optional<HasPokerusData> buildContent(DataView container) throws InvalidDataException {
			return create().from(container);
		}
	}
}
