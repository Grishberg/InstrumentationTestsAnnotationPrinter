# InstrumentationTestsAnnotationPrinter
InstrumentationTestsAnnotationPrinter

To use add gradle dependency to target android project:
```
androidTestImplementation "com.github.grishberg:annotationprinter:${printer_version}"
```
Log parser placed in https://github.com/Grigory-Rylov/android-instrumental-test-runner

To test sample use:
`
adb shell am instrument -w -r -e log true -e listener com.github.grishberg.annotationprinter.AnnotationsTestPrinter -e debug false -e class 'com.github.grigory_rylov.instrumentaltestwithannotations.ExampleInstrumentedTest' com.github.grigory_rylov.instrumentaltestwithannotations.test/android.support.test.runner.AndroidJUnitRunner
`

Copyright 2017 Grigory Rylov

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
