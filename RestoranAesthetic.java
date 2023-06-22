import java.util.*;
import java.util.Stack;

class Menu {
    String name;
    double price;
    public Menu(String name, double price) {
        this.name = name;
        this.price = price;
    }
}

class Order {
    String itemName;
    int quantity;
    public Order(String itemName, int quantity) {
        this.itemName = itemName;
        this.quantity = quantity;
    }
}

public class RestoranAesthetic {
    static String customerName1;
    public static double getMenuPrice(Menu[] menuList, String itemName) {
        for (Menu menu : menuList) {
            if (menu != null && menu.name.equalsIgnoreCase(itemName)) {
                return menu.price;
            }
        }
        return 0.0;
    }

    public static void main(String[] args) {
        Menu[] menuList = new Menu[5];
        menuList[0] = new Menu("Gurame", 20000.0);
        menuList[1] = new Menu("Udang Goreng", 15000.0);
        menuList[2] = new Menu("Cumi Bakar", 17000.0);
        menuList[3] = new Menu("Milo Chocolate", 10000.0);
        menuList[4] = new Menu("Lemon Tea", 10000.0);

        LinkedList<Order> orderList = new LinkedList<>();
        Stack<Order> orderStack = new Stack<>();
        Queue<Order> paymentQueue = new LinkedList<>();

        Scanner scanner = new Scanner(System.in);
        int choice;
        double change = 0.0;

        do {
            System.out.println("\nSilakan Pilih Opsi:");
            System.out.println("1. Lihat menu makanan");
            System.out.println("2. Tambah pesanan");
            System.out.println("3. Edit pesanan terakhir");
            System.out.println("4. Pembayaran");
            System.out.println("5. Cetak struk pembelian");
            System.out.println("6. Keluar");
            System.out.print("Pilihan Anda: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("\n======= Menu Makanan: ========");
                    System.out.println("==============================");
                    for (int i = 0; i < menuList.length; i++) {
                        if (menuList[i] != null) {
                            System.out.println(i + 1 + ". " + menuList[i].name + " - Rp" + menuList[i].price);
                        }
                    }
                    System.out.println("==============================");
                    break;

                case 2:
                    System.out.print("\nMasukkan nomor menu yang dipilih        : ");
                    int selectedMenu = scanner.nextInt();
                    scanner.nextLine();

                    if (selectedMenu >= 1 && selectedMenu <= 5 && menuList[selectedMenu - 1] != null) {
                        System.out.print("Masukkan jumlah pesanan                 : ");
                        int quantity = scanner.nextInt();
                        scanner.nextLine();

                        System.out.print("Masukkan nama pelanggan (wajib sama)    : ");
                        customerName1 = scanner.nextLine();

                        Order order = new Order(menuList[selectedMenu - 1].name, quantity);
                        orderList.add(order);
                        orderStack.push(order);
                        System.out.println("Pesanan " + order.itemName + " ditambahkan dengan jumlah " + order.quantity);
                    } else {
                        System.out.println("Menu tidak valid.");
                    }
                    break;

                case 3:
                    if (!orderStack.isEmpty()) {
                        Order lastOrder = orderStack.peek();
                        System.out.println("\nPesanan terakhir: " + lastOrder.itemName + " - Jumlah: " + lastOrder.quantity);
                        System.out.print("Masukkan nomor menu yang baru           : ");
                        int newMenuNumber = scanner.nextInt();
                        scanner.nextLine();

                        if (newMenuNumber >= 1 && newMenuNumber <= 5 && menuList[newMenuNumber - 1] != null) {
                            System.out.print("Masukkan jumlah pesanan baru            : ");
                            int newQuantity = scanner.nextInt();
                            scanner.nextLine();

                            if (newQuantity > 0) {
                                lastOrder.itemName = menuList[newMenuNumber - 1].name;
                                lastOrder.quantity = newQuantity;
                                System.out.println("Pesanan berhasil diubah menjadi " + menuList[newMenuNumber - 1].name + " dengan jumlah " + newQuantity + " item.");
                            } else {
                                System.out.println("Jumlah pesanan tidak valid.");
                            }
                        } else {
                            System.out.println("Menu tidak valid.");
                        }
                    }
                    else {
                        System.out.println("Tidak ada pesanan yang dapat diubah.");
                    }
                    break;

                case 4:
                    System.out.println("\nPesanan yang telah dimasukkan   :");
                    double totalPayment = 0.0;

                    for (Order order : orderList) {
                        System.out.println("Pesanan                         : " + order.itemName + "  Jumlah: " + order.quantity);
                        totalPayment += getMenuPrice(menuList, order.itemName) * order.quantity;
                    }

                    System.out.println("Total harga                     : Rp" + totalPayment);
                    System.out.print("Masukkan jumlah pembayaran      : Rp" );
                    double payment = scanner.nextDouble();
                    scanner.nextLine();

                    if (payment < totalPayment) {
                        System.out.println("Jumlah pembayaran tidak mencukupi.");
                    } else {
                        change = payment - totalPayment;
                        System.out.println("Kembalian                       : Rp" + change);
                        paymentQueue.addAll(orderList);
                        orderList.clear();
                        orderStack.clear();
                    }
                    break;

                case 5:
                    System.out.println("\n========= Struk Pembelian: ============");
                    System.out.println("=======================================");
                    System.out.println("Item             Quantity    Price");
                    System.out.println("=======================================");
                    double totalPaymentPrint = 0.0;
                    for (Order order : paymentQueue) {
                        double itemPrice = getMenuPrice(menuList, order.itemName);
                        double totalPrice = itemPrice * order.quantity;
                        System.out.printf("%-16s %-10d Rp%.2f%n", order.itemName, order.quantity, totalPrice);
                        totalPaymentPrint += totalPrice;
                    }
                    System.out.println("=======================================");
                    System.out.printf("Nama Pelanggan   :          " + customerName1);
                    System.out.printf("\nTotal Payment    :          Rp%.2f%n", totalPaymentPrint);
                    System.out.printf("Jumlah Pembayaran:          Rp%.2f%n", totalPaymentPrint + change);
                    System.out.printf("Kembalian        :          Rp%.2f%n", change);
                    System.out.println("=======================================");
                    System.out.println(" SILAHKAN LAKUKAN PEMBAYARAN DI KASIR! ");
                    System.out.println("=========== TUNAI/NON TUNAI ===========");
                    break;

                case 6:
                    System.out.println("\nTerima kasih! Program telah berakhir.");
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                    break;
            }
        } while (choice != 6);
    }
}