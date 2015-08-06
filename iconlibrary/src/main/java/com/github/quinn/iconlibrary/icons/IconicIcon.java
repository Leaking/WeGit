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

import com.github.quinn.iconlibrary.utils.TypefaceManager.IconicTypeface;

/**
 * A wrapper for Iconic icon font (http://somerandomdude.com/work/iconic/). 
 */
public enum IconicIcon implements Icon {

    SEARCH(0x1F50E),
    MAIL(0x2709),
    HEART(0x2665),
    HEART_EMPTY(0x2661),
    STAR(0x2605),
    USER(0x1F464),
    VIDEO(0x1F3AC),
    PICTURE(0x1F304),
    CAMERA(0x1F4F7),
    OK(0x2713),
    OK_CIRCLE(0x2714),
    CANCEL(0x2715),
    CANCEL_CIRCLE(0x2716),
    PLUS(0x2B),
    PLUS_CIRCLE(0x2795),
    MINUS(0x2D),
    MINUS_CIRCLE(0x2796),
    HELP(0x2753),
    INFO(0x2139),
    HOME(0x2302),
    LINK(0x1F517),
    ATTACH(0x1F4CE),
    LOCK(0x1F512),
    LOCK_EMPTY(0xE708),
    LOCK_OPEN(0x1F513),
    LOCK_OPEN_EMPTY(0xE709),
    PIN(0x1F4CC),
    EYE(0xE70A),
    TAG(0xE70C),
    TAG_EMPTY(0xE70E),
    DOWNLOAD(0x1F4E5),
    UPLOAD(0x1F4E4),
    DOWNLOAD_CLOUD(0xE710),
    UPLOAD_CLOUD(0xE711),
    QUOTE_LEFT(0x275D),
    QUOTE_RIGHT(0x275E),
    QUOTE_LEFT_ALT(0x275B),
    QUOTE_RIGHT_ALT(0x275C),
    PENCIL(0x270E),
    PENCIL_NEG(0x270F),
    PENCIL_ALT(0x2710),
    UNDO(0x21B6),
    COMMENT(0xE718),
    COMMENT_INV(0xE719),
    COMMENT_ALT(0xE71A),
    COMMENT_INV_ALT(0xE71B),
    COMMENT_ALT2(0xE71C),
    COMMENT_INV_ALT2(0xE71D),
    CHAT(0xE720),
    CHAT_INV(0xE721),
    LOCATION(0xE724),
    LOCATION_INV(0xE725),
    LOCATION_ALT(0xE726),
    COMPASS(0xE728),
    TRASH(0xE729),
    TRASH_EMPTY(0xE72A),
    DOC(0xE730),
    DOC_INV(0xE731),
    DOC_ALT(0xE732),
    DOC_INV_ALT(0xE733),
    ARTICLE(0xE734),
    ARTICLE_ALT(0xE735),
    BOOK_OPEN(0x1F4D6),
    FOLDER(0x1F4C1),
    FOLDER_EMPTY(0x1F4C2),
    BOX(0x1F4E6),
    RSS(0xE73A),
    RSS_ALT(0xE73B),
    COG(0x2699),
    WRENCH(0x1F527),
    SHARE(0xE73C),
    CALENDAR(0x1F4C5),
    CALENDAR_INV(0xE73E),
    CALENDAR_ALT(0x1F4C6),
    MIC(0x1F3A4),
    VOLUME_OFF(0x1F507),
    VOLUME_UP(0x1F50A),
    HEADPHONES(0x1F3A7),
    CLOCK(0x1F554),
    LAMP(0x1F4A1),
    BLOCK(0x1F6AB),
    RESIZE_FULL(0xE744),
    RESIZE_FULL_ALT(0xE745),
    RESIZE_SMALL(0xE746),
    RESIZE_SMALL_ALT(0xE747),
    RESIZE_VERTICAL(0x2B0C),
    RESIZE_HORIZONTAL(0x2B0D),
    MOVE(0xE74A),
    POPUP(0xE74C),
    DOWN(0x2193),
    LEFT(0x2190),
    RIGHT(0x2192),
    UP(0x2191),
    DOWN_CIRCLE(0xE4A4),
    LEFT_CIRCLE(0xE4A1),
    RIGHT_CIRCLE(0xE4A2),
    UP_CIRCLE(0xE4A3),
    CW(0x27F3),
    LOOP(0x1F504),
    LOOP_ALT(0x1F501),
    EXCHANGE(0x21C4),
    SPLIT(0x2387),
    ARROW_CURVED(0x2935),
    PLAY(0x25B6),
    PLAY_CIRCLE2(0xE048),
    STOP(0x25AA),
    PAUSE(0x2389),
    TO_START(0x23EE),
    TO_END(0x23ED),
    EJECT(0x23CF),
    TARGET(0x1F3AF),
    SIGNAL(0x1F4F6),
    AWARD(0x1F3C9),
    AWARD_EMPTY(0xE764),
    LIST(0xE765),
    LIST_NESTED(0xE766),
    BAT_EMPTY(0xE772),
    BAT_HALF(0xE773),
    BAT_FULL(0xE774),
    BAT_CHARGE(0xE775),
    MOBILE(0x1F4F1),
    CD(0x1F4BF),
    EQUALIZER(0xE795),
    CURSOR(0xE796),
    APERTURE(0xE797),
    APERTURE_ALT(0xE798),
    STEERING_WHEEL(0xE799),
    BOOK(0x1F4D5),
    BOOK_ALT(0x1F4D4),
    BRUSH(0xE79A),
    BRUSH_ALT(0xE79B),
    EYEDROPPER(0xE79C),
    LAYERS(0xE79D),
    LAYERS_ALT(0xE79E),
    SUN(0x263C),
    SUN_INV(0x2600),
    CLOUD(0x2601),
    RAIN(0x26C6),
    FLASH(0x26A1),
    MOON(0x263E),
    MOON_INV(0xE7A0),
    UMBRELLA(0x2602),
    CHART_BAR(0x1F4CA),
    CHART_PIE(0xE7A2),
    CHART_PIE_ALT(0xE7A3),
    KEY(0x26BF),
    KEY_INV(0x1F511),
    HASH(0x23),
    AT(0x40),
    PILCROW(0xB6),
    DIAL(0xE7A4);
    
    private final int mIconUtfValue;

    private IconicIcon(int iconUtfValue) {
        mIconUtfValue = iconUtfValue;
    }

    @Override
    public IconicTypeface getIconicTypeface() {
        return IconicTypeface.ICONIC;
    }

    @Override
    public int getIconUtfValue() {
        return mIconUtfValue;
    }
}
