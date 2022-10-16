/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

wiki = false
def devSiteBase = 'https://groovy.apache.org/'

menu {
    group('Groovy') {
        item 'Learn',                       'learn.html'
        item 'Documentation',               'documentation.html'
        item 'Download',                    "${devSiteBase}download.html"
        item 'Support',                     'support.html'
        item 'Contribute',                  "${devSiteBase}"
        item 'Ecosystem',                   'ecosystem.html'
    }

    group('About') {
        item 'Source code',                 'https://github.com/apache/groovy'
        item 'Security',                    'security.html'
        item 'Books',                       'learn.html#books'
        item 'Thanks',                      'thanks.html'
        item 'Sponsorship',                 'http://www.apache.org/foundation/sponsorship.html'
        item 'FAQ',                         'faq.html'
        item 'Search',                      'search.html'
    }

    group('Socialize') {
        item 'Discuss on the mailing-list', 'mailing-lists.html',                               'fa-envelope'
        item 'Groovy on Twitter',           'https://twitter.com/ApacheGroovy',                 'fa-twitter'
        item 'Events and conferences',      'events.html',                                      'fa-calendar'
        item 'Source code on GitHub',       'https://github.com/apache/groovy',                 'fa-github'
        item 'Report issues in Jira',       'reporting-issues.html',                            'fa-bug'
        item 'Stack Overflow questions',    'http://stackoverflow.com/questions/tagged/groovy', 'fa-stack-overflow'
	    item 'Slack Community',             'http://groovycommunity.com/',                      'fa-slack'
    }
}

pages {
    page 'index', 'index', [allEvents: allEvents]
    page 'search', 'search', [category: 'Search']
    page 'ecosystem', 'ecosystem', [category: 'Ecosystem', ecosys: ecosystem]
    page 'learn', 'learn', [category: 'Learn', docSections: documentationSections, allBooks: library, videos: videos, courses: courses]
    page 'documentation', 'documentation', [category: 'Documentation', docSections: documentationSections, allDocVersions: allDocVersions]
//    page 'download', 'download', [category: 'Download', distributions: distributions]
    page 'versioning', 'versioning', [category: 'Download']
    page 'indy', 'indy', [category: 'Download']
    page 'security', 'security', [category: 'Learn']
    page 'reporting-issues', 'reporting-issues', [category: 'Support']
    page 'support', 'support', [category: 'Support']
    page 'usergroups', 'usergroups', [category: 'Support', userGroups: usergroups]
    page 'groovy-weekly', 'groovy-weekly', [category: 'Support']
    page 'mailing-lists', 'mailing-lists', [category: 'Support']
    page 'contribute', 'contribute', [category: 'Develop']
    page 'thanks', 'thanks', [category: 'Support']
    page 'faq', 'faq', [category: 'Documentation', docSections: documentationSections]
    page 'events', 'events', [category: 'Support', allEvents: allEvents]
    page 'api', 'api', [category: 'Learn', iframeTarget: "${DOCS_BASEURL}/html/gapi"]
    page 'gdk', 'gdk', [category: 'Learn', iframeTarget: "${DOCS_BASEURL}/html/groovy-jdk"]
    page 'singlepagedocumentation', 'single-page-documentation', [category: 'Learn', iframeTarget: "${DOCS_BASEURL}/html/documentation/"]
    page 'changelogs', 'changelogs', [:]
    page '404','404', [:]
}

documentation {
    groovyDocumentationVersions([
            '1.7.0', '1.7.1', '1.7.2', '1.7.3', '1.7.4', '1.7.5', '1.7.6', '1.7.7', '1.7.8', '1.7.9', '1.7.10', '1.7.11',
            '1.8.0', '1.8.1', '1.8.2', '1.8.3', '1.8.4', '1.8.5', '1.8.6', '1.8.7', '1.8.8', '1.8.9',
            '2.0.0', '2.0.1', '2.0.2', '2.0.3', '2.0.4', '2.0.5', '2.0.6', '2.0.7', '2.0.8',
            '2.1.0', '2.1.1', '2.1.2', '2.1.3', '2.1.4', '2.1.5', '2.1.6', '2.1.7', '2.1.8', '2.1.9',
            '2.2.0', '2.2.1', '2.2.2',
            '2.3.0', '2.3.1', '2.3.2', '2.3.3', '2.3.4', '2.3.5', '2.3.6', '2.3.7', '2.3.8', '2.3.9', '2.3.10', '2.3.11',
            '2.4.0', '2.4.1', '2.4.2', '2.4.3', '2.4.4', '2.4.5', '2.4.6', '2.4.7', '2.4.8', '2.4.9', '2.4.10', '2.4.11', '2.4.12', '2.4.13', '2.4.14', '2.4.15', '2.4.16', '2.4.17', '2.4.18', '2.4.19', '2.4.20', '2.4.21',
            '2.5.0', '2.5.1', '2.5.2', '2.5.3', '2.5.4', '2.5.5', '2.5.6', '2.5.7', '2.5.8', '2.5.9', '2.5.10', '2.5.11', '2.5.12', '2.5.13', '2.5.14', '2.5.15', '2.5.16', '2.5.17', '2.5.18', '2.5.19',
            '2.6.0-alpha-1', '2.6.0-alpha-2', '2.6.0-alpha-3', '2.6.0-alpha-4',
            '3.0.0', '3.0.1', '3.0.2', '3.0.3', '3.0.4', '3.0.5', '3.0.6', '3.0.7', '3.0.8', '3.0.9', '3.0.10', '3.0.11', '3.0.12', '3.0.13',
            '4.0.0-rc-1', '4.0.0-rc-2', '4.0.0', '4.0.1', '4.0.2', '4.0.3', '4.0.4', '4.0.5', '4.0.6',
    ])

    section('Getting started','fa-graduation-cap') {
        //          NAME                                     TARGET HTML         DOCPAGE HTML                       GENERATE
        item 'Download Groovy',                             'download',         'download',                         false
        item 'Install Groovy',                              'install',          'core-getting-started'
        item 'Differences with Java',                       'differences',      'core-differences-java'
        item 'The Groovy Development Kit',                  'groovy-dev-kit',   'core-gdk'
        item 'Runtime and compile-time metaprogramming',    'metaprogramming',  'core-metaprogramming'
        item 'The Grape dependency manager',                'grape',            'grape'
        item 'Testing guide',                               'testing',          'core-testing-guide'
        item 'Domain-Specific Languages',                   'dsls',             'core-domain-specific-languages'
        item 'Integrating Groovy into applications',        'integrating',      'guide-integrating'
        item 'Security',                                    'security',         'security'
        item 'Design patterns in Groovy',                   'design-patterns',  'design-pattern-in-groovy'
        item 'Style guide',                                 'style-guide',      'style-guide'
    }

    section ('Language Specification', 'fa-graduation-cap') {
        item 'Syntax',              'syntax',               'core-syntax'
        item 'Operators',           'operators',            'core-operators'
        item 'Program structure',   'structure',            'core-program-structure'
        item 'Object orientation',  'objectorientation',    'core-object-orientation'
        item 'Closures',            'closures',             'core-closures'
        item 'Semantics',           'semantics',            'core-semantics'
    }

    section ('Tools','fa-gears') {
        item 'groovyc - the Groovy compiler',               'groovyc',          'tools-groovyc'
        item 'groovysh - the Groovy repl-like shell',       'groovysh',         'groovysh'
        item 'groovyConsole - the Groovy Swing console',    'groovyconsole',    'groovy-console'
        item 'IDE integration', 'ides', 'tools-ide'
    }

    section('Groovy module guides', 'fa-cubes') {
        item 'Parsing and producing JSON',          'processing-json',  'json-userguide'
        item 'Working with a relational database',  'databases',        'sql-userguide'
        item 'Processing XML',                      'processing-xml',   'xml-userguide'
        item 'Processing YAML',                     'processing-yaml',  'yaml-userguide'
        item 'Processing TOML',                     'processing-toml',  'toml-userguide'
        item 'SQL-like querying of collections',    'using-ginq',       'ginq-userguide'
        item 'Scripting Ant tasks',                 'scripting-ant',    'ant-builder'
        item 'Template engines',                    'templating',       'template-engines'
        item 'Creating Swing UIs',                  'swing',            'swing-builder'
        item 'Servlet support',                     'servlet',          'servlet-userguide'
        item 'Working with JMX',                    'jmx',              'jmx'
    }

    section ('API documentation', 'fa-code') {
        item 'GroovyDoc documentation of the Groovy APIs',  'api',    'api'
        item 'The Groovy Development Kit enhancements',     'gdk',    'gdk'
    }
}

ecosystem {
    project('Grails') {
        description 'Grails is an Open Source, full stack, web application framework for the JVM. It takes advantage of the Groovy programming language and convention over configuration to provide a productive and stream-lined development experience.'
        url 'http://grails.org/'
        logo 'img/ecosystem/grails.png'
    }

    project('Gradle') {
        description 'Gradle is build automation evolved. Gradle can automate the building, testing, publishing, deployment and more of software packages or other types of projects such as generated static websites, generated documentation or indeed anything else.'
        url 'http://gradle.org'
        logo 'img/ecosystem/gradle.png'
    }

    project('Spock') {
        description 'Spock is a testing and specification framework for Java and Groovy applications. What makes it stand out from the crowd is its beautiful and highly expressive specification language. Thanks to its JUnit runner, Spock is compatible with most IDEs, build tools, and continuous integration servers.'
        url 'http://spockframework.org/'
        logo ''
    }

    project('GPars') {
        description 'The GPars framework offers Java developers intuitive and safe ways to handle Java or Groovy tasks concurrently. Leveraging the enormous flexibility of the Groovy programming language and building on proven Java technologies, we aim to make concurrent programming for multi-core hardware intuitive, robust and enjoyable.'
        url 'http://www.gpars.org/'
        logo 'img/ecosystem/gpars.png'
    }

    project('Ratpack') {
        description 'Ratpack is a simple, capable, toolkit for creating high performance web applications.'
        url 'http://www.ratpack.io/'
        logo 'img/ecosystem/ratpack.png'
    }

    project('Griffon') {
        description 'Griffon is an application framework for developing desktop applications in the JVM, with Groovy being the primary language of choice. Inspired by Grails, Griffon follows the Convention over Configuration paradigm, paired with an intuitive MVC architecture and a command line interface.'
        url 'http://griffon-framework.org/'
        logo 'img/ecosystem/griffon.png'
    }

    project('Geb') {
        description 'Geb is a powerful browser functional testing framework that lets you quickly and easily write functional tests in Groovy. It brings together the power of WebDriver, the elegance of jQuery content selection, the robustness of Page Object modelling and the expressiveness of the Groovy language.'
        url 'http://www.gebish.org/'
        logo 'img/ecosystem/geb.png'
    }

    project('SDKMAN!') {
        description 'SDKMAN! is a tool for managing parallel versions of multiple Software Development Kits on most Unix based systems. It provides a convenient command line interface for installing, switching, removing and listing Candidates.'
        url 'http://sdkman.io/'
        logo 'img/ecosystem/sdkman.png'
    }

    project('BeakerX') {
        description 'BeakerX brings Groovy to the Jupyter interactive computing environment.  It provides interactive plots, tables, and a notebook-style web UI.'
        url 'http://beakerx.com/'
        logo 'img/ecosystem/beakerx.png'
    }

    project('Micronaut') {
        description 'Micronaut is a modern, JVM-based, full-stack framework for building modular, easily testable microservice applications. '
        url 'http://micronaut.io/'
        logo 'img/ecosystem/micronaut.png'
    }

    project('Infrastructor') {
        description 'Infrastructor is an agentless server provisioning tool written in Groovy. It provides a DSL to configure either VMs and bare metal machines via SSH.'
        url 'http://infrastructor.io/'
        logo 'img/ecosystem/infrastructor.png'
    }
}

allEvents {
    // Note that the event image should be 257x180 to look nice
    event('ApacheCon NA') {
        location 'New Orleans, Louisiana'
        date 'October 3-6, 2022'
        url 'https://www.apachecon.com/acna2022/'
        logo 'img/confs/acna2022.png'
        description '''
            <p>
            The latest innovations from dozens of Apache projects and their communities in a collaborative,
            vendor-neutral environment.
            </p>
            <p>There is a 2-day Groovy track covering latest news, functional programming with Groovy,
            Spock, Grails, Micronaut, Gradle, DSLs and more. Also, there is Groovy content in other tracks
            plus some special events like BoF sessions and other surprises.</p>
        '''
    }
}

books {
    book('Groovy in Action, Second Edition') {
        authors "Dierk König, Paul King, Guillaume Laforge, Hamlet D'Arcy, Cédric Champeau, Erik Pragt, and Jon Skeet"
        cover 'img/books/regina.png'
        url 'http://www.manning.com/koenig2/'
        description 'The undisputed definitive reference on the Groovy programming language, authored by core members of the development team.'
    }

    book('Making Java Groovy') {
        authors 'Ken Kousen'
        cover 'img/books/Kousen-MJG.png'
        url 'http://www.manning.com/kousen/'
        description 'Make Java development easier by adding Groovy. Each chapter focuses on a task Java developers do, like building, testing, or working with databases or restful web services, and shows ways Groovy can help.'
    }

    book('Programming Groovy 2') {
        authors 'Venkat Subramaniam'
        cover 'img/books/vslg2.jpg'
        url 'http://pragprog.com/book/vslg2/programming-groovy-2'
        description 'Dynamic productivity for the Java developer'
    }

    book('Groovy 2 Cookbook') {
        authors 'Andrey Adamovitch, Luciano Fiandeso'
        cover 'img/books/g2cook.jpg'
        url 'https://www.packtpub.com/application-development/groovy-2-cookbook'
        description 'Over 90 recipes that provide solutions to everyday programming challenges using the powerful features of Groovy 2'
    }

    book('Groovy for Domain-Specific Languages - Second Edition') {
        authors 'Fergal Dearle'
        cover 'img/books/gdsl.jpg'
        url 'https://www.packtpub.com/application-development/groovy-domain-specific-languages-second-edition'
        description 'Extend and enhance your Java applications with domain-specific scripting in Groovy'
    }

    book('Groovy Goodness Notebook') {
        authors 'Hubert A. Klein Ikkink'
        cover 'img/books/ggood.jpg'
        url 'https://leanpub.com/groovy-goodness-notebook'
        description 'Experience the Groovy programming language through code snippets. Learn more about (hidden) Groovy features with code snippets and short articles. The articles and code will get you started quickly and will give more insight in Groovy.'
    }

    book('Learning Groovy') {
        authors 'Adam L. Davis'
        cover 'img/books/learning_groovy.jpg'
        url 'http://www.apress.com/us/book/9781484221167'
        description "Start building powerful apps that take advantage of the dynamic scripting capabilities of the Groovy language. This book covers Groovy fundamentals, such as installing Groovy, using Groovy tools, and working with the Groovy Development Kit (GDK). You'll also learn more advanced aspects of Groovy."
    }

    book("Grails 3: A Practical Guide to Application Development") {
        authors "Eric Helgeson"
        cover 'img/books/practical-grails-3-book-cover.png'
        url 'https://www.grails3book.com/'
        description "The first book dedicated to Grails 3. You will learn the concepts behind building Grails applications. Real, up-to-date code examples are provided so you can easily follow along."
    }

    book('Falando de Grails') {
        authors 'Henrique Lobo Weissmann'
        cover 'img/books/weissmann_groovy_grails.png'
        url 'http://www.casadocodigo.com.br/products/livro-grails'
        description 'For Groovy and Grails developers, authored by the founder of Grails Brasil based on his experiences as a Groovy and Grails consultant.'
    }

}

usergroups {
    // Europe
    userGroup('Aarhus Groovy & Grails Meetup') {
        location 'Europe/Denmark'
        url 'https://www.linkedin.com/groups/3702945/'
    }
    userGroup('Paris Groovy Grails User Group') {
         location 'Europe/France'
    }
    userGroup('Berlin Groovy User Group') {
        location 'Europe/Germany'
        url 'http://www.meetup.com/de/Berlin-Groovy-User-Group/'
    }
    userGroup('Groovy & Grails Israel User Group') {
        location 'Europe/Israel'
    }
    userGroup('Warsaw Groovy User Group') {
        location 'Europe/Poland'
        url 'http://www.meetup.com/Warsaw-Groovy-User-Group/'
    }
    userGroup('Madrid Groovy User Group') {
        location 'Europe/Spain'
        url 'http://www.meetup.com/madrid-gug/'
    }
    userGroup('Dutch Groovy and Grails User Group (NLGUG)') {
        location 'Europe/The Netherlands'
        url 'http://www.meetup.com/nl-gug/'
    }

    // North-America
    userGroup('Austin Groovy and Grails User Group (TX)') {
        location 'North-America/United States'
        url 'http://www.meetup.com/Austin-Groovy-and-Grails-Users/'
    }
    userGroup('Boston Groovy, Grails, Spring Meetup (B2GS)') {
        location 'North-America/United States'
        url 'https://twitter.com/B2GSMeetup'
    }
    userGroup('Coder Consortium of Sacramento') {
        location 'North-America/United States'
        url 'http://coderconsortium.com/'
    }
    userGroup('DFW Groovy & Grails User Group') {
        location 'North-America/United States'
        url 'http://dfw2gug.org'
    }
    userGroup('Groovy Users of Minnesota') {
        location 'North-America/United States'
        url 'https://www.meetup.com/groovymn/'
    }
    userGroup('NYC Groovy / Grails Meetup') {
        location 'North-America/United States'
        url 'http://www.meetup.com/grails/'
    }
    userGroup('Pittsburgh Groovy Programming') {
        location 'North-America/United States'
        url 'http://www.meetup.com/Pittsburgh-Groovy-Programming/'
    }

    // South-America
    userGroup('Grails Brasil - Groovy and Grails users group of Brazil') {
        location 'South-America/Brazil'
        url 'http://www.grailsbrasil.com.br'
    }
    userGroup('Brazil Groovy and Grails Meetup') {
        location 'South-America/Brazil'
        url 'http://www.meetup.com/groovybr'
    }

    // Asia
    userGroup('Bangalore Groovy Grails Meetup') {
        location 'Asia/India'
        url 'http://www.meetup.com/Bangalore-Groovy-Grails-Meetup/'
    }
    userGroup('Japan Grails/Groovy User Group') {
        location 'Asia/Japan'
        url 'http://www.jggug.org/'
    }

    // Oceania?
    /* userGroup('') { location 'Oceania/Australia' } */
}

videos {
    video('The Groovy ecosystem revisited') {
        speaker 'Andrés Almiray'
        summary '''
            <p>Groovy is a well established player in the JVM since a few years ago.
            Its increased popularity across the years has spawned several projects that conform the Groovy Ecosystem.
            You've probably heard of Grails, Gradle, Griffon and Spock.
            But what about the rest of projects that are just waiting around the corner to be discovered and make your life easier?
            This talk presents them tools and libraries that use Groovy as the main driving force to get the job done.</p>
        '''
        pictureUrl 'groovy-ecosystem-revisited.png'
        videoUrl 'https://www.youtube.com/watch?v=2NGeaIwmnC8&list=PLwxhnQ2Qv3xuE4JEKBpyE2AbbM_7G0EN1&index=5'
        slidesUrl 'http://fr.slideshare.net/aalmiray/gr8conf-groovy-ecosystem'
    }

    video('Metaprogramming with the Groovy runtime') {
        speaker 'Jeff Brown'
        summary '''
            <p>The dynamic runtime nature of Groovy is one of the things that sets it apart from standard Java and makes it a fantastic language for building dynamic applications for the Java Platform.
            The metaprogramming capabilities offered by the language provide everything that an application development team needs to build systems that are far more capable than their all Java counterparts.
            This Part 1 of 2 will cover the runtime metaprogramming capabilities of Groovy. The session will dive deep into Groovy's Meta Object Protocol (MOP) which implements the incredibly dynamic runtime dispatch mechanism.
            The session will include a lot of live code demonstrating really powerful runtime features of the language.
            This session is focused specifically on Groovy's runtime metaprogramming capabilities.
            Part 2 of 2 will cover Groovy's compile time metaprogramming capabilities</p>
        '''
        pictureUrl 'metaprogramming-part-1.png'
        videoUrl 'https://www.youtube.com/watch?v=1xvg8Wcj-hg&list=PLwxhnQ2Qv3xuE4JEKBpyE2AbbM_7G0EN1&index=9'
    }

    video('Groovy Puzzlers') {
        speaker 'Noam Tenne'
        summary '''
            <p>Remember the epic Java Puzzlers? Here's the Groovy version, and we have some neat ones!
            Even though we are totally a Grails shop here at JFrog, some of these had us scratching our heads for days trying to figure them out.
            And there is more!
            Contributions from the truly Groovy senseis, including Guillaume Laforge, Andrés Almiray, Tim Yates, Ken Kousen
            make this talk an unforgettable journey to Groovy.
            In this talk, you'll have the expected dose of fun and enlightenment hearing about our mistakes and failures, great and small,
            in hard core Groovy/Grails development.</p>
        '''
        pictureUrl 'groovy-puzzlers.png'
        videoUrl 'https://www.youtube.com/watch?v=GfIhxi7L6R0&list=PLwxhnQ2Qv3xuE4JEKBpyE2AbbM_7G0EN1&index=17'
    }
}

courses {
    course('The Complete Apache Groovy Developer Course') {
        instructor 'Dan Vega'
        url 'https://www.udemy.com/apache-groovy/?couponCode=LEARN_GROOVY'
        description '''
            <p>I am going to teach you everything you need to know to start using The Groovy Programming language.
            If you're a beginner programmer with a some experience in another language like Python or Ruby this course is for you.
            Dynamic languages are generally thought of as easier for total beginners to learn because they're flexible and fun.
            If you're an existing Java Developer (Beginner or Experienced) this course is also for you.</p>

            <p>This course is packed with almost 14 hours of content. We are going to start off with getting your
            development environment up and running and then go through the very basics of the language.
            From there we are going to build on that in each section cover topics like closures, meta-programming,
            builders and so much more.</p>
        '''
        cover 'groovy-course-cover.png'
    }
    course('Groovy Fundamentals') {
        instructor 'Ken Kousen'
        url 'https://www.oreilly.com/library/view/groovy-programming-fundamentals/9781491926253/'
        description '''
            <p>Learn the advantages of using Groovy by itself and with existing Java projects. This video workshop takes
            you into the heart of this JVM language and shows you how Groovy can help increase your productivity through
            dynamic language features similar to those of Python, Ruby, and Smalltalk.</p>
        '''
        cover 'groovy-course-fundamentals.png'
    }
    course('Practical Groovy Programming') {
        instructor 'Ken Kousen'
        url 'https://learning.oreilly.com/videos/practical-groovy-programming/9781491930908/'
        description '''
            <p>Take your basic Groovy skills to the next level with this practical video workshop. Presenter and Java consultant
            Ken Kousen shows you how to work with XML and JSON, implement runtime metaprogramming, and use several AST transformations.
            You'll also dive into operator overloading, Groovy SQL, and the Groovy JDK.</p>
        '''
        cover 'groovy-course-practical.png'
    }
    course('Mastering Groovy Programming') {
        instructor 'Ken Kousen'
        url 'https://learning.oreilly.com/videos/mastering-groovy-programming/9781491930915'
        description '''
            <p>Learn advanced techniques for working with the Groovy programming language. In this video workshop, presenter
            and Java consultant Ken Kousen shows you how to create RESTful web services, conduct Unit Tests, apply Groovy's
            functional programming features, and use Java's Spring Framework in conjunction with Groovy.</p>
        '''
        cover 'groovy-course-mastering.png'
    }
    course('Groovy Beginner To Advanced') {
        instructor 'Object Computing'
        url 'https://objectcomputing.com/services/training/catalog/grails/groovy-beginner-to-advanced'
        description '''
            <p>This 2-day, comprehensive course covers a lot of material and takes JVM developers from beginner to advanced
            with the Groovy language by way of comprehensive lecture, demonstration, and hands-on exercises.
            Developers will leave the experience with all of the skills needed to effectively use the Groovy programming
            language to build many kinds of JVM applications.</p>
        '''
        cover 'groovy-course-beginner.png'
    }
    course('Groovy Metaprogramming') {
        instructor 'Object Computing'
        url 'https://objectcomputing.com/services/training/catalog/grails/advanced-groovy-metaprogramming-with-grails-3'
        description '''
            <p>The course covers, in-depth, how Groovy's dynamic dispatch mechanism works and how application and plugin
            code can participate with that dispatch mechanism to provide more simple application code. You'll also learn
            techniques that may be applied at compile time to enhance classes, including AST Transformations,
            automatically enhancing Grails artifacts with traits and more.</p>
        '''
        cover 'groovy-course-metaprogramming.png'
    }
}
