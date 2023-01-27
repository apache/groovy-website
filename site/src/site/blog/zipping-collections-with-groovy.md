---
layout: post
title: Zipping Collections with Groovy
date: '2022-11-17T12:50:08+00:00'
permalink: zipping-collections-with-groovy
---
<p></p><p>In computer science, <a href="https://en.wikipedia.org/wiki/Zipping_(computer_science)" target="_blank"><i>zipping</i></a>&nbsp;translates sequences into sequences where, if visualized in two dimensions, the rows and columns are swapped. So the <i>zip</i> of:</p><pre style="background-color:#2b2b2b;color:#a9b7c6;font-family:'JetBrains Mono',monospace;font-size:9.6pt;">[[<span style="color:#6a8759;">'a'</span>, <span style="color:#6a8759;">'b'</span>, <span style="color:#6a8759;">'c'</span>],
 [<span style="color:#6897bb;"> 1 </span>, <span style="color:#6897bb;"> 2 </span>, <span style="color:#6897bb;"> 3 </span>]]<br></pre>
<p>would be:</p>
<pre style="background-color:#2b2b2b;color:#a9b7c6;font-family:'JetBrains Mono',monospace;font-size:9.6pt;">[[<span style="color:#6a8759;">'a'</span>, <span style="color:#6897bb;">1</span>],
 [<span style="color:#6a8759;">'b'</span>, <span style="color:#6897bb;">2</span>],
 [<span style="color:#6a8759;">'c'</span>, <span style="color:#6897bb;">3</span>]]<br></pre><p>

It's a very handy operation and depending on the language, may be supported for tuples, lists, streams and other sequences or aggregates.</p><p> Java collections and streams don't currently support such functionality out-of-the-box with various workarounds discussed <a href="https://dzone.com/articles/bridge-the-gap-of-zip-operation" target="_blank">here</a>. The summary: language and library design is hard; any <i>zip </i>implementation that Java provides would have some limitations baked in, so they instead provide the necessary primitives to allow folks to build their own implementations based on their specific requirements.</p><p></p><p>We'll look at what Groovy provides and some of the available Java libraries that you can also use. The same caveats apply to these libraries, each will have its own implementation strengths and weaknesses.</p><p>We'll use an example inspired by this <a href="https://twitter.com/TheDonRaab" target="_blank">Donald Raab</a> <a href="https://donraab.medium.com/make-or-append-me-a-string-c654f247373a" target="_blank">blog post</a>. It looks at zipping (and formatting) lists of strings containing "fall"-inspired emoji. Yes, it's late spring for the southern hemisphere who also mostly call fall "autumn", but hopefully everyone will appreciate the inspiration.</p>
<p><br></p>
<p><img style="width:75%" src="https://blogs.apache.org/groovy/mediaresource/d5dce79c-64a1-4f41-96cf-081e19876f5a" alt="fall.png"></p>

<p><br></p>

<h3>Groovy</h3>

<p><a href="https://groovy-lang.org/" target="_blank">Groovy</a> uses the <code>transpose</code> method for zipping:</p>
<p><img style="width:100%" src="https://blogs.apache.org/groovy/mediaresource/67ebb175-e131-43d3-99a6-93ee295d98e7" alt="ZippingCollectionsGroovy.png"></p>

<h3>Eclipse Collections</h3>

<p><a href="https://www.eclipse.org/collections/" target="_blank">Eclipse Collections</a> has a <code>zip</code> method on its list classes:</p>
<p><img style="width:100%" src="https://blogs.apache.org/groovy/mediaresource/372e7ece-5b2e-4257-936a-5586c5264732" alt="ZippingCollectionsEC.png"></p>

<h3>Guava</h3>

<p><a href="https://github.com/google/guava" target="_blank">Guava</a> has a streams utility class with a <code>zip</code> method:</p>
<p><img style="width:100%" src="https://blogs.apache.org/groovy/mediaresource/c09f2360-15b5-4d55-b1ca-e60fb64b1a56" alt="ZippingCollectionsGuava.png"></p>

<h3>StreamEx</h3>

<p><a href="https://github.com/amaembo/streamex" target="_blank">StreamEx</a> provides an enhanced stream library which supports <code>zipWith</code>:</p>
<p><img style="width:100%" src="https://blogs.apache.org/groovy/mediaresource/ceb0bd2e-378d-4d84-bd24-42978a17f4f4" alt="ZippingCollectionsStreamEx.png"></p>

<h3>Vavr</h3>

<p><a href="https://github.com/vavr-io/vavr" target="_blank">Vavr</a> has a <code>zipWith</code> method on its list class:</p>
<p><img style="width:100%" src="https://blogs.apache.org/groovy/mediaresource/55f1456a-cdcf-4ac5-9428-3423380c902e" alt="ZippingCollectionsVavr.png"></p>

<h3>jOOλ</h3>

<p><a href="https://github.com/jOOQ/jOOL" target="_blank">jOOλ</a> has a <code>zip</code> method for its sequences:</p>
<p><img style="width:100%" src="https://blogs.apache.org/groovy/mediaresource/4f4de9e9-d75f-401a-9adc-cb3673e12bb8" alt="ZippingCollectionsJool.png"></p>

<h3>Groovy GQuery</h3>

<p>If you are a fan of query-like DSLs, Groovy's language integrated query, <i>gquery</i>, can also be used:</p>
<p><img style="width:100%" src="https://blogs.apache.org/groovy/mediaresource/2a95e003-cce5-4c1b-8979-06b0747d438e" alt="ZippingCollectionsGQ.png"></p>
<p>This uses a special <code>_rn</code> "row number" pre-defined variable in GQ expressions. It follows the same strategy as the IntStream "<i>workaround</i>" for Java mentioned in this&nbsp;<a href="https://www.baeldung.com/java-collections-zip" target="_blank">blog</a>.</p>

<h3>More information</h3>

<ul><li>The code examples can be found in the <a href="https://github.com/paulk-asert/zipping-collections" target="_blank">repo</a></li></ul>
<p></p>
