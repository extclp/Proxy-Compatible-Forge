package org.adde0109.pcf.mixin.command;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraftforge.network.NetworkContext;
import org.adde0109.pcf.command.IMixinWrappableCommandPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;


@Mixin(net.minecraft.commands.Commands.class)
public class CommandsMixin {

  @Redirect(method = "sendCommands(Lnet/minecraft/server/level/ServerPlayer;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerGamePacketListenerImpl;send(Lnet/minecraft/network/protocol/Packet;)V"))
  private void sendCommands$grabPacket(ServerGamePacketListenerImpl connection, Packet<?> packet) {
    NetworkContext context = NetworkContext.get(connection.getConnection());
    if (context.getRemoteChannels().stream().anyMatch((v) -> v.equals(new ResourceLocation("ambassador:commands")))) {
      connection.send(new ClientboundCustomPayloadPacket(new CustomPacketPayload() {
        @Override
        public void write(FriendlyByteBuf p_297598_) {
          ((IMixinWrappableCommandPacket)(Object) packet).wrapAndWrite(p_297598_);
        }

        @Override
        public ResourceLocation id() {
          return new ResourceLocation("ambassador:commands");
        }
      }));
    } else {
      connection.send(packet);
    }
  }
}
