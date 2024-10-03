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
 * A Steam chat flag enums.
 */
@SuppressWarnings({"unused", "DefaultAnnotationParam"})
public enum ESteamChat {

    /**
     * A Steam chat flag - Clan.
     *
     * <p>Wraps {@link USteamChat#CLAN_FLAG}.
     */
    CLAN(USteamChat.CLAN_FLAG),

    /**
     * A Steam chat flag - Lobby.
     *
     * <p>Wraps {@link USteamChat#LOBBY_FLAG}.
     */
    LOBBY(USteamChat.LOBBY_FLAG),

    /**
     * A Steam chat flag - MM Lobby.
     *
     * <p>Wraps {@link USteamChat#MM_LOBBY_FLAG}.
     */
    MM_LOBBY(USteamChat.MM_LOBBY_FLAG);

    /**
     * An attribute name of this identifier.
     */
    @NotNull
    public static final String ATTRIBUTE_NAME_ID = "id";

    /**
     * An identifier.
     */
    private final int id;

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
     * Initialize an {@link ESteamChat} instance.
     *
     * @param id    identifier
     */
    @Contract(pure = true)
    ESteamChat(
            final int id
    ) {
        this.id = id;

        this.stringCache = null;

        this.stringCacheMutex = new Object();
    }

    /**
     * Get this identifier.
     *
     * @return  identifier
     */
    @Contract(pure = true)
    public int getId() {
        return this.id;
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

            final String className = ESteamChat.class.getSimpleName();
            final String instanceName = this.name();

            return (this.stringCache = className
                    + "::"
                    + instanceName
                    + "["
                    + ATTRIBUTE_NAME_ID
                    + "="
                    + this.id
                    + "]");
        }
    }

    /**
     * Get an unmodifiable list of all Steam chat flag enums.
     *
     * @return  unmodifiable list
     */
    @NotNull
    @Unmodifiable
    @Contract(pure = true)
    public static List<@NotNull ESteamChat> getValueList() {
        return SingletonValueList.INSTANCE;
    }

    /**
     * Get an {@link ESteamChat} instance by its identifier
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromIdNoCheck(int)}.
     *
     * @param id            identifier of the instance, may be null
     * @param defaultValue  default value to return on failure, may be null
     *
     * @return  associated {@link ESteamChat} instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, _ -> param2", pure = true)
    public static ESteamChat fromIdOrElse(
            @Nullable
            final Integer id,

            @Nullable
            final ESteamChat defaultValue
    ) {
        return UwObject.ifNotNullNoCheck(id, ESteamChat::fromIdNoCheck);
    }

    /**
     * Get an {@link ESteamChat} instance by its identifier
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromIdOrNull(Integer)}.
     *
     * @param id                    identifier of the instance, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  associated {@link ESteamChat} instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, null -> null", pure = false)
    public static ESteamChat fromIdOrElse(
            @Nullable
            final Integer id,

            @Nullable
            final Supplier<@UnknownNullability ESteamChat> defaultValueSupplier
    ) {
        return UwObject.ifNull(fromIdOrNull(id), defaultValueSupplier);
    }

    /**
     * Get an {@link ESteamChat} instance by its identifier
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromIdOrElse(Integer, Supplier)}.
     *
     * @param id                    identifier of the instance, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  associated {@link ESteamChat} instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, _ -> null", pure = false)
    public static ESteamChat fromIdOrElse(
            @Nullable
            final Integer id,

            @Nullable
            final VoidSupplier<@UnknownNullability ESteamChat> defaultValueSupplier
    ) {
        return fromIdOrElse(id, (Supplier<@UnknownNullability ESteamChat>) defaultValueSupplier);
    }

    /**
     * Get an {@link ESteamChat} instance by its identifier
     * or return {@code null} on failure.
     *
     * <p>Wraps {@link #fromIdOrElse(Integer, ESteamChat)}
     * w/ {@code null} as the default value.
     *
     * @param id    identifier of the instance, may be null
     *
     * @return  associated {@link ESteamChat} instance or {@code null}
     */
    @UnknownNullability
    @Contract(value = "null -> null", pure = true)
    public static ESteamChat fromIdOrNull(
            @Nullable
            final Integer id
    ) {
        return fromIdOrElse(id, (@Nullable ESteamChat) null);
    }

    /**
     * Get an {@link ESteamChat} instance by its identifier.
     *
     * <p>Wraps {@link Map#get(Object)}.
     *
     * @param id    identifier of the instance
     *
     * @return  associated {@link ESteamChat} instance
     */
    @UnknownNullability
    @Contract(pure = true)
    public static ESteamChat fromIdNoCheck(
            final int id
    ) {
        return SingletonMapById.INSTANCE.get(id);
    }

    /**
     * Get an {@link ESteamChat} instance by its index
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromIndexNoCheck(int)}.
     *
     * @param index         index of the instance, may be null
     * @param defaultValue  default value to return on failure, may be null
     *
     * @return  associated {@link ESteamChat} instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, _ -> param2", pure = true)
    public static ESteamChat fromIndexOrElse(
            @Nullable
            final Integer index,

            @Nullable
            final ESteamChat defaultValue
    ) {
        final ESteamChat result = UwObject.ifNotNullNoCheck(index, ESteamChat::fromIndexNoCheck, defaultValue);
        return UwObject.ifNull(result, defaultValue);
    }

    /**
     * Get an {@link ESteamChat} instance by its index
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromIndexOrNull(Integer)}.
     *
     * @param index                 index of the instance, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  associated {@link ESteamChat} instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, null -> null", pure = false)
    public static ESteamChat fromIndexOrElse(
            @Nullable
            final Integer index,

            @Nullable
            final Supplier<@UnknownNullability ESteamChat> defaultValueSupplier
    ) {
        return UwObject.ifNull(fromIndexOrNull(index), defaultValueSupplier);
    }

    /**
     * Get an {@link ESteamChat} instance by its index
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromIndexOrElse(Integer, Supplier)}.
     *
     * @param index                 index of the instance, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  associated {@link ESteamChat} instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, _ -> null", pure = false)
    public static ESteamChat fromIndexOrElse(
            @Nullable
            final Integer index,

            @Nullable
            final VoidSupplier<@UnknownNullability ESteamChat> defaultValueSupplier
    ) {
        return fromIndexOrElse(index, (Supplier<@UnknownNullability ESteamChat>) defaultValueSupplier);
    }

    /**
     * Get an {@link ESteamChat} instance by its index
     * or return {@code null} instance on failure.
     *
     * <p>Wraps {@link #fromIndexOrElse(Integer, ESteamChat)}
     * w/ {@code null} as the default value.
     *
     * @param index     index of the instance, may be null
     *
     * @return  associated {@link ESteamChat} instance or {@code null}
     */
    @UnknownNullability
    @Contract(value = "null -> null", pure = true)
    public static ESteamChat fromIndexOrNull(
            @Nullable
            final Integer index
    ) {
        return fromIndexOrElse(index, (@Nullable ESteamChat) null);
    }

    /**
     * Get an {@link ESteamChat} instance by its index.
     *
     * <p>Wraps {@link UwArray#getNoCheck(Object[], int)}.
     *
     * @param index     index of the instance, may be null
     *
     * @return  associated {@link ESteamChat} instance
     */
    @UnknownNullability
    @Contract(pure = true)
    public static ESteamChat fromIndexNoCheck(
            final int index
    ) {
        return UwArray.getNoCheck(SingletonValues.INSTANCE, index);
    }

    private static final class SingletonValues {
        @NotNull
        public static final ESteamChat @NotNull [] INSTANCE = ESteamChat.values();

        @Contract(value = "-> fail", pure = false)
        private SingletonValues() {
            throw new UnsupportedOperationException();
        }
    }

    private static final class SingletonValueList {
        @NotNull
        @Unmodifiable
        public static final List<@NotNull ESteamChat> INSTANCE;

        static {
            final ESteamChat[] values = SingletonValues.INSTANCE;
            final List<ESteamChat> list = new ArrayList<>(values.length);

            //noinspection ManualArrayToCollectionCopy
            for (final ESteamChat value : values) {
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
        public static final Map<@NotNull Integer, @NotNull ESteamChat> INSTANCE;

        static {
            final ESteamChat[] values = SingletonValues.INSTANCE;
            final Map<Integer, ESteamChat> map = new HashMap<>();

            for (final ESteamChat value : values) {
                if (map.containsKey(value.id)) {
                    throw new IllegalStateException("Identifier field of ESteamChat class must be unique");
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
}
