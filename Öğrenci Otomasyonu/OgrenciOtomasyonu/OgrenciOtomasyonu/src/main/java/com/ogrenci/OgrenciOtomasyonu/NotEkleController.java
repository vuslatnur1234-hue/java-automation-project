package com.ogrenci.OgrenciOtomasyonu;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

// Veritabanı işlemleri için gerekli sınıflar
import java.sql.*;
// Liste yapıları için gerekli sınıflar
import java.util.ArrayList;
import java.util.List;

// Bu sınıfın bir Controller olduğunu belirtir
@Controller
public class NotEkleController {

    // Not ekleme sayfasını açar ve öğrenci listesini hazırlar
    @GetMapping("/not-ekle")
    public String notEkleSayfasi(Model model) {
        // Öğrencileri tutacak liste
        List<String> ogrenciListesi = new ArrayList<>();
        try {
            // Veritabanına bağlanılır
            Connection c = Database.connect();
            // Öğrenci numarası ve adı çekilir
            ResultSet rs = c.createStatement().executeQuery("SELECT no, ad FROM ogrenci");
            // Öğrenciler listeye eklenir
            while (rs.next()) {
                ogrenciListesi.add(rs.getString("no") + " - " + rs.getString("ad"));
            }
            // Veritabanı bağlantısı kapatılır
            c.close();
        } catch (Exception e) {
            // Hatalar konsola yazdırılır
            e.printStackTrace();
        }

        // Öğrenci listesi view tarafına gönderilir
        model.addAttribute("ogrenciler", ogrenciListesi);
        return "not-ekle";
    }

    // Girilen not ve ders bilgilerini kaydeder
    @PostMapping("/not-kaydet")
    public String notuKaydet(@RequestParam String secilenOgrenci,
                             @RequestParam String dersBilgisi,
                             @RequestParam String dersHocasi,
                             @RequestParam String vizeNotu,
                             @RequestParam String finalNotu,
                             @RequestParam String devamsizlik,
                             Model model) {
        try {
            // Ders bilgisini "DersAdı|AKTS|Hoca" formatına göre ayırır
            String[] parcalar = dersBilgisi.split("\\|");
            String dersAdi = parcalar[0];
            int akts = Integer.parseInt(parcalar[1]);
            String hoca = parcalar[2];

            // Not ve devamsızlık bilgileri sayısal değere çevrilir
            int vize = Integer.parseInt(vizeNotu);
            int finalN = Integer.parseInt(finalNotu);
            int devamsiz = Integer.parseInt(devamsizlik);
            // Seçilen öğrencinin numarası alınır
            String ogrenciNo = secilenOgrenci.split(" - ")[0].trim();

            // Veritabanına bağlanılır
            Connection c = Database.connect();
            // Öğrencinin not, ders ve hoca bilgileri güncellenir
            String sql = "UPDATE ogrenci SET vize=?, final=?, devamsizlik=?, ders=?, akts=?, hoca=? WHERE no=?";
            PreparedStatement ps = c.prepareStatement(sql);

            // SQL sorgusundaki parametreler atanır
            ps.setInt(1, vize);
            ps.setInt(2, finalN);
            ps.setInt(3, devamsiz);
            ps.setString(4, dersAdi);
            ps.setInt(5, akts);
            ps.setString(6, hoca);
            ps.setString(7, ogrenciNo);

            // Güncelleme işlemi çalıştırılır
            ps.executeUpdate();
            // Veritabanı bağlantısı kapatılır
            c.close();

            // Başarılı kayıt mesajı gösterilir
            model.addAttribute("mesaj", dersAdi + " dersi (" + hoca + ") başarıyla kaydedildi!");

        } catch (Exception e) {
            // Hata oluşursa kullanıcıya mesaj gösterilir
            model.addAttribute("hata", "Hata: " + e.getMessage());
        }

        // Aynı sayfa tekrar yüklenir
        return notEkleSayfasi(model);

    }
}
