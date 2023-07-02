package com.example;

import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.PluginPanel;

import javax.swing.*;
import java.awt.*;

public class TreeRunHelperPanel extends PluginPanel {
    private JButton herbRunButton;
    private ItemManager itemManager;
    private TreeRunHelper plugin;

    public TreeRunHelperPanel(TreeRunHelper plugin) {
        this.plugin = plugin;


        herbRunButton = new JButton("Herb Run");
        add(herbRunButton, BorderLayout.NORTH);
    }
}