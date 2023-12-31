/*
 * Copyright 2023 anominy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.anominy.steamid.utils;

/**
 * A Steam URLs utility.
 *
 * <p>{@code USteamUrl} is the utility class
 * for Steam related URLs.
 */
@SuppressWarnings("unused")
public final class USteamUrl {

	/**
	 * A Steam vanity /id/ URL string.
	 */
	public static final String VANITY = url(USteamDomain.COMMUNITY, USteamEndpoint.ID);

	/**
	 * A Steam worldwide /profiles/ URL string.
	 */
	public static final String PROFILE = url(USteamDomain.COMMUNITY, USteamEndpoint.PROFILES);

	/**
	 * A Steam /user/ URL string.
	 */
	public static final String USER = url(USteamDomain.COMMUNITY, USteamEndpoint.USER);

	/**
	 * A Steam invite /p/ URL string.
	 */
	public static final String INVITE = url(USteamDomain.INVITE, USteamEndpoint.P);

	/**
	 * A Steam China /profiles/ URL string.
	 */
	public static final String CHINA = url(USteamDomain.CHINA, USteamEndpoint.PROFILES);

	/**
	 * Make a Steam URL.
	 *
	 * @param domain	Steam domain to include in URL
	 * @param endpoint	Steam endpoint to include in URL
	 * @return			Steam URL pointing to the endpoint w/ specified domain
	 *
	 * @throws IllegalArgumentException	if domain is {@code null/empty} or endpoint is {@code null/empty}
	 */
	private static String url(String domain, String endpoint) {
		if (domain == null) {
			throw new IllegalArgumentException("Domain mustn't be <null>");
		}

		if (domain.isEmpty()) {
			throw new IllegalArgumentException("Domain mustn't be <empty>");
		}

		if (endpoint == null) {
			throw new IllegalArgumentException("Endpoint mustn't be <null>");
		}

		if (endpoint.isEmpty()) {
			throw new IllegalArgumentException("Endpoint mustn't be <empty>");
		}

		return "https://" + domain + "/" + endpoint + "/";
	}

	private USteamUrl() {
		throw new UnsupportedOperationException();
	}
}
