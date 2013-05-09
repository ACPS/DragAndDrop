package com.example.draganddrop;

import com.example.draganddrop.controller.MainController;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;

import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class Figura implements OnTouchListener{
	

	private boolean selected;
	private ImageView view;	
	private ViewGroup marco;
	private int xDelta;
	private int yDelta;
	private int drawable;
	private TextView text;
	private int grade;
	
	
	public Figura(ImageView v, int drawable, TextView text){
		this.selected=false;
		Log.i("Image",v+"");
		this.view = v;
		v.setOnTouchListener(this);
		this.drawable=drawable;
		this.text=text;
		this.grade=0;
	}
	
	public Figura(){
		
	}
	
	public void setGrade(int grade){
		this.grade=grade;
	}

	public int getGrade(){
		return this.grade;
	}
	
	public int getDrawable(){
		return this.drawable;
	}

	public ImageView getView(){
		return this.view;
	}
	
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public void setBitMap(Bitmap image){
		view.setImageBitmap(image);
	}

	@Override
	public boolean onTouch(View view, MotionEvent event) {
				// Recogemos las coordenadas del dedo
				final int X = (int) event.getRawX();
				final int Y = (int) event.getRawY();

				Log.i("EVENT", event.getAction() + "");
				if (event.getAction() == 261) {
					Log.i("EVENT", "Agrandar ");

				}
				if (event.getAction() == 262) {
					Log.i("EVENT", "Disminuir");
				}

				// Dependiendo de la accion recogida..
				switch (event.getAction() & MotionEvent.ACTION_MASK) {
				// Al tocar la pantalla
				case MotionEvent.ACTION_DOWN:
					// Recogemos los parametros de la imagen que hemo tocado
					
					MainController.getInstance().disabledFiguras();
						RelativeLayout.LayoutParams Params = (RelativeLayout.LayoutParams) view
								.getLayoutParams();
						
						
						
						if(MainController.getInstance().getActual()==-1){
							Log.i("Touch", "Aqui");
							if(this.selected){
								this.selected=false;
								MainController.getInstance().setActual(-1);
								MainController.getInstance().getSeekBar().setEnabled(false);
							}else{
								this.selected=true;
								MainController.getInstance().updateActual();
								MainController.getInstance().getSeekBar().setEnabled(true);
							}
						}else{
							MainController.getInstance().getFiguras().get(MainController.getInstance().getActual()).setSelected(false);
							Log.i("Igual", MainController.getInstance().getFiguras().get(MainController.getInstance().getActual()).equals(this)+"");
							if(MainController.getInstance().getFiguras().get(MainController.getInstance().getActual()).equals(this)){
									this.selected=false;
									MainController.getInstance().setActual(-1);
									MainController.getInstance().getSeekBar().setEnabled(false);
								
								
							}else{
								this.selected=true;
								MainController.getInstance().updateActual();
								MainController.getInstance().getSeekBar().setEnabled(true);
							}
							
						}
						
						
						this.xDelta = X - Params.leftMargin;
						this.yDelta = Y - Params.topMargin;
						
						
						text.setText("Seleccionado "+drawable);
						
						MainController.getInstance().updateSeekBar();
					
					break;
				case MotionEvent.ACTION_UP:
					// Al levantar el dedo simplemento mostramos un mensaje
					//Toast.makeText(this, "Soltamos", Toast.LENGTH_LONG).show();
//					this.selected=false;
					
					break;
				case MotionEvent.ACTION_POINTER_DOWN:
					// No hace falta utilizarlo
					break;
				case MotionEvent.ACTION_POINTER_UP:
					// No hace falta utilizarlo
					break;
				case MotionEvent.ACTION_MOVE:
					// Al mover el dedo vamos actualizando
					// los margenes de la imagen para
					// crear efecto de arrastrado
					RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view
							.getLayoutParams();
					Log.i("View", X+" ");
					
					layoutParams.leftMargin = X - xDelta;
					layoutParams.topMargin = Y - yDelta;
					// Qutamos un poco de margen para
					// que la imagen no se deforme
					// al llegar al final de la pantalla y pueda ir más allá
					// probar también el codigo omitiendo estas dos líneas
					layoutParams.rightMargin = -30;
					layoutParams.bottomMargin = -30;
					// le añadimos los nuevos
					// parametros para mover la imagen
					view.setLayoutParams(layoutParams);
					break;

				}
				// Se podría decir que 'dibujamos'
				// la posición de la imagen en el marco.
//				//marco.invalidate();
				return true;
	}
	
	
}
