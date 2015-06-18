
package MyUtility;

import java.util.Arrays;



/**
 * 
 * @author karlo
 * Klasa przechowuj¹ca dane pojedynczej gry
 */
public class MyGame {
	
	private MyFrame[] frames;
	private int[] actuals;
	private int scores;
	
	/**
	 * konstruktor tworzy gre wypelniona strike'ami
	 */
	public MyGame() {															
		frames= new MyFrame[12];
		actuals = new int[12];
		for(int i=0;i < frames.length;i++){
			frames[i] = new MyFrame(); 
		}
		this.myScore();
	}

/**
 *  Podaje wartosc danej ramki w tablicy int[2]
 * @param frmNmbr
 * @return int[]
 */
	public int[] getFramePins(int frmNmbr){
		int[] pins = new int[2];
		if(frmNmbr<12 && frmNmbr>=0){
			pins[0]= frames[frmNmbr].getPinF();
			pins[1] = frames[frmNmbr].getPinSecond();
			return pins;
		}
		else
			return null;
	}
/**
 * zwraca cala tablice ramek/frame'ow
 */
	public MyFrame[] getAllFrame(){
		return frames;
		
	}
	public MyFrame getOneFrame(int which)
	{
		
		if(which>=0 && which<12)
		{
			return frames[which];
		}
		else
			return null;
			
	}
	/**
	 * @param void
	 * @return String[]
	 * metoda zwracaj¹ca tablice String[] z wynikami poszczegolnych ramek
	 * podaje wszystkie wyniki w postaci tablicy[12] stringa format[xx]wynik
	 */
	public String[] GetScore(){
		String[] temp = new String[12];
		int y;
		for(y=0;y<frames.length;y++){																			//dla ramek 1-9		
			temp[y]=" ["+frames[y].toString()+"]\n"+actuals[y]+" ";
		}
		//temp[y]=" ["+frames[y]+"|"+frames[y+1]+"|"+frames[y+2]+"]\n"+actuals[y]+" ";								//dla 'ramki' 10-tej
		return temp;
	}
	
/**
 * oblicza wynik koncowy i etapowe
 */
	public void myScore(){														
		int score=0;
		int i;
		//oblicza wynik od 1-8 ramki

		for(i=0;i<frames.length-4;i++){	
			if( frames[i].isStrike() ){																			//jesli strajk dodaj dwa kolejne rzuty
				if(frames[i+1].isStrike()){																		//jesli kolejny strajk to dodaj dwa kolejne rzuty
					score =( score + (frames[i].getPins() ) + ( frames[i+1].getPins() ) + ( frames[i+2].getPinF() ) );
				}
				else
					score =( score + (frames[i].getPins() ) + ( frames[i+1].getPins() ));						//jesli nie dodaj tylko kolejna ramke
			}
			else if(frames[i].isSpare()){																		//jesli spare dodaj tylko kolejny rzut
				
				score =( score + (frames[i].getPins() ) + ( frames[i+1].getPinF() ) );
			}
			else
				score+=frames[i].getPins();
			actuals[i]=score;
			
		}
		//koniec petli 1-8 ramnki
		  																										//oblicza wynik dla 9 ramki tutaj dla strike
		if(frames[i].isStrike() ){																				//jesli strajk
			if(frames[i+1].isStrike()){																			//i w 10ramce strajk dodaj dwie ramki
				score+=frames[i].getPins()+frames[i+1].getPins()+frames[i+2].getPinF();
				i++;
			}
			else{															//w 10 nie strajk dodaj jedna ramke
				score+=frames[i].getPins()+frames[i+1].getPins();
				i++;
			}
		}
		else if(frames[i].isSpare() ){																//jesli spare w 9-tej dodaj jedna ramke
				score+=frames[i].getPins()+frames[i+1].getPinF();
				i++;
		}
		else{
			score+=frames[i].getPins();
			i++;
		}
		actuals[i-1]=score;
		//kojniec 9ramki
		//10 ramka
		while(i<12){
			score+=frames[i].getPins();
			actuals[i]=score;
			i++;
		}
		scores=score;
		//return actuals;

	}
	/**
	 * 
	 * @return int scores
	 * zwraca ilosc punktów zdobytych w grze
	 */
	public int getScore(){
		return scores;
	}
/**
 * Edytuje poszczegolne ramki wraz z wypelnieniem
 * @param pos
 * @param f
 * @param s
 * @param first
 * @param second
 */
	public void edit(int pos,int f,int s,boolean[] first,boolean[] second)
	{
		if(pos>=0 && pos<=12)
		{																	//sprawdza czy chce sie edytowac ramke z zakresu gry 13 pozycji indeks 0-12
			if( (pos>=0 && pos<9) || (pos==9 && f==10) )											//jesli ramka 1-9 lub w 10-tej strajk to edytuj normalnie
				frames[pos].edit(f, s,first,second);
			//jesli 10 ramka nie strajk/spare brak 11 i 12 ramki
			else if((pos==9) && ( (f+s)<10 ) )
			{													
				frames[pos].edit(f, s,first,second);
				frames[pos+1].edit(0, 0);
				frames[pos+2].edit(0, 0);
				frames[pos+1].setOpen(false);
				frames[pos+2].setOpen(false);
			}
			//jesli 10 ramka rowna sie spare to nie ma 12 ramki i drugiego rzutu 11
			else if(pos==9 && f+s==10){
				frames[pos].edit(f, s,first,second);
				frames[pos+1].edit(10, 0);
				frames[pos+2].edit(0, 0);
				frames[pos+2].setOpen(false);
			}
			//jesli ramka 11 i w 10 nie bylo conajmniej spare to nie edytuj
			else if( ( (pos == 10) ) && (frames[pos-1].getPins()<10))		
				System.err.println(moja.paczka.namespace.R.string.error_edit_frame2);
			//jesli ramka 11 i w 10 byl spare to edytuj tylko pierwszy rzut
			else if( ( (pos == 10) ) && (frames[pos-1].getPinF()<10 && frames[pos-1].getPins()==10 )){		
					frames[pos].edit(f, 0);
					frames[pos].setOpen(false);
					frames[pos+1].edit(0, 0);
					frames[pos+1].setOpen(false);
					
			}					
			//jesli ramka 11 i w 10 byl strike to edytuj
			else if( ( (pos == 10) ) && (frames[pos-1].getPinF()==10)){
				if(f==10){												//jesli strajk to jest 12 ramka
						frames[pos].edit(f, s,first,second);
						frames[pos+1].edit(10, 0);
				}
				else{													//jesli nie to brak 12
					frames[pos].edit(f, s,first,second);
					frames[pos+1].edit(0, 0);
					frames[pos+1].setOpen(false);
				}
			}
			//jesli 12 ramka i  nie bylo strajka w 11 to nie edytuj
			else if(pos==11 && frames[pos-1].getPinF()<10)
				System.err.println(moja.paczka.namespace.R.string.error_edit_frame);
			//jesli 12 ramka i  strajk w 11 i 10 to edytuj pierwszy rzut
			else if(pos==11 && frames[pos-1].getPinF()==10 && frames[pos-2].getPinF()==10){
					frames[pos].edit(f, 0);
					frames[pos].setOpen(false);;
			}
			
			this.myScore();
		}
		else
			System.err.println(moja.paczka.namespace.R.string.error_edit_frame2+pos);
		
	}
	/**
	 * 
	 * @param pos
	 * @param f
	 * @param s
	 * Edytuje poszczegolne ramki bez wypelnienienia
	 */
	public void edit(int pos,int f,int s)
	{

		if(pos>=0 && pos<=12)
		{																	//sprawdza czy chce sie edytowac ramke z zakresu gry 13 pozycji indeks 0-12
			if( (pos>=0 && pos<9) || (pos==9 && f==10) )											//jesli ramka 1-9 lub w 10-tej strajk to edytuj normalnie
				frames[pos].edit(f, s);
			//jesli 10 ramka nie strajk/spare brak 11 i 12 ramki
			else if((pos==9) && ( (f+s)<10 ) )
			{													
				frames[pos].edit(f, s);
				frames[pos+1].edit(0, 0);
				frames[pos+2].edit(0, 0);
				frames[pos+1].setOpen(false);
				frames[pos+2].setOpen(false);
			}
			//jesli 10 ramka rowna sie spare to nie ma 12 ramki i drugiego rzutu 11
			else if(pos==9 && f+s==10){
				frames[pos].edit(f, s);
				frames[pos+1].edit(10, 0);
				frames[pos+2].edit(0, 0);
				frames[pos+2].setOpen(false);
			}
			//jesli ramka 11 i w 10 nie bylo conajmniej spare to nie edytuj
			else if( ( (pos == 10) ) && (frames[pos-1].getPins()<10))		
				System.err.println(moja.paczka.namespace.R.string.error_edit_frame2);
			//jesli ramka 11 i w 10 byl spare to edytuj tylko pierwszy rzut
			else if( ( (pos == 10) ) && (frames[pos-1].getPinF()<10 && frames[pos-1].getPins()==10 )){		
					frames[pos].edit(f, 0);
					frames[pos].setOpen(false);
					frames[pos+1].edit(0, 0);
					frames[pos+1].setOpen(false);
					
			}					
			//jesli ramka 11 i w 10 byl strike to edytuj
			else if( ( (pos == 10) ) && (frames[pos-1].getPinF()==10)){
				if(f==10){												//jesli strajk to jest 12 ramka
						frames[pos].edit(f, s);
						frames[pos+1].edit(10, 0);
				}
				else{													//jesli nie to brak 12
					frames[pos].edit(f, s);
					frames[pos+1].edit(0, 0);
					frames[pos+1].setOpen(false);
				}
			}
			//jesli 12 ramka i  nie bylo strajka w 11 to nie edytuj
			else if(pos==11 && frames[pos-1].getPinF()<10)
				System.err.println(moja.paczka.namespace.R.string.error_edit_frame);
			//jesli 12 ramka i  strajk w 11 i 10 to edytuj pierwszy rzut
			else if(pos==11 && frames[pos-1].getPinF()==10 && frames[pos-2].getPinF()==10){
					frames[pos].edit(f, 0);
					frames[pos].setOpen(false);;
			}
			
			this.myScore();
		}
		else
			System.err.println(moja.paczka.namespace.R.string.error_edit_frame2+pos);
		
	}
	/**
	 * @param void
	 * @return String
	 * give all frames to String in fromat <x:x>
	 */
	public String getFramesToString(){
		StringBuilder sg = new StringBuilder();
		String result;
		for(MyFrame fr : frames){
			sg.append("<");
			sg.append(fr.getPinF());
			sg.append(":");
			sg.append(fr.getPinSecond());
			sg.append(">");
		}
		result = sg.toString();
		return result;
		
	}
	/**
	 * 
	 * @return String
	 * return String with all pins in every frame
	 */
	public String getPinsToString(){
		StringBuilder sg = new StringBuilder();
		String result;
		for(MyFrame fr : frames){
			sg.append(fr.getLeftToString());
		}
		result = sg.toString();
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MyGame [frames=");
		builder.append(Arrays.toString(frames));
		builder.append(", actuals=");
		builder.append(Arrays.toString(actuals));
		builder.append(", scores=");
		builder.append(scores);
		builder.append("]");
		return builder.toString();
	}
	
		
	}
	
