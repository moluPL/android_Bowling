package MyUtility;

import java.util.List;

public class MyGameFromDB {

	protected  int GAME_ID ;//= "_id";
   	protected  int GAME_PLAYER_ID ;//= "player_id";
   	protected  int GAME_MATCH_ID ;//= "match_id";
   	protected  String GAME_TYPE ;//= "type";	// game type *)training *)league *)tournament
   	protected  int GAME_RESULT ;//= "result";
   	protected  String GAME_STRIKES ;//= "game_strikes";
   	protected  String GAME_SPARES ;//= "game_spares";
   	protected  String GAME_OPENS ;//= "game_opens";
   	protected  String GAME_FRAMES ;//= "frames";	//text frames [0-9/0-9]
   	protected  List<String> GAME_FRAME1_12 ;//= "frame1 - 12";	//text frames [0-9/0-9]
	protected  String GAME_PINS ;//= "pins";		//text pins falls down 0-9, W-ends frame,N-none
   	protected  int GAME_PATTERN_LEFT ;//= "pattern_left";
   	protected  int GAME_PATTERN_RIGHT ;//= "pattern_right";
   	//protected  String GAME_BALL ;//= "game_ball";
   	protected  int GAME_WHICH ;//= "which";
   	
   	
	/**
	 * 
	 */
   	public MyGameFromDB() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * 
	 * @param gAME_ID
	 * @param gAME_PLAYER_ID
	 * @param gAME_MATCH_ID
	 * @param gAME_TYPE
	 * @param gAME_RESULT
	 * @param pattL
	 * @param pattR
	 * @param gAME_WHICH
	 */
	public MyGameFromDB(int gAME_ID, int gAME_PLAYER_ID, int gAME_MATCH_ID,
			String gAME_TYPE, int gAME_RESULT, int pattL,
			int pattR, int gAME_WHICH) {
		super();
		GAME_ID = gAME_ID;
		GAME_PLAYER_ID = gAME_PLAYER_ID;
		GAME_MATCH_ID = gAME_MATCH_ID;
		GAME_TYPE = gAME_TYPE;
		GAME_RESULT = gAME_RESULT;
		GAME_PATTERN_LEFT = pattL;
		GAME_PATTERN_RIGHT = pattR;
		GAME_WHICH = gAME_WHICH;
	}
	
	/**
	 * 
	 * @param gAME_ID
	 * @param gAME_PLAYER_ID
	 * @param gAME_MATCH_ID
	 * @param gAME_TYPE
	 * @param gAME_RESULT
	 * @param pattL
	 * @param pattR
	 * @param gAME_WHICH
	 */
	public MyGameFromDB(int gAME_ID, int gAME_PLAYER_ID, int gAME_MATCH_ID,
			String gAME_TYPE, int gAME_RESULT, int pattL,
			int pattR, int gAME_WHICH,List<String> frames) 
	{
				GAME_ID = gAME_ID;
				GAME_PLAYER_ID = gAME_PLAYER_ID;
				GAME_MATCH_ID = gAME_MATCH_ID;
				GAME_TYPE = gAME_TYPE;
				GAME_RESULT = gAME_RESULT;
				GAME_PATTERN_LEFT = pattL;
				GAME_PATTERN_RIGHT = pattR;
				GAME_WHICH = gAME_WHICH;
				GAME_FRAME1_12 = frames;
		
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString2() {
		StringBuilder builder = new StringBuilder();
		builder.append("MyGameFromDB [GAME_ID=");
		builder.append(GAME_ID);
		builder.append(", GAME_PLAYER_ID=");
		builder.append(GAME_PLAYER_ID);
		builder.append(", GAME_MATCH_ID=");
		builder.append(GAME_MATCH_ID);
		builder.append(", GAME_TYPE=");
		builder.append(GAME_TYPE);
		builder.append(", GAME_RESULT=");
		builder.append(GAME_RESULT);
		builder.append(", GAME_STRIKES=");
		builder.append(GAME_STRIKES);
		builder.append(", GAME_SPARES=");
		builder.append(GAME_SPARES);
		builder.append(", GAME_OPENS=");
		builder.append(GAME_OPENS);
		builder.append(", GAME_FRAMES=");
		builder.append(GAME_FRAMES);
		builder.append(", GAME_FRAME1_12=");
		builder.append(GAME_FRAME1_12);
		builder.append(", GAME_PINS=");
		builder.append(GAME_PINS);
		builder.append(", GAME_PATTERN_LEFT=");
		builder.append(GAME_PATTERN_LEFT);
		builder.append(", GAME_PATTERN_RIGHT=");
		builder.append(GAME_PATTERN_RIGHT);
		builder.append(", GAME_WHICH=");
		builder.append(GAME_WHICH);
		builder.append("]");
		return builder.toString();
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Game [GAME_ID=");
		builder.append(GAME_ID);
		builder.append(", GAME_PLAYER_ID=");
		builder.append(GAME_PLAYER_ID);
		builder.append(", GAME_MATCH_ID=");
		builder.append(GAME_MATCH_ID);
		builder.append(", GAME_TYPE=");
		builder.append(GAME_TYPE);
		builder.append(", GAME_RESULT=");
		builder.append(GAME_RESULT);
		builder.append(", GAME_PATTERN_LEFT=");
		builder.append(GAME_PATTERN_LEFT);
		builder.append(", GAME_PATTERN_RIGHT=");
		builder.append(GAME_PATTERN_RIGHT);
		builder.append(", GAME_WHICH=");
		builder.append(GAME_WHICH);
		builder.append("]");
		return builder.toString();
	}

   	/**
	 * @return the gAME_FRAME1_12
	 */
	public List<String> getGAME_FRAME1_12() {
		return GAME_FRAME1_12;
	}
	/**
	 * @param gAME_FRAME1_12 the gAME_FRAME1_12 to set
	 */
	public void setGAME_FRAME1_12(List<String> gAME_FRAME1_12) {
		GAME_FRAME1_12 = gAME_FRAME1_12;
	}
	public String getFrame(int number){
		return GAME_FRAME1_12.get(number);
		
	}
	
	/**
	 * @return the gAME_ID
	 */
	public int getGAME_ID() {
		return GAME_ID;
	}
	/**
	 * @param gAME_ID the gAME_ID to set
	 */
	public void setGAME_ID(int gAME_ID) {
		GAME_ID = gAME_ID;
	}
	/**
	 * @return the gAME_PLAYER_ID
	 */
	public int getGAME_PLAYER_ID() {
		return GAME_PLAYER_ID;
	}
	/**
	 * @param gAME_PLAYER_ID the gAME_PLAYER_ID to set
	 */
	public void setGAME_PLAYER_ID(int gAME_PLAYER_ID) {
		GAME_PLAYER_ID = gAME_PLAYER_ID;
	}
	/**
	 * @return the gAME_MATCH_ID
	 */
	public int getGAME_MATCH_ID() {
		return GAME_MATCH_ID;
	}
	/**
	 * @param gAME_MATCH_ID the gAME_MATCH_ID to set
	 */
	public void setGAME_MATCH_ID(int gAME_MATCH_ID) {
		GAME_MATCH_ID = gAME_MATCH_ID;
	}
	/**
	 * @return the gAME_TYPE
	 */
	public String getGAME_TYPE() {
		return GAME_TYPE;
	}
	/**
	 * @param gAME_TYPE the gAME_TYPE to set
	 */
	public void setGAME_TYPE(String gAME_TYPE) {
		GAME_TYPE = gAME_TYPE;
	}
	/**
	 * @return the gAME_RESULT
	 */
	public int getGAME_RESULT() {
		return GAME_RESULT;
	}
	/**
	 * @param gAME_RESULT the gAME_RESULT to set
	 */
	public void setGAME_RESULT(int gAME_RESULT) {
		GAME_RESULT = gAME_RESULT;
	}
	/**
	 * @return the gAME_PATTERN_LEFT
	 */
	public int getGAME_PATTERN_LEFT() {
		return GAME_PATTERN_LEFT;
	}
	/**
	 * @param gAME_PATTERN_LEFT the gAME_PATTERN_LEFT to set
	 */
	public void setGAME_PATTERN_LEFT(int gAME_PATTERN_LEFT) {
		GAME_PATTERN_LEFT = gAME_PATTERN_LEFT;
	}
	/**
	 * @return the gAME_PATTERN_RIGHT
	 */
	public int getGAME_PATTERN_RIGHT() {
		return GAME_PATTERN_RIGHT;
	}
	/**
	 * @param gAME_PATTERN_RIGHT the gAME_PATTERN_RIGHT to set
	 */
	public void setGAME_PATTERN_RIGHT(int gAME_PATTERN_RIGHT) {
		GAME_PATTERN_RIGHT = gAME_PATTERN_RIGHT;
	}
	/**
	 * @return the gAME_WHICH
	 */
	public int getGAME_WHICH() {
		return GAME_WHICH;
	}
	/**
	 * @param gAME_WHICH the gAME_WHICH to set
	 */
	public void setGAME_WHICH(int gAME_WHICH) {
		GAME_WHICH = gAME_WHICH;
	}
   	

}
