package com.ogrenci.OgrenciOtomasyonu;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;

// Veritabanı işlemlerini yöneten sınıf
public class Database {

    // Veritabanına bağlanmak için kullanılan metot
    public static Connection connect() {
        try {
            // SQLite veritabanı bağlantı adresi
            String url = "jdbc:sqlite:ogrenci.db";
            // Veritabanına bağlantı oluşturulur
            Connection c = DriverManager.getConnection(url);
            // SQL komutlarını çalıştırmak için Statement oluşturulur
            Statement st = c.createStatement();

            // 1. ADIM: Mevcut tabloda eksik sütunlar varsa eklenir (veriler silinmez)
            try { st.execute("ALTER TABLE ogrenci ADD COLUMN ders TEXT"); } catch(Exception e) {}
            try { st.execute("ALTER TABLE ogrenci ADD COLUMN akts INTEGER"); } catch(Exception e) {}
            try { st.execute("ALTER TABLE ogrenci ADD COLUMN hoca TEXT"); } catch(Exception e) {}

            // 2. ADIM: Öğrenci tablosu yoksa oluşturulur
            String sql = "CREATE TABLE IF NOT EXISTS ogrenci (" +
                    "no TEXT PRIMARY KEY," +
                    "ad TEXT," +
                    "vize INTEGER," +
                    "final INTEGER," +
                    "devamsizlik INTEGER," +
                    "ders TEXT," +
                    "akts INTEGER," +
                    "hoca TEXT)";
            st.execute(sql);

            // Bağlantı nesnesi geri döndürülür
            return c;
        } catch (Exception e) {
            // Bağlantı sırasında hata oluşursa mesaj yazdırılır
            System.out.println("Bağlantı Hatası: " + e.getMessage());
            return null;
        }
    }

    // Öğrenci bilgilerini güncellemek için kullanılan metot
    public static void ogrenciGuncelle(String eskiNo, String yeniNo, String yeniAd, int yeniVize, int yeniFinal, int yeniDevamsizlik) {
        try {
            // Veritabanına bağlanılır
            Connection c = connect();
            // Öğrenci bilgilerini güncelleyen SQL sorgusu
            String sql = "UPDATE ogrenci SET no = ?, ad = ?, vize = ?, final = ?, devamsizlik = ? WHERE no = ?";
            PreparedStatement ps = c.prepareStatement(sql);

            // Güncellenecek yeni değerler atanır
            ps.setString(1, yeniNo);
            ps.setString(2, yeniAd);
            ps.setInt(3, yeniVize);
            ps.setInt(4, yeniFinal);
            ps.setInt(5, yeniDevamsizlik);
            ps.setString(6, eskiNo);

            // Güncelleme işlemi çalıştırılır
            ps.executeUpdate();
            // Veritabanı bağlantısı kapatılır
            c.close();
        } catch (Exception e) {
            // Hata oluşursa konsola yazdırılır
            e.printStackTrace();
        }
    }
}
