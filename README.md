# WARUNGKU — Kasir Java Swing
WARUNGKU adalah aplikasi kasir berbasis desktop yang dikembangkan menggunakan Java Swing dan MySQL. Aplikasi ini dirancang untuk membantu kasir dalam mencatat daftar barang yang dibeli, menghitung total harga, menentukan jumlah uang yang dibayarkan, serta menghitung kembalian secara otomatis. Pengguna dapat melakukan login terlebih dahulu untuk mengakses halaman utama kasir, lalu memilih metode pembayaran, menambah barang ke daftar transaksi, dan menyimpan transaksi ke database. Aplikasi juga menyediakan fitur riwayat transaksi yang memudahkan kasir dalam melihat data pembelian sebelumnya. Tampilan aplikasi dibuat lebih modern dengan konsep warna yang menarik serta layout yang lebih rapi agar lebih nyaman digunakan. Selain itu, aplikasi dilengkapi dengan validasi input agar proses transaksi menjadi lebih aman dan minim kesalahan.

Teknologi yang digunakan meliputi Java, Java Swing untuk antarmuka pengguna, JDBC untuk koneksi database, dan MySQL/MariaDB sebagai penyimpanan data. Program ini dibuat untuk memudahkan proses transaksi penjualan di warung atau usaha kecil menengah dengan sistem yang sederhana namun fungsional.

## Setup database
1. Start MySQL or MariaDB.
2. Import `src/Database/kasir.sql` using phpMyAdmin or the MySQL command line. The script creates the `kasir_db` database and its tables.
3. The sample login in the SQL script is `agus` / `admin`.

The JDBC connection defaults to `localhost:3306`, database `kasir_db`, user `root`, and an empty password. Override these defaults with JVM properties if needed:

```text
-Dkasir.db.url=jdbc:mysql://localhost:3306/kasir_db?useSSL=false&serverTimezone=UTC
-Dkasir.db.username=root
-Dkasir.db.password=your-password
```

The project requires MySQL Connector/J 8.x on its classpath. In NetBeans, add the Connector/J JAR under **Project Properties → Libraries**. Avoid committing local absolute paths to the driver JAR.

## Build and run
Open `ProjectPBOLast/BismillahLast/BismillahLast` as an existing NetBeans/Ant project, ensure the Connector/J driver is on the classpath, then run `Main`.
