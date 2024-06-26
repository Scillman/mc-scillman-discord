package com.github.scillman.minecraft.reeds.feature;

import com.github.scillman.minecraft.reeds.Reeds;
import com.github.scillman.minecraft.reeds.block.ReedBlock;
import com.mojang.serialization.Codec;
import net.minecraft.block.BambooBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.BambooLeaves;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class ReedsFeature extends Feature<ProbabilityConfig>
{
	private static final BlockState REEDS = Reeds.REED_BLOCK
		.getDefaultState()
		.with(BambooBlock.AGE, Integer.valueOf(1))
		.with(BambooBlock.LEAVES, BambooLeaves.NONE)
		.with(BambooBlock.STAGE, Integer.valueOf(0))
		.with(ReedBlock.TOP, Boolean.valueOf(true));
	private static final BlockState REED_TOP_1 = REEDS.with(BambooBlock.LEAVES, BambooLeaves.LARGE).with(BambooBlock.STAGE, Integer.valueOf(1));
	private static final BlockState REED_TOP_2 = REEDS.with(BambooBlock.LEAVES, BambooLeaves.LARGE);
	private static final BlockState REED_TOP_3 = REEDS.with(BambooBlock.LEAVES, BambooLeaves.SMALL);

	public ReedsFeature(Codec<ProbabilityConfig> codec)
	{
		super(codec);
	}

	@Override
	public boolean generate(FeatureContext<ProbabilityConfig> context)
	{
		int i = 0;
		BlockPos blockPos = context.getOrigin();
		StructureWorldAccess structureWorldAccess = context.getWorld();
		Random random = context.getRandom();
		ProbabilityConfig probabilityConfig = context.getConfig();
		BlockPos.Mutable mutable = blockPos.mutableCopy();
		BlockPos.Mutable mutable2 = blockPos.mutableCopy();
		if (structureWorldAccess.isAir(mutable)) {
			if (Blocks.BAMBOO.getDefaultState().canPlaceAt(structureWorldAccess, mutable)) {
				int j = random.nextInt(12) + 5;
				if (random.nextFloat() < probabilityConfig.probability) {
					int k = random.nextInt(4) + 1;

					for (int l = blockPos.getX() - k; l <= blockPos.getX() + k; l++) {
						for (int m = blockPos.getZ() - k; m <= blockPos.getZ() + k; m++) {
							int n = l - blockPos.getX();
							int o = m - blockPos.getZ();
							if (n * n + o * o <= k * k) {
								mutable2.set(l, structureWorldAccess.getTopY(Heightmap.Type.WORLD_SURFACE, l, m) - 1, m);
								if (isSoil(structureWorldAccess.getBlockState(mutable2))) {
									structureWorldAccess.setBlockState(mutable2, Blocks.PODZOL.getDefaultState(), Block.NOTIFY_LISTENERS);
								}
							}
						}
					}
				}

				for (int k = 0; k < j && structureWorldAccess.isAir(mutable); k++) {
					structureWorldAccess.setBlockState(mutable, REEDS, Block.NOTIFY_LISTENERS);
					mutable.move(Direction.UP, 1);
				}

				if (mutable.getY() - blockPos.getY() >= 3) {
					structureWorldAccess.setBlockState(mutable, REED_TOP_1, Block.NOTIFY_LISTENERS);
					structureWorldAccess.setBlockState(mutable.move(Direction.DOWN, 1), REED_TOP_2, Block.NOTIFY_LISTENERS);
					structureWorldAccess.setBlockState(mutable.move(Direction.DOWN, 1), REED_TOP_3, Block.NOTIFY_LISTENERS);
				}
			}

			i++;
		}

		return i > 0;
	}
}
