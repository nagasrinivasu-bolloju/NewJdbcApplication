package jdbc;
//this is the starting of the execution.
public class Main
{
	private static ServiceClass sc;
	public static void main(String[] arr)
	{
		try
		{
			int choice=Integer.parseInt(arr[0]);
			sc=new ServiceClass(arr);
			switch(choice)
			{
			case 1: //select
				sc.readTable();
				break;
			case 2: //insert
				sc.insertIntoTable();
				break;
			case 3: //delete
				sc.deleteFromTable();
				break;
			case 4: //update
				sc.updateTable();
				break;
			default: System.out.println("enter correct option!!");
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			System.out.println("Enter Correct Inputs in coorect format!!!");
		}
		catch(Exception e)
		{
			 e.printStackTrace();
		}
	}
}
