/**
 * Copyright (C) 2015 ogaclejapan
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.ducph.musicplayer.ui.smartTab;

public abstract class PagerItem {

    protected static final float DEFAULT_WIDTH = 1.f;
    protected static final int DEFAULT_SIZE = 15;

    private final CharSequence mTitle;
    private final float mWidth;
    private final int size;

    protected PagerItem(CharSequence title, float width, int size) {
        mTitle = title;
        mWidth = width;
        this.size=size;
    }

    public CharSequence getTitle() {
        return mTitle;
    }
    public int getSize(){
        return this.size;
    }
    public float getWidth() {
        return mWidth;
    }

}
