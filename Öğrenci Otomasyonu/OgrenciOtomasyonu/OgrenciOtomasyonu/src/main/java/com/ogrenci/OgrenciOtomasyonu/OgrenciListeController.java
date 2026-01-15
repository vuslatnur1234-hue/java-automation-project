package com.ogrenci.OgrenciOtomasyonu;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

// Veritabanı işlemleri için gerekli sınıflar
import java.sql.*;
// Liste yapıları için gerekli sınıflar
import java.util.ArrayList;
import java.util.List;

// Bu sınıfın bir Controller olduğunu belirtir
@Controller
public class OgrenciListeController {

    // Öğrenci listeleme ve isimle arama işlemi
    @GetMapping("/ogrenci-listesi")
    public String listeyiGetir(@RequestParam(required = false) String aramaKelimesi, Model model) {
        // Öğrencileri tutacak liste
        List<Ogrenci> ogrenciler = new ArrayList<>();
        // Genel ortalama hesaplaması için değişkenler
        double toplamOrtalama = 0;
        int sayi = 0;

        try {
            // Veritabanına bağlanılır
            Connection c = Database.connect();
            Statement st = c.createStatement();

            // Temel sorgu
            String sql = "SELECT * FROM ogrenci";
            // Arama kelimesi varsa isim üzerinden filtreleme yapılır
            if (aramaKelimesi != null && !aramaKelimesi.isEmpty()) {
                sql += " WHERE ad LIKE '%" + aramaKelimesi + "%'";
            }

            // Öğrenciler isme göre sıralanır
            sql += " ORDER BY ad ASC";

            // Sorgu çalıştırılır
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                // Öğrenci nesnesi oluşturulur
                Ogrenci ogr = new Ogrenci(
                        rs.getString("no"),
                        rs.getString("ad"),
                        rs.getInt("vize"),
                        rs.getInt("final"),
                        rs.getInt("devamsizlik")
                );

                // Ders, AKTS ve hoca bilgileri nesneye atanır
                ogr.setDers(rs.getString("ders"));
                ogr.setAkts(rs.getInt("akts"));
                ogr.setHoca(rs.getString("hoca"));

                // Listeye eklenir ve ortalama hesaplanır
                ogrenciler.add(ogr);
                toplamOrtalama += ogr.getOrtalama();
                sayi++;
            }
            // Veritabanı bağlantısı kapatılır
            c.close();
        } catch (Exception e) {
            // Oluşabilecek hatalar konsola yazdırılır
            e.printStackTrace();
        }

        // Genel ortalama hesaplanır
        double genelOrt = (sayi > 0) ? (toplamOrtalama / sayi) : 0;

        // Liste ve genel ortalama view tarafına gönderilir
        model.addAttribute("liste", ogrenciler);
        model.addAttribute("genelOrtalama", (int)genelOrt);

        return "ogrenci-listesi";
    }

    // Seçilen öğrenciyi veritabanından siler
    @GetMapping("/ogrenci-sil/{no}")
    public String sil(@PathVariable String no) {
        try {
            // Veritabanına bağlanılır
            Connection c = Database.connect();
            // Öğrenci silme sorgusu hazırlanır
            PreparedStatement ps = c.prepareStatement("DELETE FROM ogrenci WHERE no = ?");
            ps.setString(1, no);
            // Silme işlemi çalıştırılır
            ps.executeUpdate();
            // Veritabanı bağlantısı kapatılır
            c.close();
        } catch (Exception e) {
            // Hatalar konsola yazdırılır
            e.printStackTrace();
        }
        // Liste sayfasına geri yönlendirme yapılır
        return "redirect:/ogrenci-listesi";
    }

    // Öğrenci düzenleme sayfasını açar
    @GetMapping("/ogrenci-duzenle/{no}")
    public String duzenleSayfasi(@PathVariable String no, Model model) {
        try {
            // Veritabanına bağlanılır
            Connection c = Database.connect();
            // Seçilen öğrencinin bilgileri çekilir
            PreparedStatement ps = c.prepareStatement("SELECT * FROM ogrenci WHERE no = ?");
            ps.setString(1, no);
            ResultSet rs = ps.executeQuery();

            // Öğrenci bulunduysa nesne oluşturulur
            if (rs.next()) {
                Ogrenci ogr = new Ogrenci(
                        rs.getString("no"),
                        rs.getString("ad"),
                        rs.getInt("vize"),
                        rs.getInt("final"),
                        rs.getInt("devamsizlik")
                );
                // Öğrenci bilgileri view tarafına gönderilir
                model.addAttribute("ogrenci", ogr);
            }
            // Veritabanı bağlantısı kapatılır
            c.close();
        } catch (Exception e) {
            // Hatalar konsola yazdırılır
            e.printStackTrace();
        }
        return "ogrenci-duzenle";
    }

    // Güncellenen öğrenci bilgilerini kaydeder
    @PostMapping("/ogrenci-guncelle-kaydet")
    public String guncelleKaydet(@RequestParam String eskiNo,
                                 @RequestParam String yeniNo,
                                 @RequestParam String yeniAd,
                                 @RequestParam int yeniVize,
                                 @RequestParam int yeniFinal,
                                 @RequestParam int yeniDevamsizlik) {

        // Database sınıfındaki güncelleme metodu çağrılır
        Database.ogrenciGuncelle(eskiNo, yeniNo, yeniAd, yeniVize, yeniFinal, yeniDevamsizlik);
        return "redirect:/ogrenci-listesi";
    }

}
