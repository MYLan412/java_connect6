package mimi;
import javax.swing.*;
import java.awt.*;
public class Client_WindowMouse extends JPanel
{
   	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTextArea textArea,textAreaB,textAreaW;
	JButton button,reStart;
   	Toolkit tool,toolG,toolB,toolW;
   	Image image,blackChess,whiteChess,backGround;

	Timer time;
	JTextField text,textB,textW,textL;

	Client_MousePolice mouse=new Client_MousePolice();
	Reaad reaad=new Reaad();
   	public Client_WindowMouse()
   	{
		setLayout(null);

       	tool=getToolkit();
       	image=tool.getImage("棋盘4.png");
       	toolG=getToolkit();
       	backGround=toolG.getImage("背景.png");
       	toolB=getToolkit();
       	blackChess=toolB.getImage("cyx黑棋.png");
       	toolW=getToolkit();
       	whiteChess=toolW.getImage("cyx白棋.png");
		init();
   	}
   	public void init()
   	{
   		Color c=new Color(188,143,143);//设置组件背景颜色
   		Color c2=new Color(255,228,225);
   		setBackground(c);
		time=new Timer(1000,mouse);
		
		button=new JButton("发送");
		reStart=new JButton("ReStart");

      	textArea=new JTextArea(10,25);
      	textArea.setBackground(c2);
      	textAreaB=new JTextArea(10,25);//黑方显示区域
      	textAreaW=new JTextArea(10,25);//白方显示区域
      	
      	//黑方区域
      	JPanel pB=new JPanel();
      	pB.setLayout(new BorderLayout());
      	pB.setBackground(c);
      	pB.setBounds(520,10,250,100);
      	
      	//白方区域
      	JPanel pW=new JPanel();
      	pW.setLayout(new BorderLayout());
      	pW.setBackground(c);
      	pW.setBounds(520,400,250,100);
      	
      	pB.add(new JButton("黑方"),BorderLayout.NORTH);
      	pW.add(new JButton("白方"),BorderLayout.NORTH);
      	
      	textB=new JTextField(10);
		textB.setBackground(c2);
		
		textW=new JTextField(10);
		textW.setBackground(c2);
		
		pB.add(textB,BorderLayout.CENTER);
		pW.add(textW,BorderLayout.CENTER);

		JScrollPane js=new JScrollPane(textArea);//加滚动条
		js.setBounds(520,120,250,200);
		add(js);
		
		textL=new JTextField(10);
		textL.setBackground(c2);
		textL.setBounds(520,325,250,30);
		add(textL);

		button.setBounds(600,360,70,30);
		add(button);
		reStart.setBounds(600,520,70,30);
		add(reStart);

      	mouse.setJTextArea(textArea);
      	mouse.setJButton_button(button);
      	mouse.setJButton(reStart);
      	mouse.setTime(time);
      	mouse.setJTextBField(textB);
      	mouse.setJTextWField(textW);
      	mouse.setJTextLField(textL);
      	
      	addMouseListener(mouse);
      	button.addActionListener(mouse);
      	reStart.addActionListener(mouse);
      	
      	add(pB);
      	add(pW);

   	}

   	public void paint(Graphics g)
   	{
   		if(Client_MousePolice.over==0||Client_MousePolice.needClean==0)
   		{
	   		super.paint(g);//有组件
	   		g.drawImage(backGround,0,0,520,600,this);//添加背景图片
	        g.drawImage(image,100,100,400,400,this);

		   	for(int i=0;i<19;i++)
		   	{
		   		for(int j=0;j<19;j++)
		   		{
			   		if(Reaad.board[i][j]==1)
			   		{
			   			int chessX=(int)(140+18*j-9);
			   			int chessY=(int)(140+18*i-9);
			   			g.drawImage(blackChess,chessX,chessY,15,15,this);
			   		}
			   		else if(Client_MousePolice.board[i][j]==-1)
			   		{
			   			int chessX=(int)(140+18*j-9);
			   			int chessY=(int)(140+18*i-9);
			   			g.drawImage(whiteChess,chessX,chessY,15,15,this);
			   		}
		   		}
	   		}
		   	repaint();//有棋子
   		}
   		else if(Reaad.w==1||Client_MousePolice.over==1||Client_MousePolice.needClean==1)
   		{
   			super.paint(g);
   			g.drawImage(backGround,0,0,520,600,this);//添加背景图片
   			g.drawImage(image,100,100,400,400,this);
   			mouse.initBoard();
   			mouse.initB();
   			for(int i=0;i<19;i++)
   			{
   				for(int j=0;j<19;j++)
   				{
   					Reaad.board[i][j]=0;
   				}
   			}
			Client_MousePolice.isWin=0;

			Client_MousePolice.over=0;
			Client_MousePolice.needClean=0;
			paint(g);
   		}
   		for(int i=0;i<19;i++)
   		{
   			int num=65+i;
   			g.setColor(Color.BLACK);
   			g.setFont(new Font("微软雅黑",1,12));
   			if(i==8)
   			{
   				g.drawString((char)num+"", 135+i*18+3, 130);//上
   				g.drawString((char)num+"", 135+i*18+3, 480);//下
   				g.drawString(i+1+"", 120, 465-i*18);//左
   				g.drawString(i+1+"", 475, 465-i*18);//右
   			}
   			else
   			{
   				g.drawString((char)num+"", 135+i*18, 130);
   				g.drawString((char)num+"", 135+i*18, 480);
   				g.drawString(i+1+"", 120, 465-i*18);
   				g.drawString(i+1+"", 475, 465-i*18);
   			}
   		}
   	}
}
