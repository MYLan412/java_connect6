package mimi;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
public class Server_MousePolice extends JFrame implements MouseListener,ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String xinxi="zxcvbnmasdfghjklqwertyuiop";
	ServerSocket server=null;
	Socket you=null;
	
   	static JTextArea area;
   	JButton button,reStart;
   	static JTextField text,textB,textW,textL;

   	Timer time;
	DataOutputStream out=null;//输出流
	Server_MousePolice()
	{
		while(true)
		{
	        try
	        {
	        	server=new ServerSocket(2010);//端口号2010
	        }
	        catch(IOException e1)//若没有连接起来，就抛出异常
	        {
	            System.out.println("正在监听");
	        }
	        try
	        {
	        	System.out.println("正在匹配玩家....");
	            you=server.accept();//将客户端的套接字对象和服务端的套接字对象连接起来
	            System.out.println("客户的地址:"+you.getInetAddress());
	            out = new DataOutputStream(you.getOutputStream());
	        }
	        catch (IOException e)
	        {
	            System.out.println("正在匹配玩家....");
	        }
	        if(you!=null)
	        {
	             new ServerThread(you).start();//为每个玩家启动一个专门的线程
	        }

	        break;

	    }
	}

   	static int x=-1,y=-1;
    static int board[][]=new int[19][19];
    static int b[][]=new int[19][19];
    static int sideCount=1;
   	static int side=1;
	static int isWin=0;//用于传递是否赢棋的特殊标记值

	static int over=0;
	static int needClean=0;

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
   	public void setJTextArea(JTextArea area)
   	{
      	Server_MousePolice.area=area;
   	}
   	public void setJButton_button(JButton button)
   	{
      	this.button=button;
   	}
   	public void setJButton(JButton reStart)
   	{
      	this.reStart=reStart;
   	}
   	public void setTime(Timer time)
   	{
      	this.time=time;
   	}
   	public void setJTextBField(JTextField textB)
   	{
      	Server_MousePolice.textB=textB;
   	}
   	public void setJTextWField(JTextField textW)
   	{
      	Server_MousePolice.textW=textW;
   	}
	public void setJTextLField(JTextField textL)
   	{
      	Server_MousePolice.textL=textL;
   	}
   	//鼠标点击事件
   	public void mousePressed(MouseEvent e)
   	{
   		x=e.getX();
   		y=e.getY();
   		x=(int)((x-140+9)/18);
   		y=(int)((y-140+9)/18);

   		if(side==1)//黑方
   		{
   	   		if(isFull())
   	   		{
   	   			System.out.println("下满了");
   	   		}
   	   		else
   	   		{
   	   			if(board[y][x]==0)
   	   			{
   	   				board[y][x]=side;
   	   			}
   	   			else{}
   	   		}
	   	   	if(cIsWin()==true&&side==1)
	   		{
		   	   	textB.setText("恭喜你获胜！");
				textW.setText("很遗憾！你输了....");
				time.stop();
				
	   			over=1;
	   			area.append("BLACK WIN!\n");
	   			//System.out.println("Bwin!!");
	   		}
	   		if(cIsWin()==true&&side==-1)
	   		{
	   			textW.setText("恭喜你获胜！");
				textB.setText("很遗憾！你输了....");
				time.stop();
				
	   			over=1;
	   			area.append("WHITE WIN!\n");
	   			//System.out.println("Wwin!!");
	   		}//不能实现 但白方获胜的信息将由客户端传过来

	      	//area.append("\n鼠标按下,位置:"+"("+x+","+y+")");
	   	}
   		
   		//通过鼠标事件传给客户端4个值
	   	try
   		{
   			//将特殊标记值和鼠标点击获得的坐标传给客户端
   			String message = String.valueOf(isWin);
   			String message_x = String.valueOf(x);
   			String message_y= String.valueOf(y);
   			if(textL.getText()==null)
   			{
	   			
   			}
   			else
   			{
   				xinxi=textL.getText();
   				//area.append("我："+xinxi+"\n");
   			}
   			out.writeUTF(message);
	        out.writeUTF(message_x);
	        out.writeUTF(message_y);
	        out.writeUTF(xinxi);
			
   			System.out.println("服务端发给客户端 是否赢棋的信息"+message);
   			System.out.println("服务端发给客户端x"+message_x);
   			System.out.println("服务端发给客户端y"+message_y+"\n\n");
   			System.out.println("服务端发给客户端 的聊天信息"+xinxi);
   		}
   		catch(IOException e2) {}

   	}

   	public boolean isTakeOver(int m,int n)
   	{
   		if(b[m][n]==1)
   		{
   			return true;
   		}
   		return false;
   	}

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

   	public void mouseReleased(MouseEvent e)
   	{
   		if(isTakeOver(y,x)==false)
   		{
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

   	public void actionPerformed(ActionEvent e)
	{/*
   		if(e.getSource()==button)
		{
			time.start();
			area.append("button\n");
		}
		if(e.getSource()==reStart)
		{
			needClean=1;
			area.append("restart_button\n");
		}
		*/
   		if(e.getSource()==button)
   		{
   		   	try
   	   		{
   	   			//将特殊标记值和鼠标点击获得的坐标传给客户端
   	   			String message = "-1";
   	   			String message_x ="-1";
   	   			String message_y= "-1";
   	   			if(textL.getText()==null)
   	   			{
   		   			
   	   			}
   	   			else
   	   			{
   	   				xinxi=textL.getText();
   	   				area.append("我："+xinxi+"\n");
   	   			}
   	   			out.writeUTF(message);
   		        out.writeUTF(message_x);
   		        out.writeUTF(message_y);
   		        out.writeUTF(xinxi);
   				
   	   			System.out.println("服务端发给客户端 是否赢棋的信息"+message);
   	   			System.out.println("服务端发给客户端x"+message_x);
   	   			System.out.println("服务端发给客户端y"+message_y+"\n\n");
   	   			System.out.println("服务端发给客户端 的聊天信息"+xinxi);
   	   			xinxi="zxcvbnmasdfghjklqwertyuiop";
   	   			textL.setText(null);
   	   		}
   	   		catch(IOException e2) {}

   	   	}
   		
	}

   	public static boolean cIsWin()
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
		            return true;
	            }

	        }
	        else
	        {
	            break;
	        }

	    }
	    countWin = 0;

	    for(i=x;i>=0;i--)
	    {
	        if(board[y][i]==side)
	        {
	            countWin++;
	            if(countWin==6)
	            {
	                isWin=1;
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

//读取信息的线程
class ServerThread extends Thread
{
	Socket socket;
	static JTextArea area;
    DataInputStream  in=null;//输入流
    static int w,x1,y1;//读取到的特殊标记值和白棋的坐标
    static String message_xinxi;
    static int board[][]=new int[19][19];
    static int b[][]=new int[19][19];
   	static int side=1;
   	ServerThread()
   	{
   	}
	ServerThread(Socket t)
	{
		socket=t;
	    try
	    {
	        in=new DataInputStream(socket.getInputStream());//只读取信息
	    }
	    catch (IOException e){}
	}

	public void run()
	{
	    while(true)
	    {
	        try
	        {
				String message=in.readUTF();
	         	String message_x=in.readUTF();
	            String message_y=in.readUTF();
	            message_xinxi=in.readUTF();
	            
				w=Integer.parseInt(message);
	            x1=Integer.parseInt(message_x);
	            y1=Integer.parseInt(message_y);

	            if(w==1)//如果读取到特殊值 说明白方赢
	            {
	            	Server_MousePolice.area.append("WHITE WIN!");
	            	Server_MousePolice.textW.setText("恭喜你获胜！");
	            	Server_MousePolice.textB.setText("很遗憾！你输了....");
	            	board[y1][x1]=-side;
	            	b[y1][x1]=1;
	            	System.out.println(message_xinxi);
	            }
	            if(w==0)//说明白方没有赢，进行正常的落点操作
	            {
	            	board[y1][x1]=-side;
            		b[y1][x1]=1;
            		System.out.println(message_xinxi);
	            }
	            if(w==-1)
	            {
	            	Server_MousePolice.area.append("对方："+message_xinxi+"\n");
	            }
	            //在控制台显示传值的信息
				System.out.println("服务端收到客户端 是否赢棋的信息"+message);
	            System.out.println("服务端收到客户端x"+message_x);
	            System.out.println("服务端收到客户端y"+message_y+"\n");
	            System.out.println("服务端收到客户端 的聊天信息"+message_y+"\n");

	        }
	        catch (IOException e)
	        {
	            System.out.println("玩家离开");
	            return;
	        }
	    }
	}
}