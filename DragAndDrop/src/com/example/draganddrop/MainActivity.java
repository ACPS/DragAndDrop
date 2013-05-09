package com.example.draganddrop;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.draganddrop.controller.MainController;

@SuppressLint("NewApi")
public class MainActivity extends Activity implements OnTouchListener {

	// Definimos el marco por el cual podemos arrastrar la imagen
	private ViewGroup marco;
	// Definimos la imagen que vasmo arrastrar
	private ImageView imagen;

	// Variables para centrar la imagen bajo el dedo
	private int xDelta;
	private int yDelta;
	private SeekBar barra;
	Matrix matrix = new Matrix();
	private int rotate=0;
	private ImageView objeto2;

	PaintView paintView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		paintView = new PaintView(this);
		//setContentView(paintView);
		paintView.requestFocus();
		
		setContentView(R.layout.activity_main);
		// Relacionamos
		marco = (ViewGroup) findViewById(R.id.marco);
		// Creamos la imagen
		imagen = new ImageView(this);

		// Señalamos la imagen a mostrar

		// Añadimos el Listener de la clase
		//imagen.setOnTouchListener(this);
		// Añadimos la imagen al marco

		matrix.postRotate(0);

		objeto2 = (ImageView) findViewById(R.id.objeto2);
		//objeto2.setOnTouchListener(this);
		
		Bitmap original = BitmapFactory.decodeResource(getResources(),
				R.drawable.google);
		Bitmap rotatedBitmap = Bitmap.createBitmap(original, 0, 0,
				original.getWidth(), original.getHeight(), matrix, true);
		// original.recycle();

		imagen.setImageBitmap(rotatedBitmap);

		imagen.buildDrawingCache();
		imagen.setScaleType(ScaleType.CENTER);
		
		marco.addView(imagen);
		marco.addView(paintView);
		
		barra = (SeekBar) findViewById(R.id.bar);
		
		MainController.getInstance();
		MainController.getInstance().setSeekBar(barra);
		
		imagen.setPivotX(imagen.getWidth()/2);
		imagen.setPivotY(imagen.getHeight()/2);
		imagen.setRotation(45);

		objeto2.setPivotX(objeto2.getWidth()/2);
		objeto2.setPivotY(objeto2.getHeight()/2);
		objeto2.setRotation(45);
		
		MainController.getInstance().addFigura(new Figura(imagen,R.drawable.google, (TextView)findViewById(R.id.selected)));
		MainController.getInstance().addFigura(new Figura(objeto2,R.drawable.ic_launcher,(TextView)findViewById(R.id.selected)));
		
		
		
		
		
		barra.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				Log.i("Change", progress + "");

				
				
				
				//matrix.setTranslate(50, 50);
				
				// BitmapDrawable drawable = (BitmapDrawable)
				// imagen.getDrawable();
				//
				// Bitmap original = drawable.getBitmap();
				//
				// Log.i("cHANGE",original+"");
				// Bitmap rotatedBitmap = Bitmap.createBitmap(original, 0, 0,
				// original.getWidth(), original.getHeight(), matrix, true);
				//
				Figura f = MainController.getInstance().getFigura();
				Log.i("figura",f.getDrawable()+"");
				f.setGrade(progress);
				Bitmap original = BitmapFactory.decodeResource(getResources(),
						f.getDrawable());
				
				matrix.postRotate(progress - rotate, 0,0);
				rotate = progress;
				
				Bitmap rotatedBitmap = Bitmap.createBitmap(original, 0, 0,
						original.getWidth(), original.getHeight(), matrix, true);
				original.recycle();

				
				f.setBitMap(rotatedBitmap);
				

			}
		});
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
			RelativeLayout.LayoutParams Params = (RelativeLayout.LayoutParams) view
					.getLayoutParams();
			xDelta = X - Params.leftMargin;
			yDelta = Y - Params.topMargin;
			
			break;
		case MotionEvent.ACTION_UP:
			// Al levantar el dedo simplemento mostramos un mensaje
			Toast.makeText(this, "Soltamos", Toast.LENGTH_LONG).show();
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
		marco.invalidate();
		return true;
	}

	public void invertirImagen(View v) {
		float[] mirrorY = { -1, 0, 0, 0, 1, 0, 0, 0, 1 };
		Matrix matrixMirror = new Matrix();
		matrixMirror.setValues(mirrorY);
		matrix.postConcat(matrixMirror);
		
		Figura f =MainController.getInstance().getFigura();
		Bitmap original = BitmapFactory.decodeResource(getResources(),
				f.getDrawable());
		Bitmap rotatedBitmap = Bitmap.createBitmap(original, 0, 0,
				original.getWidth(), original.getHeight(), matrix, true);
		original.recycle();

		f.setBitMap(rotatedBitmap);
		
//		RotateAnimation rotateAnimation = new RotateAnimation(30,90,
//				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
//				0.5f);
//
//			rotateAnimation.setInterpolator(new LinearInterpolator());
//			rotateAnimation.setDuration(5000);
//			rotateAnimation.setFillAfter(true);
//			objeto2.startAnimation(rotateAnimation);
	}

	
}
