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
package org.sonar.php.tree.impl.statement;

import org.junit.Test;
import org.sonar.php.PHPTreeModelTest;
import org.sonar.php.parser.PHPLexicalGrammar;
import org.sonar.plugins.php.api.tree.Tree.Kind;
import org.sonar.plugins.php.api.tree.statement.TryStatementTree;

import static org.fest.assertions.Assertions.assertThat;

public class TryStatementTreeTest extends PHPTreeModelTest {

  @Test
  public void testTwoCatchBlocks() throws Exception {
    TryStatementTree tree = parse("try {} catch(Exception1 $e1) {} catch(Exception2 $e2) {}", PHPLexicalGrammar.TRY_STATEMENT);

    assertThat(tree.is(Kind.TRY_STATEMENT)).isTrue();
    assertThat(tree.catchBlocks()).hasSize(2);
    assertThat(tree.finallyToken()).isNull();
  }

  @Test
  public void testFinallyBlock() throws Exception {
    TryStatementTree tree = parse("try {} finally {}", PHPLexicalGrammar.TRY_STATEMENT);

    assertThat(tree.is(Kind.TRY_STATEMENT)).isTrue();
    assertThat(tree.catchBlocks()).hasSize(0);
    assertThat(tree.finallyToken()).isNotNull();
    assertThat(tree.finallyBlock()).isNotNull();
  }

}
