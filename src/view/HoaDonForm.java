package view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import dao.HoaDonDAO;
import dao.SanPhamDAO;
import model.SanPham;
import dao.ChiTietHoaDonDAO;
import model.ChiTietHoaDon;

public class HoaDonForm extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	private JTextField txtMaHD;
	private JTextField txtSoLuong;
	private JTextField txtTongTien;

	private JTable tableHoaDon;

	private JComboBox<String> cboSanPham;

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {

			public void run() {

				try {

					HoaDonForm frame = new HoaDonForm();

					frame.setVisible(true);

				} catch (Exception e) {

					e.printStackTrace();
				}
			}
		});
	}

	public HoaDonForm() {
		setTitle("Bán hàng");

		setResizable(false);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setBounds(100, 100, 681, 487);

		contentPane = new JPanel();

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);

		contentPane.setLayout(null);

		JLabel lblMaHD = new JLabel("Mã HD");

		lblMaHD.setBounds(22, 54, 61, 14);

		contentPane.add(lblMaHD);

		txtMaHD = new JTextField();

		txtMaHD.setEditable(false);

		txtMaHD.setBounds(93, 48, 86, 20);

		contentPane.add(txtMaHD);

		txtMaHD.setColumns(10);

		JLabel lblSoLuong = new JLabel("Số Lượng");

		lblSoLuong.setBounds(22, 95, 77, 14);

		contentPane.add(lblSoLuong);

		txtSoLuong = new JTextField();

		txtSoLuong.setColumns(10);

		txtSoLuong.setBounds(93, 89, 86, 20);

		contentPane.add(txtSoLuong);

		JLabel lblSanPham = new JLabel("Chọn Tên Sản Phẩm");

		lblSanPham.setBounds(315, 54, 131, 14);

		contentPane.add(lblSanPham);

		JLabel lblTongTien = new JLabel("Tổng Tiền");

		lblTongTien.setBounds(365, 95, 77, 14);

		contentPane.add(lblTongTien);

		txtTongTien = new JTextField();

		txtTongTien.setEditable(false);

		txtTongTien.setColumns(10);

		txtTongTien.setBounds(449, 95, 116, 20);

		contentPane.add(txtTongTien);

		cboSanPham = new JComboBox<String>();

		cboSanPham.setBounds(449, 50, 116, 22);

		contentPane.add(cboSanPham);

		JButton btnThem = new JButton("Thêm vào hóa đơn");

		btnThem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				try {

					String tenSP =

							cboSanPham.getSelectedItem().toString();

					SanPham sp = getSanPhamByTen(tenSP);

					int soLuong = Integer.parseInt(txtSoLuong.getText());

					if (soLuong <= 0) {

						JOptionPane.showMessageDialog(null, "Số lượng phải > 0");

						return;
					}

					if (soLuong > sp.getSoLuong()) {

						JOptionPane.showMessageDialog(null, "Không đủ số lượng");

						return;
					}

					double thanhTien = soLuong * sp.getGia();

					DefaultTableModel model =

							(DefaultTableModel) tableHoaDon.getModel();

					model.addRow(new Object[] {

							txtMaHD.getText(),

							sp.getMaSP(),

							sp.getTenSP(),

							new DecimalFormat("#,###").format(sp.getGia()) + " VNĐ",

							soLuong,

							new DecimalFormat("#,###").format(thanhTien) + " VNĐ" });

					tinhTongTien();

				} catch (NumberFormatException ex) {

					JOptionPane.showMessageDialog(null, "Số lượng phải là số");

				} catch (Exception ex) {

					JOptionPane.showMessageDialog(null, "Dữ liệu không hợp lệ");
				}
			}
		});

		btnThem.setBounds(88, 154, 140, 23);

		contentPane.add(btnThem);

		JButton btnThanhToan = new JButton("Thanh Toán");

		btnThanhToan.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				try {

					if (tableHoaDon.getRowCount() == 0) {

						JOptionPane.showMessageDialog(null, "Chưa có sản phẩm");

						return;
					}

					HoaDonDAO hoaDonDAO = new HoaDonDAO();

					String tongTienText =

							txtTongTien.getText().replace(" VNĐ", "").replace(",", "");

					double tongTien =

							Double.parseDouble(tongTienText);

					boolean checkHD =

							hoaDonDAO.themHoaDon(

									txtMaHD.getText(),

									tongTien);

					if (!checkHD) {

						JOptionPane.showMessageDialog(null, "Lưu hóa đơn thất bại");

						return;
					}

					SanPhamDAO dao = new SanPhamDAO();

					for (int i = 0; i < tableHoaDon.getRowCount(); i++) {

						String maHD =

								tableHoaDon.getValueAt(i, 0).toString();

						String maSP =

								tableHoaDon.getValueAt(i, 1).toString();

						int soLuongMua =

								Integer.parseInt(

										tableHoaDon.getValueAt(i, 4).toString());

						String donGiaText =

								tableHoaDon.getValueAt(i, 3).toString();

						donGiaText = donGiaText.replace(" VNĐ", "").replace(",", "");

						double donGia =

								Double.parseDouble(donGiaText);

						String thanhTienText =

								tableHoaDon.getValueAt(i, 5).toString();

						thanhTienText = thanhTienText.replace(" VNĐ", "").replace(",", "");

						double thanhTien =

								Double.parseDouble(thanhTienText);

						ChiTietHoaDon cthd =

								new ChiTietHoaDon(maHD, maSP, soLuongMua, donGia, thanhTien);

						ChiTietHoaDonDAO cthdDAO =

								new ChiTietHoaDonDAO();

						cthdDAO.themChiTietHoaDon(cthd);

						SanPham sp =

								getSanPhamByMa(maSP);

						int soLuongMoi =

								sp.getSoLuong() - soLuongMua;

						dao.capNhatSoLuong(maSP, soLuongMoi);
					}

					int choose =

							JOptionPane.showConfirmDialog(

									null,

									"Thanh toán thành công!\n" + "Bạn có muốn xuất hóa đơn không?",

									"Thông báo",

									JOptionPane.YES_NO_OPTION);

					if (choose == JOptionPane.YES_OPTION) {

						ChiTietHoaDonForm form =

								new ChiTietHoaDonForm(txtMaHD.getText());

						form.setVisible(true);
					}

					clearForm();

					txtSoLuong.setText("");

					txtTongTien.setText("");

					HoaDonDAO daoHD = new HoaDonDAO();

					txtMaHD.setText(daoHD.taoMaHD());

				} catch (Exception ex) {

					ex.printStackTrace();

					JOptionPane.showMessageDialog(null, "Lỗi thanh toán");
				}
			}
		});

		btnThanhToan.setBounds(302, 154, 110, 23);

		contentPane.add(btnThanhToan);

		JButton btnLamMoi = new JButton("Làm Mới");

		btnLamMoi.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				clearForm();

				HoaDonDAO daoHD = new HoaDonDAO();

				txtMaHD.setText(daoHD.taoMaHD());
			}
		});

		btnLamMoi.setBounds(476, 154, 89, 23);

		contentPane.add(btnLamMoi);

		JScrollPane scrollPane = new JScrollPane();

		scrollPane.setBounds(88, 201, 477, 169);

		contentPane.add(scrollPane);

		tableHoaDon = new JTable();

		tableHoaDon.setModel(new DefaultTableModel(new Object[][] {}, new String[] {

				"Mã HD",

				"Mã SP",

				"Tên Sản Phẩm",

				"Đơn Giá",

				"Số Lượng",

				"Thành Tiền" }));

		tableHoaDon.setRowHeight(25);

		scrollPane.setViewportView(tableHoaDon);

		JLabel lblDuLieu = new JLabel("Dữ liệu");

		lblDuLieu.setBounds(22, 202, 63, 14);

		contentPane.add(lblDuLieu);

		loadComboBox();

		HoaDonDAO daoHD = new HoaDonDAO();

		txtMaHD.setText(daoHD.taoMaHD());
		
		JButton btnSanPham = new JButton("Quản lý sản phẩm");
		btnSanPham.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnSanPham.addActionListener(
						new ActionListener() {

							public void actionPerformed(
							ActionEvent e) {

								SanPhamForm form =

								new SanPhamForm();

								form.setVisible(true);

								dispose();
							}
						});
			}
		});
		btnSanPham.setBounds(515, 11, 140, 23);
		contentPane.add(btnSanPham);

		setVisible(true);
	}

	public void loadComboBox() {

		try {

			cboSanPham.removeAllItems();

			SanPhamDAO dao = new SanPhamDAO();

			ResultSet rs = dao.getAllSanPham();

			while (rs.next()) {

				cboSanPham.addItem(rs.getString("TenSP"));
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public SanPham getSanPhamByTen(String tenSP) {

		try {

			SanPhamDAO dao = new SanPhamDAO();

			ResultSet rs = dao.getAllSanPham();

			while (rs.next()) {

				if (rs.getString("TenSP").equals(tenSP)) {

					SanPham sp = new SanPham();

					sp.setMaSP(rs.getString("MaSP"));

					sp.setTenSP(rs.getString("TenSP"));

					sp.setGia(rs.getDouble("Gia"));

					sp.setSoLuong(rs.getInt("SoLuong"));

					return sp;
				}
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return null;
	}

	public SanPham getSanPhamByMa(String maSP) {

		try {

			SanPhamDAO dao = new SanPhamDAO();

			ResultSet rs = dao.getAllSanPham();

			while (rs.next()) {

				if (rs.getString("MaSP").equals(maSP)) {

					SanPham sp = new SanPham();

					sp.setMaSP(rs.getString("MaSP"));

					sp.setTenSP(rs.getString("TenSP"));

					sp.setGia(rs.getDouble("Gia"));

					sp.setSoLuong(rs.getInt("SoLuong"));

					return sp;
				}
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return null;
	}

	public void tinhTongTien() {

		double tongTien = 0;

		for (int i = 0; i < tableHoaDon.getRowCount(); i++) {

			String tien =

					tableHoaDon.getValueAt(i, 5).toString();

			tien = tien.replace(" VNĐ", "").replace(",", "");

			tongTien += Double.parseDouble(tien);
		}

		txtTongTien.setText(

				new DecimalFormat("#,###").format(tongTien)

						+ " VNĐ");
	}

	public void clearForm() {

		txtSoLuong.setText("");

		txtTongTien.setText("");

		DefaultTableModel model =

				(DefaultTableModel) tableHoaDon.getModel();

		model.setRowCount(0);
	}
}