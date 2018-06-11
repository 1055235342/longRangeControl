package com.wpixel.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.wpixel.main.Client;
import com.wpixel.panel.WindowPanel;

public class ClientUI {

	private JFrame frame;

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
		});
		//添加面板
		WindowPanel panel = new WindowPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		/**设置显示*/
		frame.setVisible(true);
	}

}
