package view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import dao.SanPhamDAO;
import model.SanPham;
import java.text.DecimalFormat;

public class SanPhamForm extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	private JTextField txtMaSP;
	private JTextField txtGia;
	private JTextField txtTenSP;
	private JTextField txtSoLuong;
	private JTextField txtTimKiem;

	private JTable tableSanPham;

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {

			public void run() {

				try {

					SanPhamForm frame = new SanPhamForm();

					frame.setVisible(true);

				} catch (Exception e) {

					e.printStackTrace();
				}
			}
		});
	}

	public SanPhamForm() {
		setTitle("Quản lý sản phẩm");

		setResizable(false);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setBounds(100, 100, 715, 501);

		contentPane = new JPanel();

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);

		contentPane.setLayout(null);

		JLabel lblMaSP = new JLabel("Mã SP");

		lblMaSP.setBounds(47, 49, 64, 14);

		contentPane.add(lblMaSP);

		txtMaSP = new JTextField();

		txtMaSP.setBounds(103, 46, 86, 20);

		contentPane.add(txtMaSP);

		txtMaSP.setColumns(10);

		JLabel lblGia = new JLabel("Giá");

		lblGia.setBounds(47, 94, 46, 14);

		contentPane.add(lblGia);

		txtGia = new JTextField();

		txtGia.setColumns(10);

		txtGia.setBounds(103, 91, 86, 20);

		contentPane.add(txtGia);

		JLabel lblTenSP = new JLabel("Tên SP");

		lblTenSP.setBounds(431, 52, 59, 14);

		contentPane.add(lblTenSP);

		txtTenSP = new JTextField();

		txtTenSP.setColumns(10);

		txtTenSP.setBounds(500, 49, 86, 20);

		contentPane.add(txtTenSP);

		JLabel lblSoLuong = new JLabel("Số Lượng");

		lblSoLuong.setBounds(431, 94, 69, 14);

		contentPane.add(lblSoLuong);

		txtSoLuong = new JTextField();

		txtSoLuong.setColumns(10);

		txtSoLuong.setBounds(500, 91, 86, 20);

		contentPane.add(txtSoLuong);

		JLabel lblTuKhoa = new JLabel("Từ khóa");

		lblTuKhoa.setBounds(35, 190, 76, 14);

		contentPane.add(lblTuKhoa);

		txtTimKiem = new JTextField();

		txtTimKiem.setColumns(10);

		txtTimKiem.setBounds(103, 187, 314, 20);

		contentPane.add(txtTimKiem);

		JButton btnThem = new JButton("Thêm");

		btnThem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (txtMaSP.getText().trim().isEmpty() || txtTenSP.getText().trim().isEmpty()
						|| txtGia.getText().trim().isEmpty() || txtSoLuong.getText().trim().isEmpty()) {

					JOptionPane.showMessageDialog(

							null,

							"Các dữ liệu không được bỏ trống");

					return;
				}

				try {

					String maSP = txtMaSP.getText().trim();

					String tenSP = txtTenSP.getText().trim();

					String giaText = txtGia.getText().trim();

					String soLuongText = txtSoLuong.getText().trim();

					if (maSP.isEmpty() || tenSP.isEmpty() || giaText.isEmpty() || soLuongText.isEmpty()) {

						JOptionPane.showMessageDialog(null, "Không được để trống");

						return;
					}

					if (!maSP.matches("SP\\d+")) {

						JOptionPane.showMessageDialog(null, "Mã SP phải bắt đầu bằng SP");

						return;
					}

					double gia = Double.parseDouble(giaText);

					if (gia <= 0) {

						JOptionPane.showMessageDialog(null, "Giá phải lớn hơn 0");

						return;
					}

					int soLuong = Integer.parseInt(soLuongText);

					if (soLuong < 0) {

						JOptionPane.showMessageDialog(null, "Số lượng không được âm");

						return;
					}

					SanPham sp = new SanPham(maSP, tenSP, gia, soLuong);

					SanPhamDAO dao = new SanPhamDAO();

					boolean check = dao.themSanPham(sp);

					if (check) {

						JOptionPane.showMessageDialog(null, "Thêm thành công");

						loadTable();

						clearForm();

					} else {

						JOptionPane.showMessageDialog(null, "Mã sản phẩm đã tồn tại");
					}

				} catch (NumberFormatException ex) {

					JOptionPane.showMessageDialog(null, "Giá hoặc số lượng phải là số");

				} catch (Exception ex) {

					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		});

		btnThem.setBounds(103, 135, 69, 23);

		contentPane.add(btnThem);

		JButton btnSua = new JButton("Sửa");

		btnSua.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (txtMaSP.getText().trim().isEmpty() || txtTenSP.getText().trim().isEmpty()
						|| txtGia.getText().trim().isEmpty() || txtSoLuong.getText().trim().isEmpty()) {

					JOptionPane.showMessageDialog(

							null,

							"Các dữ liệu không được bỏ trống");

					return;
				}

				try {

					String maSP = txtMaSP.getText().trim();

					String tenSP = txtTenSP.getText().trim();

					double gia = Double.parseDouble(txtGia.getText());

					int soLuong = Integer.parseInt(txtSoLuong.getText());

					if (gia <= 0) {

						JOptionPane.showMessageDialog(null, "Giá phải lớn hơn 0");

						return;
					}

					if (soLuong < 0) {

						JOptionPane.showMessageDialog(null, "Số lượng không được âm");

						return;
					}

					SanPham sp = new SanPham(maSP, tenSP, gia, soLuong);

					SanPhamDAO dao = new SanPhamDAO();

					boolean check = dao.suaSanPham(sp);

					if (check) {

						JOptionPane.showMessageDialog(null, "Sửa thành công");

						loadTable();

						clearForm();

					} else {

						JOptionPane.showMessageDialog(null, "Sửa thất bại");
					}

				} catch (NumberFormatException ex) {

					JOptionPane.showMessageDialog(null, "Giá hoặc số lượng phải là số");

				} catch (Exception ex) {

					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		});

		btnSua.setBounds(237, 135, 69, 23);

		contentPane.add(btnSua);

		JButton btnXoa = new JButton("Xóa");

		btnXoa.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				try {

					String maSP = txtMaSP.getText();

					if (maSP.isEmpty()) {

						JOptionPane.showMessageDialog(null, "Vui lòng chọn sản phẩm");

						return;
					}

					SanPhamDAO dao = new SanPhamDAO();

					int choose = JOptionPane.showConfirmDialog(null, "Bạn có muốn xóa không?");

					if (choose == 0) {

						boolean check = dao.xoaSanPham(maSP);

						if (check) {

							JOptionPane.showMessageDialog(null, "Xóa thành công");

							loadTable();

							clearForm();

						} else {

							JOptionPane.showMessageDialog(null, "Xóa thất bại");
						}
					}

				} catch (Exception ex) {

					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		});

		btnXoa.setBounds(362, 135, 69, 23);

		contentPane.add(btnXoa);

		JButton btnLamMoi = new JButton("Làm Mới");

		btnLamMoi.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				clearForm();

				loadTable();
			}
		});

		btnLamMoi.setBounds(487, 135, 99, 23);

		contentPane.add(btnLamMoi);

		JButton btnTimKiem = new JButton("Tìm Kiếm");

		btnTimKiem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				String keyword = txtTimKiem.getText();

				search(keyword);
			}
		});

		btnTimKiem.setBounds(487, 186, 99, 23);

		contentPane.add(btnTimKiem);

		JScrollPane scrollPane = new JScrollPane();

		scrollPane.setBounds(103, 237, 483, 179);

		contentPane.add(scrollPane);

		tableSanPham = new JTable();

		tableSanPham.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				int row = tableSanPham.getSelectedRow();

				txtMaSP.setText(tableSanPham.getValueAt(row, 0).toString());

				txtTenSP.setText(tableSanPham.getValueAt(row, 1).toString());

				String gia = tableSanPham.getValueAt(row, 2).toString();

				gia = gia.replace(" VNĐ", "").replace(",", "");

				txtGia.setText(gia);

				txtSoLuong.setText(tableSanPham.getValueAt(row, 3).toString());

				txtMaSP.setEditable(false);
			}
		});

		tableSanPham.setModel(
				new DefaultTableModel(new Object[][] {}, new String[] { "Mã SP", "Tên Sản Phẩm", "Giá", "Số Lượng" }));

		tableSanPham.setRowHeight(25);

		scrollPane.setViewportView(tableSanPham);

		JLabel lblDuLieu = new JLabel("Dữ liệu");

		lblDuLieu.setBounds(47, 244, 58, 14);

		contentPane.add(lblDuLieu);

		JButton btnQuayLai = new JButton("Quay lại");
		btnQuayLai.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnQuayLai.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {

						HoaDonForm form =

								new HoaDonForm();

						form.setVisible(true);

						dispose();
					}
				});
			}
		});
		btnQuayLai.setBounds(22, 11, 89, 23);
		contentPane.add(btnQuayLai);

		loadTable();

		setVisible(true);
	}

	public void clearForm() {

		txtMaSP.setText("");

		txtTenSP.setText("");

		txtGia.setText("");

		txtSoLuong.setText("");

		txtTimKiem.setText("");

		txtMaSP.setEditable(true);

		tableSanPham.clearSelection();
	}

	public void loadTable() {

		try {

			DefaultTableModel model = (DefaultTableModel) tableSanPham.getModel();

			model.setRowCount(0);

			SanPhamDAO dao = new SanPhamDAO();

			ResultSet rs = dao.getAllSanPham();

			while (rs.next()) {

				Object[] row = {

						rs.getString("MaSP"),

						rs.getString("TenSP"),

						new DecimalFormat("#,###").format(rs.getDouble("Gia")) + " VNĐ",

						rs.getInt("SoLuong") };

				model.addRow(row);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void search(String keyword) {

		try {

			DefaultTableModel model = (DefaultTableModel) tableSanPham.getModel();

			model.setRowCount(0);

			SanPhamDAO dao = new SanPhamDAO();

			ResultSet rs = dao.timKiemSanPham(keyword);

			while (rs.next()) {

				Object[] row = {

						rs.getString("MaSP"),

						rs.getString("TenSP"),

						new DecimalFormat("#,###").format(rs.getDouble("Gia")) + " VNĐ",

						rs.getInt("SoLuong") };

				model.addRow(row);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}
}