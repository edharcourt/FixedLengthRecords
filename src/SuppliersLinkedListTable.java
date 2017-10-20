import java.io.*;
/**
 * The implementation of the Suppliers Table using a linked list on disk.
 * 
 * This is a Singleton class.
 * 
 * @author ehar
 *
 */
public class SuppliersLinkedListTable implements SuppliersTable {

	private RandomAccessFile f;
	
	/**
	 * The head of our free list is a long (8 bytes)
	 */
	private static final int FREE_LIST_HEAD_SIZE = 8;
	
	/**
	 * The head of our list list is a long (8 bytes)
	 */
	private static final int LIST_HEAD_SIZE = 8;

	/**
	 * Our file header is just the two list heads.
	 */
	private static final int FILE_HEADER_SIZE = FREE_LIST_HEAD_SIZE + LIST_HEAD_SIZE;
	
	/**
	 * The next pointer in each record is a long.
	 */
	private static final int NEXTLEN = 8;
	
	/**
	 * The entire lenghth of a node in the linked list.
	 */
    private static final int NODELEN = 
    		SuppliersTableConstants.RECORDSIZE + NEXTLEN;

    /**
     * The byte offset of the free list head in the header.
     */
    private static final int FREE_LIST_HEAD_LOC = 0;

    /**
     * The byte offset of the list head in the header.
     */
    private static final int LIST_HEAD_LOC           =
    		FREE_LIST_HEAD_SIZE + FREE_LIST_HEAD_LOC;
    
    // singleton insance
	private static final SuppliersLinkedListTable instance  = 
			new SuppliersLinkedListTable();
	
	/**
	 * Constructor is private because this is a Singleton.
	 */
	private SuppliersLinkedListTable() {
		try {
		     f = new RandomAccessFile("suppliers.dat", "rw");
		}
		catch (FileNotFoundException e) {
			System.err.println("Error: could not open file suppliers.dat");
			System.err.println(e.getMessage());
			System.exit(1);
	    }	
		
		// initialize the free list head and list head
		try {
			f.writeLong(-1);
			f.writeLong(-1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		
	}
	
	/**
	 * Returns the single instance of this class.
	 * @return
	 */
	public static SuppliersLinkedListTable getInstance() {
		return instance;
	}

	/**
	 * Insert a Supplier into the table based on the primary key sno.
	 */
	@Override
	public void insert(Supplier s) throws IOException {
		
		long freepos = popFree();
		
		// get the list head
		f.seek(LIST_HEAD_LOC);
		long curr =  f.readLong();
		
		// inserting into an empty list, file ptr is right
		// where it is supposed to be
		if (curr == -1) {
			writeRecord(s, FILE_HEADER_SIZE, -1);			
			// update LIST_HEAD 
			f.seek(LIST_HEAD_LOC);
			f.writeLong(FILE_HEADER_SIZE);
			return;
		}
		
		// what is true? curr is pointing at head of list
		// freepos is still pointing at first free location
		f.seek(curr);
		int currsno = f.readInt();
		
		// check to see if inserting into the front
		if (s.getSno() < currsno) {
			// write data at freepos
			writeRecord(s,freepos,curr);	
			
			// update list head
	        f.seek(LIST_HEAD_LOC);
	        f.writeLong(freepos);
	        return;
		}
		
		// what is true? We are inserting into the middle (or end)
		// of the list
		long prev = -1;
		while ( curr != -1 && s.getSno() > currsno) {
			f.seek(curr);
			currsno = f.readInt();
			f.seek(curr + SuppliersTableConstants.RECORDSIZE);
			prev = curr;
			curr = f.readLong();
		}		
		
		// what is true?  prev and curr are at the right place
		writeRecord(s,freepos,curr);
		
		// patch up the previous pointer
		f.seek(prev + SuppliersTableConstants.RECORDSIZE);
		f.writeLong(freepos);
	}

	/**
	 * Helper method to write Supplier data at byte offset pos with next link.
	 * @param s
	 * @throws IOException
	 */
	private void writeRecord(Supplier s, long pos, long next) throws IOException {
		f.seek(pos);
		f.writeInt(s.getSno());
		f.writeChars(s.getName());
		f.writeInt(s.getStatus());
		f.writeChars(s.getLocation());
		f.writeLong(next);
	}

	/**
	 * Pop the the free list, if empty return EOF.
	 * 
	 * As we discovered the free list is a stack. When inserting we need 
	 * to pop items from the free list stack. 
	 * 
	 * @return The next free space on desk disk, if there are non then return the
	 *                  byte offset of the EOF.
	 * 
	 * @throws IOException
	 */
	private long popFree() throws IOException {
		// get the first free location
		f.seek(FREE_LIST_HEAD_LOC);
		long freepos = f.readLong();
		if (freepos == -1) {
			freepos = f.length();
		}
		else {
			f.seek(freepos);
			long v = f.readLong();
			f.seek(FREE_LIST_HEAD_LOC);
			f.writeLong(v);
		}
		return freepos;
	}

	/**
	 * Homework
	 */
	@Override
	public Supplier delete(int sno) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Homework
	 */
	@Override
	public Supplier get(int i) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

} // SuppliersLinkedListTable
