package view;

import java.awt.EventQueue;
import java.sql.ResultSet;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import dao.ChiTietHoaDonDAO;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChiTietHoaDonForm extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	private JTable tableCTHD;

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {

			public void run() {

				try {

					ChiTietHoaDonForm frame =
					new ChiTietHoaDonForm("HD01");

					frame.setVisible(true);

				} catch (Exception e) {

					e.printStackTrace();
				}
			}
		});
	}

	public ChiTietHoaDonForm(
	String maHD) {

		setTitle(
		"Chi tiết hóa đơn");

		setDefaultCloseOperation(
		JFrame.DISPOSE_ON_CLOSE);

		setBounds(
		100,
		100,
		567,
		342);

		contentPane = new JPanel();

		contentPane.setBorder(
		new EmptyBorder(
		5,
		5,
		5,
		5));

		setContentPane(
		contentPane);

		contentPane.setLayout(null);

		JLabel lblTitle =
		new JLabel(
		"Chi tiết hóa đơn: "
		+ maHD);

		lblTitle.setBounds(
		205,
		20,
		250,
		20);

		contentPane.add(
		lblTitle);

		JScrollPane scrollPane =
		new JScrollPane();

		scrollPane.setBounds(
		59,
		53,
		470,
		190);

		contentPane.add(
		scrollPane);

		tableCTHD =
		new JTable();

		tableCTHD.setModel(

		new DefaultTableModel(

			new Object[][] {},

			new String[] {

				"Mã HD",

				"Mã SP",

				"Tên SP",

				"Số Lượng",

				"Đơn Giá",

				"Thành Tiền"
			}
		));

		tableCTHD.setRowHeight(25);

		scrollPane.setViewportView(
		tableCTHD);

		JLabel lblDLieu =
		new JLabel("Dữ liệu");

		lblDLieu.setBounds(
		10,
		60,
		68,
		14);

		contentPane.add(
		lblDLieu);

		JButton btnQuayLai =
		new JButton("Quay lại");

		btnQuayLai.addActionListener(
		new ActionListener() {

			public void actionPerformed(
			ActionEvent e) {

				dispose();
			}
		});

		btnQuayLai.setBounds(
		10,
		11,
		89,
		23);

		contentPane.add(
		btnQuayLai);

		loadChiTietHoaDon(
		maHD);
	}

	public void loadChiTietHoaDon(
	String maHD) {

		try {

			DefaultTableModel model =

			(DefaultTableModel)
			tableCTHD.getModel();

			model.setRowCount(0);

			ChiTietHoaDonDAO dao =

			new ChiTietHoaDonDAO();

			ResultSet rs =

			dao.getChiTietHoaDon(
			maHD);

			boolean firstRow = true;

			double tongHoaDon = 0;

			while(rs.next()) {

				double thanhTien =

				rs.getDouble(
				"ThanhTien");

				tongHoaDon +=
				thanhTien;

				model.addRow(
				new Object[] {

					firstRow ?

					rs.getString(
					"MaHD")

					: "",

					rs.getString(
					"MaSP"),

					rs.getString(
					"TenSP"),

					rs.getInt(
					"SoLuong"),

					new DecimalFormat(
					"#,###")
					.format(

					rs.getDouble(
					"DonGia"))

					+ " VNĐ",

					new DecimalFormat(
					"#,###")
					.format(
					thanhTien)

					+ " VNĐ"
				});

				firstRow = false;
			}

			model.addRow(
			new Object[] {

				"",

				"",

				"",

				"",

				"Tổng Tiền",

				new DecimalFormat(
				"#,###")
				.format(
				tongHoaDon)

				+ " VNĐ"
			});

		} catch (Exception e) {

			e.printStackTrace();
		}
	}
}