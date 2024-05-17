package mimi;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
class POSITION
{
	int x;
	int y;
	POSITION(int x,int y)
	{
		this.x=x;
		this.y=y;
	}
}
//随机下棋时用到的位置
class POSITIONWITHRANDOM
{
	POSITION pos1;
	POSITION pos2;
	int win;
	int total;
	double prob;
	POSITIONWITHRANDOM(POSITION pos1,POSITION pos2,int win,int total,double prob)
	{
		this.pos1=pos1;
		this.pos2=pos2;
		this.win=win;
		this.total=total;
		this.prob=prob;
	}
}
public class AI_MousePolice extends JFrame implements MouseListener,ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JFrame frame;
	JTextArea area;//公告显示区域
   	JButton button,reStart,button_Save,button_Back,button_Zhu;//开始和重新开始按钮
   	//Timer time;//时间类
	JTextField text,textB,textW;//黑白方显示提示的文本框
	
	static POSITION bestPosition;
	int x,y;//点击棋盘的坐标
   	static int board[][]=new int[19][19];//棋盘
   	int b[][]=new int[19][19];//棋盘的副本，用于判断该位置是否被占用
   	int sideCount=1;//用于换边的计数
   	int side=1;//初始化下棋方为黑方
   	//int side=1;
	int isWin=0;//判断是否赢的标记值
	int over=0;//是否赢棋，一方赢了就为1
	int needClean=0;//是否清盘，点击ReStart按钮，值为1
	AI_MousePolice(JFrame frame)
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
   	public void setJTextBField(JTextField textB)
   	{
      	this.textB=textB;
   	}
   	public void setJTextWField(JTextField textW)
   	{
      	this.textW=textW;
   	}
	public void mousePressed(MouseEvent e)
	{
		x=e.getX();
   		y=e.getY();
   		x=(int)((x-140+9)/18);
   		y=(int)((y-140+9)/18);//将像素转化成坐标
   		if(isFull())
   		{
   			System.out.println("下满了");
   		}
   		else
   		{
   			if(board[y][x]==0)
   			{
   				if(side==1) 
   				{
   					manPlay();//人下棋
   					if(side==-1)
   					{
   						AIplay();
   						AIplay();//机器下两步棋
   					}
   				}
   			}
   			else{}
   		}
	}
	public void mouseReleased(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
   	public void mouseExited(MouseEvent e){}
   	public void mouseClicked(MouseEvent e){}
   	
    public void actionPerformed(ActionEvent e)
    {
    	if(e.getSource()==reStart)//重新开始
		{
			needClean=1;//将需要清盘的标记值赋成1
		}
    	if(e.getSource()==button_Zhu)//重新开始
		{
			frame.dispose();
			Xuanze_JieMian x=new Xuanze_JieMian();
			x.setBounds(200,200,450,350);
			x.setTitle("选择界面");
		}
    }
   	public void manPlay()
   	{
   		//下棋
   		board[y][x]=side;
   		//判断输赢
   		if(cIsWin()==true&&side==1)//黑方获胜
   		{
   			textB.setText("恭喜你获胜！");
			textW.setText("很遗憾！你输了....");
   			over=1;
   			area.append("BLACK WIN!");
   		}
   		//换边
   		sideCount++;
		if(sideCount==2)//计数达到2  换边
		{
		   	side=-side;
		   	sideCount=0;
		}
   	}
   	public void AIplay()
   	{
   		genPositionWithRandom(board,1,17,1,17);//生成所有可下位置
   		negaMax(1,-1);
   		//alphaBeta(1,-10000,10000,-1);//Alpha-Beta算法
   		//下棋
   		board[bestPosition.x][bestPosition.y]=side;	
   		//判断输赢
   		if(Win(board)==true&&side==-1)//白方获胜
   		{
   			System.out.println("白方获胜\n");
   			textW.setText("恭喜你获胜！");
			textB.setText("很遗憾！你输了....");
   			over=1;
   			area.append("WHITE WIN!");
   		}
   		//换边
   		sideCount++;
		if(sideCount==2)//计数达到2  换边
		{
		   	side=-side;
		   	sideCount=0;
		}
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
	//负极大值算法
	public int negaMax(int depth,int playSide)
	{
		POSITION pos;
	    int bestValue = -1000;
	    pos=new POSITION(y,x);
	  //特殊情况（避免POS.x或POS.y超出棋盘大小）
		if(pos.x-4<0||pos.y-4<0)
		{
		    pos.x=4;
		    pos.y=4;
		}
		if(pos.x+4>18||pos.y+4>18)
		{
		    pos.x=14;
		    pos.y=14;
		}
	    List<POSITIONWITHRANDOM> vecPos= genPositionWithRandom(board,pos.y-4,pos.y+4,pos.x-4,pos.x+4);
	    vecPos.iterator();
	    //在中间位置放一个黑棋
	    if(depth<=0)
	    {
	        return value(playSide,pos);
	    }
	    if(isFull())
	    {
	        return 0;
	    }

	    int i;
	    int val;
	    board[9][9]=1;
	    for(i=0;i<500;i++)
	    {
	        board[vecPos.get(i).pos1.x][vecPos.get(i).pos1.y] = -1;
	        board[vecPos.get(i).pos2.x][vecPos.get(i).pos2.y] = -1;
	        pos.x=vecPos.get(i).pos2.x;   pos.y=vecPos.get(i).pos2.y;

	        val = -negaMax(depth-1,1);
	        
	        if(val>bestValue)
	        {
	            bestValue = val;
	        }
	        bestPosition = new POSITION(vecPos.get(i).pos2.x, vecPos.get(i).pos2.y);

	        
	        board[vecPos.get(i).pos1.x][vecPos.get(i).pos1.y]= 0;
	        board[vecPos.get(i).pos2.x][vecPos.get(i).pos2.y]= 0;
	    }
	    
	    //board[9][9]=0;
	    //System.out.println("最佳位置"+bestPosition.x+"  "+bestPosition.y);
	    return bestValue;
	}
	//Alpha-Beta算法
	public int alphaBeta(int depth,int alpha,int beta,int playSide)
	{
		
	    if(depth<=0)
	    {
	        return valueAl(-1);
	    }
	    if(isFull())
	    {
	        return 0;
	    }
	    int i;
	    int val;
	    List<POSITIONWITHRANDOM> vecPos = new ArrayList<POSITIONWITHRANDOM>();
	    vecPos = genPositionWithRandom(board,1,17,1,17);

	    board[9][9]=1;
	    for(i=0;i<500;i++)
	    {
	        board[vecPos.get(i).pos1.x][vecPos.get(i).pos1.y] = -1;
	        board[vecPos.get(i).pos2.x][vecPos.get(i).pos2.y] = -1;
	        val = -alphaBeta(depth-1,-beta,-alpha,1);
	        if(val>=beta)
	        {
	            return beta;
	        }
	        if(val>alpha)
	        {
	            alpha = val;
	        }
	        bestPosition = new POSITION(vecPos.get(i).pos1.x,vecPos.get(i).pos1.y);
	        val = -alphaBeta(depth-1,-beta,-alpha,1);
	        if(val>=beta)
	        {
	            return beta;
	        }
	        if(val>alpha)
	        {
	            alpha = val;
	        }
	        bestPosition = new POSITION(vecPos.get(i).pos2.x,vecPos.get(i).pos2.y);

	        board[vecPos.get(i).pos1.x][vecPos.get(i).pos1.y] = 0;
	        board[vecPos.get(i).pos2.x][vecPos.get(i).pos2.y] = 0;
	    }

	    //board[9][9]=0;
	    
	    return alpha;
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
	//生成两个位置的算法如下：
	//生成用于随机下棋所需的位置
	public List<POSITIONWITHRANDOM> genPositionWithRandom(int board[][],int xmin,int xmax,int ymin,int ymax)
	{
	    //两个位置的链表
	    List<POSITIONWITHRANDOM> vec=new ArrayList<POSITIONWITHRANDOM>();
	    POSITION pos1,pos2;
	    int i,j,k;
	    //生成第一个位置的链表
	    List<POSITION> vecPos1=new ArrayList<POSITION>();
	    //两个位置的变量
	    POSITIONWITHRANDOM randomPos;
	    //生成第一个位置
	    if(xmin<0||ymin<0)
		{
		    xmin=4;
		    ymin=4;
		}
		if(xmax>18||ymax>18)
		{
		    xmax=14;
		    ymax=14;
		}
	    for(i=xmin;i<=xmax;i++)
	    {
	        for(j=ymin;j<=ymax;j++)
	        {
	            if(board[i][j]==0)
	            {
	            	pos1=new POSITION(i,j);
	                vecPos1.add(pos1);
	            }
	        }
	    }
	    for(k=0;k<vecPos1.size();k++)
	    {
	        board[vecPos1.get(i).x][vecPos1.get(i).y] = 1;//任意附一个棋子占位
	        for(i=xmin;i<=xmax;i++)
	        {
	            for(j=ymin;j<=ymax;j++)
	            {
	                if(board[i][j]==0)
	                {
	                	pos1=new POSITION(vecPos1.get(k).x,vecPos1.get(k).y);
	                	pos2=new POSITION(i,j);
	                	randomPos=new POSITIONWITHRANDOM(pos1,pos2,0,0,0);
	                    vec.add(randomPos);
	                }
	            }
	        }
	        board[vecPos1.get(i).x][vecPos1.get(i).y] = 0;  //还原棋盘
	    }
	    return vec;
	}
	
// ！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！9*9估值
//对于当前棋盘上最后一个棋子的位置为中心，对其进行9*9大小范围内的估值
public int value(int playSide,POSITION POS)//参数为当前下棋方和当前棋盘最后下的位置
{
	int tempBoard[][]=new int [19][19];//临时棋盘
	int sideValue = 0;//当前方的估值
	int enemyValue = 0;//对方的估值
	int countWin = 0;
	int i;
	int j;
	//己方获胜
	if(Win(board)==true&&playSide==-1)
	{
		return 10000;
	}
	//对方获胜
	if(Win(board)==true&&playSide==1)
	{	
	    return -10000;
	}
    //特殊情况（避免POS.x或POS.y超出棋盘大小）
	if(POS.x-4<0||POS.y-4<0)
	{
	    POS.x=4;
	    POS.y=4;
	}
	if(POS.x+4>18||POS.y+4>18)
	{
	    POS.x=14;
	    POS.y=14;
	}
	//初始化估值用临时棋盘(当前下棋方)
	for(i=POS.x-4;i<=POS.x+4;i++)
	{
	    for(j=POS.y-4;j<=POS.y+4;j++)
	    {
	        if(board[i][j]!=0)
	        {
	            tempBoard[i][j] = board[i][j];
	        }
	        else
	        {
	            tempBoard[i][j] = -1;
	        }
	    }
	}
	
//行计数，只对当前的9*9棋盘进行计数
	for(i=POS.x-4;i<=POS.x+4;i++)
	{
	    for(j=POS.y-4;j<=POS.y+4;j++)
	    {
	        if(tempBoard[i][j]==-1)
	        {
	            countWin++;
	            if(countWin>=6)
	            {
	                sideValue++;
	            }
	        }
	        else if(tempBoard[i][j]==1)
	        {
	            countWin=0;
	        }
	    }

	    countWin=0;
	}
	countWin=0;
//列计数
	for(j=POS.y-4;j<=POS.y+4;j++)
	{
	    for(i=POS.x-4;i<=POS.x+4;i++)
	    {
	        if(tempBoard[i][j]==-1)
	        {
	            countWin++;
	            if(countWin>=6)
	            {
	                sideValue++;
	            }
	        }
	        else if(tempBoard[i][j]==1)
	        {
	            countWin=0;
	        }
	    }
	    countWin=0;
	}
	countWin=0;
//左上至右下
//(pos.x-4,pos.y-4)
	j=POS.y-4;
	for(i=POS.x-4;i<=POS.x+4;i++)
	{
		if(j<=POS.y+4)
		{
			if(tempBoard[i][j]==-1)
	        {
				countWin++;j++;
				if(countWin>=6){sideValue++;}
	        }
			else{countWin=0;}
		}
	}
	countWin = 0;
//(pos.x-3,pos.y-4)
	j=POS.y-4;
	for(i=POS.x-3;i<=POS.x+4;i++)
	{
		if(j<POS.y-5)
		{
			if(tempBoard[i][j]==-1)
			{
				countWin++;j++;
				if(countWin>=6){sideValue++;}
			}
			else{countWin=0;}
		}
	}
	countWin = 0;
//(pos.x-2,pos.y-4)
	j=POS.y-4;
	for(i=POS.x-2;i<=POS.x+4;i++)
	{
		if(j<POS.y-6)
		{
			if(tempBoard[i][j]==-1)
			{
				countWin++;j++;
				if(countWin>=6){sideValue++;}
			}
			else{countWin=0;}
		}
	}
	countWin = 0;
//(pos.x-1,pos.y-4)
	j=POS.y-4;
	for(i=POS.x-1;i<=POS.x+4;i++)
	{
		if(j<POS.y-7)
		{
			if(tempBoard[i][j]==-1)
			{
				countWin++;j++;
				if(countWin>=6){sideValue++;}
			}
			else{countWin=0;}
		}
	}
	countWin = 0;
//(pos.x-4,pos.y-3)
	j=POS.y-3;
	for(i=POS.x-4;i<=POS.x+3;i++)
	{
	    if(j<POS.y+4)
	    {
	    	if(tempBoard[i][j]==-1)
	    	{
	    		countWin++;j++;
	        if(countWin>=6){sideValue++;}
	    	}
	    	else{countWin=0;}
	    }
	}
	countWin = 0;
//(pos.x-4,pos.y-2)
	j=POS.y-2;
	for(i=POS.x-4;i<=POS.x+2;i++)
	{
		if(j<POS.y+4)
	    {
			if(tempBoard[i][j]==-1)
			{
				countWin++;j++;
				if(countWin>=6){ sideValue++; }
			}
			else{countWin=0;}
	    }
	}
	countWin = 0;
//(pos.x-4,pos.y-1)
	j=POS.y-1;
	for(i=POS.x-4;i<=POS.x+1;i++)
	{
		if(j<POS.y+4)
	    {
			if(tempBoard[i][j]==-1)
			{
				countWin++;j++;
				if(countWin>=6){ sideValue++; }
			}
			else{countWin=0;}
	    }
	}
	countWin = 0;
//左下至右上
//(pos.x+4,pos.y-4)
	j=POS.y-4;
	for(i=POS.x+4;i>=POS.x-4;i--)
	{
		if(j<=POS.y+4)
		{
			if(tempBoard[i][j]==-1)
	        {
				countWin++;j++;
				if(countWin>=6){sideValue++;}
	        }
			else{countWin=0;}
		}
	}
	countWin=0;
//(pos.x+3,pos.y-4)
	j=POS.y-4;
	for(i=POS.x+3;i>=POS.x-4;i--)
	{
		if(j<=POS.y+3)
		{
			if(tempBoard[i][j]==-1)
	        {
				countWin++;j++;
				if(countWin>=6){sideValue++;}
	        }
			else{countWin=0;}
		}
	}
	countWin=0;
//(pos.x+2,pos.y-4)
	j=POS.y-4;
	for(i=POS.x+2;i>=POS.x-4;i--)
	{
		if(j<=POS.y+2)
		{
			if(tempBoard[i][j]==-1)
	        {
				countWin++;j++;
				if(countWin>=6){sideValue++;}
	        }
			else{countWin=0;}
		}
	}
	countWin=0;
//(pos.x+1,pos.y-4)
	j=POS.y-4;
	for(i=POS.x+1;i>=POS.x-4;i--)
	{
		if(j<=POS.y+1)
		{
			if(tempBoard[i][j]==-1)
	        {
				countWin++;j++;
				if(countWin>=6){sideValue++;}
	        }
			else{countWin=0;}
		}
	}
	countWin=0;
//(pos.x+4,pos.y-3)
	j=POS.y-3;
	for(i=POS.x+4;i>=POS.x-3;i--)
	{
		if(j<=POS.y+4)
		{
			if(tempBoard[i][j]==-1)
	        {
				countWin++;j++;
				if(countWin>=6){sideValue++;}
	        }
			else{countWin=0;}
		}
	}
	countWin=0;
//(pos.x+4,pos.y-2)
	j=POS.y-2;
	for(i=POS.x+4;i>=POS.x+2;i--)
	{
		if(j<=POS.y+4)
		{
			if(tempBoard[i][j]==-1)
	        {
				countWin++;j++;
				if(countWin>=6){sideValue++;}
	        }
			else{countWin=0;}
		}
	}
	countWin=0;
//(pos.x+4,pos.y-1)
	j=POS.y-1;
	for(i=POS.x+4;i>=POS.x+1;i--)
	{
		if(j<=POS.y+4)
		{
			if(tempBoard[i][j]==-1)
	        {
				countWin++;j++;
				if(countWin>=6){sideValue++;}
	        }
			else{countWin=0;}
		}
	}
	countWin=0;
//cout<<"sideValue"<<sideValue<<endl;

//初始化估值用临时棋盘(当前敌方)enemyValue++;
	for(i=POS.x-4;i<=POS.x+4;i++)
	{
	    for(j=POS.y-4;j<=POS.y+4;j++)
	    {
	        if(board[i][j]!=0)
	        {
	            tempBoard[i][j] = board[i][j];
	        }
	        else
	        {
	            tempBoard[i][j] = 1;
	        }
	    }
	}
//特殊情况（避免POS.x或POS.y超出棋盘大小）
	if(POS.x-4<0||POS.y-4<0)
	{
	    POS.x=4;
	    POS.y=4;
	}
	if(POS.x+4>18||POS.y+4>18)
	{
	    POS.x=14;
	    POS.y=14;
	}
//行计数，只对当前的9*9棋盘进行计数
	for(i=POS.x-4;i<=POS.x+4;i++)
	{
	    for(j=POS.y-4;j<=POS.y+4;j++)
	    {
	        if(tempBoard[i][j]==-1)
	        {
	            countWin++;
	            if(countWin>=6)
	            {
	            	enemyValue++;
	            }
	        }
	        else if(tempBoard[i][j]==1)
	        {
	            countWin=0;
	        }
	    }

	    countWin=0;
	}
	countWin=0;
//列计数
	for(j=POS.y-4;j<=POS.y+4;j++)
	{
	    for(i=POS.x-4;i<=POS.x+4;i++)
	    {
	        if(tempBoard[i][j]==-1)
	        {
	            countWin++;
	            if(countWin>=6)
	            {
	            	enemyValue++;
	            }
	        }
	        else if(tempBoard[i][j]==1)
	        {
	            countWin=0;
	        }
	    }
	    countWin=0;
	}
	countWin=0;
//左上至右下
//(pos.x-4,pos.y-4)
	j=POS.y-4;
	for(i=POS.x-4;i<=POS.x+4;i++)
	{
		if(j<=POS.y+4)
		{
			if(tempBoard[i][j]==-1)
	        {
				countWin++;j++;
				if(countWin>=6){enemyValue++;}
	        }
			else{countWin=0;}
		}
	}
	countWin = 0;
//(pos.x-3,pos.y-4)
	j=POS.y-4;
	for(i=POS.x-3;i<=POS.x+4;i++)
	{
		if(j<POS.y-5)
		{
			if(tempBoard[i][j]==-1)
			{
				countWin++;j++;
				if(countWin>=6){enemyValue++;}
			}
			else{countWin=0;}
		}
	}
	countWin = 0;
//(pos.x-2,pos.y-4)
	j=POS.y-4;
	for(i=POS.x-2;i<=POS.x+4;i++)
	{
		if(j<POS.y-6)
		{
			if(tempBoard[i][j]==-1)
			{
				countWin++;j++;
				if(countWin>=6){enemyValue++;}
			}
			else{countWin=0;}
		}
	}
	countWin = 0;
//(pos.x-1,pos.y-4)
	j=POS.y-4;
	for(i=POS.x-1;i<=POS.x+4;i++)
	{
		if(j<POS.y-7)
		{
			if(tempBoard[i][j]==-1)
			{
				countWin++;j++;
				if(countWin>=6){enemyValue++;}
			}
			else{countWin=0;}
		}
	}
	countWin = 0;
//(pos.x-4,pos.y-3)
	j=POS.y-3;
	for(i=POS.x-4;i<=POS.x+3;i++)
	{
	    if(j<POS.y+4)
	    {
	    	if(tempBoard[i][j]==-1)
	    	{
	    		countWin++;j++;
	        if(countWin>=6){enemyValue++;}
	    	}
	    	else{countWin=0;}
	    }
	}
	countWin = 0;
//(pos.x-4,pos.y-2)
	j=POS.y-2;
	for(i=POS.x-4;i<=POS.x+2;i++)
	{
		if(j<POS.y+4)
	    {
			if(tempBoard[i][j]==-1)
			{
				countWin++;j++;
				if(countWin>=6){enemyValue++;}
			}
			else{countWin=0;}
	    }
	}
	countWin = 0;
//(pos.x-4,pos.y-1)
	j=POS.y-1;
	for(i=POS.x-4;i<=POS.x+1;i++)
	{
		if(j<POS.y+4)
	    {
			if(tempBoard[i][j]==-1)
			{
				countWin++;j++;
				if(countWin>=6){enemyValue++;}
			}
			else{countWin=0;}
	    }
	}
	countWin = 0;
//左下至右上
//(pos.x+4,pos.y-4)
	j=POS.y-4;
	for(i=POS.x+4;i>=POS.x-4;i--)
	{
		if(j<=POS.y+4)
		{
			if(tempBoard[i][j]==-1)
	        {
				countWin++;j++;
				if(countWin>=6){enemyValue++;}
	        }
			else{countWin=0;}
		}
	}
	countWin=0;
//(pos.x+3,pos.y-4)
	j=POS.y-4;
	for(i=POS.x+3;i>=POS.x-4;i--)
	{
		if(j<=POS.y+3)
		{
			if(tempBoard[i][j]==-1)
	        {
				countWin++;j++;
				if(countWin>=6){enemyValue++;}
	        }
			else{countWin=0;}
		}
	}
	countWin=0;
//(pos.x+2,pos.y-4)
	j=POS.y-4;
	for(i=POS.x+2;i>=POS.x-4;i--)
	{
		if(j<=POS.y+2)
		{
			if(tempBoard[i][j]==-1)
	        {
				countWin++;j++;
				if(countWin>=6){enemyValue++;}
	        }
			else{countWin=0;}
		}
	}
	countWin=0;
//(pos.x+1,pos.y-4)
	j=POS.y-4;
	for(i=POS.x+1;i>=POS.x-4;i--)
	{
		if(j<=POS.y+1)
		{
			if(tempBoard[i][j]==-1)
	        {
				countWin++;j++;
				if(countWin>=6){enemyValue++;}
	        }
			else{countWin=0;}
		}
	}
	countWin=0;
//(pos.x+4,pos.y-3)
	j=POS.y-3;
	for(i=POS.x+4;i>=POS.x-3;i--)
	{
		if(j<=POS.y+4)
		{
			if(tempBoard[i][j]==-1)
	        {
				countWin++;j++;
				if(countWin>=6){enemyValue++;}
	        }
			else{countWin=0;}
		}
	}
	countWin=0;
//(pos.x+4,pos.y-2)
	j=POS.y-2;
	for(i=POS.x+4;i>=POS.x+2;i--)
	{
		if(j<=POS.y+4)
		{
			if(tempBoard[i][j]==-1)
	        {
				countWin++;j++;
				if(countWin>=6){enemyValue++;}
	        }
			else{countWin=0;}
		}
	}
	countWin=0;
//(pos.x+4,pos.y-1)
	j=POS.y-1;
	for(i=POS.x+4;i>=POS.x+1;i--)
	{
		if(j<=POS.y+4)
		{
			if(tempBoard[i][j]==-1)
	        {
				countWin++;j++;
				if(countWin>=6){enemyValue++;}
	        }
			else{countWin=0;}
		}
	}
	countWin=0;
//cout<<"enemy:"<<enemyValue<<endl;
	return (sideValue-enemyValue);
	
}
	
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!全盘估值

	public int valueAl(int playSide)
	{
	    int [][]tempBoard=new int[19][19];//临时棋盘
	    int sideValue = 0;      //当前方的估值
	    int enemyValue = 0;     //对方的估值
	    int countWin = 0;
	    int i;
	    int j;
	    //己方获胜
	    if(Win(board)==true&&playSide==-1)
	    {
	        return 10000;
	    }
	    //对方获胜
	    if(Win(board)==true&&playSide==1)
	    {
	        return -10000;
	    }
	    //初始化估值用临时棋盘(当前下棋方)
	    for(i=0;i<19;i++)
	    {
	        for(j=0;j<19;j++)
	        {
	            if(board[i][j]!=0)
	            {
	                tempBoard[i][j] = board[i][j];
	            }
	            else
	            {
	                tempBoard[i][j] = -1;
	            }
	        }
	    }
	//displayBoard();
	    //行计数，对整个棋盘从（0，0）位置开始，一行一行计算胜率
	    for(i=0;i<19;i++)
	    {
	        for(j=0;j<19;j++)
	        {
	            if(tempBoard[i][j]==-1)
	            {
	                countWin++;
	                if(countWin>=6)
	                {
	                    sideValue++;
	                }
	            }
	            else if(tempBoard[i][j]==1)
	            {
	                countWin=0;
	            }
	        }

	        countWin=0;
	    }
	    countWin=0;
	    //列计数
	    for(j=0;j<19;j++)
	    {
	        for(i=0;i<19;i++)
	        {
	            if(tempBoard[i][j]==-1)
	            {
	                countWin++;
	                if(countWin>=6)
	                {
	                    sideValue++;
	                }
	            }
	            else if(tempBoard[i][j]==1)
	            {
	                countWin=0;
	            }
	        }

	        countWin=0;
	    }
	    countWin=0;
	//左上至右下
	//(0,0)
	    j=0;
	    for(i=0;i<19;i++)
	    {if(j<19)
	      {if(tempBoard[i][j]==-1)
	        {countWin++;j++;
	         if(countWin>=6){sideValue++;}
	        }
	       else{countWin=0;}
	      }
	     }countWin = 0;
	//(1,0)
	  j=0;
	  for(i=1;i<19;i++)
	   {if(j<18)
	     {if(tempBoard[i][j]==-1)
	      {countWin++;j++;
	       if(countWin>=6){sideValue++;}
	       }
	       else{countWin=0;}
	     }
	    }countWin = 0;
	//(2,0)
	  j=0;
	  for(i=2;i<19;i++)
	   {if(j<17)
	     {if(tempBoard[i][j]==-1)
	      {countWin++;j++;
	       if(countWin>=6){sideValue++;}
	       }
	       else{countWin=0;}
	     }
	    }countWin = 0;
	//(3,0)
	  j=0;
	  for(i=3;i<19;i++)
	   {if(j<16)
	     {if(tempBoard[i][j]==-1)
	      {countWin++;j++;
	       if(countWin>=6){sideValue++;}
	       }
	       else{countWin=0;}
	     }
	    }countWin = 0;
	//(4,0)
	  j=0;
	  for(i=4;i<19;i++)
	   {if(j<15)
	     {if(tempBoard[i][j]==-1)
	      {countWin++;j++;
	       if(countWin>=6){sideValue++;}
	       }
	       else{countWin=0;}
	     }
	    }countWin = 0;
	//(5,0)
	  j=0;
	  for(i=5;i<19;i++)
	   {if(j<14)
	     {if(tempBoard[i][j]==-1)
	      {countWin++;j++;
	       if(countWin>=6){sideValue++;}
	       }
	       else{countWin=0;}
	     }
	    }countWin = 0;
	//(6,0)
	  j=0;
	  for(i=6;i<19;i++)
	   {if(j<13)
	     {if(tempBoard[i][j]==-1)
	      {countWin++;j++;
	       if(countWin>=6){sideValue++;}
	       }
	       else{countWin=0;}
	     }
	    }countWin = 0;
	//(7,0)
	  j=0;
	  for(i=7;i<19;i++)
	   {if(j<12)
	     {if(tempBoard[i][j]==-1)
	      {countWin++;j++;
	       if(countWin>=6){sideValue++;}
	       }
	       else{countWin=0;}
	     }
	    }countWin = 0;
	//(8,0)
	  j=0;
	  for(i=8;i<19;i++)
	  {
	    if(j<11)
	    {
	      if(tempBoard[i][j]==-1)
	       {countWin++;j++;
	        if(countWin>=6){ sideValue++; }
	       }
	      else{countWin=0;}
	    }
	   } countWin = 0;
	//(9,0)
	  j=0;
	  for(i=9;i<19;i++)
	  {
	    if(j<10)
	    {
	      if(tempBoard[i][j]==-1)
	       {countWin++;j++;
	        if(countWin>=6){ sideValue++; }
	       }
	      else{countWin=0;}
	    }
	   } countWin = 0;
	//(10,0)
	  j=0;
	  for(i=10;i<19;i++)
	  {
	    if(j<9)
	    {
	      if(tempBoard[i][j]==-1)
	       {countWin++;j++;
	        if(countWin>=6){ sideValue++; }
	       }
	      else{countWin=0;}
	    }
	   } countWin = 0;
	//(11,0)
	  j=0;
	  for(i=11;i<19;i++)
	  {
	    if(j<8)
	    {
	      if(tempBoard[i][j]==-1)
	       {countWin++;j++;
	        if(countWin>=6){ sideValue++; }
	       }
	      else{countWin=0;}
	    }
	   } countWin = 0;
	//(12,0)
	  j=0;
	  for(i=12;i<19;i++)
	  {
	    if(j<7)
	    {
	      if(tempBoard[i][j]==-1)
	       {countWin++;j++;
	        if(countWin>=6){ sideValue++; }
	       }
	      else{countWin=0;}
	    }
	   } countWin = 0;
	//(13,0)
	  j=0;
	  for(i=13;i<19;i++)
	  {
	    if(j<6)
	    {
	      if(tempBoard[i][j]==-1)
	       {countWin++;j++;
	        if(countWin>=6){ sideValue++; }
	       }
	      else{countWin=0;}
	    }
	   } countWin = 0;
	//(0,1)
	  j=1;
	  for(i=0;i<18;i++)
	  {
	    if(j<19)
	    {
	      if(tempBoard[i][j]==-1)
	       {countWin++;j++;
	        if(countWin>=6){ sideValue++; }
	       }
	      else{countWin=0;}
	    }
	   } countWin = 0;
	//(0,2)
	  j=2;
	  for(i=0;i<17;i++)
	  {
	    if(j<19)
	    {
	      if(tempBoard[i][j]==-1)
	       {countWin++;j++;
	        if(countWin>=6){ sideValue++; }
	       }
	      else{countWin=0;}
	    }
	   } countWin = 0;
	//(0,3)
	  j=3;
	  for(i=0;i<16;i++)
	  {
	    if(j<19)
	    {
	      if(tempBoard[i][j]==-1)
	       {countWin++;j++;
	        if(countWin>=6){ sideValue++; }
	       }
	      else{countWin=0;}
	    }
	   } countWin = 0;
	//(0,4)
	  j=4;
	  for(i=0;i<15;i++)
	  {
	    if(j<19)
	    {
	      if(tempBoard[i][j]==-1)
	       {countWin++;j++;
	        if(countWin>=6){ sideValue++; }
	       }
	      else{countWin=0;}
	    }
	   } countWin = 0;
	//(0,5)
	  j=5;
	  for(i=0;i<14;i++)
	  {
	    if(j<19)
	    {
	      if(tempBoard[i][j]==-1)
	       {countWin++;j++;
	        if(countWin>=6){ sideValue++; }
	       }
	      else{countWin=0;}
	    }
	   } countWin = 0;
	//(0,6)
	  j=6;
	  for(i=0;i<13;i++)
	  {
	    if(j<19)
	    {
	      if(tempBoard[i][j]==-1)
	       {countWin++;j++;
	        if(countWin>=6){ sideValue++; }
	       }
	      else{countWin=0;}
	    }
	   } countWin = 0;
	//(0,7)
	  j=7;
	  for(i=0;i<12;i++)
	  {
	    if(j<19)
	    {
	      if(tempBoard[i][j]==-1)
	       {countWin++;j++;
	        if(countWin>=6){ sideValue++; }
	       }
	      else{countWin=0;}
	    }
	   } countWin = 0;
	//(0,8)
	  j=8;
	  for(i=0;i<11;i++)
	  {
	    if(j<19)
	    {
	      if(tempBoard[i][j]==-1)
	       {countWin++;j++;
	        if(countWin>=6){ sideValue++; }
	       }
	      else{countWin=0;}
	    }
	   } countWin = 0;
	//(0,9)
	  j=9;
	  for(i=0;i<10;i++)
	  {
	    if(j<19)
	    {
	      if(tempBoard[i][j]==-1)
	       {countWin++;j++;
	        if(countWin>=6){ sideValue++; }
	       }
	      else{countWin=0;}
	    }
	   } countWin = 0;
	//(0,10)
	  j=10;
	  for(i=0;i<9;i++)
	  {
	    if(j<19)
	    {
	      if(tempBoard[i][j]==-1)
	       {countWin++;j++;
	        if(countWin>=6){ sideValue++; }
	       }
	      else{countWin=0;}
	    }
	   } countWin = 0;
	//(0,11)
	  j=11;
	  for(i=0;i<8;i++)
	  {
	    if(j<19)
	    {
	      if(tempBoard[i][j]==-1)
	       {countWin++;j++;
	        if(countWin>=6){ sideValue++; }
	       }
	      else{countWin=0;}
	    }
	   } countWin = 0;
	//(0,12)
	  j=12;
	  for(i=0;i<7;i++)
	  {
	    if(j<19)
	    {
	      if(tempBoard[i][j]==-1)
	       {countWin++;j++;
	        if(countWin>=6){ sideValue++; }
	       }
	      else{countWin=0;}
	    }
	   } countWin = 0;
	//(0,13)
	  j=13;
	  for(i=0;i<6;i++)
	  {
	    if(j<19)
	    {
	      if(tempBoard[i][j]==-1)
	       {countWin++;j++;
	        if(countWin>=6){ sideValue++; }
	       }
	      else{countWin=0;}
	    }
	   } countWin = 0;
	//左下至右上
	//(18,0)
	    j=0;
	    for(i=18;i>=0;i--)
	    {if(j<19)
	      {if(tempBoard[i][j]==-1)
	        {countWin++;j++;
	         if(countWin>=6){sideValue++;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;

	//(17,0)
	    j=0;
	    for(i=17;i>=0;i--)
	    {if(j<18)
	      {if(tempBoard[i][j]==-1)
	        {countWin++;j++;
	         if(countWin>=6){sideValue++;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(16,0)
	    j=0;
	    for(i=16;i>=0;i--)
	    {if(j<17)
	      {if(tempBoard[i][j]==-1)
	        {countWin++;j++;
	         if(countWin>=6){sideValue++;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(15,0)
	    j=0;
	    for(i=15;i>=0;i--)
	    {if(j<16)
	      {if(tempBoard[i][j]==-1)
	        {countWin++;j++;
	         if(countWin>=6){sideValue++;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(14,0)
	    j=0;
	    for(i=14;i>=0;i--)
	    {if(j<15)
	      {if(tempBoard[i][j]==-1)
	        {countWin++;j++;
	         if(countWin>=6){sideValue++;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(13,0)
	    j=0;
	    for(i=13;i>=0;i--)
	    {if(j<14)
	      {if(tempBoard[i][j]==-1)
	        {countWin++;j++;
	         if(countWin>=6){sideValue++;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(12,0)
	    j=0;
	    for(i=12;i>=0;i--)
	    {if(j<13)
	      {if(tempBoard[i][j]==-1)
	        {countWin++;j++;
	         if(countWin>=6){sideValue++;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(11,0)
	    j=0;
	    for(i=11;i>=0;i--)
	    {if(j<12)
	      {if(tempBoard[i][j]==-1)
	        {countWin++;j++;
	         if(countWin>=6){sideValue++;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(10,0)
	    j=0;
	    for(i=10;i>=0;i--)
	    {if(j<11)
	      {if(tempBoard[i][j]==-1)
	        {countWin++;j++;
	         if(countWin>=6){sideValue++;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(9,0)
	    j=0;
	    for(i=9;i>=0;i--)
	    {if(j<10)
	      {if(tempBoard[i][j]==-1)
	        {countWin++;j++;
	         if(countWin>=6){sideValue++;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(8,0)
	    j=0;
	    for(i=8;i>=0;i--)
	    {if(j<9)
	      {if(tempBoard[i][j]==-1)
	        {countWin++;j++;
	         if(countWin>=6){sideValue++;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(7,0)
	    j=0;
	    for(i=7;i>=0;i--)
	    {if(j<8)
	      {if(tempBoard[i][j]==-1)
	        {countWin++;j++;
	         if(countWin>=6){sideValue++;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(6,0)
	    j=0;
	    for(i=6;i>=0;i--)
	    {if(j<7)
	      {if(tempBoard[i][j]==-1)
	        {countWin++;j++;
	         if(countWin>=6){sideValue++;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(5,0)
	    j=0;
	    for(i=5;i>=0;i--)
	    {if(j<6)
	      {if(tempBoard[i][j]==-1)
	        {countWin++;j++;
	         if(countWin>=6){sideValue++;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(18,1)
	    j=1;
	    for(i=18;i>=1;i--)
	    {if(j<19)
	      {if(tempBoard[i][j]==-1)
	        {countWin++;j++;
	         if(countWin>=6){sideValue++;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(18,2)
	    j=2;
	    for(i=18;i>=2;i--)
	    {if(j<19)
	      {if(tempBoard[i][j]==-1)
	        {countWin++;j++;
	         if(countWin>=6){sideValue++;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(18,3)
	    j=3;
	    for(i=18;i>=3;i--)
	    {if(j<19)
	      {if(tempBoard[i][j]==-1)
	        {countWin++;j++;
	         if(countWin>=6){sideValue++;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(18,4)
	    j=4;
	    for(i=18;i>=4;i--)
	    {if(j<19)
	      {if(tempBoard[i][j]==-1)
	        {countWin++;j++;
	         if(countWin>=6){sideValue++;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(18,5)
	    j=5;
	    for(i=18;i>=5;i--)
	    {if(j<19)
	      {if(tempBoard[i][j]==-1)
	        {countWin++;j++;
	         if(countWin>=6){sideValue++;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(18,6)
	    j=6;
	    for(i=18;i>=6;i--)
	    {if(j<19)
	      {if(tempBoard[i][j]==-1)
	        {countWin++;j++;
	         if(countWin>=6){sideValue++;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(18,7)
	    j=7;
	    for(i=18;i>=7;i--)
	    {if(j<19)
	      {if(tempBoard[i][j]==-1)
	        {countWin++;j++;
	         if(countWin>=6){sideValue++;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(18,8)
	    j=8;
	    for(i=18;i>=8;i--)
	    {if(j<19)
	      {if(tempBoard[i][j]==-1)
	        {countWin++;j++;
	         if(countWin>=6){sideValue++;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(18,9)
	    j=9;
	    for(i=18;i>=9;i--)
	    {if(j<19)
	      {if(tempBoard[i][j]==-1)
	        {countWin++;j++;
	         if(countWin>=6){sideValue++;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(18,10)
	    j=10;
	    for(i=18;i>=10;i--)
	    {if(j<19)
	      {if(tempBoard[i][j]==-1)
	        {countWin++;j++;
	         if(countWin>=6){sideValue++;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(18,11)
	    j=11;
	    for(i=18;i>=11;i--)
	    {if(j<19)
	      {if(tempBoard[i][j]==-1)
	        {countWin++;j++;
	         if(countWin>=6){sideValue++;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(18,12)
	    j=12;
	    for(i=18;i>=12;i--)
	    {if(j<19)
	      {if(tempBoard[i][j]==-1)
	        {countWin++;j++;
	         if(countWin>=6){sideValue++;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(18,13)
	    j=13;
	    for(i=18;i>=13;i--)
	    {if(j<19)
	      {if(tempBoard[i][j]==-1)
	        {countWin++;j++;
	         if(countWin>=6){sideValue++;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;

	 //cout<<"sideValue"<<sideValue<<endl;

	//初始化估值用临时棋盘(当前敌方)
	    for(i=0;i<19;i++)
	    {
	        for(j=0;j<19;j++)
	        {
	            if(board[i][j]!=0)
	            {
	                tempBoard[i][j] = board[i][j];
	            }
	            else
	            {
	                tempBoard[i][j] = 1;
	            }
	        }
	    }

	    //敌方获胜情况处理
	    //行计数，整个棋盘从（0，0）开始，一行一行计算胜率
	    for(i=0;i<19;i++)
	    {
	        for(j=0;j<19;j++)
	        {
	            if(tempBoard[i][j]==1)
	            {
	                countWin++;
	                if(countWin>=6)
	                {
	                    enemyValue++;
	                }
	            }
	            else if(tempBoard[i][j]==-1)
	            {
	                countWin=0;
	            }
	        }

	        countWin=0;
	    }
	    countWin=0;
	    //列计数
	    for(j=0;j<19;j++)
	    {
	        for(i=0;i<19;i++)
	        {
	            if(tempBoard[i][j]==1)
	            {
	                countWin++;
	                if(countWin>=6)
	                {
	                    enemyValue++;
	                }
	            }
	            else if(tempBoard[i][j]==-1)
	            {
	                countWin=0;
	            }
	        }

	        countWin=0;
	    }
	    countWin=0;
	//左上至右下
	//(0,0)
	    j=0;
	    for(i=0;i<19;i++)
	    {if(j<19)
	      {if(tempBoard[i][j]==1)
	        {countWin++;j++;
	         if(countWin>=6){enemyValue++;}
	        }
	       else{countWin=0;}
	      }
	     }countWin = 0;
	//(1,0)
	  j=0;
	  for(i=1;i<19;i++)
	   {if(j<18)
	     {if(tempBoard[i][j]==1)
	      {countWin++;j++;
	       if(countWin>=6){enemyValue++;}
	       }
	       else{countWin=0;}
	     }
	    }countWin = 0;
	//(2,0)
	  j=0;
	  for(i=2;i<19;i++)
	   {if(j<17)
	     {if(tempBoard[i][j]==1)
	      {countWin++;j++;
	       if(countWin>=6){enemyValue++;}
	       }
	       else{countWin=0;}
	     }
	    }countWin = 0;
	//(3,0)
	  j=0;
	  for(i=3;i<19;i++)
	   {if(j<16)
	     {if(tempBoard[i][j]==1)
	      {countWin++;j++;
	       if(countWin>=6){enemyValue++;}
	       }
	       else{countWin=0;}
	     }
	    }countWin = 0;
	//(4,0)
	  j=0;
	  for(i=4;i<19;i++)
	   {if(j<15)
	     {if(tempBoard[i][j]==1)
	      {countWin++;j++;
	       if(countWin>=6){enemyValue++;}
	       }
	       else{countWin=0;}
	     }
	    }countWin = 0;
	//(5,0)
	  j=0;
	  for(i=5;i<19;i++)
	   {if(j<14)
	     {if(tempBoard[i][j]==1)
	      {countWin++;j++;
	       if(countWin>=6){enemyValue++;}
	       }
	       else{countWin=0;}
	     }
	    }countWin = 0;
	//(6,0)
	  j=0;
	  for(i=6;i<19;i++)
	   {if(j<13)
	     {if(tempBoard[i][j]==1)
	      {countWin++;j++;
	       if(countWin>=6){enemyValue++;}
	       }
	       else{countWin=0;}
	     }
	    }countWin = 0;
	//(7,0)
	  j=0;
	  for(i=7;i<19;i++)
	   {if(j<12)
	     {if(tempBoard[i][j]==1)
	      {countWin++;j++;
	       if(countWin>=6){enemyValue++;}
	       }
	       else{countWin=0;}
	     }
	    }countWin = 0;
	//(8,0)
	  j=0;
	  for(i=8;i<19;i++)
	  {
	    if(j<11)
	    {
	      if(tempBoard[i][j]==1)
	       {countWin++;j++;
	        if(countWin>=6){ enemyValue++; }
	       }
	      else{countWin=0;}
	    }
	   } countWin = 0;
	//(9,0)
	  j=0;
	  for(i=9;i<19;i++)
	  {
	    if(j<10)
	    {
	      if(tempBoard[i][j]==1)
	       {countWin++;j++;
	        if(countWin>=6){ enemyValue++; }
	       }
	      else{countWin=0;}
	    }
	   } countWin = 0;
	//(10,0)
	  j=0;
	  for(i=10;i<19;i++)
	  {
	    if(j<9)
	    {
	      if(tempBoard[i][j]==1)
	       {countWin++;j++;
	        if(countWin>=6){ enemyValue++; }
	       }
	      else{countWin=0;}
	    }
	   } countWin = 0;
	//(11,0)
	  j=0;
	  for(i=11;i<19;i++)
	  {
	    if(j<8)
	    {
	      if(tempBoard[i][j]==1)
	       {countWin++;j++;
	        if(countWin>=6){ enemyValue++; }
	       }
	      else{countWin=0;}
	    }
	   } countWin = 0;
	//(12,0)
	  j=0;
	  for(i=12;i<19;i++)
	  {
	    if(j<7)
	    {
	      if(tempBoard[i][j]==1)
	       {countWin++;j++;
	        if(countWin>=6){ enemyValue++; }
	       }
	      else{countWin=0;}
	    }
	   } countWin = 0;
	//(13,0)
	  j=0;
	  for(i=13;i<19;i++)
	  {
	    if(j<6)
	    {
	      if(tempBoard[i][j]==1)
	       {countWin++;j++;
	        if(countWin>=6){ enemyValue++; }
	       }
	      else{countWin=0;}
	    }
	   } countWin = 0;
	//(0,1)
	  j=1;
	  for(i=0;i<18;i++)
	  {
	    if(j<19)
	    {
	      if(tempBoard[i][j]==1)
	       {countWin++;j++;
	        if(countWin>=6){ enemyValue++; }
	       }
	      else{countWin=0;}
	    }
	   } countWin = 0;
	//(0,2)
	  j=2;
	  for(i=0;i<17;i++)
	  {
	    if(j<19)
	    {
	      if(tempBoard[i][j]==1)
	       {countWin++;j++;
	        if(countWin>=6){ enemyValue++; }
	       }
	      else{countWin=0;}
	    }
	   } countWin = 0;
	//(0,3)
	  j=3;
	  for(i=0;i<16;i++)
	  {
	    if(j<19)
	    {
	      if(tempBoard[i][j]==1)
	       {countWin++;j++;
	        if(countWin>=6){ enemyValue++; }
	       }
	      else{countWin=0;}
	    }
	   } countWin = 0;
	//(0,4)
	  j=4;
	  for(i=0;i<15;i++)
	  {
	    if(j<19)
	    {
	      if(tempBoard[i][j]==1)
	       {countWin++;j++;
	        if(countWin>=6){ enemyValue++; }
	       }
	      else{countWin=0;}
	    }
	   } countWin = 0;
	//(0,5)
	  j=5;
	  for(i=0;i<14;i++)
	  {
	    if(j<19)
	    {
	      if(tempBoard[i][j]==1)
	       {countWin++;j++;
	        if(countWin>=6){ enemyValue++; }
	       }
	      else{countWin=0;}
	    }
	   } countWin = 0;
	//(0,6)
	  j=6;
	  for(i=0;i<13;i++)
	  {
	    if(j<19)
	    {
	      if(tempBoard[i][j]==1)
	       {countWin++;j++;
	        if(countWin>=6){ enemyValue++; }
	       }
	      else{countWin=0;}
	    }
	   } countWin = 0;
	//(0,7)
	  j=7;
	  for(i=0;i<12;i++)
	  {
	    if(j<19)
	    {
	      if(tempBoard[i][j]==1)
	       {countWin++;j++;
	        if(countWin>=6){ enemyValue++; }
	       }
	      else{countWin=0;}
	    }
	   } countWin = 0;
	//(0,8)
	  j=8;
	  for(i=0;i<11;i++)
	  {
	    if(j<19)
	    {
	      if(tempBoard[i][j]==1)
	       {countWin++;j++;
	        if(countWin>=6){ enemyValue++; }
	       }
	      else{countWin=0;}
	    }
	   } countWin = 0;
	//(0,9)
	  j=9;
	  for(i=0;i<10;i++)
	  {
	    if(j<19)
	    {
	      if(tempBoard[i][j]==1)
	       {countWin++;j++;
	        if(countWin>=6){ enemyValue++; }
	       }
	      else{countWin=0;}
	    }
	   } countWin = 0;
	//(0,10)
	  j=10;
	  for(i=0;i<9;i++)
	  {
	    if(j<19)
	    {
	      if(tempBoard[i][j]==1)
	       {countWin++;j++;
	        if(countWin>=6){ enemyValue++; }
	       }
	      else{countWin=0;}
	    }
	   } countWin = 0;
	//(0,11)
	  j=11;
	  for(i=0;i<8;i++)
	  {
	    if(j<19)
	    {
	      if(tempBoard[i][j]==1)
	       {countWin++;j++;
	        if(countWin>=6){ enemyValue++; }
	       }
	      else{countWin=0;}
	    }
	   } countWin = 0;
	//(0,12)
	  j=12;
	  for(i=0;i<7;i++)
	  {
	    if(j<19)
	    {
	      if(tempBoard[i][j]==1)
	       {countWin++;j++;
	        if(countWin>=6){ enemyValue++; }
	       }
	      else{countWin=0;}
	    }
	   } countWin = 0;
	//(0,13)
	  j=13;
	  for(i=0;i<6;i++)
	  {
	    if(j<19)
	    {
	      if(tempBoard[i][j]==1)
	       {countWin++;j++;
	        if(countWin>=6){ enemyValue++; }
	       }
	      else{countWin=0;}
	    }
	   } countWin = 0;
	//左下至右上
	//(18,0)
	    j=0;
	    for(i=18;i>=0;i--)
	    {if(j<19)
	      {if(tempBoard[i][j]==1)
	        {countWin++;j++;
	         if(countWin>=6){enemyValue++;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;

	//(17,0)
	    j=0;
	    for(i=17;i>=0;i--)
	    {if(j<18)
	      {if(tempBoard[i][j]==1)
	        {countWin++;j++;
	         if(countWin>=6){enemyValue++;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(16,0)
	    j=0;
	    for(i=16;i>=0;i--)
	    {if(j<17)
	      {if(tempBoard[i][j]==1)
	        {countWin++;j++;
	         if(countWin>=6){enemyValue++;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(15,0)
	    j=0;
	    for(i=15;i>=0;i--)
	    {if(j<16)
	      {if(tempBoard[i][j]==1)
	        {countWin++;j++;
	         if(countWin>=6){enemyValue++;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(14,0)
	    j=0;
	    for(i=14;i>=0;i--)
	    {if(j<15)
	      {if(tempBoard[i][j]==1)
	        {countWin++;j++;
	         if(countWin>=6){enemyValue++;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(13,0)
	    j=0;
	    for(i=13;i>=0;i--)
	    {if(j<14)
	      {if(tempBoard[i][j]==1)
	        {countWin++;j++;
	         if(countWin>=6){enemyValue++;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(12,0)
	    j=0;
	    for(i=12;i>=0;i--)
	    {if(j<13)
	      {if(tempBoard[i][j]==1)
	        {countWin++;j++;
	         if(countWin>=6){enemyValue++;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(11,0)
	    j=0;
	    for(i=11;i>=0;i--)
	    {if(j<12)
	      {if(tempBoard[i][j]==1)
	        {countWin++;j++;
	         if(countWin>=6){enemyValue++;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(10,0)
	    j=0;
	    for(i=10;i>=0;i--)
	    {if(j<11)
	      {if(tempBoard[i][j]==1)
	        {countWin++;j++;
	         if(countWin>=6){enemyValue++;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(9,0)
	    j=0;
	    for(i=9;i>=0;i--)
	    {if(j<10)
	      {if(tempBoard[i][j]==1)
	        {countWin++;j++;
	         if(countWin>=6){enemyValue++;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(8,0)
	    j=0;
	    for(i=8;i>=0;i--)
	    {if(j<9)
	      {if(tempBoard[i][j]==1)
	        {countWin++;j++;
	         if(countWin>=6){enemyValue++;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(7,0)
	    j=0;
	    for(i=7;i>=0;i--)
	    {if(j<8)
	      {if(tempBoard[i][j]==1)
	        {countWin++;j++;
	         if(countWin>=6){enemyValue++;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(6,0)
	    j=0;
	    for(i=6;i>=0;i--)
	    {if(j<7)
	      {if(tempBoard[i][j]==1)
	        {countWin++;j++;
	         if(countWin>=6){enemyValue++;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(5,0)
	    j=0;
	    for(i=5;i>=0;i--)
	    {if(j<6)
	      {if(tempBoard[i][j]==1)
	        {countWin++;j++;
	         if(countWin>=6){enemyValue++;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(18,1)
	    j=1;
	    for(i=18;i>=1;i--)
	    {if(j<19)
	      {if(tempBoard[i][j]==1)
	        {countWin++;j++;
	         if(countWin>=6){enemyValue++;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(18,2)
	    j=2;
	    for(i=18;i>=2;i--)
	    {if(j<19)
	      {if(tempBoard[i][j]==1)
	        {countWin++;j++;
	         if(countWin>=6){enemyValue++;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(18,3)
	    j=3;
	    for(i=18;i>=3;i--)
	    {if(j<19)
	      {if(tempBoard[i][j]==1)
	        {countWin++;j++;
	         if(countWin>=6){enemyValue++;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(18,4)
	    j=4;
	    for(i=18;i>=4;i--)
	    {if(j<19)
	      {if(tempBoard[i][j]==1)
	        {countWin++;j++;
	         if(countWin>=6){enemyValue++;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(18,5)
	    j=5;
	    for(i=18;i>=5;i--)
	    {if(j<19)
	      {if(tempBoard[i][j]==1)
	        {countWin++;j++;
	         if(countWin>=6){enemyValue++;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(18,6)
	    j=6;
	    for(i=18;i>=6;i--)
	    {if(j<19)
	      {if(tempBoard[i][j]==1)
	        {countWin++;j++;
	         if(countWin>=6){enemyValue++;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(18,7)
	    j=7;
	    for(i=18;i>=7;i--)
	    {if(j<19)
	      {if(tempBoard[i][j]==1)
	        {countWin++;j++;
	         if(countWin>=6){enemyValue++;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(18,8)
	    j=8;
	    for(i=18;i>=8;i--)
	    {if(j<19)
	      {if(tempBoard[i][j]==1)
	        {countWin++;j++;
	         if(countWin>=6){enemyValue++;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(18,9)
	    j=9;
	    for(i=18;i>=9;i--)
	    {if(j<19)
	      {if(tempBoard[i][j]==1)
	        {countWin++;j++;
	         if(countWin>=6){enemyValue++;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(18,10)
	    j=10;
	    for(i=18;i>=10;i--)
	    {if(j<19)
	      {if(tempBoard[i][j]==1)
	        {countWin++;j++;
	         if(countWin>=6){enemyValue++;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(18,11)
	    j=11;
	    for(i=18;i>=11;i--)
	    {if(j<19)
	      {if(tempBoard[i][j]==1)
	        {countWin++;j++;
	         if(countWin>=6){enemyValue++;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(18,12)
	    j=12;
	    for(i=18;i>=12;i--)
	    {if(j<19)
	      {if(tempBoard[i][j]==1)
	        {countWin++;j++;
	         if(countWin>=6){enemyValue++;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(18,13)
	    j=13;
	    for(i=18;i>=13;i--)
	    {if(j<19)
	      {if(tempBoard[i][j]==1)
	        {countWin++;j++;
	         if(countWin>=6){enemyValue++;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;

	   // cout<<"enemy:"<<enemyValue<<endl;
	    return (sideValue-enemyValue);
	}
	
	public boolean Win(int board[][])//全盘判断输赢，用于搜索算法的估值
	{
	    int countWin = 0;
	    int i;
	    int j;

	//displayBoard();
	    //行计数
	    for(i=0;i<19;i++)
	    {
	        for(j=0;j<19;j++)
	        {
	            if(board[i][j]==side)
	            {
	                countWin++;
	                if(countWin>=6)
	                {
	                    return true;
	                }
	            }
	            else
	            {
	                countWin=0;
	            }
	        }

	        countWin=0;
	    }
	    countWin=0;
	    //列计数
	    for(j=0;j<19;j++)
	    {
	        for(i=0;i<19;i++)
	        {
	            if(board[i][j]==side)
	            {
	                countWin++;
	                if(countWin>=6)
	                {
	                    return true;
	                }
	            }
	            else
	            {
	                countWin=0;
	            }
	        }

	        countWin=0;
	    }
	    countWin=0;
	//左上至右下
	//(0,0)
	    j=0;
	    for(i=0;i<19;i++)
	    {if(j<19)
	      {if(board[i][j]==side)
	        {countWin++;j++;
	         if(countWin>=6){return true;}
	        }
	       else{countWin=0;}
	      }
	     }countWin = 0;
	//(1,0)
	  j=0;
	  for(i=1;i<19;i++)
	   {if(j<18)
	     {if(board[i][j]==side)
	      {countWin++;j++;
	       if(countWin>=6){return true;}
	       }
	       else{countWin=0;}
	     }
	    }countWin = 0;
	//(2,0)
	  j=0;
	  for(i=2;i<19;i++)
	   {if(j<17)
	     {if(board[i][j]==side)
	      {countWin++;j++;
	       if(countWin>=6){return true;}
	       }
	       else{countWin=0;}
	     }
	    }countWin = 0;
	//(3,0)
	  j=0;
	  for(i=3;i<19;i++)
	   {if(j<16)
	     {if(board[i][j]==side)
	      {countWin++;j++;
	       if(countWin>=6){return true;}
	       }
	       else{countWin=0;}
	     }
	    }countWin = 0;
	//(4,0)
	  j=0;
	  for(i=4;i<19;i++)
	   {if(j<15)
	     {if(board[i][j]==side)
	      {countWin++;j++;
	       if(countWin>=6){return true;}
	       }
	       else{countWin=0;}
	     }
	    }countWin = 0;
	//(5,0)
	  j=0;
	  for(i=5;i<19;i++)
	   {if(j<14)
	     {if(board[i][j]==side)
	      {countWin++;j++;
	       if(countWin>=6){return true;}
	       }
	       else{countWin=0;}
	     }
	    }countWin = 0;
	//(6,0)
	  j=0;
	  for(i=6;i<19;i++)
	   {if(j<13)
	     {if(board[i][j]==side)
	      {countWin++;j++;
	       if(countWin>=6){return true;}
	       }
	       else{countWin=0;}
	     }
	    }countWin = 0;
	//(7,0)
	  j=0;
	  for(i=7;i<19;i++)
	   {if(j<12)
	     {if(board[i][j]==side)
	      {countWin++;j++;
	       if(countWin>=6){return true;}
	       }
	       else{countWin=0;}
	     }
	    }countWin = 0;
	//(8,0)
	  j=0;
	  for(i=8;i<19;i++)
	  {
	    if(j<11)
	    {
	      if(board[i][j]==side)
	       {countWin++;j++;
	        if(countWin>=6){return true; }
	       }
	      else{countWin=0;}
	    }
	   } countWin = 0;
	//(9,0)
	  j=0;
	  for(i=9;i<19;i++)
	  {
	    if(j<10)
	    {
	      if(board[i][j]==side)
	       {countWin++;j++;
	        if(countWin>=6){return true; }
	       }
	      else{countWin=0;}
	    }
	   } countWin = 0;
	//(10,0)
	  j=0;
	  for(i=10;i<19;i++)
	  {
	    if(j<9)
	    {
	      if(board[i][j]==side)
	       {countWin++;j++;
	        if(countWin>=6){return true; }
	       }
	      else{countWin=0;}
	    }
	   } countWin = 0;
	//(11,0)
	  j=0;
	  for(i=11;i<19;i++)
	  {
	    if(j<8)
	    {
	      if(board[i][j]==side)
	       {countWin++;j++;
	        if(countWin>=6){return true; }
	       }
	      else{countWin=0;}
	    }
	   } countWin = 0;
	//(12,0)
	  j=0;
	  for(i=12;i<19;i++)
	  {
	    if(j<7)
	    {
	      if(board[i][j]==side)
	       {countWin++;j++;
	        if(countWin>=6){return true; }
	       }
	      else{countWin=0;}
	    }
	   } countWin = 0;
	//(13,0)
	  j=0;
	  for(i=13;i<19;i++)
	  {
	    if(j<6)
	    {
	      if(board[i][j]==side)
	       {countWin++;j++;
	        if(countWin>=6){return true; }
	       }
	      else{countWin=0;}
	    }
	   } countWin = 0;
	//(0,1)
	  j=1;
	  for(i=0;i<18;i++)
	  {
	    if(j<19)
	    {
	      if(board[i][j]==side)
	       {countWin++;j++;
	        if(countWin>=6){return true; }
	       }
	      else{countWin=0;}
	    }
	   } countWin = 0;
	//(0,2)
	  j=2;
	  for(i=0;i<17;i++)
	  {
	    if(j<19)
	    {
	      if(board[i][j]==side)
	       {countWin++;j++;
	        if(countWin>=6){return true; }
	       }
	      else{countWin=0;}
	    }
	   } countWin = 0;
	//(0,3)
	  j=3;
	  for(i=0;i<16;i++)
	  {
	    if(j<19)
	    {
	      if(board[i][j]==side)
	       {countWin++;j++;
	        if(countWin>=6){return true; }
	       }
	      else{countWin=0;}
	    }
	   } countWin = 0;
	//(0,4)
	  j=4;
	  for(i=0;i<15;i++)
	  {
	    if(j<19)
	    {
	      if(board[i][j]==side)
	       {countWin++;j++;
	        if(countWin>=6){return true; }
	       }
	      else{countWin=0;}
	    }
	   } countWin = 0;
	//(0,5)
	  j=5;
	  for(i=0;i<14;i++)
	  {
	    if(j<19)
	    {
	      if(board[i][j]==side)
	       {countWin++;j++;
	        if(countWin>=6){return true; }
	       }
	      else{countWin=0;}
	    }
	   } countWin = 0;
	//(0,6)
	  j=6;
	  for(i=0;i<13;i++)
	  {
	    if(j<19)
	    {
	      if(board[i][j]==side)
	       {countWin++;j++;
	        if(countWin>=6){return true; }
	       }
	      else{countWin=0;}
	    }
	   } countWin = 0;
	//(0,7)
	  j=7;
	  for(i=0;i<12;i++)
	  {
	    if(j<19)
	    {
	      if(board[i][j]==side)
	       {countWin++;j++;
	        if(countWin>=6){return true; }
	       }
	      else{countWin=0;}
	    }
	   } countWin = 0;
	//(0,8)
	  j=8;
	  for(i=0;i<11;i++)
	  {
	    if(j<19)
	    {
	      if(board[i][j]==side)
	       {countWin++;j++;
	        if(countWin>=6){return true; }
	       }
	      else{countWin=0;}
	    }
	   } countWin = 0;
	//(0,9)
	  j=9;
	  for(i=0;i<10;i++)
	  {
	    if(j<19)
	    {
	      if(board[i][j]==side)
	       {countWin++;j++;
	        if(countWin>=6){return true; }
	       }
	      else{countWin=0;}
	    }
	   } countWin = 0;
	//(0,10)
	  j=10;
	  for(i=0;i<9;i++)
	  {
	    if(j<19)
	    {
	      if(board[i][j]==side)
	       {countWin++;j++;
	        if(countWin>=6){return true; }
	       }
	      else{countWin=0;}
	    }
	   } countWin = 0;
	//(0,11)
	  j=11;
	  for(i=0;i<8;i++)
	  {
	    if(j<19)
	    {
	      if(board[i][j]==side)
	       {countWin++;j++;
	        if(countWin>=6){return true; }
	       }
	      else{countWin=0;}
	    }
	   } countWin = 0;
	//(0,12)
	  j=12;
	  for(i=0;i<7;i++)
	  {
	    if(j<19)
	    {
	      if(board[i][j]==side)
	       {countWin++;j++;
	        if(countWin>=6){return true; }
	       }
	      else{countWin=0;}
	    }
	   } countWin = 0;
	//(0,13)
	  j=13;
	  for(i=0;i<6;i++)
	  {
	    if(j<19)
	    {
	      if(board[i][j]==side)
	       {countWin++;j++;
	        if(countWin>=6){return true; }
	       }
	      else{countWin=0;}
	    }
	   } countWin = 0;
	//左下至右上
	//(18,0)
	    j=0;
	    for(i=18;i>=0;i--)
	    {if(j<19)
	      {if(board[i][j]==side)
	        {countWin++;j++;
	         if(countWin>=6){return true;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;

	//(17,0)
	    j=0;
	    for(i=17;i>=0;i--)
	    {if(j<18)
	      {if(board[i][j]==side)
	        {countWin++;j++;
	         if(countWin>=6){return true;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(16,0)
	    j=0;
	    for(i=16;i>=0;i--)
	    {if(j<17)
	      {if(board[i][j]==side)
	        {countWin++;j++;
	         if(countWin>=6){return true;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(15,0)
	    j=0;
	    for(i=15;i>=0;i--)
	    {if(j<16)
	      {if(board[i][j]==side)
	        {countWin++;j++;
	         if(countWin>=6){return true;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(14,0)
	    j=0;
	    for(i=14;i>=0;i--)
	    {if(j<15)
	      {if(board[i][j]==side)
	        {countWin++;j++;
	         if(countWin>=6){return true;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(13,0)
	    j=0;
	    for(i=13;i>=0;i--)
	    {if(j<14)
	      {if(board[i][j]==side)
	        {countWin++;j++;
	         if(countWin>=6){return true;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(12,0)
	    j=0;
	    for(i=12;i>=0;i--)
	    {if(j<13)
	      {if(board[i][j]==side)
	        {countWin++;j++;
	         if(countWin>=6){return true;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(11,0)
	    j=0;
	    for(i=11;i>=0;i--)
	    {if(j<12)
	      {if(board[i][j]==side)
	        {countWin++;j++;
	         if(countWin>=6){return true;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(10,0)
	    j=0;
	    for(i=10;i>=0;i--)
	    {if(j<11)
	      {if(board[i][j]==side)
	        {countWin++;j++;
	         if(countWin>=6){return true;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(9,0)
	    j=0;
	    for(i=9;i>=0;i--)
	    {if(j<10)
	      {if(board[i][j]==side)
	        {countWin++;j++;
	         if(countWin>=6){return true;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(8,0)
	    j=0;
	    for(i=8;i>=0;i--)
	    {if(j<9)
	      {if(board[i][j]==side)
	        {countWin++;j++;
	         if(countWin>=6){return true;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(7,0)
	    j=0;
	    for(i=7;i>=0;i--)
	    {if(j<8)
	      {if(board[i][j]==side)
	        {countWin++;j++;
	         if(countWin>=6){return true;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(6,0)
	    j=0;
	    for(i=6;i>=0;i--)
	    {if(j<7)
	      {if(board[i][j]==side)
	        {countWin++;j++;
	         if(countWin>=6){return true;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(5,0)
	    j=0;
	    for(i=5;i>=0;i--)
	    {if(j<6)
	      {if(board[i][j]==side)
	        {countWin++;j++;
	         if(countWin>=6){return true;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(18,1)
	    j=1;
	    for(i=18;i>=1;i--)
	    {if(j<19)
	      {if(board[i][j]==side)
	        {countWin++;j++;
	         if(countWin>=6){return true;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(18,2)
	    j=2;
	    for(i=18;i>=2;i--)
	    {if(j<19)
	      {if(board[i][j]==side)
	        {countWin++;j++;
	         if(countWin>=6){return true;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(18,3)
	    j=3;
	    for(i=18;i>=3;i--)
	    {if(j<19)
	      {if(board[i][j]==side)
	        {countWin++;j++;
	         if(countWin>=6){return true;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(18,4)
	    j=4;
	    for(i=18;i>=4;i--)
	    {if(j<19)
	      {if(board[i][j]==side)
	        {countWin++;j++;
	         if(countWin>=6){return true;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(18,5)
	    j=5;
	    for(i=18;i>=5;i--)
	    {if(j<19)
	      {if(board[i][j]==side)
	        {countWin++;j++;
	         if(countWin>=6){return true;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(18,6)
	    j=6;
	    for(i=18;i>=6;i--)
	    {if(j<19)
	      {if(board[i][j]==side)
	        {countWin++;j++;
	         if(countWin>=6){return true;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(18,7)
	    j=7;
	    for(i=18;i>=7;i--)
	    {if(j<19)
	      {if(board[i][j]==side)
	        {countWin++;j++;
	         if(countWin>=6){return true;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(18,8)
	    j=8;
	    for(i=18;i>=8;i--)
	    {if(j<19)
	      {if(board[i][j]==side)
	        {countWin++;j++;
	         if(countWin>=6){return true;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(18,9)
	    j=9;
	    for(i=18;i>=9;i--)
	    {if(j<19)
	      {if(board[i][j]==side)
	        {countWin++;j++;
	         if(countWin>=6){return true;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(18,10)
	    j=10;
	    for(i=18;i>=10;i--)
	    {if(j<19)
	      {if(board[i][j]==side)
	        {countWin++;j++;
	         if(countWin>=6){return true;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(18,11)
	    j=11;
	    for(i=18;i>=11;i--)
	    {if(j<19)
	      {if(board[i][j]==side)
	        {countWin++;j++;
	         if(countWin>=6){return true;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(18,12)
	    j=12;
	    for(i=18;i>=12;i--)
	    {if(j<19)
	      {if(board[i][j]==side)
	        {countWin++;j++;
	         if(countWin>=6){return true;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	//(18,13)
	    j=13;
	    for(i=18;i>=13;i--)
	    {if(j<19)
	      {if(board[i][j]==side)
	        {countWin++;j++;
	         if(countWin>=6){return true;}
	        }
	       else{countWin=0;}
	      }
	     }countWin=0;
	     
	    return false;
	}
	
}

