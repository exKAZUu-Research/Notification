package com.example.notification.function;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.Html.ImageGetter;

public class ImageGetterImpl implements ImageGetter{
	Context mContext;
	
	public ImageGetterImpl(Context context){
		mContext = context;
	}
	
	@Override
	public Drawable getDrawable(String source){
		Resources res = mContext.getResources();
		int id = res.getIdentifier(source, "drawable", mContext.getPackageName());
		Drawable d = res.getDrawable(id);
		
		int w = d.getIntrinsicWidth();
		int h = d.getIntrinsicHeight();
		if(source.equals("ic_launcher")){
			w = (int)(w*0.58f);
			h = (int)(h*0.58f);
		}
		d.setBounds(0, 0, w, h);
		
		return d;
	}
}
