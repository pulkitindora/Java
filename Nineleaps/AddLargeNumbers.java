package Nineleaps;

/*
 * QUESTION:
 * Given two numbers as strings. 
 * The numbers may be very large (may not fit in primitive data types), the task is to find sum of these two numbers.
 * Input1: 23
 * Input2: 24
 * Output: 47
 * 
 * NOTE: Don't use BigInteger or such large number containers
 */


public class AddLargeNumbers {
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
        String result = addLargeNumbers(String.valueOf(num1), String.valueOf(num2));
        StringBuilder sbResult = new StringBuilder(result);
        int flag = 0;
        for(int i=sbResult.length()-1; i>-1; i--) {
            flag++;
            if(flag==3 && i!=0) {
                sbResult.insert(i, ",");
                flag = 0;
            }
        }

        return String.valueOf(sbResult);
    }
}

class LargeNumbers {
    public static void main(String[] args) {
        String num1 = "9990";
        String num2 = "992311111";
        AddLargeNumbers nb = new AddLargeNumbers();

        String result = nb.addLargeNumbers(num1, num2);
        System.out.println("Result: "+result); // 992321101 expected

        String resultComma = nb.addLargeNumbersComma(num1, num2);
        System.out.println("Result, Comma Separated: "+resultComma); // -> 992,321,101 expected
    }
}