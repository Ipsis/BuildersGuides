package ipsis.buildersguides.client.model;

import com.google.common.collect.ImmutableList;
import com.google.common.primitives.Ints;
import ipsis.buildersguides.block.BlockMarker;
import ipsis.buildersguides.reference.Reference;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.property.IExtendedBlockState;

import java.util.ArrayList;
import java.util.List;

public class ISBMMarker implements IBakedModel {

    public static final ModelResourceLocation modelResourceLocation = new ModelResourceLocation(Reference.MOD_ID + ":" + BlockMarker.BASENAME);

    /*
    @Override
    public IBakedModel handleBlockState(IBlockState state) {

        IExtendedBlockState extendedBlockState = (IExtendedBlockState)state;
        boolean north = extendedBlockState.getValue(BlockMarker.NORTH);
        boolean south = extendedBlockState.getValue(BlockMarker.SOUTH);
        boolean east = extendedBlockState.getValue(BlockMarker.EAST);
        boolean west = extendedBlockState.getValue(BlockMarker.WEST);
        boolean up = extendedBlockState.getValue(BlockMarker.UP);
        boolean down = extendedBlockState.getValue(BlockMarker.DOWN);
        int f = extendedBlockState.getValue(BlockMarker.FACING);
        return new BakedModelMarker(north, south, east, west, up , down, EnumFacing.getFront(f));
    } */

    @Override
    public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {

        throw new UnsupportedOperationException();
    }

    @Override
    public ItemOverrideList getOverrides() {

        return ItemOverrideList.NONE;
    }

    @Override
    public boolean isAmbientOcclusion() {
        return false;
    }

    @Override
    public boolean isGui3d() {
        return false;
    }

    @Override
    public boolean isBuiltInRenderer() {
        return false;
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return null;
    }

    @Override
    public ItemCameraTransforms getItemCameraTransforms() {
        return null;
    }

    public class BakedModelMarker implements IBakedModel {

        private TextureAtlasSprite mainTexture;
        private TextureAtlasSprite activeTexture;

        private final boolean north;
        private final boolean south;
        private final boolean east;
        private final boolean west;
        private final boolean up;
        private final boolean down;
        private EnumFacing facing;

        public BakedModelMarker(boolean north, boolean south, boolean east, boolean west, boolean up, boolean down, EnumFacing facing) {
            mainTexture = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(Reference.MOD_ID_LOWER + ":blocks/" + BlockMarker.BASENAME);
            activeTexture = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("minecraft:blocks/gold_block");
            this.north = north;
            this.south = south;
            this.east = east;
            this.west = west;
            this.up = up;
            this.down = down;
            this.facing = facing;
        }

        @Override
        public ItemOverrideList getOverrides() {

            return ItemOverrideList.NONE;
        }

        private int[] vertexToInts(double x, double y, double z, int color, TextureAtlasSprite texture, float u, float v) {
            return new int[] {
                    Float.floatToRawIntBits((float) x),
                    Float.floatToRawIntBits((float) y),
                    Float.floatToRawIntBits((float) z),
                    color,
                    Float.floatToRawIntBits(texture.getInterpolatedU(u)),
                    Float.floatToRawIntBits(texture.getInterpolatedV(v)),
                    0
            };
        }

        // From ModelBakeEventDebug MinecraftForge
        private BakedQuad createSidedBakedQuad(float x1, float x2, float z1, float z2, float y, TextureAtlasSprite texture, EnumFacing side) {
            Vec3d v1 = rotate(new Vec3d(x1 - .5, y - .5, z1 - .5), side).addVector(.5, .5, .5);
            Vec3d v2 = rotate(new Vec3d(x1 - .5, y - .5, z2 - .5), side).addVector(.5, .5, .5);
            Vec3d v3 = rotate(new Vec3d(x2 - .5, y - .5, z2 - .5), side).addVector(.5, .5, .5);
            Vec3d v4 = rotate(new Vec3d(x2 - .5, y - .5, z1 - .5), side).addVector(.5, .5, .5);
            return new BakedQuad(Ints.concat(
                    vertexToInts((float)v1.xCoord, (float)v1.yCoord, (float)v1.zCoord, -1, texture, 0, 0),
                    vertexToInts((float)v2.xCoord, (float)v2.yCoord, (float)v2.zCoord, -1, texture, 0, 16),
                    vertexToInts((float)v3.xCoord, (float)v3.yCoord, (float)v3.zCoord, -1, texture, 16, 16),
                    vertexToInts((float)v4.xCoord, (float)v4.yCoord, (float)v4.zCoord, -1, texture, 16, 0)
            ), -1, side, texture, true, DefaultVertexFormats.BLOCK);
        }



        // From ModelBakeEventDebug MinecraftForge
        private Vec3d rotate(Vec3d vec, EnumFacing side)
        {
            switch(side)
            {
                case DOWN:  return new Vec3d( vec.xCoord, -vec.yCoord, -vec.zCoord);
                case UP:    return new Vec3d( vec.xCoord,  vec.yCoord,  vec.zCoord);
                case NORTH: return new Vec3d( vec.xCoord,  vec.zCoord, -vec.yCoord);
                case SOUTH: return new Vec3d( vec.xCoord, -vec.zCoord,  vec.yCoord);
                case WEST:  return new Vec3d(-vec.yCoord,  vec.xCoord,  vec.zCoord);
                case EAST:  return new Vec3d( vec.yCoord, -vec.xCoord,  vec.zCoord);
            }
            return null;
        }

        @Override
        public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {

            if (side != null)
                return ImmutableList.of();


            List<BakedQuad> quads = new ArrayList<BakedQuad>();
            double o = .4;

            // TODO
            // Create all the models on startup
            // The 6 bool values can be converted into a single number for a lookup of the model
            // or we can cache as they are created


            float[][] v = new float[][]{
                    { 0.0F, 0.2F, 0.0F, 0.2F, 1.0F },
                    { 0.0F, 0.2F, 0.8F, 1.0F, 1.0F },
                    { 0.8F, 1.0F, 0.0F, 0.2F, 1.0F },
                    { 0.8F, 1.0F, 0.8F, 1.0F, 1.0F },

                    { 0.2F, 0.8F, 0.0F, 0.2F, 1.0F },
                    { 0.2F, 0.8F, 0.8F, 1.0F, 1.0F },
                    { 0.0F, 0.2F, 0.2F, 0.8F, 1.0F },
                    { 0.8F, 1.0F, 0.2F, 0.8F, 1.0F },

                    { 0.2F, 0.8F, 0.0F, 0.2F, 0.2F },
                    { 0.2F, 0.8F, 0.8F, 1.0F, 0.2F },
                    { 0.0F, 0.2F, 0.2F, 0.8F, 0.2F },
                    { 0.8F, 1.0F, 0.2F, 0.8F, 0.2F },
            };

            // Main Frame
            for (EnumFacing f : EnumFacing.values()) {
                for (int i = 0; i < 12; i++) {
                    if (f == facing && i >= 0 && i < 4)
                        quads.add(createSidedBakedQuad(v[i][0], v[i][1], v[i][2], v[i][3], v[i][4], activeTexture, f));
                    else
                        quads.add(createSidedBakedQuad(v[i][0], v[i][1], v[i][2], v[i][3], v[i][4], mainTexture, f));
                }
            }

            if (north) {
                quads.add(createSidedBakedQuad(0.4F, 0.6F, 0.4F, 0.6F, 1.0F, activeTexture, EnumFacing.NORTH));
                quads.add(createSidedBakedQuad(0.4F, 0.6F, 0.4F, 0.6F, 0.2F, activeTexture, EnumFacing.SOUTH));
                quads.add(createSidedBakedQuad(0.4F, 0.6F, 0.0F, 0.2F, 0.6F, activeTexture, EnumFacing.WEST));
                quads.add(createSidedBakedQuad(0.4F, 0.6F, 0.0F, 0.2F, 0.6F, activeTexture, EnumFacing.EAST));
                quads.add(createSidedBakedQuad(0.4F, 0.6F, 0.0F, 0.2F, 0.6F, activeTexture, EnumFacing.UP));
                quads.add(createSidedBakedQuad(0.4F, 0.6F, 0.8F, 1.0F, 0.6F, activeTexture, EnumFacing.DOWN));
            }

            if (south) {
                quads.add(createSidedBakedQuad(0.4F, 0.6F, 0.4F, 0.6F, 1.0F, activeTexture, EnumFacing.SOUTH));
                quads.add(createSidedBakedQuad(0.4F, 0.6F, 0.4F, 0.6F, 0.2F, activeTexture, EnumFacing.NORTH));
                quads.add(createSidedBakedQuad(0.4F, 0.6F, 0.8F, 1.0F, 0.6F, activeTexture, EnumFacing.EAST));
                quads.add(createSidedBakedQuad(0.4F, 0.6F, 0.8F, 1.0F, 0.6F, activeTexture, EnumFacing.WEST));
                quads.add(createSidedBakedQuad(0.4F, 0.6F, 0.0F, 0.2F, 0.6F, activeTexture, EnumFacing.DOWN));
                quads.add(createSidedBakedQuad(0.4F, 0.6F, 0.8F, 1.0F, 0.6F, activeTexture, EnumFacing.UP));
            }

            if (east) {
                quads.add(createSidedBakedQuad(0.4F, 0.6F, 0.4F, 0.6F, 1.0F, activeTexture, EnumFacing.EAST));
                quads.add(createSidedBakedQuad(0.4F, 0.6F, 0.4F, 0.6F, 0.2F, activeTexture, EnumFacing.WEST));
                quads.add(createSidedBakedQuad(0.8F, 1.0F, 0.4F, 0.6F, 0.6F, activeTexture, EnumFacing.NORTH));
                quads.add(createSidedBakedQuad(0.8F, 1.0F, 0.4F, 0.6F, 0.6F, activeTexture, EnumFacing.SOUTH));
                quads.add(createSidedBakedQuad(0.8F, 1.0F, 0.4F, 0.6F, 0.6F, activeTexture, EnumFacing.DOWN));
                quads.add(createSidedBakedQuad(0.8F, 1.0F, 0.4F, 0.6F, 0.6F, activeTexture, EnumFacing.UP));
            }

            if (west) {
                quads.add(createSidedBakedQuad(0.4F, 0.6F, 0.4F, 0.6F, 1.0F, activeTexture, EnumFacing.WEST));
                quads.add(createSidedBakedQuad(0.4F, 0.6F, 0.4F, 0.6F, 0.2F, activeTexture, EnumFacing.EAST));
                quads.add(createSidedBakedQuad(0.0F, 0.2F, 0.4F, 0.6F, 0.6F, activeTexture, EnumFacing.SOUTH));
                quads.add(createSidedBakedQuad(0.0F, 0.2F, 0.4F, 0.6F, 0.6F, activeTexture, EnumFacing.NORTH));
                quads.add(createSidedBakedQuad(0.0F, 0.2F, 0.4F, 0.6F, 0.6F, activeTexture, EnumFacing.UP));
                quads.add(createSidedBakedQuad(0.0F, 0.2F, 0.4F, 0.6F, 0.6F, activeTexture, EnumFacing.DOWN));
            }

            if (up) {
                quads.add(createSidedBakedQuad(0.4F, 0.6F, 0.4F, 0.6F, 1.0F, activeTexture, EnumFacing.UP));
                quads.add(createSidedBakedQuad(0.4F, 0.6F, 0.4F, 0.6F, 0.2F, activeTexture, EnumFacing.DOWN));
                quads.add(createSidedBakedQuad(0.0F, 0.2F, 0.4F, 0.6F, 0.6F, activeTexture, EnumFacing.EAST));
                quads.add(createSidedBakedQuad(0.8F, 1.0F, 0.4F, 0.6F, 0.6F, activeTexture, EnumFacing.WEST));
                quads.add(createSidedBakedQuad(0.4F, 0.6F, 0.8F, 1.0F, 0.6F, activeTexture, EnumFacing.NORTH));
                quads.add(createSidedBakedQuad(0.4F, 0.6F, 0.0F, 0.2F, 0.6F, activeTexture, EnumFacing.SOUTH));
            }

            if (down) {
                quads.add(createSidedBakedQuad(0.4F, 0.6F, 0.4F, 0.6F, 1.0F, activeTexture, EnumFacing.DOWN));
                quads.add(createSidedBakedQuad(0.4F, 0.6F, 0.4F, 0.6F, 0.2F, activeTexture, EnumFacing.UP));
                quads.add(createSidedBakedQuad(0.0F, 0.2F, 0.4F, 0.6F, 0.6F, activeTexture, EnumFacing.WEST));
                quads.add(createSidedBakedQuad(0.8F, 1.0F, 0.4F, 0.6F, 0.6F, activeTexture, EnumFacing.EAST));
                quads.add(createSidedBakedQuad(0.4F, 0.6F, 0.8F, 1.0F, 0.6F, activeTexture, EnumFacing.SOUTH));
                quads.add(createSidedBakedQuad(0.4F, 0.6F, 0.0F, 0.2F, 0.6F, activeTexture, EnumFacing.NORTH));
            }

            return quads;
        }

        @Override
        public boolean isAmbientOcclusion() {
            return true;
        }

        @Override
        public boolean isGui3d() {
            return true;
        }

        @Override
        public boolean isBuiltInRenderer() {
            return false;
        }

        @Override
        public TextureAtlasSprite getParticleTexture() {
            return mainTexture;
        }

        @Override
        public ItemCameraTransforms getItemCameraTransforms() {
            return ItemCameraTransforms.DEFAULT;
        }
    }
}
