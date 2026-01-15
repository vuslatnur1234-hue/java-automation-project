package com.ogrenci.OgrenciOtomasyonu;
// Öğrenciye ait bilgileri ve hesaplamaları tutan sınıf
public class Ogrenci {

    // Öğrencinin temel bilgileri
    private String no;
    private String ad;
    private int vize;
    private int finall;
    private int devamsizlik; // Öğrencinin devamsızlık bilgisi

    // Öğrenci nesnesi oluşturulurken kullanılan kurucu metot
    public Ogrenci(String no, String ad, int vize, int finall, int devamsizlik) {
        this.no = no;
        this.ad = ad;
        this.vize = vize;
        this.finall = finall;
        this.devamsizlik = devamsizlik;
    }

    // Öğrenci bilgilerine erişim sağlayan getter metotları
    public String getNo() { return no; }
    public String getAd() { return ad; }
    public int getVize() { return vize; }
    public int getFinall() { return finall; }

    // Devamsızlık bilgisi için getter ve setter
    public int getDevamsizlik() { return devamsizlik; }
    public void setDevamsizlik(int devamsizlik) { this.devamsizlik = devamsizlik; }

    // Vize ve final notlarına göre ortalama hesaplar
    public double getOrtalama() {
        if (vize == -1 && finall == -1) return 0;
        return vize * 0.4 + finall * 0.6;
    }

    // Öğrencinin geçme, kalma veya devamsızlık durumunu hesaplar
    public String getDurum() {
        if (devamsizlik >= 5) { // Devamsızlık sınırı
            return "Devamsız";
        } else if (getOrtalama() >= 50) {
            return "Geçti";
        } else {
            return "Kaldı";
        }
    }

    // Ortalama ve devamsızlığa göre harf notunu belirler
    public String getHarfNotu() {
        double ort = getOrtalama();
        if (this.devamsizlik >= 5) return "DZ";

        if (ort >= 90) return "A+";
        if (ort >= 80) return "A";
        if (ort >= 70) return "B+";
        if (ort >= 60) return "B";
        if (ort >= 50) return "C";
        if (ort >= 40) return "D";
        return "F";
    }

    // Öğrencinin aldığı ders bilgileri
    private String ders;
    private int akts;
    private String hoca;

    // Ders bilgileri için setter metotları
    public void setDers(String ders) { this.ders = ders; }
    public void setAkts(int akts) { this.akts = akts; }
    public void setHoca(String hoca) { this.hoca = hoca; }

    // Ders bilgilerini görüntülemek için getter metotları
    public String getDers() { return ders; }
    public int getAkts() { return akts; }
    public String getHoca() { return hoca; }
}

