/**
 * Copyright (c) THALES, 2012. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.environment.filesystem.ui;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Stack;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import com.thalesgroup.orchestra.framework.environment.EnvironmentActivator;
import com.thalesgroup.orchestra.framework.lib.base.utils.OrchestraFileFilter;

/**
 * Build a directory SWT Tree with filtering rules
 * @author s0040806
 */
class DirectoryTreeBuilderVisitor extends SimpleFileVisitor<Path> {

  private static final Image FOLDER = DirectoryTreeBuilderVisitor.getImage("directories/folder.gif"); //$NON-NLS-1$

  private Stack<TreeItem> _treeItemStack;
  private TreeItem _rootItem;
  private TreeItem _currentItem;

  private OrchestraFileFilter _filter;

  public DirectoryTreeBuilderVisitor(Path rootPath_p, List<String> filters_p, Tree tree_p) {
    // Build initial tree item with full path
    _rootItem = new TreeItem(tree_p, 0);
    _rootItem.setText(rootPath_p.toString());
    _rootItem.setImage(FOLDER);

    _treeItemStack = new Stack<TreeItem>();

    _filter = new OrchestraFileFilter(rootPath_p, filters_p);
  }

  /**
   * @see java.nio.file.SimpleFileVisitor#preVisitDirectory(java.lang.Object, java.nio.file.attribute.BasicFileAttributes)
   */
  @SuppressWarnings("synthetic-access")
  @Override
  public FileVisitResult preVisitDirectory(Path dir_p, BasicFileAttributes attributes_p) throws IOException {
    if (_filter.matchesDirectory(dir_p)) {
      return FileVisitResult.SKIP_SUBTREE;
    }

    // Save parent tree item
    _treeItemStack.push(_currentItem);

    if (null == _currentItem) {
      _currentItem = _rootItem;
    } else {
      // Add tree item with last path component only
      TreeItem item = new TreeItem(_currentItem, 0);
      item.setImage(FOLDER);
      item.setText(dir_p.getName(dir_p.getNameCount() - 1).toString());
      _currentItem = item;
    }

    return FileVisitResult.CONTINUE;
  }

  /**
   * @see java.nio.file.SimpleFileVisitor#postVisitDirectory(java.lang.Object, java.io.IOException)
   */
  @Override
  public FileVisitResult postVisitDirectory(Path arg0_p, IOException arg1_p) throws IOException {
    _currentItem = _treeItemStack.pop();
    // Add directory to preview tree
    return FileVisitResult.CONTINUE;
  }

  static Image getImage(String file_p) {
    return EnvironmentActivator.getInstance().getImageDescriptor(file_p).createImage();
  }
}