package com.gmaur.legacycode.legacyutils.output;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class MockSystemOutput {
	private final StringBuilder stringBuilder= new StringBuilder();
	private boolean hasBeenReplaced;
	private static final PrintStream SYSTEM_OUT = System.out;

	public void write(final int b) {
		stringBuilder.append((char) b);
	}

	@Override
	public String toString(){
		return stringBuilder.toString();
	}

	public static MockSystemOutput inject() {
		final MockSystemOutput mockOutput = new MockSystemOutput();

		final PrintStream outputPrintStream = new PrintStream(new OutputStream() {

			@Override
			public void write(final int b) throws IOException {
				mockOutput.write(b);

			}
		});
		setNewOutput(outputPrintStream);
		mockOutput.hasBeenReplaced = true;
		return mockOutput;
	}

	private static void setNewOutput(final PrintStream mockOutput2) {
		System.setOut(mockOutput2);
	}


	public boolean undoInjection() {
		if (hasBeenReplaced) {
			setNewOutput(SYSTEM_OUT);
			hasBeenReplaced = false;
			return true;
		}
		return false;
	}
}