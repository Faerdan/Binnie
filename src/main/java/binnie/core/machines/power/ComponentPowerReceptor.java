package binnie.core.machines.power;

import Reika.RotaryCraft.API.Power.IAdvancedShaftPowerReceiver;
import Reika.RotaryCraft.API.Power.IShaftPowerInputCaller;
import Reika.RotaryCraft.API.Power.ShaftPowerInputManager;
import binnie.core.machines.IMachine;
import binnie.core.machines.MachineComponent;
import binnie.core.machines.component.IBuildcraft;
import binnie.core.machines.component.IInteraction;
import binnie.core.machines.network.INetwork;
import binnie.core.network.INetworkedEntity;
import binnie.core.network.packet.PacketPayload;
import binnie.core.triggers.TriggerData;
import binnie.core.triggers.TriggerPower;
import buildcraft.api.core.BCLog;
import cpw.mods.fml.common.Optional;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

@Optional.Interface(iface = "binnie.core.machines.component.IBuildcraft.TriggerProvider", modid = "BuildCraft|Silicon")
public class ComponentPowerReceptor extends MachineComponent implements
        IShaftPowerInputCaller,
        IBuildcraft.TriggerProvider,
        IInteraction.ChunkUnload,
        IInteraction.Invalidation,
        INetwork.TilePacketSync {

    private final ShaftPowerInputManager shaftPowerInputManager;

    public ComponentPowerReceptor(IMachine machine, String nameKey, int minTorque, int minOmega, int minPower) {
        super(machine);

        shaftPowerInputManager = new ShaftPowerInputManager(this, nameKey, minTorque, minOmega, minPower);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);

    }

    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
    }

    @Override
    public void syncToNBT(NBTTagCompound nbt) {
        nbt.setBoolean("hasPower", shaftPowerInputManager.getPower() > 0);
        if (shaftPowerInputManager.getPower() > 0)
        {
            nbt.setInteger("torque", shaftPowerInputManager.getTorque());
            nbt.setInteger("omega", shaftPowerInputManager.getOmega());
        }
    }

    @Override
    public void syncFromNBT(NBTTagCompound nbt) {
        if (nbt.getBoolean("hasPower"))
        {
            shaftPowerInputManager.setState(nbt.getInteger("torque"), nbt.getInteger("omega"));
        }
        else
        {
            shaftPowerInputManager.setState(0, 0);
        }
    }

    @Override
    public void onUpdate() {
        //BCLog.logger.info("ComponentPowerReceptor.onUpdate()");
        shaftPowerInputManager.update();
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

    /*@Override
    public void writeToPacket(PacketPayload payload) {
        BCLog.logger.info("ComponentPowerReceptor writeToPacket");
        payload.addInteger(shaftPowerInputManager.getTorque());
        payload.addInteger(shaftPowerInputManager.getOmega());
        payload.addInteger(shaftPowerInputManager.hasMismatchedInputs() ? 1 : 0);
    }

    @Override
    public void readFromPacket(PacketPayload payload) {
        BCLog.logger.info("ComponentPowerReceptor readFromPacket");
        shaftPowerInputManager.setState(payload.getInteger(), payload.getInteger(), payload.getInteger() == 1);
    }*/


	/* Rotary Power */

    @Override
    public void onPowerChange(ShaftPowerInputManager shaftPowerInputManager) {
        //this.setNeedsNetworkUpdate();
        //sendNetworkUpdate();
        getUtil().refreshBlock();
    }

    @Override
    public TileEntity getTileEntity() {
        return getMachine().getTileEntity();
    }

    @Override
    public boolean addPower(int addTorque, int addOmega, long addPower, ForgeDirection inputDirection) {
        return shaftPowerInputManager != null && shaftPowerInputManager.addPower(addTorque, addOmega, addPower, inputDirection);
    }

    @Override
    public int getStageCount() {
        return shaftPowerInputManager != null ? shaftPowerInputManager.getStageCount() : 0;
    }

    @Override
    public void setIORenderAlpha(int i) {
        if (shaftPowerInputManager != null) shaftPowerInputManager.setIORenderAlpha(i);
    }

    @Override
    public boolean canReadFrom(ForgeDirection forgeDirection) {
        return true;
    }

    @Override
    public boolean isReceiving() {
        return shaftPowerInputManager != null && shaftPowerInputManager.isReceiving();
    }

    @Override
    public int getMinTorque(int stageIndex) {
        return shaftPowerInputManager != null ? shaftPowerInputManager.getMinTorque(stageIndex) : 1;
    }

    @Override
    public int getMinOmega(int stageIndex) {
        return shaftPowerInputManager != null ? shaftPowerInputManager.getMinOmega(stageIndex) : 1;
    }

    @Override
    public long getMinPower(int stageIndex) {
        return shaftPowerInputManager != null ? shaftPowerInputManager.getMinPower(stageIndex) : 1;
    }

    @Override
    public long getPower() {
        return shaftPowerInputManager != null ? shaftPowerInputManager.getPower() : 0;
    }

    @Override
    public int getOmega() {
        return shaftPowerInputManager != null ? shaftPowerInputManager.getOmega() : 0;
    }

    @Override
    public int getTorque() {
        return shaftPowerInputManager != null ? shaftPowerInputManager.getTorque() : 0;
    }

    @Override
    public String getName() {
        return shaftPowerInputManager != null ? shaftPowerInputManager.getName() : "[Forestry]";
    }

    @Override
    public int getIORenderAlpha() {
        return shaftPowerInputManager != null ? shaftPowerInputManager.getIORenderAlpha() : 0;
    }
}
