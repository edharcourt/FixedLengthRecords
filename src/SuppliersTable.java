import java.io.IOException;
import java.io.*;

/**
 * @author ehar
 *
 */
public interface SuppliersTable {
	void insert(Supplier s) throws IOException;
	Supplier delete(int sno) throws IOException;
    Supplier get(int i) throws IOException;
}