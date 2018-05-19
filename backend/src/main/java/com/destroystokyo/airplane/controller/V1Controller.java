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
package com.destroystokyo.airplane.controller;

import com.destroystokyo.airplane.data.build.Artifact;
import com.destroystokyo.airplane.data.build.Build;
import com.destroystokyo.airplane.data.build.Builds;
import com.destroystokyo.airplane.exception.FourOhFourException;
import com.google.common.base.Suppliers;
import net.kyori.lunar.exception.Exceptions;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@RestController
@RequestMapping("/v1/")
public class V1Controller {
  private final Supplier<Builds> builds = Suppliers.memoizeWithExpiration(Exceptions.rethrowSupplier(Builds::new)::get, 5, TimeUnit.MINUTES);

  @CrossOrigin
  @GetMapping(path = "/downloads/", produces = "application/json")
  @ResponseBody
  public ResponseEntity<?> downloads() {
    return ResponseEntity.ok(this.builds.get());
  }

  @GetMapping("/download/{build}/{variant}/")
  public RedirectView downloadBuild(final @PathVariable("build") int buildNumber, final @PathVariable("variant") String variant) throws IOException {
    final Build build = this.builds.get().one(buildNumber);
    if(build == null) {
      throw new FourOhFourException();
    }

    final @Nullable Artifact artifact = build.artifact(variant);
    if(artifact == null) {
      throw new FourOhFourException();
    }

    return new RedirectView(artifact.url());
  }
}
