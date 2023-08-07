package com.uygulamam.carbon;

public class Kullanici {
    private String isim;
    private String soyisim;

    public Kullanici() {
        // Empty constructor needed for Firebase
    }

    public Kullanici(String isim, String soyisim) {
        this.isim = isim;
        this.soyisim = soyisim;
    }

    public String getIsim() {
        return isim;
    }

    public String getSoyisim() {
        return soyisim;
    }
}
