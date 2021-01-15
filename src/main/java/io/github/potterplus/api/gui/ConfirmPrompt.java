package io.github.potterplus.api.gui;

import io.github.potterplus.api.gui.button.AutoGUIButton;
import io.github.potterplus.api.gui.button.GUIButton;
import io.github.potterplus.api.misc.ItemStackBuilder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

/**
 * TODO Write docs
 */
public abstract class ConfirmPrompt extends GUI {

    @Getter @Setter
    private Material infoMaterial, confirmMaterial, cancelMaterial;

    @Getter @Setter
    private String infoName, confirmName, cancelName;

    @Getter @Setter
    private List<String> infoLore, confirmLore, cancelLore;

    @Getter @Setter
    private Sound confirmSound, cancelSound;

    @Getter
    private boolean safelyClosed;

    public void setInfoItem(ItemStack item) {
        this.setInfoMaterial(item.getType());

        if (item.hasItemMeta() && item.getItemMeta() != null) {
            ItemMeta meta = item.getItemMeta();

            if (meta.hasDisplayName()) {
                this.setInfoName(meta.getDisplayName());
            }

            if (meta.hasLore()) {
                this.setInfoLore(meta.getLore());
            }
        }
    }

    public void setInfoItem(Supplier<ItemStack> item) {
        setInfoItem(item.get());
    }

    public ConfirmPrompt(String title) {
        super(title, 9);

        this.confirmSound = Sound.ENTITY_PLAYER_LEVELUP;
        this.cancelSound = Sound.UI_BUTTON_CLICK;

        this.confirmMaterial = Material.GREEN_STAINED_GLASS;
        this.cancelMaterial = Material.RED_STAINED_GLASS;

        this.infoMaterial = Material.NAME_TAG;
        this.infoName = "&cWhat is being confirmed?";
        this.infoLore = new ArrayList<>();

        this.confirmName = "&aConfirm";
        this.cancelName = "&cCancel";
        this.confirmLore = Collections.singletonList("&8> &7Click to confirm");
        this.cancelLore = Collections.singletonList("&8> &7Click to cancel");
    }

    public ConfirmPrompt(String title, ItemStack infoItem) {
        this(title);

        this.setInfoItem(infoItem);
    }

    public ConfirmPrompt(String title, Supplier<ItemStack> infoItem) {
        this(title, infoItem.get());
    }

    @Override
    public Inventory getInventory() {
        ItemStackBuilder confirmItem = ItemStackBuilder
                .start(getConfirmMaterial())
                .name(getConfirmName())
                .lore(getConfirmLore());
        ItemStackBuilder cancelItem = ItemStackBuilder
                .start(getCancelMaterial())
                .name(getCancelName())
                .lore(getCancelLore());
        ItemStackBuilder infoItem = ItemStackBuilder
                .start(getInfoMaterial())
                .name(getInfoName())
                .lore(getInfoLore());

        GUIButton confirmButton = new GUIButton(confirmItem);
        GUIButton cancelButton = new GUIButton(cancelItem);

        confirmButton.setListener(event -> {
            event.setCancelled(true);

            this.safelyClosed = true;

            this.onConfirm(event);

            HumanEntity human = event.getWhoClicked();

            human.closeInventory();

            if (human instanceof Player) {
                ((Player) human).playSound(human.getLocation(), confirmSound, 1F, 1F);
            }
        });

        cancelButton.setListener(event -> {
            event.setCancelled(true);

            this.safelyClosed = true;

            HumanEntity human = event.getWhoClicked();

            human.closeInventory();

            if (human instanceof Player) {
                Player player = (Player) human;

                player.playSound(player.getLocation(), cancelSound, 1F, 1F);

                this.onCancel(player);
            }
        });

        AutoGUIButton infoButton = new AutoGUIButton(infoItem);

        this.setButton(0, confirmButton);
        this.setButton(1, confirmButton);
        this.setButton(2, confirmButton);

        this.setButton(4, infoButton);

        this.setButton(6, cancelButton);
        this.setButton(7, cancelButton);
        this.setButton(8, cancelButton);

        return super.getInventory();
    }

    @Override
    public void onClose(InventoryCloseEvent event) {
        HumanEntity human = event.getPlayer();

        if (human instanceof Player) {
            Player player = (Player) human;

            player.playSound(player.getLocation(), cancelSound, 1F, 1F);

            this.onCancel(player);
        }
    }

    public abstract void onConfirm(InventoryClickEvent event);
    public abstract void onCancel(Player player);
}
