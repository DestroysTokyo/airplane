/*
 * This file is part of airplane, licensed under the MIT License.
 *
 * Copyright (c) 2018 DestroysTokyo
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.destroystokyo.airplane.data.build;

import net.kyori.xml.node.Node;

public class Artifact {
  private final Build build;
  private final String displayPath;
  private final String fileName;
  private final String relativePath;

  Artifact(final Build build, final Node node) {
    this.build = build;
    this.displayPath = node.nodes("displayPath").one().required().value();
    this.fileName = node.nodes("fileName").one().required().value();
    this.relativePath = node.nodes("relativePath").one().required().value();
  }

  public String displayPath() {
    return this.displayPath;
  }

  public String fileName() {
    return this.fileName;
  }

  public String relativePath() {
    return this.relativePath;
  }

  public String url() {
    return this.build.url() + "/artifact/" + this.relativePath;
  }
}
