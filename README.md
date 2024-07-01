# Zerowaste

Zerowaste adalah layanan REST API backend yang dirancang untuk sistem jual beli bahan pokok rumah tangga seperti beras, gula, deterjen, dan sampo, dengan menggunakan sistem refill. Sistem ini memungkinkan pelanggan untuk membeli produk-produk tersebut dalam wadah yang dapat digunakan kembali, sehingga membantu mengurangi limbah kemasan sekali pakai.

## Teknologi yang Digunakan

- **Backend Framework**: Java Spring Boot
- **Database**: PostgreSQL
- **Payment Gateway**: Xendit

## Fitur Utama

1. **Manajemen Produk**: CRUD (Create, Read, Update, Delete) produk bahan pokok rumah tangga.
2. **Sistem Refill**: Pelanggan dapat membeli produk dalam wadah yang dapat diisi ulang.
3. **Integrasi Payment Gateway**: Pembayaran dilakukan melalui Xendit untuk memastikan transaksi yang aman dan mudah.
4. **Autentikasi dan Otorisasi**: Sistem login dan hak akses pengguna untuk keamanan data.
5. **Laporan Penjualan**: Pelaporan penjualan dan statistik untuk membantu analisis bisnis.

## Instalasi

1. Clone repositori ini:
   ```bash
   git clone https://github.com/username/zerowaste.git
   ```
2. Masuk ke direktori project:
   ```bash
   cd zerowaste
   ```
3. Konfigurasikan database PostgreSQL di file `application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/zerowaste
   spring.datasource.username=your_db_username
   spring.datasource.password=your_db_password
   spring.jpa.hibernate.ddl-auto=update
   ```
4. Konfigurasikan Xendit payment gateway di file `application.properties`:
   ```properties
   xendit.apiKey=your_xendit_api_key
   ```
5. Jalankan aplikasi:
   ```bash
   ./mvnw spring-boot:run
   ```

## Dokumentasi API

Setelah menjalankan aplikasi, dokumentasi API dapat diakses melalui endpoint berikut:
```
http://localhost:8080/swagger-ui.html
```

## Struktur Proyek

Berikut adalah struktur dasar proyek Zerowaste:
```
zerowaste/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           └── zerowaste/
│   │   │               ├── controller/
│   │   │               ├── model/
│   │   │               ├── repository/
│   │   │               └── service/
│   │   ├── resources/
│   │   │   ├── application.properties
│   │   │   └── static/
│   │   └── webapp/
│   └── test/
│       └── java/
│           └── com/
│               └── example/
│                   └── zerowaste/
├── .gitignore
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

## Kontribusi

Jika Anda ingin berkontribusi pada proyek ini, silakan ikuti langkah-langkah berikut:

1. Fork repositori ini
2. Buat branch fitur (`git checkout -b fitur-anda`)
3. Commit perubahan Anda (`git commit -am 'Menambahkan fitur'`)
4. Push ke branch (`git push origin fitur-anda`)
5. Buat Pull Request

## Lisensi

Project ini dilisensikan di bawah [MIT License](LICENSE).

```

Pastikan untuk mengganti `https://github.com/username/zerowaste.git` dengan URL repositori GitHub yang sebenarnya. Jika ada perubahan lain yang diperlukan sesuai dengan detail proyekmu, silakan lakukan penyesuaian yang diperlukan.
