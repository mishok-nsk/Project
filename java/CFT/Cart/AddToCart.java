import java.util.Scanner;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;

public class AddToCart {
    
    private static class Order {
        ArrayList<String> products;
        ArrayList<Double> costs;
        ArrayList<Integer> num;
        double sum;
        int count;
        int orderNum = 112233;
        String name = "Фамилия Имя Отчество";
        Date curdate;

        public Order() {
            sum = 0.0;
            count = 0;
            curdate = new Date();

        }
        public boolean isEmpty() {
            return count == 0;
        }

        public void add(String product, int nnum, double cost) {
            if (isEmpty()) {
                products = new ArrayList<>();
                costs = new ArrayList<>();
                num = new ArrayList<>();
                products.add(product);
                costs.add(cost);
                num.add(nnum);
                count = 1;
                sum = cost * nnum;
            }
            else {
                int left = 0;
                int right = count;
                int mid = 0;
                String midval = "";

                while(left != right) {
                    mid = left + (right - left) / 2;
                    midval = products.get(mid);
                    if (midval.equalsIgnoreCase(product)) break;
                    if (midval.compareToIgnoreCase(product) > 0) 
                        right = mid;
                    else 
                        left = mid+1;
                }
                
                if (left == right) {
                    products.add(left, product);
                    costs.add(left, cost);
                    num.add(left, nnum);
                    count++;
                    sum += cost * nnum;
                }
                else {
                    num.set(mid, num.get(mid) + nnum);
                    sum += cost * nnum;
                }
            }

        }

        public void print() {
            if (isEmpty()) {
                System.out.println("--------------------------------------------------------");    
                System.out.println("Корзина пуста.");
                System.out.println("--------------------------------------------------------");
            }
            else {
                System.out.println("--------------------------------------------------------");
                System.out.printf("Заказ №%1$d %2$s %3$td.%3$tm.%3$tY\n\n", orderNum, name, curdate);
                System.out.printf("%-20s %-10s %-10s %-10s\n", "Название", "Цена", "Количество", "Сумма");
                for(int i=0; i < count; i++) {
                    System.out.printf("%-20s %-10.2f %-10d %-10.2f\n", products.get(i), costs.get(i), num.get(i), num.get(i)*costs.get(i));
                }
                System.out.println();
                System.out.printf("%s %.2f\n", "Итого:", sum);
                System.out.println("--------------------------------------------------------\n");
            }
        }
    }
    private static void printmenu() {
        
        System.out.println("Выберите действие:");
        System.out.println("1 - Добавить товар в корзину;");
        System.out.println("2 - Вывести список товара;");
        System.out.println("3 - Вывести содержимое корзины;");
        System.out.println("0 - Выход.");
        
    }
    private static void printcatalog() throws Exception {
        FileReader fr = new FileReader("src/catalog.txt");
        Scanner in = new Scanner(fr);
        String[] inline;
        System.out.println("--------------------------------------------------------");
        while(in.hasNextLine()) {
            inline = in.nextLine().split(" ");
            System.out.printf("%-20s %-20s\n", inline[0], inline[1]);
        }
        System.out.println("--------------------------------------------------------\n");
        in.close();
        fr.close();
    }

    public static boolean checkinput(String[] input) {
        if (input.length < 2) return false;
        try {
            Integer.parseInt(input[1]);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static double findproduct(String product) throws Exception {
        FileReader fr = new FileReader("src/catalog.txt");
        Scanner in = new Scanner(fr);
        String[] catalogProduct;
        catalogProduct = in.nextLine().split(" ");
        while (in.hasNextLine()) {
            catalogProduct = in.nextLine().split(" ");
            if (product.equalsIgnoreCase(catalogProduct[0])) {
                in.close();
                return Double.parseDouble(catalogProduct[1]);
            }
        }
        in.close();
        fr.close();
        return 0.0;
    }
    public static void main(String[] arg) throws Exception {
        FileReader fr;
        try {
            fr = new FileReader("src/catalog.txt");
        } 
        catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.out.println("Каталог отсутсвует.");
            return;
        }
        fr.close();       
        printmenu();    
        Scanner in = new Scanner(System.in);
        String choise = in.nextLine();
        String[] product;
        Order order = new Order();
        double cost;

        while (!choise.equals("0")) {
            switch (choise) {
                case ("1") :
                    System.out.println("--------------------------------------------------------");
                    System.out.println("Введите наименование и количество товара через пробел:");
                    product = in.nextLine().split(" ");
                    if(checkinput(product)) {
                        cost = findproduct(product[0]);
                        if (cost != 0.0) {
                            order.add(product[0], Integer.parseInt(product[1]), cost);
                            System.out.println(product[0] + " в количестве " + product[1] + "шт. добавлен(а) вкорзину.");
                        }
                        else System.out.println("Товар " + product[0] + " не найден в каталоге.");
                    }
                    else System.out.println("Некорректный ввод.");
                    System.out.println("--------------------------------------------------------\n");
                    printmenu();
                    choise = in.nextLine();
                    break;
                case ("2") :
                    printcatalog();
                    printmenu();
                    choise = in.nextLine();
                    break;
                case ("3") :
                    order.print();
                    printmenu();
                    choise = in.nextLine();
                    break;
                case ("0") :
                    break;
                default :
                    System.out.println("Некорректный ввод. Повторите попытку.");
                    System.out.println("--------------------------------------------------------\n");
                    printmenu();
                    choise = in.nextLine();
            }
        }
        //fr.close();
        in.close();
        System.out.println("До свидания!");
    }
}
