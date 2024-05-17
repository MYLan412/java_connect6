package mimi;
import java.awt.event.*;

import javax.swing.*;
import java.sql.*;
import java.awt.*;
import java.io.*;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class C6_MousePolice extends JFrame implements MouseListener,ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JFrame frame;
	String xiaqifang;
	String x_zuobiao;
	String y_zuobiao;
	static List<Chess> list2=new ArrayList<Chess>();
	static Chess chess;
	int Max=20;//用于倒计时
   	JTextArea area;//公告显示区域
   	JButton button,reStart,button_Save,button_Back,button_Zhu;//开始和重新开始按钮
   	Timer time;//时间类
	JTextField text,textB,textW;//黑白方显示提示的文本框

   	static int x,y;//点击棋盘的坐标
   	static int board[][]=new int[19][19];//棋盘
   	int b[][]=new int[19][19];//棋盘的副本，用于判断该位置是否被占用
   	int sideCount=1;//用于换边的计数
   	int side=1;//初始化下棋方为黑方
	int isWin=0;//判断是否赢的标记值

	static int over=0;//是否赢棋，一方赢了就为1
	static int needClean=0;//是否清盘，点击ReStart按钮，值为1
	public C6_MousePolice(JFrame frame)
	{
		this.frame=frame;
	}
	//初始化棋盘
	public void initBoard()
	{
		for(int i=0;i<19;i++)
   		{
	   		for(int j=0;j<19;j++)
	   		{
		   		board[i][j]=0;
	   		}
   		}
	}
	//初始化副本棋盘
	public void initB()
	{
		for(int i=0;i<19;i++)
   		{
	   		for(int j=0;j<19;j++)
	   		{
		   		b[i][j]=0;
	   		}
   		}
	}
	//设置相关变量
   	public void setJTextArea(JTextArea area)
   	{
      	this.area=area;
   	}
   	public void setJButton_button(JButton button)
   	{
      	this.button=button;
   	}
   	public void setJButton(JButton reStart)
   	{
      	this.reStart=reStart;
   	}
   	public void setJButton_button_Save(JButton button_Save)
   	{
      	this.button_Save=button_Save;
   	}
   	public void setJButton_button_Back(JButton button_Back)
   	{
      	this.button_Back=button_Back;
   	}
   	public void setJButton_button_Zhu(JButton button_Zhu)
   	{
      	this.button_Zhu=button_Zhu;
   	}
   	public void setTime(Timer time)
   	{
      	this.time=time;
   	}
   	public void setJTextBField(JTextField textB)
   	{
      	this.textB=textB;
   	}
   	public void setJTextWField(JTextField textW)
   	{
      	this.textW=textW;
   	}
   	int n=-1;
	//鼠标点击事件
   	public void mousePressed(MouseEvent e)
   	{
   		x=e.getX();
   		y=e.getY();
   		x=(int)((x-140+9)/18);
   		y=(int)((y-140+9)/18);//将像素转化成坐标

		//System.out.println();
		xiaqifang=String.valueOf(side);
		x_zuobiao=String.valueOf(x);
		y_zuobiao=String.valueOf(y);
		
   		if(isFull())
   		{
   			System.out.println("下满了");
   		}
   		else
   		{
   			if(board[y][x]==0)
   			{
   				board[y][x]=side;//将点击的位置进行赋值
   				//在正确下棋的时候将点的信息存入链表
   				chess=new Chess(x,y,side);
   				list2.add(chess);
   				n++;
   			}
   			else{}
   		}
   		if(cIsWin()==true&&side==1)//黑方获胜
   		{
   			textB.setText("恭喜你获胜！");
			textW.setText("很遗憾！你输了....");
			time.stop();//倒计时结束
   			over=1;
   			area.append("BLACK WIN!");
   			//System.out.println("Bwin!!");
   			/*
   			for(int i=0;i<list2.size();i++)
			{
				int lx=list2.get(i).x;
				int ly=list2.get(i).y;
				int lside=list2.get(i).side;
				System.out.println("list2     "+lx+","+ly+"    side="+lside);
			}
			*/
   		}
   		if(cIsWin()==true&&side==-1)//白方获胜
   		{
   			textW.setText("恭喜你获胜！");
			textB.setText("很遗憾！你输了....");
			time.stop();//倒计时结束
   			over=1;
   			area.append("WHITE WIN!");
   			//System.out.println("Wwin!!");
   			/*
   			for(int i=0;i<list2.size();i++)
			{
				int lx=list2.get(i).x;
				int ly=list2.get(i).y;
				int lside=list2.get(i).side;
				System.out.println("list2     "+lx+","+ly+"    side="+lside);
			}
			*/
   		}
      	area.append("\n鼠标按下,位置:"+"("+x+","+y+")");
   	}
   	//判断棋盘上该位置是否被占用
   	public boolean isTakeOver(int m,int n)
   	{
   		if(b[m][n]==1)
   		{
   			return true;
   		}
   		return false;
   	}
   	//判断棋盘是否已满
	public boolean isFull()
   	{
   	    int i,j;
   	    for(i=0;i<19;i++)
   	    {
   	        for(j=0;j<19;j++)
   	        {
   	            if(board[i][j]==0)
   	            {
   	                return false;
   	            }
   	        }
   	    }
   	    return true;
   	}
	//鼠标释放事件
   	public void mouseReleased(MouseEvent e)
   	{
   		if(isTakeOver(y,x)==false)//该位置没有被占
   		{
   			sideCount++;
   		   	if(sideCount==2)//计数达到2  换边
   		   	{
   		   		side=-side;
   		   		sideCount=0;
   		   		
   		   		Max=20;//再次从20倒计时
   		   	}
   		   	b[y][x]=1;
   		}
   	}
   	public void mouseEntered(MouseEvent e)
   	{
   		
   	}
   	public void mouseExited(MouseEvent e)
   	{
   		
   	}
   	public void mouseClicked(MouseEvent e)
   	{
   		
   	}
   	//后退一步
   	public void one_by_one_play1()
   	{
   		x=list2.get(list2.size()-1).x;
   		y=list2.get(list2.size()-1).y;
   		side=list2.get(list2.size()-1).side;
   		board[y][x]=0;
   		b[y][x]=0;
   		n--;
   		if(n<0)
   		{
   			n=0;
   		}
   		if(sideCount==1)
   		{
   			sideCount--;
   		}
   		else if(sideCount==0)
   		{
   			sideCount=1;
   		}
   	}
   	//按钮监视器
   	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==button)//点击开始按钮  开始下棋
		{
			time.start();//启动倒计时
		}
		if(e.getSource()==time)
		{
			if(side==1&&cIsWin()==false)//黑方下棋时
			{
				textW.setText("黑方下棋中");
				textB.setText("下棋时间剩余"+Max+"秒");
				Max--;
				if(Max<=0)
				{
					time.stop();
				}
			}
			if(side==-1&&cIsWin()==false)//白方下棋时
			{
				textB.setText("白方下棋中");
				textW.setText("下棋时间剩余"+Max+"秒");
				Max--;
				if(Max<=0)
				{
					time.stop();
				}
			}
		}
		if(e.getSource()==reStart)//重新开始
		{
			time.start();
			needClean=1;//将需要清盘的标记值赋成1
			initBoard();
	   		initB();
	   		n=-1;
	   		side=1;
	   		sideCount=1;
	   		over=0;
	   		needClean=0;
	   		list2.clear();
		}
		if(e.getSource()==button_Save)//保存棋谱
		{
			Save_Way x=new Save_Way();
			x.setBounds(200,200,490,350);
			x.setTitle("选择保存棋谱的方式");
			
			//LuRu_JieMian x=new LuRu_JieMian();
			//x.setBounds(200,200,490,350);
			//x.setTitle("录入比赛信息");
			
			//Save_JieMian s=new Save_JieMian();//将棋谱信息保存至自定义数据库表中
			//display();//将棋谱信息保存到自定义文件中
		}
		if(e.getSource()==button_Back)//后退一步
		{
			//System.out.print("1111sideCount="+sideCount+"    "+side);
			one_by_one_play1();
			//System.out.println("后退1\n");
			n--;
			if(n<0)
			{
				n=0;
			}
			list2.remove(list2.size()-1);
			//System.out.print("2222sideCount="+sideCount+"    "+side);

		}
		if(e.getSource()==button_Zhu)//重新开始
		{
			frame.dispose();
			Xuanze_JieMian x=new Xuanze_JieMian();
			x.setBounds(200,200,450,350);
			x.setTitle("选择界面");
		}
	}
   	
//将当前棋谱保存到文件中
   	public void display()
   	{
   		JFileChooser fc;
   		int flag=0;
   		File f=null;
   		FileWriter fw;
   		String start="{[六子棋]  ";
   		
   		start=start+"[先手："+LuRu_JieMian.name_B+"]  "+"[后手："+LuRu_JieMian.name_W+"]  "+"[比赛地点："+LuRu_JieMian.name_Location+"]  "+"[竞赛名称："+LuRu_JieMian.name_Race+"]  "+"[获胜方："+LuRu_JieMian.win_Side+"]  "+";";
   		
   		for(int i=0;i<list2.size();i++)
   		{
   			if(list2.get(i).side==1)
   			{
   				if(i==list2.size()-1)
   				{
   					start=start+"B"+"("+(char)(list2.get(i).x+97)+","+list2.get(i).y+")";
   				}
   				else
   				{
   					start=start+"B"+"("+(char)(list2.get(i).x+97)+","+list2.get(i).y+");";
   				}
   				
   			}
   			if(list2.get(i).side==-1)
   			{
   				if(i==list2.size()-1)
   				{
   					start=start+"W"+"("+(char)(list2.get(i).x+97)+","+list2.get(i).y+")";
   				}
   				start=start+"W"+"("+(char)(list2.get(i).x+97)+","+list2.get(i).y+");";
   			}
   			//start=start+list2.get(i).side+"("+list2.get(i).x+","+list2.get(i).y+");";
   		}
   		start=start+"}";
   		System.out.println(start);
   		
   		fc = new JFileChooser();
   		String fileName;
    
   		//设置保存文件对话框的标题   
   		fc.setDialogTitle("Save File");
    
   		//这里将显示保存文件的对话框   
   		try
   		{
   			flag = fc.showSaveDialog(frame);
   		}
   		catch(HeadlessException he) 
   		{
   			System.out.println("Save File Dialog ERROR!");
        }
   
   		//如果按下确定按钮，则获得该文件。   
   		if (flag == JFileChooser.APPROVE_OPTION)
   		{
   			//获得输入的要保存的文件   
   			f = fc.getSelectedFile();
   			//获得文件名   
   			fileName = fc.getName(f);
   			//fileName=f.getName();   
            System.out.println("已将  "+fileName+"  保存成功！");
   		}
   		try
   		{
   			fw=new FileWriter(f,true);
   			fw.write(start);
   			fw.flush();
   			fw.close();
   		}
   		catch(IOException e){}
   	}
   	
   	//判断输赢
   	public boolean cIsWin()
   	{
	   	int countWin = 0;
	    int i;
	    int j;
	    for(i=y;i>=0;i--)
	    {
	        if(board[i][x]==side)
	        {
	            countWin++;
	            if(countWin==6)
	            {
		            isWin=1;
		            //System.out.printf("竖向%d",isWin);
		            //System.out.printf("竖向%d",side);
					return true;
	            }

	        }
	        else
	        {
	            break;
	        }
	    }
	    for(j=y+1;j<19;j++)
	    {
	        if(board[j][x]==side)
	        {
	            countWin++;
	            if(countWin == 6)
	            {
		            isWin=1;
		            //System.out.printf("竖向%d",isWin);
		            //System.out.printf("竖向%d",side);
		            return true;
	            }

	        }
	        else
	        {
	            break;
	        }

	    }
	    countWin = 0;//|

	    for(i=x;i>=0;i--)
	    {
	        if(board[y][i]==side)
	        {
	            countWin++;
	            if(countWin==6)
	            {
	                isWin=1;
	                //System.out.printf("横向%d",isWin);
		            //System.out.printf("横向%d",side);
		            return true;
	            }
	        }
	        else
	        {
	            break;
	        }
	    }

	    for(j=x+1;j<19;j++)
	    {
	        if(board[y][j]==side)
	        {
	            countWin++;
	            if(countWin==6)
	            {
	            	isWin=1;
	            	//System.out.printf("横向%d",isWin);
		            //System.out.printf("横向%d",side);
		            return true;
	            }

	        }
	        else
	        {
	            break;
	        }
	    }
	    countWin = 0;//——


		j=x;
		for(i=y;i>=0;i--)
		{
		    if(j>=0)
		    {
		        if(board[i][j]==side)
		        {
		            countWin++;
		            j--;
		            if(countWin==6)
		            {
		                isWin=1;
		                //System.out.printf("左上%d",isWin);
			            //System.out.printf("左上%d",side);
			            return true;
		            }
		        }
		        else
		        {
		            break;
		        }
		    }
		}

		j=x+1;
		for(i=y+1;i<19;i++)
		{
		    if(j<19)
		    {
		        if(board[i][j]==side)
		        {
		            countWin++;
		            j++;
		            if(countWin==6)
		            {
		                isWin=1;
		                //System.out.printf("左上%d",isWin);
			            //System.out.printf("左上%d",side);
			            return true;
		            }
		        }
		        else
		        {
		            break;
		        }
		    }
		} 
		countWin = 0;//\


		j=x;
		for(i=y;i>=0;i--)
		{
		    if(j<19)
		    {
		        if(board[i][j]==side)
		        {
		            countWin++;
		            j++;
		           
		            if(countWin==6)
		            {
		                isWin=1;
		                //System.out.printf("右上%d",isWin);
			            //System.out.printf("右上%d",side);
			            return true;
		            }
		        }
		        else
		        {
		            break;
		        }
		    }
		}
		j=x-1;
		for(i=y+1;i<19;i++)
		{
		    if(j>=0)
		    {
		        if(board[i][j]==side)
		        {
		            countWin++;
		            j--;
		            if(countWin==6)
		            {
		                isWin=1;
		                //System.out.printf("右上%d",isWin);
			            //System.out.printf("右上%d",side);
			            return true;
		            }
		        }
		        else
		        {
		            break;
		        }
		    }
		}
	  	countWin=0;// /
	  	return false;
	}
}

class Save_JieMian extends JFrame implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static String table;
	JLabel label;
	JTextField text;
	JButton button;
	JFrame frame;
	C6_MousePolice m=new C6_MousePolice(frame);
	Save_JieMian()
	{
		//美化窗口和界面的函数
		try
        {
            //UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        }
        catch (Exception e)
        {
            System.out.println("Substance Raven Graphite failed to initialize");
        }
		init();
		setBounds(200,200,450,350);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	public void init()//初始化第一界面
	{
		setLayout(new BorderLayout());
		JPanel p=new JPanel();
		p.setLayout(null);
		
		Color c=new Color(188,143,143);
		p.setBackground(c);
		
		//提示信息
		label=new JLabel("请输入保存的数据库的表的名称：");
		label.setBounds(110,50,200,25);
		p.add(label);
		//输入表名称
		text=new JTextField(10);
		text.setBounds(110,100,200,25);
		p.add(text);
		//添加按钮
		button=new JButton("确定");
		button.setBounds(170,170,80,25);
		p.add(button);

		add(p,BorderLayout.CENTER);
		//添加监视器
		button.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e)
	{
		//点击登录
		if(e.getSource()==button)
		{
			dispose();
			table=text.getText();//获取用户输入的表名称
			System.out.println("名称："+table);
			new Insert_new_table();
			Insert_new_table.setTable(table);
			for(int i=0;i<C6_MousePolice.list2.size();i++)
			{
				String s1=String.valueOf(C6_MousePolice.list2.get(i).side);
				String s2=String.valueOf(C6_MousePolice.list2.get(i).x);
				String s3=String.valueOf(C6_MousePolice.list2.get(i).y);
				Insert_date.setData(s1,s2,s3);
			}
		}
	}
}
//选择保存棋谱的方式
class Save_Way extends JFrame implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static int way=0;
	JLabel label;
	JButton button_File,button_Mysql;
	Save_Way()
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
		Color c2=new Color(220,220,220);
		p.setBackground(c);

		label=new JLabel("请选择保存棋谱的方式：");
		label.setBounds(150,100,200,25);
		p.add(label);
		
		button_File=new JButton("文件");
		button_File.setBounds(120,180,80,25);
		button_File.setBackground(c2);
		p.add(button_File);
		
		button_Mysql=new JButton("数据库");
		button_Mysql.setBounds(270,180,80,25);
		button_Mysql.setBackground(c2);
		p.add(button_Mysql);
		
		button_File.addActionListener(this);
		button_Mysql.addActionListener(this);
		
		add(p,BorderLayout.CENTER);
	}

	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==button_File)//保存棋谱
		{
			dispose();
			way=1;
			LuRu_JieMian x=new LuRu_JieMian();
			x.setBounds(200,200,490,350);
			x.setTitle("录入比赛信息");
			
		}
		if(e.getSource()==button_Mysql)//保存棋谱
		{
			dispose();
			way=2;
			LuRu_JieMian x=new LuRu_JieMian();
			x.setBounds(200,200,490,350);
			x.setTitle("录入比赛信息");
			
		}
	}
}
//创建一个新的数据库表
class Insert_new_table
{
	public static void setTable(String name)
	{
		Connection con;
		Statement sql;
		//PreparedStatement preSql;
		con=GetDBConnection.connectDB("student","root","myl000412!");//连接数据库
		if(con==null)
		{
			System.out.println("kong");
			return;
		}
		//String sqlStr="CREATE TABLE `"+Save_JieMian.table+"` (`side` char(100), `x` char(100), `y` char(100)) ENGINE=InnoDB DEFAULT CHARSET=utf8";
		//String sqlStr="create table"+ Save_JieMian.table+"(side varchar(80),x varchar(80),y varchar(80),)";//创建语句
		try
		{
			sql=(Statement) con.createStatement();
			//preSql=con.prepareStatement(sqlStr);
			sql.executeUpdate("CREATE TABLE `"+Save_JieMian.table+"` (`side` char(100), `x` char(100), `y` char(100)) ENGINE=InnoDB DEFAULT CHARSET=utf8");
		}
		catch(SQLException e)
		{
			System.out.println("execption"+e);
		}
	}
}
//将点的信息写入数据库的创建的表中
class Insert_date
{
	public static void setData(String sidee,String xx,String yy)
	{
		Connection con;
		//Statement sql;
		PreparedStatement preSql;
		ResultSet rs;

		con=GetDBConnection.connectDB("student","root","myl000412!");//连接数据库
		
		if(con==null)
		{
			System.out.println("kong");
			return;
		}
		
		String sqlStr="insert into "+Save_JieMian.table+" values(?,?,?)";//插入语句
		try
		{
			//sql=con.createStatement();
			preSql=con.prepareStatement(sqlStr);

			preSql.setString(1,sidee);
			preSql.setString(2,xx);
			preSql.setString(3,yy);
			preSql.executeUpdate();
			rs=preSql.executeQuery();
			
			while(rs.next())
			{
				String side=rs.getString(1);
				String x=rs.getString(2);
				String y=rs.getString(3);

				System.out.printf("%s\t",side);
				System.out.printf("%s\t",x);
				System.out.printf("%s\t",y);
			}
			con.close();
		}
		catch(SQLException e)
		{
			System.out.println("execption"+e);
		}
	}
}
