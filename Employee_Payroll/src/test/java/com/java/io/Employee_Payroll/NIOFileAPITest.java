package com.java.io.Employee_Payroll;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

class NIOFileAPITest {
	private static String HOME = System.getProperty("user.home");
	private static String PLAY_WITH_NIO = "TempPlayGround";

	@Test
	public void givenPathWhenCheckedThenConfirm() throws IOException {

		// Check File Exists
		Path homePath = Paths.get(HOME);
		assertTrue(Files.exists(homePath));

		// Delete File and Check File Not Exists
		Path playPath = Paths.get(HOME + "/" + PLAY_WITH_NIO);
		if (Files.exists(playPath))
			FileUtils.deleteFiles(playPath.toFile());
		assertTrue(Files.notExists(playPath));
		
		// Create Directory
		Files.createDirectory(playPath);
		assertTrue(Files.exists(playPath));
		
		//Create File
		IntStream.range(1, 10).forEach(cntr -> {
			Path tempfile = Paths.get(playPath + "/temp" + cntr);
			assertTrue(Files.notExists(tempfile));
			try {
				Files.createFile(tempfile);
			} catch (IOException e) {
				
			}
			assertTrue(Files.exists(tempfile));
		});
		
		// List Files, Directories as well as Files with Extension
		Files.list(playPath).filter(Files::isRegularFile).forEach(System.out::println);
		Files.newDirectoryStream(playPath).forEach(System.out::println);
		Files.newDirectoryStream(playPath, path -> path.toFile().isFile() && path.toString().startsWith("temp")).forEach(System.out::println);
	}
	
	@Test
	public void givenDirectoryWhenWatchedListsAllTheActivities() throws IOException {
		Path dir = Paths.get(HOME + "/" + PLAY_WITH_NIO);
		Files.list(dir).filter(Files::isRegularFile).forEach(System.out::println);
		new Java8WatchService(dir).processEvents();
	}
}
