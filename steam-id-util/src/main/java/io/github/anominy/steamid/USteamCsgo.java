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

/**
 * A Steam CS:GO utility.
 *
 * <p>{@code USteamCsgo} is the utility class to make conversions
 * between a Steam account type-32 identifier and a CS:GO friend code.
 *
 * @see <a href="https://www.unknowncheats.me/forum/counterstrike-global-offensive/453555-de-encoding-cs-friend-codes.html">De- and encoding CS:GO friend codes on UnKnoWnCheaTs</a>
 * @see <a href="https://github.com/emily33901/go-csfriendcode">go-csgofriendcode by emily33901 on GitHub</a>
 */
@SuppressWarnings({"unused", "DefaultAnnotationParam"})
public final class USteamCsgo {

    /**
     * A minimum CS:GO friend code value.
     */
    @NotNull
    public static final String MIN_CODE = "AJJJS-ABAA";

    /**
     * A maximum CS:GO friend code value.
     */
    @NotNull
    public static final String MAX_CODE = "S5999-9988";

    /**
     * A CS:GO friend code base.
     */
    @NotNull
    public static final String CODE_BASE = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";

    /**
     * A CS:GO friend code delimiter.
     */
    @NotNull
    public static final String CODE_DELIMITER = "-";

    /**
     * A CS:GO friend code prefix.
     *
     * <p>Used to convert an interface-friendly
     * into an actual CS:GO friend code.
     */
    @NotNull
    public static final String CODE_PREFIX = "AAAA" + CODE_DELIMITER;

    /**
     * A CS:GO friend code length.
     */
    public static final int CODE_LENGTH = 13;

    /**
     * A CS:GO friend code delimiter points.
     */
    public static final int @NotNull [] CODE_POINTS = {4, 9};

    /**
     * A CS:GO friend code bit mask.
     *
     * <p>Used to normalize the xuid of a Steam ID.
     */
    private static final long HASH_MASK = 0x4353474F00000000L;


    /**
     * Convert a unique Steam account identifier
     * to a interface-friendly CS:GO friend code
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromXuidNoCheck(int)}.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>Steam account identifier isn't valid.</li>
     *     <li>MD5 message digest algorithm isn't supported.</li>
     * </ul>
     *
     * <hr>
     * <pre>{@code
     *     // (String) defaultValue
     *     USteamCsgo.fromXuidOrElse(0, <defaultValue>);
     *
     *     // (String) defaultValue
     *     USteamCsgo.fromXuidOrElse(Integer.MAX_VALUE, <defaultValue>);
     *
     *     // (String) "AJJJS-ABAA"
     *     USteamCsgo.fromXuidOrElse(1, <defaultValue>);
     *
     *     // (String) "AEVDG-WQTQ"
     *     USteamCsgo.fromXuidOrElse(1266042636, <defaultValue>);
     * }</pre>
     * <hr>
     *
     * @param xuid          Steam account identifier to convert, may be null
     * @param defaultValue  default value to return on failure, may be null
     *
     * @return  interface-friendly CS:GO friend code or the default value
     */
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

    /**
     * Convert a unique Steam account identifier
     * to a interface-friendly CS:GO friend code
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromXuidOrNull(Integer)}.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>Steam account identifier isn't valid.</li>
     *     <li>MD5 message digest algorithm isn't supported.</li>
     * </ul>
     *
     * <hr>
     * <pre>{@code
     *     // (String) defaultValue
     *     USteamCsgo.fromXuidOrElse(0, <defaultValueSupplier>);
     *
     *     // (String) defaultValue
     *     USteamCsgo.fromXuidOrElse(Integer.MAX_VALUE, <defaultValueSupplier>);
     *
     *     // (String) "AJJJS-ABAA"
     *     USteamCsgo.fromXuidOrElse(1, <defaultValueSupplier>);
     *
     *     // (String) "AEVDG-WQTQ"
     *     USteamCsgo.fromXuidOrElse(1266042636, <defaultValueSupplier>);
     * }</pre>
     * <hr>
     *
     * @param xuid                  Steam account identifier to convert, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  interface-friendly CS:GO friend code or the default value
     */
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

    /**
     * Convert a unique Steam account identifier
     * to a interface-friendly CS:GO friend code
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromXuidOrElse(Integer, Supplier)}.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>Steam account identifier isn't valid.</li>
     *     <li>MD5 message digest algorithm isn't supported.</li>
     * </ul>
     *
     * <hr>
     * <pre>{@code
     *     // (String) null
     *     USteamCsgo.fromXuidOrElse(0, <defaultValueSupplier>);
     *
     *     // (String) null
     *     USteamCsgo.fromXuidOrElse(Integer.MAX_VALUE, <defaultValueSupplier>);
     *
     *     // (String) "AJJJS-ABAA"
     *     USteamCsgo.fromXuidOrElse(1, <defaultValueSupplier>);
     *
     *     // (String) "AEVDG-WQTQ"
     *     USteamCsgo.fromXuidOrElse(1266042636, <defaultValueSupplier>);
     * }</pre>
     * <hr>
     *
     * @param xuid                  Steam account identifier to convert, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  interface-friendly CS:GO friend code or the default value
     */
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
    /**
     * Convert a unique Steam account identifier
     * to a interface-friendly CS:GO friend code
     * or return an empty string on failure.
     *
     * <p>Wraps {@link #fromXuidOrElse(Integer, String)}
     * w/ {@link UwString#EMPTY} as the default value.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>Steam account identifier isn't valid.</li>
     *     <li>MD5 message digest algorithm isn't supported.</li>
     * </ul>
     *
     * <hr>
     * <pre>{@code
     *     // (String) ""
     *     USteamCsgo.fromXuidOrEmpty(0);
     *
     *     // (String) ""
     *     USteamCsgo.fromXuidOrEmpty(Integer.MAX_VALUE);
     *
     *     // (String) "AJJJS-ABAA"
     *     USteamCsgo.fromXuidOrEmpty(1);
     *
     *     // (String) "AEVDG-WQTQ"
     *     USteamCsgo.fromXuidOrEmpty(1266042636);
     * }</pre>
     * <hr>
     *
     * @param xuid  Steam account identifier to convert, may be null
     *
     * @return  interface-friendly CS:GO friend code or the empty string
     */
    @NotNull
    @Contract(pure = true)
    public static String fromXuidOrEmpty(
            @Nullable
            final Integer xuid
    ) {
        return fromXuidOrElse(xuid, UwString.EMPTY);
    }

    /**
     * Convert a unique Steam account identifier
     * to a interface-friendly CS:GO friend code
     * or return {@code null} on failure.
     *
     * <p>Wraps {@link #fromXuidOrElse(Integer, String)}
     * w/ {@code null} as the default value.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>Steam account identifier isn't valid.</li>
     *     <li>MD5 message digest algorithm isn't supported.</li>
     * </ul>
     *
     * <hr>
     * <pre>{@code
     *     // (String) null
     *     USteamCsgo.fromXuidOrNull(0);
     *
     *     // (String) null
     *     USteamCsgo.fromXuidOrNull(Integer.MAX_VALUE);
     *
     *     // (String) "AJJJS-ABAA"
     *     USteamCsgo.fromXuidOrNull(1);
     *
     *     // (String) "AEVDG-WQTQ"
     *     USteamCsgo.fromXuidOrNull(1266042636);
     * }</pre>
     * <hr>
     *
     * @param xuid  Steam account identifier to convert, may be null
     *
     * @return  interface-friendly CS:GO friend code or {@code null}
     */
    @UnknownNullability
    @Contract(value = "null -> null", pure = true)
    public static String fromXuidOrNull(
            @Nullable
            final Integer xuid
    ) {
        return fromXuidOrElse(xuid, (@Nullable String) null);
    }

    /**
     * Convert a unique Steam account identifier
     * to a interface-friendly CS:GO friend code.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>MD5 message digest algorithm isn't supported.</li>
     * </ul>
     *
     * @param xuid  Steam account identifier to convert
     *
     * @return  interface-friendly CS:GO friend code
     */
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

    /**
     * Convert an interface-friendly CS:GO friend code
     * to a unique Steam account identifier
     * or return a default value on failure.
     *
     * <p>Wraps {@link #toXuidNoCheck(String)}.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>CS:GO friend code is null.</li>
     *     <li>CS:GO friend code doesn't match w/ the {@link USteamRegex#CSGO_CODE}.</li>
     * </ul>
     *
     * <hr>
     * <pre>{@code
     *     // (Integer) defaultValue
     *     USteamCsgo.toXuidOrElse(null, <defaultValue>);
     *
     *     // (Integer) defaultValue
     *     USteamCsgo.toXuidOrElse("", <defaultValue>);
     *
     *     // (Integer) 1
     *     USteamCsgo.toXuidOrElse("AJJJS-ABAA", <defaultValue>);
     *
     *     // (Integer) 1
     *     USteamCsgo.toXuidOrElse("  AJJJS-ABAA  ", <defaultValue>);
     *
     *     // (Integer) 1266042636
     *     USteamCsgo.toXuidOrElse("AEVDG-WQTQ", <defaultValue>);
     * }</pre>
     * <hr>
     *
     * @param code          interface-friendly CS:GO code to convert, may be null
     * @param defaultValue  default value to return on failure, may be null
     *
     * @return  Steam unique account identifier or the default value
     */
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

    /**
     * Convert an interface-friendly CS:GO friend code
     * to a unique Steam account identifier
     * or return a default value on failure.
     *
     * <p>Wraps {@link #toXuidOrNull(String)}.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>CS:GO friend code is null.</li>
     *     <li>CS:GO friend code doesn't match w/ the {@link USteamRegex#CSGO_CODE}.</li>
     * </ul>
     *
     * <hr>
     * <pre>{@code
     *     // (Integer) defaultValue
     *     USteamCsgo.toXuidOrElse(null, <defaultValueSupplier>);
     *
     *     // (Integer) defaultValue
     *     USteamCsgo.toXuidOrElse("", <defaultValueSupplier>);
     *
     *     // (Integer) 1
     *     USteamCsgo.toXuidOrElse("AJJJS-ABAA", <defaultValueSupplier>);
     *
     *     // (Integer) 1
     *     USteamCsgo.toXuidOrElse("  AJJJS-ABAA  ", <defaultValueSupplier>);
     *
     *     // (Integer) 1266042636
     *     USteamCsgo.toXuidOrElse("AEVDG-WQTQ", <defaultValueSupplier>);
     * }</pre>
     * <hr>
     *
     * @param code                  interface-friendly CS:GO code to convert, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  Steam unique account identifier or the default value
     */
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

    /**
     * Convert an interface-friendly CS:GO friend code
     * to a unique Steam account identifier
     * or return a default value on failure.
     *
     * <p>Wraps {@link #toXuidOrElse(String, Supplier)}.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>CS:GO friend code is null.</li>
     *     <li>CS:GO friend code doesn't match w/ the {@link USteamRegex#CSGO_CODE}.</li>
     * </ul>
     *
     * <hr>
     * <pre>{@code
     *     // (Integer) null
     *     USteamCsgo.toXuidOrElse(null, <defaultValueSupplier>);
     *
     *     // (Integer) null
     *     USteamCsgo.toXuidOrElse("", <defaultValueSupplier>);
     *
     *     // (Integer) 1
     *     USteamCsgo.toXuidOrElse("AJJJS-ABAA", <defaultValueSupplier>);
     *
     *     // (Integer) 1
     *     USteamCsgo.toXuidOrElse("  AJJJS-ABAA  ", <defaultValueSupplier>);
     *
     *     // (Integer) 1266042636
     *     USteamCsgo.toXuidOrElse("AEVDG-WQTQ", <defaultValueSupplier>);
     * }</pre>
     * <hr>
     *
     * @param code                  interface-friendly CS:GO code to convert, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  Steam unique account identifier or the default value
     */
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

    /**
     * Convert an interface-friendly CS:GO friend code
     * to a unique Steam account identifier
     * or return the base value on failure.
     *
     * <p>Wraps {@link #toXuidOrElse(String, Integer)}
     * w/ {@link USteamId#BASE_XUID} as the default value.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>CS:GO friend code is null.</li>
     *     <li>CS:GO friend code doesn't match w/ the {@link USteamRegex#CSGO_CODE}.</li>
     * </ul>
     *
     * <hr>
     * <pre>{@code
     *     // (Integer) 0
     *     USteamCsgo.toXuidOrBase(null);
     *
     *     // (Integer) 0
     *     USteamCsgo.toXuidOrBase("");
     *
     *     // (Integer) 1
     *     USteamCsgo.toXuidOrBase("AJJJS-ABAA");
     *
     *     // (Integer) 1
     *     USteamCsgo.toXuidOrBase("  AJJJS-ABAA  ");
     *
     *     // (Integer) 1266042636
     *     USteamCsgo.toXuidOrBase("AEVDG-WQTQ");
     * }</pre>
     * <hr>
     *
     * @param code  interface-friendly CS:GO code to convert, may be null
     *
     * @return  Steam unique account identifier or the base value
     */
    @Contract(pure = true)
    public static int toXuidOrBase(
            @Nullable
            final String code
    ) {
        return toXuidOrElse(code, USteamId.BASE_XUID);
    }

    /**
     * Convert an interface-friendly CS:GO friend code
     * to a unique Steam account identifier
     * or return {@code null} on failure.
     *
     * <p>Wraps {@link #toXuidOrElse(String, Integer)}
     * w/ {@code null} as the default value.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>CS:GO friend code is null.</li>
     *     <li>CS:GO friend code doesn't match w/ the {@link USteamRegex#CSGO_CODE}.</li>
     * </ul>
     *
     * <hr>
     * <pre>{@code
     *     // (Integer) null
     *     USteamCsgo.toXuidOrNull(null);
     *
     *     // (Integer) null
     *     USteamCsgo.toXuidOrNull("");
     *
     *     // (Integer) 1
     *     USteamCsgo.toXuidOrNull("AJJJS-ABAA");
     *
     *     // (Integer) 1
     *     USteamCsgo.toXuidOrNull("  AJJJS-ABAA  ");
     *
     *     // (Integer) 1266042636
     *     USteamCsgo.toXuidOrNull("AEVDG-WQTQ");
     * }</pre>
     * <hr>
     *
     * @param code  interface-friendly CS:GO code to convert, may be null
     *
     * @return  Steam unique account identifier or {@code null}
     */
    @UnknownNullability
    @Contract(value = "null -> null; !null -> !null", pure = true)
    public static Integer toXuidOrNull(
            @Nullable
            final String code
    ) {
        return toXuidOrElse(code, (@Nullable Integer) null);
    }

    /**
     * Convert an interface-friendly CS:GO friend code
     * to a unique Steam account identifier.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>CS:GO friend code is null.</li>
     *     <li>CS:GO friend code doesn't match w/ the {@link USteamRegex#CSGO_CODE}.</li>
     * </ul>
     *
     * @param code  interface-friendly CS:GO code to convert, mustn't be null
     *
     * @return  Steam unique account identifier or {@code null}
     *
     * @throws NullPointerException if the provided CS:GO friend code is {@code null}
     */
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
