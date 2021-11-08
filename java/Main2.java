import java.util.*;

public class Main2 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String line = in.nextLine();
        String[] nm = line.split("\\s");
        int num_acc = Integer.parseInt(nm[0]); //n-количество счетов
        int manager = Integer.parseInt(nm[1]); //m-количество сотрудников
        int min_account_id = num_acc > manager ? num_acc-manager : 0; 
        int[] account=new int[num_acc];
        int bonus, sum, middle, x, chemp_id, chemp_x, mod;
        double chempion, cur;

        for (int i=0; i < num_acc; i++) //заполнение массива счетов 
        {
            line = in.nextLine();
            account[i] = Integer.parseInt(line);
        }
        in.close();

        sum = Arrays.stream(account).sum();
        if (sum / manager < 1) {
            //System.out.println("Сумма:" + sum);
            System.out.println("0");
            return;
        }
        
        Arrays.sort(account);
        bonus = account[num_acc-1]/manager;
        sum = account[num_acc-1];
        //k = 1;
        chemp_x = 1;
        chemp_id = num_acc-1;
        for (int i = num_acc-2; i >= min_account_id; i--)
        {
            if (account[i] < bonus) break;
            sum = sum + account[i];
            middle = sum / manager;
            chempion = middle;
            for (int j = i; j < num_acc; j++)
            {
                x = account[j] / middle;
                if (x == 0) {
                    chemp_id = j;
                    chemp_x = 1;
                    break;
                }
                mod = account[j] % middle;
                if (mod > 0)x++;
                cur = (middle - mod) / x;
                if (cur < chempion) {
                    chempion = cur;
                    chemp_id = j;
                    chemp_x = x;
                }
            }
            bonus = account[chemp_id] / chemp_x;
        }
        
        System.out.println(bonus);

    }
}
