package mimi;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
public class Client_MousePolice extends JFrame implements MouseListener,ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String xinxi="zxcvbnmasdfghjklqwertyuiop";
	DataInputStream in=null;//输入流
	DataOutputStream out=null;//输出流
	Socket mysocket;
	Client_MousePolice()
	{
	    mysocket=null;
	    Reaad read=null;
	    Thread readData;//线程对象
	    try
	    {
	    	mysocket=new Socket();
	        read = new Reaad();
	        readData = new Thread(read);
	        
	        String IP = "127.0.0.1";//设置IP地址
	        int port = 2010;//设置端口号
	        if(mysocket.isConnected()){}
	        else
	        {
	        	InetAddress  address=InetAddress.getByName(IP);
	            InetSocketAddress socketAddress=new InetSocketAddress(address,port);
	            mysocket.connect(socketAddress);

	            in =new DataInputStream(mysocket.getInputStream());
	            out = new DataOutputStream(mysocket.getOutputStream());
	            read.setDataInputStream(in);
	            readData.start();
	            
	        }
	    }
	    catch(Exception e)
	    {
	        System.out.println("服务器已断开"+e);
	    }
	}

   	static JTextArea area;
   	JButton button,reStart;
   	Timer time;
	static JTextField text,textB,textW,textL;
	
	String str_xinxi;
   	static int x,y;
   	static int board[][]=new int[19][19];
   	static int b[][]=new int[19][19];
    static int side=-1;
    static int isWin=0;

	static int over=0;//是否赢棋，一方赢了就为1
	static int needClean=0;//是否清盘，点击ReStart按钮，值为1
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
      	Client_MousePolice.area=area;
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
      	Client_MousePolice.textB=textB;
   	}
   	public void setJTextWField(JTextField textW)
   	{
      	Client_MousePolice.textW=textW;
   	}
   	public void setJTextLField(JTextField textL)
   	{
      	Client_MousePolice.textL=textL;
   	}
   	//鼠标点击事件
   	public void mousePressed(MouseEvent e)
   	{
   		x=e.getX();
   		y=e.getY();
   		x=(int)((x-140+9)/18);
   		y=(int)((y-140+9)/18);

   		if(side==-1)//白方
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

	   	   }//虽然不能实现，但黑棋赢的信息由服务端传过来
	   	   if(cIsWin()==true&&side==-1)//白方赢
	   	   {
	   		   textW.setText("恭喜你获胜！");
	   		   textB.setText("很遗憾！你输了....");
	   		   time.stop();
	   		   over=1;
	   		   area.append("WHITE WIN!\n");
	   		   //System.out.println("Wwin!!");
	   	   }
	   	   //area.append("\n鼠标按下,位置:"+"("+x+","+y+")");

   		}
   		//通过鼠标事件传给服务端4个值
   		try
   		{
   			String message = String.valueOf(isWin);
   			String message_x = String.valueOf(x);
   			String message_y = String.valueOf(y);
   			if(textL.getText().length()<=0)
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
	        
			System.out.println("客户端发给服务端 是否赢棋的信息"+message);
	        System.out.println("客户端发给服务端x"+message_x);
	        System.out.println("客户端发给服务端y"+message_y+"\n");
   			System.out.println("客户端发给服务端 的聊天信息"+xinxi);
	    }
	    catch(Exception e1) {}
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
	{
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
	    countWin = 0;//|

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
class Reaad implements Runnable
{
	DataInputStream in;//输入流
	static int w=0;
	static int x,y;
	static String message_xinxi;
	static int board[][]=new int[19][19];
	static int b[][]=new int[19][19];
	static int side=1;
	public void setDataInputStream(DataInputStream in)
	{
	    this.in = in;
	}
	public void run()
	{
	    while(true)
	    {
	        try
	        {
	        	String message = in.readUTF();
	        	String message_x = in.readUTF();
	        	String message_y = in.readUTF();
	        	message_xinxi=in.readUTF();
	        	
	        	w=Integer.parseInt( message);
	        	x=Integer.parseInt( message_x);
	        	y=Integer.parseInt( message_y);
	        	
	        	
	            if(w==1)//如果读取到特殊值 说明黑方赢
	            {
	            	Client_MousePolice.area.append("BLACK WIN!");
	           	 	Client_MousePolice.textB.setText("恭喜你获胜！");
	           	 	Client_MousePolice.textW.setText("很遗憾！你输了....");
	           	 	board[y][x]=side;
	      		   	b[y][x]=1;
	      		   	System.out.println(message_xinxi);
	            }
	            if(w==0)//黑方没有赢，进行正常的落点操作
	            {
	            	board[y][x]=side;
      		   		b[y][x]=1;
      		   		System.out.println(message_xinxi);
	            }
	            if(w==-1)
	            {
	            	Client_MousePolice.area.append("对方："+message_xinxi+"\n");
	            }
	            
	            
	            //在控制台显示传值的信息
				System.out.println("从服务端收到的 是否赢棋的信息"+message);
	        	System.out.println("从服务端收到的x"+message_x);
		        System.out.println("从服务端收到的y"+message_y+"\n");
		        System.out.println("从服务端收到的信息"+message_xinxi+"\n");
	        }

	        catch(IOException e)
	        {
	             System.out.println("与服务器已断开"+e);
	             break;
	        }
	    }
	}

}