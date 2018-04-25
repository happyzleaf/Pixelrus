package com.happyzleaf.pixelrus.data.impl;

import com.happyzleaf.pixelrus.data.HasPixelmonData;
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

public class HasPixelmonDataImpl extends AbstractBooleanData<HasPixelmonData, HasPixelmonData.Immutable> implements HasPixelmonData {
	private static final int CONTENT_VERSION = 1;
	
	HasPixelmonDataImpl(boolean announced) {
		super(announced, PixelrusKeys.HAS_POKERUS, false);
	}
	
	@Override
	public Value<Boolean> announced() {
		return getValueGetter();
	}
	
	@Override
	public Optional<HasPixelmonData> fill(DataHolder dataHolder, MergeFunction overlap) {
		Optional<HasPixelmonDataImpl> data_ = dataHolder.get(HasPixelmonDataImpl.class);
		if (data_.isPresent()) {
			HasPixelmonDataImpl data = data_.get();
			HasPixelmonDataImpl finalData = overlap.merge(this, data);
			setValue(finalData.getValue());
		}
		return Optional.of(this);
	}
	
	@Override
	public Optional<HasPixelmonData> from(DataContainer container) {
		return from((DataView) container);
	}
	
	public Optional<HasPixelmonData> from(DataView view) {
		if (view.contains(PixelrusKeys.HAS_POKERUS.getQuery())) {
			setValue(view.getBoolean(PixelrusKeys.HAS_POKERUS.getQuery()).get());
			return Optional.of(this);
		} else {
			return Optional.empty();
		}
	}
	
	@Override
	public HasPixelmonDataImpl copy() {
		return new HasPixelmonDataImpl(getValue());
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
	
	public static class Immutable extends AbstractImmutableBooleanData<HasPixelmonData.Immutable, HasPixelmonData> implements HasPixelmonData.Immutable {
		Immutable(boolean enabled) {
			super(enabled, PixelrusKeys.HAS_POKERUS, false);
		}
		
		@Override
		public HasPixelmonDataImpl asMutable() {
			return new HasPixelmonDataImpl(getValue());
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
	
	public static class Builder extends AbstractDataBuilder<HasPixelmonData> implements HasPixelmonData.Builder {
		public Builder() {
			super(HasPixelmonData.class, CONTENT_VERSION);
		}
		
		@Override
		public HasPixelmonDataImpl create() {
			return new HasPixelmonDataImpl(false);
		}
		
		@Override
		public Optional<HasPixelmonData> createFrom(DataHolder dataHolder) {
			return create().fill(dataHolder);
		}
		
		@Override
		protected Optional<HasPixelmonData> buildContent(DataView container) throws InvalidDataException {
			return create().from(container);
		}
	}
}
