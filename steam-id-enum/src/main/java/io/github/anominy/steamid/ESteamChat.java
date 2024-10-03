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
public enum ESteamChat {
    CLAN(USteamChat.CLAN_FLAG),
    LOBBY(USteamChat.LOBBY_FLAG),
    MM_LOBBY(USteamChat.MM_LOBBY_FLAG);

    @NotNull
    public static final String ATTRIBUTE_NAME_ID = "id";

    private final int id;

    @UnknownNullability
    private volatile String stringCache;

    @NotNull
    private final Object stringCacheMutex;

    @Contract(pure = true)
    ESteamChat(
            final int id
    ) {
        this.id = id;

        this.stringCache = null;

        this.stringCacheMutex = new Object();
    }

    @Contract(pure = true)
    public int getId() {
        return this.id;
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

    @NotNull
    @Unmodifiable
    @Contract(pure = true)
    public static List<@NotNull ESteamChat> getValueList() {
        return SingletonValueList.INSTANCE;
    }

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

    @UnknownNullability
    @Contract(value = "null -> null", pure = true)
    public static ESteamChat fromIdOrNull(
            @Nullable
            final Integer id
    ) {
        return fromIdOrElse(id, (@Nullable ESteamChat) null);
    }

    @UnknownNullability
    @Contract(pure = true)
    public static ESteamChat fromIdNoCheck(
            final int id
    ) {
        return SingletonMapById.INSTANCE.get(id);
    }

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

    @UnknownNullability
    @Contract(value = "null -> null", pure = true)
    public static ESteamChat fromIndexOrNull(
            @Nullable
            final Integer index
    ) {
        return fromIndexOrElse(index, (@Nullable ESteamChat) null);
    }

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
