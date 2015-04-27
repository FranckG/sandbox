/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.association;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.Assert;

import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.common.util.Couple;

/**
 * This is a prototype, not intended to be used on another computer than mine.<br>
 * This is handled within ClearCase for save purposes only.
 * @author t0076261
 */
@SuppressWarnings({ "boxing", "nls" })
public class AssociationResolver {
  /**
   * Matcher instance.
   */
  protected static Matcher __matcher = Pattern.compile("(.*)(\\(.+\\))(.*)").matcher(ICommonConstants.EMPTY_STRING); //$NON-NLS-1$

  public static void main(String[] args) {
    // Groups definition.
    Map<Integer, String> originalIdToGroup = new HashMap<Integer, String>(2);
    // originalIdToGroup.put(1, "[^\\\\]+");
    // originalIdToGroup.put(2, ".*?");
    originalIdToGroup.put(1, ".*?");
    originalIdToGroup.put(2, ".*");
    // Physical rule.
    // String physicalRule = "(2)(1)\\.txt";
    String physicalRule = "(2)\\\\(1)";
    // Logical rule.
    // Constraints :
    // 1) Can't make use of non-grouped expressions.
    // 2) Get rid of escape characters in reconstructed strings.
    // String logicalRule = "(2)TXT\\\\(1)";
    String logicalRule = "(1)(2)";
    Association association = new AssociationResolver().new Association(originalIdToGroup, physicalRule, logicalRule);
    String physicalExpression = association.getPhysicalRule()._targetExpression;
    System.out.println("Raw physical expression : " + physicalRule);
    System.out.println("Resolved physical expression : " + physicalExpression);
    String logicalExpression = association.getLogicalRule()._targetExpression;
    System.out.println("Raw logical expression : " + logicalRule);
    System.out.println("Resolved logical expression : " + logicalExpression);
    final String[] rootPaths = new String[] { "D:\\orchestra_data\\artifacts\\" };
    // Test getting data from physical to logical.
    {
      Collection<Artifact> artifacts = association.getPhysicalRule().findMatchingFiles(rootPaths, association.getLogicalRule());
      for (Artifact artifact : artifacts) {
        System.out.println("Selected artifact root path is : " + artifact._physicalPath);
        System.out.println("And logical one is : " + artifact._logicalPath);
      }
    }
  }

  public abstract class AbstractEvaluationContext {
    public abstract String getReferenceValueForGroup(Integer groupId_p);

    public abstract String getValueForGroup(Integer groupId_p);
  }

  public abstract class AbstractExpressionNode<E> {
    protected ExpressionType _type;
    protected E _rawValue;

    public AbstractExpressionNode(E rawValue_p, ExpressionType type_p) {
      _rawValue = rawValue_p;
      setType(type_p);
    }

    public abstract String evaluateExpressionValue(AbstractEvaluationContext context_p);

    public E getRawValue() {
      return _rawValue;
    }

    public ExpressionType getType() {
      return _type;
    }

    public void setType(ExpressionType type_p) {
      _type = type_p;
    }
  }

  public class Artifact {
    public String _physicalPath;
    public String _logicalPath;
  }

  public class Association {
    protected Map<Integer, String> _groups;
    protected PhysicalRule _physicalRule;
    protected Rule _logicalRule;

    public Association(Map<Integer, String> groups_p, String physicalExpression_p, String logicalExpression_p) {
      Assert.isNotNull(groups_p, "Must specify a map of groups !");
      Assert.isNotNull(physicalExpression_p, "Must specify a physical expression !");
      Assert.isNotNull(logicalExpression_p, "Must specify a logical expression !");
      _groups = groups_p;
      _physicalRule = new PhysicalRule(physicalExpression_p);
      _physicalRule.computeTargetExpression(groups_p);
      _logicalRule = new Rule(logicalExpression_p);
      _logicalRule.computeTargetExpression(groups_p);
    }

    public Rule getLogicalRule() {
      return _logicalRule;
    }

    public PhysicalRule getPhysicalRule() {
      return _physicalRule;
    }
  }

  public enum ExpressionType {
    TEXT, GROUP, GROUP_REFERENCE
  }

  public class GroupExpressionNode extends AbstractExpressionNode<Integer> {
    public GroupExpressionNode(Integer rawValue_p) {
      super(rawValue_p, ExpressionType.GROUP);
    }

    @Override
    public String evaluateExpressionValue(AbstractEvaluationContext context_p) {
      switch (_type) {
        case GROUP:
          return context_p.getValueForGroup(_rawValue);
        case GROUP_REFERENCE:
          return context_p.getReferenceValueForGroup(_rawValue);
        default:
        break;
      }
      return null;
    }

    public boolean isReference() {
      return ExpressionType.GROUP_REFERENCE.equals(_type);
    }
  }

  public interface IArtifactFactory {
    public Artifact createArtifactFromLogicalPath(String logicalPath_p);

    public Artifact createArtifactFromPhysicalPath(String physicalPath_p);
  }

  public class PhysicalRule extends Rule {
    /**
     * Physical path pattern.
     */
    private Pattern _physicalPattern;

    /**
     * Constructor.
     * @param expression_p
     */
    public PhysicalRule(String expression_p) {
      super(expression_p);
    }

    /**
     * @see com.thalesgroup.orchestra.framework.association.AssociationResolver.Rule#computeTargetExpression(java.util.Map)
     */
    @Override
    protected String computeTargetExpression(Map<Integer, String> groupToValue_p) {
      String result = super.computeTargetExpression(groupToValue_p);
      if (null != result) {
        _physicalPattern = Pattern.compile(_targetExpression);
      }
      return result;
    }

    protected void findMatchingFiles(File rootFile_p, PhysicalRuleFileFilter filter_p, Rule logicalRule_p, Collection<Artifact> resultingArtifacts_p) {
      File[] listFiles = rootFile_p.listFiles();
      if (null != listFiles) {
        for (File file : listFiles) {
          // Keep file.
          if (filter_p.accept(file)) {
            String absolutePath = file.getAbsolutePath();
            final Map<Integer, String> originalGroupIdToValue = new HashMap<Integer, String>(0);
            for (Entry<Integer, Integer> entry : _originalIdToTargetId.entrySet()) {
              Integer targetId = entry.getValue();
              originalGroupIdToValue.put(entry.getKey(), filter_p.getTargetGroupValue(targetId));
            }
            Artifact result = new Artifact();
            result._physicalPath = absolutePath;
            result._logicalPath = logicalRule_p.computeExpression(new AbstractEvaluationContext() {
              @Override
              public String getValueForGroup(Integer groupId_p) {
                return originalGroupIdToValue.get(groupId_p);
              }

              @Override
              public String getReferenceValueForGroup(Integer groupId_p) {
                return getValueForGroup(groupId_p);
              }
            });
            resultingArtifacts_p.add(result);
          }
          // Go down subtree.
          if (file.isDirectory()) {
            findMatchingFiles(file, filter_p, logicalRule_p, resultingArtifacts_p);
          }
        }
      }
    }

    protected Collection<Artifact> findMatchingFiles(String[] rootPaths_p, Rule logicalRule_p) {
      if (null == _physicalPattern) {
        return Collections.emptyList();
      }
      Collection<Artifact> result = new ArrayList<Artifact>(0);
      // First, read file system, and apply expression filter.
      for (final String rootPath : rootPaths_p) {
        File rootDirectory = new File(rootPath);
        // Precondition.
        if (!rootDirectory.exists() || !rootDirectory.isDirectory()) {
          continue;
        }
        PhysicalRuleFileFilter filter = new PhysicalRuleFileFilter(_physicalPattern, rootPath);
        findMatchingFiles(rootDirectory, filter, logicalRule_p, result);
      }
      return result;
    }
  }

  public class PhysicalRuleFileFilter implements FileFilter {
    private String _rootPath;
    private Pattern _pattern;
    private Matcher _matcher;

    public PhysicalRuleFileFilter(Pattern patternFilter_p, String rootPath_p) {
      _rootPath = rootPath_p;
      _pattern = patternFilter_p;
      _matcher = _pattern.matcher(ICommonConstants.EMPTY_STRING);
    }

    /**
     * @see java.io.FileFilter#accept(java.io.File)
     */
    public boolean accept(File pathname_p) {
      // Precondition.
      if ((null == _rootPath) || (null == _pattern)) {
        return false;
      }
      String shortPath = pathname_p.getAbsolutePath().replace(_rootPath, ICommonConstants.EMPTY_STRING);
      return _matcher.reset(shortPath).matches();
    }

    protected String getTargetGroupValue(Integer groupId_p) {
      return _matcher.group(groupId_p);
    }
  }

  public class Rule {
    protected String _expression;
    protected String _targetExpression;
    protected Map<Integer, Integer> _originalIdToTargetId;

    public Rule(String expression_p) {
      _expression = expression_p;
      _originalIdToTargetId = new HashMap<Integer, Integer>(0);
    }

    protected String computeExpression(AbstractEvaluationContext context_p) {
      Couple<List<AbstractExpressionNode>, List<Integer>> decomposedExpression = decomposeExpression();
      String result = ICommonConstants.EMPTY_STRING;
      for (AbstractExpressionNode node : decomposedExpression.getKey()) {
        result += node.evaluateExpressionValue(context_p);
      }
      return result;
    }

    protected String computeTargetExpression(final Map<Integer, String> groupToValue_p) {
      String result = computeExpression(new AbstractEvaluationContext() {
        @Override
        public String getReferenceValueForGroup(Integer groupId_p) {
          return "\\" + Rule.this.getTargetIdForGroup(groupId_p);
        }

        @Override
        public String getValueForGroup(Integer groupId_p) {
          return "(" + groupToValue_p.get(groupId_p) + ")";
        }
      });
      _targetExpression = result;
      return result;
    }

    protected Couple<List<AbstractExpressionNode>, List<Integer>> decomposeExpression() {
      // Real expression formatting, and groups reminders.
      List<AbstractExpressionNode> expressionNodes = new ArrayList<AbstractExpressionNode>(0);
      List<Integer> groupsIndex = new ArrayList<Integer>(0);
      Map<Integer, Boolean> originalIdToReference = new HashMap<Integer, Boolean>(0);
      // Break the expression into pieces.
      String value = _expression;
      while (__matcher.reset(value).matches()) {
        String result = __matcher.group(2);
        // Work with result.
        result = result.replace("(", ICommonConstants.EMPTY_STRING);
        result = result.replace(")", ICommonConstants.EMPTY_STRING);
        Integer groupNumber = null;
        try {
          groupNumber = new Integer(result);
        } catch (NumberFormatException nfe_p) {
          System.out.println("Not a valid group number !");
          System.out.println("Stop the matching here.");
          __matcher.reset();
          return null;
        }
        groupsIndex.add(0, groupNumber);
        originalIdToReference.put(groupNumber, Boolean.FALSE);
        expressionNodes.add(0, new TextExpressionNode(__matcher.group(3)));
        expressionNodes.add(0, new GroupExpressionNode(groupNumber));
        value = __matcher.group(1);
      }
      expressionNodes.add(0, new TextExpressionNode(value));
      // Clear groups index so as to get only one link between an original ID and its target position.
      List<Integer> alreadyTestedOriginalIds = new ArrayList<Integer>(0);
      for (Iterator<Integer> originalIdsIterator = groupsIndex.iterator(); originalIdsIterator.hasNext();) {
        Integer groupId = originalIdsIterator.next();
        if (!alreadyTestedOriginalIds.contains(groupId)) {
          alreadyTestedOriginalIds.add(groupId);
        } else {
          originalIdsIterator.remove();
        }
      }
      // Cycle through nodes so as to refine link between original ID group and target one.
      for (AbstractExpressionNode<?> node : expressionNodes) {
        switch (node.getType()) {
          case GROUP:
            Integer rawValue = (Integer) node.getRawValue();
            Boolean referenced = originalIdToReference.get(rawValue);
            if ((null == referenced) || (!referenced.booleanValue())) {
              int targetId = groupsIndex.lastIndexOf(rawValue) + 1;
              if (targetId > 0) {
                _originalIdToTargetId.put(rawValue, targetId);
              }
              originalIdToReference.put(rawValue, Boolean.TRUE);
            } else {
              node.setType(ExpressionType.GROUP_REFERENCE);
            }
          break;
          default:
          break;
        }
      }
      return new Couple<List<AbstractExpressionNode>, List<Integer>>(expressionNodes, groupsIndex);
    }

    protected Integer getOriginalIdFromTargetOne(Integer targetGroupId_p) {
      Integer result = null;
      // Precondition.
      if (null == targetGroupId_p) {
        return result;
      }
      // Search for target ID.
      for (Entry<Integer, Integer> entry : _originalIdToTargetId.entrySet()) {
        if (targetGroupId_p.equals(entry.getValue())) {
          result = entry.getKey();
          break;
        }
      }
      return result;
    }

    protected Integer getTargetIdForGroup(Integer groupId_p) {
      return _originalIdToTargetId.get(groupId_p);
    }
  }

  public class TextExpressionNode extends AbstractExpressionNode<String> {
    public TextExpressionNode(String rawValue_p) {
      super(rawValue_p, ExpressionType.TEXT);
    }

    @Override
    public String evaluateExpressionValue(AbstractEvaluationContext context_p) {
      return _rawValue;
    }
  }
}