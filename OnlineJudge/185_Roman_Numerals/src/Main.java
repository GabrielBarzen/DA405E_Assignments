import java.util.HashMap;

public class Main {

    static HashMap<Character, Integer> lookupTable = new HashMap<>();

    public static void main(String[] args) {
        init();



        String input = "V+V=X";
        String result = "";
        String[] equalSplit = input.split("=");
        String[] plusSplit = equalSplit[0].split("\\+");
        String first = plusSplit[0];
        String second = plusSplit[1];
        String third = equalSplit[1];
        int firstAddend = romanArabic(first);
        int secondAddend = romanArabic(second);
        int sum = romanArabic(third);
        if (firstAddend + secondAddend == sum) {
            result += "Correct";
        }  else {
            result += "Incorrect";
        }
        System.out.println(result);

    }

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
            System.out.println(romanNumearls[i]);
            if (!(i+1 == romanNumearls.length)) {
                if (lookupTable.get(romanNumearls[i]) < lookupTable.get(romanNumearls[i + 1])) {
                    System.out.println("lookupTable.get("+(romanNumearls[i])+")"+"<"+"lookupTable.get("+(romanNumearls[i + 1])+")");
                    result += lookupTable.get(romanNumearls[i + 1]) - lookupTable.get(romanNumearls[i]);
                    i++;
                    continue;
                }
            }
            result += lookupTable.get(romanNumearls[i]);
        }
        return result;
    }
}
