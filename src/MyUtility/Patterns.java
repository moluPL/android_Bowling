package MyUtility;

import android.util.Log;


  public class Patterns
  {
	  private String id,name,link,volume,dba,opb,length,ratio;
	  private Patterns_DB database;
	  
	  public Patterns(String name,String link,String lenght,String volume,String opb,String dba,String ratio)
	  {
		  id=null;
		  this.name = name;
		  this.length = lenght;
		  this.link = link;
		  this.volume = volume;
		  this.opb = opb;
		  this.dba = dba;
		  this.ratio = ratio;
		  
	  }
	  public Patterns(String id,String name,String link,String lenght,String volume,String opb,String dba,String ratio)
	  {
		  this.id=id;
		  this.name = name;
		  this.length = lenght;
		  this.link = link;
		  this.volume = volume;
		  this.opb = opb;
		  this.dba = dba;
		  this.ratio = ratio;
		  
	  }
	  public Patterns(){
		  id=null;
		  this.name = null;
		  this.length = null;
		  this.link = null;
		  this.volume = null;
		  this.opb = null;
		  this.dba = null;
		  this.ratio = null;
	  }
	  public java.lang.String GetStringAll(){
		  String temp = "Patt: id:"+id+"\n"+name+"\n"+link+"\n"+length+"\n"+ratio+"\n"+opb+"\n"+dba+"\n"+volume+"\n"+
	  "-------------------------------------------------------------------------------------------\n";
		  return temp;
	  }
	  
	  /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Patterns [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", link=");
		builder.append(link);
		builder.append(", volume=");
		builder.append(volume);
		builder.append(", dba=");
		builder.append(dba);
		builder.append(", opb=");
		builder.append(opb);
		builder.append(", length=");
		builder.append(length);
		builder.append(", ratio=");
		builder.append(ratio);
		builder.append("]");
		return builder.toString();
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
	}
	/**
	 * @return the volume
	 */
	public String getVolume() {
		return volume;
	}
	/**
	 * @return the dba
	 */
	public String getDba() {
		return dba;
	}
	/**
	 * @return the opb
	 */
	public String getOpb() {
		return opb;
	}
	/**
	 * @return the length
	 */
	public String getLength() {
		return length;
	}
	/**
	 * @return the ratio
	 */
	public String getRatio() {
		return ratio;
	}
	/**
	 * @return the database
	 */
	public Patterns_DB getDatabase() {
		return database;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @param link the link to set
	 */
	public void setLink(String link) {
		this.link = link;
	}
	/**
	 * @param volume the volume to set
	 */
	public void setVolume(String volume) {
		this.volume = volume;
	}
	/**
	 * @param dba the dba to set
	 */
	public void setDba(String dba) {
		this.dba = dba;
	}
	/**
	 * @param opb the opb to set
	 */
	public void setOpb(String opb) {
		this.opb = opb;
	}
	/**
	 * @param length the length to set
	 */
	public void setLength(String length) {
		this.length = length;
	}
	/**
	 * @param ratio the ratio to set
	 */
	public void setRatio(String ratio) {
		this.ratio = ratio;
	}
	/**
	 * @param database the database to set
	 */
	public void setDatabase(Patterns_DB database) {
		this.database = database;
	}
	public boolean addToDB(Patterns_DB datab)
	  {
		  try{
			  //database = new Patterns_DB(null);
			  datab.addPattern(name,length,link,ratio,volume,opb,dba);
			  datab.close();
		  }catch(Exception e){
			  Log.e("XmlToDb Error",e.toString());
			  datab.close();
			  return false;
		  }
		return true;
					  
	  }
  }