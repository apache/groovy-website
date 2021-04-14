layout 'layouts/main.groovy', true,
        pageTitle: 'The Apache Groovy programming language - Thanks',
        mainContent: contents {
            div(id: 'content', class: 'page-1') {
                div(class: 'row') {
                    div(class: 'row-fluid') {
                        div(class: 'col-lg-3') {
                            include template: 'includes/support-navbar.groovy'
                        }

                        div(class: 'col-lg-8 col-lg-pull-0') {
                            include template: 'includes/contribute-button.groovy'
                            h1 {
                                i(class: 'fa fa-building-o') {}
                                yield ' Thanks'
                            }
                            article {
                                p {
                                    yield '''
                                        Groovy would not be the successful Open Source project it is today,
                                        without the help of its users, forming the base of a wider Groovy
                                        community and '''
                                    a(href: 'ecosystem.html', 'ecosystem')
                                    yield ' of projects using Groovy.'
                                }
                                p "The Apache Groovy team would like to thank:"
                                ul {
                                    li """
                                        ${$a(href: 'http://www.apache.org', 'The Apache Software Foundation')} which owns
                                        the project. We want to ${$a(href: 'http://www.apache.org/foundation/thanks.html', 'thank')} the Apache
                                        Software Foundation and its sponsors for providing support for the Apache Community of open-source software projects.
                                     """
                                    li """
                                        ${$a(href: 'http://www.jetbrains.com/', 'JetBrains')} cover the cost of our server
                                        which runs our continuous integration server, hosts our documentation and website,
                                        and they are also offering free licenses for
                                        the ${$a(href: 'http://www.jetbrains.com/teamcity/', 'TeamCity')} integration server and
                                        the ${$a(href: 'http://www.jetbrains.com/idea/', 'IntelliJ IDEA')} development environment.
                                    """
                                    li """
                                        ${$a(href: 'http://www.jfrog.com/', 'JFrog')} provide infrastructure
                                        for deploying and hosting snapshots and releases of older versions of Groovy
                                        and an additional mirror for newer versions,
                                        thanks to their ${$a(href: 'https://jfrog.com/artifactory/', 'Artifactory')} offering.
                                    """
                                }
                                p """
                                    Sponsors are a key ingredient of the success of the Apache projects.
                                    If you consider helping the project in any way,
                                    please don't hesitate to contact the Groovy development team.
                                    Your help will be most appreciated.
                                """
                            }
                            hr(class: 'divider')
                        }
                    }
                }
            }
        }
