import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class test {

    static final String VALID = "valid";
    static final String IMPOSSIBLE = "impossible";
    static final String AMBIGUOUS = "ambiguous";

    static HashMap<Character,Integer> lookupTable = new HashMap<>();

    public static void init(){
        lookupTable.put('I', 1);
        lookupTable.put('V',5);
        lookupTable.put('X',10);
        lookupTable.put('L',50);
        lookupTable.put('C',100);
        lookupTable.put('D',500);
        lookupTable.put('M',1000);
    }

    public static int romanArabic(String roman){
        char[] romanNumearls = roman.toCharArray();
        int result = 0;
        for (int i = 0; i < romanNumearls.length; i++) {

            if (!(i+1 == romanNumearls.length)) {
                if (lookupTable.get(romanNumearls[i]) < lookupTable.get(romanNumearls[i + 1])) {

                    result += lookupTable.get(romanNumearls[i + 1]) - lookupTable.get(romanNumearls[i]);
                    i++;
                    continue;
                }
            }
            result += lookupTable.get(romanNumearls[i]);
        }
        return result;
    }
    private static int outIter = 1;
    public static void main(String[] args) {
        init();
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            if (input.equals("#")) break;
            String result = "";
            String[] equalSplit = input.split("=");
            String[] plusSplit = equalSplit[0].split("\\+");
            String first = plusSplit[0];
            String second = plusSplit[1];
            String third = equalSplit[1];


            int firstAddend = romanArabic(first);
            int secondAddend = romanArabic(second);
            int sumR = romanArabic(third);
            if (firstAddend + secondAddend == sumR) {
                result += "Correct ";
            }  else {
                result += "Incorrect ";
            }


            char[] addend1 = first.toCharArray();
            char[] addend2 = second.toCharArray();
            char[] sum = third.toCharArray();

            HashMap<Character, Integer> characters = new HashMap();
            for (char c : addend1) {
                characters.put(c, -1);
            }
            for (char c : addend2) {
                characters.put(c, -1);
            }
            for (char c : sum) {
                characters.put(c, -1);
            }

            int[] inUse = new int[characters.size()];
            int iter = 0;
            int start = (int) Math.pow(10, inUse.length);
            int goal = (int) Math.pow(10, inUse.length-1);
            int stop = (int) Math.pow(10, inUse.length-2);



            int count = 0;
            goalLoop:
            for (int i = start - 1; i >= 0; i--) {

                int[] nums = new int[characters.size()];
                String tmp = String.valueOf(i);

                String[] split = tmp.split("");
                String[] numsString = new String[nums.length];


                for (int j = 0; j < split.length; j++) {
                    if (split.length == numsString.length) {
                        numsString[j] = split[j];
                    } else if (numsString.length == split.length + 1) {
                        numsString[j+1] = split[j];
                    }
                }
                for (int j = 0; j < numsString.length; j++) {
                    if (numsString[j] == null) {
                        numsString[j] = "0";
                    }
                }

                for (int j = 0; j < nums.length; j++) {
                    nums[j] = Integer.parseInt(numsString[j]);
                }


                checkLoop:
                for (int j = 0; j < nums.length; j++) {
                    for (int k = 0; k < nums.length; k++) {
                        if (j == k) continue checkLoop;
                        if (nums[j] == nums[k]) continue goalLoop;
                    }
                }


                int j = 0;
                for (Map.Entry<Character, Integer> characterIntegerEntry : characters.entrySet()) {
                    characters.put(characterIntegerEntry.getKey(), nums[j]);
                    j++;
                }
                int add1 = doTheThing(addend1, characters);
                int add2 = doTheThing(addend2, characters);
                int summ = doTheThing(sum, characters);


                if ((add1 + add2) == summ) {
                    count++;
                }
                if (count >= 2) break;
            }

            switch (count) {
                case 0:
                    result += (IMPOSSIBLE);
                    break;
                case 1:
                    result += (VALID);
                    break;
            }
            if (count >= 2) {
                result += (AMBIGUOUS);
            }
            System.out.println(result);
            outIter++;
        }
    }

    private static int doTheThing(char[] chars, HashMap<Character, Integer> characters) {
        int sum = Integer.MIN_VALUE;
        StringBuilder sumStr = new StringBuilder();
        for (char c : chars) {
            sumStr.append(characters.get(c));
        }
        if (!(sumStr.toString().toCharArray()[0] == '0')) {
            sum = Integer.parseInt(sumStr.toString());
        }
        return sum;
    }
}