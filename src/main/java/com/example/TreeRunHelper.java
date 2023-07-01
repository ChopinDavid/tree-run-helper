package com.example;

import com.google.inject.Provides;

import javax.annotation.Nullable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.*;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import java.time.Instant;
import java.util.*;

@Slf4j
@PluginDescriptor(
	name = "Tree Run Helper"
)
public class TreeRunHelper extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private TreeRunHelperConfig config;

	private ConfigManager configManager;

	Integer farmingLevel;
	ArrayList<Patch> availableTreePatches = new ArrayList();
	ArrayList<Patch> availableFruitTreePatches = new ArrayList();
	ArrayList<Patch> availableSpiritTreePatches = new ArrayList();
	ArrayList<Patch> availableHardwoodTreePatches = new ArrayList();
	ArrayList<Patch> availableCalquatTreePatches = new ArrayList();
	ArrayList<Patch> availableCrystalTreePatches = new ArrayList();
	ArrayList<Patch> availableCelastrusTreePatches = new ArrayList();
	ArrayList<Patch> availableRedwoodTreePatches = new ArrayList();

	private ArrayList<Patch> allAvailablePatches() {
		ArrayList<Patch> allAvailablePatches = new ArrayList();

		for (Patch availableTreePatch: availableTreePatches) {
			allAvailablePatches.add(availableTreePatch);
		}
		for (Patch availableTreePatch: availableFruitTreePatches) {
			allAvailablePatches.add(availableTreePatch);
		}
		for (Patch availableTreePatch: availableSpiritTreePatches) {
			allAvailablePatches.add(availableTreePatch);
		}
		for (Patch availableTreePatch: availableHardwoodTreePatches) {
			allAvailablePatches.add(availableTreePatch);
		}
		for (Patch availableTreePatch: availableCalquatTreePatches) {
			allAvailablePatches.add(availableTreePatch);
		}
		for (Patch availableTreePatch: availableCrystalTreePatches) {
			allAvailablePatches.add(availableTreePatch);
		}
		for (Patch availableTreePatch: availableCelastrusTreePatches) {
			allAvailablePatches.add(availableTreePatch);
		}
		for (Patch availableTreePatch: availableRedwoodTreePatches) {
			allAvailablePatches.add(availableTreePatch);
		}

		return allAvailablePatches;
	}

	private Map<String, Long> growingPatchesMap = new HashMap();
	private ArrayList<String> harvestablePatchKeys = new ArrayList();

	private Map<Patch, Long> growingPatches() {
		Map<Patch, Long> growingPatches = new HashMap();
		for (Patch patch: allAvailablePatches()) {
			for (String key : growingPatchesMap.keySet()) {
				if (key == patch.configKey()) {
					growingPatches.put(patch, growingPatchesMap.get(key));
				}
			}
		}
		return growingPatches();
	}

	private ArrayList<Patch> harvestablePatches() {
		ArrayList<Patch> harvestablePatches = new ArrayList();
		for (Patch patch: allAvailablePatches()) {
			for (String harvestablePatchKey: harvestablePatchKeys) {
				if (patch.configKey() == harvestablePatchKey) {
					harvestablePatches.add(patch);
				}
			}
		}
		return harvestablePatches();
	}

	@Override
	protected void startUp() throws Exception
	{
	}

	@Override
	protected void shutDown() throws Exception
	{
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged event)
	{
		switch (event.getGameState())
		{
			case HOPPING:
			case LOGIN_SCREEN:
			case LOADING:
			case LOGGED_IN:
				int farmingLvl = client.getRealSkillLevel(Skill.FARMING);
		}
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event)
	{
	}

	@Subscribe
	public void onStatChanged(StatChanged statChanged)
	{
	}

	@Subscribe
	public void onItemSpawned(ItemSpawned itemSpawned) {
	}

	@Subscribe
	public void onItemDespawned(ItemDespawned itemDespawned)
	{
	}

	@Subscribe
	public void onGameTick(GameTick tick)
	{
		if (farmingLevel == null) {
			farmingLevel = client.getRealSkillLevel(Skill.FARMING);
			updateAvailablePatches();
		}

		Widget motd = client.getWidget(WidgetInfo.LOGIN_CLICK_TO_PLAY_SCREEN_MESSAGE_OF_THE_DAY);
		if (motd != null && !motd.isHidden())
		{
			return;
		}
		updatePatchValues();
		updateGrowingAndHarvestablePatches();
	}

	private void updateAvailablePatches() {
		@Nullable
		Seed bestTreeSeed = null;
		if (farmingLevel >= 75) {
			bestTreeSeed = new Seed("Magic", List.of(new Payment(ItemID.COCONUT, "Coconut", 25)));
		} else if (farmingLevel >= 60) {
			bestTreeSeed = new Seed("Yew", List.of(new Payment(ItemID.CACTUS_SPINE, "Cactus spine", 10)));
		} else if (farmingLevel >= 45) {
			bestTreeSeed = new Seed("Maple", List.of(new Payment(ItemID.ORANGES5, "Oranges(5)", 1)));
		} else if (farmingLevel >= 30) {
			bestTreeSeed = new Seed("Willow", List.of(new Payment(ItemID.APPLES5, "Apples(5)", 1)));
		} else if (farmingLevel >= 15) {
			bestTreeSeed = new Seed("Acorn", List.of(new Payment(ItemID.TOMATOES5, "Tomatoes(5)", 1)));
		}

		if (bestTreeSeed != null) {
			availableTreePatches = new ArrayList(Arrays.asList(new Patch("West of Lumbridge Castle",12594, Varbits.FARMING_4771, bestTreeSeed), new Patch("Varrock Castle courtyard", 12854, Varbits.FARMING_4771, bestTreeSeed), new Patch("Falador Park", 11828, Varbits.FARMING_4771, bestTreeSeed), new Patch("Taverly",11573, Varbits.FARMING_4771, bestTreeSeed), new Patch("Tree Gnome Stronghold", 9781, Varbits.FARMING_4771, bestTreeSeed)));
		}

		@Nullable
		Seed bestFruitTreeSeed = null;
		if (farmingLevel >= 81) {
			bestFruitTreeSeed = new Seed("Dragonfruit", List.of(new Payment(ItemID.COCONUT, "Coconut", 15)));
		} else if (farmingLevel >= 68) {
			bestFruitTreeSeed = new Seed("Palm tree", List.of(new Payment(ItemID.PAPAYA_FRUIT, "Papaya fruit", 15)));
		} else if (farmingLevel >= 57) {
			bestFruitTreeSeed = new Seed("Papaya", List.of(new Payment(ItemID.PINEAPPLE, "Pineapple", 10)));
		} else if (farmingLevel >= 51) {
			bestFruitTreeSeed = new Seed("Pineapple", List.of(new Payment(ItemID.WATERMELON, "Watermelon", 10)));
		} else if (farmingLevel >= 42) {
			bestFruitTreeSeed = new Seed("Curry", List.of(new Payment(ItemID.BANANAS5, "Bananas(5)", 5)));
		} else if (farmingLevel >= 39) {
			bestFruitTreeSeed = new Seed("Orange", List.of(new Payment(ItemID.STRAWBERRIES5, "Strawberries(5)", 3)));
		} else if (farmingLevel >= 33) {
			bestFruitTreeSeed = new Seed("Banana", List.of(new Payment(ItemID.APPLES5, "Apples(5)", 4)));
		} else if (farmingLevel >= 27) {
			bestFruitTreeSeed = new Seed("Apple", List.of(new Payment(ItemID.SWEETCORN, "Sweetcorn", 9)));
		}

		if (bestFruitTreeSeed != null) {
			availableFruitTreePatches = new ArrayList(Arrays.asList(new Patch("Tree Gnome Stronghold",9781, Varbits.FARMING_4772, bestFruitTreeSeed), new Patch("East of Catherby",11317, Varbits.FARMING_4771, bestFruitTreeSeed), new Patch("West of Tree Gnome maze", 9777, Varbits.FARMING_4771, bestFruitTreeSeed), new Patch("North of Brimhaven", 11058, Varbits.FARMING_4771, bestFruitTreeSeed)));
		}


		final Seed spiritTreeSeed = new Seed("Spirit", List.of(new Payment(ItemID.MONKEY_NUTS, "Monkey nuts", 5), new Payment(ItemID.MONKEY_BAR, "Monkey bar", 1), new Payment(ItemID.GROUND_TOOTH, "Ground tooth", 1)));
		final int hosidiusFavour = client.getVarbitValue(Varbits.KOUREND_FAVOR_HOSIDIUS);
		if (hosidiusFavour > 60) {
			if (farmingLevel >= 65) {
				availableTreePatches.add(new Patch("Farming Guild", 4922, Varbits.FARMING_7905, bestTreeSeed));
			}
			if (farmingLevel >= 85) {
				availableFruitTreePatches.add(new Patch("Farming Guild", 4922, Varbits.FARMING_7909, bestFruitTreeSeed));
				availableSpiritTreePatches.add(new Patch("Northern section of the Farming Guild", 4922, Varbits.FARMING_4771, spiritTreeSeed ));
				availableCelastrusTreePatches.add(new Patch("Farming Guild", 4922, Varbits.FARMING_7910, new Seed("Celastrus", List.of(new Payment(ItemID.POTATO_CACTUS, "Potato cactus", 8)))));
			}
		}

		final QuestState mourningsEndPartIQuestState = Quest.MOURNINGS_END_PART_I.getState(client);
		if (mourningsEndPartIQuestState == QuestState.FINISHED || mourningsEndPartIQuestState == QuestState.FINISHED) {
			availableFruitTreePatches.add(new Patch("Lletya", 9265, Varbits.FARMING_4771, bestFruitTreeSeed));
		}

		if (farmingLevel >= 83) {
			if (Quest.THE_FREMENNIK_TRIALS.getState(client) == QuestState.FINISHED) {
				availableSpiritTreePatches.add(new Patch("South-east Etceteria", 10300, Varbits.FARMING_4772, spiritTreeSeed));
			}
			availableSpiritTreePatches.add(new Patch("East of Port Sarim", 12082, Varbits.FARMING_4771, spiritTreeSeed));
			availableSpiritTreePatches.add(new Patch("North of Brimhaven", 11058, Varbits.FARMING_4772, spiritTreeSeed));
			availableSpiritTreePatches.add(new Patch("South-west of Hosidius", 6967, Varbits.FARMING_7904, spiritTreeSeed));
		}

		if (farmingLevel >= 35) {
			final Seed bestFossilIslandSeed = farmingLevel >= 55 ? new Seed("Mahogany", List.of(new Payment(ItemID.YANILLIAN_HOPS, "Yanillian hops", 25))) : new Seed("Teak", List.of(new Payment(ItemID.LIMPWURT_ROOT, "Limpwurt root", 15)));
			availableHardwoodTreePatches.add(new Patch("Fossil Island Mushroom Forest (west)", 14651, Varbits.FARMING_4773, bestFossilIslandSeed));
			availableHardwoodTreePatches.add(new Patch("Fossil Island Mushroom Forest (middle)", 14651, Varbits.FARMING_4772, bestFossilIslandSeed));
			availableHardwoodTreePatches.add(new Patch("Fossil Island Mushroom Forest (east)", 14651, Varbits.FARMING_4771, bestFossilIslandSeed));
		}
		if (farmingLevel >= 72) {
			availableCalquatTreePatches.add(new Patch("North of Tai Bwo Wannai", 11056, Varbits.FARMING_4771, new Seed("Calquat", List.of(new Payment(ItemID.POISON_IVY_BERRIES, "Poison ivy berries", 8)))));
		}
		if (farmingLevel >= 74) {
			ItemContainer itemContainer = client.getItemContainer(InventoryID.BANK);
			if (itemContainer != null)
			{
				List<Item> bankInventory = Arrays.asList(itemContainer.getItems());
				boolean canGrowCrystalTree = false;
				for (int i = 0; i < bankInventory.size(); i++) {
					final int itemId = bankInventory.get(i).getId();
					if (itemId == ItemID.CRYSTAL_ACORN || itemId == ItemID.CRYSTAL_SEEDLING || itemId == ItemID.CRYSTAL_SAPLING) {
						canGrowCrystalTree = true;
					}
				}
				if (canGrowCrystalTree) {
					availableCrystalTreePatches.add(new Patch("Prifddinas", 13151, Varbits.FARMING_4775, new Seed("Crystal", List.of())));
				}
			}
		}
		if (farmingLevel >= 90) {
			availableRedwoodTreePatches.add(new Patch("Farming guild", 4922, Varbits.FARMING_7907, new Seed("Redwood", List.of(new Payment(ItemID.DRAGONFRUIT, "Dragonfruit", 6)))));
		}
	}

	private int determineTimeToGrow(PatchType type, int value) {
		if (type == PatchType.TREE) {
			if (value == 8)
			{
				//Oak growing
				return 160 * 60;
			}
			if (value == 15)
			{
				//Willow growing
				return 240 * 60;
			}
			if (value == 24)
			{
				//Maple growing
				return 320 * 60;
			}
			if (value == 35)
			{
				//Yew growing
				return 400 * 60;
			}
			if (value == 48)
			{
				//Magic Tree growing
				return 480 * 60;
			}
		}

		if (type == PatchType.FRUIT_TREE) {
			if (value == 8)
			{
				//Apple Tree growing
				return 960 * 60;
			}
			if (value == 35) {
				//Banana Tree growing
				return 960 * 60;
			}
			if (value == 72) {
				//Orange Tree growing
				return 960 * 60;
			}
			if (value == 99) {
				//Curry Tree growing
				return 960 * 60;
			}
			if (value == 142) {
				//Pineapple Plant growing
				return 960 * 60;
			}
			if (value == 163) {
				//Papaya Tree growing
				return 960 * 60;
			}
			if (value == 200) {
				//Palm Tree growing
				return 960 * 60;
			}
			if (value == 227) {
				//Dragonfruit Tree growing
				return 960 * 60;
			}
		}

		if (type == PatchType.SPIRIT_TREE) {
			if (value == 8) {
				//Spirit Tree growing
				return 3840 * 60;
			}
		}

		if (type == PatchType.HARDWOOD_TREE) {
			if (value == 8) {
				//Teak Tree growing
				return 4480 * 60;
			}
			if (value == 30) {
				//Mahogany Tree growing
				return 5120 * 60;
			}
		}

		if (type == PatchType.CALQUAT_TREE) {
			if (value == 4) {
				//Calquat Tree growing
				return 1280 * 60;
			}
		}

		if (type == PatchType.CRYSTAL_TREE) {
			if (value == 8) {
				//Crystal Tree growing
				return 480 * 60;
			}
		}

		if (type == PatchType.CELASTRUS_TREE) {
			if (value == 8) {
				//Celastrus Tree growing
				return 800 * 60;
			}
		}

		if (type == PatchType.REDWOOD_TREE) {
			if (value == 8)
			{
				//Redwood Tree growing
				return 6400 * 60;
			}
		}

		//Weeds growing, tree harvestble, tree diseased, or tree dead
		return 0;
	}

	void updatePatchValues() {
		final int currentRegionId = client.getLocalPlayer().getWorldLocation().getRegionID();
		updatePatchValuesForPatchType(PatchType.TREE, availableTreePatches, currentRegionId);
		updatePatchValuesForPatchType(PatchType.FRUIT_TREE, availableFruitTreePatches, currentRegionId);
		updatePatchValuesForPatchType(PatchType.SPIRIT_TREE, availableSpiritTreePatches, currentRegionId);
		updatePatchValuesForPatchType(PatchType.HARDWOOD_TREE, availableHardwoodTreePatches, currentRegionId);
		updatePatchValuesForPatchType(PatchType.CALQUAT_TREE, availableCalquatTreePatches, currentRegionId);
		updatePatchValuesForPatchType(PatchType.CRYSTAL_TREE, availableCrystalTreePatches, currentRegionId);
		updatePatchValuesForPatchType(PatchType.CELASTRUS_TREE, availableCelastrusTreePatches, currentRegionId);
		updatePatchValuesForPatchType(PatchType.REDWOOD_TREE, availableRedwoodTreePatches, currentRegionId);
	}

	private void updatePatchValuesForPatchType(PatchType type, List<Patch> patches, int currentRegionId) {
		for (Patch patch: patches) {
			if (patch.getRegionId() != currentRegionId) {
				continue;
			}
			final long unixNow = Instant.now().getEpochSecond();
			final int varbitValue = client.getVarbitValue(patch.getVarbit());
			final String strVarbit = Integer.toString(varbitValue);
			final String key = patch.configKey();
			final String storedValue = configManager.getRSProfileConfiguration(TreeRunHelperConfig.CONFIG_GROUP, key);
			final int timeToGrow = determineTimeToGrow(type, varbitValue);
			String value;
			if (storedValue != null) {
				String[] parts = storedValue.split(":");
				final String oldVarbit = parts[0];
				if (!oldVarbit.equals(strVarbit)) {
					if (varbitValue > 3 && Integer.parseInt(oldVarbit) == 3) {
						//We just planted something!
						value = strVarbit + ":" + (unixNow + timeToGrow);
					} else {
						//We are ready to plant
						value = strVarbit + ":" + (unixNow + timeToGrow);
					}
				} else {
					continue;
				}
			} else {
				if (varbitValue == 3) {
					//We are ready to plant
					value = strVarbit + ":" + (unixNow + timeToGrow);
				} else {
					continue;
				}
			}
			configManager.setRSProfileConfiguration(TreeRunHelperConfig.CONFIG_GROUP, key, value);
		}
	}

	private void updateGrowingAndHarvestablePatches() {
		updateGrowingAndHarvestablePatchesFromPatchList(availableTreePatches);
		updateGrowingAndHarvestablePatchesFromPatchList(availableFruitTreePatches);
		updateGrowingAndHarvestablePatchesFromPatchList(availableSpiritTreePatches);
		updateGrowingAndHarvestablePatchesFromPatchList(availableHardwoodTreePatches);
		updateGrowingAndHarvestablePatchesFromPatchList(availableCalquatTreePatches);
		updateGrowingAndHarvestablePatchesFromPatchList(availableCrystalTreePatches);
		updateGrowingAndHarvestablePatchesFromPatchList(availableCelastrusTreePatches);
		updateGrowingAndHarvestablePatchesFromPatchList(availableHardwoodTreePatches);
	}

	private void updateGrowingAndHarvestablePatchesFromPatchList(List<Patch> patchList) {
		for (Patch patch: patchList) {
			final long unixNow = Instant.now().getEpochSecond();

			final String key = patch.configKey();
			final String storedValue = configManager.getRSProfileConfiguration(TreeRunHelperConfig.CONFIG_GROUP, key);
			if (storedValue != null) {
				String[] parts = storedValue.split(":");

				final int varBit = Integer.parseInt(parts[0]);
				if (varBit <= 3) {
					continue;
				}
				final Long harvestableUnixTime = Long.parseLong(parts[1]);
				final String patchKey = patch.configKey();
				if (unixNow < harvestableUnixTime) {
					if (!growingPatchesMap.containsKey(patchKey)) {
						growingPatchesMap.put(patchKey, harvestableUnixTime);
					}
					if (harvestablePatchKeys.contains(patchKey)) {
						//We might not need this conditional?
						harvestablePatchKeys.remove(patchKey);
					}
				} else {
					if (!harvestablePatchKeys.contains(patchKey)) {
						harvestablePatchKeys.add(patchKey);
					}
				}
			}
		}
	}

	@Subscribe
	public void onGameObjectSpawned(GameObjectSpawned event)
	{
	}

	@Subscribe
	public void onGameObjectDespawned(GameObjectDespawned event)
	{
	}

	@Subscribe
	public void onGroundObjectSpawned(GroundObjectSpawned event)
	{
	}

	@Subscribe
	public void onGroundObjectDespawned(GroundObjectDespawned event)
	{
	}

	@Subscribe
	public void onWallObjectSpawned(WallObjectSpawned event)
	{
	}

	@Subscribe
	public void onWallObjectDespawned(WallObjectDespawned event)
	{
	}

	@Subscribe
	public void onDecorativeObjectSpawned(DecorativeObjectSpawned event)
	{
	}

	@Subscribe
	public void onDecorativeObjectDespawned(DecorativeObjectDespawned event)
	{
	}

	private void onTileObject(Tile tile, TileObject oldObject, TileObject newObject)
	{
	}

	@Subscribe
	public void onNpcSpawned(NpcSpawned npcSpawned)
	{
	}

	@Subscribe
	public void onNpcDespawned(NpcDespawned npcDespawned)
	{
	}

	@Provides
	TreeRunHelperConfig provideConfig(ConfigManager configManager)
	{
		this.configManager = configManager;
		return configManager.getConfig(TreeRunHelperConfig.class);
	}
}

enum PatchType {
	TREE,
	FRUIT_TREE,
	SPIRIT_TREE,
	HARDWOOD_TREE,
	CALQUAT_TREE,
	CRYSTAL_TREE,
	CELASTRUS_TREE,
	REDWOOD_TREE
}

