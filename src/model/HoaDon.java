package model;

import java.util.Date;

public class HoaDon {

	private String maHD;

	private Date ngayLap;

	private double tongTien;

	public HoaDon() {

	}

	public HoaDon(
			String maHD,
			Date ngayLap,
			double tongTien) {

		this.maHD = maHD;
		this.ngayLap = ngayLap;
		this.tongTien = tongTien;
	}

	public String getMaHD() {
		return maHD;
	}

	public void setMaHD(String maHD) {
		this.maHD = maHD;
	}

	public Date getNgayLap() {
		return ngayLap;
	}

	public void setNgayLap(Date ngayLap) {
		this.ngayLap = ngayLap;
	}

	public double getTongTien() {
		return tongTien;
	}

	public void setTongTien(double tongTien) {
		this.tongTien = tongTien;
	}
}