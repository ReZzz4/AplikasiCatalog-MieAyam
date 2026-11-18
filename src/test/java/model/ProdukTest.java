package model;

import static org.junit.Assert.assertThrows;
import org.junit.Test;

public class ProdukTest {
    @Test
    public void menolakNamaKosongDanHargaNol() {
        assertThrows(IllegalArgumentException.class, () -> new Produk(" ", 12000, "foto.png"));
        assertThrows(IllegalArgumentException.class, () -> new Produk("Mie Ayam", 0, "foto.png"));
    }
}
