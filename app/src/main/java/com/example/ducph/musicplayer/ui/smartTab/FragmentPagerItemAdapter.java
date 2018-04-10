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

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.util.SparseArrayCompat;
import android.view.ViewGroup;


import java.lang.ref.WeakReference;

public class FragmentPagerItemAdapter extends FragmentStatePagerAdapter {
    private final FragmentPagerItems mPages;
    private final SparseArrayCompat<WeakReference<Fragment>> mHolder;
    private Handler mHandler = new Handler();
    private Fragment mCurrentFragment;

    public FragmentPagerItemAdapter(FragmentManager fm, FragmentPagerItems pages) {
        super(fm);
        mPages = pages;
        mHolder = new SparseArrayCompat<>(pages.size());
    }

    @Override
    public int getCount() {
        return mPages.size();
    }

    @Override
    public Fragment getItem(int position) {
        return getPagerItem(position).instantiate(mPages.getContext(), position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object item = super.instantiateItem(container, position);
        if (item instanceof Fragment) {
            mHolder.put(position, new WeakReference<Fragment>((Fragment) item));
        }
        return item;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        mHolder.remove(position);
        super.destroyItem(container, position, object);
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position,
                               Object object) {

        if (mCurrentFragment != object) {
            mCurrentFragment = getPage(position);

            if (mCurrentFragment != null) {
                if (mCurrentFragment instanceof CustomTabFragment) {
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ((CustomTabFragment) mCurrentFragment).onLoad();
                        }
                    },300);
                }
            }
        }

        super.setPrimaryItem(container, position, object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return getPagerItem(position).getTitle();
    }
    public int getSize(int position){
        return getPagerItem(position).getSize();
    }
    @Override
    public float getPageWidth(int position) {
        return super.getPageWidth(position);
    }

    public Fragment getPage(int position) {
        final WeakReference<Fragment> weakRefItem = mHolder.get(position);
        return (weakRefItem != null) ? weakRefItem.get() : null;
    }

    protected FragmentPagerItem getPagerItem(int position) {
        return mPages.get(position);
    }

}
