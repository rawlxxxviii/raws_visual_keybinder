package com.gmail.rawlxxxviii.advanced_item_pickup.network;

import com.gmail.rawlxxxviii.advanced_item_pickup.AdvancedItemPickupMod;
import com.gmail.rawlxxxviii.advanced_item_pickup.network.packet.c2s.*;
import com.gmail.rawlxxxviii.advanced_item_pickup.network.packet.s2c.DisableAutoPickup_S2CPacket;
import com.gmail.rawlxxxviii.advanced_item_pickup.network.packet.s2c.EnableAutoPickup_S2CPacket;
import com.gmail.rawlxxxviii.advanced_item_pickup.network.packet.s2c.RemoveNameFilter_S2CPacket;
import com.gmail.rawlxxxviii.advanced_item_pickup.network.packet.s2c.UpdateAdvancedPickupSettings_S2CPacket;
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
                        RemoveNameFilter_S2CPacket.class,
                    id(),
                    NetworkDirection.PLAY_TO_CLIENT
                )
                .decoder(RemoveNameFilter_S2CPacket::new)
                .encoder(RemoveNameFilter_S2CPacket::encodeToBytes)
                .consumerMainThread(RemoveNameFilter_S2CPacket::handle)
                .add();

        INSTANCE.messageBuilder(
                        UpdateAdvancedPickupSettings_S2CPacket.class,
                    id(),
                    NetworkDirection.PLAY_TO_CLIENT
                )
                .decoder(UpdateAdvancedPickupSettings_S2CPacket::new)
                .encoder(UpdateAdvancedPickupSettings_S2CPacket::encodeToBytes)
                .consumerMainThread(UpdateAdvancedPickupSettings_S2CPacket::handle)
                .add();

        INSTANCE.messageBuilder(
                        DisableAutoPickup_S2CPacket.class,
                    id(),
                    NetworkDirection.PLAY_TO_CLIENT
                )
                .decoder(DisableAutoPickup_S2CPacket::new)
                .encoder(DisableAutoPickup_S2CPacket::encodeToBytes)
                .consumerMainThread(DisableAutoPickup_S2CPacket::handle)
                .add();

        INSTANCE.messageBuilder(
                    EnableAutoPickup_S2CPacket.class,
                    id(),
                    NetworkDirection.PLAY_TO_CLIENT
                )
                .decoder(EnableAutoPickup_S2CPacket::new)
                .encoder(EnableAutoPickup_S2CPacket::encodeToBytes)
                .consumerMainThread(EnableAutoPickup_S2CPacket::handle)
                .add();

        INSTANCE.messageBuilder(
                    RemoveNameFilter_C2SPacket.class,
                    id(),
                    NetworkDirection.PLAY_TO_SERVER
                )
                .decoder(RemoveNameFilter_C2SPacket::new)
                .encoder(RemoveNameFilter_C2SPacket::encodeToBytes)
                .consumerMainThread(RemoveNameFilter_C2SPacket::handle)
                .add();

        INSTANCE.messageBuilder(
                    UpdateNameFilter_C2SPacket.class,
                    id(),
                    NetworkDirection.PLAY_TO_SERVER
                )
                .decoder(UpdateNameFilter_C2SPacket::new)
                .encoder(UpdateNameFilter_C2SPacket::encodeToBytes)
                .consumerMainThread(UpdateNameFilter_C2SPacket::handle)
                .add();


        INSTANCE.messageBuilder(
                    DisableAutoPickupKeyPressed_C2SPacket.class,
                    id(),
                    NetworkDirection.PLAY_TO_SERVER
                )
                .decoder(DisableAutoPickupKeyPressed_C2SPacket::new)
                .encoder(DisableAutoPickupKeyPressed_C2SPacket::encodeToBytes)
                .consumerMainThread(DisableAutoPickupKeyPressed_C2SPacket::handle)
                .add();


        INSTANCE.messageBuilder(
                    EnableAutoPickupKeyPressed_C2SPacket.class,
                    id(),
                    NetworkDirection.PLAY_TO_SERVER
                )
                .decoder(EnableAutoPickupKeyPressed_C2SPacket::new)
                .encoder(EnableAutoPickupKeyPressed_C2SPacket::encodeToBytes)
                .consumerMainThread(EnableAutoPickupKeyPressed_C2SPacket::handle)
                .add();


        INSTANCE.messageBuilder(
                    ToggleAutoPickupKeyPressed_C2SPacket.class,
                    id(),
                    NetworkDirection.PLAY_TO_SERVER
                )
                .decoder(ToggleAutoPickupKeyPressed_C2SPacket::new)
                .encoder(ToggleAutoPickupKeyPressed_C2SPacket::encodeToBytes)
                .consumerMainThread(ToggleAutoPickupKeyPressed_C2SPacket::handle)
                .add();


        INSTANCE.messageBuilder(
                    PickUpAllKeyDown_C2SPacket.class,
                    id(),
                    NetworkDirection.PLAY_TO_SERVER
                )
                .decoder(PickUpAllKeyDown_C2SPacket::new)
                .encoder(PickUpAllKeyDown_C2SPacket::encodeToBytes)
                .consumerMainThread(PickUpAllKeyDown_C2SPacket::handle)
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
