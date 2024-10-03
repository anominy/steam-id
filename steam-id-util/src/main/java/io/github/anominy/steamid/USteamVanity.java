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
 * A Steam vanity URL types utility.
 */
@SuppressWarnings({"unused", "DefaultAnnotationParam"})
public final class USteamVanity {

    /**
     * A vanity URL type - Individual.
     *
     * <p>Used for /id/ endpoint.
     */
    public static final int INDIVIDUAL = 1;

    /**
     * A vanity URL type - Group.
     *
     * <p>Used for /groups/ endpoint.
     */
    public static final int GROUP = 2;

    /**
     * A vanity URL type - Game Group.
     */
    public static final int GAME_GROUP = 3;

    /**
     * A minimum vanity URL type.
     *
     * <p>Wraps {@link #INDIVIDUAL}.
     */
    public static final int MIN = INDIVIDUAL;

    /**
     * A maximum vanity URL type.
     *
     * <p>Wraps {@link #GAME_GROUP}.
     */
    public static final int MAX = GAME_GROUP;

    /**
     * Get an array of all Steam vanity URL types.
     *
     * @return  new array
     */
    @Contract(value = "-> new", pure = true)
    public static int @NotNull [] getValues() {
        return new int[] {
                INDIVIDUAL,
                GROUP,
                GAME_GROUP
        };
    }

    /**
     * Get an unmodifiable list of all Steam vanity URL types.
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
            final int[] values = USteamVanity.getValues();
            final List<Integer> list = new ArrayList<>(values.length);

            for (final int value : values) {
                list.add(value);
            }

            INSTANCE = Collections.unmodifiableList(list);
        }

        @Contract(value = "-> fail", pure = false)
        public SingletonValueList() {
            throw new UnsupportedOperationException();
        }
    }

    @Contract(value = "-> fail", pure = false)
    private USteamVanity() {
        throw new UnsupportedOperationException();
    }
}
