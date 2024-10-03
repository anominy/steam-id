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

@SuppressWarnings({"unused", "DefaultAnnotationParam"})
public enum ESteamAccount {
    INVALID(USteamAccount.INVALID_ID, USteamAccount.INVALID_CHAR),
    INDIVIDUAL(USteamAccount.INDIVIDUAL_ID, USteamAccount.INDIVIDUAL_CHAR),
    MULTISEAT(USteamAccount.MULTISEAT_ID, USteamAccount.MULTISEAT_CHAR),
    GAME_SERVER(USteamAccount.GAME_SERVER_ID, USteamAccount.GAME_SERVER_CHAR),
    ANON_GAME_SERVER(USteamAccount.ANON_GAME_SERVER_ID, USteamAccount.ANON_GAME_SERVER_CHAR),
    PENDING(USteamAccount.PENDING_ID, USteamAccount.PENDING_CHAR),
    CONTENT_SERVER(USteamAccount.CONTENT_SERVER_ID, USteamAccount.CONTENT_SERVER_CHAR),
    CLAN(USteamAccount.CLAN_ID, USteamAccount.CLAN_CHAR),
    CHAT(USteamAccount.CHAT_ID, USteamAccount.CHAT_CHAR),
    CONSOLE_USER(USteamAccount.CONSOLE_USER_ID, USteamAccount.CONSOLE_USER_CHAR),
    ANON_USER(USteamAccount.ANON_USER_ID, USteamAccount.ANON_USER_CHAR),
    UNKNOWN(USteamAccount.UNKNOWN_ID, USteamAccount.UNKNOWN_CHAR);

    @NotNull
    public static final ESteamAccount BASE = INVALID;

    @NotNull
    public static final ESteamAccount MIN = INDIVIDUAL;

    @NotNull
    public static final ESteamAccount MAX = UNKNOWN;

    @NotNull
    public static final String ATTRIBUTE_NAME_ID = "id";

    @NotNull
    public static final String ATTRIBUTE_NAME_CHAR = "ch";

    private final int id;
    private final char ch;

    @UnknownNullability
    private volatile String stringCache;

    @NotNull
    private final Object stringCacheMutex;

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

    @Contract(pure = true)
    public int getId() {
        return this.id;
    }

    @Contract(pure = true)
    public char getChar() {
        return this.ch;
    }

    @UnknownNullability
    @Contract(pure = true)
    public String getStringCache() {
        return this.stringCache;
    }

    @Contract(pure = true)
    public boolean isStringCached() {
        return this.stringCache != null;
    }

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

    @NotNull
    @Unmodifiable
    @Contract(pure = true)
    public static List<@NotNull ESteamAccount> getValueList() {
        return SingletonValueList.INSTANCE;
    }

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

    @NotNull
    @Contract(pure = true)
    public static ESteamAccount fromIdOrBase(
            @Nullable
            final Integer id
    ) {
        return fromIdOrElse(id, BASE);
    }

    @NotNull
    @Contract(pure = true)
    public static ESteamAccount fromIdOrMin(
            @Nullable
            final Integer id
    ) {
        return fromIdOrElse(id, MIN);
    }

    @NotNull
    @Contract(pure = true)
    public static ESteamAccount fromIdOrMax(
            @Nullable
            final Integer id
    ) {
        return fromIdOrElse(id, MAX);
    }

    @UnknownNullability
    @Contract(value = "null -> null", pure = true)
    public static ESteamAccount fromIdOrNull(
            @Nullable
            final Integer id
    ) {
        return fromIdOrElse(id, (@Nullable ESteamAccount) null);
    }

    @UnknownNullability
    @Contract(pure = true)
    public static ESteamAccount fromIdNoCheck(
            final int id
    ) {
        return SingletonMapById.INSTANCE.get(id);
    }

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

    @NotNull
    @Contract(pure = true)
    public static ESteamAccount fromCharOrBase(
            @Nullable
            final Character ch
    )  {
        return fromCharOrElse(ch, BASE);
    }

    @NotNull
    @Contract(pure = true)
    public static ESteamAccount fromCharOrMin(
            @Nullable
            final Character ch
    ) {
        return fromCharOrElse(ch, MIN);
    }

    @NotNull
    @Contract(pure = true)
    public static ESteamAccount fromCharOrMax(
            @Nullable
            final Character ch
    ) {
        return fromCharOrElse(ch, MAX);
    }

    @UnknownNullability
    @Contract(value = "null -> null", pure = true)
    public static ESteamAccount fromCharOrNull(
            @Nullable
            final Character ch
    ) {
        return fromCharOrElse(ch, (@Nullable ESteamAccount) null);
    }

    @UnknownNullability
    @Contract(pure = true)
    public static ESteamAccount fromCharNoCheck(
            final char ch
    ) {
        return SingletonMapByChar.INSTANCE.get(ch);
    }

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

    @NotNull
    @Contract(pure = true)
    public static ESteamAccount fromIndexOrBase(
            @Nullable
            final Integer index
    ) {
        return fromIndexOrElse(index, BASE);
    }

    @NotNull
    @Contract(pure = true)
    public static ESteamAccount fromIndexOrMin(
            @Nullable
            final Integer index
    ) {
        return fromIndexOrElse(index, MIN);
    }

    @NotNull
    @Contract(pure = true)
    public static ESteamAccount fromIndexOrMax(
            @Nullable
            final Integer index
    ) {
        return fromIndexOrElse(index, MAX);
    }

    @UnknownNullability
    @Contract(value = "null -> null", pure = true)
    public static ESteamAccount fromIndexOrNull(
            @Nullable
            final Integer index
    ) {
        return fromIndexOrElse(index, (@Nullable ESteamAccount) null);
    }

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
