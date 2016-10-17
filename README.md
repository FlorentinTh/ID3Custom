# ID3 Custom

[![Licence Apache2](https://img.shields.io/hexpm/l/plug.svg)](http://www.apache.org/licenses/LICENSE-2.0)

---

This project refers to the Lab#1 of the course 8INF954 given out at the Université du Québec à Chicoutimi. 
The purpose of this lab was to reproduce the experimentation described in **[1]** that involve to customise the ID3 algorithm. 
This adaptation is basically a replacement of the Shannon's entropy by the Havrda-Charvat's entropy.

Requirements
---
* Java
* Apache Ant
* NodeJS 

Produce Dataset
---
```
$ node converter.js [base path] [name of the dataset] [.names file] [.data file]
```

Compile WEKA
---
* Install WEKA 3.x
* Copy ```C:/Program Files (x86)/Weka-3-x/weka-src.jar``` to a personal directory of your choice ```your_directory```
* Extract the jar using winzip 
* Past ```ID3Custom.java``` in ```your_directory/src/main/java/weka/classifiers/trees/```
* Edit ```your_directory/src/main/java/weka/gui/GenericObjectEditor.props```
* Add ```weka.classifiers.trees.ID3Custom,\``` under the list called ```Lists the Classifiers I want to choose from```
* Open a terminal in the root of ```your_directory``` 
* Build the jar with the command ```ant exejar``` 
* You can find the resulting file ```weka.jar``` in ```your_directory/dist```
* Execute ```RunWeka.bat```

Authors
-------
**[Florentin Thullier](https://github.com/florentinth)** & **[Baptiste Lemarcis](https://github.com/baptistelemarcis)** - _2016_

References
----------

**[1]**	S. Kumar and S. Jain, "Intrusion Detection and Classification Using Improved ID3 Algorithm of Data 
Mining," International Journal of Advanced Research in Computer Engineering & Technology (IJARCET), 
vol. 1, pp: 352-356, 2012.

License
---
    Copyright 2016 Florentin Thullier, Baptiste Lemarcis.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.