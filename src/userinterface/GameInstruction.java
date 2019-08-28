package userinterface; //LAI

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class GameInstruction extends JFrame {

	String content = "";
	String str;

	public GameInstruction() {

		setTitle("Game Instruction");
		setSize(600, 500); // 視窗大小
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false); // 使用者不可任意調整視窗大小

		String filePath = "data/textEN.txt";
		readALLBytesJava7(filePath); // 讀取txt檔

		JTextArea lab = new JTextArea();
		lab.setEditable(false); // 不可更動視窗內容
		lab.setText(content);
		lab.setBackground(new Color(240, 245, 255));  // 視窗背景顏色
		Font f = new Font("Serif", Font.BOLD, 18);
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
