/*
 * Copyright 2015 Google, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.netflix.spinnaker.rosco.providers.util

class DockerFriendlyPackerCommandFactory implements PackerCommandFactory {

  @Override
  List<String> buildPackerCommand(String baseCommand, Map<String, String> parameterMap, String templateFile) {
    def packerCommand = ["sh", "-c"]
    def shellCommandStr = baseCommand + "packer build -color=false"

    parameterMap.each { key, value ->
      if (key && value) {
        def keyValuePair = value instanceof String && value.contains(" ") ? "\"$key=$value\"" : "$key=$value"

        shellCommandStr += " -var $keyValuePair"
      }
    }

    shellCommandStr += " $templateFile"
    packerCommand << shellCommandStr
    packerCommand
  }

}