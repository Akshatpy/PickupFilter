package cdn.me.pickupfilter;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.InventoryManager;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.SlotPos;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;

public class PickupFilter extends JavaPlugin implements Listener {

    private final InventoryManager inventoryManager = new InventoryManager(this);

    @Override
    public void onEnable() {
        inventoryManager.init();
        getServer().getPluginManager().registerEvents(this, this);
        // Your other initialization code
    }

    @EventHandler
    public void onCommandPreprocess(PlayerCommandPreprocessEvent event) {
        String command = event.getMessage().toLowerCase();

        if (command.equals("/pickupfilter")) {
            openPickupFilterGUI(event.getPlayer());
            event.setCancelled(true);
        } else if (command.equals("/pickupfilter reload")) {
            reloadConfig();
            // Reload your configuration logic
            event.getPlayer().sendMessage("Pickup Filter configuration reloaded.");
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        if (isBannedItem(event.getItem().getItemStack())) {
            event.setCancelled(true);
        }
    }

    private void openPickupFilterGUI(Player player) {
        SmartInventory.builder()
                .manager(inventoryManager)
                .title("Pickup Filter")
                .provider(new PickupFilterProvider())
                .size(6, 9)
                .build()
                .open(player);
    }

    private boolean isBannedItem(ItemStack itemStack) {
        // Implement your logic to check if the item is banned
        // This method will depend on how you store and manage banned items in your plugin
        return false; // Replace with your logic
    }
}

class PickupFilterProvider implements InventoryProvider {

    @Override
    public void init(Player player, InventoryContents contents) {
        // Set up the initial design of the GUI
        contents.fillBorders(ClickableItem.empty(new ItemStack(Material.STAINED_GLASS_PANE)));
        contents.set(1, 4, ClickableItem.empty(new ItemStack(Material.DIAMOND_SWORD)));
        contents.set(2, 4, ClickableItem.empty(new ItemStack(Material.IRON_CHESTPLATE)));
        // Add more items based on your needs
    }

    @Override
    public void update(Player player, InventoryContents contents) {
        // Update the GUI if needed
    }


}
