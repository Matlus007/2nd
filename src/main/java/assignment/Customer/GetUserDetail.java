package assignment.Customer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GetUserDetail {
    
    
    public GetUserDetail(){
        try {
            // Load product data from a file (modify the file name as needed)
            Scanner scanner = new Scanner(new File("products.txt"));
            while (scanner.hasNext()) {
                String name = scanner.nextLine();
                double price = Double.parseDouble(scanner.nextLine());
                String status = scanner.nextLine();
                System.out.println(status);
                scanner.nextLine();

                if (status.equals("Available")){

                }
            }
            scanner.close();
        } catch (FileNotFoundException | NumberFormatException e) {
        }
    }
}
