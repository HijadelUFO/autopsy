/*
 * Autopsy
 *
 * Copyright 2019 Basis Technology Corp.
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
package org.sleuthkit.autopsy.filequery;

import com.google.common.eventbus.Subscribe;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.sleuthkit.autopsy.filequery.FileSearchData.FileType;

/**
 * Panel to display the list of groups which are provided by a search
 */
class GroupListPanel extends javax.swing.JPanel {

    private static final long serialVersionUID = 1L;
    private FileType resultType = null;
    private Map<String, Integer> groupMap = null;
    private List<FileSearchFiltering.FileFilter> searchfilters;
    private FileSearch.AttributeType groupingAttribute;
    private FileGroup.GroupSortingAlgorithm groupSort;
    private FileSorter.SortingMethod fileSortMethod;
    private String selectedGroupName;

    /**
     * Creates new form GroupListPanel
     */
    GroupListPanel() {
        initComponents();
    }

    /**
     * Subscribe to and reset the panel in response to SearchStartedEvents
     *
     * @param searchStartedEvent the SearchStartedEvent which was received
     */
    @Subscribe
    void handleSearchStartedEvent(DiscoveryEvents.SearchStartedEvent searchStartedEvent) {
        resultType = searchStartedEvent.getType();
        groupDisplayNameList.setListData(new String[0]);
    }

    /**
     * Subscribe to and update list of groups in response to
     * SearchCompleteEvents
     *
     * @param searchCompleteEvent the SearchCompleteEvent which was recieved
     */
    @Subscribe
    void handleSearchCompleteEvent(DiscoveryEvents.SearchCompleteEvent searchCompleteEvent) {
        groupMap = searchCompleteEvent.getGroupMap();
        searchfilters = searchCompleteEvent.getFilters();
        groupingAttribute = searchCompleteEvent.getGroupingAttr();
        groupSort = searchCompleteEvent.getGroupSort();
        fileSortMethod = searchCompleteEvent.getFileSort();
        List<String> groupNames = groupMap.entrySet().stream().map(e -> e.getKey() + " (" + e.getValue() + ")").collect(Collectors.toList());
        groupDisplayNameList.setListData(groupNames.toArray(new String[groupNames.size()]));
        if (groupDisplayNameList.getModel().getSize() > 0) {
            groupDisplayNameList.setSelectedIndex(0);
        }
        validate();
        repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        groupListScrollPane = new javax.swing.JScrollPane();
        groupDisplayNameList = new javax.swing.JList<>();

        groupDisplayNameList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        groupDisplayNameList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                groupSelected(evt);
            }
        });
        groupListScrollPane.setViewportView(groupDisplayNameList);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 144, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(groupListScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(groupListScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Respond to a group being selected by sending a PageRetrievedEvent
     *
     * @param evt the event which indicates a selection occurs in the list
     */
    private void groupSelected(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_groupSelected
        if (!evt.getValueIsAdjusting()) {
            String selectedGroup = groupDisplayNameList.getSelectedValue().replaceAll(" \\([0-9]+\\)$", ""); 
            for (String groupName : groupMap.keySet()) {
                if (selectedGroup.equalsIgnoreCase(groupName)) {
                    selectedGroupName = groupName;
                    DiscoveryEvents.getDiscoveryEventBus().post(new DiscoveryEvents.GroupSelectedEvent(
                            searchfilters, groupingAttribute, groupSort, fileSortMethod, selectedGroupName, groupMap.get(selectedGroupName), resultType));
                }
            }

        }
    }//GEN-LAST:event_groupSelected

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<String> groupDisplayNameList;
    private javax.swing.JScrollPane groupListScrollPane;
    // End of variables declaration//GEN-END:variables
}
