/*-
 * #%L
 * salesforce-client-command
 * %%
 * Copyright (C) 2017 Antonio Musarra's Blog
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * #L%
 */

package it.dontesta.labs.liferay.salesforce.client.command.util;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * @author amusarra
 *
 */
public final class Console {
	public static void print(String msg) {
		print(msg, "green");
	}

	public static void print(String msg, String color) {
		System.out.print(ansi().render("@|" + color + " " + msg + " |@"));
		System.out.flush();
	}

	public static void println(String msg) {
		println(msg, "green");
	}

	public static void println(String msg, String color) {
		System.out.println(ansi().render("@|" + color + " " + msg + " |@"));
		System.out.flush();
	}

}
