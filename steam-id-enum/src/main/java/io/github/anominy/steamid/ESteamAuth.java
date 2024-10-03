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
 * A Steam account authentication type enums.
 */
@SuppressWarnings({"unused", "DefaultAnnotationParam"})
public enum ESteamAuth {

    /**
     * An account authentication type enum - No.
     *
     * <p>Wraps {@link USteamAuth#NO}.
     */
    NO(USteamAuth.NO),

    /**
     * An account authentication type enum - Yes.
     *
     * <p>Wraps {@link USteamAuth#YES}.
     */
    YES(USteamAuth.YES);

    /**
     * A minimum account authentication type enum.
     *
     * <p>Wraps {@link #NO}.
     */
    @NotNull
    public static final ESteamAuth MIN = NO;

    /**
     * A maximum account authentication type enum.
     *
     * <p>Wraps {@link #YES}.
     */
    @NotNull
    public static final ESteamAuth MAX = YES;

    /**
     * An attribute name of this identifier.
     */
    @NotNull
    public static final String ATTRIBUTE_NAME_ID = "id";

    /**
     * An account authentication type identifier.
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
     * Initialize an {@link ESteamAuth} instance.
     *
     * @param id    account authentication type identifier
     */
    @Contract(pure = true)
    ESteamAuth(
            final int id
    ) {
        this.id = id;

        this.stringCache = null;

        this.stringCacheMutex = new Object();
    }

    /**
     * Get this account authentication type identifier
     *
     * @return  account authentication type identifier
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

            final String className = ESteamAuth.class.getSimpleName();
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
     * Get an unmodifiable list of all Steam account authentication type enums.
     *
     * @return  unmodifiable list
     */
    @NotNull
    @Unmodifiable
    @Contract(pure = true)
    public static List<@NotNull ESteamAuth> getValueList() {
        return SingletonValueList.INSTANCE;
    }

    /**
     * Get an {@link ESteamAuth} instance by its account authentication type identifier
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromIdNoCheck(int)}.
     *
     * @param id            account authentication type identifier of the instance, may be null
     * @param defaultValue  default value to return on failure, may be null
     *
     * @return  associated {@link ESteamAuth} instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, _ -> param2", pure = true)
    public static ESteamAuth fromIdOrElse(
            @Nullable
            final Integer id,

            @Nullable
            final ESteamAuth defaultValue
    ) {
        return UwObject.ifNotNullNoCheck(id, ESteamAuth::fromIdNoCheck);
    }

    /**
     * Get an {@link ESteamAuth} instance by its account authentication type identifier
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromIdOrNull(Integer)}.
     *
     * @param id                    account authentication type identifier of the instance, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  associated {@link ESteamAuth} instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, null -> null", pure = false)
    public static ESteamAuth fromIdOrElse(
            @Nullable
            final Integer id,

            @Nullable
            final Supplier<@UnknownNullability ESteamAuth> defaultValueSupplier
    ) {
        return UwObject.ifNull(fromIdOrNull(id), defaultValueSupplier);
    }

    /**
     * Get an {@link ESteamAuth} instance by its account authentication type identifier
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromIdOrElse(Integer, Supplier)}.
     *
     * @param id                    account authentication type identifier of the instance, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  associated {@link ESteamAuth} instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, _ -> null", pure = false)
    public static ESteamAuth fromIdOrElse(
            @Nullable
            final Integer id,

            @Nullable
            final VoidSupplier<@UnknownNullability ESteamAuth> defaultValueSupplier
    ) {
        return fromIdOrElse(id, (Supplier<@UnknownNullability ESteamAuth>) defaultValueSupplier);
    }

    /**
     * Get an {@link ESteamAuth} instance by its account authentication type identifier
     * or return the {@link #MIN} instance on failure.
     *
     * <p>Wraps {@link #fromIdOrElse(Integer, ESteamAuth)}
     * w/ {@link #MIN} as the default value.
     *
     * @param id    account authentication type identifier of the instance, may be null
     *
     * @return  associated {@link ESteamAuth} instance or {@link #MIN}
     */
    @NotNull
    @Contract(pure = true)
    public static ESteamAuth fromIdOrMin(
            @Nullable
            final Integer id
    ) {
        return fromIdOrElse(id, MIN);
    }

    /**
     * Get an {@link ESteamAuth} instance by its account authentication type identifier
     * or return the {@link #MAX} instance on failure.
     *
     * <p>Wraps {@link #fromIdOrElse(Integer, ESteamAuth)}
     * w/ {@link #MAX} as the default value.
     *
     * @param id    account authentication type identifier of the instance, may be null
     *
     * @return  associated {@link ESteamAuth} instance or {@link #MAX}
     */
    @NotNull
    @Contract(pure = true)
    public static ESteamAuth fromIdOrMax(
            @Nullable
            final Integer id
    ) {
        return fromIdOrElse(id, MAX);
    }

    /**
     * Get an {@link ESteamAuth} instance by its account authentication type identifier
     * or return {@code null} on failure.
     *
     * <p>Wraps {@link #fromIdOrElse(Integer, ESteamAuth)}
     * w/ {@code null} as the default value.
     *
     * @param id    account authentication type identifier of the instance, may be null
     *
     * @return  associated {@link ESteamAuth} instance or {@code null}
     */
    @UnknownNullability
    @Contract(value = "null -> null", pure = true)
    public static ESteamAuth fromIdOrNull(
            @Nullable
            final Integer id
    ) {
        return fromIdOrElse(id, (@Nullable ESteamAuth) null);
    }

    /**
     * Get an {@link ESteamAuth} instance by its account authentication type identifier.
     *
     * <p>Wraps {@link Map#get(Object)}.
     *
     * @param id    account authentication type identifier of the instance
     *
     * @return  associated {@link ESteamAuth} instance
     */
    @UnknownNullability
    @Contract(pure = true)
    public static ESteamAuth fromIdNoCheck(
            final int id
    ) {
        return SingletonMapById.INSTANCE.get(id);
    }

    /**
     * Get an {@link ESteamAuth} instance by its index
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromIndexNoCheck(int)}.
     *
     * @param index         index of the instance, may be null
     * @param defaultValue  default value to return on failure, may be null
     *
     * @return  associated {@link ESteamAuth} instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, _ -> param2", pure = true)
    public static ESteamAuth fromIndexOrElse(
            @Nullable
            final Integer index,

            @Nullable
            final ESteamAuth defaultValue
    ) {
        final ESteamAuth result = UwObject.ifNotNullNoCheck(index, ESteamAuth::fromIndexNoCheck, defaultValue);
        return UwObject.ifNull(result, defaultValue);
    }

    /**
     * Get an {@link ESteamAuth} instance by its index
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromIndexOrNull(Integer)}.
     *
     * @param index                 index of the instance, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  associated {@link ESteamAuth} instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, null -> null", pure = false)
    public static ESteamAuth fromIndexOrElse(
            @Nullable
            final Integer index,

            @Nullable
            final Supplier<@UnknownNullability ESteamAuth> defaultValueSupplier
    ) {
        return UwObject.ifNull(fromIndexOrNull(index), defaultValueSupplier);
    }

    /**
     * Get an {@link ESteamAuth} instance by its index
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromIndexOrElse(Integer, Supplier)}.
     *
     * @param index                 index of the instance, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  associated {@link ESteamAuth} instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, _ -> null", pure = false)
    public static ESteamAuth fromIndexOrElse(
            @Nullable
            final Integer index,

            @Nullable
            final VoidSupplier<@UnknownNullability ESteamAuth> defaultValueSupplier
    ) {
        return fromIndexOrElse(index, (Supplier<@UnknownNullability ESteamAuth>) defaultValueSupplier);
    }

    /**
     * Get an {@link ESteamAuth} instance by its index
     * or return the {@link #MIN} instance on failure.
     *
     * <p>Wraps {@link #fromIndexOrElse(Integer, ESteamAuth)}
     * w/ {@link #MIN} as the default value.
     *
     * @param index     index of the instance, may be null
     *
     * @return  associated {@link ESteamAuth} instance or {@link #MIN}
     */
    @NotNull
    @Contract(pure = true)
    public static ESteamAuth fromIndexOrMin(
            @Nullable
            final Integer index
    ) {
        return fromIndexOrElse(index, MIN);
    }

    /**
     * Get an {@link ESteamAuth} instance by its index
     * or return the {@link #MAX} instance on failure.
     *
     * <p>Wraps {@link #fromIndexOrElse(Integer, ESteamAuth)}
     * w/ {@link #MAX} as the default value.
     *
     * @param index     index of the instance, may be null
     *
     * @return  associated {@link ESteamAuth} instance or {@link #MAX}
     */
    @NotNull
    @Contract(pure = true)
    public static ESteamAuth fromIndexOrMax(
            @Nullable
            final Integer index
    ) {
        return fromIndexOrElse(index, MAX);
    }

    /**
     * Get an {@link ESteamAuth} instance by its index
     * or return {@code null} instance on failure.
     *
     * <p>Wraps {@link #fromIndexOrElse(Integer, ESteamAuth)}
     * w/ {@code null} as the default value.
     *
     * @param index     index of the instance, may be null
     *
     * @return  associated {@link ESteamAuth} instance or {@code null}
     */
    @UnknownNullability
    @Contract(value = "null -> null", pure = true)
    public static ESteamAuth fromIndexOrNull(
            @Nullable
            final Integer index
    ) {
        return fromIndexOrElse(index, (@Nullable ESteamAuth) null);
    }

    /**
     * Get an {@link ESteamAuth} instance by its index.
     *
     * <p>Wraps {@link UwArray#getNoCheck(Object[], int)}.
     *
     * @param index     index of the instance, may be null
     *
     * @return  associated {@link ESteamAuth} instance
     */
    @UnknownNullability
    @Contract(pure = true)
    public static ESteamAuth fromIndexNoCheck(
            final int index
    ) {
        return UwArray.getNoCheck(SingletonValues.INSTANCE, index);
    }

    private static final class SingletonValues {
        @NotNull
        public static final ESteamAuth @NotNull [] INSTANCE = ESteamAuth.values();

        @Contract(value = "-> fail", pure = false)
        private SingletonValues() {
            throw new UnsupportedOperationException();
        }
    }

    private static final class SingletonValueList {
        @NotNull
        @Unmodifiable
        public static final List<@NotNull ESteamAuth> INSTANCE;

        static {
            final ESteamAuth[] values = SingletonValues.INSTANCE;
            final List<ESteamAuth> list = new ArrayList<>(values.length);

            //noinspection ManualArrayToCollectionCopy
            for (final ESteamAuth value : values) {
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
        public static final Map<@NotNull Integer, @NotNull ESteamAuth> INSTANCE;

        static {
            final ESteamAuth[] values = SingletonValues.INSTANCE;
            final Map<Integer, ESteamAuth> map = new HashMap<>();

            for (final ESteamAuth value : values) {
                if (map.containsKey(value.id)) {
                    throw new IllegalStateException("Identifier field of ESteamAuth class must be unique");
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
