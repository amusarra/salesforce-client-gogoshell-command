/**
 * 
 */

package it.dontesta.labs.liferay.salesforce.client.command.configuration;

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

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

import aQute.bnd.annotation.metatype.Meta;

/**
 * @author Antonio Musarra <antonio.musarra@gmail.com>
 */
@ExtendedObjectClassDefinition(
	category = "Salesforce"
)
@Meta.OCD(
	id = "it.dontesta.labs.liferay.salesforce.client.command.configuration.SalesforceClientCommandConfiguration",
	localization = "content/Language",
	name = "salesforce.client.command.configuration.name"
)
public interface SalesforceClientCommandConfiguration {

	@Meta.AD(
		deflt = "https://login.salesforce.com/services/Soap/u/40.0", 
		description = "Setting the Salesforce endpoint", 
		required = false)
	public String authEndpoint();

	@Meta.AD(
		deflt = "/tmp/traceSalesforcePartner.log", 
		description = "Setting full path of the trace file", 
		required = false)
	public String traceFile();

	@Meta.AD(
		deflt = "true", 
		description = "Setting true if trace message", 
		required = false)
	public boolean traceMessage();

	@Meta.AD(
		deflt = "true", 
		description = "Setting true if trace message pretty", 
		required = false)
	public boolean prettyPrintXml();
}
