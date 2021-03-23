import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class FitnessTracker {

    private static final List<String[]> TYPES = new ArrayList<String[]>(){{
        add(new String[]{"Push up", "12"});
        add(new String[]{"Skipping rope", "14"});
        add(new String[]{"Squats", "9"});
    }};

    private Instant starts;
    private int type = -1;
    public String user;
    private double caloriesSum = 0;

    public FitnessTracker(String _user) throws IOException {
        try {
            FileReader reader = new FileReader(_user + ".txt");
            BufferedReader br = new BufferedReader(reader);
            String currentLine = "";
            String lastLine = "";
            while ((currentLine = br.readLine()) != null)
            {
                lastLine = currentLine;
            }
            br.close();
            if(lastLine!=null && lastLine!=""){
                String[] parameters = lastLine.split( " ");
                caloriesSum = Double.parseDouble(parameters[parameters.length-1].replace(",","."));
            }
            user = _user;
        }
        catch (FileNotFoundException e){
            System.out.println("User not found, create? (y/n)");
            String answer = new Scanner(System.in).nextLine();
            while (!answer.equals("y") && !answer.equals("n")){
                System.out.println("Error. Enter answer again");
                answer = new Scanner(System.in).nextLine();
            }
            if(answer.equals("y")){
                new File(_user + ".txt").createNewFile();
                System.out.println("User created");
                user = _user;
            }
            else {
                throw new FileNotFoundException();
            }

        }

    }

    public void setTrainingType(){
        System.out.println("What kind of workout do you want?");
        for (int i =0;i<TYPES.size();i++){
            System.out.println(i+1 + " " + TYPES.get(i)[0]);
        }
        Integer answer = new Scanner(System.in).nextInt() - 1;
        while (answer<0 || answer>TYPES.size()){
            System.out.println("Error. Enter answer again");
            answer = new Scanner(System.in).nextInt() - 1;
        }
        type = answer;
        System.out.println(TYPES.get(type)[0] + ". Great choice!");
    }

    public void start(){
        if(type<0 || type>TYPES.size()){
            throw new ArrayIndexOutOfBoundsException();
        }
        starts = Instant.now();
    }

    public void stop(){
        Instant ends = Instant.now();
        long s = Duration.between(starts, ends).toSecondsPart();
        long ms = Duration.between(starts, ends).toMillisPart();
        long m = Duration.between(starts, ends).toMinutesPart();
        String time = String.format("%d:%02d:%03d", m, s, ms);
        double calories = ((Duration.between(starts, ends).toMillis() / 1000.0) / 60.0) * Integer.parseInt(TYPES.get(type)[1]);
        caloriesSum+=calories;
        String record = "\n" + Date.from(starts).toString() + " " + type + " " + time + " " + String.format("%.2f", calories) + " " + String.format("%.2f", caloriesSum);
        try {
            Files.write(Paths.get(user+".txt"), record.getBytes(), StandardOpenOption.APPEND);
        }
        catch (IOException e) {
            System.out.println("Due to an undefined error, the result of this training could not be written to a file, but you are still a great fellow!");
        }
        System.out.println(time + " of " + TYPES.get(type)[0] + " Excellent result for today!!!");
        System.out.println("Calories: " + String.format("%.2f", calories));
        System.out.println("Total calories: " + String.format("%.2f", caloriesSum));
    }
}
