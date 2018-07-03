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

import net.kyori.lunar.exception.Exceptions;
import net.kyori.xml.node.Node;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.List;
import java.util.stream.Collectors;

public class Build {
  private final int id;
  private final String url;
  private final long timestamp;
  private final List<Artifact> artifacts;

  Build(final Node node) {
    this.id = Integer.parseInt(node.nodes("id").one().required().value());
    this.url = node.nodes("url").one().required().value();
    this.timestamp = Long.parseLong(node.nodes("timestamp").one().required().value());
    this.artifacts = node
      .elements("artifact")
      .map(Exceptions.rethrowFunction(artifact -> new Artifact(this, artifact)))
      .collect(Collectors.toList());
  }

  public int id() {
    return this.id;
  }

  public String url() {
    return this.url;
  }

  public long timestamp() {
    return this.timestamp;
  }

  public List<Artifact> artifacts() {
    return this.artifacts;
  }

  public @Nullable Artifact artifact(final String fileName) {
    for(Artifact artifact : this.artifacts) {
      if(artifact.fileName().equals(fileName)) {
        return artifact;
      }
    }
    return null;
  }
}
