---
layout: post
title: Working with SQL databases with Groovy and GraalVM
date: '2022-07-29T14:07:41+00:00'
permalink: working-with-sql-databases-with
---
<p>During the week, there was an interesting <a href="https://www.youtube.com/watch?v=rpZJz4qbhCU" target="_blank">video</a> and <a href="https://medium.com/graalvm/graalvm-22-2-smaller-jdk-size-improved-memory-usage-better-library-support-and-more-cb34b5b68ec0" target="_blank">blog post</a> on the latest <a href="https://www.graalvm.org/" target="_blank">GraalVM</a> 22.2 Release. The release has numerous new features and improvements including:</p><ul><li>smaller native executables</li><li>the ability to generate heap dumps in native executables</li><li>experimental native image debugging within IntelliJ IDEA</li><li>the ability to embed a Software Bill of Materials (SBOM) into the executable for improved security (when using GraalVM Enterprise)</li><li>native metadata integration.</li></ul><p>This blog looks at the last of these. We'll use the running example of the <a href="https://www.h2database.com/html/main.html" target="_blank">H2 database</a> which the video discusses.<br></p>
<h3>Native Metadata</h3>
<p>For anyone who has used GraalVM, they will know that frequently certain information must be given to the native compiler. Certain classes can be initialized at build time, others should be initialized at runtime. If accessing certain kinds of resources, knowledge of those resources must be given to the compiler. Parts of the application which might be invoked through reflection or involve serialization, might not be deemed reachable and won't automatically be included by the compiler.</p><p>Each library that is being used within an application will have its own set of classes and resources which will commonly need to dealt with by anyone using that library. The Native Metadata repository keeps a shared copy of this information on a per library basis. Once someone has populated the metadata, other projects using the same library can get that information automatically. We'll look more at metadata integration shortly, but first, let's look at our database application.</p>
<h3>Working with SQL in Groovy</h3>
<p>The application creates and then populates a <i>customer</i> database with four customers. It then prints them out:<br></p><pre style="background-color:#2b2b2b;color:#a9b7c6;font-family:'JetBrains Mono',monospace;font-size:9.8pt;"><span style="color:#cc7832;">import </span>groovy.sql.Sql<br><span style="color:#cc7832;">import </span>groovy.transform.<span style="color:#bbb529;">CompileStatic<br></span><span style="color:#bbb529;"><br></span><span style="color:#bbb529;">@CompileStatic<br></span><span style="color:#cc7832;">class </span>H2Demo {<br>    <span style="color:#cc7832;">static void </span>main(args) {<br>        Sql.<span style="color:#9876aa;font-style:italic;">withInstance</span>(<span style="color:#6a8759;">'jdbc:h2:./data/test'</span>) <span style="font-weight:bold;">{ </span>sql <span style="font-weight:bold;">-&gt;<br></span><span style="font-weight:bold;">            </span>sql.execute <span style="color:#6a8759;">'</span><span style="color:#6a8759;background-color:#364135;">DROP TABLE IF EXISTS customers</span><span style="color:#6a8759;">'<br></span><span style="color:#6a8759;">            </span>sql.execute <span style="color:#6a8759;">'</span><span style="color:#6a8759;background-color:#364135;">CREATE TABLE customers(id INTEGER AUTO_INCREMENT, name VARCHAR)</span><span style="color:#6a8759;">'<br></span><span style="color:#6a8759;">            </span><span style="color:#cc7832;">for </span>(cust <span style="color:#cc7832;">in </span>[<span style="color:#6a8759;">'Lord Archimonde'</span>, <span style="color:#6a8759;">'Arthur'</span>, <span style="color:#6a8759;">'Gilbert'</span>, <span style="color:#6a8759;">'Grug'</span>]) {<br>                sql.executeInsert <span style="color:#6a8759;">"INSERT INTO customers(name) VALUES </span>$cust<span style="color:#6a8759;">"<br></span><span style="color:#6a8759;">            </span>}<br>            println sql.rows(<span style="color:#6a8759;">'</span><span style="color:#6a8759;background-color:#364135;">SELECT * FROM customers</span><span style="color:#6a8759;">'</span>).join(<span style="color:#6a8759;">'</span><span style="color:#cc7832;">\n</span><span style="color:#6a8759;">'</span>)<br>        <span style="font-weight:bold;">}<br></span><span style="font-weight:bold;">    </span>}<br>}<br></pre>
<p>Groovy's <code>Sql</code> class makes this relatively easy. The <code>withInstance</code> method will create a database connection and close it down when finished with. The <code>executeInsert</code> method is using a Groovy interpolated String (GString) which creates a prepared statement under the covers.</p>
<h3>Configuring our native build</h3>
<p>Here is our build file:<br></p><pre style="background-color:#2b2b2b;color:#a9b7c6;font-family:'JetBrains Mono',monospace;font-size:9.8pt;">plugins <span style="font-weight:bold;">{<br></span><span style="font-weight:bold;">    </span>id <span style="color:#6a8759;">'application'<br></span><span style="color:#6a8759;">    </span>id <span style="color:#6a8759;">'groovy'<br></span><span style="color:#6a8759;">    </span>id <span style="color:#6a8759;">'org.graalvm.buildtools.native'<br></span><span style="font-weight:bold;">}<br></span><span style="font-weight:bold;"><br></span>application <span style="font-weight:bold;">{<br></span><span style="font-weight:bold;">    </span><span style="color:#9876aa;">mainClass </span>= <span style="color:#6a8759;">'H2Demo'<br></span><span style="font-weight:bold;">}<br></span><span style="font-weight:bold;"><br></span>repositories <span style="font-weight:bold;">{<br></span><span style="font-weight:bold;">    </span>mavenCentral()<br><span style="font-weight:bold;">}<br></span><span style="font-weight:bold;"><br></span>dependencies <span style="font-weight:bold;">{<br></span><span style="font-weight:bold;">   </span>implementation <span style="color:#6a8759;">'com.h2database:h2:2.1.210'<br></span><span style="color:#6a8759;">   </span>implementation <span style="color:#6a8759;">'org.apache.groovy:groovy:4.0.4'<br></span><span style="color:#6a8759;">   </span>implementation <span style="color:#6a8759;">'org.apache.groovy:groovy-sql:4.0.4'<br></span><span style="font-weight:bold;">}<br></span><span style="font-weight:bold;"><br></span>graalvmNative <span style="font-weight:bold;">{<br></span><span style="font-weight:bold;">    </span>agent <span style="font-weight:bold;">{<br></span><span style="font-weight:bold;">        </span>defaultMode = <span style="color:#6a8759;">'standard'<br></span><span style="color:#6a8759;">    </span><span style="font-weight:bold;">}<br></span><span style="font-weight:bold;">    </span><b>metadataRepository {<br>        enabled = <span style="color:#cc7832;">true<br></span><span style="color:#cc7832;">    </span>}<br></b><span style="font-weight:bold;">    </span>binaries <span style="font-weight:bold;">{<br></span><span style="font-weight:bold;">        </span>main <span style="font-weight:bold;">{<br></span><span style="font-weight:bold;">            </span><span style="color:#9876aa;">buildArgs</span>.addAll(<br><span style="color:#808080;">//                    '-H:IncludeSBOM=cyclonedx',<br></span><span style="color:#808080;">                    </span><span style="color:#6a8759;">'--report-unsupported-elements-at-runtime'</span>,<br>                    <span style="color:#6a8759;">'--initialize-at-run-time=groovy.grape.GrapeIvy,org.h2.store.fs.niomem.FileNioMemData'</span>,<br>                    <span style="color:#6a8759;">'--initialize-at-build-time'</span>,<br>                    <span style="color:#6a8759;">'--no-fallback'</span>,<br>            )<br>        <span style="font-weight:bold;">}<br></span><span style="font-weight:bold;">    }<br></span><span style="font-weight:bold;">}<br></span></pre><p>We make use of the graalvm native build plugin. We define our dependencies of Groovy and H2. We can also supply any needed parameters to the native compiler. Importantly, we enable integration with the metadata repository.</p><p>When we run the build, it will automatically create the native app for us:<br></p>
<pre style="background-color:#2b2b2b;color:#a9b7c6;font-family:'JetBrains Mono',monospace;font-size:9.8pt;"><span style="color:#4E9A06"><b>paulk@pop-os</b></span>:<span style="color:#3465A4"><b>/extra/projects/groovy-graalvm-h2</b></span>$ ./gradlew clean nativeRun
...
<b>&gt; Task :nativeCompile</b>
[native-image-plugin] Using executable path: /extra/devtools/graalvm-ce-java17-22.2.0/bin/native-image
<span style="color:#A1A1A1">========================================================================================================================</span>
<span style="color:#3465A4"><b>GraalVM Native Image</b></span>: Generating '<b>H2Demo</b>' (executable)...
<span style="color:#A1A1A1">========================================================================================================================</span>
...
<span style="color:#3465A4">[1/7] </span><span style="color:#3465A4"><b>Initializing</b></span><span style="color:#3465A4"><b>...</b></span>                                                                                    (5.3s @ 0.26GB)
 Version info: 'GraalVM 22.2.0 Java 17 CE'
 Java version info: '17.0.4+8-jvmci-22.2-b06'
 C compiler: gcc (linux, x86_64, 11.2.0)
 Garbage collector: Serial GC
 1 user-specific feature(s)
 - com.oracle.svm.polyglot.groovy.GroovyIndyInterfaceFeature
<span style="color:#3465A4">[2/7] </span><span style="color:#3465A4"><b>Performing analysis</b></span><span style="color:#3465A4"><b>...</b></span>  [************]                                                            (51.7s @ 1.82GB)
  10,597 (90.60%) of 11,697 classes reachable
  17,002 (64.13%) of 26,510 fields reachable
  58,165 (63.45%) of 91,666 methods reachable
     393 classes,   100 fields, and 2,057 methods registered for reflection
      65 classes,    74 fields, and    55 methods registered for JNI access
       4 native libraries: dl, pthread, rt, z
<span style="color:#3465A4">[3/7] </span><span style="color:#3465A4"><b>Building universe</b></span><span style="color:#3465A4"><b>...</b></span>                                                                               (8.0s @ 4.02GB)
<span style="color:#3465A4">[4/7] </span><span style="color:#3465A4"><b>Parsing methods</b></span><span style="color:#3465A4"><b>...</b></span>      [**]                                                                       (4.8s @ 3.85GB)
<span style="color:#3465A4">[5/7] </span><span style="color:#3465A4"><b>Inlining methods</b></span><span style="color:#3465A4"><b>...</b></span>     [***]                                                                      (3.0s @ 1.72GB)
<span style="color:#3465A4">[6/7] </span><span style="color:#3465A4"><b>Compiling methods</b></span><span style="color:#3465A4"><b>...</b></span>    [******]                                                                  (38.0s @ 3.63GB)
<span style="color:#3465A4">[7/7] </span><span style="color:#3465A4"><b>Creating image</b></span><span style="color:#3465A4"><b>...</b></span>                                                                                  (5.9s @ 1.70GB)
  26.65MB (46.64%) for code area:    38,890 compilation units
  28.04MB (49.05%) for image heap:  359,812 objects and 66 resources
   2.46MB ( 4.31%) for other data
  57.15MB in total
<span style="color:#A1A1A1">------------------------------------------------------------------------------------------------------------------------</span>
<span style="color:#C4A000"><b>Top 10 packages in code area:                               Top 10 object types in image heap:</b></span>
   1.48MB sun.security.ssl                                     5.85MB byte[] for code metadata
   1.06MB java.util                                            2.82MB java.lang.String
 979.43KB java.lang.invoke                                     2.78MB java.lang.Class
 758.29KB org.apache.groovy.parser.antlr4                      2.47MB byte[] for general heap data
 723.92KB com.sun.crypto.provider                              2.04MB byte[] for java.lang.String
 588.57KB org.h2.table                                       910.68KB com.oracle.svm.core.hub.DynamicHubCompanion
 582.06KB org.h2.command                                     764.95KB java.util.HashMap$Node
 494.23KB org.codehaus.groovy.classgen                       761.53KB java.lang.Object[]
 476.03KB c.s.org.apache.xerces.internal.impl.xs.traversers  715.65KB byte[] for embedded resources
 468.69KB java.lang                                          584.75KB java.util.HashMap$Node[]
  18.87MB for 370 more packages                                8.28MB for 2535 more object types
<span style="color:#A1A1A1">------------------------------------------------------------------------------------------------------------------------</span>
                        3.9s (3.2% of total time) in 30 GCs | Peak RSS: 6.22GB | CPU load: 6.48
<span style="color:#A1A1A1">------------------------------------------------------------------------------------------------------------------------</span>
<span style="color:#C4A000"><b>Produced artifacts:</b></span>
 /extra/projects/groovy-graalvm-h2/build/native/nativeCompile/H2Demo<span style="color:#A1A1A1"> (executable)</span>
 /extra/projects/groovy-graalvm-h2/build/native/nativeCompile/H2Demo.build_artifacts.txt<span style="color:#A1A1A1"> (txt)</span>
<span style="color:#A1A1A1">========================================================================================================================</span>
Finished generating '<b>H2Demo</b>' in 2m 1s.
    [native-image-plugin] Native Image written to: /extra/projects/groovy-graalvm-h2/build/native/nativeCompile

<b>&gt; Task :nativeRun</b>
[ID:1, NAME:Lord Archimonde]
[ID:2, NAME:Arthur]
[ID:3, NAME:Gilbert]
[ID:4, NAME:Grug]
</pre>
<h3>Checking the native image speed</h3>
<p>We can also check the speed once the native image is built:<br></p>
<pre style="background-color:#2b2b2b;color:#a9b7c6;font-family:'JetBrains Mono',monospace;font-size:9.8pt;"><span style="color:#4E9A06"><b>paulk@pop-os</b></span>:<span style="color:#3465A4"><b>/extra/projects/groovy-graalvm-h2</b></span>$ time build/native/nativeCompile/H2Demo
[ID:1, NAME:Lord Archimonde]
[ID:2, NAME:Arthur]
[ID:3, NAME:Gilbert]
[ID:4, NAME:Grug]

real	0m0.027s
user	0m0.010s
sys	0m0.011s</pre>
<h3>More information</h3><p>Check out the full source code from the repo:&nbsp;<a href="https://github.com/paulk-asert/groovy-graalvm-h2" target="_blank">https://github.com/paulk-asert/groovy-graalvm-h2</a>.</p><h3>Conclusion</h3>
<p>We have looked at a simple H2 database application and the steps involved in creating a native application with Groovy and GraalVM.</p>
