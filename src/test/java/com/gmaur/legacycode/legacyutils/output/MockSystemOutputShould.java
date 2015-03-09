/*
 *	LegacyUtils is a set of tools for dealing with legacy code 
 *
 *	Copyright (C) 2014 G Maur (gmaur.com)
 *
 *	subject to terms and condition provided in LICENSE
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
