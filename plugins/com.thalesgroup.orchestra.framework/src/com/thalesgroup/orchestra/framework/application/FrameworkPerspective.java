package com.thalesgroup.orchestra.framework.application;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class FrameworkPerspective implements IPerspectiveFactory {
  @Override
  public void createInitialLayout(IPageLayout layout) {
    layout.setEditorAreaVisible(false);
  }
}