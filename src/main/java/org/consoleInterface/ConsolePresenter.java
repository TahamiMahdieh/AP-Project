package org.consoleInterface;

import org.Database.DataBaseActions;
import org.common.Bridge;
import org.common.Commands;
import org.common.SendMessage;
import org.common.User;
import org.w3c.dom.CDATASection;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

public class ConsolePresenter{
    public synchronized static void ShowConnectionFailure(){
        System.out.println("connection failed");
    }
    public synchronized static void showWelcomePage(Socket socket, ObjectOutputStream writer, String jwt){
        // writer for mainClient

        System.out.println("-WELCOME TO LINKEDIN-");
        System.out.println("1. Sign Up");
        System.out.println("2. Sign in");
        System.out.println("3. Exit");
        String command = readFromUser();

        try {
            if (Integer.parseInt(command) == 1) {
                showSignUpPage(socket, writer, jwt);
            } else if (Integer.parseInt(command) == 2) {
                showSignInPage(socket, writer, jwt);
            } else if (Integer.parseInt(command) == 3) {

            }
            else {
                System.out.println("You must choose a number between 1 and 3.\n Please Try again.");
            }
        }
        catch (NumberFormatException e) {
            System.out.println("Please choose a NUMBER between 1 and 3. Try again.");
        }
    }
    public static synchronized void showSignUpPage(Socket socket, ObjectOutputStream writer, String jwt){
        // writer for mainClient

        System.out.println("-SIGN UP-");
        System.out.print("First name: ");
        String firstname = readFromUser();
        while (!isValidName(firstname)){
            System.out.println("First name has to be made up of letters only. Try again.");
            System.out.print("Corrected first name: ");
            firstname = readFromUser();
        }
        System.out.print("Last Name: ");
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
    public static synchronized void showSignInPage(Socket socket, ObjectOutputStream writer, String jwt) {
        System.out.println("-SIGN IN-");
        System.out.print("Email: ");
        String email = readFromUser();
        while (!isValidEmail(email)){
            System.out.println("Invalid email. Try again");
            email = readFromUser();
        }
        System.out.print("Password: ");
        String password = readFromUser();
        while (!isValidPassword(password)){
            System.out.println("Password must be at least 8 characters, made up of numbers and letters");
            System.out.print("Corrected password: ");
            password = readFromUser();
        }
        User u = new User(email, password);
        Bridge bridge = new Bridge(Commands.SIGN_IN, u, jwt);
        SendMessage.send(bridge, writer);
    }

    public static synchronized void showHome(User user, Socket socket, ObjectOutputStream writer, String jwt) {
        DataBaseActions dataBaseActions = new DataBaseActions();
        String email = user.getEmail();
        System.out.println(dataBaseActions.getFirstname(email) + " " + dataBaseActions.getLastname(email));
        System.out.println(dataBaseActions.getHeadline(email));
        System.out.println("1. Edit profile");
        System.out.println("2. Exit");
        String command;
        do {
            try {
                command = readFromUser();
                if (Integer.parseInt(command) < 1 || Integer.parseInt(command) > 2) {
                    System.out.println("That's invalid. Try again.");
                }
                else {
                    int c = Integer.parseInt(command);
                    switch (c) {
                        case 1 -> ConsolePresenter.showProfileEditPage(user, socket, writer, jwt);
                        case 2 -> ConsolePresenter.showWelcomePage(socket, writer, jwt);
                    }
                }

            } catch (NumberFormatException e) {
                System.out.println("That's invalid. Try again.");
            }
        } while (true);
    }

    public static synchronized void showProfileEditPage (User user, Socket socket, ObjectOutputStream writer, String jwt) {
        String email = user.getEmail();
        DataBaseActions da = new DataBaseActions();
        System.out.println("-EDIT-");
        System.out.println("To edit, enter the corresponding number.");
        System.out.println("1. Name: " + da.getFirstname(email) + " " + da.getLastname(email));
        System.out.println("2. Additional Name: " + da.getAdditionalName(email));
        System.out.println("3. Headline: " + da.getHeadline(email));
        System.out.println("4. This account's email: " + email);
        System.out.println("5. Profile picture: " + da.getProfilePicture(email));
        System.out.println("6. Background picture: " + da.getBackgroundPicture(email));
        System.out.println("7. Contact info");

        String command;

        do {
            try {
                command = readFromUser();
                if (Integer.parseInt(command) < 1 || Integer.parseInt(command) > 2) {
                    System.out.println("That's invalid. Try again.");
                }
                else {
                    int c = Integer.parseInt(command);
                    switch (c) {
                        case 1 -> {
                            System.out.print("New first name: ");
                            System.out.print("New last name: ");
                        }
                    }
                }

            } catch (NumberFormatException e) {
                System.out.println("That's invalid. Try again.");
            }
        } while (true);
    }

    public static synchronized void showContactInfoEditPage(User user, Socket socket, ObjectOutputStream writer, String jwt) {
        String email = user.getEmail();
        DataBaseActions da = new DataBaseActions();
    }

    public static synchronized void showErrorPage(Socket socket, ObjectOutputStream writer, String jwt) {
        System.out.println("ERROR :((((((");
        System.out.println("1. exit");
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
