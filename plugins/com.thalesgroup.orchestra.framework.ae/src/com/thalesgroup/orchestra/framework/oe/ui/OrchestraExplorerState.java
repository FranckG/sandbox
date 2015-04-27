package com.thalesgroup.orchestra.framework.oe.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.ui.IMemento;

import com.thalesgroup.orchestra.framework.oe.OrchestraExplorerActivator;
import com.thalesgroup.orchestra.framework.oe.ui.preferences.IPreferenceIDs;
import com.thalesgroup.orchestra.framework.oe.ui.preferences.PreferenceManager;
import com.thalesgroup.orchestra.framework.oe.ui.providers.AbstractTreeContentProvider;
import com.thalesgroup.orchestra.framework.oe.ui.providers.FileSystemContentProvider;
import com.thalesgroup.orchestra.framework.oe.ui.providers.FlatContentProvider;
import com.thalesgroup.orchestra.framework.oe.ui.providers.TreeLabelProvider;
import com.thalesgroup.orchestra.framework.oe.ui.providers.TypeContentProvider;
import com.thalesgroup.orchestra.framework.oe.ui.sorters.AlphabeticalSorter;
import com.thalesgroup.orchestra.framework.oe.ui.sorters.FileSystemSorter;
import com.thalesgroup.orchestra.framework.oe.ui.sorters.TypeSorter;

/**
 * Class that provide the state of the Orchestra Explorer component.
 * @author S0024585
 */
public class OrchestraExplorerState {
  protected static final String MEMENTO_TYPE_TAG = "Type"; //$NON-NLS-1$
  protected static final String MEMENTO_FOLDED_TAG = "folded";//$NON-NLS-1$
  protected static final String MEMENTO_GROUPBY_TAG = "groupby";//$NON-NLS-1$
  protected static final String MEMENTO_SORT_TAG = "sort";//$NON-NLS-1$

  /**
   * Folding
   */
  protected boolean _folded = false;

  /**
   * Sort type
   */
  protected SortType _sortType = SortType.NAME;

  /**
   * Group by type
   */
  protected GroupType _groupType = GroupType.TYPE;

  /**
   * The file system sorter
   */
  protected FileSystemSorter _fileSystemSorter = new FileSystemSorter();

  /**
   * The tool sorter
   */
  protected TypeSorter _typeSorter = new TypeSorter();

  /**
   * The alphabetical sorter
   */
  protected AlphabeticalSorter _defaultSorter = new AlphabeticalSorter();

  /**
   * The default label provider
   */
  protected final IBaseLabelProvider _labelProvider = new TreeLabelProvider(this);

  /**
   * Listeners of states changes
   */
  protected final List<IOrchestraExplorerStateListener> _stateListeners = new ArrayList<IOrchestraExplorerStateListener>(0);

  /**
   * Listeners of sort changes
   */
  private IPropertyChangeListener _preferedSortListener;
  private IPropertyChangeListener _preferedGroupListener;

  public OrchestraExplorerState() {
    // Keep default values.
    addSortPreferencelistener();
    addGroupPreferencelistener();
    _sortType = PreferenceManager.getInstance().getPreferedSortCriterion();
    _groupType = PreferenceManager.getInstance().getPreferedGroupCriterion();
  }

  /**
   * Listener on user preferences (particularly the preference related to the sort criterion)
   * @return
   */
  private void addSortPreferencelistener() {
    _preferedSortListener = new IPropertyChangeListener() {
      public void propertyChange(PropertyChangeEvent event) {
        String property = event.getProperty();
        if (property != null && property.equals(IPreferenceIDs.SORT_CRITERION_ID)) {
          setSortType(PreferenceManager.getInstance().getPreferedSortCriterion());
        }
      }
    };
    IPreferenceStore store = OrchestraExplorerActivator.getDefault().getPreferenceStore();
    store.addPropertyChangeListener(_preferedSortListener);
  }

  /**
   * Listener on user preferences (particularly the preference related to the group criterion)
   * @return
   */
  private void addGroupPreferencelistener() {
    _preferedGroupListener = new IPropertyChangeListener() {
      public void propertyChange(PropertyChangeEvent event) {
        String property = event.getProperty();
        if (property != null && property.equals(IPreferenceIDs.GROUP_CRITERION_ID)) {
          setGroupType(PreferenceManager.getInstance().getPreferedGroupCriterion());
        }
      }
    };
    IPreferenceStore store = OrchestraExplorerActivator.getDefault().getPreferenceStore();
    store.addPropertyChangeListener(_preferedGroupListener);
  }

  /**
   * Depending on the current group type, return the tree provider.
   */
  public AbstractTreeContentProvider getCurrentContentProvider() {
    switch (_groupType) {
      case ENVIRONMENT:
        return new FileSystemContentProvider(this);
      case TYPE:
        return new TypeContentProvider(this);
      default:
        return new FlatContentProvider(this);
    }
  }

  /**
   * Depending on the state of OE, return the label provider
   */
  public IBaseLabelProvider getCurrentLabelProvider() {
    return _labelProvider;
  }

  /**
   * Depending on the current sort type, return the viewer sorter.
   */
  public ViewerSorter getCurrentTreeSorter() {
    switch (_sortType) {
      case ENVIRONMENT:
        return _fileSystemSorter;
      case TYPE:
        return _typeSorter;
      default:
        return _defaultSorter;
    }
  }

  /**
   * @return The default tree sorter
   */
  public ViewerSorter getDefaultTreeSorter() {
    return _defaultSorter;
  }

  /**
   * @return the current group type
   */
  public GroupType getGroupType() {
    return _groupType;
  }

  /**
   * @return the current sort type
   */
  public SortType getSortType() {
    return _sortType;
  }

  /**
   * @return <code>true</code> if the view is folded. <code>false</code> otherwise.
   */
  public boolean isFolded() {
    return _folded;
  }

  /**
   * Read the memento for initial states.
   * @param memento_p
   */
  public void loadMemento(IMemento memento_p) {
    if (null == memento_p) {
      // No memento, no jello!!
      return;
    }
    IMemento folded = memento_p.getChild(MEMENTO_FOLDED_TAG);
    if (folded != null) {
      setFolded(true);
    } else {
      setFolded(false);
    }
    IMemento groupBy = memento_p.getChild(MEMENTO_GROUPBY_TAG);
    if (groupBy != null) {
      String groupType = groupBy.getString(MEMENTO_TYPE_TAG);
      if (groupType != null) {
        setGroupType(GroupType.valueOf(groupType));
      } else {
        setGroupType(GroupType.TYPE);
      }
    } else {
      setGroupType(GroupType.TYPE);
    }
    IMemento sortBy = memento_p.getChild(MEMENTO_SORT_TAG);
    if (sortBy != null) {
      String sortType = sortBy.getString(MEMENTO_TYPE_TAG);
      if (sortType != null) {
        setSortType(SortType.valueOf(sortType));
      } else {
        setGroupType(GroupType.TYPE);
      }
    } else {
      setGroupType(GroupType.TYPE);
    }
  }

  /**
   * Add a state listener.
   * @param orchestraExplorerView_p
   */
  public void registerStateListener(IOrchestraExplorerStateListener stateListener_p) {
    _stateListeners.add(stateListener_p);
  }

  /**
   * Remove the specified state listener
   * @param orchestraExplorerView_p
   */
  public void removeStateListener(IOrchestraExplorerStateListener stateListener_p) {
    _stateListeners.remove(stateListener_p);
  }

  /**
   * Save in the memento the current states
   * @param memento_p
   */
  public void saveMemento(IMemento memento_p) {
    // Is folded
    if (isFolded()) {
      memento_p.createChild(MEMENTO_FOLDED_TAG);
    }
    // Group method
    memento_p.createChild(MEMENTO_GROUPBY_TAG).putString(MEMENTO_TYPE_TAG, getGroupType().name());
    // Sort method
    memento_p.createChild(MEMENTO_SORT_TAG).putString(MEMENTO_TYPE_TAG, getSortType().name());
  }

  /**
   * Modify the folded state. Notify all registered {@link IOrchestraExplorerStateListener}s if the value is changed.
   * @param folded_p
   */
  public void setFolded(boolean folded_p) {
    if (_folded == folded_p) {
      // No change so do nothing
      return;
    }
    _folded = folded_p;
    for (IOrchestraExplorerStateListener stateListener : _stateListeners) {
      stateListener.foldingChanged();
    }
  }

  /**
   * Modify the group type. Notify all registered {@link IOrchestraExplorerStateListener}s if the value is changed.
   * @param groupType_p
   */
  public void setGroupType(GroupType groupType_p) {
    if (groupType_p == _groupType) {
      // No change, no event!
      return;
    }
    _groupType = groupType_p;
    for (IOrchestraExplorerStateListener stateListener : _stateListeners) {
      stateListener.groupByChanged();
    }
  }

  /**
   * Modify the sort type. Notify all registered {@link IOrchestraExplorerStateListener}s if the value is changed.
   * @param sortType_p
   */
  public void setSortType(SortType sortType_p) {
    if (sortType_p == _sortType) {
      // No change, no event!
      return;
    }
    _sortType = sortType_p;
    for (IOrchestraExplorerStateListener stateListener : _stateListeners) {
      stateListener.sortChanged();
    }
  }

  /**
   * "group by" allowed types
   * @author S0024585
   */
  public enum GroupType {
    ENVIRONMENT, TYPE, NONE
  }

  /**
   * Sort allowed types.
   * @author S0024585
   */
  public enum SortType {
    ENVIRONMENT, NAME, TYPE
  }
}
