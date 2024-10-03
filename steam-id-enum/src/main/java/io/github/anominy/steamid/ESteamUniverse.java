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
 * A Steam account universe type enums.
 */
@SuppressWarnings({"unused", "DefaultAnnotationParam"})
public enum ESteamUniverse {

    /**
     * An account universe type enum - Invalid.
     *
     * <p>Wraps {@link USteamUniverse#INVALID}.
     */
    INVALID(USteamUniverse.INVALID),

    /**
     * An account universe type enum - Public.
     *
     * <p>Wraps {@link USteamUniverse#PUBLIC}.
     */
    PUBLIC(USteamUniverse.PUBLIC),

    /**
     * An account universe type enum - Beta.
     *
     * <p>wraps {@link USteamUniverse#BETA}.
     */
    BETA(USteamUniverse.BETA),

    /**
     * An account universe type enum - Internal.
     *
     * <p>Wraps {@link USteamUniverse#INTERNAL}.
     */
    INTERNAL(USteamUniverse.INTERNAL),

    /**
     * An account universe type enum - Dev.
     *
     * <p>Wraps {@link USteamUniverse#DEV}.
     */
    DEV(USteamUniverse.DEV),

    /**
     * An account universe type enum - RC.
     *
     * <p>Wraps {@link USteamUniverse#RC}.
     */
    RC(USteamUniverse.RC);

    /**
     * A base account universe type enum.
     *
     * <p>Wraps {@link #INVALID}.
     */
    @NotNull
    public static final ESteamUniverse BASE = INVALID;

    /**
     * A minimum account universe type enum.
     *
     * <p>Wraps {@link #PUBLIC}.
     */
    @NotNull
    public static final ESteamUniverse MIN = PUBLIC;

    /**
     * A maximum account universe type enum.
     *
     * <p>Wraps {@link #RC}.
     */
    @NotNull
    public static final ESteamUniverse MAX = RC;

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
     * Initialize an {@link ESteamUniverse} instance.
     *
     * @param id    identifier
     */
    @Contract(pure = true)
    ESteamUniverse(
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

            final String className = ESteamUniverse.class.getSimpleName();
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
     * Get an unmodifiable list of all Steam account universe type enums.
     *
     * @return  unmodifiable list
     */
    @NotNull
    @Unmodifiable
    @Contract(pure = true)
    public static List<@NotNull ESteamUniverse> getValueList() {
        return SingletonValueList.INSTANCE;
    }

    /**
     * Get an {@link ESteamUniverse} instance by its identifier
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromIdNoCheck(int)}.
     *
     * @param id            identifier of the instance, may be null
     * @param defaultValue  default value to return on failure, may be null
     *
     * @return  associated {@link ESteamUniverse} instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, _ -> param2", pure = true)
    public static ESteamUniverse fromIdOrElse(
            @Nullable
            final Integer id,

            @Nullable
            final ESteamUniverse defaultValue
    ) {
        return UwObject.ifNotNullNoCheck(id, ESteamUniverse::fromIdNoCheck);
    }

    /**
     * Get an {@link ESteamUniverse} instance by its identifier
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromIdOrNull(Integer)}.
     *
     * @param id                    identifier of the instance, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  associated {@link ESteamUniverse} instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, null -> null", pure = false)
    public static ESteamUniverse fromIdOrElse(
            @Nullable
            final Integer id,

            @Nullable
            final Supplier<@UnknownNullability ESteamUniverse> defaultValueSupplier
    ) {
        return UwObject.ifNull(fromIdOrNull(id), defaultValueSupplier);
    }

    /**
     * Get an {@link ESteamUniverse} instance by its identifier
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromIdOrElse(Integer, Supplier)}.
     *
     * @param id                    identifier of the instance, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  associated {@link ESteamUniverse} instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, _ -> null", pure = false)
    public static ESteamUniverse fromIdOrElse(
            @Nullable
            final Integer id,

            @Nullable
            final VoidSupplier<@UnknownNullability ESteamUniverse> defaultValueSupplier
    ) {
        return fromIdOrElse(id, (Supplier<@UnknownNullability ESteamUniverse>) defaultValueSupplier);
    }

    /**
     * Get an {@link ESteamUniverse} instance by its identifier
     * or return the {@link #BASE} instance on failure.
     *
     * <p>Wraps {@link #fromIdOrElse(Integer, ESteamUniverse)}
     * w/ {@link #BASE} as the default value.
     *
     * @param id    identifier of the instance, may be null
     *
     * @return  associated {@link ESteamUniverse} instance or {@link #BASE}
     */
    @NotNull
    @Contract(pure = true)
    public static ESteamUniverse fromIdOrBase(
            @Nullable
            final Integer id
    ) {
        return fromIdOrElse(id, BASE);
    }

    /**
     * Get an {@link ESteamUniverse} instance by its identifier
     * or return the {@link #MIN} instance on failure.
     *
     * <p>Wraps {@link #fromIdOrElse(Integer, ESteamUniverse)}
     * w/ {@link #MIN} as the default value.
     *
     * @param id    identifier of the instance, may be null
     *
     * @return  associated {@link ESteamUniverse} instance or {@link #MIN}
     */
    @NotNull
    @Contract(pure = true)
    public static ESteamUniverse fromIdOrMin(
            @Nullable
            final Integer id
    ) {
        return fromIdOrElse(id, MIN);
    }

    /**
     * Get an {@link ESteamUniverse} instance by its identifier
     * or return the {@link #MAX} instance on failure.
     *
     * <p>Wraps {@link #fromIdOrElse(Integer, ESteamUniverse)}
     * w/ {@link #MAX} as the default value.
     *
     * @param id    identifier of the instance, may be null
     *
     * @return  associated {@link ESteamUniverse} instance or {@link #MAX}
     */
    @NotNull
    @Contract(pure = true)
    public static ESteamUniverse fromIdOrMax(
            @Nullable
            final Integer id
    ) {
        return fromIdOrElse(id, MAX);
    }

    /**
     * Get an {@link ESteamUniverse} instance by its identifier
     * or return {@code null} on failure.
     *
     * <p>Wraps {@link #fromIdOrElse(Integer, ESteamUniverse)}
     * w/ {@code null} as the default value.
     *
     * @param id    identifier of the instance, may be null
     *
     * @return  associated {@link ESteamUniverse} instance or {@code null}
     */
    @UnknownNullability
    @Contract(value = "null -> null", pure = true)
    public static ESteamUniverse fromIdOrNull(
            @Nullable
            final Integer id
    ) {
        return fromIdOrElse(id, (@Nullable ESteamUniverse) null);
    }

    /**
     * Get an {@link ESteamUniverse} instance by its identifier.
     *
     * <p>Wraps {@link Map#get(Object)}.
     *
     * @param id    identifier of the instance
     *
     * @return  associated {@link ESteamUniverse} instance
     */
    @UnknownNullability
    @Contract(pure = true)
    public static ESteamUniverse fromIdNoCheck(
            final int id
    ) {
        return SingletonMapById.INSTANCE.get(id);
    }

    /**
     * Get an {@link ESteamUniverse} instance by its index
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromIndexNoCheck(int)}.
     *
     * @param index         index of the instance, may be null
     * @param defaultValue  default value to return on failure, may be null
     *
     * @return  associated {@link ESteamUniverse} instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, _ -> param2", pure = true)
    public static ESteamUniverse fromIndexOrElse(
            @Nullable
            final Integer index,

            @Nullable
            final ESteamUniverse defaultValue
    ) {
        final ESteamUniverse result = UwObject.ifNotNullNoCheck(index, ESteamUniverse::fromIndexNoCheck, defaultValue);
        return UwObject.ifNull(result, defaultValue);
    }

    /**
     * Get an {@link ESteamUniverse} instance by its index
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromIndexOrNull(Integer)}.
     *
     * @param index                 index of the instance, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  associated {@link ESteamUniverse} instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, null -> null", pure = false)
    public static ESteamUniverse fromIndexOrElse(
            @Nullable
            final Integer index,

            @Nullable
            final Supplier<@UnknownNullability ESteamUniverse> defaultValueSupplier
    ) {
        return UwObject.ifNull(fromIndexOrNull(index), defaultValueSupplier);
    }

    /**
     * Get an {@link ESteamUniverse} instance by its index
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromIndexOrElse(Integer, Supplier)}.
     *
     * @param index                 index of the instance, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  associated {@link ESteamUniverse} instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, _ -> null", pure = false)
    public static ESteamUniverse fromIndexOrElse(
            @Nullable
            final Integer index,

            @Nullable
            final VoidSupplier<@UnknownNullability ESteamUniverse> defaultValueSupplier
    ) {
        return fromIndexOrElse(index, (Supplier<@UnknownNullability ESteamUniverse>) defaultValueSupplier);
    }

    /**
     * Get an {@link ESteamUniverse} instance by its index
     * or return the {@link #BASE} instance on failure.
     *
     * <p>Wraps {@link #fromIndexOrElse(Integer, ESteamUniverse)}
     * w/ {@link #BASE} as the default value.
     *
     * @param index     index of the instance, may be null
     *
     * @return  associated {@link ESteamUniverse} instance or {@link #BASE}
     */
    @NotNull
    @Contract(pure = true)
    public static ESteamUniverse fromIndexOrBase(
            @Nullable
            final Integer index
    ) {
        return fromIndexOrElse(index, BASE);
    }

    /**
     * Get an {@link ESteamUniverse} instance by its index
     * or return the {@link #MIN} instance on failure.
     *
     * <p>Wraps {@link #fromIndexOrElse(Integer, ESteamUniverse)}
     * w/ {@link #MIN} as the default value.
     *
     * @param index     index of the instance, may be null
     *
     * @return  associated {@link ESteamUniverse} instance or {@link #MIN}
     */
    @NotNull
    @Contract(pure = true)
    public static ESteamUniverse fromIndexOrMin(
            @Nullable
            final Integer index
    ) {
        return fromIndexOrElse(index, MIN);
    }

    /**
     * Get an {@link ESteamUniverse} instance by its index
     * or return the {@link #MAX} instance on failure.
     *
     * <p>Wraps {@link #fromIndexOrElse(Integer, ESteamUniverse)}
     * w/ {@link #MAX} as the default value.
     *
     * @param index     index of the instance, may be null
     *
     * @return  associated {@link ESteamUniverse} instance or {@link #MAX}
     */
    @NotNull
    @Contract(pure = true)
    public static ESteamUniverse fromIndexOrMax(
            @Nullable
            final Integer index
    ) {
        return fromIndexOrElse(index, MAX);
    }

    /**
     * Get an {@link ESteamUniverse} instance by its index
     * or return {@code null} instance on failure.
     *
     * <p>Wraps {@link #fromIndexOrElse(Integer, ESteamUniverse)}
     * w/ {@code null} as the default value.
     *
     * @param index     index of the instance, may be null
     *
     * @return  associated {@link ESteamUniverse} instance or {@code null}
     */
    @UnknownNullability
    @Contract(value = "null -> null", pure = true)
    public static ESteamUniverse fromIndexOrNull(
            @Nullable
            final Integer index
    ) {
        return fromIndexOrElse(index, (@Nullable ESteamUniverse) null);
    }

    /**
     * Get an {@link ESteamUniverse} instance by its index.
     *
     * <p>Wraps {@link UwArray#getNoCheck(Object[], int)}.
     *
     * @param index     index of the instance, may be null
     *
     * @return  associated {@link ESteamUniverse} instance
     */
    @UnknownNullability
    @Contract(pure = true)
    public static ESteamUniverse fromIndexNoCheck(
            final int index
    ) {
        return UwArray.getNoCheck(SingletonValues.INSTANCE, index);
    }

    private static final class SingletonValues {
        @NotNull
        public static final ESteamUniverse @NotNull [] INSTANCE = ESteamUniverse.values();

        @Contract(value = "-> fail", pure = false)
        private SingletonValues() {
            throw new UnsupportedOperationException();
        }
    }

    private static final class SingletonValueList {
        @NotNull
        @Unmodifiable
        public static final List<@NotNull ESteamUniverse> INSTANCE;

        static {
            final ESteamUniverse[] values = SingletonValues.INSTANCE;
            final List<ESteamUniverse> list = new ArrayList<>(values.length);

            //noinspection ManualArrayToCollectionCopy
            for (final ESteamUniverse value : values) {
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
        public static final Map<@NotNull Integer, @NotNull ESteamUniverse> INSTANCE;

        static {
            final ESteamUniverse[] values = SingletonValues.INSTANCE;
            final Map<Integer, ESteamUniverse> map = new HashMap<>();

            for (final ESteamUniverse value : values) {
                if (map.containsKey(value.id)) {
                    throw new IllegalStateException("Identifier field of ESteamUniverse class must be unique");
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
