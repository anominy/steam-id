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
public final class USteamDomain {
    @NotNull
    public static final String COMMUNITY = "steamcommunity.com";

    @NotNull
    public static final String INVITE = "s.team";

    @NotNull
    public static final String CHINA = "my.steamchina.com";

    @NotNull
    @Contract(value = "-> new", pure = true)
    public static String @NotNull [] getValues() {
        return new String[] {
                COMMUNITY,
                INVITE,
                CHINA
        };
    }

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
            final String[] values = USteamDomain.getValues();
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
    private USteamDomain() {
        throw new UnsupportedOperationException();
    }
}
