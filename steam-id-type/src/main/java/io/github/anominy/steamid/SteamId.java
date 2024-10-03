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

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings({"unused", "DefaultAnnotationParam", "SynchronizeOnNonFinalField", "MethodDoesntCallSuperMethod", "BooleanMethodIsAlwaysInverted"})
public final class SteamId implements Serializable, Cloneable {
    @NotNull
    private static final String ATTRIBUTE_NAME_XUID = "xuid";

    @NotNull
    private static final String ATTRIBUTE_NAME_UNIVERSE = "universe";

    @NotNull
    private static final String ATTRIBUTE_NAME_INSTANCE = "instance";

    @NotNull
    private static final String ATTRIBUTE_NAME_ACCOUNT = "account";

    @UnknownNullability
    private final Integer xuid;

    @UnknownNullability
    private final ESteamUniverse universe;

    @UnknownNullability
    private final ESteamInstance instance;

    @UnknownNullability
    private final ESteamAccount account;

    @UnknownNullability
    private transient volatile Boolean isValidCache;

    @UnknownNullability
    private transient volatile Long staticKeyCache;

    @UnknownNullability
    private transient volatile Long id64Cache;

    @UnknownNullability
    private transient volatile String id2Cache;

    @UnknownNullability
    private transient volatile String id3Cache;

    @UnknownNullability
    private transient volatile String inviteCodeCache;

    @UnknownNullability
    private transient volatile String csgoCodeCache;

    @UnknownNullability
    private transient volatile String id64UrlCache;

    @UnknownNullability
    private transient volatile String id3UrlCache;

    @UnknownNullability
    private transient volatile String userUrlCache;

    @UnknownNullability
    private transient volatile String inviteUrlCache;

    @UnknownNullability
    private transient volatile String chinaUrlCache;

    @UnknownNullability
    private transient volatile Integer hashCodeCache;

    @UnknownNullability
    private transient volatile String stringCache;

    @UnknownNullability
    private transient Object isValidCacheMutex;

    @UnknownNullability
    private transient Object staticKeyCacheMutex;

    @UnknownNullability
    private transient Object id64CacheMutex;

    @UnknownNullability
    private transient Object id2CacheMutex;

    @UnknownNullability
    private transient Object id3CacheMutex;

    @UnknownNullability
    private transient Object inviteCodeCacheMutex;

    @UnknownNullability
    private transient Object csgoCodeCacheMutex;

    @UnknownNullability
    private transient Object id64UrlCacheMutex;

    @UnknownNullability
    private transient Object id3UrlCacheMutex;

    @UnknownNullability
    private transient Object userUrlCacheMutex;

    @UnknownNullability
    private transient Object inviteUrlCacheMutex;

    @UnknownNullability
    private transient Object chinaUrlCacheMutex;

    @UnknownNullability
    private transient Object hashCodeCacheMutex;

    @UnknownNullability
    private transient Object stringCacheMutex;

    @Contract(pure = true)
    private void initMutexObjects() {
        this.isValidCacheMutex = new Object();
        this.staticKeyCacheMutex = new Object();
        this.id64CacheMutex = new Object();
        this.id2CacheMutex = new Object();
        this.id3CacheMutex = new Object();
        this.inviteCodeCacheMutex = new Object();
        this.csgoCodeCacheMutex = new Object();
        this.id64UrlCacheMutex = new Object();
        this.id3UrlCacheMutex = new Object();
        this.userUrlCacheMutex = new Object();
        this.inviteUrlCacheMutex = new Object();
        this.chinaUrlCacheMutex = new Object();
        this.hashCodeCacheMutex = new Object();
        this.stringCacheMutex = new Object();
    }

    @Contract(value = "-> this", pure = true)
    private Object readResolve() {
        this.initMutexObjects();
        return this;
    }

    @Contract(pure = true)
    private SteamId(
            @Nullable
            final Integer xuid,

            @Nullable
            final ESteamUniverse universe,

            @Nullable
            final ESteamInstance instance,

            @Nullable
            final ESteamAccount account
    ) {
        this.xuid = xuid;
        this.universe = universe;
        this.instance = instance;
        this.account = account;

        this.initMutexObjects();
    }

    @Contract(pure = true)
    private SteamId(
            @Nullable
            final Integer xuid,

            @Nullable
            final Integer universe,

            @Nullable
            final Integer instance,

            @Nullable
            final Integer account
    ) {
        this(
                xuid,
                ESteamUniverse.fromIdOrNull(universe),
                ESteamInstance.fromIdOrNull(instance),
                ESteamAccount.fromIdOrNull(account)
        );
    }

    @Contract(pure = true)
    private SteamId(
            @Nullable
            final Integer xuid,

            @Nullable
            final Integer universe,

            @Nullable
            final Integer instance,

            @Nullable
            final Character account
    ) {
        this(
                xuid,
                ESteamUniverse.fromIdOrNull(universe),
                ESteamInstance.fromIdOrNull(instance),
                ESteamAccount.fromCharOrNull(account)
        );
    }

    @Contract(pure = true)
    private SteamId(
            @Nullable
            final Integer xuid
    ) {
        this(
                xuid,
                ESteamUniverse.PUBLIC,
                ESteamInstance.DESKTOP,
                ESteamAccount.INDIVIDUAL
        );
    }

    @Contract(value = "null -> fail", pure = true)
    private SteamId(
            @UnknownNullability
            final SteamId that
    ) {
        this(
                that.xuid,
                that.universe,
                that.instance,
                that.account
        );

        this.isValidCache = that.isValidCache;
        this.staticKeyCache = that.staticKeyCache;
        this.id64Cache = that.id64Cache;
        this.id2Cache = that.id2Cache;
        this.id3Cache = that.id3Cache;
        this.inviteCodeCache = that.inviteCodeCache;
        this.csgoCodeCache = that.csgoCodeCache;
        this.id64UrlCache = that.id64UrlCache;
        this.id3UrlCache = that.id3UrlCache;
        this.userUrlCache = that.userUrlCache;
        this.inviteUrlCache = that.inviteUrlCache;
        this.chinaUrlCache = that.chinaUrlCache;

        this.hashCodeCache = that.hashCodeCache;
        this.stringCache = that.stringCache;
    }

    @Contract(pure = true)
    public boolean hasXuid() {
        return this.xuid != null;
    }

    @Contract(pure = true)
    public boolean hasUniverseType() {
        return this.universe != null;
    }

    @Contract(pure = true)
    public boolean hasInstanceType() {
        return this.instance != null;
    }

    @Contract(pure = true)
    public boolean hasAccountType() {
        return this.account != null;
    }

    @Contract(pure = true)
    public boolean isStaticKeyCached() {
        return this.staticKeyCache != null;
    }

    @Contract(pure = true)
    public boolean isSteam64Cached() {
        return this.id64Cache != null;
    }

    @Contract(pure = true)
    public boolean isSteam2Cached() {
        return this.id2Cache != null;
    }

    @Contract(pure = true)
    public boolean isSteam3Cached() {
        return this.id3Cache != null;
    }

    @Contract(pure = true)
    public boolean isInviteCodeCached() {
        return this.inviteCodeCache != null;
    }

    @Contract(pure = true)
    public boolean isCsgoCodeCached() {
        return this.csgoCodeCache != null;
    }

    @Contract(pure = true)
    public boolean isSteam64UrlCached() {
        return this.id64UrlCache != null;
    }

    @Contract(pure = true)
    public boolean isSteam3UrlCached() {
        return this.id3UrlCache != null;
    }

    @Contract(pure = true)
    public boolean isSteamUserUrlCached() {
        return this.userUrlCache != null;
    }

    @Contract(pure = true)
    public boolean isSteamInviteUrlCached() {
        return this.inviteUrlCache != null;
    }

    @Contract(pure = true)
    public boolean isSteam64ChinaUrlCached() {
        return this.chinaUrlCache != null;
    }

    @Contract(pure = true)
    public boolean isHashCodeCached() {
        return this.hashCodeCache != null;
    }

    @Contract(pure = true)
    public boolean isStringCached() {
        return this.stringCache != null;
    }

    @UnknownNullability
    @Contract(pure = true)
    public Integer getXuidOrElse(
            @Nullable
            final Integer defaultValue
    ) {
        return UwObject.ifNull(this.getXuidOrNull(), defaultValue);
    }

    @UnknownNullability
    @Contract(pure = false)
    public Integer getXuidOrElse(
            @Nullable
            final Supplier<@UnknownNullability Integer> defaltValueSupplier
    ) {
        return UwObject.ifNull(this.getXuidOrNull(), defaltValueSupplier);
    }

    @UnknownNullability
    @Contract(pure = false)
    public Integer getXuidOrElse(
            @Nullable
            final VoidSupplier<@UnknownNullability Integer> defaultValueSupplier
    ) {
        return this.getXuidOrElse((@Nullable Supplier<@UnknownNullability Integer>) defaultValueSupplier);
    }

    @NotNull
    @Contract(pure = true)
    public Integer getXuidOrBase() {
        return this.getXuidOrElse(USteamId.BASE_XUID);
    }

    @NotNull
    @Contract(pure = true)
    public Integer getXuidOrMin() {
        return this.getXuidOrElse(USteamId.MIN_XUID);
    }

    @NotNull
    @Contract(pure = true)
    public Integer getXuidOrMax() {
        return this.getXuidOrElse(USteamId.MAX_XUID);
    }

    @UnknownNullability
    @Contract(pure = true)
    public Integer getXuidOrNull() {
        return this.xuid;
    }

    @UnknownNullability
    @Contract(pure = true)
    public ESteamUniverse getUniverseTypeOrElse(
            @Nullable
            final ESteamUniverse defaultValue
    ) {
        return UwObject.ifNull(this.getUniverseTypeOrNull(), defaultValue);
    }

    @UnknownNullability
    @Contract(pure = false)
    public ESteamUniverse getUniverseTypeOrElse(
            @Nullable
            final Supplier<@UnknownNullability ESteamUniverse> defaultValueSupplier
    ) {
        return UwObject.ifNull(this.getUniverseTypeOrNull(), defaultValueSupplier);
    }

    @UnknownNullability
    @Contract(pure = false)
    public ESteamUniverse getUniverseTypeOrElse(
            @Nullable
            final VoidSupplier<@UnknownNullability ESteamUniverse> defaultValueSupplier
    ) {
        return this.getUniverseTypeOrElse((@Nullable Supplier<@UnknownNullability ESteamUniverse>) defaultValueSupplier);
    }

    @NotNull
    @Contract(pure = true)
    public ESteamUniverse getUniverseTypeOrBase() {
        return this.getUniverseTypeOrElse(ESteamUniverse.BASE);
    }

    @NotNull
    @Contract(pure = true)
    public ESteamUniverse getUniverseTypeOrMin() {
        return this.getUniverseTypeOrElse(ESteamUniverse.MIN);
    }

    @NotNull
    @Contract(pure = true)
    public ESteamUniverse getUniverseTypeOrMax() {
        return this.getUniverseTypeOrElse(ESteamUniverse.MAX);
    }

    @UnknownNullability
    @Contract(pure = true)
    public ESteamUniverse getUniverseTypeOrNull() {
        return this.universe;
    }

    @UnknownNullability
    @Contract(pure = true)
    public Integer getUniverseTypeIdOrElse(
            @Nullable
            final Integer defaultValue
    ) {
        return UwObject.ifNull(this.getUniverseTypeIdOrNull(), defaultValue);
    }

    @UnknownNullability
    @Contract(pure = false)
    public Integer getUniverseTypeIdOrElse(
            @Nullable
            final Supplier<@UnknownNullability Integer> defaultValueSupplier
    ) {
        return UwObject.ifNull(this.getUniverseTypeIdOrNull(), defaultValueSupplier);
    }

    @UnknownNullability
    @Contract(pure = false)
    public Integer getUniverseTypeIdOrElse(
            @Nullable
            final VoidSupplier<@UnknownNullability Integer> defaultValueSupplier
    ) {
        return this.getUniverseTypeIdOrElse((@Nullable Supplier<@UnknownNullability Integer>) defaultValueSupplier);
    }

    @NotNull
    @Contract(pure = true)
    public Integer getUniverseTypeIdOrBase() {
        return this.getUniverseTypeIdOrElse(USteamUniverse.BASE);
    }

    @NotNull
    @Contract(pure = true)
    public Integer getUniverseTypeIdOrMin() {
        return this.getUniverseTypeIdOrElse(USteamUniverse.MIN);
    }

    @NotNull
    @Contract(pure = true)
    public Integer getUniverseTypeIdOrMax() {
        return this.getUniverseTypeIdOrElse(USteamUniverse.MAX);
    }

    @UnknownNullability
    @Contract(pure = true)
    public Integer getUniverseTypeIdOrNull() {
        return UwObject.ifNotNullNoCheck(this.getUniverseTypeOrNull(), ESteamUniverse::getId);
    }

    @UnknownNullability
    @Contract(pure = true)
    public ESteamInstance getInstanceTypeOrElse(
            @Nullable
            final ESteamInstance defaultValue
    ) {
        return UwObject.ifNull(this.getInstanceTypeOrNull(), defaultValue);
    }

    @UnknownNullability
    @Contract(pure = false)
    public ESteamInstance getInstanceTypeOrElse(
            @Nullable
            final Supplier<@UnknownNullability ESteamInstance> defaultValueSupplier
    ) {
        return UwObject.ifNull(this.getInstanceTypeOrNull(), defaultValueSupplier);
    }

    @UnknownNullability
    @Contract(pure = false)
    public ESteamInstance getInstanceTypeOrElse(
            @Nullable
            final VoidSupplier<@UnknownNullability ESteamInstance> defaultValueSupplier
    ) {
        return this.getInstanceTypeOrElse((@Nullable Supplier<@UnknownNullability ESteamInstance>) defaultValueSupplier);
    }

    @NotNull
    @Contract(pure = true)
    public ESteamInstance getInstanceTypeOrMin() {
        return this.getInstanceTypeOrElse(ESteamInstance.MIN);
    }

    @NotNull
    @Contract(pure = true)
    public ESteamInstance getInstanceTypeOrMax() {
        return this.getInstanceTypeOrElse(ESteamInstance.MAX);
    }

    @UnknownNullability
    @Contract(pure = true)
    public ESteamInstance getInstanceTypeOrNull() {
        return this.instance;
    }

    @UnknownNullability
    @Contract(pure = true)
    public Integer getInstanceTypeIdOrElse(
            @Nullable
            final Integer defaultValue
    ) {
        return UwObject.ifNull(this.getInstanceTypeIdOrNull(), defaultValue);
    }

    @UnknownNullability
    @Contract(pure = false)
    public Integer getInstanceTypeIdOrElse(
            @Nullable
            final Supplier<@UnknownNullability Integer> defaultValueSupplier
    ) {
        return UwObject.ifNull(this.getInstanceTypeIdOrNull(), defaultValueSupplier);
    }

    @UnknownNullability
    @Contract(pure = false)
    public Integer getInstanceTypeIdOrElse(
            @Nullable
            final VoidSupplier<@UnknownNullability Integer> defaultValueSupplier
    ) {
        return this.getInstanceTypeIdOrElse((@Nullable Supplier<@UnknownNullability Integer>) defaultValueSupplier);
    }

    @NotNull
    @Contract(pure = true)
    public Integer getInstanceTypeIdOrMin() {
        return this.getInstanceTypeIdOrElse(USteamInstance.MIN);
    }

    @NotNull
    @Contract(pure = true)
    public Integer getInstanceTypeIdOrMax() {
        return this.getInstanceTypeIdOrElse(USteamInstance.MAX);
    }

    @UnknownNullability
    @Contract(pure = true)
    public Integer getInstanceTypeIdOrNull() {
        return UwObject.ifNotNullNoCheck(this.getInstanceTypeOrNull(), ESteamInstance::getId);
    }

    @UnknownNullability
    @Contract(pure = true)
    public ESteamAccount getAccountTypeOrElse(
            @Nullable
            final ESteamAccount defaultValue
    ) {
        return UwObject.ifNull(this.getAccountTypeOrNull(), defaultValue);
    }

    @UnknownNullability
    @Contract(pure = false)
    public ESteamAccount getAccountTypeOrElse(
            @Nullable
            final Supplier<@UnknownNullability ESteamAccount> defaultValueSupplier
    ) {
        return UwObject.ifNull(this.getAccountTypeOrNull(), defaultValueSupplier);
    }

    @UnknownNullability
    @Contract(pure = false)
    public ESteamAccount getAccountTypeOrElse(
            @Nullable
            final VoidSupplier<@UnknownNullability ESteamAccount> defaultValueSupplier
    ) {
        return this.getAccountTypeOrElse((@Nullable Supplier<@UnknownNullability ESteamAccount>) defaultValueSupplier);
    }

    @NotNull
    @Contract(pure = true)
    public ESteamAccount getAccountTypeOrBase() {
        return this.getAccountTypeOrElse(ESteamAccount.BASE);
    }

    @NotNull
    @Contract(pure = true)
    public ESteamAccount getAccountTypeOrMin() {
        return this.getAccountTypeOrElse(ESteamAccount.MIN);
    }

    @NotNull
    @Contract(pure = true)
    public ESteamAccount getAccountTypeOrMax() {
        return this.getAccountTypeOrElse(ESteamAccount.MAX);
    }

    @UnknownNullability
    @Contract(pure = true)
    public ESteamAccount getAccountTypeOrNull() {
        return this.account;
    }

    @UnknownNullability
    @Contract(pure = true)
    public Integer getAccountTypeIdOrElse(
            @Nullable
            final Integer defaultValue
    ) {
        return UwObject.ifNull(this.getAccountTypeIdOrNull(), defaultValue);
    }

    @UnknownNullability
    @Contract(pure = false)
    public Integer getAccountTypeIdOrElse(
            @Nullable
            final Supplier<@UnknownNullability Integer> defaultValueSupplier
    ) {
        return UwObject.ifNull(this.getAccountTypeIdOrNull(), defaultValueSupplier);
    }

    @UnknownNullability
    @Contract(pure = false)
    public Integer getAccountTypeIdOrElse(
            @Nullable
            final VoidSupplier<@UnknownNullability Integer> defaultValueSupplier
    ) {
        return this.getAccountTypeIdOrElse((@Nullable Supplier<@UnknownNullability Integer>) defaultValueSupplier);
    }

    @NotNull
    @Contract(pure = true)
    public Integer getAccountTypeIdOrBase() {
        return this.getAccountTypeIdOrElse(USteamAccount.BASE_ID);
    }

    @NotNull
    @Contract(pure = true)
    public Integer getAccountTypeIdOrMin() {
        return this.getAccountTypeIdOrElse(USteamAccount.MIN_ID);
    }

    @NotNull
    @Contract(pure = true)
    public Integer getAccountTypeIdOrMax() {
        return this.getAccountTypeIdOrElse(USteamAccount.MAX_ID);
    }

    @UnknownNullability
    @Contract(pure = true)
    public Integer getAccountTypeIdOrNull() {
        return UwObject.ifNotNullNoCheck(this.getAccountTypeOrNull(), ESteamAccount::getId);
    }

    @UnknownNullability
    @Contract(pure = true)
    public Character getAccountTypeCharOrElse(
            @Nullable
            final Character defaultValue
    ) {
        return UwObject.ifNull(this.getAccountTypeCharOrNull(), defaultValue);
    }

    @UnknownNullability
    @Contract(pure = false)
    public Character getAccountTypeCharOrElse(
            @Nullable
            final Supplier<@UnknownNullability Character> defaultValueSupplier
    ) {
        return UwObject.ifNull(this.getAccountTypeCharOrNull(), defaultValueSupplier);
    }

    @UnknownNullability
    @Contract(pure = false)
    public Character getAccountTypeCharOrElse(
            @Nullable
            final VoidSupplier<@UnknownNullability Character> defaultValueSupplier
    ) {
        return this.getAccountTypeCharOrElse((@Nullable Supplier<@UnknownNullability Character>) defaultValueSupplier);
    }

    @NotNull
    @Contract(pure = true)
    public Character getAccountTypeCharOrBase() {
        return this.getAccountTypeCharOrElse(USteamAccount.BASE_CHAR);
    }

    @NotNull
    @Contract(pure = true)
    public Character getAccountTypeCharOrMin() {
        return this.getAccountTypeCharOrElse(USteamAccount.MIN_CHAR);
    }

    @NotNull
    @Contract(pure = true)
    public Character getAccountTypeCharOrMax() {
        return this.getAccountTypeCharOrElse(USteamAccount.MAX_CHAR);
    }

    @UnknownNullability
    @Contract(pure = true)
    public Character getAccountTypeCharOrNull() {
        return UwObject.ifNotNullNoCheck(this.getAccountTypeOrNull(), ESteamAccount::getChar);
    }

    @UnknownNullability
    @Contract(pure = true)
    public Boolean getIsValidCache() {
        return this.isValidCache;
    }

    @UnknownNullability
    @Contract(pure = true)
    public Long getStaticKeyCache() {
        return this.staticKeyCache;
    }

    @UnknownNullability
    @Contract(pure = true)
    public Long getSteam64Cache() {
        return this.id64Cache;
    }

    @UnknownNullability
    @Contract(pure = true)
    public String getSteam2Cache() {
        return this.id2Cache;
    }

    @UnknownNullability
    @Contract(pure = true)
    public String getSteam3Cache() {
        return this.id3Cache;
    }

    @UnknownNullability
    @Contract(pure = true)
    public String getInviteCodeCache() {
        return this.inviteCodeCache;
    }

    @UnknownNullability
    @Contract(pure = true)
    public String getCsgoCodeCache() {
        return this.csgoCodeCache;
    }

    @UnknownNullability
    @Contract(pure = true)
    public String getSteam64UrlCache() {
        return this.id64UrlCache;
    }

    @UnknownNullability
    @Contract(pure = true)
    public String getSteam3UrlCache() {
        return this.id3UrlCache;
    }

    @UnknownNullability
    @Contract(pure = true)
    public String getSteamUserUrlCache() {
        return this.userUrlCache;
    }

    @UnknownNullability
    @Contract(pure = true)
    public String getSteamInviteUrlCache() {
        return this.inviteUrlCache;
    }

    @UnknownNullability
    @Contract(pure = true)
    public String getSteam64ChinaUrlCache() {
        return this.chinaUrlCache;
    }

    @UnknownNullability
    @Contract(pure = true)
    public Integer getHashCodeCache() {
        return this.hashCodeCache;
    }

    @UnknownNullability
    @Contract(pure = true)
    public String getStringCache() {
        return this.stringCache;
    }

    @Contract(pure = true)
    public boolean isValid() {
        if (this.isValidCache != null) {
            return this.isValidCache;
        }

        synchronized (this.isValidCacheMutex) {
            if (this.isValidCache != null) {
                return this.isValidCache;
            }

            if (this.xuid == null || this.universe == null
                    || this.instance == null || this.account == null) {
                return (this.isValidCache = false);
            }

            if (this.xuid < USteamId.BASE_XUID
                /*&& this.xuid > USteamId.MAX_XUID*/) {
                return (this.isValidCache = false);
            }

            if (this.universe == ESteamUniverse.INVALID
                    || this.account == ESteamAccount.INVALID) {
                return (this.isValidCache = false);
            }

            if (this.xuid < USteamId.MIN_XUID) {
                return (this.isValidCache = this.account != ESteamAccount.INDIVIDUAL
                        && this.account != ESteamAccount.GAME_SERVER
                        && (this.account != ESteamAccount.CLAN || this.instance == ESteamInstance.ALL));
            }

            return (this.isValidCache = true);
        }
    }

    @UnknownNullability
    @Contract(pure = true)
    public Long toStaticKeyOrElse(
            @Nullable
            final Long defaultValue
    ) {
        return UwObject.ifNull(this.toStaticKeyOrNull(), defaultValue);
    }

    @UnknownNullability
    @Contract(pure = false)
    public Long toStaticKeyOrElse(
            @Nullable
            final Supplier<@UnknownNullability Long> defaultValueSupplier
    ) {
        return UwObject.ifNull(this.toStaticKeyOrNull(), defaultValueSupplier);
    }

    @UnknownNullability
    @Contract(pure = false)
    public Long toStaticKeyOrElse(
            @Nullable
            final VoidSupplier<@UnknownNullability Long> defaultValueSupplier
    ) {
        return this.toStaticKeyOrElse((@Nullable Supplier<@UnknownNullability Long>) defaultValueSupplier);
    }

    @NotNull
    @Contract(pure = true)
    public Long toStaticKeyOrZero() {
        return this.toStaticKeyOrElse(0L);
    }

    @NotNull
    @Contract(pure = true)
    public Long toStaticKeyOrMin() {
        return this.toStaticKeyOrElse(Long.MIN_VALUE);
    }

    @NotNull
    @Contract(pure = true)
    public Long toStaticKeyOrMax() {
        return this.toStaticKeyOrElse(Long.MAX_VALUE);
    }

    @UnknownNullability
    @Contract(pure = true)
    public Long toStaticKeyOrNull() {
        if (this.staticKeyCache != null) {
            return this.staticKeyCache;
        }

        if (!this.isValid()) {
            return null;
        }

        synchronized (this.staticKeyCacheMutex) {
            if (this.staticKeyCache != null) {
                return this.staticKeyCache;
            }

            final BigInteger universe = BigInteger.valueOf(this.universe.getId())
                    .shiftLeft(USteamBit.ACCOUNT_UNIVERSE_OFFSET);

            final BigInteger account = BigInteger.valueOf(this.account.getId())
                    .shiftLeft(USteamBit.ACCOUNT_ID_OFFSET);

            try {
                return (this.staticKeyCache = BigInteger.valueOf(this.xuid)
                        .add(universe)
                        .add(account)
                        .longValueExact());
            } catch (ArithmeticException ignored) {
            }
        }

        return null;
    }

    @UnknownNullability
    @Contract(pure = true)
    public Long toSteam64OrElse(
            @Nullable
            final Long defaultValue
    ) {
        return UwObject.ifNull(this.toSteam64OrNull(), defaultValue);
    }

    @UnknownNullability
    @Contract(pure = false)
    public Long toSteam64OrElse(
            @Nullable
            final Supplier<@UnknownNullability Long> defaultValueSupplier
    ) {
        return UwObject.ifNull(this.toSteam64OrNull(), defaultValueSupplier);
    }

    @UnknownNullability
    @Contract(pure = false)
    public Long toSteam64OrElse(
            @Nullable
            final VoidSupplier<@UnknownNullability Long> defaultValueSupplier
    ) {
        return this.toSteam64OrElse((@Nullable Supplier<@UnknownNullability Long>) defaultValueSupplier);
    }

    @NotNull
    @Contract(pure = true)
    public Long toSteam64OrBase() {
        return this.toSteam64OrElse(USteamId.BASE_ID64);
    }

    @NotNull
    @Contract(pure = true)
    public Long toSteam64OrMin() {
        return this.toSteam64OrElse(USteamId.MIN_ID64);
    }

    @NotNull
    @Contract(pure = true)
    public Long toSteam64OrMax() {
        return this.toSteam64OrElse(USteamId.MAX_ID64);
    }

    @UnknownNullability
    @Contract(pure = true)
    public Long toSteam64OrNull() {
        if (this.id64Cache != null) {
            return this.id64Cache;
        }

        if (!this.isValid()) {
            return null;
        }

        synchronized (this.id64CacheMutex) {
            if (this.id64Cache != null) {
                return this.id64Cache;
            }

            final long xuid = this.xuid;
            final long instance = this.instance.getId();
            final long account = this.account.getId();
            final long universe = this.universe.getId();

            return (this.id64Cache = xuid << USteamBit.ACCOUNT_ID_OFFSET
                    | instance << USteamBit.ACCOUNT_INSTANCE_OFFSET
                    | account << USteamBit.ACCOUNT_TYPE_OFFSET
                    | universe << USteamBit.ACCOUNT_UNIVERSE_OFFSET);
        }
    }

    @UnknownNullability
    @Contract(pure = true)
    public String toSteam2OrElse(
            @Nullable
            final String defaultValue
    ) {
        return UwObject.ifNull(this.toSteam2OrNull(), defaultValue);
    }

    @UnknownNullability
    @Contract(pure = false)
    public String toSteam2OrElse(
            @Nullable
            final Supplier<@UnknownNullability String> defaultValueSupplier
    ) {
        return UwObject.ifNull(this.toSteam2OrNull(), defaultValueSupplier);
    }

    @UnknownNullability
    @Contract(pure = false)
    public String toSteam2OrElse(
            @Nullable
            final VoidSupplier<@UnknownNullability String> defaultValueSupplier
    ) {
        return this.toSteam2OrElse((@Nullable Supplier<@UnknownNullability String>) defaultValueSupplier);
    }

    @NotNull
    @Contract(pure = true)
    public String toSteam2OrEmpty() {
        return this.toSteam2OrElse(UwString.EMPTY);
    }

    @UnknownNullability
    @Contract(pure = true)
    public String toSteam2OrNull() {
        if (this.id2Cache != null) {
            return this.id2Cache;
        }

        if (!this.isValid()) {
            return null;
        }

        synchronized (this.id2CacheMutex) {
            if (this.id2Cache != null) {
                return this.id2Cache;
            }

            final int x = this.universe.getId();
            final int y = this.xuid & 1;
            final int z = this.xuid >> 1;

            return (this.id2Cache = "STEAM_" + x + ":" + y + ":" + z);
        }
    }

    @UnknownNullability
    @Contract(pure = true)
    public String toSteam3OrElse(
            @Nullable
            final String defaultValue
    ) {
        return UwObject.ifNull(this.toSteam3OrNull(), defaultValue);
    }

    @UnknownNullability
    @Contract(pure = false)
    public String toSteam3OrElse(
            @Nullable
            final Supplier<@UnknownNullability String> defaultValueSupplier
    ) {
        return UwObject.ifNull(this.toSteam3OrNull(), defaultValueSupplier);
    }

    @UnknownNullability
    @Contract(pure = false)
    public String toSteam3OrElse(
            @Nullable
            final VoidSupplier<@UnknownNullability String> defaultValueSupplier
    ) {
        return this.toSteam3OrElse((@Nullable Supplier<@UnknownNullability String>) defaultValueSupplier);
    }

    @NotNull
    @Contract(pure = true)
    public String toSteam3OrEmpty() {
        return this.toSteam3OrElse(UwString.EMPTY);
    }

    @UnknownNullability
    @Contract(pure = true)
    public String toSteam3OrNull() {
        if (this.id3Cache != null) {
            return this.id3Cache;
        }

        if (!this.isValid()) {
            return null;
        }

        synchronized (this.id3CacheMutex) {
            if (this.id3Cache != null) {
                return this.id3Cache;
            }

            final boolean bInstance;
            final char ch = this.account.getChar();

            //noinspection CommentedOutCode
            switch (this.account) {
//                case CHAT:
//                    switch (this.instance) {
//                        case CLAN:
//                            ch = USteamAccount.CLAN_CHAT_CHAR;
//                            break;
//
//                        case LOBBY:
//                            ch = USteamAccount.LOBBY_CHAT_CHAR;
//                            break;
//                    }
//                    break;

                case ANON_GAME_SERVER:
                case MULTISEAT:
                    bInstance = true;
                    break;

                default:
                    bInstance = false;
            }

            int universe = this.universe.getId();
            int instance = this.instance.getId();

            return (this.id3Cache = "[" + ch + ":" + universe + ":" + this.xuid + (bInstance ? ":" + instance : "") + "]");
        }
    }

    @UnknownNullability
    @Contract(pure = true)
    public String toInviteCodeOrElse(
            @Nullable
            final String defaultValue
    ) {
        return UwObject.ifNull(this.toInviteCodeOrNull(), defaultValue);
    }

    @UnknownNullability
    @Contract(pure = false)
    public String toInviteCodeOrElse(
            @Nullable
            final Supplier<@UnknownNullability String> defaultValueSupplier
    ) {
        return UwObject.ifNull(this.toInviteCodeOrNull(), defaultValueSupplier);
    }

    @UnknownNullability
    @Contract(pure = false)
    public String toInviteCodeOrElse(
            @Nullable
            final VoidSupplier<@UnknownNullability String> defaultValueSupplier
    ) {
        return this.toInviteCodeOrElse((@Nullable Supplier<@UnknownNullability String>) defaultValueSupplier);
    }

    @NotNull
    @Contract(pure = true)
    public String toInviteCodeOrEmpty() {
        return this.toInviteCodeOrElse(UwString.EMPTY);
    }

    @UnknownNullability
    @Contract(pure = true)
    public String toInviteCodeOrNull() {
        if (this.inviteCodeCache != null) {
            return this.inviteCodeCache;
        }

        if (!this.isValid()) {
            return null;
        }

        synchronized (this.inviteCodeCacheMutex) {
            if (this.inviteCodeCache != null) {
                return this.inviteCodeCache;
            }

            return (this.inviteCodeCache = USteamInvite.fromXuidNoCheck(this.xuid));
        }
    }

    @UnknownNullability
    @Contract(pure = true)
    public String toCsgoCodeOrElse(
            @Nullable
            final String defaultValue
    ) {
        return UwObject.ifNull(this.toCsgoCodeOrNull(), defaultValue);
    }

    @UnknownNullability
    @Contract(pure = false)
    public String toCsgoCodeOrElse(
            @Nullable
            final Supplier<@UnknownNullability String> defaultValueSupplier
    ) {
        return UwObject.ifNull(this.toCsgoCodeOrNull(), defaultValueSupplier);
    }

    @UnknownNullability
    @Contract(pure = false)
    public String toCsgoCodeOrElse(
            @Nullable
            final VoidSupplier<@UnknownNullability String> defaultValueSupplier
    ) {
        return this.toCsgoCodeOrElse((@Nullable Supplier<@UnknownNullability String>) defaultValueSupplier);
    }

    @NotNull
    @Contract(pure = true)
    public String toCsgoCodeOrEmpty() {
        return this.toCsgoCodeOrElse(UwString.EMPTY);
    }

    @UnknownNullability
    @Contract(pure = true)
    public String toCsgoCodeOrNull() {
        if (this.csgoCodeCache != null) {
            return this.csgoCodeCache;
        }

        if (!this.isValid()) {
            return null;
        }

        synchronized (this.csgoCodeCacheMutex) {
            if (this.csgoCodeCache != null) {
                return this.csgoCodeCache;
            }

            return (this.csgoCodeCache = USteamCsgo.fromXuidNoCheck(this.xuid));
        }
    }

    @UnknownNullability
    @Contract(pure = true)
    public String toSteam64UrlOrElse(
            @Nullable
            final String defaultValue
    ) {
        return UwObject.ifNull(this.toSteam64UrlOrNull(), defaultValue);
    }

    @UnknownNullability
    @Contract(pure = false)
    public String toSteam64UrlOrElse(
            @Nullable
            final Supplier<@UnknownNullability String> defaultValueSupplier
    ) {
        return UwObject.ifNull(this.toSteam64UrlOrNull(), defaultValueSupplier);
    }

    @UnknownNullability
    @Contract(pure = false)
    public String toSteam64UrlOrElse(
            @Nullable
            final VoidSupplier<@UnknownNullability String> defaultValueSupplier
    ) {
        return this.toSteam64UrlOrElse((@Nullable Supplier<@UnknownNullability String>) defaultValueSupplier);
    }

    @NotNull
    @Contract(pure = true)
    public String toSteam64UrlOrEmpty() {
        return this.toSteam64UrlOrElse(UwString.EMPTY);
    }

    @UnknownNullability
    @Contract(pure = true)
    public String toSteam64UrlOrNull() {
        if (this.id64UrlCache != null) {
            return this.id64UrlCache;
        }

        final Long id64 = this.toSteam64OrNull();
        if (id64 == null) {
            return null;
        }

        synchronized (this.id64UrlCacheMutex) {
            if (this.id64UrlCache != null) {
                return this.id64UrlCache;
            }

            return (this.id64UrlCache = USteamUrl.PROFILE + id64);
        }
    }

    @UnknownNullability
    @Contract(pure = true)
    public String toSteam3UrlOrElse(
            @Nullable
            final String defaultValue
    ) {
        return UwObject.ifNull(this.toSteam3UrlOrNull(), defaultValue);
    }

    @UnknownNullability
    @Contract(pure = false)
    public String toSteam3UrlOrElse(
            @Nullable
            final Supplier<@UnknownNullability String> defaultValueSupplier
    ) {
        return UwObject.ifNull(this.toSteam3UrlOrNull(), defaultValueSupplier);
    }

    @UnknownNullability
    @Contract(pure = false)
    public String toSteam3UrlOrElse(
            @Nullable
            final VoidSupplier<@UnknownNullability String> defaultValueSupplier
    ) {
        return this.toSteam3UrlOrElse((@Nullable Supplier<@UnknownNullability String>) defaultValueSupplier);
    }

    @NotNull
    @Contract(pure = true)
    public String toSteam3UrlOrEmpty() {
        return this.toSteam3UrlOrElse(UwString.EMPTY);
    }

    @UnknownNullability
    @Contract(pure = true)
    public String toSteam3UrlOrNull() {
        if (this.id3UrlCache != null) {
            return this.id3UrlCache;
        }

        final String id3 = this.toSteam3OrNull();
        if (id3 == null) {
            return null;
        }

        synchronized (this.id3UrlCacheMutex) {
            if (this.id3UrlCache != null) {
                return this.id3UrlCache;
            }

            return (this.id3UrlCache = USteamUrl.PROFILE + id3);
        }
    }

    @UnknownNullability
    @Contract(pure = true)
    public String toSteamUserUrlOrElse(
            @Nullable
            final String defaultValue
    ) {
        return UwObject.ifNull(this.toSteamUserUrlOrNull(), defaultValue);
    }

    @UnknownNullability
    @Contract(pure = false)
    public String toSteamUserUrlOrElse(
            @Nullable
            final Supplier<@UnknownNullability String> defaultValueSupplier
    ) {
        return UwObject.ifNull(this.toSteamUserUrlOrNull(), defaultValueSupplier);
    }

    @UnknownNullability
    @Contract(pure = false)
    public String toSteamUserUrlOrElse(
            @Nullable
            final VoidSupplier<@UnknownNullability String> defaultValueSupplier
    ) {
        return this.toSteamUserUrlOrElse((@Nullable Supplier<@UnknownNullability String>) defaultValueSupplier);
    }

    @NotNull
    @Contract(pure = true)
    public String toSteamUserUrlOrEmpty() {
        return this.toSteamUserUrlOrElse(UwString.EMPTY);
    }

    @UnknownNullability
    @Contract(pure = true)
    public String toSteamUserUrlOrNull() {
        if (this.userUrlCache != null) {
            return this.userUrlCache;
        }

        final String inviteCode = this.toInviteCodeOrNull();
        if (inviteCode == null) {
            return null;
        }

        synchronized (this.userUrlCacheMutex) {
            if (this.userUrlCache != null) {
                return this.userUrlCache;
            }

            return (this.userUrlCache = USteamUrl.USER + inviteCode);
        }
    }

    @UnknownNullability
    @Contract(pure = true)
    public String toSteamInviteUrlOrElse(
            @Nullable
            final String defaultValue
    ) {
        return UwObject.ifNull(this.toSteamInviteUrlOrNull(), defaultValue);
    }

    @UnknownNullability
    @Contract(pure = false)
    public String toSteamInviteUrlOrElse(
            @Nullable
            final Supplier<@UnknownNullability String> defaultValueSupplier
    ) {
        return UwObject.ifNull(this.toSteamInviteUrlOrNull(), defaultValueSupplier);
    }

    @UnknownNullability
    @Contract(pure = false)
    public String toSteamInviteUrlOrElse(
            @Nullable
            final VoidSupplier<@UnknownNullability String> defaultValueSupplier
    ) {
        return this.toSteamInviteUrlOrElse((@Nullable Supplier<@UnknownNullability String>) defaultValueSupplier);
    }

    @NotNull
    @Contract(pure = true)
    public String toSteamInviteUrlOrEmpty() {
        return this.toSteamInviteUrlOrElse(UwString.EMPTY);
    }

    @UnknownNullability
    @Contract(pure = true)
    public String toSteamInviteUrlOrNull() {
        if (this.inviteUrlCache != null) {
            return this.inviteUrlCache;
        }

        final String inviteCode = this.toInviteCodeOrNull();
        if (inviteCode == null) {
            return null;
        }

        synchronized (this.inviteUrlCacheMutex) {
            if (this.inviteUrlCache != null) {
                return this.inviteUrlCache;
            }

            return (this.inviteUrlCache = USteamUrl.INVITE + inviteCode);
        }
    }

    @UnknownNullability
    @Contract(pure = true)
    public String toSteam64ChinaUrlOrElse(
            @Nullable
            final String defaultValue
    ) {
        return UwObject.ifNull(this.toSteam64ChinaUrlOrNull(), defaultValue);
    }

    @UnknownNullability
    @Contract(pure = false)
    public String toSteam64ChinaUrlOrElse(
            @Nullable
            final Supplier<@UnknownNullability String> defaultValueSupplier
    ) {
        return UwObject.ifNull(this.toSteam64ChinaUrlOrNull(), defaultValueSupplier);
    }

    @UnknownNullability
    @Contract(pure = false)
    public String toSteam64ChinaUrlOrElse(
            @Nullable
            final VoidSupplier<@UnknownNullability String> defaultValueSupplier
    ) {
        return this.toSteam64ChinaUrlOrElse((@Nullable Supplier<@UnknownNullability String>) defaultValueSupplier);
    }

    @NotNull
    @Contract(pure = true)
    public String toSteam64ChinaUrlOrEmpty() {
        return this.toSteam64ChinaUrlOrElse(UwString.EMPTY);
    }

    @UnknownNullability
    @Contract(pure = true)
    public String toSteam64ChinaUrlOrNull() {
        if (this.chinaUrlCache != null) {
            return this.chinaUrlCache;
        }

        final Long id64 = this.toSteam64OrNull();
        if (id64 == null) {
            return null;
        }

        synchronized (this.chinaUrlCacheMutex) {
            if (this.chinaUrlCache != null) {
                return this.chinaUrlCache;
            }

            return (this.chinaUrlCache = USteamUrl.CHINA + id64);
        }
    }

    @NotNull
    @Contract(pure = true)
    public SteamId withXuid(
            @Nullable
            final Integer xuid
    ) {
        if (Objects.equals(this.xuid, xuid)) {
            return this;
        }

        return new SteamId(xuid, this.universe, this.instance, this.account);
    }

    @NotNull
    @Contract(pure = true)
    public SteamId withUniverseType(
            @Nullable
            final ESteamUniverse universe
    ) {
        if (this.universe == universe) {
            return this;
        }

        return new SteamId(this.xuid, universe, this.instance, this.account);
    }

    @NotNull
    @Contract(pure = true)
    public SteamId withUniverseType(
            @Nullable
            final Integer universe
    ) {
        return this.withUniverseType(ESteamUniverse.fromIdOrNull(universe));
    }

    @NotNull
    @Contract(pure = true)
    public SteamId withInstanceType(
            @Nullable
            final ESteamInstance instance
    ) {
        if (this.instance == instance) {
            return this;
        }

        return new SteamId(this.xuid, this.universe, instance, this.account);
    }

    @NotNull
    @Contract(pure = true)
    public SteamId withInstanceType(
            @Nullable
            final Integer instance
    ) {
        return this.withInstanceType(ESteamInstance.fromIdOrNull(instance));
    }

    @NotNull
    @Contract(pure = true)
    public SteamId withAccountType(
            @Nullable
            final ESteamAccount account
    ) {
        if (this.account == account) {
            return this;
        }

        return new SteamId(this.xuid, this.universe, this.instance, account);
    }

    @NotNull
    @Contract(pure = true)
    public SteamId withAccountType(
            @Nullable
            final Integer account
    ) {
        return this.withAccountType(ESteamAccount.fromIdOrNull(account));
    }

    @NotNull
    @Contract(pure = true)
    public SteamId withAccountType(
            @Nullable
            final Character account
    ) {
        return this.withAccountType(ESteamAccount.fromCharOrNull(account));
    }

    @Override
    @Contract(pure = true)
    public boolean equals(
            @Nullable
            final Object obj
    ) {
        if (this == obj) {
            return true;
        }

        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        SteamId that = (SteamId) obj;
        return Objects.equals(this.xuid, that.xuid)
                && this.universe == that.universe
                && this.instance == that.instance
                && this.account == that.account;
    }

    @Override
    @Contract(pure = true)
    public int hashCode() {
        if (this.hashCodeCache != null) {
            return this.hashCodeCache;
        }

        synchronized (this.hashCodeCacheMutex) {
            if (this.hashCodeCache != null) {
                return this.hashCodeCache;
            }

            return (this.hashCodeCache = Objects.hash(this.xuid, this.universe, this.instance, this.account));
        }
    }

    @Override
    @NotNull
    @Contract(pure = true)
    public String toString() {
        if (this.stringCache != null) {
            return this.stringCache;
        }

        synchronized (this.stringCacheMutex) {
            if (this.stringCache != null) {
                return this.stringCache;
            }

            final String simpleName = SteamId.class.getSimpleName();

            return (this.stringCache = simpleName + "["
                    + ATTRIBUTE_NAME_XUID
                    + "="
                    + this.xuid
                    + ", "
                    + ATTRIBUTE_NAME_UNIVERSE
                    + "="
                    + this.universe
                    + ", "
                    + ATTRIBUTE_NAME_INSTANCE
                    + "="
                    + this.instance
                    + ", "
                    + ATTRIBUTE_NAME_ACCOUNT
                    + "="
                    + this.account
                    + "]");
        }
    }

    @Override
    @NotNull
    @Contract(pure = true)
    public SteamId clone() {
        return new SteamId(this);
    }

    @UnknownNullability
    @Contract(value = "null, _ -> param2", pure = true)
    public static SteamId fromSteamXuidOrElse(
            @Nullable
            final Integer xuid,

            @Nullable
            final SteamId defaultValue
    ) {
        if (!USteamId.isSteamXuidValid(xuid)) {
            return defaultValue;
        }

        return new SteamId(xuid);
    }

    @UnknownNullability
    @Contract(pure = false)
    public static SteamId fromSteamXuidOrElse(
            @Nullable
            final Integer xuid,

            @Nullable
            final Supplier<@UnknownNullability SteamId> defaultValueSupplier
    ) {
        return UwObject.ifNull(fromSteamXuidOrNull(xuid), defaultValueSupplier);
    }

    @UnknownNullability
    @Contract(value = "null, _ -> null", pure = false)
    public static SteamId fromSteamXuidOrElse(
            @Nullable
            final Integer xuid,

            @Nullable
            final VoidSupplier<@UnknownNullability SteamId> defaultValueSupplier
    ) {
        return fromSteamXuidOrElse(xuid, (@Nullable Supplier<@UnknownNullability SteamId>) defaultValueSupplier);
    }

    @UnknownNullability
    @Contract(value = "null -> null", pure = true)
    public static SteamId fromSteamXuidOrNull(
            @Nullable
            final Integer xuid
    ) {
        return fromSteamXuidOrElse(xuid, (@Nullable SteamId) null);
    }

    @UnknownNullability
    @Contract(value = "null, _ -> param2", pure = true)
    public static SteamId fromSteamXuidOrElse(
            @Nullable
            final String xuid,

            @Nullable
            final SteamId defaultValue
    ) {
        try {
            //noinspection DataFlowIssue
            final int id = Integer.parseUnsignedInt(xuid);
            return fromSteamXuidOrElse(id, defaultValue);
        } catch (final NumberFormatException ignored) {
        }

        return defaultValue;
    }

    @UnknownNullability
    @Contract(pure = false)
    public static SteamId fromSteamXuidOrElse(
            @Nullable
            final String xuid,

            @Nullable
            final Supplier<@UnknownNullability SteamId> defaultValueSupplier
    ) {
        return UwObject.ifNull(fromSteamXuidOrNull(xuid), defaultValueSupplier);
    }

    @UnknownNullability
    @Contract(value = "null, _ -> null", pure = false)
    public static SteamId fromSteamXuidOrElse(
            @Nullable
            final String xuid,

            @Nullable
            final VoidSupplier<@UnknownNullability SteamId> defaultValueSupplier
    ) {
        return fromSteamXuidOrElse(xuid, (@Nullable Supplier<@UnknownNullability SteamId>) defaultValueSupplier);
    }

    @UnknownNullability
    @Contract(value = "null -> null", pure = true)
    public static SteamId fromSteamXuidOrNull(
            @Nullable
            final String xuid
    ) {
        return fromSteamXuidOrElse(xuid, (@Nullable SteamId) null);
    }

    @UnknownNullability
    @Contract(value = "null, _ -> param2", pure = true)
    public static SteamId fromSteam64OrElse(
            @Nullable
            final Long id64,

            @Nullable
            final SteamId defaultValue
    ) {
        if (!USteamId.isSteamId64Valid(id64)) {
            return defaultValue;
        }

        final int xuid = USteamBit.getAccountXuid(id64);
        final int universe = USteamBit.getAccountUniverse(id64);
        final int instance = USteamBit.getAccountInstance(id64);
        final int account = USteamBit.getAccountType(id64);

        return new SteamId(xuid, universe, instance, account);
    }

    @UnknownNullability
    @Contract(pure = false)
    public static SteamId fromSteam64OrElse(
            @Nullable
            final Long id64,

            @Nullable
            final Supplier<@UnknownNullability SteamId> defaultValueSupplier
    ) {
        return UwObject.ifNull(fromSteam64OrNull(id64), defaultValueSupplier);
    }

    @UnknownNullability
    @Contract(value = "null, _ -> null", pure = false)
    public static SteamId fromSteam64OrElse(
            @Nullable
            final Long id64,

            @Nullable
            final VoidSupplier<@UnknownNullability SteamId> defaultValueSupplier
    ) {
        return fromSteam64OrElse(id64, (@Nullable Supplier<@UnknownNullability SteamId>) defaultValueSupplier);
    }

    @UnknownNullability
    @Contract(value = "null -> null", pure = true)
    public static SteamId fromSteam64OrNull(
            @Nullable
            final Long id64
    ) {
        return fromSteam64OrElse(id64, (@Nullable SteamId) null);
    }

    @UnknownNullability
    @Contract(value = "null, _ -> param2", pure = true)
    public static SteamId fromSteam64OrElse(
            @Nullable
            String id64,

            @Nullable
            final SteamId defaultValue
    ) {
        if (id64 == null) {
            return defaultValue;
        }

        id64 = id64.trim();
        try {
            final long val = Long.parseUnsignedLong(id64);
            return fromSteam64OrElse(id64, defaultValue);
        } catch (final NumberFormatException ignored) {
        }

        return defaultValue;
    }

    @UnknownNullability
    @Contract(pure = false)
    public static SteamId fromSteam64OrElse(
            @Nullable
            final String id64,

            @Nullable
            final Supplier<@UnknownNullability SteamId> defaultValueSupplier
    ) {
        return UwObject.ifNull(fromSteam64OrNull(id64), defaultValueSupplier);
    }

    @UnknownNullability
    @Contract(value = "null, _ -> null", pure = false)
    public static SteamId fromSteam64OrElse(
            @Nullable
            final String id64,

            @Nullable
            final VoidSupplier<@UnknownNullability SteamId> defaultValueSupplier
    ) {
        return fromSteam64OrElse(id64, (@Nullable Supplier<@UnknownNullability SteamId>) defaultValueSupplier);
    }

    @UnknownNullability
    @Contract(value = "null -> null", pure = true)
    public static SteamId fromSteam64OrNull(
            @Nullable
            final String id64
    ) {
        return fromSteam64OrElse(id64, (@Nullable SteamId) null);
    }

    @UnknownNullability
    @Contract(value = "null, _ -> param2", pure = true)
    public static SteamId fromSteam2OrElse(
            @Nullable
            String id2,

            @Nullable
            final SteamId defaultValue
    ) {
        if (id2 == null) {
            return defaultValue;
        }

        id2 = id2.trim();

        final Pattern pattern = USteamPattern.getId2Pattern();
        final Matcher matcher = pattern.matcher(id2);

        if (!matcher.matches()) {
            return defaultValue;
        }

        try {
            final String mId = matcher.group(USteamRegexGroup.ID);
            final String mAuth = matcher.group(USteamRegexGroup.AUTH);

            final int id = Integer.parseUnsignedInt(mId);
            final int auth = Integer.parseUnsignedInt(mAuth);

            return new SteamId((id << 1) | auth);
        } catch (NumberFormatException ignored) {
        }

        return defaultValue;
    }

    @UnknownNullability
    @Contract(pure = false)
    public static SteamId fromSteam2OrElse(
            @Nullable
            final String id2,

            @Nullable
            final Supplier<@UnknownNullability SteamId> defaultValueSupplier
    ) {
        return UwObject.ifNull(fromSteam2OrNull(id2), defaultValueSupplier);
    }

    @UnknownNullability
    @Contract(value = "null, _ -> null", pure = false)
    public static SteamId fromSteam2OrElse(
            @Nullable
            final String id2,

            @Nullable
            final VoidSupplier<@UnknownNullability SteamId> defaultValueSupplier
    ) {
        return fromSteam2OrElse(id2, (@Nullable Supplier<@UnknownNullability SteamId>) defaultValueSupplier);
    }

    @UnknownNullability
    @Contract(value = "null -> null", pure = true)
    public static SteamId fromSteam2OrNull(
            @Nullable
            final String id2
    ) {
        return fromSteam2OrElse(id2, (@Nullable SteamId) null);
    }

    @UnknownNullability
    @Contract(value = "null, _ -> param2", pure = true)
    public static SteamId fromSteam3OrElse(
            @Nullable
            String id3,

            @Nullable
            final SteamId defaultValue
    ) {
        if (id3 == null) {
            return defaultValue;
        }

        id3 = id3.trim();

        final Pattern pattern = USteamPattern.getId3Pattern();
        final Matcher matcher = pattern.matcher(id3);

        if (!matcher.matches()) {
            return defaultValue;
        }

        try {
            final String mXuid = matcher.group(USteamRegexGroup.ID);
            final String mUniverse = matcher.group(USteamRegexGroup.UNIVERSE);
            final String mAccount = matcher.group(USteamRegexGroup.ACCOUNT);

            final int xuid = Integer.parseUnsignedInt(mXuid);
            final int universe = Integer.parseUnsignedInt(mUniverse);
            int instance = USteamInstance.DESKTOP;
            char account = mAccount.charAt(0);

            try {
                final String mInstance = matcher.group(USteamRegexGroup.INSTANCE);
                instance = Integer.parseUnsignedInt(mInstance);
            } catch (final NumberFormatException ignored) {
                switch (account) {
                    case USteamAccount.CLAN_CHAR:
                    case USteamAccount.CHAT_CHAR:
                    case USteamAccount.CLAN_CHAT_CHAR:
                    case USteamAccount.LOBBY_CHAT_CHAR:
                        instance = USteamInstance.ALL;
                        break;
                }
            }

            switch (account) {
                case USteamAccount.CLAN_CHAT_CHAR:
                case USteamAccount.LOBBY_CHAT_CHAR:
                    account = USteamAccount.CHAT_CHAR;
                    break;
            }

            return new SteamId(xuid, universe, instance, account);
        } catch (final NumberFormatException ignored) {
        }


        return defaultValue;
    }

    @UnknownNullability
    @Contract(pure = false)
    public static SteamId fromSteam3OrElse(
            @Nullable
            final String id3,

            @Nullable
            final Supplier<@UnknownNullability SteamId> defaultValueSupplier
    ) {
        return UwObject.ifNull(fromSteam3OrNull(id3), defaultValueSupplier);
    }

    @UnknownNullability
    @Contract(value = "null, _ -> null", pure = false)
    public static SteamId fromSteam3OrElse(
            @Nullable
            final String id3,

            @Nullable
            final VoidSupplier<@UnknownNullability SteamId> defaultValueSupplier
    ) {
        return fromSteam3OrElse(id3, (@Nullable Supplier<@UnknownNullability SteamId>) defaultValueSupplier);
    }

    @UnknownNullability
    @Contract(value = "null -> null", pure = true)
    public static SteamId fromSteam3OrNull(
            @Nullable
            final String id3
    ) {
        return fromSteam3OrElse(id3, (@Nullable SteamId) null);
    }

    @UnknownNullability
    @Contract(value = "null, _ -> param2", pure = true)
    public static SteamId fromInviteCodeOrElse(
            @Nullable
            final String code,

            @Nullable
            final SteamId defaultValue
    ) {
        return UwObject.ifNotNullNoCheck(USteamInvite.toXuidOrNull(code), SteamId::new, defaultValue);
    }

    @UnknownNullability
    @Contract(pure = false)
    public static SteamId fromInviteCodeOrElse(
            @Nullable
            final String code,

            @Nullable
            final Supplier<@UnknownNullability SteamId> defaultValueSupplier
    ) {
        return UwObject.ifNull(fromInviteCodeOrNull(code), defaultValueSupplier);
    }

    @UnknownNullability
    @Contract(value = "null, _ -> null", pure = false)
    public static SteamId fromInviteCodeOrElse(
            @Nullable
            final String code,

            @Nullable
            final VoidSupplier<@UnknownNullability SteamId> defaultValueSupplier
    ) {
        return fromInviteCodeOrElse(code, (@Nullable Supplier<@UnknownNullability SteamId>) defaultValueSupplier);
    }

    @UnknownNullability
    @Contract(value = "null -> null", pure = true)
    public static SteamId fromInviteCodeOrNull(
            @Nullable
            final String code
    ) {
        return fromInviteCodeOrElse(code, (@Nullable SteamId) null);
    }

    @UnknownNullability
    @Contract(value = "null, _ -> param2", pure = true)
    public static SteamId fromCsgoCodeOrElse(
            @Nullable
            final String code,

            @Nullable
            final SteamId defaultValue
    ) {
        return UwObject.ifNotNullNoCheck(USteamCsgo.toXuidOrNull(code), SteamId::new, defaultValue);
    }

    @UnknownNullability
    @Contract(pure = false)
    public static SteamId fromCsgoCodeOrElse(
            @Nullable
            final String code,

            @Nullable
            final Supplier<@UnknownNullability SteamId> defaultValueSupplier
    ) {
        return UwObject.ifNull(fromCsgoCodeOrNull(code), defaultValueSupplier);
    }

    @UnknownNullability
    @Contract(value = "null, _ -> null", pure = false)
    public static SteamId fromCsgoCodeOrElse(
            @Nullable
            final String code,

            @Nullable
            final VoidSupplier<@UnknownNullability SteamId> defaultValueSupplier
    ) {
        return fromCsgoCodeOrElse(code, (@Nullable Supplier<@UnknownNullability SteamId>) defaultValueSupplier);
    }

    @UnknownNullability
    @Contract(value = "null -> null", pure = true)
    public static SteamId fromCsgoCodeOrNull(
            @Nullable
            final String code
    ) {
        return fromCsgoCodeOrElse(code, (@Nullable SteamId) null);
    }

    @UnknownNullability
    @Contract(value = "null, _ -> param2", pure = true)
    public static SteamId fromSteamProfileUrlOrElse(
            @Nullable
            String url,

            @Nullable
            final SteamId defaultValue
    ) {
        if (url == null) {
            return defaultValue;
        }

        url = url.trim();

        final Pattern pattern = USteamPattern.getProfileUrlPattern();
        final Matcher matcher = pattern.matcher(url);

        if (!matcher.matches()) {
            return defaultValue;
        }

        final String mId = matcher.group(USteamRegexGroup.ID);
        try {
            final long id64 = Long.parseUnsignedLong(mId);
            return fromSteam64OrElse(id64, defaultValue);
        } catch (final NumberFormatException ignored) {
        }

        return fromSteam3OrElse(mId, defaultValue);
    }

    @UnknownNullability
    @Contract(pure = false)
    public static SteamId fromSteamProfileUrlOrElse(
            @Nullable
            final String url,

            @Nullable
            final Supplier<@UnknownNullability SteamId> defaultValueSupplier
    ) {
        return UwObject.ifNull(fromSteamProfileUrlOrNull(url), defaultValueSupplier);
    }

    @UnknownNullability
    @Contract(value = "null, _ -> null", pure = false)
    public static SteamId fromSteamProfileUrlOrElse(
            @Nullable
            final String url,

            @Nullable
            final VoidSupplier<@UnknownNullability SteamId> defaultValueSupplier
    ) {
        return fromSteamProfileUrlOrElse(url, (@Nullable Supplier<@UnknownNullability SteamId>) defaultValueSupplier);
    }

    @UnknownNullability
    @Contract(value = "null -> null", pure = false)
    public static SteamId fromSteamProfileUrlOrNull(
            @Nullable
            final String url
    ) {
        return fromSteamProfileUrlOrElse(url, (@Nullable SteamId) null);
    }

    @UnknownNullability
    @Contract(value = "null, _ -> param2", pure = true)
    public static SteamId fromSteamUserUrlOrElse(
            @Nullable
            String url,

            @Nullable
            final SteamId defaultValue
    ) {
        if (url == null) {
            return defaultValue;
        }

        url = url.trim();

        final Pattern pattern = USteamPattern.getUserUrlPattern();
        final Matcher matcher = pattern.matcher(url);

        if (!matcher.matches()) {
            return defaultValue;
        }

        final String code = matcher.group(USteamRegexGroup.ID);
        return fromInviteCodeOrElse(code, defaultValue);
    }

    @UnknownNullability
    @Contract(pure = false)
    public static SteamId fromSteamUserUrlOrElse(
            @Nullable
            final String url,

            @Nullable
            final Supplier<@UnknownNullability SteamId> defaultValueSupplier
    ) {
        return UwObject.ifNull(fromSteamUserUrlOrNull(url), defaultValueSupplier);
    }

    @UnknownNullability
    @Contract(value = "null, _ -> null", pure = false)
    public static SteamId fromSteamUserUrlOrElse(
            @Nullable
            final String url,

            @Nullable
            final VoidSupplier<@UnknownNullability SteamId> defaultValueSupplier
    ) {
        return fromSteamUserUrlOrElse(url, (@Nullable Supplier<@UnknownNullability SteamId>) defaultValueSupplier);
    }

    @UnknownNullability
    @Contract(value = "null -> null", pure = true)
    public static SteamId fromSteamUserUrlOrNull(
            @Nullable
            final String url
    ) {
        return fromSteamUserUrlOrElse(url, (@Nullable SteamId) null);
    }

    @UnknownNullability
    @Contract(value = "null, _ -> param2", pure = true)
    public static SteamId fromSteamUrlOrElse(
            @Nullable
            final String url,

            @Nullable
            final SteamId defaultValue
    ) {
        final SteamId result = fromSteamProfileUrlOrNull(url);
        if (result != null) {
            return result;
        }

        return fromSteamUserUrlOrElse(url, defaultValue);
    }

    @UnknownNullability
    @Contract(pure = false)
    public static SteamId fromSteamUrlOrElse(
            @Nullable
            final String url,

            @Nullable
            final Supplier<@UnknownNullability SteamId> defaultValueSupplier
    ) {
        return UwObject.ifNull(fromSteamUrlOrNull(url), defaultValueSupplier);
    }

    @UnknownNullability
    @Contract(value = "null, _ -> null", pure = false)
    public static SteamId fromSteamUrlOrElse(
            @Nullable
            final String url,

            @Nullable
            final VoidSupplier<@UnknownNullability SteamId> defaultValueSupplier
    ) {
        return fromSteamUrlOrElse(url, (@Nullable Supplier<@UnknownNullability SteamId>) defaultValueSupplier);
    }

    @UnknownNullability
    @Contract(value = "null -> null", pure = true)
    public static SteamId fromSteamUrlOrNull(
            @Nullable
            final String url
    ) {
        return fromSteamUrlOrElse(url, (@Nullable SteamId) null);
    }

    @UnknownNullability
    @Contract(value = "null, _ -> param2", pure = true)
    public static SteamId fromSteamAnyOrElse(
            @Nullable
            final Object obj,

            @Nullable
            final SteamId defaultValue
    ) {
        if (obj == null) {
            return defaultValue;
        }

        if (obj instanceof Integer) {
            return fromSteamXuidOrElse((@Nullable Integer) obj, defaultValue);
        }

        if (obj instanceof Long) {
            return fromSteam64OrElse((@Nullable Long) obj, defaultValue);
        }

        if (obj instanceof String) {
            final String str = (String) obj;

            SteamId result = fromSteamXuidOrNull(str);
            if (result != null) {
                return result;
            }

            result = fromSteam64OrNull(str);
            if (result != null) {
                return result;
            }

            result = fromSteam2OrNull(str);
            if (result != null) {
                return result;
            }

            result = fromSteam3OrNull(str);
            if (result != null) {
                return result;
            }

            result = fromInviteCodeOrNull(str);
            if (result != null) {
                return result;
            }

            result = fromCsgoCodeOrNull(str);
            if (result != null) {
                return result;
            }

            result = fromSteamUrlOrNull(str);
            if (result != null) {
                return result;
            }
        }

        return defaultValue;
    }

    @UnknownNullability
    @Contract(pure = false)
    public static SteamId fromSteamAnyOrElse(
            @Nullable
            final Object obj,

            @Nullable
            final Supplier<@UnknownNullability SteamId> defaultValueSupplier
    ) {
        return UwObject.ifNull(fromSteamAnyOrNull(obj), defaultValueSupplier);
    }

    @UnknownNullability
    @Contract(value = "null, _ -> null", pure = false)
    public static SteamId fromSteamAnyOrElse(
            @Nullable
            final Object obj,

            @Nullable
            final VoidSupplier<@UnknownNullability SteamId> defaultValueSupplier
    ) {
        return fromSteamAnyOrElse(obj, (@Nullable Supplier<@UnknownNullability SteamId>) defaultValueSupplier);
    }

    @UnknownNullability
    @Contract(value = "null -> null", pure = true)
    public static SteamId fromSteamAnyOrNull(
            @Nullable
            final Object obj
    ) {
        return fromSteamAnyOrElse(obj, (@Nullable SteamId) null);
    }

    public static final class Builder implements Serializable, Cloneable {
        @NotNull
        private static final String SIMPLE_NAME = SteamId.class.getSimpleName()
                + ":" + Builder.class.getSimpleName();

        @NotNull
        private static final String ATTRIBUTE_NAME_XUID = "xuid";

        @NotNull
        private static final String ATTRIBUTE_NAME_E_UNIVERSE = "eUniverse";

        @NotNull
        private static final String ATTRIBUTE_NAME_E_INSTANCE = "eInstance";

        @NotNull
        private static final String ATTRIBUTE_NAME_E_ACCOUNT = "eAccount";

        @NotNull
        private static final String ATTRIBUTE_NAME_I_UNIVERSE = "iUniverse";

        @NotNull
        private static final String ATTRIBUTE_NAME_I_INSTANCE = "iInstance";

        @NotNull
        private static final String ATTRIBUTE_NAME_I_ACCOUNT = "iAccount";

        @NotNull
        private static final String ATTRIBUTE_NAME_C_ACCOUNT = "cAccount";

        @UnknownNullability
        private Integer xuid;

        @UnknownNullability
        private ESteamUniverse eUniverse;

        @UnknownNullability
        private ESteamInstance eInstance;

        @UnknownNullability
        private ESteamAccount eAccount;

        @UnknownNullability
        private Integer iUniverse;

        @UnknownNullability
        private Integer iInstance;

        @UnknownNullability
        private Integer iAccount;

        @UnknownNullability
        private Character cAccount;

        @Contract(pure = true)
        public Builder() {
            this.xuid = null;
            this.eUniverse = null;
            this.eInstance = null;
            this.eAccount = null;
            this.iUniverse = null;
            this.iInstance = null;
            this.iAccount = null;
            this.cAccount = null;
        }

        @Contract(value = "null -> fail", pure = true)
        private Builder(
                @UnknownNullability final Builder that
        ) {
            this.xuid = that.xuid;
            this.eUniverse = that.eUniverse;
            this.eInstance = that.eInstance;
            this.eAccount = that.eAccount;
            this.iUniverse = that.iUniverse;
            this.iInstance = that.iInstance;
            this.iAccount = that.iAccount;
            this.cAccount = that.cAccount;
        }

        @NotNull
        @Contract(value = "-> new", pure = true)
        public SteamId build() {
            final ESteamUniverse universe = ESteamUniverse.fromIdOrElse(this.iUniverse, this.eUniverse);
            final ESteamInstance instance = ESteamInstance.fromIdOrElse(this.iInstance, this.eInstance);

            ESteamAccount account = ESteamAccount.fromIdOrElse(this.iAccount, this.eAccount);
            account = ESteamAccount.fromCharOrElse(this.cAccount, account);

            return new SteamId(this.xuid, universe, instance, account);
        }

        @Contract(pure = true)
        public boolean hasXuid() {
            return this.xuid != null;
        }

        @Contract(pure = true)
        public boolean hasUniverseType() {
            return this.eUniverse != null;
        }

        @Contract(pure = true)
        public boolean hasInstanceType() {
            return this.eInstance != null;
        }

        @Contract(pure = true)
        public boolean hasAccountType() {
            return this.eAccount != null;
        }

        @Contract(pure = true)
        public boolean hasIntUniverseType() {
            return this.iUniverse != null;
        }

        @Contract(pure = true)
        public boolean hasIntInstanceType() {
            return this.iInstance != null;
        }

        @Contract(pure = true)
        public boolean hasIntAccountType() {
            return this.iAccount != null;
        }

        @Contract(pure = true)
        public boolean hasCharAccountType() {
            return this.cAccount != null;
        }

        @UnknownNullability
        @Contract(pure = true)
        public Integer getXuid() {
            return this.xuid;
        }

        @UnknownNullability
        @Contract(pure = true)
        public ESteamUniverse getUniverseType() {
            return this.eUniverse;
        }

        @UnknownNullability
        @Contract(pure = true)
        public ESteamInstance getInstanceType() {
            return this.eInstance;
        }

        @UnknownNullability
        @Contract(pure = true)
        public ESteamAccount getAccountType() {
            return this.eAccount;
        }

        @UnknownNullability
        @Contract(pure = true)
        public Integer getIntUniverseType() {
            return this.iUniverse;
        }

        @UnknownNullability
        @Contract(pure = true)
        public Integer getIntInstanceType() {
            return this.iInstance;
        }

        @UnknownNullability
        @Contract(pure = true)
        public Integer getIntAccountType() {
            return iAccount;
        }

        @UnknownNullability
        @Contract(pure = true)
        public Character getCharAccountType() {
            return this.cAccount;
        }

        @NotNull
        @Contract(value = "_ -> this", pure = false)
        public Builder setXuid(
                @Nullable
                final Integer xuid
        ) {
            this.xuid = xuid;
            return this;
        }

        @NotNull
        @Contract(value = "_ -> this", pure = false)
        public Builder setUniverseType(
                @Nullable
                final ESteamUniverse universe
        ) {
            this.eUniverse = universe;
            this.iUniverse = null;
            return this;
        }

        @NotNull
        @Contract(value = "_ -> this", pure = false)
        public Builder setUniverseType(
                @Nullable
                final Integer universe
        ) {
            this.eUniverse = null;
            this.iUniverse = universe;
            return this;
        }

        @NotNull
        @Contract(value = "_ -> this", pure = false)
        public Builder setInstanceType(
                @Nullable
                final ESteamInstance instance
        ) {
            this.eInstance = instance;
            this.iInstance = null;
            return this;
        }

        @NotNull
        @Contract(value = "_ -> this", pure = false)
        public Builder setInstanceType(
                @Nullable
                final Integer instance
        ) {
            this.eInstance = null;
            this.iInstance = instance;
            return this;
        }

        @NotNull
        @Contract(value = "_ -> this", pure = false)
        public Builder setAccountType(
                @Nullable
                final ESteamAccount account
        ) {
            this.eAccount = account;
            this.iAccount = null;
            this.cAccount = null;
            return this;
        }

        @NotNull
        @Contract(value = "_ -> this", pure = false)
        public Builder setAccountType(
                @Nullable
                final Integer account
        ) {
            this.eAccount = null;
            this.iAccount = account;
            this.cAccount = null;
            return this;
        }

        @NotNull
        @Contract(value = "_ -> this", pure = false)
        public Builder setAccountType(
                @Nullable
                final Character account
        ) {
            this.eAccount = null;
            this.iAccount = null;
            this.cAccount = account;
            return this;
        }

        @NotNull
        @Contract(pure = true)
        public Builder withXuid(
                @Nullable
                final Integer xuid
        ) {
            if (Objects.equals(this.xuid, xuid)) {
                return this;
            }

            return this.clone()
                    .setXuid(xuid);
        }

        @NotNull
        @Contract(pure = true)
        public Builder withUniverseType(
                @Nullable
                final ESteamUniverse universe
        ) {
            if (this.eUniverse == universe) {
                return this;
            }

            return this.clone()
                    .setUniverseType(universe);
        }

        @NotNull
        @Contract(pure = true)
        public Builder withUniverseType(
                @Nullable
                final Integer universe
        ) {
            if (Objects.equals(this.iUniverse, universe)) {
                return this;
            }

            return this.clone()
                    .setUniverseType(universe);
        }

        @NotNull
        @Contract(pure = true)
        public Builder withInstanceType(
                @Nullable
                final ESteamInstance instance
        ) {
            if (this.eInstance == instance) {
                return this;
            }

            return this.clone()
                    .setInstanceType(instance);
        }

        @NotNull
        @Contract(pure = true)
        public Builder withInstanceType(
                @Nullable
                final Integer instance
        ) {
            if (Objects.equals(this.iInstance, instance)) {
                return this;
            }

            return this.clone()
                    .setInstanceType(instance);
        }

        @NotNull
        @Contract(pure = true)
        public Builder withAccountType(
                @Nullable
                final ESteamAccount account
        ) {
            if (this.eAccount == account) {
                return this;
            }

            return this.clone()
                    .setAccountType(account);
        }

        @NotNull
        @Contract(pure = true)
        public Builder withAccountType(
                @Nullable
                final Integer account
        ) {
            if (Objects.equals(this.iAccount, account)) {
                return this;
            }

            return this.clone()
                    .setAccountType(account);
        }

        @NotNull
        @Contract(pure = true)
        public Builder withAccountType(
                @Nullable
                final Character account
        ) {
            if (Objects.equals(this.cAccount, account)) {
                return this;
            }

            return this.clone()
                    .setAccountType(account);
        }

        @Override
        public int hashCode() {
            return Objects.hash(
                    this.xuid,
                    this.eUniverse,
                    this.eInstance,
                    this.eAccount,
                    this.iUniverse,
                    this.iInstance,
                    this.iAccount,
                    this.cAccount
            );
        }

        @Override
        @Contract(pure = true)
        public boolean equals(
                @Nullable
                final Object obj
        ) {
            if (this == obj) {
                return true;
            }

            if (obj == null || this.getClass() != obj.getClass()) {
                return false;
            }

            Builder that = (Builder) obj;
            return Objects.equals(this.xuid, that.xuid)
                    && this.eUniverse == that.eUniverse
                    && this.eInstance == that.eInstance
                    && this.eAccount == that.eAccount
                    && Objects.equals(this.iUniverse, that.iUniverse)
                    && Objects.equals(this.iInstance, that.iInstance)
                    && Objects.equals(this.iAccount, that.iAccount)
                    && Objects.equals(this.cAccount, that.cAccount);
        }

        @Override
        @NotNull
        @Contract(pure = true)
        public String toString() {
            return SIMPLE_NAME + "["
                    + ATTRIBUTE_NAME_XUID
                    + "="
                    + this.xuid
                    + ", "
                    + ATTRIBUTE_NAME_E_UNIVERSE
                    + "="
                    + this.eUniverse
                    + ", "
                    + ATTRIBUTE_NAME_E_INSTANCE
                    + "="
                    + this.eInstance
                    + ", "
                    + ATTRIBUTE_NAME_E_ACCOUNT
                    + "="
                    + this.eAccount
                    + ", "
                    + ATTRIBUTE_NAME_I_UNIVERSE
                    + "="
                    + this.iUniverse
                    + ", "
                    + ATTRIBUTE_NAME_I_INSTANCE
                    + "="
                    + this.iInstance
                    + ", "
                    + ATTRIBUTE_NAME_I_ACCOUNT
                    + "="
                    + this.iAccount
                    + ", "
                    + ATTRIBUTE_NAME_C_ACCOUNT
                    + "="
                    + this.cAccount
                    + "]";
        }

        @Override
        @NotNull
        @Contract(value = "-> new", pure = true)
        public Builder clone() {
            return new Builder(this);
        }
    }
}
