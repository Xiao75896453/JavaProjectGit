package userinterface; //LAI 遊戲說明視窗

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
		setSize(600, 500); // 遊戲說明視窗大小
		setBackground(Color.blue);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false); // 使用者是否可調視窗大小

		String filePath = "data/textEN.txt";
		System.out.println(readALLBytesJava7(filePath));
		System.out.println(content.length());

		//String[] tokens = content.split(";");
		
		JTextArea lab = new JTextArea();
		lab.setText(content);
		Font f = new Font("Serif",Font.BOLD,18);
		lab.setFont(f);
		add(lab);
//		for (String token : tokens) {
//			System.out.println(token);
//			
//			lab.setSize(150, 50);
//			lab.setLocation(0, 20 + i * 50);
//			i++;
//		}

//		for (int i = 0; i < 220; i += 22) {
//			
//			str = content.substring(i, i + 22);
//			JLabel lab = new JLabel(str + "\n");
//			lab.setSize(150,50);
//			lab.setLocation(0 ,20*(int)(i/21));
//			lab.setBackground(Color.magenta);
//		
//			
//			//lab.setVisible(true);
//			add(lab);
//		}
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
