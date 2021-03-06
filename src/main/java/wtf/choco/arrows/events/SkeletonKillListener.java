package wtf.choco.arrows.events;

import java.util.List;
import java.util.Random;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrow;
import wtf.choco.arrows.api.property.ArrowProperty;
import wtf.choco.arrows.registry.ArrowRegistry;

public class SkeletonKillListener implements Listener {
	
	private static final Random RANDOM = new Random();
	
	private final FileConfiguration config;
	
	public SkeletonKillListener(AlchemicalArrows plugin) {
		this.config = plugin.getConfig();
	}
	
	@EventHandler
	public void onKillSkeleton(EntityDeathEvent event) {
		LivingEntity entity = event.getEntity();
		
		if (!(entity instanceof Skeleton)) return;
		if ((RANDOM.nextInt(100) + 1) > config.getDouble("Skeletons.LootPercentage", 15.0)) return;
		
		List<ItemStack> drops = event.getDrops();
		drops.removeIf(i -> i.getType() == Material.ARROW);
		
		ItemStack toDrop = getWeightedRandom(RANDOM.nextInt(2) + 1);
		if (toDrop == null) return;
		
		drops.add(toDrop);
	}
	
	public ItemStack getWeightedRandom(int amount) {
		double totalWeight = 0;
		
		Set<AlchemicalArrow> arrows = ArrowRegistry.getRegisteredCustomArrows();
		for (AlchemicalArrow arrow : arrows) {
			totalWeight += arrow.getProperties().getPropertyValue(ArrowProperty.SKELETON_LOOT_WEIGHT);
		}
		
		ItemStack item = null;
		double randomValue = RANDOM.nextDouble() * totalWeight;
		
		for (AlchemicalArrow arrow : arrows) {
			randomValue -= arrow.getProperties().getPropertyValue(ArrowProperty.SKELETON_LOOT_WEIGHT);
			
			if (randomValue <= 0) {
				item = arrow.getItem();
				break;
			}
		}
		
		if (item == null) return null;
		
		item.setAmount(amount);
		return item;
	}
	
}