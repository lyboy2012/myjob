/**
 * Project Name:family
 * File Name:Sidebar.java
 * Package Name:com.ly.family.home.relative
 * Date:2013-7-22下午3:00:46
 * Copyright (c) 2013, ly.boy2012@gmail.com All Rights Reserved.
 *
 */

package cn.weeon.job.common;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import cn.weeon.job.Adapter.ContactAdapter;


/**
 * ClassName:Sidebar Reason: TODO ADD REASON. Date: 2013-7-22 下午3:00:46
 * 
 * @author liying
 * @version 1.0
 * @since JDK 1.6
 * @see
 */
public class Sidebar extends Button {

	private TextView mDialogLetter;
	private ListView mListView;
	public void setListView(ListView mListView) {
		this.mListView = mListView;
	}
	public TextView getmDialogLetter() {
		return mDialogLetter;
	}
	public void setmDialogLetter(TextView mDialogLetter) {
		this.mDialogLetter = mDialogLetter;
	}
	

	public  static final char[] letter = {'*','@','#', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
			'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
			'X', 'Y', 'Z' };
	private final int m_nItemHeight = 32;  
	private ContactAdapter mContactAdapter = null;
	 public Sidebar(Context context) {  
	        super(context);  
	 }  
	 
    public Sidebar(Context context, AttributeSet attrs) {  
        super(context, attrs);  
    }  
    public void setContactAdapter(ContactAdapter contactAdapter){
    	this.mContactAdapter = contactAdapter;
    }

    
    public boolean onTouchEvent(MotionEvent event) {  
        super.onTouchEvent(event);  
        int i = (int) event.getY();  
        int idx = i / m_nItemHeight;  
        if (idx >= letter.length) {  
            idx = letter.length - 1;  
        } else if (idx < 0) {  
            idx = 0;  
        }  
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {  
    	   mDialogLetter.setVisibility(View.VISIBLE);
    	   mDialogLetter.setText(""+letter[idx]);
            int position = mContactAdapter.getPositionForSection(letter[idx]);  
            if (position == -1) {  
                return true;  
            }  
            mListView.setSelection(position);  
        }else{
        	 mDialogLetter.setVisibility(View.INVISIBLE);
        }
        return true;  
    }  
    
    protected void onDraw(Canvas canvas) {  
        Paint paint = new Paint();  
        paint.setColor(0xFF423009);  
        paint.setTextSize(30);  
        paint.setTextAlign(Paint.Align.CENTER);  
        float widthCenter = getMeasuredWidth() / 2;  
        for (int i = 0; i < letter.length; i++) {  
            canvas.drawText(String.valueOf(letter[i]), widthCenter, m_nItemHeight + (i * m_nItemHeight), paint);  
        }  
        super.onDraw(canvas);  
    }  
}
