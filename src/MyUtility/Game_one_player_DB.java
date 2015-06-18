/**
 * 
 */
package MyUtility;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;


/**
 * @author karlo
 * Klasa która zapisuje do bazy danych rozegrana gre
 */
public class Game_one_player_DB extends DbBaseAdapter 
{

	public Game_one_player_DB(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public boolean addGame(int match,int player,String type,int which,String frames,int ball,int pattL,int pattR,String pins,int result)
	{
		try{
			if(db==null || !db.isOpen())
				db=open();
			ContentValues values = new ContentValues();
			values.putNull(GAME_ID);
			
			values.put(GAME_MATCH_ID, match);
			values.put(GAME_PLAYER_ID,player);
			values.put(GAME_TYPE, type);
			values.put(GAME_RESULT, result);
			values.put(GAME_WHICH,which);
			values.put(GAME_FRAMES,frames);
			values.put(GAME_BALL, ball);
			values.put(GAME_PATTERN_LEFT,pattL);
			values.put(GAME_PATTERN_RIGHT,pattR);
			values.put(GAME_PINS, pins);
			
			if(db.insert(TABLE_GAME_NAME, null, values) != -1)
				return true;
	
		}catch(Exception e)
		{
			Log.e("Error GAME_DB add", e.toString());
		}
		finally
		{
			db.close();
		}
		return false;
	}
	/**
	 * 
	 * @param gierka
	 * @param matchId
	 * @param playerId
	 * @param which
	 * @param type
	 * @param ballId
	 * @param pattL
	 * @param pattR
	 * @return
	 * dodaj gre do bazy danych
	 */
	public boolean addGame(MyGame gierka,long matchId,int playerId,int which,String type/*,int ballId*/,int pattL,int pattR)
	{
		long id=-1;
		try
		{
			if(db==null || !db.isOpen())	//otworz baze danych
				db=open();
			/**
			 * start transaction
			 */
			//db.beginTransaction();			//dodawanie gry do bazy danych
			ContentValues values = new ContentValues();
			values.putNull(GAME_ID);
			values.putNull(GAME_BALL);
			values.put(GAME_MATCH_ID, matchId);
			values.put(GAME_PLAYER_ID,playerId);
			values.put(GAME_TYPE, type);
			values.put(GAME_RESULT, gierka.getScore());
			values.put(GAME_WHICH,which);
			values.put(GAME_FRAMES,gierka.getFramesToString());
			/*
			 * dodanie wpierw ramek do bazy danych
			 */

			Throws throwDb = new Throws(context);
			Frames_DB frameDb = new Frames_DB(context);
			int tId=-1;
			int tId2=-1;
			long ramkiId[] = new long[12];
			MyFrame[] ramki = gierka.getAllFrame();	//pobranie ramek do dodania do bazy danych
			if(ramki==null)							//jesli by nie pobralo ramek zakoncz dzialanie nie zapisujac gry
			{
				Log.e("Error myGame.getAllFrame()","MyGame method error");
				//db.endTransaction();
				return false;
			}
			
			int k=10;		//zmienna do ilu ramek ma zapisac
			if(ramki[9].isStrike())
			{
				k=11;
				if(ramki[10].isStrike())
				{
					k=12;
				}
			}
			for(int i =0;i<k;i++)
			{
				ContentValues frameValues = new ContentValues();
				long frameId=-1;
				if(ramki[i].getfilled())
				{
				
						tId2 = throwDb.getThrowId(ramki[i].getSecondToString());
						tId = throwDb.getThrowId(ramki[i].getFirstToString());
						//Log.d("DEBUG", "FIRST:"+tId+"  :"+ramki[i].getFirstToString()+" second "+tId2+"  :"+ramki[i].getSecondToString());
					
					if( tId==-1 || tId2==-1)
					{
						Log.e("Error ThrowDB","filled="+ramki[i].getfilled()+"Throw Db on:"+i+" don't found id:"+ramki[i].getFirstToString()+" : "+ramki[i].getSecondToString());
						//db.endTransaction();
						return false;
					}
					frameId = frameDb.getFrameIdfromThrows(tId, tId2);	
					if(frameId == -1)
					{
						frameValues.put(FRAME_THROW1_ID,tId);
						frameValues.put(FRAME_THROW2_ID,tId2);
						frameValues.putNull(FRAME_ID);
						frameValues.put(FRAME_SPARE,ramki[i].ifSpare());
						frameValues.put(FRAME_OPEN,ramki[i].ifOpen());
						frameValues.put(FRAME_THROW1,ramki[i].getPinF());
						frameValues.put(FRAME_THROW2,ramki[i].getPinSecond());
						frameValues.put(FRAME_STRIKE, ramki[i].ifStrike());
				
						ramkiId[i] = db.insert(TABLE_FRAME_NAME, null, frameValues);				
						if(ramkiId[i] == -1)
						{
							Log.e("Error FrameDB","isnerting data into Frame DB");
							//db.endTransaction();
							return false;
						}
					}
					else
					{
						ramkiId[i]=frameId;
					}
				}
				else
				{
					frameId = frameDb.getFrameIdfromScore(ramki[i].getPinF(),ramki[i].getPinSecond());	
					if(frameId == -1)
					{
						frameValues.put(FRAME_THROW1_ID,-1);
						frameValues.put(FRAME_THROW2_ID,-1);
						frameValues.putNull(FRAME_ID);
						frameValues.put(FRAME_SPARE,ramki[i].ifSpare());
						frameValues.put(FRAME_OPEN,ramki[i].ifOpen());
						frameValues.put(FRAME_THROW1,ramki[i].getPinF());
						frameValues.put(FRAME_THROW2,ramki[i].getPinSecond());
						frameValues.put(FRAME_STRIKE, ramki[i].ifStrike());
				
						ramkiId[i] = db.insert(TABLE_FRAME_NAME, null, frameValues);				
						if(ramkiId[i] == -1)
						{
							Log.e("Error FrameDB","isnerting data into Frame DB");
							//db.endTransaction();
							return false;
						}
					}else
						ramkiId[i]=frameId;
				}
			}
		/*
		 * koniec dodawania ramek do bazy teraz dodanie gry doa bazy i relacji gra ramki player 
		 */
			values.put(GAME_FRAME1, (int) ramkiId[0]);
			values.put(GAME_FRAME2, (int) ramkiId[1]);
			values.put(GAME_FRAME3, (int) ramkiId[2]);
			values.put(GAME_FRAME4, (int) ramkiId[3]);
			values.put(GAME_FRAME5, (int) ramkiId[4]);
			values.put(GAME_FRAME6, (int) ramkiId[5]);
			values.put(GAME_FRAME7, (int) ramkiId[6]);
			values.put(GAME_FRAME8, (int) ramkiId[7]);
			values.put(GAME_FRAME9, (int) ramkiId[8]);
			values.put(GAME_FRAME10, (int) ramkiId[9]);
			if(k>10)
				values.put(GAME_FRAME11, (int) ramkiId[10]);
			else{
				values.put(GAME_FRAME11,-1);
			}
			if(k>11){
				values.put(GAME_FRAME12, (int) ramkiId[11]);
			}
			else{
				values.put(GAME_FRAME12, -1);
			}
			
			//values.put(GAME_BALL,ballId);
			values.put(GAME_PATTERN_LEFT,pattL);
			values.put(GAME_PATTERN_RIGHT,pattR);
			//values.put(GAME_PINS, gierka.getPinsToString());
			id = db.insert(TABLE_GAME_NAME, null, values);			//get result from inserting into DB
			if( id != -1)	//if inserting goes wrong rollback whole transaction
			{
				for(int i =0;i<k;i++)
				{
					Log.d("DEBUG","ADD FPG FRid="+ramkiId[i]+"  "+ramki[i].toString());
					ContentValues frameValues = new ContentValues();
					frameValues.put(FRAMEPG_FRAME_ID,ramkiId[i]);
					frameValues.put(FRAMEPG_PLAYER_ID,playerId);
					frameValues.put(FRAMEPG_GAME_ID, (int)id);
					frameValues.put(FRAMEPG_WHICH,i);
					frameValues.put(FRAMEPG_AIM,-1);
					frameValues.put(FRAMEPG_BOARD,-1);
					frameValues.put(FRAMEPG_BALL_ID,-1);
					db.insert(TABLE_FPG, null, frameValues);
				}
				/*teraz zaktualizowac id frame'ow w game
				values=new ContentValues();
				String where = "id='"+id+"'";
				db.update(TABLE_GAME_NAME, values, where, null);
				*/
			}
			else
			{
				Log.e("Error GAME_DB add","GAME_DB add");
				//db.endTransaction();
				return false;
			}
			/**
			 * end transaction ok!
			 */
				//db.setTransactionSuccessful();
				//db.endTransaction();
				return true;
	
		}catch(Exception e)
		{
			Log.e("Error GAME_DB add", e.toString());
		}
		finally
		{
			//if(db.inTransaction())
				//db.endTransaction();
			db.close();
		}
		return false;
	}	
		
	

	public boolean updateGame(int games_id, MyGame gierka, int matchId,
			int playerId, int which, String type,int pattL,int pattR) 
	{
		long id=-1;
		try
		{
			if(db==null || !db.isOpen())	//otworz baze danych
				db=open();
			/**
			 * start transaction
			 */
			//db.beginTransaction();			//dodawanie gry do bazy danych
			ContentValues values = new ContentValues();
			values.put(GAME_ID,games_id);
			values.putNull(GAME_BALL);
			values.put(GAME_MATCH_ID, matchId);
			values.put(GAME_PLAYER_ID,playerId);
			values.put(GAME_TYPE, type);
			values.put(GAME_RESULT, gierka.getScore());
			values.put(GAME_WHICH,which);
			values.put(GAME_FRAMES,gierka.getFramesToString());
			/*
			 * dodanie wpierw ramek do bazy danych
			 */

			Throws throwDb = new Throws(context);
			Frames_DB frameDb = new Frames_DB(context);
			int tId=-1;
			int tId2=-1;
			long ramkiId[] = new long[12];
			MyFrame[] ramki = gierka.getAllFrame();	//pobranie ramek do dodania do bazy danych
			if(ramki==null)							//jesli by nie pobralo ramek zakoncz dzialanie nie zapisujac gry
			{
				Log.e("Error myGame.getAllFrame()","MyGame method error");
				//db.endTransaction();
				return false;
			}
			
			int k=10;		//zmienna do ilu ramek ma zapisac
			if(ramki[9].isStrike())
			{
				k=11;
				if(ramki[10].isStrike())
				{
					k=12;
				}
			}
			for(int i =0;i<k;i++)
			{
				ContentValues frameValues = new ContentValues();
				long frameId=-1;
				if(ramki[i].getfilled())
				{
				
						tId2 = throwDb.getThrowId(ramki[i].getSecondToString());
						tId = throwDb.getThrowId(ramki[i].getFirstToString());
						//Log.d("DEBUG", "FIRST:"+tId+"  :"+ramki[i].getFirstToString()+" second "+tId2+"  :"+ramki[i].getSecondToString());
					
					if( tId==-1 || tId2==-1)
					{
						Log.e("Error ThrowDB","filled="+ramki[i].getfilled()+"Throw Db on:"+i+" don't found id:"+ramki[i].getFirstToString()+" : "+ramki[i].getSecondToString());
						//db.endTransaction();
						return false;
					}
					frameId = frameDb.getFrameIdfromThrows(tId, tId2);	
					if(frameId == -1)
					{
						frameValues.put(FRAME_THROW1_ID,tId);
						frameValues.put(FRAME_THROW2_ID,tId2);
						frameValues.putNull(FRAME_ID);
						frameValues.put(FRAME_SPARE,ramki[i].ifSpare());
						frameValues.put(FRAME_OPEN,ramki[i].ifOpen());
						frameValues.put(FRAME_THROW1,ramki[i].getPinF());
						frameValues.put(FRAME_THROW2,ramki[i].getPinSecond());
						frameValues.put(FRAME_STRIKE, ramki[i].ifStrike());
				
						ramkiId[i] = db.insert(TABLE_FRAME_NAME, null, frameValues);				
						if(ramkiId[i] == -1)
						{
							Log.e("Error FrameDB","isnerting data into Frame DB");
							//db.endTransaction();
							return false;
						}
					}
					else
					{
						ramkiId[i]=frameId;
					}
				}
				else
				{
					frameId = frameDb.getFrameIdfromScore(ramki[i].getPinF(),ramki[i].getPinSecond());	
					if(frameId == -1)
					{
						frameValues.put(FRAME_THROW1_ID,-1);
						frameValues.put(FRAME_THROW2_ID,-1);
						frameValues.putNull(FRAME_ID);
						frameValues.put(FRAME_SPARE,ramki[i].ifSpare());
						frameValues.put(FRAME_OPEN,ramki[i].ifOpen());
						frameValues.put(FRAME_THROW1,ramki[i].getPinF());
						frameValues.put(FRAME_THROW2,ramki[i].getPinSecond());
						frameValues.put(FRAME_STRIKE, ramki[i].ifStrike());
				
						ramkiId[i] = db.insert(TABLE_FRAME_NAME, null, frameValues);				
						if(ramkiId[i] == -1)
						{
							Log.e("Error FrameDB","isnerting data into Frame DB");
							//db.endTransaction();
							return false;
						}
					}else
						ramkiId[i]=frameId;
				}
			}
		/*
		 * koniec dodawania ramek do bazy teraz dodanie gry doa bazy i relacji gra ramki player 
		 */
			values.put(GAME_FRAME1, (int) ramkiId[0]);
			values.put(GAME_FRAME2, (int) ramkiId[1]);
			values.put(GAME_FRAME3, (int) ramkiId[2]);
			values.put(GAME_FRAME4, (int) ramkiId[3]);
			values.put(GAME_FRAME5, (int) ramkiId[4]);
			values.put(GAME_FRAME6, (int) ramkiId[5]);
			values.put(GAME_FRAME7, (int) ramkiId[6]);
			values.put(GAME_FRAME8, (int) ramkiId[7]);
			values.put(GAME_FRAME9, (int) ramkiId[8]);
			values.put(GAME_FRAME10, (int) ramkiId[9]);
			if(k>10)
				values.put(GAME_FRAME11, (int) ramkiId[10]);
			else{
				values.put(GAME_FRAME11,-1);
			}
			if(k>11){
				values.put(GAME_FRAME12, (int) ramkiId[11]);
			}
			else{
				values.put(GAME_FRAME12, -1);
			}
			
			//values.put(GAME_BALL,ballId);
			values.put(GAME_PATTERN_LEFT,pattL);
			values.put(GAME_PATTERN_RIGHT,pattR);
			//values.put(GAME_PINS, gierka.getPinsToString());
			id = db.update(TABLE_GAME_NAME, values, " _id="+games_id, null);
							//get result from inserting into DB
			if( id != -1)	//if inserting goes wrong rollback whole transaction
			{
				db.delete(TABLE_FPG, " player_id="+playerId+" and game_id="+games_id,null);
				for(int i =0;i<k;i++)
				{
					Log.d("DEBUG","ADD FPG FRid="+ramkiId[i]+"  "+ramki[i].toString());
					ContentValues frameValues = new ContentValues();
					frameValues.put(FRAMEPG_FRAME_ID,ramkiId[i]);
					frameValues.put(FRAMEPG_PLAYER_ID,playerId);
					frameValues.put(FRAMEPG_GAME_ID, (int)id);
					frameValues.put(FRAMEPG_WHICH,i);
					frameValues.put(FRAMEPG_AIM,-1);
					frameValues.put(FRAMEPG_BALL_ID,-1);
					frameValues.put(FRAMEPG_BOARD,-1);
					db.insert(TABLE_FPG, null, frameValues);
				}
				/*teraz zaktualizowac id frame'ow w game
				values=new ContentValues();
				String where = "id='"+id+"'";
				db.update(TABLE_GAME_NAME, values, where, null);
				*/
			}
			else
			{
				Log.e("Error GAME_DB add","GAME_DB add");
				//db.endTransaction();
				return false;
			}
			/**
			 * end transaction ok!
			 */
				//db.setTransactionSuccessful();
				//db.endTransaction();
				return true;
	
		}catch(Exception e){
			Log.e("Error GAME_DB add", e.toString());
		}
		finally{
			//if(db.inTransaction())
				//db.endTransaction();
			db.close();
		}
		return false;

		
	}
	

}


