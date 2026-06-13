import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalTime;
import java.util.*;
import java.util.List;

public class Farm_Project {

    // ── Colours ───────────────────────────────────────────────────────────────
    private static final Color C_BG       = new Color(18,  32,  18);
    private static final Color C_PANEL    = new Color(28,  48,  28);
    private static final Color C_CARD     = new Color(36,  60,  36);
    private static final Color C_ACCENT   = new Color(76, 175,  80);
    private static final Color C_ACCENT2  = new Color(139, 195, 74);
    private static final Color C_TEXT     = new Color(220, 237, 200);
    private static final Color C_SUBTEXT  = new Color(129, 170, 127);
    private static final Color C_WARN     = new Color(255, 160,  50);
    private static final Color C_ERR      = new Color(229,  57,  53);
    private static final Color C_SUCCESS  = new Color(76,  175,  80);
    private static final Color C_HEADER   = new Color(10,  20,  10);

    // ── In-memory data stores ─────────────────────────────────────────────────
    private static final List<Farmers>     farmerList     = new ArrayList<>();
    private static final List<AnimalStock> animalList     = new ArrayList<>();
    private static final List<Crops>       cropList       = new ArrayList<>();
    private static final List<Inventory>   inventoryList  = new ArrayList<>();
    private static final List<String>      notifications  = new ArrayList<>();

    // ── Session ───────────────────────────────────────────────────────────────
    private static String currentRole = "";

    // ── Demo user accounts ────────────────────────────────────────────────────
    private static final List<Login> accounts = List.of(
            new Login(1, "manager", "manager123", "MANAGER"),
            new Login(2, "farmer",  "farmer123",  "FARMER")
    );

    // ── Sensor simulation ─────────────────────────────────────────────────────
    private static final sensors soilSensor = new sensors(1, "Soil Moisture");
    private static final sensors tempSensor = new sensors(2, "Temperature");
    private static final sensors humSensor  = new sensors(3, "Humidity");
    private static JLabel lblSoil, lblTemp, lblHum;

    // ─────────────────────────────────────────────────────────────────────────
    public static void main(String[] args) {
        seedDemoData();
        SwingUtilities.invokeLater(Farm_Project::showLoginWindow);
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  SEED DATA
    // ══════════════════════════════════════════════════════════════════════════
    private static void seedDemoData() {
        farmerList.add(new Farmers(1, "Ali Hassan",   "0300-1234567", "Lahore"));
        farmerList.add(new Farmers(2, "Bilal Ahmed",  "0312-9876543", "Faisalabad"));

        animalList.add(new AnimalStock(1, "Cow",   4, "Healthy", 450,  12, 3,  80000, 120000, "Hay",      15));
        animalList.add(new AnimalStock(2, "Goat",  2, "Healthy",  35,   2, 5,   8000,  14000, "Grass",     3));
        animalList.add(new AnimalStock(3, "Sheep", 3, "Healthy",  70,  18, 4,  15000,  25000, "Hay",       5));

        cropList.add(new Crops(1, "Wheat", "Winter", 60, 2000, "2025-11-01", "2026-04-15"));
        cropList.add(new Crops(2, "Rice",  "Summer", 90, 1500, "2026-05-01", "2026-09-20"));
        cropList.add(new Crops(3, "Maize", "Summer", 45, 1200, "2026-04-10", "2026-08-01"));

        inventoryList.add(new Inventory(1, "Wheat Seeds",    ItemCategory.SEEDS,       50, 1200, 10));
        inventoryList.add(new Inventory(2, "Urea Fertilizer",ItemCategory.FERTILIZERS, 30,  800,  8));
        inventoryList.add(new Inventory(3, "Pesticide A",    ItemCategory.PESTICIDES,   7, 2500,  5));
        inventoryList.add(new Inventory(4, "Animal Feed",    ItemCategory.FEED,        90,  450, 15));
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  LOGIN WINDOW
    // ══════════════════════════════════════════════════════════════════════════
    private static void showLoginWindow() {
        JFrame frame = new JFrame("Smart Farm — Login");
        frame.setSize(460, 380);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(C_BG);
        frame.setLayout(new BorderLayout());

        JPanel header = new JPanel();
        header.setBackground(C_HEADER);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBorder(BorderFactory.createEmptyBorder(24, 0, 20, 0));

        JLabel logo = new JLabel("🌿 Smart Farm");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        logo.setForeground(C_ACCENT);
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel sub = new JLabel("Management System");
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        sub.setForeground(C_SUBTEXT);
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);

        header.add(logo);
        header.add(Box.createVerticalStrut(4));
        header.add(sub);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(C_BG);
        form.setBorder(BorderFactory.createEmptyBorder(16, 40, 16, 40));
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(6, 6, 6, 6);
        gc.fill   = GridBagConstraints.HORIZONTAL;

        JTextField     txtUser = styledField();
        JPasswordField txtPass = new JPasswordField(20);
        stylePasswordField(txtPass);
        JButton btnLogin  = styledButton("Login", C_ACCENT);
        JLabel  lblStatus = new JLabel(" ");
        lblStatus.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblStatus.setForeground(C_ERR);
        lblStatus.setHorizontalAlignment(SwingConstants.CENTER);

        addRow(form, gc, 0, "Username:", txtUser);
        addRow(form, gc, 1, "Password:", txtPass);

        gc.gridx = 0; gc.gridy = 2; gc.gridwidth = 2;
        form.add(Box.createVerticalStrut(4), gc);

        gc.gridy = 3;
        form.add(btnLogin, gc);

        gc.gridy = 4;
        form.add(lblStatus, gc);

        frame.add(header, BorderLayout.NORTH);
        frame.add(form,   BorderLayout.CENTER);

        JPanel hint = new JPanel();
        hint.setBackground(C_PANEL);
        JLabel hintLbl = new JLabel("Hint — manager / manager123   |   farmer / farmer123");
        hintLbl.setForeground(C_SUBTEXT);
        hintLbl.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        hint.add(hintLbl);
        frame.add(hint, BorderLayout.SOUTH);

        btnLogin.addActionListener(e -> {
            String u = txtUser.getText().trim();
            String p = new String(txtPass.getPassword()).trim();
            String role = null;
            for (Login acct : accounts) {
                role = acct.authenticate(u, p);
                if (role != null) break;
            }
            if (role != null) {
                currentRole = role;
                frame.dispose();
                showMainWindow();
            } else {
                lblStatus.setText("Invalid username or password.");
                lblStatus.setForeground(C_ERR);
            }
        });

        KeyAdapter enter = new KeyAdapter() {
            @Override public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) btnLogin.doClick();
            }
        };
        txtUser.addKeyListener(enter);
        txtPass.addKeyListener(enter);

        frame.setVisible(true);
        txtUser.requestFocusInWindow();
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  MAIN WINDOW
    // ══════════════════════════════════════════════════════════════════════════
    private static void showMainWindow() {
        JFrame frame = new JFrame("🌿 Smart Farm Management System  |  " + currentRole);
        frame.setSize(1150, 740);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(C_BG);
        frame.setLayout(new BorderLayout(0, 0));

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(C_HEADER);
        topBar.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));

        JLabel title = new JLabel("🌿  Smart Farm Management System");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(C_ACCENT);

        JPanel rightBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        rightBar.setOpaque(false);

        JLabel roleLabel = new JLabel("Logged in as: " + currentRole);
        roleLabel.setForeground(C_SUBTEXT);
        roleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        JButton btnLogout = styledButton("Logout", C_ERR);
        btnLogout.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        btnLogout.setPreferredSize(new Dimension(80, 28));

        rightBar.add(roleLabel);
        rightBar.add(btnLogout);

        topBar.add(title,    BorderLayout.WEST);
        topBar.add(rightBar, BorderLayout.EAST);

        JTabbedPane tabs = new JTabbedPane();
        styleTabs(tabs);

        tabs.addTab("📊 Dashboard",    buildDashboardPanel());
        tabs.addTab("🐄 Animals",       buildAnimalsPanel());
        tabs.addTab("🌾 Crops",         buildCropsPanel());
        tabs.addTab("📦 Inventory",     buildInventoryPanel());
        tabs.addTab("🔔 Notifications", buildNotificationsPanel());

        if ("MANAGER".equals(currentRole)) {
            tabs.addTab("👤 Farmers",  buildFarmersPanel());
            tabs.addTab("📈 Reports",  buildReportsPanel());
            tabs.addTab("💰 Profit",   buildProfitPanel());
        }

        frame.add(topBar, BorderLayout.NORTH);
        frame.add(tabs,   BorderLayout.CENTER);

        btnLogout.addActionListener(e -> {
            frame.dispose();
            showLoginWindow();
        });

        startSensorSimulation();
        startIrrigationNotifier();

        frame.setVisible(true);
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  DASHBOARD PANEL
    // ══════════════════════════════════════════════════════════════════════════
    private static JPanel buildDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout(12, 12));
        panel.setBackground(C_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        panel.add(sectionHeading("Farm Dashboard"), BorderLayout.NORTH);

        JPanel cards = new JPanel(new GridLayout(1, 5, 12, 12));
        cards.setOpaque(false);

        int totalAnimals = animalList.stream().mapToInt(AnimalStock::getStockCount).sum();
        long lowStock    = animalList.stream().filter(AnimalStock::isLowStock).count();
        long lowInv      = inventoryList.stream().filter(Inventory::isLowStock).count();

        cards.add(statCard("🐄", "Total Animals",   String.valueOf(totalAnimals), C_ACCENT));
        cards.add(statCard("⚠", "Low Stock Alerts", String.valueOf(lowStock + lowInv), C_WARN));
        cards.add(statCard("🌾", "Crop Types",      String.valueOf(cropList.size()), C_ACCENT2));
        cards.add(statCard("📦", "Inventory Items", String.valueOf(inventoryList.size()), C_ACCENT2));
        cards.add(statCard("👤", "Farmers",         String.valueOf(farmerList.size()), C_ACCENT));

        panel.add(cards, BorderLayout.CENTER);

        JPanel sensorPanel = new JPanel(new GridLayout(1, 3, 12, 0));
        sensorPanel.setOpaque(false);
        sensorPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(C_ACCENT, 1),
                "  Live Sensors  ",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 12), C_ACCENT));

        lblSoil = sensorValueLabel("Soil Moisture", "—%");
        lblTemp = sensorValueLabel("Temperature",   "—°C");
        lblHum  = sensorValueLabel("Humidity",      "—%");

        sensorPanel.add(wrapSensor(lblSoil));
        sensorPanel.add(wrapSensor(lblTemp));
        sensorPanel.add(wrapSensor(lblHum));

        panel.add(sensorPanel, BorderLayout.SOUTH);
        return panel;
    }

    private static JPanel statCard(String icon, String label, String value, Color accent) {
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(C_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(accent, 1, true),
                BorderFactory.createEmptyBorder(16, 12, 16, 12)));

        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 0; g.gridy = 0;

        JLabel ico = new JLabel(icon, SwingConstants.CENTER);
        ico.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 26));
        card.add(ico, g);

        g.gridy = 1;
        JLabel val = new JLabel(value, SwingConstants.CENTER);
        val.setFont(new Font("Segoe UI", Font.BOLD, 28));
        val.setForeground(accent);
        card.add(val, g);

        g.gridy = 2;
        JLabel lbl = new JLabel(label, SwingConstants.CENTER);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lbl.setForeground(C_SUBTEXT);
        card.add(lbl, g);

        return card;
    }

    private static JLabel sensorValueLabel(String name, String init) {
        JLabel l = new JLabel("<html><center><b>" + name + "</b><br><span style='font-size:18px'>"
                + init + "</span></center></html>", SwingConstants.CENTER);
        l.setForeground(C_TEXT);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        return l;
    }

    private static JPanel wrapSensor(JLabel lbl) {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(C_CARD);
        p.setBorder(BorderFactory.createEmptyBorder(12, 8, 12, 8));
        p.add(lbl);
        return p;
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  ANIMALS PANEL
    // ══════════════════════════════════════════════════════════════════════════
    private static JPanel buildAnimalsPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 12));
        panel.setBackground(C_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        panel.add(sectionHeading("Animal Stock"), BorderLayout.NORTH);

        String[] cols = {"ID","Name","Age","Health","Weight(kg)","Stock","Min Level",
                "Buy Price","Sell Price","Feed","Daily Feed","Status"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        refreshAnimalTable(model);

        JTable table = styledTable(model);
        table.getColumnModel().getColumn(11).setMinWidth(90);

        JScrollPane scroll = new JScrollPane(table);
        styleScrollPane(scroll);
        panel.add(scroll, BorderLayout.CENTER);

        if ("MANAGER".equals(currentRole)) {
            JPanel form = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
            form.setBackground(C_PANEL);
            form.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

            JTextField fId     = smallField("ID",          4);
            JTextField fName   = smallField("Name",        8);
            JTextField fAge    = smallField("Age",         3);
            JTextField fHealth = smallField("Health",      8);
            JTextField fWeight = smallField("Weight",      5);
            JTextField fStock  = smallField("Stock",       4);
            JTextField fMin    = smallField("Min",         3);
            JTextField fBuy    = smallField("Buy$",        6);
            JTextField fSell   = smallField("Sell$",       6);
            JTextField fFeed   = smallField("FeedType",    8);
            JTextField fDaily  = smallField("DailyKg",     5);

            JButton btnAdd = styledButton("Add Animal", C_ACCENT);
            JButton btnDel = styledButton("Delete Selected", C_ERR);
            JLabel  fStatus = new JLabel("  ");
            fStatus.setFont(new Font("Segoe UI", Font.PLAIN, 11));

            form.add(fId); form.add(fName); form.add(fAge); form.add(fHealth);
            form.add(fWeight); form.add(fStock); form.add(fMin);
            form.add(fBuy); form.add(fSell); form.add(fFeed); form.add(fDaily);
            form.add(btnAdd); form.add(btnDel); form.add(fStatus);

            btnAdd.addActionListener(e -> {
                try {
                    AnimalStock a = new AnimalStock(
                            Integer.parseInt(fId.getText().trim()),
                            fName.getText().trim(),
                            Integer.parseInt(fAge.getText().trim()),
                            fHealth.getText().trim(),
                            Double.parseDouble(fWeight.getText().trim()),
                            Integer.parseInt(fStock.getText().trim()),
                            Integer.parseInt(fMin.getText().trim()),
                            Double.parseDouble(fBuy.getText().trim()),
                            Double.parseDouble(fSell.getText().trim()),
                            fFeed.getText().trim(),
                            Double.parseDouble(fDaily.getText().trim())
                    );
                    animalList.add(a);
                    DatabaseManager.saveAnimalStock(a);
                    refreshAnimalTable(model);
                    checkLowStockNotifications();
                    Reportgenerator.logAction("Animal added: " + a.getDetails());
                    fStatus.setText("Added!"); fStatus.setForeground(C_SUCCESS);
                } catch (Exception ex) {
                    fStatus.setText("Error: " + ex.getMessage()); fStatus.setForeground(C_ERR);
                }
            });

            btnDel.addActionListener(e -> {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    animalList.remove(row);
                    refreshAnimalTable(model);
                    checkLowStockNotifications();
                    fStatus.setText("Deleted."); fStatus.setForeground(C_WARN);
                }
            });

            panel.add(form, BorderLayout.SOUTH);
        }

        return panel;
    }

    private static void refreshAnimalTable(DefaultTableModel m) {
        m.setRowCount(0);
        for (AnimalStock a : animalList) {
            m.addRow(new Object[]{
                    a.getAnimalId(), a.getAnimalname(), a.getAge(), a.getHealth_status(),
                    a.getWeight(), a.getStockCount(), a.getMinStockLevel(),
                    "$" + a.getPurchasePrice(), "$" + a.getSalePrice(),
                    a.getFeedType(), a.getDailyFeedKg(),
                    a.isLowStock() ? "⚠ LOW" : "OK"
            });
        }
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  CROPS PANEL  — uses Crops: cropId, cropName, season, price, quantity,
    //                 plantDate, harvestDate + addCrop()/updateCrop()/deleteCrop()
    // ══════════════════════════════════════════════════════════════════════════
    private static JPanel buildCropsPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 12));
        panel.setBackground(C_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        panel.add(sectionHeading("Crop Management"), BorderLayout.NORTH);

        String[] cols = {"ID","Crop Name","Season","Price","Quantity","Plant Date","Harvest Date","Revenue"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        refreshCropTable(model);

        JTable table = styledTable(model);
        JScrollPane scroll = new JScrollPane(table);
        styleScrollPane(scroll);
        panel.add(scroll, BorderLayout.CENTER);

        if ("MANAGER".equals(currentRole)) {
            JPanel form = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
            form.setBackground(C_PANEL);
            form.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

            JTextField fId        = smallField("ID",          4);
            JTextField fName      = smallField("CropName",    9);
            JTextField fSeason    = smallField("Season",      8);
            JTextField fPrice     = smallField("Price",       6);
            JTextField fQty       = smallField("Qty",         5);
            JTextField fPlantDate = smallField("PlantDate",   10);
            JTextField fHarvest   = smallField("HarvestDate", 10);

            JButton btnAdd    = styledButton("Add Crop",          C_ACCENT);
            JButton btnUpdate = styledButton("Update Selected",   C_ACCENT2);
            JButton btnDel    = styledButton("Delete Selected",   C_ERR);
            JLabel  fStatus   = new JLabel("  ");
            fStatus.setFont(new Font("Segoe UI", Font.PLAIN, 11));

            form.add(fId); form.add(fName); form.add(fSeason); form.add(fPrice);
            form.add(fQty); form.add(fPlantDate); form.add(fHarvest);
            form.add(btnAdd); form.add(btnUpdate); form.add(btnDel); form.add(fStatus);

            // ── ADD ──────────────────────────────────────────────────────────
            btnAdd.addActionListener(e -> {
                try {
                    int id = Integer.parseInt(fId.getText().trim());
                    for (Crops c : cropList) {
                        if (c.getCropId() == id) {
                            fStatus.setText("Error: Crop ID " + id + " already exists.");
                            fStatus.setForeground(C_ERR);
                            return;
                        }
                    }
                    Crops crop = new Crops(
                            id,
                            fName.getText().trim(),
                            fSeason.getText().trim(),
                            Integer.parseInt(fPrice.getText().trim()),
                            Integer.parseInt(fQty.getText().trim()),
                            fPlantDate.getText().trim(),
                            fHarvest.getText().trim()
                    );
                    cropList.add(crop);
                    boolean dbOk = DatabaseManager.saveCrop(crop);   // calls crop.addCrop() internally
                    refreshCropTable(model);
                    Reportgenerator.logAction("Crop added: " + crop.getDetails());
                    fStatus.setText(dbOk ? "Added & saved to DB!" : "Added (DB offline)");
                    fStatus.setForeground(dbOk ? C_SUCCESS : C_WARN);
                } catch (Exception ex) {
                    fStatus.setText("Error: " + ex.getMessage()); fStatus.setForeground(C_ERR);
                }
            });

            // ── UPDATE (price, quantity, harvestDate of selected crop) ─────────
            btnUpdate.addActionListener(e -> {
                int row = table.getSelectedRow();
                if (row < 0) { fStatus.setText("Error: Select a row first."); fStatus.setForeground(C_ERR); return; }
                try {
                    int id = (int) model.getValueAt(row, 0);
                    Crops target = null;
                    for (Crops c : cropList) if (c.getCropId() == id) { target = c; break; }
                    if (target == null) { fStatus.setText("Error: Crop not found."); fStatus.setForeground(C_ERR); return; }

                    target.setPrice(Integer.parseInt(fPrice.getText().trim()));
                    target.setQuantity(Integer.parseInt(fQty.getText().trim()));
                    target.setHarvestDate(fHarvest.getText().trim());

                    boolean dbOk = DatabaseManager.updateCrop(target);  // calls crop.updateCrop() internally
                    refreshCropTable(model);
                    Reportgenerator.logAction("Crop updated: " + target.getDetails());
                    fStatus.setText(dbOk ? "Updated & saved to DB!" : "Updated (DB offline)");
                    fStatus.setForeground(dbOk ? C_SUCCESS : C_WARN);
                } catch (Exception ex) {
                    fStatus.setText("Error: " + ex.getMessage()); fStatus.setForeground(C_ERR);
                }
            });

            // ── DELETE ───────────────────────────────────────────────────────
            btnDel.addActionListener(e -> {
                int row = table.getSelectedRow();
                if (row < 0) { fStatus.setText("Error: Select a row first."); fStatus.setForeground(C_ERR); return; }
                int id = (int) model.getValueAt(row, 0);
                Crops target = null;
                for (Crops c : cropList) if (c.getCropId() == id) { target = c; break; }
                if (target != null) {
                    DatabaseManager.deleteCrop(target);   // calls crop.deleteCrop() internally
                    cropList.remove(target);
                    refreshCropTable(model);
                    Reportgenerator.logAction("Crop deleted: ID " + id);
                    fStatus.setText("Deleted."); fStatus.setForeground(C_WARN);
                }
            });

            // Pre-fill fields when a row is selected (for easy update)
            table.getSelectionModel().addListSelectionListener(e -> {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    fId.setText(model.getValueAt(row, 0).toString());
                    fName.setText(model.getValueAt(row, 1).toString());
                    fSeason.setText(model.getValueAt(row, 2).toString());
                    fPrice.setText(model.getValueAt(row, 3).toString().replace("$", ""));
                    fQty.setText(model.getValueAt(row, 4).toString());
                    fPlantDate.setText(model.getValueAt(row, 5).toString());
                    fHarvest.setText(model.getValueAt(row, 6).toString());
                    fId.setForeground(C_TEXT); fName.setForeground(C_TEXT);
                    fSeason.setForeground(C_TEXT); fPrice.setForeground(C_TEXT);
                    fQty.setForeground(C_TEXT); fPlantDate.setForeground(C_TEXT);
                    fHarvest.setForeground(C_TEXT);
                }
            });

            panel.add(form, BorderLayout.SOUTH);
        }

        return panel;
    }

    private static void refreshCropTable(DefaultTableModel m) {
        m.setRowCount(0);
        for (Crops c : cropList) {
            m.addRow(new Object[]{
                    c.getCropId(), c.getCropName(), c.getSeason(),
                    "$" + c.getPrice(), c.getQuantity(),
                    c.getPlantDate(), c.getHarvestDate(),
                    String.format("$%.2f", c.calculateRevenue())
            });
        }
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  INVENTORY PANEL — uses Inventory + ItemCategory, persisted via DatabaseManager
    // ══════════════════════════════════════════════════════════════════════════
    private static JPanel buildInventoryPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 12));
        panel.setBackground(C_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        panel.add(sectionHeading("Inventory"), BorderLayout.NORTH);

        String[] cols = {"ID","Item Name","Category","Quantity","Price($)","Min Qty","Status"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        refreshInventoryTable(model);

        JTable table = styledTable(model);
        JScrollPane scroll = new JScrollPane(table);
        styleScrollPane(scroll);
        panel.add(scroll, BorderLayout.CENTER);

        if ("MANAGER".equals(currentRole)) {
            JPanel form = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
            form.setBackground(C_PANEL);
            form.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

            JTextField fId   = smallField("ID",   4);
            JTextField fName = smallField("Name", 12);
            JComboBox<ItemCategory> fCat = new JComboBox<>(ItemCategory.values());
            fCat.setBackground(C_CARD); fCat.setForeground(C_TEXT);
            JTextField fQty  = smallField("Qty",    5);
            JTextField fPrice= smallField("Price",  6);
            JTextField fMin  = smallField("MinQty", 5);
            JButton btnAdd    = styledButton("Add Item",         C_ACCENT);
            JButton btnUpdate = styledButton("Update Selected",  C_ACCENT2);
            JButton btnDel    = styledButton("Delete Selected",  C_ERR);
            JLabel  fStatus  = new JLabel("  ");
            fStatus.setFont(new Font("Segoe UI", Font.PLAIN, 11));

            JLabel catLbl = new JLabel("Category:");
            catLbl.setForeground(C_SUBTEXT);

            form.add(fId); form.add(fName); form.add(catLbl);
            form.add(fCat); form.add(fQty); form.add(fPrice); form.add(fMin);
            form.add(btnAdd); form.add(btnUpdate); form.add(btnDel); form.add(fStatus);

            btnAdd.addActionListener(e -> {
                try {
                    int id = Integer.parseInt(fId.getText().trim());
                    for (Inventory i : inventoryList) {
                        if (i.getInventoryID() == id) {
                            fStatus.setText("Error: Item ID " + id + " already exists.");
                            fStatus.setForeground(C_ERR);
                            return;
                        }
                    }
                    Inventory item = new Inventory(
                            id,
                            fName.getText().trim(),
                            (ItemCategory) fCat.getSelectedItem(),
                            Integer.parseInt(fQty.getText().trim()),
                            Double.parseDouble(fPrice.getText().trim()),
                            Integer.parseInt(fMin.getText().trim())
                    );
                    inventoryList.add(item);
                    boolean dbOk = DatabaseManager.saveInventoryItem(item);
                    refreshInventoryTable(model);
                    checkLowStockNotifications();
                    Reportgenerator.logAction("Inventory item added: " + item.getItemName());
                    fStatus.setText(dbOk ? "Added & saved to DB!" : "Added (DB offline)");
                    fStatus.setForeground(dbOk ? C_SUCCESS : C_WARN);
                } catch (Exception ex) {
                    fStatus.setText("Error: " + ex.getMessage()); fStatus.setForeground(C_ERR);
                }
            });

            btnUpdate.addActionListener(e -> {
                int row = table.getSelectedRow();
                if (row < 0) { fStatus.setText("Error: Select a row first."); fStatus.setForeground(C_ERR); return; }
                try {
                    int id = (int) model.getValueAt(row, 0);
                    Inventory target = null;
                    for (Inventory i : inventoryList) if (i.getInventoryID() == id) { target = i; break; }
                    if (target == null) { fStatus.setText("Error: Item not found."); fStatus.setForeground(C_ERR); return; }

                    target.setQuantity(Integer.parseInt(fQty.getText().trim()));
                    target.setPrice(Double.parseDouble(fPrice.getText().trim()));
                    target.setCategory((ItemCategory) fCat.getSelectedItem());

                    boolean dbOk = DatabaseManager.updateInventoryItem(target);
                    refreshInventoryTable(model);
                    checkLowStockNotifications();
                    Reportgenerator.logAction("Inventory item updated: " + target.getItemName());
                    fStatus.setText(dbOk ? "Updated & saved to DB!" : "Updated (DB offline)");
                    fStatus.setForeground(dbOk ? C_SUCCESS : C_WARN);
                } catch (Exception ex) {
                    fStatus.setText("Error: " + ex.getMessage()); fStatus.setForeground(C_ERR);
                }
            });

            btnDel.addActionListener(e -> {
                int row = table.getSelectedRow();
                if (row < 0) { fStatus.setText("Error: Select a row first."); fStatus.setForeground(C_ERR); return; }
                int id = (int) model.getValueAt(row, 0);
                inventoryList.removeIf(i -> i.getInventoryID() == id);
                DatabaseManager.deleteInventoryItem(id);
                refreshInventoryTable(model);
                checkLowStockNotifications();
                fStatus.setText("Deleted."); fStatus.setForeground(C_WARN);
            });

            // Pre-fill on row select
            table.getSelectionModel().addListSelectionListener(e -> {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    fId.setText(model.getValueAt(row, 0).toString());
                    fName.setText(model.getValueAt(row, 1).toString());
                    fCat.setSelectedItem(model.getValueAt(row, 2));
                    fQty.setText(model.getValueAt(row, 3).toString());
                    fPrice.setText(model.getValueAt(row, 4).toString());
                    fMin.setText(model.getValueAt(row, 5).toString());
                    fId.setForeground(C_TEXT); fName.setForeground(C_TEXT);
                    fQty.setForeground(C_TEXT); fPrice.setForeground(C_TEXT);
                    fMin.setForeground(C_TEXT);
                }
            });

            panel.add(form, BorderLayout.SOUTH);
        }
        return panel;
    }

    private static void refreshInventoryTable(DefaultTableModel m) {
        m.setRowCount(0);
        for (Inventory i : inventoryList) {
            m.addRow(new Object[]{
                    i.getInventoryID(), i.getItemName(), i.getCategory(),
                    i.getQuantity(), i.getPrice(), i.getMinQuantity(),
                    i.isLowStock() ? "⚠ LOW" : "OK"
            });
        }
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  NOTIFICATIONS PANEL
    // ══════════════════════════════════════════════════════════════════════════
    private static JPanel buildNotificationsPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 12));
        panel.setBackground(C_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        panel.add(sectionHeading("Notifications"), BorderLayout.NORTH);

        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> list = new JList<>(listModel);
        list.setBackground(C_CARD);
        list.setForeground(C_TEXT);
        list.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        list.setFixedCellHeight(32);
        list.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> l, Object val,
                                                          int idx, boolean sel, boolean focus) {
                super.getListCellRendererComponent(l, val, idx, sel, focus);
                String s = val.toString();
                setBackground(sel ? C_ACCENT : (idx % 2 == 0 ? C_CARD : C_PANEL));
                setForeground(s.contains("⚠") ? C_WARN : s.contains("🚿") ? C_ACCENT2 : C_TEXT);
                setBorder(BorderFactory.createEmptyBorder(4, 12, 4, 4));
                return this;
            }
        });

        JScrollPane scroll = new JScrollPane(list);
        styleScrollPane(scroll);
        panel.add(scroll, BorderLayout.CENTER);

        JButton btnRefresh = styledButton("Refresh Notifications", C_ACCENT);
        panel.add(btnRefresh, BorderLayout.SOUTH);

        Runnable refresh = () -> {
            listModel.clear();
            checkLowStockNotifications();
            for (String n : notifications) listModel.addElement(n);
            if (listModel.isEmpty()) listModel.addElement("✅  No active alerts. All systems normal.");
        };

        refresh.run();
        btnRefresh.addActionListener(e -> refresh.run());
        return panel;
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  FARMERS PANEL (MANAGER ONLY)
    // ══════════════════════════════════════════════════════════════════════════
    private static JPanel buildFarmersPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 12));
        panel.setBackground(C_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        panel.add(sectionHeading("Farmer Management"), BorderLayout.NORTH);

        String[] cols = {"ID","Name","Phone","Address"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        refreshFarmerTable(model);

        JTable table = styledTable(model);
        JScrollPane scroll = new JScrollPane(table);
        styleScrollPane(scroll);
        panel.add(scroll, BorderLayout.CENTER);

        JPanel form = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 6));
        form.setBackground(C_PANEL);
        form.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        JTextField fId      = smallField("ID",      5);
        JTextField fName    = smallField("Name",    12);
        JTextField fPhone   = smallField("Phone",   12);
        JTextField fAddress = smallField("Address", 14);
        JButton btnAdd  = styledButton("Add Farmer",       C_ACCENT);
        JButton btnDel  = styledButton("Delete Selected",  C_ERR);
        JLabel  fStatus = new JLabel("  ");
        fStatus.setFont(new Font("Segoe UI", Font.PLAIN, 11));

        form.add(fId); form.add(fName); form.add(fPhone); form.add(fAddress);
        form.add(btnAdd); form.add(btnDel); form.add(fStatus);

        btnAdd.addActionListener(e -> {
            try {
                Farmers f = new Farmers(
                        Integer.parseInt(fId.getText().trim()),
                        fName.getText().trim(),
                        fPhone.getText().trim(),
                        fAddress.getText().trim()
                );
                farmerList.add(f);
                boolean dbOk = DatabaseManager.saveFarmer(f);
                refreshFarmerTable(model);
                Reportgenerator.logAction("Farmer added: " + f.getDetails());
                fStatus.setText(dbOk ? "Saved to DB!" : "Saved locally (DB offline)");
                fStatus.setForeground(dbOk ? C_SUCCESS : C_WARN);
                fId.setText(""); fName.setText(""); fPhone.setText(""); fAddress.setText("");
            } catch (Exception ex) {
                fStatus.setText("Error: " + ex.getMessage()); fStatus.setForeground(C_ERR);
            }
        });

        btnDel.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                farmerList.remove(row);
                refreshFarmerTable(model);
                fStatus.setText("Deleted."); fStatus.setForeground(C_WARN);
            }
        });

        panel.add(form, BorderLayout.SOUTH);
        return panel;
    }

    private static void refreshFarmerTable(DefaultTableModel m) {
        m.setRowCount(0);
        for (Farmers f : farmerList) {
            m.addRow(new Object[]{f.getFarmerId(), f.getFarmerName(), f.getPhone(), f.getAddress()});
        }
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  REPORTS PANEL (MANAGER ONLY) — uses Reportgenerator with Crops included
    // ══════════════════════════════════════════════════════════════════════════
    private static JPanel buildReportsPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 12));
        panel.setBackground(C_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        panel.add(sectionHeading("System Reports"), BorderLayout.NORTH);

        JTextArea area = new JTextArea();
        area.setFont(new Font("Consolas", Font.PLAIN, 12));
        area.setBackground(C_CARD);
        area.setForeground(C_TEXT);
        area.setEditable(false);
        area.setText("Click 'Generate Report' to produce the full system report (Farmers, Animals, Crops, Inventory, Profit).");

        JScrollPane scroll = new JScrollPane(area);
        styleScrollPane(scroll);
        panel.add(scroll, BorderLayout.CENTER);

        JButton btnGen = styledButton("Generate & Save Report", C_ACCENT);
        panel.add(btnGen, BorderLayout.SOUTH);

        btnGen.addActionListener(e -> {
            String report = Reportgenerator.generateReport(farmerList, animalList, cropList, inventoryList);
            area.setText(report);
            area.setCaretPosition(0);
        });
        return panel;
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  PROFIT PANEL (MANAGER ONLY) — includes crop revenue
    // ══════════════════════════════════════════════════════════════════════════
    private static JPanel buildProfitPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 12));
        panel.setBackground(C_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        panel.add(sectionHeading("Profit Calculator"), BorderLayout.NORTH);

        JPanel content = new JPanel(new GridLayout(1, 2, 16, 0));
        content.setOpaque(false);

        // ── Left: tabs of breakdown tables ───────────────────────────────────
        JTabbedPane breakdown = new JTabbedPane();
        breakdown.setBackground(C_HEADER);
        breakdown.setForeground(C_TEXT);

        String[] animalCols = {"Animal","Stock","Buy Price","Sell Price","Cost","Revenue","Profit"};
        DefaultTableModel animalModel = new DefaultTableModel(animalCols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        double totalCost = 0, totalRev = 0;
        for (AnimalStock a : animalList) {
            double cost = a.getTotalCost();
            double rev  = a.getPotentialRevenue();
            double prf  = a.getPotentialProfit();
            totalCost += cost;
            totalRev  += rev;
            animalModel.addRow(new Object[]{
                    a.getAnimalname(), a.getStockCount(),
                    "$" + a.getPurchasePrice(), "$" + a.getSalePrice(),
                    String.format("$%.0f", cost),
                    String.format("$%.0f", rev),
                    String.format("$%.0f", prf)
            });
        }
        JTable animalTable = styledTable(animalModel);
        JScrollPane animalScroll = new JScrollPane(animalTable);
        styleScrollPane(animalScroll);
        breakdown.addTab("🐄 Animals", animalScroll);

        String[] cropCols = {"Crop","Season","Price","Quantity","Revenue","Plant Date","Harvest Date"};
        DefaultTableModel cropModel = new DefaultTableModel(cropCols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        double cropRev = 0;
        for (Crops c : cropList) {
            cropRev += c.calculateRevenue();
            cropModel.addRow(new Object[]{
                    c.getCropName(), c.getSeason(), "$" + c.getPrice(), c.getQuantity(),
                    String.format("$%.0f", c.calculateRevenue()),
                    c.getPlantDate(), c.getHarvestDate()
            });
        }
        JTable cropTable = styledTable(cropModel);
        JScrollPane cropScroll = new JScrollPane(cropTable);
        styleScrollPane(cropScroll);
        breakdown.addTab("🌾 Crops", cropScroll);

        content.add(breakdown);

        // ── Right: summary cards ──────────────────────────────────────────────
        JPanel summary = new JPanel(new GridLayout(6, 1, 0, 10));
        summary.setOpaque(false);

        double invValue    = inventoryList.stream().mapToDouble(i -> i.getQuantity() * i.getPrice()).sum();
        double totalRevAll = totalRev + cropRev;
        double totalInvest = totalCost + invValue;
        double netProfit   = totalRevAll - totalInvest;

        summary.add(statCard("💸", "Animal Cost",     String.format("$%.0f", totalCost), C_WARN));
        summary.add(statCard("📦", "Inventory Value", String.format("$%.0f", invValue),  C_ACCENT2));
        summary.add(statCard("🐄", "Animal Revenue",  String.format("$%.0f", totalRev),  C_ACCENT));
        summary.add(statCard("🌾", "Crop Revenue",    String.format("$%.0f", cropRev),   C_ACCENT2));
        summary.add(statCard("💰", "Total Revenue",   String.format("$%.0f", totalRevAll), C_ACCENT));
        summary.add(statCard("📊", "Net Profit",      String.format("$%.0f", netProfit),
                netProfit >= 0 ? C_SUCCESS : C_ERR));

        content.add(summary);
        panel.add(content, BorderLayout.CENTER);
        return panel;
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  NOTIFICATIONS LOGIC
    // ══════════════════════════════════════════════════════════════════════════
    private static void checkLowStockNotifications() {
        notifications.clear();
        for (AnimalStock a : animalList) {
            if (a.isLowStock()) {
                notifications.add("⚠  LOW STOCK — " + a.getAnimalname() +
                        ": only " + a.getStockCount() + " left (min: " + a.getMinStockLevel() + ")");
            }
        }
        for (Inventory i : inventoryList) {
            if (i.isLowStock()) {
                notifications.add("⚠  LOW INVENTORY — " + i.getItemName() +
                        ": only " + i.getQuantity() + " units (min: " + i.getMinQuantity() + ")");
            }
        }
    }

    private static void startIrrigationNotifier() {
        javax.swing.Timer timer = new javax.swing.Timer(60_000, e -> {
            LocalTime now = LocalTime.now();
            int h = now.getHour();
            if (h == 6 || h == 12 || h == 18) {
                notifications.add("🚿  IRRIGATION TIME — Scheduled irrigation at " + h + ":00");
                JOptionPane.showMessageDialog(null,
                        "🚿 Irrigation Reminder!\nTime to irrigate the fields.",
                        "Smart Farm Alert", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        timer.start();
    }

    private static void startSensorSimulation() {
        javax.swing.Timer t = new javax.swing.Timer(3000, e -> {
            soilSensor.generateReading();
            tempSensor.generateReading();
            humSensor.generateReading();

            if (lblSoil != null)
                lblSoil.setText("<html><center><b>Soil Moisture</b><br>" +
                        "<span style='font-size:18px; color:" + toHex(soilSensor.getValue() > 50 ? C_WARN : C_ACCENT) + "'>" +
                        soilSensor.getValue() + "%</span><br><small>" + soilSensor.getStatus() + "</small></center></html>");

            if (lblTemp != null)
                lblTemp.setText("<html><center><b>Temperature</b><br>" +
                        "<span style='font-size:18px; color:" + toHex(tempSensor.getValue() > 60 ? C_ERR : C_ACCENT2) + "'>" +
                        tempSensor.getValue() + "°C</span><br><small>" + tempSensor.getStatus() + "</small></center></html>");

            if (lblHum != null)
                lblHum.setText("<html><center><b>Humidity</b><br>" +
                        "<span style='font-size:18px; color:" + toHex(humSensor.getValue() > 50 ? C_ACCENT : C_WARN) + "'>" +
                        humSensor.getValue() + "%</span><br><small>" + humSensor.getStatus() + "</small></center></html>");
        });
        t.start();
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  UI HELPERS
    // ══════════════════════════════════════════════════════════════════════════

    private static JLabel sectionHeading(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Segoe UI", Font.BOLD, 20));
        l.setForeground(C_ACCENT);
        l.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));
        return l;
    }

    private static JTextField styledField() {
        JTextField f = new JTextField(18);
        f.setBackground(C_CARD);
        f.setForeground(C_TEXT);
        f.setCaretColor(C_TEXT);
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(C_PANEL, 1),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)));
        f.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        return f;
    }

    private static void stylePasswordField(JPasswordField f) {
        f.setBackground(C_CARD);
        f.setForeground(C_TEXT);
        f.setCaretColor(C_TEXT);
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(C_PANEL, 1),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)));
        f.setFont(new Font("Segoe UI", Font.PLAIN, 13));
    }

    private static JTextField smallField(String hint, int cols) {
        JTextField f = new JTextField(cols);
        f.setBackground(C_CARD);
        f.setForeground(C_TEXT);
        f.setCaretColor(C_TEXT);
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(C_PANEL, 1),
                BorderFactory.createEmptyBorder(4, 6, 4, 6)));
        f.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        f.setToolTipText(hint);

        f.setText(hint);
        f.setForeground(C_SUBTEXT);
        f.addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) {
                if (f.getText().equals(hint)) { f.setText(""); f.setForeground(C_TEXT); }
            }
            @Override public void focusLost(FocusEvent e) {
                if (f.getText().isEmpty()) { f.setText(hint); f.setForeground(C_SUBTEXT); }
            }
        });
        return f;
    }

    private static JButton styledButton(String text, Color bg) {
        JButton b = new JButton(text);
        b.setBackground(bg);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setFont(new Font("Segoe UI", Font.BOLD, 12));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        b.addMouseListener(new MouseAdapter() {
            Color orig = bg;
            @Override public void mouseEntered(MouseEvent e) { b.setBackground(bg.brighter()); }
            @Override public void mouseExited(MouseEvent e)  { b.setBackground(orig); }
        });
        return b;
    }

    private static JTable styledTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setBackground(C_CARD);
        table.setForeground(C_TEXT);
        table.setSelectionBackground(C_ACCENT);
        table.setSelectionForeground(Color.WHITE);
        table.setGridColor(C_PANEL);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setRowHeight(26);
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(false);

        JTableHeader header = table.getTableHeader();
        header.setBackground(C_HEADER);
        header.setForeground(C_ACCENT);
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        header.setReorderingAllowed(false);

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object val,
                                                           boolean sel, boolean focus, int row, int col) {
                super.getTableCellRendererComponent(t, val, sel, focus, row, col);
                if (!sel) {
                    setBackground(row % 2 == 0 ? C_CARD : C_PANEL);
                    setForeground(val != null && val.toString().contains("⚠") ? C_WARN : C_TEXT);
                }
                setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
                return this;
            }
        });
        return table;
    }

    private static void styleScrollPane(JScrollPane sp) {
        sp.setBackground(C_BG);
        sp.getViewport().setBackground(C_CARD);
        sp.setBorder(BorderFactory.createLineBorder(C_PANEL, 1));
    }

    private static void styleTabs(JTabbedPane tabs) {
        tabs.setBackground(C_HEADER);
        tabs.setForeground(C_TEXT);
        tabs.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabs.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        UIManager.put("TabbedPane.selected",           C_PANEL);
        UIManager.put("TabbedPane.contentAreaColor",   C_BG);
        UIManager.put("TabbedPane.background",         C_HEADER);
        UIManager.put("TabbedPane.foreground",         C_TEXT);
        UIManager.put("TabbedPane.selectHighlight",    C_ACCENT);
        tabs.setOpaque(true);
    }

    private static void addRow(JPanel p, GridBagConstraints gc, int row,
                               String label, JComponent field) {
        gc.gridwidth = 1; gc.weightx = 0;
        gc.gridx = 0; gc.gridy = row;
        JLabel l = new JLabel(label);
        l.setForeground(C_SUBTEXT);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        p.add(l, gc);

        gc.gridx = 1; gc.weightx = 1;
        p.add(field, gc);
    }

    private static String toHex(Color c) {
        return String.format("#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue());
    }
}