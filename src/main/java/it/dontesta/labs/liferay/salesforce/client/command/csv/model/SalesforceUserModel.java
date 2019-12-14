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

package it.dontesta.labs.liferay.salesforce.client.command.csv.model;

import com.univocity.parsers.annotations.Headers;
import com.univocity.parsers.annotations.Parsed;

/**
 * @author Antonio Musarra
 */
@Headers(
	extract = true,
	sequence = {
		"FIRST_NAME", "LAST_NAME", "NAME", "ALIAS", "USERNAME", "NICKNAME",
		"EMAIL", "ACTIVE", "LANGUAGE", "LOCALE", "TIME_ZONE", "CITY", "COUNTRY",
		"STATE", "STREET", "POSTAL_CODE", "EMAIL_ENCODING", "PROFILE_ID"
	}
)
public class SalesforceUserModel {

	public SalesforceUserModel() {
	}

	public SalesforceUserModel(
		String firstName, String lastName, String name, String alias,
		String userName, String nickName, String email, boolean active,
		String language, String locale, String timeZone, String city,
		String country, String state, String street, String postalCode,
		String emailEncoding, String profileId) {

		this.firstName = firstName;
		this.lastName = lastName;
		this.name = name;
		this.alias = alias;
		this.userName = userName;
		this.nickName = nickName;
		this.locale = locale;
		this.email = email;
		this.active = active;
		this.language = language;
		this.country = country;
		this.timeZone = timeZone;
		this.city = city;
		this.emailEconding = emailEncoding;
		this.state = state;
		this.profileId = profileId;
		this.street = street;
		this.postalCode = postalCode;
	}

	public String getAlias() {
		return alias;
	}

	public String getCity() {
		return city;
	}

	public String getCountry() {
		return country;
	}

	public String getEmail() {
		return email;
	}

	public String getEmailEconding() {
		return emailEconding;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLanguage() {
		return language;
	}

	public String getLastName() {
		return lastName;
	}

	public String getLocale() {
		return locale;
	}

	public String getName() {
		return name;
	}

	public String getNickName() {
		return nickName;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public String getProfileId() {
		return profileId;
	}

	public String getState() {
		return state;
	}

	public String getStreet() {
		return street;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public String getUserName() {
		return userName;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setEmailEconding(String emailEconding) {
		this.emailEconding = emailEconding;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Parsed(field = "ACTIVE")
	private boolean active;

	@Parsed(field = "ALIAS")
	private String alias;

	@Parsed(field = "CITY")
	private String city;

	@Parsed(field = "COUNTRY")
	private String country;

	@Parsed(field = "EMAIL")
	private String email;

	@Parsed(field = "EMAIL_ENCODING")
	private String emailEconding;

	@Parsed(field = "FIRST_NAME")
	private String firstName;

	@Parsed(field = "LANGUAGE")
	private String language;

	@Parsed(field = "LAST_NAME")
	private String lastName;

	@Parsed(field = "LOCALE")
	private String locale;

	@Parsed(field = "NAME")
	private String name;

	@Parsed(field = "NICKNAME")
	private String nickName;

	@Parsed(field = "POSTAL_CODE")
	private String postalCode;

	@Parsed(field = "PROFILE_ID")
	private String profileId;

	@Parsed(field = "STATE")
	private String state;

	@Parsed(field = "STREET")
	private String street;

	@Parsed(field = "TIME_ZONE")
	private String timeZone;

	@Parsed(field = "USERNAME")
	private String userName;

}