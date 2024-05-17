package mimi;
//import java.awt.Color;

//import javax.swing.JFrame;

public class Main
{
	public static void main(String args[])
	{
		//JFrame.setDefaultLookAndFeelDecorated( true );
		
		JieMian j=new JieMian();
		j.setBounds(200,200,450,350);
		j.setTitle("欢迎来到六子棋游戏！");
		/*
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
    	*/
	}
}