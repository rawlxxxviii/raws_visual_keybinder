package com.gmail.rawlxxxviii.vicinity_item_pickup.network;

import com.gmail.rawlxxxviii.vicinity_item_pickup.VicinityItemPickupMod;
import com.gmail.rawlxxxviii.vicinity_item_pickup.network.packet.c2s.*;
import com.gmail.rawlxxxviii.vicinity_item_pickup.network.packet.s2c.UpdateVicinityPickupSettings_S2CPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketHandler {

    private static int packetId = 0;
    private static int id(){
        return packetId++;
    }

    private static final SimpleChannel INSTANCE = net.minecraftforge.network.NetworkRegistry.ChannelBuilder.named(
            new ResourceLocation(VicinityItemPickupMod.MODID, "main"))
            .serverAcceptedVersions((version)->true)
            .clientAcceptedVersions((version)->true)
            .networkProtocolVersion(()-> "1")
            .simpleChannel();

    public static void register(){

        INSTANCE.messageBuilder(
                        UpdateVicinityPickupSettings_S2CPacket.class,
                    id(),
                    NetworkDirection.PLAY_TO_CLIENT
                )
                .decoder(UpdateVicinityPickupSettings_S2CPacket::new)
                .encoder(UpdateVicinityPickupSettings_S2CPacket::encodeToBytes)
                .consumerMainThread(UpdateVicinityPickupSettings_S2CPacket::handle)
                .add();

        INSTANCE.messageBuilder(
                        OpenVicinityPickupMenu_C2SPacket.class,
                    id(),
                    NetworkDirection.PLAY_TO_SERVER
                )
                .decoder(OpenVicinityPickupMenu_C2SPacket::new)
                .encoder(OpenVicinityPickupMenu_C2SPacket::encodeToBytes)
                .consumerMainThread(OpenVicinityPickupMenu_C2SPacket::handle)
                .add();

        INSTANCE.messageBuilder(
                        PickupItemEntity_C2SPacket.class,
                    id(),
                    NetworkDirection.PLAY_TO_SERVER
                )
                .decoder(PickupItemEntity_C2SPacket::new)
                .encoder(PickupItemEntity_C2SPacket::encodeToBytes)
                .consumerMainThread(PickupItemEntity_C2SPacket::handle)
                .add();



    }

    public static <T> void sendToServer(T msg){
        INSTANCE.send(PacketDistributor.SERVER.noArg(), msg);
    }

    public static <T> void sendToPlayer(T msg, ServerPlayer player){
        INSTANCE.send(PacketDistributor.PLAYER.with(()->player),msg);
    }

    public static <T> void sendToClients(T msg){
        INSTANCE.send(PacketDistributor.ALL.noArg(), msg);
    }

}
