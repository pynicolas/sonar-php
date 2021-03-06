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

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.AstNodeType;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.php.checks.utils.AbstractDuplicateBranchCheck;
import org.sonar.php.parser.PHPGrammar;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;

@Rule(
  key = "S1871",
  name = "Two branches in the same conditional structure should not have exactly the same implementation",
  tags = {Tags.BUG},
  priority = Priority.MAJOR)
@BelongsToProfile(title = CheckList.SONAR_WAY_PROFILE, priority = Priority.MAJOR)
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.LOGIC_RELIABILITY)
@SqaleConstantRemediation("10min")
public class DuplicateBranchImplementationCheck extends AbstractDuplicateBranchCheck {

  @Override
  protected AstNodeType ifBranchNodeType() {
    return PHPGrammar.STATEMENT;
  }

  @Override
  protected AstNodeType caseClauseChildType() {
    return PHPGrammar.INNER_STATEMENT_LIST;
  }

  @Override
  protected void addIssue(String type, AstNode duplicate, AstNode duplicated) {
    String message = "This {0}''s code block is the same as the block for the {0} on line {1}.";
    getContext().createLineViolation(this, message, duplicate.getParent(), type, duplicated.getParent().getTokenLine());
  }

}
