package MyUtility;

import java.io.Serializable;
import java.util.Arrays;

import android.util.Log;



public class MyFrame implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3619793202738277926L;
	private int first,second;												//pierwszy;drugi rzut 
	private boolean spare,strike,open;
	private boolean filled;
	private boolean[] firstLeft;
	private boolean[] secondLeft;
	private static String UNFILLED="";
	
	/**
	 * public constructor
	 */
	public MyFrame() {														//konstruktor domyslny ustawia na strike'a
		first = 10;
		second = 0;
		strike=true;
		spare = false;
		open = false;
		filled = true;
		firstLeft = new boolean[10];
		secondLeft = new boolean[10];
		Arrays.fill(firstLeft, true);
		Arrays.fill(secondLeft,false);
	}
	/**
	 * @return String
	 * zwraca Stringa z wartoscia ramki
	 */
	public String toString(){												//wypisuje zawartosc ramki w formie tekstowej
		if(first==10){
			return "X";
		}
		else if(spare){
			return Integer.toString(first)+":/";
		}
		else
			return Integer.toString(first)+":"+Integer.toString(second); 
		
	}
	public String firstPinsToString(){
		if(first==10){
			return "X";
		}
		else{
			return Integer.toString(first);
		}
	}
	/**
	 * Return int with number of pins falled down
	 * @return
	 */
	public String secondPinsToString(){
		if(spare){
			return "/";
		}else
			return second+"";
	}
	/**
	 * edytuje wartosc w danej ramce
	 * @param first
	 * @param second
	 * @throws 
	 */
	public void edit(int first,int second){								//zmienia zawartosc ramki
	  try{
		if( (first<=10 & ( (first+second)<=10)) && (first>=0 && second>=0 ) ){
			this.first=first;
			this.second=second;
			filled=false;
		}
		//else throw new Exception();
		if(first==10){
			strike=true;
			spare=false;
			open=false;
			Arrays.fill(secondLeft, false);
			Arrays.fill(firstLeft, true);
			filled=true;
		}
		else if((first+second)==10){
			strike=false;
			spare=true;
			open=false;
			filled=false;
		}
		else if((first+second)<10){
			strike=false;
			spare=false;
			open=true;
			filled=false;
			if(first+second==0)
			{
				Arrays.fill(secondLeft, false);
				Arrays.fill(firstLeft, false);
				filled=true;
			}
		
		}
	  }catch(Exception e){
		  Log.d("DEBUG", e.toString());
	  }
		
	}
	/**
	 * Edycja ramki wraz z wypelnieniem wartosci pinów
	 * @param first
	 * @param second
	 * @param fallF
	 * @param fallS
	 */
	public void edit(int first,int second,boolean[] fallF,boolean[] fallS){								//zmienia zawartosc ramki
		if( (first<=10 & ( (first+second)<=10)) && (first>=0 && second>=0 ) )
		{
			this.first=first;
			this.second=second;
			filled = true;
			firstLeft = fallF;
			secondLeft = fallS;
		}
		//else throw new Exception();
		if(first==10){
			strike=true;
			spare=false;
			open=false;
		}
		else if((first+second)==10){
			strike=false;
			spare=true;
			open=false;
		}
		else if((first+second)<10){
			strike=false;
			spare=false;
			open=true;
		
		}
		
	}
	
	/**
	 * Zwraca wartosc pierwszego rzutu
	 * @return int first
	 */
	public int getPinF(){
		
		return first;
	}
	/**
	 * zwraca wartosc drugiego rzutu
	 * @return int second
	 */
	public int getPinSecond(){
		
		return second;
	}
	/**
	 * Zwraca wartsoc pinów calej ramki
	 * @return int first+second
	 */
	public int getPins(){
			return first+second;
	}
	/**
	 * Zwraca boolean strike
	 * @return int strike true=1 false=0
	 */
	public int ifStrike(){
		return strike ? 1 : 0;
	}
	public int ifSpare(){
		return spare ? 1 : 0;
	}
	public int ifOpen(){
		return open ? 1 : 0;
	}
	public void setOpen(boolean opener){
		open=opener;
	}
	public boolean[] getLeftF(){
		return firstLeft;
	}
	public boolean[] getLeftS(){
		return secondLeft;
	}
	public boolean getfilled(){
		return filled;
		
	}
	/**
	 * Return what pins falls down after both throws in format
	 * <pins1,...,pins9:pins...pinsn>
	 */
	public String getLeftToString(){
		if(!filled){
			return UNFILLED;
		}
		StringBuilder sgs = new StringBuilder();
		String result;
		sgs.append("<");
		int i =1;
		boolean whatever=false;
		for(boolean b : firstLeft){
			if(b){
				whatever=true;
				sgs.append(i);
				sgs.append(",");
				
			}
			i++;
		}
		if(!whatever){
			sgs.append(0);
		}
		whatever=false;
		//sgs.deleteCharAt(sgs.length());
		sgs.append(":");
		i=1;
		for(boolean b : secondLeft){
			if(b){
				whatever=true;
				sgs.append(i);
				sgs.append(",");
			}
			i++;
		}
		if(!whatever){
			sgs.append(0);
		}
		
		sgs.append(">");
		result = sgs.toString();
		return result;
	}
	
	public String getFirstToString(){
		if(!filled){
			return UNFILLED;
		}
		if(first==0){
			return "0";
		}
		StringBuilder sgs = new StringBuilder();
		String result;
		int i =1;
		for(boolean b : firstLeft)
		{
			if(b){
				
				sgs.append(i);
			}
			i++;
		}
		result = sgs.toString();
		return result;
	}
	public String getSecondToString()
	{	
		if(!filled){
			return UNFILLED;
		}
		StringBuilder sgs = new StringBuilder();
		String result;
		int i =1;
		if(second==0){
			return "0";
		}
		if(isStrike())
			return "0";
		for(boolean b : secondLeft)
		{
			if(b)
			{
				sgs.append(i);
			}
			i++;
		}
		result = sgs.toString();
		if(result.length()>0)
			return result;
		else
			return "0";
	}
	public boolean isStrike() {
		// TODO Auto-generated method stub
		return strike;
	}
	public boolean isSpare() {
		// TODO Auto-generated method stub
		return spare;
	}
	
}
