package com.happyzleaf.pixelrus.pokerus;

import com.happyzleaf.pixelrus.Pixelrus;

public enum VirusTypes {
	A(1440),
	B(2880),
	C(4320),
	D(5760);
	
	public final int minutes;
	
	VirusTypes(int minutes) {
		this.minutes = minutes;
	}
	
	public static VirusTypes getRandom() {
		return values()[Pixelrus.rnd.nextInt(values().length)];
	}
}
