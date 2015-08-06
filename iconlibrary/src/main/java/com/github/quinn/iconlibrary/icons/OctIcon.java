package com.github.quinn.iconlibrary.icons;

import com.github.quinn.iconlibrary.utils.TypefaceManager.IconicTypeface;

public enum OctIcon implements Icon {

	
	
	STAR(0xf096),
    FORK(0xf002);

	
	private final int mIconUtfValue;

    private OctIcon(int iconUtfValue) {
        mIconUtfValue = iconUtfValue;
    }
    
	@Override
	public IconicTypeface getIconicTypeface() {
        return IconicTypeface.OCTICON;
	}

	@Override
	public int getIconUtfValue() {
		// TODO Auto-generated method stub
		return mIconUtfValue;
	}

}
