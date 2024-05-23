package org.project;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ConsoleImpl {
    private final Socket clientSocket;
    private final ObjectOutputStream write;
    private final ObjectInputStream read;

    public ConsoleImpl(Socket clientSocket, ObjectOutputStream write, ObjectInputStream read) {
        this.clientSocket = clientSocket;
        this.write = write;
        this.read = read;
    }

    public void signUpPage () throws IOException, ClassNotFoundException {
        Scanner in = new Scanner(System.in);
        while (clientSocket.isConnected()) {
            System.out.println("welcome to LinkedIn");
            System.out.print("1. login\n2. sign up\n3. exit");
            String input = in.next();
            if (input.equals("1")) {
                //TODO: get input and check if this content exists
            }
            else if (input.equals("2")) {
                Scanner scanner = new Scanner(System.in);
                System.out.print("firstname: ");
                String firstname = scanner.nextLine();
                System.out.print("lastname: ");
                String lastname = scanner.nextLine();
                System.out.print("email: ");
                String email = scanner.nextLine();
                System.out.print("password: ");
                String password = scanner.nextLine();
                System.out.print("repeat password: ");
                String password2 = scanner.nextLine();
                if (!password2.equals(password)){
                    System.out.println("repeated password is incorrect.");
                }
                else if (!isValidEmail(email) || !isValidName(firstname) || !isValidName(lastname) || !isValidPassword(password)) {
                    System.out.println("Invalid input data");
                }
                else {
                    User temp = new User(email, firstname,lastname, password);
                    Bridge bridge = new Bridge(Commands.SIGN_UP, temp);
                    write.writeObject(bridge);
                    Bridge b = (Bridge) read.readObject();
                    if (b.getErrors() != null){
                        if (b.getErrors().equals(Errors.FAILED_SIGNUP)){
                            System.out.println("sign up failed");
                        }
                    }
                    else {

                    }
                }
            }
            else if (input.equals("3")) {
                //TODO: disconnect from server
                System.exit(1);
            }
            else {
                System.out.println("invalid input. please try again");
            }
        }
    }
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return email != null && email.matches(emailRegex);
    }
    private boolean isValidName(String name) {
        return name != null && name.matches("[a-zA-Z]+");
    }

    private boolean isValidPassword(String password) {
        return password != null && password.matches(".*[a-zA-Z].*") && password.matches(".*[0-9].*");
    }
}
