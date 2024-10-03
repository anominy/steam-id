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
 * A Steam account instance type enums.
 */
@SuppressWarnings({"unused", "DefaultAnnotationParam"})
public enum ESteamInstance {

    /**
     * An account instance type enum - All.
     *
     * <p>Wraps {@link USteamInstance#ALL}.
     */
    ALL(USteamInstance.ALL),

    /**
     * An account instance type enum - Desktop.
     *
     * <p>Wraps {@link USteamInstance#DESKTOP}.
     */
    DESKTOP(USteamInstance.DESKTOP),

    /**
     * An account instance type enum - Console.
     *
     * <p>Wraps {@link USteamInstance#CONSOLE}.
     */
    CONSOLE(USteamInstance.CONSOLE),

    /**
     * An account instance type enum - Web.
     *
     * <p>Wraps {@link USteamInstance#WEB}.
     */
    WEB(USteamInstance.WEB);

    /**
     * A minimum account instance type enum.
     *
     * <p>Wraps {@link #ALL}.
     */
    @NotNull
    public static final ESteamInstance MIN = ALL;

    /**
     * A maximum account instance type enum.
     *
     * <p>Wraps {@link #WEB}.
     */
    @NotNull
    public static final ESteamInstance MAX = WEB;

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
     * Initialize an {@link ESteamInstance} instance.
     *
     * @param id    identifier
     */
    @Contract(pure = true)
    ESteamInstance(
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

            final String className = ESteamInstance.class.getSimpleName();
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
     * Get an unmodifiable list of all Steam account instance type enums.
     *
     * @return  unmodifiable list
     */
    @NotNull
    @Unmodifiable
    @Contract(pure = true)
    public static List<@NotNull ESteamInstance> getValueList() {
        return SingletonValueList.INSTANCE;
    }

    /**
     * Get an {@link ESteamInstance} instance by its identifier
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromIdNoCheck(int)}.
     *
     * @param id            identifier of the instance, may be null
     * @param defaultValue  default value to return on failure, may be null
     *
     * @return  associated {@link ESteamInstance} instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, _ -> param2", pure = true)
    public static ESteamInstance fromIdOrElse(
            @Nullable
            final Integer id,

            @Nullable
            final ESteamInstance defaultValue
    ) {
        return UwObject.ifNotNullNoCheck(id, ESteamInstance::fromIdNoCheck);
    }

    /**
     * Get an {@link ESteamInstance} instance by its identifier
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromIdOrNull(Integer)}.
     *
     * @param id                    identifier of the instance, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  associated {@link ESteamInstance} instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, null -> null", pure = false)
    public static ESteamInstance fromIdOrElse(
            @Nullable
            final Integer id,

            @Nullable
            final Supplier<@UnknownNullability ESteamInstance> defaultValueSupplier
    ) {
        return UwObject.ifNull(fromIdOrNull(id), defaultValueSupplier);
    }

    /**
     * Get an {@link ESteamInstance} instance by its identifier
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromIdOrElse(Integer, Supplier)}.
     *
     * @param id                    identifier of the instance, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  associated {@link ESteamInstance} instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, _ -> null", pure = false)
    public static ESteamInstance fromIdOrElse(
            @Nullable
            final Integer id,

            @Nullable
            final VoidSupplier<@UnknownNullability ESteamInstance> defaultValueSupplier
    ) {
        return fromIdOrElse(id, (Supplier<@UnknownNullability ESteamInstance>) defaultValueSupplier);
    }

    /**
     * Get an {@link ESteamInstance} instance by its identifier
     * or return the {@link #MIN} instance on failure.
     *
     * <p>Wraps {@link #fromIdOrElse(Integer, ESteamInstance)}
     * w/ {@link #MIN} as the default value.
     *
     * @param id    identifier of the instance, may be null
     *
     * @return  associated {@link ESteamInstance} instance or {@link #MIN}
     */
    @NotNull
    @Contract(pure = true)
    public static ESteamInstance fromIdOrMin(
            @Nullable
            final Integer id
    ) {
        return fromIdOrElse(id, MIN);
    }

    /**
     * Get an {@link ESteamInstance} instance by its identifier
     * or return the {@link #MAX} instance on failure.
     *
     * <p>Wraps {@link #fromIdOrElse(Integer, ESteamInstance)}
     * w/ {@link #MAX} as the default value.
     *
     * @param id    identifier of the instance, may be null
     *
     * @return  associated {@link ESteamInstance} instance or {@link #MAX}
     */
    @NotNull
    @Contract(pure = true)
    public static ESteamInstance fromIdOrMax(
            @Nullable
            final Integer id
    ) {
        return fromIdOrElse(id, MAX);
    }

    /**
     * Get an {@link ESteamInstance} instance by its identifier
     * or return {@code null} on failure.
     *
     * <p>Wraps {@link #fromIdOrElse(Integer, ESteamInstance)}
     * w/ {@code null} as the default value.
     *
     * @param id    identifier of the instance, may be null
     *
     * @return  associated {@link ESteamInstance} instance or {@code null}
     */
    @UnknownNullability
    @Contract(value = "null -> null", pure = true)
    public static ESteamInstance fromIdOrNull(
            @Nullable
            final Integer id
    ) {
        return fromIdOrElse(id, (@Nullable ESteamInstance) null);
    }

    /**
     * Get an {@link ESteamInstance} instance by its identifier.
     *
     * <p>Wraps {@link Map#get(Object)}.
     *
     * @param id    identifier of the instance
     *
     * @return  associated {@link ESteamInstance} instance
     */
    @UnknownNullability
    @Contract(pure = true)
    public static ESteamInstance fromIdNoCheck(
            final int id
    ) {
        return SingletonMapById.INSTANCE.get(id);
    }

    /**
     * Get an {@link ESteamInstance} instance by its index
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromIndexNoCheck(int)}.
     *
     * @param index         index of the instance, may be null
     * @param defaultValue  default value to return on failure, may be null
     *
     * @return  associated {@link ESteamInstance} instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, _ -> param2", pure = true)
    public static ESteamInstance fromIndexOrElse(
            @Nullable
            final Integer index,

            @Nullable
            final ESteamInstance defaultValue
    ) {
        final ESteamInstance result = UwObject.ifNotNullNoCheck(index, ESteamInstance::fromIndexNoCheck, defaultValue);
        return UwObject.ifNull(result, defaultValue);
    }

    /**
     * Get an {@link ESteamInstance} instance by its index
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromIndexOrNull(Integer)}.
     *
     * @param index                 index of the instance, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  associated {@link ESteamInstance} instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, null -> null", pure = false)
    public static ESteamInstance fromIndexOrElse(
            @Nullable
            final Integer index,

            @Nullable
            final Supplier<@UnknownNullability ESteamInstance> defaultValueSupplier
    ) {
        return UwObject.ifNull(fromIndexOrNull(index), defaultValueSupplier);
    }

    /**
     * Get an {@link ESteamInstance} instance by its index
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromIndexOrElse(Integer, Supplier)}.
     *
     * @param index                 index of the instance, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  associated {@link ESteamInstance} instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, _ -> null", pure = false)
    public static ESteamInstance fromIndexOrElse(
            @Nullable
            final Integer index,

            @Nullable
            final VoidSupplier<@UnknownNullability ESteamInstance> defaultValueSupplier
    ) {
        return fromIndexOrElse(index, (Supplier<@UnknownNullability ESteamInstance>) defaultValueSupplier);
    }

    /**
     * Get an {@link ESteamInstance} instance by its index
     * or return the {@link #MIN} instance on failure.
     *
     * <p>Wraps {@link #fromIndexOrElse(Integer, ESteamInstance)}
     * w/ {@link #MIN} as the default value.
     *
     * @param index     index of the instance, may be null
     *
     * @return  associated {@link ESteamInstance} instance or {@link #MIN}
     */
    @NotNull
    @Contract(pure = true)
    public static ESteamInstance fromIndexOrMin(
            @Nullable
            final Integer index
    ) {
        return fromIndexOrElse(index, MIN);
    }

    /**
     * Get an {@link ESteamInstance} instance by its index
     * or return the {@link #MAX} instance on failure.
     *
     * <p>Wraps {@link #fromIndexOrElse(Integer, ESteamInstance)}
     * w/ {@link #MAX} as the default value.
     *
     * @param index     index of the instance, may be null
     *
     * @return  associated {@link ESteamInstance} instance or {@link #MAX}
     */
    @NotNull
    @Contract(pure = true)
    public static ESteamInstance fromIndexOrMax(
            @Nullable
            final Integer index
    ) {
        return fromIndexOrElse(index, MAX);
    }

    /**
     * Get an {@link ESteamInstance} instance by its index
     * or return {@code null} instance on failure.
     *
     * <p>Wraps {@link #fromIndexOrElse(Integer, ESteamInstance)}
     * w/ {@code null} as the default value.
     *
     * @param index     index of the instance, may be null
     *
     * @return  associated {@link ESteamInstance} instance or {@code null}
     */
    @UnknownNullability
    @Contract(value = "null -> null", pure = true)
    public static ESteamInstance fromIndexOrNull(
            @Nullable
            final Integer index
    ) {
        return fromIndexOrElse(index, (@Nullable ESteamInstance) null);
    }

    /**
     * Get an {@link ESteamInstance} instance by its index.
     *
     * <p>Wraps {@link UwArray#getNoCheck(Object[], int)}.
     *
     * @param index     index of the instance, may be null
     *
     * @return  associated {@link ESteamInstance} instance
     */
    @UnknownNullability
    @Contract(pure = true)
    public static ESteamInstance fromIndexNoCheck(
            final int index
    ) {
        return UwArray.getNoCheck(SingletonValues.INSTANCE, index);
    }

    private static final class SingletonValues {
        @NotNull
        public static final ESteamInstance @NotNull [] INSTANCE = ESteamInstance.values();

        @Contract(value = "-> fail", pure = false)
        private SingletonValues() {
            throw new UnsupportedOperationException();
        }
    }

    private static final class SingletonValueList {
        @NotNull
        @Unmodifiable
        public static final List<@NotNull ESteamInstance> INSTANCE;

        static {
            final ESteamInstance[] values = SingletonValues.INSTANCE;
            final List<ESteamInstance> list = new ArrayList<>(values.length);

            //noinspection ManualArrayToCollectionCopy
            for (final ESteamInstance value : values) {
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
        public static final Map<@NotNull Integer, @NotNull ESteamInstance> INSTANCE;

        static {
            final ESteamInstance[] values = SingletonValues.INSTANCE;
            final Map<Integer, ESteamInstance> map = new HashMap<>();

            for (final ESteamInstance value : values) {
                if (map.containsKey(value.id)) {
                    throw new IllegalStateException("Identifier field of ESteamInstance class must be unique");
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
