package com.example;

import com.google.inject.Provides;

import javax.annotation.Nullable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.*;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@PluginDescriptor(
	name = "Tree Run Helper"
)
public class TreeRunHelper extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private ExampleConfig config;

	Integer farmingLevel;
	ArrayList<Patch> availableTreePatches = new ArrayList();
	ArrayList<Patch> availableFruitTreePatches = new ArrayList();
	ArrayList<Patch> availableSpiritTreePatches = new ArrayList();
	ArrayList<Patch> availableSpecialTreePatches = new ArrayList();

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
		System.out.println("asdf");
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
				availableSpecialTreePatches.add(new Patch("Farming Guild", 4922, Varbits.FARMING_7910, new Seed("Celastrus", List.of(new Payment(ItemID.POTATO_CACTUS, "Potato cactus", 8)))));
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
			availableSpecialTreePatches.add(new Patch("Fossil Island Mushroom Forest (west)", 14651, Varbits.FARMING_4773, bestFossilIslandSeed));
			availableSpecialTreePatches.add(new Patch("Fossil Island Mushroom Forest (middle)", 14651, Varbits.FARMING_4772, bestFossilIslandSeed));
			availableSpecialTreePatches.add(new Patch("Fossil Island Mushroom Forest (east)", 14651, Varbits.FARMING_4771, bestFossilIslandSeed));
		}
		if (farmingLevel >= 72) {
			availableSpecialTreePatches.add(new Patch("North of Tai Bwo Wannai", 11056, Varbits.FARMING_4771, new Seed("Calquat", List.of(new Payment(ItemID.POISON_IVY_BERRIES, "Poison ivy berries", 8)))));
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
					availableSpecialTreePatches.add(new Patch("Prifddinas", 13151, Varbits.FARMING_4775, new Seed("Crystal", List.of())));
				}
			}
		}
		if (farmingLevel >= 90) {
			availableSpecialTreePatches.add(new Patch("Farming guild", 4922, Varbits.FARMING_7907, new Seed("Redwood", List.of(new Payment(ItemID.DRAGONFRUIT, "Dragonfruit", 6)))));
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
	ExampleConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(ExampleConfig.class);
	}
}
