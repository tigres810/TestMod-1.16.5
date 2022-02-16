package com.tigres810.testmod.common.items;

import java.util.function.Supplier;

import net.minecraft.item.MusicDiscItem;
import net.minecraft.util.SoundEvent;

public class MikuTellWorldMusicDiscItem extends MusicDiscItem {

	public MikuTellWorldMusicDiscItem(int comparatorValue, Supplier<SoundEvent> soundSupplier, Properties builder) {
		super(comparatorValue, soundSupplier, builder);
	}
}
