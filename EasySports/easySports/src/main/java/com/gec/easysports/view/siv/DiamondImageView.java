package com.gec.easysports.view.siv;

import android.content.Context;
import android.util.AttributeSet;

import com.gec.easysports.R;
import com.gec.easysports.view.siv.shader.ShaderHelper;
import com.gec.easysports.view.siv.shader.SvgShader;

public class DiamondImageView extends ShaderImageView {

    public DiamondImageView(Context context) {
        super(context);
    }

    public DiamondImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DiamondImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public ShaderHelper createImageViewHelper() {
        return new SvgShader(R.raw.imgview_diamond);
    }
}
