package mimi;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
public class WindowMouse extends JPanel
{
   	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTextArea textArea,textAreaB,textAreaW,textAreaL;//定义右侧三个区域	
   	Toolkit tool,toolG,toolB,toolW,toolL,tool_hua;
   	Image image,blackChess,whiteChess,backGround,hua;//定义棋盘、黑棋、白棋、背景图片
	JTextField text,textB,textW,num;
	JButton button,button1,button2,button3,button4,button5,button6,button7;
	JFrame frame;
	MousePolice mouse;
   	public WindowMouse(JFrame frame)
   	{
   		this.frame=frame;
   		mouse=new MousePolice(frame);
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
       	tool_hua=getToolkit();
       	hua=tool_hua.getImage("花.gif");
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
      	textArea=new JTextArea(10,25);
      	textArea.setBackground(c2);
      	textAreaB=new JTextArea(10,25);//黑方显示区域
      	textAreaW=new JTextArea(10,25);//白方显示区域
      	textAreaL=new JTextArea(10,25);//消息输入区域
      	
      	button=new JButton("执行");//执行
      	button1=new JButton(new ImageIcon("左.png"));//后退
      	button2=new JButton(new ImageIcon("右.png"));//前进
      	button3=new JButton(new ImageIcon("快退.png"));//初始
      	button4=new JButton(new ImageIcon("快进.png"));//结束
      	button5=new JButton(new ImageIcon("主.png"));//返回主菜单
      	button6=new JButton(new ImageIcon("文件夹.png"));//打开
      	button7=new JButton("退出");
        
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
      	
		textB=new JTextField(10);
		textB.setBackground(c2);
		
		textW=new JTextField(10);
		textW.setBackground(c2);
		
		pB.add(textB,BorderLayout.CENTER);
		pW.add(textW,BorderLayout.CENTER);
		
		JScrollPane js=new JScrollPane(textArea);//加滚动条
		js.setBounds(520,180,250,150);
		add(js);
		
		//按钮
		button.setBounds(650,0,80,50);
		button.setBackground(null);
		add(button);//执行
		
		button6.setBounds(0,0,80,50);
		button6.setBackground(null);
		add(button6);//打开
		
		button3.setBounds(80,0,80,50);
		button3.setBackground(null);
		add(button3);//倒退到最初(空)
		
		button1.setBounds(160,0,80,50);
		button1.setBackground(null);
		add(button1);//倒退一步
		
		button2.setBounds(240,0,80,50);
		button2.setBackground(null);
		add(button2);//前进一步
		
		button4.setBounds(320,0,80,50);
		button4.setBackground(null);
		add(button4);//前进到结束(满)
		
		button5.setBounds(400,0,80,50);
		button5.setBackground(null);
		add(button5);
		
		button7.setBounds(540,0,80,50);
		button7.setBackground(null);
		add(button7);//退出

		//添加监视器
		
      	mouse.setJTextArea(textArea);
      	mouse.setJTextBField(textB);
      	mouse.setJTextWField(textW);
      	mouse.setJButton_button(button);
      	mouse.setJButton_button1(button1);
      	mouse.setJButton_button2(button2);
      	mouse.setJButton_button3(button3);
      	mouse.setJButton_button4(button4);
      	mouse.setJButton_button5(button5);
      	mouse.setJButton_button6(button6);
      	mouse.setJButton_button7(button7);
      	
      	//addMouseListener(mouse);
      	button.addActionListener(mouse);
      	button1.addActionListener(mouse);
      	button2.addActionListener(mouse);
      	button3.addActionListener(mouse);
      	button4.addActionListener(mouse);
      	button5.addActionListener(mouse);
      	button6.addActionListener(mouse);
      	button7.addActionListener(mouse);
      	
      	add(pB);
      	add(pW);
      	
      	num=new JTextField(200);
      	num.setBackground(Color.white);
      	num.setLocation(100,100);
      	add(num,BorderLayout.CENTER);
   	}
   	//绘图函数
   	public void paint(Graphics g)
   	{
   		
   		if(MousePolice.over==0||MousePolice.needClean==0)//没有一方赢且不需要清盘操作
   		{
	   		super.paint(g);//有组件
	   		
	   		g.drawImage(backGround,0,50,520,600,this);//添加背景图片
	        g.drawImage(image,100,100,400,400,this);//添加棋盘
	        g.drawImage(hua,0,0,800,600,this);//添加背景图片
	        
		   	for(int i=0;i<19;i++)
		   	{
		   		for(int j=0;j<19;j++)
		   		{
			   		if(MousePolice.board[i][j]==1)//棋盘上该位置是黑棋
			   		{
			   			int chessX=(int)(140+18*j-9);
			   			int chessY=(int)(140+18*i-9);
			   			g.drawImage(blackChess,chessX,chessY,15,15,this);
			   			
			   		}
			   		else if(MousePolice.board[i][j]==-1)//棋盘上该位置是白棋
			   		{
			   			int chessX=(int)(140+18*j-9);
			   			int chessY=(int)(140+18*i-9);
			   			g.drawImage(whiteChess,chessX,chessY,15,15,this);

			   		}
		   		}
	   		}
		   	for(int i1=1;i1<=MousePolice.n+1;i1++)
		   	{
		   		//System.out.println("显示："+MousePolice.n);
		   		if(Testt.list.size()==0)
		   		{
		   			return;
		   		}
		   		g.setColor(Color.gray);
		   		g.setFont(new Font("微软雅黑",1,10));
		   		int x=(int)(140+18*Testt.list.get(i1-1).x-9);
		   		int y=(int)(140+18*Testt.list.get(i1-1).y-9);
		   		
		   		g.drawString(""+i1, x-17, y-7);
		   	}
			repaint();//有棋子
   		}
   		else if(MousePolice.over==1||MousePolice.needClean==1)//有一方赢棋或者启动清盘操作
   		{
   			super.paint(g);
   			g.drawImage(backGround,0,50,520,600,this);
   			g.drawImage(image,100,100,400,400,this);
   			//将相关变量初始化
   			mouse.initBoard();
   			mouse.initB();
   			MousePolice.sideCount=1;
		   	MousePolice.side=1;
			MousePolice.isWin=0;

			MousePolice.over=0;
			MousePolice.needClean=0;
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
   		//画直线
   		if(MousePolice.list_win.size()==6)
   		{
   			//System.out.println("画直线");
   			Graphics2D g2 = (Graphics2D)g;//一个Graphics对象可以强制转化为Graphics2D类型（向下转化）
			g2.setStroke(new BasicStroke(4.0f));//定义直线的粗细
   			g2.setColor(Color.GREEN);//定义直线的颜色（绿色）
   			
   			for(int i2=0;i2<5;i2++)
   			{
   				//将坐标转化为像素
   				int xx1=MousePolice.list_win.get(i2).x*18+140;
   				int yy1= MousePolice.list_win.get(i2).y*18+140;
   				int xx2= (MousePolice.list_win.get(i2+1).x)*18+140;
   				int yy2=(MousePolice.list_win.get(i2+1).y)*18+140;
   				//画直线
   				g2.drawLine(xx1,yy1,xx2,yy2);
   			}
   		}
   		
  		
   		
   	}
}

class Menu extends JFrame implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JMenuItem item1,item2,item3,item4,item5,item6,item7,item8,item9,item10;
	JMenu menu1,menu2,menu3;
	JMenuBar menuBar;
	Menu()
	{
		init();
		setLocation(10,10);
        setSize(50,200);
        setVisible(true);
        setResizable(true);
	}
	public void init()
	{
		menuBar = new JMenuBar();//创建菜单栏对象
		
        menu1 = new JMenu("文件(F)");// 创建菜单对象
        menuBar.add(menu1);//将菜单对象添加到菜单栏对象中
        
        item1 = new JMenuItem("  新建(N)      Ctrl+N");//创建子菜单对象
        item1.addActionListener(this);
        menu1.add(item1);//将子菜单对象添加到一级菜单对象中
        
        item2 = new JMenuItem("  打开(O)      Ctrl+O");//创建子菜单对象
        item2.addActionListener(this);
        menu1.add(item2);//将子菜单对象添加到一级菜单对象中
        
        item3 = new JMenuItem("  保存(N)");//创建子菜单对象
        item3.addActionListener(this);
        //menu1.add(item3);//将子菜单对象添加到一级菜单对象中
        
        item4 = new JMenuItem("  退出(X)");//创建子菜单对象
        item4.addActionListener(this);
        menu1.add(item4);//将子菜单对象添加到一级菜单对象中


        menu1.addSeparator();//在菜单项中间添加分界线

        menu2 = new JMenu("视图(V)");// 创建菜单对象
        menuBar.add(menu2);//将菜单对象添加到菜单栏对象中
        
        item5 = new JMenuItem("  工具栏(T)      ");//创建子菜单对象
        item5.addActionListener(this);
        menu2.add(item5);//将子菜单对象添加到一级菜单对象中
        
        item6 = new JMenuItem("  状态栏(S)      ");//创建子菜单对象
        item6.addActionListener(this);
        menu2.add(item6);//将子菜单对象添加到一级菜单对象中
        
        menu3 = new JMenu("游戏控制(C)");// 创建菜单对象
        menuBar.add(menu3);//将菜单对象添加到菜单栏对象中
        
        item7 = new JMenuItem("  后退一步(N)      ");//创建子菜单对象
        item7.addActionListener(this);
        menu3.add(item7);//将子菜单对象添加到一级菜单对象中
        
        item8 = new JMenuItem("  前进一步(O)      ");//创建子菜单对象
        item8.addActionListener(this);
        menu3.add(item8);//将子菜单对象添加到一级菜单对象中
        
        item9 = new JMenuItem("  倒退到最初(N)      ");//创建子菜单对象
        item9.addActionListener(this);
        menu3.add(item9);//将子菜单对象添加到一级菜单对象中
        
        item10 = new JMenuItem("  前进到最后(X)      ");//创建子菜单对象
        item10.addActionListener(this);
        menu3.add(item10);//将子菜单对象添加到一级菜单对象中
       
        
        setJMenuBar(menuBar);
	}
	//按钮监视器
   	public void actionPerformed(ActionEvent e)
	{
   		if(e.getSource()==item1)//转到人人六子棋界面
		{
   			dispose();
			//转到六子棋界面
			JFrame p=new JFrame();
			C6_WindowMouse win = new C6_WindowMouse(p);
			
			p.add(win);
   			p.setTitle("人人六子棋");
			p.setBounds(10,10,800,600);
			Color c=new Color(188,143,143);
			p.setBackground(c);
   			p.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  			p.setVisible(true);
		}
   		if(e.getSource()==item2)//打开文件
		{
   			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter=new FileNameExtensionFilter("棋谱.txt","txt");
			chooser.setFileFilter(filter);
			int r = chooser.showOpenDialog(this);
			if(r==JFileChooser.APPROVE_OPTION)
			{
				MousePolice.name=chooser.getSelectedFile().getName();
				MousePolice.path=chooser.getSelectedFile().getPath();
				
			}
			new Testt();
			Testt.transt();
		}
   		if(e.getSource()==item4)//退出
		{
   			System.exit(0);
		}
   		if(e.getSource()==item7)//后退一步
		{
   			MousePolice.one_by_one_play1();
			//System.out.println("后退1\n");
			MousePolice.n--;
			if(MousePolice.n<0)
			{
				MousePolice.n=0;
			}
		}
		if(e.getSource()==item8)//前进一步
		{
			MousePolice.one_by_one_play2();
			//System.out.println("前进1\n");
			MousePolice.n++;
			if(MousePolice.n>Testt.list.size()-1)
			{
				MousePolice.n=Testt.list.size()-1;
			}
		}
		if(e.getSource()==item9)//倒退到最初
		{
			while(MousePolice.n>=0)
			{
				MousePolice.one_by_one_play1();
				MousePolice.n--;
			}
			MousePolice.n=0;
		}
		if(e.getSource()==item10)//前进到最后
		{
			while(MousePolice.n<=Testt.list.size()-1)
			{
				MousePolice.one_by_one_play2();
				MousePolice.n++;
			}
			MousePolice.n=Testt.list.size()-1;
		}
	}
}