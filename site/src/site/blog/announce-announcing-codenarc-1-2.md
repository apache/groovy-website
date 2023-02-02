---
layout: post
title: "[ANNOUNCE] Announcing CodeNarc 1.2"
date: '2018-07-10T10:16:39+00:00'
permalink: announce-announcing-codenarc-1-2
---

<a href="http://groovy-lang.org/index.html"><img src="https://blogs.apache.org/groovy/mediaresource/58a149c0-e332-40dd-b450-59ffe0c96b74?t=true" alt="groovy-logo.png"></img></a>

<p>
The CodeNarc Team is proud to announce the release of version 1.2.
</p><p>
<a href="http://codenarc.org/">CodeNarc</a> is a static analysis tool for Groovy source code. 
 </p><p>
Version 1.2 includes 5 new rules and several enhancements and bug fixes. See the full details in the <a href="https://github.com/CodeNarc/CodeNarc/blob/master/CHANGELOG.md">release notes</a>. 
 </p>
<h3>New Rules</h3>
<b></b>
<li><b>StaticFieldsBeforeInstanceFields</b> rule (convention) - Enforce that all static fields are above all instance fields within a class.
<li><b>StaticMethodsBeforeInstanceMethods</b> rule (convention) - Enforce that all static methods within each visibility level (public, protected, private) are above all instance methods within that same visibility level.
<li><b>PublicMethodsBeforeNonPublicMethods</b> rule (convention) - Enforce that all public methods are above protected and private methods.
<li><b>GrailsDomainStringPropertyMaxSize</b> rule (grails) - String properties in Grails domain classes have to define maximum size otherwise the property is mapped to VARCHAR(255) causing runtime exceptions to occur.
<li><b>NoJavaUtilDate</b> rule (convention) - Do not use java.util.Date. Prefer the classes in the java.time.* packages. Checks for construction of new java.util.Date objects.
</p><p>
Check out the <a href="https://github.com/CodeNarc/CodeNarc">project on GitHub</a>!
 </p><p>
The <a href="http://grails.org/plugin/codenarc">Grails CodeNarc Plugin</a> has been updated to version 1.2 as well.
</p>
