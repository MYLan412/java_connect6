package mimi;
import javax.swing.*;
import java.awt.*;
public class AI_WindowMouse extends JPanel
{
   	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTextArea textArea,textAreaB,textAreaW;
	JButton button,reStart,button_Save,button_Back,button_Zhu;
   	Toolkit tool,toolG,toolB,toolW;
   	Image image,blackChess,whiteChess,backGround;//定义棋盘、黑棋、白棋、背景图片
   	
	Timer time;
	JTextField text,textB,textW;
	JFrame frame;
	AI_MousePolice mouse;
   	public AI_WindowMouse(JFrame frame)
   	{
   		this.frame=frame;
   		mouse=new AI_MousePolice(frame);
   		//窗口界面的美化函数
   		try
        {
            //UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        }
        catch (Exception e)
        {
            System.out.println("Substance Raven Graphite failed to initialize");
        }

		setLayout(null);//空布局
		
		//定义相关图片
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
		//time=new Timer(1000,mouse);//1秒一更新时间
		
		button=new JButton("Start");
		reStart=new JButton("ReStart");
		button_Save=new JButton(new ImageIcon("保存.png"));
		button_Back=new JButton(new ImageIcon("左.png"));
		button_Zhu=new JButton(new ImageIcon("主.png"));
		
      	textArea=new JTextArea(10,25);
      	textArea.setBackground(c2);
      	textAreaB=new JTextArea(10,25);//黑方显示区域
      	textAreaW=new JTextArea(10,25);//白方显示区域
      	
      	//黑方区域
      	JPanel pB=new JPanel();
      	pB.setLayout(new BorderLayout());
      	pB.setBackground(c);
      	pB.setBounds(520,50,250,100);
      	
      	//白方区域
      	JPanel pW=new JPanel();
      	pW.setLayout(new BorderLayout());
      	pW.setBackground(c);
      	pW.setBounds(520,400,250,100);
      	
      	pB.add(new JButton("黑方"),BorderLayout.NORTH);
      	pW.add(new JButton("白方"),BorderLayout.NORTH);
      	
      	
		//text=new JTextField(10);
		textB=new JTextField(10);
		textB.setBackground(c2);
		
		textW=new JTextField(10);
		textW.setBackground(c2);
		
		pB.add(textB,BorderLayout.CENTER);
		pW.add(textW,BorderLayout.CENTER);
		
		JScrollPane js=new JScrollPane(textArea);//加滚动条
		js.setBounds(520,240,250,100);
		add(js);

		button.setBounds(600,10,70,30);
		add(button);
		reStart.setBounds(600,520,70,30);
		add(reStart);
		button_Save.setBounds(0,0,80,50);
		//add(button_Save);
		button_Back.setBounds(400,0,80,50);
		//add(button_Back);
		button_Zhu.setBounds(0,0,80,50);
		button_Zhu.setBackground(null);
		add(button_Zhu);
		
		//添加监视器
		
      	mouse.setJTextArea(textArea);
      	mouse.setJButton_button(button);
      	mouse.setJButton(reStart);
      	mouse.setJButton_button_Save(button_Save);
      	mouse.setJButton_button_Back(button_Back);
      	mouse.setJButton_button_Zhu(button_Zhu);
      	//mouse.setM(m);
      	//mouse.setJTextField(text);
      	mouse.setJTextBField(textB);
      	mouse.setJTextWField(textW);
      	
      	addMouseListener(mouse);
      	button.addActionListener(mouse);
      	reStart.addActionListener(mouse);
      	button_Save.addActionListener(mouse);
      	button_Back.addActionListener(mouse);
      	button_Zhu.addActionListener(mouse);
      	
      	add(pB);
      	add(pW);
	
		addMouseListener(mouse);
   	}
   	//绘图函数
   	public void paint(Graphics g)
   	{
   		if(mouse.over==0||mouse.needClean==0)//没有一方赢且不需要清盘操作
   		{
	   		super.paint(g);//有组件
	   		
	   		g.drawImage(backGround,0,50,520,600,this);//添加背景图片
	        g.drawImage(image,100,100,400,400,this);//添加棋盘
	        
		   	for(int i=0;i<19;i++)
		   	{
		   		for(int j=0;j<19;j++)
		   		{
			   		if(AI_MousePolice.board[i][j]==1)//棋盘上该位置是黑棋
			   		{
			   			int chessX=(int)(140+18*j-9);
			   			int chessY=(int)(140+18*i-9);
			   			g.drawImage(blackChess,chessX,chessY,15,15,this);
			   			
			   		}
			   		else if(AI_MousePolice.board[i][j]==-1)//棋盘上该位置是白棋
			   		{
			   			int chessX=(int)(140+18*j-9);
			   			int chessY=(int)(140+18*i-9);
			   			g.drawImage(whiteChess,chessX,chessY,15,15,this);

			   		}
		   		}
	   		}
			repaint();//有棋子
   		}
   		else if(mouse.over==1||mouse.needClean==1)//有一方赢棋或者启动清盘操作
   		{
   			super.paint(g);
   			g.drawImage(backGround,0,0,520,600,this);
   			g.drawImage(image,100,100,400,400,this);
   			//将相关变量初始化
   			mouse.initBoard();
   			mouse.initB();
   			mouse.sideCount=1;
		   	mouse.side=1;
			mouse.isWin=0;

			mouse.over=0;
			mouse.needClean=0;
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
