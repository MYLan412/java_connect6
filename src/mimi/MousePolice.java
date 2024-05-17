package mimi;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import javax.swing.filechooser.*;
//链表的属性
class Chess
{
	int x;
	int y;
	int side;
	Chess(int x,int y,int side)
	{
		this.x=x;
		this.y=y;
		this.side=side;
	}
}
//获取棋谱的信息：x坐标、y坐标、side
class Testt
{
	static List<Chess> list=new ArrayList<Chess>();//定义链表
	static Chess chess;//声明类的对象
	static Scanner sc;
	static String ss_first;
	static String ss_later;
	public static void transt()
	{
		int x,y=0;
		int side=0;
		//System.out.println("文件名称"+MousePolice.name);
		System.out.println("文件路径"+MousePolice.path);
		File f=new File(MousePolice.path);
		//File f=new File("谱1.txt");
		FileReader fReader;
		BufferedReader bReader;
		String []str;
		String []str_xinxi;
		try
		{
			fReader=new FileReader(f);
			bReader=new BufferedReader(fReader);
			String s=null;			
			while((s=bReader.readLine()) != null)
			{
				str=s.split(";");//分割成一个一个的坐标形式
				str_xinxi=s.split("]");
				ss_first=str_xinxi[1].replace("[","");//把[去掉
				ss_later=str_xinxi[2].replace("[","");//把[去掉
				System.out.println("先手："+ss_first);
				System.out.println("后手："+ss_later);
				for(int i = 1; i < str.length; i++) 
				{
					String ss=str[i].replace("{","");//把{去掉
		            //System.out.println(ss);//输出每个坐标
		            //获取下棋方
		            if(ss.startsWith("W"))
		            {
		            	side=-1;
		            }
		            else if(ss.startsWith("B"))
		            {
		            	side=1;
		            }
		            else
		            {
		            	side=0;
		            }
		            String.valueOf(side);//将side转化为String型
		            
		            String x2=ss.substring(2,3).toLowerCase();
		            char[]x1=x2.toCharArray();
		            x=x1[0]-96;
		            
		            String.valueOf(x);
		            sc = new Scanner(ss);
		            sc.useDelimiter("[^0123456789.]+");
		            //获取点的坐标
		            while(sc.hasNext())
		            {
		            	try
		            	{
		            		y=sc.nextInt();
		                    String.valueOf(y);
		            		//System.out.println("y坐标:"+y);
		            		chess=new Chess(x,y,side);
		            		list.add(chess);
		            	}
		            	catch(InputMismatchException exp){}
		            }
		            //Insert_date.setData(xiaqifang,x_zuobiao,y_zuobiao);//插入操作
				}
			}
			if(s==null)
			{
				bReader.close();
			}
		}
		catch(IOException exp){}
	}
}

public class MousePolice extends JFrame implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JFrame frame;
	static String name;
	static String path;
   	JTextArea area;//公告显示区域
   	JButton button,button1,button2,button3,button4,button5,button6,button7;
	JTextField text,textB,textW;//黑白方显示提示的文本框

   	static int x,y;//点击棋盘的坐标
   	static int board[][]=new int[19][19];//棋盘
   	static int b[][]=new int[19][19];//棋盘的副本，用于判断该位置是否被占用
   	static int sideCount=1;//用于换边的计数
   	static int side=1;//初始化下棋方为黑方
	static int isWin=0;//判断是否赢的标记值

	static int over=0;//是否赢棋，一方赢了就为1
	static int needClean=0;//是否清盘，点击ReStart按钮，值为1
	MousePolice(JFrame frame)
	{
		this.frame=frame;
		initBoard();
		initB();
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
	public void setJButton_button(JButton button)
   	{
      	this.button=button;
   	}
	public void setJButton_button1(JButton button1)
   	{
      	this.button1=button1;
   	}
	public void setJButton_button2(JButton button2)
   	{
      	this.button2=button2;
   	}
	public void setJButton_button3(JButton button3)
   	{
      	this.button3=button3;
   	}
	public void setJButton_button4(JButton button4)
   	{
      	this.button4=button4;
   	}
	public void setJButton_button5(JButton button5)
   	{
      	this.button5=button5;
   	}
	public void setJButton_button6(JButton button6)
   	{
      	this.button6=button6;
   	}
	public void setJButton_button7(JButton button7)
   	{
      	this.button7=button7;
   	}
   	public void setJTextArea(JTextArea area)
   	{
      	this.area=area;
   	}
   	public void setJTextBField(JTextField textB)
   	{
      	this.textB=textB;
   	}
   	public void setJTextWField(JTextField textW)
   	{
      	this.textW=textW;
   	}
   	//********走到第几步的链表的索引*********
   	static int n=-1;
	//将获取的信息显示在棋盘上
   	public void play()
   	{
   		//System.out.println("大小："+Testt.list.size());
   		for(int i=0;i<Testt.list.size();i++)
   		{
   			x=Testt.list.get(i).x-1;
   			y=Testt.list.get(i).y-1;
   			side=Testt.list.get(i).side;
   			//System.out.println("坐标::"+x+y+side);
   			if(isFull())
   	   		{
   	   			System.out.println("下满了");
   	   		}
   	   		else
   	   		{
   	   			if(board[y][x]==0)
   	   			{
   	   				board[y][x]=side;//将点击的位置进行赋值
   	   			}
   	   			else{}
   	   		}
   			
   	   		if(cIsWin()==true&&side==1)//黑方获胜
   	   		{
   	   			System.out.println("链表："+list_win.size());
   	   			for(int i1=0;i1<list_win.size();i1++)
   	   			{
   	   				System.out.println(" "+list_win.get(i1).side+" "+list_win.get(i1).x+" "+list_win.get(i1).y);
   	   			}
   	   			//textB.setText("恭喜你获胜！");
   				//textW.setText("很遗憾！你输了....");
   				
   				textB.setText("先手:"+Testt.ss_first+"\n"+"恭喜你获胜！");
   	   			textW.setText("后手:"+Testt.ss_later+"\n"+"很遗憾！你输了....");
   	   			
   	   			over=1;
   	   			area.append("BLACK WIN!");
   	   		}
   	   		if(cIsWin()==true&&side==-1)//白方获胜
   	   		{
   	   			System.out.println("链表："+list_win.size());
	   			for(int i1=0;i1<list_win.size();i1++)
	   			{
	   				System.out.println(" "+list_win.get(i1).side+" "+list_win.get(i1).x+" "+list_win.get(i1).y);
	   			}
   	   			//textW.setText("恭喜你获胜！");
   				//textB.setText("很遗憾！你输了....");
   				
   				textB.setText("先手:"+Testt.ss_first+"\n"+"很遗憾！你输了....");
   	   			textW.setText("后手:"+Testt.ss_later+"\n"+"恭喜你获胜！");
   	   			
   				area.append("WHITE WIN!");
   	   			over=1;
   	   		}
   	   		n++;
   		}
   	}
   	//后退一步
   	public static void one_by_one_play1()
   	{
   		x=Testt.list.get(n).x-1;
   		y=Testt.list.get(n).y-1;
   		side=Testt.list.get(n).side;
   		board[y][x]=0;
   	} 
   	//前进一步
   	public static void one_by_one_play2()
   	{
   		x=Testt.list.get(n).x-1;
   		y=Testt.list.get(n).y-1;
   		side=Testt.list.get(n).side;
   		board[y][x]=side;
   	}
   	public void clearAll()
   	{
   		initBoard();
   		initB();
   		n=-1;
   		side=1;
   		sideCount=1;
   		over=0;
   		needClean=0;
   		index=0;
   		Testt.list.clear();
   		list_win.clear();
   		textB.setText(null);
   		textW.setText(null);
   		area.setText(null);
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
	//按钮监视器
	int index=0;
   	public void actionPerformed(ActionEvent e)
	{
   		if(e.getSource()==button)//点击“执行”按钮，绘制棋谱
		{
   			textB.setText("先手："+Testt.ss_first+"\n");
   			textW.setText("后手："+Testt.ss_later+"\n");
   			
   			if(index==0)
   			{
   				play();
   				index=1;
   			}
   			
   			//cIsWin();
		}
		if(e.getSource()==button1)//后退一步
		{
			list_win.clear();
			one_by_one_play1();
			//System.out.println("后退1\n");
			n--;
			if(n<0)
			{
				n=0;
			}
		}
		if(e.getSource()==button2)//前进一步
		{
			one_by_one_play2();
			//System.out.println("前进1\n");
			n++;
			if(n>Testt.list.size()-1)
			{
				n=Testt.list.size()-1;
				cIsWin();
			}
		}
		if(e.getSource()==button3)//后退到最初状态（空）
		{
			list_win.clear();
			while(n>=0)
			{
				one_by_one_play1();
				n--;
			}
			n=0;
			//System.out.println("3\n");
		}
		if(e.getSource()==button4)//前进到最后状态（满）
		{
			while(n<=Testt.list.size()-1)
			{
				one_by_one_play2();
				n++;
			}
			n=Testt.list.size()-1;
			cIsWin();
			//System.out.println("4\n");
		}
		if(e.getSource()==button5)//回到主页面（选择界面）
		{
			frame.dispose();
			Xuanze_JieMian x=new Xuanze_JieMian();
			x.setBounds(200,200,450,350);
			x.setTitle("选择界面");
			//System.out.println("5\n");
		}
		if(e.getSource()==button6)//打开文件
		{
			clearAll();
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter=new FileNameExtensionFilter("六子棋棋谱.txt", "txt");
			chooser.setFileFilter(filter);
			int r = chooser.showOpenDialog(this);
			if(r==JFileChooser.APPROVE_OPTION)
			{
				name=chooser.getSelectedFile().getName();
				path=chooser.getSelectedFile().getPath();
			}
			new Testt();
			
   			Testt.transt();
   			
		}
		if(e.getSource()==button7)//退出程序
		{
			System.exit(0);
		}
	}
   	Chess chess;
   	static List<Chess>list_win=new ArrayList<Chess>();
   	//判断输赢
   	public boolean cIsWin()
   	{
   		list_win.clear();
	   	int countWin = 0;
	    int i;
	    int j;
	    for(i=y;i>=0;i--)
	    {
	        if(board[i][x]==side)
	        {
	            countWin++;
	            chess=new Chess(x,i,side);
        		list_win.add(chess);
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
	            chess=new Chess(x,j,side);
        		list_win.add(chess);
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
	    list_win.clear();
	    
	    for(i=x;i>=0;i--)
	    {
	        if(board[y][i]==side)
	        {
	            countWin++;
	            chess=new Chess(i,y,side);
        		list_win.add(chess);
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
	            chess=new Chess(j,y,side);
        		list_win.add(chess);
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
	    list_win.clear();

		j=x;
		for(i=y;i>=0;i--)
		{
		    if(j>=0)
		    {
		        if(board[i][j]==side)
		        {
		            countWin++;
		            chess=new Chess(j,i,side);
	        		list_win.add(chess);
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
		            chess=new Chess(j,i,side);
	        		list_win.add(chess);
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
		list_win.clear();

		j=x;
		for(i=y;i>=0;i--)
		{
		    if(j<19)
		    {
		        if(board[i][j]==side)
		        {
		            countWin++;
		            chess=new Chess(j,i,side);
	        		list_win.add(chess);
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
		            chess=new Chess(j,i,side);
	        		list_win.add(chess);
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
	  	list_win.clear();
	  	return false;
	}
}