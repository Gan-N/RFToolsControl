package mcjty.rftoolscontrol.logic.registry;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

public enum ParameterType {
    PAR_STRING() {
        @Override
        protected String stringRepresentationInternal(Object value) {
            return (String) value;
        }

        @Override
        protected void writeToNBTInternal(NBTTagCompound tag, Object value) {
            tag.setString("v", (String) value);
        }

        @Override
        protected ParameterValue readFromNBTInternal(NBTTagCompound tag) {
            return ParameterValue.constant(tag.getString("v"));
        }
    },
    PAR_INTEGER() {
        @Override
        protected String stringRepresentationInternal(Object value) {
            return Integer.toString((Integer) value);
        }

        @Override
        protected void writeToNBTInternal(NBTTagCompound tag, Object value) {
            tag.setInteger("v", (Integer) value);
        }

        @Override
        protected ParameterValue readFromNBTInternal(NBTTagCompound tag) {
            return ParameterValue.constant(tag.getInteger("v"));
        }
    },
    PAR_FLOAT() {
        @Override
        protected String stringRepresentationInternal(Object value) {
            return Float.toString((Float) value);
        }

        @Override
        protected void writeToNBTInternal(NBTTagCompound tag, Object value) {
            tag.setFloat("v", (Float) value);
        }

        @Override
        protected ParameterValue readFromNBTInternal(NBTTagCompound tag) {
            return ParameterValue.constant(tag.getFloat("v"));
        }
    },
    PAR_SIDE() {
        @Override
        protected String stringRepresentationInternal(Object value) {
            return ((EnumFacing) value).getName();
        }

        @Override
        protected void writeToNBTInternal(NBTTagCompound tag, Object value) {
            tag.setInteger("v", ((EnumFacing) value).ordinal());
        }

        @Override
        protected ParameterValue readFromNBTInternal(NBTTagCompound tag) {
            return ParameterValue.constant(EnumFacing.values()[tag.getInteger("v")]);
        }
    };

    public String stringRepresentation(ParameterValue value) {
        if (value.isVariable()) {
            return "V:" + value.getVariableIndex();
        } else if (value.getValue() == null) {
            return "NULL";
        } else {
            return stringRepresentationInternal(value.getValue());
        }
    }

    protected String stringRepresentationInternal(Object value) {
        return "?";
    }

    public void writeToNBT(NBTTagCompound tag, ParameterValue value) {
        if (value.isVariable()) {
            tag.setInteger("varIdx", value.getVariableIndex());
        } else if (value.getValue() == null) {
            // No value
            tag.setBoolean("null", true);
        } else {
            writeToNBTInternal(tag, value.getValue());
        }
    }

    public ParameterValue readFromNBT(NBTTagCompound tag) {
        if (tag.hasKey("varIdx")) {
            return ParameterValue.variable(tag.getInteger("varIdx"));
        } else if (tag.hasKey("null")) {
            return ParameterValue.constant(null);
        } else {
            return readFromNBTInternal(tag);
        }
    }

    protected ParameterValue readFromNBTInternal(NBTTagCompound tag) {
        return ParameterValue.constant(null);
    }

    protected void writeToNBTInternal(NBTTagCompound tag, Object value) {
    }
}
