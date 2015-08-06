/*
 * Copyright (C) 2013 Artur Termenji
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.quinn.iconlibrary.icons;


import com.github.quinn.iconlibrary.utils.TypefaceManager;

/**
 * A wrapper for Entypo-Social icon font (http://www.entypo.com). 
 */
public enum EntypoSocialIcon implements Icon {

    GITHUB(0xF300),
    C_GITHUB(0xF301),
    FLICKR(0xF303),
    C_FLICKR(0xF304),
    VIMEO(0xF306),
    C_VIMEO(0xF307),
    TWITTER(0xF309),
    C_TWITTER(0xF30A),
    FACEBOOK(0xF30C),
    C_FACEBOOK(0xF30D),
    S_FACEBOOK(0xF30E),
    GOOGLE_PLUS(0xF30F),
    C_GOOGLE_PLUS(0xF310),
    PINTEREST(0xF312),
    C_PINTEREST(0xF313),
    TUMBLR(0xF315),
    C_TUMBLR(0xF316),
    LINKEDIN(0xF318),
    C_LINKEDIN(0xF319),
    DRIBBBLE(0xF31B),
    C_DRIBBBLE(0xF31C),
    STUMBLEUPON(0xF31E),
    C_STUMBLEUPON(0xF31F),
    LASTFM(0xF321),
    C_LASTFM(0xF322),
    RDIO(0xF324),
    C_RDIO(0xF325),
    SPOTIFY(0xF327),
    C_SPOTIFY(0xF328),
    QQ(0xF32A),
    INSTAGRAM(0xF32D),
    DROPBOX(0xF330),
    EVERNOTE(0xF333),
    FLATTR(0xF336),
    SKYPE(0xF339),
    C_SKYPE(0xF33A),
    RENREN(0xF33C),
    SINA_WEIBO(0xF33F),
    PAYPAL(0xF342),
    PICASA(0xF345),
    SOUNDCLOUD(0xF348),
    MIXI(0xF34B),
    BEHANCE(0xF34E),
    GOOGLE_CIRCLES(0xF351),
    VK(0xF354),
    SMASHING(0xF357);
    
    private final int mIconUtfValue;

    private EntypoSocialIcon(int iconUtfValue) {
        mIconUtfValue = iconUtfValue;
    }

    @Override
    public TypefaceManager.IconicTypeface getIconicTypeface() {
        return TypefaceManager.IconicTypeface.ENTYPO_SOCIAL;
    }

    @Override
    public int getIconUtfValue() {
        return mIconUtfValue;
    }
}
