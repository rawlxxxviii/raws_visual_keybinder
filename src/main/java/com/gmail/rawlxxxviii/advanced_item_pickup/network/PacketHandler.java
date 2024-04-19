package com.gmail.rawlxxxviii.advanced_item_pickup.network;

import com.gmail.rawlxxxviii.advanced_item_pickup.AdvancedItemPickupMod;
import com.gmail.rawlxxxviii.advanced_item_pickup.network.packet.*;
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
            new ResourceLocation(AdvancedItemPickupMod.MODID, "main"))
            .serverAcceptedVersions((version)->true)
            .clientAcceptedVersions((version)->true)
            .networkProtocolVersion(()-> "1")
            .simpleChannel();

    public static void register(){

        INSTANCE.messageBuilder(
                    RemoveNameFilter_P2SPacket.class,
                    id(),
                    NetworkDirection.PLAY_TO_SERVER
                )
                .decoder(RemoveNameFilter_P2SPacket::new)
                .encoder(RemoveNameFilter_P2SPacket::encodeToBytes)
                .consumerMainThread(RemoveNameFilter_P2SPacket::handle)
                .add();

        INSTANCE.messageBuilder(
                    UpdateNameFilter_P2SPacket.class,
                    id(),
                    NetworkDirection.PLAY_TO_SERVER
                )
                .decoder(UpdateNameFilter_P2SPacket::new)
                .encoder(UpdateNameFilter_P2SPacket::encodeToBytes)
                .consumerMainThread(UpdateNameFilter_P2SPacket::handle)
                .add();


        INSTANCE.messageBuilder(
                    DisableAutoPickupKeyPressed_P2SPacket.class,
                    id(),
                    NetworkDirection.PLAY_TO_SERVER
                )
                .decoder(DisableAutoPickupKeyPressed_P2SPacket::new)
                .encoder(DisableAutoPickupKeyPressed_P2SPacket::encodeToBytes)
                .consumerMainThread(DisableAutoPickupKeyPressed_P2SPacket::handle)
                .add();


        INSTANCE.messageBuilder(
                    EnableAutoPickupKeyPressed_P2SPacket.class,
                    id(),
                    NetworkDirection.PLAY_TO_SERVER
                )
                .decoder(EnableAutoPickupKeyPressed_P2SPacket::new)
                .encoder(EnableAutoPickupKeyPressed_P2SPacket::encodeToBytes)
                .consumerMainThread(EnableAutoPickupKeyPressed_P2SPacket::handle)
                .add();


        INSTANCE.messageBuilder(
                    ToggleAutoPickupKeyPressed_P2SPacket.class,
                    id(),
                    NetworkDirection.PLAY_TO_SERVER
                )
                .decoder(ToggleAutoPickupKeyPressed_P2SPacket::new)
                .encoder(ToggleAutoPickupKeyPressed_P2SPacket::encodeToBytes)
                .consumerMainThread(ToggleAutoPickupKeyPressed_P2SPacket::handle)
                .add();


        INSTANCE.messageBuilder(
                    PickUpAllKeyDown_P2SPacket.class,
                    id(),
                    NetworkDirection.PLAY_TO_SERVER
                )
                .decoder(PickUpAllKeyDown_P2SPacket::new)
                .encoder(PickUpAllKeyDown_P2SPacket::encodeToBytes)
                .consumerMainThread(PickUpAllKeyDown_P2SPacket::handle)
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
