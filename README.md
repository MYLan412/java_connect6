< Main >:主函数。

< JieMian >:选择界面，即游戏主页。
需连接数据库，填写正确的账号密码，方可进入游戏界面。额外提供了注册功能。

< C6_MousePolice > + < C6_WindowMouse >:六子棋，单机人人六子棋游戏程序。
点击Start按钮，开始计时，点击棋盘即可下棋。右侧(3,1)和(3,3)区域为游戏结束时显示的结果；(3,2)区域为点击棋盘下棋的坐标。上方(3,1)位置的“保存按钮”可将本次棋局进行保存，保存方式可选“文件”或“数据库”，根据提示信息，录入比赛信息即可完成保存。上方(3,2)位置的“主页按钮”可返回游戏主页。上方(3,3)位置的“倒退按钮”，每点击一次可实现一步悔棋操作。

< Server_MousePolice > + < Server_WindowMouse >:联机六子棋（黑方），套接字的服务端。
< Client_MousePolice > + < Client_WindowMouse >:联机六子棋（白方），套接字的客户端。
依次分别启动两程序，通过Java套接字的网络通信，即可实现双人游戏。右侧(3,2)区域提供线上聊天功能。

< MousePolice > + < WindowMouse >:六子棋打谱。
可打开本地的棋谱并显示在当前棋盘上。选择棋谱后，点击“执行按钮”，即可显示棋局。上方按钮功能依次为：打开本地文件、后退到初始棋盘、后退一步、前进一步、前进到最终棋盘、返回游戏主页。

< AI_MousePolice > + < AI_WindowMouse >:人机下棋。
先手黑子先落棋，白子为机器自动落棋。其中，机器下棋算法还未完成。
