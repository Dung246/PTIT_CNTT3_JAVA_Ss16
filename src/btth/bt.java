package btth;
import java.util.*;

/*
 MINI PROJECT - PET KINGDOM
 Console Application
*/

public class bt{

    // ======================= ENTITY =======================

    static class Pet {
        private String id;
        private String name;
        private String species;
        private double price;

        public Pet(String id, String name, String species, double price) {
            this.id = id;
            this.name = name;
            this.species = species;
            this.price = price;
        }

        public String getId() { return id; }
        public String getName() { return name; }

        @Override
        public String toString() {
            return "Pet{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", species='" + species + '\'' +
                    ", price=" + price +
                    '}';
        }
    }

    static class Customer {
        private String id;
        private String name;
        private String phone;

        public Customer(String id, String name, String phone) {
            this.id = id;
            this.name = name;
            this.phone = phone;
        }

        public String getId() { return id; }

        @Override
        public String toString() {
            return "Customer{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", phone='" + phone + '\'' +
                    '}';
        }
    }

    // ======================= MANAGER - GENERIC =======================

    // Quản lý kho thú cưng (List)
    static class PetManager<T extends Pet> {
        private List<T> petList = new ArrayList<>();

        public void addPet(T pet) {
            petList.add(pet);
        }

        public void removePet(String id) {
            petList.removeIf(p -> p.getId().equals(id));
        }

        public T findPet(String id) {
            for (T pet : petList) {
                if (pet.getId().equals(id)) return pet;
            }
            return null;
        }

        public void displayAll() {
            if (petList.isEmpty()) {
                System.out.println("Kho trống.");
            } else {
                petList.forEach(System.out::println);
            }
        }
    }

    // Quản lý khách hàng (Map đảm bảo duy nhất)
    static class CustomerManager<T extends Customer> {
        private Map<String, T> customerMap = new HashMap<>();

        public boolean addCustomer(T customer) {
            if (customerMap.containsKey(customer.getId())) {
                return false;
            }
            customerMap.put(customer.getId(), customer);
            return true;
        }

        public T findCustomer(String id) {
            return customerMap.get(id);
        }
    }

    // Quản lý Spa (Queue FIFO)
    static class SpaManager<T extends Pet> {
        private Queue<T> queue = new LinkedList<>();

        public void addToQueue(T pet) {
            queue.offer(pet);
        }

        public T serveNext() {
            return queue.poll();
        }

        public void displayQueue() {
            if (queue.isEmpty()) {
                System.out.println("Không có thú cưng chờ.");
            } else {
                queue.forEach(System.out::println);
            }
        }
    }

    // Nhật ký & Undo (Stack LIFO)
    static class ActionLogger {
        private Stack<String> stack = new Stack<>();

        public void log(String action) {
            stack.push(action);
        }

        public void undo() {
            if (!stack.isEmpty()) {
                System.out.println("Undo: " + stack.pop());
            } else {
                System.out.println("Không có hành động để hoàn tác.");
            }
        }

        public void showHistory() {
            if (stack.isEmpty()) {
                System.out.println("Chưa có thao tác nào.");
            } else {
                System.out.println("=== Nhật ký ===");
                stack.forEach(System.out::println);
            }
        }
    }

    // ======================= MAIN =======================

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        PetManager<Pet> petManager = new PetManager<>();
        CustomerManager<Customer> customerManager = new CustomerManager<>();
        SpaManager<Pet> spaManager = new SpaManager<>();
        ActionLogger logger = new ActionLogger();

        int choice;

        do {
            System.out.println("\n===== PET KINGDOM =====");
            System.out.println("1. Thêm thú cưng");
            System.out.println("2. Hiển thị kho");
            System.out.println("3. Tìm thú cưng");
            System.out.println("4. Xóa thú cưng");
            System.out.println("5. Đăng ký khách hàng");
            System.out.println("6. Tra cứu khách hàng");
            System.out.println("7. Thêm thú cưng vào Spa");
            System.out.println("8. Phục vụ Spa");
            System.out.println("9. Xem hàng đợi Spa");
            System.out.println("10. Xem nhật ký");
            System.out.println("11. Undo");
            System.out.println("0. Thoát");
            System.out.print("Chọn: ");
            choice = Integer.parseInt(sc.nextLine());

            switch (choice) {

                case 1:
                    System.out.print("ID: ");
                    String id = sc.nextLine();
                    System.out.print("Tên: ");
                    String name = sc.nextLine();
                    System.out.print("Loài: ");
                    String species = sc.nextLine();
                    System.out.print("Giá: ");
                    double price = Double.parseDouble(sc.nextLine());

                    Pet pet = new Pet(id, name, species, price);
                    petManager.addPet(pet);
                    logger.log("Thêm thú cưng: " + name);
                    break;

                case 2:
                    petManager.displayAll();
                    break;

                case 3:
                    System.out.print("Nhập ID: ");
                    Pet found = petManager.findPet(sc.nextLine());
                    System.out.println(found != null ? found : "Không tìm thấy.");
                    break;

                case 4:
                    System.out.print("Nhập ID cần xóa: ");
                    String removeId = sc.nextLine();
                    petManager.removePet(removeId);
                    logger.log("Xóa thú cưng ID: " + removeId);
                    break;

                case 5:
                    System.out.print("ID: ");
                    String cid = sc.nextLine();
                    System.out.print("Tên: ");
                    String cname = sc.nextLine();
                    System.out.print("SĐT: ");
                    String phone = sc.nextLine();

                    Customer customer = new Customer(cid, cname, phone);
                    if (customerManager.addCustomer(customer)) {
                        logger.log("Thêm khách hàng: " + cname);
                    } else {
                        System.out.println("Khách hàng đã tồn tại!");
                    }
                    break;

                case 6:
                    System.out.print("Nhập ID khách hàng: ");
                    Customer c = customerManager.findCustomer(sc.nextLine());
                    System.out.println(c != null ? c : "Không tìm thấy.");
                    break;

                case 7:
                    System.out.print("Nhập ID thú cưng: ");
                    Pet spaPet = petManager.findPet(sc.nextLine());
                    if (spaPet != null) {
                        spaManager.addToQueue(spaPet);
                        logger.log("Đưa vào Spa: " + spaPet.getName());
                    } else {
                        System.out.println("Không tìm thấy thú cưng.");
                    }
                    break;

                case 8:
                    Pet serving = spaManager.serveNext();
                    if (serving != null) {
                        System.out.println("Đang phục vụ: " + serving);
                        logger.log("Phục vụ Spa: " + serving.getName());
                    } else {
                        System.out.println("Không có thú cưng chờ.");
                    }
                    break;

                case 9:
                    spaManager.displayQueue();
                    break;

                case 10:
                    logger.showHistory();
                    break;

                case 11:
                    logger.undo();
                    break;
            }

        } while (choice != 0);

        System.out.println("Thoát chương trình.");
    }
}
