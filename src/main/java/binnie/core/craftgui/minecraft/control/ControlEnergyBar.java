package binnie.core.craftgui.minecraft.control;

import Reika.RotaryCraft.API.Power.IAdvancedShaftPowerReceiver;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.ITooltip;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.WidgetAttribute;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.geometry.Position;
import binnie.core.craftgui.minecraft.MinecraftTooltip;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.craftgui.resource.minecraft.CraftGUITexture;
import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.power.IProcess;
import binnie.core.util.I18N;
import net.minecraft.inventory.IInventory;
import org.lwjgl.opengl.GL11;

public class ControlEnergyBar extends Control implements ITooltip {
	public static boolean isError;

	//private Position direction;

	public ControlEnergyBar(IWidget parent, int x, int y, int width, int height, Position direction) {
		super(parent, x, y, width, height);
		//this.direction = direction;
		//addAttribute(WidgetAttribute.MOUSE_OVER);
	}

	/*public IAdvancedShaftPowerReceiver getClientPower() {
		IInventory inventory = Window.get(this).getInventory();
		TileEntityMachine machine = (inventory instanceof TileEntityMachine) ? (TileEntityMachine) inventory : null;
		if (machine == null) {
			return null;
		}

		IAdvancedShaftPowerReceiver clientPower = machine.getMachine().getInterface(IAdvancedShaftPowerReceiver.class);
		return clientPower;
	}*/

	@Override
	public void getTooltip(Tooltip tooltip) {
		/*IAdvancedShaftPowerReceiver clientPower = getClientPower();
		tooltip.add(I18N.localise("binniecore.gui.tooltip.chargedPower", (clientPower != null) ? clientPower.getPower() : -1));
		tooltip.add(I18N.localise("binniecore.gui.tooltip.powerInfo", (clientPower != null) ? clientPower.getTorque() : -1, (clientPower != null) ? clientPower.getOmega() : -1));*/
	}

	@Override
	public void getHelpTooltip(Tooltip tooltip) {
		/*IAdvancedShaftPowerReceiver clientPower = getClientPower();
		tooltip.add(I18N.localise("binniecore.gui.tooltip.powerBar"));
		tooltip.add(I18N.localise("binniecore.gui.tooltip.currentPower", (clientPower != null) ? clientPower.getTorque() : -1, (clientPower != null) ? clientPower.getOmega() : -1));
		tooltip.add(I18N.localise("binniecore.gui.tooltip.capacityPower", (clientPower != null) ? clientPower.getPower() : -1));

		IProcess process = Machine.getInterface(IProcess.class, Window.get(this).getInventory());
		if (process != null) {
			tooltip.add(I18N.localise("binniecore.gui.tooltip.usagePower", 33));
		}*/
	}

	@Override
	public void onRenderBackground() {
		/*CraftGUI.Render.texture(CraftGUITexture.EnergyBarBack, getArea());

		IAdvancedShaftPowerReceiver clientPower = getClientPower();
		float percentage = clientPower != null ? Math.min(100, Math.max(0, (clientPower.getPower() / clientPower.getMinPower(0)) * 100.0f)) : 0f;
		CraftGUI.Render.color(getColourFromPercentage(percentage));
		IArea area = getArea();

		switch (direction) {
			case TOP:
			case BOTTOM:
				float height = area.size().y() * percentage;
				area.setSize(new IPoint(area.size().x(), height));
				break;

			case LEFT:
			case RIGHT:
				float width = area.size().x() * percentage;
				area.setSize(new IPoint(width, area.size().y()));
				break;
		}

		if (isMouseOver() && Window.get(this).getGui().isHelpMode()) {
			int c = 0xaa000000 + MinecraftTooltip.getOutline(Tooltip.Type.HELP);
			CraftGUI.Render.gradientRect(getArea().inset(1), c, c);
		} else if (ControlEnergyBar.isError) {
			int c = 0xaa000000 + MinecraftTooltip.getOutline(MinecraftTooltip.Type.ERROR);
			CraftGUI.Render.gradientRect(getArea().inset(1), c, c);
		}

		CraftGUI.Render.texture(CraftGUITexture.EnergyBarGlow, area);
		GL11.glColor3d(1.0, 1.0, 1.0);
		CraftGUI.Render.texture(CraftGUITexture.EnergyBarGlass, getArea());*/
	}

	@Override
	public void onRenderForeground() {
		/*if (isMouseOver() && Window.get(this).getGui().isHelpMode()) {
			IArea area = getArea();
			CraftGUI.Render.color(MinecraftTooltip.getOutline(Tooltip.Type.HELP));
			CraftGUI.Render.texture(CraftGUITexture.Outline, area.outset(1));
		} else if (ControlEnergyBar.isError) {
			IArea area = getArea();
			CraftGUI.Render.color(MinecraftTooltip.getOutline(MinecraftTooltip.Type.ERROR));
			CraftGUI.Render.texture(CraftGUITexture.Outline, area.outset(1));
		}*/
	}

	/*public int getColourFromPercentage(float percentage) {
		int color;
		if (percentage > 0.5) {
			int r = (int) ((1.0 - 2.0 * (percentage - 0.5)) * 255.0);
			color = (r << 16) + 0xff00;
		} else {
			int g = (int) (255.0f * (2.0f * percentage));
			color = 0xff0000 + (g << 8);
		}
		return color;
	}*/
}
