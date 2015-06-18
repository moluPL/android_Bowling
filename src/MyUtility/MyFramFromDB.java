package MyUtility;

public class MyFramFromDB {

	private String fThrow1;
	private String fThrow2;
	private String throw1Id;
	private String throw2Id;
	
	
	public MyFramFromDB(String ft1,String ft2,String tid1,String tid2){
		fThrow1 = ft1;
		fThrow2 = ft2;
		throw1Id = tid1;
		throw2Id = tid2;
	}
	public MyFramFromDB(String[] param){
		fThrow1 = param[0];
		fThrow2 = param[1];
		throw1Id = param[2];
		throw2Id = param[3];
	}


	/**
	 * @return the fThrow1
	 */
	public String getfThrow1() {
		return fThrow1;
	}


	/**
	 * @param fThrow1 the fThrow1 to set
	 */
	public void setfThrow1(String fThrow1) {
		this.fThrow1 = fThrow1;
	}


	/**
	 * @return the fThrow2
	 */
	public String getfThrow2() {
		return fThrow2;
	}


	/**
	 * @param fThrow2 the fThrow2 to set
	 */
	public void setfThrow2(String fThrow2) {
		this.fThrow2 = fThrow2;
	}


	/**
	 * @return the throw1Id
	 */
	public String getThrow1Id() {
		return throw1Id;
	}


	/**
	 * @param throw1Id the throw1Id to set
	 */
	public void setThrow1Id(String throw1Id) {
		this.throw1Id = throw1Id;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MyFramFromDB [fThrow1=");
		builder.append(fThrow1);
		builder.append(", fThrow2=");
		builder.append(fThrow2);
		builder.append(", throw1Id=");
		builder.append(throw1Id);
		builder.append(", throw2Id=");
		builder.append(throw2Id);
		builder.append("]");
		return builder.toString();
	}
	/**
	 * @return the throw2Id
	 */
	public String getThrow2Id() {
		return throw2Id;
	}


	/**
	 * @param throw2Id the throw2Id to set
	 */
	public void setThrow2Id(String throw2Id) {
		this.throw2Id = throw2Id;
	}
}
