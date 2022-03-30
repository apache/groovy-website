layout 'layouts/main.groovy', true,
        pageTitle: "The Apache Groovy programming language - Snapshot artifacts",
        mainContent: contents {

            div(id: 'content', class: 'page-1') {
                div(class: 'row') {
                    div(class: 'row-fluid') {
                        div(class: 'col-lg-3') {
                            ul(class: 'nav-sidebar') {
                                li {
                                    a(href: 'download.html', 'Download Official Releases of Groovy')
                                }
                            }
                        }

                        div(class: 'col-lg-8 col-lg-pull-0') {
                            h1('Snapshot artifact information')
                            p "The Groovy project values our official releases highly. They are checked by our test suite of over 10000 tests before releasing and cross-checked by humans as part of a ${$a(href:'https://www.apache.org/legal/release-policy.html#:~:text=Each%20PMC%20MUST%20obey%20the,by%20PMC%20members%20are%20binding.','formal release process')}."
                            p 'Snapshots are not official releases and are made available only to the development community to assist with integration testing leading up to an official release.'
                            h3 'Maven repositories'
                            table(class: 'table') {
                                thead {
                                    tr {
                                        th 'Groovy version(s)'
                                        th 'Official Release Jars'
                                        th 'Snapshot Jars'
                                    }
                                }
                                tbody {
                                    tr {
                                        td {
                                            strong {
                                                em 'Groovy 1.x to 3.x'
                                            }
                                            br()
                                            em 'org/codehaus/groovy'
                                        }
                                        td {
                                            a(href:'https://repo1.maven.org/maven2/','Maven Central')
                                            yield ' '
                                            a(href:'https://repo1.maven.org/maven2/org/codehaus/groovy/','(Browse)')
                                            br()
                                            a(href:'https://groovy.jfrog.io/artifactory/libs-release-local/','Groovy artifactory release repository')
                                            yield ' '
                                            a(href:'https://groovy.jfrog.io/artifactory/libs-release-local/org/codehaus/groovy','(Browse)')
                                        }
                                        td {
                                            a(href:'https://groovy.jfrog.io/artifactory/libs-snapshot-local/','Groovy artifactory snapshots repository')
                                            yield ' '
                                            a(href:'https://groovy.jfrog.io/artifactory/libs-snapshot-local/org/codehaus/groovy','(Browse)')
                                        }
                                    }
                                    tr {
                                        td {
                                            strong {
                                                em 'Groovy 4.x+'
                                            }
                                            br()
                                            em 'org/apache/groovy'
                                        }
                                        td {
                                            a(href:'https://repo1.maven.org/maven2/','Maven Central')
                                            yield ' '
                                            a(href:'https://repo1.maven.org/maven2/org/apache/groovy/','(Browse)')
                                            br()
                                            a(href:'https://groovy.jfrog.io/artifactory/libs-release-local/','Groovy artifactory release repository')
                                            yield ' '
                                            a(href:'https://groovy.jfrog.io/artifactory/libs-release-local/org/apache/groovy','(Browse)')
                                        }
                                        td {
                                            a(href:'https://repository.apache.org/content/repositories/snapshots/','ASF Snapshots repository')
                                            yield ' '
                                            a(href:'https://repository.apache.org/content/repositories/snapshots/org/apache/groovy','(Browse)')
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
