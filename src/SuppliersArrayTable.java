import java.io.*;

public class SuppliersArrayTable 
     implements SuppliersTable  {

    private RandomAccessFile f;
    
	private static final SuppliersArrayTable 
	       instance = new SuppliersArrayTable();
	
	private SuppliersArrayTable() {
		try {
		     f = new RandomAccessFile("suppliers.dat", "rw");
		}
		catch (FileNotFoundException e) {
			System.err.println("Error: could not open file suppliers.dat");
			System.err.println(e.getMessage());
			System.exit(1);
	    }
		
	}
	
	public static SuppliersArrayTable getInstance() {
		return instance;
	}
	
	/**
	 * 
	 * @param sno - <b>Primary Key</b> The serial number of the supplier
	 * @param name
	 * @param status
	 * @param location
	 * 
	 * preconditions:
	 *     sno: sno >= 0 and sno not already in the  file
	 *     name: name != null and name.length() > 0
	 * @throws IOException 
	 */
	public void insert(Supplier s) throws IOException {
		f.seek(f.length());  // move file pointer to end of file (EOF)
		f.writeInt(s.getSno());
		f.writeChars(s.getName());
		f.writeInt(s.getStatus());
		f.writeChars(s.getLocation());
	}

	private String readString(int n) throws IOException {
		String tmp  = "";
		for (int i = 0; i < n; i++)
			tmp += f.readChar();
		
		return tmp;
	}
	
	/**
	 * 
	 * @param 0 <= i < f.length()/RECORDSIZE
	 * @return The i<sup>th</sup> supplier.
	 * @throws IOException
	 */
	public Supplier get(int i) throws IOException {
		f.seek(i * SuppliersTableConstants.RECORDSIZE);
		int sno = f.readInt();
		String name = readString(Supplier.NAMELEN);
		int status = f.readInt();
		String location = readString(Supplier.LOCATIONLEN);
		return new Supplier(sno, name, status, location);
	}

	@Override
	public Supplier delete(int sno) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
