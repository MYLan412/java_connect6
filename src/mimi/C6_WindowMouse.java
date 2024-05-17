package mimi;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class C6_WindowMouse extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JFrame frame;
   	JTextArea textArea,textAreaB,textAreaW;
	JButton button,reStart,button_Save,button_Back,button_Zhu;
   	Toolkit tool,toolG,toolB,toolW,tool_hua;
   	Image image,blackChess,whiteChess,backGround,hua;//定义棋盘、黑棋、白棋、背景图片
   	
	Timer time;
	JTextField text,textB,textW;
	
	C6_MousePolice mouse;
   	public C6_WindowMouse(JFrame frame)
   	{
   		this.frame=frame;
   		mouse=new C6_MousePolice(frame);
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
       	tool_hua=getToolkit();
       	hua=tool_hua.getImage("花.gif");
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
		time=new Timer(1000,mouse);//1秒一更新时间
		
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
		button_Save.setBackground(null);
		add(button_Save);
		button_Back.setBounds(400,0,80,50);
		button_Back.setBackground(null);
		add(button_Back);
		button_Zhu.setBounds(200,0,80,50);
		button_Zhu.setBackground(null);
		add(button_Zhu);

		//添加监视器
      	mouse.setJTextArea(textArea);
      	mouse.setJButton_button(button);
      	mouse.setJButton(reStart);
      	mouse.setJButton_button_Save(button_Save);
      	mouse.setJButton_button_Back(button_Back);
      	mouse.setJButton_button_Zhu(button_Zhu);
      	mouse.setTime(time);
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

   	}
   	//绘图函数
   	public void paint(Graphics g)
   	{
   		if(C6_MousePolice.over==0||C6_MousePolice.needClean==0)//没有一方赢且不需要清盘操作
   		{
	   		super.paint(g);//有组件
	   		
	   		g.drawImage(backGround,0,50,520,600,this);//添加背景图片
	        g.drawImage(image,100,100,400,400,this);//添加棋盘
	        g.drawImage(hua,0,0,800,600,this);//添加背景图片
	        
		   	for(int i=0;i<19;i++)
		   	{
		   		for(int j=0;j<19;j++)
		   		{
			   		if(C6_MousePolice.board[i][j]==1)//棋盘上该位置是黑棋
			   		{
			   			int chessX=(int)(140+18*j-9);
			   			int chessY=(int)(140+18*i-9);
			   			g.drawImage(blackChess,chessX,chessY,15,15,this);
			   			
			   		}
			   		else if(C6_MousePolice.board[i][j]==-1)//棋盘上该位置是白棋
			   		{
			   			int chessX=(int)(140+18*j-9);
			   			int chessY=(int)(140+18*i-9);
			   			g.drawImage(whiteChess,chessX,chessY,15,15,this);

			   		}
		   		}
	   		}
		   	for(int i1=1;i1<=C6_MousePolice.list2.size();i1++)
		   	{
		   		if(C6_MousePolice.needClean==0)
		   		{
			   		g.setColor(Color.gray);
			   		g.setFont(new Font("微软雅黑",1,10));
			   		int x=(int)(140+18*C6_MousePolice.list2.get(i1-1).x-9);
			   		int y=(int)(140+18*C6_MousePolice.list2.get(i1-1).y-9);
			   		g.drawString(""+i1, x+2, y+10);
		   		}
		   		
		   	}
			repaint();//有棋子
   		}
   		else if(C6_MousePolice.over==1||C6_MousePolice.needClean==1)//有一方赢棋或者启动清盘操作
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

			C6_MousePolice.over=0;
			C6_MousePolice.needClean=0;
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

//录入比赛信息
class LuRu_JieMian extends JFrame implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static String name_B,name_W,name_Location,name_Race,win_Side;
	
	JLabel label1,label2,label3,label4,label5,label6,label7,label8;
	JTextField text1,text2,text3,text4;
	JButton button_Yes,button;
	JRadioButton radioB,radioW,radioP;
	ButtonGroup group;
	
	JFrame frame;
	C6_MousePolice m=new C6_MousePolice(frame);
	LuRu_JieMian()
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

		label1=new JLabel("先手：");
		label1.setBounds(60,60,40,25);
		p.add(label1);
		text1=new JTextField(10);
		text1.setBounds(100,60,100,25);
		text1.setBackground(c2);
		p.add(text1);

		label2=new JLabel("后手：");
		label2.setBounds(270,60,40,25);
		p.add(label2);
		text2=new JTextField(10);
		text2.setBounds(310,60,100,25);
		text2.setBackground(c2);
		p.add(text2);
		
		label3=new JLabel("比赛地点：");
		label3.setBounds(40,135,70,25);
		p.add(label3);
		text3=new JTextField(10);
		text3.setBounds(100,135,100,25);
		text3.setBackground(c2);
		p.add(text3);
		
		label4=new JLabel("竞赛名称：");
		label4.setBounds(250,135,70,25);
		p.add(label4);
		text4=new JTextField(10);
		text4.setBounds(310,135,100,25);
		text4.setBackground(c2);
		p.add(text4);
		
		label5=new JLabel("获胜方：");
		label5.setBounds(50,210,70,25);
		p.add(label5);
		
		label6=new JLabel("先手");
		label6.setBounds(150,210,50,25);
		p.add(label6);
		
		label7=new JLabel("后手");
		label7.setBounds(260,210,50,25);
		p.add(label7);
		
		label8=new JLabel("和棋");
		label8.setBounds(380,210,50,25);
		p.add(label8);
		
		group = new ButtonGroup(); 
	    radioB = new JRadioButton(); 
	    radioW = new JRadioButton();
	    radioP = new JRadioButton();
	    group.add(radioB); 
	    group.add(radioW);
	    group.add(radioP);
	    
	    radioB.setBounds(120,210,25,25);
	    radioB.setBackground(c);
	    radioW.setBounds(230,210,25,25);
	    radioW.setBackground(c);
	    radioP.setBounds(350,210,25,25);
	    radioP.setBackground(c);
	    add(radioB);
	    add(radioW);
	    add(radioP);
	    
		button=new JButton("确定");
		button.setBounds(200,260,80,25);
		button.setBackground(c2);
		p.add(button);
		
		button.addActionListener(this);
		radioB.addActionListener(this);
		radioW.addActionListener(this);
		radioP.addActionListener(this);
		add(p,BorderLayout.CENTER);
	}

	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==button)//保存棋谱
		{
			if(Save_Way.way==1)
			{
				name_B=text1.getText();
				name_W=text2.getText();
				name_Location=text3.getText();
				name_Race=text4.getText();
				//System.out.println("获取的信息如下："+name_B+name_W+name_Location+name_Race);
				dispose();
				//m.Save_JieMian s=new Save_JieMian();//将棋谱信息保存至自定义数据库表中
				m.display();//将棋谱信息保存到自定义文件中
			}
			if(Save_Way.way==2)
			{
				name_B=text1.getText();
				name_W=text2.getText();
				name_Location=text3.getText();
				name_Race=text4.getText();
				//System.out.println("获取的信息如下："+name_B+name_W+name_Location+name_Race);
				dispose();
				new Save_JieMian();
			}
		}
		if(e.getSource()==radioB)
		{
			win_Side="先手胜";
		}
		if(e.getSource()==radioW)
		{
			win_Side="后手胜";
		}
		if(e.getSource()==radioP)
		{
			win_Side="和棋";
		}
	}
}
