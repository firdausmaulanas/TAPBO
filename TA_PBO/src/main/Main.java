package main;
import authentication.Admin;
import hotelmanagement.Kamar;
import hotelmanagement.Reservasi;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Data admin untuk login
        Admin admin = new Admin("admin", "password");

        // Inisialisasi data kamar
        ArrayList<Kamar> daftarKamar = new ArrayList<>();
        daftarKamar.add(new Kamar(101, "Single Bed", 100));
        daftarKamar.add(new Kamar(102, "Double Bed", 150));
        daftarKamar.add(new Kamar(103, "Suite", 200));

        // Inisialisasi data reservasi
        ArrayList<Reservasi> daftarReservasi = new ArrayList<>();

        // Login admin
        System.out.print("Masukkan username: ");
        String enteredUsername = scanner.nextLine();
        System.out.print("Masukkan password: ");
        String enteredPassword = scanner.nextLine();

        if (admin.login(enteredUsername, enteredPassword)) {
            System.out.println("Login berhasil!\n");

            // Menu utama
            int pilihan;
            do {
                System.out.println("===== MENU UTAMA =====");
                System.out.println("1. Lihat Informasi Kamar");
                System.out.println("2. Reservasi Kamar (Check-in)");
                System.out.println("3. Lihat Detail Reservasi");
                System.out.println("4. Check-out ");
                System.out.println("5. Lihat Jumlah Kamar Tersedia");
                System.out.println("6. Lihat Data Tamu");
                System.out.println("0. Keluar");
                System.out.print("Pilih menu: ");
                pilihan = scanner.nextInt();

                switch (pilihan) {
                    case 1:
                        // Lihat Informasi Kamar
                        for (Kamar kamar : daftarKamar) {
                            kamar.displayInfo();
                        }
                        break;
                    case 2:
                        // Reservasi Kamar (Check-in)
                        System.out.print("Masukkan nama tamu: ");
                        scanner.nextLine(); // Consume newline
                        String namaTamu = scanner.nextLine();
                        System.out.print("Masukkan nomor kamar: ");
                        int nomorKamar = scanner.nextInt();
                        System.out.print("Masukkan jenis pembayaran: ");
                        scanner.nextLine(); // Consume newline
                        String jenisPembayaran = scanner.nextLine();

                        Kamar kamarDipesan = cariKamar(daftarKamar, nomorKamar);
                        if (kamarDipesan != null && kamarDipesan.isTersedia()) {
                            kamarDipesan.setTersedia(false);
                            daftarReservasi.add(new Reservasi(namaTamu, nomorKamar, jenisPembayaran));
                            System.out.println("Reservasi berhasil!");
                        } else {
                            System.out.println("Nomor kamar tidak tersedia atau salah!");
                        }
                        break;
                    case 3:
                        // Lihat Detail Reservasi
                        System.out.print("Masukkan nomor kamar: ");
                        int nomorKamarDetail = scanner.nextInt();
                        Kamar kamarDetail = cariKamar(daftarKamar, nomorKamarDetail);
                        if (kamarDetail != null) {
                            for (Reservasi reservasi : daftarReservasi) {
                                if (reservasi.getNoKamar() == nomorKamarDetail) {
                                    reservasi.displayDetail(kamarDetail);
                                    break;
                                }
                            }
                        } else {
                            System.out.println("Nomor kamar tidak ditemukan!");
                        }
                        break;
                    case 4:
                        // Check-out 
                        System.out.print("Masukkan nomor kamar: ");
                        int nomorKamarCheckout = scanner.nextInt();
                        Kamar kamarCheckout = cariKamar(daftarKamar, nomorKamarCheckout);
                        if (kamarCheckout != null && !kamarCheckout.isTersedia()) {
                            kamarCheckout.setTersedia(true);
                            System.out.println("Check-out berhasil!");
                        } else {
                            System.out.println("Nomor kamar tidak valid atau kamar sudah tersedia!");
                        }
                        break;
                    case 5:
                        // Lihat Jumlah Kamar Tersedia
                        int jumlahKamarTersedia = hitungKamarTersedia(daftarKamar);
                        System.out.println("Jumlah kamar tersedia: " + jumlahKamarTersedia);
                        break;
                    case 6:
                        // Lihat Data Tamu
                        for (Reservasi reservasi : daftarReservasi) {
                            System.out.println("Nama Tamu: " + reservasi.getNamaTamu());
                            System.out.println("Nomor Kamar: " + reservasi.getNoKamar());
                            System.out.println("Jenis Pembayaran: " + reservasi.getJenisPembayaran());
                            System.out.println("-----------------------------");
                        }
                        break;
                    case 0:
                        // Keluar dari program
                        System.out.println("Terima kasih!");
                        break;
                    default:
                        System.out.println("Pilihan tidak valid!");
                        break;
                }

            } while (pilihan != 0);

        } else {
            System.out.println("Login gagal! Username atau password salah.");
        }
    }

    // Metode untuk mencari objek Kamar berdasarkan nomor kamar
    private static Kamar cariKamar(ArrayList<Kamar> daftarKamar, int nomorKamar) {
        for (Kamar kamar : daftarKamar) {
            if (kamar.getNoKamar() == nomorKamar) {
                return kamar;
            }
        }
        return null;
    }

    // Metode untuk menghitung jumlah kamar yang tersedia
    private static int hitungKamarTersedia(ArrayList<Kamar> daftarKamar) {
        int count = 0;
        for (Kamar kamar : daftarKamar) {
            if (kamar.isTersedia()) {
                count++;
            }
        }
        return count;
    }
}