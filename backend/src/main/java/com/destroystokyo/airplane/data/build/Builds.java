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

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.kyori.lunar.EvenMoreObjects;
import net.kyori.lunar.exception.Exceptions;
import net.kyori.xml.node.Node;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.IOException;
import java.net.URL;
import java.util.function.Function;
import java.util.stream.Collectors;

@JsonSerialize(using = Builds.Serializer.class)
public class Builds {
  private static final String ALL_BUILDS = EvenMoreObjects.make(new StringBuilder(), sb -> sb
    .append("https://destroystokyo.com/ci/job/Paper/api/xml?tree=allBuilds[")
    .append("artifacts[*],")
    .append("id[*],")
    .append("result[*],")
    .append("timestamp[*],")
    .append("url[*]")
    .append(']')).toString();
  private final Int2ObjectMap<Build> builds;

  public Builds() throws IOException, JDOMException {
    this(Node.of(new SAXBuilder().build(new URL(ALL_BUILDS)).getRootElement()));
  }

  private Builds(final Node node) {
    this.builds = node
      .nodes("allBuild")
      .map(Exceptions.rethrowFunction(Build::new))
      .filter(build -> build.artifacts().size() > 0)
      .collect(Collectors.toMap(
        Build::id,
        Function.identity(),
        (v1, v2) -> {
          throw new RuntimeException(String.format("Duplicate key for values %s and %s", v1, v2));
        },
        Int2ObjectLinkedOpenHashMap::new
      ));
  }

  public @Nullable Build one(final int build) {
    return this.builds.getOrDefault(build, null);
  }

  public static class Serializer extends JsonSerializer<Builds> {
    @Override
    public void serialize(final Builds builds, final JsonGenerator generator, final SerializerProvider provider) throws IOException {
      generator.writeStartArray();
      for(final Int2ObjectMap.Entry<Build> entry : builds.builds.int2ObjectEntrySet()) {
        generator.writeStartObject();
        generator.writeNumberField("id", entry.getIntKey());
        generator.writeStringField("url", entry.getValue().url());
        generator.writeNumberField("timestamp", entry.getValue().timestamp());
        generator.writeFieldName("artifacts");
        generator.writeStartArray();
        for(final Artifact artifact : entry.getValue().artifacts()) {
          generator.writeString(artifact.fileName());
        }
        generator.writeEndArray();
        generator.writeEndObject();
      }
      generator.writeEndArray();
    }
  }
}
