package me.karamujo.regionprotection.loaders;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import java.io.IOException;
import java.lang.reflect.Field;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Enzo
 */
public class CommandLoader {

    public static void load(JavaPlugin main, String packageName) {
        try {
            ImmutableSet<ClassPath.ClassInfo> classes = ClassPath.from(main.getClass().getClassLoader()).getAllClasses();
            classes.stream().filter(clazz -> clazz.getPackageName().equalsIgnoreCase(packageName)).forEach((ClassPath.ClassInfo clazz) -> {
                Class<?> claszLoaded = clazz.load();
                if (!Command.class.isAssignableFrom(claszLoaded)) {
                    return;
                }
                register(claszLoaded);
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void register(Class<?> clasz) {
        try {
            Command command = (Command) clasz.newInstance();
            Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            field.setAccessible(true);
            CommandMap map = (CommandMap) field.get(Bukkit.getServer());
            map.register(command.getName(), command);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException ex) {
            ex.printStackTrace();
        }
    }

}
