package com.nervestaple.scalemonitor.form.portlist;

import com.nervestaple.scalemonitor.ScaleType;
import com.nervestaple.scalemonitor.form.portlist.portinfo.PortInfoController;
import com.nervestaple.utility.Platform;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.GridConstraints;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides a view that displays all of the available ports.
 *
 * @author Christopher Miles
 * @version 2.0
 */
public class PortListView2 extends JPanel {

    /**
     * Logger instance.
     */
    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * Model for this view.
     */
    private PortListModel model;

    /**
     * Table column headers.
     */
    private final String[] columnHeaders = new String[]{
            "Monitor",
            "Name",
            "Scale Type",
            "+/-"
    };

    /**
     * List to hold the PortInfoView objects.
     */
    private List portInfoControllerList;

    // gui form objects
    private JTable tablePorts;
    private JPanel panelMain;
    private JButton buttonOkay;
    private JScrollPane scrollpanePorts;
    private JButton buttonAddTestPort;
    private JLabel textfieldHeading;
    private JLabel textfieldSubhead1;

    public PortListView2(PortListModel model) {

        super();

        // save a reference to the model
        this.model = model;

        // create a list to hold the views
        portInfoControllerList = new ArrayList();

        setupPanel();

        initializeListeners();
    }

    public void addNewPortController(final PortInfoController portinfocontroller) {

        // add the row to the table
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                logger.debug("Adding a PortInfoView to the table");

                // add the port to our list
                portInfoControllerList.add(portinfocontroller);

                // create a new row for the table
                ScaleType[] scaletypeArray = (ScaleType[]) ScaleType.getEnumList().toArray(
                        new ScaleType[ScaleType.getEnumList().size()]);

                DefaultTableModel model = ((DefaultTableModel) tablePorts.getModel());
                model.insertRow(model.getRowCount(), new Object[]{
                        new Boolean(false), portinfocontroller.getModel().getName(),
                        scaletypeArray[0], new Double(0.0)
                });
            }
        });
    }

    // private methods

    private void initializeListeners() {

        initializeModelListeners();
        initializeFieldListeners();
    }

    private void initializeFieldListeners() {

        buttonOkay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                // update the model with the table info
                updatePortInfoControllerModels();

                if (model.getListenerOkay() != null) {
                    model.getListenerOkay().actionPerformed(e);
                }
            }
        });

        buttonAddTestPort.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (model.getListenerAddTestPort() != null) {
                    model.getListenerAddTestPort().actionPerformed(event);
                }
            }
        });
    }

    private void updatePortInfoControllerModels() {

        // get the number of rows in the table
        int rows = tablePorts.getModel().getRowCount();

        // loop through the table rows
        for (int index = 0; index < rows; index++) {

            // get the port info controller with the same index
            PortInfoController portinfocontroller = (PortInfoController) portInfoControllerList.get(index);

            // update the values on the port info controller's model
            portinfocontroller.getModel().setMonitor(
                    (Boolean) tablePorts.getModel().getValueAt(index, 0));
            portinfocontroller.getModel().setName((String) tablePorts.getModel().getValueAt(index, 1));
            portinfocontroller.getModel().setScaletype(
                    (ScaleType) tablePorts.getModel().getValueAt(index, 2));
            portinfocontroller.getModel().setTare((Double) tablePorts.getModel().getValueAt(index, 3));
            logger.debug((Boolean) tablePorts.getModel().getValueAt(index, 0) + ", " +
                    (String) tablePorts.getModel().getValueAt(index, 1) + ", " +
                    (ScaleType) tablePorts.getModel().getValueAt(index, 2) + ", " +
                    (Double) tablePorts.getModel().getValueAt(index, 3));
        }
    }

    private void initializeModelListeners() {

        model.addPropertyChangeListener("ports", new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent event) {

                populatePortTable((List) event.getNewValue());
            }
        });
    }

    private void populatePortTable(List ports) {

        logger.debug("Filling ports table");

        // clear our list of ports
        portInfoControllerList.clear();

        // create our table model
        DefaultTableModel tablemodel = new DefaultTableModel() {

            public Class getColumnClass(int columnIndex) {

                Object o = getValueAt(0, columnIndex);

                if (o == null) {
                    return Object.class;
                } else {
                    return o.getClass();
                }
            }
        };

        // set the headers for the model
        tablemodel.setColumnIdentifiers(columnHeaders);

        // pass the model to the table
        tablePorts.setModel(tablemodel);

        // set combo box renderer for the third column
        TableColumn tablecolumn = tablePorts.getColumnModel().getColumn(2);
        tablecolumn.setCellEditor(new ComboBoxEditor(ScaleType.getEnumList().toArray(
                new ScaleType[ScaleType.getEnumList().size()])));
        tablecolumn.setCellRenderer(new ComboBoxRenderer(ScaleType.getEnumList().toArray(
                new ScaleType[ScaleType.getEnumList().size()])));

        // set column width
        tablePorts.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        tablePorts.getColumnModel().getColumn(0).setPreferredWidth(55);
        tablePorts.getColumnModel().getColumn(1).setPreferredWidth(100);
        tablePorts.getColumnModel().getColumn(2).setPreferredWidth(160);
        tablePorts.getColumnModel().getColumn(3).setPreferredWidth(41);

        // get an array of ports
        final PortInfoController[] portinfocontrollerArray =
                (PortInfoController[]) ports.toArray(new PortInfoController[ports.size()]);

        // loop through the port array
        for (int index = 0; index < portinfocontrollerArray.length; index++) {

            // get the next port
            final PortInfoController portinfocontroller = portinfocontrollerArray[index];

            addNewPortController(portinfocontroller);
        }
    }

    private void setupPanel() {

        // remove table borders
        tablePorts.setBorder(BorderFactory.createEmptyBorder());

        // set the model for the table
        tablePorts.setModel(new DefaultTableModel(columnHeaders, 0));

        if (Platform.checkMacintosh()) {

            // stripe the table on OS X
            tablePorts.putClientProperty("Quaqua.Table.style", "striped");
        }

        // set the table's height
        tablePorts.setRowHeight(tablePorts.getFontMetrics(tablePorts.getFont()).getHeight() + 5);

        // set the layout and add the panel
        setLayout(new GridLayout(1, 1));
        add(panelMain);

        // set the font sizes on the panel
        textfieldHeading.setFont(textfieldHeading.getFont().deriveFont(Font.BOLD, 18));
        textfieldSubhead1.setFont(textfieldHeading.getFont().deriveFont(Font.PLAIN, 10));
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panelMain = new JPanel();
        panelMain.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 1, new Insets(5, 5, 5, 5), -1, -1));
        textfieldHeading = new JLabel();
        textfieldHeading.setText("Select Ports to Monitor");
        panelMain.add(textfieldHeading, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(5, 0, 0, 0), -1, -1));
        panelMain.add(panel1, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        textfieldSubhead1 = new JLabel();
        textfieldSubhead1.setText("Check the box to monitor the port. ");
        panel1.add(textfieldSubhead1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panelMain.add(panel2, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonAddTestPort = new JButton();
        buttonAddTestPort.setText("Add a test port");
        panel2.add(buttonAddTestPort, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        scrollpanePorts = new JScrollPane();
        scrollpanePorts.setVerticalScrollBarPolicy(22);
        panel2.add(scrollpanePorts, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(-1, 225), null, null, 0, false));
        tablePorts = new JTable();
        tablePorts.setPreferredScrollableViewportSize(new Dimension(-1, -1));
        scrollpanePorts.setViewportView(tablePorts);
        buttonOkay = new JButton();
        buttonOkay.setText("Okay");
        panelMain.add(buttonOkay, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panelMain;
    }

    public class ComboBoxRenderer extends JComboBox implements TableCellRenderer {
        public ComboBoxRenderer(Object[] items) {
            super(items);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {

            // Select the current value
            setSelectedItem(value);
            return this;
        }
    }

    public class ComboBoxEditor extends DefaultCellEditor {

        public ComboBoxEditor(Object[] items) {

            super(new JComboBox(items));
        }
    }

}
