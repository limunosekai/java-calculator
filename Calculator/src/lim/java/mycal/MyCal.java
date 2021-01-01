package lim.java.mycal;

import java.awt.*;
import java.awt.event.*;

public class MyCal extends Frame implements ActionListener {
	// 멤버필드
	private Label disp = new Label("0.", Label.RIGHT);
	private Button[] btn_num = new Button[12];
	private String[] str_num = { "7", "8", "9", "4", "5", "6", "1", "2", "3", "0", "+/-", "." };
	private Button[] btn_func = new Button[3];
	private String[] str_func = { "←", "CE", "C" };
	private Button[] btn_oper = new Button[4];
	private String[] str_oper = { "÷", "×", "-", "+" };
	private Button btn_equ = new Button("=");
	private double result = 0;
	private boolean dotCheck = true;
	private boolean decimal = false;
	private char operator = '+';

	// 생성자
	public MyCal() {
		super("계산기");
		buildGUI();
		setEvent();
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		setSize(300, 400);
		setResizable(false);
		setVisible(true);
	}

	// 메소드
	public void buildGUI() {// 레이아웃
		setLayout(new BorderLayout(5, 5));
		setBackground(Color.orange);
		Panel main_disp = new Panel(new BorderLayout(5, 5));
		main_disp.add("North", new Label());
		main_disp.add("West", new Label());
		main_disp.add("East", new Label());
		main_disp.add("South", new Label());
		disp.setBackground(Color.white);
		disp.setFont(new Font("SansSerif", Font.BOLD, 20));
		main_disp.add("Center", disp);
		add("North", main_disp);
		Panel main = new Panel(new GridLayout(5, 4, 5, 5));
		for (int i = 0; i < str_func.length; i++) {
			btn_func[i] = new Button(str_func[i]);
			btn_func[i].setFont(new Font("SansSerif", Font.BOLD, 20));
			main.add(btn_func[i]);
		}
		btn_oper[0] = new Button(str_oper[0]);
		btn_oper[0].setFont(new Font("SansSerif", Font.BOLD, 20));
		main.add(btn_oper[0]);
		for (int i = 0; i < str_num.length; i++) {
			btn_num[i] = new Button(str_num[i]);
			btn_num[i].setFont(new Font("SansSerif", Font.BOLD, 20));
			main.add(btn_num[i]);
			switch (i) {
			case 2:
				btn_oper[1] = new Button(str_oper[1]);
				btn_oper[1].setFont(new Font("SansSerif", Font.BOLD, 20));
				main.add(btn_oper[1]);
				break;
			case 5:
				btn_oper[2] = new Button(str_oper[2]);
				btn_oper[2].setFont(new Font("SansSerif", Font.BOLD, 20));
				main.add(btn_oper[2]);
				break;
			case 8:
				btn_oper[3] = new Button(str_oper[3]);
				btn_oper[3].setFont(new Font("SansSerif", Font.BOLD, 20));
				main.add(btn_oper[3]);
				break;
			}
			btn_equ.setFont(new Font("SansSerif", Font.BOLD, 20));
			main.add(btn_equ);
		}
		add("West", new Label());
		add("East", new Label());
		add("South", new Label());
		add("Center", main);
	}

	public void setEvent() {// 이벤트 걸기
		for (int i = 0; i < btn_num.length; i++) {
			btn_num[i].addActionListener(this);
		}
		for (int i = 0; i < btn_oper.length; i++) {
			btn_oper[i].addActionListener(this);
		}
		btn_equ.addActionListener(this);
		for (int i = 0; i < btn_func.length; i++) {
			btn_func[i].addActionListener(this);
		}
	}

	public void calc() {// 계산 기능
		double value = Double.parseDouble(disp.getText());
		switch (operator) {
		case '+':
			result += value;
			break;
		case '-':
			result -= value;
			break;
		case '×':
			result *= value;
			break;
		case '÷':
			result /= value;
			break;
		}
		double temp = result - (int) result;

		if (temp > 0) {
			disp.setText(String.valueOf(result));
		} else {
			disp.setText(String.valueOf((int) result) + ".");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for (int i = 0; i < btn_num.length - 2; i++) {
			if (e.getSource() == btn_num[i]) {
				if (dotCheck) {					// 숫자를 처음 눌렀을 때
					disp.setText(btn_num[i].getLabel() + ".");
					dotCheck = false;
				} else {						// 숫자를 누른게 처음이 아닐 때
					if (disp.getText().equals("0.") && btn_num[i].getLabel().equals("0"))
						return; 				// 0 연타 막기
					if (!decimal) {				// 소수점 버튼 안눌렀을 때
						String temp = disp.getText();
						temp = temp.substring(0, temp.length() - 1);
						disp.setText(temp + btn_num[i].getLabel() + ".");
					} else {					// 소수점 버튼 눌렀을 떄
						String temp = disp.getText();
						disp.setText(temp + btn_num[i].getLabel());
					}
				}
				return;
			}
		} // end for

		if (e.getSource() == btn_num[btn_num.length - 1]) {// 소수점 클릭시
			dotCheck = false;
			decimal = true;
			return;
		}

		if (e.getSource() == btn_num[btn_num.length - 2]) {// +/- 클릭시
			String temp = disp.getText();
			if (temp.equals("0.")) {
				return;
			} else {
				if (temp.charAt(0) != '-') {
					disp.setText("-" + temp);
				} else {
					disp.setText(temp.substring(1));
				}
			}
			return;
		} // end if

		for (int i = 0; i < btn_oper.length; i++) {// 연산자 클릭시
			if (e.getSource() == btn_oper[i]) {
				calc();
				operator = btn_oper[i].getLabel().charAt(0);
				dotCheck = true;
				decimal = false;
				return;
			}
		} // end for

		if (e.getSource() == btn_equ) {// 등호 클릭시
			calc();
			operator = '+';
			result = 0;
			return;
		}

		if (e.getSource() == btn_func[1]) {// CE 클릭시
			disp.setText("0.");
			dotCheck = true;
			decimal = false;
			return;
		}

		if (e.getSource() == btn_func[2]) {// C 클릭시
			disp.setText("0.");
			operator = '+';
			result = 0;
			dotCheck = true;
			decimal = false;
			return;
		}

		if (e.getSource() == btn_func[0]) {// backspace 클릭시
			String temp = disp.getText();
			
			if (temp.length() == 2) {// 정수 한 자리
				disp.setText("0.");
				dotCheck = true;
				decimal = false;
				operator = '+';
				return;
			}
			
			if (temp.length() == 3 && temp.charAt(0) == '-') {// 음수 한 자리
				disp.setText("0.");
				dotCheck = true;
				decimal = false;
				operator = '+';
				return;
			}
			
			if (temp.length() == 3 && temp.charAt(0) == '0') {// 소수 한 자리
				disp.setText("0.");
				dotCheck = true;
				decimal = false;
				operator = '+';
				return;
			}
			
			if (temp.charAt(temp.length() - 1) == '.') {	// 정수
				disp.setText(temp.substring(0, temp.length() - 2)+".");
			} else {										// 소수
				disp.setText(temp.substring(0, temp.length() - 1));
			}
			return;
		}
	}

	public static void main(String[] args) {
		new MyCal();

	}

}
