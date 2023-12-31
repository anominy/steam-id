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

package io.github.anominy.steamid.types;

import io.github.anominy.steamid.utils.USteamAccount;
import io.github.anominy.uwutils.UwArray;
import io.github.anominy.uwutils.UwEnum;
import io.github.anominy.uwutils.UwMap;
import io.github.anominy.uwutils.UwObject;

import java.util.Map;
import java.util.function.Supplier;

/**
 * A Steam account type enums.
 *
 * <p>Wraps {@link USteamAccount}.
 */
@SuppressWarnings("unused")
public enum ESteamAccount {

	/**
	 * An account type enum - Invalid.
	 *
	 * <p>Wraps {@link USteamAccount#INVALID_ID} and {@link USteamAccount#INVALID_CHAR}.
	 */
	INVALID(USteamAccount.INVALID_ID, USteamAccount.INVALID_CHAR),

	/**
	 * An account type enum - Individual.
	 *
	 * <p>Wraps {@link USteamAccount#INDIVIDUAL_ID} and {@link USteamAccount#INDIVIDUAL_CHAR}.
	 */
	INDIVIDUAL(USteamAccount.INDIVIDUAL_ID, USteamAccount.INDIVIDUAL_CHAR),

	/**
	 * An account type enum - Multiseat.
	 *
	 * <p>Wraps {@link USteamAccount#MULTISEAT_ID} and {@link USteamAccount#MULTISEAT_CHAR}.
	 */
	MULTISEAT(USteamAccount.MULTISEAT_ID, USteamAccount.MULTISEAT_CHAR),

	/**
	 * An account type enum - Game Server.
	 *
	 * <p>Wraps {@link USteamAccount#GAME_SERVER_ID} and {@link USteamAccount#GAME_SERVER_CHAR}.
	 */
	GAME_SERVER(USteamAccount.GAME_SERVER_ID, USteamAccount.GAME_SERVER_CHAR),

	/**
	 * An account type enum - Anonymous Game Server.
	 *
	 * <p>Wraps {@link USteamAccount#ANON_GAME_SERVER_ID} and {@link USteamAccount#ANON_GAME_SERVER_CHAR}.
	 */
	ANON_GAME_SERVER(USteamAccount.ANON_GAME_SERVER_ID, USteamAccount.ANON_GAME_SERVER_CHAR),

	/**
	 * An account type enum - Pending.
	 *
	 * <p>Wraps {@link USteamAccount#PENDING_ID} and {@link USteamAccount#PENDING_CHAR}.
	 */
	PENDING(USteamAccount.PENDING_ID, USteamAccount.PENDING_CHAR),

	/**
	 * An account type enum - Content Server.
	 *
	 * <p>Wraps {@link USteamAccount#CONTENT_SERVER_ID} and {@link USteamAccount#CONTENT_SERVER_CHAR}.
	 */
	CONTENT_SERVER(USteamAccount.CONTENT_SERVER_ID, USteamAccount.CONTENT_SERVER_CHAR),

	/**
	 * An account type enum - Clan.
	 *
	 * <p>Wraps {@link USteamAccount#CLAN_ID} and {@link USteamAccount#CLAN_CHAR}.
	 */
	CLAN(USteamAccount.CLAN_ID, USteamAccount.CLAN_CHAR),

	/**
	 * An account type enum - Chat.
	 *
	 * <p>Wraps {@link USteamAccount#CHAT_ID} and {@link USteamAccount#CHAT_CHAR}.
	 */
	CHAT(USteamAccount.CHAT_ID, USteamAccount.CHAT_CHAR),

	/**
	 * An account type enum - Console User/P2P SuperSeeder.
	 *
	 * <p>Wraps {@link USteamAccount#CONSOLE_USER_ID} and {@link USteamAccount#CONSOLE_USER_CHAR}.
	 */
	CONSOLE_USER(USteamAccount.CONSOLE_USER_ID, USteamAccount.CONSOLE_USER_CHAR),

	/**
	 * An account type enum - Anonymous User.
	 *
	 * <p>Wraps {@link USteamAccount#ANON_USER_ID} and {@link USteamAccount#ANON_USER_CHAR}.
	 */
	ANON_USER(USteamAccount.ANON_USER_ID, USteamAccount.ANON_USER_CHAR),

	/**
	 * An account type enum - Unknown.
	 *
	 * <p>Wraps {@link USteamAccount#UNKNOWN_ID} and {@link USteamAccount#UNKNOWN_CHAR}.
	 */
	UNKNOWN(USteamAccount.UNKNOWN_ID, USteamAccount.UNKNOWN_CHAR);

	/**
	 * A base account type according to its utility alternative.
	 *
	 * @see USteamAccount#BASE_ID
	 */
	public static final ESteamAccount BASE = INVALID;

	/**
	 * A minimum account type according to its utility alternative.
	 *
	 * @see USteamAccount#MIN_ID
	 */
	public static final ESteamAccount MIN = INDIVIDUAL;

	/**
	 * A maximum account type according to its utility alternative.
	 *
	 * @see USteamAccount#MAX_ID
	 */
	public static final ESteamAccount MAX = UNKNOWN;

	/**
	 * A class instance of this class.
	 */
	private static final Class<ESteamAccount> CLASS = ESteamAccount.class;

	/**
	 * A simple name of this class.
	 */
	private static final String SIMPLE_NAME = CLASS.getSimpleName();

	/**
	 * An array of {@link ESteamAccount} instances.
	 */
	private static final ESteamAccount[] VALUES = UwEnum.values(CLASS);

	/**
	 * A map of {@link ESteamAccount} instances by their identifier field.
	 */
	private static final Map<Integer, ESteamAccount> MAP_BY_ID = UwMap.createByFieldOrNull(
			entry -> entry.id, VALUES
	);

	/**
	 * A map of {@link ESteamAccount} instances by their character field.
	 */
	private static final Map<Character, ESteamAccount> MAP_BY_CHAR = UwMap.createByFieldOrNull(
			entry -> entry.ch, VALUES
	);

	/**
	 * An account type identifier.
	 */
	private final int id;

	/**
	 * An account type character.
	 */
	private final char ch;

	/**
	 * A {@link #toString()} cache.
	 */
	private volatile String stringCache;

	/**
	 * A {@link #stringCache} mutex.
	 */
	private final Object stringCacheMutex;

	/**
	 * Initialize an {@link ESteamAccount} instance.
	 *
	 * @param id	account type identifier
	 * @param ch	account type character
	 */
	ESteamAccount(int id, char ch) {
		this.id = id;
		this.ch = ch;

		this.stringCacheMutex = new Object();
	}

	/**
	 * Get this account type identifier.
	 *
	 * @return	account type identifier
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Get this account type character.
	 *
	 * @return	account type character
	 */
	public char getChar() {
		return this.ch;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		if (this.stringCache != null) {
			return this.stringCache;
		}

		synchronized (this.stringCacheMutex) {
			if (this.stringCache != null) {
				return this.stringCache;
			}

			return (this.stringCache = SIMPLE_NAME + "::" + this.name() + "["
					+ "id=" + this.id
					+ ", ch='" + this.ch + "'"
					+ "]");
		}
	}

	/**
	 * Get the account type identifier from the provided {@link ESteamAccount} instance
	 * or return a default value if failed.
	 *
	 * <p>Possible failure cases:
	 * <ul>
	 *     <li>{@link ESteamAccount} instance is {@code null}.
	 * </ul>
	 *
	 * @param account		enum value of the account type from which get the identifier
	 * @param defaultValue	default value to return on failure
	 * @return				account type identifier or the default value
	 */
	public static Integer getIdOrElse(ESteamAccount account, Integer defaultValue) {
		return UwObject.ifNotNull(account, ESteamAccount::getId, defaultValue);
	}

	/**
	 * Get the account type identifier from the provided {@link ESteamAccount} instance
	 * or return a default value if failed.
	 *
	 * <p>Possible failure cases:
	 * <ul>
	 *     <li>{@link ESteamAccount} instance is {@code null}.
	 * </ul>
	 *
	 * @param account				enum value of the account type from which get the identifier
	 * @param defaultValueSupplier	supplier from which get the default value
	 * @return						account type identifier or the default value
	 */
	public static Integer getIdOrElse(ESteamAccount account, Supplier<Integer> defaultValueSupplier) {
		return UwObject.ifNull(getIdOrNull(account), defaultValueSupplier);
	}

	/**
	 * Get the account type identifier from the provided {@link ESteamAccount} instance
	 * or return the {@link USteamAccount#BASE_ID} value if failed.
	 *
	 * <p>Possible failure cases:
	 * <ul>
	 *     <li>{@link ESteamAccount} instance is {@code null}.
	 * </ul>
	 *
	 * <p>Wraps {@link #getIdOrElse(ESteamAccount, Integer)}
	 * w/ {@link USteamAccount#BASE_ID} as the default value.
	 *
	 * @param account	enum value of the account type from which get the identifier
	 * @return			account type identifier or the {@link USteamAccount#BASE_ID} value
	 */
	public static Integer getIdOrBase(ESteamAccount account) {
		return getIdOrElse(account, USteamAccount.BASE_ID);
	}

	/**
	 * Get the account type identifier from the provided {@link ESteamAccount} instance
	 * or return {@code null} if failed.
	 *
	 * <p>Possible failure cases:
	 * <ul>
	 *     <li>{@link ESteamAccount} instance is {@code null}.
	 * </ul>
	 *
	 * <p>Wraps {@link #getIdOrElse(ESteamAccount, Integer)}
	 * w/ {@code null} as the default value.
	 *
	 * @param account	enum value of the account type from which get the identifier
	 * @return			account type identifier or {@code null}
	 */
	public static Integer getIdOrNull(ESteamAccount account) {
		return getIdOrElse(account, (Integer) null);
	}

	/**
	 * Get the account type character from the provided {@link ESteamAccount} instance
	 * or return a default value if failed.
	 *
	 * <p>Possible failure cases:
	 * <ul>
	 *     <li>{@link ESteamAccount} instance is {@code null}.
	 * </ul>
	 *
	 * @param account		enum value of the account type from which get the character
	 * @param defaultValue	default value to return on failure
	 * @return				account type character or the default value
	 */
	public static Character getCharOrElse(ESteamAccount account, Character defaultValue) {
		return UwObject.ifNotNull(account, ESteamAccount::getChar, defaultValue);
	}

	/**
	 * Get the account type character from the provided {@link ESteamAccount} instance
	 * or return a default value if failed.
	 *
	 * <p>Possible failure cases:
	 * <ul>
	 *     <li>{@link ESteamAccount} instance is {@code null}.
	 * </ul>
	 *
	 * @param account				enum value of the account type from which get the character
	 * @param defaultValueSupplier	supplier from which get the default value
	 * @return						account type character or the default value
	 */
	public static Character getCharOrElse(ESteamAccount account, Supplier<Character> defaultValueSupplier) {
		return UwObject.ifNull(getCharOrNull(account), defaultValueSupplier);
	}

	/**
	 * Get the account type character from the provided {@link ESteamAccount} instance
	 * or return the {@link USteamAccount#BASE_CHAR} value if failed.
	 *
	 * <p>Possible failure cases:
	 * <ul>
	 *     <li>{@link ESteamAccount} instance is {@code null}.
	 * </ul>
	 *
	 * <p>Wraps {@link #getIdOrElse(ESteamAccount, Integer)}
	 * w/ {@link USteamAccount#BASE_CHAR} as the default value.
	 *
	 * @param account	enum value of the account type from which get the character
	 * @return			account type character or the {@link USteamAccount#BASE_CHAR} value
	 */
	public static Character getCharOrBase(ESteamAccount account) {
		return getCharOrElse(account, USteamAccount.BASE_CHAR);
	}

	/**
	 * Get the account type character from the provided {@link ESteamAccount} instance
	 * or return {@code null} if failed.
	 *
	 * <p>Possible failure cases:
	 * <ul>
	 *     <li>{@link ESteamAccount} instance is {@code null}.
	 * </ul>
	 *
	 * <p>Wraps {@link #getCharOrElse(ESteamAccount, Character)}
	 * w/ {@code null} as the default value.
	 *
	 * @param account	enum value of the account type from which get the character
	 * @return			account type character or {@code null}
	 */
	public static Character getCharOrNull(ESteamAccount account) {
		return getCharOrElse(account, (Character) null);
	}

	/**
	 * Get an {@link ESteamAccount} instance by its account type identifier
	 * or return a default value if failed.
	 *
	 * @param id			account type identifier of the instance
	 * @param defaultValue	default value to return on failure
	 * @return				associated {@link ESteamAccount} instance or the default values
	 */
	public static ESteamAccount fromIdOrElse(Integer id, ESteamAccount defaultValue) {
		return UwMap.getOrElse(id, MAP_BY_ID, defaultValue);
	}

	/**
	 * Get an {@link ESteamAccount} instance by its account type identifier
	 * or return a default value if failed.
	 *
	 * @param id					account type identifier of the instance
	 * @param defaultValueSupplier	supplier from which get the default value
	 * @return						associated {@link ESteamAccount} instance or the default values
	 */
	public static ESteamAccount fromIdOrElse(Integer id, Supplier<ESteamAccount> defaultValueSupplier) {
		return UwObject.ifNull(fromIdOrNull(id), defaultValueSupplier);
	}

	/**
	 * Get an {@link ESteamAccount} instance by its account type identifier
	 * or return the base value if failed.
	 *
	 * <p>Wraps {@link #fromIdOrElse(Integer, ESteamAccount)}
	 * w/ {@link #BASE} as the default value.
	 *
	 * @param id	account type identifier of the instance
	 * @return		associated {@link ESteamAccount} instance or the base value
	 */
	public static ESteamAccount fromIdOrBase(Integer id) {
		return fromIdOrElse(id, BASE);
	}

	/**
	 * Get an {@link ESteamAccount} instance by its account type identifier
	 * or return {@code null} if failed.
	 *
	 * <p>Wraps {@link #fromIdOrElse(Integer, ESteamAccount)}
	 * w/ {@code null} as the default value.
	 *
	 * @param id	account type identifier of the instance
	 * @return		associated {@link ESteamAccount} instance or {@code null}
	 */
	public static ESteamAccount fromIdOrNull(Integer id) {
		return fromIdOrElse(id, (ESteamAccount) null);
	}

	/**
	 * Get an {@link ESteamAccount} instance by its account type character
	 * or return a default value if failed.
	 *
	 * @param ch			account type character of the instance
	 * @param defaultValue	default value to return on failure
	 * @return				associated {@link ESteamAccount} instance or the default value
	 */
	public static ESteamAccount fromCharOrElse(Character ch, ESteamAccount defaultValue) {
		return UwMap.getOrElse(ch, MAP_BY_CHAR, defaultValue);
	}

	/**
	 * Get an {@link ESteamAccount} instance by its account type character
	 * or return a default value if failed.
	 *
	 * @param ch					account type character of the instance
	 * @param defaultValueSupplier	supplier from which get the default value
	 * @return						associated {@link ESteamAccount} instance or the default value
	 */
	public static ESteamAccount fromCharOrElse(Character ch, Supplier<ESteamAccount> defaultValueSupplier) {
		return UwObject.ifNull(fromCharOrNull(ch), defaultValueSupplier);
	}

	/**
	 * Get an {@link ESteamAccount} instance by its account type character
	 * or return the base value if failed.
	 *
	 * <p>Wraps {@link #fromCharOrElse(Character, ESteamAccount)}
	 * w/ {@link #BASE} as the default value.
	 *
	 * @param ch	account type character of the instance
	 * @return		associated {@link ESteamAccount} instance or the base value
	 */
	public static ESteamAccount fromCharOrBase(Character ch) {
		return fromCharOrElse(ch, BASE);
	}

	/**
	 * Get an {@link ESteamAccount} instance by its account type character
	 * or return {@code null} if failed.
	 *
	 * <p>Wraps {@link #fromCharOrElse(Character, ESteamAccount)}
	 * w/ {@code null} as the default value.
	 *
	 * @param ch	account type character of the instance
	 * @return		associated {@link ESteamAccount} instance or {@code null}
	 */
	public static ESteamAccount fromCharOrNull(Character ch) {
		return fromCharOrElse(ch, (ESteamAccount) null);
	}

	/**
	 * Get an {@link ESteamAccount} instance by its index
	 * or return a default value if failed.
	 *
	 * @param index			index of the instance
	 * @param defaultValue	default value to return on failure
	 * @return				associated {@link ESteamAccount} instance or the default value
	 */
	public static ESteamAccount fromIndexOrElse(Integer index, ESteamAccount defaultValue) {
		return UwArray.getOrElse(index, VALUES, defaultValue);
	}

	/**
	 * Get an {@link ESteamAccount} instance by its index
	 * or return a default value if failed.
	 *
	 * @param index					index of the instance
	 * @param defaultValueSupplier	supplier from which get the default value
	 * @return						associated {@link ESteamAccount} instance or the default value
	 */
	public static ESteamAccount fromIndexOrElse(Integer index, Supplier<ESteamAccount> defaultValueSupplier) {
		return UwObject.ifNull(fromIndexOrNull(index), defaultValueSupplier);
	}

	/**
	 * Get an {@link ESteamAccount} instance by its index
	 * or return the base value if failed.
	 *
	 * <p>Wraps {@link #fromIndexOrElse(Integer, ESteamAccount)}
	 * w/ {@link #BASE} as the default value.
	 *
	 * @param index		index of the instance
	 * @return			associated {@link ESteamAccount} instance or the base value
	 */
	public static ESteamAccount fromIndexOrBase(Integer index) {
		return fromIndexOrElse(index, BASE);
	}

	/**
	 * Get an {@link ESteamAccount} instance by its index
	 * or return {@code null} if failed.
	 *
	 * <p>Wraps {@link ESteamAccount#fromIndexOrElse(Integer, ESteamAccount)}
	 * w/ {@code null} as the default value.
	 *
	 * @param index		index of the instance
	 * @return			associated {@link ESteamAccount} instance or {@code null}
	 */
	public static ESteamAccount fromIndexOrNull(Integer index) {
		return fromIndexOrElse(index, (ESteamAccount) null);
	}
}
