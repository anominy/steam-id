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

import io.github.anominy.uwutils.UwObject;
import io.github.anominy.uwutils.UwString;
import io.github.anominy.uwutils.VoidSupplier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings({"unused", "DefaultAnnotationParam"})
public final class USteamCsgo {
    @NotNull
    public static final String MIN_CODE = "AJJJS-ABAA";

    @NotNull
    public static final String MAX_CODE = "S5999-9988";

    @NotNull
    public static final String CODE_BASE = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";

    @NotNull
    public static final String CODE_DELIMITER = "-";

    @NotNull
    public static final String CODE_PREFIX = "AAAA" + CODE_DELIMITER;

    public static final int CODE_LENGTH = 13;

    public static final int @NotNull [] CODE_POINTS = {4, 9};

    private static final long HASH_MASK = 0x4353474F00000000L;

    @UnknownNullability
    @Contract(value = "null, _ -> param2", pure = true)
    public static String fromXuidOrElse(
            @Nullable
            final Integer xuid,

            @Nullable
            final String defaultValue
    ) {
        if (!USteamId.isSteamXuidValid(xuid)) {
            return defaultValue;
        }

        try {
            return fromXuidNoCheck(xuid);
        } catch (final IllegalStateException ignored) {
        }

        return defaultValue;
    }

    @UnknownNullability
    @Contract(pure = false)
    public static String fromXuidOrElse(
            @Nullable
            final Integer xuid,

            @Nullable
            final Supplier<@UnknownNullability String> defaultValueSupplier
    ) {
        return UwObject.ifNull(fromXuidOrNull(xuid), defaultValueSupplier);
    }

    @UnknownNullability
    @Contract(value = "null, _ -> null", pure = false)
    public static String fromXuidOrElse(
            @Nullable
            final Integer xuid,

            @Nullable
            final VoidSupplier<@UnknownNullability String> defaultValueSupplier
    ) {
        return fromXuidOrElse(xuid, (Supplier<@UnknownNullability String>) defaultValueSupplier);
    }

    @NotNull
    @Contract(pure = true)
    public static String fromXuidOrEmpty(
            @Nullable
            final Integer xuid
    ) {
        return fromXuidOrElse(xuid, UwString.EMPTY);
    }

    @UnknownNullability
    @Contract(value = "null -> null", pure = true)
    public static String fromXuidOrNull(
            @Nullable
            final Integer xuid
    ) {
        return fromXuidOrElse(xuid, (@Nullable String) null);
    }

    @NotNull
    public static String fromXuidNoCheck(
            int xuid
    ) {
        final long hash = hash(xuid);

        long res = 0;
        for (int i = 0; i < 8; i++) {
            long a = ((res << 4) & 0xFFFFFFFFL) | (xuid & 0xF);

            res = res >> 28 << 32 | a & 0xFFFF;
            res = res >> 31 << 32 | a << 1 | hash >> i & 1;

            xuid >>= 4;
        }

        return base32(res)
                .substring(CODE_PREFIX.length());
    }

    @UnknownNullability
    @Contract(value = "null, _ -> param2; !null, _ -> !null", pure = true)
    public static Integer toXuidOrElse(
            @Nullable
            final String code,

            @Nullable
            final Integer defaultValue
    ) {
        if (code == null) {
            return defaultValue;
        }

        try {
            return toXuidNoCheck(code);
        } catch (final IllegalArgumentException ignored) {
        }

        return defaultValue;
    }

    @UnknownNullability
    @Contract(value = "!null, _ -> !null", pure = false)
    public static Integer toXuidOrElse(
            @Nullable
            final String code,

            @Nullable
            final Supplier<@UnknownNullability Integer> defaultValueSupplier
    ) {
        return UwObject.ifNull(toXuidOrNull(code), defaultValueSupplier);
    }

    @UnknownNullability
    @Contract(value = "null, _ -> null; !null, _ -> !null", pure = false)
    public static Integer toXuidOrElse(
            @Nullable
            final String code,

            @Nullable
            final VoidSupplier<@UnknownNullability Integer> defaultValueSupplier
    ) {
        return toXuidOrElse(code, (Supplier<@UnknownNullability Integer>) defaultValueSupplier);
    }

    @Contract(pure = true)
    public static int toXuidOrBase(
            @Nullable
            final String code
    ) {
        return toXuidOrElse(code, USteamId.BASE_XUID);
    }

    @UnknownNullability
    @Contract(value = "null -> null; !null -> !null", pure = true)
    public static Integer toXuidOrNull(
            @Nullable
            final String code
    ) {
        return toXuidOrElse(code, (@Nullable Integer) null);
    }

    @Contract(value = "null -> fail", pure = false)
    public static int toXuidNoCheck(
            @UnknownNullability
            String code
    ) {
        code = code.trim();

        final Pattern pattern = USteamPattern.getCsgoCodePattern();
        final Matcher matcher = pattern.matcher(code);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("Code doesn't match w/ defined Steam CS:GO code pattern");
        }

        long val = base32(CODE_PREFIX + code);
        long xuid = 0;

        for (int i = 0; i < 8; i++, val >>= 4) {
            xuid = xuid << 4 | (val >>= 1) & 0xF;
        }

        return (int) xuid;
    }

    @Contract(pure = false)
    private static long hash(
            final long xuid
    ) {
        final MessageDigest md5 = SingletonMd5MessageDigest.INSTANCE;
        if (md5 == null) {
            throw new IllegalStateException("MD5 instance mustn't be <null>");
        }

        byte[] bytes = ByteBuffer.allocate(Long.BYTES)
                .order(ByteOrder.LITTLE_ENDIAN)
                .putLong(xuid | HASH_MASK)
                .array();

        bytes = md5.digest(bytes);
        md5.reset();

        return ByteBuffer.wrap(bytes)
                .order(ByteOrder.LITTLE_ENDIAN)
                .getLong();
    }

    @NotNull
    @Contract(pure = true)
    private static String base32(
            long val
    ) {
        val = Long.reverseBytes(val);

        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < CODE_LENGTH; i++, val >>= 5) {
            sb.append(CODE_BASE.charAt((int) (val & 0x1F)));
        }

        for (int i = 0; i < CODE_POINTS.length; i++) {
            sb.insert(CODE_POINTS[i] + i, CODE_DELIMITER);
        }

        return sb.toString();
    }

    @Contract(pure = true)
    private static long base32(
            @NotNull
            String val
    ) {
        val = val.replace(CODE_DELIMITER, "");

        long res = 0;
        for (int i = 0; i < CODE_LENGTH; i++) {
            res |= (long) CODE_BASE.indexOf(val.charAt(i)) << (5 * i);
        }

        return Long.reverseBytes(res);
    }


    @SuppressWarnings("CallToPrintStackTrace")
    private static final class SingletonMd5MessageDigest {
        @Nullable
        public static final MessageDigest INSTANCE;

        static {
            MessageDigest md5 = null;

            try {
                md5 = MessageDigest.getInstance("MD5");
            } catch (final NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            INSTANCE = md5;
        }

        @Contract(value = "-> fail", pure = false)
        private SingletonMd5MessageDigest() {
            throw new UnsupportedOperationException();
        }
    }

    @Contract(value = "-> fail", pure = false)
    private USteamCsgo() {
        throw new UnsupportedOperationException();
    }
}
