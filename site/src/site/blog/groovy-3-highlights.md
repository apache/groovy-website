---
layout: post
title: Groovy 3 Highlights
date: '2020-02-13T02:28:07+00:00'
permalink: groovy-3-highlights
---
<h1>Groovy 3 Highlights&nbsp;</h1> 
  <h2>General Improvements</h2> 
  <p>Groovy has both a dynamic nature (supporting code styles similar to Ruby and Python) as well as a static nature (supporting styles similar to Java, Kotlin and Scala). Groovy continues to improve both those natures - filling in any feature gaps. As just one example, Groovy has numerous facilities for better managing null values. You can use Groovy's null-safe navigation operator, piggy back on Java's <font face="courier new, courier, monospace">Optional</font> or provide a null-checking extension to the type checker. These are augmented in Groovy 3 with null-safe indexing for arrays, lists and maps and a new AST transformation <font face="courier new, courier, monospace">@NullCheck</font> for automatically instrumenting code with null checks.</p> 
  <p>In general,&nbsp;the language design borrows heavily from Java, so careful attention is paid to changes in Java and acted on accordingly if appropriate. A lot of work has been done getting Groovy ready for Java modules and for making it work well with JDK versions 9-15. Other work has dramatically improved the performance of bytecode generation which makes use of the JVMs invoke dynamic capabilities. Additional changes are already underway for further improvements in these areas in Groovy 4.</p> 
  <p>There are also many other performance improvements under the covers. More efficient type resolution occurs during compilation and more efficient byecode is generated for numerous scenarios. The addition of a Maven BOM allows more flexible usage of Groovy from other projects.</p> 
  <p>Groovy also has particular strengths for scripting, testing, writing Domain Specific Languages (DSLs) and in domains like financial calculations and data science. On-going work has been made to ensure those strengths are maintained. The accuracy used for high-precision numbers has been improved and is configurable. Much of the tooling such as Groovy Console and groovysh have also been improved.</p> 
  <p>Other key strengths of Groovy such as its runtime and compile-time meta-programming capabilities have also seen many minor enhancements. All in all, this release represents the culmination of several years of activity. Over 500 new features, improvements and bug fixes have been added since Groovy 2.5. Just a few highlights are discussed below.</p> 
  <h2>Parrot parser</h2> 
  <p>Groovy has a new parser. While mostly an internal change within Groovy, the good news for users is that the new parser is more flexible and will allow the language to more rapidly change should the need arise.</p> 
  <h3>New syntax</h3> 
  <p>The new parser gave us the opportunity to add some new syntax features:</p> 
  <ul> 
    <li>!in and !instanceof operators</li> 
  </ul> 
  <blockquote style="margin: 0px 0px 0px 40px; border: medium none; padding: 0px;"> 
    <pre style="margin-top: 0px; margin-bottom: 0px; margin-left: 40px; border: medium none; padding: 0px;">assert 45 !instanceof Date
assert 4 !in [1, 3, 5, 7]</pre> 
  </blockquote> 
  <ul> 
    <li>Elvis assignment operator</li> 
  </ul> 
  <blockquote style="margin: 0px 0px 0px 40px; border: medium none; padding: 0px;"> 
    <pre style="margin-top: 0px; margin-bottom: 0px; margin-left: 40px; border: medium none; padding: 0px;">def first = 'Jane'
def last = null
first ?= 'John'
last ?= 'Doe'
assert [first, last] == ['Jane', 'Doe']</pre> 
  </blockquote> 
  <ul> 
    <li>Identity comparison operators</li> 
  </ul> 
  <blockquote style="margin: 0px 0px 0px 40px; border: medium none; padding: 0px;"> 
    <pre style="margin-top: 0px; margin-bottom: 0px; margin-left: 40px; border: medium none; padding: 0px;">assert cat === copyCat&nbsp; // operator shorthand for <font face="courier new, courier, monospace">is</font> method
assert cat !== lion&nbsp; &nbsp; &nbsp;// negated operator shorthand</pre> 
  </blockquote> 
  <ul> 
    <li>Safe indexing (for maps, lists and arrays)</li> 
  </ul> 
  <blockquote style="margin: 0px 0px 0px 40px; border: none; padding: 0px;"> 
    <pre style="margin: 0px 0px 0px 40px; border: none; padding: 0px;">println map?['someKey'] // return null if map is null instead of throwing NPE</pre> 
  </blockquote> 
  <h3>Java compatibility</h3> 
  <p>The Groovy syntax can be thought of as a superset of Java syntax. It's considered good style to use the enhancements that Groovy provides when appropriate, but Groovy's aim is to still support as much of the Java syntax as possible to allow easy migration from Java or easy switching for folks working with both Java and Groovy.</p> 
  <p>The flexibility provided by the new parser allowed several syntax compatibility holes to be closed including:</p> 
  <p> </p> 
  <ul> 
    <li>do/while loop</li> 
  </ul> 
  <blockquote style="margin: 0px 0px 0px 40px; border: medium none; padding: 0px;"> 
    <pre style="margin: 0px 0px 0px 40px; border: medium none; padding: 0px;">def count = 5
def factorial = 1
do {
&nbsp; &nbsp; factorial *= count--
} while(count &gt; 1)
assert factorial == 120</pre> 
  </blockquote> 
  <ul> 
    <li>Enhanced classic Java-style for loop (see multi-assignment for-loop example; note the comma in the last clause of the for statement)</li> 
    <li>Multi-assignment in combination with for loop</li> 
  </ul> 
  <blockquote style="margin: 0px 0px 0px 40px; border: medium none; padding: 0px;"> 
    <pre style="margin: 0px 0px 0px 40px; border: medium none; padding: 0px;">def count = 3
println 'The next three months are:'
for (def (era, yr, mo) = new Date(); count--; yr = mo == 11 ? yr + 1 : yr, mo = mo == 11 ? 0 : mo + 1) {
&nbsp; &nbsp; println "$yr/$mo"
}</pre> 
  </blockquote> 
  <ul> 
    <li>Java-style array initialization (but you might prefer Groovy's literal list notation)</li> 
  </ul> 
  <blockquote style="margin: 0px 0px 0px 40px; border: medium none; padding: 0px;"> 
    <pre style="margin: 0px 0px 0px 40px; border: medium none; padding: 0px;">def primes = new int[] {2, 3, 5, 7, 11}</pre> 
  </blockquote> 
  <ul> 
    <li>Lambda expressions (but you might often prefer Groovy's Closures which support trampoline/tail recursion, partial application/currying, memoization/auto caching)</li> 
  </ul> 
  <blockquote style="margin: 0px 0px 0px 40px; border: medium none; padding: 0px;"> 
    <blockquote style="margin: 0px 0px 0px 40px; border: medium none; padding: 0px;"> 
      <pre>(1..10).forEach(e -&gt; { println e })</pre> 
    </blockquote> 
  </blockquote> 
  <blockquote style="margin: 0px 0px 0px 40px; border: medium none; padding: 0px;"> 
    <blockquote style="margin: 0px 0px 0px 40px; border: medium none; padding: 0px;"> 
      <pre>assert (1..10).stream()
              .filter(e -&gt; e % 2 == 0)
              .map(e -&gt; e * 2)
              .toList() == [4, 8, 12, 16, 20]</pre> 
    </blockquote> 
  </blockquote> 
  <ul> </ul> 
  <blockquote style="margin: 0px 0px 0px 40px; border: medium none; padding: 0px;"> 
    <blockquote style="margin: 0px 0px 0px 40px; border: medium none; padding: 0px;"> 
      <pre>def add = (int x, int y) -&gt; { def z = y; return x + z }
assert add(3, 4) == 7</pre> 
    </blockquote> 
  </blockquote> 
  <ul></ul> 
  <ul> 
    <li>Method references (but you might often prefer Groovy's Method pointers which are Closures with the previously mentioned benefits)</li> 
  </ul> 
  <blockquote style="margin: 0px 0px 0px 40px; border: medium none; padding: 0px;"> 
    <blockquote style="margin: 0px 0px 0px 40px; border: medium none; padding: 0px;"> 
      <pre>assert ['1', '2', '3'] == Stream.of(1, 2, 3)
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;                 .map(String::valueOf)
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;                 .toList()</pre> 
    </blockquote> 
  </blockquote> 
  <ul></ul> 
  <ul></ul> 
  <ul></ul> 
  <ul> 
    <li>&quot;var&quot; reserved type (allows Java 10/11 features even when using JDK 8)</li> 
  </ul> 
  <blockquote style="margin: 0px 0px 0px 40px; border: medium none; padding: 0px;"> 
    <pre style="margin: 0px 0px 0px 40px; border: medium none; padding: 0px;">var two = 2&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; // Java 10
IntFunction&lt;Integer&gt; twice = (final var x) -&gt; x * two&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; // Java 11
assert [1, 2, 3].collect{ twice.apply(it) } == [2, 4, 6]</pre> 
  </blockquote> 
  <ul> 
    <li>ARM Try with resources (Java 7 and 9 variations work on JDK 8 - but you might prefer Groovy's internal iteration methods for resources)</li> 
  </ul> 
  <blockquote style="margin: 0px 0px 0px 40px; border: medium none; padding: 0px;"> 
    <pre style="margin: 0px 0px 0px 40px; border: medium none; padding: 0px;">def file = new File('/path/to/file.ext')
def reader = file.newReader()
try(reader) {
&nbsp; &nbsp; String line = null
&nbsp; &nbsp; while (line = reader.readLine()) {
&nbsp; &nbsp; &nbsp; &nbsp; println line
&nbsp; &nbsp; }
}</pre> 
  </blockquote> 
  <ul> 
    <li>Nested code blocks</li> 
    <li>Java-style non-static inner class instantiation</li> 
    <li>Interface default methods (but you might prefer Groovy's traits)</li> 
  </ul> 
  <blockquote style="margin: 0px 0px 0px 40px; border: none; padding: 0px;"> 
    <pre style="margin: 0px 0px 0px 40px; border: none; padding: 0px;">interface Greetable {
&nbsp; &nbsp; String target()
&nbsp; &nbsp; default String salutation() {
&nbsp; &nbsp; &nbsp; &nbsp; 'Greetings'
&nbsp; &nbsp; }
&nbsp; &nbsp; default String greet() {
&nbsp; &nbsp; &nbsp; &nbsp; "${salutation()}, ${target()}"
&nbsp; &nbsp; }
}</pre> 
  </blockquote> 
  <blockquote style="margin: 0px 0px 0px 40px; border: medium none; padding: 0px;"> </blockquote> 
  <ul> </ul> 
  <h2>Split package changes</h2> 
  <p>In preparation for Groovy's modular jars to be first class modules, several classes have moved packages. Some examples:</p> 
  <blockquote style="margin: 0px 0px 0px 40px; border: medium none; padding: 0px;"> 
    <pre>groovy.util.XmlParser =&gt; groovy.xml.XmlParser
groovy.util.XmlSlurper =&gt; groovy.xml.XmlSlurper
groovy.util.GroovyTestCase =&gt;&nbsp;groovy.test.GroovyTestCase</pre> 
  </blockquote> 
  <p>In most cases, both the old and new class are available in Groovy 3. But by Groovy 4, the old classes will be removed. See the <a href="http://groovy-lang.org/releasenotes/groovy-3.0.html" title="release notes">release notes</a> for a complete list of these changes.&nbsp;</p> 
  <h2>DGM improvements</h2> 
  <p>Groovy adds many extension methods to existing Java classes. In Groovy 3, about 80 new such extension methods were added. We highlight just a few here:</p> 
  <p> </p> 
  <ul> 
    <li>average() on arrays and iterables</li> 
  </ul> 
  <blockquote style="margin: 0px 0px 0px 40px; border: medium none; padding: 0px;"> 
    <blockquote style="margin: 0px 0px 0px 40px; border: medium none; padding: 0px;"> 
      <pre>assert 3 == [1, 2, 6].average()</pre> 
    </blockquote> 
  </blockquote> 
  <p> </p> 
  <ul> 
    <li><font face="courier new, courier, monospace">takeBetween()</font> on <font face="courier new, courier, monospace">String</font>, <font face="courier new, courier, monospace">CharSequence</font> and <font face="courier new, courier, monospace">GString</font></li> 
  </ul> 
  <blockquote style="margin: 0px 0px 0px 40px; border: medium none; padding: 0px;"> 
    <blockquote style="margin: 0px 0px 0px 40px; border: medium none; padding: 0px;"> 
      <pre>assert 'Groovy'.takeBetween( 'r', 'v' ) == 'oo'</pre> 
    </blockquote> 
  </blockquote> 
  <p> </p> 
  <ul> 
    <li><font face="courier new, courier, monospace">shuffle()</font> and <font face="courier new, courier, monospace">shuffled()</font> on arrays and iterables</li> 
  </ul> 
  <blockquote style="margin: 0px 0px 0px 40px; border: medium none; padding: 0px;"> 
    <blockquote style="margin: 0px 0px 0px 40px; border: medium none; padding: 0px;"> 
      <pre>def orig = [1, 3, 5, 7]
def mixed = orig.shuffled()
assert mixed.size() == orig.size()
assert mixed.toString() ==~ /\[(\d, ){3}\d\]/</pre> 
    </blockquote> 
  </blockquote> 
  <p> </p> 
  <ul> 
    <li><font face="courier new, courier, monospace">collect{ }</font> on <font face="courier new, courier, monospace">Future</font></li> 
  </ul> 
  <blockquote style="margin: 0px 0px 0px 40px; border: medium none; padding: 0px;"> 
    <blockquote style="margin: 0px 0px 0px 40px; border: medium none; padding: 0px;"> 
      <pre>Future&lt;String&gt; foobar = executor.submit{ "foobar" }
Future&lt;Integer&gt; foobarSize = foobar.collect{ it.size() } // async
assert foobarSize.get() == 6</pre> 
    </blockquote> 
  </blockquote> 
  <p> </p> 
  <ul> 
    <li><font face="courier new, courier, monospace">minus()</font> on <font face="courier new, courier, monospace">LocalDate</font></li> 
  </ul> 
  <blockquote style="margin: 0px 0px 0px 40px; border: medium none; padding: 0px;"> 
    <blockquote style="margin: 0px 0px 0px 40px; border: medium none; padding: 0px;"> 
      <pre>def xmas = LocalDate.of(2019, Month.DECEMBER, 25)
def newYear = LocalDate.of(2020, Month.JANUARY, 1)
assert newYear - xmas == 7 // a week apart
</pre> 
    </blockquote> 
  </blockquote> 
  <h2>Other Improvements</h2> 
  <h3>Improved Annotation Support</h3> 
  <p>Recent version of Java allow annotations in more places (JSR308). Groovy now also supports such use cases. This is important for frameworks like Spock, Micronaut, Grails, Jqwik and others, and also opens up the possibility for additional AST transformations (a key meta-programming feature of Groovy).</p> 
  <h3>Groovydoc Enhancements</h3> 
  <p>In addition to Groovydoc supporting the new parser, you can now embed Groovydoc comments in various ways:</p> 
  <p> </p> 
  <ul> 
    <li>They can be made available within the AST for use by AST transformations and other tools.</li> 
    <li>Groovydoc comments starting with a special <font face="courier new, courier, monospace">/**@</font> opening comment delimiter can also be embedded into the class file. This provides a capability in Groovy inspired by languages like Ruby which can embed documentation into the standard binary jar and is thus always available rather than relying on a separate javadoc jar.</li> 
  </ul> 
  <h2>Getting Groovy</h2> 
  <p>The official source release are on the <a href="https://groovy.apache.org/download.html" title="download page">download page</a>. Convenience binaries, downloadable documentation, an SDK bundle and pointers to various community artifacts can be found on that page along with information to allow you to verify your installation. You can use the zip installation on any platform with Java support, or consider using an installer for your platform or IDE.</p> 
  <p>The Windows installer for the latest versions of Groovy 3 are available from <a href="https://bintray.com/groovy/Distributions/Windows-Installer" title="groovy msi">bintray</a>. (community artifact)</p> 
  <p>For Linux users, the latest versions of Groovy 3 are also available in the <a href="https://snapcraft.io/groovy" title="snap store">Snap Store</a>. (community artifact)</p> 
  <p>For Eclipse users, the latest versions of the Groovy 3 groovy-eclipse-batch plugin are available from&nbsp;<a href="https://bintray.com/groovy/maven/groovy-eclipse-batch/" title="groovy-eclipse-batch">bintray</a>. (community artifact)</p>
  <p>For Intellij users, the latest community editions of <a href="https://www.jetbrains.com/idea/" title="IDEA">IDEA</a> have Groovy 3 support.&nbsp;</p> 
  <p> </p>
