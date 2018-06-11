package com.wpixel.panel;

import java.awt.Graphics;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import com.wpixel.controller.ClientRevAndSend;
import com.wpixel.ui.UIProper;

public class WindowPanel extends JPanel {

	private static final long serialVersionUID = -6325576426375916444L;
	
	private byte[] bytes = null;
	
	public WindowPanel() {
		new MyThread().start();
	}

	@Override
	public void paint(Graphics g){
		try {
			if(bytes != null){
				InputStream input = new ByteArrayInputStream(bytes);
				//new File(System.getProperty("user.dir")+"/src/main/resources/images/BG.jpeg")
				Image image = ImageIO.read(input);
				g.drawImage(image, 0, 0, UIProper.winWidth, UIProper.winHeight, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
    }
	
	class MyThread extends Thread{
		public void run() {
			while(true){
				try {
					Thread.sleep(100);
					if(ClientRevAndSend.image != null){
						bytes = ClientRevAndSend.image.getBytes();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				repaint();
			}
		}
	}
}
