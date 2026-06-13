import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Reportgenerator {

    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static Crops[] Crops;

    /** Builds a full system report string (Farmers, Animals, Crops, Inventory, Profit) and writes it to a file. */
    public static String generateReport(List<Farmers> farmers,
                                        List<AnimalStock> animals,
                                        List<Crops> cropList, List<Inventory> inventoryItems) {

        StringBuilder sb = new StringBuilder();
        String sep = "═".repeat(60) + "\n";
        String now = LocalDateTime.now().format(FMT);

        sb.append(sep);
        sb.append("      SMART FARM MANAGEMENT SYSTEM — FULL REPORT\n");
        sb.append("      Generated: ").append(now).append("\n");
        sb.append(sep);

        // ── [1] Farmers ─────────────────────────────────────────────────────
        sb.append("\n[SECTION 1] REGISTERED FARMERS  (").append(farmers.size()).append(")\n");
        sb.append("─".repeat(60)).append("\n");
        for (Farmers f : farmers) {
            sb.append("  ").append(f.getDetails()).append("\n");
        }

        // ── [2] Animal Stock ────────────────────────────────────────────────
        sb.append("\n[SECTION 2] ANIMAL STOCK  (").append(animals.size()).append(" types)\n");
        sb.append("─".repeat(60)).append("\n");
        double totalCost    = 0;
        double totalRevenue = 0;
        for (AnimalStock a : animals) {
            sb.append("  ").append(a.getDetails()).append("\n");
            totalCost    += a.getTotalCost();
            totalRevenue += a.getPotentialRevenue();
            if (a.isLowStock()) {
                sb.append("  *** LOW STOCK ALERT: ").append(a.getAnimalname())
                        .append(" — only ").append(a.getStockCount()).append(" remaining ***\n");
            }
        }
        double animalProfit = totalRevenue - totalCost;
        sb.append("\n  Total Input Cost    : $").append(String.format("%.2f", totalCost)).append("\n");
        sb.append("  Potential Revenue   : $").append(String.format("%.2f", totalRevenue)).append("\n");
        sb.append("  Projected Profit    : $").append(String.format("%.2f", animalProfit)).append("\n");

        // ── [3] Crops ───────────────────────────────────────────────────────
        sb.append("\n[SECTION 3] CROPS (")
                .append(cropList.size())
                .append(" types)\n");
        double cropRevenue = 0.0d;
        for (Crops c : cropList) {
            sb.append(c.getDetails()).append("\n");
            cropRevenue = cropRevenue + c.calculateRevenue();
        
        }

        // ── [4] Inventory ───────────────────────────────────────────────────
        sb.append("\n[SECTION 4] INVENTORY ITEMS  (").append(inventoryItems.size()).append(" items)\n");
        sb.append("─".repeat(60)).append("\n");
        double invValue = 0;
        for (Inventory item : inventoryItems) {
            sb.append(String.format("  %-4d | %-20s | %-12s | Qty: %-5d | $%.2f%s\n",
                    item.getInventoryID(),
                    item.getItemName(),
                    item.getCategory(),
                    item.getQuantity(),
                    item.getPrice(),
                    item.isLowStock() ? "  ⚠ LOW" : ""));
            invValue += item.getQuantity() * item.getPrice();
        }
        sb.append("\n  Total Inventory Value: $").append(String.format("%.2f", invValue)).append("\n");

        // ── [5] Financial Summary ───────────────────────────────────────────
        double totalRevenueAll = totalRevenue + cropRevenue;
        double totalInvestment = totalCost + invValue;
        double netProfit       = totalRevenueAll - totalInvestment;

        sb.append("\n[SECTION 5] FINANCIAL SUMMARY\n");
        sb.append("─".repeat(60)).append("\n");
        sb.append("  Animal Stock Cost     : $").append(String.format("%.2f", totalCost)).append("\n");
        sb.append("  Inventory Value       : $").append(String.format("%.2f", invValue)).append("\n");
        sb.append("  Total Investment      : $").append(String.format("%.2f", totalInvestment)).append("\n");
        sb.append("  Animal Revenue        : $").append(String.format("%.2f", totalRevenue)).append("\n");
        sb.append("  Crop Revenue          : $").append(String.format("%.2f", cropRevenue)).append("\n");
        sb.append("  Total Revenue         : $").append(String.format("%.2f", totalRevenueAll)).append("\n");
        sb.append("  Net Profit            : $").append(String.format("%.2f", netProfit)).append("\n");

        sb.append("\n").append(sep);
        sb.append("  END OF REPORT\n");
        sb.append(sep);

        writeToFile("farm_report_" + LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".txt",
                sb.toString());

        return sb.toString();
    }

    private static void writeToFile(String filename, String content) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            pw.print(content);
            System.out.println("Report saved to " + filename);
        } catch (IOException e) {
            System.err.println("Could not write report: " + e.getMessage());
        }
    }

    /** Simple log entry for the running farm_log.txt */
    public static void logAction(String details) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("farm_log.txt", true))) {
            pw.println("[" + LocalDateTime.now().format(FMT) + "] " + details);
        } catch (IOException e) {
            System.err.println("Log write failed: " + e.getMessage());
        }
    }
}