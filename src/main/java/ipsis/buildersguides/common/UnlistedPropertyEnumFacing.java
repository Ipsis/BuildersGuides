package ipsis.buildersguides.common;

import net.minecraftforge.common.property.IUnlistedProperty;

public class UnlistedPropertyEnumFacing implements IUnlistedProperty<Integer> {

    private final String name;

    public UnlistedPropertyEnumFacing(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isValid(Integer value) {
        return value >= 0 && value < 6;
    }

    @Override
    public Class<Integer> getType() {
        return Integer.class;
    }

    @Override
    public String valueToString(Integer value) {
        return value.toString();
    }
}
