import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

// ─────────────────────────────────────────────
//  INTERFACE
// ─────────────────────────────────────────────
interface StaffDetails {
    void displayDetails();
    String getRole();
}

// ─────────────────────────────────────────────
//  ABSTRACT CLASS
// ─────────────────────────────────────────────
abstract class Staff implements StaffDetails {
    protected int    staffId;
    protected String name;
    protected int    age;
    protected String gender;
    protected double salary;

    public Staff(int staffId, String name, int age, String gender, double salary) {
        this.staffId = staffId;
        this.name    = name;
        this.age     = age;
        this.gender  = gender;
        this.salary  = salary;
    }

    // Getters
    public int    getStaffId() { return staffId; }
    public String getName()    { return name;    }
    public int    getAge()     { return age;     }
    public String getGender()  { return gender;  }
    public double getSalary()  { return salary;  }

    @Override
    public abstract void   displayDetails();
    @Override
    public abstract String getRole();
}

// ─────────────────────────────────────────────
//  DOCTOR CLASS  (extends Staff)
// ─────────────────────────────────────────────
class Doctor extends Staff {
    private String specialization;
    private int    experienceYears;
    private String licenseNumber;
    private String department;

    public Doctor(int staffId, String name, int age, String gender,
                  double salary, String specialization,
                  int experienceYears, String licenseNumber, String department) {
        super(staffId, name, age, gender, salary);
        this.specialization  = specialization;
        this.experienceYears = experienceYears;
        this.licenseNumber   = licenseNumber;
        this.department      = department;
    }

    // Doctor-specific getters
    public String getSpecialization()  { return specialization;  }
    public int    getExperienceYears() { return experienceYears; }
    public String getLicenseNumber()   { return licenseNumber;   }
    public String getDepartment()      { return department;      }

    @Override
    public String getRole() { return "Doctor"; }

    @Override
    public void displayDetails() {
        System.out.println("=========================================");
        System.out.println("           DOCTOR DETAILS");
        System.out.println("=========================================");
        System.out.println("Staff ID       : " + staffId);
        System.out.println("Name           : Dr. " + name);
        System.out.println("Age            : " + age);
        System.out.println("Gender         : " + gender);
        System.out.println("Salary         : $" + String.format("%.2f", salary));
        System.out.println("Specialization : " + specialization);
        System.out.println("Experience     : " + experienceYears + " years");
        System.out.println("License No.    : " + licenseNumber);
        System.out.println("Department     : " + department);
        System.out.println("Role           : " + getRole());
        System.out.println("=========================================");
    }

    public Object[] toTableRow() {
        return new Object[]{
            staffId, "Dr. " + name, age, gender,
            String.format("$%.2f", salary),
            specialization, experienceYears + " yrs",
            licenseNumber, department, getRole()
        };
    }
}

// ─────────────────────────────────────────────
//  MAIN GUI CLASS
// ─────────────────────────────────────────────
public class HospitalManagementSystem extends JFrame {

    // ── palette ──────────────────────────────
    private static final Color BG        = new Color(0x0D1117);
    private static final Color PANEL_BG  = new Color(0x161B22);
    private static final Color CARD_BG   = new Color(0x1C2333);
    private static final Color ACCENT    = new Color(0x00B4D8);
    private static final Color ACCENT2   = new Color(0x0077B6);
    private static final Color SUCCESS   = new Color(0x28A745);
    private static final Color DANGER    = new Color(0xDC3545);
    private static final Color WARNING   = new Color(0xFFC107);
    private static final Color FG        = new Color(0xE6EDF3);
    private static final Color FG_MUTED  = new Color(0x8B949E);
    private static final Color BORDER_C  = new Color(0x30363D);
    private static final Color INPUT_BG  = new Color(0x0D1117);

    // ── fonts ────────────────────────────────
    private static final Font FONT_H1   = new Font("Segoe UI", Font.BOLD,  22);
    private static final Font FONT_H2   = new Font("Segoe UI", Font.BOLD,  15);
    private static final Font FONT_BODY = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FONT_MONO = new Font("Consolas", Font.PLAIN, 12);

    // ── data ─────────────────────────────────
    private final List<Doctor> doctors = new ArrayList<>();
    private DefaultTableModel  tableModel;

    // ── form fields ───────────────────────────
    private JTextField tfId, tfName, tfAge, tfSalary;
    private JTextField tfSpec, tfExp, tfLicense, tfDept;
    private JComboBox<String> cbGender;
    private JTable     table;
    private JTextArea  taDetails;

    // ─────────────────────────────────────────
    public HospitalManagementSystem() {
        super("🏥  Hospital Management System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1280, 760);
        setMinimumSize(new Dimension(1100, 650));
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG);
        setLayout(new BorderLayout(0, 0));

        add(buildHeader(),  BorderLayout.NORTH);
        add(buildCenter(),  BorderLayout.CENTER);
        add(buildFooter(),  BorderLayout.SOUTH);

        seedSampleData();
        setVisible(true);
    }

    // ══════════════════════════════════════════
    //  HEADER
    // ══════════════════════════════════════════
    private JPanel buildHeader() {
        JPanel hdr = new JPanel(new BorderLayout());
        hdr.setBackground(PANEL_BG);
        hdr.setBorder(BorderFactory.createCompoundBorder(
            new MatteBorder(0, 0, 1, 0, BORDER_C),
            new EmptyBorder(14, 24, 14, 24)));

        JLabel title = new JLabel("🏥  Hospital Management System");
        title.setFont(FONT_H1);
        title.setForeground(ACCENT);

        JLabel sub = new JLabel("Doctor Staff Registry  •  Java OOP Demo");
        sub.setFont(FONT_BODY);
        sub.setForeground(FG_MUTED);

        JPanel left = new JPanel();
        left.setOpaque(false);
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.add(title);
        left.add(Box.createVerticalStrut(2));
        left.add(sub);

        // stat chips
        JPanel stats = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        stats.setOpaque(false);
        stats.add(chip("Staff Interface", new Color(0x3D8B37)));
        stats.add(chip("Abstract Class", ACCENT2));
        stats.add(chip("OOP Inheritance", WARNING));

        hdr.add(left,  BorderLayout.WEST);
        hdr.add(stats, BorderLayout.EAST);
        return hdr;
    }

    private JLabel chip(String text, Color color) {
        JLabel lbl = new JLabel("  " + text + "  ");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lbl.setForeground(Color.WHITE);
        lbl.setBackground(color);
        lbl.setOpaque(true);
        lbl.setBorder(new EmptyBorder(4, 8, 4, 8));
        return lbl;
    }

    // ══════════════════════════════════════════
    //  CENTER  (form + table + detail)
    // ══════════════════════════════════════════
    private JPanel buildCenter() {
        JPanel center = new JPanel(new BorderLayout(10, 10));
        center.setBackground(BG);
        center.setBorder(new EmptyBorder(12, 16, 8, 16));

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                                          buildFormPanel(),
                                          buildRightPanel());
        split.setDividerLocation(360);
        split.setDividerSize(6);
        split.setBackground(BG);
        split.setBorder(null);

        center.add(split, BorderLayout.CENTER);
        return center;
    }

    // ── LEFT: FORM ────────────────────────────
    private JScrollPane buildFormPanel() {
        JPanel form = new JPanel();
        form.setBackground(PANEL_BG);
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBorder(new CompoundBorder(
            new LineBorder(BORDER_C, 1, true),
            new EmptyBorder(16, 16, 16, 16)));

        form.add(sectionLabel("👤  Staff Information"));
        form.add(Box.createVerticalStrut(8));

        tfId     = addField(form, "Staff ID",     "e.g. 101");
        tfName   = addField(form, "Full Name",    "e.g. Sarah Connor");
        tfAge    = addField(form, "Age",           "e.g. 35");
        cbGender = addCombo(form, "Gender",        new String[]{"Female","Male","Other"});
        tfSalary = addField(form, "Salary ($)",    "e.g. 95000");

        form.add(Box.createVerticalStrut(14));
        form.add(sectionLabel("🩺  Doctor Details"));
        form.add(Box.createVerticalStrut(8));

        tfSpec    = addField(form, "Specialization", "e.g. Cardiology");
        tfExp     = addField(form, "Experience (yrs)","e.g. 8");
        tfLicense = addField(form, "License Number",  "e.g. LIC-2024-001");
        tfDept    = addField(form, "Department",      "e.g. Cardiac ICU");

        form.add(Box.createVerticalStrut(18));
        form.add(buildButtons());

        JScrollPane sp = new JScrollPane(form);
        sp.setBackground(BG);
        sp.getViewport().setBackground(PANEL_BG);
        sp.setBorder(null);
        return sp;
    }

    private JLabel sectionLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(FONT_H2);
        lbl.setForeground(ACCENT);
        lbl.setAlignmentX(LEFT_ALIGNMENT);
        return lbl;
    }

    private JTextField addField(JPanel parent, String label, String placeholder) {
        JPanel row = new JPanel(new BorderLayout(0, 4));
        row.setOpaque(false);
        row.setAlignmentX(LEFT_ALIGNMENT);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 62));

        JLabel lbl = new JLabel(label);
        lbl.setFont(FONT_BODY);
        lbl.setForeground(FG_MUTED);

        JTextField tf = new JTextField();
        tf.setBackground(INPUT_BG);
        tf.setForeground(FG);
        tf.setCaretColor(ACCENT);
        tf.setFont(FONT_BODY);
        tf.setBorder(new CompoundBorder(
            new LineBorder(BORDER_C, 1, true),
            new EmptyBorder(6, 10, 6, 10)));
        tf.setToolTipText(placeholder);

        row.add(lbl, BorderLayout.NORTH);
        row.add(tf,  BorderLayout.CENTER);
        parent.add(row);
        parent.add(Box.createVerticalStrut(6));
        return tf;
    }

    private JComboBox<String> addCombo(JPanel parent, String label, String[] items) {
        JPanel row = new JPanel(new BorderLayout(0, 4));
        row.setOpaque(false);
        row.setAlignmentX(LEFT_ALIGNMENT);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 62));

        JLabel lbl = new JLabel(label);
        lbl.setFont(FONT_BODY);
        lbl.setForeground(FG_MUTED);

        JComboBox<String> cb = new JComboBox<>(items);
        cb.setBackground(INPUT_BG);
        cb.setForeground(FG);
        cb.setFont(FONT_BODY);

        row.add(lbl, BorderLayout.NORTH);
        row.add(cb,  BorderLayout.CENTER);
        parent.add(row);
        parent.add(Box.createVerticalStrut(6));
        return cb;
    }

    // ── BUTTONS ───────────────────────────────
    private JPanel buildButtons() {
        JPanel p = new JPanel(new GridLayout(2, 2, 8, 8));
        p.setOpaque(false);
        p.setAlignmentX(LEFT_ALIGNMENT);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));

        p.add(btn("➕  Add Doctor",    SUCCESS, e -> addDoctor()));
        p.add(btn("🔍  Display Info",  ACCENT,  e -> displaySelected()));
        p.add(btn("🗑️  Remove",        DANGER,  e -> removeSelected()));
        p.add(btn("🔄  Clear Form",    FG_MUTED, e -> clearForm()));

        return p;
    }

    private JButton btn(String text, Color color, ActionListener al) {
        JButton b = new JButton(text);
        b.setFont(new Font("Segoe UI", Font.BOLD, 12));
        b.setBackground(color);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.addActionListener(al);

        b.addMouseListener(new MouseAdapter() {
            Color orig = color;
            public void mouseEntered(MouseEvent e) {
                b.setBackground(orig.brighter());
            }
            public void mouseExited(MouseEvent e) {
                b.setBackground(orig);
            }
        });
        return b;
    }

    // ── RIGHT PANEL  (table + detail) ─────────
    private JPanel buildRightPanel() {
        JPanel right = new JPanel(new BorderLayout(0, 10));
        right.setBackground(BG);

        right.add(buildTablePanel(),  BorderLayout.CENTER);
        right.add(buildDetailPanel(), BorderLayout.SOUTH);
        return right;
    }

    private JPanel buildTablePanel() {
        String[] cols = {"ID","Name","Age","Gender","Salary",
                         "Specialization","Experience","License","Department","Role"};
        tableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);
        styleTable();

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) syncDetailPane();
        });

        JScrollPane sp = new JScrollPane(table);
        sp.setBackground(PANEL_BG);
        sp.getViewport().setBackground(PANEL_BG);
        sp.setBorder(new LineBorder(BORDER_C, 1, true));

        JPanel wrap = new JPanel(new BorderLayout());
        wrap.setBackground(BG);

        JLabel lbl = new JLabel("  📋  Doctor Registry");
        lbl.setFont(FONT_H2);
        lbl.setForeground(FG);
        lbl.setBorder(new EmptyBorder(0, 0, 6, 0));
        wrap.add(lbl, BorderLayout.NORTH);
        wrap.add(sp,  BorderLayout.CENTER);
        return wrap;
    }

    private void styleTable() {
        table.setBackground(PANEL_BG);
        table.setForeground(FG);
        table.setFont(FONT_MONO);
        table.setRowHeight(28);
        table.setGridColor(BORDER_C);
        table.setSelectionBackground(ACCENT2);
        table.setSelectionForeground(Color.WHITE);
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(false);
        table.setIntercellSpacing(new Dimension(0, 1));

        JTableHeader th = table.getTableHeader();
        th.setBackground(CARD_BG);
        th.setForeground(ACCENT);
        th.setFont(new Font("Segoe UI", Font.BOLD, 12));
        th.setBorder(new MatteBorder(0, 0, 1, 0, ACCENT));
        th.setReorderingAllowed(false);

        // column widths
        int[] widths = {40, 130, 40, 60, 80, 110, 80, 110, 100, 60};
        for (int i = 0; i < widths.length; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
        }

        // alternating rows
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object v,
                    boolean sel, boolean foc, int row, int col) {
                super.getTableCellRendererComponent(t, v, sel, foc, row, col);
                setForeground(sel ? Color.WHITE : FG);
                setBackground(sel ? ACCENT2 : (row % 2 == 0 ? PANEL_BG : CARD_BG));
                setBorder(new EmptyBorder(0, 6, 0, 6));
                return this;
            }
        });
    }

    private JPanel buildDetailPanel() {
        JPanel p = new JPanel(new BorderLayout(0, 6));
        p.setBackground(BG);
        p.setPreferredSize(new Dimension(0, 170));

        JLabel lbl = new JLabel("  🗒️  Doctor Detail View  (select a row above)");
        lbl.setFont(FONT_H2);
        lbl.setForeground(FG);
        p.add(lbl, BorderLayout.NORTH);

        taDetails = new JTextArea(6, 30);
        taDetails.setEditable(false);
        taDetails.setBackground(CARD_BG);
        taDetails.setForeground(ACCENT);
        taDetails.setFont(FONT_MONO);
        taDetails.setBorder(new EmptyBorder(10, 12, 10, 12));
        taDetails.setText("  Select a doctor from the table to view full details here...");

        JScrollPane sp = new JScrollPane(taDetails);
        sp.setBorder(new LineBorder(BORDER_C, 1, true));
        p.add(sp, BorderLayout.CENTER);
        return p;
    }

    // ══════════════════════════════════════════
    //  FOOTER
    // ══════════════════════════════════════════
    private JPanel buildFooter() {
        JPanel f = new JPanel(new BorderLayout());
        f.setBackground(PANEL_BG);
        f.setBorder(new CompoundBorder(
            new MatteBorder(1, 0, 0, 0, BORDER_C),
            new EmptyBorder(7, 16, 7, 16)));

        JLabel lbl = new JLabel(
            "Java OOP  •  abstract Staff  ←extends—  Doctor  •  implements StaffDetails interface");
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lbl.setForeground(FG_MUTED);

        JLabel count = new JLabel("Doctors: " + doctors.size());
        count.setFont(new Font("Segoe UI", Font.BOLD, 11));
        count.setForeground(ACCENT);

        f.add(lbl,   BorderLayout.WEST);
        f.add(count, BorderLayout.EAST);
        return f;
    }

    // ══════════════════════════════════════════
    //  ACTIONS
    // ══════════════════════════════════════════
    private void addDoctor() {
        try {
            if (tfId.getText().trim().isEmpty() || tfName.getText().trim().isEmpty())
                throw new IllegalArgumentException("Staff ID and Name are required.");

            int    id   = Integer.parseInt(tfId.getText().trim());
            String name = tfName.getText().trim();
            int    age  = Integer.parseInt(tfAge.getText().trim());
            String gen  = (String) cbGender.getSelectedItem();
            double sal  = Double.parseDouble(tfSalary.getText().trim());
            String spec = tfSpec.getText().trim();
            int    exp  = Integer.parseInt(tfExp.getText().trim());
            String lic  = tfLicense.getText().trim();
            String dept = tfDept.getText().trim();

            // check duplicate ID
            for (Doctor d : doctors)
                if (d.getStaffId() == id)
                    throw new IllegalArgumentException("Staff ID " + id + " already exists.");

            Doctor doc = new Doctor(id, name, age, gen, sal, spec, exp, lic, dept);
            doctors.add(doc);
            tableModel.addRow(doc.toTableRow());
            doc.displayDetails();   // also prints to console
            clearForm();
            updateFooter();
            showInfo("Doctor added successfully!\n\nDr. " + name +
                     " — " + spec + " (" + exp + " yrs exp)");

        } catch (NumberFormatException ex) {
            showError("Please enter valid numbers for ID, Age, Salary and Experience.");
        } catch (IllegalArgumentException ex) {
            showError(ex.getMessage());
        }
    }

    private void displaySelected() {
        int row = table.getSelectedRow();
        if (row < 0) { showError("Please select a doctor row first."); return; }
        syncDetailPane();

        Doctor d = doctors.get(row);
        String msg = String.format(
            "Staff ID       : %d\nName           : Dr. %s\nAge            : %d\n" +
            "Gender         : %s\nSalary         : $%.2f\n" +
            "Specialization : %s\nExperience     : %d years\n" +
            "License No.    : %s\nDepartment     : %s\nRole           : %s",
            d.getStaffId(), d.getName(), d.getAge(), d.getGender(), d.getSalary(),
            d.getSpecialization(), d.getExperienceYears(),
            d.getLicenseNumber(), d.getDepartment(), d.getRole());

        JOptionPane.showMessageDialog(this, msg,
            "Doctor Info — Dr. " + d.getName(),
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void removeSelected() {
        int row = table.getSelectedRow();
        if (row < 0) { showError("Please select a doctor to remove."); return; }
        Doctor d = doctors.get(row);
        int ans = JOptionPane.showConfirmDialog(this,
            "Remove Dr. " + d.getName() + " from the registry?",
            "Confirm Removal", JOptionPane.YES_NO_OPTION);
        if (ans == JOptionPane.YES_OPTION) {
            doctors.remove(row);
            tableModel.removeRow(row);
            taDetails.setText("  No doctor selected.");
            updateFooter();
        }
    }

    private void clearForm() {
        tfId.setText(""); tfName.setText(""); tfAge.setText("");
        tfSalary.setText(""); tfSpec.setText(""); tfExp.setText("");
        tfLicense.setText(""); tfDept.setText("");
        cbGender.setSelectedIndex(0);
    }

    private void syncDetailPane() {
        int row = table.getSelectedRow();
        if (row < 0 || row >= doctors.size()) return;
        Doctor d = doctors.get(row);
        taDetails.setText(String.format(
            "  ══════════════════════════════════════════════════════════\n" +
            "   DOCTOR RECORD  —  displayDetails() via StaffDetails interface\n" +
            "  ══════════════════════════════════════════════════════════\n" +
            "   Staff ID       : %d          Role   : %s\n" +
            "   Name           : Dr. %-20s  Age    : %d  |  Gender: %s\n" +
            "   Salary         : $%-12.2f       Department : %s\n" +
            "   Specialization : %-22s  Experience : %d years\n" +
            "   License No.    : %s\n" +
            "  ══════════════════════════════════════════════════════════",
            d.getStaffId(), d.getRole(), d.getName(), d.getAge(), d.getGender(),
            d.getSalary(), d.getDepartment(),
            d.getSpecialization(), d.getExperienceYears(), d.getLicenseNumber()));
    }

    private void updateFooter() {
        // refresh footer count label
        Component[] comps = ((JPanel)getContentPane().getComponent(2)).getComponents();
        for (Component c : comps)
            if (c instanceof JLabel && ((JLabel)c).getText().startsWith("Doctors:"))
                ((JLabel)c).setText("Doctors: " + doctors.size());
        repaint();
    }

    // ══════════════════════════════════════════
    //  SEED DATA
    // ══════════════════════════════════════════
    private void seedSampleData() {
        Doctor[] samples = {
            new Doctor(101,"Ananya Sharma",42,"Female",115000,"Cardiology",12,"LIC-2012-001","Cardiac ICU"),
            new Doctor(102,"James Whitfield",38,"Male",  98500,"Neurology", 10,"LIC-2014-002","Neuro Wing"),
            new Doctor(103,"Priya Nair",     35,"Female",87000, "Pediatrics", 8,"LIC-2016-003","Pediatric OPD"),
            new Doctor(104,"Carlos Mendez",  50,"Male", 130000,"Oncology",  18,"LIC-2006-004","Oncology Ward"),
        };
        for (Doctor d : samples) {
            doctors.add(d);
            tableModel.addRow(d.toTableRow());
            d.displayDetails();
        }
        updateFooter();
    }

    // ══════════════════════════════════════════
    //  HELPERS
    // ══════════════════════════════════════════
    private void showInfo(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Success ✔", JOptionPane.INFORMATION_MESSAGE);
    }
    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error ✗", JOptionPane.ERROR_MESSAGE);
    }

    // ══════════════════════════════════════════
    //  ENTRY POINT
    // ══════════════════════════════════════════
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(HospitalManagementSystem::new);
    }
}
