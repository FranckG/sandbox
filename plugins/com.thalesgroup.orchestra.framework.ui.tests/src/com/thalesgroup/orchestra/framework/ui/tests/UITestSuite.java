/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.thalesgroup.orchestra.framework.ui.tests.datamanagement.DirtyStateTests;
import com.thalesgroup.orchestra.framework.ui.tests.datamanagement.SaveActionTests;
import com.thalesgroup.orchestra.framework.ui.tests.datamanagement.category.CreateCategoryTests;
import com.thalesgroup.orchestra.framework.ui.tests.datamanagement.category.CutCopyPasteCategoryTests;
import com.thalesgroup.orchestra.framework.ui.tests.datamanagement.category.DeleteCategoryTests;
import com.thalesgroup.orchestra.framework.ui.tests.datamanagement.category.EditCategoryTests;
import com.thalesgroup.orchestra.framework.ui.tests.datamanagement.category.SelectCategoryTests;
import com.thalesgroup.orchestra.framework.ui.tests.datamanagement.context.CreateNewAdminContextTests;
import com.thalesgroup.orchestra.framework.ui.tests.datamanagement.context.CreateNewUserContextTests;
import com.thalesgroup.orchestra.framework.ui.tests.datamanagement.context.DeleteAdminContextTests;
import com.thalesgroup.orchestra.framework.ui.tests.datamanagement.context.DeleteUserContextTests;
import com.thalesgroup.orchestra.framework.ui.tests.datamanagement.context.EditAdminContextTests;
import com.thalesgroup.orchestra.framework.ui.tests.datamanagement.context.ExportContextTests;
import com.thalesgroup.orchestra.framework.ui.tests.datamanagement.context.ImportContextTests;
import com.thalesgroup.orchestra.framework.ui.tests.datamanagement.context.SetAsCurrentAdminContextTest;
import com.thalesgroup.orchestra.framework.ui.tests.datamanagement.context.SetAsCurrentContextActionEnablementTests;
import com.thalesgroup.orchestra.framework.ui.tests.datamanagement.context.SetAsCurrentUserContextTest;
import com.thalesgroup.orchestra.framework.ui.tests.datamanagement.context.synchronize.TestSync01AddNewElementsInParentContextAndSynchronize;
import com.thalesgroup.orchestra.framework.ui.tests.datamanagement.context.synchronize.TestSync02ChangeElementsNameInParentContextAndSynchronize;
import com.thalesgroup.orchestra.framework.ui.tests.datamanagement.context.synchronize.TestSync03EditExpandInheritedCategory;
import com.thalesgroup.orchestra.framework.ui.tests.datamanagement.context.synchronize.TestSync04EditExpandInheritedFileVariable;
import com.thalesgroup.orchestra.framework.ui.tests.datamanagement.context.synchronize.TestSync05EditExpandInheritedFolderVariable;
import com.thalesgroup.orchestra.framework.ui.tests.datamanagement.context.synchronize.TestSync06EditExpandInheritedVariable;
import com.thalesgroup.orchestra.framework.ui.tests.datamanagement.context.synchronize.TestSync07EditFinalVariableInParentContextAndSynchronize;
import com.thalesgroup.orchestra.framework.ui.tests.datamanagement.context.synchronize.TestSync08EditParentAndInheritedContextAndSynchronize;
import com.thalesgroup.orchestra.framework.ui.tests.datamanagement.context.synchronize.TestSync09ExpandElementsInParentContextAndSynchronize;
import com.thalesgroup.orchestra.framework.ui.tests.datamanagement.context.synchronize.TestSync10ExpandInheritedStructure;
import com.thalesgroup.orchestra.framework.ui.tests.datamanagement.context.synchronize.TestSync11ExpandInheritedVariable;
import com.thalesgroup.orchestra.framework.ui.tests.datamanagement.context.synchronize.TestSync12InheritedCopiedCategoryIsEditable;
import com.thalesgroup.orchestra.framework.ui.tests.datamanagement.context.synchronize.TestSync13InheritedCopiedCategoryVariableIsEditable;
import com.thalesgroup.orchestra.framework.ui.tests.datamanagement.context.synchronize.TestSync14InheritedCopiedVariableIsEditable;
import com.thalesgroup.orchestra.framework.ui.tests.datamanagement.context.synchronize.TestSync15InheritedCopyVariableIsEditable;
import com.thalesgroup.orchestra.framework.ui.tests.datamanagement.context.synchronize.TestSync16InheritedFileVariable;
import com.thalesgroup.orchestra.framework.ui.tests.datamanagement.context.synchronize.TestSync17InheritedFinalCoherence;
import com.thalesgroup.orchestra.framework.ui.tests.datamanagement.context.synchronize.TestSync18InheritedFolderVariable;
import com.thalesgroup.orchestra.framework.ui.tests.datamanagement.context.synchronize.TestSync19InheritedStructureDataIsNotEditable;
import com.thalesgroup.orchestra.framework.ui.tests.datamanagement.context.synchronize.TestSync20InheritedStructureIsFinal;
import com.thalesgroup.orchestra.framework.ui.tests.datamanagement.context.synchronize.TestSync21InheritedVariableAndCategoryPresence;
import com.thalesgroup.orchestra.framework.ui.tests.datamanagement.context.synchronize.TestSync22InheritedVariableCoherence;
import com.thalesgroup.orchestra.framework.ui.tests.datamanagement.context.synchronize.TestSync23InheritedVariableDataPresence;
import com.thalesgroup.orchestra.framework.ui.tests.datamanagement.context.synchronize.TestSync24RemoveElementsInParentContextAndSynchronize;
import com.thalesgroup.orchestra.framework.ui.tests.datamanagement.variable.CreateVariableTests;
import com.thalesgroup.orchestra.framework.ui.tests.datamanagement.variable.CutCopyPasteVariableTests;
import com.thalesgroup.orchestra.framework.ui.tests.datamanagement.variable.DeleteVariableTests;
import com.thalesgroup.orchestra.framework.ui.tests.datamanagement.variable.EditVariableTests;
import com.thalesgroup.orchestra.framework.ui.tests.datamanagement.variable.VariableValuesTests;

/**
 * Orchestra Framework UI test suite.
 * @author t0076261
 */
@RunWith(Suite.class)
@SuiteClasses({ DirtyStateTests.class, SaveActionTests.class, CreateNewAdminContextTests.class, CreateNewUserContextTests.class,
               SetAsCurrentAdminContextTest.class, SetAsCurrentContextActionEnablementTests.class, SetAsCurrentUserContextTest.class,
               DeleteAdminContextTests.class, DeleteUserContextTests.class, EditAdminContextTests.class, ExportContextTests.class, ImportContextTests.class,
               CreateCategoryTests.class, CutCopyPasteCategoryTests.class, DeleteCategoryTests.class, EditCategoryTests.class, SelectCategoryTests.class,
               CreateVariableTests.class, CutCopyPasteVariableTests.class, DeleteVariableTests.class, EditVariableTests.class, VariableValuesTests.class,
               TestSync01AddNewElementsInParentContextAndSynchronize.class, TestSync02ChangeElementsNameInParentContextAndSynchronize.class,
               TestSync03EditExpandInheritedCategory.class, TestSync04EditExpandInheritedFileVariable.class, TestSync05EditExpandInheritedFolderVariable.class,
               TestSync06EditExpandInheritedVariable.class, TestSync07EditFinalVariableInParentContextAndSynchronize.class,
               TestSync08EditParentAndInheritedContextAndSynchronize.class, TestSync09ExpandElementsInParentContextAndSynchronize.class,
               TestSync10ExpandInheritedStructure.class, TestSync11ExpandInheritedVariable.class, TestSync12InheritedCopiedCategoryIsEditable.class,
               TestSync13InheritedCopiedCategoryVariableIsEditable.class, TestSync14InheritedCopiedVariableIsEditable.class,
               TestSync15InheritedCopyVariableIsEditable.class, TestSync16InheritedFileVariable.class, TestSync17InheritedFinalCoherence.class,
               TestSync18InheritedFolderVariable.class, TestSync19InheritedStructureDataIsNotEditable.class, TestSync20InheritedStructureIsFinal.class,
               TestSync21InheritedVariableAndCategoryPresence.class, TestSync22InheritedVariableCoherence.class, TestSync23InheritedVariableDataPresence.class,
               TestSync24RemoveElementsInParentContextAndSynchronize.class })
public class UITestSuite {
  // Nothing to do.
}