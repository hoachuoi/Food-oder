package gui;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Manage extends JFrame implements ActionListener {
	private JFrame frame;
	private JTable table;
	private static String Url = "jdbc:mysql://localhost:3306/fastfood";
	private static String User = "root";
	private static String Password = "";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Manage window = new Manage();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void getdatauser(JTable table) {

		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("ID");
		model.addColumn("Name");
		model.addColumn("Address");
		model.addColumn("Phone");
		model.addColumn("CMND");
		model.addColumn("User name");
		model.addColumn("Password");
		model.addColumn("Authority");

		try {
			// Kết nối CSDL
			Class.forName("com.mysql.jdbc.Driver");
			java.sql.Connection con = DriverManager.getConnection(Url, User, Password);
			Statement stmt = con.createStatement();
			String sql = "SELECT * FROM user";
			ResultSet rs = stmt.executeQuery(sql);

			// Thêm dữ liệu từ ResultSet vào mô hình dữ liệu
			while (rs.next()) {
				int id = rs.getInt("Id");
				String name = rs.getString("Name");
				String Address = rs.getString("Address");
				String Phone = rs.getString("Phone");
				String CMND = rs.getString("CMND");
				String UserName = rs.getString("UserName");
				String Pass = rs.getString("PassWord");
				String quyen = rs.getString("Quyen");

				model.addRow(new Object[] { id, name, Address, Phone, CMND, UserName, Pass, quyen });
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
		table.getColumnModel().getColumn(0).setPreferredWidth(24);
		table.getColumnModel().getColumn(1).setPreferredWidth(135);
		table.getColumnModel().getColumn(2).setPreferredWidth(84);
		table.getColumnModel().getColumn(3).setPreferredWidth(80);
		table.getColumnModel().getColumn(4).setPreferredWidth(82);
		table.getColumnModel().getColumn(5).setPreferredWidth(87);
		table.getColumnModel().getColumn(7).setPreferredWidth(77);
	}

	public void deletedata(JTable table) {
		int selectedRow = table.getSelectedRow();
		String iddele = "";
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		if (selectedRow != -1) {
			iddele = table.getValueAt(selectedRow, 0).toString();
			model.removeRow(selectedRow);
			System.out.println(iddele);
			try {
				// Kết nối CSDL
				Class.forName("com.mysql.jdbc.Driver");
				java.sql.Connection con = DriverManager.getConnection(Url, User, Password);
				Statement stmt = con.createStatement();
				String sql = "DELETE FROM user WHERE ID = " + iddele;
				int rs = stmt.executeUpdate(sql);
				// Đóng ResultSet, Statement và Connection
				// rs.close();
				stmt.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(frame, "No row selected.");
		}

	}

	/**
	 * Create the application.
	 */
	public Manage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 717, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 691, 261);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("MANAGE");
		lblNewLabel.setFont(new Font("Consolas", Font.BOLD, 16));
		lblNewLabel.setBounds(115, 26, 188, 29);
		panel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("LIST:");
		lblNewLabel_1.setBounds(10, 52, 116, 23);
		panel.add(lblNewLabel_1);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(48, 86, 608, 119);
		panel.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(new Object[][] { { null, null, null, null, null, null, null, null }, },
				new String[] { "ID", "Name", "Addess", "Phone", "CMND", "User name", "Passwword", "Authority" }) {
			Class[] columnTypes = new Class[] { String.class, Object.class, Object.class, Object.class, Object.class,
					Object.class, Object.class, String.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(24);
		table.getColumnModel().getColumn(1).setPreferredWidth(135);
		table.getColumnModel().getColumn(2).setPreferredWidth(84);
		table.getColumnModel().getColumn(3).setPreferredWidth(80);
		table.getColumnModel().getColumn(4).setPreferredWidth(82);
		table.getColumnModel().getColumn(5).setPreferredWidth(87);
		table.getColumnModel().getColumn(7).setPreferredWidth(77);

		JButton view = new JButton("View List");
		view.setForeground(Color.BLUE);
		view.setBounds(190, 227, 89, 23);
		panel.add(view);
		view.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				getdatauser(table);
			}
		});

		JButton add = new JButton("Add");
		add.setForeground(Color.BLUE);
		add.setBounds(297, 227, 89, 23);
		panel.add(add);
		add.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				signup sn = new signup();
				sn.GUI();
			}
		});

		JButton dele = new JButton("Delete");
		dele.setForeground(Color.RED);
		dele.setBounds(425, 227, 89, 23);
		panel.add(dele);
		dele.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				deletedata(table);
			}
		});

		JButton back = new JButton("Log out");
		back.setForeground(Color.DARK_GRAY);
		back.setBounds(578, 227, 89, 23);
		panel.add(back);
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					MainMenu menu = new MainMenu();
					menu.main(null);
					// menu.createAndShowGUI();
					menu.setVisible(true);
					// setVisible(false);
					frame.dispose();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		JButton btnNewButton = new JButton("View order");
		btnNewButton.setForeground(SystemColor.textHighlight);
		btnNewButton.setBounds(51, 227, 121, 23);
		panel.add(btnNewButton);
		FoodMenu manaFoodMenu = new FoodMenu();
		btnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				OrderMenu order = new OrderMenu();
				order.main(null);
				order.setVisible(true);
				setVisible(false);
				frame.dispose();

			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}
