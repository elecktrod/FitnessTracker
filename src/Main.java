import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String args[]){
        FitnessTracker ft = createFitnessTracker();
        if(ft == null)
            return;

        System.out.println("Hello " + ft.user + "!");
        System.out.println(
                "select - select the type of training enter\n" +
                "start - start training\n" +
                "exit - exit training");
        while (true){
            String answer = new Scanner(System.in).nextLine();
            if(answer.equals("select")){
                ft.setTrainingType();
            }
            else if(answer.equals("start")){
                try {
                    ft.start();
                    System.out.println("To stop enter any character");
                    new Scanner(System.in).nextLine();
                    ft.stop();
                }
                catch (ArrayIndexOutOfBoundsException e){
                    System.out.println("First you need to choose the type of training");
                }
            }
            else  if(answer.equals("exit")){
                System.out.println("See you soon, come back!");
                break;
            }
            else {
                System.out.println("Error. Enter command again");
            }
        }
    }

    private static FitnessTracker createFitnessTracker(){
        System.out.println("Enter your username");
        FitnessTracker ft;
        while (true){
            try{
                ft = new FitnessTracker(new Scanner(System.in).nextLine());
                break;
            }
            catch(FileNotFoundException e){
                System.out.println("Enter your username");
            }
            catch (IOException e){
                System.out.println("Error while creating user");
                return null;
            };
        }
        return ft;
    }
}
