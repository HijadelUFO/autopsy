/*
 * Autopsy Forensic Browser
 *
 * Copyright 2013 Basis Technology Corp.
 * Contact: carrier <at> sleuthkit <dot> org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sleuthkit.autopsy.report;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;
import org.openide.util.NbBundle;

/**
 * Visual component of the File Report Configuration panel of the Report Wizard.
 *
 * @author jwallace
 */
class ReportWizardFileOptionsVisualPanel extends javax.swing.JPanel {

    private List<FileReportDataTypes> options;
    private Map<FileReportDataTypes, Boolean> optionStates = new EnumMap<>(FileReportDataTypes.class);
    private ListModel<FileReportDataTypes> model;
    private ReportWizardFileOptionsPanel wizPanel;

    public ReportWizardFileOptionsVisualPanel(ReportWizardFileOptionsPanel wizPanel) {
        this.wizPanel = wizPanel;
        initComponents();
        initOptionsList();
    }

    @Override
    public String getName() {
        return NbBundle.getMessage(this.getClass(), "ReportWizardFileOptionsVisualPanel.getName.text");
    }

    /**
     * Populate the list of File Report Information that can be selected.
     */
    private void initOptionsList() {
        options = Arrays.asList(FileReportDataTypes.values());
        for (FileReportDataTypes col : options) {
            optionStates.put(col, Boolean.FALSE);
        }

        model = new OptionsListModel();
        optionsList.setModel(model);
        optionsList.setCellRenderer(new OptionsListRenderer());
        optionsList.setVisibleRowCount(-1);

        selectAllButton.setEnabled(true);
        deselectAllButton.setEnabled(false);

        // Add the ability to enable and disable Tag checkboxes to the list
        optionsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {

                int index = optionsList.locationToIndex(evt.getPoint());
                FileReportDataTypes value = model.getElementAt(index);
                optionStates.put(value, !optionStates.get(value));
                optionsList.repaint();
                boolean anySelected = anySelected();
                deselectAllButton.setEnabled(anySelected);
                wizPanel.setFinish(anySelected);
                selectAllButton.setEnabled(notAllSelected());
            }
        });
    }

    /**
     * Are any options selected?
     *
     * @return
     */
    private boolean anySelected() {
        for (Boolean b : optionStates.values()) {
            if (b) {
                return true;
            }
        }
        return false;
    }

    /**
     * Are no options selected?
     *
     * @return
     */
    private boolean notAllSelected() {
        for (Boolean b : optionStates.values()) {
            if (!b) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the user-selected settings.
     *
     * @return
     */
    Map<FileReportDataTypes, Boolean> getFileReportOptions() {
        return optionStates;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        optionsList = new javax.swing.JList<>();
        selectAllButton = new javax.swing.JButton();
        deselectAllButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        optionsList.setModel(new javax.swing.AbstractListModel<FileReportDataTypes>() {
            FileReportDataTypes[] types = { };
            public int getSize() { return types.length; }
            public FileReportDataTypes getElementAt(int i) { return types[i]; }
        });
        jScrollPane1.setViewportView(optionsList);

        org.openide.awt.Mnemonics.setLocalizedText(selectAllButton, org.openide.util.NbBundle.getMessage(ReportWizardFileOptionsVisualPanel.class, "ReportWizardFileOptionsVisualPanel.selectAllButton.text")); // NOI18N
        selectAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectAllButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(deselectAllButton, org.openide.util.NbBundle.getMessage(ReportWizardFileOptionsVisualPanel.class, "ReportWizardFileOptionsVisualPanel.deselectAllButton.text")); // NOI18N
        deselectAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deselectAllButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(ReportWizardFileOptionsVisualPanel.class, "ReportWizardFileOptionsVisualPanel.jLabel1.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(selectAllButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(deselectAllButton)))
                        .addGap(0, 210, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(deselectAllButton)
                    .addComponent(selectAllButton))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void selectAllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectAllButtonActionPerformed
        for (FileReportDataTypes option : options) {
            optionStates.put(option, Boolean.TRUE);
        }
        optionsList.repaint();
        selectAllButton.setEnabled(false);
        deselectAllButton.setEnabled(true);
        wizPanel.setFinish(true);
    }//GEN-LAST:event_selectAllButtonActionPerformed

    private void deselectAllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deselectAllButtonActionPerformed
        for (FileReportDataTypes option : options) {
            optionStates.put(option, Boolean.FALSE);
        }
        optionsList.repaint();
        selectAllButton.setEnabled(true);
        deselectAllButton.setEnabled(false);
        wizPanel.setFinish(false);
    }//GEN-LAST:event_deselectAllButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton deselectAllButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<FileReportDataTypes> optionsList;
    private javax.swing.JButton selectAllButton;
    // End of variables declaration//GEN-END:variables

    private class OptionsListModel implements ListModel<FileReportDataTypes> {

        @Override
        public int getSize() {
            return options.size();
        }

        @Override
        public FileReportDataTypes getElementAt(int index) {
            return options.get(index);
        }

        @Override
        public void addListDataListener(ListDataListener l) {
        }

        @Override
        public void removeListDataListener(ListDataListener l) {
        }
    }

    /**
     * Render each item in the list to be a selectable check box.
     */
    private class OptionsListRenderer extends JCheckBox implements ListCellRenderer<FileReportDataTypes> {

        @Override
        public Component getListCellRendererComponent(JList<? extends FileReportDataTypes> list, FileReportDataTypes value, int index, boolean isSelected, boolean cellHasFocus) {
            if (value != null) {
                FileReportDataTypes col = value;
                setEnabled(list.isEnabled());
                setSelected(optionStates.get(col));
                setFont(list.getFont());
                setBackground(list.getBackground());
                setForeground(list.getForeground());
                setText(col.getName());
                return this;
            }
            return new JLabel();
        }

    }

}
