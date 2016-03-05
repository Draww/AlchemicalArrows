package me.choco.arrows;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import me.choco.arrows.events.ArrowHitEntity;
import me.choco.arrows.events.ArrowHitGround;
import me.choco.arrows.events.ArrowHitPlayer;
import me.choco.arrows.events.ProjectileShoot;
import me.choco.arrows.utils.ArrowRegistry;
import me.choco.arrows.utils.ItemRecipes;
import me.choco.arrows.utils.ParticleLoop;
import me.choco.arrows.utils.arrows.AirArrow;
import me.choco.arrows.utils.arrows.ConfusionArrow;
import me.choco.arrows.utils.arrows.DarknessArrow;
import me.choco.arrows.utils.arrows.DeathArrow;
import me.choco.arrows.utils.arrows.EarthArrow;
import me.choco.arrows.utils.arrows.EnderArrow;
import me.choco.arrows.utils.arrows.FireArrow;
import me.choco.arrows.utils.arrows.FrostArrow;
import me.choco.arrows.utils.arrows.LifeArrow;
import me.choco.arrows.utils.arrows.LightArrow;
import me.choco.arrows.utils.arrows.MagicArrow;
import me.choco.arrows.utils.arrows.MagneticArrow;
import me.choco.arrows.utils.arrows.NecroticArrow;
import me.choco.arrows.utils.arrows.WaterArrow;
import me.choco.arrows.utils.commands.GiveArrowCmd;
import me.choco.arrows.utils.commands.MainCmd;

public class AlchemicalArrows extends JavaPlugin{
	
	private static AlchemicalArrows instance;
	private ArrowRegistry registry;
	
	@SuppressWarnings("deprecation")
	@Override
	public void onEnable(){
		instance = this;
		registry = new ArrowRegistry(this);
		getConfig().options().copyDefaults(true);
		saveConfig();
		ItemRecipes recipes = new ItemRecipes(this);
		
		//Enable the particle loop
		new ParticleLoop(this).runTaskTimerAsynchronously(this, 0L, 1L);
		
		//Register events
		this.getLogger().info("Registering events");
		Bukkit.getPluginManager().registerEvents(new ArrowHitEntity(this), this);
		Bukkit.getPluginManager().registerEvents(new ArrowHitGround(this), this);
		Bukkit.getPluginManager().registerEvents(new ArrowHitPlayer(this), this);
		Bukkit.getPluginManager().registerEvents(new ProjectileShoot(this), this);
		//TODO Bukkit.getPluginManager().registerEvents(new PickupArrow(this), this);
		Bukkit.getPluginManager().registerEvents(recipes, this);
		
		//Register commands
		this.getLogger().info("Registering commands");
		this.getCommand("alchemicalarrows").setExecutor(new MainCmd(this));
			this.getCommand("alchemicalarrows").setTabCompleter(new MainCmd(this));
		this.getCommand("givearrow").setExecutor(new GiveArrowCmd(this));
			this.getCommand("givearrow").setTabCompleter(new GiveArrowCmd(this));
		
		//Register crafting recipes
		this.getLogger().info("Registering recipes");
		Bukkit.getServer().addRecipe(new ShapelessRecipe(recipes.airArrow).addIngredient(Material.ARROW).addIngredient(Material.FEATHER));
		Bukkit.getServer().addRecipe(new ShapelessRecipe(recipes.confusionArrow).addIngredient(Material.ARROW).addIngredient(Material.POISONOUS_POTATO));
		Bukkit.getServer().addRecipe(new ShapelessRecipe(recipes.darknessArrow).addIngredient(Material.ARROW).addIngredient(Material.COAL));
		Bukkit.getServer().addRecipe(new ShapelessRecipe(recipes.darknessArrow).addIngredient(Material.ARROW).addIngredient(Material.COAL, (byte) 1));
		Bukkit.getServer().addRecipe(new ShapelessRecipe(recipes.deathArrow).addIngredient(Material.ARROW).addIngredient(Material.SKULL_ITEM, (byte) 1));
		Bukkit.getServer().addRecipe(new ShapelessRecipe(recipes.earthArrow).addIngredient(Material.ARROW).addIngredient(Material.DIRT));
		Bukkit.getServer().addRecipe(new ShapelessRecipe(recipes.enderArrow).addIngredient(Material.ARROW).addIngredient(Material.EYE_OF_ENDER));
		Bukkit.getServer().addRecipe(new ShapelessRecipe(recipes.fireArrow).addIngredient(Material.ARROW).addIngredient(Material.FIREBALL));
		Bukkit.getServer().addRecipe(new ShapelessRecipe(recipes.frostArrow).addIngredient(Material.ARROW).addIngredient(Material.SNOW_BALL));
		Bukkit.getServer().addRecipe(new ShapelessRecipe(recipes.lifeArrow).addIngredient(Material.ARROW).addIngredient(Material.SPECKLED_MELON));
		Bukkit.getServer().addRecipe(new ShapelessRecipe(recipes.lightArrow).addIngredient(Material.ARROW).addIngredient(Material.GLOWSTONE_DUST));
		Bukkit.getServer().addRecipe(new ShapelessRecipe(recipes.magicArrow).addIngredient(Material.ARROW).addIngredient(Material.BLAZE_POWDER));
		Bukkit.getServer().addRecipe(new ShapelessRecipe(recipes.magneticArrow).addIngredient(Material.ARROW).addIngredient(Material.IRON_INGOT));
		Bukkit.getServer().addRecipe(new ShapelessRecipe(recipes.necroticArrow).addIngredient(Material.ARROW).addIngredient(Material.ROTTEN_FLESH));
		Bukkit.getServer().addRecipe(new ShapelessRecipe(recipes.waterArrow).addIngredient(Material.ARROW).addIngredient(Material.WATER_BUCKET));
		
		//Arrow registry
		this.getLogger().info("Registering all basic AlchemicalArrow arrows");
		ArrowRegistry.registerAlchemicalArrow(recipes.airArrow, AirArrow.class);
		ArrowRegistry.registerAlchemicalArrow(recipes.confusionArrow, ConfusionArrow.class);
		ArrowRegistry.registerAlchemicalArrow(recipes.darknessArrow, DarknessArrow.class);
		ArrowRegistry.registerAlchemicalArrow(recipes.deathArrow, DeathArrow.class);
		ArrowRegistry.registerAlchemicalArrow(recipes.earthArrow, EarthArrow.class);
		ArrowRegistry.registerAlchemicalArrow(recipes.enderArrow, EnderArrow.class);
		ArrowRegistry.registerAlchemicalArrow(recipes.fireArrow, FireArrow.class);
		ArrowRegistry.registerAlchemicalArrow(recipes.frostArrow, FrostArrow.class);
		ArrowRegistry.registerAlchemicalArrow(recipes.lifeArrow, LifeArrow.class);
		ArrowRegistry.registerAlchemicalArrow(recipes.lightArrow, LightArrow.class);
		ArrowRegistry.registerAlchemicalArrow(recipes.magicArrow, MagicArrow.class);
		ArrowRegistry.registerAlchemicalArrow(recipes.magneticArrow, MagneticArrow.class);
		ArrowRegistry.registerAlchemicalArrow(recipes.necroticArrow, NecroticArrow.class);
		ArrowRegistry.registerAlchemicalArrow(recipes.waterArrow, WaterArrow.class);
	}
	
	@Override
	public void onDisable() {
		registry.getRegisteredArrows().clear();
		ArrowRegistry.getArrowRegistry().clear();
	}
	
	public static AlchemicalArrows getPlugin(){
		return instance;
	}
	
	public ArrowRegistry getArrowRegistry(){
		return registry;
	}
}

/* Legacy Arrow Handling
 * https://bitbucket.org/2008Choco/alchemicalarrows/src/1c1778740b822d7ae1fa0dc963a99703b433296b/src/me/choco/arrows/utils/ArrowHandling.java?fileviewer=file-view-default
 */

/* 2.0 CHANGELOG:
 * 
 */

/* TODO: WHAT'S LEFT TO COMPLETE
 * Fix WorldGuard support (Wait for sk89q to update to 1.9)
 * Fix arrows not working when being picked up (TODO: Wait for ticket request "PlayerPickupArrowEvent" response, hope for new event)
 * Set up Metrics
 */