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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public class MockSystemOutputShould {

	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
	private static final String ANOTHER_MESSAGE = "TEST2";
	private static final String MESSAGE = "TEST";

	@Test
	public void capture_a_single_line_of_the_system_output() {
		final MockSystemOutput systemOutput = MockSystemOutput.inject();
		System.out.println(MESSAGE);

		assertThat(systemOutput.toString(), equalTo(addLineSeparatorTo(MESSAGE)));
	}

	@Test
	public void capture_a_multiple_lines_of_the_system_output() {
		final MockSystemOutput systemOutput = MockSystemOutput.inject();
		System.out.println(MESSAGE);
		System.out.println(ANOTHER_MESSAGE);

		assertThat(systemOutput.toString(), equalTo(addLineSeparatorTo(MESSAGE, ANOTHER_MESSAGE)));
	}

	@Test
	public void should_not_reset_mock_output_if_did_not_inject_it_before() {
		assertThat(new MockSystemOutput().undoInjection(), is(false));
	}

	@Test
	public void should_put_the_original_output_back() {
		final MockSystemOutput systemOutput = MockSystemOutput.inject();
		assertThat(systemOutput.undoInjection(), is(true));
	}

	@Test
	public void putting_the_original_output_back_should_output_to_sys_out() {
		MockSystemOutput.inject().undoInjection();

		capture_a_single_line_of_the_system_output();
	}

	private String addLineSeparatorTo(final String... string) {
		final StringBuffer stringBuffer = new StringBuffer();
		for (final String current : string) {
			stringBuffer.append(current);
			stringBuffer.append(LINE_SEPARATOR);
		}
		return stringBuffer.toString();
	}

}
