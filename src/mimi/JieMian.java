package mimi;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
//登录操作和转入注册操作
/*
 * 定义了一个名为JieMian的类，它继承了JFrame类并实现了ActionListener接口。
 * JFrame类是Java中用于创建图形用户界面的类，ActionListener接口是Java中用于处理事件的接口，它需要实现actionPerformed方法来响应事件。
 */
public class JieMian extends JFrame implements ActionListener //大类：登录界面
{
	private static final long serialVersionUID = 1L; //serialVersionUID 的存在是为了确保序列化和反序列化的版本兼容性。
	/*
	 * JLabel标签 JTextField文本框  JTextArea文本区  JCheckBox复选框  JComboBox下拉列表
	 */
	JLabel beiJing,labelA,labelB;
	JTextField textA,textB;
	JButton button,show_button,zhuce_button;
	JieMian()
	{
		//美化窗口和界面的函数（不能用）
		/*
		try
        {
            //UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        }
        catch (Exception e)
        {
            System.out.println("Substance Raven Graphite failed to initialize");
        }
        */
		init();
		setVisible(true); //对部件是否可见。
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //设置用户在此框架上启动‘关闭’时默认执行的操作。
	}
	/*
	 * public void 非静态方法，属于对象，在对象初始化后才能被调用；
	 * public static void 静态方法，属于类，可以使用【类名.方法名】直接调用。
	 */
	public void init()//初始化第一界面
	{
		setLayout(new BorderLayout()); //布局管理器。
		JPanel p=new JPanel(); //JPanel是Java图形化界面中常用的容器，面板。
		p.setLayout(null); //设置空布局即自由布局
		
		Color c=new Color(188,143,143);
		Color c2=new Color(220,220,220);
		p.setBackground(c); //设置背景颜色
		
		/*
		 * setBounds(窗口的x坐标，窗口的y坐标，窗口的宽度，窗口的高度)
		 */
		//标签和文本框
		labelA=new JLabel("用户名：");
		labelA.setBounds(90,50,70,25);
		p.add(labelA);
		textA=new JTextField(10);
		textA.setBounds(180,50,100,25);
		textA.setBackground(c2);
		p.add(textA);
		//标签和文本框
		labelB=new JLabel("密  码：");
		labelB.setBounds(90,100,70,25);
		p.add(labelB);
		textB=new JTextField(10);
		textB.setBounds(180,100,100,25);
		textB.setBackground(c2);
		p.add(textB);
		//登录
		button=new JButton("登录");
		button.setBounds(160,170,80,25);
		p.add(button);
		//注册
		zhuce_button=new JButton("注册");
		zhuce_button.setBounds(250,170,80,25);
		p.add(zhuce_button);
		//显示信息
		show_button=new JButton("显示信息");
		show_button.setBounds(160,250,100,25);
		p.add(show_button);
		//面板添加到布局管理器
		add(p,BorderLayout.CENTER);
		
		//给实例添加事件监听接口
		/*
		 * this表示当前类的对象，在一个类里，你不需要new他的实例就直接可以用this调用它的方法和属性
		 */
		button.addActionListener(this);
		zhuce_button.addActionListener(this);
		show_button.addActionListener(this);
	}
	/*
	 * 事件触发ActionEvent事件后，监视器调用接口中的方法actionPerformed(ActionEvent e)对发生的事件做出处理。
	 */
	public void actionPerformed(ActionEvent e)//事件监听器做出处理
	{
		//点击登录
		if(e.getSource()==button)
		{
			dispose();//是用来关闭一个GUI界面的
			String yongHuMing=textA.getText();//用户输入在文本框里的用户名
			//System.out.println("用户输入在文本框里的用户名"+yongHuMing);
			String miMa=textB.getText();//用户输入在文本框里的密码
			//System.out.println("用户输入在文本框里的密码"+miMa);
			String s=Select_date.getData(yongHuMing);//获得该用户名在数据库中录入的密码
			//System.out.println("获得该用户名在数据库中录入的密码"+s);
			if(miMa.compareTo(s)==0)//用户输入了正确的密码  转入选择界面
			{
				System.out.println("登陆成功！");
						
				Xuanze_JieMian x=new Xuanze_JieMian();
				x.setBounds(200,200,450,350);
				x.setTitle("选择界面");

			}
			else
			{
				System.out.println("登录失败！");
			}

		}
		//点击转入注册
		if(e.getSource()==zhuce_button)
		{
			dispose();
			Zhuce_JieMian j=new Zhuce_JieMian();
			
			j.setBounds(200,200,450,350);
			j.setTitle("注册界面");
		}
		//显示信息
		if(e.getSource()==show_button)
		{
			Query.show_table();//以表格的形式显示数据库中的数据
		}
	}
}

class Xuanze_JieMian extends JFrame implements ActionListener //大类：选择功能界面
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton button,button_A,button_B,button_AI,button_Dapu;

	Xuanze_JieMian()
	{
		init();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void init()//初始化注册界面
	{
		setLayout(new BorderLayout());
		JPanel p=new JPanel();
		p.setLayout(null);
		
		Color c=new Color(188,143,143);
		p.setBackground(c);
		
		button_Dapu=new JButton("六子棋打谱");
		button_Dapu.setBounds(130,40,200,25);
		p.add(button_Dapu);
		
		button=new JButton("六子棋");
		button.setBounds(130,80,200,25);
		p.add(button);

		button_A=new JButton("联机六子棋（黑方）");
		button_A.setBounds(130,120,200,25);
		p.add(button_A);

		button_B=new JButton("联机六子棋（白方）");
		button_B.setBounds(130,200,200,25);
		p.add(button_B);
		
		button_AI=new JButton("人机下棋");
		button_AI.setBounds(130,240,200,25);
		p.add(button_AI);

		add(p,BorderLayout.CENTER);

		button.addActionListener(this);
		button_A.addActionListener(this);
		button_B.addActionListener(this);
		button_AI.addActionListener(this);
		button_Dapu.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e)
	{
		//转到相应程序中
		if(e.getSource()==button_Dapu)
		{
			dispose();
			//转到六子棋打谱界面
	 		Menu p=new Menu();
	 		WindowMouse win = new WindowMouse(p);
			p.add(win);
	    	p.setTitle("六子棋打谱");
			p.setBounds(150,10,800,600);
	    	p.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    	p.setVisible(true);
		}
		
		if(e.getSource()==button)
		{
			dispose();
			//转到六子棋界面
			JFrame p=new JFrame();
			C6_WindowMouse win = new C6_WindowMouse(p);
			
			p.add(win);
   			p.setTitle("人人六子棋");
			p.setBounds(150,10,800,600);
			Color c=new Color(188,143,143);
			p.setBackground(c);
   			p.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  			p.setVisible(true);
		}
		if(e.getSource()==button_AI)
		{
			dispose();
			//转到人机下棋界面
			JFrame p=new JFrame();
			AI_WindowMouse win = new AI_WindowMouse(p);
			
			p.add(win);
   			p.setTitle("人机下棋");
			p.setBounds(150,10,800,600);
			
			Color c=new Color(188,143,143);
			p.setBackground(c);
			
   			p.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  			p.setVisible(true);
		}
		if(e.getSource()==button_A)
		{
			dispose();
			//服务端（黑方）
			Server_WindowMouse win = new Server_WindowMouse();
			JFrame p=new JFrame();
			p.add(win);
	      	p.setTitle("Server");
	      	
			p.setBounds(150,10,800,600);
	      	p.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      	p.setVisible(true);
		}
		if(e.getSource()==button_B)
		{
			dispose();
			//客户端（白方）
			Client_WindowMouse win = new Client_WindowMouse();
			JFrame p=new JFrame();
			p.add(win);
	      	p.setTitle("Client");
	      	
			p.setBounds(150,10,800,600);
	      	p.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      	p.setVisible(true);
		}
		

	}
}


class Zhuce_JieMian extends JFrame implements ActionListener //大类：注册界面
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel beiJing,labelA,labelB;
	JTextField textA,textB;
	JButton button,show_button,zhuce_button;

	Zhuce_JieMian()
	{
		init();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void init()//初始化注册界面
	{
		setLayout(new BorderLayout());
		JPanel p=new JPanel();
		p.setLayout(null);
		
		Color c=new Color(188,143,143);
		p.setBackground(c);

		labelA=new JLabel("用户名：");
		labelA.setBounds(90,50,70,25);
		p.add(labelA);
		textA=new JTextField(10);
		textA.setBounds(180,50,100,25);
		p.add(textA);

		labelB=new JLabel("密  码：");
		labelB.setBounds(90,100,70,25);
		p.add(labelB);
		textB=new JTextField(10);
		textB.setBounds(180,100,100,25);
		p.add(textB);

		zhuce_button=new JButton("注册");
		zhuce_button.setBounds(180,170,80,25);
		p.add(zhuce_button);

		add(p,BorderLayout.CENTER);

		zhuce_button.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e)
	{
		//将注册信息写入数据库，并转入注册成功界面
		if(e.getSource()==zhuce_button)
		{
			String yongHuMing=textA.getText();//获得用户输入的用户名
			String miMa=textB.getText();//获得用户输入的密码
			Insert_userInformation.setData(yongHuMing,miMa);//将用户名和密码写入数据库

			Xinxi x=new Xinxi();
			Color c=new Color(255,182,193);
			x.setBackground(c);
			x.setBounds(200,200,450,350);
			x.setTitle("注册成功提示");
		}
	}
}
//注册成功提示界面
class Xinxi extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel xinxi;
	Xinxi()
	{
		init();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void init()
	{
		setLayout(new BorderLayout());
		//JPanel p=new JPanel();
		xinxi=new JLabel("注册成功");
		add(xinxi,BorderLayout.CENTER);
	}
}

//将用户名和密码写入数据库的Connect表中
class Insert_userInformation
{
	public static void setData(String textA,String textB)
	{
		Connection con;
		//Statement sql;
		PreparedStatement preSql;
		ResultSet rs;

		con=GetDBConnection.connectDB("dataku.student","root","myl000412");//连接数据库
		if(con==null)return;
		
		String sqlStr="insert into dataku.student values(?,?)";//插入语句
		try
		{
			//sql=con.createStatement();
			preSql=con.prepareStatement(sqlStr);

			preSql.setString(1,textA);
			preSql.setString(2,textB);
			preSql.executeUpdate();
			rs=preSql.executeQuery();
			while(rs.next())
			{
				String name=rs.getString(1);
				String mima=rs.getString(2);


				System.out.printf("%s\t",name);
				System.out.printf("%s\t",mima);
			}
			con.close();
		}
		catch(SQLException e)
		{
			System.out.println("execption"+e);
		}
	}
}
//查询操作
class Select_date
{
	public static String getData(String yongHuMing)
	{
		String mima=null;//查询到的数据库中的密码
		Connection con;
		Statement sql;
		//PreparedStatement preSql;
		ResultSet rs;

		con=GetDBConnection.connectDB("dataku.student","root","myl000412");//连接数据库
		if(con==null)return"";
		String sqlStr="select mima from dataku.student where name = '"+yongHuMing+"'";//查询数据库的语句
		try
		{
			sql=con.createStatement();
			//preSql=con.prepareStatement();
			rs=sql.executeQuery(sqlStr);
			//rs=preSql.executeQuery();
			while(rs.next())
			{
				//String name=rs.getString(1);
				mima=rs.getString(1);//查询到的密码
			}
			con.close();//关闭连接
		}
		catch(SQLException e)
		{
			System.out.println("execption"+e);
		}

		return mima;
	}
}

//将数据库中的表以组件(JTable)的形式显示
class Query
{
	public static void show_table()
	{
		String [] tableHead;//表头(name,mima)
		String [][] content;//表中内容
		JTable table;//组件		//定义显示表格相关
		JFrame win= new JFrame();

		Query findRecord = new  Query();//定义类的对象
		findRecord.setDatabaseName("dataku.student");
	    findRecord.setSQL("select * from dataku.student");
		content = findRecord.getRecord();//获得二维数组中的全部记录
		tableHead=findRecord.getColumnName();//获得全部列名

		table = new JTable(content,tableHead);//(表中内容，表头)

		win.add(new JScrollPane(table));
		win.setBounds(12,100,400,200);
		win.setVisible(true);
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	String databaseName="";//数据库名
	String SQL;//SQL语句
	String [] columnName;//全部列名
	String [][] record;//查询到的记录
   public Query()
   {
      try
      	{
      		Class.forName("com.mysql.cj.jdbc.Driver");//加载JDBC-MySQL驱动
      	}
      catch(Exception e){}
   }

   //设置要查询的数据库的名称
   public void setDatabaseName(String s)
   {
      databaseName=s.trim();
   }

   //设置要执行操作的SQL语句
   public void setSQL(String SQL)
   {
      this.SQL=SQL.trim();
   }

   //获得第i列的名字
   public String[] getColumnName()
   {
       if(columnName ==null )
       {
           System.out.println("先查询记录");
           return null;
       }
       return columnName;
   }

   //获得数据
   public String[][] getRecord()
   {
       startQuery();
       return record;
   }


   private void startQuery()
   {
      Connection con;
      Statement sql;
      ResultSet rs;
      //String uri = "jdbc:mysql://localhost:3306/"+databaseName+"?useSSL=true&characterEncoding=utf-8";
      String uri="jdbc:mysql://127.0.0.1:3306/dataku?useUnicode=true&characterEncoding=UTF-8&userSSL=false&serverTimezone=GMT%2B8";
		
      try
      {
        con=DriverManager.getConnection(uri,"root","myl000412");
        sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
        rs=sql.executeQuery(SQL);

        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();//获得表的列数

        //设置了列名
        columnName=new String[columnCount];
        for(int i=1;i<=columnCount;i++)
        {
           	columnName[i-1]=metaData.getColumnName(i);
        }

        rs.last();//先移到最后，再计算它的数目

        int recordAmount =rs.getRow();//获得表的行数

        record = new String[recordAmount][columnCount];//定义组件的表的行列数

        int i=0;
        rs.beforeFirst();//又移到了最前面，为了能从头读取数据
        while(rs.next())
        {
          for(int j=1;j<=columnCount;j++)
          {
             record[i][j-1]=rs.getString(j); //第i条记录，放入二维数组的第i行
          }
          i++;
        }
        con.close();
      }

      catch(SQLException e)
      {
        System.out.println("请输入正确的表名"+e);
      }
   }
}