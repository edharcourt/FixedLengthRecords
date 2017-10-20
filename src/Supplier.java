
/**
 * Document this
 * @author ehar
 *
 */
public class Supplier {

	private int sno;
	private String name;
	private int status;
	private String location;
	
	public static final int NAMELEN = 10; 
	public static final int LOCATIONLEN = 10;

	private String resize(String s, int n) {
		StringBuilder sb = new StringBuilder(s);
		sb.setLength(n);
		return sb.toString();
	}
	
	public Supplier(int sno, String name, int status, String location) {
		this.sno = sno;
		this.name = resize(name, NAMELEN);
		this.status = status;
		this.location = resize(location, LOCATIONLEN);
	}
	
	public int getSno() { return sno; }

	public String getName() {
		return name;
	}

	public int getStatus() {
		return status;
	}

	public String getLocation() {
		return location;
	}
	
}
