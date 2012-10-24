package com.github.dreadslicer.tekkitrestrict;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class TRNoDupe {

	@SuppressWarnings("unused")
	private static boolean preventAlcDupe, preventRMDupe, preventTransmuteDupe;
	public static int dupeAttempts = 0;
	public static String lastPlayer = "";

	public static void reload() {
		preventAlcDupe = tekkitrestrict.config.getBoolean("PreventAlcDupe");
		preventRMDupe = tekkitrestrict.config
				.getBoolean("PreventRMFurnaceDupe");
		preventTransmuteDupe = tekkitrestrict.config
				.getBoolean("PreventTransmuteDupe");
	}

	public static void handleDupes(InventoryClickEvent event) {
		// event.getInventory();
		Player player = tekkitrestrict.getInstance().getServer()
				.getPlayer(event.getWhoClicked().getName());
		int slot = event.getSlot();

		String title = event.getView().getTopInventory().getTitle()
				.toLowerCase();
		// RMDupe Slot35
		if (!TRPermHandler.hasPermission(player, "dupe", "bypass", "")) {
			// tekkitrestrict.log.info("t0-"+title+"-");
			if (title.equals("rm furnace")) {
				// tekkitrestrict.log.info("t1");
				if (slot == 35) {
					// tekkitrestrict.log.info("t2");
					if (event.isShiftClick()) {
						// tekkitrestrict.log.info("t3");
						if (preventRMDupe) {
							event.setCancelled(true);
							player.sendRawMessage("[TRDupe] you are not allowed to Shift+Click here while using a RM Furnace!");

							TRLogger.Log("Dupe", player.getName()
									+ " attempted to dupe using a RM Furnace!");
						} else {
							TRLogger.Log("Dupe", player.getName()
									+ " duped using a RM Furnace!");
						}
						dupeAttempts++;
						TRLogger.broadcastDupe(player.getName(),
								"the RM Furnace", "RMFurnace");
					}
				}
			} else if (title.equals("trans tablet")) {
				// slots-6 7 5 3 1 0 2
				int item = event.getCurrentItem().getTypeId();
				if (item == 27557) {
				}
				if (item == 27558) {
				}
				if (item == 27559) {
				}
				if (item == 27560) {
				}
				if (item == 27561) {
				}
				if (item == 27591) {
				}
				if (event.isShiftClick()) {
					// if (isKlein) {
					boolean isslot = slot == 0 || slot == 1 || slot == 2
							|| slot == 3 || slot == 4 || slot == 5 || slot == 6
							|| slot == 7;
					if (isslot) {
						if (preventTransmuteDupe) {
							event.setCancelled(true);
							player.sendRawMessage("[TRDupe] you are not allowed to Shift+Click any ");
							player.sendRawMessage("           item out of the transmutation table!");

							TRLogger.Log("Dupe", player.getName()
									+ " attempted to transmute dupe!");
						} else {
							TRLogger.Log("Dupe", player.getName()
									+ " attempted to transmute dupe!");
						}
						dupeAttempts++;
						TRLogger.broadcastDupe(player.getName(),
								"the Transmutation Table", "transmute");
					}
					// }
				}
			}/*
			 * else if(title.equals("bag")){ //prevent a player from "Removing"
			 * an item by throwing it out while this bag is open. if(slot ==
			 * -999){ //Player threw an item out! if(preventAlcDupe){
			 * event.setCancelled(true); player.sendRawMessage(
			 * "[TRDupe] you are not allowed to throw stuff out of your alchemy bag or inventory!"
			 * ); if(this.showDupesOnConsole)
			 * tekkitrestrict.log.info(player.getName
			 * ()+" attempted to throw items out and dupe with the Alchemy Bag!"
			 * ); TRLogger.Log(player,"Dupe", player.getName()+
			 * " attempted to throw items out and dupe with the Alchemy Bag!");
			 * } else{ TRLogger.Log(player,"Dupe", player.getName()+
			 * " threw items out and possibly duped with the Alchemy Bag!"); } }
			 * 
			 * //prevent them from placing a BHB into the alc bag. }
			 */
			else {
				// ok, so this is a raw event. This will likely slow down the
				// server.
				// get alc bags of the player... ALL of them.

				/*
				 * if(slot == -999){ if(preventAlcDupe){
				 * if(tekkitrestrict.EEEnabled){
				 * //tekkitrestrict.log.info("???l4"); //8+ String Color = "";
				 * for(int i=0;i<16;i++){ try{ EntityHuman H =
				 * ((org.bukkit.craftbukkit
				 * .entity.CraftPlayer)player).getHandle(); ee.AlchemyBagData
				 * ABD = ee.ItemAlchemyBag.getBagData(i, H, H.world);
				 * //tekkitrestrict.log.info("???l5"); //ok, now we search!
				 * net.minecraft.server.ItemStack[] iss = ABD.items;
				 * //TRLogger.Log("debug", "info: TTAlc slot "+iss.length);
				 * for(int j=0;j<iss.length;j++){ if(iss[j] != null){
				 * if(iss[j].id == 27532 || iss[j].id == 27593){
				 * if(!lastPlayer.equals(player.getName()) &&
				 * player.isOnline()){ //they are attempting to dupe? Color =
				 * TRCommandAlc.getColor(i); String s = (iss[j].id == 27532) ?
				 * "Black Hole Band" : "Void Ring"; //player.kickPlayer(
				 * "[TRDupe] you have a Black Hole Band in your ["
				 * +Color+"] Alchemy Bag! Please remove it NOW!");
				 * event.setCancelled(true);
				 * player.kickPlayer("[TRDupe] you have a Black Hole Band in ["
				 * +Color+"] Alchemy Bag! No throwy!"); //player.sendRawMessage(
				 * "[TRDupe] you have a Black Hole Band in your ");
				 * //player.sendRawMessage
				 * ("          ["+Color+"] Alchemy Bag! Please remove it NOW!");
				 * if(showDupesOnConsole)
				 * tekkitrestrict.log.info(player.getName(
				 * )+" ["+Color+" bag] attempted to dupe with the "+s+"!");
				 * TRLogger.Log("Dupe",
				 * player.getName()+" ["+Color+" bag] attempted to dupe with the "
				 * +s+"!"); TRLogger.broadcastDupe(player.getName(),
				 * "the Alchemy Bag and "+s); lastPlayer = player.getName(); } }
				 * } } } catch(Exception e){ //This alc bag does not exist } } }
				 * } }
				 */
			}
		}
	}

	public static void handleDropDupes(
			org.bukkit.event.player.PlayerDropItemEvent e) {
		Player player = e.getPlayer();
		TRNoDupe_BagCache cache;
		if ((cache = TRNoDupe_BagCache.check(player)) != null) {
			if (cache.hasBHBInBag) {
				try {
					TRNoDupe_BagCache.expire(cache);
					e.setCancelled(true);
					player.kickPlayer("[TRDupe] you have a Black Hole Band in your ["
							+ cache.inBagColor
							+ "] Alchemy Bag! Please remove it NOW!");

					// if(showDupesOnConsole)
					// tekkitrestrict.log.info(player.getName()+" ["+cache.inBagColor+" bag] attempted to dupe with the "+cache.dupeItem+"!");
					TRLogger.Log("Dupe", player.getName() + " ["
							+ cache.inBagColor
							+ " bag] attempted to dupe with the "
							+ cache.dupeItem + "!");
					TRLogger.broadcastDupe(player.getName(),
							"the Alchemy Bag and " + cache.dupeItem, "alc");
				} catch (Exception err) {
				}
			}
		}
	}
}
