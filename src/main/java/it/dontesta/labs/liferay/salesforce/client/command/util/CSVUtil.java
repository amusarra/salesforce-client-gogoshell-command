/*-
 * #%L
 * salesforce-client-command
 * %%
 * Copyright (C) 2019 Antonio Musarra's Blog
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

import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.util.List;

import org.osgi.framework.BundleContext;

/**
 * @author Antonio Musarra
 */
public final class CSVUtil {

	public static URL getCSVURL(BundleContext bundleContext) {
		return bundleContext.getBundle(
		).getResource(
			"data/salesforceusers.csv"
		);
	}

	public static <T> List<T> readCSVToModel(
			BundleContext bundleContext, Class<T> classType)
		throws IOException {

		BeanListProcessor<T> rowProcessor;
		rowProcessor = new BeanListProcessor<>(classType);

		CsvParserSettings parserSettings = new CsvParserSettings();

		parserSettings.getFormat(
		).setLineSeparator(
			"\n"
		);
		parserSettings.setProcessor(rowProcessor);
		parserSettings.getFormat(
		).setDelimiter(
			";"
		);
		parserSettings.setHeaderExtractionEnabled(true);

		CsvParser parser = new CsvParser(parserSettings);

		try (InputStream inputStream = getCSVURL(
				bundleContext
			).openStream()) {

			parser.parse(inputStream);

			return rowProcessor.getBeans();
		}
	}

	private CSVUtil() {
		throw new IllegalStateException("This is a final class");
	}

}