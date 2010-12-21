package com.nervestaple.scalemonitor.form.datatable;

import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;
import java.math.BigDecimal;
import java.util.Date;

import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.Spacer;

/**
 * Provides a view for the DataTable.
 *
 * @author Christopher Miles
 * @version 1.0
 */
public class DataTableView extends JPanel {

    /**
     * Logger instance.
     */
    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * Data model.
     */
    private DataTableModel model;

    /**
     * Maximum number of rows to display.
     */
    private final static int MAX_ROWS = 1000;

    // gui form objects
    private JPanel panelMain;
    private JScrollPane scrollpaneTable;
    private JTable tableData;

    /**
     * Creates a new DataTableView.
     *
     * @param model
     */
    public DataTableView(DataTableModel model) {

        super();

        // save a reference to the model
        this.model = model;

        setupPanel();
        setupListeners();
    }

    public DataTableModel getModel() {
        return model;
    }

    // private methods

    /**
     * Sets up the listeners on the model.
     */
    private void setupListeners() {

        model.addPropertyChangeListener("columnHeaders", new PropertyChangeListener() {

            /**
             * This method gets called when a bound property is changed.
             *
             * @param evt A PropertyChangeEvent object describing the event source and the property that has changed.
             */
            public void propertyChange(PropertyChangeEvent evt) {

                if (evt.getNewValue() != null) {

                    rebuildTableData();
                }
            }
        });

        model.addPropertyChangeListener("dataRows", new PropertyChangeListener() {

            /**
             * This method gets called when a bound property is changed.
             *
             * @param evt A PropertyChangeEvent object describing the event source and the property that has changed.
             */
            public void propertyChange(PropertyChangeEvent evt) {

                if (evt.getNewValue() != null) {

                    rebuildTableData();
                }
            }
        });

        model.addDataTableModelListener(new DataTableModelListener() {

            public void columnAdded(Object column) {

                rebuildTableData();
            }

            public void columnRemoved(Object column) {

                rebuildTableData();
            }

            public void rowAdded(final Object[] row) {

                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {

                        DefaultTableModel tablemodel = (DefaultTableModel) tableData.getModel();

                        // make sure we only retain the last 1000 records
                        if (tablemodel.getRowCount() == MAX_ROWS) {
                            tablemodel.removeRow(MAX_ROWS - 1);
                        }

                        tablemodel.insertRow(0, row);
                    }
                });
            }

            public void rowRemoved(Object[] row) {

                rebuildTableData();
            }
        });
    }

    /**
     * Repopulates the table with current data.
     */
    private void rebuildTableData() {

        // create a cell renderer
        final DataTableCellRenderer cellrenderer = new DataTableCellRenderer();

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                // rebuild table
                tableData.setModel(new DefaultTableModel(model.getDataRowsArray(),
                        model.getColumnHeadersArray()));

                // set cell renderers
                tableData.getColumnModel().getColumn(2).setCellRenderer(cellrenderer);
                tableData.getColumnModel().getColumn(4).setCellRenderer(cellrenderer);

                // set column width
                tableData.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
                tableData.getColumnModel().getColumn(0).setPreferredWidth(125);
                tableData.getColumnModel().getColumn(1).setPreferredWidth(60);
                tableData.getColumnModel().getColumn(2).setPreferredWidth(75);
                tableData.getColumnModel().getColumn(3).setPreferredWidth(60);
                tableData.getColumnModel().getColumn(4).setPreferredWidth(160);
            }
        });
    }

    /**
     * Sets up the panel.
     */
    private void setupPanel() {

        // add the view to the panel
        setLayout(new BorderLayout(1, 1));
        add(panelMain);

        // stripe the table on OS X
        tableData.putClientProperty("Quaqua.Table.style", "striped");
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
        panelMain.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 3, new Insets(3, 5, 3, 5), -1, -1));
        panelMain.add(panel1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        panel1.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(-1, 12), new Dimension(-1, 12), new Dimension(-1, 12), 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer2 = new com.intellij.uiDesigner.core.Spacer();
        panel1.add(spacer2, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        scrollpaneTable = new JScrollPane();
        scrollpaneTable.setHorizontalScrollBarPolicy(31);
        scrollpaneTable.setVerticalScrollBarPolicy(22);
        panelMain.add(scrollpaneTable, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tableData = new JTable();
        tableData.setPreferredScrollableViewportSize(new Dimension(-1, -1));
        scrollpaneTable.setViewportView(tableData);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panelMain;
    }

    /**
     * Provides a cell renderer for the data table.
     *
     * @author Christopher Miles
     * @version 1.0
     */
    private class DataTableCellRenderer extends DefaultTableCellRenderer {

        SimpleDateFormat simpledateformat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
        DecimalFormat decimalformat = new DecimalFormat("0000.00");

        /**
         * Creates a new DataTableCellRender.
         */
        public DataTableCellRenderer() {
            super();
        }

        public Component getTableCellRendererComponent(JTable jtable, Object object, boolean booleanIsSelected,
                                                       boolean booleanHasFocus, int intRow, int intColumn) {

            Component component = super.getTableCellRendererComponent(jtable, object, booleanIsSelected, booleanHasFocus,
                    intRow, intColumn);

            JLabel label = (JLabel) component;

            if (object instanceof BigDecimal) {

                Number number = (Number) object;
                label.setText(decimalformat.format(number));
                label.setHorizontalAlignment(JLabel.RIGHT);
            }

            if (object instanceof Date) {

                Date date = (Date) object;

                if (date.getTime() > 0) {

                    label.setText(simpledateformat.format(date));
                } else {

                    label.setText("");
                }
            }

            return ((Component) label);
        }
    }
}
