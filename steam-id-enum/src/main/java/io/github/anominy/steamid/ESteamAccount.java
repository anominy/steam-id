/*
 * Copyright 2024 anominy
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

package io.github.anominy.steamid;

import io.github.anominy.uwutils.UwArray;
import io.github.anominy.uwutils.UwObject;
import io.github.anominy.uwutils.VoidSupplier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;
import org.jetbrains.annotations.Unmodifiable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * A Steam account type enums.
 */
@SuppressWarnings({"unused", "DefaultAnnotationParam"})
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
     * A base account type enum.
     *
     * <p>Wraps {@link #INVALID}.
     */
    @NotNull
    public static final ESteamAccount BASE = INVALID;

    /**
     * A minimum account type enum.
     *
     * <p>Wraps {@link #INDIVIDUAL}.
     */
    @NotNull
    public static final ESteamAccount MIN = INDIVIDUAL;

    /**
     * A maximum account type enum.
     *
     * <p>Wraps {@link #UNKNOWN}.
     */
    @NotNull
    public static final ESteamAccount MAX = UNKNOWN;

    /**
     * An attribute name of this identifier.
     */
    @NotNull
    public static final String ATTRIBUTE_NAME_ID = "id";

    /**
     * An attribute name of this character.
     */
    @NotNull
    public static final String ATTRIBUTE_NAME_CHAR = "ch";

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
    @UnknownNullability
    private volatile String stringCache;

    /**
     * A {@link #stringCache} mutex.
     */
    @NotNull
    private final Object stringCacheMutex;

    /**
     * Initialize an {@link ESteamAccount} instance.
     *
     * @param id    account type identifier
     * @param ch    account type character
     */
    @Contract(pure = true)
    ESteamAccount(
            final int id,
            final char ch
    ) {
        this.id = id;
        this.ch = ch;

        this.stringCache = null;

        this.stringCacheMutex = new Object();
    }

    /**
     * Get this account type identifier.
     *
     * @return  account type identifier
     */
    @Contract(pure = true)
    public int getId() {
        return this.id;
    }

    /**
     * Get this account type character.
     *
     * @return  account type character
     */
    @Contract(pure = true)
    public char getChar() {
        return this.ch;
    }

    /**
     * Get this {@link #toString()} cache.
     *
     * @return  string cache
     */
    @UnknownNullability
    @Contract(pure = true)
    public String getStringCache() {
        return this.stringCache;
    }

    /**
     * Check if {@link #toString()} is cached.
     *
     * @return  {@code true} if cached
     *          or {@code false} otherwise
     */
    @Contract(pure = true)
    public boolean isStringCached() {
        return this.stringCache != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    @Contract(pure = true)
    public String toString() {
        if (this.stringCache != null) {
            return this.stringCache;
        }

        synchronized (this.stringCacheMutex) {
            if (this.stringCache != null) {
                return this.stringCache;
            }

            final String className = ESteamAccount.class.getSimpleName();
            final String instanceName = this.name();

            return (this.stringCache = className
                    + "::"
                    + instanceName
                    + "["
                    + ATTRIBUTE_NAME_ID
                    + "="
                    + this.id
                    + ", "
                    + ATTRIBUTE_NAME_CHAR
                    + "="
                    + this.ch
                    + "]");
        }
    }

    /**
     * Get an unmodifiable list of all Steam account type enums.
     *
     * @return  unmodifiable list
     */
    @NotNull
    @Unmodifiable
    @Contract(pure = true)
    public static List<@NotNull ESteamAccount> getValueList() {
        return SingletonValueList.INSTANCE;
    }

    /**
     * Get an {@link ESteamAccount} instance by its account type identifier
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromIdNoCheck(int)}.
     *
     * @param id            account type identifier of the instance, may be null
     * @param defaultValue  default value to return on failure, may be null
     *
     * @return  associated {@link ESteamAccount} instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, _ -> param2", pure = true)
    public static ESteamAccount fromIdOrElse(
            @Nullable
            final Integer id,

            @Nullable
            final ESteamAccount defaultValue
    ) {
        return UwObject.ifNotNullNoCheck(id, ESteamAccount::fromIdNoCheck, defaultValue);
    }

    /**
     * Get an {@link ESteamAccount} instance by its account type identifier
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromIdOrNull(Integer)}.
     *
     * @param id                    account type identifier of the instance, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  associated {@link ESteamAccount} instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, null -> null", pure = false)
    public static ESteamAccount fromIdOrElse(
            @Nullable
            final Integer id,

            @Nullable
            final Supplier<@UnknownNullability ESteamAccount> defaultValueSupplier
    ) {
        return UwObject.ifNull(fromIdOrNull(id), defaultValueSupplier);
    }

    /**
     * Get an {@link ESteamAccount} instance by its account type identifier
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromIdOrElse(Integer, Supplier)}.
     *
     * @param id                    account type identifier of the instance, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  associated {@link ESteamAccount} instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, _ -> null", pure = false)
    public static ESteamAccount fromIdOrElse(
            @Nullable
            final Integer id,

            @Nullable
            final VoidSupplier<@UnknownNullability ESteamAccount> defaultValueSupplier
    ) {
        return fromIdOrElse(id, (Supplier<@UnknownNullability ESteamAccount>) defaultValueSupplier);
    }

    /**
     * Get an {@link ESteamAccount} instance by its account type identifier
     * or return the {@link #BASE} instance on failure.
     *
     * <p>Wraps {@link #fromIdOrElse(Integer, ESteamAccount)}
     * w/ {@link #BASE} as the default value.
     *
     * @param id    account type identifier of the instance, may be null
     *
     * @return  associated {@link ESteamAccount} instance or {@link #BASE}
     */
    @NotNull
    @Contract(pure = true)
    public static ESteamAccount fromIdOrBase(
            @Nullable
            final Integer id
    ) {
        return fromIdOrElse(id, BASE);
    }

    /**
     * Get an {@link ESteamAccount} instance by its account type identifier
     * or return the {@link #MIN} instance on failure.
     *
     * <p>Wraps {@link #fromIdOrElse(Integer, ESteamAccount)}
     * w/ {@link #MIN} as the default value.
     *
     * @param id    account type identifier of the instance, may be null
     *
     * @return  associated {@link ESteamAccount} instance or {@link #MIN}
     */
    @NotNull
    @Contract(pure = true)
    public static ESteamAccount fromIdOrMin(
            @Nullable
            final Integer id
    ) {
        return fromIdOrElse(id, MIN);
    }

    /**
     * Get an {@link ESteamAccount} instance by its account type identifier
     * or return the {@link #MAX} instance on failure.
     *
     * <p>Wraps {@link #fromIdOrElse(Integer, ESteamAccount)}
     * w/ {@link #MAX} as the default value.
     *
     * @param id    account type identifier of the instance, may be null
     *
     * @return  associated {@link ESteamAccount} instance or {@link #MAX}
     */
    @NotNull
    @Contract(pure = true)
    public static ESteamAccount fromIdOrMax(
            @Nullable
            final Integer id
    ) {
        return fromIdOrElse(id, MAX);
    }

    /**
     * Get an {@link ESteamAccount} instance by its account type identifier
     * or return {@code null} on failure.
     *
     * <p>Wraps {@link #fromIdOrElse(Integer, ESteamAccount)}
     * w/ {@code null} as the default value.
     *
     * @param id    account type identifier of the instance, may be null
     *
     * @return  associated {@link ESteamAccount} instance or {@code null}
     */
    @UnknownNullability
    @Contract(value = "null -> null", pure = true)
    public static ESteamAccount fromIdOrNull(
            @Nullable
            final Integer id
    ) {
        return fromIdOrElse(id, (@Nullable ESteamAccount) null);
    }

    /**
     * Get an {@link ESteamAccount} instance by its account type identifier.
     *
     * <p>Wraps {@link Map#get(Object)}.
     *
     * @param id    account type identifier of the instance
     *
     * @return  associated {@link ESteamAccount} instance
     */
    @UnknownNullability
    @Contract(pure = true)
    public static ESteamAccount fromIdNoCheck(
            final int id
    ) {
        return SingletonMapById.INSTANCE.get(id);
    }

    /**
     * Get an {@link ESteamAccount} instance by its account type character
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromCharNoCheck(char)}.
     *
     * @param ch            account type character of the instance, may be null
     * @param defaultValue  default value to return on failure, may be null
     *
     * @return  associated {@link ESteamAccount} instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, _ -> param2", pure = true)
    public static ESteamAccount fromCharOrElse(
            @Nullable
            final Character ch,

            @Nullable
            final ESteamAccount defaultValue
    ) {
        return UwObject.ifNotNullNoCheck(ch, ESteamAccount::fromCharNoCheck, defaultValue);
    }

    /**
     * Get an {@link ESteamAccount} instance by its account type character
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromCharOrNull(Character)}.
     *
     * @param ch                    account type character of the instance, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  associated {@link ESteamAccount} instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, null -> null", pure = false)
    public static ESteamAccount fromCharOrElse(
            @Nullable
            final Character ch,

            @Nullable
            final Supplier<@UnknownNullability ESteamAccount> defaultValueSupplier
    ) {
        return UwObject.ifNull(fromCharOrNull(ch), defaultValueSupplier);
    }

    /**
     * Get an {@link ESteamAccount} instance by its account type character
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromCharOrElse(Character, Supplier)}.
     *
     * @param ch                    account type character of the instance, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  associated {@link ESteamAccount} instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, _ -> null", pure = false)
    public static ESteamAccount fromCharOrElse(
            @Nullable
            final Character ch,

            @Nullable
            final VoidSupplier<@UnknownNullability ESteamAccount> defaultValueSupplier
    ) {
        return fromCharOrElse(ch, (Supplier<@UnknownNullability ESteamAccount>) defaultValueSupplier);
    }

    /**
     * Get an {@link ESteamAccount} instance by its account type character
     * or return the {@link #BASE} instance on failure.
     *
     * <p>Wraps {@link #fromCharOrElse(Character, ESteamAccount)}
     * w/ {@link #BASE} as the default value.
     *
     * @param ch    account type character of the instance, may be null
     *
     * @return  associated {@link ESteamAccount} instance or {@link #BASE}
     */
    @NotNull
    @Contract(pure = true)
    public static ESteamAccount fromCharOrBase(
            @Nullable
            final Character ch
    )  {
        return fromCharOrElse(ch, BASE);
    }

    /**
     * Get an {@link ESteamAccount} instance by its account type character
     * or return the {@link #MIN} instance on failure.
     *
     * <p>Wraps {@link #fromCharOrElse(Character, ESteamAccount)}
     * w/ {@link #MIN} as the default value.
     *
     * @param ch    account type character of the instance, may be null
     *
     * @return  associated {@link ESteamAccount} instance or {@link #MIN}
     */
    @NotNull
    @Contract(pure = true)
    public static ESteamAccount fromCharOrMin(
            @Nullable
            final Character ch
    ) {
        return fromCharOrElse(ch, MIN);
    }

    /**
     * Get an {@link ESteamAccount} instance by its account type character
     * or return the {@link #MAX} instance on failure.
     *
     * <p>Wraps {@link #fromCharOrElse(Character, ESteamAccount)}
     * w/ {@link #MAX} as the default value.
     *
     * @param ch    account type character of the instance, may be null
     *
     * @return  associated {@link ESteamAccount} instance or {@link #MAX}
     */
    @NotNull
    @Contract(pure = true)
    public static ESteamAccount fromCharOrMax(
            @Nullable
            final Character ch
    ) {
        return fromCharOrElse(ch, MAX);
    }

    /**
     * Get an {@link ESteamAccount} instance by its account type character
     * or return {@code null} on failure.
     *
     * <p>Wraps {@link #fromCharOrElse(Character, ESteamAccount)}
     * w/ {@code null} as the default value.
     *
     * @param ch    account type character of the instance, may be null
     *
     * @return  associated {@link ESteamAccount} instance or {@code null}
     */
    @UnknownNullability
    @Contract(value = "null -> null", pure = true)
    public static ESteamAccount fromCharOrNull(
            @Nullable
            final Character ch
    ) {
        return fromCharOrElse(ch, (@Nullable ESteamAccount) null);
    }

    /**
     * Get an {@link ESteamAccount} instance by its account type character.
     *
     * <p>Wraps {@link Map#get(Object)}.
     *
     * @param ch    account type character of the instance
     *
     * @return  associated {@link ESteamAccount} instance
     */
    @UnknownNullability
    @Contract(pure = true)
    public static ESteamAccount fromCharNoCheck(
            final char ch
    ) {
        return SingletonMapByChar.INSTANCE.get(ch);
    }

    /**
     * Get an {@link ESteamAccount} instance by its index
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromIndexNoCheck(int)}.
     *
     * @param index         index of the instance, may be null
     * @param defaultValue  default value to return on failure, may be null
     *
     * @return  associated {@link ESteamAccount} instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, _ -> param2", pure = true)
    public static ESteamAccount fromIndexOrElse(
            @Nullable
            final Integer index,

            @Nullable
            final ESteamAccount defaultValue
    ) {
        final ESteamAccount result = UwObject.ifNotNullNoCheck(index, ESteamAccount::fromIndexNoCheck, defaultValue);
        return UwObject.ifNull(result, defaultValue);
    }

    /**
     * Get an {@link ESteamAccount} instance by its index
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromIndexOrNull(Integer)}.
     *
     * @param index                 index of the instance, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  associated {@link ESteamAccount} instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, null -> null", pure = false)
    public static ESteamAccount fromIndexOrElse(
            @Nullable
            final Integer index,

            @Nullable
            final Supplier<@UnknownNullability ESteamAccount> defaultValueSupplier
    ) {
        return UwObject.ifNull(fromIndexOrNull(index), defaultValueSupplier);
    }

    /**
     * Get an {@link ESteamAccount} instance by its index
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromIndexOrElse(Integer, Supplier)}.
     *
     * @param index                 index of the instance, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  associated {@link ESteamAccount} instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, _ -> null", pure = false)
    public static ESteamAccount fromIndexOrElse(
            @Nullable
            final Integer index,

            @Nullable
            final VoidSupplier<@UnknownNullability ESteamAccount> defaultValueSupplier
    ) {
        return fromIndexOrElse(index, (Supplier<@UnknownNullability ESteamAccount>) defaultValueSupplier);
    }

    /**
     * Get an {@link ESteamAccount} instance by its index
     * or return the {@link #BASE} instance on failure.
     *
     * <p>Wraps {@link #fromIndexOrElse(Integer, ESteamAccount)}
     * w/ {@link #BASE} as the default value.
     *
     * @param index     index of the instance, may be null
     *
     * @return  associated {@link ESteamAccount} instance or {@link #BASE}
     */
    @NotNull
    @Contract(pure = true)
    public static ESteamAccount fromIndexOrBase(
            @Nullable
            final Integer index
    ) {
        return fromIndexOrElse(index, BASE);
    }

    /**
     * Get an {@link ESteamAccount} instance by its index
     * or return the {@link #MIN} instance on failure.
     *
     * <p>Wraps {@link #fromIndexOrElse(Integer, ESteamAccount)}
     * w/ {@link #MIN} as the default value.
     *
     * @param index     index of the instance, may be null
     *
     * @return  associated {@link ESteamAccount} instance or {@link #MIN}
     */
    @NotNull
    @Contract(pure = true)
    public static ESteamAccount fromIndexOrMin(
            @Nullable
            final Integer index
    ) {
        return fromIndexOrElse(index, MIN);
    }

    /**
     * Get an {@link ESteamAccount} instance by its index
     * or return the {@link #MAX} instance on failure.
     *
     * <p>Wraps {@link #fromIndexOrElse(Integer, ESteamAccount)}
     * w/ {@link #MAX} as the default value.
     *
     * @param index     index of the instance, may be null
     *
     * @return  associated {@link ESteamAccount} instance or {@link #MAX}
     */
    @NotNull
    @Contract(pure = true)
    public static ESteamAccount fromIndexOrMax(
            @Nullable
            final Integer index
    ) {
        return fromIndexOrElse(index, MAX);
    }

    /**
     * Get an {@link ESteamAccount} instance by its index
     * or return {@code null} instance on failure.
     *
     * <p>Wraps {@link #fromIndexOrElse(Integer, ESteamAccount)}
     * w/ {@code null} as the default value.
     *
     * @param index     index of the instance, may be null
     *
     * @return  associated {@link ESteamAccount} instance or {@code null}
     */
    @UnknownNullability
    @Contract(value = "null -> null", pure = true)
    public static ESteamAccount fromIndexOrNull(
            @Nullable
            final Integer index
    ) {
        return fromIndexOrElse(index, (@Nullable ESteamAccount) null);
    }

    /**
     * Get an {@link ESteamAccount} instance by its index.
     *
     * <p>Wraps {@link UwArray#getNoCheck(Object[], int)}.
     *
     * @param index     index of the instance, may be null
     *
     * @return  associated {@link ESteamAccount} instance
     */
    @UnknownNullability
    @Contract(pure = true)
    public static ESteamAccount fromIndexNoCheck(
            final int index
    ) {
        return UwArray.getNoCheck(SingletonValues.INSTANCE, index);
    }

    private static final class SingletonValues {
        @NotNull
        public static final ESteamAccount @NotNull [] INSTANCE = ESteamAccount.values();

        @Contract(value = "-> fail", pure = false)
        private SingletonValues() {
            throw new UnsupportedOperationException();
        }
    }

    private static final class SingletonValueList {
        @NotNull
        @Unmodifiable
        public static final List<@NotNull ESteamAccount> INSTANCE;

        static {
            final ESteamAccount[] values = SingletonValues.INSTANCE;
            final List<ESteamAccount> list = new ArrayList<>(values.length);

            //noinspection ManualArrayToCollectionCopy
            for (final ESteamAccount value : values) {
                //noinspection UseBulkOperation
                list.add(value);
            }

            INSTANCE = Collections.unmodifiableList(list);
        }

        @Contract(value = "-> fail", pure = false)
        private SingletonValueList() {
            throw new UnsupportedOperationException();
        }
    }

    private static final class SingletonMapById {
        @NotNull
        @Unmodifiable
        public static final Map<@NotNull Integer, @NotNull ESteamAccount> INSTANCE;

        static {
            final ESteamAccount[] values = SingletonValues.INSTANCE;
            final Map<Integer, ESteamAccount> map = new HashMap<>();

            for (final ESteamAccount value : values) {
                if (map.containsKey(value.id)) {
                    throw new IllegalStateException("Identifier field of ESteamAccount class must be unique");
                }

                map.put(value.id, value);
            }

            INSTANCE = Collections.unmodifiableMap(map);
        }

        @Contract(value = "-> fail", pure = false)
        private SingletonMapById() {
            throw new UnsupportedOperationException();
        }
    }

    private static final class SingletonMapByChar {
        @NotNull
        @Unmodifiable
        public static final Map<@NotNull Character, @NotNull ESteamAccount> INSTANCE;

        static {
            final ESteamAccount[] values = SingletonValues.INSTANCE;
            final Map<Character, ESteamAccount> map = new HashMap<>();

            for (final ESteamAccount value : values) {
                if (map.containsKey(value.ch)) {
                    throw new IllegalStateException("Character identifier of ESteamAccount class must be unique");
                }

                map.put(value.ch, value);
            }

            INSTANCE = Collections.unmodifiableMap(map);
        }

        @Contract(value = "-> fail", pure = false)
        private SingletonMapByChar() {
            throw new UnsupportedOperationException();
        }
    }
}
