package org.adde0109.pcf.command;

import net.minecraft.network.FriendlyByteBuf;

public interface IMixinWrappableEntry {
  void wrapAndWrite(FriendlyByteBuf byteBuf);
}
