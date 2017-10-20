import static org.junit.Assert.*;

import org.junit.Test;


public class InsertTest {

	@Test
	public void createSupplierTable() throws Exception {
		   SuppliersTable st = SuppliersLinkedListTable.getInstance();
	       assertNotEquals(st, null);
	}

}
