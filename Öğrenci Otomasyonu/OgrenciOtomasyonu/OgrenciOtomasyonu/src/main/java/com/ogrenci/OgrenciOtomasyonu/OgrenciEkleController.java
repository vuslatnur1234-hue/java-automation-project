package com.ogrenci.OgrenciOtomasyonu;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

// Veritabanı işlemleri için gerekli sınıflar
import java.sql.Connection;
import java.sql.PreparedStatement;

// Bu sınıfın bir Controller olduğunu belirtir
@Controller
public class OgrenciEkleController {

    // Öğrenci ekleme sayfasını açar
    @GetMapping("/ogrenci-ekle")
    public String sayfaAc() {
        return "ogrenci-ekle"; // HTML sayfası döndürülür
    }

    // Öğrenci kaydetme işlemini gerçekleştirir
    @PostMapping("/ogrenci-kaydet")
    public String kaydet(@RequestParam String no,
                         @RequestParam String ad,
                         Model model) {

        // Boş alan kontrolü yapılır
        if (no.trim().isEmpty() || ad.trim().isEmpty()) {
            model.addAttribute("hata", "Lütfen tüm alanları doldurunuz!");
            return "ogrenci-ekle";
        }

        // Öğrenci numarasının sadece rakamlardan oluşması kontrol edilir
        if (!no.matches("\\d+")) {
            model.addAttribute("hata", "Öğrenci numarası sadece rakamlardan oluşmalıdır!");
            return "ogrenci-ekle";
        }

        // Öğrenci adının sadece harflerden ve boşluklardan oluşması kontrol edilir
        if (!ad.matches("[a-zA-ZğüşöçĞÜŞİÖÇİı\\s]+")) {
            model.addAttribute("hata", "İsim sadece harflerden oluşmalıdır!");
            return "ogrenci-ekle";
        }

        // Veritabanına öğrenci ekleme işlemleri
        try {
            // Veritabanına bağlanılır
            Connection c = Database.connect();

            // Yeni öğrenci kaydı için SQL sorgusu
            String sql = "INSERT INTO ogrenci(no, ad, vize, final, devamsizlik) VALUES(?, ?, 0, 0, 0)";
            PreparedStatement ps = c.prepareStatement(sql);

            // SQL parametreleri atanır
            ps.setString(1, no);
            ps.setString(2, ad);

            // Kayıt işlemi çalıştırılır
            ps.executeUpdate();
            // Veritabanı bağlantısı kapatılır
            c.close();

            // Başarılı kayıt mesajı gösterilir
            model.addAttribute("mesaj", "Öğrenci başarıyla kaydedildi.");

        } catch (Exception ex) {
            // Veritabanı hatası oluşursa kullanıcıya mesaj gösterilir
            model.addAttribute("hata", "Veritabanı Hatası: " + ex.getMessage());
        }

        // Aynı sayfa tekrar açılır
        return "ogrenci-ekle";
    }
}
