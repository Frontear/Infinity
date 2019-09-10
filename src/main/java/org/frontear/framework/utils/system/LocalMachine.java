package org.frontear.framework.utils.system;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.awt.*;
import java.awt.datatransfer.*;
import java.io.*;
import java.lang.management.ManagementFactory;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Map;

@UtilityClass public class LocalMachine {
	private static final byte OS, ARCH;

	static {
		if (ManagementFactory.getRuntimeMXBean().getInputArguments().stream()
				.noneMatch(x -> x.startsWith("-Dos.name="))) {
			final String name = System.getProperty("os.name");
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
			throw new UnsupportedOperationException("Property \"os.name\" was overwritten in the JVM args"); // if os.name has been specified manually in the JVM args, we cannot guarantee the right result
		}

		if (ManagementFactory.getRuntimeMXBean().getInputArguments().stream().noneMatch(x -> x.startsWith("-Dos.arch="))) {
			final String arch = System.getProperty("os.arch");
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
			throw new UnsupportedOperationException("Property \"os.arch\" was overwritten in the JVM args");
		}
	}

	public Map<Integer, String> getProcesses() {
		final Map<Integer, String> processes = Maps.newHashMap();
		final boolean windows = OS == OperatingSystem.WINDOWS;
		try {
			final Process process = Runtime.getRuntime().exec(windows ? "tasklist /fo csv /nh" : "ps -e");
			try (final BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
				reader.lines().skip(1).forEach(x -> { // the first line is usually just a line to explain the format
					if (windows) {
						// "name","id","type","priority","memory?"
						final String[] split = x.replace("\"", "").split(",");
						processes.put(Integer.valueOf(split[1]), split[0]);
					}
					else {
						// id tty time command
						final String[] split = Arrays.stream(x.trim().split(" ")).map(String::trim)
								.filter(s -> !s.isEmpty()).toArray(String[]::new); // yikes
						processes.put(Integer.valueOf(split[0]), split[split.length - 1]);
					}
				});
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return processes;
	}

	public void kill(int id) {
		try {
			Runtime.getRuntime().exec((OS == OperatingSystem.WINDOWS ? "taskkill /F /PID " : "kill -9 ") + id);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getClipboardContent() {
		final Toolkit toolkit = Toolkit.getDefaultToolkit();
		try {
			return (String) toolkit.getSystemClipboard().getData(DataFlavor.stringFlavor);
		}
		catch (UnsupportedFlavorException | IOException e) {
			e.printStackTrace();
		}

		return "";
	}

	public void setClipboardContent(@NonNull String content) {
		final Toolkit toolkit = Toolkit.getDefaultToolkit();
		if (!content.isEmpty()) {
			final StringSelection string = new StringSelection(content);
			toolkit.getSystemClipboard().setContents(string, string);
		}
	}

	@SneakyThrows({ URISyntaxException.class, IOException.class }) public void openUrl(@NonNull String url) {
		final URI uri = new URI(url); // intentional, this will catch errors with the URL, even if Desktop isn't supported, it still plays a role
		if (Desktop.isDesktopSupported()) {
			Desktop.getDesktop().browse(uri); // this can lag a bit
		}
		else {
			final Runtime runtime = Runtime.getRuntime();
			if (compareOS(OperatingSystem.WINDOWS)) {
				runtime.exec("cmd /k start " + url);
			}
			else if (compareOS(OperatingSystem.LINUX)) {
				runtime.exec("xdg-open " + url);
			}
			else if (compareOS(OperatingSystem.MACOSX)) {
				runtime.exec("open " + url);
			}
			else if (compareOS(OperatingSystem.SOLARIS)) {
				runtime.exec("/usr/dt/bin/sdtwebclient -b " + url); // https://trac.sagemath.org/ticket/4979
			}
			else {
				new UnsupportedOperationException("Couldn't open url " + url).printStackTrace();
			}
		}
	}

	public boolean compareOS(byte os) {
		Preconditions
				.checkArgument(os == OperatingSystem.WINDOWS || os == OperatingSystem.LINUX || os == OperatingSystem.MACOSX || os == OperatingSystem.SOLARIS || os == OperatingSystem.UNSUPPORTED);

		return os == OS;
	}

	public boolean compareArch(byte arch) {
		Preconditions.checkArgument(arch == SystemArchitecture.x64 || arch == SystemArchitecture.x86 || arch == SystemArchitecture.UNSUPPORTED);

		return arch == ARCH;
	}
}
