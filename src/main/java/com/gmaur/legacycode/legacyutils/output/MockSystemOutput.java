/*
 *	LegacyUtils is a set of tools for dealing with legacy code 
 *
 *	Copyright (C) 2014 G Maur (gmaur.com)
 *
 *	This program is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *	
 *	This program is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *	
 *	You should have received a copy of the GNU General Public License
 *	along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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