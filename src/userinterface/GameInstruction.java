package userinterface; //LAI �C����������

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
		setSize(600, 500); // �C�����������j�p
		setBackground(Color.blue);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false); // �ϥΪ̬O�_�i�յ����j�p

		String filePath = "data/textEN.txt";
		System.out.println(readALLBytesJava7(filePath));
		System.out.println(content.length());

		//String[] tokens = content.split(";");
		
		JTextArea lab = new JTextArea();
		lab.setText(content);
		Font f = new Font("Serif",Font.BOLD,18);
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
