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
 * A Steam regex utility.
 */
@SuppressWarnings({"unused", "DefaultAnnotationParam"})
public final class USteamRegex {

    /**
     * A Steam ID2 regex.
     *
     * <p>Possible matching group names:
     * <ul>
     *     <li>{@link USteamRegexGroup#UNIVERSE}.</li>
     *     <li>{@link USteamRegexGroup#AUTH}.</li>
     *     <li>{@link USteamRegexGroup#ID}.</li>
     * </ul>
     */
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

    /**
     * A Steam ID3 regex.
     *
     * <p>Possible matching group names:
     * <ul>
     *     <li>{@link USteamRegexGroup#ACCOUNT}.</li>
     *     <li>{@link USteamRegexGroup#UNIVERSE}.</li>
     *     <li>{@link USteamRegexGroup#ID}.</li>
     *     <li>{@link USteamRegexGroup#INSTANCE}.</li>
     * </ul>
     */
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

    /**
     * A Steam ID64 regex.
     */
    @NotNull
    public static final String ID64 = "^[0-9]{17}$";

    /**
     * A Steam vanity ID regex.
     */
    @NotNull
    public static final String VANITY_ID = "^[a-zA-Z0-9_-]{2,32}$";

    /**
     * A Steam invite code regex.
     */
    @NotNull
    public static final String INVITE_CODE = "^["
            + USteamInvite.CODE_BASE
            + USteamInvite.CODE_DELIMITER
            + "]+$";

    /**
     * A CS:GO friend code regex.
     */
    @NotNull
    public static final String CSGO_CODE = "^["
            + USteamCsgo.CODE_BASE
            + USteamCsgo.CODE_DELIMITER
            + "]{10}$";

    /**
     * A Steam /profiles/ URL regex.
     *
     * <p>Possible matching group names:
     * <ul>
     *     <li>{@link USteamRegexGroup#ID}</li>
     * </ul>
     */
    @NotNull
    public static final String PROFILE_URL = "^(?:https?:+//)?/*(?:my\\.steamchina|(?:www\\.)?steamcommunity)\\.com/+profiles/+(?<"
            + USteamRegexGroup.ID
            + ">.+?)/*$";

    /**
     * A Steam /user/ URL regex.
     *
     * <p>Possible matching group names:
     * <ul>
     *     <li>{@link USteamRegexGroup#ID}</li>
     * </ul>
     */
    @NotNull
    public static final String USER_URL = "^(?:https?:+//)?/*(?:(?:my\\.steamchina|(?:www\\.)?steamcommunity)\\.com/+user|s\\.team/+p)/+(?<"
            + USteamRegexGroup.ID
            + ">[\\w-]+)/*$";

    /**
     * Get an array of all Steam regexes.
     *
     * @return  new array
     */
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

    /**
     * Get an unmodifiable list of all Steam regexes.
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
