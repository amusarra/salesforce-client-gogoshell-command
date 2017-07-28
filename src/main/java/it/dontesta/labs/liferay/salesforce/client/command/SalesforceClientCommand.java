package it.dontesta.labs.liferay.salesforce.client.command;

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

import static org.fusesource.jansi.Ansi.ansi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Scanner;

import org.apache.felix.service.command.Descriptor;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.sforce.soap.partner.Error;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.partner.QueryResult;
import com.sforce.soap.partner.SaveResult;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectorConfig;

import aQute.bnd.annotation.metatype.Configurable;
import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;
import it.dontesta.labs.liferay.salesforce.client.command.configuration.SalesforceClientCommandConfiguration;
import it.dontesta.labs.liferay.salesforce.client.command.util.Console;

/**
 * Gogo Shell Command Series for Salesforce 
 * (Example: create leads, create customers, search, etc.).
 * 
 * @author Antonio Musarra <antonio.musarra@gmail.com>
 */
@Component(
		configurationPid = "it.dontesta.labs.liferay.salesforce.client.command.configuration.SalesforceClientCommandConfiguration",
		property = { 
				"osgi.command.function=login", 
				"osgi.command.function=createAccount", 
				"osgi.command.function=getNewestAccount", 
				"osgi.command.scope=salesforce" 
		}, 
		service = Object.class
)
@Descriptor("Gogo Shell Command Series for Salesforce "
		+ "(Example: create leads, create customers, search, etc.).")
public class SalesforceClientCommand {

	static PartnerConnection partnerConnection = null;
	
	/**
	 * Login to your Saleforce instance
	 * 
	 * @param usename
	 *            Your Salesforce username
	 * @param password
	 *            Your Saleforce password (Note: append your API Key to Password)
	 * @throws PortalException
	 */
	@Descriptor("Login to your Salesforce instance")
	public void login(
		@Descriptor("The your username") String username,
		@Descriptor("The your password + append your API Key") String password
		)
		throws PortalException {
		
		boolean success = false;

		try {
			ConnectorConfig config = new ConnectorConfig();
			config.setUsername(username);
			config.setPassword(password);
			config.setAuthEndpoint(_configuration.authEndpoint());
			config.setTraceFile(_configuration.traceFile());
			config.setTraceMessage(_configuration.traceMessage());
			config.setPrettyPrintXml(_configuration.prettyPrintXml());
			
			partnerConnection = new PartnerConnection(config);
			success = true;

			if (success) {
				System.out.println(ansi().eraseScreen());
				
				System.out.println(
					ansi().render(
						"@|green Login successful to Salesforce with username " 
					+ username
					+ "|@"));
				
				System.out.println(ansi().render("@|yellow Welcome "
					+ partnerConnection.getUserInfo().getUserFullName()
					+ "|@"));
				
				System.out.println(ansi().render("@|yellow Your sessionId is: " 
					+ partnerConnection.getSessionHeader().getSessionId()
					+ "|@"));
			}
		}
		catch (Exception e) {
			System.out.println(ansi().eraseScreen());
			e.printStackTrace();
		}
		
		if (!success) {
			System.out.println(
				ansi().render(
					"@|red Login error to Salesforce with username " + username + "|@"));
		}
	}

	/**
	 * Create account into your Salesforce instance
	 * 
	 * @throws PortalException
	 */
	@Descriptor("Create account into your Salesforce instance")
	public void createAccount() throws PortalException {
		System.out.println(ansi().eraseScreen());

		Scanner scanner = new Scanner(System.in);
		
		Console.print("Account Name: ");
		String accountName = GetterUtil.getString(scanner.nextLine());

		Console.print("Web Site: ");
		String website = GetterUtil.getString(scanner.nextLine());

		Console.print("Phone: ");
		String phone = GetterUtil.getString(scanner.nextLine());

		Console.print(
			"Do you confirm that I can start creating this account? (y): ");
		
		String confirm = GetterUtil.getString(scanner.nextLine(), "y");
		confirm = confirm.toLowerCase().trim();
		
		if (!confirm.equals("y") && !confirm.equals("yes") &&
			Validator.isNotNull(confirm)) {

			Console.println("Abort operation", "red");
			scanner.close();
			return;
		}

		SObject[] records = new SObject[1];

		try {
			SObject so = new SObject();
			so.setType("Account");
			so.setField("Name", accountName);
			so.setField("WebSite", website);
			so.setField("Phone", phone);
			
			records[0] = so;
			
			if (Validator.isNull(partnerConnection)) {
				Console.println("You must do login first.", "red");
				scanner.close();
				return;
			}
			
			SaveResult[] saveResults = partnerConnection.create(records);

			// check the returned results for any errors
			for (int i = 0; i < saveResults.length; i++) {
				if (saveResults[i].isSuccess()) {
					Console.println(
						i + ". Successfully created record - Id: " +
							saveResults[i].getId(), "yellow");
				}
				else {
					Error[] errors = saveResults[i].getErrors();
					for (int j = 0; j < errors.length; j++) {
						Console.println(
							"ERROR creating record: " + errors[j].getMessage(),
							"red");
					}
				}
			}
		}
		catch (Exception e) {
			System.out.println(ansi().eraseScreen());
			e.printStackTrace();
		}

		scanner.close();
	}

	@Descriptor("Query for the newest contacts")
	public void getNewestAccount(
		@Descriptor("How many records returned") int accountLimit
		)
		throws PortalException {

		if (Validator.isNull(partnerConnection)) {
			Console.println("You must do login first.", "red");
			return;
		}

		AsciiTable at = new AsciiTable();
		at.addRule();
		at.addRow(
			"Id", "Account Name", "Web Site", "Phone",
			"Type", "CreatedDate");
		at.addRule();
		
		try {
			QueryResult queryResults = partnerConnection.query(
				"SELECT Id, Name, Type, Website, CreatedDate, CreatedById, " +
					"Phone FROM Account ORDER BY CreatedDate DESC LIMIT " +
					accountLimit);
			if (queryResults.getSize() > 0) {
				for (SObject s : queryResults.getRecords()) {
					Collection<String> columnsValue = new ArrayList<>();
					columnsValue.add(s.getId());
					columnsValue.add(s.getField("Name").toString());
					
					if (Validator.isNotNull(s.getField("Website"))) {
						columnsValue.add(s.getField("Website").toString());
					} else {
						columnsValue.add(StringPool.BLANK);
					}

					if (Validator.isNotNull(s.getField("Phone"))) {
						columnsValue.add(s.getField("Phone").toString());
					} else {
						columnsValue.add(StringPool.BLANK);
					}

					if (Validator.isNotNull(s.getField("Type"))) {
						columnsValue.add(s.getField("Type").toString());
					} else {
						columnsValue.add(StringPool.BLANK);
					}

					columnsValue.add(s.getField("CreatedDate").toString());
					
					at.addRow(columnsValue);
					at.addRule();
				}
			} else {
				at.addRow("No Account found");
				at.setTextAlignment(TextAlignment.CENTER);
				at.addRule();
			}
			
			Console.println(at.render(160), "white");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_configuration = Configurable.createConfigurable(
			SalesforceClientCommandConfiguration.class, properties);
	}

	private volatile SalesforceClientCommandConfiguration _configuration;
}
