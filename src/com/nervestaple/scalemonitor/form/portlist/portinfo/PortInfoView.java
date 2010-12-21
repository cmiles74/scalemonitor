package com.nervestaple.scalemonitor.form.portlist.portinfo;

import com.nervestaple.scalemonitor.ScaleType;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.GridConstraints;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

/**
 * Provides a view for PortInfo.
 *
 * @author Christopher Miles
 * @version 1.0
 */
public class PortInfoView extends JPanel {

    /**
     * Logger instance.
     */
    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * Model for this view.
     */
    private PortInfoModel model;

    /**
     * Format for tare.
     */
    private NumberFormat formatTare = new DecimalFormat("##0.0");

    // gui objects
    private JPanel panelMain;
    private JComboBox comboboxType;
    private JTextField textfieldName;
    private JCheckBox checkboxMonitor;
    private JFormattedTextField textfieldTare;

    /**
     * Creates a new PortInfoView.
     *
     * @param model
     */
    public PortInfoView(PortInfoModel model) {

        super();

        // save a reference to the model
        this.model = model;

        setupPanel();

        initializeListeners();
    }

    // private methods

    /**
     * Setup listeners.
     */
    private void initializeListeners() {

        initializeModelListeners();
        initializeFieldListeners();
    }

    /**
     * Setup field listeners.
     */
    private void initializeFieldListeners() {

        checkboxMonitor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                model.setMonitor(new Boolean(checkboxMonitor.isSelected()));
            }
        });

        textfieldName.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent event) {
                model.setName(textfieldName.getText());
            }
        });

        textfieldTare.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent event) {

                try {
                    textfieldTare.commitEdit();
                } catch (ParseException e) {
                    logger.warn(e);
                }

                model.setTare((Double) textfieldTare.getValue());
            }
        });

        comboboxType.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                model.setScaletype((ScaleType) comboboxType.getSelectedItem());
            }
        });
    }

    /**
     * Setup model listeners.
     */
    private void initializeModelListeners() {

        model.addPropertyChangeListener("monitor", new PropertyChangeListener() {
            public void propertyChange(final PropertyChangeEvent event) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {

                        if (event.getNewValue() == null) {
                            checkboxMonitor.setSelected(false);
                        } else {
                            checkboxMonitor.setSelected(((Boolean) event.getNewValue()).booleanValue());
                        }
                    }
                });
            }
        });

        model.addPropertyChangeListener("name", new PropertyChangeListener() {
            public void propertyChange(final PropertyChangeEvent event) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {

                        textfieldName.setText((String) event.getNewValue());
                    }
                });
            }
        });

        model.addPropertyChangeListener("tare", new PropertyChangeListener() {
            public void propertyChange(final PropertyChangeEvent event) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {

                        if (event.getNewValue() != null) {
                            textfieldTare.setValue((Double) event.getNewValue());
                        } else {
                            textfieldTare.setValue(new Double(0));
                        }
                    }
                });
            }
        });

        model.addPropertyChangeListener("scaletype", new PropertyChangeListener() {
            public void propertyChange(final PropertyChangeEvent event) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {

                        if (event.getNewValue() == null) {
                            comboboxType.setSelectedIndex(-1);
                        } else {
                            comboboxType.setSelectedItem(event.getNewValue());
                        }
                    }
                });
            }
        });
    }

    /**
     * Sets up the panel.
     */
    private void setupPanel() {

        // layout the panel
        setLayout(new GridLayout(1, 1));
        add(panelMain);

        setPreferredSize(new Dimension(-1, 35));
        setMaximumSize(new Dimension(500, 35));

        // set the scale types
        comboboxType.setModel(
                new DefaultComboBoxModel((ScaleType[]) ScaleType.getEnumList().toArray(
                        new ScaleType[ScaleType.getEnumList().size()])));

        DefaultFormatterFactory formatterTare =
                new DefaultFormatterFactory(new NumberFormatter(formatTare));

        textfieldTare.setFormatterFactory(formatterTare);
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
        panelMain.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 5, new Insets(0, 5, 0, 5), -1, -1));
        checkboxMonitor = new JCheckBox();
        checkboxMonitor.setText("");
        panelMain.add(checkboxMonitor, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textfieldName = new JTextField();
        panelMain.add(textfieldName, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboboxType = new JComboBox();
        panelMain.add(comboboxType, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("+/-");
        panelMain.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textfieldTare = new JFormattedTextField();
        textfieldTare.setColumns(4);
        panelMain.add(textfieldTare, new com.intellij.uiDesigner.core.GridConstraints(0, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panelMain;
    }
}
