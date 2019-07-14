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
def userSiteBase = 'http://groovy-lang.org/'
def devSiteBase = '/'

changelogs = false
releaseNotes = false

menu {
    group('Groovy') {
        item 'Learn',                       "${userSiteBase}learn.html"
        item 'Documentation',               "${userSiteBase}documentation.html"
        item 'Download',                    "${devSiteBase}download.html"
        item 'Support',                     "${userSiteBase}support.html"
        item 'Contribute',                  "${devSiteBase}"
        item 'Ecosystem',                   "${userSiteBase}ecosystem.html"
    }

    group('About') {
        item 'Source code',                 'https://github.com/apache/groovy'
        item 'Security',                    "${userSiteBase}security.html"
        item 'Books',                       "${userSiteBase}learn.html#books"
        item 'Thanks',                      "${userSiteBase}thanks.html"
        item 'Sponsorship',                 'http://www.apache.org/foundation/sponsorship.html'
        item 'FAQ',                         "${userSiteBase}faq.html"
        item 'Search',                      "${userSiteBase}search.html"
    }

    group('Socialize') {
        item 'Discuss on the mailing-list', "${userSiteBase}mailing-lists.html",                'fa-envelope'
        item 'Groovy newsletter',           "${userSiteBase}groovy-weekly.html",                'fa-envelope-o'
        item 'Groovy on Twitter',           'https://twitter.com/ApacheGroovy',                 'fa-twitter'
        item 'Events and conferences',      "${userSiteBase}events.html",                       'fa-calendar'
        item 'Source code on GitHub',       'https://github.com/apache/groovy',                 'fa-github'
        item 'Report issues in Jira',       "${userSiteBase}reporting-issues.html",             'fa-bug'
        item 'Stack Overflow questions',    'http://stackoverflow.com/questions/tagged/groovy', 'fa-stack-overflow'
        item 'Slack Community',             'http://groovycommunity.com/',                      'fa-slack'
    }
}

pages {
//    page 'index', 'index', [:]
    page 'search', 'search', [category: 'Search']
    page 'download', 'download', [category: 'Download', distributions: distributions]
    page 'versioning', 'versioning', [category: 'Download']
    page 'contribute', 'index', [category: 'Develop']
    page 'buildstatus', 'buildstatus', [category: 'Develop']
    page 'faq', 'faq', [category: 'Documentation']
    page '404','404', [:]
}

documentation {
    section('Getting started','fa-graduation-cap') {
        //          NAME                                     TARGET HTML         DOCPAGE HTML                       GENERATE
        item 'Download Groovy',                             'download',         'download',                         false
    }
}

downloads {
    distribution('Groovy 3.0') {
        description {
            yield 'Groovy 3.0 is a bleeding edge '
            a(href: 'versioning.html', 'version')
            yield ' of Groovy designed for JDK8+ and with the new Parrot parser enabled by default. Pre-stable versions are available:'
        }
        version('3.0.0-beta-2') {
            stable false
//            windowsInstaller 'https://dl.bintray.com/groovy/Distributions/groovy-3.0.0-beta-2-installer.exe'
        }
        version('3.0.0-beta-1') {
            stable false
            windowsInstaller 'https://dl.bintray.com/groovy/Distributions/groovy-3.0.0-beta-1-installer.exe'
        }
    }
    distribution('Groovy 2.6') {
        description {
            yield 'Groovy 2.6 is designed for JDK7+ and supports the new Parrot parser (when enabled) but has been'
            yield ' retired before reaching final release to focus on Groovy 3.0. Alpha versions are available to help'
            yield ' people wanting to port towards Groovy 3.0 but who are stuck on JDK7. See links under "Other versions" for details.'
        }
    }
    distribution('Groovy 2.5') {
        description {
            yield 'Groovy 2.5 is the latest stable '
            a(href: 'versioning.html', 'version')
            yield ' of Groovy.'
        }
        version('2.5.7') {
            stable true
            windowsInstaller 'https://dl.bintray.com/groovy/Distributions/groovy-2.5.7-installer.exe'
        }
    }
    distribution('Groovy 2.4') {
        description {
            yield 'Groovy 2.4 is the previous stable '
            a(href: 'versioning.html', 'version')
            yield ' of Groovy.'
            yieldUnescaped ''' Important: Releases before 2.4.4 weren't done under the Apache Software Foundation and are provided as a convenience, without any warranty.'''
        }
        version('2.4.17') {
            stable true
            windowsInstaller 'https://dl.bintray.com/groovy/Distributions/groovy-2.4.17-installer.exe'
        }
    }
}
