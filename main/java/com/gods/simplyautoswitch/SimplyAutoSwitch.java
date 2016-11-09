package com.gods.simplyautoswitch;

import com.gods.simplyautoswitch.client.Client;
import com.gods.simplyautoswitch.client.KeyBindings;
import com.gods.simplyautoswitch.client.KeyInputHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLEventChannel;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = SimplyAutoSwitch.MODID, version = SimplyAutoSwitch.VERSION, name = SimplyAutoSwitch.NAME)
public class SimplyAutoSwitch {
    public static final String MODID = "simplyautoswitch";
    public static final String NAME = "simplyautoswitch";
    public static final String VERSION = "0.0.1";
	
    public static FMLEventChannel eventChannel;
	public static final String ChannelName = MODID.substring(0, (MODID.length() < 20 ? MODID.length() : 20));
    
    private static boolean wasAttacking = false;
	private static int iPrevItem = -99;
    
	@Mod.Instance(MODID)
	public static SimplyAutoSwitch instance;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		if (FMLCommonHandler.instance().getSide().isClient()) {
			MinecraftForge.EVENT_BUS.register(new KeyInputHandler());
			KeyBindings.init();
		}
	}
	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		eventChannel = NetworkRegistry.INSTANCE.newEventDrivenChannel(ChannelName);
		eventChannel.register(this);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void tickEvent(TickEvent.ClientTickEvent event) {
		if (!TickEvent.Phase.START.equals(event.phase)) return;
		if (!Client.enabled) return;
		
		Minecraft mc = Minecraft.getMinecraft();
		if (!mc.inGameHasFocus || mc.isGamePaused() || mc.playerController.isInCreativeMode()) return;
		
		EntityPlayer player = mc.thePlayer;
		if (null == player || player.isDead || player.isPlayerSleeping()) return;

		World world = mc.theWorld;
		if (null == world) return;
		
		boolean isAttacking = mc.gameSettings.keyBindAttack.isKeyDown();
		
		if (!isAttacking && wasAttacking && player.inventory.currentItem != iPrevItem) {
			player.inventory.currentItem = iPrevItem;
			iPrevItem = -99;
		}
		else if (isAttacking && !wasAttacking)
			iPrevItem = player.inventory.currentItem;
		
		if (isAttacking && mc.objectMouseOver != null)
			if (mc.objectMouseOver.typeOfHit == RayTraceResult.Type.BLOCK) {
				BlockPos oPos = mc.objectMouseOver.getBlockPos();
	    		//Block block = world.getBlockState(oPos).getBlock();
    			SubstituteTool(world, oPos);
			}
			else if (mc.objectMouseOver.typeOfHit == RayTraceResult.Type.ENTITY && mc.objectMouseOver.entityHit instanceof EntityLivingBase) {
				SubstituteWeapon();
			}
		
		wasAttacking = isAttacking;
	}

	private void SubstituteTool(World world, BlockPos oPos) {
		try {
			int iSubstituteTool = iPrevItem;
			
			Minecraft mc = Minecraft.getMinecraft();
			ItemStack[] inventory = mc.thePlayer.inventory.mainInventory;

			for (int i = 0; i < 9; i++)
				if (i != iSubstituteTool && inventory[i] != null && Client.determineTool(inventory[iSubstituteTool], inventory[i], world, oPos))
					iSubstituteTool = i;
			
			if (mc.thePlayer.inventory.currentItem != iSubstituteTool) {
				mc.thePlayer.inventory.currentItem = iSubstituteTool;
				mc.thePlayer.openContainer.detectAndSendChanges();
			}
			
		} catch (Throwable e) {
			System.out.println("Error switching tools - " + e.getMessage());
		}
	}
	
	private void SubstituteWeapon() {
		try {
			Minecraft mc = Minecraft.getMinecraft();
			ItemStack[] inventory = mc.thePlayer.inventory.mainInventory;
			int iSubstituteWeapon = iPrevItem;

			for (int i = 0; i < 9; i++)
				if (i != iSubstituteWeapon && inventory[i] != null)
					if (Client.determineWeapon(inventory[iSubstituteWeapon], inventory[i]))
						iSubstituteWeapon = i;

			if (mc.thePlayer.inventory.currentItem != iSubstituteWeapon) {
				mc.thePlayer.inventory.currentItem = iSubstituteWeapon;
				mc.thePlayer.openContainer.detectAndSendChanges();

				iPrevItem = iSubstituteWeapon;
			}
		} catch (Throwable e) {
			System.out.println("Error switching weapons - " + e.getMessage());
		}
	}
	
}
