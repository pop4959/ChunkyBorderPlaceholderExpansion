package org.popcraft.chunkyborder;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Arrays;

@SuppressWarnings("unused")
public class ChunkyBorderPlaceholderExpansion extends PlaceholderExpansion {
    private static final String IDENTIFIER = "chunkyborder";
    private static final String AUTHOR = "pop4959";
    private static final String VERSION = "1.0.1";
    private static final String NAME = "ChunkyBorder";
    private final ChunkyBorder chunkyBorder;

    public ChunkyBorderPlaceholderExpansion() {
        this.chunkyBorder = Bukkit.getServicesManager().load(ChunkyBorder.class);
    }

    @Override
    public @NonNull String getIdentifier() {
        return IDENTIFIER;
    }

    @Override
    public @NonNull String getAuthor() {
        return AUTHOR;
    }

    @Override
    public @NonNull String getVersion() {
        return VERSION;
    }

    @Override
    public @NonNull String getName() {
        return NAME;
    }

    @Override
    public @NonNull String getRequiredPlugin() {
        return NAME;
    }

    @Override
    public @Nullable String onRequest(final OfflinePlayer player, final @NonNull String params) {
        if (chunkyBorder == null) {
            return null;
        }
        final String[] tokens = params.split("_");
        if (tokens.length < 3 || !"border".equals(tokens[0])) {
            return null;
        }
        final String placeholder = tokens[1];
        final String world = String.join("_", Arrays.copyOfRange(tokens, 2, tokens.length));
        final BorderData borderData = chunkyBorder.getBorders().get(world);
        if ("exists".equals(placeholder)) {
            return Boolean.toString(borderData != null);
        }
        if (borderData == null) {
            return null;
        }
        return switch (placeholder) {
            case "world" -> borderData.getWorld();
            case "centerx" -> Double.toString(borderData.getCenterX());
            case "centerz" -> Double.toString(borderData.getCenterZ());
            case "radius", "radiusx" -> Double.toString(borderData.getRadiusX());
            case "radiusz" -> Double.toString(borderData.getRadiusZ());
            case "shape" -> borderData.getShape();
            case "wrap" -> borderData.getWrap();
            default -> null;
        };
    }
}
