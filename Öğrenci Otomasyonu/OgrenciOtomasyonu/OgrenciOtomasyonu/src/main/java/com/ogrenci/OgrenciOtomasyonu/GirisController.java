package com.ogrenci.OgrenciOtomasyonu;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// Veritabanı işlemleri için gerekli sınıflar
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
// Liste yapıları için gerekli sınıflar
import java.util.ArrayList;
import java.util.List;

// Bu sınıfın bir Controller olduğunu belirtir
@Controller
public class GirisController {

    // Sistemde kullanılan şifreyi hafızada tutan değişken
    private static String mevcutSifre = "1234";

    // Ana giriş sayfasını gösterir
    @GetMapping("/")
    public String girisSayfasi() {
        return "giris";
    }

    // Kullanıcıdan gelen giriş bilgilerini kontrol eder
    @PostMapping("/giris-yap")
    public String girisKontrol(@RequestParam String kullanici, @RequestParam String sifre, Model model) {
        // Kullanıcı adı ve şifre doğruysa ana menüye yönlendirir
        if (kullanici.equals("akademisyen") && sifre.equals(mevcutSifre)) {
            return "redirect:/ana-menu";
        } else {
            // Hatalı giriş durumunda hata mesajı gösterilir
            model.addAttribute("hata", "Hatalı kullanıcı adı veya şifre!");
            return "giris";
        }
    }

    // Şifre yenileme sayfasını açar
    @GetMapping("/sifre-yenile")
    public String sifreYenileSayfasi() {
        return "sifre-yenile";
    }

    // Yeni şifre bilgilerini kontrol eder ve günceller
    @PostMapping("/sifre-guncelle")
    public String sifreGuncelle(@RequestParam String kullanici,
                                @RequestParam String yeniSifre,
                                @RequestParam String yeniSifreTekrar,
                                RedirectAttributes redirectAttributes,
                                Model model) {

        // Girilen şifreler birbiriyle uyuşmuyorsa hata verir
        if (!yeniSifre.equals(yeniSifreTekrar)) {
            model.addAttribute("hata", "Şifreler birbiriyle eşleşmiyor!");
            return "sifre-yenile";
        }

        // Kullanıcı adı doğruysa şifre güncellenir
        if (kullanici.equals("akademisyen")) {
            mevcutSifre = yeniSifre;

            // Sayfa yönlendirilse bile mesajın kaybolmamasını sağlar
            redirectAttributes.addFlashAttribute(
                    "mesaj",
                    "Şifreniz başarıyla güncellendi! Yeni şifrenizle giriş yapabilirsiniz."
            );

            // Giriş sayfasına yönlendirme yapılır
            return "redirect:/";
        } else {
            // Kullanıcı adı bulunamazsa hata mesajı gösterilir
            model.addAttribute("hata", "Kullanıcı adı bulunamadı!");
            return "sifre-yenile";
        }
    }

    // Öğrenci listesini gösteren sayfa
    @GetMapping("/liste")
    public String liste(Model model) {
        // Öğrencileri tutacak liste
        List<Ogrenci> ogrenciler = new ArrayList<>();
        try {
            // Veritabanına bağlanılır
            Connection c = Database.connect();
            if(c != null) {
                // Öğrenci tablosundaki tüm kayıtlar çekilir
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM ogrenci");

                // Her öğrenci nesneye dönüştürülerek listeye eklenir
                while (rs.next()) {
                    ogrenciler.add(
                            new Ogrenci(
                                    rs.getString("no"),
                                    rs.getString("ad"),
                                    rs.getInt("vize"),
                                    rs.getInt("final"),
                                    0
                            )
                    );
                }
                // Veritabanı bağlantısı kapatılır
                c.close();
            }
        } catch (Exception e) {
            // Oluşan hatalar konsola yazdırılır
            e.printStackTrace();
        }

        // Öğrenci listesi view tarafına gönderilir
        model.addAttribute("ogrenciler", ogrenciler);
        return "liste";
    }
}
