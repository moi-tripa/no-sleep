package moitripa.nosleep;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(Constants.MOD_ID)
public class NoSleep {

    public NoSleep() {}

    @Mod.EventBusSubscriber(modid = Constants.MOD_ID)
    public static class ForgeEvents {

        @SubscribeEvent
        public static void onPlayerSleep(PlayerSleepInBedEvent event) {
            Player player = event.getEntity();
            Level level = player.level();
            BlockPos pos = event.getPos();
            BlockState state = level.getBlockState(pos);

            if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
                if (state.getBlock() instanceof BedBlock) {
                    serverPlayer.setRespawnPosition(level.dimension(), pos, player.getYRot(), false, true);
                    serverPlayer.displayClientMessage(Component.translatable("block.minecraft.bed.set_spawn"), true);
                    player.displayClientMessage(Component.literal("You may not rest"), true);
                    event.setResult(Player.BedSleepingProblem.OTHER_PROBLEM);
                    event.setResult(Event.Result.DENY);
                }
            }
        }
    }
}
