
package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

public class FoodMenu {
	static private JFrame frame;
	static private JButton backButton, orderButton, viewButton;
	static private JTextField textField;
	JComboBox<String> comboBox;
	static private GridBagConstraints gbc;
	private JTable table;
	private static String Url = "jdbc:mysql://localhost:3306/fastfood";
	private static String User = "root";
	private static String Password = "";
	String[] arrfood;

	public double GetPrice(String s) throws SQLException {
		double Price = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(Url, User, Password);
			Statement stmt = conn.createStatement();
			String sql = "SELECT Price from food where Name=" + "'" + s + "'";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Price = rs.getDouble("Price");
			}
			rs.close();
			stmt.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return Price;
	}

	public int GetRow() throws SQLException {
		int countRow = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(Url, User, Password);
			Statement stmt = conn.createStatement();
			String sql = "SELECT * from food";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				countRow++;
			}
			rs.close();
			stmt.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return countRow;
	}

	public void GetName() throws SQLException {
		arrfood = new String[GetRow()];
		int i = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(Url, User, Password);
			Statement stmt = conn.createStatement();
			String sql = "SELECT Name from food";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				arrfood[i] = rs.getString("Name");
				i++;
			}
			rs.close();
			stmt.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	// gui du lieu khi nhan nut order
	public void updateDatabase(JTable table) throws ClassNotFoundException {
		try {

			for (int row = 1; row < table.getRowCount(); row++) {

				Class.forName("com.mysql.jdbc.Driver");
				java.sql.Connection con = DriverManager.getConnection(Url, User, Password);
				java.sql.Statement stmt = con.createStatement();

				String productName = dtm.getValueAt(row, 0).toString();
				int quantity = Integer.parseInt(dtm.getValueAt(row, 1).toString());
				double totalPrice = Double.parseDouble(dtm.getValueAt(row, 2).toString());
				String numtable = (String) comboBox.getSelectedItem();

				System.out.println(numtable);

				String sql = "INSERT INTO bill (Idbill, name, quantity, total_price, numtable, total_bill)"
						+ " VALUES (" + "NULL" + "," + "'" + productName + "'" + "," + quantity + "," + totalPrice + ","
						+ numtable + "," + totalForFoods + ")";

				System.out.println("thanh cong");
				int rs = stmt.executeUpdate(sql);
				// hien thi de test
				System.out.println(sql);
				System.out.println(rs);

				stmt.close();
			}

			System.out.println("Dữ liệu đã được cập nhật vào CSDL thành công!");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private static void TableNumbers(JComboBox<String> comboBox) {
		// Thực hiện kết nối CSDL và truy vấn
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(Url, User, Password);
			Statement stmt = con.createStatement();
			String sql = "SELECT * FROM `table`";
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

	// FoodMenu getprice = new FoodMenu();
	DefaultTableModel dtm;
	Double[] price;

	double totalPrice = 0;
	double p1, p2, p3, p4, p5, p6, p7, p8, p9;

	private JSpinner[] numSpinner;
	static private JLabel[] foodLabel;
	static private JLabel[] foodImage;
	private String[] file;

	private static final int ELEMENTS = 9;

	double total = 0;
	double food[];

	double totalForFoods;

	void createAndShowGUI() throws IOException {
		frame = new JFrame("Main Menu ");
		frame.setBounds(100, 100, 750, 550);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		JLabel lblFoodOrdered = new JLabel("Food Ordered");
		lblFoodOrdered.setBounds(529, 11, 81, 14);

		frame.getContentPane().add(lblFoodOrdered);

		table = new JTable();
		backButton = new JButton();
		orderButton = new JButton();
		viewButton = new JButton();
		dtm = new DefaultTableModel(0, 0);
		final String header[] = new String[] { "Item", "Qty", "Price", "Spinner" };
		dtm.setColumnIdentifiers(header);
		dtm.addRow(header);
		table = new JTable();
		table.setModel(dtm);
		table.setBounds(475, 31, 1, 1); // int x, int y, int width, int height
		table.setSize(245, 300); // width,height
		table.getColumnModel().getColumn(0).setPreferredWidth(80);
		table.getColumnModel().getColumn(1).setPreferredWidth(30);
		table.getColumnModel().getColumn(2).setPreferredWidth(30);
		table.getColumnModel().getColumn(3).setMinWidth(0); // hide spinner
															// column
		table.getColumnModel().getColumn(3).setMaxWidth(0); // hide spinner
															// column
		table.setShowGrid(false); // remove cell boarder
		frame.getContentPane().add(table);

		JLabel lblTotal = new JLabel("Total  : ");
		lblTotal.setBounds(519, 340, 46, 14);
		frame.getContentPane().add(lblTotal);
		JLabel lbtable = new JLabel("Table : ");
		lbtable.setBounds(519, 360, 46, 14);
		frame.getContentPane().add(lbtable);
		textField = new JTextField();
		textField.setBounds(585, 340, 86, 20);
		frame.getContentPane().add(textField);

//		textField2 = new JTextField();
//		textField2.setBounds(585, 360, 86, 20);
//		frame.getContentPane().add(textField2);
//		textField.setColumns(10);

		comboBox = new JComboBox<>();
		TableNumbers(comboBox);
		comboBox.setBounds(585, 360, 86, 20);
		frame.getContentPane().add(comboBox);

		orderButton = new JButton("Order");
		orderButton.setBounds(500, 385, 89, 23);
		frame.getContentPane().add(orderButton);

		viewButton = new JButton("View Order");
		viewButton.setBounds(530, 415, 150, 23);
		frame.getContentPane().add(viewButton);
		backButton = new JButton("Log out");
		backButton.setBounds(610, 385, 89, 23);
		frame.getContentPane().add(backButton);
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		addIt(tabbedPane, "Foods");
		tabbedPane.setBounds(18, 11, 450, 450);
		frame.getContentPane().add(tabbedPane);
		frame.setVisible(true);

		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					MainMenu menu = new MainMenu();
					menu.main(header);
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
		viewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					OrderMenu order = new OrderMenu();
					order.main(header);
					order.setVisible(true);
					setVisible(false);
					frame.dispose();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
// print
		orderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "You not ordered anything yet");
				} else {
					try {
						JOptionPane.showMessageDialog(null, "Order successful");
						updateDatabase(table);
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
//				
			}

		});
	}

	void addIt(JTabbedPane tabbedPane, String text) throws IOException {
		JPanel panel = new JPanel(new GridBagLayout());
		gbc = new GridBagConstraints(); // getting constraints for the specified
										// component
		gbc.insets = new Insets(10, 0, 0, 0);
		foodImage = new JLabel[ELEMENTS];
		foodLabel = new JLabel[ELEMENTS];
		numSpinner = new JSpinner[ELEMENTS];
		file = new String[ELEMENTS];
		price = new Double[ELEMENTS];

		file[0] = new String("/MedSalad.png");
		file[1] = new String("/JapanesePanNoodles.png");
		file[2] = new String("/spaghetti.jpg");
		file[3] = new String("/PadThai.png");
		file[4] = new String("/RamenNoodles.png");
		file[5] = new String("/kids_spaghetti.png");
		file[6] = new String("/chickenRice.jpg");
		file[7] = new String("/thaiFood.jpg");
		file[8] = new String("/vietnamFood.jpg");

		try {
			GetName();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		int row;
		try {
			row = GetRow();
			for (int i = 0; i < row; i++) {
				foodLabel[i] = new JLabel(arrfood[i]);
			}
			for (int i = 0; i < row; i++) {
				try {
					price[i] = GetPrice(arrfood[i]);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < ELEMENTS; i++) {

			System.out.print(file[i]);
			try {

				Image image = ImageIO.read(this.getClass().getResource(file[i]));
				Image imageScaled = image.getScaledInstance(80, 95, Image.SCALE_SMOOTH);
				ImageIcon imageIcon = new ImageIcon(imageScaled);
				SpinnerNumberModel spnummodel = new SpinnerNumberModel(0, 0, 10, 1); // value,minimum,maximum,stepSize
				numSpinner[i] = new JSpinner(spnummodel);
				numSpinner[i].addChangeListener(listener);
				foodImage[i] = new JLabel(imageIcon);
			} catch (Exception e) {
				System.out.print(e);
			}
		}
		gbc.gridx = 0; // gridx 0 represent the most left
		for (int i = 0; i < ELEMENTS; i++) {
			if (i % 3 == 0) {
				gbc.gridy += 2;
				gbc.gridx = 0;
			}
			panel.add(foodImage[i], gbc);
			gbc.gridy++; // gridy---> add one row,for foodLabel
			panel.add(foodLabel[i], gbc);
			gbc.gridy--; // remove the row
			gbc.gridx++; // move to next column
			panel.add(numSpinner[i], gbc);
			gbc.gridx++; // move to next column
			tabbedPane.addTab(text, panel);
		}
	}

	ChangeListener listener = new ChangeListener() {
		public void stateChanged(ChangeEvent e) {
			final int quantity = (int) ((JSpinner) e.getSource()).getValue(); // số lượng
			final int rows = table.getRowCount(); // số hàng trong bảng hóa đơn
			String arr[] = new String[rows];
			for (int i = 0; i < rows; i++) {
				arr[i] = dtm.getValueAt(i, 0).toString();
			}

			for (int row = 0; row < rows; row++) {
				if (dtm.getValueAt(row, 3) == e.getSource()) {
					if (dtm.getValueAt(row, 0).equals(arr[row])) {
						try {
							int oldQuantity = Integer.parseInt(dtm.getValueAt(row, 1).toString());
							totalForFoods -= (GetPrice(arr[row]) * oldQuantity); // Subtract old total
							dtm.setValueAt(quantity, row, 1); // obj, row, column
							double newTotal = GetPrice(arr[row]) * quantity;
							dtm.setValueAt(newTotal, row, 2);
							totalForFoods += newTotal; // Add new total
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}

					if (quantity == 0) {
						dtm.removeRow(row);
					}
					textField.setText(totalForFoods + "");
					return;
				}
			}

			for (int i = 0; i < ELEMENTS; i++) {
				if (numSpinner[i] == e.getSource()) {
					totalPrice = price[i];
					dtm.addRow(new Object[] { foodLabel[i].getText(), quantity, totalPrice, numSpinner[i] });
					totalForFoods += totalPrice * quantity; // Add new total for the added row
					textField.setText(totalForFoods + "");
					return;
				}
			}
		}

	};

	public void setVisible(boolean b) throws IOException {
	}
}
