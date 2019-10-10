package org.frontear.framework.utils.system;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.net.URI;
import java.util.Arrays;
import java.util.Map;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.val;

@UtilityClass
public class LocalMachine {
    private final byte OS, ARCH;

    static {
        if (ManagementFactory.getRuntimeMXBean().getInputArguments().stream()
            .noneMatch(x -> x.startsWith("-Dos.name="))) {
            val name = System.getProperty("os.name");
            if (name.contains("Windows")) {
                OS = OperatingSystem.WINDOWS;
            }
            else if (name.contains("Linux")) {
                OS = OperatingSystem.LINUX;
            }
            else if (name.contains("OS X")) {
                OS = OperatingSystem.MACOSX;
            }
            else if (name.contains("Solaris") || name.contains("SunOS")) {
                OS = OperatingSystem.SOLARIS;
            }
            else {
                OS = OperatingSystem.UNSUPPORTED;
            }
        }
        else {
            throw new UnsupportedOperationException(
                "Property \"os.name\" was overwritten in the JVM args"); // if os.name has been specified manually in the JVM args, we cannot guarantee the right result
        }

        if (ManagementFactory.getRuntimeMXBean().getInputArguments().stream()
            .noneMatch(x -> x.startsWith("-Dos.arch="))) {
            val arch = System.getProperty("os.arch");
            if (arch.equals("amd64")) {
                ARCH = SystemArchitecture.x64;
            }
            else if (arch.equals("x86")) {
                ARCH = SystemArchitecture.x86;
            }
            else {
                ARCH = SystemArchitecture.UNSUPPORTED;
            }
        }
        else {
            throw new UnsupportedOperationException(
                "Property \"os.arch\" was overwritten in the JVM args");
        }
    }

    public Map<Integer, String> getProcesses() {
        val processes = Maps.<Integer, String>newHashMap();
        val windows = OS == OperatingSystem.WINDOWS;
        val process = Runtime.getRuntime().exec(windows ? "tasklist /fo csv /nh" : "ps -e");
        try (val reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            reader.lines().skip(1)
                .forEach(x -> { // the first line is usually just a line to explain the format
                    if (windows) {
                        // "name","id","type","priority","memory?"
                        val split = x.replace("\"", "").split(",");
                        processes.put(Integer.valueOf(split[1]), split[0]);
                    }
                    else {
                        // id tty time command
                        val split = Arrays.stream(x.trim().split(" ")).map(String::trim)
                            .filter(s -> !s.isEmpty())
                            .toArray(String[]::new); // yikes
                        processes.put(Integer.valueOf(split[0]), split[split.length - 1]);
                    }
                });
        }

        return processes;
    }

    public void kill(int id) {
        Runtime.getRuntime()
            .exec((OS == OperatingSystem.WINDOWS ? "taskkill /F /PID " : "kill -9 ") + id);
    }

    public String getClipboardContent() {
        val toolkit = Toolkit.getDefaultToolkit();
        return (String) toolkit.getSystemClipboard().getData(DataFlavor.stringFlavor);
    }

    public void setClipboardContent(@NonNull String content) {
        val toolkit = Toolkit.getDefaultToolkit();
        if (!content.isEmpty()) {
            val string = new StringSelection(content);
            toolkit.getSystemClipboard().setContents(string, string);
        }
    }

    public void openUrl(@NonNull String url) {
        val uri = new URI(
            url); // intentional, this will catch errors with the URL, even if Desktop isn't supported, it still plays a role
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().browse(uri); // this can lag a bit
        }
        else {
            val runtime = Runtime.getRuntime();
            if (compareOS(OperatingSystem.WINDOWS)) {
                runtime.exec("cmd /k start $url");
            }
            else if (compareOS(OperatingSystem.LINUX)) {
                runtime.exec("xdg-open $url");
            }
            else if (compareOS(OperatingSystem.MACOSX)) {
                runtime.exec("open $url");
            }
            else if (compareOS(OperatingSystem.SOLARIS)) {
                runtime.exec(
                    "/usr/dt/bin/sdtwebclient -b $url"); // https://trac.sagemath.org/ticket/4979
            }
            else {
                new UnsupportedOperationException("Couldn't open url $url").printStackTrace();
            }
        }
    }

    public boolean compareOS(byte os) {
        Preconditions
            .checkArgument(os == OperatingSystem.WINDOWS || os == OperatingSystem.LINUX
                || os == OperatingSystem.MACOSX || os == OperatingSystem.SOLARIS
                || os == OperatingSystem.UNSUPPORTED);

        return os == OS;
    }

    public boolean compareArch(byte arch) {
        Preconditions
            .checkArgument(arch == SystemArchitecture.x64 || arch == SystemArchitecture.x86
                || arch == SystemArchitecture.UNSUPPORTED);

        return arch == ARCH;
    }
}
