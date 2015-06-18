package MyUtility;

import android.content.Context;
import moja.paczka.namespace.R;



public class MyMatch {

	protected  String MyMatch_ID;// = "_id"; an id from DB
   	protected  String MyMatch_PLAYER_ID;// = "player_id"; an player_id from DB
   	protected  String MyMatch_TYPE;// = "type"; training/league/tournament
   	protected  String MyMatch_LGID;// = "league_id"; an id from DB if not =-1
   	protected  String MyMatch_TOURID;// = "tournament_id";an id from DB if not =-1
   	protected  String MyMatch_DATE;// = "date";
   	protected  String MyMatch_IS_TEAM;// = "is_team";bool 1 is 0 not
   	protected  String MyMatch_TEAM_ID;// = "team_id";an id from DB if not =-1
   	protected  String MyMatch_TOTAL;// = "total"; total pinfall
   	protected  String MyMatch_AVG;// = "average"; average pinfall/gamesnumber
	protected String MyMatch_NMBR_GAMES;// = "numberOfGames";
	protected Context context;
	/**
	 * constructor void
	 */
	public MyMatch() {

	}
	
	/**
	 * Constructor with params
	 * @param id
	 * @param playerId
	 * @param type
	 * @param lgid
	 * @param tourid
	 * @param date
	 * @param isteam
	 * @param teamid
	 * @param total
	 * @param avg
	 * @param nmbrG
	 */
	public MyMatch(String id,String playerId,String type,String lgid,String tourid,String date,String isteam,String teamid,String total,String avg,String nmbrG){
		try{
			if(id!=null && id.length()>0)
				MyMatch_ID=id;
			else
				throw new IllegalArgumentException("argument can't be empty!");
			if(playerId!=null && playerId.length()>0)
				MyMatch_PLAYER_ID=playerId;
			else
				throw new IllegalArgumentException("n must be positive");
			if(type!=null && type.length()>0)
				MyMatch_TYPE=type;
			else
				throw new IllegalArgumentException("n must be positive");
			if(lgid!=null && lgid.length()>0)
				MyMatch_LGID=lgid;
			else
				throw new IllegalArgumentException("n must be positive");
			if(tourid!=null && tourid.length()>0)
				MyMatch_TOURID=tourid;
			else
				throw new IllegalArgumentException("n must be positive");
			if(date!=null && date.length()>0)
				MyMatch_DATE=date;
			else
				throw new IllegalArgumentException("n must be positive");
			if(isteam!=null && isteam.length()>0)
				MyMatch_IS_TEAM=isteam;
			else
				throw new IllegalArgumentException("n must be positive");
			if(teamid!=null && teamid.length()>0)
				MyMatch_TEAM_ID=teamid;
			else
				throw new IllegalArgumentException("n must be positive");
			if(total!=null && total.length()>0)
				MyMatch_TOTAL=total;
			else
				throw new IllegalArgumentException("n must be positive");
			if(avg!=null && avg.length()>0)
				MyMatch_AVG=avg;
			else
				throw new IllegalArgumentException("n must be positive");
			if(nmbrG!=null && nmbrG.length()>0)
				MyMatch_NMBR_GAMES=nmbrG;
			else
				throw new IllegalArgumentException("n must be positive");
				
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	/**
	 * Set context 
	 * @param con
	 */
	public void setContext(Context con){
		if(con!=null)
			context=con;
	}

	/**
	 * @return the myMatch_ID
	 */
	public String getMyMatch_ID() {
		return MyMatch_ID;
	}

	/**
	 * @return the myMatch_PLAYER_ID
	 */
	public String getMyMatch_PLAYER_ID() {
		return MyMatch_PLAYER_ID;
	}

	/**
	 * @return the myMatch_TYPE
	 */
	public String getMyMatch_TYPE() {
		return MyMatch_TYPE;
	}

	/**
	 * @return the myMatch_LGID
	 */
	public String getMyMatch_LGID() {
		return MyMatch_LGID;
	}

	/**
	 * @return the myMatch_TOURID
	 */
	public String getMyMatch_TOURID() {
		return MyMatch_TOURID;
	}

	/**
	 * @return the myMatch_DATE
	 */
	public String getMyMatch_DATE() {
		return MyMatch_DATE;
	}

	/**
	 * @return the myMatch_IS_TEAM
	 */
	public String getMyMatch_IS_TEAM() {
		return MyMatch_IS_TEAM;
	}

	/**
	 * @return the myMatch_TEAM_ID
	 */
	public String getMyMatch_TEAM_ID() {
		return MyMatch_TEAM_ID;
	}

	/**
	 * @return the myMatch_TOTAL
	 */
	public String getMyMatch_TOTAL() {
		return MyMatch_TOTAL;
	}

	/**
	 * @return the myMatch_AVG
	 */
	public String getMyMatch_AVG() {
		return MyMatch_AVG;
	}

	/**
	 * @return the myMatch_NMBR_GAMES
	 */
	public String getMyMatch_NMBR_GAMES() {
		return MyMatch_NMBR_GAMES;
	}

	/**
	 * @param myMatch_ID the myMatch_ID to set
	 */
	public void setMyMatch_ID(String myMatch_ID) {
		MyMatch_ID = myMatch_ID;
	}

	/**
	 * @param myMatch_PLAYER_ID the myMatch_PLAYER_ID to set
	 */
	public void setMyMatch_PLAYER_ID(String myMatch_PLAYER_ID) {
		MyMatch_PLAYER_ID = myMatch_PLAYER_ID;
	}

	/**
	 * @param myMatch_TYPE the myMatch_TYPE to set
	 */
	public void setMyMatch_TYPE(String myMatch_TYPE) {
		MyMatch_TYPE = myMatch_TYPE;
	}

	/**
	 * @param myMatch_LGID the myMatch_LGID to set
	 */
	public void setMyMatch_LGID(String myMatch_LGID) {
		MyMatch_LGID = myMatch_LGID;
	}

	/**
	 * @param myMatch_TOURID the myMatch_TOURID to set
	 */
	public void setMyMatch_TOURID(String myMatch_TOURID) {
		MyMatch_TOURID = myMatch_TOURID;
	}

	/**
	 * @param myMatch_DATE the myMatch_DATE to set
	 */
	public void setMyMatch_DATE(String myMatch_DATE) {
		MyMatch_DATE = myMatch_DATE;
	}

	/**
	 * @param myMatch_IS_TEAM the myMatch_IS_TEAM to set
	 */
	public void setMyMatch_IS_TEAM(String myMatch_IS_TEAM) {
		MyMatch_IS_TEAM = myMatch_IS_TEAM;
	}

	/**
	 * @param myMatch_TEAM_ID the myMatch_TEAM_ID to set
	 */
	public void setMyMatch_TEAM_ID(String myMatch_TEAM_ID) {
		MyMatch_TEAM_ID = myMatch_TEAM_ID;
	}

	/**
	 * @param myMatch_TOTAL the myMatch_TOTAL to set
	 */
	public void setMyMatch_TOTAL(String myMatch_TOTAL) {
		MyMatch_TOTAL = myMatch_TOTAL;
	}

	/**
	 * @param myMatch_AVG the myMatch_AVG to set
	 */
	public void setMyMatch_AVG(String myMatch_AVG) {
		MyMatch_AVG = myMatch_AVG;
	}

	/**
	 * @param myMatch_NMBR_GAMES the myMatch_NMBR_GAMES to set
	 */
	public void setMyMatch_NMBR_GAMES(String myMatch_NMBR_GAMES) {
		MyMatch_NMBR_GAMES = myMatch_NMBR_GAMES;
	}
	
	public String toString(Context con)
	{
		setContext(con);
		StringBuilder sb = new StringBuilder();
		
		
		sb.append(context.getString(R.string.Match_info_Date)+MyMatch_DATE+" \n ");
		sb.append(context.getString(R.string.Match_info_type)+MyMatch_TYPE+" ");
		sb.append(context.getString(R.string.Match_info_total)+MyMatch_TOTAL+" ");
		sb.append(context.getString(R.string.Match_info_avg)+MyMatch_AVG+" ");
		sb.append(context.getString(R.string.Match_info_gamesNo)+MyMatch_NMBR_GAMES+"");
	   	/*
	   	protected  String MyMatch_TYPE;// = "type"; training/league/tournament
	   	protected  String MyMatch_LGID;// = "league_id"; an id from DB if not =-1
	   	protected  String MyMatch_TOURID;// = "tournament_id";an id from DB if not =-1
	   	protected  String MyMatch_DATE;// = "date";
	   	protected  String MyMatch_IS_TEAM;// = "is_team";bool 1 is 0 not
	   	protected  String MyMatch_TEAM_ID;// = "team_id";an id from DB if not =-1
	   	protected  String MyMatch_TOTAL;// = "total"; total pinfall
	   	protected  String MyMatch_AVG;// = "average"; average pinfall/gamesnumber
		protected String MyMatch_NMBR_GAMES;
		sb.append()
		*/
	   	return sb.toString();
	}

}


