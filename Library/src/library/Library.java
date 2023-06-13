/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package library;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author ASUS
 */

class Person {
    protected String name;

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void displayInfo() {
        System.out.println("Nama        : " + name);
    }
}

class Member extends Person {
    private int idMember;

    public Member(String name) {
        super(name);
        this.idMember = generateIdMember();
    }

    private int generateIdMember() {
        Random random = new Random();
        int randomNumber = random.nextInt(9000) + 1000;
        return randomNumber;
    }

    public int getIdMember() {
        return idMember;
    }

    public void displayInfo() {
        System.out.println("Nama        : " + name);
        System.out.println("ID Member   : " + idMember);
    }
}

public class Library {
    static ArrayList<Member> accounts = new ArrayList<>();
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        
        do {
            System.out.println("'Selamat Datang di Perpustakaan Kota'");
            System.out.println("1. Registrasi Member");
            System.out.println("2. Peminjaman Buku");
            System.out.println("3. Pengembalian Buku");
            System.out.println("0. Exit");
            System.out.print("Masukkan pilihan Anda: ");
            choice = scanner.nextInt();
            System.out.println();
            
            switch (choice) {
                case 1:
                    registrasiMember(scanner);
                    break;
                case 2:
                    peminjamanBuku(scanner);
                    break;
                case 3:
                    pengembalianBuku(scanner);
                    break;
                case 0:
                    System.out.println("Terima Kasih!");;
                    break;
                default:
                    System.out.println("Pilhan tidak valid. Silahkan coba kembali.");
                    break;
            }
            System.out.println();
        }
        while (choice != 0);
    }

    private static void registrasiMember(Scanner scanner) {
        System.out.print("Masukkan nama Anda: ");
        scanner.nextLine();
        String name = scanner.nextLine();

        Person member = new Member(name);
        accounts.add((Member) member);

        System.out.println();
        member.displayInfo();
    }

    private static void peminjamanBuku(Scanner scanner) {
        System.out.print("Masukkan ID member Anda   : ");
        int memberId = scanner.nextInt();
        Member member = getMember(memberId);

        if (member == null) {
            System.out.println("ID member tidak ditemukan. Pastikan Anda sudah melakukan registrasi.");
            return;
        }

        System.out.print("Masukkan judul buku       : ");
        scanner.nextLine();
        String judulBuku = scanner.nextLine();

        LocalDate peminjamanDate = LocalDate.now();
        LocalDate pengembalianDate = peminjamanDate.plusDays(7);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("Tanggal Peminjaman        : " + peminjamanDate.format(formatter));
        System.out.println("Tanggal Pengembalian      : " + pengembalianDate.format(formatter));
        System.out.println("Mohon mengembalikan buku tepat waktu. Anda akan dikenakan denda jika terlambat.");
    }

    private static void pengembalianBuku(Scanner scanner) {
        System.out.print("Masukkan ID member Anda   : ");
        int memberId = scanner.nextInt();
        Member member = getMember(memberId);

        if (member == null) {
            System.out.println("ID member tidak ditemukan. Pastikan Anda sudah melakukan registrasi.");
            return;
        }

        System.out.print("Masukkan judul buku       : ");
        scanner.nextLine();
        String judulBuku = scanner.nextLine();
        
        LocalDate pengembalianDate = LocalDate.now();
        LocalDate peminjamanDate = getPeminjamanDate(memberId, judulBuku);
        int denda = calculateDenda(pengembalianDate, peminjamanDate);

        if (denda > 0) {
            System.out.println("Anda mengembalikan buku terlambat " + denda + " hari.");
            System.out.println("Denda yang harus dibayarkan: Rp " + denda * 2000);
        } else {
            System.out.println("Terima kasih sudah mengembalikan buku tepat waktu.");
            System.out.println("Ditunggu kedatangannya kembali di Perpustakaan Kota.");
        }        
    }
    
private static LocalDate getPeminjamanDate(int memberId, String judulBuku) {
    Random random = new Random();
    int days = random.nextInt(14);
    return LocalDate.now().minusDays(days);
}

private static int calculateDenda(LocalDate pengembalianDate, LocalDate peminjamanDate) {
    int daysLate = (int) ((int) pengembalianDate.toEpochDay() - peminjamanDate.toEpochDay() - 7);
    return Math.max(0, daysLate);
}    
    
    private static Member getMember(int MemberId) {
        for (Member member : accounts) {
            if (member.getIdMember() == MemberId) {
                return member;
            }
        }
        return null;
    }
}
