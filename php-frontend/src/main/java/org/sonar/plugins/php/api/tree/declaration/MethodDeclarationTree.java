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
package org.sonar.plugins.php.api.tree.declaration;

import com.google.common.annotations.Beta;
import org.sonar.php.api.PHPKeyword;
import org.sonar.php.api.PHPPunctuator;
import org.sonar.plugins.php.api.tree.Tree;
import org.sonar.plugins.php.api.tree.expression.IdentifierTree;
import org.sonar.plugins.php.api.tree.lexical.SyntaxToken;

import java.util.List;

/**
 * <p>Class <a href="http://php.net/manual/en/language.oop5.basic.php">Methods</a>
 * <pre>
 *  public {@link #name()} {@link Tree.Kind#BLOCK {}}
 *  protected {@link #name()} {@link Tree.Kind#BLOCK {}}
 *  private {@link #name()} {@link Tree.Kind#BLOCK {}}
 *
 *  public static {@link #name()} {@link Tree.Kind#BLOCK {}}
 *  public final {@link #name()} {@link Tree.Kind#BLOCK {}}
 *
 *  abstract public {@link #name()} ;
 * </pre>
 */
@Beta
public interface MethodDeclarationTree extends ClassMemberTree, FunctionTree {

  /**
   * Members can be:
   * <ul>
   *   <li>{@link PHPKeyword#PUBLIC public}
   *   <li>{@link PHPKeyword#PROTECTED protected}
   *   <li>{@link PHPKeyword#PRIVATE private}
   *   <li>{@link PHPKeyword#STATIC static}
   *   <li>{@link PHPKeyword#ABSTRACT abstract}
   *   <li>{@link PHPKeyword#FINAL final}
   * <ul/>
   */
  List<SyntaxToken> modifiersToken();

  IdentifierTree name();

  /**
   * Either {@link PHPPunctuator#SEMICOLON ;} or {@link Tree.Kind#BLOCK block}
   */
  @Override
  Tree body();

}
