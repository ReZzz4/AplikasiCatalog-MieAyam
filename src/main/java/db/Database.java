package db;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    private static final Path FILE_DATABASE = Path.of(
            System.getProperty("user.dir"), "data", "katalog.db"
    ).toAbsolutePath().normalize();

    public static Connection bukaKoneksi() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:" + FILE_DATABASE);
    }

    public static void prepareDatabase() throws IOException, SQLException {
        Files.createDirectories(FILE_DATABASE.getParent());
        System.out.println("DATABASE AKTIF: " + FILE_DATABASE);
        String sql = "CREATE TABLE IF NOT EXISTS produk ("
                + "id INTEGER PRIMARY KEY, "
                + "nama TEXT NOT NULL CHECK(length(trim(nama)) > 0), "
                + "harga INTEGER NOT NULL CHECK(harga > 0), "
                + "foto TEXT NOT NULL CHECK(length(trim(foto)) > 0))";

        try (
            Connection koneksi = bukaKoneksi();  
            Statement perintah = koneksi.createStatement()) {
            // Jalankan SQL yang sudah disimpan dalam variabel sql.
            perintah.executeUpdate(sql);
            tambahDataAwal(perintah, "Mie Ayam Original", 12000, "mie-ayam-original.png");
            tambahDataAwal(perintah, "Mie Ayam Bakso", 16000, "mie-ayam-bakso.png");
            tambahDataAwal(perintah, "Mie Ayam Cakar", 18000, "mie-ayam-cakar.png");
            tambahDataAwal(perintah, "Pangsit Goreng", 5000, "pangsit-goreng.png");
        }
    }

    private static void tambahDataAwal(Statement perintah, String nama, long harga, String foto) throws SQLException {
        String sql = "INSERT INTO produk (nama, harga, foto) SELECT '" + nama + "', " + harga
                + ", '" + foto + "' WHERE NOT EXISTS (SELECT 1 FROM produk WHERE nama = '" + nama + "')";
        perintah.executeUpdate(sql);
    }
}
