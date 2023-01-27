---
layout: post
title: Groovy Dates And Times Cheat Sheet
date: '2022-10-24T07:27:25+00:00'
permalink: groovy-dates-and-times-cheat
---
<p>Java has had a <code>Date</code> class from the very beginning and Groovy supports using it and several related classes like <code>Calendar</code>. Throughout this blog post we refer to those classes as the <i style="background-color: rgb(255, 255, 255);"><b>legacy date classes</b></i>.
Groovy enhances the experience of using the legacy date classes with simpler mechanisms for formatting, parsing and extracting fields from the related classes.</p>

<p>Since Java 8, the JDK has included the <a href="https://jcp.org/en/jsr/detail?id=310" target="_blank">JSR-310</a> Date Time API. We refer to these classes as the <i style="background-color: rgb(255, 255, 255);"><b>new date classes</b></i>. The new date classes remove many limitations of the legacy date classes and bring some greatly appreciated additional consistency. Groovy provides similar enhancements for the new date classes too.</p><p>Groovy's enhancements for the legacy date classes are in the <code>groovy-dateutil</code> module (prior to Groovy 2.5, this functionality was built in to the core module). The <code>groovy-datetime</code> module has the enhancements for the new date classes. You can include a dependency to this module in your build file or reference the <code>groovy-all</code> pom dependency. Both modules are part of a standard Groovy install.</p><p>The next few sections illustrate common date and time tasks and the code to perform them using the new and legacy classes with Groovy enhancements in numerous places.</p><p><b>Please note</b>: that some of the formatting commands are <i>Locale dependent</i> and the output <i>may vary</i> slightly if you run these examples yourself.<br></p>

<h2>Representing the current date/time</h2>

<p>The legacy date classes have an abstraction which includes date and time. If you are only interested in one of those two aspects, you simply ignore the other aspect. The new date classes allow you to have date-only, time-only and date-time representations.</p><table cellspacing="5px"><tbody><tr></tr></tbody></table>The examples create instances representing the current date and/or time. Various information is extracted from the instances and they are printed in various ways. Some of the examples use the <code>SV</code> macro which prints the name and string value of one or more variables.<p></p><table cellspacing="5px"><tbody><tr><th style="text-align:center; padding:10px">task</th><th style="text-align:center; padding:10px">java.time</th><th style="text-align:center; padding:10px">legacy</th></tr>
<tr style="background-color:#ddeedd"><td style="padding:10px"><p><span style="background-color: transparent;">current date and time</span></p></td>
<td style="padding:10px"><pre style="background-color:#2b2b2b;color:#a9b7c6;font-family:'JetBrains Mono',monospace;font-size:9.6pt;">println LocalDateTime.<span style="color:#9876aa;font-style:italic;">now</span>()      <span style="color:#808080;"><br></span>println Instant.<span style="color:#9876aa;font-style:italic;">now</span>()            <span style="color:#808080;"><br></span></pre>
<pre>2022-10-24T12:40:02.218130200
2022-10-24T02:40:02.223131Z
</pre></td>
<td style="padding:10px">
<pre style="background-color:#2b2b2b;color:#a9b7c6;font-family:'JetBrains Mono',monospace;font-size:9.6pt;">println <span style="color:#cc7832;">new </span>Date()               <span style="color:#808080;"><br></span>println Calendar.<span style="color:#9876aa;font-style:italic;">instance</span>.<span style="color:#9876aa;">time   </span><span style="color:#808080;"></span></pre>
<pre>Mon Oct 24 12:40:02 AEST 2022
Mon Oct 24 12:40:02 AEST 2022<br></pre></td></tr>
<tr style="background-color:#ddddee"><td style="padding:10px">day of current year &amp;<br>day of current month</td>
<td style="padding:10px"><pre style="background-color:#2b2b2b;color:#a9b7c6;font-family:'JetBrains Mono',monospace;font-size:9.6pt;">println LocalDateTime.<span style="color:#9876aa;font-style:italic;">now</span>().<span style="color:#9876aa;">dayOfYear<br></span>println LocalDateTime.<span style="color:#9876aa;font-style:italic;">now</span>().<span style="color:#9876aa;">dayOfMonth<br></span></pre>
<pre>297
24</pre>
</td>
<td style="padding:10px"><pre style="background-color:#2b2b2b;color:#a9b7c6;font-family:'JetBrains Mono',monospace;font-size:9.6pt;">println Calendar.<span style="color:#9876aa;font-style:italic;">instance</span>[<span style="color:#9876aa;font-style:italic;">DAY_OF_YEAR</span>]<br>println Calendar.<span style="color:#9876aa;font-style:italic;">instance</span>[<span style="color:#9876aa;font-style:italic;">DAY_OF_MONTH</span>]<br></pre><pre>297
24</pre></td>
</tr>
<tr style="background-color:#ddeedd"><td style="padding:10px">extract today's<br>year, month &amp; day</td>
<td style="padding:10px"><pre style="background-color:#2b2b2b;color:#a9b7c6;font-family:'JetBrains Mono',monospace;font-size:9.6pt;"><span style="color:#cc7832;">var </span>now = LocalDate.<span style="color:#9876aa;font-style:italic;">now</span>()     <span style="font-size: 9.6pt; color: rgb(128, 128, 128);">// or LocalDateTime</span><br><br>println SV(now.<span style="color:#9876aa;">year</span>, now.<span style="color:#9876aa;">monthValue</span>, now.<span style="color:#9876aa;">dayOfMonth</span>)<br><span style="color:#808080;"><br></span>(Y, M, D) = now[<span style="color:#9876aa;font-style:italic;">YEAR</span>, <span style="color:#9876aa;font-style:italic;">MONTH_OF_YEAR</span>, <span style="color:#9876aa;font-style:italic;">DAY_OF_MONTH</span>]<br>println <span style="color:#6a8759;">"Today is </span>$Y $M $D<span style="color:#6a8759;">"<br></span></pre><pre>now.year=2022, now.monthValue=10, now.dayOfMonth=24
Today is 2022 10 24</pre></td>
<td style="padding:10px"><pre style="background-color:#2b2b2b;color:#a9b7c6;font-family:'JetBrains Mono',monospace;font-size:9.6pt;"><span style="color:#cc7832;">var </span>now = Calendar.<span style="color:#9876aa;font-style:italic;">instance<br></span>(_E, Y, M, _WY, _WM, D) = now<br>println <span style="color:#6a8759;">"Today is </span>$Y $<span style="font-weight:bold;">{</span>M+<span style="color:#6897bb;">1</span><span style="font-weight:bold;">} </span>$D<span style="color:#6a8759;">"<br></span><span style="color:#6a8759;"><br></span>(Y, M, D) = now[<span style="color:#9876aa;font-style:italic;">YEAR</span>, <span style="color:#9876aa;font-style:italic;">MONTH</span>, <span style="color:#9876aa;font-style:italic;">DAY_OF_MONTH</span>]<br>println <span style="color:#6a8759;">"Today is </span>$Y $<span style="font-weight:bold;">{</span>M+<span style="color:#6897bb;">1</span><span style="font-weight:bold;">} </span>$D<span style="color:#6a8759;">"<br></span></pre><pre>Today is 2022 10 24
Today is 2022 10 24</pre></td>
</tr>
<tr style="background-color:#ddddee"><td style="padding:10px">alternatives to print today</td>
<td style="padding:10px"><pre style="background-color:#2b2b2b;color:#a9b7c6;font-family:'JetBrains Mono',monospace;font-size:9.6pt;">println now.format(<span style="color:#6a8759;">"'Today is 'YYYY-MM-dd"</span>)<br>printf <span style="color:#6a8759;">'Today is %1$tY-%1$tm-%1$te%n'</span>, now</pre><pre>Today is 2022-10-24
Today is 2022-10-24</pre></td>
<td style="padding:10px"><pre style="background-color:#2b2b2b;color:#a9b7c6;font-family:'JetBrains Mono',monospace;font-size:9.6pt;">println now.format(<span style="color:#6a8759;">"'Today is 'YYYY-MM-dd"</span>)<br>printf <span style="color:#6a8759;">'Today is %1$tY-%1$tm-%1$te%n'</span>, now</pre><pre>Today is 2022-10-24
Today is 2022-10-24</pre></td>
</tr>
<tr style="background-color:#ddeedd"><td style="padding:10px"><p>extract parts of current time</p></td>
<td style="padding:10px"><pre style="background-color:#2b2b2b;color:#a9b7c6;font-family:'JetBrains Mono',monospace;font-size:9.6pt;">now = LocalTime.<span style="color:#9876aa;font-style:italic;">now</span>() <span style="color:#808080;">// or LocalDateTime<br></span>println SV(now.<span style="color:#9876aa;">hour</span>, now.<span style="color:#9876aa;">minute</span>, now.<span style="color:#9876aa;">second</span>)<br>(H, M, S) = now[<span style="color:#9876aa;font-style:italic;">HOUR_OF_DAY</span>, <span style="color:#9876aa;font-style:italic;">MINUTE_OF_HOUR</span>,<br>                <span style="color:#9876aa;font-style:italic;">SECOND_OF_MINUTE</span>]<br>printf <span style="color:#6a8759;">'The time is %02d:%02d:%02d</span><span style="color:#cc7832;">\n</span><span style="color:#6a8759;">'</span>, H, M, S<br></pre><pre>now.hour=12, now.minute=40, now.second=2
The time is 12:40:02</pre></td>
<td style="padding:10px"><pre style="background-color:#2b2b2b;color:#a9b7c6;font-family:'JetBrains Mono',monospace;font-size:9.6pt;">(H, M, S) = now[<span style="color:#9876aa;font-style:italic;">HOUR_OF_DAY</span>, <span style="color:#9876aa;font-style:italic;">MINUTE</span>, <span style="color:#9876aa;font-style:italic;">SECOND</span>]
<br>println SV(H, M, S)
<br>printf <span style="color:#6a8759;">'The time is %02d:%02d:%02d%n</span><span style="color:#6a8759;">'</span>, H, M, S<br></pre><pre>H=12, M=40, S=2
The time is 12:40:02</pre></td>
</tr>
<tr style="background-color:#ddddee"><td style="padding:10px">alternatives to print time</td>
<td style="padding:10px"><pre style="background-color:#2b2b2b;color:#a9b7c6;font-family:'JetBrains Mono',monospace;font-size:9.6pt;">println now.format(<span style="color:#6a8759;">"'The time is 'HH:mm:ss"</span>)<br>printf <span style="color:#6a8759;">'The time is %1$tH:%1$tM:%1$tS%n</span><span style="color:#6a8759;">'</span>, now<br></pre><pre>The time is 12:40:02
The time is 12:40:02<br></pre></td>
<td style="padding:10px"><pre style="background-color:#2b2b2b;color:#a9b7c6;font-family:'JetBrains Mono',monospace;font-size:9.6pt;">println now.format(<span style="color:#6a8759;">"'The time is 'HH:mm:ss"</span>)<br>printf <span style="color:#6a8759;">'The time is %1$tH:%1$tM:%1$tS%n</span><span style="color:#6a8759;">'</span>, now<br></pre><pre>The time is 12:40:02
The time is 12:40:02<br></pre></td>
</tr>
</tbody></table>

<h2>Processing times</h2>

<p>The new date classes have a <code>LocalTime</code> class specifically for representing time-only quantities. The legacy date classes don't have such a purpose-built abstraction; you essentially just ignore the day, month, and year parts of a date. The <code>java.sql.Time</code> class could be used as an alterative but rarely is.&nbsp;The Java <a href="https://docs.oracle.com/javase/tutorial/datetime/iso/legacy.html" target="_blank" style="background-color: rgb(255, 255, 255);">documentation comparing the new date classes to their legacy equivalents</a>, talks about
using <code>GregorianCalendar</code> with the date set to the epoch value of <code>1970-01-01</code>
as an approximation of the <code>LocalTime</code> class. We'll follow that approach here to provide a comparison but we <i>strongly recommend</i> upgrading to the
new classes if you need to represent time-only values or use the <a href="https://www.joda.org/joda-time/" target="_blank" style="background-color: rgb(255, 255, 255);">Joda-Time</a> library on JDK versions prior to 8.</p><p>
</p>

The examples look at representing a minute before and after midnight, and some times at which you might eat your meals. For the meals, as well as printing various values, we might be interested in calculating new times in terms of existing times, e.g. lunch and dinner are 7 hours apart.<table cellspacing="5px"><tbody>
<tr><th style="text-align:center; padding:10px">task</th><th style="text-align:center; padding:10px">java.time</th><th style="text-align:center; padding:10px">legacy</th></tr>
<tr style="background-color:#ddeedd"><td style="padding:10px">one min after midnight</td>
<td style="padding:10px"><pre style="background-color:#2b2b2b;color:#a9b7c6;font-family:'JetBrains Mono',monospace;font-size:9.6pt;">LocalTime.<span style="color:#9876aa;font-style:italic;">of</span>(<span style="color:#6897bb;">0</span>, <span style="color:#6897bb;">1</span>).with <span style="font-weight:bold;">{<br></span><span style="font-weight:bold;">    </span>println format(<span style="color:#6a8759;">'HH:mm'</span>)<br>    println format(<span style="color:#6a8759;">'hh:mm a'</span>)<br>    println format(<span style="color:#6a8759;">'K:mm a'</span>)<br><span style="font-weight:bold;">}</span></pre><pre>00:01
12:01 am
0:01 am</pre></td>
<td style="padding:10px"><pre style="background-color:#2b2b2b;color:#a9b7c6;font-family:'JetBrains Mono',monospace;font-size:9.6pt;">Calendar.<span style="color:#9876aa;font-style:italic;">instance</span>.with <span style="font-weight:bold;">{<br></span><span style="font-weight:bold;">    </span>clear()<br>    set(<span style="color:#9876aa;font-style:italic;">MINUTE</span>, <span style="color:#6897bb;">1</span>)<br>    println format(<span style="color:#6a8759;">'HH:mm'</span>)<br>    println format(<span style="color:#6a8759;">'hh:mm a'</span>)<br>    println format(<span style="color:#6a8759;">'K:mm a'</span>)<br><span style="font-weight:bold;">}</span></pre><pre>00:01
12:01 am
0:01 am</pre></td>
</tr>
<tr style="background-color:#ddddee"><td style="padding:10px">one min before midnight</td>
<td style="padding:10px"><pre style="background-color:#2b2b2b;color:#a9b7c6;font-family:'JetBrains Mono',monospace;font-size:9.6pt;">LocalTime.<span style="color:#9876aa;font-style:italic;">of</span>(<span style="color:#6897bb;">23</span>, <span style="color:#6897bb;">59</span>).with <span style="font-weight:bold;">{<br></span><span style="font-weight:bold;">    </span>println format(<span style="color:#6a8759;">'HH:mm'</span>)<br>    println format(<span style="color:#6a8759;">'hh:mm a'</span>)<br>    println format(<span style="color:#6a8759;">'K:mm a'</span>)<br><span style="font-weight:bold;">}</span></pre><pre>23:59
11:59 pm
11:59 pm</pre></td>
<td style="padding:10px"><pre style="background-color:#2b2b2b;color:#a9b7c6;font-family:'JetBrains Mono',monospace;font-size:9.6pt;">Calendar.<span style="color:#9876aa;font-style:italic;">instance</span>.with <span style="font-weight:bold;">{<br></span><span style="font-weight:bold;">    </span>clear()<br>    set(<span style="color:#6a8759;">hourOfDay</span>: <span style="color:#6897bb;">23</span>, <span style="color:#6a8759;">minute</span>: <span style="color:#6897bb;">59</span>)<br>    println format(<span style="color:#6a8759;">'HH:mm'</span>)<br>    println format(<span style="color:#6a8759;">'hh:mm a'</span>)<br>    println format(<span style="color:#6a8759;">'K:mm a'</span>)<br><span style="font-weight:bold;">}</span></pre><pre>23:59
11:59 pm
11:59 pm</pre></td>
</tr>
<tr style="background-color:#ddeedd"><td style="padding:10px">meal times</td>
<td style="padding:10px"><pre style="background-color:#2b2b2b;color:#a9b7c6;font-family:'JetBrains Mono',monospace;font-size:9.6pt;"><span style="color:#cc7832;">var </span>breakfast = LocalTime.<span style="color:#9876aa;font-style:italic;">of</span>(<span style="color:#6897bb;">7</span>, <span style="color:#6897bb;">30</span>)<br><span style="color:#cc7832;">var </span>lunch = LocalTime.<span style="color:#9876aa;font-style:italic;">parse</span>(<span style="color:#6a8759;">'12:30'</span>)<br><span style="color:#cc7832;">assert </span>lunch == LocalTime.<span style="color:#9876aa;font-style:italic;">parse</span>(<span style="color:#6a8759;">'12:30.00 pm'</span>, <span style="color:#6a8759;">'hh:mm.ss a'</span>)<br>lunch.with <span style="font-weight:bold;">{ </span><span style="color:#cc7832;">assert </span><span style="color:#9876aa;">hour </span>== <span style="color:#6897bb;">12 </span>&amp;&amp; <span style="color:#9876aa;">minute </span>== <span style="color:#6897bb;">30 </span><span style="font-weight:bold;">}<br></span><span style="color:#cc7832;">var </span>dinner = lunch.plusHours(<span style="color:#6897bb;">7</span>)<br><span style="color:#cc7832;">assert </span>dinner == lunch.plus(<span style="color:#6897bb;">7</span>, ChronoUnit.<span style="color:#9876aa;font-style:italic;">HOURS</span>)<br><span style="color:#cc7832;">assert </span>Duration.<span style="color:#9876aa;font-style:italic;">between</span>(lunch, dinner).toHours() == <span style="color:#6897bb;">7<br></span><span style="color:#cc7832;">assert </span>breakfast.isBefore(lunch)   <span style="color:#808080;">// Java API<br></span><span style="color:#cc7832;">assert </span>lunch &lt; dinner              <span style="color:#808080;">// Groovy shorthand<br></span><span style="color:#cc7832;">assert </span>lunch <span style="color:#cc7832;">in </span>breakfast..dinner<br><span style="color:#cc7832;">assert </span>dinner.format(<span style="color:#6a8759;">'hh:mm a'</span>) == <span style="color:#6a8759;">'07:30 pm'<br></span><span style="color:#cc7832;">assert </span>dinner.format(<span style="color:#6a8759;">'k:mm'</span>) == <span style="color:#6a8759;">'19:30'<br></span><span style="color:#cc7832;">assert </span>dinner.format(FormatStyle.<span style="color:#9876aa;font-style:italic;">MEDIUM</span>) == <span style="color:#6a8759;">'7:30:00 pm'<br></span><span style="color:#cc7832;">assert </span>dinner.<span style="color:#9876aa;">timeString </span>== <span style="color:#6a8759;">'19:30:00'</span></pre></td>
<td style="padding:10px"><pre style="background-color:#2b2b2b;color:#a9b7c6;font-family:'JetBrains Mono',monospace;font-size:9.6pt;"><span style="color:#cc7832;">var </span>breakfast = Date.<span style="color:#9876aa;font-style:italic;">parse</span>(<span style="color:#6a8759;">'hh:mm'</span>, <span style="color:#6a8759;">'07:30'</span>)<br><span style="color:#cc7832;">var </span>lunch = Calendar.<span style="color:#9876aa;font-style:italic;">instance</span>.tap <span style="font-weight:bold;">{<br></span><span style="font-weight:bold;">    </span>clear()<br>    set(<span style="color:#6a8759;">hourOfDay</span>: <span style="color:#6897bb;">12</span>, <span style="color:#6a8759;">minute</span>: <span style="color:#6897bb;">30</span>)<br><span style="font-weight:bold;">}<br></span><span style="color:#cc7832;">assert </span>lunch[<span style="color:#9876aa;font-style:italic;">HOUR_OF_DAY</span>, <span style="color:#9876aa;font-style:italic;">MINUTE</span>] == [<span style="color:#6897bb;">12</span>, <span style="color:#6897bb;">30</span>]<br><span style="color:#cc7832;">var </span>dinner = lunch.clone().tap <span style="font-weight:bold;">{ </span>it[<span style="color:#9876aa;font-style:italic;">HOUR_OF_DAY</span>] += <span style="color:#6897bb;">7 </span><span style="font-weight:bold;">}<br></span><span style="color:#cc7832;">assert </span>dinner == lunch.copyWith(<span style="color:#6a8759;">hourOfDay</span>: <span style="color:#6897bb;">19</span>)<br><span style="color:#cc7832;">assert </span>dinner.format(<span style="color:#6a8759;">'hh:mm a'</span>) == <span style="color:#6a8759;">'07:30 pm'<br></span><span style="color:#cc7832;">assert </span>dinner.format(<span style="color:#6a8759;">'k:mm'</span>) == <span style="color:#6a8759;">'19:30'<br></span><span style="color:#cc7832;">assert </span>dinner.time.timeString == <span style="color:#6a8759;">'7:30:00 pm'<br></span><span style="color:#cc7832;">assert </span>breakfast.before(lunch.<span style="color:#9876aa;">time</span>)  <span style="color:#808080;">// Java API<br></span><span style="color:#cc7832;">assert </span>lunch &lt; dinner                <span style="color:#808080;">// Groovy shorthand</span></pre></td>
</tr>
</tbody></table>

<h2>Processing dates</h2>

<p>To represent date-only information with the legacy date classes, you set the time aspects to zero, or simply ignore them. Alternatively, you could consider the less commonly used&nbsp;<code>java.sql.Date</code>&nbsp;class. The new date classes have the special <code>LocalDate</code> class for this purpose which we highly recommend.</p><p>The examples create dates for Halloween and Melbourne Cup day (a public holiday in the Australia state of Victoria). We look at various properties of those two dates.</p>

<table cellspacing="5px"><tbody>
<tr><th style="text-align:center; padding:10px">task</th><th style="text-align:center; padding:10px">java.time</th><th style="text-align:center; padding:10px">legacy</th></tr>
<tr style="background-color:#ddeedd"><td style="padding:10px">holidays</td>
<td style="padding:10px"><pre style="background-color:#2b2b2b;color:#a9b7c6;font-family:'JetBrains Mono',monospace;font-size:9.6pt;"><span style="color:#cc7832;">var </span>halloween22 = LocalDate.<span style="color:#9876aa;font-style:italic;">of</span>(<span style="color:#6897bb;">2022</span>, <span style="color:#6897bb;">10</span>, <span style="color:#6897bb;">31</span>)<br><span style="color:#cc7832;">var </span>halloween23 = LocalDate.<span style="color:#9876aa;font-style:italic;">parse</span>(<span style="color:#6a8759;">'2023-Oct-31'</span>, <span style="color:#6a8759;">'yyyy-LLL-dd'</span>)<br><span style="color:#cc7832;">assert </span>halloween22 == halloween23 - <span style="color:#6897bb;">365<br></span><span style="color:#cc7832;">assert </span>halloween23 == halloween22.plusYears(<span style="color:#6897bb;">1</span>)<br><span style="color:#cc7832;">var </span>melbourneCup22 = LocalDate.<span style="color:#9876aa;font-style:italic;">of</span>(<span style="color:#6897bb;">2022</span>, <span style="color:#6897bb;">11</span>, <span style="color:#6897bb;">1</span>)<br><span style="color:#cc7832;">assert </span>halloween22 &lt; melbourneCup22<br><span style="color:#cc7832;">assert </span>melbourneCup22 - halloween22 == <span style="color:#6897bb;">1<br></span><span style="color:#cc7832;">assert </span>Period.<span style="color:#9876aa;font-style:italic;">between</span>(halloween22, melbourneCup22).<span style="color:#9876aa;">days </span>== <span style="color:#6897bb;">1<br></span><span style="color:#cc7832;">assert </span>ChronoUnit.<span style="color:#9876aa;font-style:italic;">DAYS</span>.between(halloween22, melbourneCup22) == <span style="color:#6897bb;">1L<br></span><span style="color:#cc7832;">var </span>days = []<br>halloween22.upto(melbourneCup22) <span style="font-weight:bold;">{</span>days &lt;&lt; <span style="color:#6a8759;">"</span>$it.dayOfWeek<span style="color:#6a8759;">" </span><span style="font-weight:bold;">}<br></span><span style="color:#cc7832;">assert </span>days == [<span style="color:#6a8759;">'MONDAY'</span>, <span style="color:#6a8759;">'TUESDAY'</span>]<br><span style="color:#cc7832;">var </span>hols = halloween22..melbourneCup22<br><span style="color:#cc7832;">assert </span>hols.size() == <span style="color:#6897bb;">2</span></pre></td>
<td style="padding:10px"><pre style="background-color:#2b2b2b;color:#a9b7c6;font-family:'JetBrains Mono',monospace;font-size:9.6pt;"><span style="color:#cc7832;">var </span>halloween21 = Date.<span style="color:#9876aa;font-style:italic;">parse</span>(<span style="color:#6a8759;">'dd/MM/yyyy'</span>, <span style="color:#6a8759;">'31/10/2021'</span>)<br><span style="color:#cc7832;">var </span>halloween22 = Date.<span style="color:#9876aa;font-style:italic;">parse</span>(<span style="color:#6a8759;">'yyyy-MMM-dd'</span>, <span style="color:#6a8759;">'2022-Oct-31'</span>)<br><span style="color:#cc7832;">assert </span>halloween21 + <span style="color:#6897bb;">365 </span>== halloween22<br><span style="color:#cc7832;">var </span>melbourneCup22 = <span style="color:#cc7832;">new </span>GregorianCalendar(<span style="color:#6897bb;">2022</span>, <span style="color:#6897bb;">10</span>, <span style="color:#6897bb;">1</span>).<span style="color:#9876aa;">time<br></span><span style="color:#cc7832;">assert </span>melbourneCup22.<span style="color:#9876aa;">dateString </span>== <span style="color:#6a8759;">'1/11/22' </span><span style="color:#808080;">// AU Locale<br></span><span style="color:#cc7832;">assert </span>halloween22 &lt; melbourneCup22<br><span style="color:#cc7832;">assert </span>melbourneCup22 - halloween22 == <span style="color:#6897bb;">1<br></span><span style="color:#cc7832;">assert </span>melbourneCup22 == halloween22.copyWith(<span style="color:#6a8759;">month</span>: <span style="color:#6897bb;">10</span>, <span style="color:#6a8759;">date</span>: <span style="color:#6897bb;">1</span>)<br><span style="color:#cc7832;">var </span>days = []<br>halloween22.upto(melbourneCup22) <span style="font-weight:bold;">{ </span>days &lt;&lt; it.format(<span style="color:#6a8759;">'EEEEE'</span>) <span style="font-weight:bold;">}<br></span><span style="color:#cc7832;">assert </span>days == [<span style="color:#6a8759;">'Monday'</span>, <span style="color:#6a8759;">'Tuesday'</span>]<br><span style="color:#cc7832;">var </span>hols = halloween22..melbourneCup22<br><span style="color:#cc7832;">assert </span>hols.size() == <span style="color:#6897bb;">2</span></pre></td>
</tr>
</tbody></table>

<h2>Processing date and time combinations</h2>

<p>
The new date classes use <code>LocalDateTime</code> to represent a quantity with both date and time aspects. Many of the methods seen earlier will also be applicable here.</p>
<p>The examples show creating and printing a representation of lunch on Melbourne Cup day.</p><p>
</p>

<table cellspacing="5px"><tbody>
<tr><th style="text-align:center; padding:10px">task</th><th style="text-align:center; padding:10px">java.time</th><th style="text-align:center; padding:10px">legacy</th></tr>
<tr style="background-color:#ddeedd"><td style="padding:10px">holidays</td>
<td style="padding:10px"><pre style="background-color:#2b2b2b;color:#a9b7c6;font-family:'JetBrains Mono',monospace;font-size:9.6pt;"><span style="color:#cc7832;">var </span>melbourneCupLunch = LocalDateTime.<span style="color:#9876aa;font-style:italic;">of</span>(<span style="color:#6897bb;">2022</span>, <span style="color:#6897bb;">11</span>, <span style="color:#6897bb;">1</span>, <span style="color:#6897bb;">12</span>, <span style="color:#6897bb;">30</span>)<br><span style="color:#cc7832;">assert </span>melbourneCupLunch.<span style="color:#9876aa;">timeString </span>== <span style="color:#6a8759;">'12:30:00'<br></span><span style="color:#cc7832;">assert </span>melbourneCupLunch.<span style="color:#9876aa;">dateString </span>== <span style="color:#6a8759;">'2022-11-01'<br></span><span style="color:#cc7832;">assert </span>melbourneCupLunch.<span style="color:#9876aa;">dateTimeString </span>== <span style="color:#6a8759;">'2022-11-01T12:30:00'<br></span><span style="color:#cc7832;">assert </span>melbourneCupLunch.toLocalDate() == melbourneCup22<br><span style="color:#cc7832;">assert </span>melbourneCupLunch.toLocalTime() == lunch<br><span style="color:#cc7832;">assert </span>melbourneCupLunch == melbourneCup22 &lt;&lt; lunch</pre></td>
<td style="padding:10px"><pre style="background-color:#2b2b2b;color:#a9b7c6;font-family:'JetBrains Mono',monospace;font-size:9.6pt;"><span style="color:#cc7832;">var </span>melbourneCupLunch = <span style="color:#cc7832;">new </span>GregorianCalendar(<span style="color:#6897bb;">2022</span>, <span style="color:#6897bb;">10</span>, <span style="color:#6897bb;">1</span>, <span style="color:#6897bb;">12</span>, <span style="color:#6897bb;">30</span>).<span style="color:#9876aa;">time<br></span><span style="color:#cc7832;">assert </span>melbourneCupLunch.<span style="color:#9876aa;">timeString </span>== <span style="color:#6a8759;">'12:30:00 pm'              </span><span style="color:#808080;">// Locale specific<br></span><span style="color:#cc7832;">assert </span>melbourneCupLunch.<span style="color:#9876aa;">dateString </span>== <span style="color:#6a8759;">'1/11/22'                  </span><span style="color:#808080;">// Locale specific<br></span><span style="color:#cc7832;">assert </span>melbourneCupLunch.<span style="color:#9876aa;">dateTimeString </span>== <span style="color:#6a8759;">'1/11/22, 12:30:00 pm' </span><span style="color:#808080;">// Locale specific<br></span><span style="color:#cc7832;">assert </span>melbourneCupLunch.clearTime() == melbourneCup22</pre></td>
</tr>
</tbody></table>

<h2>Processing zoned date and times</h2>

<p>
The legacy date classes have the concept of a <code>TimeZone</code>, predominantly used by the Calendar class. The new date classes has a similar concept but uses the <code>ZoneId</code>, <code>ZoneOffset</code>, and <code>ZonedDateTime</code> classes (among others).</p><p>The examples show various properties of zones and show that during the Melbourne cup breakfast, it would still be the night before (Halloween) in Los Angeles. They also show that those two zones are 18 hours apart at that time of the year.</p>

<table cellspacing="5px"><tbody>
<tr><th style="text-align:center; padding:10px">task</th><th style="text-align:center; padding:10px">java.time</th><th style="text-align:center; padding:10px">legacy</th></tr>
<tr style="background-color:#ddeedd"><td style="padding:10px">holidays</td>
<td style="padding:10px"><pre style="background-color:#2b2b2b;color:#a9b7c6;font-family:'JetBrains Mono',monospace;font-size:9.6pt;"><span style="color:#cc7832;">var </span>aet = ZoneId.<span style="color:#9876aa;font-style:italic;">of</span>(<span style="color:#6a8759;">'Australia/Sydney'</span>)<br><span style="color:#cc7832;">assert </span>aet.<span style="color:#9876aa;">fullName </span>== <span style="color:#6a8759;">'Australian Eastern Time' </span>&amp;&amp; aet.<span style="color:#9876aa;">shortName </span>== <span style="color:#6a8759;">'AET'<br></span><span style="color:#cc7832;">assert </span>aet.<span style="color:#9876aa;">offset </span>== ZoneOffset.<span style="color:#9876aa;font-style:italic;">of</span>(<span style="color:#6a8759;">'+11:00'</span>)<br><span style="color:#cc7832;">var </span>melbCupBreakfastInAU = ZonedDateTime.<span style="color:#9876aa;font-style:italic;">of</span>(melbourneCup22, breakfast, aet)<br><span style="color:#cc7832;">var </span>melbCupBreakfast = LocalDateTime.<span style="color:#9876aa;font-style:italic;">of</span>(melbourneCup22, breakfast)<br><span style="color:#cc7832;">assert </span>melbCupBreakfastInAU == melbCupBreakfast &lt;&lt; aet<br><span style="color:#cc7832;">var </span>pst = ZoneId.<span style="color:#9876aa;font-style:italic;">of</span>(<span style="color:#6a8759;">'America/Los_Angeles'</span>)<br><span style="color:#cc7832;">assert </span>pst.<span style="color:#9876aa;">fullName </span>== <span style="color:#6a8759;">'Pacific Time' </span>&amp;&amp; pst.<span style="color:#9876aa;">shortName </span>== <span style="color:#6a8759;">'GMT-08:00'<br></span><span style="color:#cc7832;">var </span>meanwhileInLA = melbCupBreakfastInAU.withZoneSameInstant(pst)<br><span style="color:#cc7832;">assert </span>halloween22 == meanwhileInLA.toLocalDate()<br><span style="color:#cc7832;">assert </span>aet.<span style="color:#9876aa;">offset</span>.<span style="color:#9876aa;">hours </span>- pst.<span style="color:#9876aa;">offset</span>.<span style="color:#9876aa;">hours </span>== <span style="color:#6897bb;">18</span></pre></td>
<td style="padding:10px"><pre style="background-color:#2b2b2b;color:#a9b7c6;font-family:'JetBrains Mono',monospace;font-size:9.6pt;"><span style="color:#cc7832;">var </span>aet = TimeZone.<span style="color:#9876aa;font-style:italic;">getTimeZone</span>(<span style="color:#6a8759;">'Australia/Sydney'</span>)<br><span style="color:#cc7832;">assert </span>aet.<span style="color:#9876aa;">displayName </span>== <span style="color:#6a8759;">'Australian Eastern Standard Time'<br></span><span style="color:#cc7832;">assert </span>aet.observesDaylightTime()<br><span style="color:#cc7832;">var </span>melbourneCupBreakfast = <span style="color:#cc7832;">new </span>GregorianCalendar(aet).tap <span style="font-weight:bold;">{<br></span><span style="font-weight:bold;">    </span>set(<span style="color:#6a8759;">year</span>: <span style="color:#6897bb;">2022</span>, <span style="color:#6a8759;">month</span>: <span style="color:#6897bb;">10</span>, <span style="color:#6a8759;">date</span>: <span style="color:#6897bb;">1</span>, <span style="color:#6a8759;">hourOfDay</span>: <span style="color:#6897bb;">7</span>, <span style="color:#6a8759;">minute</span>: <span style="color:#6897bb;">30</span>)<br><span style="font-weight:bold;">}<br></span><span style="color:#cc7832;">var </span>pst = TimeZone.<span style="color:#9876aa;font-style:italic;">getTimeZone</span>(<span style="color:#6a8759;">'America/Los_Angeles'</span>)<br><span style="color:#cc7832;">assert </span>pst.<span style="color:#9876aa;">displayName </span>== <span style="color:#6a8759;">'Pacific Standard Time'<br></span><span style="color:#cc7832;">var </span>meanwhileInLA = <span style="color:#cc7832;">new </span>GregorianCalendar(pst).tap <span style="font-weight:bold;">{<br></span><span style="font-weight:bold;">    </span>setTimeInMillis(melbourneCupBreakfast.<span style="color:#9876aa;">timeInMillis</span>)<br><span style="font-weight:bold;">}<br></span><span style="color:#cc7832;">assert </span>meanwhileInLA.<span style="color:#9876aa;">time</span>.format(<span style="color:#6a8759;">'MMM dd'</span>, pst) == halloween22.format(<span style="color:#6a8759;">'MMM dd'</span>)<br><span style="color:#cc7832;">assert </span>aet.<span style="color:#9876aa;">rawOffset </span>/ <span style="color:#6897bb;">3600000 </span>- pst.<span style="color:#9876aa;">rawOffset </span>/ <span style="color:#6897bb;">3600000 </span>== <span style="color:#6897bb;">18<br></span></pre></td></tr></tbody></table><br>

<h2>Other useful classes</h2>

<p>The new date classes offer a few more useful classes. Here are some of the common ones:</p>

<ul><li><code>OffsetDateTime</code> - like <code>ZonedDateTime</code> but with just an offset from UTC rather than a full time-zone</li>
<li><code>Instant</code> - like <code>OffsetDateTime</code> but tied to UTC</li>
<li><code>YearMonth</code> - like a <code>LocalDate</code> but with no day component</li>
<li><code>MonthDay</code> - like a <code>LocalDate</code> but with no year component</li>
<li><code>Period</code> - used to represent periods of time, e.g. <code>Period.ofDays(14)</code>, <code>Period.ofYears(2)</code>; see also the&nbsp;<code>LocalDate</code> example above.</li>
<li><code>Duration</code> - a time-based amount of time, e.g. <code>Duration.ofSeconds(30)</code>, <code>Duration.ofHours(7)</code>; see also the&nbsp;<code>LocalTime</code> example above.</li></ul>

<h2>Conversions</h2>

<p>It is useful to convert between the new and legacy classes. Some useful conversion methods are shown below with Groovy enhancements shown in <span style="color:blue">blue</span>.</p>
<table style="width:70%;">
<tbody style="background-color:#eeeeee;">
<tr><th>From</th><th>Conversion method/property</th></tr>
<tr><td>GregorianCalendar&nbsp;</td><td><pre>toInstant()
toZonedDateTime()
from(ZonedDateTime)
</pre></td></tr>
<tr><td>Calendar</td><td><pre>toInstant()
<span style="color:blue">toZonedDateTime()
toOffsetDateTime()
toLocalDateTime()
toLocalDate()
toLocalTime()
toOffsetTime()
toDayOfWeek()
toYear()
toYearMonth()
toMonth()
toMonthDay()
zoneOffset
zoneId
</span></pre></td></tr>
<tr><td>Date</td>
<td><pre>toInstant()
from(Instant)
<span style="color:blue">toZonedDateTime()
toOffsetDateTime()
toLocalDateTime()
toLocalDate()
toLocalTime()
toOffsetTime()
toDayOfWeek()
toYear()
toYearMonth()
toMonth()
toMonthDay()
zoneOffset
zoneId
</span></pre></td></tr>
<tr><td>ZonedDateTime<br>OffsetDateTime<br>LocalDateTime<br>LocalDate<br>LocalTime</td>
<td><pre><span style="color:blue">toDate()
toCalendar()
</span></pre></td></tr>
</tbody></table>


<h2>SimpleDateFormat patterns</h2>

<p>
We saw several examples above using the <code>format</code> and <code>parse</code> methods. For the legacy date classes, numerous Groovy enhancements delegate to <code><a href="https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/text/SimpleDateFormat.html" target="_blank">SimpleDateFormat</a></code>.
This class represents date/time formats using pattern strings. These are special letters to represent some time or date component mixed with escaped literal strings. The special letters are often repeated to represent the minimum size field for number components and whether the full or an abbreviated form is used for other components.
</p>
<p>As an example, for the U.S. locale and U.S. Pacific Time time zone, the following pattern:
</p><pre>yyyy.MM.dd G 'at' HH:mm:ss z</pre>
would apply to the following text:
<pre>2001.07.04 AD at 12:08:56 PDT</pre>
<p></p>

<table>
<tbody style="background-color:#eeeeee">
<tr><th>Letter&nbsp;</th><th>Description</th></tr>
<tr><td>G</td><td>Era designator <code>AD</code></td></tr>
<tr><td>y</td><td>Year <code>1996; 96</code></td></tr>
<tr><td>Y</td><td>Week year (similar to year but allotted by weeks; the first/last few days of a year might be allotted to finish/start the last/previous week)</td></tr>
<tr><td>M</td><td>Month in year (context sensitive) <code>July; Jul; 07</code></td></tr>
<tr><td>L</td><td>Month in year (standalone form) <code>July; Jul; 07</code></td></tr>
<tr><td>w</td><td>Week in year <code>27</code></td></tr>
<tr><td>W</td><td>Week in month <code>2</code></td></tr>
<tr><td>D</td><td>Day in year <code>189</code></td></tr>
<tr><td>d</td><td>Day in month <code>10</code></td></tr>
<tr><td>F</td><td>Day of week in month <code>2</code></td></tr>
<tr><td>E</td><td>Day name in week <code>Tuesday; Tue</code></td></tr>
<tr><td>u</td><td>Day number of week (1 = Monday, ..., 7 = Sunday)</td></tr>
<tr><td>a</td><td>Am/pm marker <code>PM</code></td></tr>
<tr><td>H</td><td>Hour in day (0-23) <code>0</code></td></tr>
<tr><td>k</td><td>Hour in day (1-24) <code>24</code></td></tr>
<tr><td>K</td><td>Hour in am/pm (0-11) <code>0</code></td></tr>
<tr><td>h</td><td>Hour in am/pm (1-12) <code>12</code></td></tr>
<tr><td>m</td><td>Minute in hour <code>30</code></td></tr>
<tr><td>s</td><td>Second in minute <code>55</code></td></tr>
<tr><td>S</td><td>Millisecond <code>978</code></td></tr>
<tr><td>z</td><td>Time zone <code>Pacific Standard Time; PST; GMT-08:00</code></td></tr>
<tr><td>Z</td><td>Time zone (RFC 822) <code>-0800</code></td></tr>
<tr><td>X</td><td>Time zone (ISO 8601) <code>-08; -0800; -08:00</code></td></tr>
<tr><td>'</td><td>to escape text put a single quote on either side</td></tr>
<tr><td>''</td><td>two single quotes for a literal single quote <code>'</code></td></tr>
</tbody></table>

<p><br></p>

<h2>DateTimeFormatter patterns</h2>

<p>Groovy's <code>format</code> and <code>parse</code> enhancements for the new date classes delegate to the <code><a href="https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/format/DateTimeFormatter.html" target="_blank">DateTimeFormatter</a></code> class. It's behavior is similar to what we saw for&nbsp;<code>SimpleDateFormat</code>&nbsp;but with slightly different conversion letters:</p>

<table>
<tbody style="background-color:#eeeeee">
<tr><th>Conversion suffix&nbsp;</th><th>Description</th></tr>
<tr><td>G</td><td>era <code>AD</code></td></tr>
<tr><td>u</td><td>year <code>2004; 04</code></td></tr>
<tr><td>y</td><td>year-of-era <code>2004; 04</code></td></tr>
<tr><td>D</td><td>day-of-year <code>189</code></td></tr>
<tr><td>M/L</td><td>month-of-year <code>7; 07; Jul; July; J</code></td></tr>
<tr><td>d</td><td>day-of-month <code>10</code></td></tr>
<tr><td>Q/q</td><td>quarter-of-year <code>3; 03; Q3; 3rd quarter</code></td></tr>
<tr><td>Y</td><td>week-based-year <code>1996; 96</code></td></tr>
<tr><td>w</td><td>week-of-week-based-year <code>27</code></td></tr>
<tr><td>W</td><td>week-of-month <code>4</code></td></tr>
<tr><td>E</td><td>day-of-week <code>Tue; Tuesday; T</code></td></tr>
<tr><td>e/c</td><td>localized day-of-week <code>2; 02; Tue; Tuesday; T</code></td></tr>
<tr><td>F</td><td>week-of-month <code>3</code></td></tr>
<tr><td>a</td><td>am-pm-of-day <code>PM</code></td></tr>
<tr><td>h</td><td>clock-hour-of-am-pm (1-12) <code>12</code></td></tr>
<tr><td>K</td><td>hour-of-am-pm (0-11) <code>0</code></td></tr>
<tr><td>k</td><td>clock-hour-of-am-pm (1-24) <code>0</code></td></tr>
<tr><td>H</td><td>hour-of-day (0-23) <code>0</code></td></tr>
<tr><td>m</td><td>minute-of-hour <code>30</code></td></tr>
<tr><td>s</td><td>second-of-minute <code>55</code></td></tr>
<tr><td>S</td><td>fraction-of-second <code>978</code></td></tr>
<tr><td>A</td><td>milli-of-day <code>1234</code></td></tr>
<tr><td>n</td><td>nano-of-second <code>987654321</code></td></tr>
<tr><td>N</td><td>nano-of-day <code>1234000000</code></td></tr>
<tr><td>V</td><td>time-zone ID <code>America/Los_Angeles; Z; -08:30</code></td></tr>
<tr><td>z</td><td>time-zone name <code>Pacific Standard Time; PST</code></td></tr>
<tr><td>O</td><td>localized zone-offset <code>GMT+8; GMT+08:00; UTC-08:00;</code></td></tr>
<tr><td>X</td><td>zone-offset 'Z' for zero <code>Z; -08; -0830; -08:30; -083015; -08:30:15;</code></td></tr>
<tr><td>x</td><td>zone-offset <code>+0000; -08; -0830; -08:30; -083015; -08:30:15;</code></td></tr>
<tr><td>Z</td><td>zone-offset <code>+0000; -0800; -08:00;</code></td></tr>
<tr><td>p</td><td>pad next</td></tr>
<tr><td>'</td><td>to escape text put a single quote on either side</td></tr>
<tr><td>''</td><td>two single quotes for a literal single quote <code>'</code></td></tr>
</tbody></table>

<h3 id="localizedPatterns">Localized Patterns</h3>

<p>JDK19 adds the <code>ofLocalizedPattern(String requestedTemplate)</code> method. The requested template is one or more regular expression pattern symbols ordered from the largest to the smallest unit, and
consisting of the following patterns:</p>
<pre>     "G{0,5}" +        // Era
     "y*" +            // Year
     "Q{0,5}" +        // Quarter
     "M{0,5}" +        // Month
     "w*" +            // Week of Week Based Year
     "E{0,5}" +        // Day of Week
     "d{0,2}" +        // Day of Month
     "B{0,5}" +        // Period/AmPm of Day
     "[hHjC]{0,2}" +   // Hour of Day/AmPm (refer to LDML for 'j' and 'C')
     "m{0,2}" +        // Minute of Hour
     "s{0,2}" +        // Second of Minute
     "[vz]{0,4}"       // Zone
</pre>
<p>The requested template is mapped to the closest of available localized format as defined by the <a href="https://www.unicode.org/reports/tr35/tr35-dates.html#availableFormats_appendItems" target="_blank">Unicode LDML specification</a>. Here is an example of usage:<br></p>
<pre style="background-color:#2b2b2b;color:#a9b7c6;font-family:'JetBrains Mono',monospace;font-size:9.6pt;"><span style="color:#cc7832;">var </span>now = ZonedDateTime.<span style="color:#9876aa;font-style:italic;">now</span>()<br><span style="color:#cc7832;">var </span>columns = <span style="color:#6a8759;">'%7s | %10s | %10s | %10s | %14s%n'<br></span>printf columns, <span style="color:#6a8759;">'locale'</span>, <span style="color:#6a8759;">'GDK'</span>, <span style="color:#6a8759;">'custom'</span>, <span style="color:#6a8759;">'local'</span>, <span style="color:#6a8759;">'both'<br></span>[<span style="color:#9876aa;font-style:italic;">locale</span>(<span style="color:#6a8759;">'en'</span>, <span style="color:#6a8759;">'US'</span>),<br> <span style="color:#9876aa;font-style:italic;">locale</span>(<span style="color:#6a8759;">'ro'</span>, <span style="color:#6a8759;">'RO'</span>),<br> <span style="color:#9876aa;font-style:italic;">locale</span>(<span style="color:#6a8759;">'vi'</span>, <span style="color:#6a8759;">'VN'</span>)].each <span style="font-weight:bold;">{ </span>locale <span style="font-weight:bold;">-&gt;<br></span><span style="font-weight:bold;">    </span>Locale.<span style="color:#9876aa;font-style:italic;">default </span>= locale<br>    <span style="color:#cc7832;">var </span>gdk = now.format(<span style="color:#6a8759;">'y-MM-dd'</span>)<br>    <span style="color:#cc7832;">var </span>custom = now.format(<span style="color:#9876aa;font-style:italic;">ofPattern</span>(<span style="color:#6a8759;">'y-MM-dd'</span>))<br>    <span style="color:#cc7832;">var </span>local = now.format(<span style="color:#9876aa;font-style:italic;">ofLocalizedDate</span>(<span style="color:#9876aa;font-style:italic;">SHORT</span>))<br>    <span style="color:#cc7832;">var </span>both = now.format(<span style="color:#9876aa;font-style:italic;">ofLocalizedPattern</span>(<span style="color:#6a8759;">'yMM'</span>))<br>    printf columns, locale, gdk, custom, local, both<br><span style="font-weight:bold;">}<br></span></pre>

<p>Which has this output:<br></p>

<pre style="background-color:#2b2b2b;color:#a9b7c6;font-family:'JetBrains Mono',monospace;font-size:9.6pt;"><span style="color:#808080;">locale |        GDK |     custom |      local |           both<br></span><span style="color:#808080;"> en_US | 2022-12-18 | 2022-12-18 |   12/18/22 |        12/2022<br></span><span style="color:#808080;"> ro_RO | 2022-12-18 | 2022-12-18 | 18.12.2022 |        12.2022<br></span><span style="color:#808080;"> vi_VN | 2022-12-18 | 2022-12-18 | 18/12/2022 | tháng 12, 2022<br></span></pre>
<p>Example credit: <a href="https://twitter.com/nipafx/status/1604152548503461891" target="_blank">this example</a> from <a href="https://twitter.com/nipafx" target="_blank">Nicolai Parlog</a>.</p>

<h2>Formatter formats</h2>

<p>The <code><a href="https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Formatter.html" target="_blank">java.util.Formatter</a></code> class is a base class in Java for various kinds of formatting. It can be used directly, via <code>String.format</code>, <code>parse</code>, <code>printf</code>, or Groovy's <code>sprintf</code>.
We saw several examples of using <code>printf</code> and <code>parse</code> formatting in the above examples.
</p>
<p>
The <code>Formatter</code> class has methods which take a format string as its first argument and zero or more additional arguments.
The format string typically has one or more format specifiers (starting with a percent character) which
indicate that a formatted version of one of the additional arguments should be placed into the string at that point.
The general form of a format specifier is:
</p>
<pre>%[argument_index$][flag][width][.precision]conversion</pre>
<p>
Most of the parts are optional. The <code>argument_index</code> part is only used when referencing
one of the additional arguments more than once (or out of order). The <code>precision</code> part
is only used for floating point numbers. The <code>flag</code> part is used to indicate always include sign(+),
zero-padding(0), locale-specific comma delimiters(,), and left justification(-).
The <code>width</code> indicates the minimum number of characters for the output.
The <code>conversion</code> indicates how the argument should be processed, e.g. as a numeric field, a date,
a special character, or some other special processing. Upper and lowercase variants exist for most conversions
which, for the uppercase variant, will call <code>toUpperCase</code> after the conversion is complete.
</p>

<table>
<tbody style="background-color:#eeeeee">
<tr><th>Conversion&nbsp;</th><th>Description</th></tr>
<tr><td>'b', 'B'</td><td>Treat as a boolean or false if null</td></tr>
<tr><td>'h', 'H'</td><td>Output the arguments hashcode as a hex string</td></tr>
<tr><td>'s', 'S'</td><td>Treat as a String</td></tr>
<tr><td>'c', 'C'</td><td>Treat as a Unicode character</td></tr>
<tr><td>'d'</td><td>Treat as a decimal integer</td></tr>
<tr><td>'o'</td><td>Treat as an octal integer</td></tr>
<tr><td>'x', 'X'</td><td>Treat as a hexadecimal integer</td></tr>
<tr><td>'e', 'E'</td><td>Treat as a decimal number in scientific notation</td></tr>
<tr><td>'f'</td><td>Treat as a floating point number</td></tr>
<tr><td>'g', 'G'</td><td>Treat as a floating point in either decimal or scientific notation</td></tr>
<tr><td>'a', 'A'</td><td>Treat as a hexadecimal floating-point number</td></tr>
<tr style="background-color:#a9b7c6"><td>'t', 'T'</td><td>Treat as the <i>prefix </i>for a date/time conversion</td></tr>
<tr><td>'%'</td><td>A literal percent</td></tr>
<tr><td>'n'</td><td>A line separator</td></tr>
</tbody>
</table>
<p>When the date/time prefix is used, additional suffixes are applicable.</p>

<p>For formatting times:</p>

<table>
<tbody style="background-color:#eeeeee">
<tr><th>Conversion suffix&nbsp;</th><th>Description</th></tr>
<tr><td>'H'</td><td>Hour of the day for the 24-hour clock as two digits <code>00 - 23</code></td></tr>
<tr><td>'I'</td><td>Hour for the 12-hour clock as two digits <code>01 - 12</code></td></tr>
<tr><td>'k'</td><td>Hour of the day for the 24-hour clock <code>0 - 23</code></td></tr>
<tr><td>'l'</td><td>Hour for the 12-hour clock <code>1 - 12</code></td></tr>
<tr><td>'M'</td><td>Minute within the hour as two digits <code>00 - 59</code></td></tr>
<tr><td>'S'</td><td>Seconds within the minute as two digits <code>00 - 60</code><br>("60" is used for leap seconds)</td></tr>
<tr><td>'L'</td><td>Millisecond within the second as three digits <code>000 - 999</code></td></tr>
<tr><td>'N'</td><td>Nanosecond within the second as nine digits <code>000000000 - 999999999</code></td></tr>
<tr><td>'p'</td><td>Locale-specific morning or afternoon marker in lower case, <code>am</code> or <code>pm</code><br>(The conversion prefix 'T' forces this output to upper case)</td></tr>
<tr><td>'z'</td><td>RFC&nbsp;822 style numeric time zone offset from GMT <code>-0800</code><br>(Adjusted as needed for Daylight Saving Time)</td></tr>
<tr><td>'Z'</td><td>Abbreviated time zone</td></tr>
<tr><td>'s'</td><td>Seconds since the beginning of the epoch starting at 1 January 1970 00:00:00 UTC</td></tr>
<tr><td>'Q'</td><td>Milliseconds since the beginning of the epoch starting at 1 January 1970 00:00:00 UTC</td></tr>
</tbody></table>

<p><br>For formatting dates:</p>

<table>
<tbody style="background-color:#eeeeee">
<tr><th>Conversion suffix&nbsp;</th><th>Description</th></tr>
<tr><td>'B'</td><td>Locale-specific full month name <code>January</code></td></tr>
<tr><td>'b', 'h'</td><td>Locale-specific abbreviated month name <code>Jan</code></td></tr>
<tr><td>'A'</td><td>Locale-specific full name of the day of the week <code>Sunday</code></td></tr>
<tr><td>'a'</td><td>Locale-specific short name of the day of the week <code>Sun</code></td></tr>
<tr><td>'C'</td><td>First two digits of four-digit year <code>00 - 99</code></td></tr>
<tr><td>'Y'</td><td>Year as four digits <code>0092</code></td></tr>
<tr><td>'y'</td><td>Last two digits of the year <code>00 - 99</code></td></tr>
<tr><td>'j'</td><td>Day of year as three digits <code>001 - 366</code></td></tr>
<tr><td>'m'</td><td>Month as two digits <code>01 - 13<code></code></code></td></tr>
<tr><td>'d'</td><td>Day of month as two digits <code>01 - 31</code></td></tr>
<tr><td>'e'</td><td>Day of month <code>1 - 31</code></td></tr>
</tbody></table>

<p><br>For formatting date/time compositions:</p>

<table>
<tbody style="background-color:#eeeeee">
<tr><th>Conversion suffix&nbsp;</th><th>Description</th></tr>
<tr><td>'R'</td><td>Time formatted for the 24-hour clock as "%tH:%tM"</td></tr>
<tr><td>'T'</td><td>Time formatted for the 24-hour clock as "%tH:%tM:%tS"</td></tr>
<tr><td>'r'</td><td>Time formatted for the 12-hour clock as "%tI:%tM:%tS %Tp"<br>The location of the morning or afternoon marker ('%Tp') may be locale-dependent.</td></tr>
<tr><td>'D'</td><td>Date formatted as "%tm/%td/%ty"</td></tr>
<tr><td>'F'</td><td>ISO&nbsp;8601 date formatted as "%tY-%tm-%td"</td></tr>
<tr><td>'c'</td><td>Date and time formatted as "%ta %tb %td %tT %tZ %tY" <code>Sun Jul 21 15:17:00 EDT 1973</code></td></tr>
</tbody></table>

<p><br></p>

<h2>Further information</h2>

<ul><li>Java 8 LocalDate, LocalDateTime, Instant <a href="https://www.digitalocean.com/community/tutorials/java-8-date-localdate-localdatetime-instant" target="_blank">tutorial</a></li><li><a href="https://www.baeldung.com/java-8-date-time-intro" target="_blank">Introduction</a> to the Java 8 Date/Time API</li><li>A <a href="https://www.baeldung.com/java-simple-date-format" target="_blank">guide</a> to SimpleDateFormat</li><li>Joda-Time <a href="https://www.joda.org/joda-time/" target="_blank">website</a></li><li><a href="https://docs.oracle.com/javase/tutorial/datetime/iso/legacy.html" target="_blank">Guidelines</a> for Date-Time/legacy date interoperability</li>
<li>Source code: <a href="https://github.com/paulk-asert/groovy-cheat-sheets/blob/main/src/main/groovy/DateTimeExamples.groovy" target="_blank">examples for new date classes</a></li>
<li>Source code: <a href="https://github.com/paulk-asert/groovy-cheat-sheets/blob/main/src/main/groovy/DateUtilExamples.groovy" target="_blank">examples for legacy date classes</a></li></ul>
