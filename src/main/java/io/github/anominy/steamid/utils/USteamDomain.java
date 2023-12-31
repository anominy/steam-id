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
 * A Steam domains utility.
 *
 * <p>{@code USteamDomain} is the utility class
 * for Steam related domain strings.
 */
@SuppressWarnings("unused")
public final class USteamDomain {

	/**
	 * A worldwide Steam community domain string.
	 */
	public static final String COMMUNITY = "steamcommunity.com";

	/**
	 * A Steam invite domain string.
	 */
	public static final String INVITE = "s.team";

	/**
	 * A Steam China community domain string.
	 */
	public static final String CHINA = "my.steamchina.com";

	private USteamDomain() {
		throw new UnsupportedOperationException();
	}
}
