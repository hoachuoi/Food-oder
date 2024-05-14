package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;

public class OrderMenu {
	private JFrame frame;
	private JTable table;
	private JTextField textField_1;
	JComboBox<String> comboBox;
	private static String Url = "jdbc:mysql://localhost:3306/fastfood";
	private static String User = "root";
	private static String Password = "";

	/**
	 * Launch the application.
	 */
	// Lấy dữ liệu bỏ vào bảng

	public void getdata(JTable table) {

		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Name");
		model.addColumn("Quantity");
		model.addColumn("Price");

		try {
			// Kết nối CSDL
			Class.forName("com.mysql.jdbc.Driver");
			java.sql.Connection con = DriverManager.getConnection(Url, User, Password);
			Statement stmt = con.createStatement();
			String selectedValue = (String) comboBox.getSelectedItem();

			System.out.println("Selected value: " + selectedValue);
			String sql = "SELECT * FROM bill WHERE numtable =" + selectedValue;
			ResultSet rs = stmt.executeQuery(sql);

			// Thêm dữ liệu từ ResultSet vào mô hình dữ liệu
			while (rs.next()) {
				String name = rs.getString("name");
				double quantity = rs.getDouble("quantity");
				double price = rs.getDouble("total_price");
				model.addRow(new Object[] { name, quantity, price });
			}

			// Đóng ResultSet, Statement và Connection
			rs.close();
			stmt.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Đặt mô hình dữ liệu cho bảng
		table.setModel(model);

	}

	public double GetTotalbill() throws SQLException {
		double total_bill = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(Url, User, Password);
			String selectedValue = (String) comboBox.getSelectedItem();
			Statement stmt = conn.createStatement();
			String sql = "SELECT total_bill from bill where numtable=" + selectedValue;
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				total_bill = rs.getDouble("total_bill");
			}
			rs.close();
			stmt.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return total_bill;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OrderMenu window = new OrderMenu();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// lay so bàn
	private static void populateTableNumbers(JComboBox<String> comboBox) {
		// Thực hiện kết nối CSDL và truy vấn
		try {
			Connection con = DriverManager.getConnection(Url, User, Password);
			Statement stmt = con.createStatement();
			String sql = "SELECT DISTINCT numtable FROM bill";
			ResultSet rs = stmt.executeQuery(sql);

			// Tạo mô hình dữ liệu cho JComboBox
			DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();

			// Thêm số bàn vào mô hình dữ liệu
			while (rs.next()) {
				String tableNumber = rs.getString("numtable");
				comboBoxModel.addElement(tableNumber);
			}

			// Đóng ResultSet, Statement và Connection
			rs.close();
			stmt.close();
			con.close();

			// Đặt mô hình dữ liệu cho JComboBox
			comboBox.setModel(comboBoxModel);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public OrderMenu() {
		// TODO Auto-generated constructor stub
		initialize();
	};

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.activeCaption);
		panel.setBounds(0, 0, 434, 261);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(91, 79, 258, 134);
		panel.add(scrollPane);

		table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Name", "quantity", "Total price" }));
		scrollPane.setViewportView(table);

		JLabel lblNewLabel = new JLabel("Table");
		lblNewLabel.setBounds(114, 29, 46, 14);
		panel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Infor bill");
		lblNewLabel_1.setBounds(66, 54, 116, 14);
		panel.add(lblNewLabel_1);

		JButton btnNewButton = new JButton("View detail");
		btnNewButton.setBackground(Color.LIGHT_GRAY);
		btnNewButton.setBounds(272, 25, 100, 23);
		panel.add(btnNewButton);

		btnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				getdata(table);
				double totalBill;
				String stringValue = null;
				try {
					totalBill = GetTotalbill();
					stringValue = Double.toString(totalBill);
					textField_1.setText(stringValue);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		textField_1 = new JTextField();

		textField_1.setBounds(170, 230, 86, 20);
		panel.add(textField_1);
		textField_1.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Total bill");
		lblNewLabel_2.setBounds(114, 233, 46, 14);
		panel.add(lblNewLabel_2);

		comboBox = new JComboBox<>();
		populateTableNumbers(comboBox);
		comboBox.setBounds(183, 25, 55, 22);
		panel.add(comboBox);

		JButton btnNewButton_1 = new JButton("Back");
		btnNewButton_1.setBounds(317, 229, 89, 23);
		panel.add(btnNewButton_1);

		btnNewButton_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				FoodMenu food;
				try {
					food = new FoodMenu();
					food.createAndShowGUI();
					food.setVisible(true);
					setVisible(false);
					frame.dispose();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}

	public void setVisible(boolean b) {
		// TODO Auto-generated method stub

	}
}
