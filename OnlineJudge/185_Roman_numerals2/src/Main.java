import java.util.*;

public class Main {

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

    public static void main(String[] args) {
        init();
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            if (input.equals("#")) break;
            String result = "";
            String[] split = input.split("[+,=]+");

            int firstAddend = romanArabic(split[0]);
            int secondAddend = romanArabic(split[1]);
            int sumR = romanArabic(split[2]);
            if (firstAddend + secondAddend == sumR) {
                result += "Correct ";
            }  else {
                result += "Incorrect ";
            }

            char[] addend1 = split[0].toCharArray();
            char[] addend2 = split[1].toCharArray();
            char[] sum = split[2].toCharArray();

            HashSet<Character> characters = new HashSet<Character>();
            for (char c : addend1) {
                characters.add(c);
            }
            for (char c : addend2) {
                characters.add(c);
            }
            for (char c : sum) {
                characters.add(c);
            }

            int numSolutions = -1;
            if (addend1.length > sum.length || addend2.length > sum.length) {
                numSolutions = 0;
            }
            if (sum.length > addend1.length+1 && sum.length > addend2.length+1) {
                numSolutions = 0;
            }
            if (characters.size() == 1) {
                numSolutions = 0;
            }
            HashMap<Character,Integer> charNum = new HashMap<>();
            for (Character character : characters) {
                charNum.put(character,0);
            }
            if (numSolutions == -1) numSolutions = backtrack(-1,addend1,addend2,sum,charNum, characters.toArray(new Character[0]),0);

            switch (numSolutions) {
                case 0 :
                    result += IMPOSSIBLE;
                break;
                case 1 :
                    result += VALID;
                break;
                default :
                    result+= AMBIGUOUS;
                break;
            }

            System.out.println(result);
        }
    }

    private static int backtrack(int prevI, char[] addend1, char[] addend2, char[] sum, HashMap<Character,Integer> charNum, Character[] allChars,int numIdx) {
        int result = 0;
        if (numIdx < allChars.length) {
            for (int i = 0; i < 10; i++) {
                if (i == prevI) {
                    continue;
                }
                charNum.put(allChars[numIdx], i);
                result += backtrack(i,addend1, addend2, sum, charNum, allChars, numIdx+1);
                if (numIdx == allChars.length-1) {
                    result += solve(addend1, addend2, sum, charNum);
                }
                if (result > 1) return 2;
            }
        }
        return result;
    }

    private static int solve(char[] addend1, char[] addend2, char[] summ, HashMap<Character,Integer> charNum) {
        int add1 = toInt(addend1, charNum);
        int add2 = toInt(addend2, charNum);
        int sum  = toInt(summ, charNum);
        if (!(add1 * add2 == 0 || sum == 0)) {
            if (add1 + add2 == sum) {
                return 1;
            } else {
                return 0;
            }
        } else return 0;
    }

    private static int toInt(char[] nums, HashMap<Character,Integer> charNum) {
        int result;
        if (!(charNum.get(nums[0]) == 0)) {
            result = 0;
            for (char num : nums) {
                result = result * 10 + charNum.get(num);
            }
        } else {
            return 0;
        }
        return result;
    }
}
