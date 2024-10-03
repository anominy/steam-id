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
public final class USteamRegex {
    @NotNull
    public static final String ID2 = "^STEAM_(?<"
            + USteamRegexGroup.UNIVERSE
            + ">["
            + USteamUniverse.BASE
            + "-"
            + USteamUniverse.MAX
            + "]):(?<"
            + USteamRegexGroup.AUTH
            + ">["
            + USteamAuth.MIN
            + "-"
            + USteamAuth.MAX
            + "]):(?<"
            + USteamRegexGroup.ID
            + ">\\d+)$";

    @NotNull
    public static final String ID3 = "^\\[?(?<"
            + USteamRegexGroup.ACCOUNT
            + ">["
            + USteamAccount.CHAR_BASE
            + "]){1}:(?<"
            + USteamRegexGroup.UNIVERSE
            + ">["
            + USteamUniverse.BASE
            + "-"
            + USteamUniverse.MAX
            + "]):(?<"
            + USteamRegexGroup.ID
            + ">\\d+)(:(?<"
            + USteamRegexGroup.INSTANCE
            + ">["
            + USteamInstance.MIN
            + "-"
            + USteamInstance.MAX
            + "]))?]?$";

    @NotNull
    public static final String ID64 = "^[0-9]{17}$";

    @NotNull
    public static final String VANITY_ID = "^[a-zA-Z0-9_-]{2,32}$";

    @NotNull
    public static final String INVITE_CODE = "^["
            + USteamInvite.CODE_BASE
            + USteamInvite.CODE_DELIMITER
            + "]+$";

    @NotNull
    public static final String CSGO_CODE = "^["
            + USteamCsgo.CODE_BASE
            + USteamCsgo.CODE_DELIMITER
            + "]{10}$";

    @NotNull
    public static final String PROFILE_URL = "^(?:https?:+//)?/*(?:my\\.steamchina|(?:www\\.)?steamcommunity)\\.com/+profiles/+(?<"
            + USteamRegexGroup.ID
            + ">.+?)/*$";

    @NotNull
    public static final String USER_URL = "^(?:https?:+//)?/*(?:(?:my\\.steamchina|(?:www\\.)?steamcommunity)\\.com/+user|s\\.team/+p)/+(?<"
            + USteamRegexGroup.ID
            + ">[\\w-]+)/*$";

    @NotNull
    @Contract(value = "-> new", pure = true)
    public static String @NotNull [] getValues() {
        return new String[] {
                ID2,
                ID3,
                ID64,
                VANITY_ID,
                INVITE_CODE,
                CSGO_CODE,
                PROFILE_URL,
                USER_URL
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
            final String[] values = USteamRegex.getValues();
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
    private USteamRegex() {
        throw new UnsupportedOperationException();
    }
}
