
public abstract class SuppliersTableConstants {
	
	// constants on size of attributes of 
	 static final int NAMELEN = Supplier.NAMELEN * 2;        // char is 2 bytes 
	 static final int LOCATIONLEN = Supplier.LOCATIONLEN * 2;
	 static final int SNOLEN = 4;
	 static final int STATUSLEN = 4;
	 static final int RECORDSIZE = 
			NAMELEN + LOCATIONLEN + SNOLEN + STATUSLEN;
}
