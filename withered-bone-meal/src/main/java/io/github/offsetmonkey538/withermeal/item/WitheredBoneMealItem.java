package io.github.offsetmonkey538.withermeal.item;

import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class WitheredBoneMealItem extends Item {
    private final Random random = Random.create();

    public WitheredBoneMealItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        Vec3d pos = Vec3d.of(blockPos);
        BlockState blockState = world.getBlockState(blockPos);
        Block block = blockState.getBlock();
        ItemStack stack = context.getStack();


        if (block instanceof GrassBlock || block instanceof MyceliumBlock) {
            addParticles(world, pos.add(0, 1, 0));
            setBlockState(world, blockPos, Blocks.DIRT);

            return returnSuccess(stack, world);
        }

        if (block instanceof NyliumBlock) {
            addParticles(world, pos.add(0, 1, 0));
            setBlockState(world, blockPos, Blocks.NETHERRACK);

            return returnSuccess(stack, world);
        }

        if (block instanceof CropBlock || block instanceof StemBlock) {
            int newAge = 0;
            if (block instanceof CropBlock cropBlock)
                newAge = blockState.get(cropBlock.getAgeProperty()) - (((blockState.get(cropBlock.getAgeProperty()) - 1) < 0) ? 0 : 1);
            if (block instanceof StemBlock)
                newAge = blockState.get(Properties.AGE_7) - (((blockState.get(Properties.AGE_7) - 1) < 0) ? 0 : 1);

            addParticles(world, pos);
            setBlockState(world, blockPos, blockState.with((block instanceof CropBlock cropBlock ? cropBlock.getAgeProperty() : Properties.AGE_7), newAge));
            return returnSuccess(stack, world);
        }

        if (block instanceof NetherWartBlock) {
            int newAge = blockState.get(Properties.AGE_3) + (((blockState.get(Properties.AGE_3) + 1) > Properties.AGE_3_MAX) ? 0 : 1);

            addParticles(world, pos);
            setBlockState(world, blockPos, blockState.with(Properties.AGE_3, newAge));

            return returnSuccess(stack, world);
        }

        if (block instanceof FlowerBlock && !(block instanceof WitherRoseBlock)) {
            addParticles(world, pos);
            setBlockState(world, blockPos, Blocks.WITHER_ROSE);

            return returnSuccess(stack, world);
        }

        if (block instanceof Fertilizable && !(block instanceof NetherrackBlock)) {
            addParticles(world, pos);
            setBlockState(world, blockPos, Blocks.AIR);

            return returnSuccess(stack, world);
        }

        
        return ActionResult.PASS;
    }

    private ActionResult returnSuccess(ItemStack stack, World world) {
        stack.decrement(1);
        return ActionResult.success(world.isClient());
    }

    private void setBlockState(World world, BlockPos pos, Block block) {
        setBlockState(world, pos, block.getDefaultState());
    }
    private void setBlockState(World world, BlockPos pos, BlockState state) {
        if (!world.isClient()) {
            world.setBlockState(pos, state);
        }
    }

    private void addParticles(World world, Vec3d pos) {
        if (world.isClient()) {
            for (int i = 0; i < 40; ++i) {
                double velX = random.nextGaussian() * 0.02;
                double velY = random.nextGaussian() * 0.02;
                double velZ = random.nextGaussian() * 0.02;

                double x = (pos.getX() + 0.5) + (0.5 - 1.0) + random.nextDouble() * 1.0 * 1.0;
                double y = (pos.getY() + 0.5) + (0.5 - 1.0) + random.nextDouble() * 1.0 * 1.0;
                double z = (pos.getZ() + 0.5) + (0.5 - 1.0) + random.nextDouble() * 1.0 * 1.0;

                world.addParticle(ParticleTypes.SMOKE, x, y, z, velX, velY, velZ);
            }
        }
    }
}
