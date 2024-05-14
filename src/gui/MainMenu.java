package gui;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class MainMenu extends JPanel {
	JLabel picLabel, title;
	//JButton bttLogin;
	static JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    JButton loginButton = new JButton("Đăng nhập");
    JButton registerButton = new JButton("Đăng ký");
	private static String Url = "jdbc:mysql://localhost:3306/fastfood";
	private static String User = "root";
	private static String Password ="";
	public void createAndShowGUI() throws IOException {
        // Tạo các thành phần giao diện
        JLabel usernameLabel = new JLabel("Tài khoản:");
        JLabel passwordLabel = new JLabel("Mật khẩu:");

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);        
        
        // Thiết lập layout sử dụng GridBagLayout
        setLayout(new GridBagLayout());
        // Thiết lập constraints cho các thành phần
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Đặt khoảng cách giữa các thành phần
        // Thêm các thành phần vào cửa sổ
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(usernameLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(usernameField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(passwordLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(passwordField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2; // Cho phép button chiếm 2 cột
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1; // Đặt lại gridwidth về 1 để nút "Đăng nhập" chỉ chiếm 1 cột
        add(loginButton, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1; // Đặt lại gridwidth về 1 để nút "Đăng ký" chỉ chiếm 1 cột
        add(registerButton, gbc);

        // Xử lý sự kiện đăng nhập
        loginButton.addActionListener(new ActionListener() {
            String user,pass;
            boolean check =false;
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                char[] password = passwordField.getPassword();
                String passwordText = new String(password);
                String Quyen;
                // Thực hiện xác thực tài khoản ở đây 
                try {
                	Class.forName("com.mysql.jdbc.Driver");
        			Connection conn = DriverManager.getConnection(Url,User,Password);
        			Statement stmt = conn.createStatement();
                    String sql1 = "SELECT * FROM user";
                    ResultSet rs = stmt.executeQuery(sql1);
        			while(rs.next()) {
                        user = rs.getString("UserName");
                        pass = rs.getString("PassWord");
                        Quyen = rs.getString("Quyen");
                        if (user.equals(username) && pass.equals(passwordText)) {
                        	if(Quyen.equals("Nhân viên")) {
                        		FoodMenu food;
                         		try {
                         			food = new FoodMenu();
                         			food.createAndShowGUI();
                         			food.setVisible(true);
                         			setVisible(false);
                         			frame.dispose();
                         			} catch (IOException e1) {
                         				e1.printStackTrace();
                         			}
                         		check = true;
                        	}
                        	if(Quyen.equals("Quản lý")) {
                        		Manage mn;
                         		mn = new Manage();
								mn.main(null);
								mn.setVisible(true);
								setVisible(false);
								frame.dispose();
                         		check = true;
                        	}
                     		
                        }
                        
        			}
        			 
                    if(check == false){
                         JOptionPane.showMessageDialog(MainMenu.this, "Bạn chưa có tài khoản.Vui lòng đăng ký!");
                         usernameField.setText("");
                         passwordField.setText("");
                    }
                    rs.close();
                    stmt.close();
                    conn.close(); // Close the connection
                } catch (ClassNotFoundException | SQLException exception) {
                    exception.printStackTrace();
                }              
            }
        });
        // Xử lý sự kiện đăng ký
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
				signup sn = new signup();
				sn.GUI();
            }
        });
    }


	public static void main(String args[]) throws IOException {
		MainMenu main = new MainMenu();
		main.createAndShowGUI();
		frame = new JFrame();
		frame.setTitle("Fast food restaurant login");
		frame.getContentPane().add(main);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
