package com.example.draganddrop.controller;

import java.util.LinkedList;
import java.util.List;

import android.widget.SeekBar;

import com.example.draganddrop.Figura;

public class MainController {

	
	private static MainController instance;
	private List<Figura> figuras;
	private SeekBar barra;
	private int actual=-1;
	
	private MainController(){
		figuras =new LinkedList<Figura>();
	}
	
	public static MainController  getInstance(){
		if(instance==null){
			instance=new MainController();
		}
		return instance;
	}
	
	
	public void setSeekBar(SeekBar seekbar){
		this.barra=seekbar;
	}
	
	public SeekBar getSeekBar(){
		return this.barra;
	}
	
	public void addFigura(Figura f){
		figuras.add(f);
	}
	
	public Figura getFigura(){
		Figura figura= null;
		for(Figura f: figuras){
			if(f.isSelected()){
				figura=f;
			}
		}
		return figura;
	}
	
	public boolean isSelectedFigura(){
		for(Figura f: figuras){
			if(f.isSelected()){
				return true;
			}
		}
		return false;
	}
	
	
	public void disabledFiguras(){
		for(Figura f: figuras){
			f.setSelected(false);
		}
	}
	
	public void updateSeekBar(){
		for(Figura f: figuras){
			if(f.isSelected()){
				this.barra.setProgress(f.getGrade());
			}
		}
	}
	
	
	
	public int getActual(){
		return this.actual;
	}
	
	public void setActual(int actual){
		this.actual =actual;
	}
	
	public void updateActual(){
		int i=0;
		
		for(Figura f: figuras){
			if(f.isSelected()){
				this.actual=i;
			}
			i++;
		}
	}
	

	public List<Figura> getFiguras(){
		return this.figuras;
	}
	
}
