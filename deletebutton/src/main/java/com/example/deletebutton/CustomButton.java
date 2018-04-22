package com.example.deletebutton;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * TODO: document your custom view class.
 */
public class CustomButton extends LinearLayout implements View.OnClickListener{
    private Button delete;
    private OnDeleteButtonClickedListener mCallback = null;

    public interface OnDeleteButtonClickedListener{
        public void onDeleteButtonClicked();
    }
    public void setOnDeleteButtonClickedListener(OnDeleteButtonClickedListener listener){
        mCallback=listener;
    }
    public CustomButton(Context context) {
        super(context);
        init(null, 0);
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        LayoutInflater inflater =(LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.sample_custom_button,this);

        delete = (Button)findViewById(R.id.button);

        //Load attributes
        TypedArray a = getContext().obtainStyledAttributes(
                attrs,R.styleable.CustomButton,defStyle,0);
        a.recycle();

        delete.setOnClickListener(this);


    }
    public void setTextButton(String text){
        delete.setText(text);
        invalidate();
        requestLayout();
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button){
            if(mCallback!=null){
                mCallback.onDeleteButtonClicked();
            }

        }
    }
}
