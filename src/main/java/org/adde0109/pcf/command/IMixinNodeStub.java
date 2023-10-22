package org.adde0109.pcf.command;

import net.minecraft.network.FriendlyByteBuf;

public interface IMixinNodeStub {
  void wrapAndWrite(FriendlyByteBuf byteBuf);
}
