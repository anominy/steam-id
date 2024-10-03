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

/**
 * A Steam ID utility.
 */
@SuppressWarnings({"unused", "DefaultAnnotationParam", "BooleanMethodIsAlwaysInverted"})
public final class USteamId {

    /**
     * A base type-32 identifier value.
     */
    public static final int BASE_XUID = 0x00000000;

    /**
     * A minimum type-32 identifier value.
     */
    public static final int MIN_XUID = 0x00000001;

    /**
     * A maximum type-32 identifier value.
     */
    public static final int MAX_XUID = 0x7FFFFFFF;

    /**
     * A base type-64 identifier value.
     */
    public static final long BASE_ID64 = 0x0110000100000000L;

    /**
     * A minimum type-64 identifier value.
     */
    public static final long MIN_ID64 = 0x0110000100000001L;

    /**
     * A maximum type-64 identifier value.
     */
    public static final long MAX_ID64 = 0x011000017FFFFFFFL;

    /**
     * A minimum type-2 identifier value.
     */
    public static final int MIN_ID2 = 0x00000000;

    /**
     * A maximum type-2 identifier value.
     */
    public static final int MAX_ID2 = 0x3FFFFFFF;

    /**
     * Check if Steam type-32 identifier is valid.
     *
     * <p>Wraps {@link #isSteamXuidValidNoCheck(int)}.
     *
     * @param xuid  Steam type-32 identifier to check, may be null
     *
     * @return  {@code true} if valid
     *          or {@code false} otherwise
     */
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

    /**
     * Check if Steam type-32 identifier is valid.
     *
     * <p>Wraps {@link #isSteamXuidValidNoCheck(long)}.
     *
     * @param xuid  Steam type-32 identifier to check, may be null
     *
     * @return  {@code true} if valid
     *          or {@code false} otherwise
     */
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

    /**
     * Check if Steam type-32 identifier is valid.
     *
     * <p>Wraps {@link #isSteamXuidValidNoCheck(String)}.
     *
     * @param xuid  Steam type-32 identifier to check, may be null
     *
     * @return  {@code true} if valid
     *          or {@code false} otherwise
     */
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

    /**
     * Check if Steam type-32 identifier is valid.
     *
     * @param xuid  Steam type-32 identifier to check
     *
     * @return  {@code true} if valid
     *          or {@code false} otherwise
     */
    @Contract(pure = true)
    public static boolean isSteamXuidValidNoCheck(
            final int xuid
    ) {
        return xuid >= MIN_XUID;
//                && xuid <= MAX_XUID;
    }

    /**
     * Check if Steam type-32 identifier is valid.
     *
     * @param xuid  Steam type-32 identifier to check
     *
     * @return  {@code true} if valid
     *          or {@code false} otherwise
     */
    @Contract(pure = true)
    public static boolean isSteamXuidValidNoCheck(
            final long xuid
    ) {
        return xuid >= MIN_XUID
                && xuid <= MAX_XUID;
    }

    /**
     * Check if Steam type-32 identifier is valid.
     *
     * <p>Wraps {@link #isSteamXuidValidNoCheck(int)}.
     *
     * @param xuid  Steam type-32 identifier to check, mustn't be null
     *
     * @return  {@code true} if valid
     *          or {@code false} otherwise
     *
     * @throws NumberFormatException if the provided identifier is {@code null}
     */
    @Contract(value = "null -> fail", pure = false)
    public static boolean isSteamXuidValidNoCheck(
            @UnknownNullability
            final String xuid
    ) {
        final int intVal = Integer.parseInt(xuid);
        return isSteamXuidValidNoCheck(intVal);
    }

    /**
     * Check if Steam type-64 identifier is valid.
     *
     * <p>Wraps {@link #isSteamId64ValidNoCheck(long)}.
     *
     * @param id64  Steam type-64 identifier to check, may be null
     *
     * @return  {@code true} if valid
     *          or {@code false} otherwise
     */
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

    /**
     * Check if Steam type-64 identifier is valid.
     *
     * <p>Wraps {@link #isSteamId64ValidNoCheck(String)}.
     *
     * @param id64  Steam type-64 identifier to check, may be null
     *
     * @return  {@code true} if valid
     *          or {@code false} otherwise
     */
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

    /**
     * Check if Steam type-64 identifier is valid.
     *
     * @param id64  Steam type-64 identifier to check
     *
     * @return  {@code true} if valid
     *          or {@code false} otherwise
     */
    @Contract(pure = true)
    public static boolean isSteamId64ValidNoCheck(
            final long id64
    ) {
        return id64 >= MIN_ID64
                && id64 <= MAX_ID64;
    }

    /**
     * Check if Steam type-64 identifier is valid.
     *
     * <p>Wraps {@link #isSteamId64ValidNoCheck(long)}.
     *
     * @param id64  Steam type-64 identifier to check, mustn't be null
     *
     * @return  {@code true} if valid
     *          or {@code false} otherwise
     *
     * @throws NumberFormatException if the provided identifier is {@code null}
     */
    @Contract(value = "null -> fail", pure = false)
    public static boolean isSteamId64ValidNoCheck(
            @UnknownNullability
            final String id64
    ) {
        final long longVal = Long.parseLong(id64);
        return isSteamId64ValidNoCheck(longVal);
    }

    /**
     * Check if Steam type-2 identifier is valid.
     *
     * <p>Wraps {@link #isSteamId2ValidNoCheck(int)}.
     *
     * @param id2  Steam type-2 identifier to check, may be null
     *
     * @return  {@code true} if valid
     *          or {@code false} otherwise
     */
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

    /**
     * Check if Steam type-2 identifier is valid.
     *
     * <p>Wraps {@link #isSteamId2ValidNoCheck(long)}.
     *
     * @param id2  Steam type-2 identifier to check, may be null
     *
     * @return  {@code true} if valid
     *          or {@code false} otherwise
     */
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

    /**
     * Check if Steam type-2 identifier is valid.
     *
     * <p>Wraps {@link #isSteamId2ValidNoCheck(String)}.
     *
     * @param id2  Steam type-2 identifier to check, may be null
     *
     * @return  {@code true} if valid
     *          or {@code false} otherwise
     */
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

    /**
     * Check if Steam type-2 identifier is valid.
     *
     * @param id2  Steam type-2 identifier to check
     *
     * @return  {@code true} if valid
     *          or {@code false} otherwise
     */
    @Contract(pure = true)
    public static boolean isSteamId2ValidNoCheck(
            final int id2
    ) {
        return id2 >= MIN_ID2
                && id2 <= MAX_ID2;
    }

    /**
     * Check if Steam type-2 identifier is valid.
     *
     * @param id2  Steam type-2 identifier to check
     *
     * @return  {@code true} if valid
     *          or {@code false} otherwise
     */
    @Contract(pure = true)
    public static boolean isSteamId2ValidNoCheck(
            final long id2
    ) {
        return id2 >= MIN_ID2
                && id2 <= MAX_ID2;
    }

    /**
     * Check if Steam type-2 identifier is valid.
     *
     * <p>Wraps {@link #isSteamId2ValidNoCheck(int)}.
     *
     * @param id2  Steam type-2 identifier to check, mustn't be null
     *
     * @return  {@code true} if valid
     *          or {@code false} otherwise
     *
     * @throws NullPointerException if the provided identifier is {@code null}
     */
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

    /**
     * Check if Steam type-3 identifier is valid.
     *
     * <p>Wraps {@link #isSteamId3ValidNoCheck(String)}.
     *
     * @param id3  Steam type-3 identifier to check, may be null
     *
     * @return  {@code true} if valid
     *          or {@code false} otherwise
     */
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

    /**
     * Check if Steam type-3 identifier is valid.
     *
     * <p>Wraps {@link #isSteamXuidValidNoCheck(int)}.
     *
     * @param id3  Steam type-3 identifier to check, mustn't be null
     *
     * @return  {@code true} if valid
     *          or {@code false} otherwise
     *
     * @throws NullPointerException if the provided identifier is {@code null}
     */
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
