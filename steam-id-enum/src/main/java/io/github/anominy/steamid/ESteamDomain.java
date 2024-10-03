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
 * A Steam domain type enums.
 */
@SuppressWarnings({"unused", "DefaultAnnotationParam"})
public enum ESteamDomain {

    /**
     * A worldwide Steam community domain enum.
     *
     * <p>Wraps {@link USteamDomain#COMMUNITY}.
     */
    COMMUNITY(USteamDomain.COMMUNITY),

    /**
     * A Steam invite domain enum.
     *
     * <p>Wraps {@link USteamDomain#INVITE}.
     */
    INVITE(USteamDomain.INVITE),

    /**
     * A Steam China community domain enum.
     *
     * <p>Wraps {@link USteamDomain#CHINA}.
     */
    CHINA(USteamDomain.CHINA);

    /**
     * An attribute name of this value.
     */
    @NotNull
    public static final String ATTRIBUTE_NAME_VALUE = "value";

    /**
     * A domain string.
     */
    @NotNull
    private final String value;

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
     * Initialize an {@link ESteamDomain} instance.
     *
     * @param value     domain string
     */
    @Contract(pure = true)
    ESteamDomain(
            @NotNull
            final String value
    ) {
        this.value = value;

        this.stringCache = null;

        this.stringCacheMutex = new Object();
    }

    /**
     * Get this domain string.
     *
     * @return  domain string
     */
    @Contract(pure = true)
    public String getAsString() {
        return this.value;
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

            final String className = ESteamDomain.class.getSimpleName();
            final String instanceName = this.name();

            return (this.stringCache = className
                    + "::"
                    + instanceName
                    + "["
                    + ATTRIBUTE_NAME_VALUE
                    + "="
                    + this.value
                    + "]");
        }
    }

    /**
     * Get an unmodifiable list of all Steam domain enums.
     *
     * @return  unmodifiable list
     */
    @NotNull
    @Unmodifiable
    @Contract(pure = true)
    public static List<@NotNull ESteamDomain> getValueList() {
        return SingletonValueList.INSTANCE;
    }

    /**
     * Get an {@link ESteamDomain} instance by its domain string
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromStringNoCheck(String)}.
     *
     * @param value         domain string of the instance, may be null
     * @param defaultValue  default value to return on failure, may be null
     *
     * @return  associated {@link ESteamDomain} instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, _ -> param2", pure = true)
    public static ESteamDomain fromStringOrElse(
            @Nullable
            final String value,

            @Nullable
            final ESteamDomain defaultValue
    ) {
        return UwObject.ifNotNullNoCheck(value, ESteamDomain::fromStringNoCheck);
    }

    /**
     * Get an {@link ESteamDomain} instance by its domain string
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromStringOrNull(String)}.
     *
     * @param value                 domain string of the instance, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  associated {@link ESteamDomain} instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, null -> null", pure = false)
    public static ESteamDomain fromStringOrElse(
            @Nullable
            final String value,

            @Nullable
            final Supplier<@UnknownNullability ESteamDomain> defaultValueSupplier
    ) {
        return UwObject.ifNull(fromStringOrNull(value), defaultValueSupplier);
    }

    /**
     * Get an {@link ESteamDomain} instance by its domain string
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromStringOrElse(String, Supplier)}.
     *
     * @param value                 domain string of the instance, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  associated {@link ESteamDomain} instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, _ -> null", pure = false)
    public static ESteamDomain fromStringOrElse(
            @Nullable
            final String value,

            @Nullable
            final VoidSupplier<@UnknownNullability ESteamDomain> defaultValueSupplier
    ) {
        return fromStringOrElse(value, (Supplier<@UnknownNullability ESteamDomain>) defaultValueSupplier);
    }

    /**
     * Get an {@link ESteamDomain} instance by its domain string
     * or return {@code null} on failure.
     *
     * <p>Wraps {@link #fromStringOrElse(String, ESteamDomain)}
     * w/ {@code null} as the default value.
     *
     * @param value     domain string of the instance, may be null
     *
     * @return  associated {@link ESteamDomain} instance or {@code null}
     */
    @UnknownNullability
    @Contract(value = "null -> null", pure = true)
    public static ESteamDomain fromStringOrNull(
            @Nullable
            final String value
    ) {
        return fromStringOrElse(value, (@Nullable ESteamDomain) null);
    }

    /**
     * Get an {@link ESteamDomain} instance by its domain string.
     *
     * <p>Wraps {@link Map#get(Object)}.
     *
     * @param value     domain string of the instance, mustn't be null
     *
     * @return  associated {@link ESteamDomain} instance
     */
    @UnknownNullability
    @Contract(pure = true)
    public static ESteamDomain fromStringNoCheck(
            @NotNull
            final String value
    ) {
        return SingletonMapByString.INSTANCE.get(value);
    }

    /**
     * Get an {@link ESteamDomain} instance by its index
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromIndexNoCheck(int)}.
     *
     * @param index         index of the instance, may be null
     * @param defaultValue  default value to return on failure, may be null
     *
     * @return  associated {@link ESteamDomain} instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, _ -> param2", pure = true)
    public static ESteamDomain fromIndexOrElse(
            @Nullable
            final Integer index,

            @Nullable
            final ESteamDomain defaultValue
    ) {
        final ESteamDomain result = UwObject.ifNotNullNoCheck(index, ESteamDomain::fromIndexNoCheck, defaultValue);
        return UwObject.ifNull(result, defaultValue);
    }

    /**
     * Get an {@link ESteamDomain} instance by its index
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromIndexOrNull(Integer)}.
     *
     * @param index                 index of the instance, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  associated {@link ESteamDomain} instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, null -> null", pure = false)
    public static ESteamDomain fromIndexOrElse(
            @Nullable
            final Integer index,

            @Nullable
            final Supplier<@UnknownNullability ESteamDomain> defaultValueSupplier
    ) {
        return UwObject.ifNull(fromIndexOrNull(index), defaultValueSupplier);
    }

    /**
     * Get an {@link ESteamDomain} instance by its index
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromIndexOrElse(Integer, Supplier)}.
     *
     * @param index                 index of the instance, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  associated {@link ESteamDomain} instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, _ -> null", pure = false)
    public static ESteamDomain fromIndexOrElse(
            @Nullable
            final Integer index,

            @Nullable
            final VoidSupplier<@UnknownNullability ESteamDomain> defaultValueSupplier
    ) {
        return fromIndexOrElse(index, (Supplier<@UnknownNullability ESteamDomain>) defaultValueSupplier);
    }

    /**
     * Get an {@link ESteamDomain} instance by its index
     * or return {@code null} instance on failure.
     *
     * <p>Wraps {@link #fromIndexOrElse(Integer, ESteamDomain)}
     * w/ {@code null} as the default value.
     *
     * @param index     index of the instance, may be null
     *
     * @return  associated {@link ESteamDomain} instance or {@code null}
     */
    @UnknownNullability
    @Contract(value = "null -> null", pure = true)
    public static ESteamDomain fromIndexOrNull(
            @Nullable
            final Integer index
    ) {
        return fromIndexOrElse(index, (@Nullable ESteamDomain) null);
    }

    /**
     * Get an {@link ESteamDomain} instance by its index.
     *
     * <p>Wraps {@link UwArray#getNoCheck(Object[], int)}.
     *
     * @param index     index of the instance, may be null
     *
     * @return  associated {@link ESteamDomain} instance
     */
    @UnknownNullability
    @Contract(pure = true)
    public static ESteamDomain fromIndexNoCheck(
            final int index
    ) {
        return UwArray.getNoCheck(SingletonValues.INSTANCE, index);
    }

    private static final class SingletonValues {
        @NotNull
        public static final ESteamDomain @NotNull [] INSTANCE = ESteamDomain.values();

        @Contract(value = "-> fail", pure = false)
        private SingletonValues() {
            throw new UnsupportedOperationException();
        }
    }

    private static final class SingletonValueList {
        @NotNull
        @Unmodifiable
        public static final List<@NotNull ESteamDomain> INSTANCE;

        static {
            final ESteamDomain[] values = SingletonValues.INSTANCE;
            final List<ESteamDomain> list = new ArrayList<>(values.length);

            //noinspection ManualArrayToCollectionCopy
            for (final ESteamDomain value : values) {
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

    private static final class SingletonMapByString {
        @NotNull
        @Unmodifiable
        public static final Map<@NotNull String, @NotNull ESteamDomain> INSTANCE;

        static {
            final ESteamDomain[] values = SingletonValues.INSTANCE;
            final Map<String, ESteamDomain> map = new HashMap<>();

            for (final ESteamDomain value : values) {
                if (map.containsKey(value.value)) {
                    throw new IllegalStateException("String value field of ESteamDomain class must be unique");
                }

                map.put(value.value, value);
            }

            INSTANCE = Collections.unmodifiableMap(map);
        }

        @Contract(value = "-> fail", pure = false)
        private SingletonMapByString() {
            throw new UnsupportedOperationException();
        }
    }
}
