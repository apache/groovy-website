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
def userSiteBase = 'https://groovy-lang.org/'
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
        item 'Blog posts',                  "${devSiteBase}blog"
        item '',                            "https://groovy.apache.org/events.html" // keep ASF web crawler somewhat happy
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
        item 'Discuss on the mailing list', "${userSiteBase}mailing-lists.html",                'fa-classic fa-regular fa-envelope'
        item 'Groovy on X',                 'https://x.com/ApacheGroovy',                       'fa-brands fa-x-twitter'
        item 'Groovy on Bluesky',           'https://bsky.app/profile/groovy.apache.org',       'fa-brands fa-bluesky'
        item 'Groovy on Mastodon',          'https://fosstodon.org/@ApacheGroovy',              'fa-brands fa-mastodon'
        item 'Groovy on LinkedIn',          'https://www.linkedin.com/company/106402668/admin/dashboard/',  'fa-brands fa-linkedin'
        item 'Events and conferences',      "${userSiteBase}events.html",                       'fa-classic fa-solid fa-calendar-days'
        item 'Source code on GitHub',       'https://github.com/apache/groovy',                 'fa-brands fa-github'
        item 'Report issues in Jira',       "${userSiteBase}reporting-issues.html",             'fa-classic fa-solid fa-bug'
        item 'Stack Overflow questions',    'http://stackoverflow.com/questions/tagged/groovy', 'fa-brands fa-stack-overflow'
        item 'Slack Community',             'http://www.groovycommunity.com/',                  'fa-brands fa-slack'
    }
}

pages {
//    page 'index', 'index', [:]
    page 'search', 'search', [category: 'Search']
    page 'download', 'download', [category: 'Download', distributions: distributions]
    page 'versioning', 'versioning', [category: 'Download']
    page 'snapshots', 'snapshots', [category: 'Develop']
    page 'contribute', 'index', [category: 'Develop']
    page 'guidelines', 'guidelines', [category: 'Develop']
    page 'buildstatus', 'buildstatus', [category: 'Develop']
    page 'faq', 'faq', [category: 'Documentation']
    page '404','404', [:]
    page 'eventsForwarder','events', [:]
}

documentation {
    section('Getting started','fa-graduation-cap') {
        //          NAME                                     TARGET HTML         DOCPAGE HTML                       GENERATE
        item 'Download Groovy',                             'download',         'download',                         false
    }
}

downloads {
    distribution('Groovy 5.0') {
        description {
            a(href: "${userSiteBase}releasenotes/groovy-5.0.html", 'Groovy 5.0')
            yield ' is the latest stable '
            a(href: 'versioning.html', 'version')
            yield ' of Groovy designed for JDK11+.'
        }
        version('5.0.2') {
            stable true
//            windowsInstaller 'https://groovy.jfrog.io/artifactory/dist-release-local/groovy-windows-installer/groovy-5.0.2/'
        }
        version('5.0.1') {
            stable true
            archive true
            windowsInstaller 'https://groovy.jfrog.io/artifactory/dist-release-local/groovy-windows-installer/groovy-5.0.1/'
        }
    }
    distribution('Groovy 4.0') {
        description {
            a(href: "${userSiteBase}releasenotes/groovy-4.0.html", 'Groovy 4.0')
            yield ' is the previous stable '
            a(href: 'versioning.html', 'version')
            yield ' of Groovy designed for JDK8+ with much improved JPMS support.'
        }
        version('4.0.29') {
            stable true
//            windowsInstaller 'https://groovy.jfrog.io/artifactory/dist-release-local/groovy-windows-installer/groovy-4.0.29/'
        }
        version('4.0.28') {
            stable true
            archive true
            windowsInstaller 'https://groovy.jfrog.io/artifactory/dist-release-local/groovy-windows-installer/groovy-4.0.28/'
        }
    }
    distribution('Groovy 3.0') {
        description {
            a(href: "${userSiteBase}releasenotes/groovy-3.0.html", 'Groovy 3.0')
            yield ' is an earlier '
            a(href: 'versioning.html', 'version')
            yield ' of Groovy still in widespread use and designed for JDK8+ with a new more flexible parser.'
        }
        version('3.0.25') {
            stable true
            windowsInstaller 'https://groovy.jfrog.io/artifactory/dist-release-local/groovy-windows-installer/groovy-3.0.25/'
        }
    }
    distribution('Groovy 2.5') {
        description {
            a(href: "${userSiteBase}releasenotes/groovy-2.5.html", 'Groovy 2.5')
            yield ' is an earlier '
            a(href: 'versioning.html', 'version')
            yield ' of Groovy still in widespread use.'
            yield ' We encourage users to upgrade from this version as we no longer support this version actively.'
        }
        version('2.5.23') {
            stable true
            windowsInstaller 'https://groovy.jfrog.io/artifactory/dist-release-local/groovy-windows-installer/groovy-2.5.23/'
        }
    }
}
