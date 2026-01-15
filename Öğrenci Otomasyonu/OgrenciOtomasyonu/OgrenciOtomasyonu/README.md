# Öğrenci Otomasyonu Projesi

Bu proje, Spring Boot kullanılarak geliştirilmiş bir **Öğrenci Otomasyon Sistemi**dir.
Akademisyenlerin öğrencileri yönetmesini, not girişlerini yapmasını ve başarı durumlarını
takip etmesini sağlar.

---

## Projenin Amacı

Bu projenin amacı;
- Öğrenci bilgilerini dijital ortamda tutmak
- Not ve devamsızlık takibini yapmak
- Öğrencilerin geçme / kalma durumlarını otomatik hesaplamak
- Genel başarı durumunu grafik ile göstermek

---

## Proje Özellikleri

- Kullanıcı giriş sistemi
- Şifre yenileme işlemi
- Öğrenci ekleme
- Öğrenci silme
- Öğrenci bilgisi düzenleme
- Vize, final ve devamsızlık girişi
- Ortalama ve harf notu hesaplama
- Devamsızlığa göre kalma durumu
- Öğrenci başarı grafiği (pasta grafik)

---

## Kullanılan Teknolojiler

- Java
- Spring Boot (MVC)
- Thymeleaf
- SQLite
- HTML
- CSS
- JavaScript
- Chart.js

---

## Projeyi Çalıştırma

1. Projeyi IntelliJ IDEA veya Eclipse ile açınız.
2. Maven bağımlılıklarının otomatik olarak yüklenmesini bekleyiniz.
3. Projeyi çalıştırınız.
4. Tarayıcıdan aşağıdaki adresi açınız:

http://localhost:8080/


---

## Giriş Bilgileri

- Kullanıcı Adı: `akademisyen`
- Şifre: `1234`

Şifre, uygulama içerisindeki **Şifre Yenileme** ekranından değiştirilebilir.

---

## Veritabanı Bilgileri

- Veritabanı: **SQLite**
- Dosya adı: `ogrenci.db`
- Uygulama ilk çalıştırıldığında gerekli tablo otomatik olarak oluşturulur.
- Eksik sütunlar varsa silme işlemi olmadan otomatik eklenir.

---

## Not Hesaplama Sistemi

- Vize notu: %40
- Final notu: %60
- Ortalama ≥ 50 → Geçti
- Devamsızlık ≥ 5 → Devamsız (Otomatik kaldı)

---

## Grafik Sistemi

Ana menüde yer alan pasta grafik ile:
- Geçen öğrenci sayısı
- Kalan öğrenci sayısı
- Devamsız kalan öğrenci sayısı
  görsel olarak gösterilmektedir.


