package com.quinn.githubknife.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.quinn.githubknife.R;
import com.quinn.githubknife.utils.L;
import com.quinn.githubknife.utils.UIUtils;

/**
 * Created by Quinn on 9/21/15.
 */
public class UserLabel extends View{

    private final static String TAG = UserLabel.class.getSimpleName();


    //两个画笔
    private Paint namePaint;
    private Paint valuePaint;
    private Rect nameRect;
    private Rect valueRect;

    private String label_name;
    private String label_value;


    public UserLabel(Context context) {
        this(context, null);
    }

    public UserLabel(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UserLabel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.user_verical_label);
        label_name = a.getString(R.styleable.user_verical_label_name);
        label_value = a.getString(R.styleable.user_verical_label_value);

        a.recycle();

        nameRect = new Rect();
        namePaint = new Paint();
        namePaint.setTextSize(UIUtils.sp2px(this.getContext(), 15));
        namePaint.setColor(getResources().getColor(R.color.theme_color));

        valueRect = new Rect();
        valuePaint = new Paint();
        valuePaint.setTextSize(UIUtils.sp2px(this.getContext(), 25));
        valuePaint.setColor(getResources().getColor(R.color.color_accent));

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        L.i(TAG, "valueRect.width() = " + valueRect.width());



        namePaint.getTextBounds(label_name, 0, label_name.length(), nameRect);

        valuePaint.getTextBounds(label_value, 0, label_value.length(), valueRect);



        int valueX = getMeasuredWidth() / 2 - valueRect.width() / 2;
        int valueY = getMeasuredWidth() / 2 - (valueRect.height() + nameRect.height())/2;

        int nameX = getMeasuredWidth() / 2 - nameRect.width() / 2;
        int nameY = valueY + nameRect.height()+ UIUtils.sp2px(this.getContext(), 3);


        canvas.drawText(label_value, valueX, valueY, valuePaint);
        canvas.drawText(label_name, nameX, nameY, namePaint);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = measureDimension(200, widthMeasureSpec);
        int height = measureDimension(200, heightMeasureSpec);
        setMeasuredDimension(width, height);



    }

    public int measureDimension(int defaultSize, int measureSpec){
        int result;

        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if(specMode == MeasureSpec.EXACTLY){
            result = specSize;
        }else{
            result = defaultSize;   //UNSPECIFIED
            if(specMode == MeasureSpec.AT_MOST){
                result = Math.min(result, specSize);
            }
        }
        return result;
    }


    public void setName(String name){
        this.label_name = name;
        invalidate();
    }

    public void setValue(String value){
        this.label_value = value;
        invalidate();
    }


}
