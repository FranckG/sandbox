/*******************************************************************************
 *  Copyright (c) 2009 Thales Corporate Services S.A.S.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 * 
 *  Contributors:
 *      Thales Corporate Services S.A.S - initial API and implementation
 *******************************************************************************/
package com.thalesgroup.orchestra.framework.root.ui.forms;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.events.IHyperlinkListener;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ImageHyperlink;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

/**
 * UI Forms helper.<br>
 * Allows creation of Composites, Layouts and Forms widgets.
 * @author t0076261
 */
public class FormHelper {
  /**
   * Available styles for styled text.
   */
  protected static StyleRange[] __styles;
  /**
   * Styled text delimiter regular expression.
   */
  protected static final String STYLED_TEXT_REGULAR_EXPRESSION_DELIMITER = "\\{[0-9]+\\}"; //$NON-NLS-1$
  /**
   * Styled text regular expression.
   */
  protected static final String STYLED_TEXT_REGULAR_EXPRESSION_FULL_EXPRESSION = "(.*)(" + STYLED_TEXT_REGULAR_EXPRESSION_DELIMITER + ")(.*)"; //$NON-NLS-1$ //$NON-NLS-2$
  /**
   * Full expression matcher.
   */
  protected static final Matcher STYLED_TEXT_REGULAR_EXPRESSION_MATCHER = Pattern.compile(STYLED_TEXT_REGULAR_EXPRESSION_FULL_EXPRESSION, Pattern.DOTALL)
      .matcher(""); //$NON-NLS-1$
  static {
    StyleRange bold = new StyleRange();
    bold.fontStyle = SWT.BOLD;
    StyleRange italic = new StyleRange();
    italic.fontStyle = SWT.ITALIC;
    StyleRange underlined = new StyleRange();
    underlined.underline = true;
    StyleRange box = new StyleRange();
    box.borderStyle = SWT.BORDER_SOLID;
    __styles = new StyleRange[] { bold, italic, underlined, box };
  }

  /**
   * Create a user button widget.
   * @param toolkit_p
   * @param parent_p
   * @param buttonLabel_p
   * @param buttonStyle_p
   * @return
   */
  public static Button createButton(FormToolkit toolkit_p, Composite parent_p, String buttonLabel_p, int buttonStyle_p) {
    return toolkit_p.createButton(parent_p, buttonLabel_p, buttonStyle_p);
  }

  /**
   * Create a new composite and set the layout using
   * {@link #updateCompositeLayoutWithLayoutType(Composite, org.eclipse.egf.common.ui.helper.FormHelper.LayoutType, int)} method.
   * @param toolkit_p
   * @param parent_p
   * @param numColumns_p
   * @return
   */
  public static Composite createCompositeWithLayoutType(FormToolkit toolkit_p, Composite parent_p, LayoutType layoutType_p, int numColumns_p,
      boolean equalWidth_p) {
    Composite result = toolkit_p.createComposite(parent_p);
    updateCompositeLayoutWithLayoutType(result, layoutType_p, numColumns_p, equalWidth_p);
    return result;
  }

  /**
   * Create a label widget.
   * @param toolkit_p
   * @param parent_p
   * @param labelMessage_p
   * @return
   */
  public static Label createLabel(FormToolkit toolkit_p, Composite parent_p, String labelMessage_p) {
    // Create label.
    Label label = toolkit_p.createLabel(parent_p, labelMessage_p, SWT.WRAP);
    label.setForeground(toolkit_p.getColors().getColor(IFormColors.TITLE));
    return label;
  }

  /**
   * Create a user text widget with preceding label.<br>
   * Requires at least a two columns layout so that both the label and the text are displayed on the same line.
   * @param toolkit_p
   * @param parent_p
   * @param labelMessage_p
   * @param initialText_p
   * @param textStyle_p
   * @return
   */
  public static Map<Class, Widget> createLabelAndText(FormToolkit toolkit_p, Composite parent_p, String labelMessage_p, String initialText_p, int textStyle_p) {
    // Create label.
    Label label = toolkit_p.createLabel(parent_p, labelMessage_p, SWT.WRAP);
    label.setForeground(toolkit_p.getColors().getColor(IFormColors.TITLE));
    // Create text.
    Text text = toolkit_p.createText(parent_p, (null != initialText_p) ? initialText_p : "", textStyle_p); //$NON-NLS-1$
    text.setBackground(parent_p.getDisplay().getSystemColor(SWT.COLOR_WHITE));
    Map<Class, Widget> result = new HashMap<Class, Widget>(2);
    result.put(Label.class, label);
    result.put(Text.class, text);
    return result;
  }

  /**
   * Create a link with a label description.<br>
   * Requires a two columns layout so that both the link and the label are displayed on the same line.
   * @param toolkit_p
   * @param parent_p
   * @param icon_p
   * @param linkText_p
   * @param linkRef_p
   * @param linkDescription_p
   * @param listener_p
   */
  public static void createLinkWithDescription(FormToolkit toolkit_p, Composite parent_p, Image icon_p, String linkText_p, Object linkRef_p,
      String linkDescription_p, IHyperlinkListener listener_p) {
    ImageHyperlink specificationLink = toolkit_p.createImageHyperlink(parent_p, SWT.WRAP);
    specificationLink.setText(linkText_p);
    specificationLink.setImage(icon_p);
    specificationLink.setHref(linkRef_p);
    specificationLink.addHyperlinkListener(listener_p);
    toolkit_p.createLabel(parent_p, linkDescription_p, SWT.WRAP);
  }

  /**
   * Create a section with a composite child using given child layout type.
   * @param toolkit_p
   * @param parent_p
   * @param sectionStyle_p
   * @param layoutType_p
   * @param childNumColumns_p
   * @param equalWidth_p
   * @return
   */
  public static Map<Class, Control> createSectionWithChildComposite(FormToolkit toolkit_p, Composite parent_p, int sectionStyle_p, LayoutType layoutType_p,
      int childNumColumns_p, boolean equalWidth_p) {
    Section resultingSection = toolkit_p.createSection(parent_p, sectionStyle_p);
    updateControlLayoutDataWithLayoutTypeData(resultingSection, layoutType_p);
    Composite childComposite = createCompositeWithLayoutType(toolkit_p, resultingSection, layoutType_p, childNumColumns_p, equalWidth_p);
    resultingSection.setClient(childComposite);
    Map<Class, Control> result = new HashMap<Class, Control>(2);
    result.put(Section.class, resultingSection);
    result.put(Composite.class, childComposite);
    return result;
  }

  /**
   * Add introduction text that takes place above the viewer and the buttons.<br>
   * This is a guide to what the user should provide as values.<br>
   * It makes use of styled text (with limited possibilities though).
   * @param toolkit_p
   * @param parent_p
   * @param rawText_p A raw text, that should be formated using predefined styles.<br>
   *          A style reference is done using <code>{X}Text to apply style X to{X}</code> where <code>X</code> is a positive integer referring to a predefined
   *          style.<br>
   *          Existing styles are :<br>
   *          <ul>
   *          <li><code>0</code> : text in bold</li>
   *          <li><code>1</code> : text in italic</li>
   *          <li><code>2</code> : text underlined</li>
   *          <li><code>3</code> : text boxed</li>
   *          </ul>
   * <br>
   *          For instance, the following raw text : This is {0}a text in bold{0}<br>
   *          would give the resulting styled text : This is <b>a text in bold</b>
   * @param styledTextStyle_p the style of the StyledText widget.
   * @return the created StyledText.
   */
  public static StyledText createStyledText(FormToolkit toolkit_p, Composite parent_p, String rawText_p, int styledTextStyle_p) {
    // Get contents.
    String initialContents = rawText_p;
    // Precondition.
    if (null == initialContents) {
      return null;
    }
    // Get styled parts.
    Map<String, Integer> styledTextParts = getStyledTextParts(initialContents);
    // Get clean contents (without delimiters).
    String cleanContents = initialContents.replaceAll(STYLED_TEXT_REGULAR_EXPRESSION_DELIMITER, ""); //$NON-NLS-1$
    // Create styled text.
    StyledText styledText = new StyledText(parent_p, styledTextStyle_p);
    styledText.setText(cleanContents);
    if (!styledTextParts.isEmpty()) {
      // Apply style.
      // Cycle through text to apply style to.
      for (Map.Entry<String, Integer> entry : styledTextParts.entrySet()) {
        String styledPart = entry.getKey();
        int style = entry.getValue().intValue();
        int length = styledPart.length();
        style = Math.min(style, __styles.length - 1);
        int startingIndex = cleanContents.indexOf(styledPart);
        while (-1 != startingIndex) {
          // Make sure style reference does not exceed existing ones.
          styledText.setStyleRanges(0, 0, new int[] { startingIndex, length }, new StyleRange[] { __styles[style] });
          startingIndex = cleanContents.indexOf(styledPart, startingIndex + 1);
        }
      }
    }
    styledText.setBackground(parent_p.getBackground());
    return styledText;
  }

  public static Text createText(FormToolkit toolkit_p, Composite parent_p, String initialText_p, int textStyle_p) {
    // Create text.
    Text text = toolkit_p.createText(parent_p, (null != initialText_p) ? initialText_p : "", textStyle_p); //$NON-NLS-1$
    text.setBackground(parent_p.getDisplay().getSystemColor(SWT.COLOR_WHITE));
    return text;
  }

  /**
   * Force control size.
   * @param control_p
   * @param widthInChars_p The expected width, in number of chars to display.
   * @param heightInChars_p The expected height, in number of chars to display.
   */
  public static void forceControlSize(Control control_p, int widthInChars_p, int heightInChars_p) {
    // Preconditions.
    if ((null == control_p) || (0 >= widthInChars_p) || (0 >= heightInChars_p)) {
      return;
    }
    // Get font metrics.
    GC gc = new GC(control_p);
    FontMetrics fontMetrics = gc.getFontMetrics();
    gc.dispose();
    // Get layout data.
    Object layoutData = control_p.getLayoutData();
    if (layoutData instanceof GridData) {
      GridData data = (GridData) layoutData;
      data.widthHint = Dialog.convertWidthInCharsToPixels(fontMetrics, widthInChars_p);
      data.heightHint = Dialog.convertHeightInCharsToPixels(fontMetrics, heightInChars_p);
    } else if (layoutData instanceof TableWrapData) {
      TableWrapData data = (TableWrapData) layoutData;
      data.heightHint = Dialog.convertHeightInCharsToPixels(fontMetrics, heightInChars_p);
    }
  }

  /**
   * Get text parts that should be applied a style, along with the style code, for specified raw input text.<br>
   * Use of <code>\n</code> or <code>\t</code> characters is supported in the raw text.<br>
   * Styles are injected by surrounding the expected styled text with <code>{X}</code> delimiters where <code>X</code> is the identifier of the style to apply.
   * @param rawText_p
   * @return
   */
  protected static Map<String, Integer> getStyledTextParts(String rawText_p) {
    // Precondition.
    if (null == rawText_p) {
      return Collections.emptyMap();
    }
    Map<String, Integer> textToStyle = new HashMap<String, Integer>(0);
    String value = rawText_p;
    int i = 0;
    while (STYLED_TEXT_REGULAR_EXPRESSION_MATCHER.reset(value).matches()) {
      i++;
      String result = STYLED_TEXT_REGULAR_EXPRESSION_MATCHER.group(2);
      result = result.replace("{", ""); //$NON-NLS-1$ //$NON-NLS-2$
      result = result.replace("}", ""); //$NON-NLS-1$ //$NON-NLS-2$
      Integer styleNumber = null;
      try {
        styleNumber = new Integer(result);
      } catch (NumberFormatException nfe_p) {
        // An error occurred.
        // No relevant result.
        return Collections.emptyMap();
      }
      value = STYLED_TEXT_REGULAR_EXPRESSION_MATCHER.group(1);
      if ((i / 2) == (((float) i) / 2)) {
        textToStyle.put(STYLED_TEXT_REGULAR_EXPRESSION_MATCHER.group(3), styleNumber);
      }
    }
    return textToStyle;
  }

  /**
   * Update given composite with given layout type and given number of columns (if it makes any sense).<br>
   * Also set the layout data to {@link #updateControlLayoutDataWithLayoutTypeData(Composite, org.eclipse.egf.common.ui.helper.FormHelper.LayoutType)}.
   * @param composite_p
   * @param layoutType_p
   * @param numColumns_p
   */
  public static Object updateCompositeLayoutWithLayoutType(Composite composite_p, LayoutType layoutType_p, int numColumns_p, boolean equalWidth_p) {
    Layout selectedLayout = null;
    if (LayoutType.GRID_LAYOUT.equals(layoutType_p)) {
      GridLayout layout = new GridLayout();
      layout.numColumns = numColumns_p;
      layout.makeColumnsEqualWidth = equalWidth_p;
      selectedLayout = layout;
    } else if (LayoutType.TABLEWRAP_LAYOUT.equals(layoutType_p)) {
      TableWrapLayout layout = new TableWrapLayout();
      layout.numColumns = numColumns_p;
      layout.makeColumnsEqualWidth = equalWidth_p;
      selectedLayout = layout;
    }
    // Do not set neither layout nor layout data if layout could not be created.
    if (null != selectedLayout) {
      composite_p.setLayout(selectedLayout);
      LayoutType dataLayout = layoutType_p;
      if (composite_p.getParent().getLayout() instanceof GridLayout) {
        dataLayout = LayoutType.GRID_LAYOUT;
      } else if (composite_p.getParent().getLayout() instanceof TableWrapLayout) {
        dataLayout = LayoutType.TABLEWRAP_LAYOUT;
      }
      updateControlLayoutDataWithLayoutTypeData(composite_p, dataLayout);
    }
    return selectedLayout;
  }

  /**
   * Update given control layout data depending on given layout type.<br>
   * Replace layout data is set to fill/grab in both directions, if it makes any sense.
   * @param control_p
   */
  public static Object updateControlLayoutDataWithLayoutTypeData(Control control_p, LayoutType layoutType_p) {
    Object layoutData = null;
    if (LayoutType.GRID_LAYOUT.equals(layoutType_p)) {
      layoutData = new GridData(GridData.FILL_BOTH);
    } else if (LayoutType.TABLEWRAP_LAYOUT.equals(layoutType_p)) {
      layoutData = new TableWrapData(TableWrapData.FILL_GRAB);
    }
    // Do not set layout data if it could not be created.
    if (null != layoutData) {
      control_p.setLayoutData(layoutData);
    }
    return layoutData;
  }

  /**
   * Layout usable types.
   * @author t0076261
   */
  public static enum LayoutType {
    GRID_LAYOUT, TABLEWRAP_LAYOUT
  }
}
