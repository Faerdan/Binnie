package binnie.core.machines.power;

import Reika.RotaryCraft.API.Power.IShaftPowerReceiver;
import binnie.core.machines.IMachine;
import binnie.core.machines.MachineComponent;
import binnie.core.machines.component.IBuildcraft;
import binnie.core.machines.component.IInteraction;
import binnie.core.triggers.TriggerData;
import binnie.core.triggers.TriggerPower;
import cpw.mods.fml.common.Optional;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

@Optional.Interface(iface = "binnie.core.machines.component.IBuildcraft.TriggerProvider", modid = "BuildCraft|Silicon")
public class ComponentPowerReceptor extends MachineComponent implements
        IShaftPowerReceiver,
        IBuildcraft.TriggerProvider,
        IInteraction.ChunkUnload,
        IInteraction.Invalidation {

    private String nameKey;

    public ComponentPowerReceptor(IMachine machine, String nameKey, int minTorque, int minOmega, int minPower) {
        super(machine);

        this.nameKey = nameKey;

        setMinTorque(minTorque);
        setMinOmega(minOmega);
        setMinPower(minPower);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        setTorque(nbttagcompound.getInteger("torque"));
        setOmega(nbttagcompound.getInteger("omega"));
        setPower(nbttagcompound.getLong("power"));

    }

    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setInteger("torque", getTorque());
        nbttagcompound.setInteger("omega", getOmega());
        nbttagcompound.setLong("power", getPower());
    }

    @Override
    public void onUpdate() {

    }

    @Optional.Method(modid = "BuildCraft|Silicon")
    @Override
    public void getTriggers(List<TriggerData> triggers) {
        triggers.add(TriggerPower.powerNone(this));
        triggers.add(TriggerPower.powerLow(this));
        triggers.add(TriggerPower.powerMedium(this));
        triggers.add(TriggerPower.powerHigh(this));
        triggers.add(TriggerPower.powerFull(this));
    }

    @Override
    public void onInvalidation() {

    }

    @Override
    public void onChunkUnload() {

    }

    /* Rotary Power */
    private int rotaryMinTorque = 1;
    private int rotaryMinOmega = 1;
    private long rotaryMinPower = 1;

    private int rotaryOmega;
    private int rotaryTorque;
    private long rotaryPower;
    private int rotaryIORenderAlpha;

    @Override
    public void setOmega(int i) {
        //BCLog.logger.info("Quarry setOmega: " + i);
        rotaryOmega = i;
    }

    @Override
    public void setTorque(int i) {
        //BCLog.logger.info("Quarry setTorque: " + i);
        rotaryTorque = i;
    }

    @Override
    public void setPower(long l) {
        //BCLog.logger.info("Quarry setPower: " + l);
        rotaryPower = l;
    }

    @Override
    public void noInputMachine() {
        rotaryOmega = 0;
        rotaryTorque = 0;
        rotaryPower = 0;
    }

    @Override
    public boolean canReadFrom(ForgeDirection forgeDirection) {
        return true; // (forgeDirection == ForgeDirection.EAST || forgeDirection == ForgeDirection.WEST || forgeDirection == ForgeDirection.NORTH);
    }

    @Override
    public boolean isReceiving() {
        return true;
    }

    @Override
    public int getMinTorque() {
        return getMinTorque(getTorque());
    }

    @Override
    public int getMinTorque(int i) {
        return rotaryMinTorque;
    }

    @Override
    public int getMinOmega() {
        return getMinOmega(getOmega());
    }

    @Override
    public int getMinOmega(int i) {
        return rotaryMinOmega;
    }

    @Override
    public long getMinPower() {
        return getMinPower(getPower());
    }

    @Override
    public long getMinPower(long l) {
        return rotaryMinPower;
    }

    @Override
    public int getOmega() {
        return rotaryOmega;
    }

    @Override
    public int getTorque() {
        return rotaryTorque;
    }

    @Override
    public long getPower() {
        return rotaryPower;
    }

    @Override
    public String getName() {
        return nameKey;
    }

    @Override
    public int getIORenderAlpha() {
        return rotaryIORenderAlpha;
    }

    @Override
    public void setIORenderAlpha(int i) {
        rotaryIORenderAlpha = i;
    }

    @Override
    public void setMinTorque(int i) {
        if (i >= 1)
        {
            rotaryMinTorque = i;
        }
    }

    @Override
    public void setMinOmega(int i) {
        if (i >= 1)
        {
            rotaryMinOmega = i;
        }
    }

    @Override
    public void setMinPower(long l) {
        if (l >= 1)
        {
            rotaryMinPower = l;
        }
    }


}
