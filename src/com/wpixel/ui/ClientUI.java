package com.wpixel.ui;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.wpixel.main.Client;
import com.wpixel.panel.WindowPanel;

public class ClientUI {

	private JFrame frame;
	private SystemTray systemTray; 
	private TrayIcon trayIcon;

	/**
	 * 应用程序主函数
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientUI window = new ClientUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		//链接服务端
		Client.start();
	}

	public ClientUI() {
		initialize();
	}

	/**
	 * 初始化应用界面
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBackground(Color.BLACK);
		frame.setTitle("远程桌面");
		frame.setUndecorated(true);//去掉边框
		frame.setIconImage(new ImageIcon("src/com/wpixel/images/icon.jpg").getImage());
		/**设置大小*/
		frame.setSize(UIProper.winWidth, UIProper.winHeight);
		/**设置大小不可变*/
		frame.setResizable(true);
		/**设置默认关闭进程*/
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		/**设置默认居中*/
		frame.setLocationRelativeTo(null);
		//关闭窗口提示的事件
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int num = JOptionPane.showConfirmDialog(null, "退出？", "提示", JOptionPane.OK_CANCEL_OPTION);
				if(num == JOptionPane.OK_OPTION){
					System.exit(0);
				}
			}
			@Override
			public void windowIconified(WindowEvent e) {
				// 判断系统是否支持系统托盘  
				if (SystemTray.isSupported()) {
					 if (systemTray==null) {  
                         systemTray=SystemTray.getSystemTray();//创建系统托盘  
                         if (trayIcon!=null) {  
                             systemTray.remove(trayIcon);  
                         }  
                     }
				}
				//创建弹出式菜单  
                PopupMenu popup = new PopupMenu();  
                //主界面选项  
                MenuItem mainMenuItem = new MenuItem("show");  
                mainMenuItem.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						frame.setExtendedState(JFrame.NORMAL);
					}  
                });  
                //退出程序选项  
                MenuItem exitMenuItem = new MenuItem("exit");  
                exitMenuItem.addActionListener(new ActionListener(){  
                    public void actionPerformed(ActionEvent e) {  
                        System.exit(0);  
                    }  
                }); 
                popup.add(mainMenuItem);
                popup.addSeparator();
                popup.add(exitMenuItem);
                trayIcon = new TrayIcon(new ImageIcon("src/com/wpixel/images/icon.jpg").getImage(), "longRangeControl", popup);
                trayIcon.setImageAutoSize(true);  
                trayIcon.addActionListener(new ActionListener() {  
                    public void actionPerformed(ActionEvent e) { 
                    	frame.setExtendedState(JFrame.NORMAL);
                    }  
                });  
                try {  
                    systemTray.add(trayIcon);  
                } catch (AWTException e1) {  
                    e1.printStackTrace();  
                }  
			}
		});
		//添加面板
		WindowPanel panel = new WindowPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		/**设置显示*/
		frame.setVisible(true);
	}

}
