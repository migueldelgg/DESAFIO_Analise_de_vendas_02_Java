package application;

import entities.Sale;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Program {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter full file path: ");
        String path = sc.next();
        System.out.println();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            List<Sale> list = new ArrayList<>();
            Map<String, Double> map = new LinkedHashMap<>();


            String line = br.readLine();
            while (line != null) {
                String[] fields = line.split(",");

                list.add(new Sale(Integer.parseInt(fields[0]),
                        Integer.parseInt(fields[1]),
                        fields[2],
                        Integer.parseInt(fields[3]),
                        Double.parseDouble(fields[4])));
                map.put(fields[2], 0.0);
                line = br.readLine();
            }

            for (Map.Entry<String, Double> entry : map.entrySet()) {
                double total = list.stream()
                        .filter(s -> s.getSeller().equals(entry.getKey()))
                        .map(s -> s.getTotal())
                        .reduce(0.0, (x, y) -> x + y);
                map.put(entry.getKey(), total);
            }

            System.out.println("Total de vendas por vendedor: ");
            for (Map.Entry<String, Double> entry : map.entrySet()) {
                System.out.println(entry.getKey() + " - R$ " + String.format("%.2f", entry.getValue()));
            }

        } catch (IOException E) {
            System.out.println("Error: " + E.getMessage());
        } finally {
            sc.close();
        }
    }
}
