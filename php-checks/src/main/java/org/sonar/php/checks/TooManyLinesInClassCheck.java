/*
 * SonarQube PHP Plugin
 * Copyright (C) 2010 SonarSource and Akram Ben Aissi
 * sonarqube@googlegroups.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.php.checks;

import com.google.common.collect.ImmutableList;
import java.util.List;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.plugins.php.api.tree.Tree;
import org.sonar.plugins.php.api.tree.Tree.Kind;
import org.sonar.plugins.php.api.tree.declaration.ClassDeclarationTree;
import org.sonar.plugins.php.api.tree.expression.IdentifierTree;
import org.sonar.plugins.php.api.visitors.PHPSubscriptionCheck;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;

@Rule(
  key = TooManyLinesInClassCheck.KEY,
  name = "Classes should not have too many lines",
  priority = Priority.MAJOR,
  tags = {Tags.BRAIN_OVERLOAD})
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.ARCHITECTURE_CHANGEABILITY)
@SqaleConstantRemediation("1h")
public class TooManyLinesInClassCheck extends PHPSubscriptionCheck {

  public static final String KEY = "S2042";

  private static final String MESSAGE_FORMAT = "Class \"%s\" has %s lines, which is greater than the %s authorized. Split it into smaller classes.";

  private static final int DEFAULT = 200;

  @RuleProperty(
    key = "maximumLinesThreshold",
    defaultValue = "" + DEFAULT)
  public int maximumLinesThreshold = DEFAULT;

  @Override
  public List<Kind> nodesToVisit() {
    return ImmutableList.of(Kind.CLASS_DECLARATION, Kind.INTERFACE_DECLARATION);
  }

  @Override
  public void visitNode(Tree tree) {
    ClassDeclarationTree declaration = (ClassDeclarationTree) tree;
    int numberOfLines = declaration.closeCurlyBraceToken().line() - declaration.openCurlyBraceToken().line() + 1;
    if (numberOfLines > maximumLinesThreshold) {
      IdentifierTree name = declaration.name();
      context().newIssue(KEY, String.format(MESSAGE_FORMAT, name.text(), numberOfLines, maximumLinesThreshold)).tree(name);
    }
  }

}
