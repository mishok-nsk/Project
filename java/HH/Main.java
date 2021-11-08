import java.util.*;

public class Main {

    public static class BonusCalculate {
        private List<Double> deviation;
        private List<Integer> numOfBonus;
        private List<Integer> accounts;
        private final double middle;
        private int bonus;
        private int totalNumBonus;
        private final int managers;
        private int size;
        private int idmaxbonus;

        public BonusCalculate(List<Integer> accounts_in, int managers_in, double middle_in) {
            int num;
            accounts = accounts_in;
            numOfBonus = new ArrayList<>();
            deviation = new ArrayList<>();
            size = accounts.size();
            managers = managers_in;
            middle = middle_in;
            totalNumBonus = 0;

            for ( int acc : accounts) {
                //float fnum = acc / middle;
                num =(int)(acc / middle) + 1;
                numOfBonus.add(num);
                totalNumBonus += num;
                deviation.add((middle - acc % middle) / num );
            }
            sort(0, size - 1);
            Calc();
        }

        private void exch(int i, int j) {
            int x;
            double d;
            d = deviation.get(i);
            deviation.set(i, deviation.get(j));
            deviation.set(j, d);

            x = numOfBonus.get(i);
            numOfBonus.set(i, numOfBonus.get(j));
            numOfBonus.set(j, x);

            x = accounts.get(i);
            accounts.set(i, accounts.get(j));
            accounts.set(j, x);
        }
        private void sort(int lo, int hi) {
            if (hi <= lo) return;
            int j = partition(lo, hi);
            sort(lo, j-1);
            sort(j+1, hi);
        }

        private int partition(int lo, int hi)
        {
            int i = lo, j = hi+1;
            while (true)
            {
                while (deviation.get(++i) < deviation.get(lo))
                    if (i == hi) break;
                while (deviation.get(lo) < deviation.get(--j))
                    if (j == lo) break;

                if (i >= j) break;
                exch(i, j);
            }
                exch(lo, j);
            return j;
        }

        private void decreceBonus() {
            numOfBonus.set(idmaxbonus, numOfBonus.get(idmaxbonus) - 1);
            totalNumBonus--;
            idmaxbonus--;
            //if (numOfBonus.get(idmaxbonus)  == 0) size--;
        }
        private void Calc() {
            //int bonusOnacc;
            //int i;
            double deviationprob = 0.0;
            idmaxbonus = size - 1;
            /*
            for (i = 0; i < size ; i++) {
                //if (totalNumBonus == managers) break;
                numOfBonus.set(i, numOfBonus.get(i) + 1);
                totalNumBonus++;
                if (totalNumBonus == managers) break;
            }*/
            while (totalNumBonus > managers) {
                decreceBonus();
            }
            if (idmaxbonus < 0) idmaxbonus = 0;
            int i = 0;
            while (i < idmaxbonus) {
                int j;
                for (j = i; j < idmaxbonus; j++) {
                 int k = numOfBonus.get(j) - (int)(accounts.get(j) / middle) + 1;
                 deviationprob = (middle*k - (accounts.get(j) % middle)) / (numOfBonus.get(j) + 1);
                 if (deviationprob <= deviation.get(idmaxbonus)) {
                    deviation.set(j, deviationprob);
                    numOfBonus.set(j, numOfBonus.get(j) + 1);
                    decreceBonus();
                    sort(j, idmaxbonus); 
                    break;
                 }
                }
                //if ( i == idmaxbonus) break;
                i = j;
            }
            
            
            bonus = accounts.get(idmaxbonus) / numOfBonus.get(idmaxbonus);
            /*
            for (int j = 1; j < size; j++ ) {
                bonusOnacc = accounts.get(j) / numOfBonus.get(j);
                if ( bonusOnacc < bonus) bonus  = bonusOnacc;
            }*/
        }

        public int getBonus() {
            return bonus;
        } 
    }
    private static long sumAccount(List<Integer> accounts) {
        long sum = 0;
        for ( int m : accounts)
            sum +=m;
            return sum;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int num_acc = in.nextInt();
        int managers = in.nextInt();
        List<Integer> accounts=new ArrayList<>();
        int bonus;
        long sum;
        
        for (int i=0; i < num_acc; i++) //заполнение массива счетов 
        {
            accounts.add(in.nextInt());
        }
        in.close();
        
        sum = sumAccount(accounts);
        if (sum / managers < 1) {
            System.out.println("0");
            return;
        }
        double middle = 1.0*sum / managers;
        //int middle = lmiddle.intValue();

        BonusCalculate bonuscalc = new BonusCalculate(accounts, managers, middle);
        bonus = bonuscalc.getBonus();
        
        System.out.println(bonus);

    }
}
