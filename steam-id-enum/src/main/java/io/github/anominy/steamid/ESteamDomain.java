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
public enum ESteamDomain {
    COMMUNITY(USteamDomain.COMMUNITY),
    INVITE(USteamDomain.INVITE),
    CHINA(USteamDomain.CHINA);

    @NotNull
    public static final String ATTRIBUTE_NAME_VALUE = "value";

    @NotNull
    private final String value;

    @UnknownNullability
    private volatile String stringCache;

    @NotNull
    private final Object stringCacheMutex;

    @Contract(pure = true)
    ESteamDomain(
            @NotNull
            final String value
    ) {
        this.value = value;

        this.stringCache = null;

        this.stringCacheMutex = new Object();
    }

    @Contract(pure = true)
    public String getAsString() {
        return this.value;
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

    @NotNull
    @Unmodifiable
    @Contract(pure = true)
    public static List<@NotNull ESteamDomain> getValueList() {
        return SingletonValueList.INSTANCE;
    }

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

    @UnknownNullability
    @Contract(value = "null -> null", pure = true)
    public static ESteamDomain fromStringOrNull(
            @Nullable
            final String value
    ) {
        return fromStringOrElse(value, (@Nullable ESteamDomain) null);
    }

    @UnknownNullability
    @Contract(pure = true)
    public static ESteamDomain fromStringNoCheck(
            @NotNull
            final String value
    ) {
        return SingletonMapByString.INSTANCE.get(value);
    }

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

    @UnknownNullability
    @Contract(value = "null -> null", pure = true)
    public static ESteamDomain fromIndexOrNull(
            @Nullable
            final Integer index
    ) {
        return fromIndexOrElse(index, (@Nullable ESteamDomain) null);
    }

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
