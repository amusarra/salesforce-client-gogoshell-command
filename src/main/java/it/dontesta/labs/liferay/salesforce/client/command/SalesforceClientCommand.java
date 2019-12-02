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

package it.dontesta.labs.liferay.salesforce.client.command;

import static org.fusesource.jansi.Ansi.ansi;

import aQute.bnd.annotation.metatype.Configurable;

import com.sforce.soap.enterprise.Connector;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.sobject.Account;
import com.sforce.soap.partner.Error;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.partner.QueryResult;
import com.sforce.soap.partner.SaveResult;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectorConfig;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;

import it.dontesta.labs.liferay.salesforce.client.command.configuration.SalesforceClientCommandConfiguration;
import it.dontesta.labs.liferay.salesforce.client.command.util.Console;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

import org.apache.felix.service.command.Descriptor;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * Gogo Shell Command Series for Salesforce
 * (Example: create leads, create customers, search, etc.).
 *
 * @author Antonio Musarra antonio.musarra@gmail.com
 */
@Component(
	configurationPid = "it.dontesta.labs.liferay.salesforce.client.command.configuration.SalesforceClientCommandConfiguration",
	property = {
		"osgi.command.function=login", "osgi.command.function=loginEnterprise",
		"osgi.command.function=createAccount",
		"osgi.command.function=getNewestAccount",
		"osgi.command.function=getNewestAccountEnterprise",
		"osgi.command.scope=salesforce"
	},
	service = Object.class,
	scope = ServiceScope.SINGLETON,
	immediate = true
)
@Descriptor(
	"Gogo Shell Command Series for Salesforce " +
		"(Example: create leads, create customers, search, etc.)."
)
public class SalesforceClientCommand {

	/**
	 * Create account into your Salesforce instance using the Partner Connection
	 *
	 * @throws Exception When create account failed
	 */
	@Descriptor(
		"Create account into your Salesforce instance using the Partner Connection"
	)
	public void createAccount() throws Exception {
		System.out.println(ansi().eraseScreen());

		Scanner scanner = new Scanner(System.in);

		Console.print("Account Name: ");
		String accountName = scanner.nextLine();

		Console.print("Web Site: ");
		String website = scanner.nextLine();

		Console.print("Phone: ");
		String phone = scanner.nextLine();

		Console.print(
			"Do you confirm that I can start creating this account? (y): ");

		String confirm = Optional.of(
			scanner.nextLine()
		).orElse(
			"y"
		);

		confirm = confirm.toLowerCase(
		).trim();

		if (!"y".equals(confirm) && !"yes".equals(confirm)) {
			Console.println("Abort operation", "red");

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

			if (Objects.isNull(_partnerConnection)) {
				Console.println("You must do login first.", "red");
				scanner.close();

				return;
			}

			SaveResult[] saveResults = _partnerConnection.create(records);

			// check the returned results for any errors

			for (int i = 0; i < saveResults.length; i++) {
				if (saveResults[i].isSuccess()) {
					Console.println(
						i + ". Successfully created record - Id: " +
							saveResults[i].getId(),
						"yellow");
				}
				else {
					Error[] errors = saveResults[i].getErrors();

					for (Error error : errors) {
						Console.println(
							"ERROR creating record: " + error.getMessage(),
							"red");
					}
				}
			}
		}
		catch (Exception e) {
			System.out.println(ansi().eraseScreen());
			e.printStackTrace();
		}
	}

	/**
	 * Query for the newest contacts using the Partner Connection
	 *
	 * @param accountLimit How many records returned
	 * @throws Exception When query failed
	 */
	@Descriptor("Query for the newest contacts using the Partner Connection")
	public void getNewestAccount(
			@Descriptor("How many records returned") int accountLimit)
		throws Exception {

		if (Objects.isNull(_partnerConnection)) {
			Console.println("You must do login first.", "red");

			return;
		}

		AsciiTable at = new AsciiTable();

		at.addRule();
		at.addRow(
			"Id", "Account Name", "Web Site", "Phone", "Type", "CreatedDate");
		at.addRule();

		try {
			QueryResult queryResults = _partnerConnection.query(
				"SELECT Id, Name, Type, Website, CreatedDate, CreatedById, " +
					"Phone FROM Account ORDER BY CreatedDate DESC LIMIT " +
						accountLimit);

			if (queryResults.getSize() > 0) {
				for (SObject s : queryResults.getRecords()) {
					Collection<String> columnsValue = new ArrayList<>();

					columnsValue.add(s.getId());
					columnsValue.add(
						s.getField(
							"Name"
						).toString());

					if (Objects.nonNull(s.getField("Website"))) {
						columnsValue.add(
							s.getField(
								"Website"
							).toString());
					}
					else {
						columnsValue.add("");
					}

					if (Objects.nonNull(s.getField("Phone"))) {
						columnsValue.add(
							s.getField(
								"Phone"
							).toString());
					}
					else {
						columnsValue.add("");
					}

					if (Objects.nonNull(s.getField("Type"))) {
						columnsValue.add(
							s.getField(
								"Type"
							).toString());
					}
					else {
						columnsValue.add("");
					}

					columnsValue.add(
						s.getField(
							"CreatedDate"
						).toString());

					at.addRow(columnsValue);
					at.addRule();
				}
			}
			else {
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

	/**
	 * Query for the newest contacts using the Enterprise Connection
	 *
	 * @param accountLimit How many records returned
	 * @throws Exception When query failed
	 */
	@Descriptor("Query for the newest contacts using the Enterprise Connection")
	public void getNewestAccountEnterprise(
			@Descriptor("How many records returned") int accountLimit)
		throws Exception {

		if (Objects.isNull(_enterpriseConnection)) {
			Console.println(
				"You must do login first (Enterprise connection).", "red");

			return;
		}

		AsciiTable at = new AsciiTable();

		at.addRule();
		at.addRow(
			"Id", "Account Name", "Web Site", "Phone", "Type", "CreatedDate");
		at.addRule();

		try {
			com.sforce.soap.enterprise.QueryResult queryResults =
				_enterpriseConnection.query(
					"SELECT Id, Name, Type, Website, CreatedDate, CreatedById, " +
						"Phone FROM Account ORDER BY CreatedDate DESC LIMIT " +
							accountLimit);

			if (queryResults.getSize() > 0) {
				for (com.sforce.soap.enterprise.sobject.SObject s :
						queryResults.getRecords()) {

					Collection<String> columnsValue = new ArrayList<>();

					columnsValue.add(s.getId());
					columnsValue.add(((Account)s).getName());

					if (Objects.nonNull(((Account)s).getWebsite())) {
						columnsValue.add(((Account)s).getWebsite());
					}
					else {
						columnsValue.add("");
					}

					if (Objects.nonNull(((Account)s).getPhone())) {
						columnsValue.add(((Account)s).getPhone());
					}
					else {
						columnsValue.add("");
					}

					if (Objects.nonNull(((Account)s).getType())) {
						columnsValue.add(((Account)s).getType());
					}
					else {
						columnsValue.add("");
					}

					columnsValue.add(
						((Account)s).getCreatedDate(
						).toString());

					at.addRow(columnsValue);
					at.addRule();
				}
			}
			else {
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

	/**
	 * Login to your Saleforce instance using the Partner Connection
	 *
	 * @param username
	 *            Your Salesforce username
	 * @param password
	 *            Your Saleforce password (Note: append your API Key to Password)
	 * @throws Exception When login failed
	 */
	@Descriptor(
		"Login to your Salesforce instance using the Partner Connection"
	)
	public void login(
			@Descriptor("The your username") String username,
			@Descriptor("The your password + append your API Key")
				String password)
		throws Exception {

		boolean success = false;

		try {
			ConnectorConfig config = new ConnectorConfig();

			config.setUsername(username);
			config.setPassword(password);
			config.setAuthEndpoint(_configuration.authEndpoint());
			config.setTraceFile(_configuration.traceFile());
			config.setTraceMessage(_configuration.traceMessage());
			config.setPrettyPrintXml(_configuration.prettyPrintXml());

			_partnerConnection = new PartnerConnection(config);
			success = true;

			System.out.println(ansi().eraseScreen());

			System.out.println(
				ansi().render(
					"@|green Login successful to Salesforce with username " +
						username + "|@"));

			System.out.println(
				ansi().render(
					"@|yellow Welcome " +
						_partnerConnection.getUserInfo(
						).getUserFullName() + "|@"));

			System.out.println(
				ansi().render(
					"@|yellow Your sessionId is: " +
						_partnerConnection.getSessionHeader(
						).getSessionId() + "|@"));
		}
		catch (Exception e) {
			System.out.println(ansi().eraseScreen());
			e.printStackTrace();
		}

		if (!success) {
			System.out.println(
				ansi().render(
					"@|red Login error to Salesforce with username " +
						username + "|@"));
			_partnerConnection = null;
		}
	}

	/**
	 * Login to your Saleforce instance using the Enterprise Connection
	 *
	 * @param username
	 *            Your Salesforce username
	 * @param password
	 *            Your Saleforce password (Note: append your API Key to Password)
	 * @throws Exception When login failed
	 */
	@Descriptor(
		"Login to your Salesforce instance using the Enterprise Connection"
	)
	public void loginEnterprise(
			@Descriptor("The your username") String username,
			@Descriptor("The your password + append your API Key")
				String password)
		throws Exception {

		boolean success = false;

		try {
			ConnectorConfig config = new ConnectorConfig();

			config.setUsername(username);
			config.setPassword(password);
			config.setAuthEndpoint(_configuration.authEndpointEnterprise());
			config.setTraceFile(_configuration.traceFileEnterprise());
			config.setTraceMessage(_configuration.traceMessage());
			config.setPrettyPrintXml(_configuration.prettyPrintXml());

			_enterpriseConnection = Connector.newConnection(config);
			success = true;

			System.out.println(ansi().eraseScreen());

			System.out.println(
				ansi().render(
					"@|green Login successful to Salesforce with username " +
						username + "|@"));

			System.out.println(
				ansi().render(
					"@|yellow Welcome " +
						_enterpriseConnection.getUserInfo(
						).getUserFullName() + "|@"));

			System.out.println(
				ansi().render(
					"@|yellow Your sessionId is: " +
						_enterpriseConnection.getSessionHeader(
						).getSessionId() + "|@"));
		}
		catch (Exception e) {
			System.out.println(ansi().eraseScreen());
			e.printStackTrace();
		}

		if (!success) {
			System.out.println(
				ansi().render(
					"@|red Login error to Salesforce with username " +
						username + "|@"));
			_enterpriseConnection = null;
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_configuration = Configurable.createConfigurable(
			SalesforceClientCommandConfiguration.class, properties);
	}

	private EnterpriseConnection _enterpriseConnection = null;
	private PartnerConnection _partnerConnection = null;

	private SalesforceClientCommandConfiguration _configuration;

}