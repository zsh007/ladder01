import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Date;
 
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.table.DefaultTableModel;
 
public class Test extends JFrame {
    /**
     *
     */
    private static final long serialVersionUID = -6263975104443132420L;
 
    /**
     * �Զ�����չ��������
     *
     * @author ����ξ
     */
    private final class CustomFilter implements java.io.FileFilter {
        @Override
        public boolean accept(File pathname) {
            // ��ȡ�û����õ�ָ����չ��
            String extName = extNameField.getText();
            if (extName == null || extName.isEmpty())
                return false;
            if (!extName.startsWith("."))// �ж���չ��ǰ׺
                extName = "." + extName;// ������չ��ǰ׺
            extName = extName.toLowerCase();
            // �ж���չ��������ļ����Ƿ����Ҫ��
            if (pathname.getName().toLowerCase().endsWith(extName))
                return true;
            return false;
        }
    }
     
    private JPanel contentPane;
    private JTextField extNameField;
    private JTable table;
    private File dir;
    private JLabel label;
     
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Test frame = new Test();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
     
    /**
     * Create the frame.
     */
    public Test() {
        setTitle("��ʾָ�����͵��ļ�");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
         
        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.NORTH);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[] { 93, 54, 0 };
        gbl_panel.rowHeights = new int[] { 23, 0, 0 };
        gbl_panel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
        gbl_panel.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
        panel.setLayout(gbl_panel);
         
        JButton button = new JButton("ѡ���ļ���");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                do_button_actionPerformed(e);
            }
        });
        GridBagConstraints gbc_button = new GridBagConstraints();
        gbc_button.anchor = GridBagConstraints.NORTH;
        gbc_button.gridx = 0;
        gbc_button.gridy = 0;
        panel.add(button, gbc_button);
         
        label = new JLabel("�ļ���");
        GridBagConstraints gbc_label = new GridBagConstraints();
        gbc_label.fill = GridBagConstraints.HORIZONTAL;
        gbc_label.gridx = 1;
        gbc_label.gridy = 0;
        panel.add(label, gbc_label);
         
        JLabel label_1 = new JLabel("����ָ���ļ���չ���ƣ�");
        GridBagConstraints gbc_label_1 = new GridBagConstraints();
        gbc_label_1.anchor = GridBagConstraints.EAST;
        gbc_label_1.insets = new Insets(0, 0, 0, 5);
        gbc_label_1.gridx = 0;
        gbc_label_1.gridy = 1;
        panel.add(label_1, gbc_label_1);
         
        extNameField = new JTextField();
        extNameField.addCaretListener(new CaretListener() {
            public void caretUpdate(CaretEvent e) {
                do_extNameField_caretUpdate(e);
            }
        });
        extNameField.setText(".gif");
        GridBagConstraints gbc_extNameField = new GridBagConstraints();
        gbc_extNameField.insets = new Insets(0, 0, 5, 0);
        gbc_extNameField.fill = GridBagConstraints.HORIZONTAL;
        gbc_extNameField.gridx = 1;
        gbc_extNameField.gridy = 1;
        panel.add(extNameField, gbc_extNameField);
        extNameField.setColumns(10);
         
        JScrollPane scrollPane = new JScrollPane();
        contentPane.add(scrollPane, BorderLayout.CENTER);
         
        table = new JTable();
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setModel(new DefaultTableModel(new Object[][] {}, new String[] {"�ļ�����", "�ļ���С","�޸�����" }) {
            /**
                     *
                     */
                    private static final long serialVersionUID = 5274214559103654856L;
            boolean[] columnEditables = new boolean[] { false, false, false };
             
            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }
        });
        table.getColumnModel().getColumn(0).setPreferredWidth(220);
        table.getColumnModel().getColumn(1).setPreferredWidth(85);
        table.getColumnModel().getColumn(2).setPreferredWidth(110);
        scrollPane.setViewportView(table);
    }
     
    /**
     * ѡ���ļ��а�ť���¼�������
     *
     * @param e
     */
    protected void do_button_actionPerformed(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();// �����ļ�ѡ����
        // ����ѡ�����Ĺ�����
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.showDialog(this, null);
        dir = chooser.getSelectedFile();
        getLabel().setText(dir.toString());
        // ��ȡ���˺�ķ����������ļ�����
        listFiles();
    }
     
    /**
     * ��ʾ�ļ����е��ļ�
     */
    private void listFiles() {
        if (dir == null)
            return;
        // ��ȡ�����������ļ�����
        File[] files = dir.listFiles(new CustomFilter());
        // ��ȡ��������ģ��
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        for (File file : files) {// �����ļ�����
            // �������������
            Object[] row = { file.getName(), file.length(),
                    new Date(file.lastModified()) };
            model.addRow(row);// ��������ݵ����ģ��
        }
    }
     
    protected void do_extNameField_caretUpdate(CaretEvent e) {
        listFiles();
    }
     
    protected JLabel getLabel() {
        return label;
    }
}