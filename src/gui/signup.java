package gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class signup extends JFrame implements ActionListener{
    private JLabel lbName, lbAddress, lbPhone, lbCMND, lbUser, lbPass, lbQuyen;
    private JTextField txtName, txtAddress, txtPhone, txtCMND, txtUser, txtPass;
    private Button signUp;
    private JComboBox cbQuyen;
	private static String Url = "jdbc:mysql://localhost:3306/fastfood";
	private static String User = "root";
	private static String Password ="";

    public void GUI() {
        lbName = new JLabel("Họ và Tên");
        lbAddress = new JLabel("Địa chỉ: ");
        lbPhone = new JLabel("Số điện thoại: ");
        lbCMND = new JLabel("CMND/CCCD: ");
        lbUser = new JLabel("UserName: ");
        lbPass = new JLabel("PassWord: ");
        lbQuyen = new JLabel("Vai trò: ");

        txtName = new JTextField();
        txtAddress = new JTextField();
        txtPhone = new JTextField();
        txtCMND = new JTextField();
        txtUser = new JTextField();
        txtPass = new JTextField();
        
        cbQuyen = new JComboBox();
        cbQuyen.addItem("Quản lý");
        cbQuyen.addItem("Nhân viên");

        signUp = new Button("Đăng ký");
        signUp.addActionListener(this);

        JPanel pn = new JPanel(new GridLayout(7, 2, 10, 10)); // Sử dụng GridLayout với khoảng cách giữa các cell
        pn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Thêm khoảng cách ngoại vi

        pn.add(lbName);
        pn.add(txtName);
        pn.add(lbAddress);
        pn.add(txtAddress);
        pn.add(lbPhone);
        pn.add(txtPhone);
        pn.add(lbCMND);
        pn.add(txtCMND);
        pn.add(lbUser);
        pn.add(txtUser);
        pn.add(lbPass);
        pn.add(txtPass);
        pn.add(lbQuyen);
        pn.add(cbQuyen);
        

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(signUp);

        setLayout(new BorderLayout());
        add(pn, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setSize(400, 300);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String Name, Address, Phone, CMND, user, Pass, Quyen;
        Name = txtName.getText().toString();
        Address = txtAddress.getText().toString();
        Phone = txtPhone.getText().toString();
        CMND = txtCMND.getText().toString();
        user = txtUser.getText().toString();
        Pass = txtPass.getText().toString();
        Quyen = cbQuyen.getSelectedItem().toString();
        if (e.getSource() == signUp) {
            try {
            	Class.forName("com.mysql.jdbc.Driver");
    			Connection conn = DriverManager.getConnection(Url,User,Password);
    			Statement stmt = conn.createStatement();
                String sql = "INSERT INTO user (Name, Address, Phone, CMND, UserName, PassWord, Quyen) VALUES ('" +
                        Name + "','" + Address + "','" + Phone + "','" + CMND + "','" + user + "','" + Pass + "','" + Quyen +"')";
                stmt.executeUpdate(sql);
                stmt.close();
                conn.close(); // Close the connection
            } catch (ClassNotFoundException | SQLException exception) {
                exception.printStackTrace();
            }
        }
		setVisible(false);
		
    }
}

