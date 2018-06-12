package com.wpixel.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import com.wpixel.main.Client;
import com.wpixel.panel.PicPanel;

public class ClientUI {

	public static JFrame frame;
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
		frame.setTitle("远程桌面");
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
					Client.close();
					System.exit(0);
				}
			}
		});
		/**设置显示*/
		frame.setVisible(true);
		//添加面板
		JPanel window = new JPanel();
		frame.getContentPane().add(window, BorderLayout.CENTER);
		window.setLayout(new BorderLayout(0, 0));
		
		{
			JPanel top = new JPanel();
			top.setBounds(0, 0, 700, 100);
			top.setBackground(new Color(135, 206, 250));
			window.add(top, BorderLayout.NORTH);
			top.setLayout(new FlowLayout(0, 0, 0));
			JButton btn1 = new JButton("连接");
			btn1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
				}
			});
			btn1.setBorder(null);
			btn1.setIcon(new ImageIcon("src/com/wpixel/images/0.png"));
			top.add(btn1);
			JButton btn2 = new JButton("关闭");
			btn2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Client.close();
				}
			});
			btn2.setBorder(null);
			btn2.setIcon(new ImageIcon("src/com/wpixel/images/1.png"));
			top.add(btn2);
			JButton btn3 = new JButton("");
//			btn3.addActionListener(new TopMenuListener());
			btn3.setBorder(null);
			btn3.setIcon(new ImageIcon("src/com/wpixel/images/2.png"));
			top.add(btn3);
			JButton btn4 = new JButton("");
//			btn4.addActionListener(new TopMenuListener());
			btn4.setBorder(null);
			btn4.setIcon(new ImageIcon("src/com/wpixel/images/3.png"));
			top.add(btn4);
		}
		
		{
			JPanel left = new JPanel();
			left.setBackground(Color.WHITE);
			window.add(left, BorderLayout.WEST);
			left.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			
			JTree tree = new JTree();
			left.add(tree);
			tree.setModel(new DefaultTreeModel(
					new DefaultMutableTreeNode("JTree") {
						{
							DefaultMutableTreeNode node_1;
							node_1 = new DefaultMutableTreeNode("sports");
							node_1.add(new DefaultMutableTreeNode("basketball"));
							node_1.add(new DefaultMutableTreeNode("soccer"));
							node_1.add(new DefaultMutableTreeNode("football"));
							node_1.add(new DefaultMutableTreeNode("hockey"));
							add(node_1);
							node_1 = new DefaultMutableTreeNode("food");
							node_1.add(new DefaultMutableTreeNode("hot dogs"));
							node_1.add(new DefaultMutableTreeNode("pizza"));
							node_1.add(new DefaultMutableTreeNode("ravioli"));
							node_1.add(new DefaultMutableTreeNode("bananas"));
							add(node_1);
						}
					}
					));
			
		}
		
		{
			JPanel butten = new JPanel();
			butten.setBackground(new Color(135, 206, 235));
			window.add(butten, BorderLayout.SOUTH);
			JLabel lblNewLabel = new JLabel("Copyright © 1999-2018, CSDN.NET, All Rights Reserved");
			butten.add(lblNewLabel);
		}
		
		{
			JPanel right = new JPanel();
			right.setBackground(Color.WHITE);
			window.add(right, BorderLayout.EAST);
		}
		
		{
			PicPanel center = new PicPanel();
			center.setBackground(Color.LIGHT_GRAY);
			window.add(center, BorderLayout.CENTER);
		}
	}
}
