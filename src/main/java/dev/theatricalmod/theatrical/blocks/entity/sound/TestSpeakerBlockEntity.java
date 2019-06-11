package dev.theatricalmod.theatrical.blocks.entity.sound;

import dev.theatricalmod.theatrical.registry.TheatricalBlockEntities;
import dev.theatricalmod.theatrical.util.WorldUtil;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;

import java.util.Set;
import java.util.stream.Collectors;

public class TestSpeakerBlockEntity extends BlockEntity {

    private final double maxRange;
    private Set<PlayerEntity> audibleTo;

    public TestSpeakerBlockEntity() {
        super(TheatricalBlockEntities.TEST_SPEAKER);

        this.maxRange = 10;
    }

    public void playSound(SoundEvent soundEvent) {
        if (world.getPlayers() == null)
            return;
        if (world.getPlayers().size() == 0)
            return;

        Set<PlayerEntity> audibleTo = world.getPlayers().stream().filter(p -> WorldUtil.calcDistance(((PlayerEntity) p).getPos(),
                new Vec3d(this.pos)) < this.maxRange).collect(Collectors.toSet());

        for (PlayerEntity player : audibleTo) {
            System.out.println("playing to");
            player.playSound(soundEvent, SoundCategory.BLOCKS, /*(float) (WorldUtil.calcDistance(player.getPos(), new Vec3d(this.pos)) / this.maxRange)*/1f, 1f);
        }

        this.audibleTo = audibleTo;
    }

    public Set<PlayerEntity> getAudibleTo() {
        return audibleTo;
    }

}
