package moitripa.nosleep;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.BlockState;

public class NoSleep implements ModInitializer {

    public static final String MOD_ID = "nosleep";

    @Override
    public void onInitialize() {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            BlockPos pos = hitResult.getBlockPos();
            BlockState state = world.getBlockState(pos);

            if (!world.isClientSide && player instanceof ServerPlayer serverPlayer) {
                if (state.getBlock() instanceof BedBlock) {
                    serverPlayer.setRespawnPosition(world.dimension(), pos, player.getYRot(), false, true);
                    serverPlayer.displayClientMessage(Component.translatable("block.minecraft.bed.set_spawn"), true);
                    player.displayClientMessage(Component.literal("You may not rest"), true);
                    return InteractionResult.FAIL;
                }
            }

            return InteractionResult.PASS;
        });

    }
}
