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
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings({"unused", "DefaultAnnotationParam"})
public final class USteamId {
    public static final int BASE_XUID = 0x00000000;
    public static final int MIN_XUID = 0x00000001;
    public static final int MAX_XUID = 0x7FFFFFFF;

    public static final long BASE_ID64 = 0x0110000100000000L;
    public static final long MIN_ID64 = 0x0110000100000001L;
    public static final long MAX_ID64 = 0x011000017FFFFFFFL;

    public static final int MIN_ID2 = 0x00000000;
    public static final int MAX_ID2 = 0x3FFFFFFF;

    @Contract(value = "null -> false", pure = true)
    public static boolean isSteamXuidValid(
            @Nullable
            final Integer xuid
    ) {
        if (xuid == null) {
            return false;
        }

        return isSteamXuidValidNoCheck(xuid);
    }

    @Contract(value = "null -> false", pure = true)
    public static boolean isSteamXuidValid(
            @Nullable
            final Long xuid
    ) {
        if (xuid == null) {
            return false;
        }

        return isSteamXuidValidNoCheck(xuid);
    }

    @Contract(value = "null -> false", pure = true)
    public static boolean isSteamXuidValid(
            @Nullable
            final String xuid
    ) {
        if (xuid == null) {
            return false;
        }

        try {
            return isSteamXuidValidNoCheck(xuid);
        } catch (final IllegalArgumentException ignored) {
        }

        return false;
    }

    @Contract(pure = true)
    public static boolean isSteamXuidValidNoCheck(
            final int xuid
    ) {
        return xuid >= MIN_XUID;
//                && xuid <= MAX_XUID;
    }

    @Contract(pure = true)
    public static boolean isSteamXuidValidNoCheck(
            final long xuid
    ) {
        return xuid >= MIN_XUID
                && xuid <= MAX_XUID;
    }

    @Contract(value = "null -> fail", pure = false)
    public static boolean isSteamXuidValidNoCheck(
            @UnknownNullability
            final String xuid
    ) {
        final int intVal = Integer.parseInt(xuid);
        return isSteamXuidValidNoCheck(intVal);
    }

    @Contract(value = "null -> false", pure = true)
    public static boolean isSteamId64Valid(
            @Nullable
            final Long id64
    ) {
        if (id64 == null) {
            return false;
        }

        return isSteamId64ValidNoCheck(id64);
    }

    @Contract(value = "null -> false", pure = true)
    public static boolean isSteamId64Valid(
            @Nullable
            final String id64
    ) {
        if (id64 == null) {
            return false;
        }

        try {
            return isSteamId64ValidNoCheck(id64);
        } catch (final IllegalArgumentException ignored) {
        }

        return false;
    }

    @Contract(pure = true)
    public static boolean isSteamId64ValidNoCheck(
            final long id64
    ) {
        return id64 >= MIN_ID64
                && id64 <= MAX_ID64;
    }

    @Contract(value = "null -> fail", pure = false)
    public static boolean isSteamId64ValidNoCheck(
            @UnknownNullability
            final String id64
    ) {
        final long longVal = Long.parseLong(id64);
        return isSteamId64ValidNoCheck(longVal);
    }

    @Contract(value = "null -> false", pure = true)
    public static boolean isSteamId2Valid(
            @Nullable
            final Integer id2
    ) {
        if (id2 == null) {
            return false;
        }

        return isSteamId2ValidNoCheck(id2);
    }

    @Contract(value = "null -> false", pure = true)
    public static boolean isSteamId2Valid(
            @Nullable
            final Long id2
    ) {
        if (id2 == null) {
            return false;
        }

        return isSteamId2ValidNoCheck(id2);
    }

    @Contract(value = "null -> false", pure = true)
    public static boolean isSteamId2Valid(
            @Nullable
            final String id2
    ) {
        if (id2 == null) {
            return false;
        }

        try {
            return isSteamId2ValidNoCheck(id2);
        } catch (final IllegalArgumentException ignored) {
        }

        return false;
    }

    @Contract(pure = true)
    public static boolean isSteamId2ValidNoCheck(
            final int id2
    ) {
        return id2 >= MIN_ID2
                && id2 <= MAX_ID2;
    }

    @Contract(pure = true)
    public static boolean isSteamId2ValidNoCheck(
            final long id2
    ) {
        return id2 >= MIN_ID2
                && id2 <= MAX_ID2;
    }

    @Contract(value = "null -> fail", pure = false)
    public static boolean isSteamId2ValidNoCheck(
            @UnknownNullability
            final String id2
    ) {
        final Pattern pattern = USteamPattern.getId2Pattern();
        final Matcher matcher = pattern.matcher(id2);

        if (!matcher.matches()) {
            return false;
        }

        final String strVal = matcher.group(USteamRegexGroup.ID);
        final int intVal = Integer.parseInt(strVal);

        return isSteamId2ValidNoCheck(intVal);
    }

    @Contract(value = "null -> false", pure = true)
    public static boolean isSteamId3Valid(
            @Nullable
            final String id3
    ) {
        if (id3 == null) {
            return false;
        }

        try {
            return isSteamId3ValidNoCheck(id3);
        } catch (final IllegalArgumentException ignored) {
        }

        return false;
    }

    @Contract(value = "null -> fail", pure = false)
    public static boolean isSteamId3ValidNoCheck(
            @UnknownNullability
            final String id3
    ) {
        final Pattern pattern = USteamPattern.getId3Pattern();
        final Matcher matcher = pattern.matcher(id3);

        if (!matcher.matches()) {
            return false;
        }

        final String strVal = matcher.group(USteamRegexGroup.ID);
        final int intVal = Integer.parseInt(strVal);

        return isSteamXuidValidNoCheck(intVal);
    }

    @Contract(value = "-> fail", pure = false)
    private USteamId() {
        throw new UnsupportedOperationException();
    }
}
