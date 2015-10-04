package com.github.quinn.iconlibrary.icons;

import com.github.quinn.iconlibrary.utils.TypefaceManager.IconicTypeface;

public enum OctIcon implements Icon {

	
	
	STAR(0xf02a),
    FORK(0xf002),
    FILE(0xf011),
	FOLDER(0xf016),
	SEARCH(0xf02e),
	BRANCH(0xf020),
	EMAIL(0xf03b),
	LOCATE(0xf060),
	COMPANY(0xf037),
	BLOG(0xf05C),
	JOIN(0xf046),
	REPO(0xf001),
	PUSH(0xf01f),
	COMMIT(0xf07e),
	CODE(0xf05f),
	ISSUE(0xf026),
	TAG(0xf015),
	PERSON(0xf018),
	ISSUE_CLOSE(0xf028),
	ISSUE_OPNE(0xf026);



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
