package Wayfair;

class Numbers {
    public String addLargeNumbers(String num1, String num2) {
        if(num2.length()>num1.length()) return addLargeNumbers(num2, num1);

        StringBuilder sb1 = new StringBuilder(num1);
        StringBuilder sb2 = new StringBuilder(num2);
        sb1.reverse(); sb2.reverse();
        int carry = 0;
        StringBuilder result = new StringBuilder();
        for(int i=0; i<sb2.length(); i++) {
            int sum = Integer.valueOf(sb1.charAt(i)+"")+Integer.valueOf(sb2.charAt(i)+"")+carry;
            carry = sum/10;
            sum %= 10;
            result.append(sum);
        }

        for(int i=sb2.length(); i<sb1.length(); i++) {
            int sum = Integer.valueOf(sb1.charAt(i)+"")+carry;
            carry = sum/10;
            sum %= 10;
            result.append(sum);
        }

        if(carry==1) result.append(carry);
        return String.valueOf(result.reverse());
    }

    public String addLargeNumbersComma(String num1, String num2) {
        String[] newNum1 = num1.split(",");
        String[] newNum2 = num2.split(",");

        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        for(String num: newNum1) sb1.append(num);
        for(String num: newNum2) sb2.append(num);

        String result = addLargeNumbers(String.valueOf(sb1), String.valueOf(sb2));
        StringBuilder sbResult = new StringBuilder();
        for(int i=0; i<result.length(); i++) {
            char ch = result.charAt(i);
            sbResult.append(ch+",");
        }
        return String.valueOf(sbResult).substring(0, sbResult.length()-1);
    }

    public String calculateFibonacci(String num) {
        String zero = "0";
        String one = "1";
        for(int i=2; i<=Long.valueOf(num+""); i++) {
            String temp = addLargeNumbersComma(zero, one);
            zero = one;
            one = temp;
        }
        return one;
    }
}

class LargeNumbers {
    public static void main(String[] args) {
        String num1 = "123";
        String num2 = "99";
        Numbers nb = new Numbers();
        String result = nb.addLargeNumbers(num1, num2);
        System.out.println("Result: "+result); // 222 expected

        String numComma1 = "9,9,9,0";
        String numComma2 = "9,9";
        Numbers nb1 = new Numbers();
        String resultComma = nb1.addLargeNumbersComma(numComma1, numComma2);
        System.out.println("ResultComma: "+resultComma); // -> 1,0,0,8,9 expected

        String fibNum = "50";
        Numbers nb2 = new Numbers();
        String resultFib = nb2.calculateFibonacci(fibNum);
        System.out.println("ResultFibonacci: "+resultFib); // -> 1,2,5,8,6,2,6,9,0,2,5 expected
    }
}