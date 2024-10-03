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

@SuppressWarnings({"unused", "DefaultAnnotationParam"})
public final class USteamAuth {
    public static final int NO = 0;
    public static final int YES = 1;

    public static final int MIN = NO;
    public static final int MAX = YES;

    @Contract(value = "-> new", pure = true)
    public static int @NotNull [] getValues() {
        return new int[] {
                NO,
                YES
        };
    }

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
            final int[] values = USteamAuth.getValues();
            final List<Integer> list = new ArrayList<>(values.length);

            for (final int value : values) {
                list.add(value);
            }

            INSTANCE = Collections.unmodifiableList(list);
        }
    }

    @Contract(value = "-> fail", pure = false)
    private USteamAuth() {
        throw new UnsupportedOperationException();
    }
}
