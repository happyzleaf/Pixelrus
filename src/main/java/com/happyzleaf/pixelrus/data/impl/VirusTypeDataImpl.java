package com.happyzleaf.pixelrus.data.impl;

import com.happyzleaf.pixelrus.data.VirusTypeData;
import com.happyzleaf.pixelrus.data.PixelrusKeys;
import com.happyzleaf.pixelrus.pokerus.VirusTypes;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableSingleData;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractIntData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;
import org.spongepowered.api.data.value.immutable.ImmutableValue;
import org.spongepowered.api.data.value.mutable.Value;

import java.util.Optional;

public class VirusTypeDataImpl extends AbstractIntData<VirusTypeData, VirusTypeData.Immutable> implements VirusTypeData {
	private static final int CONTENT_VERSION = 1;
	
	protected VirusTypeDataImpl(int value) {
		super(value, PixelrusKeys.VIRUS_TYPE);
	}
	
	@Override
	public Value<Integer> type() {
		return getValueGetter();
	}
	
	@Override
	protected Value<Integer> getValueGetter() {
		return Sponge.getRegistry().getValueFactory().createValue(PixelrusKeys.VIRUS_TYPE, this.getValue(), VirusTypes.getRandom().ordinal());
	}
	
	@Override
	public Optional<VirusTypeData> fill(DataHolder dataHolder, MergeFunction overlap) {
		Optional<VirusTypeDataImpl> data_ = dataHolder.get(VirusTypeDataImpl.class);
		if (data_.isPresent()) {
			VirusTypeDataImpl data = data_.get();
			VirusTypeDataImpl finalData = overlap.merge(this, data);
			setValue(finalData.getValue());
		}
		return Optional.of(this);
	}
	
	@Override
	public Optional<VirusTypeData> from(DataContainer container) {
		return from((DataView) container);
	}
	
	public Optional<VirusTypeData> from(DataView view) {
		if (view.contains(PixelrusKeys.VIRUS_TYPE.getQuery())) {
			setValue(view.getInt(PixelrusKeys.VIRUS_TYPE.getQuery()).get());
			return Optional.of(this);
		} else {
			return Optional.empty();
		}
	}
	
	@Override
	public VirusTypeData copy() {
		return new VirusTypeDataImpl(getValue());
	}
	
	@Override
	public DataContainer toContainer() {
		return super.toContainer().set(this.usedKey.getQuery(), getValue());
	}
	
	@Override
	public VirusTypeData.Immutable asImmutable() {
		return new Immutable(getValue());
	}
	
	@Override
	public int getContentVersion() {
		return CONTENT_VERSION;
	}
	
	public static class Immutable extends AbstractImmutableSingleData<Integer, VirusTypeData.Immutable, VirusTypeData> implements VirusTypeData.Immutable {
		private ImmutableValue<Integer> immutableValue;
		
		protected Immutable(Integer value) {
			super(value, PixelrusKeys.VIRUS_TYPE);
			immutableValue = Sponge.getRegistry().getValueFactory().createValue(PixelrusKeys.VIRUS_TYPE, 0, value).asImmutable();
		}
		
		@Override
		public ImmutableValue<Integer> type() {
			return getValueGetter();
		}
		
		@Override
		protected ImmutableValue<Integer> getValueGetter() {
			return immutableValue;
		}
		
		@Override
		public DataContainer toContainer() {
			return super.toContainer().set(PixelrusKeys.VIRUS_TYPE.getQuery(), getValue());
		}
		
		@Override
		public VirusTypeData asMutable() {
			return new VirusTypeDataImpl(getValue());
		}
		
		@Override
		public int getContentVersion() {
			return CONTENT_VERSION;
		}
	}
	
	public static class Builder extends AbstractDataBuilder<VirusTypeData> implements VirusTypeData.Builder {
		public Builder() {
			super(VirusTypeData.class, CONTENT_VERSION);
		}
		
		@Override
		public VirusTypeDataImpl create() {
			return new VirusTypeDataImpl(0);
		}
		
		@Override
		public Optional<VirusTypeData> createFrom(DataHolder dataHolder) {
			return create().fill(dataHolder);
		}
		
		@Override
		protected Optional<VirusTypeData> buildContent(DataView container) throws InvalidDataException {
			return create().from(container);
		}
	}
}
