layout 'layouts/main.groovy', true,
        pageTitle: 'The Apache Groovy programming language - Continuous integration',
        mainContent: contents {
            div(id: 'content', class: 'page-1') {
                div(class: 'row') {
                    div(class: 'row-fluid') {
                        div(class: 'col-lg-8 col-lg-pull-0') {
                            include template: 'includes/contribute-button.groovy'
                            h1 {
                                i(class: 'fa fa-circle-o-notch') {}
                                yield ' Continuous integration'
                            }
                            article {
                                p """
                                    Our ${
                                    $a(href: 'http://ci.groovy-lang.org?guest=1', 'continuous integration server')
                                },
                                    sponsored by ${$a(href: 'http://www.jetbrains.com', 'JetBrains')},
                                    builds Groovy against multiple JDK versions, as well as some projects from the community tested
                                    against development versions of Groovy:
                                """
                                hr(class: 'divider')

                                h2 'Groovy builds'

                                def renderBuilds = { Map builds ->
                                    table(class: 'table table-stripped') {
                                        thead {
                                            tr {
                                                th('Build name')
                                                th('Status')
                                            }
                                        }
                                        tbody {
                                            builds.each { name, ref ->
                                                def (id, branch) = ref
                                                tr {
                                                    td(name)
                                                    td {
                                                        a(href: "https://ci.groovy-lang.org/viewType.html?buildTypeId=$id&tab=buildTypeStatusDiv&guest=1") {
                                                            img(src: """https://ci.groovy-lang.org/app/rest/builds/buildType:(id:$id)${branch?",branch:$branch":''}/statusIcon""")
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                renderBuilds([
                                        'Groovy master, JDK 11': ['MasterTestAllJdk11', ''],
                                        'Groovy master, JDK 8': ['MasterTestAllJdk8', ''],
                                        'Groovy 3.0.X, JDK 8': ['Groovy30xTestAllJdk8', ''],
                                        'Groovy 2.5.X, JDK 8': ['Groovy25xCheckJdk8', ''],
                                        'Groovy 2.5.X, JDK 11': ['Groovy25xCheckJdk11', ''],
                                        'Groovy 2.4.X, JDK 8': ['Groovy24xCheckJdk8', '']
                                ])

                                hr(class: 'divider')

                                h2 'Community joint builds'
                                renderBuilds([
                                        'Grails 3.2.x, Groovy 2.4.X': ['JointBuilds_Grails_Grails32x_Groovy24x', ''],
                                        'GORM 6.1.x, Groovy 2.4.X': ['JointBuilds_Grails_Gorm61xGroovy24x', ''],
                                        'Nextflow master, Groovy 2.4.X': ['JointBuilds_Nextflow_Groovy24xJointBuild', ''],
                                        'Ratpack master, Groovy 2.4.X': ['JointBuilds_Ratpac_RatpackGroovy24x', '']
                                ])

                                p """
                                    The Groovy development team is keen to test Groovy development versions against important
                                    ecosystem projects. If you want your project to be added to the list, please contact
                                    us on the ${$a(href:'http://groovy-lang.org/mailing-lists.html','development mailing list')}.
                                """
                            }
                        }
                    }
                }
            }
        }
