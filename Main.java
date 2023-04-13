import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

    private int spawnX;
    private int spawnY;
    private int spawnZ;
    private int protectionRadius;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    private void loadConfig() {
        FileConfiguration config = getConfig();
        spawnX = config.getInt("spawn.x");
        spawnY = config.getInt("spawn.y");
        spawnZ = config.getInt("spawn.z");
        protectionRadius = config.getInt("protection-radius");
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Location blockLocation = event.getBlock().getLocation();
        if (isWithinSpawnProtection(blockLocation)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Location blockLocation = event.getBlock().getLocation();
        if (isWithinSpawnProtection(blockLocation)) {
            event.setCancelled(true);
        }
    }

    private boolean isWithinSpawnProtection(Location location) {
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();
        double distance = Math.sqrt(Math.pow(x - spawnX, 2) + Math.pow(y - spawnY, 2) + Math.pow(z - spawnZ, 2));
        return distance <= protectionRadius;
    }
}
