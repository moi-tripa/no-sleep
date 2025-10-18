package moitripa.nosleep.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BedBlock.class)
public class BedBlockMixin {

    @Inject(method = "useWithoutItem", at = @At("HEAD"), cancellable = true)
    private void disableSleeping(BlockState state, Level level, BlockPos pos,
                                 Player player, BlockHitResult hitResult,
                                 CallbackInfoReturnable<InteractionResult> cir) {
        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            serverPlayer.setRespawnPosition(level.dimension(), pos, 0.0F, true, false);
        }

        // Display message
        player.displayClientMessage(Component.literal("You may not rest"), true);

        cir.setReturnValue(InteractionResult.SUCCESS);
    }
}
