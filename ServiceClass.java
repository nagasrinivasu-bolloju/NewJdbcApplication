package jdbc;

//import java.sql.SQLException;
import java.util.HashMap;

public class ServiceClass implements serviceInterface
{
	private int empId;
	private String firstName,lastName,address,addrId,salary;
	private String arr[];
	DaoClass db;
	public ServiceClass(String[] arr)
	{
		this.arr=arr;
		db=new DaoClass();
		if(db.connect())
				System.out.println("Connection successfull!!!");
		else
			System.out.println("Connection failed!!!");
	}
	public void insertIntoTable()
	{
		try
		{
		empId=Integer.parseInt(arr[1]);
		firstName=arr[2];
		lastName=arr[3];
		addrId=arr[5];
		address=arr[6];
		salary=arr[4];
		db.insert(empId,firstName,lastName,salary,addrId,address);
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			System.out.println("Enter the details completely in the form:\\n option empid firstname lastname salary addrid address.\");");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void deleteFromTable()
	{
		try
		{
		empId=Integer.parseInt(arr[1]);
		db.delete(empId);
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			System.out.println("Enter empId to delete row!!!");
		}
	}
	public void updateTable()
	{
		try
		{
		 empId=Integer.parseInt(arr[1]);
		 HashMap<String,String> dict=new HashMap<>();
		 dict.put("empId", arr[1]);
		 if(!arr[2].equalsIgnoreCase("nochange"))
		 {
			 dict.put("firstName",arr[2]);
		 }
		 if(!arr[3].equalsIgnoreCase("nochange"))
		 {
			 dict.put("lastName",arr[3]);
		 }
		 if(!arr[4].equalsIgnoreCase("nochange"))
		 {
			 dict.put("salary",arr[4]);
		 }
		 if(!arr[5].equalsIgnoreCase("nochange"))
		 {
			 dict.put("addrId",arr[5]);
		 }
		 if(!arr[6].equalsIgnoreCase("nocahange"))
		 {
			 dict.put("address",arr[6]);
		 }
		 db.update(dict);
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			System.out.println("Enter correct inputsin the form:\n option empid firstname lastname salary addrid address.");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void readTable()
	{
		try
		{
		db.readData();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
