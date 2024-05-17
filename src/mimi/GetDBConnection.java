package mimi;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;
//书上的例子，已经封装好的类，可以直接使用~
public class GetDBConnection
{
	public static Connection connectDB(String DBName,String id,String p)
	{
		Connection con=null;
		//String uri="jdbc:mysql://localhost:3306/"+DBName+"?useSSL=true&characterEncoding=utf-8";
		String uri="jdbc:mysql://127.0.0.1:3306/dataku?useUnicode=true&characterEncoding=UTF-8&userSSL=false&serverTimezone=GMT%2B8";
		try
		{
			//Class.forName("com.mysql.jdbc.Driver");
			Class.forName("com.mysql.cj.jdbc.Driver");
		}
		catch(Exception e)
		{
			System.out.println("没有成功1");
		}
		try{
			//System.out.println("**");
			con=DriverManager.getConnection(uri,id,p);

		}
		catch(SQLException e)
		{
			System.out.println("没有成功2");
		}
		return con;
	}
}