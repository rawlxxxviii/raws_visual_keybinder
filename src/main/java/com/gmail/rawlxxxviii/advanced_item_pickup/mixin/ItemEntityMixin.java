package com.gmail.rawlxxxviii.advanced_item_pickup.mixin;

import com.gmail.rawlxxxviii.advanced_item_pickup.capability.AdvancedPickup;
import com.gmail.rawlxxxviii.advanced_item_pickup.capability.AdvancedPickupAttacher;
import com.gmail.rawlxxxviii.advanced_item_pickup.common.ItemFilterType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {


    @Shadow public abstract ItemStack getItem();

    private ItemEntityMixin(EntityType<?> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }


    
    @Inject(method = "playerTouch(Lnet/minecraft/world/entity/player/Player;)V", at = @At("HEAD"), cancellable = true, require = 1)
    public void playerTouchInject(Player player, CallbackInfo info) {

        player.getCapability(AdvancedPickup.INSTANCE).ifPresent(c-> {


                if(c.isPickingUpItems()) {
                    return;
                }

                var filterResult = c.getFilterResult( this.getItem().getItem());

                if (c.isAutoPickupEnabled()){

                    if(filterResult == ItemFilterType.NEVER){
                        info.cancel();
                    }

                }else{
                    if(filterResult == ItemFilterType.ALWAYS){
                        return;
                    }
                    info.cancel();
                }
            }

        );


    }

    
}
