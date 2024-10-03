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
 * A Steam vanity URL type enums.
 */
@SuppressWarnings({"unused", "DefaultAnnotationParam"})
public enum ESteamVanity {

    /**
     * A vanity URL type enum - Individual.
     *
     * <p>Wraps {@link USteamVanity#INDIVIDUAL}.
     */
    INDIVIDUAL(USteamVanity.INDIVIDUAL),

    /**
     * A vanity URL type enum - Group.
     *
     * <p>Wraps {@link USteamVanity#GROUP}.
     */
    GROUP(USteamVanity.GROUP),

    /**
     * A vanity URL type enum - Game Group.
     *
     * <p>Wraps {@link USteamVanity#GAME_GROUP}.
     */
    GAME_GROUP(USteamVanity.GAME_GROUP);

    /**
     * A minimum vanity URL type enum.
     *
     * <p>Wraps {@link #INDIVIDUAL}.
     */
    @NotNull
    public static final ESteamVanity MIN = INDIVIDUAL;

    /**
     * A maximum vanity URL type enum.
     *
     * <p>Wraps {@link #GAME_GROUP}.
     */
    @NotNull
    public static final ESteamVanity MAX = GAME_GROUP;

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
     * Initialize an {@link ESteamVanity} instance.
     *
     * @param id    identifier
     */
    @Contract(pure = true)
    ESteamVanity(
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

            final String className = ESteamVanity.class.getSimpleName();
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
     * Get an unmodifiable list of all Steam vanity URL type enums.
     *
     * @return  unmodifiable list
     */
    @NotNull
    @Unmodifiable
    @Contract(pure = true)
    public static List<@NotNull ESteamVanity> getValueList() {
        return SingletonValueList.INSTANCE;
    }

    /**
     * Get an {@link ESteamVanity} instance by its identifier
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromIdNoCheck(int)}.
     *
     * @param id            identifier of the instance, may be null
     * @param defaultValue  default value to return on failure, may be null
     *
     * @return  associated {@link ESteamVanity} instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, _ -> param2", pure = true)
    public static ESteamVanity fromIdOrElse(
            @Nullable
            final Integer id,

            @Nullable
            final ESteamVanity defaultValue
    ) {
        return UwObject.ifNotNullNoCheck(id, ESteamVanity::fromIdNoCheck);
    }

    /**
     * Get an {@link ESteamVanity} instance by its identifier
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromIdOrNull(Integer)}.
     *
     * @param id                    identifier of the instance, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  associated {@link ESteamVanity} instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, null -> null", pure = false)
    public static ESteamVanity fromIdOrElse(
            @Nullable
            final Integer id,

            @Nullable
            final Supplier<@UnknownNullability ESteamVanity> defaultValueSupplier
    ) {
        return UwObject.ifNull(fromIdOrNull(id), defaultValueSupplier);
    }

    /**
     * Get an {@link ESteamVanity} instance by its identifier
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromIdOrElse(Integer, Supplier)}.
     *
     * @param id                    identifier of the instance, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  associated {@link ESteamVanity} instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, _ -> null", pure = false)
    public static ESteamVanity fromIdOrElse(
            @Nullable
            final Integer id,

            @Nullable
            final VoidSupplier<@UnknownNullability ESteamVanity> defaultValueSupplier
    ) {
        return fromIdOrElse(id, (Supplier<@UnknownNullability ESteamVanity>) defaultValueSupplier);
    }

    /**
     * Get an {@link ESteamVanity} instance by its identifier
     * or return the {@link #MIN} instance on failure.
     *
     * <p>Wraps {@link #fromIdOrElse(Integer, ESteamVanity)}
     * w/ {@link #MIN} as the default value.
     *
     * @param id    identifier of the instance, may be null
     *
     * @return  associated {@link ESteamVanity} instance or {@link #MIN}
     */
    @NotNull
    @Contract(pure = true)
    public static ESteamVanity fromIdOrMin(
            @Nullable
            final Integer id
    ) {
        return fromIdOrElse(id, MIN);
    }

    /**
     * Get an {@link ESteamVanity} instance by its identifier
     * or return the {@link #MAX} instance on failure.
     *
     * <p>Wraps {@link #fromIdOrElse(Integer, ESteamVanity)}
     * w/ {@link #MAX} as the default value.
     *
     * @param id    identifier of the instance, may be null
     *
     * @return  associated {@link ESteamVanity} instance or {@link #MAX}
     */
    @NotNull
    @Contract(pure = true)
    public static ESteamVanity fromIdOrMax(
            @Nullable
            final Integer id
    ) {
        return fromIdOrElse(id, MAX);
    }

    /**
     * Get an {@link ESteamVanity} instance by its identifier
     * or return {@code null} on failure.
     *
     * <p>Wraps {@link #fromIdOrElse(Integer, ESteamVanity)}
     * w/ {@code null} as the default value.
     *
     * @param id    identifier of the instance, may be null
     *
     * @return  associated {@link ESteamVanity} instance or {@code null}
     */
    @UnknownNullability
    @Contract(value = "null -> null", pure = true)
    public static ESteamVanity fromIdOrNull(
            @Nullable
            final Integer id
    ) {
        return fromIdOrElse(id, (@Nullable ESteamVanity) null);
    }

    /**
     * Get an {@link ESteamVanity} instance by its identifier.
     *
     * <p>Wraps {@link Map#get(Object)}.
     *
     * @param id    identifier of the instance
     *
     * @return  associated {@link ESteamVanity} instance
     */
    @UnknownNullability
    @Contract(pure = true)
    public static ESteamVanity fromIdNoCheck(
            final int id
    ) {
        return SingletonMapById.INSTANCE.get(id);
    }

    /**
     * Get an {@link ESteamVanity} instance by its index
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromIndexNoCheck(int)}.
     *
     * @param index         index of the instance, may be null
     * @param defaultValue  default value to return on failure, may be null
     *
     * @return  associated {@link ESteamVanity} instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, _ -> param2", pure = true)
    public static ESteamVanity fromIndexOrElse(
            @Nullable
            final Integer index,

            @Nullable
            final ESteamVanity defaultValue
    ) {
        final ESteamVanity result = UwObject.ifNotNullNoCheck(index, ESteamVanity::fromIndexNoCheck, defaultValue);
        return UwObject.ifNull(result, defaultValue);
    }

    /**
     * Get an {@link ESteamVanity} instance by its index
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromIndexOrNull(Integer)}.
     *
     * @param index                 index of the instance, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  associated {@link ESteamVanity} instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, null -> null", pure = false)
    public static ESteamVanity fromIndexOrElse(
            @Nullable
            final Integer index,

            @Nullable
            final Supplier<@UnknownNullability ESteamVanity> defaultValueSupplier
    ) {
        return UwObject.ifNull(fromIndexOrNull(index), defaultValueSupplier);
    }

    /**
     * Get an {@link ESteamVanity} instance by its index
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromIndexOrElse(Integer, Supplier)}.
     *
     * @param index                 index of the instance, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  associated {@link ESteamVanity} instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, _ -> null", pure = false)
    public static ESteamVanity fromIndexOrElse(
            @Nullable
            final Integer index,

            @Nullable
            final VoidSupplier<@UnknownNullability ESteamVanity> defaultValueSupplier
    ) {
        return fromIndexOrElse(index, (Supplier<@UnknownNullability ESteamVanity>) defaultValueSupplier);
    }

    /**
     * Get an {@link ESteamVanity} instance by its index
     * or return the {@link #MIN} instance on failure.
     *
     * <p>Wraps {@link #fromIndexOrElse(Integer, ESteamVanity)}
     * w/ {@link #MIN} as the default value.
     *
     * @param index     index of the instance, may be null
     *
     * @return  associated {@link ESteamVanity} instance or {@link #MIN}
     */
    @NotNull
    @Contract(pure = true)
    public static ESteamVanity fromIndexOrMin(
            @Nullable
            final Integer index
    ) {
        return fromIndexOrElse(index, MIN);
    }

    /**
     * Get an {@link ESteamVanity} instance by its index
     * or return the {@link #MAX} instance on failure.
     *
     * <p>Wraps {@link #fromIndexOrElse(Integer, ESteamVanity)}
     * w/ {@link #MAX} as the default value.
     *
     * @param index     index of the instance, may be null
     *
     * @return  associated {@link ESteamVanity} instance or {@link #MAX}
     */
    @NotNull
    @Contract(pure = true)
    public static ESteamVanity fromIndexOrMax(
            @Nullable
            final Integer index
    ) {
        return fromIndexOrElse(index, MAX);
    }

    /**
     * Get an {@link ESteamVanity} instance by its index
     * or return {@code null} instance on failure.
     *
     * <p>Wraps {@link #fromIndexOrElse(Integer, ESteamVanity)}
     * w/ {@code null} as the default value.
     *
     * @param index     index of the instance, may be null
     *
     * @return  associated {@link ESteamVanity} instance or {@code null}
     */
    @UnknownNullability
    @Contract(value = "null -> null", pure = true)
    public static ESteamVanity fromIndexOrNull(
            @Nullable
            final Integer index
    ) {
        return fromIndexOrElse(index, (@Nullable ESteamVanity) null);
    }

    /**
     * Get an {@link ESteamVanity} instance by its index.
     *
     * <p>Wraps {@link UwArray#getNoCheck(Object[], int)}.
     *
     * @param index     index of the instance, may be null
     *
     * @return  associated {@link ESteamVanity} instance
     */
    @UnknownNullability
    @Contract(pure = true)
    public static ESteamVanity fromIndexNoCheck(
            final int index
    ) {
        return UwArray.getNoCheck(SingletonValues.INSTANCE, index);
    }

    private static final class SingletonValues {
        @NotNull
        public static final ESteamVanity @NotNull [] INSTANCE = ESteamVanity.values();

        @Contract(value = "-> fail", pure = false)
        private SingletonValues() {
            throw new UnsupportedOperationException();
        }
    }

    private static final class SingletonValueList {
        @NotNull
        @Unmodifiable
        public static final List<@NotNull ESteamVanity> INSTANCE;

        static {
            final ESteamVanity[] values = SingletonValues.INSTANCE;
            final List<ESteamVanity> list = new ArrayList<>(values.length);

            //noinspection ManualArrayToCollectionCopy
            for (final ESteamVanity value : values) {
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
        public static final Map<@NotNull Integer, @NotNull ESteamVanity> INSTANCE;

        static {
            final ESteamVanity[] values = SingletonValues.INSTANCE;
            final Map<Integer, ESteamVanity> map = new HashMap<>();

            for (final ESteamVanity value : values) {
                if (map.containsKey(value.id)) {
                    throw new IllegalStateException("Identifier field of ESteamVanity class must be unique");
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
