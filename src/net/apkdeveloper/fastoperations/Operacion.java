package net.apkdeveloper.fastoperations;

public class Operacion {
	 private int operando1;	 
	 private int operando2;	 
	 private char operador;
	 
	 public Operacion(int o1, int o2, char op){	  
	  operando1=o1;
	  operando2=o2;
	  operador=op;
	 }

	 public int getOperando1(){
	  return operando1;
	 }
	 
	 public int getOperando2(){
	  return operando2;
	 }

	 public char getOperador(){
	  return operador;
	 }
}
