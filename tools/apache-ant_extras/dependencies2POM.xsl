<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
 xmlns:wyona-tools="http://www.wyona.org/tools/1.0"
 exclude-result-prefixes="wyona-tools"
>
<xsl:output method="xml" version="1.0" encoding="UTF-8" standalone="yes" omit-xml-declaration="yes" indent="yes"/>


<xsl:param name="dependencies-class" select="''"/>
<xsl:param name="group-ID"/>
<xsl:param name="artifact-ID"/>
<xsl:param name="version"/>


<xsl:template match="/">
<project>
  <modelVersion>4.0.0</modelVersion>
  <groupId><xsl:value-of select="$group-ID"/></groupId>
  <artifactId><xsl:value-of select="$artifact-ID"/></artifactId>
  <version><xsl:value-of select="$version"/></version>
  <dependencies>
  <xsl:for-each select="//wyona-tools:dependencies[@class = $dependencies-class]">
    <xsl:for-each select="dependency">
  <dependency>
    <groupId><xsl:value-of select="@groupId"/></groupId>
    <artifactId><xsl:value-of select="@artifactId"/></artifactId>
    <version><xsl:value-of select="@version"/></version>
  <xsl:if test="@optional = 'true'">
    <optional><xsl:value-of select="@optional"/></optional>
  </xsl:if>
  <xsl:if test="@scope and @scope != 'compile'">
    <scope><xsl:value-of select="@scope"/></scope>
  </xsl:if>
  </dependency>
    </xsl:for-each>
  </xsl:for-each>
  </dependencies>
</project>
</xsl:template>


</xsl:stylesheet>
