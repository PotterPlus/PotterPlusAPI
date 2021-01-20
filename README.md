# PotterPlusAPI

This is the API used by PotterPlus to power its many plugins.

### Maven

To install via Maven, in your pom.xml:

> Make sure you update the {VERSION} placeholder to the [latest version](https://oss.sonatype.org/#nexus-search;classname~PotterPlusAPI)

```
<repositories>
    <repository>
        <id>sonatype</id>
        <url>https://oss.sonatype.org/content/repositories/snapshots</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>io.github.grisstyl.ppapi</groupId>
        <artifactId>PotterPlusAPI</artifactId>
        <version>{VERSION}</version>
    </dependency>
</dependencies>
```