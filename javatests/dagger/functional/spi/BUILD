# Copyright (C) 2017 The Dagger Authors.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

# Description:
#   Functional tests for the experimental Dagger SPI

load("@rules_java//java:defs.bzl", "java_library", "java_plugin")
load("//:test_defs.bzl", "GenJavaTests")

package(default_visibility = ["//:src"])

java_plugin(
    name = "test_plugin",
    srcs = ["TestPlugin.java"],
    deps = [
        "//dagger-spi",
        "//third_party/java/auto:service",
        "//third_party/java/guava/base",
        "//third_party/java/guava/collect",
        "//third_party/java/javapoet",
    ],
)

java_library(
    name = "test_lib",
    exported_plugins = [":test_plugin"],
)

GenJavaTests(
    name = "tests",
    srcs = glob(
        ["*.java"],
        exclude = ["TestPlugin.java"],
    ),
    functional = 0,
    test_only_deps = [
        "//third_party/java/truth",
        "//third_party/java/junit",
    ],
    deps = [
        ":test_lib",
        "//third_party/java/dagger",
        "//third_party/java/guava",
    ],
)
