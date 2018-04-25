package com.happyzleaf.pixelrus.data.impl;

import com.happyzleaf.pixelrus.data.HasPixelmonData;
import com.happyzleaf.pixelrus.data.InfectedData;
import com.happyzleaf.pixelrus.data.PixelrusKeys;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableBooleanData;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableSingleData;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractBooleanData;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractIntData;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractSingleData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;
import org.spongepowered.api.data.value.BaseValue;
import org.spongepowered.api.data.value.immutable.ImmutableValue;
import org.spongepowered.api.data.value.mutable.Value;

import java.util.Optional;

public class InfectedDataImpl extends AbstractIntData<InfectedData, InfectedData.Immutable> implements InfectedData {
	private static final int CONTENT_VERSION = 1;
	
	protected InfectedDataImpl(int value) {
		super(value, PixelrusKeys.INFECTED);
	}
	
	@Override
	public Value<Integer> timePassed() {
		return getValueGetter();
	}
	
	@Override
	protected Value<Integer> getValueGetter() {
		return Sponge.getRegistry().getValueFactory().createValue((Key<Value<Integer>>) this.usedKey, this.getValue(), 0);
	}
	
	@Override
	public Optional<InfectedData> fill(DataHolder dataHolder, MergeFunction overlap) {
		Optional<InfectedDataImpl> data_ = dataHolder.get(InfectedDataImpl.class);
		if (data_.isPresent()) {
			InfectedDataImpl data = data_.get();
			InfectedDataImpl finalData = overlap.merge(this, data);
			setValue(finalData.getValue());
		}
		return Optional.of(this);
	}
	
	@Override
	public Optional<InfectedData> from(DataContainer container) {
		return from((DataView) container);
	}
	
	public Optional<InfectedData> from(DataView view) {
		if (view.contains(PixelrusKeys.INFECTED.getQuery())) {
			setValue(view.getInt(PixelrusKeys.INFECTED.getQuery()).get());
			return Optional.of(this);
		} else {
			return Optional.empty();
		}
	}
	
	@Override
	public InfectedData copy() {
		return new InfectedDataImpl(getValue());
	}
	
	@Override
	public DataContainer toContainer() {
		return super.toContainer().set(this.usedKey.getQuery(), getValue());
	}
	
	@Override
	public InfectedData.Immutable asImmutable() {
		return new Immutable(getValue());
	}
	
	@Override
	public int getContentVersion() {
		return CONTENT_VERSION;
	}
	
	public static class Immutable extends AbstractImmutableSingleData<Integer, InfectedData.Immutable, InfectedData> implements InfectedData.Immutable {
		private ImmutableValue<Integer> immutableValue;
		
		protected Immutable(Integer value) {
			super(value, PixelrusKeys.INFECTED);
			immutableValue = Sponge.getRegistry().getValueFactory().createValue(PixelrusKeys.INFECTED, 0, value).asImmutable();
		}
		
		@Override
		public ImmutableValue<Integer> timePassed() {
			return getValueGetter();
		}
		
		@Override
		protected ImmutableValue<Integer> getValueGetter() {
			return immutableValue;
		}
		
		@Override
		public DataContainer toContainer() {
			return super.toContainer().set(PixelrusKeys.INFECTED.getQuery(), getValue());
		}
		
		@Override
		public InfectedData asMutable() {
			return new InfectedDataImpl(getValue());
		}
		
		@Override
		public int getContentVersion() {
			return CONTENT_VERSION;
		}
	}
	
	public static class Builder extends AbstractDataBuilder<InfectedData> implements InfectedData.Builder {
		public Builder() {
			super(InfectedData.class, CONTENT_VERSION);
		}
		
		@Override
		public InfectedDataImpl create() {
			return new InfectedDataImpl(0);
		}
		
		@Override
		public Optional<InfectedData> createFrom(DataHolder dataHolder) {
			return create().fill(dataHolder);
		}
		
		@Override
		protected Optional<InfectedData> buildContent(DataView container) throws InvalidDataException {
			return create().from(container);
		}
	}
}
