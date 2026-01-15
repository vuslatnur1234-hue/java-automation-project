package com.ogrenci.OgrenciOtomasyonu;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.sql.*;

// Bu sınıfın bir Controller olduğunu belirtir
@Controller
public class AnaMenuController {

    // /ana-menu adresine gelen GET isteklerini karşılar
    @GetMapping("/ana-menu")
    public String anaMenuGoster(Model model) {

        // Geçen, kalan ve devamsız öğrencilerin sayıları
        int gecen = 0;
        int kalan = 0;
        int devamsiz = 0;

        try {
            // Veritabanına bağlanılır
            Connection c = Database.connect();
            // SQL komutlarını çalıştırmak için Statement oluşturulur
            Statement st = c.createStatement();
            // Öğrenci tablosundaki tüm kayıtlar çekilir
            ResultSet rs = st.executeQuery("SELECT * FROM ogrenci");

            // Tüm öğrenciler tek tek gezilir
            while (rs.next()) {
                // Vize, final ve devamsızlık bilgileri alınır
                int v = rs.getInt("vize");
                int f = rs.getInt("final");
                int d = rs.getInt("devamsizlik");

                // Öğrencinin not ortalaması hesaplanır
                double ort = (v * 0.4) + (f * 0.6);

                // Devamsızlık ve ortalamaya göre durum belirlenir
                if (d >= 5) devamsiz++;
                else if (ort >= 50) gecen++;
                else kalan++;
            }
            // Veritabanı bağlantısı kapatılır
            c.close();
        } catch (Exception e) {
            // Oluşabilecek hatalar konsola yazdırılır
            e.printStackTrace();
        }

        // Hesaplanan değerler ekrana (view) gönderilir
        model.addAttribute("gecen", gecen);
        model.addAttribute("kalan", kalan);
        model.addAttribute("devamsiz", devamsiz);

        // ana-menu sayfası döndürülür
        return "ana-menu";
    }
}
