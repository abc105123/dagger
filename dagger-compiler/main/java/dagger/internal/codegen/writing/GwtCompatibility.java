/*
 * Copyright (C) 2017 The Dagger Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dagger.internal.codegen.writing;

import static com.google.common.base.Preconditions.checkArgument;

import androidx.room.compiler.codegen.XAnnotationSpec;
import androidx.room.compiler.processing.XAnnotation;
import androidx.room.compiler.processing.XElement;
import dagger.internal.codegen.binding.Binding;
import dagger.internal.codegen.xprocessing.XAnnotations;
import java.util.Optional;

final class GwtCompatibility {

  /**
   * Returns a {@code @GwtIncompatible} annotation that is applied to {@code binding}'s {@link
   * Binding#bindingElement()} or any enclosing type.
   */
  static Optional<XAnnotationSpec> gwtIncompatibleAnnotation(Binding binding) {
    checkArgument(binding.bindingElement().isPresent());
    XElement element = binding.bindingElement().get();
    while (element != null) {
      Optional<XAnnotationSpec> gwtIncompatible =
          element.getAllAnnotations().stream()
              .filter(GwtCompatibility::isGwtIncompatible)
              .map(XAnnotations::asClassName)
              .map(XAnnotationSpec::of)
              .findFirst();
      if (gwtIncompatible.isPresent()) {
        return gwtIncompatible;
      }
      element = element.getEnclosingElement();
    }
    return Optional.empty();
  }

  private static boolean isGwtIncompatible(XAnnotation annotation) {
    return XAnnotations.getClassName(annotation).simpleName().contentEquals("GwtIncompatible");
  }
}
