package com.example.draganddrop;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class PaintView extends View implements OnTouchListener {
	private static final String TAG = "PaintView";

	List<Point> points = new ArrayList<Point>();
	Paint paint = new Paint();
	Path path ;
	List<Path> paths=new LinkedList<Path>();

	public PaintView(Context context) {
		super(context);
		setFocusable(true);
		setFocusableInTouchMode(true);

		this.setOnTouchListener(this);

		
		
		paint.setAntiAlias(true);
		
//		paint.setStrokeCap(Cap.ROUND);
//		paint.setStrokeJoin(Join.BEVEL);
		paint.setColor(Color.BLACK);
		
	}

	@Override
	public void onDraw(Canvas canvas) {
	
		for (Path path : paths) {
		    //canvas.drawPoint(graphic.x, graphic.y, mPaint);
		    canvas.drawPath(path, paint);
		 }
	}

	public boolean onTouch(View view, MotionEvent event) {
		Point point = new Point();
		point.x = event.getX();
		point.y = event.getY();
		points.add(point);
		invalidate();
		Log.d(TAG, "point: " + point);
		
		 int action = event.getAction();
		    switch (action) {
		    case MotionEvent.ACTION_DOWN:
		    		
		    	 path = new Path();
		         path.moveTo(event.getX(), event.getY());
		         path.lineTo(event.getX(), event.getY());
		      break;
		    case MotionEvent.ACTION_MOVE:
		    	path.lineTo(event.getX(), event.getY());
		      break;
		    case MotionEvent.ACTION_UP:
		    	path.lineTo(event.getX(), event.getY());
		    	paths.add(path);
		      break;
		    case MotionEvent.ACTION_CANCEL:
		      break;
		    default:
		      break;
		    }
		
		return true;
	}
}