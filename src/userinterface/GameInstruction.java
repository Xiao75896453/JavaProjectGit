package userinterface; 

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Formatter;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class GameInstruction extends JFrame {

	String content = "";
	String str;

	public GameInstruction() {

		setTitle("Game Instruction");
		setSize(600, 500); //視窗大小

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);  //使用者不可變更視窗大小

		String filePath = "data/textEN.txt";  
		System.out.println(readALLBytesJava7(filePath));  //讀取txt檔
		
		JTextArea lab = new JTextArea();
		lab.setText(content);
		lab.setBackground(new Color(240, 245, 255)); //設定遊戲說明視窗背景顏色
		Font f = new Font("Serif",Font.BOLD,18);  //字型、字體
		lab.setFont(f);
		add(lab);
	}

	private String readALLBytesJava7(String filePath) {  
		try {
			content = new String(Files.readAllBytes(Paths.get(filePath)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}

}
