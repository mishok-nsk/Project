package probability;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    
    public static class StackValue {

        private NodeVal first = null;

        private class NodeVal {
            NodeVal next;
            int size;
            ArrayList<Long> value;
            ArrayList<Double> percent;

            public NodeVal() {
                size = 0;
                next = null;
                value = new ArrayList<>();
                percent = new ArrayList<>();
            }

            public NodeVal(boolean dynamic, int val) {
                if (dynamic) {
                    size = val;
                    value = new ArrayList<>();
                    percent = new ArrayList<>();
                    for (int i = 0; i < size; i++) {
                        value.add((long)(i + 1));
                        percent.add(100.0 / size);
                    }
                }
                else {
                    size = 1;
                    value = new ArrayList<>();
                    percent = new ArrayList<>();
                    value.add((long)val);
                    percent.add(100.0);
                }
            }

            public void add(long val, double perc) {
                int left = 0;
                int right = size;
                int mid = 0;
                long midval = 0;

                while(left != right) {
                    mid = left + (right - left) / 2;
                    midval = value.get(mid);
                    if (midval == val) break;
                    if (midval > val) 
                        right = mid;
                    else 
                        left = mid+1;
                }
                
                if (left == right) {
                    value.add(left, val);
                    percent.add(left, perc);
                    size++;
                }
                else 
                    percent.set(mid, percent.get(mid) + perc);
                
            }

            public long getvalue(int i) {
                return value.get(i);
            }

            public double getpercent(int i) {
                return percent.get(i);
            }
        }

        public void push(boolean dynamic, int value) {
            
            NodeVal add = new NodeVal(dynamic, value);
            add.next = first;
            first = add;
        }

        

        public void calc(char operand) {
            long firstterm, sum = 0;
            double firstpercent, sumpercent;
            NodeVal second = first.next;
            NodeVal summ = new NodeVal();
            for (int i = 0; i < first.size; i++) {
                firstterm = first.getvalue(i);
                firstpercent = first.getpercent(i);
                for ( int j = 0; j < second.size; j++) {
                    switch (operand) {
                        case ('+'):
                            sum = firstterm + second.getvalue(j);
                            break;
                        case ('-'):
                            sum = second.getvalue(j) - firstterm;
                            break;
                        case ('*'):
                            sum = firstterm * second.getvalue(j);
                            break;
                        case ('>'):
                            sum = firstterm < second.getvalue(j) ? 1 : 0;
                            //break;
                    }
                    //sum = firstterm + second.getvalue(j);
                    sumpercent = firstpercent * second.getpercent(j) / 100;
                    summ.add(sum, sumpercent);
                }
            }
            summ.next = second.next;
            first = summ;
        }

        public void print() {
            String result;
            for(int i = 0; i < first.size; i++) {
                result = String.format("%.2f", first.getpercent(i));
                System.out.println(first.getvalue(i) + " " + result);
            }
        }
 
    }

    public static class StackOperator {

        private NodeOp first;

        private class NodeOp {
            NodeOp next;
            char value;
        }

        public StackOperator() {
            first = null;
        }

        public void push(char in) {

            NodeOp add = new NodeOp();
            add.value = in;
            add.next = first;
            first = add;
        }

        public char pop() {
            char out = first.value;
            first = first.next;
            return out;
        }

        public char get() {
            return first.value;
        }

        public boolean isEmpty() {
            return first == null;
        }
    }
    
    public static void Calculate(char operator, StackOperator stackop, StackValue stval) {
        char prevoperator;
        /*
        if (value != 0)
            stval.push(dynamic, value);
        */   

        if (stackop.isEmpty()) { 
            stackop.push(operator);
            return;
        }

        switch (operator) {
            case ('>'):
                while (!(stackop.isEmpty())) {
                    if (stackop.get() == '(') break;
                    stval.calc(stackop.pop());
                }
                stackop.push(operator);
                break;
            case (')'):
                prevoperator = stackop.pop();
                while (prevoperator != '(') {
                    stval.calc(prevoperator);
                    prevoperator = stackop.pop();
                }
                break;
            case ('*'):
                prevoperator = stackop.get();
                if (prevoperator == '*') 
                    stval.calc(operator);                    
                else 
                    stackop.push('*');
                break;
            case ('+'):
            case ('-'):
                prevoperator = stackop.get();
                if (prevoperator != '>' & prevoperator != '(') {
                    stval.calc(stackop.pop());
                    if (!stackop.isEmpty()) {
                        prevoperator = stackop.get();
                        if (prevoperator == '+' || prevoperator == '-') {
                            stval.calc(stackop.pop());
                        }
                    }
                } 
                stackop.push(operator);
            
        }

    }
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        String inline = in.nextLine();
        in.close();
        char c;
        int value = 0;
        int length = inline.length();
        boolean dynamic = false;
        boolean num = false;
        //ArrayList<String> operators = new ArrayList<>();
        StackValue stval = new StackValue();
        StackOperator stackop = new StackOperator();

        for ( int i = 0; i < length; i++) {
            c=inline.charAt(i);
            switch (c) {
                case ('d'):
                    dynamic = true;
                    break;
                case ('('):
                    stackop.push('(');
                    //stval.push(dynamic, value);
                    //dynamic = false;
                    //value = 0;
                    break;
                case ('*'):
                case ('+'):
                case ('-'):    
                case ('>'):
                case (')'):
                    if (num) {
                        stval.push(dynamic, value);
                        dynamic = false;
                        num = false;
                        value = 0;
                    }
                    Calculate(c, stackop, stval);
                    //dynamic = false;
                    //value = 0;
                    break;
                default:
                    num = true;
                    value = value * 10 + c - '0';
            }
        }
        if (inline.charAt(length - 1) != ')') 
            stval.push(dynamic, value);

        while (!stackop.isEmpty()) {
            stval.calc(stackop.pop());
        }
        stval.print();

    }
}
