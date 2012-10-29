package server;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {

	public static final String logName = "log.txt";
	public FileWriter fStream;
	public BufferedWriter fOut;

	/*
	 * There is a Logger class in the Java API but it seems way to involved for
	 * what we want here
	 */
	public Logger() {

		try {
			fStream = new FileWriter(logName); // overwrites log file every time
			fOut = new BufferedWriter(fStream);
		} catch (Exception e) {
			System.out.println("Error creating log file");
		}

	}

	/*
	 * This method takes a line of text and writes it to the log file followed
	 * by a new line
	 * 
	 * @param line Line of text to write to the log file
	 */
	public void writeln(String line) {
		try {
			fOut.write(line);
			fOut.newLine();
		} catch (IOException e) {
			System.out.println("Error writing to log file");
		}
	}

	/*
	 * This method is in here because the server should probably close the log
	 * file after its done with it
	 */
	public void closeLog() {
		try {
			fOut.close();
			fStream.close();
		} catch (IOException e) {
			System.out.println("Error closing log file");
		}
	}
}
