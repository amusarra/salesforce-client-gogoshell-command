
# Salesforce Liferay Gogo Shell Command Client
[![Build Status](https://travis-ci.org/amusarra/salesforce-client-gogoshell-command.svg?branch=master)](https://travis-ci.org/amusarra/salesforce-client-gogoshell-command)

This project is born as demo for using the **[Salesforce SOAP API Client OSGi Bundle](https://github.com/amusarra/salesforce-client-soap)**. This sample project implements a set of Gogo Shell commands that allow us to interact with the Salesforce CRM system.

The commands that are implemented:

1. **salesforce:login**: Login to your Salesforce instance
2. **salesforce:createAccount**: Create account into your Salesforce instance
3. **salesforce:getNewestAccount**: Query for the newest accounts

The diagram in Figure 1 shows a possible integration scenario between Liferay and the CRM system that in this case is Salesforce.com. The CRM Application (Figure 1) in this case is implemented by this project.

![Figure 1 - Integration scenario Liferay 7 and CRM via SOAP](https://www.dontesta.it/wp-content/uploads/2016/07/Figure_1_OverviewIntegrationScenarioSOAPLiferay7.jpg)

Figure 1 - Integration scenario Liferay 7 and CRM (in this case Salesforce.com) via SOAP

The version of this project was tested on Liferay 7 CE GA4. You can download the tomcat bundle of the [Liferay 7 CE GA4](https://sourceforge.net/projects/lportal/files/Liferay%20Portal/7.0.3%20GA4/liferay-ce-portal-tomcat-7.0-ga4-20170613175008905.zip/download) from sourceforge.

### 1. Getting started
To build the project you need:

1. Sun/Oracle JDK 1.8
2. Maven 3.2 or Gradle 3.x (this project include the gradle wrapper)
3. Git tools

You also need to install the OSGi Salesforce SOAP API client bundle. You can follow these instructions [How to install in Liferay 7 CE/DXP]( https://github.com/amusarra/salesforce-client-soap#2-how-to-install-in-liferay-7-cedxp). If you want can download the bundle JAR [salesforce-client-soap (v1.0.1)](http://repo1.maven.org/maven2/it/dontesta/labs/liferay/salesforce/client/soap/salesforce-client-soap/1.0.1/salesforce-client-soap-1.0.1.jar) from Maven repository and deploy to Liferay (via auto deploy directory or directly in *$LIFERAY_HOME/osgi/modules*);

To start testing the plugin you need:

1. clone this repository
2. build project (whit Maven or Gradle)
3. deploy OSGi module (salesforce-client-gogoshell-command-$version.jar) to Liferay instance

From your terminal execute the commands:

	$ git clone https://github.com/amusarra/salesforce-client-gogoshell-command.git
	$ cd salesforce-client-gogoshell-command

if use Maven then run this command:

	$ mvn clean verify

if use gradle wrapper (gradlew) then run this command:

	$ ./gradlew clean deploy

The last commands create a OSGi bundle and deploy directly on your Liferay instance. The deployment directory is set by the property **liferay.home** for Maven (defined inside the pom.xml), while for Gradle is set by the property **auto.deploy.dir** (inside the gradle.properties).

The default value for the two properties:

1. **liferay.home** /opt/liferay-ce-portal-7.0-ga4
2. **auto.deploy.dir** /opt/liferay-ce-portal-7.0-ga4/deploy

If you want could customize the deployment directory in this two way (Maven or Gradle):

```
$ mvn clean verify -Dliferay.home=$YOUR_LIFERAY_HOME
```

```
$ ./gradlew clean deploy -Pauto.deploy.dir=$YOUR_LIFERAY_AUTO_DEPLOY
```

Check if the bundles are installed correctly via Gogo Shell.

```
$ telnet localhost 11311
g! lb | grep Salesforce
527|Active     |   10|Salesforce Client Gogo Shell Command (1.0.0.SNAPSHOT)
528|Active     |    1|Salesforce SOAP Client (1.0.1)
true
```

Both bundles are installed correctly.

### 2. Gogo Shell Command in action
Bundles are installed correctly, so we can begin to see how to use commands. The commands that are implemented:

1. **salesforce:login**: Login to your Salesforce instance
2. **salesforce:createAccount**: Create account into your Salesforce instance
3. **salesforce:getNewestAccount**: Query for the newest accounts

To verify the commands that are available it is possible to execute the command (via Gogo Shell) ``help | grep salesforce`` and obtain the following result.

```
salesforce:login
salesforce:getNewestAccount
salesforce:createAccount
```

You can display online help for each command typing (on Gogo Shell) ``help $scope:$command`` and obtain the following result (for salesforce:login):

```
login - Login to your Salesforce instance
   scope: salesforce
   parameters:
      String   The your username
      String   The your password + append your API Key
```
Console 1 - Output of the command help:salesforce:login

Now let's see the commands in action (in the order shown above).

```
g! salesforce:login antonio.musarra@gmail.com uuyteTey0PPntoZ9reywPmW1kmoClPDKa
```
Console 2 - Try login to Salesforce.com (password is fake)


```
Login successful to Salesforce with username antonio.musarra@gmail.com
Welcome Antonio Musarra
Your sessionId is: 00D20000000m69v!ARAAQO2nNNOWOl9COMVk23Y9U99uZliR0vysheoRbrMd3SRVNtaDP6r5UOE8njs21pODplqA7vXYAsSnj6mYzzloHuSpFZ9z
```
Console 3 - Result of the login operation

```
g! salesforce:getNewestAccount 5
```
Console 4 - Get the last newest five accounts

```
┌──────────────────────────┬──────────────────────────┬──────────────────────────┬─────────────────────────┬─────────────────────────┬─────────────────────────┐
│Id                        │Account Name              │Web Site                  │Phone                    │Type                     │CreatedDate              │
├──────────────────────────┼──────────────────────────┼──────────────────────────┼─────────────────────────┼─────────────────────────┼─────────────────────────┤
│00120000018683xAAA        │GenePoint                 │www.genepoint.com         │(650) 867-3450           │Customer - Channel       │2014-05-06T11:37:25.000Z │
├──────────────────────────┼──────────────────────────┼──────────────────────────┼─────────────────────────┼─────────────────────────┼─────────────────────────┤
│00120000018683yAAA        │United Oil & Gas, UK      │http://www.uos.com        │+44 191 4956203          │Customer - Direct        │2014-05-06T11:37:25.000Z │
├──────────────────────────┼──────────────────────────┼──────────────────────────┼─────────────────────────┼─────────────────────────┼─────────────────────────┤
│00120000018683zAAA        │United    Oil    &    Gas,│http://www.uos.com        │(650) 450-8810           │Customer - Direct        │2014-05-06T11:37:25.000Z │
│                          │Singapore                 │                          │                         │                         │                         │
├──────────────────────────┼──────────────────────────┼──────────────────────────┼─────────────────────────┼─────────────────────────┼─────────────────────────┤
│001200000186840AAA        │Edge Communications       │http://edgecomm.com       │(512) 757-6000           │Customer - Direct        │2014-05-06T11:37:25.000Z │
├──────────────────────────┼──────────────────────────┼──────────────────────────┼─────────────────────────┼─────────────────────────┼─────────────────────────┤
│001200000186841AAA        │Burlington  Textiles  Corp│www.burlington.com        │(336) 222-7000           │Customer - Direct        │2014-05-06T11:37:25.000Z │
│                          │of America                │                          │                         │                         │                         │
└──────────────────────────┴──────────────────────────┴──────────────────────────┴─────────────────────────┴─────────────────────────┴─────────────────────────┘
```
Console 5 - Result of the salesforce:getNewestAccount operation

```
g! salesforce:createAccount
```
Console 6 - Start the interactive account creation process

```
Account Name:  Antonio Musarra's Blog
Web Site:  https://www.dontesta.it
Phone:  +39334756787
Do you confirm that I can start creating this account? (y):  y
0. Successfully created record - Id: 0010O00001o0BlVQAU
```
Console 7 - Result of the salesforce:createAccount operation

```
g! salesforce:getNewestAccount 1
```
Console 8 - Get the last one newest accounts

```
┌──────────────────────────┬──────────────────────────┬──────────────────────────┬─────────────────────────┬─────────────────────────┬─────────────────────────┐
│Id                        │Account Name              │Web Site                  │Phone                    │Type                     │CreatedDate              │
├──────────────────────────┼──────────────────────────┼──────────────────────────┼─────────────────────────┼─────────────────────────┼─────────────────────────┤
│0010O00001o0BlVQAU        │Antonio Musarra's Blog    │https://www.dontesta.it   │+39334756787             │                         │2017-07-26T20:44:55.000Z │
└──────────────────────────┴──────────────────────────┴──────────────────────────┴─────────────────────────┴─────────────────────────┴─────────────────────────┘
```
Console 9 - Result of the salesforce:getNewestAccount

The [SalesforceClientCommand](https://github.com/amusarra/salesforce-client-gogoshell-command/blob/master/src/main/java/it/dontesta/labs/liferay/salesforce/client/command/SalesforceClientCommand.java) OSGi component is linked to the [SalesforceClientCommandConfiguration](https://github.com/amusarra/salesforce-client-gogoshell-command/blob/master/src/main/java/it/dontesta/labs/liferay/salesforce/client/command/configuration/SalesforceClientCommandConfiguration.java) configuration (via **configurationPid** attribute).

```java
...
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
	...
}
```
Java Code 1 - Definition of the OSGi component implementing the commands.

Figure 2 shows the configuration parameters used by the Gogo Shell commands. As you can see from the configuration, communication tracking between Liferay and Salesforce is enabled. Each SOAP request and response flow is traced to the configured file.

![Salesforce Client Gogo Shell Command OSGi Configuration ](https://www.dontesta.it/wp-content/uploads/2017/07/SalesforceClientGogoShellCommand_Configuration.png)
Figure 2 - Salesforce Client Gogo Shell Command OSGi Configuration

```xml
<?xml version="1.0" encoding="UTF-8"?><env:Envelope
   xmlns:env="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsd="http://www.w3.org/2001/XMLSchema"
   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
 <env:Header>
  <SessionHeader xmlns="urn:partner.soap.sforce.com">
   <sessionId>00D20000000m69v!ARAAQO2nNNOWOl9COMVk23Y9U99uZliR0vysheoRbrMd3SRVNtaDP6r5UOE8njs21pODplqA7vXYAsSnj6mYzzloHuSpFZ9z</sessionId>
  </SessionHeader>
 </env:Header>
 <env:Body>
  <m:query xmlns:m="urn:partner.soap.sforce.com" xmlns:sobj="urn:sobject.partner.soap.sforce.com">
   <m:queryString>SELECT Id, Name, Type, Website, CreatedDate, CreatedById, Phone FROM Account ORDER BY CreatedDate DESC LIMIT 1</m:queryString>
  </m:query>
 </env:Body>
</env:Envelope>
```
XML Code 1 - SOAP Request for the salesforce:getNewestAccount operation

```xml
<?xml version="1.0" encoding="UTF-8"?>
	<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns="urn:partner.soap.sforce.com" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:sf="urn:sobject.partner.soap.sforce.com">
		<soapenv:Header>
			<LimitInfoHeader>
				<limitInfo>
					<current>4</current>
					<limit>15000</limit>
					<type>API REQUESTS</type>
			</limitInfo>
		</LimitInfoHeader>
	</soapenv:Header>
		<soapenv:Body>
			<queryResponse>
				<result xsi:type="QueryResult">
					<done>true</done>
					<queryLocator xsi:nil="true"/>
						<records xsi:type="sf:sObject">
							<sf:type>Account</sf:type>
							<sf:Id>0010O00001o0BlVQAU</sf:Id>
							<sf:Id>0010O00001o0BlVQAU</sf:Id>
							<sf:Name>Antonio Musarra&apos;s Blog</sf:Name>
							<sf:Type xsi:nil="true"/>
								<sf:Website>https://www.dontesta.it</sf:Website>
								<sf:CreatedDate>2017-07-26T20:44:55.000Z</sf:CreatedDate>
								<sf:CreatedById>00520000003S6PBAA0</sf:CreatedById>
								<sf:Phone>+39334756787</sf:Phone>
						</records>
							<size>1</size>
					</result>
				</queryResponse>
			</soapenv:Body>
		</soapenv:Envelope>
```
XML Code 2 - SOAP Response for the salesforce:getNewestAccount operation

### Resources
If you follow this resources you could see how to use Salesforce SOAP API.

1. [Introducing SOAP API](https://developer.salesforce.com/docs/atlas.en-us.api.meta/api/sforce_api_quickstart_intro.htm)
2. [Force.com Web Service Connector (WSC)](https://github.com/forcedotcom/wsc)

[![Liferay 7: Demo Salesforce Gogo Shell Command ](https://img.youtube.com/vi/nQXqzKpnxoc/0.jpg)](https://youtu.be/nQXqzKpnxoc)

Video 1 - Liferay 7: Demo Salesforce Gogo Shell Command

### Project License
The MIT License (MIT)

Copyright &copy; 2017 Antonio Musarra's Blog - [https://www.dontesta.it](https://www.dontesta.it "Antonio Musarra's Blog") , [antonio.musarra@gmail.com](mailto:antonio.musarra@gmail.com "Antonio Musarra Email")

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

<span style="color:#D83410">
	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
	OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
	SOFTWARE.
<span>
