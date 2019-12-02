# Salesforce Gogo Shell Command Client
[![Antonio Musarra's Blog](https://img.shields.io/badge/maintainer-Antonio_Musarra's_Blog-purple.svg?colorB=6e60cc)](https://www.dontesta.it)
[![Build Status](https://travis-ci.org/amusarra/salesforce-client-gogoshell-command.svg?branch=master)](https://travis-ci.org/amusarra/salesforce-client-gogoshell-command)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/44af66efc4d246519d86fda127de0406)](https://www.codacy.com/app/amusarra/salesforce-client-gogoshell-command?utm_source=github.com&utm_medium=referral&utm_content=amusarra/salesforce-client-gogoshell-command&utm_campaign=badger)
[![Twitter Follow](https://img.shields.io/twitter/follow/antonio_musarra.svg?style=social&label=%40antonio_musarra%20on%20Twitter&style=plastic)](https://twitter.com/antonio_musarra)

This project is born as demo for using the **[Salesforce SOAP API Client OSGi Bundle](https://github.com/amusarra/salesforce-client-soap)**. This sample project implements a set of Gogo Shell commands that allow us to interact with the Salesforce CRM system.

The Force.com SOAP API (formerly known as the Force.com Web Services API)  lets you integrate Force.com applications that can create, retrieve, update or delete records managed by Salesforce, Force.com, and Database.com, records such as accounts, leads, and custom objects. With more than 20 different calls, SOAP API also lets you to maintain passwords, perform searches, and much more.

You can use the SOAP API with any programming language that supports Web services.

![Figure 1 - Integration scenario Liferay 7 and CRM via SOAP](https://s3.amazonaws.com/dfc-wiki/en/images/1/17/SOAP-API-01.png)

The commands that are implemented:

1. **salesforce:login**: Login to your Salesforce instance (Partner Mode)
2. **salesforce:loginEnterprise**: Login to your Salesforce instance (Enterprise Mode)
3. **salesforce:createAccount**: Create account into your Salesforce instance
4. **salesforce:getNewestAccount**: Query for the newest accounts
5. **salesforce:getNewestAccountEnterprise**: Query for the newest accounts (Enterprise Mode)

The diagram in Figure 1 shows a possible integration scenario between Liferay and the CRM system that in this case is Salesforce.com. The CRM Application (Figure 1) in this case is implemented by this project.

![Figure 1 - Integration scenario Liferay 7 and CRM via SOAP](https://www.dontesta.it/wp-content/uploads/2016/07/Figure_1_OverviewIntegrationScenarioSOAPLiferay7.jpg)

Figure 1 - Integration scenario Liferay 7/DXP and CRM (in this case Salesforce.com) via SOAP

The version of this project was tested on Liferay 7.2 CE GA2. You can download the tomcat bundle of the [Liferay 7.2 CE GA2](https://sourceforge.net/projects/lportal/files/Liferay%20Portal/7.2.1%20GA2/liferay-ce-portal-tomcat-7.2.1-ga2-20191111141448326.tar.gz/download) from sourceforge.

### 1. Getting started
To build the project you need:

1. Sun/Oracle JDK 1.8
2. Maven 3.2 or Gradle 4.10.x (this project include the gradle wrapper)
3. Git tools

You also need to install the OSGi Salesforce SOAP API client bundle. You can follow these instructions [How to install in Liferay 7/7.1 CE/DXP](https://github.com/amusarra/salesforce-client-soap#2-how-to-install-in-liferay-771-cedxp). If you want can download the bundle JAR [salesforce-client-soap (v1.2.0)](http://repo1.maven.org/maven2/it/dontesta/labs/liferay/salesforce/client/soap/salesforce-client-soap/1.2.0/salesforce-client-soap-1.2.0.jar) from Maven repository and deploy to Liferay (via auto deploy directory or directly in *$LIFERAY_HOME/osgi/modules*);

To start testing the plugin you need:

1. clone this repository
2. build project (whit Maven or Gradle)
3. deploy OSGi module (salesforce-client-gogoshell-command-$version.jar) to Liferay instance

From your terminal execute the commands:

```shell
$ git clone https://github.com/amusarra/salesforce-client-gogoshell-command.git
$ cd salesforce-client-gogoshell-command
```

if use Maven then run this command:

```shell
$ mvn clean verify
```

if use gradle wrapper (gradlew) then run this command:

```shell
$ ./gradlew clean deploy
```

The last commands create a OSGi bundle and deploy directly on your Liferay instance. The deployment directory is set by the property **liferay.home** for Maven (defined inside the pom.xml), while for Gradle is set by the property **auto.deploy.dir** (inside the gradle.properties).

The default value for the two properties:

1. **liferay.home** /opt/liferay-ce-portal-7.2.1-ga2
2. **auto.deploy.dir** /opt/liferay-ce-portal-7.2.1-ga2/deploy

If you want could customize the deployment directory in this two way (Maven or Gradle):

```Shell
$ mvn clean verify -Dliferay.home=$YOUR_LIFERAY_HOME
```

```shell
$ ./gradlew clean deploy -Pauto.deploy.dir=$YOUR_LIFERAY_AUTO_DEPLOY
```

Check if the bundles are installed correctly via Gogo Shell.

```shell
$ telnet localhost 11311
g! lb | grep Salesforce
527|Active     |   10|Salesforce Client Gogo Shell Command (1.2.0.SNAPSHOT)|1.2.0.SNAPSHOT
528|Active     |    1|Salesforce SOAP API Client OSGi Bundle (1.2.0)|1.2.0
true
```

Both bundles are installed correctly.

### 2. Gogo Shell Command in action
Bundles are installed correctly, so we can begin to see how to use commands. The commands that are implemented:

1. **salesforce:login**: Login to your Salesforce instance (Partner Mode)
2. **salesforce:loginEnterprise**: Login to your Salesforce instance (Enterprise Mode)
3. **salesforce:createAccount**: Create account into your Salesforce instance
4. **salesforce:getNewestAccount**: Query for the newest accounts
5. **salesforce:getNewestAccountEnterprise**: Query for the newest accounts (Enterprise Mode)

You can display online help for each command typing (on Gogo Shell) ``help $scope:$command`` and obtain the following result (for salesforce:login):

```shell
login - Login to your Salesforce instance
   scope: salesforce
   parameters:
      String   The your username
      String   The your password + append your API Key
```
Console 1 - Output of the command help:salesforce:login

Now let's see the commands in action (in the order shown above).

```shell
g! salesforce:login antonio.musarra@gmail.com uuyteTey0PPntoZ9reywPmW1kmoClPDKa
```
Console 2 - Try login to Salesforce.com (password is fake)

```shell
Login successful to Salesforce with username antonio.musarra@gmail.com
Welcome Antonio Musarra
Your sessionId is: 00D20000000m69v!ARAAQO2nNNOWOl9COMVk23Y9U99uZliR0vysheoRbrMd3SRVNtaDP6r5UOE8njs21pODplqA7vXYAsSnj6mYzzloHuSpFZ9z
```
Console 3 - Result of the login operation

```shell
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

```shell
g! salesforce:createAccount
```
Console 6 - Start the interactive account creation process

```shell
Account Name:  Antonio Musarra's Blog
Web Site:  https://www.dontesta.it
Phone:  +39334756787
Do you confirm that I can start creating this account? (y):  y
0. Successfully created record - Id: 0010O00001o0BlVQAU
```
Console 7 - Result of the salesforce:createAccount operation

```shell
g! salesforce:getNewestAccount 1
```
Console 8 - Get the last one newest accounts

```shell
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
				"osgi.command.function=loginEnterprise",
				"osgi.command.function=createAccount",
				"osgi.command.function=getNewestAccount",
				"osgi.command.function=getNewestAccountEnterprise",
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

Figure 2 shows the configuration parameters used by the Gogo Shell commands. As you can see from the configuration, communication tracking between Liferay and Salesforce is enabled. Each SOAP request and response
flow is traced to the configured file.

![Salesforce Client Gogo Shell Command OSGi Configuration ](docs/images/salesforce-gogoshell-command-configuration.png)
Figure 2 - Salesforce Client Gogo Shell Command OSGi Configuration

Below are the SOAP messages that are exchanged between the two systems and that are saved on the trace files.

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

### 3. Not just Liferay
This Salesforce integration example bundle can also be installed in other [OSGi R6](https://www.osgi.org/developer/downloads/release-6/) containers, such as [Apache Karaf](http://karaf.apache.org). Your Karaf instance must have the feature scr installed. You could run the installation by running the following command from the console: 

```shell
karaf@root()> feature:install scr
```

You also need to install the OSGi Salesforce SOAP API client bundle. You can follow these instructions [How to install in Apache Karaf 4.x](https://github.com/amusarra/salesforce-client-soap#3-how-to-install-in-apache-karaf-4x). If you want can download the bundle JAR [salesforce-client-soap (v1.2.0)](http://repo1.maven.org/maven2/it/dontesta/labs/liferay/salesforce/client/soap/salesforce-client-soap/1.2.0/salesforce-client-soap-1.2.0.jar) from Maven repository and deploy by copying into your **$KARAF_HOME/deploy** directory.

After you install the [Salesforce SOAP client bundle](https://github.com/amusarra/salesforce-client-soap), you can install the Gogo Shell commands bundle. To install the bundle, just copy this into Apache Karaf deployment directories. ($KARAF_HOME/deploy).

After deploying the two bundles, connecting to the Apache Karaf console and typing the ``list`` command, you should see the two bundles in the active state, as shown in Figure 3.

![salesforce-gogoshell-command-bundle-list](docs/images/salesforce-gogoshell-command-bundle-list.png)

Figure 3 - Check of the installed bundle

Below you will see the commands available on the Salesforce scope. The functionality of the commands has been explained earlier.

```
karaf@root()> salesforce
salesforce                              salesforce:getNewestAccountEnterprise   salesforce:loginEnterprise
salesforce:createAccount                salesforce:getnewestaccount             salesforce:loginenterprise
salesforce:createaccount                salesforce:getnewestaccountenterprise
salesforce:getNewestAccount             salesforce:login
```
Console 11 - Available command with the scope salesforce

![salesforce-gogoshell-command-samples](docs/images/salesforce-gogoshell-command-samples.png)

Figure 4 - Example of login and view of the last created accounts.

To access the configuration of this bundle, you can do it directly from the Apache Karaf console via the command:

``config:meta it.dontesta.labs.liferay.salesforce.client.command.configuration.SalesforceClientCommandConfiguration``

![salesforce-gogoshell-command-configuration-view-from-console](docs/images/salesforce-gogoshell-command-configuration-view-from-console.png)

Console 11 - OSGi MetaType configuration

You can of course change the values based on your needs. Apache Karaf provides a [set of commands](http://karaf.apache.org/manual/latest/#__code_config_code_commands) to manage the configuration.

### Resources
If you follow this resources you could see how to use Salesforce SOAP API.

1. [Introducing SOAP API](https://developer.salesforce.com/docs/atlas.en-us.api.meta/api/sforce_api_quickstart_intro.htm)
2. [Cheat Sheets](https://developer.salesforce.com/page/Cheat_Sheets)
3. [Force.com SOAP API Cheatsheet](http://resources.docs.salesforce.com/rel1/doc/en-us/static/pdf/SF_Soap_API_cheatsheet_web.pdf)
3. [Force.com Web Service Connector (WSC)](https://github.com/forcedotcom/wsc)

[![Liferay 7: Demo Salesforce Gogo Shell Command ](https://img.youtube.com/vi/nQXqzKpnxoc/0.jpg)](https://youtu.be/nQXqzKpnxoc)

Video 1 - Liferay 7: Demo Salesforce Gogo Shell Command

### Project License
The MIT License (MIT)

Copyright &copy; 2019 Antonio Musarra's Blog - [https://www.dontesta.it](https://www.dontesta.it "Antonio Musarra's Blog") , [antonio.musarra@gmail.com](mailto:antonio.musarra@gmail.com "Antonio Musarra Email")

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.