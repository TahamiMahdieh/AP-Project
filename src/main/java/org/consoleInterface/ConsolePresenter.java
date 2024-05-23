package org.consoleInterface;

import org.common.Bridge;
import org.common.Commands;
import org.common.SendMessage;
import org.common.User;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ConsolePresenter{
    public synchronized static void ShowConnectionFailure(){
        System.out.println("connection failed");
    }
    public synchronized static void showWelcomePage(Socket socket, ObjectOutputStream writer, String jwt){
        System.out.println("welcome to LinkedIn!");
        System.out.println("1. sign up");
        System.out.println("2. sign in");
        System.out.println("3. exit");
        String command = readFromUser();
        if (Integer.parseInt(command) == 1){
            showSignInPage(socket, writer, jwt);
        }
        else if (Integer.parseInt(command)== 2){

        }
        else if (Integer.parseInt(command) == 3){

        }
        else {
            System.out.println("invalid input");
        }
    }
    private synchronized static void showSignInPage(Socket socket, ObjectOutputStream writer, String jwt){
        System.out.print("firstname: ");
        String firstname = readFromUser();
        while (!isValidName(firstname)){
            System.out.println("firstname has to be made of letters only");
            System.out.print("correct firstname: ");
            firstname = readFromUser();
        }
        System.out.print("lastname: ");
        String lastname = readFromUser();
        while (!isValidName(lastname)){
            System.out.println("lastname has to be made of letters only");
            System.out.print("correct lastname: ");
            firstname = readFromUser();
        }
        System.out.print("email: ");
        String email = readFromUser();
        while (!isValidEmail(email)){
            System.out.println("invalid email. try again");
            email = readFromUser();
        }
        System.out.print("password: ");
        String password = readFromUser();
        while (!isValidPassword(password)){
            System.out.println("password must have at least 8chars, made of numbers and letters");
            System.out.print("correct password: ");
            password = readFromUser();
        }
        System.out.print("repeat password: ");
        String password2 = readFromUser();
        while (!password2.equals(password)){
            System.out.println("repeated password doesn't match. try again");
            password2 = readFromUser();
        }
        User u = new User(email, firstname, lastname, password);
        Bridge bridge = new Bridge(Commands.SIGN_UP, u, jwt);
        SendMessage.send(bridge, writer);

    }

    private static String readFromUser() {
        Scanner s = new Scanner(System.in);
        String command = s.nextLine();
        return command;
    }
    private static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return email != null && email.matches(emailRegex);
    }
    private static boolean isValidName(String name) {
        return name != null && name.matches("[a-zA-Z]+");
    }

    private static boolean isValidPassword(String password) {
        return password != null && password.length()>= 8 && password.matches(".*[a-zA-Z].*") && password.matches(".*[0-9].*");
    }
}
