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
 * A Steam account universe types utility.
 *
 * @see <a href="https://developer.valvesoftware.com/wiki/SteamID#Universes_Available_for_Steam_Accounts">
 *     Steam Account Universe Types on Valve Developer Community</a>
 */
@SuppressWarnings({"unused", "DefaultAnnotationParam"})
public final class USteamUniverse {

    /**
     * An account universe type - Invalid.
     */
    public static final int INVALID = 0;

    /**
     * An account universe type - Public.
     */
    public static final int PUBLIC = 1;

    /**
     * An account universe type - Beta.
     */
    public static final int BETA = 2;

    /**
     * An account universe type - Internal.
     */
    public static final int INTERNAL = 3;

    /**
     * An account universe type - Dev.
     */
    public static final int DEV = 4;

    /**
     * An account universe type - RC.
     */
    public static final int RC = 5;

    /**
     * A base account universe type.
     *
     * <p>Wraps {@link #INVALID}.
     */
    public static final int BASE = INVALID;

    /**
     * A minimum account universe type.
     *
     * <p>Wraps {@link #PUBLIC}.
     */
    public static final int MIN = PUBLIC;

    /**
     * A maximum account universe type.
     *
     * <p>Wraps {@link #RC}.
     */
    public static final int MAX = RC;

    /**
     * Get an array of all Steam account universe types.
     *
     * @return  new array
     */
    @Contract(value = "-> new", pure = true)
    public static int @NotNull [] getValues() {
        return new int[] {
                INVALID,
                PUBLIC,
                BETA,
                INTERNAL,
                DEV,
                RC
        };
    }

    /**
     * Get an unmodifiable list of all Steam account universe types.
     *
     * @return  unmodifiable list
     */
    @NotNull
    @Unmodifiable
    @Contract(pure = true)
    public static List<@NotNull Integer> getValueList() {
        return SingletonValueList.INSTANCE;
    }

    private static final class SingletonValueList {
        @NotNull
        @Unmodifiable
        public static final List<@NotNull Integer> INSTANCE;

        static {
            final int[] values = USteamUniverse.getValues();
            final List<Integer> list = new ArrayList<>(values.length);

            for (final int value : values) {
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
    private USteamUniverse() {
        throw new UnsupportedOperationException();
    }
}
