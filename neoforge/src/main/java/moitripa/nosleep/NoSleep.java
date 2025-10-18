package moitripa.nosleep;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.BlockState;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@Mod(NoSleep.MOD_ID)
public class NoSleep {

    public static final String MOD_ID = "nosleep";

    public NoSleep(IEventBus modEventBus) {
        NeoForge.EVENT_BUS.addListener(NoSleep::onRightClickBlock);
    }

    private static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);

        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            if (state.getBlock() instanceof BedBlock) {
                serverPlayer.setRespawnPosition(level.dimension(), pos, player.getYRot(), false, true);
                serverPlayer.displayClientMessage(Component.translatable("block.minecraft.bed.set_spawn"), true);
                serverPlayer.displayClientMessage(Component.literal("You may not rest"), true);
                event.setCanceled(true);
            }
        }
    }
}
