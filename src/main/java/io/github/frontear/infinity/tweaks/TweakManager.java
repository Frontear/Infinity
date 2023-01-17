package io.github.frontear.infinity.tweaks;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import io.github.frontear.infinity.tweaks.impl.AutoClicker;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class TweakManager {
    private static final Map<Class<? extends AbstractTweak>, AbstractTweak> tweaks = new HashMap<>(); // TODO: memory implications with high object counts
    private static final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().excludeFieldsWithModifiers(Modifier.TRANSIENT).setPrettyPrinting().serializeNulls().create(); // https://stackoverflow.com/a/33666187/9091276
    private static final Path config = FabricLoader.getInstance().getConfigDir().resolve("infinity.json");

    static {
        loadTweaks();
        if (loadConfig()) {
            Runtime.getRuntime().addShutdownHook(new Thread(TweakManager::saveConfig));
        }
    }

    public static void handleKeyBinds(int key, int action) {
        var client = Minecraft.getInstance();

        if (client.screen == null && action == GLFW.GLFW_PRESS) {
            for (var tweak : tweaks.values()) {
                if (tweak.keybind == key) {
                    tweak.toggle();
                }
            }
        }
    }

    public static boolean isTweakEnabled(Class<? extends AbstractTweak> type) {
        if (tweaks.containsKey(type)) {
            return tweaks.get(type).enabled;
        }

        throw new NoSuchElementException("Cannot find the specified tweak (" + type.getSimpleName() + "). You may have forgotten to initialize it in the static constructor.");
    }

    // pulling from what I did in Efkolia's JavaExecutable
    private static void loadTweaks() {
        try {
            var source = Paths.get(TweakManager.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            var jar = source.getFileName().toString().endsWith(".jar");

            var system = jar ? FileSystems.newFileSystem(source) : FileSystems.getDefault();
            var pkg = AutoClicker.class.getPackageName();
            var path = Paths.get("", pkg.split("\\."));

            try (var walk = Files.walk(system.getPath((jar ? path : source.resolve(path)).toString())).skip(1)) { // skip directory itself
                walk.forEach(x -> {
                    var clazz = x.getFileName().toString();
                    var name = pkg + "." + clazz.substring(0, clazz.length() - ".class".length());

                    try {
                        // set to null initially since gson populates them, and if not, it will populate later
                        tweaks.put((Class<? extends AbstractTweak>) Class.forName(name), null);
                    }
                    catch (ClassNotFoundException e) {
                        throw new Error(e); // should NOT happen
                    }
                });
            }
        }
        catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean loadConfig() {
        if (Files.exists(config)) {
            try (var reader = Files.newBufferedReader(config)) {
                var map = gson.fromJson(reader, JsonObject.class);

                if (map != null) {
                    for (var key : tweaks.keySet()) {
                        var obj = map.get(key.getSimpleName());
                        tweaks.putIfAbsent(key, gson.fromJson(obj, key));
                    }
                }
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        for (var key : tweaks.keySet()) {
            try {
                if (tweaks.get(key) == null) { // different from putIfAbsent because constructor.newInstance doesn't need to be called _each_ time
                    tweaks.put(key, key.getConstructor().newInstance());
                }
            }
            catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        }

        return true;
    }

    private static void saveConfig() {
        try (var writer = Files.newBufferedWriter(config)) {
            var map = new JsonObject();

            for (var key : tweaks.keySet()) {
                map.add(key.getSimpleName(), gson.toJsonTree(tweaks.get(key)));
            }

            gson.toJson(map, writer);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
