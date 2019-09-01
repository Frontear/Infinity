package org.frontear.framework.utils.system;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

import javax.management.OperationsException;
import java.io.*;
import java.lang.management.ManagementFactory;
import java.util.*;
import java.util.stream.Collectors;

public class LocalMachine {
	public boolean compareOS(byte os) {
		Preconditions
				.checkArgument(os == OperatingSystem.WINDOWS || os == OperatingSystem.LINUX || os == OperatingSystem.MACOSX || os == OperatingSystem.SOLARIS);

		if (ManagementFactory.getRuntimeMXBean().getInputArguments().stream()
				.noneMatch(x -> x.startsWith("-Dos.name="))) {
			final String name = System.getProperty("os.name");
			switch (os) {
				case OperatingSystem.WINDOWS:
					return name.contains("Windows");
				case OperatingSystem.LINUX:
					return name.contains("Linux");
				case OperatingSystem.MACOSX:
					return name.contains("OS X");
				case OperatingSystem.SOLARIS:
					return name.contains("Solaris") || name.contains("SunOS");
			}
		}

		throw new UnsupportedOperationException("Property \"os.name\" was overwritten in the JVM args"); // if os.name has been specified in the JVM args, we cannot guarantee the right result
	}

	public Map<Integer, String> getProcesses() {
		final Map<Integer, String> processes = Maps.newHashMap();
		final boolean windows = this.compareOS(OperatingSystem.WINDOWS);
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
						final String[] split = Arrays.stream(x.trim().split(" ")).map(String::trim).filter(s -> !s.isEmpty()).toArray(String[]::new); // yikes
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
}
