## Timeline-history
## TODO
- [x] sub-module availabilities (8)
- [x] sub-module directories (5)
- [x] root pom (6)
- [x] module pom (6)
- [x] sub-module pom (5)
- [x] module dependency (8)
- [x] sub-module dependency (7)
- [x] jar scanner (5)
- [x] jar installer (5)
- [ ] build element (2)
- [ ] web project (2)
- [ ] ear project (5)
- [ ] web assembly (4)
- [ ] persistence assembly(3)
- [ ] ejb assembly (2)
- [ ] general assembly (1)
- [ ] presentation assembly (1)

### facts

https://maven.apache.org/guides/mini/guide-using-one-source-directory.html
https://maven.apache.org/ref/3.6.3/maven-model-builder/super-pom.html

```
<build>
    <sourceDirectory>../src/main/java</sourceDirectory>
    <plugins>
      <plugin>
        <artifactId>maven-compiler-plugin</artifactId>
        <version>2.0.2</version>
        <configuration>
          <includes><include>**/core/**</include></includes>
        </configuration>
      </plugin>
    </plugins>
  </build>
```

#### defaults 
```
<build>
    <directory>${project.basedir}/target</directory>
    <outputDirectory>${project.build.directory}/classes</outputDirectory>
    <finalName>${project.artifactId}-${project.version}</finalName>
    <testOutputDirectory>${project.build.directory}/test-classes</testOutputDirectory>
    <sourceDirectory>${project.basedir}/src/main/java</sourceDirectory>
    <scriptSourceDirectory>${project.basedir}/src/main/scripts</scriptSourceDirectory>
    <testSourceDirectory>${project.basedir}/src/test/java</testSourceDirectory>
    <resources>
      <resource>
        <directory>${project.basedir}/src/main/resources</directory>
      </resource>
    </resources>
    <testResources>
      <testResource>
        <directory>${project.basedir}/src/test/resources</directory>
      </testResource>
    </testResources>
  </build>
```
```

src/main/java/doradus/enterprise/name/general
src/main/java/doradus/enterprise/name/persistence
src/main/java/doradus/enterprise/name/presentation
src/main/webapp/name

--check assembly files
```