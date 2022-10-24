package jdbc;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;

public class DaoClass implements DaoInterface
{
	private final String url="jdbc:postgresql://localhost:5432/DemoDB";
	private final String username="postgres";
	private final String password="srinu534";
	private Connection connection =null;
	private Statement statement=null;
	private PreparedStatement prepStatementEmp=null;
	private PreparedStatement prepStatementAddr=null;
	
	public boolean connect()
	{
		try
		{
			connection=DriverManager.getConnection(url,username,password);
			if(connection!=null)
			{
				//System.out.println("Connection build succesfully!!");
				try
				{
				statement=connection.createStatement();	// for statement also create one method and keep in try catch..
				return true;
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				
			}
			else
			{
				return false;
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	public void insert(int empId,String firstName,String lastName,String salary,String addrId,String address)
	{ 
		
		String sql="insert into employee values(?,?,?,?,?)";
		int rows1;
		try
		{
		prepStatementEmp=connection.prepareStatement(sql);
		ResultSet rs=statement.executeQuery("select addrid from address where addrid="+addrId);
		if(!rs.next() && !addToAddress(addrId,address))	 //if addrid not present in addresstable or addtoaddress() fails then return false.
		{
			System.out.println("Unable to insert!!");
			return;
		}
		prepStatementEmp.setInt(1,empId);
		prepStatementEmp.setString(2,firstName);
		prepStatementEmp.setString(3,lastName);
		prepStatementEmp.setFloat(4,Float.parseFloat(salary));
		prepStatementEmp.setInt(5,Integer.parseInt(addrId));
		rows1=prepStatementEmp.executeUpdate();
		if(rows1>0)
			System.out.println("inserted "+rows1+" successfully.");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
    public void delete(int empId)
    {
    	try
    	{
    		if(statement.executeUpdate("delete from employee where empid="+empId)>0)
    		{
    			System.out.println("\nDeleted 1 row successfully..");
    		}
    		
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
    
    public boolean addToAddress(String addrId,String address)
    {
    	String sql2="insert into address values(?,?)";
    	int rows=0;
    	try
    	{
    	prepStatementAddr=connection.prepareStatement(sql2);
    	prepStatementAddr.setInt(1,Integer.parseInt(addrId));
		prepStatementAddr.setString(2,address);
		rows=prepStatementAddr.executeUpdate();
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	if(rows>0)
			return true;
		return false;
    }
    public void update(HashMap<String,String> dict)
    {
    	int empId=Integer.parseInt(dict.remove("empId"));
    	if(dict.containsKey("addrId"))			//here need to update address table also..
    	{
    		String addrId=dict.get("addrId");
    		ResultSet rs;
			try
			{
				rs = statement.executeQuery("select addrid from address where addrid="+addrId);
				if(!rs.next())
	    		{
					if(dict.containsKey("address"))		//there may be a chance of giving addrid without giving the address. by the user..
					{
						addToAddress(addrId,dict.get("address"));
					}
					else
					{
						System.out.println("address needed along with new addrId!!");
						return;
					}
	    		}
	    		else
	    		{
	    			//no need to add since addrid is already present in the address table;
	    		}
	    		
			}
			catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    	}
    	//finally after checking addrId,if address is present then remove it irrespective of updation of addrId.
    	//ex: euser asks to update adress without giving respective addrId..in that case remove that address as below..
    	if(dict.containsKey("address"))
			dict.remove("address");		
    	StringBuilder sql=new StringBuilder("update employee set ");
    	Iterator<String> it=dict.keySet().iterator();
    	while(it.hasNext())
    	{
    		String key=it.next();
    		String value=dict.get(key);
//    		if(key=="addrId")
//    			sql.append(key+"="+value+",");
//    		else
    		sql.append(key+"='"+value+"',");
    		System.out.println(key+" "+value);
    	}
    	sql.deleteCharAt(sql.length()-1);
    	sql.append(" where empId="+empId);
    	String s=sql.toString();
    	System.out.println(s);
    	try
    	{
    		System.out.println(s);
    		if(statement.executeUpdate(s)>0)
    			System.out.println("\nupdated one row successfully..");
    	
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	
    }
    public void readData()
    {
    	try
    	{
    	ResultSet rs=statement.executeQuery("select * from employee inner join address on employee.addrId=address.addrId;");
    	while(rs.next())
    	{
    		System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getFloat(4)+" "+rs.getString(5)+" "+rs.getString(7));
    	}
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
}
