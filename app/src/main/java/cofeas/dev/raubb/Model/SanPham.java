package cofeas.dev.raubb.Model;

import java.io.Serializable;

/**
 * Created by falcon on 18/11/2017.
 */

public class SanPham implements Serializable {
    public int IDSanPham;
    public int IDLoaiSanPham;
    public String TenSanPham;
    public String ThongTinSanPham;
    public Integer Gia;
    public String DonVi;
    public String HinhAnh;
    public int IDKhuyenMai;

    public int getIDSanPham() {
        return IDSanPham;
    }

    public void setIDSanPham(int IDSanPham) {
        this.IDSanPham = IDSanPham;
    }

    public int getIDLoaiSanPham() {
        return IDLoaiSanPham;
    }

    public void setIDLoaiSanPham(int IDLoaiSanPham) {
        this.IDLoaiSanPham = IDLoaiSanPham;
    }

    public String getTenSanPham() {
        return TenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        TenSanPham = tenSanPham;
    }

    public String getThongTinSanPham() {
        return ThongTinSanPham;
    }

    public void setThongTinSanPham(String thongTinSanPham) {
        ThongTinSanPham = thongTinSanPham;
    }

    public Integer getGia() {
        return Gia;
    }

    public void setGia(Integer gia) {
        Gia = gia;
    }

    public String getDonVi() {
        return DonVi;
    }

    public void setDonVi(String donVi) {
        DonVi = donVi;
    }

    public String getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        HinhAnh = hinhAnh;
    }

    public int getIDKhuyenMai() {
        return IDKhuyenMai;
    }

    public void setIDKhuyenMai(int IDKhuyenMai) {
        this.IDKhuyenMai = IDKhuyenMai;
    }

    public SanPham(int IDSanPham, int IDLoaiSanPham, String tenSanPham, String thongTinSanPham, Integer gia, String donvi, String hinhAnh, int IDKhuyenMai) {
        this.IDSanPham = IDSanPham;
        this.IDLoaiSanPham = IDLoaiSanPham;
        TenSanPham = tenSanPham;
        ThongTinSanPham = thongTinSanPham;
        Gia = gia;
        DonVi = donvi;
        HinhAnh = hinhAnh;
        this.IDKhuyenMai = IDKhuyenMai;

    }
}