# legacyutils (by GMaur)

### Importing the dependency

Add JitPack as a `repository`:

```xml
<repositories>
  <repository>
      <id>jitpack.io</id>
      <url>https://jitpack.io</url>
  </repository>
</repositories>
```

Add to `dependencies`:

```xml
<dependency>
    <groupId>com.github.gmaur</groupId>
    <artifactId>legacyutils</artifactId>
    <version>v0.0.1</version>
</dependency>
```

See JitPack for more information (gradle/leiningen/sbt/maven): https://jitpack.io/#gmaur/legacyutils/

### Using the dependency

A quick example:

```java
import com.gmaur.legacycode.legacyutils.output.MockSystemOutput;

@Test
public void inject_the_system_output() {
  // inject the SystemOutput
  final MockSystemOutput systemOutput = MockSystemOutput.inject();

  // do your logic that prints to the System.out
  your_logic_here();

  // you can catch the output using:
  assertThat(systemOutput.toString(), equalTo("hello world!" + System.System.lineSeparator()));
}

public void your_logic_here() {
  System.out.println("hello world!");
}
```

See the [tests](https://github.com/GMaur/legacyutils/blob/master/src/test/) for more information

## License

[LGPL3](https://github.com/GMaur/legacyutils/blob/master/LICENSE.md)
