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

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A Steam endpoints utility.
 */
@SuppressWarnings({"unused", "DefaultAnnotationParam"})
public final class USteamEndpoint {

    /**
     * A Steam endpoint - ID.
     */
    @NotNull
    public static final String ID = "id";

    /**
     * A Steam endpoint - Profiles.
     */
    @NotNull
    public static final String PROFILES = "profiles";

    /**
     * A Steam endpoint - User.
     */
    @NotNull
    public static final String USER = "user";

    /**
     * A Steam endpoint - P.
     */
    @NotNull
    public static final String P = "p";

    /**
     * Get an array of all Steam endpoints.
     *
     * @return  new array
     */
    @NotNull
    @Contract(value = "-> new", pure = true)
    public static String @NotNull [] getValues() {
        return new String[] {
                ID,
                PROFILES,
                USER
        };
    }

    /**
     * Get an unmodifiable list of all Steam endpoints.
     *
     * @return  unmodifiable list
     */
    @NotNull
    @Unmodifiable
    @Contract(pure = true)
    public static List<@NotNull String> getValueList() {
        return SingletonValueList.INSTANCE;
    }

    private static final class SingletonValueList {
        @NotNull
        @Unmodifiable
        public static final List<@NotNull String> INSTANCE;

        static {
            final String[] values = USteamEndpoint.getValues();
            final List<String> list = new ArrayList<>(values.length);

            //noinspection ManualArrayToCollectionCopy
            for (final String value : values) {
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

    @Contract(value = "-> fail", pure = false)
    private USteamEndpoint() {
        throw new UnsupportedOperationException();
    }
}
