package me.choco.arrows.events;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.projectiles.BlockProjectileSource;

import me.choco.arrows.AlchemicalArrows;
import me.choco.arrows.api.AlchemicalArrow;
import me.choco.arrows.registry.ArrowRegistry;
import me.choco.arrows.utils.ConfigOption;

public class CustomDeathMessage implements Listener{

	private static final String playerDeathMessage = "%player% was shot by %killer% using a %type% arrow";
	private static final String skeletonDeathMessage = "%player% was shot by a skeleton using a %type% arrow";
	private static final String blockSourceDeathMessage = "%player% was shot using a %type% arrow";

	private final ArrowRegistry arrowRegistry;
	public CustomDeathMessage(AlchemicalArrows plugin){
		this.arrowRegistry = plugin.getArrowRegistry();
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerDeath(PlayerDeathEvent event){
		if (!ConfigOption.CUSTOM_DEATH_MESSAGES) return;
		if (!(event.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent)) return;
		
		EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event.getEntity().getLastDamageCause();
		if (!(e.getDamager() instanceof Arrow)) return;
		
		Arrow arrow = (Arrow) e.getDamager();
		if (!arrowRegistry.isAlchemicalArrow(arrow)) return;

		AlchemicalArrow aarrow = arrowRegistry.getAlchemicalArrow(arrow);
		
		String killedName = event.getEntity().getName();
		String arrowType = aarrow.getName().toLowerCase();
		
		if (arrow.getShooter() instanceof Player){
			Player killer = (Player) arrow.getShooter();
			event.setDeathMessage(playerDeathMessage.replace("%player%", killedName).replace("%killer%", killer.getName()).replace("%type%", arrowType));
		}
		
		else if (arrow.getShooter() instanceof Skeleton)
			event.setDeathMessage(skeletonDeathMessage.replace("%player%", killedName).replace("%type%", arrowType));
		
		else if (arrow.getShooter() instanceof BlockProjectileSource)
			event.setDeathMessage(blockSourceDeathMessage.replace("%player%", killedName).replace("%type%", arrowType));
	}
}