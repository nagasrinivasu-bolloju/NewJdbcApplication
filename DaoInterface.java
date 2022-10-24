package jdbc;

import java.util.HashMap;

public interface DaoInterface
{
	boolean connect();
	void insert(int empId,String firstName,String lastName,String salary,String addrId,String address);
	void delete(int empId);
	void update(HashMap<String,String> dict);
	void readData();
}
